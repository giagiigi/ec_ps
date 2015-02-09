package jp.co.sint.webshop.data;

import java.util.Date;

/**
 * SI Web Shoppingデータベースエンティティの既定インタフェースです。
 * 
 * @author System Integrator Corp.
 */
public interface WebshopEntity {
  
  /**
   * データ行IDを取得します
   *
   * @return データ行ID
   */
  Long getOrmRowid();

  /**
   * 作成ユーザを取得します
   *
   * @return 作成ユーザ
   */
  String getCreatedUser();

  /**
   * 作成日時を取得します
   *
   * @return 作成日時
   */
  Date getCreatedDatetime();

  /**
   * 更新ユーザを取得します
   *
   * @return 更新ユーザ
   */
  String getUpdatedUser();

  /**
   * 更新日時を取得します
   *
   * @return 更新日時
   */
  Date getUpdatedDatetime();

  /**
   * データ行IDを設定します
   *
   * @param  val データ行ID
   */
  void setOrmRowid(Long val);

  /**
   * 作成ユーザを設定します
   *
   * @param  val 作成ユーザ
   */
  void setCreatedUser(String val);

  /**
   * 作成日時を設定します
   *
   * @param  val 作成日時
   */
  void setCreatedDatetime(Date val);

  /**
   * 更新ユーザを設定します
   *
   * @param  val 更新ユーザ
   */
  void setUpdatedUser(String val);

  /**
   * 更新日時を設定します
   *
   * @param  val 更新日時
   */
  void setUpdatedDatetime(Date val);

}
