package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
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
public class ShopListInitAction extends WebBackAction<ShopListBean> {

  private boolean shopNameLinkFlg = false;

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

    if (Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(loginInfo)) {
      setNextUrl("/app/shop/shop_edit");
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

    ShopListSearchCondition condition = new ShopListSearchCondition();
    condition.setShopStatus("0");

    SearchResult<Shop> result = service.getShopList(condition);
    // オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.OVERFLOW);

    List<Shop> shopList = result.getRows();

    // shopListBeanの作成
    ShopListBean shopListBean = new ShopListBean();

    shopListBean.setPagerValue(PagerUtil.createValue(result));

    // ショップデータを受取る変数を作成
    List<ShopDetail> shopDetailList = new ArrayList<ShopDetail>();

    // ショップコードリスト(shopCodeとshopNameのリスト)を受取る変数を作成
    List<NameValue> shopCodeList = new ArrayList<NameValue>();

    // 検索結果を一件づつshopDetailにセット
    for (Shop shop : shopList) {
      ShopDetail shopDetail = new ShopDetail();
      NameValue shopNameValue = new NameValue();

      Date openDate;
      Date closeDate;

      if (shop.getOpenDatetime() == null) {
        openDate = DateUtil.getMin();
      } else {
        openDate = shop.getOpenDatetime();
      }

      if (shop.getCloseDatetime() == null) {
        closeDate = DateUtil.getMax();
      } else {
        closeDate = shop.getCloseDatetime();
      }

      int openDateTime = Integer.parseInt(DateUtil.toDateString(openDate).replace("/", ""));
      int closeDateTime = Integer.parseInt(DateUtil.toDateString(closeDate).replace("/", ""));
      int sysDate = Integer.parseInt(DateUtil.toDateString(DateUtil.getSysdate()).replace("/", ""));
      if (openDateTime <= sysDate && sysDate < closeDateTime) {
        shopDetail.setShopStatus("0");
      } else {
        shopDetail.setShopStatus("1");
      }

      shopDetail.setShopCode(shop.getShopCode());
      shopDetail.setShopName(shop.getShopName());
      shopDetail.setEmail(shop.getEmail());
      shopDetail.setTel(shop.getPhoneNumber());
      //Add by V10-CH start
      shopDetail.setMobileTel(shop.getMobileNumber());
      //Add by V10-CH end

      shopDetailList.add(shopDetail);

      // shopCodeListを作成
      shopNameValue.setName(shop.getShopCode());
      shopNameValue.setValue(shop.getShopName());
      shopCodeList.add(shopNameValue);

    }

    // shopListBeanにショップ情報リスト・ショップコードリストをセット
    shopListBean.setSearchResultList(shopDetailList);
    shopListBean.setShopNameList(shopCodeList);
    shopListBean.setShopNameLinkFlg(shopNameLinkFlg);

    // 検索条件の初期値を設定
    ShopDetail searchConditionShopDetail = new ShopDetail();
    searchConditionShopDetail.setShopCode("");
    searchConditionShopDetail.setShopName("");
    searchConditionShopDetail.setTel("");
    //Add by V10-CH start
    searchConditionShopDetail.setMobileTel("");
    //Add by V10-CH end
    searchConditionShopDetail.setEmail("");
    searchConditionShopDetail.setShopStatus("0");
    shopListBean.setSearchCondition(searchConditionShopDetail);

    setRequestBean(shopListBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    BackLoginInfo loginInfo = getLoginInfo();
    if (Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(loginInfo)) {
      shopNameLinkFlg = true;
    }

    ShopListBean shopListBean = (ShopListBean) getRequestBean();
    shopListBean.setShopNameLinkFlg(shopNameLinkFlg);
    setRequestBean(shopListBean);

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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.ShopListInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105021002";
  }

}
