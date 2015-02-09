package jp.co.sint.webshop.web.action.front.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.communication.CampaignHeadLine;
import jp.co.sint.webshop.service.communication.CampaignListSearchCondition;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.CampaignListBean;
import jp.co.sint.webshop.web.bean.front.catalog.CampaignListBean.CampaignListDetailBean;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U2060310:キャンペーン一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CampaignListInitAction extends WebFrontAction<CampaignListBean> {

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
	  UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    CampaignListSearchCondition condition = new CampaignListSearchCondition();
    condition.setCampaignStartDateTo(DateUtil.getSysdateString());
    condition.setCampaignEndDateFrom(DateUtil.getSysdateString());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    SearchResult<CampaignHeadLine> result = communicationService.getCampaignList(condition);
    List<CampaignHeadLine> campaignList = result.getRows();

    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    DataIOService dataIOService = ServiceLocator.getDataIOService(getLoginInfo());

    CampaignListBean nextBean = getBean();

    ContentsSearchCondition contensCondition = new ContentsSearchCondition();
    if (DIContainer.getWebshopConfig().isOne()) {
      contensCondition.setContentsType(ContentsType.CONTENT_SHOP_CAMPAIGN_SITE);
    } else {
      contensCondition.setContentsType(ContentsType.CONTENT_SHOP_CAMPAIGN_SHOP);
    }

    List<CampaignListDetailBean> list = new ArrayList<CampaignListDetailBean>();
    for (CampaignHeadLine campaign : campaignList) {
      CampaignListDetailBean detail = new CampaignListDetailBean();
      detail.setShopCode(campaign.getShopCode());
      detail.setShopName(campaign.getShopName());
      detail.setCampaignCode(campaign.getCampaignCode());
      detail.setCampaignName(utilService.getNameByLanguage(campaign.getCampaignName(),campaign.getCampaignNameEn(),campaign.getCampaignNameJp()));
      detail.setCampaignStartDate(campaign.getCampaignStartDate());
      detail.setCampaignEndDate(campaign.getCampaignEndDate());
      detail.setCampaignDiscountRate(campaign.getCampaignDiscountRate());
      detail.setCampaignCommodityCount(NumUtil.toString(catalogService.getCampaignCommodityCount(campaign.getShopCode(), campaign
          .getCampaignCode(), DisplayClientType.PC.getValue())));

      contensCondition.setShopCode(detail.getShopCode());
      contensCondition.setCampaignCode(detail.getCampaignCode());

      detail.setCampaignExists(dataIOService.contentsExists(contensCondition));

      list.add(detail);
    }
    nextBean.setList(list);

    nextBean.setPagerValue(PagerUtil.createValue(result));

    setRequestBean(nextBean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CampaignListBean reqBean = (CampaignListBean) getRequestBean();
    if (getConfig().getOperatingMode() == OperatingMode.ONE) {
      reqBean.setDisplayShopNameColumn(false);
    } else {
      reqBean.setDisplayShopNameColumn(true);
    }
    setRequestBean(reqBean);
  }

}
