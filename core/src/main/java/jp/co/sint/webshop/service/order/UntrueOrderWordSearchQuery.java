package jp.co.sint.webshop.service.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.StringUtil;
public class UntrueOrderWordSearchQuery extends AbstractQuery<UntrueOrderWordResult> {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public UntrueOrderWordSearchQuery() {
  }
  
  public UntrueOrderWordSearchQuery (UntrueOrderWordSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    builder.append("SELECT ORDER_WORD_CODE,ORDER_WORD_NAME FROM UNTRUE_ORDER_WORD WHERE 1=1 ");
    
    if (StringUtil.hasValue(condition.getOrderWordName())) {
      builder.append(" AND ORDER_WORD_NAME LIKE ?"); 
      params.add("%"+condition.getOrderWordName()+"%");  
    }  
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }
  

  public Class<UntrueOrderWordResult> getRowType() {
    return UntrueOrderWordResult.class;
  }
}
