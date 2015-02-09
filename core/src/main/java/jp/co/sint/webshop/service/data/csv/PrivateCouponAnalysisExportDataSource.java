package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.PrivateCouponSearchQuery;

public class PrivateCouponAnalysisExportDataSource extends SqlExportDataSource<PrivateCouponAnalysisCsvSchema, PrivateCouponListExportCondition> {

  
		  public Query getExportQuery() {

		    return new PrivateCouponSearchQuery(getCondition().getSearchCondition(),true,true);
		  }

		  
}
