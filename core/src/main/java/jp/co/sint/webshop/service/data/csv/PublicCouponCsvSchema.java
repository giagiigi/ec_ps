package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.NewCouponRule;

public class PublicCouponCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public PublicCouponCsvSchema() {
    getColumns().add(new CsvColumnImpl("COUPON_CODE", "优惠券规则编号", CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("COUPON_NAME", "优惠券规则名称", CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("COUPON_NAME_EN", "优惠券规则名称(英文)", CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("COUPON_NAME_JP", "优惠券规则名称(日文)", CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("COUPON_TYPE", "优惠券发行种别", CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("COUPON_ISSUE_TYPE", "优惠券种别", CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("MIN_ISSUE_ORDER_AMOUNT", "发行最小购买金额", CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("MIN_ISSUE_START_DATETIME", "发行开始日时", CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("MIN_ISSUE_END_DATETIME", "发行结束日时", CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("COUPON_PROPORTION", "优惠比例", CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("COUPON_AMOUNT", "优惠金额", CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("MIN_USE_ORDER_AMOUNT", "优惠券使用最小购买金额", CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("MIN_USE_START_DATETIME", "使用开始日时", CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("MIN_USE_END_DATETIME", "使用结束日时", CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("PERSONAL_USE_LIMIT", "个人最大利用回数", CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("SITE_USE_LIMIT", "SITE最大利用回数", CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("ISSUE_REASON", "发行理由", CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("MEMO", "备注", CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("APPLICABLE_OBJECTS", "适用对象", CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("APPLICABLE_AREA", "适用区域", CsvDataType.STRING));   
    getColumns().add(new CsvColumnImpl("OBJECT_COMMODITIES", "对象商品集合字段", CsvDataType.STRING));  
    getColumns().add(new CsvColumnImpl("MAX_USE_ORDER_AMOUNT", "优惠券使用最大购买金额", CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("BEFORE_AFTER_DISCOUNT_TYPE", "优惠券发行金额类别", CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("USE_TYPE", "关联对象使用类型", CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("OBJECT_BRAND", "优惠券发行金额类别", CsvDataType.STRING));
    // 20131017 txw add start
    getColumns().add(new CsvColumnImpl("OBJECT_CATEGORY", "对象分类集合字段", CsvDataType.STRING));
    // 20131017 txw add end
  }

  public String getExportConfigureId() {
    return "";
  }

  public String getImportConfigureId() {
    return "PublicCouponImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(NewCouponRule.class);
  }
}
