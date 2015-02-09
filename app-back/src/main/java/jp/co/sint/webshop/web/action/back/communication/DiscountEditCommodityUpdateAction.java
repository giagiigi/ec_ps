package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.UsingFlg;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.DiscountEditBean;
import jp.co.sint.webshop.web.bean.back.communication.DiscountEditBean.DiscountDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1061210:限时限量折扣编辑画面商品更新表示处理
 * 
 * @author System Integrator Corp.
 */
public class DiscountEditCommodityUpdateAction extends DiscountEditBaseAction {

  
  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    return Permission.DISCOUNT_UPDATE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    String[] param = getRequestParameter().getPathArgs();
    DiscountDetailBean bean = getBean().getEdit();

    if (param.length == 2) {
      CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

      DiscountCommodity dc = communicationService.getDiscountCommodity(param[0], param[1]);
      if (dc == null) {
        throw new URLNotFoundException();
      } else {
        bean.setCommodityCode(param[1]);
        bean.setDiscountPrice(getRequestParameter().get("dp" + param[1]));
        bean.setCustomerMaxTotalNum(getRequestParameter().get("cm" + param[1]));
        bean.setSiteMaxTotalNum(getRequestParameter().get("sm" + param[1]));
        if (StringUtil.hasValue(getRequestParameter().get("uf" + param[1]))) {
          bean.setUseFlg(UsingFlg.VISIBLE.longValue());
        } else {
          bean.setUseFlg(UsingFlg.HIDDEN.longValue());
        }
        bean.setDiscountDirectionsCn(getRequestParameter().get("cn" + param[1]));
        bean.setDiscountDirectionsJp(getRequestParameter().get("jp" + param[1]));
        bean.setDiscountDirectionsEn(getRequestParameter().get("en" + param[1]));
        
        if(StringUtil.isNullOrEmpty(getRequestParameter().get("check_cn_" + param[1]))) {
          bean.setRankcn("");
        } else {
          addWarningMessage("更新排序请先勾中显示标志");
          bean.setRankcn(getRequestParameter().get("rank_cn_" + param[1]));
        }
        if(StringUtil.isNullOrEmpty(getRequestParameter().get("check_jp_" + param[1]))) {
          bean.setRankjp("");
        } else {
          addWarningMessage("更新排序请先勾中显示标志");
          bean.setRankjp(getRequestParameter().get("rank_jp_" + param[1]));
        }
        if(StringUtil.isNullOrEmpty(getRequestParameter().get("check_en_" + param[1]))) {
          bean.setRanken("");
        } else {
          addWarningMessage("更新排序请先勾中显示标志");
          bean.setRanken(getRequestParameter().get("rank_en_" + param[1]));
        }
      }
    } else {
      throw new URLNotFoundException();
    }

    for (DiscountDetailBean ddb : getBean().getList()) {
      if (bean.getCommodityCode().equals(ddb.getCommodityCode())) {
        ddb.setDiscountPrice(bean.getDiscountPrice());
        ddb.setCustomerMaxTotalNum(bean.getCustomerMaxTotalNum());
        ddb.setSiteMaxTotalNum(bean.getSiteMaxTotalNum());
        ddb.setUseFlg(bean.getUseFlg());
        ddb.setDiscountDirectionsCn(bean.getDiscountDirectionsCn());
        ddb.setDiscountDirectionsJp(bean.getDiscountDirectionsJp());
        ddb.setDiscountDirectionsEn(bean.getDiscountDirectionsEn());
        ddb.setRankcn(bean.getRankcn());
        ddb.setRankjp(bean.getRankjp());
        ddb.setRanken(bean.getRanken());
      }
    }

    return validateItems(bean, "discountPrice", "customerMaxTotalNum", "siteMaxTotalNum", "useFlg", "discountDirectionsCn",
        "discountDirectionsJp", "discountDirectionsEn","rankcn","rankjp","ranken");
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    DiscountEditBean bean = getBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    DiscountCommodity dc = new DiscountCommodity();
    setDiscountCommodity(bean, dc);

    ServiceResult result = communicationService.updateDiscountCommodity(dc);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.DISCOUNT_COMMODITY_NOT_EXIST_ERROR, bean.getDiscountCode(), dc
              .getCommodityCode()));
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        } else if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    }

    setNextUrl("/app/communication/discount_edit/select/" + bean.getDiscountCode() + "/commodityUpdate");
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.DiscountEditCommodityUpdateAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106122005";
  }

}
