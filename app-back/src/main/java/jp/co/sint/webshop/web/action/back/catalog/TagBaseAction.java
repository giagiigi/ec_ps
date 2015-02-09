package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.TagCount;
import jp.co.sint.webshop.service.catalog.TagSearchCondition;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.TagBean;
import jp.co.sint.webshop.web.bean.back.catalog.TagBean.TagDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040410:タグマスタのアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class TagBaseAction extends WebBackAction<TagBean> {

  /**
   * アクションを実行します。
   */
  @Override
  public abstract WebActionResult callService();

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
   * @param reqBean
   * @param tagList
   */
  public void createInitNextBean(TagBean reqBean, List<TagCount> tagList) {
    List<TagDetailBean> tagDetails = new ArrayList<TagDetailBean>();

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setShopList(utilService.getShopNames(true));

    for (TagCount tt : tagList) {
      TagDetailBean tagDetail = new TagDetailBean();
      setResultTagList(tt, tagDetail);
      tagDetails.add(tagDetail);
    }
    reqBean.setList(tagDetails);
    reqBean.getEdit().setShopCode(reqBean.getSearchShopCode());
    reqBean.getEdit().setTagCode("");
    reqBean.getEdit().setTagName("");
    //20120514 tuxinwei add start
    reqBean.getEdit().setTagNameEn("");
    reqBean.getEdit().setTagNameJp("");
    //20120514 tuxinwei add end
    reqBean.getEdit().setDisplayOrder("");
    reqBean.getEdit().setUpdatedDatetime(null);

  }

  /**
   * 検索結果を元に画面表示用のBeanを生成します<BR>
   * 入力項目欄には、画面で選択したタグコードに関連付いている情報をセットします
   * 
   * @param reqBean
   * @param tagList
   * @param tagCode
   */
  public void createSelectNextBean(TagBean reqBean, List<TagCount> tagList, String tagCode) {
    // 画面表示用Beanを生成
    List<TagDetailBean> tagDetails = new ArrayList<TagDetailBean>();

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setShopList(utilService.getShopNames(false));

    TagDetailBean tagDetailBean = new TagDetailBean();
    reqBean.setEdit(tagDetailBean);

    for (TagCount tt : tagList) {
      TagDetailBean tagDetail = new TagDetailBean();
      setResultTagList(tt, tagDetail);
      tagDetails.add(tagDetail);

      if (tt.getTagCode().equals(tagCode)) {
        reqBean.getEdit().setShopCode(tt.getShopCode());
        reqBean.getEdit().setTagCode(tt.getTagCode());
        reqBean.getEdit().setTagName(tt.getTagName());
        //20120514 tuxinwei add start
        reqBean.getEdit().setTagNameEn(tt.getTagNameEn());
        reqBean.getEdit().setTagNameJp(tt.getTagNameJp());
        //20120514 tuxinwei add end
        reqBean.getEdit().setDisplayOrder(NumUtil.toString(tt.getDisplayOrder()));
        reqBean.getEdit().setUpdatedDatetime(tt.getUpdatedDatetime());
      }

    }
    reqBean.setList(tagDetails);

    if (StringUtil.isNullOrEmpty(reqBean.getEdit().getTagCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.TagBaseAction.0")));
    }

  }

  /**
   * 検索結果を、検索結果リスト用Beanにセットします
   * 
   * @param tt
   * @param tagDetail
   */
  private void setResultTagList(TagCount tt, TagDetailBean tagDetail) {
    tagDetail.setShopCode(tt.getShopCode());
    tagDetail.setTagCode(tt.getTagCode());
    tagDetail.setTagName(tt.getTagName());
    //20120514 tuxinwei add start
    tagDetail.setTagNameEn(tt.getTagNameEn());
    tagDetail.setTagNameJp(tt.getTagNameJp());
    //20120514 tuxinwei add end
    tagDetail.setDisplayOrder(NumUtil.toString(tt.getDisplayOrder()));
    tagDetail.setUpdatedDatetime(tt.getUpdatedDatetime());
    setDeleteButtonDisplayControl(tt, tagDetail);
  }

  /**
   * 検索条件を元にDB内を検索し、検索結果を取得します
   * 
   * @param reqBean
   * @param condition
   * @return tagList
   */
  public List<TagCount> getTagList(TagBean reqBean, TagSearchCondition condition) {
    // ショップが持つタグの一覧を取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<TagCount> tagResult = service.getTagSearch(condition);

    if (tagResult.isOverflow()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil
          .formatNumber("" + tagResult.getRowCount()), ""
          + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }

    List<TagCount> tagList = tagResult.getRows();

    // ページ情報を追加
    reqBean.setPagerValue(PagerUtil.createValue(tagResult));
    return tagList;
  }

  /**
   * 画面から取得した検索条件をセットします
   * 
   * @param reqBean
   * @return condition
   */
  public TagSearchCondition setSearchCondition(TagBean reqBean) {
    TagSearchCondition condition = new TagSearchCondition();
    condition.setShopCode(reqBean.getSearchShopCode());
    condition.setSearchTagCodeStart(reqBean.getSearchTagCodeStart());
    condition.setSearchTagCodeEnd(reqBean.getSearchTagCodeEnd());
    condition.setSearchTagName(reqBean.getSearchTagName());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);
    return condition;
  }

  /**
   * 画面上に表示される項目の表示/非表示を制御します <BR>
   * サイト管理者は参照のみの権限により、ボタン及び入力項目の表示制御をします<BR>
   * ショップ管理者は登録・更新の権限に応じて、ボタン及び入力項目の表示制御をします<BR>
   * 参照モード時の表示制御を行います
   * 
   * @param tagBean
   */
  public void setEditDisplayControl(TagBean tagBean) {
    tagBean.setMode(WebConstantCode.DISPLAY_EDIT);
    // 更新処理に関する表示制御を実施
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      tagBean.setRegisterButtonDisplayFlg(true);
      tagBean.setUpdateButtonDisplayFlg(false);
      tagBean.setRegisterTableDisplayFlg(true);
    } else {
      tagBean.setRegisterButtonDisplayFlg(false);
      tagBean.setUpdateButtonDisplayFlg(false);
      tagBean.setRegisterTableDisplayFlg(false);
    }

    // ショップ検索の表示制御を実施
    if (getLoginInfo().isSite()) {
      tagBean.setSearchButtonDisplayFlg(true);
    } else {
      tagBean.setSearchButtonDisplayFlg(false);
    }
  }

  /**
   * 画面上に表示される項目の表示/非表示を制御します <BR>
   * サイト管理者は参照のみの権限により、ボタン及び入力項目の表示制御をします<BR>
   * ショップ管理者は登録・更新の権限に応じて、ボタン及び入力項目の表示制御をします<BR>
   * 更新モード時の表示制御を行います
   * 
   * @param tagBean
   */
  public void setHiddenDisplayControl(TagBean tagBean) {
    tagBean.setMode(WebConstantCode.DISPLAY_HIDDEN);
    // 更新処理に関する表示制御を実施
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      tagBean.setRegisterButtonDisplayFlg(false);
      tagBean.setUpdateButtonDisplayFlg(true);
      tagBean.setRegisterTableDisplayFlg(true);
    } else {
      tagBean.setRegisterButtonDisplayFlg(false);
      tagBean.setUpdateButtonDisplayFlg(false);
      tagBean.setRegisterTableDisplayFlg(false);
    }

    // ショップ検索の表示制御を実施
    if (getLoginInfo().isSite()) {
      tagBean.setSearchButtonDisplayFlg(true);
    } else {
      tagBean.setSearchButtonDisplayFlg(false);
    }
  }

  /**
   * 画面上に表示される削除ボタンの表示/非表示を制御します。<BR>
   * 「削除権限がある」「タグに商品が1つも関連付いていない」時にボタンを表示します。
   * 
   * @param tt
   * @param tagDetail
   */
  public void setDeleteButtonDisplayControl(TagCount tt, TagDetailBean tagDetail) {
    if (tt.getRelatedCount() == null && Permission.COMMODITY_DELETE.isGranted(getLoginInfo())) {
      tagDetail.setDeleteButtonDisplayFlg(true);
    } else {
      tagDetail.setDeleteButtonDisplayFlg(false);
    }
  }

  public void clearSearchCondition(TagBean reqBean) {
    reqBean.setSearchTagCodeStart("");
    reqBean.setSearchTagCodeEnd("");
    reqBean.setSearchTagName("");
  }
}
