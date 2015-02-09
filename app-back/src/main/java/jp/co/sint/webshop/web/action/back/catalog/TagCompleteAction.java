package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.TagCount;
import jp.co.sint.webshop.service.catalog.TagSearchCondition;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TagBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040410:タグマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TagCompleteAction extends TagBaseAction {

  private static final String PICTURE_NAME = Messages.getString("web.action.back.catalog.TagCompleteAction.0");

  /**
   * 認証処理を実行します<BR>
   * 更新または削除権限があることをチェックします
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()) || Permission.COMMODITY_DELETE.isGranted(getLoginInfo());
  }

  /**
   * 指定されたショップコードを元に、ショップが持つタグの一覧を取得します<BR>
   * サイト管理者の場合は、画面で選択されたショップコードを元に一覧を取得します<BR>
   * ショップ管理者の場合は、ログイン情報のショップコードを元に一覧を取得します
   */
  @Override
  public WebActionResult callService() {
    TagBean reqBean = (TagBean) getBean();

    // 検索条件のクリア
    clearSearchCondition(reqBean);

    // ショップ管理者、または一店舗版の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 検索条件の設定
    TagSearchCondition condition = setSearchCondition(reqBean);

    // タグ一覧の取得
    List<TagCount> tagList = getTagList(reqBean, condition);

    // nextBeanの生成
    createInitNextBean(reqBean, tagList);

    // nextBeanのセット
    setRequestBean(reqBean);

    setNextUrl(null);

    return BackActionResult.RESULT_SUCCESS;
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
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    TagBean reqBean = (TagBean) getRequestBean();
    setEditDisplayControl(reqBean);
    setCompleteParameter();
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします <BR>
   * 処理完了パラメータがない場合は、何もメッセージを設定しません<BR>
   * 登録完了時：insert 更新完了時：update 削除完了時：delete <BR>
   */
  private void setCompleteParameter() {
    String[] param = getRequestParameter().getPathArgs();
    String completeParam = "";
    if (param.length == 1) {
      completeParam = param[0];
    }

    if (completeParam.equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, PICTURE_NAME));

    } else if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, PICTURE_NAME));

    } else if (completeParam.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, PICTURE_NAME));
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.TagCompleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104041001";
  }

}
