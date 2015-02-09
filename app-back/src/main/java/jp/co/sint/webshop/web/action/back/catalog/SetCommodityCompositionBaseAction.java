package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.SetCommodityCompositionBean;
import jp.co.sint.webshop.web.bean.back.catalog.SetCommodityCompositionBean.CompositionDetail;

/**
 * 套餐商品明细のアクションの基底クラスです
 * 
 * @author KS.
 */
public abstract class SetCommodityCompositionBaseAction extends WebBackAction<SetCommodityCompositionBean> {

  /**
   * 套餐明细取得
   */
  public CompositionDetail createCommodityComposition(Long allUseQuantity ,String shopCode, String retailPrice, String tmallRetailPrice, String commodityCode) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo result = service.getCommodityInfo(shopCode, commodityCode);
    Long stockQuantity = service.getAvailableStock(result.getHeader().getShopCode(), result.getHeader().getRepresentSkuCode());
    if (result == null) {
      return null;
    }
    // 2014/06/17 库存更新对应 ob_李先超 delete start
//    Long stockTmall = 0L;
//    Long allocatedTmall = 0L;
//    if (result.getStock() != null) {
//      if (result.getStock().getStockTmall() != null) {
//        stockTmall = result.getStock().getStockTmall();
//      }
//      if (result.getStock().getAllocatedTmall() != null) {
//        allocatedTmall = result.getStock().getAllocatedTmall();
//      }
//    }
    // 2014/06/17 库存更新对应 ob_李先超 delete end

    CompositionDetail edit = new CompositionDetail();
    edit.setStockQuantity(NumUtil.toString(stockQuantity));
    if(StringUtil.isNullOrEmpty(edit.getStockQuantity())) {
      edit.setStockQuantity("0");
    }
    // 2014/06/17 库存更新对应 ob_李先超 update start
    //edit.setStockTmall(NumUtil.toString(stockTmall - allocatedTmall - allUseQuantity) );
    Long tmallStock = service.getTmallStock(commodityCode);
    edit.setStockTmall(NumUtil.toString(tmallStock));
    Long jdStock = service.getJdStock(commodityCode);
    edit.setJdTmall(NumUtil.toString(jdStock));
    // 2014/06/17 库存更新对应 ob_李先超 update end
    edit.setCommodityName(result.getHeader().getCommodityName());
    edit.setSaleStartDatetime(DateUtil.toDateTimeString(result.getHeader().getSaleStartDatetime()));
    edit.setSaleEndDatetime(DateUtil.toDateTimeString(result.getHeader().getSaleEndDatetime()));
    edit.setRetailPrice(retailPrice);
    edit.setShopCode(shopCode);
    edit.setCommodityCode(commodityCode);
    edit.setTmallRetailPrice(tmallRetailPrice);
    return edit;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    SetCommodityCompositionBean reqBean = (SetCommodityCompositionBean) getRequestBean();
    // 登录更新权限
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      reqBean.setRegisterButtonDisplayFlg(true);
    }
    // 删除权限
    if (Permission.COMMODITY_DELETE.isGranted(getLoginInfo())) {
      reqBean.setDeleteButtonDisplayFlg(true);
    }
    setRequestBean(reqBean);
  }

}
