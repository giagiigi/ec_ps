package jp.co.sint.webshop.web.action.back.data;

import java.util.List;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.data.DataImportBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1080210:データ一括取込のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DataImportCompleteAction extends WebBackAction<DataImportBean> {

  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  private static final long serialVersionUID = 1L;

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean result = false;
    BackLoginInfo login = getLoginInfo();

    // データ入出力アクセス_サイトを持つユーザは常にアクセス可能
    if (Permission.DATA_IO_ACCESS_SITE.isGranted(login) || Permission.DATA_IO_ACCESS_SHOP.isGranted(login)) {
      result = true;
    }
    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);

    DataImportBean bean = (DataImportBean) getBean();

    List<UploadResult> resultList = messageBean.getUploadDetailList();

    if (messageBean.getResult().equals(ResultType.SUCCESS)) {
      addInformationMessage(WebMessage.get(CompleteMessage.CSV_IMPORT_COMPLETE));
    } else if (messageBean.getResult().equals(ResultType.FAILED)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_FAILED));
    } else {
      addWarningMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_PARTIAL, bean.getImportDataType()));
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

    bean.setImportDataTypeList(bean.getImportDataTypeList(getLoginInfo(), getConfig()));

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.data.DataImportCompleteAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "7108021001";
  }

}
