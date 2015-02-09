package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.utility.StringUtil;

public class CCommodityDetailQuery extends AbstractQuery<CCommodityDetail> {

  private static final long serialVersionUID = 1L;


  public CCommodityDetailQuery(String commodityCode) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append(BASE_QUERY);
    
    if (StringUtil.hasValue(commodityCode)) {
      builder.append(" AND CCD.COMMODITY_CODE = ? ");
      params.add(commodityCode);
    }
    setSqlString(builder.toString());
    setParameters(params.toArray());
  }
  
  public CCommodityDetailQuery() {
    setSqlString(BASE_QUERY);
  }

  // 特别注明:tmall_discount_price在前台用作京东售价
  private static final String BASE_QUERY = "SELECT CCD.sku_code, "
  		+ "   CCD.unit_price, "
      + "   CCD.discount_price, "
      + "   CCD.tmall_unit_price, "
      + "   CCD.tmall_discount_price, "
      + "   CCD.average_cost, "
      + "   CCD.tax_class "
      + "   FROM C_COMMODITY_DETAIL CCD "
      + "   where 1 = 1 ";
  

  public Class<CCommodityDetail> getRowType() {
    return CCommodityDetail.class;
  }

}