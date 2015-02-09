package jp.co.sint.webshop.service.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

public class InformationCountSearchQuery extends SimpleQuery {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public InformationCountSearchQuery() {
  }

  public InformationCountSearchQuery(InformationCountSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT COUNT(INFORMATION_NO) FROM INFORMATION ");
    builder.append("WHERE 1=1 ");
//  postgreSQL start    
//    builder.append("AND INFORMATION_START_DATETIME <= SYSDATE ");
//    builder.append("AND INFORMATION_END_DATETIME >= SYSDATE ");
    builder.append("AND INFORMATION_START_DATETIME <= "+SqlDialect.getDefault().getCurrentDatetime()+" ");
    builder.append("AND INFORMATION_END_DATETIME >= "+SqlDialect.getDefault().getCurrentDatetime()+" ");    
//  postgreSQL end
    
    if (StringUtil.hasValue(condition.getInformationType())) {
      builder.append("AND INFORMATION_TYPE = ? ");
      params.add(condition.getInformationType());
    }
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

}
