package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.communication.CampaignHeadLine;
import jp.co.sint.webshop.service.communication.CampaignListSearchCondition;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.CampaignListBean;
import jp.co.sint.webshop.web.bean.back.communication.CampaignListBean.CampaignListBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CampaignListSearchAction extends WebBackAction<CampaignListBean> {

  private CampaignListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new CampaignListSearchCondition();
  }

  protected CampaignListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected CampaignListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.CAMPAIGN_READ_SITE.isGranted(login) || Permission.CAMPAIGN_READ_SHOP.isGranted(login);
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    // 画面遷移時はボタン表示制御を行わない
    if (StringUtil.isNullOrEmpty(getNextUrl())) {

      BackLoginInfo login = getLoginInfo();

      CampaignListBean b = (CampaignListBean) getRequestBean();

      // ボタンの表示制御
      // ショップ管理者は削除権限があるとき、サイト管理者は削除権限があり、かつ一店舗版のときのみ削除可能
      boolean hasDeleteAuthority = Permission.CAMPAIGN_DELETE_SHOP.isGranted(login)
          || (Permission.CAMPAIGN_DELETE_SITE.isGranted(login) && getConfig().isOne());

      b.setDeleteAuthorizeFlg(hasDeleteAuthority);

      // ショップ管理者は更新権限があるとき、サイト管理者は更新権限があり、かつ一店舗版のときのみ
      // 更新・新規登録・関連付け可能
      boolean hasUpdateAuthority = Permission.CAMPAIGN_UPDATE_SHOP.isGranted(login)
          || (Permission.CAMPAIGN_UPDATE_SITE.isGranted(login) && getConfig().isOne());

      b.setUpdateAuthorizeFlg(hasUpdateAuthority);
      b.setRegisterNewDisplayFlg(hasUpdateAuthority);

      // ショップ名ドロップダウン作成
      b.getShopNames().clear();
      if (Permission.CAMPAIGN_READ_SITE.isGranted(getLoginInfo())) {
        UtilService service = ServiceLocator.getUtilService(getLoginInfo());
        b.setShopNames(service.getShopNamesDefaultAllShop(false, false));
      } else if (Permission.CAMPAIGN_READ_SHOP.isGranted(getLoginInfo())) {
        List<CodeAttribute> shopNames = new ArrayList<CodeAttribute>();
        shopNames.add(new NameValue(getLoginInfo().getShopName(), getLoginInfo().getShopCode()));
        b.setShopNames(shopNames);
      }

    }
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CampaignListBean bean = getBean();

    condition = getCondition();
    if (getLoginInfo().isSite() && !getConfig().isOne()) {
      condition.setShopCode(bean.getSearchShopCode());
    } else if (getLoginInfo().isShop() || getConfig().isOne()) {
      condition.setShopCode(getLoginInfo().getShopCode());
    }
    condition.setCampaignCode(bean.getSearchCampaignCode());
    condition.setCampaignName(bean.getSearchCampaignName());
    condition.setCampaignStartDateFrom(bean.getSearchCampaignStartDateFrom());
    condition.setCampaignStartDateTo(bean.getSearchCampaignStartDateTo());
    condition.setCampaignEndDateFrom(bean.getSearchCampaignEndDateFrom());
    condition.setCampaignEndDateTo(bean.getSearchCampaignEndDateTo());

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    SearchResult<CampaignHeadLine> result = service.getCampaignList(condition);

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    bean.setPagerValue(PagerUtil.createValue(result));

    bean.getCampaignList().clear();
    for (CampaignHeadLine row : result.getRows()) {
      CampaignListBeanDetail detail = new CampaignListBeanDetail();

      detail.setShopCode(row.getShopCode());
      detail.setShopName(row.getShopName());
      detail.setCampaignCode(row.getCampaignCode());
      detail.setCampaignName(row.getCampaignName());
      detail.setCampaignNameEn(row.getCampaignNameEn());
      detail.setCampaignNameJp(row.getCampaignNameJp());
      detail.setCampaignStartDate(row.getCampaignStartDate());
      detail.setCampaignEndDate(row.getCampaignEndDate());
      detail.setCampaignDiscountRate(row.getCampaignDiscountRate());
      detail.setPreviewButtonFlg(getPreviewButtonFlg(row.getShopCode(), row.getCampaignCode()));
      detail.setAnalysisButtonFlg(getAnalysisButtonFlg(row.getCampaignStartDate()));
      bean.getCampaignList().add(detail);
    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    ValidationSummary summary = BeanValidator.validate(getBean());
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
      return false;
    }

    // 日付の大小関係チェック
    condition.setCampaignStartDateFrom(getBean().getSearchCampaignStartDateFrom());
    condition.setCampaignStartDateTo(getBean().getSearchCampaignStartDateTo());
    condition.setCampaignEndDateFrom(getBean().getSearchCampaignEndDateFrom());
    condition.setCampaignEndDateTo(getBean().getSearchCampaignEndDateTo());
    if (condition.isValid()) {
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
    return false;
  }

  /**
   * プレビューボタンの表示制御を行います<BR>
   * キャンペーンのコンテンツが存在する場合は表示、存在しない場合は非表示とします
   * 
   * @param shopCode
   * @param campaignCode
   * @return contents.contentsExists(condition)
   */
  protected boolean getPreviewButtonFlg(String shopCode, String campaignCode) {
    // キャンペーンコンテンツ(index.html)のパスを取得
    ContentsSearchCondition contentsCondition = new ContentsSearchCondition();
    DataIOService contents = ServiceLocator.getDataIOService(getLoginInfo());
    if (DIContainer.getWebshopConfig().isOne()) {
      contentsCondition.setContentsType(ContentsType.CONTENT_SHOP_CAMPAIGN_SITE);
    } else {
      contentsCondition.setContentsType(ContentsType.CONTENT_SHOP_CAMPAIGN_SHOP);
    }
    contentsCondition.setShopCode(shopCode);
    contentsCondition.setCampaignCode(campaignCode);

    // コンテンツの存在チェック
    return contents.contentsExists(contentsCondition);

  }

  /**
   * 分析ボタンの表示制御を行います<BR>
   * 実施前のキャンペーンの場合は非表示、実施中か実施終了の場合は表示とします
   * 
   * @param startDate
   * @return date.after(DateUtil.fromString(startDate))
   */
  protected boolean getAnalysisButtonFlg(String startDate) {
    Date date = DateUtil.getSysdate();
    return date.after(DateUtil.fromString(startDate));
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return true;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.CampaignListSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106031004";
  }

}
