package jp.co.sint.webshop.web.action.back.communication;



import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PlanEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1030740:促销企划登录/更新のアクションクラスです
 * 
 * @author OB.
 */
public class PlanEditMoveAction extends WebBackAction<PlanEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    String[] args = getRequestParameter().getPathArgs();
    if(args.length == 0 && Permission.PLAN_UPDATE_SHOP.isDenied(login)) {
      return false;
    }
    return Permission.PLAN_READ_SHOP.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
//	if (getRequestParameter().getPathArgs().length == 0) {
//	  throw new URLNotFoundException();
//	}
//	getBean().setPlanTypeMode(getRequestParameter().getPathArgs()[0]);
//	if (getBean().getPlanTypeMode().equals(PlanType.PROMOTION.getValue()) || getBean().getPlanTypeMode().equals(PlanType.FEATURE.getValue())) {
//	} else {
//	  throw new URLNotFoundException();
//	}
	return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
	String[] params = getRequestParameter().getPathArgs();
	CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
	if (params.length == 2 && params[0].equals("add")) {
	 if (communicationService.getPlan(getBean().getPlanCode())== null) {
	    addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "企划"));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
	  }
	  setNextUrl("/app/communication/plan_related/init/" + getBean().getPlanCode() + "/" + params[1]);
	} else if(params.length == 2){
	  String detailType = params[0];
	  String detailCode = params[1];
	  if (communicationService.getPlanDetail(getBean().getPlanCode(), detailType, detailCode) == null) {
	    addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "企划明细"));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
	  }
	  setNextUrl("/app/communication/plan_related/init/"+ getBean().getPlanCode() + "/" + detailType +"/" + detailCode);
	}
	DisplayTransition.add(getBean(), "/app/communication/plan_edit/init/"+ getBean().getPlanTypeMode() + "/" + getBean().getPlanCode(), getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
	if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
	  return "促销企划登录初期表示处理";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "特集企划登录初期表示处理";
	}
    return "";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
	  return "5106082003";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "5106092003";
	}
    return "";
  }
}
