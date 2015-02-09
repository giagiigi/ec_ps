package jp.co.sint.webshop.service.analysis;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.AccessLog;
import jp.co.sint.webshop.data.dto.CommodityAccessLog;
import jp.co.sint.webshop.data.dto.Referer;
import jp.co.sint.webshop.data.dto.SearchKeywordLog;
import jp.co.sint.webshop.utility.SqlDialect;

/**
 * 分析サービス実装クラス内で使用するSQL文を集約したクラス
 * 
 * @author System Integrator Corp.
 */
public final class AnalysisServiceQuery {

  /** default constructor */
  private AnalysisServiceQuery() {
  }

  /** 検索キーの取得 */
  public static final String SELECT_SEARCH_KEY = "SELECT SEARCH_KEY FROM SEARCH_KEYWORD_LOG GROUP BY SEARCH_KEY";

  /** 顧客分析データの取得 */
  public static final String SELECT_CUSTOMER_STATISTICS = "SELECT" + "  HEADER_TABLE.ID," + "  HEADER_TABLE.STATISTICS_GROUP,"
      + "  DETAIL_TABLE.STATISTICS_ITEM," + "  DETAIL_TABLE.CUSTOMER_AMOUNT" + " FROM" + "   (" + "   SELECT"
      + "     STATISTICS_GROUP," + "     MAX(CUSTOMER_STATISTICS_ID) AS ID" + "   FROM" + "     CUSTOMER_STATISTICS"
      + "   GROUP BY" + "     STATISTICS_GROUP" + "   ) HEADER_TABLE," + "   (" + "   SELECT" + "     STATISTICS_GROUP,"
      + "     STATISTICS_ITEM," + "     SUM(CUSTOMER_AMOUNT) AS CUSTOMER_AMOUNT" + "   FROM" + "     CUSTOMER_STATISTICS"
      + "   GROUP BY" + "     STATISTICS_GROUP," + "     STATISTICS_ITEM" + "   ) DETAIL_TABLE" + " WHERE"
      + "   HEADER_TABLE.STATISTICS_GROUP = DETAIL_TABLE.STATISTICS_GROUP" + " ORDER BY" + "   HEADER_TABLE.ID,"
      + "   DETAIL_TABLE.CUSTOMER_AMOUNT DESC";

  /** 最新のアクセスログのアクセス日時の取得 */
  public static final String SELECT_LATEST_ACCESS_LOG_DATE = "SELECT " + SqlDialect.getDefault().toCharFromDate("ACCESS_DATE")
      + " AS ACCESS_DATE ,MAX(ACCESS_TIME) AS ACCESS_TIME FROM ACCESS_LOG "
      + "WHERE ACCESS_DATE = (SELECT MAX(ACCESS_DATE) FROM ACCESS_LOG) GROUP BY ACCESS_DATE";

  /** アクセスログの挿入 */
  public static final String INSERT_ACCESS_LOG = "INSERT INTO " + DatabaseUtil.getTableName(AccessLog.class) + " ( "
//postgreSQL start
  //+ DatabaseUtil.getColumnNamesCsv(AccessLog.class) + " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ACCESS_LOG_SEQ.NEXTVAL, ?, ?, ?, ? )";
  + DatabaseUtil.getColumnNamesCsv(AccessLog.class) + " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, "+SqlDialect.getDefault().getNextvalNOprm("ACCESS_LOG_SEQ")+", ?, ?, ?, ? )";
//postgreSQL end
  
  /** アクセスログの削除 */
  // 10.1.4 10083 修正 ここから
  // public static final String DELETE_ACCESS_LOG = "DELETE FROM ACCESS_LOG WHERE ACCESS_DATE = ?";
  public static final String DELETE_ACCESS_LOG = "DELETE FROM ACCESS_LOG WHERE ACCESS_DATE = ? AND ACCESS_TIME = ?";
  // 10.1.4 10083 修正 ここまで

  /** アクセスログ(ビジット数、購入者数)の更新 */
  public static final String UPDATE_ACCESS_LOG = "UPDATE ACCESS_LOG SET VISITOR_COUNT = ? ,PURCHASER_COUNT = ?, "
      + "UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE ACCESS_DATE = ? AND ACCESS_TIME = ? AND CLIENT_GROUP = ?";

  /** 最新のリファラログアクセス日の取得 */
  public static final String SELECT_LATEST_REFERER_DATE = "SELECT MAX(ACCESS_DATE) FROM REFERER";

  /** リファラーログの挿入 */
  public static final String INSERT_REFERER = "INSERT INTO " + DatabaseUtil.getTableName(Referer.class) + " ( "
      //+ DatabaseUtil.getColumnNamesCsv(Referer.class) + " ) VALUES ( ?, ?, ?, ?, ?, REFERER_SEQ.NEXTVAL, ?, ?, ?, ? )";
      + DatabaseUtil.getColumnNamesCsv(Referer.class) + " ) VALUES ( ?, ?, ?, ?, ?, "+SqlDialect.getDefault().getNextvalNOprm("REFERER_SEQ")+", ?, ?, ?, ? )";
  
  /** リファラーログの削除 */
  public static final String DELETE_REFERER = "DELETE FROM REFERER WHERE ACCESS_DATE = ?";

  /** 検索キーワードログの挿入 */
  public static final String INSERT_SEARCH_KEYWORD_LOG = "INSERT INTO " + DatabaseUtil.getTableName(SearchKeywordLog.class) + " ( "
      + DatabaseUtil.getColumnNamesCsv(SearchKeywordLog.class)
      //+ " ) VALUES ( ?, ?, ?, ?, ?, SEARCH_KEYWORD_LOG_SEQ.NEXTVAL, ?, ?, ?, ?)";
      + " ) VALUES ( ?, ?, ?, ?, ?, "+SqlDialect.getDefault().getNextvalNOprm("SEARCH_KEYWORD_LOG_SEQ")+", ?, ?, ?, ?)";

  /** 検索キーワードログの削除 */
  public static final String DELETE_SEARCH_KEYWORD_LOG = "DELETE FROM SEARCH_KEYWORD_LOG WHERE SEARCH_DATE = ?"
      + " AND SEARCH_KEY = ? AND SEARCH_WORD = ?";

  /** 最新の商品別アクセスログのアクセス日の取得 */
  public static final String SELECT_LATEST_COMMODITY_ACCESS_LOG_DATE = "SELECT MAX(ACCESS_DATE) FROM COMMODITY_ACCESS_LOG";

  /** 商品別アクセスログの挿入 */
  public static final String INSERT_COMMODITY_ACCSES_LOG = "INSERT INTO " + DatabaseUtil.getTableName(CommodityAccessLog.class)
      + " ( " + DatabaseUtil.getColumnNamesCsv(CommodityAccessLog.class)
      //+ ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, COMMODITY_ACCESS_LOG_SEQ.NEXTVAL, ?, ?, ?, ? ) ";
      + ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, "+SqlDialect.getDefault().getNextvalNOprm("COMMODITY_ACCESS_LOG_SEQ")+", ?, ?, ?, ? ) ";

  /** 商品別アクセスログの削除 */
  public static final String DELETE_COMMODITY_ACCESS_LOG = "DELETE FROM COMMODITY_ACCESS_LOG WHERE ACCESS_DATE = ?";

  /** 購入者数のカウント */
  public static final String COUNT_PURCHASER = "SELECT COUNT(ORDER_NO) FROM ORDER_HEADER "
      + "WHERE ? <= ORDER_DATETIME AND ORDER_DATETIME < ? AND CLIENT_GROUP = ?";
}
