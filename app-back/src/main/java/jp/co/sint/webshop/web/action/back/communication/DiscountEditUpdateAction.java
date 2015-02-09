package jp.co.sint.webshop.web.action.back.communication;

import java.util.List;

import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.DiscountEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1061210:限时限量折扣编辑画面更新表示处理
 * 
 * @author System Integrator Corp.
 */
public class DiscountEditUpdateAction extends DiscountEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
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
    DiscountEditBean bean = getBean();
    boolean flg = validateItems(bean, "discountCode", "discountName", "discountStartDatetime", "discountEndDatetime","commodityTypeNum","soCouponFlg");
    if (flg) {
      // 日付の大小関係チェック
      if (!StringUtil.isCorrectRange(bean.getDiscountStartDatetime(), bean.getDiscountEndDatetime())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        return false;
      }
      //20140923 hdh add start 更新时有效期交叉时商品登录重复验证
      CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
      List<DiscountCommodity> commoditys = communicationService.getDiscountCommodityListByCouponCode(bean.getDiscountCode());
      if(commoditys !=null && commoditys.size()>0){
        List<DiscountHeader> discountList =communicationService.getCrossDiscountHeaderList(DateUtil.fromString(bean.
            getDiscountStartDatetime(),true),DateUtil.fromString(bean.getDiscountEndDatetime(),true));
        if(discountList!=null &&discountList.size()>0){
          for(DiscountCommodity commdidty: commoditys){
            for(DiscountHeader header:discountList){
              if(header.getDiscountCode().equals(bean.getDiscountCode())){  //如果是当前活动，则不处理
                continue;
              }
              DiscountCommodity dc = communicationService.getDiscountCommodity(header.getDiscountCode(),
                  commdidty.getCommodityCode());
              if(dc!=null){
                addErrorMessage(WebMessage.get(CommunicationErrorMessage.DUPLICATED_DISCOUNT_COMMODITY_ERROR, 
                    header.getDiscountCode(),commdidty.getCommodityCode()));
                return false;
              }
              
            }
          }

        }
      }
      //20140923 hdh add end
      
    }

    
    
    return flg;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    DiscountEditBean bean = getBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    DiscountHeader dh = new DiscountHeader();
    setDiscountHeader(bean, dh);

    ServiceResult result = communicationService.updateDiscountHeader(dh);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.DISCOUNT_NOT_EXIST_ERROR, bean.getDiscountCode()));
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        } else if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    }

    setNextUrl("/app/communication/discount_edit/select/" + bean.getDiscountCode() + "/update");
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.DiscountEditUpdateAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106122003";
  }

}
