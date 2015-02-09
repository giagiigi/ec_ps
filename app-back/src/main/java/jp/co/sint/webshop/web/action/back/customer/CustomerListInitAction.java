package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CustomerListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030110:顧客マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerListInitAction extends CustomerListBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 参照権限チェック
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      return Permission.CUSTOMER_READ.isGranted(getLoginInfo());
    } else {
      if (getLoginInfo().isSite()) {
        return Permission.CUSTOMER_READ.isGranted(getLoginInfo());
      } else {
        return Permission.CUSTOMER_READ_SHOP.isGranted(getLoginInfo());
      }
    }
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CustomerListBean bean = new CustomerListBean();

    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setSearchCustomerStatus(CustomerStatus.MEMBER.getValue());
    bean.setSearchCustomerGroupList(s.getCustomerGroupNames());

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * 画面上に表示されるボタンの表示/非表示を制御します。<BR>
   * 新規顧客登録ボタン/CSV出力系ボタン/削除ボタン<BR>
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    CustomerListBean nextBean = (CustomerListBean) getRequestBean();

    // 登録権限チェック
    if (Permission.CUSTOMER_UPDATE.isGranted(login)) {
      // 情報メール存在チェック
      ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
      if (shopService.getInformationMailTypeList().size() > 0) {
        nextBean.setMailDisplayFlg(true);
      } else {
        nextBean.setMailDisplayFlg(false);
      }
      nextBean.setHasNoRequestCustomer(true);
    }
    // データ入出力権限チェック
    if (Permission.CUSTOMER_IO.isGranted(login)) {
      nextBean.setIODisplayFlg(true);
    }
    // 削除権限チェック
    if (Permission.CUSTOMER_DELETE.isGranted(login)) {
      nextBean.setDeleteDisplayFlg(true);
    }
    // サイト管理者権限チェック
    if (Permission.CUSTOMER_POINT_READ.isGranted(login)) {
      nextBean.setPointDisplayFlg(true);
    }
    // サイト管理者権限チェック
    if (Permission.CUSTOMER_COUPON_READ.isGranted(login)) {
      nextBean.setCouponDisplayFlg(true);
    }
    // 注文履歴権限チェック
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      nextBean.setOrderDisplayFlg(getLoginInfo().hasPermission(Permission.ORDER_READ_SITE));
    } else {
      nextBean.setOrderDisplayFlg(getLoginInfo().hasPermission(Permission.ORDER_READ_SHOP));
    }
    setRequestBean(nextBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerListInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103011002";
  }

}
