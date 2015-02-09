package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;

import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 免邮促销一览删除表示处理
 * 
 * @author Kousen.
 */
public class FreePostageListDeleteAction extends FreePostageListSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.FREE_POSTAGE_DELETE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // チェックボックスが選択されているか
    if (StringUtil.isNullOrEmpty(getRequestParameter().getAll("checkBox")[0])) {
      getDisplayMessage().addError(
          WebMessage.get(ActionErrorMessage.NO_CHECKED, Messages
              .getString("web.action.back.communication.FreePostageListDeleteAction.1")));
      return false;
    }
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
   * 开始执行动作
   * 
   * @return 返回结果
   */
  @Override
  public WebActionResult callService() {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    boolean success = false;
    FreePostageListBean nextBean = getBean();

    // 選択されたレビューIDを取得する
    String[] values = getRequestParameter().getAll("checkBox");

    // 削除処理実行
    for (String freePostageNo : values) {

      FreePostageRule freePostageRule = service.getFreePostageRule(freePostageNo);
      if (freePostageRule == null) {
        continue;
      }

      // 实施中的不可删除
      if (DateUtil.isPeriodDate(freePostageRule.getFreeStartDate(), freePostageRule.getFreeEndDate())) {
        addErrorMessage(MessageFormat.format(Messages.getString("web.action.back.communication.FreePostageListDeleteAction.2"),
            freePostageNo));
        continue;
      }

      ServiceResult serviceResult = service.deleteFreePostage(freePostageNo);
      if (serviceResult.hasError()) {
        addErrorMessage(MessageFormat.format(Messages.getString("web.action.back.communication.FreePostageListDeleteAction.3"),
            freePostageNo));
      }

      success = true;
    }
    if (success) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
          .getString("web.action.back.communication.FreePostageListDeleteAction.4")));
    }

    setRequestBean(nextBean);

    // 削除後の結果を再検索する
    return super.callService();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.FreePostageListDeleteAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106131003";
  }
}
