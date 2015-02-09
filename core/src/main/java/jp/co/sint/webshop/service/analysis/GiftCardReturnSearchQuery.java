package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.GiftCardReturnApply;
import jp.co.sint.webshop.service.communication.GiftCardReturnListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.DateSearchAccuracy;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class GiftCardReturnSearchQuery extends AbstractQuery<GiftCardReturnApply> {

  private static final long serialVersionUID = 1L;

  public GiftCardReturnSearchQuery(GiftCardReturnListSearchCondition condition, boolean flag) {
	  buildQuery(ANALYSIS_QUERY, condition, flag,false);
  }


  public GiftCardReturnSearchQuery(GiftCardReturnListSearchCondition condition, boolean flag,boolean flg) {
	  buildQuery(ANALYSIS_CSV_QUERY, condition, flag,flg);
  }
  
  private static final String ANALYSIS_CSV_QUERY = "";
  
  private static final String ANALYSIS_QUERY = " SELECT * FROM GIFT_CARD_RETURN_APPLY WHERE ";
  
  /**
   * SQL语句查询条件
   * @param query 查询语句
   * @param condition 查询条件实体
   * @param flag true:顾客别查询; false :公共优惠劵查询     
   * @param flg  true:顾客别CSV查询 false:公共优惠劵查询
   * 
   * */
  private void buildQuery(String query, GiftCardReturnListSearchCondition condition, boolean flag,boolean flg) {
    StringBuilder builder = new StringBuilder(query);
		builder.append(" 1 = 1 ");
		
    List<Object> params = new ArrayList<Object>();
    // 检索条件：订单编号
    if (StringUtil.hasValue(condition.getSearchOrderNo())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("ORDER_NO", condition.getSearchOrderNo(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 检索条件：确认标志
    if (StringUtil.hasValue(condition.getSearchReturnFlg())) {
      builder.append(" AND CONFIRM_FLG = ? " );
      params.add(condition.getSearchReturnFlg());
    }
    
    // 检索条件：发货时间
    if (StringUtil.hasValueAnyOf(condition.getSearchReturnDatetimeFrom(), condition.getSearchReturnDatetimeTo())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createDateRangeClause("return_date", DateUtil.fromString(condition
          .getSearchReturnDatetimeFrom()), DateUtil.fromString(condition.getSearchReturnDatetimeTo()), DateSearchAccuracy.DATE);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    
    
    
    //排序
    builder.append(" ORDER BY ORDER_NO");
    
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  public Class<GiftCardReturnApply> getRowType() {
    return GiftCardReturnApply.class;
  }

}
