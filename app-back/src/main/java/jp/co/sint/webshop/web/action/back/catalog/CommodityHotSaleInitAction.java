package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.dao.HotSaleCommodityDao;
import jp.co.sint.webshop.data.dto.HotSaleCommodity;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityHotSaleBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityHotSaleBean.HotSaleDetail;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 套餐设定のアクションクラスです
 * 
 * @author KS.
 */
public class CommodityHotSaleInitAction extends WebBackAction<CommodityHotSaleBean> {

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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommodityHotSaleBean bean = getBean();
    bean.setSearchCommodityCodejp("");
    bean.setSearchCommodityCodezh("");
    bean.setSearchCommodityCodeus("");
    bean.setInputSortRankjp("");
    bean.setInputSortRankzh("");
    bean.setInputSortRankus("");
    
    HotSaleCommodityDao saleDao = DIContainer.getDao(HotSaleCommodityDao.class);
    List<HotSaleCommodity> saleCommodityList = saleDao.loadByLanguageCode("zh-cn");
    if (saleCommodityList != null && saleCommodityList.size() > 0) {
      createDetailFromDto(saleCommodityList,bean.getHotListzh());
    } else {
      bean.getHotListzh().clear();
    }
    
    List<HotSaleCommodity> saleCommodityListjp = saleDao.loadByLanguageCode("ja-jp");
    if (saleCommodityListjp != null && saleCommodityListjp.size() > 0) {
      createDetailFromDto(saleCommodityListjp,bean.getHotListjp());
    } else {
      bean.getHotListjp().clear();
    }
    
    List<HotSaleCommodity> saleCommodityListus = saleDao.loadByLanguageCode("en-us");
    if (saleCommodityListus != null && saleCommodityListus.size() > 0) {
      createDetailFromDto(saleCommodityListus,bean.getHotListus());
    } else {
      bean.getHotListus().clear();
    }
    
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }
  
  private void createDetailFromDto(List<HotSaleCommodity> saleCommodityList,List<HotSaleDetail> detailList) {
    detailList.clear();
    for(HotSaleCommodity sale : saleCommodityList) {
      
      HotSaleDetail detail = new HotSaleDetail();
      detail.setCommodityCode(sale.getCommodityCode());
      detail.setCommodityName(sale.getCommodityName());
      detail.setLanguageCode(sale.getLanguageCode());
      detail.setSortRank(sale.getSortRank().toString());
      
      detailList.add(detail);
    }
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CommodityHotSaleBean reqBean = (CommodityHotSaleBean) getRequestBean();
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
