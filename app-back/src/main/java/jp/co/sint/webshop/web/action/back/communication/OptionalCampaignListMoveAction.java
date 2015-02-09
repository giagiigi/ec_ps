package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.OptionalCampaignListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author OB.
 */
public class OptionalCampaignListMoveAction extends WebBackAction<OptionalCampaignListBean> {
	
	private final String UPDATE = "edit";
	
	private final String REGISTER = "new";

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {

	  return true;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
	  
    String[] urlParam = getRequestParameter().getPathArgs();
    return (urlParam.length ==1 && REGISTER.equals(urlParam[0])) || (urlParam.length == 2 && UPDATE.equals(urlParam[0]));
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String shopCode = getLoginInfo().getShopCode();
    String[] urlParam = getRequestParameter().getPathArgs();

    ////修改公共优惠劵规则的时候判断公共优惠劵规则信息是否存在
    if (UPDATE.equals(urlParam[0])) { 
      CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
      OptionalCampaign campaign = svc.loadOptionalCampaign(shopCode,urlParam[1]);
      if (campaign == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "任意活动"));
        setRequestBean(getBean());
        setNextUrl(null);
        return BackActionResult.RESULT_SUCCESS;
      }
    }

    //新建公共优惠劵规则的时候
    if (urlParam[0].equals("new")) {
      setNextUrl("/app/communication/optional_campaign_edit/init");
    } else if (urlParam[0].equals("edit")) {
      setNextUrl(getUrl("optional_campaign_edit", urlParam[1]));
    } else {
      addErrorMessage(WebMessage.get(CommunicationErrorMessage.MOVE_ACTION_ERROR, "任意编号"));
      setRequestBean(getBean());
      setNextUrl(null);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/communication/optional_campaign_list/search_back", getSessionContainer());
    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "公共优惠券发行规则详细信息画面跳转处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106071002";
  }
  private String getUrl(String jspId, String campaignCode) {
    return "/app/communication/" + jspId + "/select/" + campaignCode;
  }

}
