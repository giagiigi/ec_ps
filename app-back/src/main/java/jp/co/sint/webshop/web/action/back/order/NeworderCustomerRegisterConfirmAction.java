package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderCustomerRegisterBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020190:新規受注（顧客登録）のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCustomerRegisterConfirmAction extends NeworderBaseAction<NeworderCustomerRegisterBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
	  //soukai delete 2012/1/2 ob start
    // add by V10-CH 170 start
	  /*UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    getBean().setCityList(s.getCityNames(getBean().getPrefectureCode()));*/
  //soukai delete 2012/1/2 ob end
    // add by V10-CH 170 end
    
    return customerValidate();
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    NeworderCustomerRegisterBean bean = getBean();

    bean.setDisplayNextButtonModeFlg(false);
    bean.setDisplayRegisterButtonModeFlg(true);
    bean.setDisplayBackButtonModeFlg(true);

    bean.setDisplayTextMode(WebConstantCode.DISPLAY_HIDDEN);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderCustomerRegisterConfirmAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102019002";
  }

}
