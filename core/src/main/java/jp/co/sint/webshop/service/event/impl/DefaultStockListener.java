package jp.co.sint.webshop.service.event.impl;

import jp.co.sint.webshop.data.domain.StockChangeType;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.StockService;
import jp.co.sint.webshop.service.event.StockEvent;
import jp.co.sint.webshop.service.event.StockListener;
import jp.co.sint.webshop.text.Messages;

import org.apache.log4j.Logger;

public class DefaultStockListener implements StockListener {

  private static final long serialVersionUID = 1L;

  public DefaultStockListener() {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultStockListener.0"));
  }

  @Override
  public void changeEcAllocatedQuantity(StockEvent event) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultStockListener.1"));
    String order = event.getOrderNo();
    StockService stockService = ServiceLocator.getStockService(ServiceLoginInfo.getInstance());
    stockService.setStockTempForAllocatedQuantity(order, StockChangeType.EC_ALLOCATED.longValue());
  }

  @Override
  public void changeTmAllocatedQuantity(StockEvent event){
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultStockListener.2"));
    String order = event.getOrderNo();
    StockService stockService = ServiceLocator.getStockService(ServiceLoginInfo.getInstance());
    stockService.setStockTempForAllocatedQuantity(order, StockChangeType.TM_ALLOCATED.longValue());
  }

  @Override
  public void changeJdAllocatedQuantity(StockEvent event) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultStockListener.3"));
    String order = event.getOrderNo();
    StockService stockService = ServiceLocator.getStockService(ServiceLoginInfo.getInstance());
    stockService.setStockTempForAllocatedQuantity(order, StockChangeType.JD_ALLOCATED.longValue());
  }

}
