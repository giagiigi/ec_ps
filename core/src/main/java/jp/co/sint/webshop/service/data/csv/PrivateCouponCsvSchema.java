package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.NewCouponHistory;

public class PrivateCouponCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public PrivateCouponCsvSchema() {
    getColumns().add(new CsvColumnImpl("CUSTOMER_CODE", "顾客编号", CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("COUPON_CODE", "优惠券规则编号", CsvDataType.STRING));
  }

  public String getExportConfigureId() {
    return "";
  }

  public String getImportConfigureId() {
    return "PrivateCouponImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(NewCouponHistory.class);
  }
}
