package jp.co.sint.webshop.service.data.csv;


import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerServiceQuery;

public class CustomerExportDataSource extends SqlExportDataSource<CustomerCsvSchema, CustomerExportCondition> {

  @Override
  public Query getExportQuery() {
    
    String sql = CustomerServiceQuery.EXPORT_CUSTOMER_QUERY + " ORDER BY CUSTOMER_CODE ";
    // 10.1.3 10150 修正 ここから
    // Query q = new SimpleQuery(sql, CustomerStatus.MEMBER.getValue(), CustomerConstant.SELFE_ADDRESS_NO);
    Query q = new SimpleQuery(sql, CustomerStatus.MEMBER.getValue(), CustomerConstant.SELF_ADDRESS_NO);
    // 10.1.3 10150 修正 ここまで
//    params.add(NumUtil.parseLong(NumUtil.getScale(condition.getScale())));
//    params.add(PointAmplificationRate.fromValue(String.valueOf(NumUtil.getScale(condition.getScale()))).getName());
//    params.add(NumUtil.parseLong(NumUtil.getScale(condition.getScale())));
//    params.add(PointAmplificationRate.fromValue(String.valueOf(NumUtil.getScale(condition.getScale()))).getName());
    return q;
  }

}
