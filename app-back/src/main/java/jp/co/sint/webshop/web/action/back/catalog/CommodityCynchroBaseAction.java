package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.CommodityListSearchCondition;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCynchroBean;

/**
 * U1040110:商品マスタのアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class CommodityCynchroBaseAction extends WebBackAction<CommodityCynchroBean> {

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    CommodityCynchroBean bean = getBean();

    

    setBean(bean);
  }

  public void setCondition(CommodityListSearchCondition condition) {
    
  }

  /**
   * 画面に表示するボタンの制御を行います。
   * 
   * @param bean
   */
  public void setDisplayControl(CommodityCynchroBean bean) {
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      bean.setUpdateButtonDisplayFlg(true);
    }else {
      bean.setUpdateButtonDisplayFlg(false);
    }
  }

}
