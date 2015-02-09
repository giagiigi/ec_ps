package jp.co.sint.webshop.service.catalog;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.ShippingStatus; // 10.1.7 10295 追加
import jp.co.sint.webshop.data.dto.ArrivalGoods;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CampaignCommodity;
import jp.co.sint.webshop.data.dto.CategoryAttributeValue;
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.data.dto.CommodityAccessLog;
import jp.co.sint.webshop.data.dto.FavoriteCommodity;
import jp.co.sint.webshop.data.dto.GiftCommodity;
import jp.co.sint.webshop.data.dto.PopularRankingDetail;
import jp.co.sint.webshop.data.dto.RecommendedCommodity;
import jp.co.sint.webshop.data.dto.RelatedCommodityA;
import jp.co.sint.webshop.data.dto.RelatedCommodityB;
import jp.co.sint.webshop.data.dto.ReviewPost;
import jp.co.sint.webshop.data.dto.ReviewSummary;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.StockIODetail;
import jp.co.sint.webshop.data.dto.TagCommodity;
import jp.co.sint.webshop.utility.ArrayUtil;


public final class CCommodityDeleteQuery {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private CCommodityDeleteQuery() {    
  }
  
  /** ショップコード、商品コードに関連付いているテーブルリスト */
  private static final String[] COMMODITY_TABLE_LIST = {
    DatabaseUtil.getTableName(CampaignCommodity.class),
    DatabaseUtil.getTableName(CategoryAttributeValue.class),
    DatabaseUtil.getTableName(CategoryCommodity.class),
    DatabaseUtil.getTableName(CommodityAccessLog.class),
    DatabaseUtil.getTableName(GiftCommodity.class),
    DatabaseUtil.getTableName(PopularRankingDetail.class),
    DatabaseUtil.getTableName(RecommendedCommodity.class),
    DatabaseUtil.getTableName(RelatedCommodityA.class),
    DatabaseUtil.getTableName(RelatedCommodityB.class),
    DatabaseUtil.getTableName(ReviewPost.class),
    DatabaseUtil.getTableName(ReviewSummary.class),
    DatabaseUtil.getTableName(TagCommodity.class),
    DatabaseUtil.getTableName(CCommodityHeader.class)
  };
  
  /** ショップコード、SKUコードに関連付いているテーブルリスト */
  private static final String[] SKU_TABLE_LIST = {
    DatabaseUtil.getTableName(ArrivalGoods.class),
    DatabaseUtil.getTableName(FavoriteCommodity.class),
    DatabaseUtil.getTableName(Stock.class),
    DatabaseUtil.getTableName(StockIODetail.class),
    DatabaseUtil.getTableName(CCommodityDetail.class)
  };
  
  public static String getNotFixedSaleCommodityCountQuery() {
    final String sql = ""
      + " SELECT COUNT(*)  "
      + "   FROM " + DatabaseUtil.getTableName(ShippingHeader.class) + " SH "
      + "  INNER JOIN " + DatabaseUtil.getTableName(ShippingDetail.class) + " SD ON SD.SHIPPING_NO = SH.SHIPPING_NO "
      + "  WHERE SD.SHOP_CODE = ?  "
      + "    AND SD.SKU_CODE = ?  "
      + "    AND SH.SHIPPING_STATUS <> " + ShippingStatus.CANCELLED.getValue() // 10.1.7 10295 追加
      + "    AND SH.FIXED_SALES_STATUS = " + FixedSalesStatus.NOT_FIXED.getValue();
    return sql;
  }

  public static String getCCommoditySkuQuery() {
    final String sql = DatabaseUtil.getSelectAllQuery(CCommodityDetail.class)
                     + " WHERE SHOP_CODE = ? "
                     + " AND   COMMODITY_CODE = ? "
                     + " ORDER BY DISPLAY_ORDER, SKU_CODE ";
    return sql;
  }
  /** ショップコード、商品コードに関連付く商品を削除するクエリ */
  private static final String COMMODITY_DELETE_QUERY = " DELETE FROM {0} WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? ";
  
  /** ショップコード、SKUコードに関連付くSKUを削除するクエリ */
  private static final String SKU_DELETE_QUERY = " DELETE FROM {0} WHERE SHOP_CODE = ? AND SKU_CODE = ? ";
    
  public static String[] getCommodityDeleteQuery() {
    return getFormatedQueryList(COMMODITY_DELETE_QUERY, COMMODITY_TABLE_LIST);
  }
  
  public static String[] getSkuDeleteQuery() {
    return getFormatedQueryList(SKU_DELETE_QUERY, SKU_TABLE_LIST);
  }
  
  public static String getArrivalGoodsDeleteQuery() {
    final String sql = " DELETE FROM ARRIVAL_GOODS WHERE SHOP_CODE = ? AND SKU_CODE = ? ";
    return sql;
  }

  private static String[] getFormatedQueryList(String query, String[] tableList) {
    List<String> queryList = new ArrayList<String>();
    for (String tableName : tableList) {
      queryList.add(MessageFormat.format(query, tableName));
    }
    return ArrayUtil.toArray(queryList, String.class);
  }

}
