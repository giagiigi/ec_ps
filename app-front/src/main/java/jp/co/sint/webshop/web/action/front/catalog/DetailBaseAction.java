package jp.co.sint.webshop.web.action.front.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CommodityLayout;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.campain.CampaignMain;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean.DisplayPartsCode;
import jp.co.sint.webshop.web.exception.NoSuchCommodityException;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.CompleteMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.message.front.cart.CartDisplayMessage;
import jp.co.sint.webshop.web.message.front.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040510:商品詳細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class DetailBaseAction extends WebFrontAction<CommodityDetailBean> {

  /**
   * 商品選択なし
   */
  public static final String NOT_SELECT_SKU = "notSelected";

  /**
   * 商品が存在しない
   */
  public static final String NOT_EXIST_SKU = "notExistSku";

  /**
   * お気に入りのコピー
   */
  public static final String DUPLICATED_FAVORITE = "duplicated_favorite";

  /**
   * お気に入りに登録
   */
  public static final String REGISTER_FAVORITE = "register_favorite";

  /**
   * 予約上限数超過
   */
  public static final String ONESHOT_RESERVATION_OVER = "oneshot_reservation_over";

  /**
   * 入荷お知らせ登録
   */
  public static final String REGISTER_ARRIVAL_GOODS = "register_arrival_goods";

  // 10.1.7 10308 追加 ここから
  /**
   * レビュー二重投稿
   */
  public static final String DUPLICATED_REVIEW_POST = "duplicated_review_post";
  // 10.1.7 10308 追加 ここまで
  
  /**
   * 商品検索キーワードからメタタグに設定するための文字数
   */
  public static final int META_TAG_LENGTH = 100;

  /**
   * paramを元に画面表示されるエラーメッセージを設定します
   * 
   * @param param
   */
  public void setDisplayMessage(String param) {
    if (param.equals(NOT_SELECT_SKU)) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.NO_STANDARD_COMBINATION));
    } else if (param.equals(DUPLICATED_FAVORITE)) {
      addErrorMessage(WebMessage.get(CartDisplayMessage.DUPLICATED_FAVORITE));
    } else if (param.equals(REGISTER_FAVORITE)) {
      addErrorMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.front.catalog.DetailBaseAction.0")));
    } else if (param.equals(NOT_EXIST_SKU)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.front.catalog.DetailBaseAction.1")));
    } else if (param.equals(REGISTER_ARRIVAL_GOODS)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.front.catalog.DetailBaseAction.2")));
      // 10.1.7 10308 追加 ここから
    } else if (param.equals(DUPLICATED_REVIEW_POST)) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.DUPLICATED_REVIEW_ERROR));
    // 10.1.7 10308 追加 ここまで
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }
  }

  /**
   * 商品SKUを選択したかどうかを返します 商品SKUが選択されていた場合はNextUrlに遷移先URLを格納します
   * 
   * @param reqBean
   * @return true or false
   */
  public boolean isNotSelectedSku(CommodityDetailBean reqBean) {
    if (StringUtil.isNullOrEmpty(reqBean.getSkuCode())) {
      setNextUrl("/app/catalog/detail/init/" + reqBean.getShopCode() + "/" + reqBean.getCommodityCode() + "/" + NOT_SELECT_SKU);
      return true;
    }
    return false;
  }

  /**
   * 商品SKUが存在しているかどうかを返します 商品SKUが存在していた場合はNextUrlに遷移先URLを格納します
   * 
   * @param reqBean
   * @return true or false
   */
  public boolean existSku(CommodityDetailBean reqBean) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo info = service.getSkuInfo(reqBean.getShopCode(), reqBean.getSkuCode());
    if (info != null) {
      return true;
    }
    setNextUrl("/app/catalog/detail/init/" + reqBean.getShopCode() + "/" + reqBean.getCommodityCode() + "/" + NOT_EXIST_SKU);
    return false;
  }

  /**
   * 商品情報を生成します
   * 
   * @param reqBean
   */
  public void createCommodityInfo(CommodityDetailBean reqBean) {

    boolean isForSale = !reqBean.isPreview();

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    List<CommodityContainer> containerList = service.getCommoditySkuList(reqBean.getShopCode(), reqBean.getCommodityCode(),
        isForSale, DisplayClientType.PC);
    if (containerList.isEmpty()) {
      throw new NoSuchCommodityException(reqBean.getShopCode(), reqBean.getCommodityCode());
    } else {
      if (containerList.get(0).getCommodityDetail().getInnerQuantity() != null ){
        reqBean.setInnerQuantity(containerList.get(0).getCommodityDetail().getInnerQuantity()+"");
      }
      if (containerList.get(0).getCommodityDetail().getUnitPrice()!= null){
        reqBean.setUnitPrice(containerList.get(0).getCommodityDetail().getUnitPrice()+"");
      }
      if (containerList.get(0).getCommodityDetail().getDiscountPrice()!= null){
        reqBean.setDiscountPrice(containerList.get(0).getCommodityDetail().getDiscountPrice()+"");
      }
      
      
      if(containerList.get(0).getCommodityHeader().getImportCommodityType() != null){
        reqBean.setImportCommodityType(containerList.get(0).getCommodityHeader().getImportCommodityType());
      }
      if(containerList.get(0).getCommodityHeader().getClearCommodityType() != null){
        reqBean.setClearCommodityType(containerList.get(0).getCommodityHeader().getClearCommodityType());
      }
      if(containerList.get(0).getCommodityHeader().getReserveCommodityType1() != null){
        reqBean.setReserveCommodityType1(containerList.get(0).getCommodityHeader().getReserveCommodityType1());
      }
      if(containerList.get(0).getCommodityHeader().getReserveCommodityType2() != null){
        reqBean.setReserveCommodityType2(containerList.get(0).getCommodityHeader().getReserveCommodityType2());
      }
      if(containerList.get(0).getCommodityHeader().getReserveCommodityType3() != null){
        reqBean.setReserveCommodityType3(containerList.get(0).getCommodityHeader().getReserveCommodityType3());
      }
      if(containerList.get(0).getCommodityHeader().getNewReserveCommodityType1() != null){
        reqBean.setNewReserveCommodityType1(containerList.get(0).getCommodityHeader().getNewReserveCommodityType1());
      }
      if(containerList.get(0).getCommodityHeader().getNewReserveCommodityType2() != null){
        reqBean.setNewReserveCommodityType2(containerList.get(0).getCommodityHeader().getNewReserveCommodityType2());
      }
      if(containerList.get(0).getCommodityHeader().getNewReserveCommodityType3() != null){
        reqBean.setNewReserveCommodityType3(containerList.get(0).getCommodityHeader().getNewReserveCommodityType3());
      }
      if(containerList.get(0).getCommodityHeader().getNewReserveCommodityType4() != null){
        reqBean.setNewReserveCommodityType4(containerList.get(0).getCommodityHeader().getNewReserveCommodityType4());
      }
      if(containerList.get(0).getCommodityHeader().getNewReserveCommodityType5() != null){
        reqBean.setNewReserveCommodityType5(containerList.get(0).getCommodityHeader().getNewReserveCommodityType5());
      }
      reqBean.setSaleFlag(containerList.get(0).getCommodityHeader().getSaleFlg().toString());
      if(containerList.get(0).getCommodityHeader().getDiscountPriceEndDatetime()==null && containerList.get(0).getCommodityHeader().getDiscountPriceStartDatetime()==null){
        reqBean.setSaleFlagFlag(false);
      } else {
        CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
        TaxUtil tu = DIContainer.get("TaxUtil");
        Long taxRate = tu.getTaxRate();
        Campaign campaign = catalogService.getAppliedCampaignInfo(containerList.get(0).getCommodityHeader().getShopCode(), containerList.get(0).getCommodityHeader()
            .getCommodityCode());
        Price price = new Price(containerList.get(0).getCommodityHeader(), containerList.get(0).getCommodityDetail(), campaign, taxRate);
        if(price.isDiscount()){
          reqBean.setSaleFlagFlag(true);
        }
        setDisplayPriceMode(reqBean, price);
      }
      
    }
    String commoditySearchWords = "";
    if (!reqBean.isPreview()) {
      if (containerList.isEmpty()) {
        throw new NoSuchCommodityException(reqBean.getShopCode(), reqBean.getCommodityCode());
      } else {
        commoditySearchWords = containerList.get(0).getCommodityHeader().getCommoditySearchWords();
      }
    } else {
      RequestParameter reqparam = getRequestParameter();
      commoditySearchWords = reqparam.get("commoditySearchWords");
    }

    if (StringUtil.isNullOrEmptyAnyOf(commoditySearchWords) || commoditySearchWords.length() <= META_TAG_LENGTH) {
      reqBean.setMetaTag(commoditySearchWords);
    } else {
      reqBean.setMetaTag(commoditySearchWords.substring(0, META_TAG_LENGTH));
    }
    // add by lc 2012-10-25 start for 验证商品是否在活动中
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    CampainFilter cf = new CampainFilter();
    CampaignMain freeShippingCm = cf.existsSkuCampaign(reqBean.getCommodityCode());
    if (freeShippingCm!=null) {
    	if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
            reqBean.getCampaignTip().add(freeShippingCm.getCampaignName());
          } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
            reqBean.getCampaignTip().add(freeShippingCm.getCampaignNameJp());
          } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
            reqBean.getCampaignTip().add(freeShippingCm.getCampaignNameEn());
          }
    }
    
    // 2012/11/16 促销对应 ob add start
    List<CampaignMain> cm = cf.getSkuCampaigns(reqBean.getCommodityCode());
    if (cm != null) {
      for (CampaignMain compaign : cm) {
        if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
          reqBean.getCampaignTip().add(compaign.getCampaignName());
        } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
          reqBean.getCampaignTip().add(compaign.getCampaignNameJp());
        } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
          reqBean.getCampaignTip().add(compaign.getCampaignNameEn());
        }
      }
    }
    // 2012/11/16 促销对应 ob add end
    // add by lc 2012-10-25 end
    
    List<CommodityLayout> layoutList = service.getCommodityLayoutList(reqBean.getShopCode());
    List<String> partsList = new ArrayList<String>();
    for (CommodityLayout cl : layoutList) {
      if (cl.getDisplayFlg().equals(DisplayFlg.VISIBLE.longValue())) {
        partsList.add(DisplayPartsCode.getPartsId(cl.getPartsCode()));
      }
    }
    reqBean.setPartsList(partsList);
  }
  
  public String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }

  // 10.1.4 10193 追加 ここから
  /**
   * 必須入力項目が値を持っているかどうかを判定します。<br>
   * ひとつでも値を持たない項目がある場合、Mapのkeyを使用してエラーメッセージを作成しfalseを返します。<br>
   * すべて値を持つ場合は、trueを返します。
   * 
   * @param target
   * @return true or false
   */
  public boolean checkRequiredValue(Map<String, String> target) {
    boolean result = true;
    for (Entry<String, String> e : target.entrySet()) {
      if (StringUtil.isNullOrEmpty(e.getValue())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, e.getKey()));
        result &= false;
      }
    }
    return result;
  }
  // 10.1.4 10193 追加 ここまで
  
  /**
   * @param reqBean
   * @param price
   */
  private void setDisplayPriceMode(CommodityDetailBean reqBean, Price price) {
    if (price.isSale()) {
      // 通常の販売期間の場合
      reqBean.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
    } else if (price.isDiscount()) {
      // 特別価格期間の場合
      reqBean.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
    } else if (price.isReserved()) {
      // 予約期間の場合
      reqBean.setPriceMode(CommodityPriceType.RESERVATION_PRICE.getValue());
    } else {
      reqBean.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
    }
  }

}
