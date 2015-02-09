package jp.co.sint.webshop.web.action.back.customer;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CouponStatusListSearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CouponListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1030140:ポイント履歴のアクションクラスです

 * 
 * @author System Integrator Corp.
 */
public class CouponListCompleteAction extends CouponListBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。

   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 参照権限チェック
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_COUPON_READ.isGranted(login);
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    // 検索条件を取得

    CouponListBean bean = (CouponListBean) getBean();

    String[] urlParam = getRequestParameter().getPathArgs();
    // 存在チェック
    if (urlParam.length > 0) {
      if (cs.isNotFound(urlParam[0]) || cs.isWithdrawed(urlParam[0])) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.customer.PointHistoryCompleteAction.0")));
        return BackActionResult.RESULT_SUCCESS;
      }
    } else {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.PointHistoryCompleteAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    // 検索条件を取得

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
    
    BackLoginInfo login = getLoginInfo();
    nextBean.setUpdateMode(Permission.CUSTOMER_POINT_INVEST.isGranted(login));
    nextBean.setDeleteMode(Permission.CUSTOMER_POINT_DELETE.isGranted(login));

    // ポイント登録部初期化
    nextBean.getEdit().setDescription("");
    nextBean.getEdit().setCouponIssueNo("");

    setRequestBean(nextBean);

    return BackActionResult.RESULT_SUCCESS;
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
    return Messages.getString("web.action.back.customer.PointHistoryCompleteAction.3");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103014001";
  }

}
