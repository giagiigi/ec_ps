package jp.co.sint.webshop.web.action.back.service;

import java.util.ArrayList;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.MemberInfoBean;
import jp.co.sint.webshop.web.bean.back.service.MemberInfoBean.CustomerSearchedBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;

public class MemberInfoSelectAction extends WebBackAction<MemberInfoBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.SERVICE_USER_DATA_READ);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (StringUtil.hasValue(getCustomerCode())) {
      return true;
    }
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    MemberInfoBean bean = getBean();
    CustomerSearchedBean customerInfo = new CustomerSearchedBean();
    customerInfo.setCustomerCode(getCustomerCode());
    bean.setCustomerInfo(customerInfo);

    String historyMode = "";
    bean.setDisplayShippingButton(true);
    if (bean.isDisplayShippingButton()) {
      historyMode = MemberInfoHistoryAction.SHIPPING_MODE;
    } else if (bean.isDisplayInquiryButton()) {
      historyMode = MemberInfoHistoryAction.INQUIRY_MODE;
    } else {
      historyMode = MemberInfoHistoryAction.COUPON_MODE;
    }

    if (StringUtil.hasValue(historyMode)) {
      bean.setDisplayHistoryArea(true);
      setNextUrl("/app/service/member_info/history/" + historyMode);
    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {
    MemberInfoBean bean = (MemberInfoBean) getRequestBean();
    bean.setPageMode(true);
    bean.setCustomerList(new ArrayList<CustomerSearchedBean>());
    bean.setPagerValue(new PagerValue());
  }

  /**
   * URLから顧客コードを取得します。<BR>
   * 
   * @return customerCode
   */
  private String getCustomerCode() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.MemberInfoSelectAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109011003";
  }
}
