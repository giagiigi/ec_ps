package jp.co.sint.webshop.service.catalog;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.ShippingStatus; // 10.1.7 10295 追加
import jp.co.sint.webshop.data.dto.ArrivalGoods;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityExt;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CampaignCommodity;
import jp.co.sint.webshop.data.dto.CategoryAttributeValue;
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.data.dto.CommodityAccessLog;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.FavoriteCommodity;
import jp.co.sint.webshop.data.dto.GiftCommodity;
import jp.co.sint.webshop.data.dto.JdStockAllocation;
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
import jp.co.sint.webshop.data.dto.TmallStockAllocation;
import jp.co.sint.webshop.utility.ArrayUtil;


public final class CommodityDeleteQuery {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private CommodityDeleteQuery() {    
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
    DatabaseUtil.getTableName(CommodityHeader.class)
  };
  
  /** ショップコード、SKUコードに関連付いているテーブルリスト */
  private static final String[] SKU_TABLE_LIST = {
    DatabaseUtil.getTableName(ArrivalGoods.class),
    DatabaseUtil.getTableName(FavoriteCommodity.class),
    DatabaseUtil.getTableName(Stock.class),
    DatabaseUtil.getTableName(StockIODetail.class),
    DatabaseUtil.getTableName(CommodityDetail.class)
  };
  
  private static final String[] STITUTE_TABLE_LIST = {
    DatabaseUtil.getTableName(Stock.class),
    DatabaseUtil.getTableName(CategoryCommodity.class),
    DatabaseUtil.getTableName(CCommodityDetail.class),
    DatabaseUtil.getTableName(CCommodityHeader.class),
    DatabaseUtil.getTableName(TmallStockAllocation.class),
    // 2014/06/14 库存更新对应 ob_卢 add start
    DatabaseUtil.getTableName(JdStockAllocation.class),
    DatabaseUtil.getTableName(CCommodityExt.class),
    // 2014/06/14 库存更新对应 ob_卢 add end
    DatabaseUtil.getTableName(CategoryAttributeValue.class)

  };
  /** ショップコード、SKUコードに関連付いているテーブルリスト */
  private static final String[] CC_SKU_TABLE_LIST = {
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

  public static String getCommoditySkuQuery() {
    final String sql = DatabaseUtil.getSelectAllQuery(CommodityDetail.class)
                     + " WHERE SHOP_CODE = ? "
                     + " AND   COMMODITY_CODE = ? "
                     + " ORDER BY DISPLAY_ORDER, SKU_CODE ";
    return sql;
  }
  public static String getCCommoditySkuQuery() {
    final String sql = DatabaseUtil.getSelectAllQuery(CCommodityDetail.class)
                     + " WHERE SHOP_CODE = ? "
                     + " AND   COMMODITY_CODE = ? "
                     + " ORDER BY  SKU_CODE ";
    return sql;
  }
  /** ショップコード、商品コードに関連付く商品を削除するクエリ */
  private static final String COMMODITY_DELETE_QUERY = " DELETE FROM {0} WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? ";
  
  /** ショップコード、SKUコードに関連付くSKUを削除するクエリ */
  private static final String SKU_DELETE_QUERY = " DELETE FROM {0} WHERE SHOP_CODE = ? AND SKU_CODE = ? ";
    
  private static final String STITUTE_DELETE_QUERY="DELETE FROM {0} WHERE COMMODITY_CODE = ?";
  public static String[] getCommodityDeleteQuery() {
    return getFormatedQueryList(COMMODITY_DELETE_QUERY, COMMODITY_TABLE_LIST);
  }
  
  public static String[] getSkuDeleteQuery() {
    return getFormatedQueryList(SKU_DELETE_QUERY, SKU_TABLE_LIST);
  }
  
  public static String[] getstituteDeleteQuery() {
    return getFormatedQueryList(STITUTE_DELETE_QUERY, STITUTE_TABLE_LIST);
  }
  
  /**
   * add by os014 2012-01-31 
   * @return
   */
  public static String[] getCcSkuDeleteQuery() {
    return getFormatedQueryList(SKU_DELETE_QUERY, CC_SKU_TABLE_LIST);
  }
  
  public static String getArrivalGoodsDeleteQuery() {
    final String sql = " DELETE FROM ARRIVAL_GOODS WHERE SHOP_CODE = ? AND SKU_CODE = ? ";
    return sql;
  }
//add by tangweihui 2012-11-16 start
  //更新setCommodityFlg的值
  public static String setCommodityFlgUpdateQuery() {
    final String sql = " UPDATE  COMMODITY_HEADER SET SET_COMMODITY_FLG=?  WHERE SHOP_CODE = ?  AND COMMODITY_CODE = ?";
    return sql;
  }
//add by tangweihui 2012-11-16 end

  private static String[] getFormatedQueryList(String query, String[] tableList) {
    List<String> queryList = new ArrayList<String>();
    for (String tableName : tableList) {
      queryList.add(MessageFormat.format(query, tableName));
    }
    return ArrayUtil.toArray(queryList, String.class);
  }

}
