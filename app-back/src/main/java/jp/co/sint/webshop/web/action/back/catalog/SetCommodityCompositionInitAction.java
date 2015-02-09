package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dao.JdSuitCommodityDao;
import jp.co.sint.webshop.data.dao.TmallSuitCommodityDao;
import jp.co.sint.webshop.data.dto.JdSuitCommodity;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.dto.TmallSuitCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StockQuantityUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.SetCommodityCompositionBean;
import jp.co.sint.webshop.web.bean.back.catalog.SetCommodityCompositionBean.CompositionDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 套餐设定のアクションクラスです
 * 
 * @author KS.
 */
public class SetCommodityCompositionInitAction extends SetCommodityCompositionBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] url = getRequestParameter().getPathArgs();
    if (url == null || url.length < 2) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    TmallSuitCommodityDao suitDao = DIContainer.getDao(TmallSuitCommodityDao.class);
    SetCommodityCompositionBean bean = getBean();

    String[] url = getRequestParameter().getPathArgs();
    String shopCode = "00000000";
    String commodityCode;
    if(url.length > 0) {
      commodityCode = url[1];
      if (bean.isUpdatePriceFlg()) {
        commodityCode = bean.getCommodityCode();
      }
    } else {
      commodityCode = bean.getCommodityCode();
    }


    
    TmallSuitCommodity suit = suitDao.load(commodityCode);
    if(suit != null) {
      bean.setSuitTmallStock(suit.getStockQuantity().toString());
      bean.setScaleValue(suit.getScaleValue().toString());
      bean.setSuitTmallAllocate(suit.getAllocatedQuantity().toString());
    } else {
      bean.setSuitTmallStock("");
      bean.setScaleValue("");
      bean.setSuitTmallAllocate("");
    }
    
    // 取得套餐商品信息
    CommodityInfo result = service.getCommodityInfo(shopCode, commodityCode);
    bean.setShopCode(result.getHeader().getShopCode());
    bean.setCommodityCode(result.getHeader().getCommodityCode());
    bean.setCommodityName(result.getHeader().getCommodityName());
    bean.setSaleStartDatetime(DateUtil.toDateTimeString(result.getHeader().getSaleStartDatetime()));
    bean.setSaleEndDatetime(DateUtil.toDateTimeString(result.getHeader().getSaleEndDatetime()));
    // 取得套餐明细信息
    List<SetCommodityComposition> compositionList = service.getSetCommodityInfo(bean.getShopCode(), bean.getCommodityCode());
    List<CompositionDetail> list = new ArrayList<CompositionDetail>();
    bean.setTmallRetailPrice(BigDecimal.ZERO.toString());
    bean.setRetailPrice(BigDecimal.ZERO.toString());
    if (compositionList != null) {
      for (SetCommodityComposition edit : compositionList) {
        Long allUseQuantity = service.getUseSuitStock(edit.getChildCommodityCode());
        // 组合品分配库存
        Long stockNum = StockQuantityUtil.getOriginalTmallStockQUANTITY(edit.getChildCommodityCode());
        String price = edit.getRetailPrice().toString();
        String priceTmall = edit.getTmallRetailPrice().toString();
        list.add(createCommodityComposition(allUseQuantity + stockNum,edit.getShopCode(), price,priceTmall, edit.getChildCommodityCode()));
        bean.setTmallRetailPrice(BigDecimalUtil.add( StringUtil.hasValue(bean.getTmallRetailPrice())? new BigDecimal(bean.getTmallRetailPrice()) : BigDecimal.ZERO , edit.getTmallRetailPrice()).toString() );
        bean.setRetailPrice(BigDecimalUtil.add( StringUtil.hasValue(bean.getRetailPrice())? new BigDecimal(bean.getRetailPrice()) : BigDecimal.ZERO , edit.getRetailPrice()).toString() );
      }   
    } 
    bean.setSize(list.size());
    bean.setCompositionDetailList(list);

    // 套餐商品价格
    //bean.setRetailPrice(result.getDetail().getUnitPrice().toString());
    bean.setInputRetailPrice("");
    bean.setSearchCommodityCode("");
    bean.setInputTmallRetailPrice("");
    
    // 2014/06/05 库存更新对应 ob_李先超 add start
    //京东套餐库存比例的获取
    JdSuitCommodityDao jdSuitDao = DIContainer.getDao(JdSuitCommodityDao.class);
    JdSuitCommodity jdSuit = jdSuitDao.load(commodityCode);
    if(jdSuit != null) {
      bean.setSuitJdStock(jdSuit.getStockQuantity().toString());
      bean.setSuitJdAllocate(jdSuit.getAllocatedQuantity().toString());
      bean.setJdScaleValue(jdSuit.getScaleValue().toString());
    } else {
      bean.setSuitJdStock("");
      bean.setSuitJdAllocate("");
      bean.setJdScaleValue("");
    }
    
    //[登录]、[删除]按钮显示Flg设置
    if (NumUtil.toLong(bean.getScaleValue()) > 0L || NumUtil.toLong(bean.getJdScaleValue()) > 0L) {
      bean.setButtonDisplayFlg(false);
    } else {
      bean.setButtonDisplayFlg(true);
    }
    // 2014/06/05 库存更新对应 ob_李先超 add end

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    super.prerender();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("套餐设定初期表示处理");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104019001";
  }

}
