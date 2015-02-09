package jp.co.sint.webshop.data;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * DTOとSQL関連のユーティリティクラスです。
 * 
 * @author System Integrator Corp.
 */
public final class SqlUtil {

  private SqlUtil() {
  }

  /**
   * 指定されたDTOに対応するテーブルの列名リストを返します。
   * 
   * @param <T>
   *          DTOの型
   * @param dtoClass
   *          DTOのClassオブジェクト
   * @return 列名のリスト
   */
  public static <T extends WebshopEntity>List<String> getColumnNames(Class<T> dtoClass) {
    List<String> fields = new ArrayList<String>();
    if (dtoClass != null) {
      for (Field ff : dtoClass.getDeclaredFields()) {
        if (ff.isAnnotationPresent(Metadata.class)) {
          String colName = StringUtil.toUnderScoreDelimitedFormat(ff.getName());
          fields.add(colName);
        }
      }
    }
    return fields;
  }

  /**
   * 指定されたDTOに対応する、テーブルの名称を返します。
   * 
   * @param <T>
   *          DTOの型
   * @param dtoClass
   *          DTOのClassオブジェクト
   * @return テーブルの名称
   */
  public static <T extends WebshopEntity>String getTableName(Class<T> dtoClass) {
    if (dtoClass == null) {
      return null;
    } else {
      return StringUtil.toUnderScoreDelimitedFormat(dtoClass.getSimpleName());
    }
  }

  /**
   * 指定されたDTOに対応する、すべての列値をカンマ区切りで列挙したもの(<i>COLUMN1, COLUMN2, ... COLUMNn</i>)を返します。
   * 
   * @param <T>
   *          DTOの型
   * @param dtoClass
   *          DTOのClassオブジェクト
   * @return すべての列値をカンマ区切りで列挙したもの(<i>COLUMN1, COLUMN2, ... COLUMNn</i>)を返します。
   */
  public static <T extends WebshopEntity>String getColumnNamesCsv(Class<T> dtoClass) {
    List<String> columns = getColumnNames(dtoClass);
    return convertListToCsv(columns);
  }

  /**
   * 指定されたDTOに対応する、すべての列値をカンマ区切りで列挙したもの(<i>COLUMN1, COLUMN2, ... COLUMNn</i>)を返します。
   * 各列の先頭にはプレフィクスが付与されます。
   * 
   * @param <T>
   *          DTOの型
   * @param dtoClass
   *          DTOのClassオブジェクト
   * @param prefix
   *          列のプレフィクス
   * @return すべての列値をカンマ区切りで列挙したもの(<i>COLUMN1, COLUMN2, ... COLUMNn</i>)を返します。
   */
  public static <T extends WebshopEntity>String getColumnNamesCsv(Class<T> dtoClass, String prefix) {
    List<String> columns = getColumnNames(dtoClass);
    return convertListToCsv(columns, prefix);
  }

  /**
   * 指定されたDTOに対応する、すべての列値を取得するSELECT文(SELECT <i>COLUMN1, COLUMN2, ... COLUMNn</i>
   * FROM <i>TABLENAME</i>)を返します。
   * 
   * @param <T>
   *          DTOの型
   * @param dtoClass
   *          DTOのClassオブジェクト
   * @return すべての列値を取得するSELECT文(SELECT <i>COLUMN1, COLUMN2, ... COLUMNn</i>
   *         FROM <i>TABLENAME</i>)を返します。dtoClassがnullのときはnullを返します。
   */
  public static <T extends WebshopEntity>String getSelectAllQuery(Class<T> dtoClass) {
    if (dtoClass == null) {
      return null;
    }
    return MessageFormat.format("SELECT {0} FROM {1}", getColumnNamesCsv(dtoClass), getTableName(dtoClass));
  }

  public static <T extends WebshopEntity>String getInsertQuery(Class<T> dtoClass) {
    String insertSql = "INSERT INTO {0} ({1}) VALUES ({2})";
    String tableName = getTableName(dtoClass);
    String columns = getColumnNamesCsv(dtoClass);
    String placeHolders = columns.replaceAll("[A-Z_]+", "?");
    return MessageFormat.format(insertSql, tableName, columns, placeHolders);
  }

  public static <T extends WebshopEntity>String getUpdateQuery(Class<T> dtoClass) {
    String updateSql = "UPDATE {0} SET {1} WHERE ORM_ROWID = ? AND UPDATED_DATETIME = ?";
    String tableName = getTableName(dtoClass);
    List<String> columns = getColumnNames(dtoClass);
    columns.remove("ORM_ROWID");
    columns.remove("UPDATED_DATETIME");

    List<String> colSetClause = new ArrayList<String>();
    for (String s : columns) {
      colSetClause.add(s + "= ?");
    }
    String colCsv = convertListToCsv(colSetClause);
    return MessageFormat.format(updateSql, tableName, colCsv);
  }

  public static <T extends WebshopEntity>String getDeleteQuery(Class<T> dtoClass) {
    String deleteSql = "DELETE FROM {0} WHERE ORM_ROWID = ?";
    String tableName = getTableName(dtoClass);
    return MessageFormat.format(deleteSql, tableName);
  }

  private static String convertListToCsv(List<String> columns) {
    return convertListToCsv(columns, "");
  }

  private static String convertListToCsv(List<String> columns, String prefix) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < columns.size(); i++) {
      String s = columns.get(i);
      if (StringUtil.hasValue(prefix)) {
        builder.append(prefix);
        builder.append(".");
      }
      builder.append(s);
      if (i != columns.size() - 1) {
        builder.append(", ");
      }
    }
    return builder.toString();
  }

}
