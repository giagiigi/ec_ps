package jp.co.sint.webshop.data.csv.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvData;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.CsvExportException;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.csv.CsvUtil;

public class SqlCsvData implements CsvData {

  private ResultSet resultSet;

  private CsvSchema schema;

  public SqlCsvData() {
  }

  public SqlCsvData(CsvSchema schema, ResultSet rset) {
    setSchema(schema);
    setResultSet(rset);
  }

  ResultSet getResultSet() {
    return this.resultSet;
  }

  void setResultSet(ResultSet resultSet) {
    this.resultSet = resultSet;
  }

  CsvSchema getSchema() {
    return this.schema;
  }

  void setSchema(CsvSchema schema) {
    this.schema = schema;
  }

  public Iterator<List<String>> iterator() {
    return new ResultSetIterator(this);
  }

  public void dispose() {
    DatabaseUtil.closeResource(resultSet);
  }

  private static class ResultSetIterator implements Iterator<List<String>> {

    private SqlCsvData data;

    public ResultSetIterator(SqlCsvData data) {
      this.data = data;
    }

    public boolean hasNext() {
      boolean result = false;
      try {
        result = data.getResultSet().next();
      } catch (SQLException e) {
        DatabaseUtil.closeResource(data.getResultSet());
        throw new CsvExportException(e);
      }
      return result;
    }

    public List<String> next() {
      List<String> result = new ArrayList<String>();
      try {
        List<CsvColumn> columns = data.getSchema().getColumns();
        for (int i = 0; i < columns.size(); i++) {
          CsvDataType dataType = columns.get(i).getDataType();
          Object object;
          switch (dataType) {
            case DATE:
            case DATETIME:
              object = data.getResultSet().getTimestamp(i + 1);
              break;
            // 10.1.3 10140 追加 ここから
            case STRING:
              object = data.getResultSet().getString(i + 1);
              break;
            // 10.1.3 10140 追加 ここまで
            default:
              object = data.getResultSet().getObject(i + 1);
          }
          String value = dataType.format(object);
          // 10.1.3 10152 修正 ここから
          // result.add(CsvUtil.adjustLineFeed(value));
          result.add(CsvUtil.convertLineFeedForExport(value));
          // 10.1.3 10152 修正 ここまで
        }
      } catch (SQLException e) {
        DatabaseUtil.closeResource(data.getResultSet());
        throw new CsvExportException(e);
      }
      return result;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

}
