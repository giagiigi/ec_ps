package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.customer.InquiryExportQuery;

public class InquiryListExportDataSource extends SqlExportDataSource<InquiryListCsvSchema, InquiryListExportCondition> {

  @Override
  public Query getExportQuery() {
    return new InquiryExportQuery(getCondition().getSearchCondition());
  }

}
