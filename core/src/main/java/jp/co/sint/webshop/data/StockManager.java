package jp.co.sint.webshop.data;

import java.util.List;

/**
 * 在庫管理用の処理インタフェースです。
 * 
 * @author System Integrator Corp.
 */
public interface StockManager {

  /**
   * 在庫を引き当てます。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean allocate(StockUnit stockUnit);

  /**
   * 在庫を引き当てます。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean allocate(List<StockUnit> stockUnitList);

  /**
   * 在庫を引き当てます(管理者調整)。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean liquidatedAllocate(StockUnit stockUnit);

  /**
   * 在庫を引き当てます(管理者調整)。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean liquidatedAllocate(List<StockUnit> stockUnitList);

  /**
   * 在庫引き当てを取り消します。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean deallocate(StockUnit stockUnit);

  /**
   * 在庫引き当てを取り消します。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean deallocate(List<StockUnit> stockUnitList);

  /**
   * 在庫を引き当てます。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean entry(StockUnit stockUnit);

  /**
   * 在庫を引き当てます。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean entry(List<StockUnit> stockUnitList);

  /**
   * 引き当てた在庫を出庫します。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean deliver(StockUnit stockUnit);

  /**
   * 引き当てた在庫を出庫します。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean deliver(List<StockUnit> stockUnitList);

  /**
   * 在庫を予約モードで引き当てます。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean reserving(StockUnit stockUnit);

  /**
   * 在庫を予約モードで引き当てます。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean reserving(List<StockUnit> stockUnitList);

  /**
   * 在庫を予約モードで引き当てます(管理者調整)。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean liquidatedReserving(StockUnit stockUnit);

  /**
   * 在庫を予約モードで引き当てます(管理者調整)。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean liquidatedReserving(List<StockUnit> stockUnitList);

  /**
   * 予約モードの在庫引き当てを取り消します。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean cancelReserving(StockUnit stockUnit);

  /**
   * 予約モードの在庫引き当てを取り消します。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean cancelReserving(List<StockUnit> stockUnitList);

  /**
   * 絶対数量で在庫数を変更します。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean absolute(StockUnit stockUnit);

  /**
   * 絶対数量で在庫数を変更します。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean absolute(List<StockUnit> stockUnitList);

  /**
   * 予約数を減らし引当数を増やします。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean shift(StockUnit stockUnit);

  /**
   * 予約数を減らし引当数を増やします。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean shift(List<StockUnit> stockUnitList);

  /**
   * 在庫数と引当数を減らします。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean shipping(StockUnit stockUnit);

  /**
   * 在庫数と引当数を減らします。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean shipping(List<StockUnit> stockUnitList);

  /**
   * 在庫数と引当数を増やします。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean cancelShipping(StockUnit stockUnit);

  /**
   * 在庫数と引当数を増やします。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 成功すればtrue、失敗すればfalse
   */
  boolean cancelShipping(List<StockUnit> stockUnitList);

  /**
   * 引当、または予約可能かを判定します。
   * 
   * @param stockUnit
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 引当または予約可能であればtrue、不可であればfalse
   */
  boolean hasStock(StockUnit stockUnit);

  /**
   * 引当、または予約可能かを判定します。
   * 
   * @param stockUnitList
   *          在庫取り扱い単位を示すStockUnitオブジェクトのリスト
   * @return 引当または予約可能であればtrue、不可であればfalse
   */
  boolean hasStock(List<StockUnit> stockUnitList);
}
