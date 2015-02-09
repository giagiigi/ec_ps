package jp.co.sint.webshop.web.action.back.data;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.data.ContentsListResult;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.data.FileDeleteBean;
import jp.co.sint.webshop.web.bean.back.data.FileDeleteBean.FileDeleteDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1080410:ファイル削除のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class FileDeleteSearchAction extends FileDeleteBaseAction {

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

    FileDeleteBean requestBean = new FileDeleteBean();
    FileDeleteBean bean = getBean();

    // コンテンツ分類、ファイル種別の設定
    requestBean.setContentsTypeList(getContentsTypeList(getLoginInfo()));
    requestBean.setContentsTypeCondition(bean.getContentsTypeCondition());

    // コンテンツが何も選択されていなければ検索結果をクリアする
    if (bean.getContentsTypeCondition().equals(FileDeleteBean.DEFAULT_CONTENTS_TYPE_CONDITION)) {
      setRequestBean(requestBean);
      setNextUrl(null);
      return BackActionResult.RESULT_SUCCESS;
    }

    requestBean.setShopCode(getLoginInfo().getShopCode());

    DataIOService service = ServiceLocator.getDataIOService(getLoginInfo());
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

    // 画像,テキストコンテンツ選択時
    if (requestBean.getContentsTypeCondition().equals(ContentsType.IMAGE_DATA_SHOP_COMMODITY.getCode())
        || requestBean.getContentsTypeCondition().equals(ContentsType.IMAGE_DATA_SHOP_GIFT.getCode())
        || requestBean.getContentsTypeCondition().equals(ContentsType.CONTENT_SITE_MOBILE.getCode())
        || requestBean.getContentsTypeCondition().equals(ContentsType.CONTENT_SHOP_MOBILE.getCode())) {
      requestBean.setImageContents(true);
    }

    // 画像,テキストコンテンツ選択時はページングを設定し、それ以外は設定しない
    List<ContentsListResult> result = null;
    if (requestBean.getImageContents()) {
      PagerUtil.createSearchCondition(getRequestParameter(), condition);
      SearchResult<ContentsListResult> searchResult = service.getPartialContentsList(condition);
      requestBean.setPagerValue(PagerUtil.createValue(searchResult));
      result = searchResult.getRows();
    } else {
      result = service.getContentsList(condition);
    }

    List<FileDeleteDetailBean> list = new ArrayList<FileDeleteDetailBean>();
    int checkBoxId = 0;
    for (ContentsListResult contents : result) {
      String treeText = "";
      if (!contents.getDepthLevel().equals(0L)) {
        for (int i = 1; i < contents.getDepthLevel(); i++) {
          treeText = treeText + "　　";
        }
        treeText = treeText + Messages.getString("web.action.back.data.FileDeleteSearchAction.0");
      }

      FileDeleteDetailBean detailBean = new FileDeleteDetailBean();
      detailBean.setUploadFilePath(contents.getDirectoryPath());
      detailBean.setUploadFileName(contents.getFileName());
      detailBean.setUploadFileSize(contents.getSize());
      detailBean.setUpdatedDateTime(contents.getUpdateDateTime());
      detailBean.setDirectory(contents.isDirectory());
      detailBean.setTreeText(treeText);
      detailBean.setCheckBoxId(String.valueOf(checkBoxId));
      list.add(detailBean);
      checkBoxId++;
    }
    requestBean.setSearchResultList(list);
    requestBean.setSearchButtonDisplay(true);
    requestBean.setSearchResultDisplay(true);
    requestBean.setUploadAuthority(true);
    if (list.size() > 0) {
      requestBean.setDeleteAuthority(true);
    }
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
    return Messages.getString("web.action.back.data.FileDeleteSearchAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "7108041003";
  }

}
