package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvExportException;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.service.order.OrderServiceQuery;
import jp.co.sint.webshop.utility.DateUtil;

import org.apache.log4j.Logger;

public class OutputOrderExportDataSource extends SqlExportDataSource<OutputOrderCsvSchema, OutputOrderExportCondition> {

  private PreparedStatement updateStatement = null;

  @Override
  public void beforeExport() {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      getConnection().setAutoCommit(false);
      updateStatement = getConnection().prepareStatement(OrderServiceQuery.UPDATE_ORDER_HEADER_DATA_TRANSPORT_STATUS_QUERY);

    } catch (Exception e) {
      logger.error(e);
    } finally {
      try {
        getConnection().rollback();
      } catch (Exception e) {
        throw new CsvExportException();
      }
    }
  }

  public Query getExportQuery() {
    String sql = OrderServiceQuery.getOutputOrderDataQuery();
    Query q = new SimpleQuery(sql);
    return q;

  }

  @Override
  public void onFetchData(List<String> csvLine) {
    try {
      Object[] args = new Object[] {
          DataTransportStatus.TRANSPORTED.getValue(), getCondition().getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(),
          csvLine.get(0)
      };
      DatabaseUtil.bindParameters(updateStatement, args);
      updateStatement.executeUpdate();
      updateStatement.clearParameters();
    } catch (SQLException e) {
      throw new CsvExportException(e);
    }

  }

  @Override
  public void afterExport() {
    try {
      DatabaseUtil.tryCommit(getConnection());
    } catch (DataAccessException e) {
      DatabaseUtil.tryRollback(getConnection());
      throw e;
    }
  }

}
