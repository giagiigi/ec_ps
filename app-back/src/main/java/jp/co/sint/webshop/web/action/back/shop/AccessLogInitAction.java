package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.AccessLogBean;
import jp.co.sint.webshop.web.log.back.AccessLogDiv;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1050940:管理側アクセスログのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AccessLogInitAction extends WebBackAction<AccessLogBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().isAdmin();
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
    AccessLogBean bean = new AccessLogBean();
    List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

    if (getLoginInfo().isSite() && getLoginInfo().isAdmin()) {
      shopList = ServiceLocator.getUtilService(getLoginInfo()).getShopNames(true, true);
    } else {
      ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
      String shopCode = getLoginInfo().getShopCode();
      Shop userShop = shopService.getShop(shopCode);
      shopList.add(new NameValue(userShop.getShopName(), shopCode));
    }
    bean.setShopList(shopList);

    Date today = DateUtil.truncateDate(DateUtil.getSysdate());
    Date tomorrow = DateUtil.addDate(today, 1);
    bean.getSearchCondition().setDatetimeFrom(DateUtil.toDateTimeString(today));
    bean.getSearchCondition().setDatetimeTo(DateUtil.toDateTimeString(tomorrow));

    List<CodeAttribute> operationList = new ArrayList<CodeAttribute>();
    operationList.add(new NameValue(Messages.getString(
      "web.action.back.shop.AccessLogInitAction.0"), ""));
    for (AccessLogDiv logDiv : AccessLogDiv.values()) {
      operationList.add(new NameValue(logDiv.getName(), logDiv.getValue()));
    }
    bean.setOperationList(operationList);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.AccessLogInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105094001";
  }

}
