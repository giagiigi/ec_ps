package jp.co.sint.webshop.web.action.front.catalog;

import java.util.Date;

import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.CampaignListBean;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2060310:キャンペーン一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CampaignListMoveAction extends WebFrontAction<CampaignListBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    if (getRequestParameter().getPathArgs().length < 2) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }

    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String shopCode = getRequestParameter().getPathArgs()[0];
    String campaignCode = getRequestParameter().getPathArgs()[1];

    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
    String nextUrl = "";
    Campaign campaign = svc.getCampaign(shopCode, campaignCode);

    // キャンペーンが存在しない、または実施中でない場合は一覧に戻す
    if (campaign == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.front.catalog.CampaignListMoveAction.0")));
      setRequestBean(getBean());
      return FrontActionResult.RESULT_SUCCESS;
    } else {
      Date startDate = campaign.getCampaignStartDate();
      Date endDate = campaign.getCampaignEndDate();
      Date today = DateUtil.truncateDate(DateUtil.getSysdate());
      if (today.before(startDate) || today.after(endDate)) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.front.catalog.CampaignListMoveAction.0")));
        setRequestBean(getBean());
        return FrontActionResult.RESULT_SUCCESS;
      }
    }

    ContentsSearchCondition condition = new ContentsSearchCondition();
    if (DIContainer.getWebshopConfig().isOne()) {
      condition.setContentsType(ContentsType.CONTENT_SHOP_CAMPAIGN_SITE);
    } else {
      condition.setContentsType(ContentsType.CONTENT_SHOP_CAMPAIGN_SHOP);
    }
    condition.setShopCode(shopCode);
    condition.setCampaignCode(campaignCode);

    DataIOService service = ServiceLocator.getDataIOService(getLoginInfo());
    if (service.contentsExists(condition)) {
      nextUrl = nextUrl + "/contents/shop/" + shopCode + "/campaign/" + campaignCode;
    } else {
      CommodityListBean nextBean = new CommodityListBean();
      nextBean.setSearchShopCode(shopCode);
      nextBean.setSearchCampaignCode(campaignCode);
      nextUrl = nextUrl + "/app/catalog/list/init" + nextBean.toQueryString();
    }

    setNextUrl(nextUrl);

    // 遷移元情報をセッションに保存
    DisplayTransition.add(getBean(), "/app/catalog/campaign_list/init",
        getSessionContainer());

    return FrontActionResult.RESULT_SUCCESS;
  }

}
