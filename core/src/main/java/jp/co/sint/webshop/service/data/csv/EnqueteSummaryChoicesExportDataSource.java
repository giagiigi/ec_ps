package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.communication.EnqueteQuery;

public class EnqueteSummaryChoicesExportDataSource extends
    SqlExportDataSource<EnqueteSummaryChoicesCsvSchema, EnqueteSummaryChoicesExportCondition> {

  @Override
  public Query getExportQuery() {
    String query = EnqueteQuery.CSV_OUTPUT_CHOICETYPE;
    List<Object> params = new ArrayList<Object>();
    params.add(getCondition().getEnqueteCode());
    params.add(getCondition().getEnqueteQuestionNo());

    return new SimpleQuery(query, params.toArray());
  }

}
