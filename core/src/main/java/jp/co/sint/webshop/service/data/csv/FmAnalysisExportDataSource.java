package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.FmAnalysisExportQuery;

public class FmAnalysisExportDataSource extends SqlExportDataSource<FmAnalysisCsvSchema, FmAnalysisExportCondition> {

  @Override
  public Query getExportQuery() {
    return new FmAnalysisExportQuery(getCondition().getSearchCondition());
  }

}
