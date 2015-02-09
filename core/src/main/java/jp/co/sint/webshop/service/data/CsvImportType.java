package jp.co.sint.webshop.service.data;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.data.csv.CCommodityDataImportCondition;
import jp.co.sint.webshop.service.data.csv.CCommodityDetailImportCondition;
import jp.co.sint.webshop.service.data.csv.CCommodityHeaderImportCondition;
import jp.co.sint.webshop.service.data.csv.CCommodityInitialStageImportCondition;
import jp.co.sint.webshop.service.data.csv.CampaignCommodityImportCondition;
import jp.co.sint.webshop.service.data.csv.CategoryAttributeImportCondition;
import jp.co.sint.webshop.service.data.csv.CategoryAttributeValueDataImportCondition;
import jp.co.sint.webshop.service.data.csv.CategoryAttributeValueImportCondition;
import jp.co.sint.webshop.service.data.csv.CategoryCommodityImportCondition;
import jp.co.sint.webshop.service.data.csv.CategoryImportCondition;
import jp.co.sint.webshop.service.data.csv.CommodityDetailImportCondition;
import jp.co.sint.webshop.service.data.csv.CommodityGiftImportCondition;
import jp.co.sint.webshop.service.data.csv.CommodityHeaderImportCondition;
import jp.co.sint.webshop.service.data.csv.CommodityProductionDateImportCondition;
import jp.co.sint.webshop.service.data.csv.CustomerAddressImportCondition;
import jp.co.sint.webshop.service.data.csv.CustomerImportCondition;
import jp.co.sint.webshop.service.data.csv.DeliverySlipNoImportCondition;
import jp.co.sint.webshop.service.data.csv.GiftCommodityImportCondition;
import jp.co.sint.webshop.service.data.csv.GiftImportCondition;
import jp.co.sint.webshop.service.data.csv.HolidayImportCondition;
import jp.co.sint.webshop.service.data.csv.NewDeliverySlipNoImportCondition;
import jp.co.sint.webshop.service.data.csv.PaymentInputImportCondition;
import jp.co.sint.webshop.service.data.csv.PrivateCouponImportCondition;
import jp.co.sint.webshop.service.data.csv.PublicCouponImportCondition;
import jp.co.sint.webshop.service.data.csv.RelatedCommodityAImportCondition;
import jp.co.sint.webshop.service.data.csv.ShippingReportImportCondition;
import jp.co.sint.webshop.service.data.csv.StockIODetailImportCondition;
import jp.co.sint.webshop.service.data.csv.TagCommodityImportCondition;
import jp.co.sint.webshop.service.data.csv.TagImportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfInvErpImportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfInvWmsImportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfPoWmsImportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfSOShipErpImportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfSOShipWmsImportCondition;
import jp.co.sint.webshop.service.data.csv.ZsIfUnpWmsImportCondition;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

/**
 * インポートCSVのコード定義を表す列挙クラスです。<br>
 * 
 * @author System Integrator Corp.
 */
public enum CsvImportType implements CodeAttribute {

  /** 「CSVタイプ」を表す値です。 */
  IMPORT_CSV_CUSTOMER("import-customer", "顧客マスタ", CustomerImportCondition.class, CsvSchemaType.CUSTOMER, Permission.CUSTOMER_IO),

  IMPORT_CSV_CATEGORY("import-category", "カテゴリ", CategoryImportCondition.class, CsvSchemaType.CATEGORY, Permission.CATEGORY_DATA_IO),

  IMPORT_CSV_CATEGORY_ATTRIBUTE("import-category_attribute", "カテゴリ属性名称", CategoryAttributeImportCondition.class,
      CsvSchemaType.CATEGORY_ATTRIBUTE, Permission.CATEGORY_DATA_IO),

  IMPORT_CSV_CATEGORY_ATTRIBUTE_VALUE("import-category_attribute_value", "カテゴリ属性値", CategoryAttributeValueImportCondition.class,
      CsvSchemaType.CATEGORY_ATTRIBUTE_VALUE, Permission.COMMODITY_DATA_IO),

  IMPORT_CSV_COMMODITY_HEADER("import-commodity_header", "商品ヘッダ", CommodityHeaderImportCondition.class,
      CsvSchemaType.COMMODITY_HEADER, Permission.COMMODITY_DATA_IO),

  IMPORT_CSV_COMMODITY_DETAIL("import-commodity_detail", "商品明細", CommodityDetailImportCondition.class,
      CsvSchemaType.COMMODITY_DETAIL, Permission.COMMODITY_DATA_IO),

  IMPORT_CSV_CATEGORY_COMMODITY("import-category_commodity", "カテゴリ陳列商品", CategoryCommodityImportCondition.class,
      CsvSchemaType.CATEGORY_COMMODITY, Permission.COMMODITY_DATA_IO),

  IMPORT_CSV_CAMPAIGN_COMMODITY("import-campaign_commodity", "商品キャンペーン", CampaignCommodityImportCondition.class,
      CsvSchemaType.CAMPAIGN_COMMODITY, Permission.COMMODITY_DATA_IO),

  IMPORT_CSV_TAG("import-tag", "タグ", TagImportCondition.class, CsvSchemaType.TAG, Permission.COMMODITY_DATA_IO),

  IMPORT_CSV_TAG_COMMODITY("import-tag_commodity", "商品タグ", TagCommodityImportCondition.class, CsvSchemaType.TAG_COMMODITY,
      Permission.COMMODITY_DATA_IO),

  IMPORT_CSV_RELATED_COMMODITY_A("import-related_commodity_a", "手動関連付け商品", RelatedCommodityAImportCondition.class,
      CsvSchemaType.RELATED_COMMODITY_A, Permission.COMMODITY_DATA_IO),

  IMPORT_CSV_GIFT("import-gift", "ギフト", GiftImportCondition.class, CsvSchemaType.GIFT, Permission.COMMODITY_DATA_IO),

  IMPORT_CSV_GIFT_COMMODITY("import-gift_commodity", "ギフト対象商品", GiftCommodityImportCondition.class, CsvSchemaType.GIFT_COMMODITY,
      Permission.COMMODITY_DATA_IO),

  IMPORT_CSV_STOCK_IO_DETAIL("import-stock_io_detail", "入出庫管理", StockIODetailImportCondition.class, CsvSchemaType.STOCK_IO_DETAIL,
      Permission.STOCK_MANAGEMENT_DATA_IO),

  IMPORT_CSV_SHIPPING_REPORT("import-shipping_report", "出荷実績", ShippingReportImportCondition.class, CsvSchemaType.SHIPPING_REPORT,
      Permission.SHIPPING_DATA_IO_SITE, Permission.SHIPPING_DATA_IO_SHOP),

  IMPORT_CSV_PAYMENT_INPUT("import-payment_input", "入金情報", PaymentInputImportCondition.class, CsvSchemaType.PAYMENT_INPUT),

  IMPORT_CSV_HOLIDAY_SHOP("import-holiday_shop", "休日", HolidayImportCondition.class, CsvSchemaType.HOLIDAY,
      Permission.SHOP_MANAGEMENT_IO_SHOP),

  IMPORT_CSV_HOLIDAY_SITE("import-holiday_site", "休日", HolidayImportCondition.class, CsvSchemaType.HOLIDAY,
      Permission.SHOP_MANAGEMENT_IO_SITE),

  IMPORT_CSV_CUSTOMER_ADDRESS("import-customer_address", "顧客アドレス", CustomerAddressImportCondition.class,
      CsvSchemaType.CUSTOMER_ADDRESS, Permission.CUSTOMER_IO),
  // soukai add ob 2011/12/20 start
  IMPORT_CSV_PRIVATE_COUPON("import-private_coupon", "优惠券履历导入", PrivateCouponImportCondition.class, CsvSchemaType.PRIVATE_COUPON,
      Permission.PUBLIC_COUPON_DATA_IO),
  IMPORT_CSV_PUBLIC_COUPON("import-public_coupon", "媒体优惠券履历导入", PublicCouponImportCondition.class, CsvSchemaType.PUBLIC_COUPON,
          Permission.PUBLIC_COUPON_DATA_IO),
  // soukai add ob 2011/12/20 end
  IMPORT_CSV_DELIVERY_SLIP_NO("delivery_slip_no", "宅配便伝票", DeliverySlipNoImportCondition.class, CsvSchemaType.DELIVERY_SLIP_NO,
      Permission.ORDER_DATA_IO_SITE, Permission.ORDER_DATA_IO_SHOP),

  IMPORT_CSV_C_COMMODITY_HEADER("import-c_commodity_header", "商品ヘッダ", CCommodityHeaderImportCondition.class,
      CsvSchemaType.CCOMMODITY_HEADER, Permission.COMMODITY_DATA_IO),

  IMPORT_CSV_C_COMMODITY_DETAIL("import-c_commodity_detail", "商品明細", CCommodityDetailImportCondition.class,
      CsvSchemaType.CCOMMODITY_DETAIL, Permission.COMMODITY_DATA_IO),

  IMPORT_CSV_C_COMMODITY_DATA("import-c_commodity_data", "商品データ", CCommodityDataImportCondition.class,
      CsvSchemaType.CCOMMODITY_DATA, Permission.COMMODITY_DATA_IO),
  // 20120209 os013 add start
  IMPORT_CSV_CATEGORY_ATTRIBUTE_VALUE_DATA("import-category_attribute_value_data", "商品分类设定", CategoryAttributeValueDataImportCondition.class,
      CsvSchemaType.CATEGORY_ATTRIBUTE_VALUE_DATA, Permission.COMMODITY_DATA_IO),  
  IMPORT_CSV_C_COMMODITY_INITIAL_STAGE("import-c_commodity_initial_stage", "商品初期导入", CCommodityInitialStageImportCondition.class,
      CsvSchemaType.C_COMMODITY_INITIAL_STAGE, Permission.COMMODITY_DATA_IO), 
  //20120209 os013 add end
  // 2012-01-07 yyq add start desc:有效库存数
  IMPORT_CSV_INV_DATA_ERP("import-inv_data_erp", "有效库存", ZsIfInvErpImportCondition.class, CsvSchemaType.INV_DATA_ERP,
      Permission.STOCK_MANAGEMENT_DATA_IO),
  // 2012-01-07 yyq add end desc:有效库存数
  // 2012-01-13 yyq add start desc:发货实际ERP
  IMPORT_CSV_SOSHIP_DATA_ERP("import-soship_data_erp", "发货实际", ZsIfSOShipErpImportCondition.class, CsvSchemaType.SOSHIP_DATA_ERP,
      Permission.SHIPPING_DATA_IO_SHOP),
  // 2012-01-13 yyq add end desc:发货实际ERP
      
  // 2012-01-13 yyq add start desc:发货实际WMS
  IMPORT_CSV_SOSHIP_DATA_WMS("import-soship_data_wms", "发货实际", ZsIfSOShipWmsImportCondition.class, CsvSchemaType.SOSHIP_DATA_WMS,
          Permission.SHIPPING_DATA_IO_SHOP),
  // 2012-01-13 yyq add end desc:发货实际WMS
          
  // 2012-01-07 yyq add start desc:有效库存数
  IMPORT_CSV_INV_DATA_WMS("import-inv_data_wms", "库存增量", ZsIfInvWmsImportCondition.class, CsvSchemaType.INV_DATA_WMS,
      Permission.STOCK_MANAGEMENT_DATA_IO),
  // 2012-01-07 yyq add end desc:有效库存数
  // 2012-02-02 OS011 add start desc:盘点库存增量
  IMPORT_CSV_UNP_DATA_WMS("import-unp_data_wms", "盘点库存增量", ZsIfUnpWmsImportCondition.class, CsvSchemaType.INV_DATA_WMS_UNP,
      Permission.STOCK_MANAGEMENT_DATA_IO),
  // 2012-02-02 OS011 add end desc:盘点库存增量
  // 2012-02-21 OS011 add start desc:入库完了
  IMPORT_CSV_PO_DATA_WMS("import-po_data_wms", "盘点库存增量", ZsIfPoWmsImportCondition.class, CsvSchemaType.INV_DATA_WMS_PO,
      Permission.STOCK_MANAGEMENT_DATA_IO),
  // 2012-02-21 OS011 add end desc:入库完了
  // 2014-02-26 txw add start desc:商品生产日期入库
  IMPORT_CSV_COMMODITY_PRODUCTION_DATE("import-commodity-producton-date", "商品生产日期", CommodityProductionDateImportCondition.class, CsvSchemaType.COMMODITY_PRODUCTION_DATE,
      Permission.COMMODITY_DATA_IO),
  // 2014-02-26 txw add end desc:商品生产日期入库
  // 2012-11-19 yyq add start desc : 赠品初期导入
  IMPORT_CSV_COMMODITY_GIFT("import-c_commodity_gift", "赠品初期导入", CommodityGiftImportCondition.class, CsvSchemaType.COMMODITY_GIFT,
      Permission.COMMODITY_DATA_IO),
  // 2012-11-19 yyq add end desc : 赠品初期导入 
  
  // 2014-10-22 hdh add start
  IMPORT_CSV_DELIVERY_TYPE_NO("import-delivery-type-no", "运单号导入", NewDeliverySlipNoImportCondition.class, CsvSchemaType.DELIVERY_TYPE_NO,
          Permission.SHIPPING_DATA_IO_SHOP);
  // 2014-10-22 hdh add end 
  
  private String code;

  private String name;

  private Class<? extends CsvImportCondition<?>> conditionType;

  private CsvSchemaType schemaType;

  private Permission[] permissions;

  private CsvImportType(String code, String name, Class<? extends CsvImportCondition<?>> conditionType, CsvSchemaType schemaType,
      Permission... permissions) {
    this.code = code;
    this.name = name;
    this.conditionType = conditionType;
    this.permissions = ArrayUtil.immutableCopy(permissions);
    this.schemaType = schemaType;
  }

  public static CsvImportType[] getCsvImportTypeSet(OperatingMode mode) {
    switch (mode) {
      case MALL:
        return ArrayUtil.immutableCopy(CSV_IMPORT_TYPE_MALL);
      case SHOP:
        return ArrayUtil.immutableCopy(CSV_IMPORT_TYPE_SHOP);
      case ONE:
        return ArrayUtil.immutableCopy(CSV_IMPORT_TYPE_ONE);
      default:
        return new CsvImportType[0];
    }
  }

  /** モール一括決済のときにCSV取り込み可能なマスタ */
  private static final CsvImportType[] CSV_IMPORT_TYPE_MALL = new CsvImportType[] {
      IMPORT_CSV_CUSTOMER, IMPORT_CSV_CATEGORY, IMPORT_CSV_CATEGORY_ATTRIBUTE, IMPORT_CSV_CATEGORY_ATTRIBUTE_VALUE,
      IMPORT_CSV_COMMODITY_HEADER, IMPORT_CSV_COMMODITY_DETAIL, IMPORT_CSV_CATEGORY_COMMODITY, IMPORT_CSV_CAMPAIGN_COMMODITY,
      IMPORT_CSV_TAG, IMPORT_CSV_TAG_COMMODITY, IMPORT_CSV_RELATED_COMMODITY_A, IMPORT_CSV_GIFT, IMPORT_CSV_GIFT_COMMODITY,
      IMPORT_CSV_STOCK_IO_DETAIL, IMPORT_CSV_SHIPPING_REPORT, IMPORT_CSV_HOLIDAY_SHOP, IMPORT_CSV_CUSTOMER_ADDRESS
      ,IMPORT_CSV_DELIVERY_TYPE_NO
  };

  /** ショップ個別決済のときにCSV取り込み可能なマスタ */
  private static final CsvImportType[] CSV_IMPORT_TYPE_SHOP = new CsvImportType[] {
      IMPORT_CSV_CUSTOMER, IMPORT_CSV_CATEGORY, IMPORT_CSV_CATEGORY_ATTRIBUTE, IMPORT_CSV_CATEGORY_ATTRIBUTE_VALUE,
      IMPORT_CSV_COMMODITY_HEADER, IMPORT_CSV_COMMODITY_DETAIL, IMPORT_CSV_CATEGORY_COMMODITY, IMPORT_CSV_CAMPAIGN_COMMODITY,
      IMPORT_CSV_TAG, IMPORT_CSV_TAG_COMMODITY, IMPORT_CSV_RELATED_COMMODITY_A, IMPORT_CSV_GIFT, IMPORT_CSV_GIFT_COMMODITY,
      IMPORT_CSV_STOCK_IO_DETAIL, IMPORT_CSV_SHIPPING_REPORT, IMPORT_CSV_HOLIDAY_SHOP, IMPORT_CSV_CUSTOMER_ADDRESS
      ,IMPORT_CSV_DELIVERY_TYPE_NO
  };

  /** 一店舗のときにCSV取り込み可能なマスタ */
  private static final CsvImportType[] CSV_IMPORT_TYPE_ONE = new CsvImportType[] {
      IMPORT_CSV_CUSTOMER, IMPORT_CSV_CATEGORY, IMPORT_CSV_CATEGORY_ATTRIBUTE, IMPORT_CSV_CATEGORY_ATTRIBUTE_VALUE,
      IMPORT_CSV_COMMODITY_HEADER, IMPORT_CSV_COMMODITY_DETAIL, IMPORT_CSV_CATEGORY_COMMODITY, IMPORT_CSV_CAMPAIGN_COMMODITY,
      IMPORT_CSV_TAG, IMPORT_CSV_TAG_COMMODITY, IMPORT_CSV_RELATED_COMMODITY_A, IMPORT_CSV_GIFT, IMPORT_CSV_GIFT_COMMODITY,
      IMPORT_CSV_STOCK_IO_DETAIL, IMPORT_CSV_SHIPPING_REPORT, IMPORT_CSV_HOLIDAY_SITE
      // soukai add ob 2011/12/20 start
      , IMPORT_CSV_PRIVATE_COUPON,IMPORT_CSV_PUBLIC_COUPON
      // soukai add ob 2011/12/20 end
      , IMPORT_CSV_CUSTOMER_ADDRESS, IMPORT_CSV_C_COMMODITY_HEADER, IMPORT_CSV_C_COMMODITY_DETAIL, IMPORT_CSV_C_COMMODITY_DATA
      //20120209 os013 add start
      ,IMPORT_CSV_CATEGORY_ATTRIBUTE_VALUE_DATA,IMPORT_CSV_C_COMMODITY_INITIAL_STAGE,IMPORT_CSV_COMMODITY_GIFT,IMPORT_CSV_DELIVERY_TYPE_NO
      //20120209 os013 add end
      ,IMPORT_CSV_DELIVERY_TYPE_NO
  };

  /**
   * コード名称を返します。
   * 
   * @return コード名称
   */
  public String getName() {
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
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
  public static CsvImportType fromName(String name) {
    for (CsvImportType p : CsvImportType.values()) {
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
  public static CsvImportType fromValue(String code) {
    for (CsvImportType p : CsvImportType.values()) {
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
      for (CsvImportType p : CsvImportType.values()) {
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

  /**
   * CsvConditionImplを実装した各Conditionクラスを生成します。<BR>
   * 生成されたconditionのgetFilter()メソッドで取得される
   * {@link jp.co.sint.webshop.data.csv.CsvFilter}
   * クラスのインスタンスのoutputWriter,errorWriter,logWriter は全て
   * {@link jp.co.sint.webshop.utility.VoidWriter}が設定されます。
   * 
   * @param <T>
   * @return CsvImportCondition
   */
  @SuppressWarnings("unchecked")
  public <T extends CsvImportCondition>T createConditionInstance() {
    Logger logger = Logger.getLogger(CsvExportType.class);
    T instance = null;
    try {
      instance = (T) conditionType.newInstance();
      instance.setSchema(DIContainer.getCsvSchema(this.schemaType));
    } catch (InstantiationException e) {
      logger.error(e.getMessage());
    } catch (IllegalAccessException e) {
      logger.error(e.getMessage());
    }
    return instance;
  }

  /**
   * schemaTypeを取得します。
   * 
   * @return schemaType
   */

  public CsvSchemaType getSchemaType() {
    return schemaType;
  }
}
