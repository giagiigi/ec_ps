package jp.co.sint.webshop.web.action.back.data;

import java.util.List;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.data.FileUploadBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1080310:ファイルアップロードのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class FileUploadCompleteAction extends FileUploadSearchAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);

    if (messageBean.getResult().equals(ResultType.SUCCESS)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPLOAD_COMPLETE,
          Messages.getString("web.action.back.data.FileUploadCompleteAction.0")));
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.UPLOAD_FAILED,
          Messages.getString("web.action.back.data.FileUploadCompleteAction.0")));
    }

    List<UploadResult> resultList = messageBean.getUploadDetailList();

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

    // TempBeanの内容でBeanを上書き
    FileUploadBean bean = getBean();
    FileUploadBean tempBean = (FileUploadBean) getSessionContainer().getTempBean();

    bean.setContentsTypeCondition(tempBean.getContentsTypeCondition());
    bean.setContentsTypeList(tempBean.getContentsTypeList());
    bean.setCategoryTopList(tempBean.getCategoryTopList());
    bean.setCategoryTopDisplay(tempBean.getCategoryTopDisplay());
    bean.setCampaignList(tempBean.getCampaignList());
    bean.setCampaignDisplay(tempBean.getCampaignDisplay());

    bean.setSearchResultList(tempBean.getSearchResultList());
    bean.setShopCode(tempBean.getShopCode());
    bean.setUploadAuthority(tempBean.getUploadAuthority());
    bean.setUploadFilePath(tempBean.getUploadFilePath());

    setBean(bean);
    return super.callService();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.data.FileUploadCompleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "7108031001";
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }

}
