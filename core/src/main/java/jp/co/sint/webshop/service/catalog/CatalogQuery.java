package jp.co.sint.webshop.service.catalog;
  
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SqlUtil;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CommoditySyncFlag;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.data.domain.PartsCode;
import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.domain.SyncFlagJd;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.SearchKeywordLog;
import jp.co.sint.webshop.data.dto.TagCommodity;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.data.domain.UsingFlg;

public final class CatalogQuery {

  private CatalogQuery() {
  }

  public static final String CATEGORY_DELIMITER = "~";

  /**
   * 第二步修改引当数：tmall引当数-EC侧此订单商品数量 库存再分配
   */
  public static final String SUBSTRACT_TMALL = "OPERATION_SUBSTRACT_TMALL";

  /**
   * 引当数+CVS订单商品数量
   */
  public static final String PLUS_TMALL_AFTER_INSERT = "OPERATION_PLUS_TMALL_AFTER_INSERT";

  /**
   * 引当数+订单商品数量差额 ※订单商品数量差额=CSV本次订单商品数量-EC上次订单商品数量
   */
  public static final String PLUS_TMALL_NOT_CANCEL = "PLUS_TMALL_NOT_CANCEL";

  public static final String COMMODITY_BASE_COLUMN = " SELECT A.SHOP_CODE, "
      + "        A.COMMODITY_CODE, "
      + "        A.SKU_CODE, "
      + "        A.REPRESENT_SKU_CODE, "
      + "        A.COMMODITY_STANDARD1_NAME, "
      + "        A.COMMODITY_STANDARD2_NAME, "
      + "        A.STANDARD_DETAIL1_NAME, "
      + "        A.STANDARD_DETAIL2_NAME, "
      + "        A.COMMODITY_NAME, "
      + "        A.UNIT_PRICE, "
      + "        A.DISCOUNT_PRICE, "
      + "        A.RESERVATION_PRICE, "
      + "        A.SALE_START_DATETIME, "
      + "        A.SALE_END_DATETIME, "
      + "        A.DISCOUNT_PRICE_START_DATETIME, "
      + "        A.DISCOUNT_PRICE_END_DATETIME, "
      + "        A.RESERVATION_START_DATETIME, "
      + "        A.RESERVATION_END_DATETIME, "
      + "        A.COMMODITY_TAX_TYPE, "
      + "        A.COMMODITY_DESCRIPTION_PC, "
      + "        A.COMMODITY_DESCRIPTION_MOBILE, "
      + "        A.STOCK_MANAGEMENT_TYPE, "
      + "        A.ARRIVAL_GOODS_FLG, "
      + "        A.DELIVERY_TYPE_NO, "
      + "        A.LINK_URL, "
      + "        A.RECOMMEND_COMMODITY_RANK, "
      + "        A.JAN_CODE, "
      + "        A.COMMODITY_POINT_RATE, "
      + "        A.COMMODITY_POINT_START_DATETIME, "
      + "        A.COMMODITY_POINT_END_DATETIME, "
      + "        A.SALE_FLG, "
      + "        A.RETAIL_PRICE,"
      // 2012/12/20 促销对应 ob add start
      + "        A.set_commodity_flg, "
      + "        A.commodity_type,"
      // 2012/12/20 促销对应 ob add end
      + "        A.AVAILABLE_STOCK_QUANTITY, A.WARNING_FLAG, A.USE_FLG "
      + "      ,A.COMMODITY_NAME_EN , A.COMMODITY_NAME_JP ,  "
      + "     A.COMMODITY_STANDARD1_NAME_EN, A.COMMODITY_STANDARD1_NAME_JP,"
      + "     A.COMMODITY_STANDARD2_NAME_EN,   A.COMMODITY_STANDARD2_NAME_JP, "
      + "     A.STANDARD_DETAIL1_NAME_EN,   A.STANDARD_DETAIL1_NAME_JP, "
      + "     A.STANDARD_DETAIL2_NAME_EN, A.STANDARD_DETAIL2_NAME_JP, "
      + "     A.COMMODITY_DESCRIPTION_PC_EN,         A.COMMODITY_DESCRIPTION_MOBILE_EN,  "
      + "     A.COMMODITY_DESCRIPTION_PC_JP,         A.COMMODITY_DESCRIPTION_MOBILE_JP, "
      // 20130625 txw add start
      + "     A.ORIGINAL_COMMODITY_CODE, A.COMBINATION_AMOUNT ";
      // 20130625 txw add end

  public static final String SALE_RECOMMEND_COLUMN = " SELECT "
    + " A.COMMODITY_NAME,"  
    + " A.COMMODITY_NAME_EN,"
    + " A.COMMODITY_NAME_JP,"
    + " A.COMMODITY_CODE, "  
    + " A.SHOP_CODE,"  
    + " A.UNIT_PRICE,"  
    + " A.DISCOUNT_PRICE,"  
    + " A.RESERVATION_PRICE,"  
    + " A.COMMODITY_TAX_TYPE,"  
    + " A.STOCK_MANAGEMENT_TYPE" ;

  public static final String COMMODITY_COLUMN = COMMODITY_BASE_COLUMN
  + "        ,A.COMMODITY_SEARCH_WORDS,  " + "        D.SHOP_NAME, "
  + "        E.STOCK_STATUS_NO, "
  + "        E.STOCK_SUFFICIENT_MESSAGE, "
  + "        E.STOCK_LITTLE_MESSAGE, "
  + "        E.OUT_OF_STOCK_MESSAGE, "
  + "        E.STOCK_SUFFICIENT_THRESHOLD, "
  + "        F.REVIEW_SCORE, " + "        F.REVIEW_COUNT, "
  + "        REVIEW_DISPLAY_STATUS(A.SHOP_CODE) AS DISPLAY_FLG ";
  
  public static final String GET_TAG = " SELECT A.SHOP_CODE,"
      + "        A.TAG_CODE,"
      + "        A.TAG_NAME,"
      + "        A.DISPLAY_ORDER,"
      + "        A.ORM_ROWID,"
      + "        A.CREATED_USER,"
      + "        A.CREATED_DATETIME,"
      + "        A.UPDATED_USER,"
      + "        A.UPDATED_DATETIME,"
      + "        B.RELATED_COUNT"
      + " FROM   TAG a LEFT OUTER JOIN"
      + "        (SELECT SHOP_CODE, TAG_CODE, COUNT(TAG_CODE) AS RELATED_COUNT"
      + "         FROM TAG_COMMODITY" + "         WHERE SHOP_CODE = ?"
      + "         GROUP BY SHOP_CODE, TAG_CODE) b"
      + "       ON A.SHOP_CODE = B.SHOP_CODE AND"
      + "          A.TAG_CODE = B.TAG_CODE" + " WHERE A.SHOP_CODE = ?"
      + " ORDER BY A.TAG_CODE";

  public static final String GET_GIFT_COMMODITY_LIST = "SELECT A.SHOP_CODE, "
      + "      A.GIFT_CODE, " + "      A.GIFT_NAME, "
      + "      A.GIFT_DESCRIPTION," + "      A.GIFT_PRICE, "
      + "      A.DISPLAY_ORDER, " + "      A.GIFT_TAX_TYPE, "
      + "      A.DISPLAY_FLG, " + "      A.ORM_ROWID, "
      + "      A.CREATED_USER," + "      A.CREATED_DATETIME,"
      + "      A.UPDATED_USER, " + "      A.UPDATED_DATETIME "
      + " FROM GIFT A " + "        INNER JOIN GIFT_COMMODITY B "
      + "          ON A.SHOP_CODE = B.SHOP_CODE AND "
      + "             A.GIFT_CODE = B.GIFT_CODE "
      + " WHERE A.DISPLAY_FLG    = " + DisplayFlg.VISIBLE.getValue()
      + " AND " + "       B.SHOP_CODE      = ? AND "
      + "       B.COMMODITY_CODE = ? " + " ORDER BY A.DISPLAY_ORDER, "
      + "          A.GIFT_CODE ";

  public static final String DISPLAY_PC_SQL = " AND (A.DISPLAY_CLIENT_TYPE = "
      + DisplayClientType.ALL.getValue()
      + " OR DISPLAY_CLIENT_TYPE = "
      + DisplayClientType.PC.getValue() + ")";

  public static final String DISPLAY_MOBILE_SQL = " AND (A.DISPLAY_CLIENT_TYPE = "
      + DisplayClientType.ALL.getValue()
      + " OR DISPLAY_CLIENT_TYPE = "
      + DisplayClientType.MOBILE.getValue() + ")";

  public static final String GET_MOBILE_COMMODITY_SKU = COMMODITY_COLUMN
      + ",A.ORIGINAL_COMMODITY_CODE,A.COMBINATION_AMOUNT"
      + " FROM   COMMODITY_LIST_VIEW A "
      + "            INNER JOIN SHOP D "
      + "              ON A.SHOP_CODE  = D.SHOP_CODE "
      + "              AND "
      + SqlDialect.getDefault().getTruncSysdate()
      + " "
      + "              BETWEEN COALESCE (D.OPEN_DATETIME , TO_DATE('"
      + DateUtil.toDateString(DateUtil.getMin()) + "', 'yyyy/MM/dd')) "
      + "              AND COALESCE (D.CLOSE_DATETIME , TO_DATE('"
      + DateUtil.toDateString(DateUtil.getMax()) + "', 'yyyy/MM/dd')) "
      + "            LEFT OUTER JOIN STOCK_STATUS E "
      + "              ON A.SHOP_CODE  = E.SHOP_CODE  AND "
      + "                 A.STOCK_STATUS_NO = E.STOCK_STATUS_NO "
      + "            LEFT OUTER JOIN REVIEW_SUMMARY F "
      + "              ON A.SHOP_CODE  = F.SHOP_CODE  AND "
      + "                 A.COMMODITY_CODE  = F.COMMODITY_CODE "
      + "            INNER JOIN DELIVERY_TYPE G "
      + "              ON A.DELIVERY_TYPE_NO = G.DELIVERY_TYPE_NO AND "
      + "                 A.SHOP_CODE = G.SHOP_CODE AND "
      + "                 G.DISPLAY_FLG <> "
      + DisplayFlg.HIDDEN.getValue()
      + "            INNER JOIN COMMODITY_LAYOUT K "
      + "              ON A.SHOP_CODE = K.SHOP_CODE AND "
      + "                 K.PARTS_CODE = '"
      + PartsCode.REVIEWS.getValue()
      + "' WHERE  ((A.SALE_START_DATETIME     <= "
      + SqlDialect.getDefault().getCurrentDatetime() + " AND "
      + SqlDialect.getDefault().getCurrentDatetime()
      + "            <= A.SALE_END_DATETIME) OR "
      + "         (A.RESERVATION_START_DATETIME <= "
      + SqlDialect.getDefault().getCurrentDatetime() + " AND "
      + SqlDialect.getDefault().getCurrentDatetime()
      + "            <= A.RESERVATION_END_DATETIME)) AND "
      + "          A.SALE_FLG = " + SaleFlg.FOR_SALE.getValue()
      + "  AND  " + "         (A.COMMODITY_CODE = ?)  AND  "
      + "          A.SHOP_CODE = ? " + DISPLAY_MOBILE_SQL
      + "ORDER BY A.DISPLAY_ORDER ASC ";

  public static String getCommoditySkuQuery(boolean isForSale) {
    String sql = COMMODITY_COLUMN
        + ",A.ORIGINAL_COMMODITY_CODE,A.COMBINATION_AMOUNT"
        // 20130808 txw add start
        + ",A.TITLE,A.TITLE_EN,A.TITLE_JP,A.DESCRIPTION,A.DESCRIPTION_EN,A.DESCRIPTION_JP,A.KEYWORD,A.KEYWORD_EN,A.KEYWORD_JP "
        // 20130808 txw add end
        + ",  A.IMPORT_COMMODITY_TYPE,A.CLEAR_COMMODITY_TYPE,A.RESERVE_COMMODITY_TYPE1,A.RESERVE_COMMODITY_TYPE2,A.RESERVE_COMMODITY_TYPE3," +
            "A.NEW_RESERVE_COMMODITY_TYPE1,A.NEW_RESERVE_COMMODITY_TYPE2,A.NEW_RESERVE_COMMODITY_TYPE3,A.NEW_RESERVE_COMMODITY_TYPE4,A.NEW_RESERVE_COMMODITY_TYPE5," +
            "A.INNER_QUANTITY,A.COMMODITY_NAME_EN , A.COMMODITY_NAME_JP ,"
        + "   A.COMMODITY_STANDARD1_NAME_EN, A.COMMODITY_STANDARD1_NAME_JP,"
        + "   A.COMMODITY_STANDARD2_NAME_EN,   A.COMMODITY_STANDARD2_NAME_JP, "
        + "   A.STANDARD_DETAIL1_NAME_EN,   A.STANDARD_DETAIL1_NAME_JP, "
        + "   A.STANDARD_DETAIL2_NAME_EN, A.STANDARD_DETAIL2_NAME_JP, "
        + "   A.COMMODITY_DESCRIPTION_PC_EN,         A.COMMODITY_DESCRIPTION_MOBILE_EN,  "
        + "   A.COMMODITY_DESCRIPTION_PC_JP,         A.COMMODITY_DESCRIPTION_MOBILE_JP, "
        + "     A.WEIGHT, A.BRAND_CODE , B.BRAND_NAME ," 
        +"    B.BRAND_ENGLISH_NAME , B.BRAND_JAPANESE_NAME," 
        +"    B.BRAND_DESCRIPTION ,B.BRAND_DESCRIPTION_EN ,B.BRAND_DESCRIPTION_JP ," 
        +"    A.DISPLAY_ORDER FROM   COMMODITY_LIST_VIEW A "
        + "            INNER JOIN SHOP D "
        // postgreSQL start
        // + " ON A.SHOP_CODE = D.SHOP_CODE " + " AND TRUNC(SYSDATE) " +
        // "
        // BETWEEN "
        + "              ON A.SHOP_CODE  = D.SHOP_CODE "
        + "              AND "
        + SqlDialect.getDefault().getTruncSysdate()
        + "              BETWEEN "
        // postgreSQL end
        + "              COALESCE (D.OPEN_DATETIME , TO_DATE('"
        + DateUtil.toDateString(DateUtil.getMin())
        + "', 'yyyy/MM/dd')) "
        + "              AND "
        + "              COALESCE (D.CLOSE_DATETIME , TO_DATE('"
        + DateUtil.toDateString(DateUtil.getMax())
        + "', 'yyyy/MM/dd')) "
        + "            LEFT OUTER JOIN STOCK_STATUS E "
        + "              ON A.SHOP_CODE  = E.SHOP_CODE  AND "
        + "                 A.STOCK_STATUS_NO = E.STOCK_STATUS_NO "
        + "            LEFT OUTER JOIN REVIEW_SUMMARY F "
        + "              ON A.SHOP_CODE  = F.SHOP_CODE  AND "
        + "                 A.COMMODITY_CODE  = F.COMMODITY_CODE "
        + "            INNER JOIN DELIVERY_TYPE G "
        + "              ON A.DELIVERY_TYPE_NO = G.DELIVERY_TYPE_NO AND "
        + "                 A.SHOP_CODE = G.SHOP_CODE AND "
        + "                 G.DISPLAY_FLG <> "
        + DisplayFlg.HIDDEN.getValue()
        + "            INNER JOIN COMMODITY_LAYOUT K "
        + "              ON A.SHOP_CODE = K.SHOP_CODE AND "
        + "                 K.PARTS_CODE = '"
        + PartsCode.REVIEWS.getValue()
        + "'     LEFT OUTER JOIN BRAND B ON A.BRAND_CODE = B.BRAND_CODE";
    if (isForSale) {
      sql = sql + " WHERE  ((A.SALE_START_DATETIME     <= "
          + SqlDialect.getDefault().getCurrentDatetime() + " AND "
          + SqlDialect.getDefault().getCurrentDatetime()
          + "            <= A.SALE_END_DATETIME) OR "
          + "         (A.RESERVATION_START_DATETIME <= "
          + SqlDialect.getDefault().getCurrentDatetime() + " AND "
          + SqlDialect.getDefault().getCurrentDatetime()
          + "            <= A.RESERVATION_END_DATETIME)) AND "
          + "          A.SALE_FLG = " + SaleFlg.FOR_SALE.getValue()
          + "  AND  " + "          A.COMMODITY_CODE = ?  AND  "
          + "          A.SHOP_CODE = ? " + DISPLAY_PC_SQL
          + "ORDER BY  A.DISPLAY_ORDER ASC, A.SKU_CODE ASC ";
    } else {
      sql = sql + "' WHERE    A.COMMODITY_CODE = ?  AND  "
          + "          A.SHOP_CODE = ? " + DISPLAY_PC_SQL
          + "ORDER BY  A.DISPLAY_ORDER ASC, A.SKU_CODE ASC";
    }
    return sql;
  }

  public static final String GET_TOPIC_PATH_LIST = " SELECT     A.CATEGORY_CODE,"
      + "          A.CATEGORY_NAME_PC,"
      //20120521 tuxinwei add start
      + "          A.CATEGORY_NAME_PC_JP,"
      + "          A.CATEGORY_NAME_PC_EN,"
      //20120521 tuxinwei add end
      + "          A.CATEGORY_NAME_MOBILE,"
      + "          A.PARENT_CATEGORY_CODE,"
      + "          A.PATH,"
      + "          A.DEPTH,"
      + "          A.DISPLAY_ORDER,"
      + "          A.COMMODITY_COUNT_PC,"
      + "          A.COMMODITY_COUNT_MOBILE"
      + " FROM     CATEGORY A"
      + "            INNER JOIN CATEGORY_COMMODITY B"
      + "              ON  A.CATEGORY_CODE = B.CATEGORY_CODE"
      + " WHERE    B.SHOP_CODE      = ? AND"
      + "          B.COMMODITY_CODE = ?"
      + " ORDER BY A.PATH DESC, A.DISPLAY_ORDER ASC, A.CATEGORY_CODE ASC";

  public static final String GET_APPLIED_CAMPAIGN_INFO = ""
      + " SELECT A.CAMPAIGN_CODE," 
      + "        A.CAMPAIGN_NAME,A.CAMPAIGN_NAME_EN,A.CAMPAIGN_NAME_JP,"
      + "        A.CAMPAIGN_DISCOUNT_RATE" + " FROM   CAMPAIGN A"
      + "          INNER JOIN CAMPAIGN_COMMODITY B"
      + "            ON A.SHOP_CODE = B.SHOP_CODE AND"
      + "               A.CAMPAIGN_CODE = B.CAMPAIGN_CODE"
      + " WHERE A.CAMPAIGN_START_DATE <= ? "
      + "   AND A.CAMPAIGN_END_DATE >= ? " + "   AND B.SHOP_CODE = ? "
      + "   AND B.COMMODITY_CODE = ? "
      + " ORDER BY A.CAMPAIGN_DISCOUNT_RATE DESC, "
      + "          A.CAMPAIGN_START_DATE DESC, "
      + "          A.CAMPAIGN_CODE ASC";
  
   public static final String GET_CAMPAIGN_MAIN_TYPE = ""
     + " SELECT A.CAMPAIGN_CODE," 
     + "        A.CAMPAIGN_NAME,A.CAMPAIGN_START_DATE,A.CAMPAIGN_END_DATE,"
     + "        A.GIFT_AMOUNT,A.MIN_COMMODITY_NUM,"
     + "        A.CAMPAIGN_TYPE" + " FROM   CAMPAIGN_MAIN A"
     + "  WHERE A.CAMPAIGN_START_DATE <= ? "
     + "   AND A.CAMPAIGN_END_DATE >= ? "
     + "   AND A.CAMPAIGN_TYPE = ? ";
   
   public static final String GET_CAMPAIGN_CONDITION_TYPE = ""
     + " SELECT A.CAMPAIGN_CODE," 
     + "        A.CAMPAIGN_CONDITION_TYPE,A.CAMPAIGN_CONDITION_FLG,A.ATTRIBUTR_VALUE"
     + "        FROM   CAMPAIGN_CONDITION A"
     + " WHERE A.CAMPAIGN_CODE = ? AND A.CAMPAIGN_CONDITION_TYPE = 1 ";
   
   public static final String GET_CAMPAIGN_DOINGS_TYPE = ""
     + " SELECT A.CAMPAIGN_CODE," 
     + "        A.ATTRIBUTR_VALUE"
     + "        FROM  CAMPAIGN_DOINGS A"
     + " WHERE A.CAMPAIGN_CODE = ? AND A.CAMPAIGN_TYPE = 3 ";


  public static final String DELETE_CAMPAIGN_COMMODITY = ""
      + " DELETE FROM CAMPAIGN_COMMODITY "
      + " WHERE SHOP_CODE      = ? AND" + "       CAMPAIGN_CODE  = ? AND"
      + "       COMMODITY_CODE = ?";

  public static final String DELETE_GIFT_COMMODITY = ""
      + " DELETE FROM GIFT_COMMODITY " + " WHERE SHOP_CODE      = ? AND"
      + "       GIFT_CODE      = ? AND" + "       COMMODITY_CODE = ?";

  public static final String DELETE_TAG_COMMODITY = ""
      + " DELETE FROM TAG_COMMODITY "
      + " WHERE SHOP_CODE          = ? AND" + "       TAG_CODE = ? AND"
      + "       COMMODITY_CODE     = ?";

  public static final String GET_CATEGORY_DEPTH_LIST = " SELECT DEPTH FROM CATEGORY GROUP BY DEPTH ORDER BY DEPTH";

  public static String[] getDeleteCategoryQuery() {
    final String[] sql = {
        " DELETE FROM CATEGORY WHERE CATEGORY_CODE = ? ",
        " DELETE FROM CATEGORY_ATTRIBUTE WHERE CATEGORY_CODE = ? ",
        " DELETE FROM CATEGORY_ATTRIBUTE_VALUE WHERE CATEGORY_CODE = ? ",
        " DELETE FROM CATEGORY_COMMODITY WHERE CATEGORY_CODE = ? ", };
    return sql;
  }
  
  public static final String DELETE_INDEX_COMMODITY = " DELETE FROM INDEX_BATCH_COMMODITY ";

  public static final String GET_SUB_CATEGORY = DatabaseUtil
      .getSelectAllQuery(Category.class)
      + " WHERE PARENT_CATEGORY_CODE = ? ORDER BY DISPLAY_ORDER, CATEGORY_CODE";

  public static final String DELETE_CATEGORY_ATTRIBUTE = "DELETE FROM CATEGORY_ATTRIBUTE "
      + "WHERE CATEGORY_CODE = ? AND CATEGORY_ATTRIBUTE_NO = ?";

  public static final String DELETE_CATEGORY_ATTRIBUTE_VALUE = ""
      + "DELETE FROM CATEGORY_ATTRIBUTE_VALUE WHERE CATEGORY_CODE = ? AND CATEGORY_ATTRIBUTE_NO = ?";

  public static final String GET_ROOT_CATEGORY = DatabaseUtil
      .getSelectAllQuery(Category.class)
      + " WHERE DEPTH = '0'";

  public static final String GET_CATEGORY_LIST_FROM_PATH = DatabaseUtil
      .getSelectAllQuery(Category.class)
      + " WHERE CATEGORY_CODE = ? OR "
      + SqlDialect.getDefault().getLikeClausePattern("PATH")
      + " ORDER BY CATEGORY_CODE";

  public static final String CHANGE_CHILDREN_LIST = "SELECT CATEGORY_CODE,"
      + "CATEGORY_NAME_PC"
      //20120521 tuxinwei add start
      + ",CATEGORY_NAME_PC_JP,"
      + "CATEGORY_NAME_PC_EN"
      //20120521 tuxinwei add end
      + ",CATEGORY_NAME_MOBILE,PARENT_CATEGORY_CODE,PATH,DEPTH,"
      + "DISPLAY_ORDER,COMMODITY_COUNT_PC,COMMODITY_COUNT_MOBILE,ORM_ROWID,"
      + "CREATED_USER,CREATED_DATETIME,UPDATED_USER,UPDATED_DATETIME "
      + "FROM CATEGORY" + " WHERE "
      + SqlDialect.getDefault().getLikeClausePattern("PATH") + "";

  public static final String GET_CATEGORY_ATTRIBUTE_LIST = " SELECT   CATEGORY_CODE,"
      + "          CATEGORY_ATTRIBUTE_NO,"
      + "          CATEGORY_ATTRIBUTE_NAME,"
      + "          ORM_ROWID,"
      + "          CREATED_USER,"
      + "          CREATED_DATETIME,"
      + "          UPDATED_USER,"
      + "          UPDATED_DATETIME"
      + " FROM     CATEGORY_ATTRIBUTE"
      + " WHERE    CATEGORY_CODE = ? "
      + " ORDER BY CATEGORY_ATTRIBUTE_NO";

  public static final String UPDATE_CATEGORY_ATTRIBUTE = "UPDATE CATEGORY_ATTRIBUTE SET CATEGORY_ATTRIBUTE_NAME = ?, "
      + "CATEGORY_ATTRIBUTE_NAME_EN = ?, CATEGORY_ATTRIBUTE_NAME_JP = ?," 
      + " UPDATED_USER = ?, UPDATED_DATETIME = ? "
      + "WHERE CATEGORY_CODE = ? AND CATEGORY_ATTRIBUTE_NO = ?";

  public static final String GET_COMMODITY_TAG = DatabaseUtil
      .getSelectAllQuery(TagCommodity.class)
      + " WHERE SHOP_CODE = ? AND TAG_CODE = ?";

  public static final String GET_TAG_COMMODITY_LIST = " SELECT SHOP_CODE, "
      + "        TAG_CODE, " + "        TAG_NAME, "
      + "        DISPLAY_ORDER, " + "        ORM_ROWID, "
      + "        CREATED_USER, " + "        CREATED_DATETIME, "
      + "        UPDATED_USER, " + "        UPDATED_DATETIME "
      + " FROM   TAG " + " WHERE  SHOP_CODE = ? AND "
      + "        TAG_CODE IN(SELECT TAG_CODE "
      + "                    FROM   TAG_COMMODITY "
      + "                    WHERE  SHOP_CODE      = ? AND "
      + "                           COMMODITY_CODE = ?) ";

  public static final String GET_SKU_LIST = DatabaseUtil
      .getSelectAllQuery(CommodityDetail.class)
      + " WHERE SHOP_CODE = ? AND COMMODITY_CODE = ?";

  public static final String REVISION_PRICING_DETAIL = ""
      + " UPDATE COMMODITY_DETAIL CD "
      + " SET    UNIT_PRICE       = COALESCE(CHANGE_UNIT_PRICE, UNIT_PRICE), "
      + "        UPDATED_USER     = ?, "
      + "        UPDATED_DATETIME = "
      + SqlDialect.getDefault().getCurrentDatetime()
      + " WHERE  (SHOP_CODE, COMMODITY_CODE) = (SELECT SHOP_CODE, COMMODITY_CODE "
      + "                                       FROM   COMMODITY_HEADER CH "
      + "                                       WHERE  CHANGE_UNIT_PRICE_DATETIME = "
      + SqlDialect.getDefault().toDate()
      + "                                          AND CD.SHOP_CODE      = CH.SHOP_CODE "
      + "                                          AND CD.COMMODITY_CODE = CH.COMMODITY_CODE) ";

  public static final String REVISION_PRICING_HEADER = ""
      + " UPDATE COMMODITY_HEADER CH "
      + " SET    REPRESENT_SKU_UNIT_PRICE = (SELECT CD.UNIT_PRICE "
      + "                               FROM   COMMODITY_DETAIL CD "
      + "                               WHERE  CH.SHOP_CODE          = CD.SHOP_CODE "
      + "                                  AND CH.COMMODITY_CODE     = CD.COMMODITY_CODE "
      + "                                  AND CH.REPRESENT_SKU_CODE = CD.SKU_CODE), "
      + " UPDATED_USER     = ?, " + " UPDATED_DATETIME = "
      + SqlDialect.getDefault().getCurrentDatetime()
      + " WHERE  (CH.SHOP_CODE, CH.REPRESENT_SKU_CODE) = "
      + "        (SELECT SCH.SHOP_CODE, SCH.REPRESENT_SKU_CODE "
      + "         FROM   COMMODITY_HEADER SCH "
      + "         WHERE  SCH.CHANGE_UNIT_PRICE_DATETIME = "
      + SqlDialect.getDefault().toDate()
      + "           AND  SCH.SHOP_CODE = CH.SHOP_CODE "
      + "           AND  SCH.COMMODITY_CODE = CH.COMMODITY_CODE )";

  public static final String UPDATE_RESERVATIOIN_INFO = ""
      + " UPDATE STOCK SET ONESHOT_RESERVATION_LIMIT = ?, RESERVATION_LIMIT = ?, STOCK_THRESHOLD = ?,"
      + "  UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE SHOP_CODE = ? AND SKU_CODE = ?";

  public static final String GET_ARRIVAL_GOODS_MAIL_INTENDED_COMMODITY = ""
      + " SELECT A.SHOP_CODE, "
      + "        A.SKU_CODE"
      + " FROM   ARRIVAL_GOODS A"
      + "          INNER JOIN COMMODITY_LIST_VIEW B "
      + "            ON A.SHOP_CODE = B.SHOP_CODE AND "
      + "               A.SKU_CODE  = B.SKU_CODE "
      + "          INNER JOIN SHOP C "
      + "            ON A.SHOP_CODE = C.SHOP_CODE "
      + "          INNER JOIN CATEGORY_COMMODITY D "
      + "            ON B.SHOP_CODE = D.SHOP_CODE AND "
      + "               B.COMMODITY_CODE  = D.COMMODITY_CODE "
      + "          INNER JOIN DELIVERY_TYPE E "
      + "            ON B.SHOP_CODE = E.SHOP_CODE AND "
      + "               B.DELIVERY_TYPE_NO = E.DELIVERY_TYPE_NO "
      + " WHERE ((AVAILABLE_SKU_QUANTITY_FUNC(A.SHOP_CODE, A.SKU_CODE) > 0 AND"
      + "       (B.STOCK_MANAGEMENT_TYPE = ? OR B.STOCK_MANAGEMENT_TYPE = ? )) OR "
      + "       (B.STOCK_MANAGEMENT_TYPE = ? OR B.STOCK_MANAGEMENT_TYPE = ? )) AND "
      + "       B.ARRIVAL_GOODS_FLG = ? AND "
      + "       E.DISPLAY_FLG       = ? AND "
      + "       "
      + SqlDialect.getDefault().getTrunc()
      + "("
      + SqlDialect.getDefault().getCurrentDatetime()
      + ") "
      + "             BETWEEN B.SALE_START_DATETIME AND B.SALE_END_DATETIME AND "
      + "       " + SqlDialect.getDefault().getTrunc() + "("
      + SqlDialect.getDefault().getCurrentDatetime() + ") "
      + "             BETWEEN C.OPEN_DATETIME AND C.CLOSE_DATETIME AND "
      + "        B.SALE_FLG = ? " + " GROUP BY A.SHOP_CODE, A.SKU_CODE";

  public static final String GET_ARRIVAL_GOODS_MAIL_INTENDED_CUSTOMER = ""
      + " SELECT SHOP_CODE,"
      + " SKU_CODE,"
      + "        CUSTOMER_CODE, EMAIL"
      + " FROM   ARRIVAL_GOODS"
      + " WHERE SHOP_CODE = ? AND"
      + "       SKU_CODE = ? "
      + "          AND NOT EXISTS (SELECT CUSTOMER_CODE FROM CUSTOMER CM "
      + "            WHERE CM.CUSTOMER_CODE = ARRIVAL_GOODS.CUSTOMER_CODE AND CM.LOGIN_LOCKED_FLG = "
      + LoginLockedFlg.LOCKED.getValue() + "          )"
      + " ORDER BY ORM_ROWID";


  public static final String GET_COMMODITY_VIEW_LIST_COMMODITY = COMMODITY_BASE_COLUMN
      + " FROM   COMMODITY_LIST_VIEW A "
      + " WHERE  A.SHOP_CODE = ? AND A.COMMODITY_CODE = ?";

  public static final String GET_COMMODITY_VIEW_LIST_SKU = COMMODITY_BASE_COLUMN
      + " FROM   COMMODITY_LIST_VIEW A "
      + " WHERE  A.SHOP_CODE = ? AND A.SKU_CODE = ?";

  public static final String GET_CHANGEABLE_RESERVED_TO_ORDER_COMMODITY = ""
      + " SELECT A.SHOP_CODE, A.SKU_CODE "
      + " FROM   COMMODITY_LIST_VIEW A "
      + " WHERE  A.RESERVATION_END_DATETIME < "
      + SqlDialect.getDefault().toDatetime() + " AND "
      + "      ((A.STOCK_QUANTITY > 0 AND "
      + "        A.ALLOCATED_QUANTITY >= 0 AND "
      + "        A.RESERVED_QUANTITY >= 0 AND "
      + "        A.STOCK_QUANTITY -  A.ALLOCATED_QUANTITY > 0) OR "
      + "        A.STOCK_MANAGEMENT_TYPE IN ("
      + StockManagementType.NONE.longValue() + ", "
      + StockManagementType.NOSTOCK.longValue() + ")) ";

  public static final String GET_STOCK_WITH_LOCK = "SELECT * FROM STOCK WHERE SHOP_CODE = ? AND SKU_CODE = ? FOR UPDATE";

  public static String getRankingQuery(String shopCode, boolean isOrder) {
    WebshopConfig config = DIContainer.getWebshopConfig();

    String sql = " SELECT * "
        + " FROM  (SELECT A.SHOP_CODE, "
        + "               A.COMMODITY_CODE, "
        + "               A.COMMODITY_NAME, "
        + "               B.ORDER_RANKING, "
        + "               B.LASTTIME_ORDER_RANKING, "
        + "               B.COUNT_RANKING, "
        + "               B.LASTTIME_COUNT_RANKING "
        + "        FROM COMMODITY_HEADER A "
        + "          INNER JOIN (SELECT * "
        + "                      FROM   POPULAR_RANKING_DETAIL "
        + "                      WHERE POPULAR_RANKING_COUNT_ID ="
        + "                             (SELECT MAX(POPULAR_RANKING_COUNT_ID) "
        + "                              FROM POPULAR_RANKING_HEADER)) B "
        + "            ON A.SHOP_CODE = B.SHOP_CODE AND "
        + "               A.COMMODITY_CODE = B.COMMODITY_CODE ";

    // 10.1.4 10187 追加 ここから
    sql += "WHERE ("
        + SqlDialect.getDefault().getCurrentDatetime()
        + " BETWEEN A.SALE_START_DATETIME AND A.SALE_END_DATETIME OR "
        + SqlDialect.getDefault().getCurrentDatetime()
        + " BETWEEN A.RESERVATION_START_DATETIME AND A.RESERVATION_END_DATETIME)"
        + " AND A.SALE_FLG = "
        + SaleFlg.FOR_SALE.getValue()
        + " AND EXISTS (SELECT /*+ INDEX(SHOP_IX1) */ 'OK' FROM SHOP WHERE SHOP.SHOP_CODE = A.SHOP_CODE"
        + " AND "
        + SqlDialect.getDefault().getCurrentDatetime()
        + " BETWEEN SHOP.OPEN_DATETIME AND SHOP.CLOSE_DATETIME)"
        + " AND EXISTS (SELECT /*+ INDEX(CC CATEGORY_COMMODITY_IX1) */ 'OK' FROM CATEGORY_COMMODITY CC"
        + "               WHERE CC.SHOP_CODE = A.SHOP_CODE AND CC.COMMODITY_CODE = A.COMMODITY_CODE)"
        + " AND EXISTS (SELECT /*+ INDEX(DT DELIVERY_TYPE_IX1) */ 'OK' FROM DELIVERY_TYPE DT"
        + "               WHERE DT.DELIVERY_TYPE_NO = A.DELIVERY_TYPE_NO AND DT.SHOP_CODE = A.SHOP_CODE AND DT.DISPLAY_FLG = 1)";
    // 10.1.4 10187 追加 ここまで

    if (!shopCode.equals(config.getSiteShopCode())) {
      // 10.1.4 10187 修正 ここから
      // sql = sql + " WHERE A.SHOP_CODE = ? ";
      sql = sql + " AND  A.SHOP_CODE = ? ";
      // 10.1.4 10187 修正 ここまで
    }

    if (isOrder) {
      sql = sql + " ORDER BY ORDER_RANKING) ";
    } else {
      sql = sql + " ORDER BY COUNT_RANKING) ";
    }

    // postgreSQL start
    if (DIContainer.getWebshopConfig().isPostgreSQL()) {
      sql = sql + " LIMIT  ? OFFSET 0 ";
    } else {
      // postgreSQL end
      sql = sql + " WHERE ROWNUM BETWEEN 1 AND ? ";
    }

    return sql;
  }

  public static final String GET_EXCEPT_CAMPAIGN_COMMODITY = ""
      + " SELECT A.SHOP_CODE, "
      + "        A.CAMPAIGN_CODE, "
      + "        A.COMMODITY_CODE, "
      + "        A.ORM_ROWID, "
      + "        A.CREATED_USER, "
      + "        A.CREATED_DATETIME, "
      + "        A.UPDATED_USER, "
      + "        A.UPDATED_DATETIME "
      + " FROM   CAMPAIGN_COMMODITY A INNER JOIN COMMODITY_LIST_VIEW B ON A.COMMODITY_CODE = B.COMMODITY_CODE "
      + " WHERE  B.SHOP_CODE = ? "
      + " AND   (CAMPAIGN_CODE IN "
      + "            (SELECT CAMPAIGN_CODE FROM CAMPAIGN "
      + "             WHERE  SHOP_CODE = ? "
      + "             AND    (CAMPAIGN_DISCOUNT_RATE > ? "
      + "                     OR CAMPAIGN_DISCOUNT_RATE = ? AND CAMPAIGN_START_DATE > ? "
      + "                     OR CAMPAIGN_DISCOUNT_RATE = ? AND CAMPAIGN_START_DATE = ? AND CAMPAIGN_CODE < ? "
      + "                    ) "
      + "             AND "
      + SqlDialect.getDefault().getCurrentDatetime()
      + " BETWEEN CAMPAIGN_START_DATE AND CAMPAIGN_END_DATE "
      + "            ) "
      + "        OR "
      + SqlDialect.getDefault().getCurrentDatetime()
      + "               BETWEEN B.RESERVATION_START_DATETIME AND B.RESERVATION_END_DATETIME "
      + "        OR "
      + SqlDialect.getDefault().getCurrentDatetime()
      + "               BETWEEN B.DISCOUNT_PRICE_START_DATETIME AND B.DISCOUNT_PRICE_END_DATETIME "
      + "       ) ";

  public static final String GET_CAMPAIGN_COMMODITY_COUNT = (DIContainer
      .getWebshopConfig().isPostgreSQL() ? "select count(*) from (SELECT COUNT(CAMPAIGN_COMMODITY.COMMODITY_CODE)"
      : " SELECT COUNT(COUNT(CAMPAIGN_COMMODITY.COMMODITY_CODE)) ")
      + " FROM "
      + "   CAMPAIGN_COMMODITY "
      + " INNER JOIN "
      + "   SHOP "
      + " ON "
      + "   SHOP.SHOP_CODE = CAMPAIGN_COMMODITY.SHOP_CODE "
      + " INNER JOIN  "
      + "   COMMODITY_HEADER "
      + " ON "
      + "   COMMODITY_HEADER.SHOP_CODE = CAMPAIGN_COMMODITY.SHOP_CODE "
      + " AND "
      + "   COMMODITY_HEADER.COMMODITY_CODE = CAMPAIGN_COMMODITY.COMMODITY_CODE "
      + " INNER JOIN "
      + "   CATEGORY_COMMODITY "
      + " ON "
      + "   CATEGORY_COMMODITY.SHOP_CODE = CAMPAIGN_COMMODITY.SHOP_CODE "
      + " AND "
      + "   CATEGORY_COMMODITY.COMMODITY_CODE = CAMPAIGN_COMMODITY.COMMODITY_CODE "
      + " INNER JOIN "
      + "   DELIVERY_TYPE "
      + " ON "
      + "   CAMPAIGN_COMMODITY.SHOP_CODE = DELIVERY_TYPE.SHOP_CODE "
      + " AND "
      + "   COMMODITY_HEADER.DELIVERY_TYPE_NO = DELIVERY_TYPE.DELIVERY_TYPE_NO "
      + " WHERE "
      + " CAMPAIGN_COMMODITY.COMMODITY_CODE NOT IN "
      + "   ( "
      + "     SELECT "
      + "       COMMODITY_CODE "
      + "     FROM "
      + "       CAMPAIGN_COMMODITY "
      + "     WHERE "
      + "       (SHOP_CODE,CAMPAIGN_CODE) IN  "
      + "       ( "
      + "         SELECT "
      + "           CAMP.SHOP_CODE, "
      + "           CAMP.CAMPAIGN_CODE "
      + "         FROM "
      + "           CAMPAIGN CAMP "
      + "         INNER JOIN "
      + "           ( "
      + "             SELECT "
      + "               CAMPAIGN_DISCOUNT_RATE, "
      + "               CAMPAIGN_START_DATE, "
      + "               CAMPAIGN_CODE "
      + "             FROM "
      + "               CAMPAIGN "
      + "             WHERE "
      + "               SHOP_CODE = ? "
      + "             AND "
      + "               CAMPAIGN_CODE = ? "
      + "           ) TARGET "
      + "         ON "
      + "           CAMP.CAMPAIGN_DISCOUNT_RATE > TARGET.CAMPAIGN_DISCOUNT_RATE "
      + "           OR "
      + "           ( "
      + "             CAMP.CAMPAIGN_DISCOUNT_RATE = TARGET.CAMPAIGN_DISCOUNT_RATE "
      + "             AND "
      + "             CAMP.CAMPAIGN_START_DATE > TARGET.CAMPAIGN_START_DATE "
      + "           ) "
      + "           OR "
      + "           ( "
      + "             CAMP.CAMPAIGN_DISCOUNT_RATE = TARGET.CAMPAIGN_DISCOUNT_RATE "
      + "             AND "
      + "             CAMP.CAMPAIGN_START_DATE = TARGET.CAMPAIGN_START_DATE "
      + "             AND "
      + "             CAMP.CAMPAIGN_CODE < TARGET.CAMPAIGN_CODE "
      + "           ) "
      + "         WHERE "
      + SqlDialect.getDefault().getCurrentDatetime()
      + "           BETWEEN CAMP.CAMPAIGN_START_DATE AND CAMP.CAMPAIGN_END_DATE "
      + "     ) "
      + "   ) "
      + " AND "
      + "   CAMPAIGN_COMMODITY.SHOP_CODE = ? "
      + " AND "
      + "   CAMPAIGN_COMMODITY.CAMPAIGN_CODE = ? "
      + " AND "
      + SqlDialect.getDefault().getTrunc()
      + "("
      + SqlDialect.getDefault().getCurrentDatetime()
      + ")    BETWEEN COALESCE(SHOP.OPEN_DATETIME,"
      + SqlDialect.getDefault().toDate(
          DateUtil.toDateString(DateUtil.getMin()))
      + "            ) "
      + "    AND COALESCE(SHOP.CLOSE_DATETIME,"
      + SqlDialect.getDefault().toDate(
          DateUtil.toDateString(DateUtil.getMax()))
      + "        ) "
      + " AND "
      + "   COMMODITY_HEADER.SALE_FLG = "
      + SaleFlg.FOR_SALE.getValue()
      + " AND "
      + "   ( "
      + SqlDialect.getDefault().getCurrentDatetime()
      + "   BETWEEN COALESCE(COMMODITY_HEADER.SALE_START_DATETIME,"
      + SqlDialect.getDefault().toDate(
          DateUtil.toDateString(DateUtil.getMin()))
      + "           ) "
      + "   AND COALESCE(COMMODITY_HEADER.SALE_END_DATETIME,"
      + SqlDialect.getDefault().toDate(
          DateUtil.toDateString(DateUtil.getMax()))
      + "       ) "
      + "   OR "
      + SqlDialect.getDefault().getCurrentDatetime()
      + "   BETWEEN COALESCE(COMMODITY_HEADER.RESERVATION_START_DATETIME,"
      + SqlDialect.getDefault().toDate(
          DateUtil.toDateString(DateUtil.getMin()))
      + "           ) "
      + "   AND COMMODITY_HEADER.RESERVATION_END_DATETIME"
      + "   ) "
      + " AND "
      + "   COMMODITY_HEADER.DISPLAY_CLIENT_TYPE IN ("
      + DisplayClientType.ALL.getValue()
      + ", ? ) "
      + " AND "
      + "   DELIVERY_TYPE.DISPLAY_FLG = "
      + DisplayFlg.VISIBLE.getValue()
      + " GROUP BY CATEGORY_COMMODITY.SHOP_CODE, CATEGORY_COMMODITY.COMMODITY_CODE "
      + (DIContainer.getWebshopConfig().isPostgreSQL() ? " ) t" : "");

  public static final String LOAD_SEARCH_KEYWORD_LOG = DatabaseUtil
      .getSelectAllQuery(SearchKeywordLog.class)
      + " WHERE SEARCH_DATE = ? AND SEARCH_KEY = ? AND SEARCH_WORD = ?";

  public static String getCategoryTreeList(String inSql,
      boolean categoryCodeFlg) {
    String categoryTreeListSql = " SELECT CATEGORY_CODE "
        + "       ,CATEGORY_NAME_PC " 
        //20120521 tuxinwei add start
        + "       ,CATEGORY_NAME_PC_JP "
        + "       ,CATEGORY_NAME_PC_EN "
        //20120521 tuxinwei add end
        + "       ,CATEGORY_NAME_MOBILE "
        + "       ,PARENT_CATEGORY_CODE " + "       ,PATH "
        + "       ,DEPTH " + "       ,DISPLAY_ORDER "
        + "       ,COMMODITY_COUNT_PC "
        + "       ,COMMODITY_COUNT_MOBILE " + "   FROM CATEGORY "
        + "  WHERE PATH IN( " + " SELECT PATH " + "   FROM CATEGORY "
        + "  WHERE " + inSql + " ) ";
    // wjh modify start
    // if (!categoryCodeFlg) {
    categoryTreeListSql += "  AND  CATEGORY_CODE = ?";
    // }
    categoryTreeListSql += "  UNION  " + " SELECT CATEGORY_CODE "
        + "       ,CATEGORY_NAME_PC " 
        //20120521 tuxinwei add start
        + "       ,CATEGORY_NAME_PC_JP "
        + "       ,CATEGORY_NAME_PC_EN "
        //20120521 tuxinwei add end
        + "       ,CATEGORY_NAME_MOBILE "
        + "       ,PARENT_CATEGORY_CODE " + "       ,PATH "
        + "       ,DEPTH " + "       ,DISPLAY_ORDER "
        + "       ,COMMODITY_COUNT_PC "
        + "       ,COMMODITY_COUNT_MOBILE " + "   FROM CATEGORY "
        + "  WHERE PARENT_CATEGORY_CODE = ? ";
    if (categoryCodeFlg) {
      categoryTreeListSql += "  OR  CATEGORY_CODE = ?";
    }

    categoryTreeListSql += "  ORDER BY DEPTH "
        + "          ,DISPLAY_ORDER DESC "
        + "          ,CATEGORY_CODE DESC ";
    // wjh modify end

    return categoryTreeListSql;
  }

  public static String getTopicPathList(String inSql) {
    String topicPathListSql = " SELECT CATEGORY_CODE "
        + "       ,CATEGORY_NAME_PC " 
        //20120521 tuxinwei add start
        + "       ,CATEGORY_NAME_PC_JP "
        + "       ,CATEGORY_NAME_PC_EN "
        //20120521 tuxinwei add end
        + "       ,CATEGORY_NAME_MOBILE "
        + "       ,PARENT_CATEGORY_CODE " + "       ,PATH "
        + "       ,DEPTH " + "       ,DISPLAY_ORDER "
        + "       ,COMMODITY_COUNT_PC "
        + "       ,COMMODITY_COUNT_MOBILE " + "   FROM CATEGORY "
        + "  WHERE " + inSql + "  ORDER BY DEPTH ";

    return topicPathListSql;
  }

  public static String getCommodityDetailCategory(String likeSql) {
    String commodityDetailCategoryCode = " SELECT DISTINCT A.CATEGORY_CODE "
        + "       ,A.CATEGORY_NAME_PC "
        //20120521 tuxinwei add start
        + "       ,A.CATEGORY_NAME_PC_JP "
        + "       ,A.CATEGORY_NAME_PC_EN "
        //20120521 tuxinwei add end
        + "       ,A.CATEGORY_NAME_MOBILE "
        + "       ,A.PARENT_CATEGORY_CODE "
        + "       ,A.PATH "
        + "       ,A.DEPTH "
        + "       ,A.DISPLAY_ORDER "
        + "       ,A.COMMODITY_COUNT_PC "
        + "       ,A.COMMODITY_COUNT_MOBILE "
        + "   FROM CATEGORY A "
        + "   JOIN CATEGORY_COMMODITY B ON A.CATEGORY_CODE = B.CATEGORY_CODE "
        + "  WHERE B.SHOP_CODE = ? "
        + "    AND B.COMMODITY_CODE = ? "
        + "    AND "
        + likeSql
        + "  ORDER BY A.DEPTH DESC "
        + "          ,A.DISPLAY_ORDER " + "          ,A.CATEGORY_CODE ";

    return commodityDetailCategoryCode;
  }

  public static final String GET_COMMODITY_DETAIL_CATEGORY_CODE = " SELECT DISTINCT A.CATEGORY_CODE "
      + "       ,A.CATEGORY_NAME_PC "
      + "     ,A.CATEGORY_NAME_PC_EN"
      + "     ,A.CATEGORY_NAME_PC_JP"
      + "       ,A.CATEGORY_NAME_MOBILE "
      + "       ,A.PARENT_CATEGORY_CODE "
      + "       ,A.PATH "
      + "       ,A.DEPTH "
      + "       ,A.DISPLAY_ORDER "
      + "       ,A.COMMODITY_COUNT_PC "
      + "       ,A.COMMODITY_COUNT_MOBILE "
      + "   FROM CATEGORY A "
      + "   JOIN CATEGORY_COMMODITY B ON A.CATEGORY_CODE = B.CATEGORY_CODE "
      + "  WHERE B.SHOP_CODE = ? "
      + "    AND B.COMMODITY_CODE = ? "
      + "  ORDER BY A.DEPTH DESC "
      + "          ,A.DISPLAY_ORDER "
      + "          ,A.CATEGORY_CODE ";

  public static final String GET_ROOT_CATEGORY_TREE = " SELECT CATEGORY_CODE "
      + "       ,CATEGORY_NAME_PC "
      //20120521 tuxinwei add start
      + "       ,CATEGORY_NAME_PC_JP "
      + "       ,CATEGORY_NAME_PC_EN "
      //20120521 tuxinwei add end
      + "       ,CATEGORY_NAME_MOBILE "
      + "       ,PARENT_CATEGORY_CODE "
      + "       ,PATH "
      + "       ,DEPTH "
      + "       ,DISPLAY_ORDER "
      + "       ,COMMODITY_COUNT_PC "
      + "       ,COMMODITY_COUNT_MOBILE "
      + "   FROM CATEGORY "
      + "  WHERE DEPTH < 2 "
      + "  ORDER BY DEPTH "
      + "          ,DISPLAY_ORDER DESC" + "          ,CATEGORY_CODE DESC";

  // add by os012 20111222 start
  /**
   * 根据SHOP_CODE, SKU_CODE获取stock信息
   */
  public static final String GET_ALL_STOCK_LIST = "SELECT A.SHOP_CODE,  A.SKU_CODE, A.COMMODITY_CODE, A.STOCK_QUANTITY, A.ALLOCATED_QUANTITY, "
      + " A.RESERVED_QUANTITY, A.RESERVATION_LIMIT, A.ONESHOT_RESERVATION_LIMIT, "
      + " A.STOCK_THRESHOLD,  A.ORM_ROWID,  A.CREATED_USER,  A.CREATED_DATETIME,  A.UPDATED_USER, "
      + "  A.UPDATED_DATETIME, ALLOCATED_TMALL, SHARE_RATIO, (A.STOCK_TOTAL+ (CASE WHEN B.ADD_STOCK_TOTAL IS NULL THEN 0 ELSE B.ADD_STOCK_TOTAL END)) AS STOCK_TOTAL,"
      + "  A.STOCK_TMALL, A.SHARE_RECALC_FLAG "
      + " FROM STOCK A LEFT JOIN STOCK_TEMP B ON  A.SHOP_CODE=B.SHOP_CODE AND A.SKU_CODE=B.SKU_CODE";
  /**
   * 根据SHOP_CODE, SKU_CODE获取stock信息
   */
  public static final String GET_STOCK_LIST = "SELECT SHOP_CODE, SKU_CODE, COMMODITY_CODE, STOCK_QUANTITY, ALLOCATED_QUANTITY, "
      + "   RESERVED_QUANTITY, RESERVATION_LIMIT, ONESHOT_RESERVATION_LIMIT, "
      + "   STOCK_THRESHOLD, ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER,"
      + "   UPDATED_DATETIME, ALLOCATED_TMALL, SHARE_RATIO, STOCK_TOTAL, "
      + "   STOCK_TMALL, SHARE_RECALC_FLAG"
      + "  FROM STOCK WHERE  "
      + " SHOP_CODE= ? " + "  AND SKU_CODE= ? ";
  // add by os011 start 20120110

  public static final String STOCK_TEMP_LIST = "SELECT A.SHOP_CODE, A.SKU_CODE, "
      + "   A.add_stock_total, A.add_stock_ec, A.add_stock_tmall, "
      + "   A.add_stock_threshold, A.ORM_ROWID, A.CREATED_USER, A.CREATED_DATETIME, A.UPDATED_USER,"
      + "   A.UPDATED_DATETIME" + "  FROM  STOCK_TEMP A  ";

  public static final String STOCK_INFO_LIST = "SELECT A.SHOP_CODE, A.SKU_CODE, A.COMMODITY_CODE, A.STOCK_QUANTITY, A.ALLOCATED_QUANTITY, "
      + "   A.RESERVED_QUANTITY, A.RESERVATION_LIMIT, A.ONESHOT_RESERVATION_LIMIT, "
      + "   A.STOCK_THRESHOLD, A.ORM_ROWID, A.CREATED_USER, A.CREATED_DATETIME, A.UPDATED_USER,"
      + "   A.UPDATED_DATETIME, A.ALLOCATED_TMALL, A.SHARE_RATIO, A.STOCK_TOTAL AS ORIGINAL_STOCK, (A.STOCK_TOTAL+(CASE WHEN B.add_stock_total IS NULL THEN 0 ELSE B.add_stock_total END)) AS STOCK_TOTAL ,B.add_stock_total, "
      + "   B.add_stock_ec, B.add_stock_tmall, B.add_stock_threshold, "
      + "   A.STOCK_TMALL, A.SHARE_RECALC_FLAG"
      + "  FROM STOCK A INNER JOIN STOCK_TEMP B ON A.SHOP_CODE=B.SHOP_CODE AND A.SKU_CODE=B.SKU_CODE  ";

  public static final String STOCK_AND_STOCK_TEMP_INFO = "SELECT A.SHOP_CODE, A.SKU_CODE, A.COMMODITY_CODE, A.STOCK_QUANTITY, A.ALLOCATED_QUANTITY, "
      + "   A.RESERVED_QUANTITY, A.RESERVATION_LIMIT, A.ONESHOT_RESERVATION_LIMIT, "
      + "   A.STOCK_THRESHOLD, A.ORM_ROWID, A.CREATED_USER, A.CREATED_DATETIME, A.UPDATED_USER,"
      + "   A.UPDATED_DATETIME, A.ALLOCATED_TMALL, A.SHARE_RATIO, A.STOCK_TOTAL,B.add_stock_total, "
      + "   B.add_stock_ec, B.add_stock_tmall, B.add_stock_threshold, "
      + "   A.STOCK_TMALL, A.SHARE_RECALC_FLAG"
      + "  FROM STOCK A LEFT JOIN STOCK_TEMP B ON A.SHOP_CODE=B.SHOP_CODE AND A.SKU_CODE=B.SKU_CODE ";

  public static final String STOCK_STEMP_INFO = "SELECT SHOP_CODE FROM STOCK_TEMP WHERE SHOP_CODE=? AND SKU_CODE =? ";

  public static final String UPDATE_STOCK_TEMP = "UPDATE STOCK_TEMP "
      + " SET add_stock_ec=?, add_stock_tmall=? , UPDATED_USER=?, UPDATED_DATETIME=? WHERE SHOP_CODE= ?   AND SKU_CODE= ? ";
  
  public static final String UPDATE_STOCK_TEMP_STOCK_TOTAL = "UPDATE STOCK_TEMP "
    + " SET add_stock_ec=?, add_stock_tmall=? , add_stock_total = ?,  UPDATED_USER=?, UPDATED_DATETIME=? WHERE SHOP_CODE= ?   AND SKU_CODE= ? ";

  public static final String UPDATE_STOCK_TEMP_THRESHOLD = "UPDATE STOCK_TEMP "
      + " SET add_stock_ec=?, add_stock_tmall=? , add_stock_threshold=?,UPDATED_USER=?, UPDATED_DATETIME=? WHERE SHOP_CODE= ?   AND SKU_CODE= ? ";

  public static final String INSERT_STOCK_TEMP = "INSERT INTO STOCK_TEMP "
      + " (SHOP_CODE,SKU_CODE,add_stock_total,add_stock_ec, add_stock_tmall ,add_stock_threshold, ORM_ROWID, CREATED_USER, CREATED_DATETIME,UPDATED_USER, UPDATED_DATETIME) values (?,?,?,?,?,?,?,?,?,?,?)";

  public static final String DELETE_STOCK_TEMP_ALL = " DELETE FORM STOCK_TEMP ";
  
  public static final String GET_COMBINE_COMMODITY = " SELECT * FROM TMALL_STOCK_ALLOCATION WHERE COMMODITY_CODE = ?";

  public static final String DELETE_STOCK_TEMP = " DELETE FROM STOCK_TEMP WHERE SHOP_CODE=? AND SKU_CODE=? ";
  
  public static final String GET_TMLL_COMBINE_COMMODITY = " SELECT COMBINATION_AMOUNT ,COMMODITY_CODE FROM C_COMMODITY_HEADER WHERE ORIGINAL_COMMODITY_CODE = ? ";
  
  public static final String GET_TMALL_STOCK_ALLOCATION = " SELECT * FROM TMALL_STOCK_ALLOCATION WHERE ORIGINAL_COMMODITY_CODE = ? ";
  
  
  // 在库品区分check
  public static final String ON_STOCK_FLG_CHECK = " select b.on_stock_flag as stock_flg from c_commodity_detail   a left join   c_commodity_ext b  on  a.shop_code =b.shop_code and a.commodity_code=b.commodity_code where a.SKU_CODE= ?  ";
  // 更新在库区分
  public static final String UPDATE_ON_STOCK_FLAG = "UPDATE C_COMMODITY_EXT SET ON_STOCK_FLAG = ? , UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
  // 更新商品Header中的在库管理区分stock_management_type
  public static final String UPDATE_STOCK_MANAGEMENT_TYPE = "UPDATE COMMODITY_HEADER SET STOCK_MANAGEMENT_TYPE = ? , UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
  // 更新C商品Header的导出标志
  public static final String UPDATE_EXPORT_FLG = "UPDATE C_COMMODITY_HEADER SET EXPORT_FLAG_ERP = '1' ,EXPORT_FLAG_WMS='1', UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
  // 更新STOCK 库存分配比例
  public static final String UPDATE_STOCK_SHARE_RATIO = "UPDATE STOCK SET SHARE_RATIO = ? , UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";

  // add by os011 end 20120110
  /**
   * 根据SKU_CODE, 上传标志获取没有上传到淘宝的SKu信息
   */
  public static final String GET_TMALL_UP_SKU_CODE_LIST = "SELECT SHOP_CODE,SKU_CODE,ADD_STOCK_THRESHOLD,(CASE WHEN add_stock_total IS NULL THEN 0 ELSE add_stock_total END) AS add_stock_total,"
      + " (CASE WHEN add_stock_ec IS NULL THEN 0 ELSE add_stock_ec END) AS add_stock_ec,"
      + " (CASE WHEN add_stock_tmall IS NULL THEN 0 ELSE add_stock_tmall END) AS add_stock_tmall,"
      + " (CASE WHEN add_stock_threshold IS NULL THEN 0 ELSE add_stock_threshold END) AS add_stock_threshold"
      + "  FROM STOCK_TEMP WHERE SHOP_CODE= ? ";

  /**
   * 根据SHOP_CODE, SKU_CODE获取未发货購入商品数信息
   */
  public static final String GET_PURCHASING_AMOUNT_FROM_SHIPPING = "SELECT SUM( A.PURCHASING_AMOUNT )PURCHASING_AMOUNT FROM  "
      + "SHIPPING_DETAIL AS A,  SHIPPING_HEADER AS B, ORDER_HEADER  AS C WHERE   A.SHIPPING_NO = B.SHIPPING_NO AND "
      + "C.ORDER_NO = B.ORDER_NO AND  B.SHIPPING_STATUS = ?  AND C.ORDER_STATUS IN (?,?) "
      + " AND A.SHOP_CODE= ?   AND A.SKU_CODE= ? ";

  /**
   * 根据SHOP_CODE, SKU_CODE更新stock里ec与tmall在库信息
   */
  public static final String UPDATE_STOCK_LIST = "UPDATE STOCK "
      + " SET  STOCK_QUANTITY=?, STOCK_TMALL=? ,   UPDATED_USER=?, UPDATED_DATETIME=? WHERE SHOP_CODE= ?   AND SKU_CODE= ? ";

  public static final String UPDATE_STOCK_THRESHOLD = "UPDATE STOCK "
      + " SET  STOCK_QUANTITY=?, STOCK_TMALL=? , stock_threshold=?,  UPDATED_USER=?, UPDATED_DATETIME=? WHERE SHOP_CODE= ?   AND SKU_CODE= ? ";

  /**
   * 根据SHOP_CODE, SKU_CODE更新stock里总在库信息
   */
  public static final String UPDATE_STOCK_STOCK_TOTAL = "UPDATE STOCK "
      + " SET STOCK_TOTAL=? ,   UPDATED_USER=?, UPDATED_DATETIME=? WHERE SHOP_CODE= ?   AND SKU_CODE= ? ";

  // add by os012 20111227 end

  /** 有効在庫取得ファンクション名の定義 */
  public static final String AVAILABLE_SKU_QUANTITY_FUNC = "AVAILABLE_SKU_QUANTITY_FUNC(?, ?)";

  /** 有効在庫取得ファンクション名の定義(予約可能数用) */
  public static final String AVAILABLE_SKU_RESERVING_FUNC = "AVAILABLE_SKU_RESERVING_FUNC(?, ?)";

  /** 有効在庫取得クエリ */
  public static final String GET_AVAILABLE_SKU_QUANTITY = " SELECT "
      + AVAILABLE_SKU_QUANTITY_FUNC + " "
      + SqlDialect.getDefault().getDummyTable();
  
  public static final String GET_ALL_HISTORY_IMG = " SELECT COUNT(*) FROM DESCRIBE_IMG_HISTORY WHERE COMMODITY_CODE = ? AND LANG = ?";
  
  public static final String GET_ALL_USE_QUANTITY = "SELECT CASE WHEN SUM(TSC.STOCK_QUANTITY-TSC.allocated_quantity) IS NULL THEN 0 ELSE SUM(TSC.STOCK_QUANTITY-TSC.allocated_quantity) END AS TOTAL_QUANTITY   " 
      + " FROM TMALL_SUIT_COMMODITY TSC "
      + " INNER JOIN SET_COMMODITY_COMPOSITION SCC ON TSC.COMMODITY_CODE = SCC.COMMODITY_CODE "
  		+ " where scc.child_commodity_code = ? ";
  
  public static final String GET_ALL_USE_QUANTITY_BUT_SELF = "SELECT CASE WHEN SUM(TSC.STOCK_QUANTITY-TSC.allocated_quantity) IS NULL THEN 0 ELSE SUM(TSC.STOCK_QUANTITY-TSC.allocated_quantity) END AS TOTAL_QUANTITY   "
    //+ " + (select allocated_quantity from TMALL_SUIT_COMMODITY tsc2 where tsc2.commodity_code = ? ) AS TOTAL_QUANTITY " 
    + " FROM TMALL_SUIT_COMMODITY TSC "
    + " INNER JOIN SET_COMMODITY_COMPOSITION SCC ON TSC.COMMODITY_CODE = SCC.COMMODITY_CODE "
    + " where scc.child_commodity_code = ? and TSC.commodity_code <> ?";

  /** 有効在庫取得クエリ(予約可能数用) */
  public static final String GET_RESERVATION_AVAILABLE_STOCK = " SELECT "
      + AVAILABLE_SKU_RESERVING_FUNC + " "
      + SqlDialect.getDefault().getDummyTable();

  // 10.1.4 K00175 追加 ここから
  /** ショップコード、商品コードを指定して商品詳細のセットを取得するクエリ */
  public static final String GET_COMMODITY_DETAILS_BY_COMMODITY_CODE = SqlUtil
      .getSelectAllQuery(CommodityDetail.class)
      + " CD WHERE CD.SHOP_CODE = ? AND CD.COMMODITY_CODE = ?";

  // 10.1.4 K00175 追加 ここまで
  // 10.1.7 10327 追加 ここから
  public static final String COUNT_NOT_MODIFIABLE_SKU_QUERY = " SELECT COUNT(*) FROM COMMODITY_DETAIL CD "
      + " WHERE CD.SHOP_CODE = ? AND CD.COMMODITY_CODE = ? "
      + " AND EXISTS (SELECT * FROM SHIPPING_DETAIL SD "
      + " INNER JOIN SHIPPING_HEADER SH ON SH.SHIPPING_NO = SD.SHIPPING_NO "
      + " WHERE SD.SHOP_CODE = CD.SHOP_CODE AND SD.SKU_CODE = CD.SKU_CODE "
      + " AND SH.FIXED_SALES_STATUS = "
      + FixedSalesStatus.NOT_FIXED.getValue()
      + " AND SH.SHIPPING_STATUS <> "
      + ShippingStatus.CANCELLED.getValue() + ")";

  // 10.1.7 10327 追加 ここまで
  // 11.12.27 OS014 追加 START
  public static final String CYNCHRO_EC_INFO_QUERY = " select * from c_commodity_header where sync_flag_ec="
      + CommoditySyncFlag.CAN_SYNC.getValue();
  
  public static final String CYNCHRO_EC_INFOBYCHECKBOX_QUERY = " select * from c_commodity_header where sync_flag_ec="
    + CommoditySyncFlag.CAN_SYNC.getValue() + "and COMMODITY_CODE = ?";

  public static final String CYNCHRO_TMALL_INFO_QUERY = " SELECT * FROM C_COMMODITY_HEADER CH INNER JOIN " +
      " C_COMMODITY_DETAIL CD ON CH.COMMODITY_CODE=CD.COMMODITY_CODE AND CH.REPRESENT_SKU_CODE=CD.SKU_CODE " +
      " INNER JOIN C_COMMODITY_EXT CE ON CE.COMMODITY_CODE=CH.COMMODITY_CODE " +
      "WHERE CD.TMALL_USE_FLG = 1 AND CH.COMMODITY_TYPE =0 AND CE.ON_STOCK_FLAG =1 AND CH.SYNC_FLAG_TMALL="
      + CommoditySyncFlag.CAN_SYNC.getValue();
  
  public static final String CYNCHRO_TMALL_INFOBYCHECKBOX_QUERY = " SELECT * FROM C_COMMODITY_HEADER CH INNER JOIN " +
  " C_COMMODITY_DETAIL CD ON CH.COMMODITY_CODE=CD.COMMODITY_CODE AND CH.REPRESENT_SKU_CODE=CD.SKU_CODE " +
  " INNER JOIN C_COMMODITY_EXT CE ON CE.COMMODITY_CODE=CH.COMMODITY_CODE " +
  "WHERE CD.TMALL_USE_FLG = 1 AND CH.COMMODITY_TYPE =0 AND CE.ON_STOCK_FLAG =1 AND CH.SYNC_FLAG_TMALL="
  + CommoditySyncFlag.CAN_SYNC.getValue()+ "and CH.COMMODITY_CODE = ?";

  public static final String CATEGORY_QUERY = "select b.category_id_tmall from category_commodity  a,category b where a.category_code=b.category_code and a.commodity_code=?";

  public static final String CYNCHRO_EC_ISCYNCHRO_INFO_UPDATE = "update c_commodity_header set sync_flag_ec=?,sync_time_ec=? where shop_code=? and commodity_code=?";

  public static final String CYNCHRO_TMALL_ISCYNCHRO_UPDATE = "update c_commodity_header set sync_flag_tmall = 2,sync_time_tmall=? where shop_code=? and commodity_code=?";
  public static final String CC_CYNCHRO_TMALL_INFO_QUERY = " SELECT  A.SHOP_CODE, A.COMMODITY_CODE, A.COMMODITY_NAME, A.COMMODITY_NAME_EN,"
      + " A.REPRESENT_SKU_CODE, A.REPRESENT_SKU_UNIT_PRICE, A.COMMODITY_DESCRIPTION1, "
      + " A.COMMODITY_DESCRIPTION2, A.COMMODITY_DESCRIPTION3, A.COMMODITY_DESCRIPTION_SHORT, "
      + "  A.COMMODITY_SEARCH_WORDS,   A.DISCOUNT_PRICE_START_DATETIME, "
      + "  A.DISCOUNT_PRICE_END_DATETIME, A.STANDARD1_ID, A.STANDARD1_NAME, A.STANDARD2_ID, "
      + "  A.STANDARD2_NAME, A.SALE_FLG_EC, A.RETURN_FLG,A.WARNING_FLAG, A.LEAD_TIME, "
      + "  case when A.SALE_FLG_EC='0' or A.SALE_FLG_EC is null then '销售停止' else  '销售中' end SALE_FLAG,"
      + "  A.SPEC_FLAG, A.BRAND_CODE, A.TMALL_COMMODITY_ID,A.TMALL_REPRESENT_SKU_PRICE,A."
      + "  TMALL_CATEGORY_ID,A.SUPPLIER_CODE,A.BUYER_CODE,A.ORIGINAL_PLACE,A."
      + "  INGREDIENT_NAME1,A.INGREDIENT_VAL1,A.INGREDIENT_NAME2,A.INGREDIENT_VAL2,A."
      + "  INGREDIENT_NAME3,A.INGREDIENT_VAL3,A.INGREDIENT_NAME4,A.INGREDIENT_VAL4,A."
      + "  INGREDIENT_NAME5,A.INGREDIENT_VAL5,A.INGREDIENT_NAME6,A.INGREDIENT_VAL6,A."
      + "  INGREDIENT_NAME7,A.INGREDIENT_VAL7,A.INGREDIENT_NAME8,A.INGREDIENT_VAL8,A."
      + "  INGREDIENT_NAME9,A.INGREDIENT_VAL9,A.INGREDIENT_NAME10,A.INGREDIENT_VAL10,A."
      + " INGREDIENT_NAME11,A.INGREDIENT_VAL11,A.INGREDIENT_NAME12,A.INGREDIENT_VAL12,A."
      + "  INGREDIENT_NAME13,A.INGREDIENT_VAL13,A.INGREDIENT_NAME14,A.INGREDIENT_VAL14,A."
      + "  INGREDIENT_NAME15,A.INGREDIENT_VAL15,A.MATERIAL1,A.MATERIAL2,A.MATERIAL3,A."
      + "  MATERIAL4,A.MATERIAL5,A.MATERIAL6,A.MATERIAL7,A.MATERIAL8,A.MATERIAL9,A."
      + "  MATERIAL10,A.MATERIAL11,A.MATERIAL12,A.MATERIAL13,A.MATERIAL14,A.MATERIAL15,A."
      + "  SHELF_LIFE_FLAG,A.SHELF_LIFE_DAYS,A.BIG_FLAG,A.SYNC_TIME_EC,A.SYNC_TIME_TMALL,A."
      + "  SYNC_FLAG_EC,A.SYNC_FLAG_TMALL,A.SYNC_USER_EC,A.SYNC_USER_TMALL,A."
      + "  EXPORT_FLAG_ERP,A.EXPORT_FLAG_WMS,A.ORM_ROWID,A.CREATED_USER,A.CREATED_DATETIME,A."
      + "  UPDATED_USER, to_char(A.UPDATED_DATETIME,'YYYY'||'/'||'MM'||'/'||'DD'||' '||'HH24'||':'||'mm'||':'||'ss') UPDATED_TIME, "
      + "    to_char(A.SALE_START_DATETIME,'YYYY'||'/'||'MM'||'/'||'DD')SALE_START_DATETIME ,"
      + "    to_char(A.SALE_END_DATETIME,'YYYY'||'/'||'MM'||'/'||'DD') SALE_END_DATETIME,"
      + "    A.CATEGORY_SEARCH_PATH,A.       CATEGORY_ATTRIBUTE_VALUE,  "
      + "  case when B.FIXED_PRICE_FLAG='1' then '定价' else '非定价' end FIXED_PRICE_FLAG, "
      + " B.SUGGESTE_PRICE,   B.PURCHASE_PRICE,   B.UNIT_PRICE, CASE WHEN B.USE_FLG='1' THEN '使用' ELSE '没使用' END USE_FLG,"
      // 2014/05/07 京东WBS对应 ob_姚 add start
      + " CASE WHEN B.jd_use_flg='1' THEN '使用' ELSE '没使用' END JD_USE_FLG,"
      // 2014/05/07 京东WBS对应 ob_姚 add end
      + " CASE WHEN B.TMALL_USE_FLG='1' THEN '使用' ELSE '没使用' END TMALL_USE_FLG,   B.DISCOUNT_PRICE,   B.TMALL_UNIT_PRICE,   B.TMALL_DISCOUNT_PRICE, "
      + " B.MIN_PRICE FROM C_COMMODITY_HEADER A, C_COMMODITY_DETAIL B left join commodity_price_change_history CPCH on B.commodity_code = cpch.commodity_code, C_COMMODITY_EXT CE " +
        " WHERE A.represent_sku_code=B.sku_code AND A.COMMODITY_TYPE =0 AND CE.COMMODITY_CODE=A.COMMODITY_CODE AND CE.ON_STOCK_FLAG =1 AND A.SYNC_FLAG_TMALL = 1 AND TMALL_USE_FLG = 1"
      + " AND (not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where A.commodity_code = commodity_code) " 
      + " OR (not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where CPCH.commodity_code = commodity_code and CPCH.submit_time < submit_time) AND CPCH.review_or_not_flg = 1) "
      + " OR (not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where CPCH.commodity_code = commodity_code and CPCH.submit_time < submit_time) AND ((CPCH.ec_profit_margin IS NULL OR CPCH.ec_profit_margin >= 0) AND (CPCH.ec_special_profit_margin IS NULL OR CPCH.ec_special_profit_margin >= 0) AND (CPCH.tmall_profit_margin IS NULL OR CPCH.tmall_profit_margin >= 0) AND (CPCH.jd_profit_margin IS NULL OR CPCH.jd_profit_margin >= 0))) "
      + " OR (not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where CPCH.commodity_code = commodity_code and CPCH.submit_time < submit_time) AND exists(select 1 from C_COMMODITY_HEADER where A.commodity_code = commodity_code AND clear_commodity_type = 1))) ";

  // 2014/05/02 京东WBS对应 ob_姚 add start
  public static final String CC_CYNCHRO_JD_INFO_QUERY = " SELECT  A.SHOP_CODE, A.COMMODITY_CODE, A.COMMODITY_NAME, A.COMMODITY_NAME_EN,"
    + " A.REPRESENT_SKU_CODE, A.REPRESENT_SKU_UNIT_PRICE, A.COMMODITY_DESCRIPTION1, "
    + " A.COMMODITY_DESCRIPTION2, A.COMMODITY_DESCRIPTION3, A.COMMODITY_DESCRIPTION_SHORT, "
    + "  A.COMMODITY_SEARCH_WORDS,   A.DISCOUNT_PRICE_START_DATETIME, "
    + "  A.DISCOUNT_PRICE_END_DATETIME, A.STANDARD1_ID, A.STANDARD1_NAME, A.STANDARD2_ID, "
    + "  A.STANDARD2_NAME, A.SALE_FLG_EC, A.RETURN_FLG,A.WARNING_FLAG, A.LEAD_TIME, "
    + "  case when A.SALE_FLG_EC='0' or A.SALE_FLG_EC is null then '销售停止' else  '销售中' end SALE_FLAG,"
    + "  A.SPEC_FLAG, A.BRAND_CODE, A.TMALL_COMMODITY_ID,A.TMALL_REPRESENT_SKU_PRICE, A.jd_commodity_id,A."
    + "  jd_category_id,A.SUPPLIER_CODE,A.BUYER_CODE,A.ORIGINAL_PLACE,A."
    + "  INGREDIENT_NAME1,A.INGREDIENT_VAL1,A.INGREDIENT_NAME2,A.INGREDIENT_VAL2,A."
    + "  INGREDIENT_NAME3,A.INGREDIENT_VAL3,A.INGREDIENT_NAME4,A.INGREDIENT_VAL4,A."
    + "  INGREDIENT_NAME5,A.INGREDIENT_VAL5,A.INGREDIENT_NAME6,A.INGREDIENT_VAL6,A."
    + "  INGREDIENT_NAME7,A.INGREDIENT_VAL7,A.INGREDIENT_NAME8,A.INGREDIENT_VAL8,A."
    + "  INGREDIENT_NAME9,A.INGREDIENT_VAL9,A.INGREDIENT_NAME10,A.INGREDIENT_VAL10,A."
    + " INGREDIENT_NAME11,A.INGREDIENT_VAL11,A.INGREDIENT_NAME12,A.INGREDIENT_VAL12,A."
    + "  INGREDIENT_NAME13,A.INGREDIENT_VAL13,A.INGREDIENT_NAME14,A.INGREDIENT_VAL14,A."
    + "  INGREDIENT_NAME15,A.INGREDIENT_VAL15,A.MATERIAL1,A.MATERIAL2,A.MATERIAL3,A."
    + "  MATERIAL4,A.MATERIAL5,A.MATERIAL6,A.MATERIAL7,A.MATERIAL8,A.MATERIAL9,A."
    + "  MATERIAL10,A.MATERIAL11,A.MATERIAL12,A.MATERIAL13,A.MATERIAL14,A.MATERIAL15,A."
    + "  SHELF_LIFE_FLAG,A.SHELF_LIFE_DAYS,A.BIG_FLAG,A.SYNC_TIME_EC,A.SYNC_TIME_TMALL,A."
    + "  SYNC_FLAG_EC,A.SYNC_FLAG_TMALL,A.sync_flag_jd, A.sync_time_jd,A.sync_user_jd,A.SYNC_USER_EC,A.SYNC_USER_TMALL,A."
    + "  EXPORT_FLAG_ERP,A.EXPORT_FLAG_WMS,A.ORM_ROWID,A.CREATED_USER,A.CREATED_DATETIME,A."
    + "  UPDATED_USER, to_char(A.UPDATED_DATETIME,'YYYY'||'/'||'MM'||'/'||'DD'||' '||'HH24'||':'||'mi'||':'||'ss') UPDATED_TIME, "
    + "    to_char(A.SALE_START_DATETIME,'YYYY'||'/'||'MM'||'/'||'DD')SALE_START_DATETIME ,"
    + "    to_char(A.SALE_END_DATETIME,'YYYY'||'/'||'MM'||'/'||'DD') SALE_END_DATETIME,"
    + "    A.CATEGORY_SEARCH_PATH,A.       CATEGORY_ATTRIBUTE_VALUE,  "
    + "  case when B.FIXED_PRICE_FLAG='1' then '定价' else '非定价' end FIXED_PRICE_FLAG, "
    + " B.SUGGESTE_PRICE,   B.PURCHASE_PRICE,   B.UNIT_PRICE, CASE WHEN B.USE_FLG='1' THEN '使用' ELSE '没使用' END USE_FLG,"
    + " CASE WHEN B.jd_use_flg='1' THEN '使用' ELSE '没使用' END JD_USE_FLG,"
    + " CASE WHEN B.TMALL_USE_FLG='1' THEN '使用' ELSE '没使用' END TMALL_USE_FLG,   B.DISCOUNT_PRICE,   B.TMALL_UNIT_PRICE,   B.TMALL_DISCOUNT_PRICE, "
    + " B.MIN_PRICE FROM    C_COMMODITY_HEADER A,  C_COMMODITY_DETAIL B    left join commodity_price_change_history CPCH on B.commodity_code = cpch.commodity_code, C_COMMODITY_EXT CE " 
    + " WHERE A.represent_sku_code=B.sku_code AND A.sync_flag_jd <> 2 AND A.sync_flag_jd <> 0 AND B.JD_USE_FLG = 1  AND A.COMMODITY_TYPE =0 AND CE.COMMODITY_CODE=A.COMMODITY_CODE AND CE.ON_STOCK_FLAG =1 "
    + " AND (not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where A.commodity_code = commodity_code) " 
    + " OR (not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where CPCH.commodity_code = commodity_code and CPCH.submit_time < submit_time) AND CPCH.review_or_not_flg = 1) "
    + " OR (not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where CPCH.commodity_code = commodity_code and CPCH.submit_time < submit_time) AND ((CPCH.ec_profit_margin IS NULL OR CPCH.ec_profit_margin >= 0) AND (CPCH.ec_special_profit_margin IS NULL OR CPCH.ec_special_profit_margin >= 0) AND (CPCH.tmall_profit_margin IS NULL OR CPCH.tmall_profit_margin >= 0) AND (CPCH.jd_profit_margin IS NULL OR CPCH.jd_profit_margin >= 0))) "
    + " OR (not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where CPCH.commodity_code = commodity_code and CPCH.submit_time < submit_time) AND exists(select 1 from C_COMMODITY_HEADER where A.commodity_code = commodity_code AND clear_commodity_type = 1))) ";

  public static final String CYNCHRO_JD_INFO_QUERY = " SELECT * FROM C_COMMODITY_HEADER CH INNER JOIN " +
    " C_COMMODITY_DETAIL CD ON CH.COMMODITY_CODE=CD.COMMODITY_CODE AND CH.REPRESENT_SKU_CODE=CD.SKU_CODE " +
    " WHERE CD.jd_use_flg = 1 AND CH.sync_flag_jd=" + SyncFlagJd.SYNCVISIBLE.getValue();
  // 2014/05/02 京东WBS对应 ob_姚 add end
  
  public static final String CC_CYNCHRO_EC_INFO_QUERY = " SELECT   A.SHOP_CODE, A.COMMODITY_CODE,A.KEYWORD_JP2,A.KEYWORD_CN2,A.KEYWORD_EN2, A.COMMODITY_NAME, A.COMMODITY_NAME_EN,"
      + " A.REPRESENT_SKU_CODE, A.REPRESENT_SKU_UNIT_PRICE, A.COMMODITY_DESCRIPTION1, "
      + " A.COMMODITY_DESCRIPTION2, A.COMMODITY_DESCRIPTION3, A.COMMODITY_DESCRIPTION_SHORT, "
      + "  A.COMMODITY_SEARCH_WORDS,   A.DISCOUNT_PRICE_START_DATETIME, "
      + "  A.DISCOUNT_PRICE_END_DATETIME, A.STANDARD1_ID, A.STANDARD1_NAME, A.STANDARD2_ID, "
      + "  A.STANDARD2_NAME, A.SALE_FLG_EC, A.RETURN_FLG,A.WARNING_FLAG, A.LEAD_TIME, "
      + "  case when A.SALE_FLG_EC='0' or A.SALE_FLG_EC is null then '销售停止' else  '销售中' end SALE_FLAG,"
      + "  A.SPEC_FLAG, A.BRAND_CODE, A.TMALL_COMMODITY_ID,A.TMALL_REPRESENT_SKU_PRICE,A."
      + "  TMALL_CATEGORY_ID,A.SUPPLIER_CODE,A.BUYER_CODE,A.ORIGINAL_PLACE,A."
      + "  INGREDIENT_NAME1,A.INGREDIENT_VAL1,A.INGREDIENT_NAME2,A.INGREDIENT_VAL2,A."
      + "  INGREDIENT_NAME3,A.INGREDIENT_VAL3,A.INGREDIENT_NAME4,A.INGREDIENT_VAL4,A."
      + "  INGREDIENT_NAME5,A.INGREDIENT_VAL5,A.INGREDIENT_NAME6,A.INGREDIENT_VAL6,A."
      + "  INGREDIENT_NAME7,A.INGREDIENT_VAL7,A.INGREDIENT_NAME8,A.INGREDIENT_VAL8,A."
      + "  INGREDIENT_NAME9,A.INGREDIENT_VAL9,A.INGREDIENT_NAME10,A.INGREDIENT_VAL10,A."
      + " INGREDIENT_NAME11,A.INGREDIENT_VAL11,A.INGREDIENT_NAME12,A.INGREDIENT_VAL12,A."
      + "  INGREDIENT_NAME13,A.INGREDIENT_VAL13,A.INGREDIENT_NAME14,A.INGREDIENT_VAL14,A."
      + "  INGREDIENT_NAME15,A.INGREDIENT_VAL15,A.MATERIAL1,A.MATERIAL2,A.MATERIAL3,A."
      + "  MATERIAL4,A.MATERIAL5,A.MATERIAL6,A.MATERIAL7,A.MATERIAL8,A.MATERIAL9,A."
      + "  MATERIAL10,A.MATERIAL11,A.MATERIAL12,A.MATERIAL13,A.MATERIAL14,A.MATERIAL15,A."
      + "  SHELF_LIFE_FLAG,A.SHELF_LIFE_DAYS,A.BIG_FLAG,A.SYNC_TIME_EC,A.SYNC_TIME_TMALL,A."
      + "  SYNC_FLAG_EC,A.SYNC_FLAG_TMALL,A.SYNC_USER_EC,A.SYNC_USER_TMALL,A."
      + "  EXPORT_FLAG_ERP,A.EXPORT_FLAG_WMS,A.ORM_ROWID,A.CREATED_USER,A.CREATED_DATETIME,A."
      + "  UPDATED_USER, to_char(A.UPDATED_DATETIME,'YYYY'||'/'||'MM'||'/'||'DD'||' '||'HH24'||':'||'mm'||':'||'ss') UPDATED_TIME, "
      + "    to_char(A.SALE_START_DATETIME,'YYYY'||'/'||'MM'||'/'||'DD')SALE_START_DATETIME ,"
      + "    to_char(A.SALE_END_DATETIME,'YYYY'||'/'||'MM'||'/'||'DD') SALE_END_DATETIME,"
      + "    A.CATEGORY_SEARCH_PATH,A.       CATEGORY_ATTRIBUTE_VALUE,  "
      + "  case when B.FIXED_PRICE_FLAG='1' then '定价' else '非定价' end FIXED_PRICE_FLAG, "
      + " B.SUGGESTE_PRICE,   B.PURCHASE_PRICE,   B.UNIT_PRICE, CASE WHEN B.USE_FLG='1' THEN '使用' ELSE '没使用' END USE_FLG,"
      // 2014/05/07 京东WBS对应 ob_姚 add start
      + " CASE WHEN B.jd_use_flg='1' THEN '使用' ELSE '没使用' END JD_USE_FLG,"
      // 2014/05/07 京东WBS对应 ob_姚 add end
      + " CASE WHEN B.TMALL_USE_FLG='1' THEN '使用' ELSE '没使用' END TMALL_USE_FLG,   B.DISCOUNT_PRICE,   B.TMALL_UNIT_PRICE,   B.TMALL_DISCOUNT_PRICE, "
      + " B.MIN_PRICE FROM C_COMMODITY_HEADER A, C_COMMODITY_DETAIL B LEFT JOIN commodity_price_change_history CPCH ON B.commodity_code = CPCH.commodity_code WHERE A.represent_sku_code=B.sku_code AND A.SYNC_FLAG_EC = 1 "
      + " AND (not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where A.commodity_code = commodity_code) " 
      + " OR (not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where CPCH.commodity_code = commodity_code and CPCH.submit_time < submit_time) AND CPCH.review_or_not_flg = 1) "
      + " OR (not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where CPCH.commodity_code = commodity_code and CPCH.submit_time < submit_time) AND ((CPCH.ec_profit_margin IS NULL OR CPCH.ec_profit_margin >= 0) AND (CPCH.ec_special_profit_margin IS NULL OR CPCH.ec_special_profit_margin >= 0) AND (CPCH.tmall_profit_margin IS NULL OR CPCH.tmall_profit_margin >= 0) AND (CPCH.jd_profit_margin IS NULL OR CPCH.jd_profit_margin >= 0))) "
      + " OR (not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where CPCH.commodity_code = commodity_code and CPCH.submit_time < submit_time) AND exists(select 1 from C_COMMODITY_HEADER where A.commodity_code = commodity_code AND clear_commodity_type = 1))) ";

  public static final String CYNCHRO_COMMDITYHEADER_BYCYNCHRO_UPDATE = ""
      + "update commodity_header set commodity_name=?,commodity_name_en=?"
      + ",represent_sku_code=?,represent_sku_unit_price=?"
      // + ",stock_management_type=?"
      + ",commodity_description_pc=?,commodity_description_mobile=?"
      + ",commodity_search_words=?,sale_start_datetime=?"
      + ",sale_end_datetime=?,discount_price_start_datetime=?"
      + ",discount_price_end_datetime=?"
      + ",commodity_standard1_name=?"
      + ",commodity_standard2_name=?"
      + ",sale_flg=?"
      + ",return_flg=?"
      + ",warning_flag=?"
      + ",sale_flag=?"
      + ",spec_flag=?"
      + ",brand_code=?"
      + ",category_path=?"
      + ",category_attribute1=?"
      + ",category_attribute1_en=?"
      + ",category_attribute1_jp=?"
      + ",updated_user=?"
      + ",updated_datetime=?, "
      + "commodity_name_jp=?, "
      + "commodity_description_pc_en=?, commodity_description_pc_jp=?, "
      + "commodity_description_mobile_en=?, commodity_description_mobile_jp=?, "
      + "commodity_standard1_name_en=?, commodity_standard1_name_jp=?, "
      + "commodity_standard2_name_en=?, commodity_standard2_name_jp=?, "
      + "original_place=?, original_place_en=?, "
      + "original_place_jp=?, shelf_life_days=?,shelf_life_flag=?,commodity_type=? ,set_commodity_flg=? ,import_commodity_type=?,clear_commodity_type=?,reserve_commodity_type1=?,reserve_commodity_type2=?,reserve_commodity_type3=?,"
      + "new_reserve_commodity_type1=?,new_reserve_commodity_type2=?,new_reserve_commodity_type3=?,new_reserve_commodity_type4=?,new_reserve_commodity_type5=?,original_code=?,"
      + "keyword_cn1=?,keyword_cn2=?,keyword_en1=?,keyword_en2=?,keyword_jp1=?,keyword_jp2=?"
      // txw 20130609 add start
      + ",original_commodity_code=?,combination_amount=?"
      // txw 20130609 add end
      // 20130808 txw add start
      + ",title=?, title_en=?, title_jp=?"
      + ",description=?, description_en=?, description_jp=?"
      + ",keyword=?, keyword_en=?, keyword_jp=?"
      // 20130808 txw add end
      + ",hot_flg_en=?,hot_flg_jp=?"
      + " where shop_code=? and commodity_code=?";

  public static final String Tmall_UPDATE_CCOMMONDITY_HEADER = ""
      + "update C_commodity_header set commodity_code = ?,"
      + "tmall_commodity_search_words = ?,combination_amount = ?,"
      + "export_flag_erp=?,export_flag_wms=?,"
      + "commodity_name = ?"
      + ",represent_sku_code = ?"
      + ",commodity_name_en=?,supplier_code=?,buyer_code=?,lead_time=?"
      + ",sale_start_datetime = ?,sale_end_datetime = ?"
      + ",discount_price_start_datetime=?"
      + ",discount_price_end_datetime = ?,standard1_name = ?"
      + ",standard2_name = ?,brand_code=?,sale_flg_ec = ? "
      + ",spec_flag=?,warning_flag=?,commodity_search_words = ?,commodity_description1 = ?"
      + ",commodity_description2 = ?,commodity_description3 = ?"
      + ",commodity_description_short=?"
      + ",original_place=?"
      + ",return_flg=?,sale_flag=?,big_flag=?"
      + ",tmall_category_id=?,"
      + "ingredient_name1=?, ingredient_val1=?, ingredient_name2=?,"
      + "ingredient_val2=?, ingredient_name3=?, ingredient_val3=?, ingredient_name4=?,"
      + "ingredient_val4=?, ingredient_name5=?, ingredient_val5=?, ingredient_name6=?, "
      + "ingredient_val6=?, ingredient_name7=?, ingredient_val7=?, ingredient_name8=?, "
      + "ingredient_val8=?, ingredient_name9=?, ingredient_val9=?, ingredient_name10=?, "
      + "ingredient_val10=?, ingredient_name11=?, ingredient_val11=?, "
      + "ingredient_name12=?, ingredient_val12=?, ingredient_name13=?, "
      + "ingredient_val13=?, ingredient_name14=?, ingredient_val14=?, "
      + "ingredient_name15=?, ingredient_val15=?, material1=?, material2=?, "
      + "material3=?, material4=?, material5=?, material6=?, material7=?, "
      + "material8=?, material9=?, material10=?, material11=?, material12=?, "
      + "material13=?, material14=?, material15=?, shelf_life_flag=?, "
      + "shelf_life_days=? ,in_bound_life_days=? ,out_bound_life_days=? ,shelf_life_alert_days=? ,"
      + "updated_user = ?,updated_datetime = ?,sync_flag_ec=1,sync_flag_tmall=1, "
      + "commodity_name_jp=?, "
      + "commodity_description1_en=?, commodity_description1_jp=?, commodity_description2_en=?, "
      + "commodity_description2_jp=?, commodity_description3_en=?, commodity_description3_jp=?, "
      + "commodity_description_short_en=?, commodity_description_short_jp=?, "
      + "food_security_prd_license_no=?,"
      + "food_security_design_code=?,"
      + "food_security_factory=?,"
      + "food_security_factory_site=?,"
      + "food_security_contact=?,"
      + "food_security_mix=?,"
      + "food_security_plan_storage=?,"
      + "food_security_period=?,"
      + "food_security_food_additive=?,"
      + "food_security_supplier=?,"
      + "food_security_product_date_start=?,"
      + "food_security_product_date_end=?,"
      + "food_security_stock_date_start=?,"
      + "food_security_stock_date_end=?,"
      + "original_place_en=?, "
      + "keyword_cn2=?,keyword_en2=?,keyword_jp2=?,"
      + "original_place_jp=?,tmall_mjs_flg=? ,import_commodity_type=?,clear_commodity_type=?,reserve_commodity_type1=?,"
      + "reserve_commodity_type2=?,reserve_commodity_type3=? ,new_reserve_commodity_type1=?,new_reserve_commodity_type2=?,"
      + "new_reserve_commodity_type3=?,new_reserve_commodity_type4=? ,new_reserve_commodity_type5=?,original_code=?,hot_flg_en=?,hot_flg_jp=?"
      // 20130808 txw add start
      + ",title=?,title_en=?,title_jp=?,description=?,description_en=?,description_jp=?,keyword=?,keyword_en=?,keyword_jp=?"
      // 20130808 txw add end
      //2014/4/28 京东WBS对应 ob_李 add start
      + ",sync_flag_jd=? , advert_content = ?"
      //2014/4/28 京东WBS对应 ob_李 add end
      + " where shop_code = ? and commodity_code = ?";

  public static final String CYNCHRO_COMMODITYDETAIL_UPDATE = "update commodity_detail"
      + " set commodity_code=?,unit_price=?,discount_price=?"
      + ",standard_detail1_name=?,standard_detail1_name_en=?,standard_detail1_name_jp=?,"
      + " standard_detail2_name=?,standard_detail2_name_en=?,standard_detail2_name_jp=?,weight=?"
      + ",use_flg=?,updated_user=?,updated_datetime=?"
      + ",inner_quantity=?"
      + " where shop_code=? and sku_code=?";

  // 11.12.27 OS014 追加 END
  // 20111230 os013 add start
  // 无限大设定为T-Mall，返回淘宝的库存为99999999
  public static final String UPDATE_STOCK_STOCK_TMALL = "UPDATE STOCK "
      + "SET STOCK_TMALL='99999999', UPDATED_USER=?, UPDATED_DATETIME=? WHERE SHOP_CODE= ?  AND SKU_CODE= ?";

  // 20111230 os013 add start
  /**
   * 查询商品关键属性(有非关键属性)
   */
  public static final String QUERY_TMALL_HEADER_PROPERTYS = ""
      + "select distinct p.property_id || ':' || p.value_id from c_commodity_detail as d,TMall_Commodity_Property as p,TMall_Property as v "
      + " where d.commodity_code=p.commodity_code "
      + " and p.property_id=v.property_id " + " and v.is_must=1"
      + " and d.commodity_code=?";
  /**
   * 查询商品关键属性(无非关键属性)
   */
  public static final String QUERY_TMALL_PROPERTYS = ""
      + "select distinct p.property_id || ':' || p.value_id from c_commodity_detail as d,TMall_Commodity_Property as p,TMall_Property as v "
      + " where d.commodity_code=p.commodity_code "
      + " and p.property_id=v.property_id "
      + " and d.commodity_code=? and v.is_key='1' and p.value_id <> '0'";
  /**
   * 查询商品销售属性
   */
  public static final String QUERY_TMALL_SALE_PROPERTYS = ""
      + "select distinct p.property_id || ':' || p.value_id from c_commodity_detail as d,TMall_Commodity_Property as p,TMall_Property as v "
      + " where d.commodity_code=p.commodity_code "
      + " and p.property_id=v.property_id " + " and v.is_must=1"
      + " and d.commodity_code=? and v.is_sale='1'";
  /**
   * 查询商品非关键属性
   */
  public static final String QUERY_TMALL_NO_KEY_PROPERTYS = ""
      + "select distinct p.property_id || ':' || p.value_id from c_commodity_detail as d,TMall_Commodity_Property as p,TMall_Property as v "
      + " where d.commodity_code=p.commodity_code "
      + " and p.property_id=v.property_id "
      + " and v.is_must=1"
      + " and d.commodity_code=? and v.is_key='0'and v.is_sale='0' and p.value_id<>'0'";
  /**
   * 查询sku关键属性
   */
  public static final String QUERY_TMALL_SKU_PROPERTYS = ""
      + "select d.standard_detail1_id,d.standard_detail2_id from c_commodity_detail as d where d.sku_code = ? and d.commodity_code = ? ";

  /**
   * 查询tmall detail 消息
   */
  public static final String QUERY_TMALL_DETAIL_INFO = ""
      + "select d.tmall_sku_id,h.represent_sku_unit_price,"
      + " d.tmall_unit_price,d.sku_code,d.tmall_discount_price,d.tmall_sku_id"
      + " ,s.stock_tmall-s.allocated_tmall stock_tmall"
      + " from c_commodity_header h,c_commodity_detail d, stock s"
      + " where h.commodity_code = d.commodity_code"
      + " And d.sku_code=s.sku_code" + " AND h.shop_code=d.shop_code"
      + " AND d.shop_code=s.shop_code"
      + " and h.commodity_code=? and h.shop_code=?";

  public static final String CYNCHRO_TMALL_ISCYNCHRO_INFO_UPDATE = "update c_commodity_header set sync_flag_tmall=?,sync_time_tmall=?,tmall_commodity_id=? where shop_code=? and commodity_code=?";

  public static final String CYNCHRO_TMALL_ISCYNCHRO_DETAIL_SKUCODE_UPDATE = "update c_commodity_detail set tmall_sku_id = ? where shop_code=? and commodity_code=? and sku_code=?";

  public static final String QUERY_EC_DETAIL_INFO_BY_COMMODITYID = "SELECT SHOP_CODE,SKU_CODE,COMMODITY_CODE,"
      + "UNIT_PRICE,DISCOUNT_PRICE,"
      + "STANDARD_DETAIL1_NAME,"
      + "STANDARD_DETAIL2_NAME,WEIGHT,USE_FLG FROM C_COMMODITY_DETAIL WHERE SHOP_CODE=? AND COMMODITY_CODE=? order by sku_code";

  public static final String QUERY_COMMODITY_PROPERTY = "SELECT property_id, category_id, property_name, parent_pid, is_must,"
      + " is_sale, is_enum, is_multi, orm_rowid, created_user, created_datetime, "
      + "  updated_user, updated_datetime"
      + "FROM tmall_property WHERE IS_MUST=1 AND CATEGORY_ID=? ";
  // 2012-01-04 add by os014 end
  // add by wjw 20120103 start
  public static final String GET_COMMODITY_DETAIL_CATEGORY_PATH = " SELECT A.category_path AS PATH, 1 as DEPTH "
      + "   FROM commodity_header A WHERE A.SHOP_CODE = ? "
      + "    AND A.COMMODITY_CODE = ? ";

  // add by wjw 20120103 end
  public static final String GET_COMMODITY_DETAIL_MINPRICE = "select min(purchase_price) purchase_price,min(unit_price) unit_price,min(tmall_unit_price) tmall_unit_price,min(discount_price) discount_price,min(tmall_discount_price) tmall_discount_price from c_commodity_detail where shop_code=? and commodity_code=? ";

  // 20120106 os013 add start
  public static final String CYNCHRO_TCOMMODITYID_QUERY = "select * from c_commodity_header where commodity_code = ? ";
  public static final String CYNCHRO_TCOMMODITYID_DETAIL_QUERY = "select * from c_commodity_detail where sku_code = ? ";

  // 20120106 os013 add end
  // add by os012 20120107 start
  public static final String STOCK_QUERY = "SELECT SHOP_CODE, SKU_CODE, COMMODITY_CODE, STOCK_QUANTITY, ALLOCATED_QUANTITY, "
      + " RESERVED_QUANTITY, RESERVATION_LIMIT, ONESHOT_RESERVATION_LIMIT, "
      + " STOCK_THRESHOLD, ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, "
      + " UPDATED_DATETIME, ALLOCATED_TMALL, SHARE_RATIO, STOCK_TOTAL, "
      + " STOCK_TMALL, SHARE_RECALC_FLAG"
      + "    FROM STOCK WHERE  SHOP_CODE = ? AND SKU_CODE = ?";
  // add by os012 20120107 end
  // add by wjh 20120107 start
  public static final String GET_BRAND_LIST = "SELECT B.SHOP_CODE, B.BRAND_CODE, B.BRAND_NAME, B.BRAND_NAME_ABBR, B.BRAND_DESCRIPTION, COUNT(CH.COMMODITY_CODE) COMMODITY_COUNT FROM BRAND B "
      + "LEFT JOIN COMMODITY_LIST_VIEW CH ON B.BRAND_CODE = CH.BRAND_CODE WHERE CH.REPRESENT_SKU_CODE = CH.SKU_CODE GROUP BY B.SHOP_CODE, B.BRAND_CODE, B.BRAND_NAME, B.BRAND_NAME_ABBR, B.BRAND_DESCRIPTION ";

  public static final String GET_REVIEW_SUMMARY = "SELECT  CASE WHEN R.REVIEW_SCORE = 100 THEN 80 ELSE R.REVIEW_SCORE END , COUNT(R.COMMODITY_CODE) COMMODITY_COUNT "
      + "FROM  REVIEW_SUMMARY R INNER JOIN COMMODITY_LIST_VIEW C ON R.COMMODITY_CODE = C.COMMODITY_CODE WHERE C.REPRESENT_SKU_CODE = C.SKU_CODE GROUP BY R.REVIEW_SCORE ORDER BY R.REVIEW_SCORE";

  public static final String GET_PRICE_COUNT = "SELECT COUNT(COMMODITY_CODE) FROM COMMODITY_LIST_VIEW WHERE REPRESENT_SKU_CODE = SKU_CODE AND UNIT_PRICE BETWEEN ? AND ?";

  public static final String GET_PRICE_COUNT1 = "SELECT COUNT(COMMODITY_CODE) FROM COMMODITY_LIST_VIEW WHERE REPRESENT_SKU_CODE = SKU_CODE AND UNIT_PRICE >= ?";

  public static final String GET_ATTRIBUTE = "SELECT SUBSTRING ( CATEGORY_ATTRIBUTE1 , 0, POSITION ( '|' IN CATEGORY_ATTRIBUTE1 ) ) CATEGORY_ATTRIBUTE, SUBSTRING(CATEGORY_ATTRIBUTE1, POSITION('|' IN CATEGORY_ATTRIBUTE1) + 1) CATEGORY_ATTRIBUTE1, COUNT ( COMMODITY_CODE ) COMMODITY_COUNT "
      + "FROM COMMODITY_LIST_VIEW WHERE REPRESENT_SKU_CODE = SKU_CODE AND CATEGORY_ATTRIBUTE1 IS NOT NULL GROUP BY SUBSTRING ( CATEGORY_ATTRIBUTE1 , 0, POSITION ( '|' IN CATEGORY_ATTRIBUTE1 ) ), SUBSTRING(CATEGORY_ATTRIBUTE1, POSITION('|' IN CATEGORY_ATTRIBUTE1) + 1) ORDER BY CATEGORY_ATTRIBUTE1";

  public static final String GET_CATEGORY_COUNT = "SELECT COUNT(COMMODITY_CODE) FROM COMMODITY_LIST_VIEW WHERE REPRESENT_SKU_CODE = SKU_CODE AND (CAST(CATEGORY_PATH AS TEXT) = ? OR (CAST(CATEGORY_PATH AS TEXT ) LIKE ? ))";

  public static final String GET_CATEGORY_COUNT1 = "SELECT COUNT(COMMODITY_CODE) FROM COMMODITY_LIST_VIEW WHERE REPRESENT_SKU_CODE = SKU_CODE AND (CAST(CATEGORY_PATH AS TEXT) = ? OR (CAST(CATEGORY_PATH AS TEXT ) LIKE ? )) AND BRAND_CODE = ?";

  public static final String GET_BRAND = "SELECT * FROM BRAND WHERE BRAND_CODE = ? ";

  public static final String GET_TMALL_BRAND = "SELECT * FROM TMALL_BRAND WHERE TMALL_BRAND_CODE = ? ";

  public static final String GET_SALES_CHARTS = " SELECT C.CATEGORY_CODE ,  C.CATEGORY_NAME_PC , "
    //20120522 tuxinwei add start
      + "  C.CATEGORY_NAME_PC_JP , C.CATEGORY_NAME_PC_EN ,"
      + "  CH.COMMODITY_NAME_EN , CH.COMMODITY_NAME_JP ,"
    //20120522 tuxinwei add end
      + "  CH.COMMODITY_CODE, CH.COMMODITY_NAME, "
      + " CD.UNIT_PRICE, "
      + " CD.DISCOUNT_PRICE, "
      + " CD.RESERVATION_PRICE, "
      + " CH.COMMODITY_TAX_TYPE, "
      + " CH.SALE_START_DATETIME, "
      + " CH.SALE_END_DATETIME, "
      + " CH.DISCOUNT_PRICE_START_DATETIME, "
      + " CH.DISCOUNT_PRICE_END_DATETIME "
      + "  FROM CATEGORY C LEFT JOIN COMMODITY_HEADER CH ON CH.CATEGORY_PATH LIKE '%' || C.PATH || '~' || C.CATEGORY_CODE || '%' "
      + "  INNER JOIN COMMODITY_DETAIL CD ON CD.SHOP_CODE = CH.SHOP_CODE AND CD.SKU_CODE = CH.REPRESENT_SKU_CODE "
      + "  WHERE C.DEPTH < 2 AND C.DEPTH > 0 AND  ((NOW() BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME)  OR "
      + " (NOW() BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME)) AND CH.SALE_FLG = 1 "
      + "  ORDER BY C.DEPTH,C.DISPLAY_ORDER,C.CATEGORY_CODE, CH.COMMODITY_POPULAR_RANK ASC";
  // add by wjh 20120107 end

  public static final String GET_SALES_CHARTS_B = " SELECT * FROM (SELECT" 
      + " CH.COMMODITY_NAME_EN , CH.COMMODITY_NAME_JP ,"
      + " CH.COMMODITY_CODE, CH.COMMODITY_NAME, "
      + " CD.UNIT_PRICE, "
      + " CD.DISCOUNT_PRICE, "
      + " CD.RESERVATION_PRICE, "
      + " CH.COMMODITY_TAX_TYPE, "
      + " CH.SALE_START_DATETIME, "
      + " CH.SALE_END_DATETIME, "
      + " CH.DISCOUNT_PRICE_START_DATETIME, "
      + " CH.DISCOUNT_PRICE_END_DATETIME "
      + " FROM COMMODITY_HEADER CH "
      + " INNER JOIN COMMODITY_DETAIL CD ON "
      // 20120827 add yyq start
      /* 首页显示商品，根据大分类进行查询，由于部分商品可能关联多个分类，所以在CATEGORY_PATH
       * 中有可能会出现；例："/~0~10~10300~10300300#/~0~70~70250100"这种情况，要提取所有用#隔开大分类编号，
       * 由于商品登录表中的商品最多可以关联五个分类，所以目前查询关联分类为5个
       * */
      + " (? =  SPLIT_PART(SPLIT_PART(CATEGORY_PATH, '#', 1),'~',3) OR  "
      + "  ? =  SPLIT_PART(SPLIT_PART(CATEGORY_PATH, '#', 2),'~',3) OR  "
      + "  ? =  SPLIT_PART(SPLIT_PART(CATEGORY_PATH, '#', 3),'~',3) OR  "
      + "  ? =  SPLIT_PART(SPLIT_PART(CATEGORY_PATH, '#', 4),'~',3) OR  "
      + "  ? =  SPLIT_PART(SPLIT_PART(CATEGORY_PATH, '#', 5),'~',3))  "
      // 20120827 add yyq end
      + " AND CD.SKU_CODE = CH.REPRESENT_SKU_CODE AND CH.SALE_FLG = 1 " 
      + " ORDER BY "
      + " CASE WHEN (SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK"
      + " WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 OR"
      + " CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC,"
      + " COMMODITY_POPULAR_RANK ASC) T LIMIT ?";
  
  public static final String GET_SALES_CHARTS_C = " SELECT * FROM (SELECT" 
    + " CH.COMMODITY_NAME_EN , CH.COMMODITY_NAME_JP ,"
    + " CH.COMMODITY_CODE, CH.COMMODITY_NAME, "
    + " CD.UNIT_PRICE, "
    + " CD.DISCOUNT_PRICE, "
    + " CD.RESERVATION_PRICE, "
    + " CH.COMMODITY_TAX_TYPE, "
    + " CH.SALE_START_DATETIME, "
    + " CH.SALE_END_DATETIME, "
    + " CH.DISCOUNT_PRICE_START_DATETIME, "
    + " CH.DISCOUNT_PRICE_END_DATETIME, "
    // 20130415 add by yyq start
    + " CH.IMPORT_COMMODITY_TYPE, "
    + " CH.CLEAR_COMMODITY_TYPE, "
    + " CH.RESERVE_COMMODITY_TYPE1, "
    + " CH.RESERVE_COMMODITY_TYPE2, "
    + " CH.RESERVE_COMMODITY_TYPE3, "
    + " CH.NEW_RESERVE_COMMODITY_TYPE1, "
    + " CH.NEW_RESERVE_COMMODITY_TYPE2, "
    + " CH.NEW_RESERVE_COMMODITY_TYPE3, "
    + " CH.NEW_RESERVE_COMMODITY_TYPE4, "
    + " CH.NEW_RESERVE_COMMODITY_TYPE5, "
    + " CD.INNER_QUANTITY ,"
    + " CH.ORIGINAL_COMMODITY_CODE ,CH.COMBINATION_AMOUNT"
    // 20130415 add by yyq end
    + " FROM COMMODITY_HEADER CH "
    + " INNER JOIN COMMODITY_DETAIL CD ON "
    // 20120827 add yyq start
    /* 首页显示商品，根据大分类进行查询，由于部分商品可能关联多个分类，所以在CATEGORY_PATH
     * 中有可能会出现；例："/~0~10~10300~10300300#/~0~70~70250100"这种情况，要提取所有用#隔开大分类编号，
     * 由于商品登录表中的商品最多可以关联五个分类，所以目前查询关联分类为5个
     * */
    + " (? =  SPLIT_PART(SPLIT_PART(CATEGORY_PATH, '#', 1),'~',3) OR  "
    + "  ? =  SPLIT_PART(SPLIT_PART(CATEGORY_PATH, '#', 2),'~',3) OR  "
    + "  ? =  SPLIT_PART(SPLIT_PART(CATEGORY_PATH, '#', 3),'~',3) OR  "
    + "  ? =  SPLIT_PART(SPLIT_PART(CATEGORY_PATH, '#', 4),'~',3) OR  "
    + "  ? =  SPLIT_PART(SPLIT_PART(CATEGORY_PATH, '#', 5),'~',3) )  "
    // 20120827 add yyq end
    + " AND CD.SKU_CODE = CH.REPRESENT_SKU_CODE AND CH.SALE_FLG = 1 " 
    + " INNER JOIN TAG_COMMODITY TC ON "
    + " TC.COMMODITY_CODE = CH.REPRESENT_SKU_CODE AND TC.TAG_CODE = ?" 
    + " WHERE CH.RESERVE_COMMODITY_TYPE1 <> 1 AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1)"
    + " ORDER BY "
    + " CASE WHEN (SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK"
    + " WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 OR"
    + " CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC,"
    + " COMMODITY_POPULAR_RANK ASC) T LIMIT ?";
  
  // 20130321 add by yyq start 详细页明星产品查询

  public static final String getSalesStar(String tagNo, String currentLanguageCode, String limit) {
    StringBuffer sqlSales = new StringBuffer("");
    sqlSales.append(" SELECT * FROM (SELECT");
    sqlSales.append(" CH.COMMODITY_NAME_EN , CH.COMMODITY_NAME_JP ,");
    sqlSales.append(" CH.COMMODITY_CODE, CH.COMMODITY_NAME, ");
    //zzy 2015/01/29 add satrt 查询套装品 单品 的金额
    sqlSales.append(" (case when ch.set_commodity_flg=1 then (SELECT SUM(SCC.RETAIL_PRICE)AS unit_prices FROM SET_COMMODITY_COMPOSITION SCC,STOCK S WHERE SCC.CHILD_COMMODITY_CODE=S.COMMODITY_CODE AND SCC.COMMODITY_CODE=CH.COMMODITY_CODE GROUP BY SCC.COMMODITY_CODE) else unit_price end), ");
    //zzy 2015/01/29 add end
    //sqlSales.append(" CD.UNIT_PRICE, ");
    sqlSales.append(" CD.DISCOUNT_PRICE, ");
    sqlSales.append(" CD.RESERVATION_PRICE, ");
    sqlSales.append(" CH.COMMODITY_TAX_TYPE, ");
    sqlSales.append(" CH.SALE_START_DATETIME, ");
    sqlSales.append(" CH.SALE_END_DATETIME, ");
    sqlSales.append(" CH.DISCOUNT_PRICE_START_DATETIME, ");
    sqlSales.append(" CH.IMPORT_COMMODITY_TYPE, ");
    sqlSales.append(" CH.CLEAR_COMMODITY_TYPE, ");
    sqlSales.append(" CH.RESERVE_COMMODITY_TYPE1, ");
    sqlSales.append(" CH.RESERVE_COMMODITY_TYPE2, ");
    sqlSales.append(" CH.RESERVE_COMMODITY_TYPE3, ");
    sqlSales.append(" CH.NEW_RESERVE_COMMODITY_TYPE1, ");
    sqlSales.append(" CH.NEW_RESERVE_COMMODITY_TYPE2, ");
    sqlSales.append(" CH.NEW_RESERVE_COMMODITY_TYPE3, ");
    sqlSales.append(" CH.NEW_RESERVE_COMMODITY_TYPE4, ");
    sqlSales.append(" CH.NEW_RESERVE_COMMODITY_TYPE5, ");
    sqlSales.append(" CD.INNER_QUANTITY, CH.ORIGINAL_COMMODITY_CODE,CH.COMBINATION_AMOUNT,");
    sqlSales.append(" CH.DISCOUNT_PRICE_END_DATETIME ");
    sqlSales.append(" FROM COMMODITY_HEADER CH ");
    sqlSales.append(" INNER JOIN COMMODITY_DETAIL CD ON ");
    sqlSales.append(" CD.SKU_CODE = CH.REPRESENT_SKU_CODE AND CH.SALE_FLG = 1 " );
    sqlSales.append(" INNER JOIN TAG_COMMODITY TC ON ");
    sqlSales.append(" TC.COMMODITY_CODE = CH.REPRESENT_SKU_CODE AND TC.TAG_CODE = '" + tagNo + "'" );
    sqlSales.append(" WHERE CH.RESERVE_COMMODITY_TYPE1 <> 1 AND CH.COMMODITY_TYPE = 0 ");
    //zzy 2015/01/29 add satrt 查询判断套装品  单品 库存
    //sqlSales.append(" AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END)) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1)");
    sqlSales.append(" and (case when ch.set_commodity_flg=1 then (SELECT MIN(S.STOCK_QUANTITY-S.ALLOCATED_QUANTITY) FROM SET_COMMODITY_COMPOSITION SCC,STOCK S WHERE SCC.CHILD_COMMODITY_CODE=S.COMMODITY_CODE AND SCC.COMMODITY_CODE=CH.COMMODITY_CODE GROUP BY SCC.COMMODITY_CODE )>=1 else ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE SKU_CODE = ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END)) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1) end )");
    //zzy 2015/01/29 add end 
    sqlSales.append(" AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END)) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 0 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1) ");
    sqlSales.append(" AND (TC.LANG LIKE '%" + currentLanguageCode + "%' OR TC.LANG IS NULL OR TC.LANG = '')");
    sqlSales.append(" ORDER BY");
    if ("ja-jp".equals(currentLanguageCode)) {
      sqlSales.append(" CASE WHEN TC.SORT_NUM_JP IS NULL THEN 9999 ELSE SORT_NUM_JP END ASC,");
    } else if ("en-us".equals(currentLanguageCode)) {
      sqlSales.append(" CASE WHEN TC.SORT_NUM_EN IS NULL THEN 9999 ELSE SORT_NUM_EN END ASC,");
    } else {
      sqlSales.append(" CASE WHEN TC.SORT_NUM IS NULL THEN 9999 ELSE SORT_NUM END ASC,");
    }
    sqlSales.append(" TC.LANG,");
    sqlSales.append(" CASE WHEN (SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK");
    sqlSales.append(" WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 OR");
    sqlSales.append(" CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC,");
    sqlSales.append(" COMMODITY_POPULAR_RANK ASC) T LIMIT " + limit);
    return sqlSales.toString();
  }
  
  public static final String QUERY_TMALLPROPERTY_BYID = "SELECT property_id, category_id, property_name, parent_pid, is_must, "
      + "is_sale, is_enum, is_multi, orm_rowid, created_user, created_datetime, "
      + "updated_user, updated_datetime "
      + "FROM tmall_property WHERE CATEGORY_ID = ? AND PROPERTY_ID=?";
  // ADD BY OS014 2012-01-17
  public static final String UPDATE_DETAIL_BY_QUERY = "UPDATE c_commodity_detail"
      + "SET  sku_name=?,"
      + "suggeste_price=?, purchase_price=?, unit_price=?,tmall_unit_price=?,"
      + "discount_price=?, tmall_discount_price=?"
      + "standard_detail1_name=?,"
      + "standard_detail2_name=?, weight=?, volume=?, volume_unit=?,  "
      + "minimum_order=?, maximum_order=?, order_multiple=?, stock_warning=?, "
      + "updated_user=?, updated_datetime=?"
      + " where sku_code=? and commodity_code=? and shop_code=?";
  public static final String DELETE_COMMODITY_PROPERTY = "DELETE FROM tmall_commodity_property"
      + " WHERE COMMODITY_CODE=?";

  public static final String GET_FAVORITECOMMODITY_QUERY = "SELECT F.CUSTOMER_CODE, F.SHOP_CODE, F.SKU_CODE, F.FAVORITE_REGISTER_DATE"
      + " FROM FAVORITE_COMMODITY F WHERE F.CUSTOMER_CODE=? AND F.SHOP_CODE=? AND F.SKU_CODE=?";

  public static final String GET_COMMODITY_IMG_INFO = "SELECT IUH.SHOP_CODE, IUH.SKU_CODE, IUH.COMMODITY_CODE, IUH.TMALL_IMG_ID, IUH.TMALL_IMG_URL, "
      + "IUH.LOCAL_OPER_FLG, IUH.TMALL_UPLOAD_FLG, IUH.EC1_UPLOAD_FLG, IUH.EC2_UPLOAD_FLG, IUH.UPLOAD_DATETIME, "
      + "IUH.ORM_ROWID, IUH.CREATED_USER, IUH.CREATED_DATETIME, IUH.UPDATED_USER, IUH.UPDATED_DATETIME "
      + "FROM IMAGE_UPLOAD_HISTORY AS IUH INNER JOIN COMMODITY_HEADER AS CH ON "
      + "IUH.SKU_CODE = CH.REPRESENT_SKU_CODE AND IUH.SHOP_CODE = CH.SHOP_CODE WHERE IUH.SHOP_CODE = ? AND LOCAL_OPER_FLG = 1";

  // 20120215 os013 add start
  public static final String GET_C_COMMODITY_EXT_LIST = "SELECT COMMODITY_CODE FROM  C_COMMODITY_EXT "
      + " WHERE  SHOP_CODE = ? AND COMMODITY_CODE = ?";
  public static final String GET_CATEGORY_COMMODITY_INSERT_LIST = "SELECT CATEGORY_CODE FROM  CATEGORY_COMMODITY "
      + " WHERE  SHOP_CODE = ? AND COMMODITY_CODE = ?";
  public static final String GET_CATEGORY_COMMODITY_DELETE_LIST = "SELECT CATEGORY_CODE FROM  CATEGORY_COMMODITY "
      + " WHERE  SHOP_CODE = ? AND COMMODITY_CODE = ? AND CATEGORY_CODE <> ?";
  public static final String GET_CATEGORY_ATTRIBUTE_VALUE_LIST = "SELECT CATEGORY_CODE ,CATEGORY_ATTRIBUTE_NO FROM  CATEGORY_ATTRIBUTE_VALUE "
      + " WHERE COMMODITY_CODE = ? ORDER BY CATEGORY_CODE";
  public static final String GET_CATEGORY_CODE_LIST = "SELECT CATEGORY_CODE FROM  CATEGORY_ATTRIBUTE_VALUE "
      + " WHERE COMMODITY_CODE = ? AND CATEGORY_CODE<>?";
  public static final String GET_CATEGORY_ATTRIBUTE_NAME_LIST = "SELECT CATEGORY_ATTRIBUTE_NAME FROM  CATEGORY_ATTRIBUTE "
      + " WHERE CATEGORY_CODE = ? AND CATEGORY_ATTRIBUTE_NO = ? ";
  // 20120215 os013 add end

  public static final String DELETE_BRAND_PROPERTY = "DELETE FROM tmall_commodity_property"
      + " WHERE COMMODITY_CODE=? ";
  
  //2014/4/28 京东WBS对应 ob_李 add start
  public static final String DELETE_JD_COMMODITY_PROPERTY = "DELETE FROM jd_commodity_property"
    + " WHERE COMMODITY_CODE=? ";
  //2014/4/28 京东WBS对应 ob_李 add end
  
  public static final String QUERY_PROPERTY_PROPERTYVALUE = ""
      + " SELECT P.PROPERTY_ID AS PROPERTY_ID,P.CATEGORY_ID AS CATEGORY_ID,P.PROPERTY_NAME AS PROPERTY_NAME, P.PARENT_PID AS PARENT_PID, "
      + " P.PARENT_VID AS PARENT_VID,V.VALUE_ID AS VALUE_ID,V.VALUE_NAME AS VALUE_NAME "
      + " FROM TMALL_PROPERTY P LEFT JOIN TMALL_PROPERTY_VALUE V "
      + " ON "
      + "(P.PROPERTY_ID = V.PROPERTY_ID AND P.CATEGORY_ID = V.CATEGORY_ID) "
      + " WHERE P.PROPERTY_ID = ? AND P.CATEGORY_ID = ? AND V.VALUE_ID = ?";
  // 2012/11/16 促销对应 ob add start
  
  public static final String GET_CAMPAIGN_COMMODITY_BY_COMMODITYCODE="SELECT * FROM CAMPAIGN_COMMODITY WHERE SHOP_CODE=? AND "
    +"COMMODITY_CODE =?";
  public static final String GET_SETCOMMODITY_COMPOSITION_BY_CHILDCOMMODITY="SELECT * FROM SET_COMMODITY_COMPOSITION WHERE CHILD_COMMODITY_CODE=? AND SHOP_CODE=?";
  public static final String GET_SETCOMMODITY_COMPOSITION_BY_CHILDCOMMODITY2="SELECT SC.COMMODITY_CODE FROM SET_COMMODITY_COMPOSITION SC " +
  "INNER JOIN COMMODITY_HEADER CH ON CH.COMMODITY_CODE=SC.COMMODITY_CODE AND CH.SHOP_CODE=? " +
  "INNER JOIN COMMODITY_DETAIL CD ON CD.COMMODITY_CODE=CH.COMMODITY_CODE " +
  "WHERE SC.COMMODITY_CODE IN " +
  "(SELECT COMMODITY_CODE FROM SET_COMMODITY_COMPOSITION WHERE CHILD_COMMODITY_CODE = ?) AND SC.CHILD_COMMODITY_CODE = ?" +
  "AND (SELECT NOW()) BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME ";
  public static final String GET_SETCOMMODITY_COMPOSITION = "SELECT * FROM SET_COMMODITY_COMPOSITION WHERE COMMODITY_CODE=?  AND SHOP_CODE=? ORDER BY CHILD_COMMODITY_CODE";

  public static final String GET_CAMPAIGN_DOING_GIFT = "SELECT ATTRIBUTR_VALUE FROM CAMPAIGN_DOINGS WHERE CAMPAIGN_CODE = ? AND CAMPAIGN_TYPE = " + CampaignMainType.GIFT.longValue();
  
  public static final String GET_CHILD_SET_COMMODITY = "SELECT CM.COMMODITY_CODE, CM.COMMODITY_NAME, CM.COMMODITY_NAME_EN,  CM.COMMODITY_NAME_JP, "
      + "CM.REPRESENT_SKU_CODE,CM.COMMODITY_TAX_TYPE, "
      + "CM.SALE_START_DATETIME, CM.SALE_END_DATETIME,"
      + "CM.REPRESENT_SKU_UNIT_PRICE,"
      + "CM.SALE_FLG,CM.DISCOUNT_PRICE_START_DATETIME,CM.DISCOUNT_PRICE_END_DATETIME,"
      + "CD.UNIT_PRICE ,CD.DISCOUNT_PRICE "
      + "FROM COMMODITY_HEADER CM "
      + "INNER JOIN COMMODITY_DETAIL CD ON CD.COMMODITY_CODE=CM.COMMODITY_CODE AND CD.SKU_CODE=CM.REPRESENT_SKU_CODE "
      + "WHERE 1=1 AND CM.SHOP_CODE = ? ";
   public static final String GET_DETAIL_SKU_COUNT = "SELECT COUNT(*)"
     + "FROM COMMODITY_HEADER CM "
     + "INNER JOIN COMMODITY_DETAIL CD ON CD.COMMODITY_CODE=CM.COMMODITY_CODE "
     + "WHERE 1=1 AND CM.SHOP_CODE = ? ";
  public static String getChildSetCommodity(List<String> commodityCodes){
    if(commodityCodes != null && commodityCodes.size() > 0){
      StringBuffer sql=new StringBuffer(GET_CHILD_SET_COMMODITY);
      sql.append("AND CM.COMMODITY_CODE = '"+commodityCodes.get(0)+"'");
      if(commodityCodes.size() > 1){
      for(int i = 1 ;i < commodityCodes.size() ;i++){
        sql.append(" OR CM.COMMODITY_CODE = '"+commodityCodes.get(i)+"'");
      }}
       sql.append("ORDER BY CM.COMMODITY_CODE");
      return sql.toString();
    }
    return GET_CHILD_SET_COMMODITY;
  }
   public static String getDetailSkuCount(List<String> commodityCodes){
      if(commodityCodes != null && commodityCodes.size() > 0){
        StringBuffer sql=new StringBuffer(GET_DETAIL_SKU_COUNT);
        sql.append("AND CM.COMMODITY_CODE = '"+commodityCodes.get(0)+"'");
        if(commodityCodes.size() > 1){
        for(int i = 1 ;i < commodityCodes.size() ;i++){
          sql.append(" OR CM.COMMODITY_CODE = '"+commodityCodes.get(i)+"'");
        }}
        return sql.toString();
      }
      return GET_DETAIL_SKU_COUNT;
    }
  
  
    /** 套餐构成品取得*/
    public static final String GET_COMPOSITION_BASE_QUERY = "   FROM  " + "     COMMODITY_HEADER CH "
      + "   LEFT OUTER JOIN " + "     SET_COMMODITY_COMPOSITION SCC " + "   ON "
      + "     CH.COMMODITY_CODE = SCC.CHILD_COMMODITY_CODE" + "   AND " + "     CH.SHOP_CODE = SCC.SHOP_CODE "
      + "   WHERE " + "     SCC.SHOP_CODE = ? " + "   AND " + "     SCC.COMMODITY_CODE = ? "
      + " ORDER BY CH.COMMODITY_CODE";
    
    public static final String GET_SUIT_SALE_PRICE = "SELECT SUM(CASE WHEN retail_price IS NULL THEN 0 ELSE retail_price END ) as retail_price FROM set_commodity_composition WHERE commodity_code = ? ";

    /**套餐构成品取得 */
    public static final String GET_ALL_COMPOSITION_HEADER_QUERY = " SELECT CH.* " + GET_COMPOSITION_BASE_QUERY;
    public static final String GET_SET_COMMODITY_COMPOSITION_BY_COMMODITY_CODE = " SELECT CH.COMMODITY_CODE FROM COMMODITY_HEADER CH "
      + " INNER JOIN COMMODITY_DETAIL CD ON CD.COMMODITY_CODE = CH.COMMODITY_CODE AND CD.SHOP_CODE = CH.SHOP_CODE "
      + " INNER JOIN SET_COMMODITY_COMPOSITION SCC ON SCC.COMMODITY_CODE = CH.COMMODITY_CODE AND SCC.SHOP_CODE = CH.SHOP_CODE "
      + " WHERE CH.SHOP_CODE = ? AND CH.COMMODITY_CODE = ? "
      + " AND SET_COMMODITY_FLG = "
      + NumUtil.toLong(SetCommodityFlg.OBJECTIN.getValue());
    // 2012/11/16 促销对应 ob add end
    
    // 2012.11.22 add by yyq 库存预警商品查询
    public static final String GET_STOCK_WARN_COMMODITIES =
      "   SELECT CCD.SKU_CODE, CCH.COMMODITY_NAME,CCH.SUPPLIER_CODE,S.STOCK_TOTAL-ALLOCATED_TMALL-ALLOCATED_QUANTITY AS STOCK_NUM,CCD.STOCK_WARNING "
      + " FROM C_COMMODITY_HEADER CCH "
      + " INNER JOIN STOCK S ON CCH.COMMODITY_CODE = S.COMMODITY_CODE "
      + " INNER JOIN C_COMMODITY_DETAIL CCD ON CCH.COMMODITY_CODE = CCD.COMMODITY_CODE "
      + " WHERE S.STOCK_TOTAL-ALLOCATED_TMALL-ALLOCATED_QUANTITY < CCD.STOCK_WARNING"
      + " ORDER BY CCD.SKU_CODE";
    
    public static final String GET_IS_TMALLSTOCKALLOCATION="SELECT * FROM TMALL_STOCK_ALLOCATION WHERE COMMODITY_CODE=?";
    
    public static final String GET_IS_CCommodityHeader="SELECT * FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE=?";
    
    public static final String GET_CUSTOMER_COMMODITY_ALL=" SELECT CC.* FROM CUSTOMER_COMMODITY CC  "
    	+	" INNER JOIN COMMODITY_HEADER CH ON CH.COMMODITY_CODE = CC.COMMODITY_CODE " 
    	+	" INNER JOIN STOCK S ON S.COMMODITY_CODE = CC.COMMODITY_CODE WHERE  " 
    	+ " ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.COMMODITY_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 1 ELSE CH.COMBINATION_AMOUNT END )   " 
    	+	" ORDER BY CC.CUSTOMER_CODE ";
    
    public static String  getCategoryForPath(String languageCode){
        StringBuffer sql=new StringBuffer("SELECT CATEGORY_CODE,");
        if (languageCode.equals("zh-cn")){
          sql.append("CATEGORY_NAME_PC") ;
        }else if(languageCode.equals("ja-jp")){
          sql.append("CATEGORY_NAME_PC_JP") ;
        }else{
          sql.append("CATEGORY_NAME_PC_EN") ;
        }
        sql.append(" FROM CATEGORY WHERE CATEGORY_CODE = ?");
        return sql.toString();
    } 
    
    public static String  getFreeCategoryPath(String languageCode,String categoryCode,String keyword){
      StringBuffer sql=new StringBuffer("SELECT * FROM CATEGORY C  WHERE ");
      String str = SqlDialect.getDefault().escape(StringUtil.replaceKeyword(keyword));
      str = str.replace("\\", "\\\\");
      String[] strList = str.split(" ");
      String sqlStr1 = "";
      String sqlStr2 = "";
      for(int i = 0 ;i<strList.length;i++){
          if(i > 0){
             sql.append(" AND ");
          }
          if (languageCode.equals("zh-cn")) {
            sqlStr1= "(( C.KEYWORD_CN1 LIKE '%"+strList[i]+"%' OR KEYWORD_CN2 LIKE '%"+strList[i]+"%') ";
            sqlStr2 = "AND C.CATEGORY_CODE NOT IN ( SELECT PARENT_CATEGORY_CODE FROM CATEGORY WHERE (C.KEYWORD_CN1 LIKE '%"+strList[i]+"%' OR KEYWORD_CN2 LIKE '%"+strList[i]+"%')))";
            sql.append(sqlStr1) ;
            sql.append(sqlStr2);
          }else if(languageCode.equals("ja-jp")){
            sqlStr1= "(( C.KEYWORD_JP1 LIKE '%"+strList[i]+"%' OR KEYWORD_JP2 LIKE '%"+strList[i]+"%') ";
            sqlStr2 = "AND C.CATEGORY_CODE NOT IN ( SELECT PARENT_CATEGORY_CODE FROM CATEGORY WHERE (C.KEYWORD_JP1 LIKE '%"+strList[i]+"%' OR KEYWORD_JP2 LIKE '%"+strList[i]+"%')))";
            sql.append(sqlStr1) ;
            sql.append(sqlStr2);
          }else{
            sqlStr1= "(( C.KEYWORD_EN1 LIKE '%"+strList[i]+"%' OR KEYWORD_EN2 LIKE '%"+strList[i]+"%') ";
            sqlStr2 = "AND C.CATEGORY_CODE NOT IN ( SELECT PARENT_CATEGORY_CODE FROM CATEGORY WHERE (C.KEYWORD_EN1 LIKE '%"+strList[i]+"%' OR KEYWORD_EN2 LIKE '%"+strList[i]+"%')))";
            sql.append(sqlStr1) ;
            sql.append(sqlStr2);
          }
      }

      sql.append(" AND ( C.PARENT_CATEGORY_CODE = ? or C.PATH LIKE ? or C.CATEGORY_CODE = ?");
      if(!categoryCode.equals("/") && !categoryCode.equals("0") ){
        sql.append(")");
      }else{
        sql.append("or 1 =1 )");
      }
      sql.append("ORDER BY C.PATH, C.DISPLAY_ORDER LIMIT ?");
      return sql.toString();
    }
    
    public static String  getFreeBrandName(String languageCode,String keyword){
      StringBuffer sql=new StringBuffer("SELECT * FROM BRAND WHERE");
      String str = SqlDialect.getDefault().escape(StringUtil.replaceKeyword(keyword));
      str = str.replace("\\", "\\\\");
      String[] strList = str.split(" ");
      String sqlStr = "";
      for(int i = 0 ;i<strList.length;i++){
        if(i > 0){
           sql.append(" AND ");
        }
        if (languageCode.equals("zh-cn")) {
            sqlStr= "( KEYWORD_CN1 LIKE '%"+strList[i]+"%' OR KEYWORD_CN2 LIKE '%"+strList[i]+"%') ";
            sql.append(sqlStr) ;
        }else if(languageCode.equals("ja-jp")){
            sqlStr= "( KEYWORD_JP1 LIKE '%"+strList[i]+"%' OR KEYWORD_JP2 LIKE '%"+strList[i]+"%') ";
            sql.append(sqlStr) ;
        }else{
            sqlStr= "( KEYWORD_EN1 LIKE '%"+strList[i]+"%' OR KEYWORD_EN2 LIKE '%"+strList[i]+"%') ";
            sql.append(sqlStr) ;
        }
          
      }
      sql.append(" LIMIT ?");
      return sql.toString();
    }
    
    public static final String GET_SUGGESTION_SEARCHWORD = 
      " SELECT SEARCH_WORD,HIT_COUNT FROM CANDIDATE_WORD WHERE " +
      "( lower(SEARCH_WORD) LIKE ?  OR lower(PINYIN) LIKE ? ) AND HIT_COUNT <> '0' " +
      " AND LANG = ? ORDER BY HIT_COUNT DESC LIMIT ? " ;

    public static final String GET_CATEGORYSEL_ALL =
      "SELECT CC.CATEGORY_CODE,CC.COMMODITY_CODE,C.KEYWORD_JP1, C.KEYWORD_JP2,C.KEYWORD_CN1, C.KEYWORD_CN2, C.KEYWORD_EN1, C.KEYWORD_EN2 FROM CATEGORY_COMMODITY CC, CATEGORY C WHERE CC.CATEGORY_CODE = C.CATEGORY_CODE AND CC.COMMODITY_CODE = ?";

    public static final String GET_SEARCH_KEYWORD_LOG_ALL="SELECT * FROM SEARCH_KEYWORD_LOG WHERE SEARCH_WORD = ?";
    
    public static final String GET_CANDIDATE_WORD_ALL="SELECT * FROM CANDIDATE_WORD WHERE SEARCH_WORD = ? AND LANG = ?";

    public static String  getCommodityHeaderCountByKeyword(String[] strList,String languageCode){
      
      StringBuffer sql = new StringBuffer();
      
      sql.append(" SELECT COUNT(*) as ALL_CNT ");
      sql.append("FROM COMMODITY_HEADER CH ");
      
      sql.append("INNER JOIN STOCK ST ");
      sql.append("ON CH.COMMODITY_CODE = ST.COMMODITY_CODE ");
      
      sql.append("WHERE ");
      sql.append("CH.SALE_FLG = 1 AND ");  //販売状況
      sql.append("( NOW() BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME ) AND "); //販売期間
      sql.append("CH.commodity_type = 0 AND ");  //商品タイプ（普通商品）
      sql.append("( CH.set_commodity_flg IS NULL OR CH.set_commodity_flg = 0 ) AND ");  //商品タイプ（普通商品）
      sql.append("CH.CATEGORY_PATH IS NOT NULL AND ");  //カテゴリ
      
      // キーワード
      if (languageCode.equals("CN")){
        for (int i = 0 ; i<strList.length; i++){
          sql.append("( lower(CH.KEYWORD_CN1) LIKE ? OR lower(CH.KEYWORD_CN2) LIKE ?) AND ") ;
        }
      }else if(languageCode.equals("JP")){
        for (int i = 0 ; i<strList.length; i++){
          sql.append("( lower(CH.KEYWORD_JP1) LIKE ? OR lower(CH.KEYWORD_JP2) LIKE ?) AND ") ;
        }
      }else{
        for (int i = 0 ; i<strList.length; i++){
          sql.append("( lower(CH.KEYWORD_EN1) LIKE ? OR lower(CH.KEYWORD_EN2) LIKE ?) AND ") ;
        }
      }
      
      // 有効在庫
      sql.append("(");
      sql.append("CH.STOCK_MANAGEMENT_TYPE = 1 OR ");
      sql.append("(");
      // CASE文開始（元商品と組合商品の判断）
      sql.append("(CASE ");
      sql.append(" WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN ST.STOCK_QUANTITY - ST.ALLOCATED_QUANTITY > 0 ");
      sql.append(" ELSE (SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY - CH.combination_amount AS ACT_STOCK FROM STOCK WHERE CH.ORIGINAL_COMMODITY_CODE = COMMODITY_CODE) >= 0");
      sql.append(" END ) ");
      // CASE文終了
      sql.append(")");
      sql.append(")");
      
      return sql.toString();
      
    } 
    
    public static final String GET_C_COMMODITY_HEADER_COUNT="SELECT COUNT(*) as ALL_CNT FROM C_COMMODITY_HEADER WHERE KEYWORD_JP1 LIKE ? OR KEYWORD_JP2 LIKE ?";
    
    public static final String GET_C_COMMODITY_HEADER_BY_ORIGINALCOMMODITYCODE="SELECT * FROM C_COMMODITY_HEADER WHERE ORIGINAL_COMMODITY_CODE = ? OR COMMODITY_CODE = ? ORDER BY ORIGINAL_COMMODITY_CODE DESC ,COMMODITY_CODE";
    
    public static final String GET_COMMODITY_CODE="SELECT * FROM C_COMMODITY_HEADER WHERE ORIGINAL_COMMODITY_CODE = ?";
    
    public static final String ORIGINAL_COMMODITY_CODE="SELECT * FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE = ?";
    
    public static final String GET_C_COMMODITY_DETAIL_BY_COMMODITYCODE="SELECT * FROM C_COMMODITY_DETAIL WHERE COMMODITY_CODE = ?";
    
    public static final String GET_COMMODITY_HEADER_COUNT="SELECT COUNT(*) as ALL_CNT FROM COMMODITY_HEADER WHERE COMMODITY_CODE = ?";
    
    public static final String GET_CATEGORY_BY_PAREMENT = 
      "SELECT C2.KEYWORD_JP1,C2.KEYWORD_CN1,C2.KEYWORD_EN1,C2.KEYWORD_JP2,C2.KEYWORD_CN2,C2.KEYWORD_EN2"
      +" FROM CATEGORY C1 LEFT JOIN CATEGORY C2 ON C1.PARENT_CATEGORY_CODE = C2.CATEGORY_CODE"
      +" WHERE C1.CATEGORY_CODE = ? ORDER BY C1.DEPTH, C1.PATH";
    
    public static final String GET_CATEGORY_BY_SELF = 
      "SELECT CATEGORY_CODE, CATEGORY_NAME_PC, CATEGORY_NAME_PC_JP, CATEGORY_NAME_PC_EN,KEYWORD_JP1, KEYWORD_CN1, KEYWORD_EN1"
      +" FROM CATEGORY WHERE CATEGORY_CODE <> '0' ORDER BY DEPTH, PATH";
  
    // 2014/06/05 库存更新对应 ob_卢 add start

    public static final String GET_ALL_USE_QUANTITY_JD = "SELECT " 
    	+	" CASE WHEN SUM(TSC.STOCK_QUANTITY-TSC.ALLOCATED_QUANTITY) IS NULL " 
    	+	" THEN 0 ELSE SUM(TSC.STOCK_QUANTITY-TSC.ALLOCATED_QUANTITY) END AS TOTAL_QUANTITY   " 
      + " FROM JD_SUIT_COMMODITY TSC "
      + " INNER JOIN SET_COMMODITY_COMPOSITION SCC ON TSC.COMMODITY_CODE = SCC.COMMODITY_CODE "
      + " where scc.child_commodity_code = ? ";
    
    public static final String GET_JD_STOCK_INFO_BY_QUERY = "SELECT CH.COMMODITY_CODE," 
    	+	" CH.COMMODITY_NAME," 
    	+	" CH.COMBINATION_AMOUNT," 
    	+	" CD.JD_USE_FLG," 
      + " GET_JD_STOCK(CH.COMMODITY_CODE) AS STOCK_QUANTITY," 
      + " GET_JD_ALLOCATED_STOCK(CH.COMMODITY_CODE) AS ALLOCATED_QUANTITY," 
      + " NULL AS SCALE_VALUE"
      + " FROM C_COMMODITY_HEADER CH"
      + " INNER JOIN C_COMMODITY_DETAIL CD ON CD.COMMODITY_CODE=CH.COMMODITY_CODE"
      + " WHERE CH.COMMODITY_CODE= ?";
    
      public static final String GET_JD_STOCK_INFO_LIST_BY_QUERY = "SELECT " 
      	+	" CH.COMMODITY_CODE," 
      	+	" CH.COMMODITY_NAME," 
      	+	" CH.COMBINATION_AMOUNT," 
      	+	" CD.JD_USE_FLG," 
      	+	" TSA.STOCK_QUANTITY AS STOCK_QUANTITY," 
      	+	" TSA.ALLOCATED_QUANTITY AS ALLOCATED_QUANTITY," 
      	+	" TSA.SCALE_VALUE AS SCALE_VALUE" 
        + " FROM C_COMMODITY_HEADER CH"
        + " INNER JOIN C_COMMODITY_DETAIL CD ON CD.COMMODITY_CODE=CH.COMMODITY_CODE"
        + " INNER JOIN JD_STOCK_ALLOCATION TSA ON CH.COMMODITY_CODE=TSA.COMMODITY_CODE"
        + " WHERE TSA.ORIGINAL_COMMODITY_CODE= ?"
        + " ORDER BY CH.COMBINATION_AMOUNT ASC";
    // 2014/06/05 库存更新对应 ob_卢 add end
      
    // 20130613 txw add start
    // 2014/06/10 库存更新对应 ob_卢 update start

    //public static final String GET_TMALL_STOCK_INFO_BY_QUERY = "SELECT CH.COMMODITY_CODE,CH.COMMODITY_NAME,CH.COMBINATION_AMOUNT,CD.TMALL_USE_FLG,S.STOCK_TMALL AS STOCK_QUANTITY,S.ALLOCATED_TMALL AS ALLOCATED_QUANTITY,NULL AS SCALE_VALUE"
    public static final String GET_TMALL_STOCK_INFO_BY_QUERY = "SELECT CH.COMMODITY_CODE,CH.COMMODITY_NAME,CH.COMBINATION_AMOUNT,CD.TMALL_USE_FLG," 
    		    + " GET_TMALL_STOCK(CH.COMMODITY_CODE) AS STOCK_QUANTITY," 
    		    +	" GET_TMALL_ALLOCATED_STOCK(CH.COMMODITY_CODE) AS ALLOCATED_QUANTITY," 
    		    +	" NULL AS SCALE_VALUE"
    // 2014/06/10 库存更新对应 ob_卢 update end
            + " FROM C_COMMODITY_HEADER CH"
            + " INNER JOIN C_COMMODITY_DETAIL CD ON CD.COMMODITY_CODE=CH.COMMODITY_CODE"
            + " INNER JOIN STOCK S ON CH.COMMODITY_CODE=S.COMMODITY_CODE"
            + " WHERE CH.COMMODITY_CODE= ?";
    
    public static final String GET_TMALL_STOCK_INFO_LIST_BY_QUERY = "SELECT CH.COMMODITY_CODE,CH.COMMODITY_NAME,CH.COMBINATION_AMOUNT,CD.TMALL_USE_FLG,TSA.STOCK_QUANTITY AS STOCK_QUANTITY,TSA.ALLOCATED_QUANTITY AS ALLOCATED_QUANTITY,TSA.SCALE_VALUE AS SCALE_VALUE" 
            + " FROM C_COMMODITY_HEADER CH"
            + " INNER JOIN C_COMMODITY_DETAIL CD ON CD.COMMODITY_CODE=CH.COMMODITY_CODE"
            + " INNER JOIN TMALL_STOCK_ALLOCATION TSA ON CH.COMMODITY_CODE=TSA.COMMODITY_CODE"
            + " WHERE TSA.ORIGINAL_COMMODITY_CODE= ?"
            + " ORDER BY CH.COMBINATION_AMOUNT ASC";
    
    public static final String GET_STOCK_BY_CODE="SELECT * FROM STOCK WHERE COMMODITY_CODE= ?";
    
    public static final String GET_ON_STOCK_FLAG="SELECT ON_STOCK_FLAG FROM C_COMMODITY_EXT WHERE COMMODITY_CODE= ?";
    
    public static final String GET_HISTORY_BUY_AMOUNT="SELECT SUM(SD.PURCHASING_AMOUNT) " 
      + "FROM SHIPPING_DETAIL SD INNER JOIN SHIPPING_HEADER SH ON SD.SHIPPING_NO = SH.SHIPPING_NO " 
      + "WHERE SD.CAMPAIGN_CODE = (SELECT DC.DISCOUNT_CODE FROM DISCOUNT_COMMODITY DC INNER JOIN DISCOUNT_HEADER DH ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE WHERE DC.COMMODITY_CODE = ?  " 
      + " AND DH.DISCOUNT_START_DATETIME <= "
      + SqlDialect.getDefault().getCurrentDatetime() + " AND "
      + SqlDialect.getDefault().getCurrentDatetime()
      + " <= DH.DISCOUNT_END_DATETIME ) "
      + "AND SD.SKU_CODE=? AND SH.SHIPPING_STATUS <> ? AND SH.CUSTOMER_CODE = ? ";
    
    public static final String GET_HISTORY_BUY_AMOUNT_TOTAL="SELECT SUM(SD.PURCHASING_AMOUNT) " 
      + "FROM SHIPPING_DETAIL SD INNER JOIN SHIPPING_HEADER SH ON SD.SHIPPING_NO = SH.SHIPPING_NO " 
      + "WHERE SD.CAMPAIGN_CODE = (SELECT DC.DISCOUNT_CODE FROM DISCOUNT_COMMODITY DC INNER JOIN DISCOUNT_HEADER DH ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE WHERE DC.COMMODITY_CODE = ?" 
      + " AND DH.DISCOUNT_START_DATETIME <= "
      + SqlDialect.getDefault().getCurrentDatetime() + " AND "
      + SqlDialect.getDefault().getCurrentDatetime()
      + " <= DH.DISCOUNT_END_DATETIME ) "
      + "AND SD.SKU_CODE=? AND SH.SHIPPING_STATUS <> ?";
    

    // public static final String GET_DISCOUNT_TYPE="SELECT SD.DISCOUNT_TYPE  FROM SHIPPING_DETAIL SD WHERE SKU_CODE = ? AND SD.DISCOUNT_TYPE = ?  and SD.UNIT_PRICE = ? " +
    //        " AND SHIPPING_NO = (SELECT  SH.SHIPPING_NO FROM ORDER_DETAIL  OD INNER JOIN SHIPPING_HEADER  SH ON   OD.ORDER_NO =  SH.ORDER_NO WHERE OD.ORDER_NO = ?)";
    
    public static final String GET_DISCOUNT_TYPE=" SELECT SD.DISCOUNT_TYPE  FROM SHIPPING_DETAIL SD INNER JOIN SHIPPING_HEADER  SH ON SD.SHIPPING_NO = SH.SHIPPING_NO "
      +" WHERE SD.SKU_CODE = ? AND SD.DISCOUNT_TYPE = ?  AND SD.UNIT_PRICE = ? AND SH.ORDER_NO = ? AND  SH.RETURN_ITEM_TYPE <> 1 ";

    public static final String GET_DISCOUNT_COMMODITY="SELECT DC.*" 
      + " FROM DISCOUNT_COMMODITY DC INNER JOIN DISCOUNT_HEADER DH ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE" 
      + " WHERE DC.COMMODITY_CODE = ? AND DC.USE_FLG = " + UsingFlg.VISIBLE.longValue()
      + " AND DH.DISCOUNT_START_DATETIME <= "
      + SqlDialect.getDefault().getCurrentDatetime() + " AND "
      + SqlDialect.getDefault().getCurrentDatetime()
      + " <= DH.DISCOUNT_END_DATETIME ";
    
    public static final String GET_DISCOUNT_HEADER_LEAST="SELECT DH.DISCOUNT_END_DATETIME , DH.DISCOUNT_START_DATETIME" 
      + " FROM DISCOUNT_HEADER DH  " 
      + " WHERE  DH.DISCOUNT_START_DATETIME <= NOW() AND NOW() <= DH.DISCOUNT_END_DATETIME  "
      + " ORDER BY DISCOUNT_START_DATETIME LIMIT 1 ";
    
    public static final String GET_DISCOUNT_PLAN="SELECT  CH.SHOP_CODE, CH.COMMODITY_CODE, CH.COMMODITY_NAME, CH.COMMODITY_NAME_EN, CH.COMMODITY_NAME_JP, CD.UNIT_PRICE AS COMMODITY_DESCRIPTION_PC ,DC.DISCOUNT_PRICE AS COMMODITY_DESCRIPTION_PC_JP,DC.SITE_MAX_TOTAL_NUM AS COMMODITY_DESCRIPTION_PC_EN "
      +" FROM COMMODITY_HEADER CH INNER JOIN COMMODITY_DETAIL CD "
      +" ON CD.SHOP_CODE = CH.SHOP_CODE AND CD.SKU_CODE = CH.REPRESENT_SKU_CODE "
      +" INNER JOIN DISCOUNT_COMMODITY DC ON DC.COMMODITY_CODE = CH.COMMODITY_CODE "
      +" WHERE (NOW() BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME) "
      +" AND CH.SALE_FLG = 1 AND CH.CATEGORY_PATH IS NOT NULL "
      +" AND DC.USE_FLG = 1 AND DC.DISCOUNT_CODE  =  "
      +" (SELECT DISCOUNT_CODE FROM DISCOUNT_HEADER  WHERE DISCOUNT_START_DATETIME <= NOW() AND NOW() <= DISCOUNT_END_DATETIME "
      +"  ORDER BY DISCOUNT_START_DATETIME LIMIT 1) ORDER BY CASE WHEN DC. LIMIT 20 " ;
    
    public static final String GET_CAMPAIGN_MAIN = " SELECT CM.MEMO ,CC.ATTRIBUTR_VALUE AS campaign_code, cm.campaign_name,cm.campaign_name_en,cm.campaign_name_jp "
      + " FROM CAMPAIGN_MAIN CM INNER JOIN CAMPAIGN_CONDITION CC ON CM.CAMPAIGN_CODE = CC.CAMPAIGN_CODE "
      + " AND CM.CAMPAIGN_TYPE = 1 AND CC.CAMPAIGN_CONDITION_TYPE =1   "
      + " WHERE  CM.CAMPAIGN_START_DATE <= "
      + SqlDialect.getDefault().getCurrentDatetime() + " AND "
      + SqlDialect.getDefault().getCurrentDatetime()
      + " <= CM.CAMPAIGN_END_DATE   " ;
    
    public static final String COMMODITY_PLAN = "SELECT CH.COMMODITY_CODE ,ch.brand_code ,ch.category_path FROM COMMODITY_HEADER CH "
      + " INNER JOIN COMMODITY_DETAIL CD ON CD.SHOP_CODE = CH.SHOP_CODE AND CD.SKU_CODE = CH.REPRESENT_SKU_CODE  "
      + " WHERE ((NOW() BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME)  OR  (NOW() BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME))   "
      + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1)  "
      + " AND CH.SALE_FLG = 1 AND CH.CATEGORY_PATH IS NOT NULL   "
      + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 0 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1) "
      + " AND EXISTS (SELECT 'OK' FROM SHOP SP WHERE SP.SHOP_CODE = CH.SHOP_CODE AND NOW() BETWEEN SP.OPEN_DATETIME AND SP.CLOSE_DATETIME)  "
      + " AND EXISTS (SELECT 'OK' FROM DELIVERY_TYPE DJ WHERE DJ.DELIVERY_TYPE_NO = CH.DELIVERY_TYPE_NO AND DJ.SHOP_CODE = CH.SHOP_CODE AND DJ.DISPLAY_FLG = 1)   "
      + " AND CH.RESERVE_COMMODITY_TYPE1 <> 1  "
      + " AND EXISTS (SELECT  'OK' FROM CATEGORY_COMMODITY CC WHERE CC.SHOP_CODE = CH.SHOP_CODE AND CC.COMMODITY_CODE = CH.COMMODITY_CODE)  "
      + " AND CH.DISPLAY_CLIENT_TYPE IN (0,1)  ";
      
    public static final String COMMODITY_PLAN_INDEX =" SELECT CH.BRAND_CODE ,CH.CATEGORY_PATH,CH.COMMODITY_POPULAR_RANK,"
    + " CH.SHOP_CODE, CH.COMMODITY_CODE,CH.COMMODITY_NAME,CH.ORIGINAL_COMMODITY_CODE,CH.COMBINATION_AMOUNT, "
    + " CH.COMMODITY_NAME_JP,"
    + " CH.COMMODITY_NAME_EN,CD.UNIT_PRICE," 
    +	" CASE WHEN DD.DISCOUNT_PRICE IS NOT NULL THEN DD.DISCOUNT_PRICE ELSE CD.DISCOUNT_PRICE END AS DISCOUNT_PRICE,"
    + " CASE WHEN DD.DISCOUNT_START_DATETIME IS NOT NULL THEN DD.DISCOUNT_START_DATETIME ELSE CH.DISCOUNT_PRICE_START_DATETIME END AS DISCOUNT_PRICE_START_DATETIME,"
    + " CASE WHEN DD.DISCOUNT_END_DATETIME IS NOT NULL THEN DD.DISCOUNT_END_DATETIME ELSE CH.DISCOUNT_PRICE_END_DATETIME END AS DISCOUNT_PRICE_END_DATETIME,"
    + " CH.SALE_START_DATETIME,"
    + " CH.SALE_END_DATETIME,"
    + " CH.RESERVATION_START_DATETIME,"
    + " CH.RESERVATION_END_DATETIME,"
    + " CH.COMMODITY_TAX_TYPE,"
    + " CH.COMMODITY_DESCRIPTION_PC,"
    + " CH.STOCK_MANAGEMENT_TYPE,"
    + " CH.IMPORT_COMMODITY_TYPE,"
    + " CH.CLEAR_COMMODITY_TYPE,"
    + " CH.RESERVE_COMMODITY_TYPE1,"
    + " CH.RESERVE_COMMODITY_TYPE2,"    
    + " CH.RESERVE_COMMODITY_TYPE3,"    
    + " CH.NEW_RESERVE_COMMODITY_TYPE1,"  
    + " CH.NEW_RESERVE_COMMODITY_TYPE2,"  
    + " CH.NEW_RESERVE_COMMODITY_TYPE3,"  
    + " CH.NEW_RESERVE_COMMODITY_TYPE4," 
    + " CH.NEW_RESERVE_COMMODITY_TYPE5," 
    + " CD.INNER_QUANTITY," 
    + " CH.SALE_FLG"
    + " FROM COMMODITY_HEADER CH"
    + " INNER JOIN COMMODITY_DETAIL CD ON CD.SHOP_CODE = CH.SHOP_CODE AND CD.SKU_CODE = CH.REPRESENT_SKU_CODE  "
    + " LEFT JOIN (SELECT DH.DISCOUNT_START_DATETIME,DH.DISCOUNT_END_DATETIME,DC.DISCOUNT_PRICE,DC.COMMODITY_CODE FROM DISCOUNT_HEADER DH "
    + " INNER JOIN DISCOUNT_COMMODITY DC ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE WHERE DC.USE_FLG = 1 "
    + " AND DH.DISCOUNT_START_DATETIME <= NOW() AND NOW() <= DH.DISCOUNT_END_DATETIME) DD ON DD.COMMODITY_CODE = CH.COMMODITY_CODE "
    + " WHERE ((NOW() BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME)  OR  (NOW() BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME))   "
    + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1)  "
    + " AND CH.SALE_FLG = 1 AND CH.COMMODITY_TYPE = 0 AND CH.CATEGORY_PATH IS NOT NULL   "
    + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 0 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1) "
    + " AND EXISTS (SELECT 'OK' FROM SHOP SP WHERE SP.SHOP_CODE = CH.SHOP_CODE AND NOW() BETWEEN SP.OPEN_DATETIME AND SP.CLOSE_DATETIME)  "
    + " AND EXISTS (SELECT 'OK' FROM DELIVERY_TYPE DJ WHERE DJ.DELIVERY_TYPE_NO = CH.DELIVERY_TYPE_NO AND DJ.SHOP_CODE = CH.SHOP_CODE AND DJ.DISPLAY_FLG = 1)   "
    + " AND CH.RESERVE_COMMODITY_TYPE1 <> 1  "
    + " AND EXISTS (SELECT  'OK' FROM CATEGORY_COMMODITY CC WHERE CC.SHOP_CODE = CH.SHOP_CODE AND CC.COMMODITY_CODE = CH.COMMODITY_CODE)  "
    + " AND CH.DISPLAY_CLIENT_TYPE IN (0,1)  ";  
    
    public static String getCommodityPlanDiscountEveryLanguageCode(String languageCode) {
      String sql = COMMODITY_PLAN_DISCOUNT;
      if (languageCode.equals("zh-cn")) {
        sql += " ORDER BY DC.RANK_CN ASC LIMIT 20 ";
      } else if (languageCode.equals("ja-jp")) {
        sql += " ORDER BY DC.RANK_JP ASC LIMIT 20 ";
      } else if (languageCode.equals("en-us")) {
        sql += " ORDER BY DC.RANK_EN ASC LIMIT 20 ";
      } else {
        sql += " ORDER BY DC.RANK_CN ASC LIMIT 20 ";
      }
      return sql;
    }
    
    public static final String COMMODITY_PLAN_DISCOUNT =" SELECT CH.BRAND_CODE ,CH.CATEGORY_PATH,CH.COMMODITY_POPULAR_RANK,"
      + " CH.SHOP_CODE, CH.COMMODITY_CODE,CH.COMMODITY_NAME,CH.ORIGINAL_COMMODITY_CODE,CH.COMBINATION_AMOUNT, "
      + " CH.COMMODITY_NAME_JP,"
      + " CH.COMMODITY_NAME_EN,CD.UNIT_PRICE," 
      + " CASE WHEN DC.DISCOUNT_PRICE IS NOT NULL THEN DC.DISCOUNT_PRICE ELSE CD.DISCOUNT_PRICE END AS DISCOUNT_PRICE,"
      + " CASE WHEN DH.DISCOUNT_START_DATETIME IS NOT NULL THEN DH.DISCOUNT_START_DATETIME ELSE CH.DISCOUNT_PRICE_START_DATETIME END AS DISCOUNT_PRICE_START_DATETIME,"
      + " CASE WHEN DH.DISCOUNT_END_DATETIME IS NOT NULL THEN DH.DISCOUNT_END_DATETIME ELSE CH.DISCOUNT_PRICE_END_DATETIME END AS DISCOUNT_PRICE_END_DATETIME,"
      + " CH.SALE_START_DATETIME,"
      + " CH.SALE_END_DATETIME,"
      + " CH.RESERVATION_START_DATETIME,"
      + " CH.RESERVATION_END_DATETIME,"
      + " CH.COMMODITY_TAX_TYPE,"
      + " CH.COMMODITY_DESCRIPTION_PC,"
      + " CH.STOCK_MANAGEMENT_TYPE,"
      + " CH.IMPORT_COMMODITY_TYPE,"
      + " CH.CLEAR_COMMODITY_TYPE,"
      + " CH.RESERVE_COMMODITY_TYPE1,"
      + " CH.RESERVE_COMMODITY_TYPE2,"    
      + " CH.RESERVE_COMMODITY_TYPE3,"    
      + " CH.NEW_RESERVE_COMMODITY_TYPE1,"  
      + " CH.NEW_RESERVE_COMMODITY_TYPE2,"  
      + " CH.NEW_RESERVE_COMMODITY_TYPE3,"  
      + " CH.NEW_RESERVE_COMMODITY_TYPE4," 
      + " CH.NEW_RESERVE_COMMODITY_TYPE5," 
      + " CD.INNER_QUANTITY," 
      + " CH.SALE_FLG,dc.discount_directions_cn as commodity_description_pc,dc.discount_directions_jp as commodity_description_pc_jp,dc.discount_directions_en as commodity_description_pc_en"
      + " FROM DISCOUNT_COMMODITY DC "
      + " INNER JOIN COMMODITY_HEADER CH ON CH.COMMODITY_CODE = DC.COMMODITY_CODE "
      + " INNER JOIN COMMODITY_DETAIL CD ON  CD.SKU_CODE = CH.COMMODITY_CODE  "
      + " INNER JOIN discount_header dh ON  dh.DISCOUNT_CODE = DC.DISCOUNT_CODE  "
      + " WHERE DC.DISCOUNT_CODE  = (SELECT DISCOUNT_CODE FROM DISCOUNT_HEADER  WHERE DISCOUNT_START_DATETIME <= NOW() AND NOW() <= DISCOUNT_END_DATETIME ORDER BY DISCOUNT_START_DATETIME LIMIT 1)  "
      + " AND DC.USE_FLG = 1  AND CH.RESERVE_COMMODITY_TYPE1 <> 1   AND CH.SALE_FLG = 1 AND CH.COMMODITY_TYPE = 0 AND CH.CATEGORY_PATH IS NOT NULL    "
      + " and ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.COMMODITY_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 1 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1) "
      + " AND EXISTS (SELECT 'OK' FROM SHOP SP WHERE SP.SHOP_CODE = CH.SHOP_CODE AND NOW() BETWEEN SP.OPEN_DATETIME AND SP.CLOSE_DATETIME)  "
      + " AND EXISTS (SELECT 'OK' FROM DELIVERY_TYPE DJ WHERE DJ.DELIVERY_TYPE_NO = CH.DELIVERY_TYPE_NO AND DJ.SHOP_CODE = CH.SHOP_CODE AND DJ.DISPLAY_FLG = 1)   "
      + " AND ((NOW() BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME)  OR  (NOW() BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME))   "
      + " AND EXISTS (SELECT  'OK' FROM CATEGORY_COMMODITY CC WHERE CC.SHOP_CODE = CH.SHOP_CODE AND CC.COMMODITY_CODE = CH.COMMODITY_CODE)  "
      + " AND CH.DISPLAY_CLIENT_TYPE IN (0,1)  "
      + " and CASE WHEN (SELECT SUM(SD.PURCHASING_AMOUNT) FROM SHIPPING_DETAIL SD INNER JOIN SHIPPING_HEADER SH ON SD.SHIPPING_NO = SH.SHIPPING_NO  "
      + " and SD.CAMPAIGN_CODE = DC.DISCOUNT_CODE AND SD.SKU_CODE=ch.commodity_code  AND SH.SHIPPING_STATUS <> 4) IS NULL THEN 0 ELSE  "
      +	" (SELECT SUM(SD.PURCHASING_AMOUNT) FROM SHIPPING_DETAIL SD INNER JOIN SHIPPING_HEADER SH ON SD.SHIPPING_NO = SH.SHIPPING_NO and SD.CAMPAIGN_CODE = DC.DISCOUNT_CODE AND SD.SKU_CODE=ch.commodity_code  AND SH.SHIPPING_STATUS <> 4) END < dc.site_max_total_num "
      + "";
    
    public static final String GET_NEW_COMMODITY_PLAN = COMMODITY_PLAN_INDEX + " order by ch.SALE_START_DATETIME desc limit ?  ";
    
    public static final String GET_INDEX_BATCH_COMMODITY = " SELECT CH.NEW_RESERVE_COMMODITY_TYPE1,CH.NEW_RESERVE_COMMODITY_TYPE3,CH.SHOP_CODE,IBC.COMMODITY_CODE ,IBC.COMMODITY_NAME_CN,IBC.COMMODITY_NAME_CN AS COMMODITY_NAME,IBC.COMMODITY_NAME_EN,IBC.COMMODITY_NAME_JP, " 
      + " CH.ORIGINAL_COMMODITY_CODE,CH.COMBINATION_AMOUNT,DETAIL.UNIT_PRICE,IBC.INNER_QUANTITY,IBC.IMPORT_COMMODITY_TYPE ,IBC.CLEAR_COMMODITY_TYPE,CH.RESERVE_COMMODITY_TYPE2,CH.COMMODITY_TAX_TYPE,"
      + " CASE WHEN DD.DISCOUNT_PRICE IS NOT NULL THEN DD.DISCOUNT_PRICE ELSE DETAIL.DISCOUNT_PRICE END AS DISCOUNT_PRICE,"
      + " CASE WHEN DD.DISCOUNT_START_DATETIME IS NOT NULL THEN DD.DISCOUNT_START_DATETIME ELSE CH.DISCOUNT_PRICE_START_DATETIME END AS DISCOUNT_PRICE_START_DATETIME,"
      + " CASE WHEN DD.DISCOUNT_END_DATETIME IS NOT NULL THEN DD.DISCOUNT_END_DATETIME ELSE CH.DISCOUNT_PRICE_END_DATETIME END AS DISCOUNT_PRICE_END_DATETIME"
      + " FROM COMMODITY_HEADER CH "
      + " INNER JOIN INDEX_BATCH_COMMODITY IBC ON CH.COMMODITY_CODE = IBC.COMMODITY_CODE "
      + " INNER JOIN STOCK S ON CH.COMMODITY_CODE = S.COMMODITY_CODE "
      + " INNER JOIN COMMODITY_DETAIL DETAIL ON CH.COMMODITY_CODE = DETAIL.COMMODITY_CODE "
      + " LEFT JOIN (SELECT DH.DISCOUNT_START_DATETIME,DH.DISCOUNT_END_DATETIME,DC.DISCOUNT_PRICE,DC.COMMODITY_CODE FROM DISCOUNT_HEADER DH "
      + " INNER JOIN DISCOUNT_COMMODITY DC ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE WHERE DC.USE_FLG = 1 "
      + " AND DH.DISCOUNT_START_DATETIME <= NOW() AND NOW() <= DH.DISCOUNT_END_DATETIME) DD ON DD.COMMODITY_CODE = CH.COMMODITY_CODE "
      +	" WHERE ((NOW() BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME)  OR  (NOW() BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME))   "
      + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.COMMODITY_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1)  "
      + " AND CH.SALE_FLG = 1 AND CH.CATEGORY_PATH IS NOT NULL   "
      + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.COMMODITY_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 0 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1) AND IBC.COMMODITY_TYPE = ? order by ibc.orm_rowid asc limit 20";
    
    // 品店精选
    public static final String GET_CHOSEN_SORT_COMMODITY = " SELECT CH.NEW_RESERVE_COMMODITY_TYPE1,CH.SHOP_CODE,CH.COMMODITY_CODE ,CH.COMMODITY_NAME AS COMMODITY_NAME_CN,CH.COMMODITY_NAME AS COMMODITY_NAME,CH.COMMODITY_NAME_EN,CH.COMMODITY_NAME_JP, " 
      + " CH.ORIGINAL_COMMODITY_CODE,CH.COMBINATION_AMOUNT,DETAIL.UNIT_PRICE,DETAIL.INNER_QUANTITY,CH.IMPORT_COMMODITY_TYPE ,CH.CLEAR_COMMODITY_TYPE,CH.RESERVE_COMMODITY_TYPE2,CH.COMMODITY_TAX_TYPE,"
      + " CASE WHEN DD.DISCOUNT_PRICE IS NOT NULL THEN DD.DISCOUNT_PRICE ELSE DETAIL.DISCOUNT_PRICE END AS DISCOUNT_PRICE,"
      + " CASE WHEN DD.DISCOUNT_START_DATETIME IS NOT NULL THEN DD.DISCOUNT_START_DATETIME ELSE CH.DISCOUNT_PRICE_START_DATETIME END AS DISCOUNT_PRICE_START_DATETIME,"
      + " CASE WHEN DD.DISCOUNT_END_DATETIME IS NOT NULL THEN DD.DISCOUNT_END_DATETIME ELSE CH.DISCOUNT_PRICE_END_DATETIME END AS DISCOUNT_PRICE_END_DATETIME"
      + " FROM COMMODITY_HEADER CH "
      + " INNER JOIN STOCK S ON CH.COMMODITY_CODE = S.COMMODITY_CODE "
      + " INNER JOIN COMMODITY_DETAIL DETAIL ON CH.COMMODITY_CODE = DETAIL.COMMODITY_CODE "
      + " LEFT JOIN (SELECT DH.DISCOUNT_START_DATETIME,DH.DISCOUNT_END_DATETIME,DC.DISCOUNT_PRICE,DC.COMMODITY_CODE FROM DISCOUNT_HEADER DH "
      + " INNER JOIN DISCOUNT_COMMODITY DC ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE WHERE DC.USE_FLG = 1 "
      + " AND DH.DISCOUNT_START_DATETIME <= NOW() AND NOW() <= DH.DISCOUNT_END_DATETIME) DD ON DD.COMMODITY_CODE = CH.COMMODITY_CODE "
      + " WHERE ((NOW() BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME)  OR  (NOW() BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME))   "
      //+ " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.COMMODITY_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1)  "
      + " AND CH.SALE_FLG = 1 AND CH.CATEGORY_PATH IS NOT NULL   "
      + " AND CH.CHOSEN_SORT_RANK IS NOT NULL "
      //+ " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.COMMODITY_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 1 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1) " +
      		+" AND CH.NEW_RESERVE_COMMODITY_TYPE1 = ? order by CH.CHOSEN_SORT_RANK ASC limit 20";
    
    public static final String GET_HOT_SALE_COMMODITY = " SELECT CH.NEW_RESERVE_COMMODITY_TYPE1,CH.NEW_RESERVE_COMMODITY_TYPE3,CH.SHOP_CODE,hsc.COMMODITY_CODE ,CH.cOMMODITY_NAME as COMMODITY_NAME_CN,CH.COMMODITY_NAME_EN,CH.COMMODITY_NAME_JP,   " 
      + " CH.ORIGINAL_COMMODITY_CODE,CH.COMBINATION_AMOUNT,DETAIL.UNIT_PRICE,detail.INNER_QUANTITY,ch.IMPORT_COMMODITY_TYPE ,ch.CLEAR_COMMODITY_TYPE,CH.RESERVE_COMMODITY_TYPE2,CH.COMMODITY_TAX_TYPE, "
      + " CASE WHEN DD.DISCOUNT_PRICE IS NOT NULL THEN DD.DISCOUNT_PRICE ELSE DETAIL.DISCOUNT_PRICE END AS DISCOUNT_PRICE,"
      + " CASE WHEN DD.DISCOUNT_START_DATETIME IS NOT NULL THEN DD.DISCOUNT_START_DATETIME ELSE CH.DISCOUNT_PRICE_START_DATETIME END AS DISCOUNT_PRICE_START_DATETIME,"
      + " CASE WHEN DD.DISCOUNT_END_DATETIME IS NOT NULL THEN DD.DISCOUNT_END_DATETIME ELSE CH.DISCOUNT_PRICE_END_DATETIME END AS DISCOUNT_PRICE_END_DATETIME"
      + " FROM COMMODITY_HEADER CH "
      + " INNER JOIN hot_sale_commodity hsc ON CH.COMMODITY_CODE = hsc.COMMODITY_CODE and hsc.language_code = ?  "
      + " INNER JOIN STOCK S ON CH.COMMODITY_CODE = S.COMMODITY_CODE "
      + " INNER JOIN COMMODITY_DETAIL DETAIL ON CH.COMMODITY_CODE = DETAIL.COMMODITY_CODE "
      + " LEFT JOIN (SELECT DH.DISCOUNT_START_DATETIME,DH.DISCOUNT_END_DATETIME,DC.DISCOUNT_PRICE,DC.COMMODITY_CODE FROM DISCOUNT_HEADER DH "
      + " INNER JOIN DISCOUNT_COMMODITY DC ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE WHERE DC.USE_FLG = 1 "
      + " AND DH.DISCOUNT_START_DATETIME <= NOW() AND NOW() <= DH.DISCOUNT_END_DATETIME) DD ON DD.COMMODITY_CODE = CH.COMMODITY_CODE "
      + " WHERE ((NOW() BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME)  OR  (NOW() BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME))   "
      + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.COMMODITY_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1)  "
      + " AND CH.SALE_FLG = 1 AND CH.CATEGORY_PATH IS NOT NULL   "
      + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.COMMODITY_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 0 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1)  order by hsc.sort_rank  limit 20 ";
    
    public static final String GET_HOT_COMMODITY_PLAN_BY_BRAND = COMMODITY_PLAN 
      +" AND ch.BRAND_CODE = ? order by ch.COMMODITY_POPULAR_RANK asc limit 3";
    
    public static final String GET_HOT_COMMODITY_PLAN_BY_CATEGORY_PATH = "SELECT * FROM ("
      + " (" + COMMODITY_PLAN_INDEX   + "AND SUBSTRING ( CH.CATEGORY_PATH , 0, 8 )  = ?  ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 15 )"
      + " UNION (" + COMMODITY_PLAN_INDEX   + " AND SUBSTRING ( CH.CATEGORY_PATH , 0, 8 )  = ?  ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 15 )"
      + " UNION (" + COMMODITY_PLAN_INDEX   + " AND SUBSTRING ( CH.CATEGORY_PATH , 0, 8 )  = ?  ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 15 )"
      + " UNION (" + COMMODITY_PLAN_INDEX   + " AND SUBSTRING ( CH.CATEGORY_PATH , 0, 8 )  = ?  ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 15 )"
      + " UNION (" + COMMODITY_PLAN_INDEX   + " AND SUBSTRING ( CH.CATEGORY_PATH , 0, 8 )  = ?  ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 15 )"
      + " UNION (" + COMMODITY_PLAN_INDEX   + " AND SUBSTRING ( CH.CATEGORY_PATH , 0, 8 )  = ?  ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 15 )"
      + " UNION (" + COMMODITY_PLAN_INDEX   + " AND SUBSTRING ( CH.CATEGORY_PATH , 0, 8 )  = ?  ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 15 )"
      + " UNION (" + COMMODITY_PLAN_INDEX   + " AND SUBSTRING ( CH.CATEGORY_PATH , 0, 8 )  = ?  ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 15 ) ) T "
      + " ORDER BY COMMODITY_POPULAR_RANK ASC LIMIT ? ";
    
    public static final String GET_HOT_COMMODITY_PLAN = COMMODITY_PLAN_INDEX + " AND CH.NEW_RESERVE_COMMODITY_TYPE1 = 1 "
    +	" ORDER BY COMMODITY_POPULAR_RANK ASC LIMIT ? ";
    
    public static final String GET_COMMODITY_PLAN_FIRST_TIME = COMMODITY_PLAN_INDEX 
    +" AND CH.CATEGORY_PATH LIKE ? ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 10";
    
  public static final String GET_COMMODITY_PLAN_SOME_CATEGOTY = "SELECT * FROM ("
    + "       (" + COMMODITY_PLAN_INDEX   + " AND CH.CATEGORY_PATH LIKE ? ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 3 )"
    + " UNION (" + COMMODITY_PLAN_INDEX   + " AND CH.CATEGORY_PATH LIKE ? ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 3 )"
    + " UNION (" + COMMODITY_PLAN_INDEX   + " AND CH.CATEGORY_PATH LIKE ? ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 3 )"
    + " UNION (" + COMMODITY_PLAN_INDEX   + " AND CH.CATEGORY_PATH LIKE ? ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 3 )"
    + " UNION (" + COMMODITY_PLAN_INDEX   + " AND CH.CATEGORY_PATH LIKE ? ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 3 )"
    + " UNION (" + COMMODITY_PLAN_INDEX   + " AND CH.CATEGORY_PATH LIKE ? ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 3 )"
    + " UNION (" + COMMODITY_PLAN_INDEX   + " AND CH.CATEGORY_PATH LIKE ? ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 3 )"
    + " UNION (" + COMMODITY_PLAN_INDEX   + " AND CH.CATEGORY_PATH LIKE ? ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 3 )";
    
   public static String  getCommodityPlanSomeCategoty(String[] strList){
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT * FROM (("  + COMMODITY_PLAN_INDEX   + " AND CH.CATEGORY_PATH LIKE ? ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 5 )");
      for (int i = 1 ; i < strList.length; i++) {
        sql.append(" UNION (" + COMMODITY_PLAN_INDEX   + " AND CH.CATEGORY_PATH LIKE ? ORDER BY CH.COMMODITY_POPULAR_RANK ASC LIMIT 5 )");
      }
      sql.append(" ) AA ORDER BY AA.COMMODITY_POPULAR_RANK ASC ,AA.CATEGORY_PATH LIMIT 20");
      return sql.toString();
   }
   
   public static String GET_COMMODITY_BY_BRAND_EACH = "  SELECT CHH.BRAND_CODE ,CHH.CATEGORY_PATH,CHH.COMMODITY_POPULAR_RANK,"
     + " CHH.SHOP_CODE, CHH.COMMODITY_CODE,CHH.COMMODITY_NAME,CHH.ORIGINAL_COMMODITY_CODE,CHH.COMBINATION_AMOUNT, "
     + " CHH.COMMODITY_NAME_JP,"
     + " CHH.COMMODITY_NAME_EN,CD.UNIT_PRICE,CASE WHEN DD.DISCOUNT_PRICE IS NOT NULL THEN DD.DISCOUNT_PRICE ELSE CD.DISCOUNT_PRICE END AS DISCOUNT_PRICE,"
     + " CHH.SALE_START_DATETIME,"
     + " CHH.SALE_END_DATETIME,"
     + " CASE WHEN DD.DISCOUNT_START_DATETIME IS NOT NULL THEN DD.DISCOUNT_START_DATETIME ELSE CHH.DISCOUNT_PRICE_START_DATETIME END AS DISCOUNT_PRICE_START_DATETIME,"
     + " CASE WHEN DD.DISCOUNT_END_DATETIME IS NOT NULL THEN DD.DISCOUNT_END_DATETIME ELSE CHH.DISCOUNT_PRICE_END_DATETIME END AS DISCOUNT_PRICE_END_DATETIME,"
     + " CHH.RESERVATION_START_DATETIME,"
     + " CHH.RESERVATION_END_DATETIME,"
     + " CHH.COMMODITY_TAX_TYPE,"
     + " CHH.COMMODITY_DESCRIPTION_PC,"
     + " CHH.STOCK_MANAGEMENT_TYPE,"
     + " CHH.IMPORT_COMMODITY_TYPE,"
     + " CHH.CLEAR_COMMODITY_TYPE,"
     + " CHH.RESERVE_COMMODITY_TYPE1,"
     + " CHH.RESERVE_COMMODITY_TYPE2,"    
     + " CHH.RESERVE_COMMODITY_TYPE3,"    
     + " CHH.NEW_RESERVE_COMMODITY_TYPE1,"  
     + " CHH.NEW_RESERVE_COMMODITY_TYPE2,"  
     + " CHH.NEW_RESERVE_COMMODITY_TYPE3,"  
     + " CHH.NEW_RESERVE_COMMODITY_TYPE4," 
     + " CHH.NEW_RESERVE_COMMODITY_TYPE5," 
     + " CD.INNER_QUANTITY," 
     + " CHH.SALE_FLG"
     + " FROM COMMODITY_HEADER CHH"
     + " INNER JOIN COMMODITY_DETAIL CD ON CD.SHOP_CODE = CHH.SHOP_CODE AND CD.SKU_CODE = CHH.REPRESENT_SKU_CODE   "
     + " LEFT JOIN (SELECT DH.DISCOUNT_START_DATETIME,DH.DISCOUNT_END_DATETIME,DC.DISCOUNT_PRICE,DC.COMMODITY_CODE FROM DISCOUNT_HEADER DH "
     + " INNER JOIN DISCOUNT_COMMODITY DC ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE WHERE DC.USE_FLG = 1 "
     + " AND DH.DISCOUNT_START_DATETIME <= NOW() AND NOW() <= DH.DISCOUNT_END_DATETIME) DD ON DD.COMMODITY_CODE = CHH.COMMODITY_CODE "
   	 + " WHERE (CHH.BRAND_CODE, CHH.COMMODITY_POPULAR_RANK) IN (  "
   	 + " SELECT CH.BRAND_CODE, MIN(CH.COMMODITY_POPULAR_RANK) FROM COMMODITY_HEADER CH "
   	 + " WHERE (NOW() BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME) "
   	 + " AND CH.SALE_FLG = 1 AND CH.CATEGORY_PATH IS NOT NULL   "
   	 + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CH.SHOP_CODE = SHOP_CODE AND ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.COMMODITY_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1) "
   	 + " AND CH.SALE_FLG = 1 AND CH.CATEGORY_PATH IS NOT NULL  "
   	 + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CH.SHOP_CODE = SHOP_CODE AND ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.COMMODITY_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 1 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1)  "
   	 + " AND EXISTS (SELECT 'OK' FROM SHOP SP WHERE SP.SHOP_CODE = CH.SHOP_CODE AND NOW() BETWEEN SP.OPEN_DATETIME AND SP.CLOSE_DATETIME)  "
   	 + " AND EXISTS (SELECT 'OK' FROM DELIVERY_TYPE DJ WHERE DJ.DELIVERY_TYPE_NO = CH.DELIVERY_TYPE_NO AND DJ.SHOP_CODE = CH.SHOP_CODE AND DJ.DISPLAY_FLG = 1)   "
   	 + " AND CH.RESERVE_COMMODITY_TYPE1 <> 1   "
   	 + " AND EXISTS (SELECT  'OK' FROM CATEGORY_COMMODITY CC WHERE CC.SHOP_CODE = CH.SHOP_CODE AND CC.COMMODITY_CODE = CH.COMMODITY_CODE)  "
   	 + " AND CH.DISPLAY_CLIENT_TYPE IN (0,1)  "
   	 + " GROUP BY CH.BRAND_CODE "
   	 + " ORDER BY MIN(CH.COMMODITY_POPULAR_RANK) ASC "
   	 + " LIMIT 20  )";
   
   public static final String GET_AVA_COMMODITY_PLAN = COMMODITY_PLAN_INDEX 
   +" AND ch.CATEGORY_PATH like ? order by ch.COMMODITY_POPULAR_RANK asc limit ?";

    public static final String GET_DISCOUNT_HEADER="SELECT DH.DISCOUNT_CODE ,DH.DISCOUNT_NAME " 
      + "FROM DISCOUNT_HEADER DH INNER JOIN DISCOUNT_COMMODITY DC ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE " 
      + " WHERE DC.COMMODITY_CODE = ? AND DC.USE_FLG = " + UsingFlg.VISIBLE.longValue()
      + " AND DH.DISCOUNT_START_DATETIME <= "
      + SqlDialect.getDefault().getCurrentDatetime() + " AND "
      + SqlDialect.getDefault().getCurrentDatetime()
      + " <= DH.DISCOUNT_END_DATETIME ";
    
    public static final String GET_TMALL_STOCK_ALLOCATION_BY_COMMODITY_CODE = "SELECT * FROM TMALL_STOCK_ALLOCATION WHERE COMMODITY_CODE= ?";
    // 20130613 txw add end
    public static final String GET_COMMODITY_HEADER_BY_SALE_FLG = COMMODITY_BASE_COLUMN 
          + ",A.STOCK_THRESHOLD,A.STOCK_QUANTITY,"
          + " (CASE WHEN " + SqlDialect.getDefault().getCurrentDatetime()
          + " BETWEEN A.RESERVATION_START_DATETIME AND A.RESERVATION_END_DATETIME THEN 0 " + "  WHEN "
          + SqlDialect.getDefault().getCurrentDatetime() + " BETWEEN A.SALE_START_DATETIME AND A.SALE_END_DATETIME THEN 1 "
          + "       ELSE 2 END) AS SALE_STATUS, " + " (CASE WHEN A.SALE_FLG = 1 AND DT.DISPLAY_FLG = 1 "
          + " AND COUNT_CATEGORY_FUNC(A.SHOP_CODE, A.COMMODITY_CODE) > 0 THEN 1 " + "       ELSE 0 END) AS SALE_TYPE "
          + " FROM COMMODITY_LIST_VIEW A "
          + " INNER JOIN DELIVERY_TYPE DT ON DT.SHOP_CODE = A.SHOP_CODE AND DT.DELIVERY_TYPE_NO = A.DELIVERY_TYPE_NO ";
    public static final String GET_STOCK_BY_COMMODITY_CODE="SELECT * FROM STOCK WHERE COMMODITY_CODE= ?";
    // 20130807 txw add start
    public static final String GET_LOGIN_DISCOUNT_COMMODITY_NAME_QUERY="SELECT DH.DISCOUNT_NAME FROM DISCOUNT_HEADER DH"
        + " INNER JOIN DISCOUNT_COMMODITY DC ON DH.DISCOUNT_CODE = DC.DISCOUNT_CODE"
        + " WHERE DC.COMMODITY_CODE = ?"
        + " AND ((DH.DISCOUNT_START_DATETIME >= ? AND DH.DISCOUNT_START_DATETIME <= ?)"
        + "   OR (DH.DISCOUNT_END_DATETIME >= ? AND DH.DISCOUNT_END_DATETIME <= ?)"
        + "   OR (DH.DISCOUNT_START_DATETIME <= ? AND DH.DISCOUNT_END_DATETIME >= ?))";
    // 20130807 txw add end
    // 20130906 txw add start
    public static final String GET_IMAGE_UPLOAD_HISTORY_QUERY="SELECT * FROM IMAGE_UPLOAD_HISTORY WHERE SHOP_CODE = ? AND SKU_CODE = ? AND UPLOAD_COMMODITY_IMG = ?";
    // 20130906 txw add end
    // 20131021 txw add start
    public static final String GET_LOGIN_FREE_POSTAGE_NAME_QUERY="SELECT FPR.FREE_POSTAGE_NAME FROM FREE_POSTAGE_RULE FPR"
        + " WHERE ? IN (SELECT REGEXP_SPLIT_TO_TABLE(FPR.OBJECT_ISSUE_CODE, ';') FROM DUAL)"
        + " AND ((FPR.FREE_START_DATE >= ? AND FPR.FREE_START_DATE <= ?)"
        + "   OR (FPR.FREE_END_DATE >= ? AND FPR.FREE_END_DATE <= ?)"
        + "   OR (FPR.FREE_START_DATE <= ? AND FPR.FREE_END_DATE >= ?))";
    // 20131021 txw add end
    // 20140325 txw add start
    public static String GET_COMMODITY_BY_FREE_POSTAGE = "  SELECT CHH.BRAND_CODE ,CHH.CATEGORY_PATH,CHH.COMMODITY_POPULAR_RANK,"
      + " CHH.SHOP_CODE, CHH.COMMODITY_CODE,CHH.COMMODITY_NAME,CHH.ORIGINAL_COMMODITY_CODE,CHH.COMBINATION_AMOUNT, "
      + " CHH.COMMODITY_NAME_JP,"
      + " CHH.COMMODITY_NAME_EN,CD.UNIT_PRICE,CASE WHEN DD.DISCOUNT_PRICE IS NOT NULL THEN DD.DISCOUNT_PRICE ELSE CD.DISCOUNT_PRICE END AS DISCOUNT_PRICE,"
      + " CHH.SALE_START_DATETIME,"
      + " CHH.SALE_END_DATETIME,"
      + " CASE WHEN DD.DISCOUNT_START_DATETIME IS NOT NULL THEN DD.DISCOUNT_START_DATETIME ELSE CHH.DISCOUNT_PRICE_START_DATETIME END AS DISCOUNT_PRICE_START_DATETIME,"
      + " CASE WHEN DD.DISCOUNT_END_DATETIME IS NOT NULL THEN DD.DISCOUNT_END_DATETIME ELSE CHH.DISCOUNT_PRICE_END_DATETIME END AS DISCOUNT_PRICE_END_DATETIME,"
      + " CHH.RESERVATION_START_DATETIME,"
      + " CHH.RESERVATION_END_DATETIME,"
      + " CHH.COMMODITY_TAX_TYPE,"
      + " CHH.COMMODITY_DESCRIPTION_PC,"
      + " CHH.STOCK_MANAGEMENT_TYPE,"
      + " CHH.IMPORT_COMMODITY_TYPE,"
      + " CHH.CLEAR_COMMODITY_TYPE,"
      + " CHH.RESERVE_COMMODITY_TYPE1,"
      + " CHH.RESERVE_COMMODITY_TYPE2,"    
      + " CHH.RESERVE_COMMODITY_TYPE3,"    
      + " CHH.NEW_RESERVE_COMMODITY_TYPE1,"  
      + " CHH.NEW_RESERVE_COMMODITY_TYPE2,"  
      + " CHH.NEW_RESERVE_COMMODITY_TYPE3,"  
      + " CHH.NEW_RESERVE_COMMODITY_TYPE4," 
      + " CHH.NEW_RESERVE_COMMODITY_TYPE5," 
      + " CD.INNER_QUANTITY," 
      + " CHH.SALE_FLG,"
      + " FCC1.PREFECTURE_CODES"
      + " FROM (SELECT (REGEXP_SPLIT_TO_TABLE(CC.ATTRIBUTR_VALUE,',')) AS COMMODITY_CODE, CM.CAMPAIGN_CODE FROM CAMPAIGN_MAIN CM INNER JOIN CAMPAIGN_CONDITION CC ON CM.CAMPAIGN_CODE = CC.CAMPAIGN_CODE WHERE CM.CAMPAIGN_TYPE = 1 AND (NOW() BETWEEN CM.CAMPAIGN_START_DATE AND CM.CAMPAIGN_END_DATE) AND CC.CAMPAIGN_CONDITION_TYPE = 1) AS FCC"
      + " LEFT JOIN (SELECT CC.ATTRIBUTR_VALUE AS PREFECTURE_CODES, CM.CAMPAIGN_CODE FROM CAMPAIGN_MAIN CM INNER JOIN CAMPAIGN_CONDITION CC ON CM.CAMPAIGN_CODE = CC.CAMPAIGN_CODE WHERE CM.CAMPAIGN_TYPE = 1 AND (NOW() BETWEEN CM.CAMPAIGN_START_DATE AND CM.CAMPAIGN_END_DATE) AND CC.CAMPAIGN_CONDITION_TYPE = 2) AS FCC1 ON FCC.CAMPAIGN_CODE = FCC1.CAMPAIGN_CODE"
      + " INNER JOIN COMMODITY_HEADER CHH ON FCC.COMMODITY_CODE = CHH.COMMODITY_CODE"
      + " INNER JOIN COMMODITY_DETAIL CD ON CD.SHOP_CODE = CHH.SHOP_CODE AND CD.SKU_CODE = CHH.REPRESENT_SKU_CODE   "
      + " LEFT JOIN (SELECT DH.DISCOUNT_START_DATETIME,DH.DISCOUNT_END_DATETIME,DC.DISCOUNT_PRICE,DC.COMMODITY_CODE FROM DISCOUNT_HEADER DH "
      + " INNER JOIN DISCOUNT_COMMODITY DC ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE WHERE DC.USE_FLG = 1 "
      + " AND DH.DISCOUNT_START_DATETIME <= NOW() AND NOW() <= DH.DISCOUNT_END_DATETIME) DD ON DD.COMMODITY_CODE = CHH.COMMODITY_CODE "
      + " WHERE (NOW() BETWEEN CHH.SALE_START_DATETIME AND CHH.SALE_END_DATETIME) "
      + " AND CHH.SALE_FLG = 1 AND CHH.CATEGORY_PATH IS NOT NULL   "
      + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CHH.SHOP_CODE = SHOP_CODE AND ( CASE WHEN CHH.ORIGINAL_COMMODITY_CODE IS NULL THEN CHH.COMMODITY_CODE ELSE CHH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) > 0 OR CHH.STOCK_MANAGEMENT_TYPE = 1) "
      + " AND CHH.SALE_FLG = 1 AND CHH.CATEGORY_PATH IS NOT NULL  "
      + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CHH.SHOP_CODE = SHOP_CODE AND ( CASE WHEN CHH.ORIGINAL_COMMODITY_CODE IS NULL THEN CHH.COMMODITY_CODE ELSE CHH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) >=  CASE WHEN CHH.COMBINATION_AMOUNT IS NULL THEN 1 ELSE CHH.COMBINATION_AMOUNT END OR CHH.STOCK_MANAGEMENT_TYPE = 1)  "
      + " AND EXISTS (SELECT 'OK' FROM SHOP SP WHERE SP.SHOP_CODE = CHH.SHOP_CODE AND NOW() BETWEEN SP.OPEN_DATETIME AND SP.CLOSE_DATETIME)  "
      + " AND EXISTS (SELECT 'OK' FROM DELIVERY_TYPE DJ WHERE DJ.DELIVERY_TYPE_NO = CHH.DELIVERY_TYPE_NO AND DJ.SHOP_CODE = CHH.SHOP_CODE AND DJ.DISPLAY_FLG = 1)   "
      + " AND CHH.RESERVE_COMMODITY_TYPE1 <> 1   "
      + " AND EXISTS (SELECT  'OK' FROM CATEGORY_COMMODITY CC WHERE CC.SHOP_CODE = CHH.SHOP_CODE AND CC.COMMODITY_CODE = CHH.COMMODITY_CODE)  "
      + " AND CHH.DISPLAY_CLIENT_TYPE IN (0,1)          AND (CASE WHEN DD.DISCOUNT_PRICE IS NOT NULL THEN DD.DISCOUNT_PRICE ELSE CD.DISCOUNT_PRICE END)  < 100 "
      + " ORDER BY CHH.COMMODITY_POPULAR_RANK ASC "
      + " LIMIT 10";
    // 20140325 txw add end
    
    // 2014/05/02 京东WBS对应 ob_姚 add start
    public static String GET_JD_COMMODITY_PROPERTY_INPUT = "SELECT cp.* from c_commodity_header c inner join jd_property p "
      + " on c.jd_category_id = p. category_id "
      + " inner join jd_commodity_property cp " 
      + " on c.commodity_code = cp.commodity_code "
      + " and p.property_id = cp.property_id "
      + " where c.shop_code = ? "
      + " and   c.commodity_code = ? "
      + " and   p.input_type = 3 ";
    
    public static String GET_JD_COMMODITY_PROPERTY_NOT_INPUT = "SELECT cp.* from c_commodity_header c inner join jd_property p "
      + " on c.jd_category_id = p. category_id "
      + " inner join jd_commodity_property cp " 
      + " on c.commodity_code = cp.commodity_code "
      + " and p.property_id = cp.property_id "
      + " where c.shop_code = ? "
      + " and   c.commodity_code = ? "
      + " and   (p.input_type = 1 or p.input_type = 2)";
    
    public static final String GET_OPTIONAL_CAMPAIN_LIST = "SELECT * FROM OPTIONAL_CAMPAIGN WHERE CAMPAIGN_START_DATE < NOW() AND CAMPAIGN_END_DATE > NOW() AND RELATED_COMMODITY IS NOT NULL  AND CAMPAIGN_STATUS = 1";
    
    public static final String GET_JD_COMMODITY_PROPERTY_ID = "SELECT distinct property_id FROM jd_commodity_property WHERE COMMODITY_CODE = ?";
    
    public static final String GET_JD_COMMODITY_PROPERTY_VALUE_LIST = "SELECT * FROM jd_commodity_property WHERE COMMODITY_CODE = ? and property_id = ?";
    
    public static final String JD_IMAGE_UPLOAD_HISTORY_UPDATE = "update image_upload_history set jd_att_id=?,jd_image_id=?,jd_upload_time=? where commodity_code=?";
    
    // 删除京东商品类目
    public static final String DELETE_JD_CATEGORY = "DELETE FROM JD_CATEGORY ";
    
    // 删除京东类目属性
    public static final String DELETE_ALL_JD_PROPERTY = "DELETE FROM JD_PROPERTY ";
    
    // 删除京东类目属性值
    public static final String DELETE_ALL_JD_PROPERTY_VALUE = "DELETE FROM JD_PROPERTY_VALUE ";
    
    // 删除指定类目下的京东类目属性
    public static final String DELETE_JD_PROPERTY_BY_CATEGORY_ID = "DELETE FROM JD_PROPERTY WHERE CATEGORY_ID = ? ";
    
    // 删除指定类目下的指定京东类目属性
    public static final String DELETE_JD_PROPERTY_BY_CATEGORY_ID_AND_PROPERTY_ID = "DELETE FROM JD_PROPERTY WHERE CATEGORY_ID = ? AND PROPERTY_ID = ? ";
    
    // 删除指定属性下的京东类目属性值
    public static final String DELETE_JD_PROPERTY_VALUE_BY_PROPERTY_ID = "DELETE FROM JD_PROPERTY_VALUE WHERE PROPERTY_ID = ? ";
    
    // 删除京东品牌
    public static final String DELETE_JD_BRAND = "DELETE FROM JD_BRAND ";
    
    // 查询出最新上传的商品图片的JD用图片ID
    public static final String GET_LASTEST_IMAGE_UPLOAD_HIS = "SELECT * FROM image_upload_history WHERE shop_code = ? AND sku_code = ? AND jd_att_id = ? ORDER BY jd_upload_time DESC ";
    
    // 2014/05/02 京东WBS对应 ob_姚 add end
    
    // 2014/06/17 库存更新对应 ob_李先超 add start
    public static final String GET_TMALL_STOCK_FUNC = "GET_TMALL_STOCK(?)";
    
    public static final String GET_TMALL_ALL_STOCK = " SELECT "
      + GET_TMALL_STOCK_FUNC + " "
      + SqlDialect.getDefault().getDummyTable();
    
    public static final String GET_TMALL_ALLOCATED_STOCK_FUNC = "GET_TMALL_ALLOCATED_STOCK(?)";
    
    public static final String GET_TMALL_ALLOCATED_STOCK = " SELECT "
      + GET_TMALL_ALLOCATED_STOCK_FUNC + " "
      + SqlDialect.getDefault().getDummyTable();
    
    public static final String GET_JD_STOCK_FUNC = "GET_JD_STOCK(?)";
    
    public static final String GET_JD_ALL_STOCK = " SELECT "
      + GET_JD_STOCK_FUNC + " "
      + SqlDialect.getDefault().getDummyTable();
    
    public static final String GET_JD_ALLOCATED_STOCK_FUNC = "GET_JD_ALLOCATED_STOCK(?)";
    
    public static final String GET_JD_ALLOCATED_STOCK = " SELECT "
      + GET_JD_ALLOCATED_STOCK_FUNC + " "
      + SqlDialect.getDefault().getDummyTable();
    // 2014/06/17 库存更新对应 ob_李先超 add end
    
    
    public static final String GET_BRAND_LIST_BY_RELATED_COMMODITY_CODE = " SELECT  DISTINCT BRAND.* " +
    		" FROM BRAND INNER JOIN C_COMMODITY_HEADER CCH ON BRAND.BRAND_CODE = CCH.BRAND_CODE" +
    		" WHERE CCH.COMMODITY_CODE IN "+
        "(SELECT COM.COMMODITY_CODE FROM CATEGORY_COMMODITY  COM INNER JOIN " +
           " CATEGORY_COMMODITY CATE ON COM.CATEGORY_CODE=CATE.CATEGORY_CODE " +
           " WHERE CATE.COMMODITY_CODE=?)";
    
    public static final String GET_CATEGORY_LIST_BY_RELATED_COMMODITY_CODE = " select category.* from category" +
    		" where parent_category_code in" +
    		" ( select cate.parent_category_code from category  cate inner join" +
    		  " category_commodity com on com.category_code=cate.category_code" +
    		    "  where com.commodity_code=? and cate.depth='3')";
    
    
    public static String getRelatedBrandByCommodityCode(Long limitNum){
      return "SELECT * FROM RELATED_BRAND WHERE COMMODITY_CODE=? LIMIT "+limitNum;
    }
    public static String getRelatedSiblingCategoryByCommodityCode(Long limitNum){
      return "SELECT * FROM RELATED_SIBLING_CATEGORY WHERE COMMODITY_CODE=? LIMIT "+limitNum;
    }
   //zzy 2015/01/09 add start
    public static String GET_COMMODITY_SKU="SELECT COUNT(1) FROM COMMODITY_SKU WHERE COMMODITY_CODE = ?";
    public static String GET_COMMODITY_CODE1="SELECT COUNT(1) FROM COMMODITY_MASTER WHERE COMMODITY_CODE= ?";
    public static String GET_COMMODITY_SKUS="SELECT * FROM COMMODITY_SKU WHERE COMMODITY_CODE = ?";
    public static String GET_COMMODITY_HEADER_NAME="select * from commodity_header where commodity_code=?";
    public static String get_Commodity_Sku_Code ="select count(1) from commodity_sku  where commodity_code=? and sku_code=?";
    //zzy 2015/01/14 add end
    public static final String GET_COMMODITY_MASTER_BY_TMALL_CODE = "SELECT * FROM COMMODITY_MASTER"
      +" WHERE COMMODITY_CODE =? AND TMALL_COMMODITY_CODE=? ";
    
    public static final String GET_COMMODITY_SKU_BY_TMALL_CODE = "SELECT * FROM COMMODITY_SKU"
      +" WHERE  COMMODITY_CODE =? AND SKU_CODE=? and TMALL_USE_FLAG="+UsingFlg.VISIBLE.longValue();
    
    public static final String GET_COMMODITY_MASTER_BY_JD_CODE = "SELECT * FROM COMMODITY_MASTER"
      +" WHERE JD_COMMODITY_CODE=? ";
    
    public static final String GET_COMMODITY_SKU_BY_JD_CODE = "SELECT * FROM COMMODITY_SKU"
      +" WHERE  COMMODITY_CODE =? AND SKU_CODE=? and JD_USE_FLAG="+UsingFlg.VISIBLE.longValue();
    
    public static String GET_WAREHOUSE_SKUNAME="select commodity_name from commodity_header where commodity_code=?";
    public static String GET_JD_BJ_COMMODITY="select sku_code from  jd_bj_commodity where sku_code=?";
    public static String GET_JD_GZ_COMMODITY="select sku_code from  jd_gz_commodity where sku_code=?";
    public static String GET_TMALL_BJ_COMMODITY="select sku_code from tmall_bj_commodity where sku_code=?";
    public static String GET_TMALL_GZ_COMMODITY="select sku_code from  tmall_gz_commodity where sku_code=?";
    public static String GET_TMALL_BJ_COMMODITY_LIST="SELECT SKU_CODE FROM TMALL_BJ_COMMODITY";
    public static String GET_TMALL_GZ_COMMODITY_LIST="SELECT SKU_CODE FROM TMALL_GZ_COMMODITY";
    public static String GET_COMMODITY_SKU_CODES = "SELECT COMMODITY_CODE FROM COMMODITY_SKU WHERE SKU_CODE=?";
}
