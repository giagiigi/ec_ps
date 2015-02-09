package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityChoseBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 套餐设定のアクションクラスです
 * 
 * @author KS.
 */
public class CommodityChoseUpdateAction extends CommodityChoseSearchAction {
  
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
    CommodityChoseBean reqBean = getBean();
    String[] param = getRequestParameter().getPathArgs();
    List<String> checkList = new ArrayList<String>();
    if(param.length > 0 ) {
      String commodityCode = param[0];
      checkList.add(commodityCode);
    } else {
      checkList = reqBean.getCheckedCommodityList();

    }
    for (String s : checkList) {
      selectCommodityRank = getRequestParameter().get("sortRank_"+"_"+s);
      if (!validateBean(this)) {
        return false;
      }
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
    CommodityChoseBean reqBean = getBean();
    
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeaderDao headerDao = DIContainer.getDao(CommodityHeaderDao.class);
    List<String> checkList = new ArrayList<String>();
    String[] param = getRequestParameter().getPathArgs();
    if(param.length > 0 ) {
      String commodityCode = param[0];
      
      checkList.add(commodityCode);
    } else {
      checkList = reqBean.getCheckedCommodityList();

    }
    
    for (String s : checkList) {
      // 添加商品存在
      CommodityInfo info = catalogService.getCommodityInfo("00000000", s);
      if (info == null || info.getHeader() == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "商品:" + s));
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }
      CommodityHeader ch = info.getHeader();
      selectCommodityRank = getRequestParameter().get("sortRank_"+"_"+s);
      if (StringUtil.hasValue(selectCommodityRank) ) {
        ch.setChosenSortRank(Long.parseLong(selectCommodityRank));
      } else {
        ch.setChosenSortRank(null);
      }
      
      headerDao.update(ch);
    }

    addInformationMessage("排列顺序更新成功");
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
    return Messages.getString("品店精选排序更新");
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
