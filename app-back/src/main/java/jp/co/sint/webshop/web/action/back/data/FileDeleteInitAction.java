package jp.co.sint.webshop.web.action.back.data;

import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.data.FileDeleteBean;
import jp.co.sint.webshop.web.bean.back.data.FileUploadBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1080410:ファイル削除のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class FileDeleteInitAction extends FileDeleteBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    // データ入出力のアクセス権限が必要
    boolean result = Permission.DATA_IO_ACCESS_SITE.isGranted(login) || Permission.DATA_IO_ACCESS_SHOP.isGranted(login);

    // 選択可能なコンテンツがない場合はfalseにする
    List<CodeAttribute> deletableList = getContentsTypeList(login);
    // 「選択してください」以外が必要
    result &= deletableList != null && deletableList.size() > 1;

    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    FileDeleteBean requestBean = new FileDeleteBean();
    FileDeleteBean bean = (FileDeleteBean) getBean();

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
      if (campaignList.size() == 0) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CAMPAIGN_ERROR));
      }

      requestBean.setCampaignDisplay(true);
      requestBean.setCampaignList(campaignList);
      requestBean.setCampaignCondition(bean.getCampaignCondition());
    }

    // 画像・テキスト選択時
    if (requestBean.getContentsTypeCondition().equals(ContentsType.IMAGE_DATA_SHOP_COMMODITY.getCode())
        || requestBean.getContentsTypeCondition().equals(ContentsType.IMAGE_DATA_SHOP_GIFT.getCode())
        || requestBean.getContentsTypeCondition().equals(ContentsType.CONTENT_SITE_MOBILE.getCode())
        || requestBean.getContentsTypeCondition().equals(ContentsType.CONTENT_SHOP_MOBILE.getCode())) {
      requestBean.setImageContents(true);
    }

    requestBean.setSearchButtonDisplay(true);
    setRequestBean(requestBean);
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
    return Messages.getString("web.action.back.data.FileDeleteInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "7108041002";
  }

}
