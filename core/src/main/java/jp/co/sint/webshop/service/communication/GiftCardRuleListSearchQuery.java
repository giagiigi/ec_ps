package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.GiftCardRule;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;


public class GiftCardRuleListSearchQuery extends AbstractQuery<GiftCardRule> {

  private static final long serialVersionUID = 1L;

  private static final String base_sql 
        = "select "
        + "g.card_code, "
        + "g.card_name, "
        + "g.effective_years, "
        + "g.weight, "
        + "g.unit_price, "
        + "g.denomination "
        + "from gift_card_rule g";
  private static final String base_sql2
        =" order by g.card_code ";
  
  public GiftCardRuleListSearchQuery(){
    
  }
  public GiftCardRuleListSearchQuery(GiftCardRuleListSearchCondition gift){
    StringBuilder builder = new StringBuilder(base_sql);
    builder.append(" where 1 = 1 ");
    List<Object> params = new ArrayList<Object>();
    
    //检索条件：礼品卡编号，部分匹配。
    if (StringUtil.hasValue(gift.getSearchCardCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" and ");
      SqlFragment fragment = dialect.createLikeClause("g.card_code", gift.getSearchCardCode(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    if (StringUtil.hasValue(gift.getSearchCardName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" and ");
      SqlFragment fragment = dialect.createLikeClause("g.card_name", gift.getSearchCardName(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    builder.append(base_sql2);
    setSqlString(builder.toString());
    setParameters(params.toArray());
    
    setPageNumber(gift.getCurrentPage());
    setPageSize(gift.getPageSize());
  }
  
  
  @Override
  public Class<GiftCardRule> getRowType() {
    // TODO Auto-generated method stub
    return GiftCardRule.class;
  }

}
