package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityEditPreviewAction extends CommodityEditBaseAction {

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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    WebshopConfig config = DIContainer.getWebshopConfig();
    CommodityEditBean reqBean = getBean();
    reqBean.setDisplayPreview(true);
    reqBean.setPreviewDigest(PasswordUtil.encrypt(reqBean.getShopCode() + "/" + reqBean.getCommodityCode() + "/"
        + DateUtil.getTimeStamp()));
    
    String previewUrl = WebUtil.getSecureUrl(config.getHostName(), config.getHttpsPort(), config.getFrontContext()
        + config.getPreviewUrl() + "/" + reqBean.getShopCode() + "/" + reqBean.getCommodityCode());
    reqBean.setPreviewUrl(previewUrl);
//    reqBean.setPreviewUrl(config.getTopPageUrl() + config.getPreviewUrl() + "/" + reqBean.getShopCode() + "/"
//        + reqBean.getCommodityCode());
    setRequestBean(reqBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditPreviewAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104012006";
  }

}
