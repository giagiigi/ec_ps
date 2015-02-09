package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.domain.UsingFlg;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.TmallStockInfo;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StockQuantityUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteTmallstockBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteTmallstockBean.CommodityConstituteTmallstockBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

public abstract class CommodityConstituteTmallstockBaseAction extends WebBackAction<CommodityConstituteTmallstockBean> {

  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    return Permission.COMMODITY_CONSTITUTE_READ.isGranted(login);
  }

  public abstract WebActionResult callService();

  public boolean validate() {
    return true;
  }

  public void addDataToBean(CommodityConstituteTmallstockBean bean, TmallStockInfo tsi, List<TmallStockInfo> list) {
    Long combStockTotal = 0L;
    Long combScaleTotal = 0L;
    Long combAllocatedTotal = 0L;
    Long combSaleTotal = 0L;
    
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
//    // 组合品库存
    Long stockZuhe = StockQuantityUtil.getOriginalTmallStockQUANTITY(tsi.getCommodityCode());
    // 已经分配的套装商品数量
    Long allUseQuantity = catalogService.getUseSuitStock(tsi.getCommodityCode());
    
    bean.setOrgCommodityCode(tsi.getCommodityCode());
    bean.setOrgCommodityName(tsi.getCommodityName());
    bean.setOrgStockTotal(NumUtil.toString(tsi.getStockQuantity()));
    bean.setOrgAllocatedTotal(NumUtil.toString(tsi.getAllocatedQuantity()));
    bean.setOrgSaleQuantity(NumUtil.toString(tsi.getStockQuantity() - tsi.getAllocatedQuantity() - allUseQuantity ));
    bean.setSuitQuantity(allUseQuantity.toString());
    for (TmallStockInfo info : list) {
      // 组合品库存合计
      combStockTotal = combStockTotal + info.getStockQuantity();
      combScaleTotal = combScaleTotal + info.getScaleValue();
      // 组合品引当合计
      combAllocatedTotal = combAllocatedTotal + info.getAllocatedQuantity();
      
      combSaleTotal = combSaleTotal + (info.getStockQuantity() - info.getAllocatedQuantity());
    }

    CommodityConstituteTmallstockBeanDetail deatil = new CommodityConstituteTmallstockBeanDetail();
    deatil.setCommodityCode(tsi.getCommodityCode());
    deatil.setCommodityName(tsi.getCommodityName());
    deatil.setCombinationAmount("1");
    deatil.setTmallUseFlg(UsingFlg.fromValue(tsi.getTmallUseFlg()).getName());
    deatil.setTmallStock(NumUtil.toString(tsi.getStockQuantity() - combStockTotal));
    deatil.setBeforeTmallStock(NumUtil.toString(tsi.getStockQuantity() - combStockTotal));
    deatil.setTmallAllocated(NumUtil.toString(tsi.getAllocatedQuantity() - combAllocatedTotal));
    deatil.setTmallSale(NumUtil.toString(tsi.getStockQuantity() - tsi.getAllocatedQuantity() + combAllocatedTotal - allUseQuantity - stockZuhe));
    deatil.setScale(NumUtil.toString(100 - combScaleTotal));

    bean.getList().add(deatil);

    for (TmallStockInfo info : list) {
      CommodityConstituteTmallstockBeanDetail dtl = new CommodityConstituteTmallstockBeanDetail();
      dtl.setCommodityCode(info.getCommodityCode());
      dtl.setCommodityName(info.getCommodityName());
      dtl.setCombinationAmount(NumUtil.toString(info.getCombinationAmount()));
      dtl.setTmallUseFlg(UsingFlg.fromValue(info.getTmallUseFlg()).getName());
      dtl.setTmallStock(NumUtil.toString(info.getStockQuantity()));
      dtl.setBeforeTmallStock(NumUtil.toString(info.getStockQuantity()));
      dtl.setTmallAllocated(NumUtil.toString(info.getAllocatedQuantity()));
      dtl.setTmallSale(NumUtil.toString(info.getStockQuantity() - info.getAllocatedQuantity()));
      dtl.setScale(NumUtil.toString(info.getScaleValue()));
      dtl.setGroupAmount(NumUtil.toString(info.getStockQuantity() / info.getCombinationAmount()));

      bean.getList().add(dtl);
    }
  }

}
