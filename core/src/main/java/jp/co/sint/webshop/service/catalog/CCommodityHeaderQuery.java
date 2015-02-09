package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.utility.StringUtil;

public class CCommodityHeaderQuery extends AbstractQuery<CCommodityHeader> {

  private static final long serialVersionUID = 1L;


  public CCommodityHeaderQuery(String commodityCode) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append(BASE_QUERY);
    
    if (StringUtil.hasValue(commodityCode)) {
      builder.append(" AND CCH.COMMODITY_CODE = ? ");
      params.add(commodityCode);
    }
    setSqlString(builder.toString());
    setParameters(params.toArray());
  }
  
  public CCommodityHeaderQuery() {
    setSqlString(BASE_QUERY);
  }

  // 特别注明:tmall_discount_price在前台用作京东售价
  private static final String BASE_QUERY = "SELECT CCH.clear_commodity_type, "
      + "   CCH.discount_price_start_datetime, "
      + "   CCH.discount_price_end_datetime "
      + "   FROM C_COMMODITY_HEADER CCH "
      + "   where 1 = 1 ";
  

  public Class<CCommodityHeader> getRowType() {
    return CCommodityHeader.class;
  }

}