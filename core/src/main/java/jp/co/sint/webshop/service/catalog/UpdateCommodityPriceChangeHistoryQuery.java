package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CommodityPriceChangeHistory;
import jp.co.sint.webshop.service.analysis.PostgresDatabaseAnalysisQuery;
import jp.co.sint.webshop.utility.StringUtil;


public class UpdateCommodityPriceChangeHistoryQuery extends PostgresDatabaseAnalysisQuery{
  
  private static final long serialVersionUID = 1L;
  
  StringBuilder builder = new StringBuilder();
  List<Object> params = new ArrayList<Object>();
  
  // 插入操作
  public UpdateCommodityPriceChangeHistoryQuery(String commodityCode, String updatedUser, String updatedDatetime) {

      builder.append(UPDATE_SQL);
      
      if (StringUtil.hasValue(updatedUser)) {
        params.add(updatedUser);
      } else {
        try {
          throw new Exception();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      
      if (StringUtil.hasValue(updatedDatetime)) {
        params.add(updatedDatetime);
      } else {
        try {
          throw new Exception();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      
      if (StringUtil.hasValue(commodityCode)) {
        params.add(commodityCode);
      } else {
        try {
          throw new Exception();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      setSqlString(builder.toString());
      setParameters(params.toArray());
  }
  
  public Object[][] getUpdateParameters() {
    Object[] p = params.toArray();
    Object[][] p2 = {p};
    return p2;
  }
  
  private static final String UPDATE_SQL = "UPDATE COMMODITY_PRICE_CHANGE_HISTORY CPCH SET REVIEW_OR_NOT_FLG = 1, updated_user = ?, updated_datetime = ?"
                                         + "where not exists(select 1 from COMMODITY_PRICE_CHANGE_HISTORY where CPCH.commodity_code = commodity_code and CPCH.submit_time < submit_time) AND CPCH.review_or_not_flg = 0 "
                                         + "AND COMMODITY_CODE = ?";
  public Class<CommodityPriceChangeHistory> getRowType() {
    return CommodityPriceChangeHistory.class;
  }
  
}
