package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.CodeUtil;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.GiftCount;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean.GiftDetailBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.file.UploadContents;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040310:ギフトのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GiftInitAction extends GiftBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
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

    GiftBean reqBean = new GiftBean();

    reqBean.setSearchShopCode(getLoginInfo().getShopCode());

    // ショップリストの取得
    if (getLoginInfo().isSite()) {
      // サイト管理者の場合のみ、ショップリストを取得
      UtilService service = ServiceLocator.getUtilService(getLoginInfo());
      reqBean.setShopList(service.getShopNames(true));
    }

    // ショップが持つギフト一覧を取得
    List<GiftCount> giftList = getGiftList(reqBean);

    // 画面表示用Beanを生成
    List<GiftDetailBean> detailList = new ArrayList<GiftDetailBean>();
    List<CodeAttribute> giftImageList = new ArrayList<CodeAttribute>();
    if (giftList.size() > 0) {
      giftImageList.add(new NameValue(Messages.getString(
          "web.action.back.catalog.GiftInitAction.0"), ""));
    }
    for (GiftCount gift : giftList) {
      GiftDetailBean detail = new GiftDetailBean();
      setResultGiftList(gift, detail);
      detailList.add(detail);
      giftImageList.add(new NameValue(gift.getGiftName(), gift.getGiftCode() + "/" + UploadContents.GIFT_IMAGE));
    }
    reqBean.setList(detailList);
    reqBean.setGiftImageList(giftImageList);

    GiftDetailBean edit = new GiftDetailBean();
    edit.setShopCode(reqBean.getSearchShopCode());
    edit.setDisplayFlg(DisplayFlg.HIDDEN.getValue());
    edit.setGiftTaxType(CodeUtil.getDefaultValue(TaxType.class).getValue());
    reqBean.setEdit(edit);

    // 画面表示用Beanを次画面Beanに設定
    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    // 完了メッセージを取得する
    // URL形式:/app/gift/init/【完了メッセージ】
    String[] params = getRequestParameter().getPathArgs();
    String complete = "";
    if (params.length > 0) {
      complete = params[0];
    }

    // 完了メッセージを設定する
    setCompleteMessage(complete);

    // 画面項目の表示/表示を設定する
    GiftBean bean = (GiftBean) getRequestBean();
    bean.setMode(MODE_NEW);
    setDisplayControl(bean);

    // 次画面のBeanを設定する
    setRequestBean(bean);
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします <BR>
   * 処理完了パラメータがない場合は、何もメッセージを設定しません<BR>
   * 登録完了時：insert 更新完了時：update 削除完了時：delete <BR>
   * 
   * @param complete
   *          処理完了パラメータ
   */
  private void setCompleteMessage(String complete) {

    if (WebConstantCode.COMPLETE_INSERT.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.catalog.GiftInitAction.1")));
    } else if (WebConstantCode.COMPLETE_UPDATE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
          Messages.getString("web.action.back.catalog.GiftInitAction.1")));
    } else if (WebConstantCode.COMPLETE_DELETE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.catalog.GiftInitAction.1")));
    } else if (WebConstantCode.COMPLETE_UPLOAD.equals(complete)) {
      UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);
      if (messageBean != null) {
        if (messageBean.getResult().equals(ResultType.SUCCESS)) {
          addInformationMessage(WebMessage.get(CompleteMessage.UPLOAD_COMPLETE,
              Messages.getString("web.action.back.catalog.GiftInitAction.2")));
        } else {
          addErrorMessage(WebMessage.get(ActionErrorMessage.UPLOAD_FAILED,
              Messages.getString("web.action.back.catalog.GiftInitAction.2")));
        }

        for (UploadResult ur : messageBean.getUploadDetailList()) {
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
      }
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.GiftInitAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104031002";
  }

}
