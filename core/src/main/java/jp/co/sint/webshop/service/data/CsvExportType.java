package jp.co.sint.webshop.service.data;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.data.csv.AccessLogExportCondition;
import jp.co.sint.webshop.service.data.csv.ArrivalGoodsExportCondition;
import jp.co.sint.webshop.service.data.csv.BrandListExportCondition;
import jp.co.sint.webshop.service.data.csv.CCommodityDataExportCondition;
import jp.co.sint.webshop.service.data.csv.CCommodityDetailExportCondition;
import jp.co.sint.webshop.service.data.csv.CCommodityHeaderExportCondition;
import jp.co.sint.webshop.service.data.csv.CampaignCommodityExportCondition;
import jp.co.sint.webshop.service.data.csv.CampaignResearchExportCondition;
import jp.co.sint.webshop.service.data.csv.CategoryAttributeExportCondition;
import jp.co.sint.webshop.service.data.csv.CategoryAttributeValueDataExportCondition;
import jp.co.sint.webshop.service.data.csv.CategoryAttributeValueExportCondition;
import jp.co.sint.webshop.service.data.csv.CategoryCommodityExportCondition;
import jp.co.sint.webshop.service.data.csv.CategoryExportCondition;
import jp.co.sint.webshop.service.data.csv.CategoryListExportCondition;
import jp.co.sint.webshop.service.data.csv.CommodityAccessLogExportCondition;
import jp.co.sint.webshop.service.data.csv.CommodityDataExportCondition;
import jp.co.sint.webshop.service.data.csv.CommodityDetailExportCondition;
import jp.co.sint.webshop.service.data.csv.CommodityHeaderExportCondition;
import jp.co.sint.webshop.service.data.csv.CouponStatusAllExportCondition;
import jp.co.sint.webshop.service.data.csv.CustomerAddressExportCondition;
import jp.co.sint.webshop.service.data.csv.CustomerDMExportCondition;
import jp.co.sint.webshop.service.data.csv.CustomerExportCondition;
import jp.co.sint.webshop.service.data.csv.CustomerListExportCondition;
import jp.co.sint.webshop.service.data.csv.CustomerPreferenceExportCondition;
import jp.co.sint.webshop.service.data.csv.EnqueteSummaryChoicesExportCondition;
import jp.co.sint.webshop.service.data.csv.EnqueteSummaryInputExportCondition;
import jp.co.sint.webshop.service.data.csv.FmAnalysisExportCondition;
import jp.co.sint.webshop.service.data.csv.GiftCardLogExportCondition;
import jp.co.sint.webshop.service.data.csv.GiftCardRuleExportCondition;
import jp.co.sint.webshop.service.data.csv.GiftCardUseLogExportCondition;
import jp.co.sint.webshop.service.data.csv.GiftCommodityExportCondition;
import jp.co.sint.webshop.service.data.csv.GiftExportCondition;
import jp.co.sint.webshop.service.data.csv.HolidayExportCondition;
import jp.co.sint.webshop.service.data.csv.InquiryListExportCondition;
import jp.co.sint.webshop.service.data.csv.JdBrandListExportCondition;
import jp.co.sint.webshop.service.data.csv.JdCategoryListExportCondition;
import jp.co.sint.webshop.service.data.csv.JdOrderListExportCondition;
import jp.co.sint.webshop.service.data.csv.JdOrderListExportConditionTwo;
import jp.co.sint.webshop.service.data.csv.MailMagazineExportCondition;
import jp.co.sint.webshop.service.data.csv.NearCommodityCompareWarnCondition;
import jp.co.sint.webshop.service.data.csv.NearCommodityWarnCondition;
import jp.co.sint.webshop.service.data.csv.NewPublicCouponDetailsExportCondition;
import jp.co.sint.webshop.service.data.csv.NewPublicCouponExportCondition;
import jp.co.sint.webshop.service.data.csv.NoPicOrStopSellingCondition;
import jp.co.sint.webshop.service.data.csv.OrderListExportCondition;
import jp.co.sint.webshop.service.data.csv.OutputOrderExportCondition;
import jp.co.sint.webshop.service.data.csv.PointStatusAllExportCondition;
import jp.co.sint.webshop.service.data.csv.PointStatusShopExportCondition;
import jp.co.sint.webshop.service.data.csv.PrivateCouponListExportCondition;
import jp.co.sint.webshop.service.data.csv.RefererExportCondition;
import jp.co.sint.webshop.service.data.csv.RelatedCommodityAExportCondition;
import jp.co.sint.webshop.service.data.csv.RfAnalysisExportCondition;
import jp.co.sint.webshop.service.data.csv.RmAnalysisExportCondition;
import jp.co.sint.webshop.service.data.csv.SalesAmountByShopDailyExportCondition;
import jp.co.sint.webshop.service.data.csv.SalesAmountByShopMonthlyExportCondition;
import jp.co.sint.webshop.service.data.csv.SalesAmountExportCondition;
import jp.co.sint.webshop.service.data.csv.SalesAmountSkuExportCondition;
import jp.co.sint.webshop.service.data.csv.SearchKeywordLogExportCondition;
import jp.co.sint.webshop.service.data.csv.ShippingListExportCondition;
import jp.co.sint.webshop.service.data.csv.StockExportCondition;
import jp.co.sint.webshop.service.data.csv.TagCommodityExportCondition;
import jp.co.sint.webshop.service.data.csv.TagExportCondition;
import jp.co.sint.webshop.service.data.csv.TmallBrandListExportCondition;
import jp.co.sint.webshop.service.data.csv.TmallCategoryListExportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfAREcErpExportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfARJdErpExportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfARTmallErpExportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfCustErpExportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfItemErpExportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfItemWmsExportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfSOErpExportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfSOWmsExportCondition;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

/**
 * エクスポートCSVのコード定義を表す列挙クラスです。<br>
 * 
 * @author System Integrator Corp.
 */
public enum CsvExportType implements CodeAttribute {

  /** 「CSVタイプ」を表す値です。 */
  EXPORT_CSV_CUSTOMER("0", CustomerExportCondition.class, "顧客マスタ", CsvSchemaType.CUSTOMER, Permission.CUSTOMER_IO),

  EXPORT_CSV_CATEGORY("1", CategoryExportCondition.class, "カテゴリ", CsvSchemaType.CATEGORY, Permission.CATEGORY_DATA_IO),

  EXPORT_CSV_CATEGORY_ATTRIBUTE("2", CategoryAttributeExportCondition.class, "カテゴリ属性名称", CsvSchemaType.CATEGORY_ATTRIBUTE,
      Permission.CATEGORY_DATA_IO),

  EXPORT_CSV_CATEGORY_ATTRIBUTE_VALUE("3", CategoryAttributeValueExportCondition.class, "カテゴリ属性値",
      CsvSchemaType.CATEGORY_ATTRIBUTE_VALUE, Permission.COMMODITY_DATA_IO),

  EXPORT_CSV_COMMODITY_HEADER("4", CommodityHeaderExportCondition.class, "商品ヘッダ", CsvSchemaType.COMMODITY_HEADER,
      Permission.COMMODITY_DATA_IO),

  EXPORT_CSV_COMMODITY_DETAIL("5", CommodityDetailExportCondition.class, "商品明細", CsvSchemaType.COMMODITY_DETAIL,
      Permission.COMMODITY_DATA_IO),

  EXPORT_CSV_CATEGORY_COMMODITY("6", CategoryCommodityExportCondition.class, "カテゴリ陳列商品", CsvSchemaType.CATEGORY_COMMODITY,
      Permission.COMMODITY_DATA_IO),

  EXPORT_CSV_CAMPAIGN_COMMODITY("7", CampaignCommodityExportCondition.class, "商品キャンペーン", CsvSchemaType.CAMPAIGN_COMMODITY,
      Permission.COMMODITY_DATA_IO),

  EXPORT_CSV_TAG("8", TagExportCondition.class, "タグ", CsvSchemaType.TAG, Permission.COMMODITY_DATA_IO),

  EXPORT_CSV_TAG_COMMODITY("9", TagCommodityExportCondition.class, "商品タグ", CsvSchemaType.TAG_COMMODITY,
      Permission.COMMODITY_DATA_IO),

  EXPORT_CSV_RELATED_COMMODITY_A("10", RelatedCommodityAExportCondition.class, "手動関連付け商品", CsvSchemaType.RELATED_COMMODITY_A,
      Permission.COMMODITY_DATA_IO),

  EXPORT_CSV_GIFT("11", GiftExportCondition.class, "ギフト", CsvSchemaType.GIFT, Permission.COMMODITY_DATA_IO),

  EXPORT_CSV_GIFT_COMMODITY("12", GiftCommodityExportCondition.class, "ギフト対象商品", CsvSchemaType.GIFT_COMMODITY,
      Permission.COMMODITY_DATA_IO),

  EXPORT_CSV_STOCK("13", StockExportCondition.class, "在庫", CsvSchemaType.STOCK, Permission.STOCK_MANAGEMENT_DATA_IO),

  EXPORT_CSV_HOLIDAY_SITE("14", HolidayExportCondition.class, "休日", CsvSchemaType.HOLIDAY, Permission.SHOP_MANAGEMENT_IO_SITE),

  EXPORT_CSV_HOLIDAY_SHOP("15", HolidayExportCondition.class, "休日", CsvSchemaType.HOLIDAY, Permission.SHOP_MANAGEMENT_IO_SHOP),

  EXPORT_CSV_ARRIVAL_GOODS("16", ArrivalGoodsExportCondition.class, "入荷お知らせ", CsvSchemaType.ARRIVAL_GOODS,
      Permission.COMMODITY_DATA_IO),
  /** 売上集計を表す値です。 */
  EXPORT_SALES_AMOUNT("17", SalesAmountExportCondition.class, "売上集計", CsvSchemaType.SALES_AMOUNT, Permission.ANALYSIS_DATA_SITE),
  /** ショップ別売上集計-日別を表す値です */
  EXPORT_SALES_AMOUNT_BY_SHOP_DAILY("18", SalesAmountByShopDailyExportCondition.class, "ショップ別売上集計-日別",
      CsvSchemaType.SALES_AMOUNT_BY_SHOP_DAILY, Permission.ANALYSIS_DATA_SITE),
  /** ショップ別売上集計-月別を表す値です */
  EXPORT_SALES_AMOUNT_BY_SHOP_MONTHLY("19", SalesAmountByShopMonthlyExportCondition.class, "ショップ別売上集計-月別",
      CsvSchemaType.SALES_AMOUNT_BY_SHOP_MONTHLY, Permission.ANALYSIS_DATA_SITE),

  EXPORT_CSV_ORDER_LIST("20", OrderListExportCondition.class, "受注データ", CsvSchemaType.ORDER_LIST, Permission.ORDER_DATA_IO_SITE,
      Permission.ORDER_DATA_IO_SHOP),

  EXPORT_CSV_SHIPPING_LIST("21", ShippingListExportCondition.class, "出荷データ", CsvSchemaType.SHIPPING_LIST,
      Permission.ORDER_DATA_IO_SITE, Permission.ORDER_DATA_IO_SHOP),
  /** アクセスログ集計を表す値です。 */
  EXPORT_CSV_ACCESS_LOG("22", AccessLogExportCondition.class, "アクセスログ", CsvSchemaType.ACCESS_LOG, Permission.ANALYSIS_DATA_SITE),
  /** 商品データを表す値です */
  EXPORT_CSV_COMMODITY_DATA("23", CommodityDataExportCondition.class, "商品データ", CsvSchemaType.COMMODITY_DATA,
      Permission.COMMODITY_DATA_IO),
  /** 顧客アドレスを表す値です */
  EXPORT_CSV_CUSTOMER_ADDRESS("24", CustomerAddressExportCondition.class, "顧客アドレス", CsvSchemaType.CUSTOMER_ADDRESS,
      Permission.CUSTOMER_IO),
  /** アンケート分析-選択式を表す値です */
  EXPORT_CSV_ENQUETE_SUMMARY_CHOICE("25", EnqueteSummaryChoicesExportCondition.class, "アンケート分析選択式",
      CsvSchemaType.ENQUETE_SUMMARY_CHOICES, Permission.ENQUETE_DATA_IO_SITE),
  /** アンケート分析-自由回答を表す値です */
  EXPORT_CSV_ENQUETE_SUMMARY_INPUT("26", EnqueteSummaryInputExportCondition.class, "アンケート分析自由回答",
      CsvSchemaType.ENQUETE_SUMMARY_INPUT, Permission.ENQUETE_DATA_IO_SITE),
  /** ポイント使用状況-全件を表す値です */
  EXPORT_CSV_POINT_STATUS_ALL("27", PointStatusAllExportCondition.class, "ポイント使用状況全件", CsvSchemaType.POINT_STATUS_ALL,
      Permission.CUSTOMER_IO),
  /** ポイント使用状況-ショップ別を表す値です */
  EXPORT_CSV_POINT_STATUS_SHOP("28", PointStatusShopExportCondition.class, "ポイント使用状況ショップ別", CsvSchemaType.POINT_STATUS_SHOP,
      Permission.CUSTOMER_IO),
  /** 商品別アクセスログを表す値です */
  EXPORT_CSV_COMMODITY_ACCESS_LOG("29", CommodityAccessLogExportCondition.class, "商品別アクセスログ", CsvSchemaType.COMMODITY_ACCESS_LOG,
      Permission.ANALYSIS_DATA_SITE, Permission.ANALYSIS_DATA_SHOP),
  /** 顧客嗜好分析を表す値です */
  EXPORT_CSV_CUSTOMER_PREFERENCE("30", CustomerPreferenceExportCondition.class, "顧客嗜好分析", CsvSchemaType.CUSTOMER_PREFERENCE,
      Permission.ANALYSIS_DATA_SITE),
  /** リファラーを表す値です */
  EXPORT_CSV_REFERER("31", RefererExportCondition.class, "リファラー", CsvSchemaType.REFERER, Permission.ANALYSIS_DATA_SITE),
  /** RF分析を表す値です */
  EXPORT_CSV_RF_ANALYSIS("32", RfAnalysisExportCondition.class, "RF分析", CsvSchemaType.RF_ANALYSIS, Permission.ANALYSIS_DATA_SITE),
  /** RM分析を表す値です */
  EXPORT_CSV_RM_ANALYSIS("33", RmAnalysisExportCondition.class, "RM分析", CsvSchemaType.RM_ANALYSIS, Permission.ANALYSIS_DATA_SITE),
  /** FM分析を表す値です */
  EXPORT_CSV_FM_ANALYSIS("34", FmAnalysisExportCondition.class, "FM分析", CsvSchemaType.FM_ANALYSIS, Permission.ANALYSIS_DATA_SITE),
  /** 商品別売上集計を表す値です。 */
  EXPORT_CSV_SALES_AMOUNT_SKU("35", SalesAmountSkuExportCondition.class, "商品別売上集計", CsvSchemaType.SALES_AMOUNT_SKU,
      Permission.ANALYSIS_DATA_SITE),
  /** 検索キーワードログ集計を表す値です。 */
  EXPORT_CSV_SEARCH_KEYWORD_LOG("36", SearchKeywordLogExportCondition.class, "検索キーワード集計", CsvSchemaType.SEARCH_KEYWORD_LOG,
      Permission.ANALYSIS_DATA_SITE),
  /** キャンペーン分析を表す値です。 */
  EXPORT_CSV_CAMPAIGN_RESEARCH("37", CampaignResearchExportCondition.class, "キャンペーン分析", CsvSchemaType.CAMPAIGN_RESEARCH,
      Permission.ANALYSIS_DATA_SITE),
  /** メールマガジンを表す値です。 */
  EXPORT_CSV_MAIL_MAGAZINE("38", MailMagazineExportCondition.class, "メールマガジン", CsvSchemaType.MAIL_MAGAZINE,
      Permission.MAIL_MAGAZINE_IO_SITE),
  /** 顧客マスタを表す値です。 */
  EXPORT_CSV_CUSTOMER_LIST("39", CustomerListExportCondition.class, "顧客マスタ", CsvSchemaType.CUSTOMER_LIST, Permission.CUSTOMER_IO),
  /** DM送付先を表す値です。 */
  EXPORT_CSV_CUSTOMER_DM("40", CustomerDMExportCondition.class, "DM送付先", CsvSchemaType.CUSTOMER_DM, Permission.CUSTOMER_IO),
  /** 受注データを表す値です。 */
  EXPORT_CSV_OUTPUT_ORDER("41", OutputOrderExportCondition.class, "受注データ", CsvSchemaType.OUTPUT_ORDER,
      Permission.ORDER_DATA_IO_SHOP),

  EXPORT_CSV_COUPON_STATUS_ALL("42", CouponStatusAllExportCondition.class, "クーポン使用状況全件", CsvSchemaType.COUPON_STATUS_ALL,
      Permission.CUSTOMER_COUPON_IO),

  EXPORT_CSV_INQUIRY("44", InquiryListExportCondition.class, "咨询", CsvSchemaType.INQUIRY, Permission.SERVICE_COMPLAINT_DATA_EXPORT),

  // soukai add ob 2011/12/15 start
  EXPORT_CSV_PRIVATE_COUPON_ANALYSIS("45", PrivateCouponListExportCondition.class, "顾客别优惠券发行分析",
      CsvSchemaType.PRIVATE_COUPON_ANALYSIS, Permission.ANALYSIS_DATA_SHOP),
  // soukai add ob 2011/12/14 end

  // 2012-01-04 yyq add start desc:商品数据导出到ERP
  EXPORT_CSV_ITEM_MASTERDATA_ERP("46", ZsIfItemErpExportCondition.class, "商品数据导出到ERP", CsvSchemaType.ITEM_MASTERDATA_ERP,
      Permission.COMMODITY_DATA_IO),
  // 2012-01-04 yyq add end desc:商品数据导出到ERP
  // soukai add wjh 2011/12/30 start
  /** 商品データを表す値です */
  EXPORT_CSV_COMMODITY_HEAD_DATA("47", CCommodityHeaderExportCondition.class, "商品ヘッダ", CsvSchemaType.CCOMMODITY_HEADER,
      Permission.COMMODITY_DATA_IO), EXPORT_CSV_COMMODITY_DETAIL_DATA("48", CCommodityDetailExportCondition.class, "商品明細",
      CsvSchemaType.CCOMMODITY_DETAIL, Permission.COMMODITY_DATA_IO), EXPORT_CSV_COMMODITY_DATA_DATA("49",
      CCommodityDataExportCondition.class, "商品データ", CsvSchemaType.CCOMMODITY_DATA, Permission.COMMODITY_DATA_IO),
  // soukai add wjh 2011/12/30 end
  // 2012-01-06 yyq add start desc:发货指示导出到ERP
  EXPORT_CSV_SO_DATA_ERP("50", ZsIfSOErpExportCondition.class, "发货指示导出到ERP", CsvSchemaType.SO_DATA_ERP,
      Permission.ORDER_DATA_IO_SHOP),
  // 2012-01-06 yyq add end desc:发货指示导出到ERP
  // 2012-01-06 yyq add start desc:发货指示导出到ERP
  EXPORT_CSV_SO_DATA_WMS("51", ZsIfSOWmsExportCondition.class, "发货指示导出到WMS", CsvSchemaType.SO_DATA_WMS,
          Permission.ORDER_DATA_IO_SHOP),
  // 2012-01-06 yyq add end desc:发货指示导出到ERP
  // 20120119 os013 add start desc:库存设定导出
  // EXPORT_CSV_STOCK_DATA("51", CCommodityStockExportCondition.class, "库存设定导出",
  // CsvSchemaType.CCOMMODITY_STOCK,
  // Permission.STOCK_MANAGEMENT_READ),
  // 20120119 os013 add end
  // 2012-01-30 yyq add start desc:会员数据导出到ERP
  EXPORT_CSV_CUST_MASTERDATA_ERP("52", ZsIfCustErpExportCondition.class, "会员数据导出到ERP", CsvSchemaType.CUST_MASTERDATA_ERP,
      Permission.COMMODITY_DATA_IO),
  // 2012-01-30 yyq add end desc:会员数据导出到ERP
  // 2012-01-30 yyq add start desc:EC入金数据导出到ERP
  EXPORT_CSV_AR_ECDATA_ERP("53", ZsIfAREcErpExportCondition.class, "EC入金数据导出到ERP", CsvSchemaType.AR_ECDATA_ERP,
      Permission.COMMODITY_DATA_IO),
  // 2012-01-30 yyq add end desc:EC入金数据导出到ERP
  // 2012-01-31 yyq add start desc:商品数据导出到WMS
  EXPORT_CSV_ITEM_MASTERDATA_WMS("54", ZsIfItemWmsExportCondition.class, "商品数据导出到WMS", CsvSchemaType.ITEM_MASTERDATA_WMS,
      Permission.COMMODITY_DATA_IO),
  // 2012-01-31 yyq add end desc:商品数据导出到WMS
  // 20120206 os013 add start
  EXPORT_CSV_CATEGORY_ATTRIBUTE_VALUE_DATA("55", CategoryAttributeValueDataExportCondition.class, "商品分类关联设置导出",
      CsvSchemaType.CATEGORY_ATTRIBUTE_VALUE_DATA, Permission.COMMODITY_DATA_IO),
  // 20120206 os013 add end
  // 2012-02-09 yyq add start desc:EC入金数据导出到ERP
  EXPORT_CSV_AR_TMALLDATA_ERP("56", ZsIfARTmallErpExportCondition.class, "TMALL入金数据导出到ERP", CsvSchemaType.AR_TMALLDATA_ERP,
      Permission.COMMODITY_DATA_IO),
  // 2012-02-09 yyq add end desc:EC入金数据导出到ERP
  //add by lc 2012-04-13 start
  /** 淘宝商品品牌表。 */
  EXPORT_CSV_TMALL_BRAND_LIST("57", TmallBrandListExportCondition.class, "淘宝商品品牌", CsvSchemaType.TMALL_BRAND_LIST, Permission.CUSTOMER_IO),
  /** 商品品牌表。 */
  EXPORT_CSV_BRAND_LIST("58", BrandListExportCondition.class, "商品品牌", CsvSchemaType.BRAND_LIST, Permission.CUSTOMER_IO),
  /** 淘宝商品类目表。 */
  EXPORT_CSV_TMALL_CATEGORY_LIST("59", TmallCategoryListExportCondition.class, "淘宝商品类目", CsvSchemaType.TMALL_CATEGORY_LIST, Permission.CUSTOMER_IO),
  /** EC商品类目表。 */
  EXPORT_CSV_CATEGORY_LIST("60", CategoryListExportCondition.class, "EC商品类目", CsvSchemaType.CATEGORY_LIST, Permission.CUSTOMER_IO),
  /** ショップ別売上集計-日別を表す値です */
  EXPORT_CSV_NEW_PUBLIC_COUPON("61", NewPublicCouponExportCondition.class, "ショップ別売上集計-日別",
      CsvSchemaType.NEW_PUBLIC_COUPON, Permission.ANALYSIS_DATA_SITE),
  EXPORT_CSV_NEW_PUBLIC_COUPON_DETAILS("62", NewPublicCouponDetailsExportCondition.class, "ショップ別売上集計-日別",
      CsvSchemaType.NEW_PUBLIC_COUPON_DETAILS, Permission.ANALYSIS_DATA_SITE),

  //add by lc 2012-04-13 end
  //add by yyq 2013-09-02 start
  EXPORT_CSV_NO_PIC_OR_STOP_SELLING("63", NoPicOrStopSellingCondition.class, "无图片或停止销售商品列表",
      CsvSchemaType.NO_PIC_OR_STOP_SELLING, Permission.COMMODITY_DATA_IO),
  //add by yyq 2013-09-02 end
  // 20131105 wz add start
  /** 礼品卡抽出。 */
  EXPORT_CSV_GIFT_CART_RULE("64", GiftCardRuleExportCondition.class, "礼品卡抽出", 
      CsvSchemaType.GIFT_CARD_RULE, Permission.GIFT_CARD_RULE_IO),
  /** 礼品卡发行统计导出。 */    
  EXPORT_CSV_GIFT_CART_LOG("65", GiftCardLogExportCondition.class, "礼品卡发行统计导出", 
      CsvSchemaType.GIFT_CARD_LOG, Permission.GIFT_CARD_LOG_IO),
  EXPORT_CSV_GIFT_CART_USE_LOG("66", GiftCardUseLogExportCondition.class, "礼品卡使用明细导出", 
      CsvSchemaType.GIFT_CARD_USE_LOG, Permission.GIFT_CARD_USE_LOG_IO),
  // 20140227 txw add start desc:临期商品邮件发送
  EXPORT_CSV_NEAR_COMMODITY_WARN("67", NearCommodityWarnCondition.class, "临期商品列表",
      CsvSchemaType.NEAR_COMMODITY_WARN, Permission.COMMODITY_DATA_IO),
  EXPORT_CSV_NEAR_COMMODITY_COMPARE_WARN("68", NearCommodityCompareWarnCondition.class, "临期商品差异列表",
      CsvSchemaType.NEAR_COMMODITY_COMPARE_WARN, Permission.COMMODITY_DATA_IO),
  // 20140227 txw add end desc:临期商品邮件发送
  
  // 2014/04/25 京东WBS对应 ob_卢 add start
  /** 京东商品品牌表。 */
  EXPORT_CSV_JD_BRAND_LIST("69", JdBrandListExportCondition.class, "京东商品品牌", CsvSchemaType.JD_BRAND_LIST, Permission.CUSTOMER_IO),
  /** 京东商品类目表。 */
  EXPORT_CSV_JD_CATEGORY_LIST("70", JdCategoryListExportCondition.class, "京东商品类目", CsvSchemaType.JD_CATEGORY_LIST, Permission.CUSTOMER_IO),
  
  // 2014/04/25 京东WBS对应 ob_卢 add end
  // 20131105 wz add end
  
  // 2014-05-15 hdh add end desc:京东入金数据导出到ERP
  EXPORT_CSV_AR_JDDATA_ERP("71", ZsIfARJdErpExportCondition.class, "京东入金数据导出到ERP", CsvSchemaType.AR_JDDATA_ERP,
      Permission.COMMODITY_DATA_IO),
  // 2014-05-15 hdh add end desc:京东入金数据导出到ERP
  // 2014-12-04 zzy add start
  EXPORT_CSV_JD_ISP("72", JdOrderListExportCondition.class, "京东商品品牌导出", CsvSchemaType.JD_ORDER_LIST, Permission.CUSTOMER_IO),
  EXPORT_CSV_JD_ISP_TWO("73", JdOrderListExportConditionTwo.class, "京东商品品牌导出", CsvSchemaType.JD_ORDER_LIST_TWO, Permission.CUSTOMER_IO);
    // 2014-12-04 zzy add end
	 //add by lc 2012-04-13 start
  private String code;

  private String typeName;

  private String name;

  private Permission[] permissions;

  private Class<? extends CsvExportCondition<?>> conditionType;

  private CsvSchemaType schemaType;

  private CsvExportType(String code, Class<? extends CsvExportCondition<?>> conditionType, String name, CsvSchemaType schemaType,
      Permission... permissions) {
    this.code = code;
    this.conditionType = conditionType;
    this.name = name;
    this.permissions = ArrayUtil.immutableCopy(permissions);
    this.schemaType = schemaType;
  }

  public static CsvExportType[] getCsvExportTypeSet(OperatingMode mode) {
    switch (mode) {
      case MALL:
        return ArrayUtil.immutableCopy(CSV_EXPORT_TYPE_MALL);
      case SHOP:
        return ArrayUtil.immutableCopy(CSV_EXPORT_TYPE_SHOP);
      case ONE:
        return ArrayUtil.immutableCopy(CSV_EXPORT_TYPE_ONE);
      default:
        return new CsvExportType[0];
    }
  }

  /** モール一括決済のときにCSV出力可能なマスタ */
  private static final CsvExportType[] CSV_EXPORT_TYPE_MALL = {
      EXPORT_CSV_CUSTOMER, EXPORT_CSV_CATEGORY, EXPORT_CSV_CATEGORY_ATTRIBUTE, EXPORT_CSV_CATEGORY_ATTRIBUTE_VALUE,
      EXPORT_CSV_COMMODITY_HEADER, EXPORT_CSV_COMMODITY_DETAIL, EXPORT_CSV_CATEGORY_COMMODITY, EXPORT_CSV_CAMPAIGN_COMMODITY,
      EXPORT_CSV_TAG, EXPORT_CSV_TAG_COMMODITY, EXPORT_CSV_RELATED_COMMODITY_A, EXPORT_CSV_GIFT, EXPORT_CSV_GIFT_COMMODITY,
      EXPORT_CSV_STOCK, EXPORT_CSV_HOLIDAY_SHOP, EXPORT_CSV_CUSTOMER_ADDRESS
  };

  /** ショップ個別決済のときにCSV出力可能なマスタ */
  private static final CsvExportType[] CSV_EXPORT_TYPE_SHOP = {
      EXPORT_CSV_CUSTOMER, EXPORT_CSV_CATEGORY, EXPORT_CSV_CATEGORY_ATTRIBUTE, EXPORT_CSV_CATEGORY_ATTRIBUTE_VALUE,
      EXPORT_CSV_COMMODITY_HEADER, EXPORT_CSV_COMMODITY_DETAIL, EXPORT_CSV_CATEGORY_COMMODITY, EXPORT_CSV_CAMPAIGN_COMMODITY,
      EXPORT_CSV_TAG, EXPORT_CSV_TAG_COMMODITY, EXPORT_CSV_RELATED_COMMODITY_A, EXPORT_CSV_GIFT, EXPORT_CSV_GIFT_COMMODITY,
      EXPORT_CSV_STOCK, EXPORT_CSV_HOLIDAY_SHOP, EXPORT_CSV_CUSTOMER_ADDRESS
  };

  /** 一店舗のときにCSV出力可能なマスタ */
  private static final CsvExportType[] CSV_EXPORT_TYPE_ONE = {
      EXPORT_CSV_CUSTOMER, EXPORT_CSV_CATEGORY, EXPORT_CSV_CATEGORY_ATTRIBUTE, EXPORT_CSV_CATEGORY_ATTRIBUTE_VALUE,
      EXPORT_CSV_COMMODITY_HEADER, EXPORT_CSV_COMMODITY_DETAIL, EXPORT_CSV_CATEGORY_COMMODITY, EXPORT_CSV_CAMPAIGN_COMMODITY,
      EXPORT_CSV_TAG, EXPORT_CSV_TAG_COMMODITY, EXPORT_CSV_RELATED_COMMODITY_A, EXPORT_CSV_GIFT, EXPORT_CSV_GIFT_COMMODITY,
      EXPORT_CSV_STOCK, EXPORT_CSV_HOLIDAY_SITE, EXPORT_CSV_CUSTOMER_ADDRESS
  };

  /**
   * コード名称を返します。
   * 
   * @return コード名称
   */
  public String getName() {
    // add by V10-CH 170 start
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
    // add by V10-CH 170 end
  }

  /**
   * コード値を返します。
   * 
   * @return コード値
   */
  public String getValue() {
    return code;
  }

  /**
   * @return the permissions
   */
  public Permission[] getPermissions() {
    return ArrayUtil.immutableCopy(permissions);
  }

  /**
   * 指定されたコード名を持つファイル種別を返します。
   * 
   * @param name
   *          コード名
   * @return ファイル種別
   */
  public static CsvExportType fromName(String name) {
    for (CsvExportType p : CsvExportType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つファイル種別を返します。
   * 
   * @param code
   *          コード値
   * @return ファイル種別
   */
  public static CsvExportType fromValue(String code) {
    for (CsvExportType p : CsvExportType.values()) {
      if (p.getValue().equals(code)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値が有効かどうかを返します。
   * 
   * @param code
   *          コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(String code) {
    if (StringUtil.hasValue(code)) {
      for (CsvExportType p : CsvExportType.values()) {
        if (p.getValue().equals(code)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * codeを取得します。
   * 
   * @return code
   */
  public String getCode() {
    return code;
  }

  public String getTypeName() {
    return typeName;
  }

  /**
   * schemaTypeを取得します。
   * 
   * @return schemaType
   */

  public CsvSchemaType getSchemaType() {
    return schemaType;
  }

  @SuppressWarnings("unchecked")
  public <T extends CsvExportCondition>T createConditionInstance() {
    Logger logger = Logger.getLogger(CsvExportType.class);
    T instance = null;
    try {
      instance = (T) conditionType.newInstance();
      instance.setSchema(DIContainer.getCsvSchema(this.getSchemaType()));
    } catch (InstantiationException e) {
      logger.error(e.getMessage());
    } catch (IllegalAccessException e) {
      logger.error(e.getMessage());
    }
    return (T) instance;
  }

}
