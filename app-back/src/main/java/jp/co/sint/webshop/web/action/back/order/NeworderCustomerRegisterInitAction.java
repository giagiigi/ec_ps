package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UIBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCustomerRegisterBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020190:新規受注（顧客登録）のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCustomerRegisterInitAction extends NeworderBaseAction<NeworderCustomerRegisterBean> {

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

    NeworderCustomerRegisterBean bean = null;

    UIBean tmpBean = getSessionContainer().getTempBean();
    if (tmpBean != null && tmpBean instanceof NeworderCustomerRegisterBean) {
      bean = (NeworderCustomerRegisterBean) tmpBean;
    } else {
      bean = new NeworderCustomerRegisterBean();
    }

    // 初期値を設定
    bean.setSex(Sex.MALE.getValue());
    // 10.1.4 K00171 修正 ここから
    // bean.setRequestMailType(RequestMailType.WANTED.getValue());
    bean.setRequestMailType(RequestMailType.UNWANTED.getValue());
    // 10.1.4 K00171 修正 ここまで
  //soukai delete 2012/1/2 ob start
    // modify by V10-CH 170 start 
    /*UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setCityList(s.getCityNames(bean.getPrefectureCode()));*/
  //soukai delete 2012/1/2 ob end
    // modify by V10-CH 170 end
    
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    NeworderCustomerRegisterBean bean = (NeworderCustomerRegisterBean) getRequestBean();
    bean.setDisplayNextButtonModeFlg(true);
    bean.setDisplayRegisterButtonModeFlg(false);
    bean.setDisplayBackButtonModeFlg(false);

    bean.setDisplayTextMode(WebConstantCode.DISPLAY_EDIT);

    setRequestBean(bean);
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderCustomerRegisterInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102019003";
  }

}
