package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.DiscountType;
import jp.co.sint.webshop.data.domain.NewCampaignConditionType;

import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.communication.CampaignLine;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;

import jp.co.sint.webshop.web.bean.back.communication.NewCampaignEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * キャンペーンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class NewCampaignEditUpdateAction extends NewCampaignEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    // ショップ管理者で更新権限のあるユーザか、サイト管理者で更新権限があり、かつ一店舗モードの
    // 時のみアクセス可能
    boolean auth = Permission.CAMPAIGN_UPDATE_SHOP.isGranted(login)
        || (Permission.CAMPAIGN_UPDATE_SITE.isGranted(login) && getConfig().isOne());
    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    NewCampaignEditBean bean = getBean();

    CampaignMain campaignMain = new CampaignMain();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    campaignMain = communicationService.loadCampaignMain(bean.getCampaignCode());

    if (campaignMain == null) {
      throw new URLNotFoundException();
    }

    boolean flg = validateBean(bean);
    if (flg) {
      // 日付の大小関係チェック
      if (!StringUtil.isCorrectRange(bean.getCampaignStartDate(), bean.getCampaignEndDate())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        return false;
      }
    }
    if (CampaignMainType.SHIPPING_CHARGE_FREE.getValue().equals(bean.getCampaignType())) {
      if (bean.getCheckList().size() < 1
          || !StringUtil.hasValueAllOf(ArrayUtil.toArray(getBean().getCheckList(), String.class))) {
        addErrorMessage(WebMessage.get(CommunicationErrorMessage.CONDITION_TYPE_NO_CHOOSE));
        return false;

      }
    } else if (CampaignMainType.GIFT.getValue().equals(bean.getCampaignType())) {
      if (!StringUtil.isNullOrEmpty(validateUpdateSamePeriod(getBean()))) {
        // 同一赠品不能同时适用于多个特定商品的促销活动
        List<String> list = new ArrayList<String>();
        list = Arrays.asList(validateUpdateSamePeriod(getBean()).split(","));
        for (int i = 0; i < list.size(); i++) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.NO_SPECIAL_MANY_ERROR, list.get(i)));
        }
        return false;
      }
      if(StringUtil.isNullOrEmpty(bean.getMinCommodityNum())){
        addErrorMessage("最小购买数不能为空");
        return false;
      }
      
      if(StringUtil.isNullOrEmpty(bean.getGiftAmount())){
        addErrorMessage("赠品数量不能为空");
        return false;
      }
      
      //
      
      
    } else if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType())) {
      if (DiscountType.CUSTOMER.getValue().equals(bean.getDiscountType())
          && StringUtil.isNullOrEmpty(bean.getDiscoutRate())) {
        this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DISCOUNT_NOT_NULL, "折扣比例"));
        return false;
      } else if (DiscountType.COUPON.getValue().equals(bean.getDiscountType())
          && StringUtil.isNullOrEmpty(bean.getDiscountMoney())) {
        this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DISCOUNT_NOT_NULL, "折扣金额"));
        return false;
      }
    }

    return flg;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    NewCampaignEditBean bean = getBean();
    CampaignLine campaignLine = new CampaignLine();
    // 为dto设置值
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    campaignLine = communicationService.loadCampaignLine(bean.getCampaignCode());

    ServiceResult result = null;
    boolean flg = false;// 删除用
    boolean resultflg = false;// 删除用
    boolean comdityFlg = false;// 增加用
    boolean areaFlg = false;// 增加用
    
    // 向数据库中增加促销信息
    if (CampaignMainType.SHIPPING_CHARGE_FREE.getValue().equals(bean.getCampaignType())) {
      setModifyCampaignLine(bean, campaignLine);
      CampaignCondition campaignCondition = null;
      if (bean.getCheckList() != null && bean.getCheckList().size() > 1) {
        flg = false;
        resultflg = false;
        if (campaignLine.getConditionList().size() == 1) {
          if (!NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(
              campaignLine.getConditionList().get(0).getCampaignConditionType())) {
            campaignCondition = new CampaignCondition();
            campaignCondition.setCampaignCode(bean.getCampaignCode());
            campaignCondition.setCampaignConditionType(NewCampaignConditionType.ORDER_COMMODITY.longValue());

            if (!StringUtil.isNullOrEmpty(bean.getCampaignConditionFlg())) {
              campaignCondition.setCampaignConditionFlg(Long.valueOf(bean.getCampaignConditionFlg()));
            }
            campaignCondition.setAttributrValue("");
            comdityFlg = true;
          } else if (!NewCampaignConditionType.ORDER_ADDRESS.longValue().equals(campaignLine.getConditionList().get(0))) {
            campaignCondition = new CampaignCondition();
            campaignCondition.setCampaignCode(bean.getCampaignCode());
            campaignCondition.setCampaignConditionType(NewCampaignConditionType.ORDER_ADDRESS.longValue());
            if (!StringUtil.isNullOrEmpty(bean.getCampaignConditionFlg())) {
              campaignCondition.setCampaignConditionFlg(Long.valueOf(bean.getCampaignConditionFlg()));
            }
            campaignCondition.setAttributrValue("");
            areaFlg = true;
          }
        }
      } else if (bean.getCheckList() != null && bean.getCheckList().size() == 1) {
        // 选中一个,数据库值也是一个
        if (NewCampaignConditionType.ORDER_COMMODITY.getValue().equals(bean.getCheckList().get(0))) {
          if (campaignLine.getConditionList().size() == 1) {
            if (!NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(
                campaignLine.getConditionList().get(0).getCampaignConditionType())) {
              campaignCondition = new CampaignCondition();
              campaignCondition.setCampaignCode(bean.getCampaignCode());
              campaignCondition.setCampaignConditionType(NewCampaignConditionType.ORDER_COMMODITY.longValue());
              if (!StringUtil.isNullOrEmpty(bean.getCampaignConditionFlg())) {
                campaignCondition.setCampaignConditionFlg(NumUtil.toLong(bean.getCampaignConditionFlg()));
              }
              campaignCondition.setAttributrValue("");
              
              flg = false;// 删除对象商品
              resultflg = true;// 删除地址区域
              comdityFlg = true;// 增加商品
              areaFlg = false;// 增加地址
            }
          } else {
            // 数据库值是二条
            if (campaignLine.getConditionList().size() == 2) {
              if (NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(campaignLine.getConditionList().get(0).getCampaignConditionType())) {
                flg = false;// 删除对象商品
                resultflg = true;// 删除地址区域
                comdityFlg = false;// 增加商品
                areaFlg = false;// 增加地址
              }
            }
          }
        } else {
          // 选择的是区域地址
          if (campaignLine.getConditionList().size() == 1) {
            if (!NewCampaignConditionType.ORDER_ADDRESS.longValue().equals(
                campaignLine.getConditionList().get(0).getCampaignConditionType())) {
              campaignCondition = new CampaignCondition();
              campaignCondition.setCampaignCode(bean.getCampaignCode());
              campaignCondition.setCampaignConditionType(NewCampaignConditionType.ORDER_ADDRESS.longValue());

              if (!StringUtil.isNullOrEmpty(bean.getCampaignConditionFlg())) {
                campaignCondition.setCampaignConditionFlg(Long.valueOf(bean.getCampaignConditionFlg()));
              }
              campaignCondition.setAttributrValue("");
              flg = true;// 删除对象商品
              resultflg = false;// 删除地址区域
              comdityFlg = false;// 增加商品
              areaFlg = true;// 增加地址
            }
          } else {
            // 数据库值是二条
            if (campaignLine.getConditionList().size() == 2) {
              if (NewCampaignConditionType.ORDER_ADDRESS.longValue().equals(
                  campaignLine.getConditionList().get(1).getCampaignConditionType())) {
                flg = true;// 删除对象商品
                resultflg = false;// 删除地址区域
                comdityFlg = false;// 增加商品
                areaFlg = false;// 增加地址
              }

            }
          }
        }
      }

      if (campaignLine.getConditionList().size() < 2 && campaignCondition != null) {
        campaignLine.getConditionList().add(campaignCondition);
      }
      result = communicationService.modifyCampaignLine(campaignLine, true, false, flg, resultflg, comdityFlg, areaFlg);

    } else if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType())
        && CouponIssueType.PROPORTION.getValue().equals(bean.getDiscountType())) {
      setModifyCampaignLine(bean, campaignLine);
      result = communicationService.modifyCampaignLine(campaignLine, true, true, flg, resultflg, comdityFlg, areaFlg);
    } else if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType())
        && CouponIssueType.FIXED.getValue().equals(bean.getDiscountType())) {
      setModifyCampaignLine(bean, campaignLine);
      result = communicationService.modifyCampaignLine(campaignLine, true, true, flg, resultflg, comdityFlg, areaFlg);
    } else if (CampaignMainType.MULTIPLE_GIFT.getValue().equals(bean.getCampaignType())) {
      setModifyCampaignLine(bean, campaignLine);
      result = communicationService.modifyCampaignLine(campaignLine, true, false, flg, resultflg, comdityFlg, areaFlg);
    } else if (CampaignMainType.GIFT.getValue().equals(bean.getCampaignType())) {
      setModifyCampaignLine(bean, campaignLine);
      result = communicationService.modifyCampaignLine(campaignLine, false, false, flg, resultflg, comdityFlg, areaFlg);
    }

    if (result.hasError()) {
      this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.UPDATE_FAILED_ERROR, bean.getCampaignCode()));
      return BackActionResult.RESULT_SUCCESS;

    }
    setNextUrl("/app/communication/new_campaign_edit/select/" + bean.getCampaignCode() + "/update");
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.NewCampaignEditInitAction.004");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106102003";
  }

}
