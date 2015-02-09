package jp.co.sint.webshop.service.catalog;


import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.CSynchistory;
import jp.co.sint.webshop.utility.StringUtil;

public class CommodityHistoryQuery extends AbstractQuery<CSynchistory> {

  private static final long serialVersionUID = 1L;

  public Class<CSynchistory> getRowType() {
    return CSynchistory.class;
  }

  public CommodityHistoryQuery(CommodityHistorySearchCondition condition) {
    this.setPageSize(condition.getPageSize());
    List<String> params = new ArrayList<String>();
    StringBuffer b = new StringBuffer("SELECT sync_code, " 
    		+ "total_count, success_count, failure_count, sync_user," 
    		+ "sync_starttime, sync_endtime, orm_rowid, created_user, created_datetime,"
    		+ "updated_user, updated_datetime FROM C_SYNCHISTORY");
    String endTime = condition.getSearchCynchroEndTime();
    String startTime = condition.getSearchCynchroStartTime();
    int theFirstParamFlag = 0;
    if(StringUtil.hasValue(startTime)){
      theFirstParamFlag = 1;
      b.append(" where to_char(sync_starttime,'yyyy/MM/dd hh24:mi:ss') >='"+startTime+"' ");
      params.add(startTime);
    }
    if(StringUtil.hasValue(endTime)){
      if(theFirstParamFlag==1){
        b.append(" and ");
      }else{
        b.append(" where ");
      }
      theFirstParamFlag = 1;
      b.append("  to_char(sync_starttime,'yyyy/MM/dd hh24:mi:ss') <='"+endTime+"' ");
      params.add(endTime);
    }
    
    b.append(" order by sync_starttime desc ");
//    this.setParameters(params.toArray());
    this.setSqlString(b.toString());
  }
}
