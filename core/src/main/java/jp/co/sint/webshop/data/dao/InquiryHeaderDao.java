//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.InquiryHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「咨询Header(INQUIRY_HEADER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author swj.
 *
 */
public interface InquiryHeaderDao extends GenericDao<InquiryHeader, Long> {

  /**
   * 指定されたorm_rowidを持つ咨询Headerのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するInquiryHeaderのインスタンス
   */
  InquiryHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定して咨询Headerのインスタンスを取得します。
   *
   * @param inquiryHeaderNo 咨询编号
   * @return 主キー列の値に対応するInquiryHeaderのインスタンス
   */
  InquiryHeader load(String inquiryHeaderNo);

  /**
   * 主キー列の値を指定して咨询Headerが既に存在するかどうかを返します。
   *
   * @param inquiryHeaderNo 咨询编号
   * @return 主キー列の値に対応するInquiryHeaderの行が存在すればtrue
   */
  boolean exists(String inquiryHeaderNo);

  /**
   * 新規InquiryHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のInquiryHeader
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(InquiryHeader obj, LoginInfo loginInfo);

  /**
   * 咨询Headerを更新します。
   *
   * @param obj 更新対象のInquiryHeader
   * @param loginInfo ログイン情報
   */
  void update(InquiryHeader obj, LoginInfo loginInfo);

  /**
   * 咨询Headerを削除します。
   *
   * @param obj 削除対象のInquiryHeader
   * @param loginInfo ログイン情報
   */
  void delete(InquiryHeader obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して咨询Headerを削除します。
   *
   * @param inquiryHeaderNo 咨询编号
   */
  void delete(String inquiryHeaderNo);

  /**
   * Queryオブジェクトを指定して咨询Headerのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するInquiryHeaderのリスト
   */
  List<InquiryHeader> findByQuery(Query query);

  /**
   * SQLを指定して咨询Headerのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するInquiryHeaderのリスト
   */
  List<InquiryHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のInquiryHeaderのリスト
   */
  List<InquiryHeader> loadAll();

}
