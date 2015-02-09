package jp.co.sint.webshop.web.action.back.customer;

import java.util.List;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CustomerEditBean;
import jp.co.sint.webshop.web.bean.back.customer.CustomerEditBean.CustomerAttributeListAnswerBean;
import jp.co.sint.webshop.web.bean.back.customer.CustomerEditBean.CustomerAttributeListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030120:顧客登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerEditConfirmAction extends CustomerEditBaseAction {

  // バリデーション結果
  private boolean successValidate = true;

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 更新権限チェック
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
    successValidate = super.validate();

    // add by V10-CH 170 start
    CustomerEditBean bean = getBean();
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setCityList(s.getCityNames(bean.getPrefectureCode()));
    // add by V10-CH 170 end
      bean.setMobileNumberSucces(bean.getMobileNumber());
    return successValidate;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CustomerEditBean bean = getBean();

    // 顧客属性入力チェック(システムエラー)
    boolean answerResult = true;
    List<CustomerAttributeListBean> edit2AttributeList = bean.getAttributeList();
    for (CustomerAttributeListBean ca : edit2AttributeList) {
      if (validateBean(ca)) {
        for (CustomerAttributeListAnswerBean aa : ca.getAttributeAnswerList()) {
          if (validateBean(aa)) {
            continue;
          } else {
            answerResult = false;
          }
        }
      } else {
        answerResult = false;
      }
    }
    if (!answerResult) {
      return BackActionResult.SERVICE_VALIDATION_ERROR;
    }

    // 都道府県名を取得

    // modify by V10-CH 170 start
    //bean.setAddress1(PrefectureCode.fromValue(bean.getPrefectureCode()).getName());
//    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
//    bean.setAddress1(PrefectureCode.fromValue(bean.getPrefectureCode()).getName());
//    bean.setAddress2(s.getCityName(bean.getPrefectureCode(), bean.getCityCode()));
//    bean.setPrefectureCode(bean.getPrefectureCode());
    // modify by V10-CH 170 end
    
    setRequestBean(bean);

    addWarningMessage(WebMessage.get(InformationMessage.REGISTER_CONFIRM));
    
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {
    if (!successValidate) {
      return;
    }
    CustomerEditBean nextBean = (CustomerEditBean) getRequestBean();
    // 入力モードを読み取り専用に設定
    nextBean.setEditMode(WebConstantCode.DISPLAY_HIDDEN);
    // 退会ボタンを非表示に設定
    nextBean.setWithdrawalDisplayFlg(false);
    // 次へボタンを非表示に設定
    nextBean.setNextButtonDisplayFlg(false);
    // 登録/更新ボタンを設定
    if (StringUtil.hasValue(nextBean.getCustomerCode())) {
      nextBean.setRegisterButtonDisplayFlg(false);
      nextBean.setUpdateButtonDisplayFlg(true);
    } else {
      nextBean.setRegisterButtonDisplayFlg(true);
      nextBean.setUpdateButtonDisplayFlg(false);
    }
    nextBean.setBackButtonDisplayFlg(true);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerEditConfirmAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103012002";
  }

}
