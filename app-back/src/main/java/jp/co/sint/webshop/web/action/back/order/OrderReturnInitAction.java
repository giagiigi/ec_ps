package jp.co.sint.webshop.web.action.back.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderReturnBean;
import jp.co.sint.webshop.web.bean.back.order.OrderReturnBean.ShippingBean;
import jp.co.sint.webshop.web.bean.back.order.OrderReturnBean.ShippingBean.ShippingDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020260:受注返品のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderReturnInitAction extends WebBackAction<OrderReturnBean> {

  private Map<String, String> shopNameMap = new HashMap<String, String>();

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    LoginInfo login = getLoginInfo();
    WebshopConfig config = getConfig();
    boolean auth = false;
    if (config.getOperatingMode() == OperatingMode.MALL) {
      auth = Permission.ORDER_READ_SITE.isGranted(login);
    } else if (config.getOperatingMode() == OperatingMode.SHOP) {
      auth = Permission.ORDER_READ_SHOP.isGranted(login) || Permission.ORDER_READ_SITE.isGranted(login);
    } else if (config.getOperatingMode() == OperatingMode.ONE) {
      auth = Permission.ORDER_READ_SITE.isGranted(login);
    }
    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = StringUtil.hasValue(getPathInfo(0));
    if (!valid) {
      // OrderNoがURLに設定されていなければログイン画面に遷移
      setNextUrl("/app/common/login");
    }
    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    OrderReturnBean bean = new OrderReturnBean();
    String orderNo = getPathInfo(0);
    bean.setOrderNo(orderNo);

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());

    OrderContainer container = service.getOrder(orderNo);
    if (container == null) {
      // OrderNoがただしいものでなければログイン画面に遷移
      setNextUrl("/app/common/login");
      return BackActionResult.RESULT_SUCCESS;
    }

    OrderSummary orderSummary = service.getOrderSummary(orderNo);
    if (orderSummary != null
        && orderSummary.getShippingStatusSummary() != null
        && (orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.PARTIAL_SHIPPED.longValue())
            || orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.SHIPPED_ALL.longValue())
            || orderSummary.getReturnStatusSummary().equals(ReturnStatusSummary.PARTIAL_RETURNED.longValue()) || orderSummary
            .getReturnStatusSummary().equals(ReturnStatusSummary.RETURNED_ALL.longValue()))) {

      bean = setOrderContainerToBean(bean, container);

      bean.setReturnSkuList(getSkuList(bean.getShippingList()));
      bean.setDispStatus(WebConstantCode.DISPLAY_EDIT);

      bean.setReturnShopList(getShopList(bean.getShippingList()));

      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;

    } else {
      // 受注情報に出荷済みデータが無い場合ログイン画面に遷移
      setNextUrl("/app/common/login");
      return BackActionResult.RESULT_SUCCESS;

    }
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    OrderReturnBean bean = (OrderReturnBean) getRequestBean();
    boolean auth = false;

    if (bean == null) {
      return;
    }

    DataTransportStatus transportStatus = DataTransportStatus.fromValue(bean.getDataTransportStatus());
    if (transportStatus == DataTransportStatus.TRANSPORTED) {
      // データ連携済み
      auth = false;
      bean.setRegisterButtonDisplay(false);
      bean.setDispStatus(WebConstantCode.DISPLAY_HIDDEN);
      addInformationMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
    } else if (transportStatus == DataTransportStatus.NOT_TRANSPORTED) {
      // データ未連携
      LoginInfo login = getLoginInfo();
      WebshopConfig config = getConfig();
      // 更新権限をチェックし更新ボタンの表示を制御する
      if (config.getOperatingMode() == OperatingMode.MALL) {
        auth = Permission.ORDER_MODIFY_SITE.isGranted(login);
      } else if (config.getOperatingMode() == OperatingMode.SHOP) {
        auth = Permission.ORDER_MODIFY_SHOP.isGranted(login) || Permission.ORDER_MODIFY_SITE.isGranted(login);
      } else if (config.getOperatingMode() == OperatingMode.ONE) {
        auth = Permission.ORDER_MODIFY_SITE.isGranted(login);
      }
    } else {
      throw new RuntimeException();
    }

    // 顧客が退会済みの場合はボタン・入力項目を非表示にする制御を行う
    boolean dispFlg = true;
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    String customerCode = orderService.getOrder(bean.getOrderNo()).getOrderHeader().getCustomerCode();
    if (CustomerConstant.isCustomer(customerCode)) {
      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
      if (customerService.isWithdrawed(customerCode)) {
        dispFlg = false;
      }
    }

    bean.setConfirmButtonDisplay(auth && dispFlg);
    for (ShippingBean shipping : bean.getShippingList()) {
      shipping.setDeleteButtonDisplay(auth && shipping.isDeleteButtonDisplay() && dispFlg);
    }
    bean.setRegisterButtonDisplay(false);
    if (auth && dispFlg) {
      bean.setDispStatus(WebConstantCode.DISPLAY_EDIT);
    } else {
      bean.setDispStatus(WebConstantCode.DISPLAY_HIDDEN);
    }

    String complete = getPathInfo(1);
    if (StringUtil.hasValue(complete)) {
      if (complete.equals(WebConstantCode.COMPLETE_REGISTER)) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
            Messages.getString("web.action.back.order.OrderReturnInitAction.0")));
      } else if (complete.equals(WebConstantCode.COMPLETE_DELETE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
            Messages.getString("web.action.back.order.OrderReturnInitAction.0")));
      }
    }
    setRequestBean(bean);
  }

  /**
   * 受注ヘッダの情報を返品画面Beanに設定する
   * 
   * @param orderBean
   * @param header
   */
  private OrderReturnBean setOrderContainerToBean(OrderReturnBean orderBean, OrderContainer container) {
    OrderHeader header = container.getOrderHeader();
    orderBean.setOrderStatus(NumUtil.toString(header.getOrderStatus()));
    orderBean.setDataTransportStatus(header.getDataTransportStatus());
    orderBean.setMessage(header.getMessage());
    orderBean.setCaution(header.getCaution());
    orderBean.setOrderUpdateDatetime(header.getUpdatedDatetime());
    orderBean.setCustomerCode(header.getCustomerCode());

    orderBean = setShippingToBean(orderBean, container);

    return orderBean;
  }

  /**
   * 出荷情報をBeanに設定する
   * 
   * @param orderBean
   * @param header
   * @param detailList
   */
  private OrderReturnBean setShippingToBean(OrderReturnBean orderBean, OrderContainer container) {

    List<ShippingContainer> shippingList = container.getShippings();
    ShippingContainer[] shippings = new ShippingContainer[shippingList.size()];

    // 出荷情報リストを出荷番号の昇順でソート
    Arrays.sort(shippingList.toArray(shippings), new ShippingContainerComparator());

    List<ShippingContainer> sortedShippings = new ArrayList<ShippingContainer>();

    for (ShippingContainer shipping : shippings) {
      sortedShippings.add(shipping);
    }

    container.setShippings(sortedShippings);

    List<ShippingBean> shippingBeanList = new ArrayList<ShippingBean>();
    int i = 1;
    for (ShippingContainer shipping : container.getShippings()) {
      ShippingHeader header = shipping.getShippingHeader();

      ShippingBean headerBean = new ShippingBean();
      headerBean.setShippingNo(header.getShippingNo());
      String shopCode = header.getShopCode();
      // ログインユーザがショップ管理者の場合自ショップ以外の情報は表示しない
      if (isNotUsableShopCode(shopCode)) {
        continue;
      }

      headerBean.setShopCode(shopCode);
      headerBean.setShopName(getShopName(shopCode));
      headerBean.setDeliveryTypeNo(header.getDeliveryTypeNo());
      headerBean.setDeliveryCharge(NumUtil.toString(header.getShippingCharge()));

      headerBean.setShippingNo(header.getShippingNo());
      headerBean.setShippingStatus(header.getShippingStatus());
      if (NumUtil.toString(header.getReturnItemType()).equals(ReturnItemType.RETURNED.getValue())) {
        headerBean.setReturnItem(true);
        headerBean.setItemLossMoney(NumUtil.toString(header.getReturnItemLossMoney()));

        boolean isCancelReturnItem = false;
        for (ShippingContainer sh : container.getShippings()) {
          if (!NumUtil.isNull(header.getOriginalShippingNo()) && StringUtil.hasValue(sh.getShippingNo())
              && NumUtil.toString(header.getOriginalShippingNo()).equals(sh.getShippingNo())
              && sh.getShippingHeader().getReturnItemType().equals(ReturnItemType.RETURNED.longValue())) {
            isCancelReturnItem = true;
            break;
          }
        }

        if (isCancelReturnItem) {
          headerBean.setCancelReturnItemFlg(true);
        } else {
          headerBean.setCancelReturnItemFlg(false);
        }

      } else {
        headerBean.setReturnItem(false);
        headerBean.setDeliveryCharge(NumUtil.toString(header.getShippingCharge()));
      }

      List<ShippingDetailBean> shippingDetailBeanList = new ArrayList<ShippingDetailBean>();
      for (ShippingDetail detail : shipping.getShippingDetails()) {
        ShippingDetailBean detailBean = new ShippingDetailBean();
        detailBean.setSkuCode(detail.getSkuCode());
        detailBean.setSalesPrice(NumUtil.toString(detail.getRetailPrice()));
        detailBean.setDetailGiftPrice(NumUtil.toString(detail.getGiftPrice()));
        detailBean.setPurchasingAmount(NumUtil.toString(detail.getPurchasingAmount()));
        //modify by V10-CH 170 start
        //detailBean.setDetailSummaryPrice(NumUtil.toString(header.getReturnItemLossMoney() + NumUtil.toLong(totalPrice(detail))));
        detailBean.setDetailSummaryPrice(NumUtil.toString(header.getReturnItemLossMoney().add(NumUtil.parse(totalPrice(detail)))));
        //modify by V10-CH 170 end
        detailBean.setShippingDetailBeanNo(Integer.toString(i++));
        // 商品関連情報は受注明細より取得
        OrderDetail orderDetail = getOrderDetail(container.getOrderDetails(), detail.getShopCode(), detail.getSkuCode());
        if (orderDetail != null) {
          detailBean.setComodityName(orderDetail.getCommodityName());
          detailBean.setStandardDetailName(createStandardDetailName(orderDetail.getStandardDetail1Name(), orderDetail
              .getStandardDetail2Name()));
        }
        shippingDetailBeanList.add(detailBean);
      }

      // 出荷明細情報が０件の場合は商品無し返品・金額調整とみなす
      if (shippingDetailBeanList.size() <= 0) {
        ShippingDetailBean detailBean = new ShippingDetailBean();
        detailBean.setDetailSummaryPrice(NumUtil.toString(header.getReturnItemLossMoney()));
        detailBean.setComodityName(Messages.getString("web.action.back.order.OrderReturnInitAction.1"));
        shippingDetailBeanList.add(detailBean);
      }
      headerBean.setShippingDetailList(shippingDetailBeanList);

      boolean deleteButtonDisplay = true;
      // 取得してきた出荷情報の中に、元出荷番号が自分自身の物があれば削除フラグはfalseにする
      for (ShippingContainer sc : container.getShippings()) {
        ShippingHeader sh = sc.getShippingHeader();
        Long orgShippingNo = sh.getOriginalShippingNo();
        if (orgShippingNo != null && sh.getOriginalShippingNo().equals(NumUtil.toLong(header.getShippingNo()))) {
          deleteButtonDisplay &= false;
        }
      }
      headerBean.setDeleteButtonDisplay(deleteButtonDisplay);
      shippingBeanList.add(headerBean);
    }
    orderBean.setShippingList(shippingBeanList);

    return orderBean;
  }

  /**
   * ショップコードとSKUコードが一致する受注明細情報を返す
   * 
   * @param detailList
   * @param shopCode
   * @param skuCode
   * @return 受注明細
   */
  private OrderDetail getOrderDetail(List<OrderDetail> detailList, String shopCode, String skuCode) {
    for (OrderDetail detail : detailList) {
      if (detail != null && detail.getShopCode().equals(shopCode) && detail.getSkuCode().equals(skuCode)) {
        return detail;
      }
    }
    return null;
  }

  private List<CodeAttribute> getShopList(List<ShippingBean> shippingList) {
    List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();
    Map<String, String> shopListMap = new HashMap<String, String>();

    if (getLoginInfo().isShop()) {
      shopList.add(new NameValue(getLoginInfo().getShopName(), getLoginInfo().getShopCode()));
      return shopList;
    }

    for (ShippingBean shipping : shippingList) {
      if (shopListMap.get(shipping.getShopCode()) == null) {
        shopList.add(new NameValue(shipping.getShopName(), shipping.getShopCode()));
        shopListMap.put(shipping.getShopCode(), shipping.getShopName());
      }
    }

    return shopList;
  }

  /**
   * 返品できる商品の一覧情報を取得<BR>
   * 取得する情報は、受注明細に登録されている商品の<BR>
   * name = ショップ名：SKUコード：商品名(規格詳細1名称/規格詳細2名称)<BR>
   * value = ショップコード＋SKUコード<BR>
   * に設定されたNameValueのリスト<BR>
   * デフォルト値として<BR>
   * name = 「商品なし返品・金額調整」<BR>
   * value = 0<BR>
   * を先頭に設定
   * 
   * @param container
   * @return ショップ名：SKUコード：商品名(規格詳細1名称/規格詳細2名称)とショップコード＋SKUコードのNameValueリスト
   */
  private List<CodeAttribute> getSkuList(List<ShippingBean> shippingList) {
    List<CodeAttribute> skuList = new ArrayList<CodeAttribute>();
    NameValue noReturnSku = new NameValue(Messages.getString("web.action.back.order.OrderReturnInitAction.1"), "0");
    skuList.add(noReturnSku);
    for (ShippingBean shipping : shippingList) {
      if (isNotUsableShopCode(shipping.getShopCode())) {
        continue;
      }
      if (!shipping.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
        continue;
      }
      if (shipping.isReturnItem()) {
        continue;
      }
      for (ShippingDetailBean detail : shipping.getShippingDetailList()) {

        // 商品なし返品の情報
        if (StringUtil.isNullOrEmpty(detail.getSkuCode())) {
          continue;
        }
        String detailCommodityName = detail.getComodityName();
        if (detailCommodityName.length() > 15) {
          detailCommodityName = detailCommodityName.substring(0, 15) + "...";
        }
        String shopName = getShopName(shipping.getShopCode())
        + Messages.getString("web.action.back.order.OrderReturnInitAction.2");;
        if (DIContainer.getWebshopConfig().getOperatingMode() == OperatingMode.ONE) {
          shopName = "";
        }
        String name = shipping.getShippingNo()
        + Messages.getString("web.action.back.order.OrderReturnInitAction.2") + shopName + detail.getSkuCode()
        + Messages.getString("web.action.back.order.OrderReturnInitAction.2") + detailCommodityName
        + detail.getStandardDetailName();

        NameValue value = new NameValue(name, detail.getShippingDetailBeanNo());
        skuList.add(value);

      }
    }

    return skuList;
  }

  /**
   * 規格詳細名称を生成する<BR>
   * 規格詳細１名称と規格詳細２名称両方存在する場合<BR>
   * (規格詳細１名称/規格詳細２名称)<BR>
   * 規格詳細１名称のみ存在する場合<BR>
   * (規格詳細１名称)<BR>
   * それ以外の場合空文字を返す
   * 
   * @param detail1Name
   * @param detail2Name
   * @return 規格詳細名称
   */
  private String createStandardDetailName(String detail1Name, String detail2Name) {
    String detail = "";
    if (StringUtil.hasValue(detail1Name) && StringUtil.hasValue(detail2Name)) {
      if (detail1Name.length() > 10) {
        detail1Name = detail1Name.substring(0, 10) + "...";
      }
      if (detail1Name.length() > 10) {
        detail2Name = detail2Name.substring(0, 10) + "...";
      }
      detail = "(" + detail1Name + "/" + detail2Name + ")";
    } else if (StringUtil.hasValue(detail1Name) && StringUtil.isNullOrEmpty(detail2Name)) {
      if (detail1Name.length() > 10) {
        detail1Name = detail1Name.substring(0, 10) + "...";
      }
      detail = "(" + detail1Name + ")";
    } else {
      detail = "";
    }
    return detail;
  }

  /**
   * ショップコードからショップ名を取得する<BR>
   * 取得したショップ名はマップに入れておきDBへの連続アクセスを回避する<BR>
   * 
   * @param shopCode
   * @return ショップ名
   */
  private String getShopName(String shopCode) {
    String shopName = shopNameMap.get(shopCode);
    if (StringUtil.isNullOrEmpty(shopName)) {
      ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
      Shop shop = shopService.getShop(shopCode);
      if (shop != null) {
        shopName = shop.getShopName();
      } else {
        shopName = "";
      }
      shopNameMap.put(shopCode, shopName);
    }
    return shopName;
  }

  /**
   * 出荷合計金額を合計する
   * 
   * @param detail
   * @return 出荷合計金額
   */
  private String totalPrice(ShippingDetail detail) {
    //modify by V10-CH 170 start
    //Long total = (detail.getRetailPrice() + detail.getGiftPrice()) * detail.getPurchasingAmount();
    BigDecimal total = BigDecimalUtil.multiply(detail.getRetailPrice().add(detail.getGiftPrice()), detail.getPurchasingAmount());
    //modify by V10-CH 170 end
    String sTotal = NumUtil.toString(total);

    return sTotal;
  }

  /**
   * 権限・モード等により使用できるショップコードか判断する<BR>
   * サイト管理者の場合全ショップの情報にアクセス可<BR>
   * ショップ管理者の場合自ショップの情報にアクセス可
   * 
   * @param shopCode
   * @return 該当ショップコードにアクセスできればtrue
   */
  private boolean isNotUsableShopCode(String shopCode) {
    BackLoginInfo login = getLoginInfo();
    boolean usable = true;
    if (StringUtil.isNullOrEmpty(shopCode)) {
      usable = true;
    } else if (login.isSite()) {
      usable = false;
    } else if (login.isShop() && login.getShopCode().equals(shopCode)) {
      usable = false;
    } else {
      usable = true;
    }
    return usable;
  }

  private String getPathInfo(int index) {
    String[] argsTmp = getRequestParameter().getPathArgs();
    if (argsTmp.length > index) {
      return argsTmp[index];
    }
    return "";
  }

  private static class ShippingContainerComparator implements Serializable, Comparator<ShippingContainer> {

    private static final long serialVersionUID = 1L;

    /**
     * 出荷情報のソート用に比較を行います。
     * 
     * @param shippingNo1
     * @param shippingNo2
     * @return 処理結果
     */
    public int compare(ShippingContainer shippingNo1, ShippingContainer shippingNo2) {
      return shippingNo1.getShippingHeader().getShippingNo().compareTo(shippingNo2.getShippingHeader().getShippingNo());
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderReturnInitAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102026004";
  }

}
