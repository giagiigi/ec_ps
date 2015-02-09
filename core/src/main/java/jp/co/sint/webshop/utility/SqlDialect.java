package jp.co.sint.webshop.utility;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.data.domain.RdbmsType;

/**
 * RDBM依存のSQL構文を吸収するクラスです。 当面はOracle10gをターゲットとして直接このクラスにメソッドを記述します。
 * 開発後半にインタフェース化してマルチDB化を図るので、利用クラス側では
 * <p>
 * SqlDialect.getDefault().<i>method()</i>
 * </p>
 * のように記述して下さい
 * 
 * @author System Integrator Corp.
 */
public class SqlDialect {

  /**
   * 新しいSqlDialectを生成します。
   */
  protected SqlDialect() {
  }

  /**
   * SqlDialectを新しく生成します。
   * 
   * @return 新しく生成したSqlDialectを返します。
   */
  public static SqlDialect getDefault() {
    return new SqlDialect();
  }

  /**
   * Oracle10gの場合、SqlDialectを新しく生成します。
   * 
   * @param rdbms
   *          RDBMSのコード定義
   * @return Oracle10gの場合、新しく生成したSqlDialectを返します。<br>
   *         Oracle10gのでない場合、nullを返します。
   */
  public static SqlDialect get(RdbmsType rdbms) {
    if (rdbms == RdbmsType.ORACLE_10G) {
      return new SqlDialect();
    }
    return null;
  }

  /**
   * 各要素をシングルクォート修飾せず、複数の文字列を連結します。
   * 
   * @param strings
   *          連結するための文字列
   * @return 単純な文字列連結を返します。
   */
  public String concatString(String... strings) {
    return concatString(false, strings);
  }

  /**
   * 複数の文字列を連結します。
   * 
   * @param withQuote
   *          各要素をシングルクォート修飾するかどうか
   * @param strings
   *          各文字列要素
   * @return 連結された文字列を返します。
   */
  public String concatString(boolean withQuote, String... strings) {

    if (strings == null || strings.length < 1) {
      return "";
    } else if (strings.length == 1) {
      return strings[0];
    } else {
      StringBuilder builder = new StringBuilder();
      for (int i = 1; i < strings.length; i++) {
        String s = strings[i];
        builder.append(" || ");
        if (withQuote) {
          builder.append(quote(s));
        } else {
          builder.append(s);
        }
      }
      return builder.toString();
    }
  }

  /**
   * IN検索のSQLフラグメントを生成します
   * 
   * @param columnName
   *          列名
   * @param values
   *          IN検索の条件
   * @return 生成されたSQLフラグメントを返します。
   */
  public SqlFragment createInClause(String columnName, Object... values) {
    StringBuilder builder = new StringBuilder(columnName);
    List<Object> params = new ArrayList<Object>();
    builder.append(" IN (");
    for (int i = 0; i < values.length; i++) {
      builder.append("?");
      if (i == values.length - 1) {
        builder.append(")");
      } else {
        builder.append(", ");
      }
      params.add(values[i]);

    }
    return new SqlFragment(builder.toString(), params.toArray());
  }

  /**
   * 日付範囲検索のSQLフラグメントを生成します。<br>
   * startValue、startValueはDate型とします。
   * 
   * @param columnName
   *          列名
   * @param startValue
   *          範囲の最小値
   * @param endValue
   *          範囲の最大値
   * @return 検索条件を返します。
   */
  public SqlFragment createDateRangeClause(String columnName, Date startValue, Date endValue) {
    return createDateRangeClause(columnName, startValue, endValue, DateSearchAccuracy.NONE);
  }

  /**
   * 日付範囲検索のSQLフラグメントを生成します。<br>
   * startValue、startValueはDate型とします。
   * 
   * @param columnName
   *          列名
   * @param startValue
   *          範囲の最小値
   * @param endValue
   *          範囲の最大値
   * @param accuracy
   *          入力値補正の精度を指定します。
   * @return 検索条件を返します。
   */
  public SqlFragment createDateRangeClause(String columnName, Date startValue, Date endValue, DateSearchAccuracy accuracy) {
    // NOTE: 秒単位の精度しか保証していない
    switch (accuracy) {
      // 日付単位＝開始日の時刻は00:00:00、終了日の時刻は23:59:59に補正
      case DATE:
        startValue = DateUtil.setHour(startValue, 0);
        endValue = DateUtil.setHour(endValue, 23);
        startValue = DateUtil.setMinute(startValue, 0);
        endValue = DateUtil.setMinute(endValue, 59);
        startValue = DateUtil.setSecond(startValue, 0);
        endValue = DateUtil.setSecond(endValue, 59);
        break;
      // 時間単位＝開始日の分は00:00、終了日の分は59:59に補正
      case HOUR:
        startValue = DateUtil.setMinute(startValue, 0);
        endValue = DateUtil.setMinute(endValue, 59);
        startValue = DateUtil.setSecond(startValue, 0);
        endValue = DateUtil.setSecond(endValue, 59);
        break;
      // 分単位＝開始日の秒は00、終了日の秒は59に補正
      case MINUTE:
        startValue = DateUtil.setSecond(startValue, 0);
        endValue = DateUtil.setSecond(endValue, 59);
        break;
      default:
        break;
    }
    return createRangeClause(columnName, startValue, endValue);
  }

  /**
   * 日付範囲検索のSQLフラグメントを生成します。<br>
   * startValue、startValueはString型とします。
   * 
   * @param columnName
   *          列名
   * @param startValue
   *          範囲の最小値
   * @param endValue
   *          範囲の最大値
   * @return 検索条件を返します。
   */
  public SqlFragment createStringRangeClause(String columnName, String startValue, String endValue) {
    return createRangeClause(columnName, startValue, endValue);
  }

  /**
   * 範囲検索のSQLフラグメントを生成します。<br>
   * startValue、startValueは引数で指定した型とします。戻り値のSQLフラグメントでは、式全体が( )で括られます。
   * 
   * @param <T>
   *          startValue、endValueの型
   * @param columnName
   *          列名
   * @param startValue
   *          範囲の最小値
   * @param endValue
   *          範囲の最大値
   * @return 検索条件を返します。
   */
  public <T>SqlFragment createRangeClause(String columnName, T startValue, T endValue) {
    return createRangeClause(columnName, startValue, endValue, true);
  }

  /**
   * 日付範囲検索のSQLフラグメントを生成します。<br>
   * startValue、startValueは引数で指定した型とします。
   * 
   * @param <T>
   *          startValue、endValueの型
   * @param columnName
   *          列名
   * @param startValue
   *          範囲の最小値
   * @param endValue
   *          範囲の最大値
   * @param blocking
   *          フラグメント全体を( )で括る必要があるときはtrueを指定します。
   * @return 検索条件を返します。
   */
  public <T>SqlFragment createRangeClause(String columnName, T startValue, T endValue, boolean blocking) {
    StringBuilder builder = new StringBuilder();
    Object[] values = null;

    if (startValue instanceof String) {
      if (!StringUtil.hasValue((String) startValue)) {
        startValue = null;
      }
    }
    if (endValue instanceof String) {
      if (!StringUtil.hasValue((String) endValue)) {
        endValue = null;
      }
    }

    if (startValue != null && endValue != null) {
      builder.append(columnName);
      builder.append(" BETWEEN ? AND ?");
      values = new Object[] {
          startValue, endValue
      };
    } else if (startValue != null) {
      builder.append(columnName);
      builder.append(" >= ?");
      values = new Object[] {
        startValue
      };
    } else if (endValue != null) {
      builder.append(columnName);
      builder.append(" <= ?");
      values = new Object[] {
        endValue
      };
    } else {
      values = new Object[] {};
    }

    if (blocking && (startValue != null || endValue != null)) {
      builder.insert(0, "(");
      builder.append(")");
    }

    return new SqlFragment(builder.toString(), values);
  }

  /**
   * LIKE検索部のSQLフラグメントを生成します。<BR>
   * 
   * @param columnName
   *          列名
   * @param value
   *          値
   * @param option
   *          前方一致、後方一致、部分一致のいずれかを指定
   * @param useLikec
   *          trueの場合はlikec、falseの場合はlikeで検索
   * @return LIKE検索部のSQLフラグメントを返します。
   */
  public SqlFragment createLikeClause(String columnName, String value, LikeClauseOption option, boolean useLikec) {
    StringBuilder builder = new StringBuilder();

    if (useLikec) {
        // postgreSQL start
	//  builder.append(" (" + columnName);
	//  builder.append(" LIKEC ? {ESCAPE '\\'}");
	//} else {
	//  builder.append(" ( TO_CHAR(" + columnName + ")");
	//  builder.append(" LIKE ? {ESCAPE '\\'}");
        if (DIContainer.getWebshopConfig().isPostgreSQL()) {
          builder.append(columnName + " LIKE ? ");
        } else {
          builder.append(columnName + " LIKEC ? {ESCAPE '\\'}"); //$NON-NLS-1$
        }
      } else {
        if (DIContainer.getWebshopConfig().isPostgreSQL()) {
      	//builder.append(" ( CAST(" + columnName + " AS TEXT)");
      	builder.append(" ( CAST(UPPER(" + columnName + " )AS TEXT)");
      	builder.append(" LIKE UPPER(?) ");
        } else {
      	builder.append(" ( TO_CHAR(" + columnName + ")"); //$NON-NLS-1$ //$NON-NLS-2$
          builder.append(" LIKE ? {ESCAPE '\\'}"); //$NON-NLS-1$
        }
        builder.append(") ");
        // postgreSQL end
    }

    String pattern = this.escape(value);
    switch (option) {
      case STARTS_WITH:
        pattern = pattern + "%";
        break;
      case ENDS_WITH:
        pattern = "%" + pattern;
        break;
      case PARTIAL_MATCH:
        pattern = "%" + pattern + "%";
        break;
      default:
        break;
    }
    return new SqlFragment(builder.toString(), pattern);
  }

  /**
   * LIKE検索部のSQLフラグメントを生成します。
   * 
   * @param columnName
   *          列名
   * @param value
   *          値
   * @param option
   *          前方一致、後方一致、部分一致のいずれかを指定
   * @return LIKE検索部のSQLフラグメントを返します。
   */
  public SqlFragment createLikeClause(String columnName, String value, LikeClauseOption option) {
    return createLikeClause(columnName, value, option, true);
  }

  /**
   * " FOO LIKEC ? {ESCAPE '\'} "となるよう<br>
   * MessageFormatなので「{」「}」「'」はシングルクォートでエスケープ
   * 
   * @param columnName
   *          列名
   * @return 指定されたパターンを使ってフォーマットしたものを返します。
   */
  public String getLikeClausePattern(String columnName) {
    // " FOO LIKEC ? {ESCAPE '\'} "となるよう
    // MessageFormatなので「{」「}」「'」はシングルクォートでエスケープ
	// postgreSQL start
	//return MessageFormat.format(" {0} LIKEC ? '{ESCAPE ''\\'''} ", columnName);
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? MessageFormat.format(" {0} LIKE ? ", columnName) : MessageFormat.format(" {0} LIKEC ? '{ESCAPE ''\\'''} ", columnName); //$NON-NLS-1$
    // postgreSQL end
  }

  /**
   * 文字列をシングルクォート(')で括ります。文字列中の(')は('')に置換されます。
   * 
   * @param s
   *          シングルクォート(')で括るための文字列
   * @return シングルクォート(')で装飾した文字列を返します。
   */
  public String quote(String s) {
    String result = s;
    if (StringUtil.hasValue(result)) {
      result = "'" + result.replace("'", "''") + "'";
    }
    return result;
  }

  /**
   * LIKE検索に使用するパターン文字列から「_」「%」をエスケープします。 エスケープ文字には「\」が使われます。
   * 
   * @param str
   *          エスケープするための文字列
   * @return エスケープした文字列を返します。
   */
  public String escape(String str) {
    return escape(str, "\\", "\\\\");
  }

  /**
   * エスケープ文字を指定して、 LIKE検索に使用するパターン文字列から「_」「%」をエスケープします。
   * 
   * @param str
   *          エスケープするための文字列
   * @param escapeChar
   *          エスケープ文字(以前の文字)
   * @param selfEscape
   *          エスケープ文字(新しい文字)
   * @return エスケープした文字列を返します。
   */
  public String escape(String str, String escapeChar, String selfEscape) {
    if (StringUtil.isNullOrEmpty(str)) {
      return str;
    } else {
      str = str.replace(escapeChar, selfEscape);
      StringBuilder builder = new StringBuilder();
      for (char c : str.toCharArray()) {
        switch (c) {
          case '_':
          case '＿':
          case '%':
          case '％':
            builder.append(escapeChar);
            break; // 10.1.4 K00168 追加
          default:
            break;
        }
        builder.append(c);
      }
      return builder.toString();
    }
  }

  // postgreSQL start
  public String getTrunc() {
	return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "" : "TRUNC";
  }
  
  public String getTruncSysdate() {
	return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "CURRENT_DATE" : "TRUNC(SYSDATE)";
  }
  
  public String getConcat(String string1, String string2) {
	return DIContainer.getWebshopConfig().isPostgreSQL() == true ? string1 + " || " + string2 : "CONCAT(" + string1 + "," + string2 + ")";
  }
  
  public String getAddMinute() {
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? " CAST(to_char(?,'9999')||' minute' AS interval)" : " ?/1440 ";
  }
  
  public String getAddMonth(String string1,String string2) {
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? string1 + " + CAST(to_char(" + string2 + ",'99')||' Month' AS interval)" : "ADD_MONTHS(" + string1 + "," + string2 + " )";
  }
  
  public String getNextval(SequenceType sequenceType,String string) {
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "NEXTVAL('"+MessageFormat.format(string , sequenceType.getValue())+"')": MessageFormat.format(string, sequenceType.getValue()) + ".NEXTVAL";
  }
  
  public String getNextvalNOprm(String string) {
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "NEXTVAL('" + string + "')": string + ".NEXTVAL";
  }
  
  public String getAddTimeStampHour(String strTimeStamp, String strHour) {
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? strTimeStamp + " + interval '" + strHour + "hour'" : strTimeStamp + strHour + "/24";
  }
  
  public String getCallStart() {
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "select * from " : "{call ";
  }
  
  public String getCallEnd() {
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? ")" : ", ?)}";
  }
  
  public String getCallEnd2() {
	    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? ")" : "?)}";
	  }
  
  public String toChar(String columnName) {
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "CAST(" + columnName + " AS TEXT)" : "TO_CHAR(" + columnName + ")";
  }
  
  public String numeric2char(String columnName) {
	return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "CAST(" + columnName + " AS TEXT)" : columnName;
  }
  
  public String intervalDay(String intervalDay) {
		return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "interval '"+intervalDay+" day'" : intervalDay;
	  }
  
  // postgreSQL end
  
  /**
   * SYSDATEを返します。
   * 
   * @return SYSDATEを返します。
   */
  public String getCurrentDatetime() {
    //return "SYSDATE";
    // postgreSQL start
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "NOW()" : "SYSDATE"; //$NON-NLS-1$
    // postgreSQL end
  }
  
  // postgreSQL start
  /**
   * SYSDATEを返します。
   * 
   * @return SYSDATEを返します。
   */
  public String getCurrentDatetimeChr() {
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "NOW()" : "TO_CHAR(SYSDATE, 'YYYY/MM/DD')"; //$NON-NLS-1$
  }
  // postgreSQL end
  
  /**
   * Date型(SYSDATE)のカラムをStringに変換します。(YYYY/MM/DD)
   * 
   * @return SQLStringを返します。
   */
  public String getCurrentDateString() {
    // postgreSQL start	  
    //return "TO_CHAR(SYSDATE, 'YYYY/MM/DD')";
	return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "CURRENT_DATE" :"TO_CHAR(" + getCurrentDatetime() + ", 'YYYY/MM/DD')";
	// postgreSQL end
  }

  /**
   * Date型(SYSDATE)のカラムをStringに変換します。(YYYY/MM/DD HH24:MI:SS)
   * 
   * @return SQLStringを返します。
   */
  public String getCurrentDateTimeString() {
    // postgreSQL start	  
    //return "TO_CHAR(SYSDATE, 'YYYY/MM/DD HH24:MI:SS')";
	return "TO_CHAR(" + getCurrentDatetime() + ", 'YYYY/MM/DD HH24:MI:SS')";
	// postgreSQL end 
  }

  /**
   * Date型のカラムをStringに変換します。(YYYY/MM/DD)
   * 
   * @param columnName
   *          列名
   * @return SQLStringを返します。
   */
  public String toCharFromDate(String columnName) {
    return "TO_CHAR(" + columnName + " , 'YYYY/MM/DD')";
  }

  /**
   * Date型のカラムをStringに変換します。(YYYY/MM/DD HH24:MI:SS)
   * 
   * @param columnName
   *          列名
   * @return SQLStringを返します。
   */
  public String toCharFromDatetime(String columnName) {
    return "TO_CHAR(" + columnName + " , 'YYYY/MM/DD HH24:MI:SS')";
  }

  /**
   * Date型のカラムから週の日を取得します。
   * 
   * @param columnName
   *          列名
   * @return SQLStringを返します。
   */
  public String getDayOfWeekFromDate(String columnName) {
    return "TO_CHAR(" + columnName + " , 'D')";
  }

  /**
   * Date型のカラムから月の日を取得します。
   * 
   * @param columnName
   *          列名
   * @return SQLStringを返します。
   */
  public String getDayFromDate(String columnName) {
    return "TO_CHAR(" + columnName + " , 'DD')";
  }

  /**
   * Date型のカラムから月を取得します。
   * 
   * @param columnName
   *          列名
   * @return SQLStringを返します。
   */
  public String getMonthFromDate(String columnName) {
    return "TO_CHAR(" + columnName + " , 'MM')";
  }

  /**
   * Date型のカラムから月・日を取得します。
   * 
   * @param columnName
   *          列名
   * @return SQLStringを返します。
   */
  public String getMonthDayFromDate(String columnName) {
    return "TO_CHAR(" + columnName + ", 'MMDD')";
  }

  /**
   * 指定されたパラメータをDate型に変換します。(YYYY/MM/DD)
   * 
   * @return SQLStringを返します。
   */
  public String toDate() {
    return "TO_DATE( ? , 'YYYY/MM/DD')";
  }

  /**
   * 指定されたパラメータをDate型に変換します。(YYYY/MM/DD HH24:MI:SS)
   * 
   * @return SQLStringを返します。
   */
  public String toDatetime() {
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "TO_TIMESTAMP( ? , 'YYYY/MM/DD HH24:MI:SS')" :"TO_DATE( ? , 'YYYY/MM/DD HH24:MI:SS')";
  }

  /**
   * 引数で受け取った値を、Date型に変換します。(YYYY/MM/DD)
   * 
   * @return SQLStringを返します。
   */
  public String toDate(String s) {
    return "TO_DATE(" + quote(s) + ", 'YYYY/MM/DD')";
  }

  /**
   * 引数で受け取った値を、Date型に変換します。(YYYY/MM/DD HH24:MI:SS)
   * 
   * @return SQLStringを返します。
   */
  public String toDatetime(String s) {
    return "TO_DATE(" + quote(s) + ", 'YYYY/MM/DD HH24:MI:SS')";
  }

  /**
   * 引数で受け取った値を、数値型に変換します。
   * 
   * @return SQLStringを返します。
   */
  public String toNumber(String s) {
    return "TO_NUMBER(" + quote(s) + ")";
  }

  /**
   * 現在の日付を返します。
   * 
   * @return SQLStringを返します。
   */
  public String getSystemDateQuery() {
	// postgreSQL start
    //return "SELECT SYSDATE FROM DUAL";
    return "SELECT " + getCurrentDatetime() +" FROM DUAL"; //$NON-NLS-1$
    // postgreSQL end
  }

  /**
   * 関数呼び出しのためのダミー表を返します。
   * 
   * @return SQLStringを返します。
   */
  public String getDummyTable() {
    return "FROM DUAL";
  }

  public static enum LikeClauseOption {
    STARTS_WITH, ENDS_WITH, PARTIAL_MATCH;
  }

  public static enum DateSearchAccuracy {
    DATE, HOUR, MINUTE, NONE
  }
  
  //postgres start
  public String getProcedureString(String procedureString) {
    if (StringUtil.hasValue(procedureString)) {
      if (DIContainer.getWebshopConfig().isPostgreSQL()) {
        procedureString = procedureString.trim();
        int index = procedureString.indexOf(",?)}");
        if (index == -1) {
          index = procedureString.indexOf(", ?)}");
        }
        if (index == -1) {
          index = procedureString.indexOf("?)}");
        }
        if (index > 0) {
          if (procedureString.startsWith("{CALL ") && (procedureString.endsWith(",?)}") || procedureString.endsWith(", ?)}")
              || procedureString.endsWith("?)}"))) {
            procedureString = procedureString.substring(0, index);
            procedureString = procedureString.replace("{CALL ", "");
            procedureString = "select * from " + procedureString + ")";
          }
        }
      }
    }
    return procedureString;
  }
  //postgres end
  // 10.1.6 10260 
  /**
   * SYSDATE
   * 
   * @return
   */
  public String getCurrentDate() {
    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "CURRENT_DATE" : "TRUNC(SYSDATE,'DD')"; //$NON-NLS-1$
  }
  // 10.1.6 10260 
}
