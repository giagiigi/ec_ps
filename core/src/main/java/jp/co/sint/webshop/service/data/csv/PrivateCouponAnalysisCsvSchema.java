package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class PrivateCouponAnalysisCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public PrivateCouponAnalysisCsvSchema() {
	  List<CsvColumn> columns = new ArrayList<CsvColumn>();
	  columns.add(new CsvColumnImpl("COUPON_ISSUE_NO", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.0"), CsvDataType.STRING));
	  columns.add(new CsvColumnImpl("COUPON_NAME", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.1"), CsvDataType.STRING));
	  columns.add(new CsvColumnImpl("COUPON_CODE", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.2"), CsvDataType.STRING));
	  columns.add(new CsvColumnImpl("COUPON_ISSUE_TYPE", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.3"), CsvDataType.BIGDECIMAL));
	  columns.add(new CsvColumnImpl("COUPON_PROPORTION", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.4"), CsvDataType.BIGDECIMAL));
	  columns.add(new CsvColumnImpl("COUPON_AMOUNT", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.5"), CsvDataType.BIGDECIMAL));
	  columns.add(new CsvColumnImpl("USE_START_DATETIME", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.6"), CsvDataType.BIGDECIMAL)); 
	  columns.add(new CsvColumnImpl("USE_END_DATETIME", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.7"), CsvDataType.BIGDECIMAL));
	  columns.add(new CsvColumnImpl("CUSTOMER_CODE", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.8"), CsvDataType.BIGDECIMAL));  
	  columns.add(new CsvColumnImpl("LAST_NAME", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.9"), CsvDataType.STRING));
	  columns.add(new CsvColumnImpl("USE_STATUS", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.10"), CsvDataType.STRING));
	  columns.add(new CsvColumnImpl("ISSUE_ORDER_NO", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.11"), CsvDataType.BIGDECIMAL));
	  columns.add(new CsvColumnImpl("USE_ORDER_NO", Messages.getCsvKey("service.data.csv.PrivateCouponAnalysisCsvSchema.12"), CsvDataType.BIGDECIMAL));

    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "PrivateCouponAnalysisExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }
}
