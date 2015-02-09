package jp.co.sint.webshop.service.data;

/**
 * CSVのスキーマ定義の一覧を表す列挙クラスです。<br>
 * 
 * @author System Integrator Corp.
 */
public enum CsvSchemaType {
  
  ARRIVAL_GOODS("ArrivalGoodsCsv"),
  CAMPAIGN_COMMODITY("CampaignCommodityCsv"),
  CAMPAIGN_RESEARCH("CampaignResearchCsv"),
  CATEGORY_ATTRIBUTE("CategoryAttributeCsv"),
  CATEGORY_ATTRIBUTE_VALUE("CategoryAttributeValueCsv"),
  CATEGORY_COMMODITY("CategoryCommodityCsv"),
  CATEGORY("CategoryCsv"),
  COMMODITY_DATA("CommodityDataCsv"),
  COMMODITY_ACCESS_LOG("CommodityAccessLogCsv"),
  COMMODITY_DETAIL("CommodityDetailCsv"),
  COMMODITY_HEADER("CommodityHeaderCsv"),
  // 20140226 txw add start
  COMMODITY_PRODUCTION_DATE("CommodityProductonDateCsv"),
  NEAR_COMMODITY_WARN("NearCommodityWarnCsv"),
  NEAR_COMMODITY_COMPARE_WARN("NearCommodityCompareWarnCsv"),
  // 20140226 txw add end
  CUSTOMER_ADDRESS("CustomerAddressCsv"),
  CUSTOMER("CustomerCsv"),
  CUSTOMER_DM("CustomerDMCsv"),
  CUSTOMER_LIST("CustomerListCsv"),
  CUSTOMER_PREFERENCE("CustomerPreferenceCsv"),
  DELIVERY_SLIP_NO("DeliverySlipNoCsv"),
  FM_ANALYSIS("FmAnalysisCsv"),
  GIFT_COMMODITY("GiftCommodityCsv"),
  GIFT("GiftCsv"),
  HOLIDAY("HolidayCsv"),
  MAIL_MAGAZINE("MailMagazineCsv"),
  ORDER_LIST("OrderListCsv"),
  OUTPUT_ORDER("OutputOrderCsv"),
  PAYMENT_INPUT("PaymentInputCsv"),
  POINT_STATUS_ALL("PointStatusAllCsv"),
  POINT_STATUS_SHOP("PointStatusShopCsv"), 
  REFERER("RefererCsv"),
  RELATED_COMMODITY_A("RelatedCommodityACsv"),
  RF_ANALYSIS("RfAnalysisCsv"),
  RM_ANALYSIS("RmAnalysisCsv"),
  SALES_AMOUNT_SKU("SalesAmountSkuCsv"),
  SEARCH_KEYWORD_LOG("SearchKeywordLogCsv"),
  SHIPPING_LIST("ShippingListCsv"),
  SHIPPING_REPORT("ShippingReportCsv"),
  STOCK("StockCsv"),
  STOCK_IO_DETAIL("StockIODetailCsv"),
  TAG_COMMODITY("TagCommodityCsv"),
  TAG("TagCsv"),
  /**アクセスログ集計を表す値です。*/
  ACCESS_LOG("AccessLogCsv"),
  /**商品別アクセスログ集計を表す値です。*/
  COMMKODITY_ACCESS_LOG("CommodityAccessLogCsv"),
  /**ショップ別売上集計を表す値です。*/
  SALES_AMOUNT("SalesAmountCsv"),
  /**売上集計-日別を表す値です。*/
  SALES_AMOUNT_BY_SHOP_DAILY("SalesAmountByShopDailyCsv"),
  /**売上集計-月別を表す値です。*/
  SALES_AMOUNT_BY_SHOP_MONTHLY("SalesAmountByShopMonthlyCsv"),
  /**アンケート集計-選択式を表す値です*/
  ENQUETE_SUMMARY_CHOICES("EnqueteSummaryChoicesCsv"),
  /**アンケート集計-自由回答を表す値です*/
  ENQUETE_SUMMARY_INPUT("EnqueteSummaryInputCsv"),
  //add by V10-CH start
  COUPON_STATUS_ALL("CouponStatusAllCsv"),
  //add by V10-CH end
  //soukai add ob 2011/12/20 start
  PRIVATE_COUPON("PrivateCouponCsv"),
  PUBLIC_COUPON("PublicCouponCsv"),
  PRIVATE_COUPON_ANALYSIS("PrivateCouponAnalysisCsv"),
  PUBLIC_COUPON_ANALYSIS("PublicCouponAnalysisCsv"),
  //soukai add ob 2011/12/20 end
  INQUIRY("InquiryCsv"),
  
  // 2012-01-04 yyq add start desc:商品数据导出到ERP
  ITEM_MASTERDATA_ERP("ItemMasterDataErpCsv"),
  // 2012-01-04 yyq add end desc:商品数据导出到ERP
  // 2012-01-30 yyq add start desc:商品数据导出到WMS
  ITEM_MASTERDATA_WMS("ItemMasterDataWmsCsv"),
  // 2012-01-30 yyq add end desc:商品数据导出到WMS
  // 2012-01-06 yyq add start desc:发货指示导出到ERP
  SO_DATA_ERP("SODataErpCsv"),
  // 2012-01-06 yyq add end desc:发货指示导出到ERP
  // 2012-12-21 yyq add start desc:发货指示导出到WMS
  SO_DATA_WMS("SODataWmsCsv"),
  // 2012-12-21 yyq add end desc:发货指示导出到WMS
  // 2012-01-07 yyq add start desc:有效库存导入到EC
  INV_DATA_ERP("INVDataErpCsv"),
  INV_DATA_WMS("INVDataWmsCsv"),
  INV_DATA_WMS_UNP("UNPDataWmsCsv"),
  INV_DATA_WMS_PO("PODataWmsCsv"),
  // 2012-01-07 yyq add end desc:有效库存导入到EC
  // 2012-01-13 yyq add start desc:发货实际ERP导入到EC
  SOSHIP_DATA_ERP("SOShipDataErpCsv"),
  // 2012-01-13 yyq add end desc:发货实际ERP导入到EC
  // 2012-09-09 yyq add start desc:发货实际WMS导入到EC
  SOSHIP_DATA_WMS("SOShipDataWmsCsv"),
  // 2012-09-09 yyq add end desc:发货实际WMS导入到EC
  // 2012-01-30 yyq add start desc:会员导出到ERP
  CUST_MASTERDATA_ERP("CustMasterDataErpCsv"),
  // 2012-01-30 yyq add end desc:会员导出到ERP 
  // 2012-01-30 yyq add start desc:EC入金导出到ERP
  AR_ECDATA_ERP("AREcDataErpCsv"),
  // 2012-01-30 yyq add end desc:EC入金导出到ERP 
  // 2012-02-09 yyq add start desc:Tmall入金导出到ERP
  AR_TMALLDATA_ERP("ARTmallDataErpCsv"),
  // 2012-02-09 yyq add end desc:Tmall入金导出到ERP 
  // add by wjh 2011/12/30 start
  CCOMMODITY_HEADER("CCommodityHeaderCsv"),
  CCOMMODITY_DETAIL("CCommodityDetailCsv"),
  CCOMMODITY_DATA("CCommodityDataCsv"),
  // add by wjh 2011/12/30 end
  //20120119 os013 add start
//  CCOMMODITY_STOCK("CCommodityStockCsv");
  //20120119 os013 add end
  //20120206 os013 add start
  //商品分类设定
  CATEGORY_ATTRIBUTE_VALUE_DATA("CategoryAttributeValueDataCsv"),
  //商品初期导入
  C_COMMODITY_INITIAL_STAGE("CCommodityInitialStageCsv"),
  //20120206 os013 add end
  // add by lc 2012-04-13 start
  TMALL_BRAND_LIST("TmallBrandListCsv"),
  TMALL_CATEGORY_LIST("TmallCategoryListCsv"),
  // 2014/04/25 京东WBS对应 ob_卢 add start
  JD_BRAND_LIST("JdBrandListCsv"),
  JD_CATEGORY_LIST("JdCategoryListCsv"),
  // 2014/04/25 京东WBS对应 ob_卢 add end
  CATEGORY_LIST("CategoryListCsv"),
  BRAND_LIST("BrandListCsv"),
  NEW_PUBLIC_COUPON("NewPublicCouponCsv"),
  NEW_PUBLIC_COUPON_DETAILS("NewPublicCouponDetailsCsv"),
  // add by lc 2012-04-13 start
  // add by yyq 2012-11-19 start
  COMMODITY_GIFT("CommodityGiftCsv"),
  // add by yyq 2012-11-19 end
  // add by yyq 2013-09-02 start
  NO_PIC_OR_STOP_SELLING("NoPicOrStopSellingCsv"),
  // add by yyq 2013-09-02 start
  // 20131105 wz add start
  /** 礼品卡抽出。 */
  GIFT_CARD_RULE("GiftCardRuleCsv"),
  /** 礼品卡发行统计导出。 */
  GIFT_CARD_LOG("GiftCardLogCsv"),
  /** 礼品卡使用明细导出。 */
  GIFT_CARD_USE_LOG("GiftCardUseLogCsv"),
  // 20131105 wz add end
  
  // 2014-05-15 hdh add start desc:京东入金导出到ERP
  AR_JDDATA_ERP("ARJdDataErpCsv"),
  // 2014-05-15 hdh add end desc:京东入金导出到ERP 
  // 2014-10-22 hdh add start
  DELIVERY_TYPE_NO("NewDeliverySlipNoCsv"),
  // 2014-10-22 hdh add end  
  // 2014-10-22 zzy add start
  JD_ORDER_LIST("JdOrderListCsv"),
  JD_ORDER_LIST_TWO("JdOrderListCsvTwo");
  
  // 2014-10-22 zzy add end

  private String id;
  
  private CsvSchemaType(String id) {
    this.id = id;
  }

  /**
   * idを取得します。
   * 
   * @return id
   */

  public String getId() {
    return id;
  }

}
