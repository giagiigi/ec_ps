package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CustomerEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030120:顧客登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerEditInitAction extends CustomerEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 権限チェック(更新)
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_UPDATE.isGranted(login);
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

    CustomerEditBean bean = new CustomerEditBean();

    // 顧客グループリストの取得
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setCustomerGroupList(s.getCustomerGroupNames());

    bean.setCustomerCode("");
    //modify by yl 20111212 start
    //bean.setSex(Sex.MALE.getValue());
    bean.setSex(Sex.FEMALE.getValue());
    //modify by yl 20111212 end  
    bean.setReceivedWithdrawalNotice(false);
    // 10.1.4 K00171 修正 ここから
    // bean.setRequestMailType(RequestMailType.WANTED.getValue());
    //modify by yl 20111212 start
   // bean.setRequestMailType(RequestMailType.UNWANTED.getValue());
    bean.setRequestMailType(RequestMailType.WANTED.getValue());
    //modify by yl 20111212 end
    // 10.1.4 K00171 修正 ここまで 

    // modify by V10-CH 170 start
    bean.setCityList(s.getCityNames(bean.getPrefectureCode()));
    // modify by V10-CH 170 end
    //20120510 tuxinwei add start
    bean.setLanguageCode("");
    //20120510 tuxinwei add end
    // 顧客属性をセット
    setAttributeList(bean, false);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {
    CustomerEditBean nextBean = (CustomerEditBean) getRequestBean();

    nextBean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    nextBean.setNextButtonDisplayFlg(true);

    // 新規登録の場合は非表示設定
    nextBean.setWithdrawalDisplayFlg(false);
    nextBean.setPointDisplayFlg(false);
    nextBean.setOrderDisplayFlg(false);
    nextBean.setCouponDisplayFlg(false);

    // 初期表示時は常に登録/更新/戻るボタンを非表示にセット
    nextBean.setRegisterButtonDisplayFlg(false);
    nextBean.setUpdateButtonDisplayFlg(false);
    nextBean.setBackButtonDisplayFlg(false);
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
    return Messages.getString("web.action.back.customer.CustomerEditInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103012003";
  }

}
