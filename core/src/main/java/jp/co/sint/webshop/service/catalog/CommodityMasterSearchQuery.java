package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.StringUtil;
public class CommodityMasterSearchQuery extends AbstractQuery<CommodityMasterResult> {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public CommodityMasterSearchQuery() {
  }
  
  public CommodityMasterSearchQuery (CommodityMasterSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    builder.append("SELECT COMMODITY_CODE,COMMODITY_NAME,JD_COMMODITY_CODE,TMALL_COMMODITY_CODE FROM COMMODITY_MASTER WHERE 1=1");
    if (StringUtil.hasValue(condition.getCommodityCode())) {
      builder.append(" AND COMMODITY_CODE = ? ");
      params.add(condition.getCommodityCode());  
    }  
    if(StringUtil.hasValue(condition.getCommodityName())){
      builder.append(" AND COMMODITY_NAME LIKE ?");
      params.add("%"+condition.getCommodityName()+"%");
    }
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }
  
  public Class<CommodityMasterResult> getRowType() {
    return CommodityMasterResult.class;
  }
}
