package jp.co.sint.webshop.data.hibernate;

import java.sql.Connection;
import java.util.List;

import jp.co.sint.webshop.data.StockManager;
import jp.co.sint.webshop.data.StockUnit;
import jp.co.sint.webshop.data.StockUtil;

public class StockManagerImpl implements StockManager {

  private Connection connection;

  public StockManagerImpl() {
  }

  public StockManagerImpl(Connection connection) {
    setConnection(connection);
  }

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }

  public boolean absolute(StockUnit stockUnit) {
    return StockUtil.absolute(stockUnit, getConnection());
  }

  public boolean absolute(List<StockUnit> stockUnitList) {
    return StockUtil.absolute(stockUnitList, getConnection());
  }

  public boolean allocate(StockUnit stockUnit) {
    return StockUtil.allocate(stockUnit, getConnection());
  }

  public boolean allocate(List<StockUnit> stockUnitList) {
    return StockUtil.allocate(stockUnitList, getConnection());
  }

  public boolean liquidatedAllocate(StockUnit stockUnit) {
    return StockUtil.liquidatedAllocate(stockUnit, getConnection());
  }

  public boolean liquidatedAllocate(List<StockUnit> stockUnitList) {
    return StockUtil.liquidatedAllocate(stockUnitList, getConnection());
  }

  public boolean cancelReserving(StockUnit stockUnit) {
    return StockUtil.cancelReserve(stockUnit, getConnection());
  }

  public boolean cancelReserving(List<StockUnit> stockUnitList) {
    return StockUtil.cancelReserve(stockUnitList, getConnection());
  }

  public boolean deallocate(StockUnit stockUnit) {
    return StockUtil.deallocate(stockUnit, getConnection());
  }

  public boolean deallocate(List<StockUnit> stockUnitList) {
    return StockUtil.deallocate(stockUnitList, getConnection());
  }

  public boolean deliver(StockUnit stockUnit) {
    return StockUtil.deliver(stockUnit, getConnection());
  }

  public boolean deliver(List<StockUnit> stockUnitList) {
    return StockUtil.deliver(stockUnitList, getConnection());
  }

  public boolean entry(StockUnit stockUnit) {
    return StockUtil.entry(stockUnit, getConnection());
  }

  public boolean entry(List<StockUnit> stockUnitList) {
    return StockUtil.entry(stockUnitList, getConnection());
  }

  public boolean reserving(StockUnit stockUnit) {
    return StockUtil.reserve(stockUnit, getConnection());
  }

  public boolean reserving(List<StockUnit> stockUnitList) {
    return StockUtil.reserve(stockUnitList, getConnection());
  }

  public boolean liquidatedReserving(StockUnit stockUnit) {
    return StockUtil.liquidatedReserve(stockUnit, getConnection());
  }

  public boolean liquidatedReserving(List<StockUnit> stockUnitList) {
    return StockUtil.liquidatedReserve(stockUnitList, getConnection());
  }

  public boolean shift(StockUnit stockUnit) {
    return StockUtil.changeToOrder(stockUnit, getConnection());
  }

  public boolean shift(List<StockUnit> stockUnitList) {
    return StockUtil.changeToOrder(stockUnitList, getConnection());
  }

  public boolean shipping(StockUnit stockUnit) {
    return StockUtil.shipping(stockUnit, getConnection());
  }

  public boolean shipping(List<StockUnit> stockUnitList) {
    return StockUtil.shipping(stockUnitList, getConnection());
  }

  public boolean cancelShipping(StockUnit stockUnit) {
    return StockUtil.cancelShipping(stockUnit, getConnection());
  }

  public boolean cancelShipping(List<StockUnit> stockUnitList) {
    return StockUtil.cancelShipping(stockUnitList, getConnection());
  }

  public boolean hasStock(StockUnit stockUnit) {
    return StockUtil.hasStock(stockUnit, getConnection());
  }

  public boolean hasStock(List<StockUnit> stockUnitList) {
    return StockUtil.hasStock(stockUnitList, getConnection());
  }

}
