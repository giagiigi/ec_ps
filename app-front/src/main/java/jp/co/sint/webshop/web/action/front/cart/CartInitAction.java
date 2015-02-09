package jp.co.sint.webshop.web.action.front.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dao.CampaignMainDao;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.CompositionItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.catalog.CommodityCompositionContainer;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.NumberLimitPolicy;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.cart.CartBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.cart.CartDisplayMessage;

/**
 * U2020110:ショッピングカートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CartInitAction extends CartBaseAction {

  // 2012/12/11 ob add start
  @Override
  public void init() {
	  if (StringUtil.hasValue(this.getRequestParameter().get("mcd")) && this.getRequestParameter().get("mcd").length()<=16) {
		  this.getSessionContainer().setMedia(this.getRequestParameter().get("mcd"));
	  }
    // ADD  赠品促销   START
	  Cart cart = getCart();
	  if (cart.getItemCountExceptGift() > 0) {
	    CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());
      for (CartItem item : cart.get()) {
        Long CampaignAvailability = catalogSvc.campaignAvailability(item.getCommodityCode(), item.getShopCode(), null);
        if (CampaignAvailability == null || CampaignAvailability == 0L) {
          // 20140807 hdh update start  小购物车与大购物车内容不一致
//          cart.remove(item.getShopCode(), item.getSkuCode(), item.getCommodityCode());
          // 20140807 hdh update start
        } else {
          setOtherGift(item.getShopCode(), item.getSkuCode(), item.getSkuCode(), item.getQuantity());
        }
      }
	  }
    // ADD  赠品促销   END
  }

  /**
   * 促销礼品设置
   * 
   * @param 店铺编号
   * @param SKU编号
   * @param 数量
   */
  private void setOtherGift(String shopCode, String skuCode, String setSkuCode, int quantitys) {
    CampainFilter campainFilter = new CampainFilter();
    CampaignMainDao camDao = DIContainer.getDao(CampaignMainDao.class);
    CartItem cartItem = getCart().get(shopCode, setSkuCode);
    List<GiftItem> giftItems = campainFilter.getOtherGiftCodeBySku(cartItem.getCommodityCode(), shopCode);
    if (cartItem != null) {
      quantitys = cartItem.getQuantity();
    }
    
    if (giftItems != null) {
      for (int i = 0; i < giftItems.size(); i++) {
        CampaignMain camMain = camDao.load(giftItems.get(i).getCampaignCode());
        Long giftAmount = 1L;
        if (camMain.getGiftAmount() != null ) {
          giftAmount = camMain.getGiftAmount();
        }
        giftItems.get(i).setQuantity(Integer.parseInt(giftAmount.toString()));

      }
    }
    cartItem.getCommodityInfo().setGiftList(giftItems);
  }
  // 2012/11/17 促销对应 ob add end
  
  
  // 2012/12/11 ob add end
  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    Cart cart = getCart();
    if (cart.getItemCountExceptGift() == 0) {
      cart.clearAcceptedGift();
      addErrorMessage(WebMessage.get(CartDisplayMessage.NO_CART_ITEM));
    }

    String shopName = "";

    // 限界値チェック
    NumberLimitPolicy numberPolicy = DIContainer.getNumberLimitPolicy();
    if (getConfig().getOperatingMode() == OperatingMode.MALL || getConfig().getOperatingMode() == OperatingMode.ONE) {
      SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
      Shop site = service.getSite();
      shopName = site.getShopName();

      BigDecimal totalCurrency = BigDecimal.ZERO;
      int totalQuantity = 0;

      for (CartItem item : cart.get()) {
        totalCurrency = totalCurrency.add(BigDecimalUtil.multiply(item.getRetailPrice().add(item.getGiftPrice()), item
            .getQuantity()));
        totalQuantity = totalQuantity + item.getQuantity();
      }
      ValidationResult resultCurrency = numberPolicy.validTotalCurrency(totalCurrency);
      if (resultCurrency.isError()) {
        addErrorMessage(getLimitErrorMessageOfEachMode(shopName, resultCurrency.getFormedMessage()));
      }
      ValidationResult resultAmount = numberPolicy.validTotalAmount(Long.valueOf(totalQuantity));
      if (resultAmount.isError()) {
        addErrorMessage(getLimitErrorMessageOfEachMode(shopName, resultAmount.getFormedMessage()));
      }
    } else if (getConfig().getOperatingMode() == OperatingMode.SHOP) {
      for (String shopCode : cart.getShopCodeList()) {
        BigDecimal totalCurrency = BigDecimal.ZERO;
        int totalQuantity = 0;
        String itemShopName = "";
        for (CartItem item : cart.get(shopCode)) {
          itemShopName = item.getShopName();
          totalCurrency = totalCurrency.add(BigDecimalUtil.multiply((item.getRetailPrice().add(item.getGiftPrice())), item
              .getQuantity()));
          totalQuantity = totalQuantity + item.getQuantity();
        }
        ValidationResult resultCurrency = numberPolicy.validTotalCurrency(totalCurrency);
        if (resultCurrency.isError()) {
          addErrorMessage(getLimitErrorMessageOfEachMode(itemShopName, resultCurrency.getFormedMessage()));
        }
        ValidationResult resultAmount = numberPolicy.validTotalAmount(Long.valueOf(totalQuantity));
        if (resultAmount.isError()) {
          addErrorMessage(getLimitErrorMessageOfEachMode(itemShopName, resultAmount.getFormedMessage()));
        }
      }
    }
    // 予約品限界値チェック
    List<CartItem> reserveList = cart.getReserve();
    for (CartItem item : reserveList) {
      if (getConfig().getOperatingMode() == OperatingMode.SHOP) {
        shopName = item.getShopName();
      }
      ValidationResult resultCurrency = numberPolicy.validTotalCurrency(BigDecimalUtil.multiply((item.getRetailPrice().add(item
          .getGiftPrice())), item.getQuantity()));
      if (resultCurrency.isError()) {
        addErrorMessage(getLimitErrorMessageOfEachMode(shopName, resultCurrency.getFormedMessage()));
      }
      ValidationResult resultAmount = numberPolicy.validTotalAmount(Long.valueOf(item.getQuantity()));
      if (resultAmount.isError()) {
        addErrorMessage(getLimitErrorMessageOfEachMode(shopName, resultAmount.getFormedMessage()));
      }
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
    //session中赠品重置  20140725 hdh
    giftReset(getCart(), "00000000");
    
    CartBean bean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    // 在庫引当エラー
    if (bean.getErrorMessageList() != null) {
      for (String error : bean.getErrorMessageList()) {
        addErrorMessage(error);
      }
    }
    Cart cart = getCart();
    for (CartItem item : cart.get()) {
      if (SetCommodityFlg.OBJECTIN.longValue().equals(item.getCommodityInfo().getSetCommodityFlg())) {
        setComposition("00000000", item.getCommodityInfo().getCommodityCode(), item.getCommodityInfo().getCommodityCode(), service, item);
      }
    }
    
    CartBean nextBean = createBeanFromCart();
    
    List<Sku> skuList = cart.getHistory();
    if (skuList.size() > 0) {
      Sku sku = skuList.get(skuList.size() - 1);

      String skuCode = sku.getSkuCode();
      if (SetCommodityFlg.OBJECTIN.longValue().equals(sku.getCommodityType()) && sku.getSkuCode().contains(":")) {
        skuCode = sku.getSkuCode().split(":")[0];
      }
      CommodityInfo commodityInfo = service.getSkuInfo(sku.getShopCode(), skuCode);
      
      if (commodityInfo == null) {
        nextBean.setDisplayBackButton(false);
      } else {
        String commodityCode = commodityInfo.getHeader().getCommodityCode();
        nextBean.setDisplayBackButton(true);
        nextBean.setBackUrl("/app/catalog/detail/init/" + commodityCode + "?selectedSkuCode="
            + skuCode);
      }
    } else {
      nextBean.setDisplayBackButton(false);
    }
    bean = nextBean;
    
    //赠品重置
    //giftReset(getCart(), "00000000");
//    if(bean.getShopCartBean() !=null && bean.getShopCartBean().size()>0){
//      for(ShopCartBean shopBean:bean.getShopCartBean()){
//        giftReset(shopBean);
//      }
//    }
    
    setRequestBean(nextBean);

    return FrontActionResult.RESULT_SUCCESS;
  }
  
  public void setComposition(String shopCode, String skuCode, String commodityCode, CatalogService cs, CartItem itemOld) {

    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    TaxUtil u = DIContainer.get("TaxUtil");
    Long taxRate = u.getTaxRate();
    CartItem cartItem = itemOld;
    cartItem.getCommodityInfo().setCompositionList(new ArrayList<CompositionItem>());
    BigDecimal weight = new BigDecimal(0);
    List<SetCommodityComposition> setCommodityCompositions = cs.getSetCommodityCompositipon(commodityCode, shopCode);
    List<CommodityCompositionContainer> compositionContainerList = getCompositionContainerList(shopCode, commodityCode, cs);

    for (CommodityCompositionContainer detail : compositionContainerList) {
      CompositionItem item = new CompositionItem();
      String compositionSkuCode = getStockOfAllSku(shopCode, detail.getCommodityDetailList(), cs);
      CommodityInfo info = cs.getCommodityInfoBySkuCode(shopCode, detail.getCommodityCode(), compositionSkuCode);
      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        item.setCommodityName(info.getHeader().getCommodityName());
        item.setStandardDetail1Name(info.getDetail().getStandardDetail1Name());
        item.setStandardDetail2Name(info.getDetail().getStandardDetail2Name());
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        item.setCommodityName(info.getHeader().getCommodityNameJp());
        item.setStandardDetail1Name(info.getDetail().getStandardDetail1NameJp());
        item.setStandardDetail2Name(info.getDetail().getStandardDetail2NameJp());
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        item.setCommodityName(info.getHeader().getCommodityNameEn());
        item.setStandardDetail1Name(info.getDetail().getStandardDetail1NameEn());
        item.setStandardDetail2Name(info.getDetail().getStandardDetail2NameEn());
      }
      item.setShopCode(shopCode);
      item.setCommodityCode(info.getHeader().getCommodityCode());
      item.setSkuCode(info.getDetail().getSkuCode());
      Price price = new Price(info.getHeader(), info.getDetail(), null, taxRate);
      for (SetCommodityComposition set : setCommodityCompositions) {
        if (info.getHeader().getCommodityCode().equals(set.getChildCommodityCode())) {
          item.setRetailPrice(set.getRetailPrice());
        }
      }
      item.setCommodityTaxType(info.getHeader().getCommodityTaxType());
      item.setRepresentFlg(info.getHeader().getRepresentSkuCode().equals(info.getDetail().getSkuCode()));
      item.setUnitPrice(info.getDetail().getUnitPrice());
      item.setRetailTax(price.retailTaxCharge().longValue());
      item.setCommodityTaxRate(taxRate);
      item.setWeight(info.getDetail().getWeight());
      item.setCommodityTax(price.getUnitTaxCharge());
      weight = weight.add(info.getDetail().getWeight());
      cartItem.getCommodityInfo().getCompositionList().add(item);
    }

    CommodityInfo info = cs.getCommodityInfo(shopCode, commodityCode);
    cartItem.getCommodityInfo().setCompositionWeight(info.getDetail().getWeight());
    cartItem.getCommodityInfo().setWeight(weight);
  }
  
  public List<CommodityCompositionContainer> getCompositionContainerList(String shopCode, String commodityCode, CatalogService cs) {
    // 取得子商品
    List<CommodityCompositionContainer> commodityCompositionContainerList = cs.getCommodityCompositionContainerList(shopCode,
        commodityCode);
    return commodityCompositionContainerList;
  }
  


}
