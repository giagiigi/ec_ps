package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.domain.CsvFlg;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;

import org.apache.log4j.Logger;

public class GiftCardRuleExportDataSource extends SqlExportDataSource<GiftCardRuleCsvSchema, GiftCardRuleExportCondition> {

  private PreparedStatement updateStatement = null;

  private Logger logger = Logger.getLogger(this.getClass());

  String updateSql = "UPDATE gift_card_issue_history SET csv_flg = ?,UPDATED_USER = ?, UPDATED_DATETIME = ?  WHERE card_code = ? AND card_history_no =?";

  public Query getExportQuery() {
    String cardCode = getCondition().getSearchCondition().getSearchCardCode();
    String cardHistoryNo = getCondition().getSearchCondition().getCardHistoryNo();
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT ");
    sql.append("R.card_code, ");
    sql.append("R.card_name, ");
    sql.append("R.effective_years, ");
    sql.append("R.weight, ");
    sql.append("R.unit_price, ");
    sql.append("R.denomination, ");
    sql.append("D.card_id, ");
    sql.append("D.pass_word, ");
    sql.append("H.issue_date ");
    sql.append("FROM gift_card_rule R ");
    sql.append("INNER JOIN gift_card_issue_history H ON R.card_code = H.card_code ");
    sql.append("RIGHT JOIN gift_card_issue_detail D ON H.card_code = D.card_code ");

    List<Object> params = new ArrayList<Object>();

    sql.append("WHERE R.card_code = ? AND H.card_history_no = ? AND D.card_history_no = ?");
    params.add(cardCode);
    params.add(NumUtil.toLong(cardHistoryNo));
    params.add(NumUtil.toLong(cardHistoryNo));

    Query q = null;
    q = new SimpleQuery(sql.toString(), params.toArray());

    return q;
  }

  /*
   * 导出成功后更新导出标志
   */
  public void afterExport() {

    logger.debug("UPDATE gift_card_issue_history statement: " + updateSql);
    try {
      updateStatement = createPreparedStatement(updateSql);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }

    List<Object> updateShippingParams = new ArrayList<Object>();
    updateShippingParams.add(CsvFlg.EXPORT.longValue());
    updateShippingParams.add("BACK:0:" + DIContainer.getWebshopConfig().getSiteShopCode() + ":"
        + getCondition().getSearchCondition().getUpdateUser());
    updateShippingParams.add(DateUtil.getSysdate());
    updateShippingParams.add(getCondition().getSearchCondition().getSearchCardCode());
    updateShippingParams.add(NumUtil.toLong(getCondition().getSearchCondition().getCardHistoryNo()));

    logger.debug("Table:gift_card_issue_history UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));

    try {
      DatabaseUtil.bindParameters(updateStatement, ArrayUtil.toArray(updateShippingParams, Object.class));
      updateStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
