package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CommunicationServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.CustomerMessageBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1061210:限时限量折扣一览画面删除处理
 * 
 * @author KS.
 */
public class CustomerMessageDeleteAction extends CustomerMessageSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  private boolean messageFlag;
  
  @Override
  public boolean authorize() {
    return Permission.MESSAGE_DELETE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    CustomerMessageBean nextBean = getBean();

    // 選択されたレビューIDを取得する
    String[] values = getRequestParameter().getAll("checkBox");
    ArrayList<Long> intValuesList = new ArrayList<Long>();
    for(int i = 0; i < values.length; i++) {
      intValuesList.add(new Long(values[i]));
    }
    
    // チェックボックスが選択されているか
    if (StringUtil.isNullOrEmpty(values[0])) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED_NEW));
      setNextUrl(null);
      this.setRequestBean(nextBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    List<String> successList = new ArrayList<String>();
    List<String> failureList = new ArrayList<String>();

    // 削除処理実行
    for (long ormRowid : intValuesList) {
      ServiceResult serviceResult = service.deleteMessage(ormRowid);
      String s = Long.toString(ormRowid);
      // 错误处理
      if (serviceResult.hasError()) {
        for (ServiceErrorContent error : serviceResult.getServiceErrorList()) {
          if (CommonServiceErrorContent.NO_DATA_ERROR.equals(error)) {
            failureList.add(WebMessage.get(CommunicationErrorMessage.MESSAGE_NOT_EXIST_ERROR, s));
          }

          if (error.equals(CommunicationServiceErrorContent.DELETE_DISCOUNT_ERROR)) {
            failureList.add(WebMessage.get(CommunicationErrorMessage.MESSAGE_DELETE_ERROR, s));
          }
        }
      } else {
        if(!messageFlag) {
        // when there is not delete notice message in the JSP page  
        successList.add(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "所选留言"));
        messageFlag = true;
        }
      }
    }

    for (String s : failureList) {
      addErrorMessage(s);
    }

    for (String s : successList) {
      addInformationMessage(s);
    }

    this.setRequestBean(nextBean);

    // 削除後の結果を再検索する
    return super.callService();
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
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.CustomerMessageDeleteAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106121005";
  }
}
