package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.RmAnalysisExportQuery;

public class RmAnalysisExportDataSource extends SqlExportDataSource<RmAnalysisCsvSchema, RmAnalysisExportCondition> {

  @Override
  public Query getExportQuery() {
    return new RmAnalysisExportQuery(getCondition().getSearchCondition());
  }

}
