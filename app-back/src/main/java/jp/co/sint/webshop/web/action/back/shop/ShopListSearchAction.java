package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.shop.ShopListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.ShopListBean;
import jp.co.sint.webshop.web.bean.back.shop.ShopListBean.ShopDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1050210:ショップマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShopListSearchAction extends WebBackAction<ShopListBean> {

  private ShopListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new ShopListSearchCondition();
  }

  protected ShopListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected ShopListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;
    BackLoginInfo loginInfo = getLoginInfo();

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = false;
    } else {
      authorization = Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(loginInfo);
    }

    return authorization;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    ShopListBean bean = getBean();

    // 検索を実行
    SearchResult<Shop> sResult = service.getShopList(condition);
    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(sResult, SearchWarningType.BOTH);

    List<Shop> shopList = sResult.getRows();

    ShopListBean nextBean = new ShopListBean();

    // ページ情報を追加
    nextBean.setPagerValue(PagerUtil.createValue(sResult));

    nextBean.setShopNameList(bean.getShopNameList());
    nextBean.setSearchCondition(bean.getSearchCondition());

    List<ShopDetail> shopDetailList = new ArrayList<ShopDetail>();

    for (Shop shopData : shopList) {
      ShopDetail shopDetail = new ShopDetail();
      shopDetail.setShopCode(shopData.getShopCode());
      shopDetail.setShopName(shopData.getShopName());
      shopDetail.setTel(shopData.getPhoneNumber());
      //Add by V10-CH start
      shopDetail.setMobileTel(shopData.getMobileNumber());
      //Add by V10-CH end
      shopDetail.setEmail(shopData.getEmail());
      int openDateTime = Integer.parseInt(DateUtil.toDateString(shopData.getOpenDatetime()).replace("/", ""));
      int closeDateTime = 99991231;
      if (shopData.getCloseDatetime() != null) {
        closeDateTime = Integer.parseInt(DateUtil.toDateString(shopData.getCloseDatetime()).replace("/", ""));
      }
      int sysDate = Integer.parseInt(DateUtil.toDateString(DateUtil.getSysdate()).replace("/", ""));
      if (openDateTime <= sysDate && sysDate <= closeDateTime) {
        shopDetail.setShopStatus("0");
      } else {
        shopDetail.setShopStatus("1");
      }

      shopDetailList.add(shopDetail);

    }

    nextBean.setSearchResultList(shopDetailList);
    nextBean.setShopNameLinkFlg(bean.getShopNameLinkFlg());

    setRequestBean(nextBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    ShopListBean bean = getBean();

    condition.setShopCode(bean.getSearchCondition().getShopCode());
    condition.setShopName(bean.getSearchCondition().getShopName());
    condition.setTel(bean.getSearchCondition().getTel().replace("-", "")); // もしハイフンが入力されていても除去
    //Add by V10-CH start
    condition.setMobileTel(bean.getSearchCondition().getMobileTel());
    //Add by V10-CH end
    condition.setEmail(bean.getSearchCondition().getEmail());
    condition.setShopStatus(bean.getSearchCondition().getShopStatus());

    condition = getCondition();

    return validateBean(condition);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.ShopListSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105021003";
  }

}
