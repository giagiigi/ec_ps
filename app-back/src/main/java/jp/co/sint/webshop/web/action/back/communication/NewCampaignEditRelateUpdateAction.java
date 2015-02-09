package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.CampaignConditionFlg;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.NewCampaignConditionType;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.campain.CampaignMain;
import jp.co.sint.webshop.service.communication.CampaignLine;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.NewCampaignEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060320:キャンペーンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class NewCampaignEditRelateUpdateAction extends NewCampaignEditBaseAction {

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

    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0) {
      if ("delete".equals(getRequestParameter().getPathArgs()[0])) {
        auth = Permission.CAMPAIGN_DELETE_SHOP.isGranted(login) || (Permission.CAMPAIGN_DELETE_SITE.isGranted(login) && getConfig().isOne());
      }
    }

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    NewCampaignEditBean bean = getBean();
    // 新建关联商品登录
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0) {
      if ("login".equals(getRequestParameter().getPathArgs()[0])) {
        CampaignLine campaignLine = new CampaignLine();
        CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
        campaignLine = communicationService.loadCampaignLine(bean.getCampaignCode());
        boolean flag = validateBean(bean.getRelatedCommodityBean());
        if (!flag) {
          return false;
        } else {
          boolean commodityFlg = isCommodity(bean, true, false);
          boolean existFlg = isExists(bean, campaignLine);
          boolean resultFlg = isRelated(bean);
          //所有的活动的关联商品登录时，套餐商品时不可登录  20141230 hdh 套装品可以登录
//          boolean setCommodityFlg = isSetCommodity(bean);
          if (commodityFlg) {
            // 商品不存在
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "商品:" + bean.getRelatedCommodityBean().getRelatedComdtyCode()));
            return false;
          } else if (resultFlg) {
            // 不是通常品
            addErrorMessage(WebMessage.get(CommunicationErrorMessage.NO_COMMON_COMMODITY));
            return false;
          } 
//          else if (setCommodityFlg) {
//              addErrorMessage(WebMessage.get(CommunicationErrorMessage.SET_COMMODITY_ERROR));
//              return false;
//          }
          else if (existFlg) {
            // 在数据中存在
            addErrorMessage(WebMessage.get(CommunicationErrorMessage.DUPLICATED_COMMODITY_ERROR, "关联"));
            return false;
          }
          
          //是否存在其他活动中 
          
          if(existInOtherCampaignMain()){
            addErrorMessage("该商品已存在其他活动中");
            return false;
          }
          
        }
      } else if ("delete".equals(getRequestParameter().getPathArgs()[0])) {
        if (bean.getCheckedCode().size() < 1
            || !StringUtil.hasValueAllOf(ArrayUtil.toArray(getBean().getCheckedCode(), String.class))) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.COMMODITY_NO_CHOOSE, "关联商品"));
          return false;

        }
      }
    }
    return true;
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
    boolean flag = false;
    boolean isExists = false;
    ServiceResult result = null;
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0
        && "login".equals(getRequestParameter().getPathArgs()[0])) {
      campaignLine = communicationService.loadCampaignLine(bean.getCampaignCode());
      if (campaignLine.getConditionList() != null && campaignLine.getConditionList().size() > 0) {
        List<CampaignCondition> cList = campaignLine.getConditionList();
        CampaignCondition condition = null;

        if (cList != null && cList.size() > 0) {
          for (int i = 0; i < cList.size(); i++) {
            condition = cList.get(i);
            if (NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(condition.getCampaignConditionType())) {
              condition.setAttributrValue(setJoin(bean, campaignLine));
              break;
            }
            if (NewCampaignConditionType.COMMODITY_RELATED.longValue().equals(condition.getCampaignConditionType())) {
              condition.setAttributrValue(setJoin(bean, campaignLine));
              break;
            }
          }
        }
      }
      
      flag = true;
    }
    
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0
        && "delete".equals(getRequestParameter().getPathArgs()[0])) {
      campaignLine = communicationService.loadCampaignLine(bean.getCampaignCode());

      if (campaignLine.getConditionList() != null && campaignLine.getConditionList().size() > 0) {
        List<CampaignCondition> cList = campaignLine.getConditionList();
        CampaignCondition condition = null;
        String str = "";

        for (int i = 0; i < bean.getRelatedList().size(); i++) {
          boolean equalFlg = false;
          for (int j = 0; j < bean.getCheckedCode().size(); j++) {
            if (bean.getCheckedCode().get(j).equals(bean.getRelatedList().get(i).getRelatedComdtyCode())) {
              equalFlg = true;
              break;
            }
          }

          if (!equalFlg) {
            if (StringUtil.isNullOrEmpty(str)) {
              str += bean.getRelatedList().get(i).getRelatedComdtyCode();
            } else {
              str += "," + bean.getRelatedList().get(i).getRelatedComdtyCode();
            }

          }
        }

        if (cList != null && cList.size() > 0) {
          for (int i = 0; i < cList.size(); i++) {
            condition = cList.get(i);
            if (NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(condition.getCampaignConditionType())) {
              condition.setAttributrValue(str);
              break;
            }
            if (NewCampaignConditionType.COMMODITY_RELATED.longValue().equals(condition.getCampaignConditionType())) {
              condition.setAttributrValue(str);
              break;
            }
          }
        }
        
        campaignLine.getCampaignMain().setUpdatedDatetime(bean.getUpdateDatetime());
        result = communicationService.updateCampaignCondition(campaignLine, true);
        
        if (result.hasError()) {
          this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DELETE_FAILED_ERROR, "关联商品"));
          return BackActionResult.RESULT_SUCCESS;
        } else {
          setNextUrl("/app/communication/new_campaign_edit/select/" + bean.getCampaignCode() + "/relatedDelete");
        }
      }
    }

    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0
        && "area".equals(getRequestParameter().getPathArgs()[0])) {
      campaignLine = communicationService.loadCampaignLine(bean.getCampaignCode());

      if (campaignLine.getConditionList() != null && campaignLine.getConditionList().size() > 0) {
        List<CampaignCondition> cList = campaignLine.getConditionList();
        CampaignCondition condition = null;

        String str = "";
        for (int i = 0; i < bean.getPrefectureBeanList().size(); i++) {
          for (int j = 0; j < bean.getPrefectureCode().size(); j++) {
            if (bean.getPrefectureCode().get(j).equals(bean.getPrefectureBeanList().get(i).getPrefectureCode())) {
              if (StringUtil.isNullOrEmpty(str)) {
                str += bean.getPrefectureCode().get(j);
              } else {
                str += "," + bean.getPrefectureCode().get(j);
              }
              break;
            }
          }
        }

        if (cList != null && cList.size() > 0) {
          for (int i = 0; i < cList.size(); i++) {
            condition = cList.get(i);
            if (NewCampaignConditionType.ORDER_ADDRESS.getValue().equals(condition.getCampaignConditionType() + "")) {
              condition.setAttributrValue(str);
              isExists = true;
              break;
            }
          }
        }
        if (isExists == false) {
          condition = new CampaignCondition();
          condition.setCampaignCode(campaignLine.getCampaignMain().getCampaignCode());
          condition.setAttributrValue(str);
          condition.setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue());
          condition.setCampaignConditionType(NewCampaignConditionType.ORDER_ADDRESS.longValue());
          campaignLine.getConditionList().add(condition);
        }

        campaignLine.getCampaignMain().setUpdatedDatetime(bean.getUpdateDatetime());
        result = communicationService.updateCampaignCondition(campaignLine, isExists);
        if (result.hasError()) {
          this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_FAILED_ERROR, bean.getRelatedCommodityBean().getRelatedComdtyCode()));
          return BackActionResult.RESULT_SUCCESS;
        } else {
          setNextUrl("/app/communication/new_campaign_edit/select/" + bean.getCampaignCode() + "/area");
        }
      }
    }

    if (flag) {
      campaignLine.getCampaignMain().setUpdatedDatetime(bean.getUpdateDatetime());
      // 向数据库中增加促销信息
      if (CampaignMainType.SHIPPING_CHARGE_FREE.getValue().equals(bean.getCampaignType())) {

        if (bean.getCheckList() != null && bean.getCheckList().size() > 0) {
          result = communicationService.updateCampaignLine(campaignLine, true, true, false);
        }

      } else if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType())
          && CouponIssueType.PROPORTION.getValue().equals(bean.getDiscountType())) {
        result = communicationService.updateCampaignLine(campaignLine, true, true, true);
      } else if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType())
          && CouponIssueType.FIXED.getValue().equals(bean.getDiscountType())) {
        result = communicationService.updateCampaignLine(campaignLine, true, true, true);
      } else if (CampaignMainType.MULTIPLE_GIFT.getValue().equals(bean.getCampaignType())) {
        result = communicationService.updateCampaignLine(campaignLine, true, true, false);
      } else if (CampaignMainType.GIFT.getValue().equals(bean.getCampaignType())) {
        result = communicationService.updateCampaignLine(campaignLine, true, true, false);
      }

      if (result.hasError()) {
        this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.UPDATE_FAILED_ERROR, bean
            .getRelatedCommodityBean().getRelatedComdtyCode()));
        return BackActionResult.RESULT_SUCCESS;

      } else {
        setNextUrl("/app/communication/new_campaign_edit/select/" + bean.getCampaignCode() + "/relatedLogin");
      }
    }

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.NewCampaignEditInitAction.005");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106102004";
  }

  
  public boolean existInOtherCampaignMain(){
    NewCampaignEditBean bean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());

    List<CampaignMain> cmList = service.getCampaignMainByDateAndType(DateUtil.fromString(bean.getCampaignStartDate()), DateUtil
        .fromString(bean.getCampaignEndDate()), CampaignMainType.GIFT.longValue());
    if (cmList == null) {
      return false; // 当前没有活动
    }

    String newCommodityCode = bean.getRelatedCommodityBean().getRelatedComdtyCode();

    for (CampaignMain cmMain : cmList) {
      CampaignCondition cc = service.getCampaignConditionByType(cmMain.getCampaignCode());

      List<String> attrValue = new ArrayList<String>();
      if (cc != null && StringUtil.hasValue(cc.getAttributrValue())) {
        attrValue.addAll(Arrays.asList(cc.getAttributrValue().split(",")));
      }
      if (attrValue != null && attrValue.size() > 0) {
        if (attrValue.contains(newCommodityCode)) {
          return true;
        }
      }
    }
    return false;
  }
  
}
