package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.service.communication.GiftCardDetailListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class GiftCardDetailSearchQuery extends AbstractQuery<GiftCardDetailListSummary> {

  private static final long serialVersionUID = 1L;

  public GiftCardDetailSearchQuery(GiftCardDetailListSearchCondition condition, boolean flag) {
	  buildQuery(ANALYSIS_QUERY, condition, flag,false);
  }


  public GiftCardDetailSearchQuery(GiftCardDetailListSearchCondition condition, boolean flag,boolean flg) {
	  buildQuery(ANALYSIS_CSV_QUERY, condition, flag,flg);
  }
  
  private static final String ANALYSIS_CSV_QUERY = "";
  
  private static final String ANALYSIS_QUERY = "SELECT GCID.CARD_CODE, GCID.CARD_NAME ,GCID.ISSUE_DATE AS ISSUE_TIME ,COUNT(GCID.CARD_ID) AS ISSUE_NUM ," 
  	+	"SUM (GCID.UNIT_PRICE) AS TOTAL_SALE_PRICE ,SUM (GCID.DENOMINATION) AS TOTAL_DENOMINATION "
    + " ,SUM(CASE WHEN CCI.DENOMINATION IS NULL THEN 0 ELSE CCI.DENOMINATION END) AS ACTIVATE_AMOUNT  ,SUM (GCID.DENOMINATION)-SUM(CASE WHEN CCI.DENOMINATION IS NULL THEN 0 ELSE CCI.DENOMINATION END) AS UNACT_AMOUNT "
		+ " ,(SELECT CASE WHEN SUM(CCUI.USE_AMOUNT) IS NULL THEN 0 ELSE SUM(CCUI.USE_AMOUNT) END FROM GIFT_CARD_ISSUE_DETAIL GCID2 INNER JOIN CUSTOMER_CARD_USE_INFO CCUI ON GCID2.CARD_ID = CCUI.CARD_ID INNER JOIN SHIPPING_HEADER SH ON CCUI.ORDER_NO = SH.ORDER_NO AND SH.SHIPPING_STATUS = 3 AND SH.RETURN_ITEM_TYPE <> 1 WHERE GCID2.CARD_STATUS = 1 AND GCID2.CARD_CODE = GCID.CARD_CODE AND GCID2.CARD_HISTORY_NO = GCID.CARD_HISTORY_NO AND GCID2.CANCEL_FLG = 0 and ccui.use_status = 0)  - (select case when sum(gcrc.return_amount) is null then 0 else sum(gcrc.return_amount) end from gift_card_return_confirm gcrc inner join GIFT_CARD_ISSUE_DETAIL GCID3 on gcrc.card_id = gcid3.card_id and  gcid3.card_code = gcid.card_code) AS USE_AMOUNT ,  "
		+ " SUM(CASE WHEN CCI.DENOMINATION IS NULL THEN 0 ELSE CCI.DENOMINATION END) - (SELECT CASE WHEN SUM(CCUI.USE_AMOUNT) IS NULL THEN 0 ELSE SUM(CCUI.USE_AMOUNT) END FROM GIFT_CARD_ISSUE_DETAIL GCID2 INNER JOIN CUSTOMER_CARD_USE_INFO CCUI ON GCID2.CARD_ID = CCUI.CARD_ID INNER JOIN SHIPPING_HEADER SH ON CCUI.ORDER_NO = SH.ORDER_NO AND SH.SHIPPING_STATUS = 3 AND SH.RETURN_ITEM_TYPE <> 1 WHERE GCID2.CARD_STATUS = 1 AND GCID2.CARD_CODE = GCID.CARD_CODE AND GCID2.CARD_HISTORY_NO = GCID.CARD_HISTORY_NO AND GCID2.CANCEL_FLG = 0 and ccui.use_status = 0)  + (select case when sum(gcrc.return_amount) is null then 0 else sum(gcrc.return_amount) end from gift_card_return_confirm gcrc inner join GIFT_CARD_ISSUE_DETAIL GCID3 on gcrc.card_id = gcid3.card_id and  gcid3.card_code = gcid.card_code)  AS LEFT_AMOUNT  "
		+ " FROM GIFT_CARD_ISSUE_DETAIL GCID  "
		+ " INNER JOIN GIFT_CARD_RULE GCR ON GCID.CARD_CODE  = GCR.CARD_CODE  "
		+ " INNER JOIN GIFT_CARD_ISSUE_HISTORY GCIH ON GCIH.CARD_CODE = GCID.CARD_CODE AND GCIH.CARD_HISTORY_NO = GCID.CARD_HISTORY_NO AND GCID.CANCEL_FLG = 0 "
		+ " LEFT JOIN CUSTOMER_CARD_INFO CCI ON CCI.CARD_ID = GCID.CARD_ID  WHERE ";
  
  /**
   * SQL语句查询条件
   * @param query 查询语句
   * @param condition 查询条件实体
   * @param flag true:顾客别查询; false :公共优惠劵查询     
   * @param flg  true:顾客别CSV查询 false:公共优惠劵查询
   * 
   * */
  private void buildQuery(String query, GiftCardDetailListSearchCondition condition, boolean flag,boolean flg) {
    StringBuilder builder = new StringBuilder(query);
		builder.append(" 1 = 1 ");
		
    List<Object> params = new ArrayList<Object>();
    // 检索条件：礼品卡编号
    if (StringUtil.hasValue(condition.getSearchGiftCardCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("GCR.CARD_CODE", condition.getSearchGiftCardCode(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 检索条件：礼品卡名称
    if (StringUtil.hasValue(condition.getSearchGiftCardName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("GCR.CARD_NAME", condition
          .getSearchGiftCardName(), LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    
    // 检索条件：优惠券利用开始日时
    if (StringUtil.hasValueAnyOf(condition.getSearchMinIssueStartDatetimeFrom(), condition.getSearchMinIssueStartDatetimeTo())) {
      SqlDialect dialect = SqlDialect.getDefault();
      String endDate = condition.getSearchMinIssueStartDatetimeTo();
      if(!StringUtil.isNullOrEmpty(endDate)){
    	  endDate =condition.getSearchMinIssueStartDatetimeTo().substring(0,17) + "59";
      }
      SqlFragment fragment = dialect.createRangeClause("GCIH.ISSUE_DATE", DateUtil.fromString(condition.getSearchMinIssueStartDatetimeFrom()
          , true), DateUtil.fromString(endDate, true));
      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    
    //排序
    builder.append(" GROUP BY GCID.CARD_CODE, GCID.CARD_NAME ,GCID.ISSUE_DATE ,GCID.CARD_HISTORY_NO ");
    builder.append(" ORDER BY CARD_CODE");
    
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  public Class<GiftCardDetailListSummary> getRowType() {
    return GiftCardDetailListSummary.class;
  }

}
