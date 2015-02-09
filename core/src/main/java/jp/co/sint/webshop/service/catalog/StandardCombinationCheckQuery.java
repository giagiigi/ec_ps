package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;

public class StandardCombinationCheckQuery implements Query {

  private static final long serialVersionUID = 1L;

  private String sqlString;

  private Object[] parameters = new Object[0];

  /**
   * ショップコード、商品コードと規格数を指定して、既存SKUの規格組み合わせに重複がないかどうかを調べるクエリを生成します。
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param standardCount
   *          規格数(1または2)
   */
  public StandardCombinationCheckQuery(String shopCode, String commodityCode, int standardCount) {

    if (StringUtil.isNullOrEmptyAnyOf(shopCode, commodityCode)) {
      throw new IllegalArgumentException("Arguments(shopCode, commodityCode) must be required.");
    }
    if (!ValidatorUtil.inRange(standardCount, 1, 2)) {
      throw new IllegalArgumentException("standardCount must be 1 or 2");
    }
    StringBuilder b = new StringBuilder();
    b.append("SELECT CASE WHEN COUNT(*) = SUM(X.COMBINATION) THEN 0 ELSE 1 END RESULT ");
    b.append("FROM (SELECT COUNT(*) COMBINATION FROM COMMODITY_DETAIL CD ");
    b.append("WHERE CD.SHOP_CODE = ? AND CD.COMMODITY_CODE = ? ");
    b.append("GROUP BY CD.SHOP_CODE, CD.COMMODITY_CODE, CD.STANDARD_DETAIL1_NAME");
    if (standardCount == 2) {
      b.append(", CD.STANDARD_DETAIL2_NAME ");
    }
    b.append(") X ");
    this.sqlString = b.toString();

    List<Object> params = new ArrayList<Object>();
    params.add(shopCode);
    params.add(commodityCode);
    this.parameters = params.toArray();
  }

  /**
   * 更新予定のSKUに指定する規格名称が、登録済みの名称と重複がないかどうかを調べるクエリを生成します。
   * 生成されたクエリは単一の値を返します。値は重複がなければゼロ、重複があれば1です。
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param skuCode
   *          SKUコード
   * @param standardDetail1Name
   *          規格詳細1名称
   */
  public StandardCombinationCheckQuery(String shopCode, String commodityCode, String skuCode, String standardDetail1Name) {
    this(shopCode, commodityCode, skuCode, standardDetail1Name, null);
    if (StringUtil.isNullOrEmptyAnyOf(shopCode, commodityCode, skuCode, standardDetail1Name)) {
      throw new IllegalArgumentException("Arguments(shopCode, commodityCode, skuCode, standardDetail1Name) must be required.");
    }
    buildQuery(shopCode, commodityCode, skuCode, standardDetail1Name, null);
  }

  /**
   * 更新予定のSKUに指定する規格名称が、登録済みの名称組み合わせと重複がないかどうかを調べるクエリを生成します。
   * 生成されたクエリは単一の値を返します。値は重複がなければゼロ、重複があれば1です。
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param skuCode
   *          SKUコード
   * @param standardDetail1Name
   *          規格詳細1名称
   * @param standardDetail2Name
   *          規格詳細2名称
   */
  public StandardCombinationCheckQuery(String shopCode, String commodityCode, String skuCode, String standardDetail1Name,
      String standardDetail2Name) {

    if (StringUtil.isNullOrEmptyAnyOf(shopCode, commodityCode, skuCode, standardDetail1Name, standardDetail2Name)) {
      throw new IllegalArgumentException(
          "Arguments(shopCode, commodityCode, skuCode, standardDetail1Name, standardDetail2Name) must be required.");
    }
    buildQuery(shopCode, commodityCode, skuCode, standardDetail1Name, standardDetail2Name);
  }

  private void buildQuery(String shopCode, String commodityCode, String skuCode, String standardDetail1Name,
      String standardDetail2Name) {

    List<Object> params = new ArrayList<Object>();
    this.parameters = params.toArray();

    StringBuilder b = new StringBuilder();
    b.append("SELECT CASE WHEN COUNT(*) = 0 THEN 0 ELSE 1 END RESULT ");
    b.append("FROM COMMODITY_DETAIL CD　WHERE CD.SHOP_CODE = ? AND CD.COMMODITY_CODE = ? ");
    params.add(shopCode);
    params.add(commodityCode);

    b.append("AND CD.STANDARD_DETAIL1_NAME = ? ");
    params.add(standardDetail1Name);

    if (StringUtil.hasValue(standardDetail2Name)) {
      b.append("AND CD.STANDARD_DETAIL2_NAME = ? ");
      params.add(standardDetail2Name);
    }

    b.append("AND CD.SKU_CODE <> ? ");
    params.add(skuCode);

    this.sqlString = b.toString();
    this.parameters = params.toArray();

  }

  public String getSqlString() {
    return this.sqlString;
  }

  public Object[] getParameters() {
    return ArrayUtil.immutableCopy(this.parameters);
  }

}
