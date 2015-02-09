package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.RfAnalysisExportQuery;

public class RfAnalysisExportDataSource extends SqlExportDataSource<RfAnalysisCsvSchema, RfAnalysisExportCondition> {

  @Override
  public Query getExportQuery() {
    return new RfAnalysisExportQuery(getCondition().getSearchCondition());
  }

}
