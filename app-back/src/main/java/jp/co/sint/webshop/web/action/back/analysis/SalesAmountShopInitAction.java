package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountShopBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070820:ショップ別売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountShopInitAction extends SalesAmountShopSearchAction {

  @Override
  public boolean authorize() {
    boolean authorization;

    BackLoginInfo login = getLoginInfo();
    authorization = Permission.ANALYSIS_READ_SITE.isGranted(login) || Permission.ANALYSIS_READ_SHOP.isGranted(login);

    String[] urlParam = getRequestParameter().getPathArgs();

    if ((getConfig().isMall() || getConfig().isShop()) && urlParam.length < 3) {
      return false;
    } else {
      if (login.isShop() && getRequestParameter().getPathArgs().length > 0) {

        String shopCode = getRequestParameter().getPathArgs()[0];
        if (StringUtil.isNullOrEmpty(shopCode)) {
          return false;
        }

        authorization &= shopCode.equals(login.getShopCode());
      }
    }

    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] urlParam = getRequestParameter().getPathArgs();

    if ((getConfig().isMall() || getConfig().isShop()) && urlParam.length < 3) {
      throw new URLNotFoundException();
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
    String[] urlParam = getRequestParameter().getPathArgs();

    String shopCode = "";
    String year = "";
    String month = "";

    if (getConfig().isOne()) {
      shopCode = getLoginInfo().getShopCode();
      year = DateUtil.getYYYY(DateUtil.getSysdate());
      month = DateUtil.getMM(DateUtil.getSysdate());
    } else {
      shopCode = urlParam[0];
      year = urlParam[1];
      month = urlParam[2];
    }

    SalesAmountShopBean bean = new SalesAmountShopBean();
    bean.setShopCode(shopCode);
    bean.setSearchYear(year);
    bean.setSearchMonth(month);
    bean.setDisplayMode(SalesAmountShopBean.DISPLAY_MODE_DAY);

    setBean(bean);
    return super.callService();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SalesAmountShopInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107082002";
  }

}
