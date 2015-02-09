package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.CommodityPriceChangeHistory;
import jp.co.sint.webshop.utility.StringUtil;

public class CommodityPriceChangeHistoryQuery extends AbstractQuery<CommodityPriceChangeHistory> {

  private static final long serialVersionUID = 1L;

  // 查询操作 
  public CommodityPriceChangeHistoryQuery(CommodityPriceChangeHistoryCondition commondityPriceChangeHistory) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    
    builder.append(BASE_QUERY);
    
    if (StringUtil.hasValue(commondityPriceChangeHistory.getReviewOrNotFlg())) {
      params.add(commondityPriceChangeHistory.getReviewOrNotFlg());
    }
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(commondityPriceChangeHistory.getCurrentPage());
    setPageSize(commondityPriceChangeHistory.getPageSize());
    setMaxFetchSize(commondityPriceChangeHistory.getMaxFetchSize());
  }
  

  // 当每个编号最新的一条记录的reveiw_or_not_flg=0且4个毛利率其中之一小于0时，显示该最新记录，否则不显示。
  private static final String BASE_QUERY = "SELECT CPCH.commodity_code, "
      + "   CPCH.submit_time, "
      + "   CPCH.responsible_person, "
      + "   CPCH.old_official_price, "
      + "   CPCH.new_official_price, "
      + "   CPCH.old_official_special_price, "
      + "   CPCH.new_official_special_price, "
      + "   CPCH.old_tmall_price, "
      + "   CPCH.new_tmall_price, "
      + "   CPCH.old_jd_price, "
      + "   CPCH.new_jd_price, "
      + "   CPCH.ec_profit_margin, "
      + "   CPCH.ec_special_profit_margin, "
      + "   CPCH.tmall_profit_margin, "
      + "   CPCH.jd_profit_margin "
      + "   FROM COMMODITY_PRICE_CHANGE_HISTORY CPCH "
      + "   LEFT JOIN C_COMMODITY_HEADER CCH ON CCH.commodity_code = CPCH.commodity_code"
      + "   where not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where CPCH.commodity_code = commodity_code and CPCH.submit_time < submit_time) AND CPCH.review_or_not_flg = ? "
      + "   AND (CPCH.ec_profit_margin < 0 OR CPCH.ec_special_profit_margin < 0 OR CPCH.tmall_profit_margin < 0 OR CPCH.jd_profit_margin < 0) "
      + "   AND (CCH.clear_commodity_type <> 1)"
      + "   ORDER BY CPCH.submit_time desc ";
  
  public Class<CommodityPriceChangeHistory> getRowType() {
    return CommodityPriceChangeHistory.class;
  }

}
