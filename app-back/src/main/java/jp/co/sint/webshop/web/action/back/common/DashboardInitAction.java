package jp.co.sint.webshop.web.action.back.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CouponFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.InformationType;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.SaleStatus;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.domain.SkuStatus;
import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.data.dto.Information;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.catalog.CommodityListSearchCondition;
import jp.co.sint.webshop.service.communication.CampaignHeadLine;
import jp.co.sint.webshop.service.communication.CampaignListSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.common.DashboardBean;
import jp.co.sint.webshop.web.bean.back.common.DashboardBean.DashboardBeanDetail;
import jp.co.sint.webshop.web.bean.back.common.DashboardBean.MenuDetail;
import jp.co.sint.webshop.web.bean.back.common.DashboardBean.MessageDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.menu.back.BackDetailMenuBase;
import jp.co.sint.webshop.web.menu.back.BackMenuController;
import jp.co.sint.webshop.web.menu.back.TabMenu;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.common.DashboardInformationMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.SessionContainer;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1010210:ダッシュボードのアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DashboardInitAction extends WebBackAction<DashboardBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return (login != null && login.isLogin());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    // 画面遷移履歴情報ほか削除
    SessionContainer session = getSessionContainer();
    session.clearBackTransitionInfoList();
    Cart cart = session.getCart();
    if (cart != null) {
      cart.clear();
    }
    session.setTempBean(null);
    session.getAfterLoginInfo();

    DashboardBean bean = new DashboardBean();
    BackLoginInfo login = getLoginInfo();
    List<DashboardBeanDetail> todoList = new ArrayList<DashboardBeanDetail>();

    // ToDoリスト：支払期日切れの受注情報
    if (Permission.ORDER_READ_SITE.isGranted(login) || Permission.ORDER_READ_SHOP.isGranted(login)) {
      DashboardBeanDetail detail = getPaymentDeadline();
      if (detail != null) {
        todoList.add(detail);
      }
    }

    // ToDoリスト：退会依頼中の顧客情報
    if (getLoginInfo().isSite() && Permission.CUSTOMER_READ.isGranted(login)) {
      DashboardBeanDetail detail = this.getCustomerWithdrawal();
      if (detail != null) {
        todoList.add(detail);
      }
    }

    // ToDoリスト：在庫切れSKU情報
    if (Permission.CATALOG_READ.isGranted(login) || Permission.COMMODITY_READ.isGranted(login)) {
      DashboardBeanDetail detail =null;// this.getStockLimit();
      if (detail != null) {
        todoList.add(detail);
      }
    }

    bean.setTodoSituation(todoList);
    bean.setTodoDisplay(todoList.size() > 0);

    // 現在のシステムお知らせ一覧
    List<MessageDetail> systemInformationList = getInformationMessages(InformationType.SYSTEM);
    bean.setSystemInformationMessages(systemInformationList);
    bean.setInformationDisplay(systemInformationList != null && systemInformationList.size() > 0);

    // サイト状況：実施中のポイントルール情報
    List<String> pointRuleList = this.getPointRule();
    for (String s : pointRuleList) {
      bean.getSiteSituation().add(s);
      bean.setSiteDisplay(true);
    }
    List<String> couponRuleList = this.getCouponRule();
    for (String s : couponRuleList) {
      bean.getSiteSituation().add(s);
      bean.setSiteDisplay(true);
    }
    

    // キャンペーン状況：実施中のキャンペーン情報
    if (Permission.CAMPAIGN_READ_SITE.isGranted(login) || Permission.CAMPAIGN_READ_SHOP.isGranted(login)) {
      List<DashboardBeanDetail> detailList = this.getCampaignUnderway();
      for (DashboardBeanDetail r : detailList) {
        bean.getCampaignSituation().add(r);
        bean.setCampaignDisplay(true);
      }
    }

    bean.setUsableFunction(createMenu());

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
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
   * ログインしたユーザが参照可能な全てのページ情報を取得します。<br>
   */
  private Map<String, List<MenuDetail>> createMenu() {
    BackMenuController controller = new BackMenuController(getLoginInfo(), getConfig());

    Map<String, List<MenuDetail>> menu = new HashMap<String, List<MenuDetail>>();

    for (TabMenu tab : TabMenu.values()) {
      if (controller.getPermittedMenus(tab).length <= 0) {
        continue;
      }
      List<MenuDetail> list = new ArrayList<MenuDetail>();
      for (BackDetailMenuBase base : controller.getPermittedMenus(tab)) {
        MenuDetail detail = new MenuDetail();
        detail.setName(base.getLabel());
        String url = base.getUrl();
        url = url.replace("/menu", "/app");
        detail.setUrl(url);
        list.add(detail);
      }
      menu.put(tab.getSubsystemName(), list);
    }

    return menu;
  }

  /**
   * ToDoリストに表示する支払期日切れの受注情報を取得します。<br>
   * 存在すれば、件数情報を含むメッセージと遷移先URLを返します。<br>
   * 結果が0件なら空文字を返します。<br>
   * 
   * @return DashboardBeanDetail
   */
  private DashboardBeanDetail getPaymentDeadline() {
    BackLoginInfo login = getLoginInfo();
    OrderService orderService = ServiceLocator.getOrderService(login);
    OrderListSearchCondition condition = new OrderListSearchCondition();
    if (getConfig().isShop() && Permission.ORDER_READ_SHOP.isGranted(login)) {
      condition.setShopCode(getLoginInfo().getShopCode());
    } else if (Permission.ORDER_READ_SITE.isGranted(login)) {
      condition.setSiteAdmin(true);
      condition.setShopCode(getConfig().getSiteShopCode());
    }
    // 受注ステータス:受注
    condition.setOrderStatus(new String[] {
        OrderStatus.RESERVED.getValue(), OrderStatus.ORDERED.getValue()
    });
    // 出荷ステータス:未出荷/一部出荷/全出荷
    condition.setShippingStatusSummary(ShippingStatusSummary.SHIPPED_ALL.getValue(),
        ShippingStatusSummary.IN_PROCESSING.getValue(), ShippingStatusSummary.PARTIAL_SHIPPED.getValue(),
        ShippingStatusSummary.NOT_SHIPPED.getValue());
    // 返品状況:返品なし/一部返品/全返品
    condition.setReturnStatusSummary(new String[] {
        ReturnStatusSummary.NOT_RETURNED.getValue(), ReturnStatusSummary.PARTIAL_RETURNED.getValue(),
        ReturnStatusSummary.RETURNED_ALL.getValue()
    });
    // 入金状況:未入金のみ
    condition.setPaymentStatus(PaymentStatus.NOT_PAID.getValue());
    // 支払期限:支払期限を過ぎた伝票のみ
    condition.setSearchPaymentLimitOver(true);

    condition.setSearchFixedSalesDataFlg(true);

    DashboardBeanDetail detail = null;
    if (orderService.searchOrderList(condition).getRowCount() > 0) {
      detail = new DashboardBeanDetail();
      detail.setMessage(WebMessage.get(DashboardInformationMessage.PAYMENT_DEADLINE));
      detail.setUrl("/app/order/order_list/search");
      Map<String, String[]> params = new HashMap<String, String[]>();
      // 受注ステータス:受注
      params.put("searchOrderStatus", new String[] {
          OrderStatus.RESERVED.getValue(), OrderStatus.ORDERED.getValue()
      });
      // 出荷ステータス:未出荷/一部出荷/全出荷
      params.put("searchShippingStatusSummary", new String[] {
          ShippingStatusSummary.SHIPPED_ALL.getValue(), ShippingStatusSummary.PARTIAL_SHIPPED.getValue(),
          ShippingStatusSummary.IN_PROCESSING.getValue(), ShippingStatusSummary.NOT_SHIPPED.getValue()
      });
      // 返品状況:返品なし/一部返品/全返品
      params.put("searchReturnStatusSummary", new String[] {
          ReturnStatusSummary.NOT_RETURNED.getValue(), ReturnStatusSummary.PARTIAL_RETURNED.getValue(),
          ReturnStatusSummary.RETURNED_ALL.getValue()
      });
      // 受注管理画面で詳細検索ウィンドウを開かない
      params.put("advancedSearchMode", new String[] {
        // 10.1.1 10007 修正 ここから
        // "1"
        "0"
        // 10.1.1 10007 修正 ここまで
      });
      // 入金状況:未入金のみ
      params.put("searchPaymentStatus", new String[] {
        PaymentStatus.NOT_PAID.getValue()
      });
      // 支払期限:支払期限を過ぎた伝票のみ
      params.put("searchPaymentLimitOver", new String[] {
        "true"
      });
      // 売上確定フラグ：確定データを含む
      params.put("searchFixedSalesDataFlg", new String[] {
        WebConstantCode.VALUE_TRUE
      });
      detail.setRequestParameter(params);
    }
    return detail;
  }

  /**
   * ToDoリストに表示する退会依頼中の顧客情報を取得します。<br>
   * 存在すれば、メッセージと遷移先URLを返します。<br>
   * 存在しないならnullを返します。<br>
   * 
   * @return DashboardBeanDetail
   */
  private DashboardBeanDetail getCustomerWithdrawal() {
    CustomerSearchCondition condition = new CustomerSearchCondition();
    condition.setSearchCustomerStatus(CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.getValue());
    condition.setSearchRequestMailType("");

    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());

    DashboardBeanDetail detail = null;
    if (customerService.findCustomer(condition).getRowCount() > 0) {
      detail = new DashboardBeanDetail();
      detail.setMessage(WebMessage.get(DashboardInformationMessage.CUSTOMER_WITHDRAWAL));
      Map<String, String[]> params = new HashMap<String, String[]>();
      params.put("searchCustomerStatus", new String[] {
        CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.getValue()
      });
      params.put("searchRequestMailType", new String[] {
        ""
      });
      detail.setRequestParameter(params);
      detail.setUrl("/app/customer/customer_list/search");
    }
    return detail;
  }

  /**
   * ToDoリストに表示する在庫切れSKU情報を取得します。<br>
   * 存在すれば、件数情報を含むメッセージと遷移先URLを返します。<br>
   * 結果が0件なら空文字を返します。<br>
   * 
   * @return DashboardBeanDetail
   */
  private DashboardBeanDetail getStockLimit() {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    CommodityListSearchCondition condition = new CommodityListSearchCondition();
    // ショップコード
    condition.setSearchShopCode(getLoginInfo().getShopCode());
    // 在庫状況:一部規格あり/全規格なし
    condition.setSearchStockStatus(new String[] {
        SkuStatus.NONE.getValue(), SkuStatus.SOME.getValue()
    });
    // 販売状態:両方
    condition.setSearchSaleType("2");
    // 適用期間:予約期間中/販売中
    condition.setSearchSaleStatus(new String[] {
        SaleStatus.ACCESPTING_RESERVATION.getValue(), SaleStatus.ON_SALE.getValue()
    });
    // 表示クライアント:両方/PC/携帯
    condition.setSearchDisplayClientType(new String[] {
        DisplayClientType.ALL.getValue(), DisplayClientType.PC.getValue(), DisplayClientType.MOBILE.getValue()
    });
    // 入荷お知らせ:両方
    condition.setSearchArrivalGoods("2");
    SearchResult<CommodityContainer> resultList = catalogService.getCommoditySearch(condition);

    DashboardBeanDetail detail = null;
    if (resultList.getRowCount() > 0) {
      detail = new DashboardBeanDetail();
      detail.setMessage(WebMessage.get(DashboardInformationMessage.STOCK_LIMIT));
      detail.setUrl("/app/catalog/commodity_list/search");
      Map<String, String[]> params = new HashMap<String, String[]>();
      // 詳細検索ウィンドウを開く
      params.put("advancedSearchMode", new String[] {
        "1"
      });
      // ショップコード
      params.put("searchShopCode", new String[] {
        getLoginInfo().getShopCode()
      });
      // 在庫状況:一部規格あり/全規格なし
      params.put("searchStockStatus", new String[] {
          SkuStatus.NONE.getValue(), SkuStatus.SOME.getValue()
      });
      // 販売状態:両方
      params.put("searchSaleType", new String[] {
        "2"
      });
      // 適用期間:予約期間中/販売中
      params.put("searchSaleStatus", new String[] {
          SaleStatus.ACCESPTING_RESERVATION.getValue(), SaleStatus.ON_SALE.getValue()
      });
      // 表示クライアント:両方/PC/携帯
      params.put("searchDisplayClientPc", new String[] {
        "1"
      });
      params.put("searchDisplayClientMobile", new String[] {
        "1"
      });

      // 入荷お知らせ:両方
      params.put("searchArrivalGoods", new String[] {
        "2"
      });
      detail.setRequestParameter(params);
    }
    return detail;
  }

  /**
   * サイト状況に表示するお知らせ情報を取得します。<br>
   * 表示件数が0件なら空文字を返します。<br>
   * 
   * @return message
   */
  private List<MessageDetail> getInformationMessages(InformationType type) {
    SiteManagementService siteManagementService = ServiceLocator.getSiteManagementService(getLoginInfo());

    List<MessageDetail> informationList = new ArrayList<MessageDetail>();
    for (Information info : siteManagementService.getInformationList()) {
      if (info.getInformationType().equals(NumUtil.toLong(type.getValue()))
          && DateUtil.isPeriodDate(info.getInformationStartDatetime(), info.getInformationEndDatetime())) {
        MessageDetail detail = new MessageDetail();

        detail.setStartDatetime(DateUtil.toDateTimeString(info.getInformationStartDatetime()));
        detail.setEndDatetime(DateUtil.toDateTimeString(info.getInformationEndDatetime()));
        detail.setMessage(info.getInformationContent());
        informationList.add(detail);
      }
    }
    return informationList;
  }

  /**
   * サイト状況に表示する実施中ポイントルール情報を取得します。<br>
   * ポイントルールを運用していなければ、空文字を返します。<br>
   * 
   * @return message
   */
  private List<String> getPointRule() {
    SiteManagementService siteManagementService = ServiceLocator.getSiteManagementService(getLoginInfo());
    PointRule point = siteManagementService.getPointRule();

    List<String> message = new ArrayList<String>();
    if (point.getPointFunctionEnabledFlg().equals(Long.valueOf(PointFunctionEnabledFlg.ENABLED.getValue()))) {
      String pointMessage = WebMessage.get(DashboardInformationMessage.POINT_ENABLED);
      message.add(pointMessage);

      // ボーナスポイント適用開始/終了日がともにnullの場合、メッセージを追加しない
      Date pointStartDate = point.getBonusPointStartDate();
      Date pointEndDate = point.getBonusPointEndDate();
      if (pointStartDate != null || pointEndDate != null) {
        // ボーナスポイント適用開始/終了日がnullだったらMin/Max値を設定
        if (pointStartDate == null) {
          pointStartDate = DateUtil.getMin();
        }
        if (pointEndDate == null) {
          pointEndDate = DateUtil.getMax();
        }

        if (DateUtil.isPeriodDate(pointStartDate, pointEndDate)
            || DateUtil.getDD(DateUtil.getSysdate()).equals(point.getBonusPointDate().toString())) {
          String bonusMessage = WebMessage.get(DashboardInformationMessage.BONUS_POINT, point.getBonusPointTermRate().toString());
          message.add(bonusMessage);
        }
      }
    }
    return message;
  }
  
  /**
   * サイト状況に表示する実施中ポイントルール情報を取得します。<br>
   * ポイントルールを運用していなければ、空文字を返します。<br>
   * 
   * @return message
   */
  private List<String> getCouponRule() {
    SiteManagementService siteManagementService = ServiceLocator.getSiteManagementService(getLoginInfo());
    CouponRule coupon = siteManagementService.getCouponRule();

    List<String> message = new ArrayList<String>();
    if (coupon.getCouponFunctionEnabledFlg().equals(Long.valueOf(CouponFunctionEnabledFlg.ENABLED.getValue()))) {
      String couponMessage = WebMessage.get(DashboardInformationMessage.COUPON_ENABLED);
      message.add(couponMessage);
    }
    return message;
  }

  /**
   * サイト状況に表示する実施中のキャンペーン情報を取得します。<br>
   * 存在すれば、件数情報を含むメッセージと遷移先URLを返します。<br>
   * 結果が0件なら空文字を返します。<br>
   * 
   * @return List<DashboardBeanDetail>
   */
  private List<DashboardBeanDetail> getCampaignUnderway() {
    CampaignListSearchCondition condition = new CampaignListSearchCondition();
    if (!getConfig().getSiteShopCode().equals(getLoginInfo().getShopCode())) {
      condition.setShopCode(getLoginInfo().getShopCode());
    }
    condition.setCampaignStartDateTo(DateUtil.getSysdateString());
    condition.setCampaignEndDateFrom(DateUtil.getSysdateString());

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    SearchResult<CampaignHeadLine> result = communicationService.getCampaignList(condition);

    List<DashboardBeanDetail> detailList = new ArrayList<DashboardBeanDetail>();
    for (CampaignHeadLine c : result.getRows()) {
      DashboardBeanDetail detail = new DashboardBeanDetail();
      String message = WebMessage.get(DashboardInformationMessage.CAMPAIGN_UNDERWAY, c.getCampaignEndDate(), c.getCampaignName(), c
          .getCampaignDiscountRate());

      StringBuilder builder = new StringBuilder();
      builder.append("/app/communication/campaign_research/init/");
      builder.append(c.getShopCode() + "/");
      builder.append(c.getCampaignCode());

      detail.setMessage(message);
      detail.setUrl(builder.toString());

      detailList.add(detail);
    }
    return detailList;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.common.DashboardInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "0101021001";
  }

}
