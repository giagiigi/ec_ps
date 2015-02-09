package jp.co.sint.webshop.web.action.back.data;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.data.FileUploadBean;
import jp.co.sint.webshop.web.bean.back.data.FileUploadBean.FileUploadDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1080310:ファイルアップロードのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class FileUploadSearchAction extends FileUploadBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    // データ入出力のアクセス権限があれば画面表示可能
    if (Permission.DATA_IO_ACCESS_SITE.isGranted(login) || Permission.DATA_IO_ACCESS_SHOP.isGranted(login)) {
      return true;
    }
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    FileUploadBean requestBean = new FileUploadBean();
    FileUploadBean bean = getBean();

    // コンテンツ分類、ファイル種別の設定
    requestBean.setContentsTypeList(getContentsTypeList(getLoginInfo()));
    requestBean.setContentsTypeCondition(bean.getContentsTypeCondition());

    // コンテンツが何も選択されていなければ検索結果をクリアする
    if (bean.getContentsTypeCondition().equals(FileUploadBean.DEFAULT_CONTENTS_TYPE_CONDITION)) {
      setRequestBean(requestBean);
      setNextUrl(null);
      return BackActionResult.RESULT_SUCCESS;
    }

    requestBean.setShopCode(getLoginInfo().getShopCode());

    ContentsSearchCondition condition = new ContentsSearchCondition();
    condition.setContentsType(ContentsType.fromValue(requestBean.getContentsTypeCondition()));
    condition.setShopCode(requestBean.getShopCode());

    // カテゴリトップコンテンツ選択時
    if (requestBean.getContentsTypeCondition().equals(ContentsType.CONTENT_SITE_CATEGORY.getCode())) {
      requestBean.setCategoryTopDisplay(true);
      requestBean.setCategoryTopList(getCategoryTopList());
      requestBean.setCategoryTopCondition(bean.getCategoryTopCondition());
      condition.setCategoryCode(bean.getCategoryTopCondition());
    }

    // キャンペーンコンテンツ選択時
    if (requestBean.getContentsTypeCondition().equals(ContentsType.CONTENT_SHOP_CAMPAIGN_SITE.getCode())
        || requestBean.getContentsTypeCondition().equals(ContentsType.CONTENT_SHOP_CAMPAIGN_SHOP.getCode())) {
      requestBean.setCampaignDisplay(true);
      requestBean.setCampaignList(getCampaignList(getLoginInfo().getShopCode()));
      requestBean.setCampaignCondition(bean.getCampaignCondition());
      condition.setCampaignCode(bean.getCampaignCondition());
    }

    List<FileUploadDetailBean> list = new ArrayList<FileUploadDetailBean>();

    requestBean.setSearchResultList(list);
    requestBean.setUploadAuthority(true);
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
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    // tempBeanにBeanの内容を退避する
    FileUploadBean requestBean = getBean();
    FileUploadBean tempBean = new FileUploadBean();

    tempBean.setContentsTypeList(requestBean.getContentsTypeList());
    tempBean.setContentsTypeCondition(requestBean.getContentsTypeCondition());

    tempBean.setCategoryTopDisplay(requestBean.getCategoryTopDisplay());
    tempBean.setCategoryTopList(requestBean.getCategoryTopList());
    tempBean.setCategoryTopCondition(requestBean.getCategoryTopCondition());

    tempBean.setCampaignDisplay(requestBean.getCampaignDisplay());
    tempBean.setCampaignList(requestBean.getCampaignList());
    tempBean.setCampaignCondition(requestBean.getCampaignCondition());

    tempBean.setSearchResultList(requestBean.getSearchResultList());
    tempBean.setUploadAuthority(requestBean.getUploadAuthority());

    getSessionContainer().setTempBean(tempBean);
    setBean(requestBean);

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.data.FileUploadSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "7108031003";
  }

}
