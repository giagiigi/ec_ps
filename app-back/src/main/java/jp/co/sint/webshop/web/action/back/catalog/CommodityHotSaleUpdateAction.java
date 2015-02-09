package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dao.HotSaleCommodityDao;
import jp.co.sint.webshop.data.dto.HotSaleCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityHotSaleBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 套餐设定のアクションクラスです
 * 
 * @author KS.
 */
public class CommodityHotSaleUpdateAction extends CommodityHotSaleInitAction {
  
  @Required
  @Quantity
  @Range(min = 0, max = 99999)
  @Metadata(name = "排列顺序")
  private String selectCommodityRank;

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  @Override
  public boolean validate() {
    String languageCode = getRequestParameter().getPathArgs()[0];
    String commodityCode = getRequestParameter().getPathArgs()[1];
    selectCommodityRank = getRequestParameter().get("sortRank_"+languageCode+"_"+commodityCode);
    if (!validateBean(this)) {
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
    CommodityHotSaleBean reqBean = getBean();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    HotSaleCommodityDao saleDao = DIContainer.getDao(HotSaleCommodityDao.class);

    String languageCode = getRequestParameter().getPathArgs()[0];
    String commodityCode = getRequestParameter().getPathArgs()[1];
    String dtoLangCode = "";
    if (languageCode.equals("zh")) {
      dtoLangCode = "zh-cn";
    } else if (languageCode.equals("jp")) {
      dtoLangCode = "ja-jp";
    } else if  (languageCode.equals("us")) {
      dtoLangCode = "en-us";
    } else {
      dtoLangCode = "zh-cn";
    }
    
    HotSaleCommodity hsc = saleDao.load(commodityCode, dtoLangCode);
    if (hsc == null ) {
      addErrorMessage("该商品未登录");
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }
    
    // 添加商品存在
    CommodityInfo info = catalogService.getCommodityInfo("00000000", commodityCode);
    if (info == null || info.getHeader() == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "商品:" + commodityCode));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    hsc.setSortRank(Long.parseLong(selectCommodityRank));
    saleDao.update(hsc);

    addInformationMessage(WebMessage.get(ActionErrorMessage.REGISTER_SECCESS, "热销商品:" + commodityCode));
    setRequestBean(reqBean);
    super.callService();
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("套餐明细登录处理");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104019002";
  }

  
  /**
   * @return the selectCommodityRank
   */
  public String getSelectCommodityRank() {
    return selectCommodityRank;
  }

  
  /**
   * @param selectCommodityRank the selectCommodityRank to set
   */
  public void setSelectCommodityRank(String selectCommodityRank) {
    this.selectCommodityRank = selectCommodityRank;
  }

}
