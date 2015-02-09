package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.domain.UsingFlg;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.JdStockInfo;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StockQuantityUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteJdstockBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteJdstockBean.CommodityConstituteJdstockBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

public abstract class CommodityConstituteJdstockBaseAction extends WebBackAction<CommodityConstituteJdstockBean> {

  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    return Permission.COMMODITY_CONSTITUTE_READ.isGranted(login);
  }

  public abstract WebActionResult callService();

  public boolean validate() {
    return true;
  }

  public void addDataToBean(CommodityConstituteJdstockBean bean, JdStockInfo tsi, List<JdStockInfo> list) {
    Long combStockTotal = 0L;
    Long combScaleTotal = 0L;
    Long combAllocatedTotal = 0L;
    Long combSaleTotal = 0L;
    
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    // 组合品库存
    Long stockZuhe = StockQuantityUtil.getOriginalJdStockQuantity(tsi.getCommodityCode());
    // 已经分配的套装商品数量
    Long allUseQuantity = catalogService.getUseSuitStockJd(tsi.getCommodityCode());
    
    bean.setOrgCommodityCode(tsi.getCommodityCode());
    bean.setOrgCommodityName(tsi.getCommodityName());
    bean.setOrgStockTotal(NumUtil.toString(tsi.getStockQuantity()));
    bean.setOrgAllocatedTotal(NumUtil.toString(tsi.getAllocatedQuantity()));
    bean.setOrgSaleQuantity(NumUtil.toString(tsi.getStockQuantity() - tsi.getAllocatedQuantity() - allUseQuantity ));
    bean.setSuitQuantity(allUseQuantity.toString());
    for (JdStockInfo info : list) {
      // 组合品库存合计
      combStockTotal = combStockTotal + info.getStockQuantity();
      combScaleTotal = combScaleTotal + info.getScaleValue();
      // 组合品引当合计
      combAllocatedTotal = combAllocatedTotal + info.getAllocatedQuantity();
      
      combSaleTotal = combSaleTotal + (info.getStockQuantity() - info.getAllocatedQuantity());
    }

    CommodityConstituteJdstockBeanDetail deatil = new CommodityConstituteJdstockBeanDetail();
    deatil.setCommodityCode(tsi.getCommodityCode());
    deatil.setCommodityName(tsi.getCommodityName());
    deatil.setCombinationAmount("1");
    if (NumUtil.isNull(tsi.getJdUseFlg())) {
      deatil.setJdUseFlg(UsingFlg.HIDDEN.getName());
    } else {
      deatil.setJdUseFlg(UsingFlg.fromValue(tsi.getJdUseFlg()).getName());
    }
    
    deatil.setJdStock(NumUtil.toString(tsi.getStockQuantity() - combStockTotal));
    deatil.setBeforeJdStock(NumUtil.toString(tsi.getStockQuantity() - combStockTotal));
    deatil.setJdAllocated(NumUtil.toString(tsi.getAllocatedQuantity() - combAllocatedTotal));
    // 天猫在库总数-天猫引当总数 + 天猫组合品引当数 - 套餐有效库存 - 组合品数
    deatil.setJdSale(NumUtil.toString(tsi.getStockQuantity() - tsi.getAllocatedQuantity() + combAllocatedTotal - allUseQuantity - stockZuhe));
    deatil.setScale(NumUtil.toString(100 - combScaleTotal));

    bean.getList().add(deatil);

    for (JdStockInfo info : list) {
      CommodityConstituteJdstockBeanDetail dtl = new CommodityConstituteJdstockBeanDetail();
      dtl.setCommodityCode(info.getCommodityCode());
      dtl.setCommodityName(info.getCommodityName());
      dtl.setCombinationAmount(NumUtil.toString(info.getCombinationAmount()));
      if (NumUtil.isNull(info.getJdUseFlg())) {
        dtl.setJdUseFlg(UsingFlg.HIDDEN.getName());
      } else {
        dtl.setJdUseFlg(UsingFlg.fromValue(info.getJdUseFlg()).getName());
      }
      
      dtl.setJdStock(NumUtil.toString(info.getStockQuantity()));
      dtl.setBeforeJdStock(NumUtil.toString(info.getStockQuantity()));
      dtl.setJdAllocated(NumUtil.toString(info.getAllocatedQuantity()));
      dtl.setJdSale(NumUtil.toString(info.getStockQuantity() - info.getAllocatedQuantity()));
      dtl.setScale(NumUtil.toString(info.getScaleValue()));
      dtl.setGroupAmount(NumUtil.toString(info.getStockQuantity() / info.getCombinationAmount()));

      bean.getList().add(dtl);
    }
  }

}
