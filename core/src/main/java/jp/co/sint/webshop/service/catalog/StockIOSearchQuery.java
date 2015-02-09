package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.StockIODetail;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;

public class StockIOSearchQuery extends AbstractQuery<StockIODetail> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public StockIOSearchQuery createStockIOSearchQuery(StockIOSearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append(" SELECT  A.STOCK_IO_ID, "
        + "         A.SHOP_CODE,  "
        + "         A.STOCK_IO_DATE, "
        + "         A.SKU_CODE,  "
        + "         A.STOCK_IO_QUANTITY, "
        + "         A.STOCK_IO_TYPE, "
        + "         A.MEMO "
        + " FROM    STOCK_IO_DETAIL A "
        + " WHERE   A.SHOP_CODE = ? AND "
        + "         A.SKU_CODE  = ? ");

    params.add(condition.getSearchShopCode());
    params.add(condition.getSearchSkuCode());

    // 入出庫日の範囲検索
    if (DateUtil.isCorrect(condition.getSearchStockIODateStart()) && DateUtil.isCorrect(condition.getSearchStockIODateEnd())) {
      builder.append(" AND A.STOCK_IO_DATE BETWEEN TO_DATE(?,'yyyy-mm-dd') AND TO_DATE(?,'yyyy-mm-dd')");
      params.add(condition.getSearchStockIODateStart());
      params.add(condition.getSearchStockIODateEnd());
    } else if (DateUtil.isCorrect(condition.getSearchStockIODateStart())) {
      builder.append(" AND A.STOCK_IO_DATE >= TO_DATE(?,'yyyy-mm-dd')");
      params.add(condition.getSearchStockIODateStart());
    } else if (DateUtil.isCorrect(condition.getSearchStockIODateEnd())) {
      builder.append(" AND A.STOCK_IO_DATE <= TO_DATE(?,'yyyy-mm-dd')");
      params.add(condition.getSearchStockIODateEnd());
    }

    // 入出庫区分の検索
    if (StringUtil.hasValueAnyOf(condition.getStockIOType())) {
      builder.append(" AND (A.STOCK_IO_TYPE = ?");
      if (condition.getStockIOType().length > 1) {
        for (int i = 1; i < condition.getStockIOType().length; i++) {
          builder.append(" OR A.STOCK_IO_TYPE = ?");
        }
      }
      builder.append(" )");

      for (int i = 0; i < condition.getStockIOType().length; i++) {
        params.add(condition.getStockIOType()[i]);
      }
    } else {
      builder.append(" AND 1 = 0");
    }

    builder.append(" ORDER BY A.STOCK_IO_DATE DESC, A.ORM_ROWID DESC");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }

  public Class<StockIODetail> getRowType() {
    return StockIODetail.class;
  }
}
