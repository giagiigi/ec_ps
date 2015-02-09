package jp.co.sint.webshop.web.action.back.data;

import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.data.FileUploadBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1080310:ファイルアップロードのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class FileUploadInitAction extends FileUploadBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    // データ入出力のアクセス権限があれば画面表示可能
    boolean result = Permission.DATA_IO_ACCESS_SITE.isGranted(login) || Permission.DATA_IO_ACCESS_SHOP.isGranted(login);

    // アップロード可能なコンテンツが1つもない場合はfalseにする
    List<CodeAttribute> uploadableList = getContentsTypeList(login);
    // 「選択してください」以外があること
    result &= uploadableList != null && uploadableList.size() > 1;

    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    FileUploadBean requestBean = new FileUploadBean();
    FileUploadBean bean = (FileUploadBean) getBean();

    // コンテンツ分類、ファイル種別の設定
    requestBean.setContentsTypeList(getContentsTypeList(getLoginInfo()));
    requestBean.setContentsTypeCondition(bean.getContentsTypeCondition());
    requestBean.setShopCode(getLoginInfo().getShopCode());

    // 選択肢が無い場合は、エラーメッセージを表示する。
    if (requestBean.getContentsTypeList().size() == 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_IO_SUBJECT));
    }

    // コンテンツが何も選択されていなければ検索結果をクリアする
    if (bean.getContentsTypeCondition().equals(FileUploadBean.DEFAULT_CONTENTS_TYPE_CONDITION)) {
      setRequestBean(requestBean);
      setNextUrl(null);
      return BackActionResult.RESULT_SUCCESS;
    }

    // カテゴリトップコンテンツ選択時
    if (requestBean.getContentsTypeCondition().equals(ContentsType.CONTENT_SITE_CATEGORY.getCode())) {
      requestBean.setCategoryTopDisplay(true);
      requestBean.setCategoryTopList(getCategoryTopList());
      requestBean.setCategoryTopCondition(bean.getCategoryTopCondition());
    }

    // キャンペーンコンテンツ選択時
    if (requestBean.getContentsTypeCondition().equals(ContentsType.CONTENT_SHOP_CAMPAIGN_SITE.getCode())
        || requestBean.getContentsTypeCondition().equals(ContentsType.CONTENT_SHOP_CAMPAIGN_SHOP.getCode())) {

      List<CodeAttribute> campaignList = getCampaignList(getLoginInfo().getShopCode());
      // キャンペーンが存在しない場合、エラーメッセージを表示する
      if (campaignList.size() == 0) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CAMPAIGN_ERROR));
      }

      requestBean.setCampaignDisplay(true);
      requestBean.setCampaignList(campaignList);
      requestBean.setCampaignCondition(bean.getCampaignCondition());
    }

    setRequestBean(requestBean);

    getSessionContainer().setTempBean(bean);
    setNextUrl(null);

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
    return Messages.getString("web.action.back.data.FileUploadInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "7108031002";
  }

}
