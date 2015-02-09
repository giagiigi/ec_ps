package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;

public class MailMagazineExportDataSource extends SqlExportDataSource<MailMagazineCsvSchema, MailMagazineExportCondition> {

  @Override
  public Query getExportQuery() {
    List<Object> params = new ArrayList<Object>();

    params.add(getCondition().getMailMagazineCode());

    return new SimpleQuery("SELECT MAIL_MAGAZINE_CODE, EMAIL FROM MAIL_MAGAZINE_SUBSCRIBER WHERE MAIL_MAGAZINE_CODE = ?", params
        .toArray());
  }

}
