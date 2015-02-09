package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.communication.CampaignQuery;

public class CampaignResearchExportDataSource extends
    SqlExportDataSource<CampaignResearchCsvSchema, CampaignResearchExportCondition> {

  @Override
  public Query getExportQuery() {
    List<Object> params = new ArrayList<Object>();
    params.add(getCondition().getShopCode());
    params.add(getCondition().getCampaignCode());

    return new SimpleQuery(CampaignQuery.getLoadCampaignResearchQuery(), params.toArray());
  }

}
