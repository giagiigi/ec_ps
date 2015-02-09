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
import jp.co.sint.webshop.data.dto.InquiryDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「咨询详细(INQUIRY_DETAIL)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author swj.
 *
 */
public interface InquiryDetailDao extends GenericDao<InquiryDetail, Long> {

  /**
   * 指定されたorm_rowidを持つ咨询详细のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するInquiryDetailのインスタンス
   */
  InquiryDetail loadByRowid(Long id);

  /**
   * 主キー列の値を指定して咨询详细のインスタンスを取得します。
   * 
   * @param inquiryHeaderNo 咨询编号
   * @param inquiryDetailNo 咨询明细编号
   * @return 主キー列の値に対応するInquiryDetailのインスタンス
   */
  InquiryDetail load(String inquiryHeaderNo, String inquiryDetailNo);

  /**
   * 主キー列の値を指定して咨询详细が既に存在するかどうかを返します。
   *
   * @param inquiryHeaderNo 咨询编号
   * @param inquiryDetailNo 咨询明细编号
   * @return 主キー列の値に対応するInquiryDetailの行が存在すればtrue
   */
  boolean exists(String inquiryHeaderNo, String inquiryDetailNo);

  /**
   * 新規InquiryDetailをデータベースに追加します。
   *
   * @param obj 追加対象のInquiryDetail
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(InquiryDetail obj, LoginInfo loginInfo);

  /**
   * 咨询详细を更新します。
   *
   * @param obj 更新対象のInquiryDetail
   * @param loginInfo ログイン情報
   */
  void update(InquiryDetail obj, LoginInfo loginInfo);

  /**
   * 咨询详细を削除します。
   *
   * @param obj 削除対象のInquiryDetail
   * @param loginInfo ログイン情報
   */
  void delete(InquiryDetail obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して咨询详细を削除します。
   *
   * @param inquiryHeaderNo 咨询编号
   * @param inquiryDetailNo 咨询明细编号
   */
  void delete(String inquiryHeaderNo, String inquiryDetailNo);

  /**
   * Queryオブジェクトを指定して咨询详细のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するInquiryDetailのリスト
   */
  List<InquiryDetail> findByQuery(Query query);

  /**
   * SQLを指定して咨询详细のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するInquiryDetailのリスト
   */
  List<InquiryDetail> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のInquiryDetailのリスト
   */
  List<InquiryDetail> loadAll();

}
