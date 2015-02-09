package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.customer.DeliveryHistorySearchCondition;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.AddressListBean;
import jp.co.sint.webshop.web.bean.back.customer.AddressListBean.AddressListDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1030210:アドレス帳一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressListInitAction extends WebBackAction<AddressListBean> {

  private CustomerSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new CustomerSearchCondition();
  }

  protected CustomerSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected CustomerSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization;

    // 削除権限チェック
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.CUSTOMER_READ.isGranted(getLoginInfo());
    } else {
      if (getLoginInfo().isSite()) {
        authorization = Permission.CUSTOMER_READ.isGranted(getLoginInfo());
      } else {
        authorization = Permission.CUSTOMER_READ_SHOP.isGranted(getLoginInfo());
      }
    }

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 0) {

      String customerCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(customerCode)) {
        return false;
      }

      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      authorization &= service.isShopCustomer(customerCode, getLoginInfo().getShopCode());
    }

    return authorization;
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

    AddressListBean bean = new AddressListBean();

    String customerCode = "";
    String completeParam = "";

    // parameter[0] : 顧客コード , parameter[1] : 処理完了パラメータ(delete)
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      customerCode = parameter[0];
      if (parameter.length > 1) {
        completeParam = parameter[1];
      }
    }

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    // 顧客存在チェック
    if (StringUtil.isNullOrEmpty(customerCode) || service.isNotFound(customerCode) || service.isWithdrawed(customerCode)) {
      setNextUrl("/app/common/dashboard/init/");
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    // 顧客情報
    CustomerInfo info = service.getCustomer(customerCode);
    Customer customer = info.getCustomer();
    bean.setCustomerCode(customerCode);
    bean.setFirstName(customer.getFirstName());
    bean.setLastName(customer.getLastName());
    bean.setFirstNameKana(customer.getFirstNameKana());
    bean.setLastNameKana(customer.getLastNameKana());
    bean.setEmail(customer.getEmail());

    // 検索条件を取得
    condition.setCustomerCode(customerCode);
    // condition = PagerUtil.createSearchCondition(getRequestParameter(),
    // condition); // 10.1.4 K00168 削除
    condition = getCondition();

    // アドレス帳一覧
    SearchResult<CustomerAddress> result = service.getCustomerAddressList(condition);
    // アドレス帳一覧存在チェック
    if (result.getRowCount() < 1) {
      // delete by os012 20111213 start
      // setNextUrl("/app/common/dashboard/init/");
      // setRequestBean(getBean());
      //
      // return BackActionResult.RESULT_SUCCESS;
      // delete by os012 20111213 end
    }

    // オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.OVERFLOW);

    // ページ情報を追加
    bean.setPagerValue(PagerUtil.createValue(result));

    List<CustomerAddress> addressList = result.getRows();
    List<AddressListDetailBean> list = new ArrayList<AddressListDetailBean>();

    for (CustomerAddress addr : addressList) {
      AddressListDetailBean detail = new AddressListDetailBean();

      detail.setAddressNo(Long.toString(addr.getAddressNo()));
      detail.setAddressAlias(addr.getAddressAlias());
      detail.setAddressFirstName(addr.getAddressFirstName());
      detail.setAddressLastName(addr.getAddressLastName());
      detail.setPostalCode(addr.getPostalCode());
      detail.setAddress1(addr.getAddress1());
      detail.setAddress2(addr.getAddress2());
      detail.setAddress3(addr.getAddress3());
      detail.setAddress4(addr.getAddress4());

      // 配送履歴を取得
      DeliveryHistorySearchCondition searchCondition = new DeliveryHistorySearchCondition();
      searchCondition.setSearchCustomerCode(customerCode);
      searchCondition.setSearchAddressNo(Long.toString(addr.getAddressNo()));
      // 検索実行
      // SearchResult<DeliveryHistoryInfo> searchResult
      // =service.findDeliveryHistoryInfo(searchCondition);
      // リスト生成
      // List<DeliveryHistoryInfo> deliveryList = searchResult.getRows();
      // detail.setDeliveryDatetime("");
      // for (DeliveryHistoryInfo b : deliveryList) {
      // if (StringUtil.isNullOrEmpty(detail.getDeliveryDatetime()) &&
      // StringUtil.isNotNull(b.getShippingDate())) {
      // // 配送履歴の出荷日がNULLではない最初のレコードの出荷日を最終配送日とする。
      // detail.setDeliveryDatetime(DateUtil.toDateString(DateUtil.fromString(b.getShippingDate())));
      // }
      // }
      list.add(detail);
    }
    bean.setList(list);

    // 処理完了メッセージを設定する
    setCompleteMessage(completeParam);
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * ボタン表示設定
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    AddressListBean nextBean = (AddressListBean) getRequestBean();
    // 削除権限チェック
    if (Permission.CUSTOMER_DELETE.isGranted(login)) {
      setDeleteDisplayControl(nextBean, true);
    } else {
      setDeleteDisplayControl(nextBean, false);
    }
    // 更新権限チェック
    if (Permission.CUSTOMER_UPDATE.isGranted(login)) {
      nextBean.setInsertDisplayFlg(true);
      nextBean.setEditDisplayFlg(true);
    } else {
      nextBean.setInsertDisplayFlg(false);
      nextBean.setEditDisplayFlg(false);
    }
    // 編集ボタン表示設定
    setEditDisplayControl(nextBean);
  }

  /**
   * 画面上に表示される削除ボタンの表示/非表示を制御します。<br>
   * 「削除権限がある」、「本人でない」時にボタンを表示します。
   * 
   * @param nextBean
   * @param delete
   */
  private void setDeleteDisplayControl(AddressListBean nextBean, boolean delete) {
    for (AddressListDetailBean ad : nextBean.getList()) {
      if (delete) {
        // 10.1.3 10150 修正 ここから
        // if
        // (ad.getAddressNo().equals(Long.toString(CustomerConstant.SELFE_ADDRESS_NO)))
        // {
        if (ad.getAddressNo().equals(Long.toString(CustomerConstant.SELF_ADDRESS_NO))) {
          // 10.1.3 10150 修正 ここまで
          ad.setDeleteDisplayFlg(false);
        } else {
          ad.setDeleteDisplayFlg(true);
        }
      } else {
        ad.setDeleteDisplayFlg(false);
      }
    }
    setRequestBean(nextBean);
  }

  /**
   * 画面上に表示される編集ボタンの表示/非表示を制御します。<br>
   * 「本人でない」時にボタンを表示します。
   * 
   * @param nextBean
   */
  private void setEditDisplayControl(AddressListBean nextBean) {
    for (AddressListDetailBean ad : nextBean.getList()) {
      // 10.1.3 10150 修正 ここから
      // if
      // (ad.getAddressNo().equals(Long.toString(CustomerConstant.SELFE_ADDRESS_NO)))
      // {
      // 20120116 del by wjw start
      // if
      // (ad.getAddressNo().equals(Long.toString(CustomerConstant.SELF_ADDRESS_NO)))
      // {
      // // 10.1.3 10150 修正 ここまで
      // ad.setEditDisplayFlg(false);
      // } else {
      // ad.setEditDisplayFlg(true);
      // }
      ad.setEditDisplayFlg(true);
      // 20120116 del by wjw end
    }
    setRequestBean(nextBean);
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします。
   * 
   * @param completeParam
   */
  private void setCompleteMessage(String completeParam) {
    if (completeParam.equals("delete")) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
          .getString("web.action.back.customer.AddressListInitAction.0")));
    }
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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.AddressListInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103021002";
  }

}
