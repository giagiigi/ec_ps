package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PlanListBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;

/**
 * U1030740:企划一览のアクションクラスです
 * 
 * @author OB.
 */
public class PlanListInitAction extends WebBackAction<PlanListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.PLAN_READ_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
	String[] params = getRequestParameter().getPathArgs();
	if (params.length != 1) {
	  throw new URLNotFoundException();
	}
    return true;
  }
  
  public void init() {
    PlanListBean bean = new PlanListBean();
    bean.setPlanTypeMode(getRequestParameter().getPathArgs()[0]);
    setBean(bean);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
	PlanListBean bean = getBean();
	if (Permission.PLAN_UPDATE_SHOP.isGranted(getLoginInfo())) {
	  bean.setUpdateAuthorizeFlg(true);
	} else {
	  bean.setUpdateAuthorizeFlg(false);
	}
	if (Permission.PLAN_DELETE_SHOP.isGranted(getLoginInfo())) {
      bean.setDeleteAuthorizeFlg(true);
	} else {
	  bean.setDeleteAuthorizeFlg(false);
	}
	  
	if (PlanType.PROMOTION.getValue().equals(bean.getPlanTypeMode())) {
		bean.setPlanDetailTypeList(DIContainer.getPlanDetailTypeValue().getSalePlanDetailTypes());
    // 20130702 txw add start
    bean.setTjFlg(false);
    // 20130702 txw add end
	} else {
	  // 20130702 txw update start
	  // bean.setPlanDetailTypeList(DIContainer.getPlanDetailTypeValue().getFeaturedPlanDetailTypes());
	  bean.setTjFlg(true);
	  // 20130702 txw update end
	}
	setRequestBean(bean);

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
	  return "促销企划管理画面初期表示处理";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "特集企划管理画面初期表示处理";
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
	  return "5106081001";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "5106091001";
	}
    return "";
   }
}
