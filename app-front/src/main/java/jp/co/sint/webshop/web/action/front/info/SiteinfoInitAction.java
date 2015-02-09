package jp.co.sint.webshop.web.action.front.info;

import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.info.SiteinfoBean;

/**
 * U2050110:サイト情報のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SiteinfoInitAction extends WebFrontAction<SiteinfoBean> {

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
    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());

    SiteinfoBean bean = new SiteinfoBean();
    Shop siteInfo = service.getSite();
    bean.setSiteInfo(siteInfo);
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

}
