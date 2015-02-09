package jp.co.sint.webshop.web.action.back.customer;

import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerGroupCount;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CustomerGroupBean;
import jp.co.sint.webshop.web.bean.back.customer.CustomerGroupBean.CustomerGroupBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030410:顧客グループマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupInitAction extends WebBackAction<CustomerGroupBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
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

    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());
    List<CustomerGroupCount> group = cs.getCustomerGroup();

    CustomerGroupBean bean = new CustomerGroupBean();

    // 一覧表示部
    List<CustomerGroupBeanDetail> list = bean.getList();
    for (CustomerGroupCount cg : group) {
      CustomerGroupBeanDetail detail = new CustomerGroupBeanDetail();

      detail.setCustomerGroupCode(cg.getCustomerGroupCode());
      detail.setCustomerGroupName(cg.getCustomerGroupName());
      detail.setMemberShip(Long.toString(cg.getMemberShip()));
      if (Long.parseLong(detail.getMemberShip()) > 0) {
        detail.setDeleteButtonDisplayFlg(WebConstantCode.VALUE_FALSE);
      } else {
        detail.setDeleteButtonDisplayFlg(WebConstantCode.VALUE_TRUE);
      }
      detail.setCustomerGroupPointRate(Long.toString(cg.getCustomerGroupPointRate()));
      detail.setUpdatedDateTime(DateUtil.toDateTimeString(cg.getUpdatedDatetime()));
      // 20120517 shen add start
      detail.setCustomerGroupNameEn(cg.getCustomerGroupNameEn());
      detail.setCustomerGroupNameJp(cg.getCustomerGroupNameJp());
      // 20120517 shen add end
      
      list.add(detail);
    }

    bean.setList(list);

    // 登録・更新部
    CustomerGroupBeanDetail edit = bean.getEdit();
    edit.setCustomerGroupCode("");
    edit.setCustomerGroupName("");
    edit.setCustomerGroupPointRate("");
    edit.setDisplayMode(WebConstantCode.DISPLAY_EDIT);

    bean.setEdit(edit);

    // 完了メッセージ設定
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length > 0) {
      if (urlParam[0].equals("register")) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
            Messages.getString("web.action.back.customer.CustomerGroupInitAction.0")));
      } else if (urlParam[0].equals("update")) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
            Messages.getString("web.action.back.customer.CustomerGroupInitAction.0")));
      } else if (urlParam[0].equals("delete")) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
            Messages.getString("web.action.back.customer.CustomerGroupInitAction.0")));
      }
    }

    this.setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    // 画面上に表示されるボタンの表示/非表示を制御します。
    // 選択ボタン/削除ボタン/更新ボタン/キャンセルボタン
    BackLoginInfo login = getLoginInfo();
    CustomerGroupBean bean = (CustomerGroupBean) getRequestBean();

    bean.setUpdateMode(Permission.CUSTOMER_UPDATE.isGranted(login));
    bean.setDeleteMode(Permission.CUSTOMER_DELETE.isGranted(login));

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerGroupInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103041002";
  }

}
