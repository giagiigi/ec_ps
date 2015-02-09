package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CommodityPriceChangeHistory;
import jp.co.sint.webshop.service.analysis.PostgresDatabaseAnalysisQuery;
import jp.co.sint.webshop.utility.StringUtil;


public class InsertCommodityPriceChangeHistoryQuery extends PostgresDatabaseAnalysisQuery{
  
  private static final long serialVersionUID = 1L;
  
  StringBuilder builder = new StringBuilder();
  List<Object> params = new ArrayList<Object>();
  
  // 插入操作
  public InsertCommodityPriceChangeHistoryQuery(CommodityPriceChangeHistoryCondition commondityPriceChangeHistory) {

      builder.append(INSERT_SQL);
      
      if (StringUtil.hasValue(commondityPriceChangeHistory.getCommodityCode())) {
        params.add(commondityPriceChangeHistory.getCommodityCode());
      } 
      if (StringUtil.hasValue(commondityPriceChangeHistory.getSubmitTime())) {
        params.add(commondityPriceChangeHistory.getSubmitTime());
      } 
      if (StringUtil.hasValue(commondityPriceChangeHistory.getResponsiblePerson())) {
        params.add(commondityPriceChangeHistory.getResponsiblePerson());
      } 
      if (StringUtil.hasValue(commondityPriceChangeHistory.getOldOfficialPrice())) {
        params.add(commondityPriceChangeHistory.getOldOfficialPrice());
      } else {
        params.add(null);
      }
      // 如果更新动作更新了OfficialPrice，那么newOfficialPrice为更新后的值
      // 如果没有更新，那么newOfficialPrice不变，还是为oldOfficialPrice
      if (StringUtil.hasValue(commondityPriceChangeHistory.getNewOfficialPrice())) {
        params.add(commondityPriceChangeHistory.getNewOfficialPrice());
      } else if(StringUtil.hasValue(commondityPriceChangeHistory.getOldOfficialPrice())) {
        params.add(commondityPriceChangeHistory.getOldOfficialPrice());
      } else {
        params.add(null);
      }
      if (StringUtil.hasValue(commondityPriceChangeHistory.getOldOfficialSpecialPrice())) {
        params.add(commondityPriceChangeHistory.getOldOfficialSpecialPrice());
      } else {
        params.add(null);
      }
      // 如果更新动作更新了OfficialSpecialPrice，那么NewOfficialSpecialPrice为更新后的值
      // 如果没有更新，那么NewOfficialSpecialPrice不变，还是为OldOfficialSpecialPrice
      if (StringUtil.hasValue(commondityPriceChangeHistory.getNewOfficialSpecialPrice())) {
        params.add(commondityPriceChangeHistory.getNewOfficialSpecialPrice());
      } else if(StringUtil.hasValue(commondityPriceChangeHistory.getOldOfficialSpecialPrice())) {
        params.add(commondityPriceChangeHistory.getOldOfficialSpecialPrice());
      } else {
        params.add(null);
      }
      if (StringUtil.hasValue(commondityPriceChangeHistory.getOldTmallPrice())) {
        params.add(commondityPriceChangeHistory.getOldTmallPrice());
      } else {
        params.add(null);
      }
      // 如果更新动作更新了TmallPrice，那么NewTmallPrice为更新后的值
      // 如果没有更新，那么NewTmallPrice不变，还是为OldTmallPrice
      if (StringUtil.hasValue(commondityPriceChangeHistory.getNewTmallPrice())) {
        params.add(commondityPriceChangeHistory.getNewTmallPrice());
      } else if (StringUtil.hasValue(commondityPriceChangeHistory.getOldTmallPrice())) {
        params.add(commondityPriceChangeHistory.getOldTmallPrice());
      } else {
        params.add(null);
      }
      if (StringUtil.hasValue(commondityPriceChangeHistory.getOldJdPrice())) {
        params.add(commondityPriceChangeHistory.getOldJdPrice());
      } else {
        params.add(null);
      }
      // 如果更新动作更新了JdPrice，那么NewJdPrice为更新后的值
      // 如果没有更新，那么NewJdPrice不变，还是为OldJdPrice
      if (StringUtil.hasValue(commondityPriceChangeHistory.getNewJdPrice())) {
        params.add(commondityPriceChangeHistory.getNewJdPrice());
      } else if(StringUtil.hasValue(commondityPriceChangeHistory.getOldJdPrice())){
        params.add(commondityPriceChangeHistory.getOldJdPrice());
      } else {
        params.add(null);
      }
      if (StringUtil.hasValue(commondityPriceChangeHistory.getOrmRowid())) {
        params.add(commondityPriceChangeHistory.getOrmRowid());
      }
      if (StringUtil.hasValue(commondityPriceChangeHistory.getCreatedUser())) {
        params.add(commondityPriceChangeHistory.getCreatedUser());
      }
      if (StringUtil.hasValue(commondityPriceChangeHistory.getCreatedDatetime())) {
        params.add(commondityPriceChangeHistory.getCreatedDatetime());
      }
      if (StringUtil.hasValue(commondityPriceChangeHistory.getUpdatedUser())) {
        params.add(commondityPriceChangeHistory.getUpdatedUser());
      }
      if (StringUtil.hasValue(commondityPriceChangeHistory.getUpdatedDatetime())) {
        params.add(commondityPriceChangeHistory.getUpdatedDatetime());
      }
      if (StringUtil.hasValue(commondityPriceChangeHistory.getReviewOrNotFlg())) {
        params.add(commondityPriceChangeHistory.getReviewOrNotFlg());
      } else {
        params.add(null);
      }
      setSqlString(builder.toString());
      setParameters(params.toArray());
  }
  
  public Object[][] getUpdateParameters() {
    Object[] p = params.toArray();
    Object[][] p2 = {p};
    return p2;
  }
  
  private static final String INSERT_SQL = "INSERT INTO COMMODITY_PRICE_CHANGE_HISTORY (commodity_code, submit_time, responsible_person, old_official_price, new_official_price, old_official_special_price, new_official_special_price, old_tmall_price, new_tmall_price, old_jd_price, new_jd_price, orm_rowid, created_user, created_datatime, updated_user, updated_datetime, review_or_not_flg) " 
    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  public Class<CommodityPriceChangeHistory> getRowType() {
    return CommodityPriceChangeHistory.class;
  }
  
}
