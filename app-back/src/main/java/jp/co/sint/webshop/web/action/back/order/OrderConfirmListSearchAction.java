package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.order.OrderHeadline;
import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.service.order.OrderPaymentInfo;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderConfirmListBean;
import jp.co.sint.webshop.web.bean.back.order.OrderConfirmListBean.OrderSearchedBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020510:受注確認管理のアクションクラスです。

 * 
 * @author System Integrator Corp.
 */
public class OrderConfirmListSearchAction extends WebBackAction<OrderConfirmListBean> {

  private OrderListSearchCondition condition;

  protected OrderListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected OrderListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * 初期処理を実行します。

   */
  @Override
  public void init() {

    OrderConfirmListBean bean = getBean();

    setRequestBean(bean);
  }

  /**
   * beanのcreateAttributeを実行します。

   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    if (getRequestParameter().getPathArgs().length > 0) {
      return false;
    }
    return true;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。

   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.ORDER_READ_SHOP) || getLoginInfo().hasPermission(Permission.ORDER_READ_SITE);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。

   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    ValidationSummary summary = BeanValidator.validate(getBean());
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
      return summary.isValid();
    }

    boolean authorization = true;

    // 個別チェック開始
    if (!getBean().getSearchFromOrderNo().equals("") && !getBean().getSearchToOrderNo().equals("")) {
      if (NumUtil.toLong(getBean().getSearchFromOrderNo()) > NumUtil.toLong(getBean().getSearchToOrderNo())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM,
            Messages.getString("web.action.back.order.OrderConfirmListSearchAction.0")));
        authorization = false;
      }
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

    OrderConfirmListBean bean = getBean();

    // 検索条件作成
    condition = new OrderListSearchCondition();
    condition.setShopCode(getLoginInfo().getShopCode());
    condition.setOrderNoStart(bean.getSearchFromOrderNo());
    condition.setOrderNoEnd(bean.getSearchToOrderNo());
    // 受注タイプ(EC/TMALL)
    condition.setSearchOrderType(bean.getOrderType());
    // 検査フラグ
    condition.setSearchOrderFlg(OrderFlg.NOT_CHECKED.getValue());
    
    condition.setContentFlg(bean.getContentFlg());
    
    condition.setConfirmFlg(bean.getConfirmFlg());
    
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      condition.setSiteAdmin(true);
    } else {
      condition.setSiteAdmin(getLoginInfo().isSite());
    }

    if (bean.isOrderByOrderNo()) {
      condition.setSearchListSort("orderNo");
    }
    String[] orderStatusArray = new String[]{"1"};
    condition.setOrderStatus(orderStatusArray);
    
    String[] shippingStatusSummaryArray = new String[]{"0","1","2","3"};
    condition.setShippingStatusSummary(shippingStatusSummaryArray);
    
    String[] returnStatusSummaryArray = new String[]{"0","1","2"};
    condition.setReturnStatusSummary(returnStatusSummaryArray);
    //add by lc 2012-04-13 start
    condition.setSearchUnpaymentFlg(true);
    //add by lc 2012-04-13 end    
    condition = getCondition();

    // 検索実行
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    SearchResult<OrderHeadline> result = service.searchOrderList(condition);
    if (result.getRowCount() == 0) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    } else if (result.isOverflow()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber("" + result.getRowCount()),
          "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }
    bean.setPagerValue(PagerUtil.createValue(result));

    // リスト生成

    List<OrderSearchedBean> detailList = new ArrayList<OrderSearchedBean>();
    List<String> orderNoList = new ArrayList<String>();
    for (OrderHeadline ln : result.getRows()) {
      orderNoList.add(ln.getOrderNo());
    }
    Map<String, OrderPaymentInfo> paymentInfoMap = service.getOrderPaymentInfo(orderNoList);

    //soukai upd 2011/12/29 ob start
    BigDecimal lngPurchasingPriceTotal = BigDecimal.ZERO;
    TmallOrderHeader tmallOrderHeader = new TmallOrderHeader();
    JdOrderHeader jdOrderHeader = new JdOrderHeader();
    for (OrderHeadline orderHeadline : result.getRows()) {
      OrderSearchedBean row = new OrderSearchedBean();
      row.setOrderNo(orderHeadline.getOrderNo());
      row.setCustomerName(orderHeadline.getCustomerName());
      row.setMessage(orderHeadline.getMessage());
      row.setCaution(orderHeadline.getCaution());
      row.setTel(orderHeadline.getPhoneNumber());
      //Add by V10-CH start
      row.setMobileTel(orderHeadline.getMobileNumber());
      //Add by V10-CH end
      row.setOrderDatetime(orderHeadline.getOrderDatetime());
      row.setPaymentDate(orderHeadline.getPaymentDate());
      row.setPaymentMethod(orderHeadline.getPaymentMethodName());
      row.setGuestFlg(orderHeadline.getGuestFlg());
      row.setPaymentLimitDate(DateUtil.toDateString(orderHeadline.getPaymentLimitDate()));
      
      // soukai add 2011/12/29 ob start
      // 受注タイプ(名称)
      row.setOrderType(OrderType.fromValue(orderHeadline.getOrderType()).getName());
      row.setOrderFlgName(OrderFlg.fromValue(orderHeadline.getOrderFlg()).getName());
      // soukai add 2011/12/29 ob end
     
      OrderPaymentInfo paymentInfo = paymentInfoMap.get(orderHeadline.getOrderNo());
      if (paymentInfo != null) {
        row.setSentPaymentReceivedMailList(paymentInfo.getReceivedMailSentDateList());
        row.setSentPaymentReminderMailList(paymentInfo.getReminderMailSentDateList());
      }

//      Long paymentCommission = NumUtil.toPrimitiveLong(orderHeadline.getPaymentCommission(), 0);
//      String totalOrderPrice = NumUtil.toString(NumUtil.toLong(orderHeadline.getTotalAmount()) + paymentCommission);
      // upd by lc 2012-04-25 start
      BigDecimal paymentCommission = NumUtil.coalesce(orderHeadline.getPaymentCommission(), BigDecimal.ZERO);
      String totalOrderPrice = NumUtil.toString(BigDecimalUtil.add(NumUtil.parse(orderHeadline.getTotalAmount()), paymentCommission));
      
      if (orderHeadline.getOrderType().equals(OrderType.EC.getValue())) {
        
        // soukai add 2012/01/31 ob start
        if (StringUtil.hasValue(orderHeadline.getDiscountPrice())) {
          totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(new BigDecimal(orderHeadline.getDiscountPrice()))).toString();
        }
        // soukai add 2012/01/31 ob end
        // 如果订单类型为JD的话
      } else if (orderHeadline.getOrderType().equals(OrderType.JD.getValue())) {
        jdOrderHeader = service.getJdOrderHeader(orderHeadline.getOrderNo());
        
        if (StringUtil.hasValue(orderHeadline.getDiscountPrice())) {
          totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(new BigDecimal(orderHeadline.getDiscountPrice()))).toString();
        }
        
        if (jdOrderHeader.getDiscountPrice() != null) {
          totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(jdOrderHeader.getDiscountPrice())).toString();
        }

      }
        // 如果订单类型为TMALL的话
      else {
         tmallOrderHeader = service.getTmallOrderHeader(orderHeadline.getOrderNo());
         // 淘宝订单交易号
         //row.setTmallTid(tmallOrderHeader.getTmallTid());
         
         if (StringUtil.hasValue(orderHeadline.getDiscountPrice())) {
           totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(new BigDecimal(orderHeadline.getDiscountPrice()))).toString();
         }
         
         if (tmallOrderHeader.getTmallDiscountPrice() != null) {
           totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(tmallOrderHeader.getTmallDiscountPrice())).toString();
         }
         
         if (tmallOrderHeader.getPointConvertPrice() != null) {
           totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(tmallOrderHeader.getPointConvertPrice())).toString();
         }
         
         if (tmallOrderHeader.getAdjustFee() != null) {
           totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(tmallOrderHeader.getAdjustFee())).toString();
         }
         
         if (tmallOrderHeader.getMjsDiscount() != null) {
           totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(tmallOrderHeader.getMjsDiscount())).toString();
         }
      }
      // upd by lc 2012-04-25 start      
      row.setPurchasingPrice(totalOrderPrice);
      row.setUpdatedDatetime(orderHeadline.getUpdatedDatetime());

      if (StringUtil.hasValue(orderHeadline.getCaution()) || StringUtil.hasValue(orderHeadline.getMessage())) {
        row.setMemo(true);
      }
      CodeAttribute orderStatus = OrderStatus.fromValue(orderHeadline.getOrderStatus());
      if (orderStatus != null) {
        String x = orderStatus.getName();
        row.setOrderStatus(x);
      }

      CodeAttribute shippingStatusSummary = ShippingStatusSummary.fromValue(orderHeadline.getShippingStatusSummary());
      if (shippingStatusSummary != null) {
        String x = shippingStatusSummary.getName();
        row.setShippingStatusSummary(x);
      }

      CodeAttribute returnStatusSummary = ReturnStatusSummary.fromValue(orderHeadline.getReturnStatusSummary());
      if (returnStatusSummary != null) {
        String x = returnStatusSummary.getName();
        row.setReturnStatusSummary(x);
      }
      row.setTmallBuyerMessage(orderHeadline.getTmallBuyerMessage());
      detailList.add(row);
    }
    bean.setPurchasingPriceTotal(NumUtil.toString(lngPurchasingPriceTotal));//订单总合计金额
    //soukai upd 2011/12/29 ob end
    bean.setOrderSearchedList(detailList);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。

   */
  @Override
  public void prerender() {
    OrderConfirmListBean bean = (OrderConfirmListBean) getRequestBean();

    if (Permission.ORDER_READ_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_READ_SITE.isGranted(getLoginInfo())) {
      bean.setAuthorityRead(true);
    }
    if (Permission.ORDER_UPDATE_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo())) {
      bean.setAuthorityUpdate(true);
    }
    if (Permission.ORDER_MODIFY_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_MODIFY_SITE.isGranted(getLoginInfo())) {
      bean.setAuthorityDelete(true);
    }
    if (Permission.ORDER_DATA_IO_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_DATA_IO_SITE.isGranted(getLoginInfo())) {
      bean.setAuthorityIO(true);
    }

    if (getRequestParameter().getPathArgs().length > 0) {
      String completeParam = getRequestParameter().getPathArgs()[0];
      if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
            Messages.getString("web.action.back.order.OrderConfirmListSearchAction.4")));
      }
    }

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderConfirmListSearchAction.3");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021009";
  }

}
