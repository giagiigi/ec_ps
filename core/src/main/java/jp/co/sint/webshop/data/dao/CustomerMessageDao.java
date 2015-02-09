//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.dto.CustomerMessage;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 「TMALL库存比率分配(TmallStockAllocation)」テーブルへのデータアクセスを担当するDAO(Data Access
 * Object)です。
 * 
 * @author System Integrator Corp.
 */
public interface CustomerMessageDao extends GenericDao<CustomerMessage, Long> {

  /**
   * 指定されたorm_rowidを持つギフトのインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するTmallStockAllocationのインスタンス
   */
  CustomerMessage loadByRowid(Long id);

  /**
   * 新規TmallStockAllocationをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のTmallStockAllocation
   * @param loginInfo
   *          ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CustomerMessage obj, LoginInfo loginInfo);

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のTmallStockAllocation
   * @param loginInfo
   *          ログイン情報
   */
  void update(CustomerMessage obj, LoginInfo loginInfo);

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のTmallStockAllocation
   * @param loginInfo
   *          ログイン情報
   */
  void delete(CustomerMessage obj, LoginInfo loginInfo);

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のTmallStockAllocationのリスト
   */
  List<CustomerMessage> loadAll();

}
