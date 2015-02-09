package jp.co.sint.webshop.service.data.csv;

import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.domain.DayOfWeek;
import jp.co.sint.webshop.service.analysis.AccessLogExportQuery;
import jp.co.sint.webshop.service.analysis.CountType;

public class AccessLogExportDataSource extends SqlExportDataSource<AccessLogCsvSchema, AccessLogExportCondition> {

  @Override
  public void onFetchData(List<String> csvLine) {
    if (getCondition().getType().equals(CountType.EVERY_DAY_OF_WEEK)) {
      if (csvLine == null || csvLine.size() == 0) {
        return;
      }
      DayOfWeek day = DayOfWeek.fromValue(csvLine.get(0));
      csvLine.set(0, day.getName());
    }
  }

  @Override
  public Query getExportQuery() {
    return new AccessLogExportQuery(getCondition().getRange(), getCondition().getClientGroup(), getCondition().getType());
  }

}
