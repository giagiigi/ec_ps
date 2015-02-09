package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityBean.CommodityListDetailBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1020110:新規受注(商品選択)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCommoditySearchAction extends NeworderCommodityBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    NeworderCommodityBean bean = getBean();
    return validateBean(bean);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    NeworderCommodityBean bean = getBean();

    // カート情報部生成
    bean.setCartCommodityList(createBeanFromCart());

    // SearchConditionに検索条件を設定
    CommodityContainerCondition condition = new CommodityContainerCondition();
    condition.setSearchCategoryCode(bean.getSearchCategoryCode());
    condition.setSearchShopCode(bean.getSearchShopCode());
    condition.setSearchSkuCode(bean.getSearchSkuCode());
    condition.setSearchCommodityName(bean.getSearchCommodityName());
    condition.setAlignmentSequence("back_neworder");

    PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // 検索実行
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<CommodityContainer> result = service.getCommodityContainerBySku(condition);

    // ページング設定
    bean.setPagerValue(PagerUtil.createValue(result));

    // リスト生成
    List<CommodityListDetailBean> detailList = new ArrayList<CommodityListDetailBean>();
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    for (CommodityContainer commodity : result.getRows()) {
      CommodityListDetailBean row = new CommodityListDetailBean();
      String shopCode = commodity.getCommodityHeader().getShopCode();
      String commodityCode = commodity.getCommodityHeader().getCommodityCode();
      Campaign campaign = service.getAppliedCampaignInfo(shopCode, commodityCode);
      Price price = new Price(commodity.getCommodityHeader(), commodity.getCommodityDetail(), campaign, taxRate);
      row.setShopCode(commodity.getCommodityHeader().getShopCode());
      row.setShopName(commodity.getShop().getShopName());
      row.setCommodityCode(commodity.getCommodityHeader().getCommodityCode());
      row.setCommodityName(commodity.getCommodityHeader().getCommodityName());
      row.setSkuCode(commodity.getCommodityDetail().getSkuCode());
      row.setStandardName1(commodity.getCommodityDetail().getStandardDetail1Name());
      row.setStandardName2(commodity.getCommodityDetail().getStandardDetail2Name());
      // 20130625 txw add start
      if(StringUtil.hasValue(commodity.getCommodityHeader().getOriginalCommodityCode()) && commodity.getCommodityHeader().getCombinationAmount()!=null){
        row.setUnitPrice(commodity.getCommodityDetail().getUnitPrice().multiply(new BigDecimal(commodity.getCommodityHeader().getCombinationAmount())));
        Long sq=service.getAvailableStock(commodity.getCommodityHeader().getShopCode(), commodity.getCommodityHeader().getOriginalCommodityCode());
        row.setStockQuantity(new Double(Math.floor(sq / commodity.getCommodityHeader().getCombinationAmount())).longValue());
      }else{
        row.setUnitPrice(commodity.getCommodityDetail().getUnitPrice());
        row.setStockQuantity(service.getAvailableStock(commodity.getCommodityHeader().getShopCode(), commodity.getCommodityDetail()
            .getSkuCode()));
      }
      // 20130625 txw add end
      row.setTaxType(commodity.getCommodityHeader().getCommodityTaxType());
      row.setStockManagementType(NumUtil.toString(commodity.getCommodityHeader().getStockManagementType()));

      // キャンペーン値引き設定
      Campaign campainInfo = price.getCampaignInfo();
      if (campainInfo != null) {
        row.setCampainCode(campainInfo.getCampaignCode());
        row.setCampainName(campainInfo.getCampaignName());
        row.setCampaignDiscountRate(NumUtil.toString(campainInfo.getCampaignDiscountRate()));
      }
      
      // 20130625 txw add start
      if(StringUtil.hasValue(commodity.getCommodityHeader().getOriginalCommodityCode()) && commodity.getCommodityHeader().getCombinationAmount()!=null){
        row.setRetailPrice(price.getRetailPrice().multiply(new BigDecimal(commodity.getCommodityHeader().getCombinationAmount())));
      }else{
        row.setRetailPrice(price.getRetailPrice());
      }
      // 20130625 txw add end
      row.setTaxRate(price.getTaxRate());
      row.setReserve(price.isReserved());

      // 有効在庫数チェック
      if (service.isAvailable(row.getShopCode(), row.getSkuCode(), 1, row.isReserve()).equals(CommodityAvailability.AVAILABLE)) {
        row.setDisplayCartButton(true);
      } else {
        row.setDisplayCartButton(false);
      }

      // 2012/11/20 促销对应 新建订单_商品选择  ob add start
      getAvailableStock(shopCode, commodity.getCommodityDetail().getSkuCode(), row);
      // 2012/11/20 促销对应 新建订单_商品选择  ob add end
      detailList.add(row);
    }
    bean.setCommodityList(detailList);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderCommoditySearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102011007";
  }

}
