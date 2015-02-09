package jp.co.sint.webshop.web.action.back.customer;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CouponStatusListSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CouponListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1030140:ポイント履歴のアクションクラスです

 * 
 * @author System Integrator Corp.
 */
public class CouponListInitAction extends CouponListBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。

   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_COUPON_READ.isGranted(login);
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

    CouponListBean bean = new CouponListBean();
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    String[] urlParam = getRequestParameter().getPathArgs();

    // 顧客ポイント関連情報表示

    if (urlParam.length < 1) {
      setNextUrl("/app/common/dashboard/init/");
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }
    CustomerInfo customerInfo = cs.getCustomer(urlParam[0]);
    bean.setCustomerCode(customerInfo.getCustomer().getCustomerCode());
    bean.setEmail(customerInfo.getCustomer().getEmail());
    bean.setMobileNumber(customerInfo.getAddress().getMobileNumber());
    bean.setName(customerInfo.getCustomer().getLastName());
    bean.setPhoneNumber(customerInfo.getAddress().getPhoneNumber());
    
    //List<CustomerCouponInfo> infoList = cs.getCustomerCouponInfoBack(urlParam[0]);
    if (cs.isWithdrawed(urlParam[0])) {
      setNextUrl("/app/common/dashboard/init/");
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    // 検索条件を取得
    CouponStatusListSearchCondition condition = new CouponStatusListSearchCondition();
    condition.setSearchCustomerCode(urlParam[0]);

    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // ポイント履歴取得
    SearchResult<CustomerCoupon> pList = cs.findCouponStatusCustomerInfo(condition);

    CouponListBean nextBean = bean;
    nextBean.getList().clear();

    if (pList != null) {
      // オーバーフローチェック

      prepareSearchWarnings(pList, SearchWarningType.OVERFLOW);

      nextBean.setPagerValue(PagerUtil.createValue(pList));

      List<CustomerCoupon> couponList = pList.getRows();
      setCouponHistoryList(nextBean, couponList);
    }
    // 登録部初期化
    //new PointHistoryEdit();

    // ボタン表示設定

    BackLoginInfo login = getLoginInfo();
    bean.setUpdateMode(Permission.CUSTOMER_COUPON_INVEST.isGranted(login));
   // bean.setDeleteMode(Permission.CUSTOMER_COUPON_DELETE.isGranted(login));

    // ショップのリスト取得
    UtilService service = ServiceLocator.getUtilService(getLoginInfo());
    bean.setShopList(service.getShopNamesDefaultAllShop(false, false));

    this.setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.PointHistoryInitAction.0");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103014003";
  }
 
}
