package jp.co.sint.webshop.data.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.List;

import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.IOUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.io.ICsvWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * CSV入出力用ユーティリティです。このクラスをインスタンス化することはできません。
 * 
 * @author System Integrator Corp.
 */
public final class CsvUtil {
  public static final String WEBSHOP_LINEFEED = "\r\n"; // 10.1.3 10152 追加
  
  public static final String SUPERCSV_LINEFEED = "\n"; // 10.1.3 10152 追加

  private CsvUtil() {
  }

  public static String getCsvFileName(String csvId) {
    StringBuilder builder = new StringBuilder();
    builder.append(csvId);
    builder.append("_");
    builder.append(DateUtil.getTimeStamp());
    builder.append(".csv");

    return builder.toString();
  }

  public static void close(ICsvBeanReader reader) {
    if (reader != null) {
      try {
        reader.close();
      } catch (Exception ex) {
        Logger.getLogger(CsvUtil.class).warn(ex);
      }
    }
  }

  public static void close(ICsvListReader reader) {
    if (reader != null) {
      try {
        reader.close();
      } catch (Exception ex) {
        Logger.getLogger(CsvUtil.class).warn(ex);
      }
    }
  }

  public static void close(ICsvMapReader reader) {
    if (reader != null) {
      try {
        reader.close();
      } catch (Exception ex) {
        Logger.getLogger(CsvUtil.class).warn(ex);
      }
    }
  }

  public static void close(ICsvWriter writer) {
    if (writer != null) {
      try {
        writer.close();
      } catch (Exception ex) {
        Logger.getLogger(CsvUtil.class).warn(ex);
      }
    }
  }

  /**
   * 改行コードを補正します。
   * 
   * @deprecated SI WebShopping Ver.10.1.3から、次のメソッド{@link CsvUtil#convertLineFeedForExport(String)}に置き換えられました。
   * 
   * @param value
   *          入力値
   * @return 改行コードを補正した値
   */
  public static String adjustLineFeed(String value) {
    // SuperCsvがデータ内の[CRLF]を[CR][LF]と認識して
    // 改行を2つ(STANDARD_PREFERENCEの場合は[CRLF][CRLF])にする問題への対処
    if (StringUtil.hasValue(value) && value.contains("\r\n")) {
      return value.replace("\r\n", "\n");
    }
    return value;
  }
  
  // 10.1.3 10152 追加 ここから
  /**
   * 改行コードを補正します。
   * 
   * @param value
   *          入力値
   * @return 改行コードを補正した値
   */
  public static String convertLineFeedForImport(String value) {
    // SuperCsvの改行コードを、WebShopping内で使用する改行コードに変換する。
    if (StringUtil.hasValue(value) && value.contains(SUPERCSV_LINEFEED)) {
      return value.replace(SUPERCSV_LINEFEED, WEBSHOP_LINEFEED);
    }
    return value;
  }
  
  /**
   * 改行コードを補正します。
   * 
   * @param value
   *          入力値
   * @return 改行コードを補正した値
   */
  public static String convertLineFeedForExport(String value) {
    // WebShopping内で使用している改行コードを、SuperCsvの改行コードに変換する。
    if (StringUtil.hasValue(value) && value.contains(WEBSHOP_LINEFEED)) {
      return value.replace(WEBSHOP_LINEFEED, SUPERCSV_LINEFEED);
    }
    return value;
  }
  // 10.1.3 10152 追加 ここまで

  /**
   * CsvListReaderのインスタンスを取得します。
   * 
   * @param reader
   *          読み取り元
   * @return CsvListReader
   */
  public static ICsvListReader getCsvReader(Reader reader) throws IOException {
    return new CsvListReader(IOUtil.getReader(reader), CsvPreference.STANDARD_PREFERENCE);
  }

  /**
   * CsvListWriterのインスタンスを取得します。
   * 
   * @param writer
   *          書き込み先
   * @return CsvListWriter
   */
  public static ICsvListWriter getCsvWriter(Writer writer) throws IOException {
    return new CsvListWriter(IOUtil.getWriter(writer), CsvPreference.STANDARD_PREFERENCE);
  }

  public static <E>E createBeanFromCsvLine(List<String> csvLine, CsvSchema schema, Class<E> beanType) {
    E bean = null;
    try {
      bean = beanType.newInstance();
      for (int i = 0; i < schema.getColumns().size(); i++) {
        CsvColumn column = schema.getColumns().get(i);
        Object value = column.getDataType().parse(csvLine.get(i));
        if (value != null) {
          try {
            BeanUtil.invokeSetter(bean, StringUtil.toCamelFormat(column.getPhysicalName()), value);
          } catch (RuntimeException e) {
            Logger.getLogger(CsvUtil.class).trace(e.getMessage());
          }
        }
      }
    } catch (IllegalArgumentException e) {
      throw new CsvImportException(e);
    } catch (IllegalAccessException e) {
      throw new CsvImportException(e);
    } catch (InstantiationException e) {
      throw new CsvImportException(e);
    }
    return bean;
  }

  public static String buildInsertQuery(CsvSchema schema) {
    StringBuilder sqlColumns = new StringBuilder();
    StringBuilder sqlValues = new StringBuilder();
    for (int i = 0; i < schema.getColumns().size(); i++) {
      CsvColumn col = schema.getColumns().get(i);
      if (col.isExcluded()) {
        continue;
      } else if (sqlColumns.length() != 0) {
        sqlColumns.append(", ");
        sqlValues.append(", ");
      }
      sqlColumns.append(col.getPhysicalName());
      if (col.isGenerative()) {
//    	postgreSQL start
        //sqlValues.append(col.getSequenceName() + ".NEXTVAL");
    	sqlValues.append(SqlDialect.getDefault().getNextvalNOprm(col.getSequenceName()));
//    	postgreSQL end    	
      } else {
        sqlValues.append("?");
      }
    }
    return MessageFormat.format("INSERT INTO {0}({1}) VALUES({2})", schema.getTargetTableName(), sqlColumns.toString(), sqlValues
        .toString());
  }

  public static String buildUpdateQuery(CsvSchema schema) {
    StringBuilder sqlValues = new StringBuilder();
    StringBuilder sqlConditions = new StringBuilder();
    for (int i = 0; i < schema.getColumns().size(); i++) {
      CsvColumn col = schema.getColumns().get(i);
      String physicalName = col.getPhysicalName();
      if (physicalName.equalsIgnoreCase("orm_rowid") || physicalName.equalsIgnoreCase("created_user")
          || physicalName.equalsIgnoreCase("created_datetime")) {
        continue;
      } else if (col.isPrimaryKey()) {
        if (sqlConditions.length() != 0) {
          sqlConditions.append(" AND ");
        }
        sqlConditions.append(col.getPhysicalName() + "= ?");
      } else if (col.isExcluded() || col.isGenerative()) {
        continue;
      } else {
        if (sqlValues.length() != 0) {
          sqlValues.append(", ");
        }
        sqlValues.append(col.getPhysicalName() + "= ?");
      }
    }
    if (sqlConditions.length() > 0) {
      sqlConditions.insert(0, "WHERE ");
    }
    return MessageFormat.format("UPDATE {0} SET {1} {2}", schema.getTargetTableName(), sqlValues.toString(), sqlConditions
        .toString());
  }

  public static String buildCheckExistsQuery(CsvSchema schema) {
    StringBuilder sqlConditions = new StringBuilder();
    for (int i = 0; i < schema.getColumns().size(); i++) {
      CsvColumn col = schema.getColumns().get(i);
      if (col.isPrimaryKey()) {
        if (sqlConditions.length() != 0) {
          sqlConditions.append(" AND ");
        }
        sqlConditions.append(col.getPhysicalName() + "= ?");
      }
    }
    if (sqlConditions.length() > 0) {
      sqlConditions.insert(0, "WHERE ");
    }
    return MessageFormat.format("SELECT COUNT(*) FROM {0} {1} ", schema.getTargetTableName(), sqlConditions);
  }
}
