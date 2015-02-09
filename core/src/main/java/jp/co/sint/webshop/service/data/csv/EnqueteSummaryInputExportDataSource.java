package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.communication.EnqueteQuery;

public class EnqueteSummaryInputExportDataSource extends
    SqlExportDataSource<EnqueteSummaryInputCsvSchema, EnqueteSummaryInputExportCondition> {

  @Override
  public Query getExportQuery() {
    String query = EnqueteQuery.CSV_OUTPUT_INPUTTYPE;
    List<Object> params = new ArrayList<Object>();
    params.add(getCondition().getEnqueteCode());
    params.add(getCondition().getEnqueteQuestionNo());

    return new SimpleQuery(query, params.toArray());
  }

}
