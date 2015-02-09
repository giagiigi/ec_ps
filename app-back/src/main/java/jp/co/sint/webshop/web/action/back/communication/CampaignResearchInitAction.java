package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.CampaignResearch;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.CampaignResearchBean;
import jp.co.sint.webshop.web.bean.back.communication.CampaignResearchBean.CampaignCommodityResearchBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060340:キャンペーン分析のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CampaignResearchInitAction extends WebBackAction<CampaignResearchBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = Permission.CAMPAIGN_READ_SITE.isGranted(getLoginInfo())
        || Permission.CAMPAIGN_READ_SHOP.isGranted(getLoginInfo());

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 0) {

      String shopCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(shopCode)) {
        return false;
      }

      authorization &= shopCode.equals(getLoginInfo().getShopCode());
    }

    return authorization;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String[] path = getRequestParameter().getPathArgs();

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    Campaign campaign = service.getCampaign(path[0], path[1]);

    if (campaign == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.communication.CampaignResearchInitAction.0")));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    CampaignResearchBean bean = new CampaignResearchBean();
    bean.setCampaignCode(campaign.getCampaignCode());
    bean.setCampaignName(campaign.getCampaignName());
    bean.setCampaignDiscountRate(Long.toString(campaign.getCampaignDiscountRate()));
    bean.setCampaignStartDate(DateUtil.toDateString(campaign.getCampaignStartDate()));
    bean.setCampaignEndDate(DateUtil.toDateString(campaign.getCampaignEndDate()));
    bean.setShopCode(path[0]);

    int orderAmount = 0;
    BigDecimal totalSales = BigDecimal.ZERO;

    List<CampaignResearch> list = service.getCampaignResearch(path[0], path[1]);
    for (CampaignResearch c : list) {
      CampaignCommodityResearchBean commodity = new CampaignCommodityResearchBean();
      commodity.setCommodityCode(c.getCommodityCode());
      commodity.setCommodityName(c.getCommodityName());
      commodity.setCommoditySalesAmount(c.getCommoditySalesAmount());
      orderAmount += Integer.parseInt(c.getCommodityOrderAmount());
      //totalSales += Integer.parseInt(c.getCommoditySalesAmount());
      totalSales = totalSales.add(NumUtil.parse(c.getCommoditySalesAmount()));
      bean.getList().add(commodity);
    }

    bean.setOrderAmount(String.valueOf(orderAmount));
    bean.setTotalSales(String.valueOf(totalSales));

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
    if (getRequestParameter().getPathArgs().length < 2) {
      setNextUrl("/app/communication/campaign_list/init");
      return false;
    }
    return true;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    // CSV出力ボタン表示制御
    CampaignResearchBean bean = (CampaignResearchBean) getRequestBean();
    bean.setCsvButtonDisplay(Permission.CAMPAIGN_DATA_IO_SITE.isGranted(getLoginInfo())
        || Permission.CAMPAIGN_DATA_IO_SHOP.isGranted(getLoginInfo()));

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.CampaignResearchInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106034002";
  }

}
