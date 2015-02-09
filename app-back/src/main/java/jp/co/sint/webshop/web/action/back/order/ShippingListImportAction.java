package jp.co.sint.webshop.web.action.back.order;

import java.util.List;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.order.ShippingListBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020410:出荷管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingListImportAction extends ShippingListBaseAction {

  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth;
    BackLoginInfo login = getLoginInfo();
    // 10.1.7 10306 修正 ここから
    // if (Permission.SHIPPING_UPDATE_SITE.isGranted(login)) {
    //   auth = true;
    // } else if (Permission.SHIPPING_UPDATE_SHOP.isGranted(login)) {
    //   auth = true;
    // } else {
    //   auth = false;
    // }
    auth = Permission.SHIPPING_DATA_IO_SITE.isGranted(login) || Permission.SHIPPING_DATA_IO_SHOP.isGranted(login);
    // 10.1.7 10306 修正 ここまで
    return auth;
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
    UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);

    ShippingListBean bean = getBean();

    List<UploadResult> resultList = messageBean.getUploadDetailList();

    if (messageBean.getResult().equals(ResultType.SUCCESS)) {
      addInformationMessage(WebMessage.get(CompleteMessage.CSV_IMPORT_COMPLETE));
    } else if (messageBean.getResult().equals(ResultType.FAILED)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_FAILED));
    } else {
      addWarningMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_PARTIAL));
    }

    for (UploadResult ur : resultList) {

      for (String s : ur.getInformationMessage()) {
        addInformationMessage(s);
      }
      for (String s : ur.getWarningMessage()) {
        addWarningMessage(s);
      }
      for (String s : ur.getErrorMessage()) {
        addErrorMessage(s);
      }

    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingListImportAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102041004";
  }

}
