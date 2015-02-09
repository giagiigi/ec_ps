package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.CustomerGroupCampaignListBean;

/**
 * U1060510:顾客组别优惠规则管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupCampaignListInitAction extends WebBackAction<CustomerGroupCampaignListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.CUSTOMER_GROUP_CAMPAIGN_READ_SHOP.isGranted(getLoginInfo());
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
	CustomerGroupCampaignListBean bean = new CustomerGroupCampaignListBean();
	
	if (Permission.CUSTOMER_GROUP_CAMPAIGN_UPDATE_SHOP.isGranted(getLoginInfo())) {
	  bean.setUpdateAuthorizeFlg(true);
	} else {
	  bean.setUpdateAuthorizeFlg(false);
	}
	if (Permission.CUSTOMER_GROUP_CAMPAIGN_DELETE_SHOP.isGranted(getLoginInfo())) {
      bean.setDeleteAuthorizeFlg(true);
	} else {
	  bean.setDeleteAuthorizeFlg(false);
	}
	  
	// 顧客グループリストの取得
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setCustomerGroupList(s.getCustomerGroupNames());
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
    return "顾客组别优惠规则管理初期表示处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106051001";
  }

}
