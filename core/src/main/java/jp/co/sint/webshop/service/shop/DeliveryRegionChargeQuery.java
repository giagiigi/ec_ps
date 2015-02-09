package jp.co.sint.webshop.service.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 运费设定执行复杂查询
 * @author YLP
 */
public class DeliveryRegionChargeQuery extends AbstractQuery<DeliveryRegionChargeInfo> {

  private static final long serialVersionUID = 1L;
  
  private static final String LOAD_DELIVERY_REGION_CHARGE= "SELECT DRC.*,PR.PREFECTURE_NAME FROM DELIVERY_REGION_CHARGE DRC  " +
  		" INNER JOIN PREFECTURE PR "+
        "ON DRC.PREFECTURE_CODE = PR.PREFECTURE_CODE WHERE DRC.DELIVERY_COMPANY_NO = ?";
  /**
   */
  public DeliveryRegionChargeQuery() {

  }
  /**
   */
  public DeliveryRegionChargeQuery(DeliveryRegionChargeCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    builder.append(LOAD_DELIVERY_REGION_CHARGE);
    builder.append(" ORDER BY DRC.PREFECTURE_CODE");
    if (StringUtil.hasValue(condition.getDeliveryCompanyNo())) {
      params.add(condition.getDeliveryCompanyNo());
    }
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  public Class<DeliveryRegionChargeInfo> getRowType() {
    return DeliveryRegionChargeInfo.class;
  }
}
