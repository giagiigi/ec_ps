package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.BrandSearchCondition;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.BrandListBean;
import jp.co.sint.webshop.web.bean.back.catalog.BrandListBean.BrandDetailBean;
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
public abstract class BrandListBaseAction extends WebBackAction<BrandListBean> {

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
   * @param BrandList
   */
  public void createInitNextBean(BrandListBean reqBean, List<Brand> BrandList) {
    List<BrandDetailBean> BrandDetails = new ArrayList<BrandDetailBean>();

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setShopList(utilService.getShopNames(true));

    for (Brand tt : BrandList) {
      BrandDetailBean BrandDetail = new BrandDetailBean();
      setResultBrandList(tt, BrandDetail);
      BrandDetails.add(BrandDetail);
    }
    reqBean.setList(BrandDetails);
    reqBean.getEdit().setShopCode(reqBean.getSearchShopCode());
    reqBean.getEdit().setBrandCode("");
    reqBean.getEdit().setBrandName("");
    reqBean.getEdit().setBrandDescription("");
    // 20120524 tuxinwei add start
    reqBean.getEdit().setBrandDescriptionEn("");
    reqBean.getEdit().setBrandDescriptionJp("");
    // 20120524 tuxinwei add end
    reqBean.getEdit().setBrandEnglishName("");
    // 20120515 tuxinwei add start
    reqBean.getEdit().setBrandJapaneseName("");
    // 20120515 tuxinwei add end
    reqBean.getEdit().setBrandNameAbbr("");
    reqBean.getEdit().setTmallBrandCode("");
    reqBean.getEdit().setTmallBrandName("");
    reqBean.getEdit().setUpdatedDatetime(null);
    reqBean.getEdit().setTitle("");
    reqBean.getEdit().setTitleEn("");
    reqBean.getEdit().setTitleJp("");
    reqBean.getEdit().setDescription("");
    reqBean.getEdit().setDescriptionEn("");
    reqBean.getEdit().setDescriptionJp("");
    reqBean.getEdit().setKeyword("");
    reqBean.getEdit().setKeywordEn("");
    reqBean.getEdit().setKeywordJp("");

  }

  /**
   * 検索結果を元に画面表示用のBeanを生成します<BR>
   * 入力項目欄には、画面で選択したタグコードに関連付いている情報をセットします
   * 
   * @param reqBean
   * @param BrandList
   * @param BrandCode
   */
  public void createSelectNextBean(BrandListBean reqBean, List<Brand> BrandList, String BrandCode) {
    // 画面表示用Beanを生成
    List<BrandDetailBean> BrandDetails = new ArrayList<BrandDetailBean>();

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setShopList(utilService.getShopNames(false));

    BrandDetailBean BrandDetailBean = new BrandDetailBean();
    reqBean.setEdit(BrandDetailBean);

    for (Brand tt : BrandList) {
      BrandDetailBean BrandDetail = new BrandDetailBean();
      setResultBrandList(tt, BrandDetail);
      BrandDetails.add(BrandDetail);

      if (tt.getBrandCode().equals(BrandCode)) {
        reqBean.getEdit().setShopCode(tt.getShopCode());
        reqBean.getEdit().setBrandCode(tt.getBrandCode());
        reqBean.getEdit().setBrandName(tt.getBrandName());
        reqBean.getEdit().setBrandDescription(tt.getBrandDescription());
        // 20120524 tuxinwei add start
        reqBean.getEdit().setBrandDescriptionEn(tt.getBrandDescriptionEn());
        reqBean.getEdit().setBrandDescriptionJp(tt.getBrandDescriptionJp());
        // 20120524 tuxinwei add end
        reqBean.getEdit().setBrandEnglishName(tt.getBrandEnglishName());
        // 20120515 tuxinwei add start
        reqBean.getEdit().setBrandJapaneseName(tt.getBrandJapaneseName());
        // 20120515 tuxinwei add end
        reqBean.getEdit().setBrandNameAbbr(tt.getBrandNameAbbr());
        reqBean.getEdit().setTmallBrandCode(tt.getTmallBrandCode());
        reqBean.getEdit().setTmallBrandName(tt.getTmallBrandName());
        reqBean.getEdit().setUpdatedDatetime(tt.getUpdatedDatetime());
        reqBean.getEdit().setKeywordCn2(tt.getKeywordCn2());
        reqBean.getEdit().setKeywordEn2(tt.getKeywordEn2());
        reqBean.getEdit().setKeywordJp2(tt.getKeywordJp2());
        if (NumUtil.isNull(tt.getBrandType())) {
          reqBean.getEdit().setBrandType(0L);
        } else {
          reqBean.getEdit().setBrandType(tt.getBrandType());
        }
        // 20130703 txw add start
        reqBean.getEdit().setTitle(tt.getTitle());
        reqBean.getEdit().setTitleEn(tt.getTitleEn());
        reqBean.getEdit().setTitleJp(tt.getTitleJp());
        reqBean.getEdit().setDescription(tt.getDescription());
        reqBean.getEdit().setDescriptionEn(tt.getDescriptionEn());
        reqBean.getEdit().setDescriptionJp(tt.getDescriptionJp());
        reqBean.getEdit().setKeyword(tt.getKeyword());
        reqBean.getEdit().setKeywordEn(tt.getKeywordEn());
        reqBean.getEdit().setKeywordJp(tt.getKeywordJp());
        // 20130703 txw add end
      }

    }
    reqBean.setList(BrandDetails);

    if (StringUtil.isNullOrEmpty(reqBean.getEdit().getBrandCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.BrandBaseAction.0")));
    }

  }

  /**
   * 検索結果を、検索結果リスト用Beanにセットします
   * 
   * @param tt
   * @param BrandDetail
   */
  private void setResultBrandList(Brand tt, BrandDetailBean BrandDetail) {
    BrandDetail.setShopCode(tt.getShopCode());
    BrandDetail.setBrandCode(tt.getBrandCode());
    BrandDetail.setBrandName(tt.getBrandName());
    BrandDetail.setBrandDescription(tt.getBrandDescription());
    // 20120524 tuxinwei add start
    BrandDetail.setBrandDescriptionEn(tt.getBrandDescriptionEn());
    BrandDetail.setBrandDescriptionJp(tt.getBrandDescriptionJp());
    // 20120524 tuxinwei add end
    BrandDetail.setBrandEnglishName(tt.getBrandEnglishName());
    // 20120515 tuxinwei add start
    BrandDetail.setBrandJapaneseName(tt.getBrandJapaneseName());
    // 20120515 tuxinwei add end
    BrandDetail.setBrandNameAbbr(tt.getBrandNameAbbr());
    BrandDetail.setUpdatedDatetime(tt.getUpdatedDatetime());
    BrandDetail.setTmallBrandCode(tt.getTmallBrandCode());
    BrandDetail.setTmallBrandName(tt.getTmallBrandName());
    setDeleteButtonDisplayControl(tt, BrandDetail);
  }

  /**
   * 検索条件を元にDB内を検索し、検索結果を取得します
   * 
   * @param reqBean
   * @param condition
   * @return BrandList
   */
  public List<Brand> getBrandList(BrandListBean reqBean, BrandSearchCondition condition) {
    // ショップが持つタグの一覧を取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<Brand> BrandResult = service.getBrandSearch(condition);

    if (BrandResult.isOverflow()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
          + BrandResult.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }

    List<Brand> BrandList = BrandResult.getRows();

    // ページ情報を追加
    reqBean.setPagerValue(PagerUtil.createValue(BrandResult));
    return BrandList;
  }

  /**
   * 画面から取得した検索条件をセットします
   * 
   * @param reqBean
   * @return condition
   */
  public BrandSearchCondition setSearchCondition(BrandListBean reqBean) {
    BrandSearchCondition condition = new BrandSearchCondition();
    condition.setShopCode(reqBean.getSearchShopCode());
    condition.setSearchBrandCode(reqBean.getSearchBrandCode());
    condition.setSearchBrandName(reqBean.getSearchBrandName());
    condition.setSearchBrandEnglishName(reqBean.getSearchBrandEnglishName());
    // 20120515 tuxinwei add start
    condition.setSearchBrandJapaneseName(reqBean.getSearchBrandJapaneseName());
    // 20120515 tuxinwei add end
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);
    return condition;
  }

  /**
   * 画面上に表示される項目の表示/非表示を制御します <BR>
   * サイト管理者は参照のみの権限により、ボタン及び入力項目の表示制御をします<BR>
   * ショップ管理者は登録・更新の権限に応じて、ボタン及び入力項目の表示制御をします<BR>
   * 参照モード時の表示制御を行います
   * 
   * @param BrandListBean
   */
  public void setEditDisplayControl(BrandListBean BrandListBean) {
    BrandListBean.setMode(WebConstantCode.DISPLAY_EDIT);
    // 更新処理に関する表示制御を実施
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      BrandListBean.setRegisterButtonDisplayFlg(true);
      BrandListBean.setUpdateButtonDisplayFlg(false);
      BrandListBean.setRegisterTableDisplayFlg(false);
      BrandListBean.setSearchResultTableDisplayFlg(true);
      BrandListBean.setSearchTableDisplayFlg(true);
    } else {
      BrandListBean.setRegisterButtonDisplayFlg(false);
      BrandListBean.setUpdateButtonDisplayFlg(false);
      BrandListBean.setRegisterTableDisplayFlg(false);
      BrandListBean.setSearchResultTableDisplayFlg(true);
      BrandListBean.setSearchTableDisplayFlg(true);

    }

    // ショップ検索の表示制御を実施
    if (getLoginInfo().isSite()) {
      BrandListBean.setSearchButtonDisplayFlg(true);
    } else {
      BrandListBean.setSearchButtonDisplayFlg(false);
    }
  }

  /**
   * 画面上に表示される項目の表示/非表示を制御します <BR>
   * サイト管理者は参照のみの権限により、ボタン及び入力項目の表示制御をします<BR>
   * ショップ管理者は登録・更新の権限に応じて、ボタン及び入力項目の表示制御をします<BR>
   * 更新モード時の表示制御を行います
   * 
   * @param BrandListBean
   */
  public void setHiddenDisplayControl(BrandListBean BrandListBean) {
    BrandListBean.setMode(WebConstantCode.DISPLAY_HIDDEN);
    // 更新処理に関する表示制御を実施
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      BrandListBean.setRegisterButtonDisplayFlg(false);
      BrandListBean.setUpdateButtonDisplayFlg(true);
      BrandListBean.setRegisterTableDisplayFlg(true);
    } else {
      BrandListBean.setRegisterButtonDisplayFlg(false);
      BrandListBean.setUpdateButtonDisplayFlg(false);
      BrandListBean.setRegisterTableDisplayFlg(false);
    }

    // ショップ検索の表示制御を実施
    if (getLoginInfo().isSite()) {
      BrandListBean.setSearchButtonDisplayFlg(true);
    } else {
      BrandListBean.setSearchButtonDisplayFlg(false);
    }
  }

  /**
   * 画面上に表示される削除ボタンの表示/非表示を制御します。<BR>
   * 「削除権限がある」「タグに商品が1つも関連付いていない」時にボタンを表示します。
   * 
   * @param tt
   * @param BrandDetail
   */
  public void setDeleteButtonDisplayControl(Brand tt, BrandDetailBean BrandDetail) {
    if (Permission.COMMODITY_DELETE.isGranted(getLoginInfo())) {
      BrandDetail.setDeleteButtonDisplayFlg(true);
    } else {
      BrandDetail.setDeleteButtonDisplayFlg(false);
    }
  }

  public void clearSearchCondition(BrandListBean reqBean) {
    reqBean.setSearchBrandCode("");
    reqBean.setSearchBrandName("");
    reqBean.setSearchBrandEnglishName("");
    // 20120515 tuxinwei add start
    reqBean.setSearchBrandJapaneseName("");
    // 20120515 tuxinwei add end
  }
}
