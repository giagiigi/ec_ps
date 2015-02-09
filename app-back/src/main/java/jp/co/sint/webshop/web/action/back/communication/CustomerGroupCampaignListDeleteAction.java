package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CommunicationServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.CustomerGroupCampaignListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;

/**
 * U1060510:顾客组别优惠规则管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupCampaignListDeleteAction extends CustomerGroupCampaignListSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.CUSTOMER_GROUP_CAMPAIGN_READ_SHOP.isGranted(getLoginInfo()) && Permission.CUSTOMER_GROUP_CAMPAIGN_DELETE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    CustomerGroupCampaignListBean nextBean = getBean();

    // 選択されたレビューIDを取得する
    String[] values = getRequestParameter().getAll("checkBox");

    // チェックボックスが選択されているか
    if (StringUtil.isNullOrEmpty(values[0])) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED, "顾客组别优惠规则"));
      setNextUrl(null);
      this.setRequestBean(nextBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    List<String> successList = new ArrayList<String>();
    List<String> failureList = new ArrayList<String>();
    // 削除処理実行
    for (String campaignCode : values) {
      ServiceResult serviceResult = service.deleteCustomerGroupCampaign(campaignCode);
      if (serviceResult.hasError()) {
	    for (ServiceErrorContent error : serviceResult.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
            failureList.add(Message.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format("顾客组别优惠规则(顾客组别优惠规则编号：{0})",
                campaignCode)));
          } else if(error.equals(CommunicationServiceErrorContent.NEWCOUPONRULE_USE_ERROR)) {
        	addErrorMessage(WebMessage.get(CommunicationErrorMessage.COUPON_DELETE_ERROR, MessageFormat.format(
        			"顾客组别优惠规则(顾客组别优惠规则编号：{0})", campaignCode)));
          }
        }
      } else {
    	successList.add(WebMessage.get(CompleteMessage.DELETE_COMPLETE, MessageFormat.format("顾客组别优惠规则(顾客组别优惠规则编号：{0})",
    	 campaignCode)));
      }
    }
    for (String s : failureList) {
        addErrorMessage(s);
    }
    for (String s : successList) {
      addInformationMessage(s);
    }

    this.setRequestBean(nextBean);

    // 削除後の結果を再検索する
    return super.callService();
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "顾客组别优惠规则管理删除处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106051004";
  }
}
