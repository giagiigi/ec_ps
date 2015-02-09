package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.shop.UserAccountSearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.AccountListBean;
import jp.co.sint.webshop.web.bean.back.shop.AccountListBean.AccountListDetail;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1050910:管理ユーザマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class AccountListBaseAction extends WebBackAction<AccountListBean> {

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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    if (getLoginInfo().isOperator()) {
      setNextUrl("/app/shop/account_edit/init");
      return BackActionResult.RESULT_SUCCESS;
    }

    AccountListBean bean = getBean();
    AccountListDetail detail = bean.getSearchCondition();

    List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();
    if (getLoginInfo().isShop()) {
      String shopCode = getLoginInfo().getShopCode();
      detail.setShopCode(shopCode);
      bean.setSearchCondition(detail);

      // ショップリストを自分自身１件分表示する処理
      detail.setShopCode(shopCode);
      ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
      Shop userShop = shopService.getShop(shopCode);
      shopList.add(new NameValue(userShop.getShopName(), shopCode));
    } else {
      shopList = ServiceLocator.getUtilService(getLoginInfo()).getShopNamesDefaultAllShop(true, false);
    }

    UserAccountSearchCondition condition = getSearchCondition();
    condition.setShopCode(getBean().getSearchCondition().getShopCode());
    condition.setUserLoginId(getBean().getSearchCondition().getUserLoginId());
    condition.setUserName(getBean().getSearchCondition().getUserName());

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    SearchResult<UserAccount> searchResult = service.getUserAccountList(condition);
    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(searchResult, SearchWarningType.BOTH);

    List<UserAccount> accountList = searchResult.getRows();
    List<AccountListDetail> detailList = new ArrayList<AccountListDetail>();

    for (UserAccount account : accountList) {
      AccountListDetail detailResult = new AccountListDetail();
      detailResult.setShopCode(account.getShopCode());
      detailResult.setUserName(account.getUserName());
      detailResult.setUserCode(Long.toString(account.getUserCode()));
      detailResult.setUserLoginId(account.getUserLoginId());

      detailList.add(detailResult);
    }

    AccountListBean reqBean = getBean();

    reqBean.setSearchResult(detailList);

    reqBean.setPagerValue(PagerUtil.createValue(searchResult));

    reqBean.setShopList(shopList);

    setRequestBean(reqBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 管理ユーザの検索条件を取得する基底クラスです。
   * 
   * @return 検索条件
   */
  public abstract UserAccountSearchCondition getSearchCondition();

}
