package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.JdCommodityProperty;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 「JD商品属性关联表(jd_Commodity_Property)」テーブルへのデータアクセスを担当するDAO(Data Access
 * Object)です。
 * 
 * @author OB
 * 
 */
public interface JdCommodityPropertyDao extends GenericDao<JdCommodityProperty, Long> {

  /**
   * 指定されたorm_rowidを持つJD商品属性关联表のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するJdCommodityPropertyのインスタンス
   */
  JdCommodityProperty loadByRowid(Long id);

  /**
   * 主キー列の値を指定してJD商品属性关联表のインスタンスを取得します。
   * 
   * @param commodityCode 商品编号
   * @param propertyId 属性ID
   * @param valueId 属性值ID
   * @return 主キー列の値に対応するJdCommodityPropertyのインスタンス
   */
  JdCommodityProperty load(String commodityCode, String propertyId, String valueId);

  /**
   * 主キー列の値を指定してJD商品属性关联表が既に存在するかどうかを返します。
   * 
   * @param commodityCode 商品编号
   * @param propertyId 属性ID
   * @param valueId 属性值ID
   * @return 主キー列の値に対応するJdCommodityPropertyの行が存在すればtrue
   */
  boolean exists(String commodityCode, String propertyId, String valueId);

  /**
   * 新規OrderHeaderをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のJdCommodityProperty
   * @param loginInfo
   *          ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(JdCommodityProperty obj, LoginInfo loginInfo);

  /**
   * JD商品属性关联表を更新します。
   * 
   * @param obj
   *          更新対象のJdCommodityProperty
   * @param loginInfo
   *          ログイン情報
   */
  void update(JdCommodityProperty obj, LoginInfo loginInfo);

  /**
   * JD商品属性关联表を削除します。
   * 
   * @param obj
   *          削除対象のJdCommodityProperty
   * @param loginInfo
   *          ログイン情報
   */
  void delete(JdCommodityProperty obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してJD商品属性关联表を削除します。
   * 
   * @param orderNo
   *          受注番号
   */
  void delete(String commodityCode, String propertyId, String valueId);

  /**
   * Queryオブジェクトを指定してJD商品属性关联表のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するJdCommodityPropertyのリスト
   */
  List<JdCommodityProperty> findByQuery(Query query);

  /**
   * SQLを指定してJD商品属性关联表のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するJdCommodityPropertyのリスト
   */
  List<JdCommodityProperty> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のJdCommodityPropertyのリスト
   */
  List<JdCommodityProperty> loadAll();

  /**
   * 指定して商品编号のJdCommodityPropertyリストを取得します。
   * @param commodityCode 商品编号
   * @return テーブルの全データ分のJdCommodityPropertyのリスト
   */
  List<JdCommodityProperty> loadByCommodityCode(String commodityCode);

  /**
   * 查询商品自定义的属性
   * @param commodityCode 商品编号
   */
  List<JdCommodityProperty> loadInputProByCommodityCode(String commodityCode);

}
