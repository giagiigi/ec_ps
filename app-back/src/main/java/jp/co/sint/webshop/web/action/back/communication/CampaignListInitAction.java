package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.CampaignHeadLine;
import jp.co.sint.webshop.service.communication.CampaignListSearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.CampaignListBean;
import jp.co.sint.webshop.web.bean.back.communication.CampaignListBean.CampaignListBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CampaignListInitAction extends CampaignListSearchAction {

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

    CampaignListBean bean = getBean();
    CampaignListSearchCondition condition = getCondition();

    if (getLoginInfo().isSite() && !getConfig().isOne()) {
      condition.setShopCode("");
    } else if (getLoginInfo().isShop() || getConfig().isOne()) {
      condition.setShopCode(getLoginInfo().getShopCode());
    }
    condition.setCampaignCode("");
    condition.setCampaignName("");
    condition.setCampaignStartDateFrom("");
    condition.setCampaignStartDateTo("");
    condition.setCampaignEndDateFrom("");
    condition.setCampaignEndDateTo("");

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    SearchResult<CampaignHeadLine> result = service.getCampaignList(condition);

    // オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.OVERFLOW);

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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.CampaignListInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106031002";
  }

}
