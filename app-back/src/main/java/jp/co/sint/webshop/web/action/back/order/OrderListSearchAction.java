package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.AllOrderType;
import jp.co.sint.webshop.data.domain.MobileComputerType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.JdService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.order.OrderHeadline;
import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.service.order.OrderPaymentInfo;
import jp.co.sint.webshop.service.tmall.TmallService;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderListBean;
import jp.co.sint.webshop.web.bean.back.order.OrderListBean.OrderSearchedBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020210:受注入金管理のアクションクラスです。

 * 
 * @author System Integrator Corp.
 */
public class OrderListSearchAction extends WebBackAction<OrderListBean> {

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

    OrderListBean bean = getBean();

    List<CodeAttribute> paymentMethodList = new ArrayList<CodeAttribute>();
    paymentMethodList.add(new NameValue("-----", ""));
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    List<PaymentMethod> paymentMethod = service.getAllPaymentMethodList(getLoginInfo().getShopCode());
    for (PaymentMethod payment : paymentMethod) {
      CodeAttribute addPayment = new NameValue(payment.getPaymentMethodName(), payment.getPaymentMethodNo().toString());
      paymentMethodList.add(addPayment);
    }

    bean.setPaymentMethodList(paymentMethodList);

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
            Messages.getString("web.action.back.order.OrderListSearchAction.0")));
        authorization = false;
      }
    }
    if (!getBean().getSearchFromOrderDatetime().equals("") && !getBean().getSearchToOrderDatetime().equals("")) {
      if (DateUtil.fromString(getBean().getSearchFromOrderDatetime()).after(
          DateUtil.fromString(getBean().getSearchToOrderDatetime()))) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM,
            Messages.getString("web.action.back.order.OrderListSearchAction.1")));
        authorization = false;
      }
    }
    if (!getBean().getSearchFromPaymentDatetime().equals("") && !getBean().getSearchToPaymentDatetime().equals("")) {
      if (DateUtil.fromString(getBean().getSearchFromPaymentDatetime()).after(
          DateUtil.fromString(getBean().getSearchToPaymentDatetime()))) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM,
            Messages.getString("web.action.back.order.OrderListSearchAction.2")));
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
    TmallService tmallService = ServiceLocator.getTmallService(getLoginInfo());
    JdService jdService = ServiceLocator.getJdService(getLoginInfo());
    OrderListBean bean = getBean();
    //获取子订单编号
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    String childid = bean.getChildOrderNo();
    Long childorder = service.getCustomerCode(childid);
    
    // 検索条件作成
    condition = new OrderListSearchCondition();
    //京东订单交易号
    if(!bean.getChildOrderNo().equals("")){
      if(childorder!=null){
        condition.setSearchFromJdDid(childorder.toString()); 
      }else{
        condition.setSearchFromJdDid("132465789");
      }  
    }else{
      condition.setSearchFromJdDid(bean.getSearchFromJdDid());
    }
    
    
    
    condition.setShopCode(getLoginInfo().getShopCode());
    condition.setCustomerName(StringUtil.parse(bean.getSearchCustomerName()));
    condition.setCustomerNameKana(bean.getSearchCustomerNameKana());
    condition.setOrderNoStart(bean.getSearchFromOrderNo());
    condition.setOrderNoEnd(bean.getSearchToOrderNo());
    condition.setEmail(bean.getSearchEmail());
    condition.setTel(bean.getSearchTel());
    //Add by V10-CH start
    condition.setMobileTel(bean.getSearchMobile());
    //Add by V10-CH end
    condition.setOrderDatetimeStart(bean.getSearchFromOrderDatetime());
    condition.setOrderDatetimeEnd(bean.getSearchToOrderDatetime());
    // soukai add 2011/12/29 ob start
    // 受注タイプ(EC/TMALL)
    
    if(bean.getOrderType().equals(MobileComputerType.Mobile.getValue())){
      condition.setSearchOrderType(OrderType.EC.getValue());
      condition.setSearchMobileComputerType(MobileComputerType.Mobile.getValue());
    }else if(bean.getOrderType().equals(OrderType.EC.getValue()) || bean.getOrderType().equals(OrderType.TMALL.getValue()) ){
      condition.setSearchOrderType(bean.getOrderType());
      condition.setSearchMobileComputerType(MobileComputerType.PC.getValue());
    }else{
      condition.setSearchOrderType(bean.getOrderType());
    }
    condition.setSearchOrderClientType(bean.getOrderClientType());
    // 検査フラグ
    condition.setSearchOrderFlg(bean.getOrderFlg());
  
    condition.setSearchLanguageCode(bean.getLanguageCode());
    // soukai add 2011/12/29 ob end
    String[] orderStatusArray = bean.getSearchOrderStatus().toArray(new String[bean.getSearchOrderStatus().size()]);
    condition.setOrderStatus(orderStatusArray);
    // upd by lc 2012-04-12 start
    if (!(bean.getSearchShippingStatusSummary().size() > 0 && bean.getSearchShippingStatusSummary().get(0).equals(""))){
      String[] shippingStatusSummaryArray = bean.getSearchShippingStatusSummary().toArray(
          new String[bean.getSearchShippingStatusSummary().size()]);
      condition.setShippingStatusSummary(shippingStatusSummaryArray);
    }
    // upd by lc 2012-04-12 end
    String[] returnStatusSummaryArray = bean.getSearchReturnStatusSummary().toArray(
        new String[bean.getSearchReturnStatusSummary().size()]);
    condition.setReturnStatusSummary(returnStatusSummaryArray);
    // add by lc 2012-04-09 start
    condition.setSearchFromTmallTid(bean.getSearchFromTmallTid());
  
    // add by lc 2012-04-09 end
    condition.setPaymentDatetimeStart(bean.getSearchFromPaymentDatetime());
    condition.setPaymentDatetimeEnd(bean.getSearchToPaymentDatetime());
    condition.setPaymentMethod(bean.getSearchPaymentMethod());
    condition.setPaymentStatus(bean.getSearchPaymentStatus());
    condition.setSearchPaymentLimitOver(bean.isSearchPaymentLimitOver());
    condition.setPaymentLimitDays(bean.getPaymentLimitDays());
    condition.setSearchWithSentPaymentReminderMail(bean.isWithSentPaymentReminder());
    condition.setSearchFixedSalesDataFlg(bean.getSearchFixedSalesDataFlg());
    condition.setSearchDataTransportFlg(bean.getSearchDataTransportFlg());
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      condition.setSiteAdmin(true);
    } else {
      condition.setSiteAdmin(getLoginInfo().isSite());
    }

    if (bean.isOrderByOrderNo()) {
      condition.setSearchListSort("orderNo");
    }
    if (bean.isOrderByCustomerName()) {
      condition.setSearchListSort("customerName");
    }
    if (bean.isOrderByOrderDatetime()) {
      condition.setSearchListSort("orderDatetime");
    }
    if (bean.isOrderByPaymentDatetime()) {
      condition.setSearchListSort("paymentDatetime");
    }
   
    condition = getCondition();

    // 検索実行
  //  OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    JdService jdservice = ServiceLocator.getJdService(getLoginInfo());
    SearchResult<OrderHeadline> result = service.searchOrderList(condition);
    //BigDecimal jdPurchasingPriceTotal = new BigDecimal("0.00");
    
    //soukai add 2012/01/14 ob start
    bean.setPurchasingPriceTotal(service.getTotalOrderPrice(condition).toString());
    //soukai add 2012/01/14 ob end
    
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
      row.setTmallTid(orderHeadline.getTmallTid());
      // soukai add 2011/12/29 ob start
      // 受注タイプ(名称)
      if(orderHeadline.getMobileComputerType().equals(MobileComputerType.Mobile.getValue())){
        row.setOrderType(MobileComputerType.Mobile.getName());
      }else{
        if(orderHeadline.getOrderType().equals(OrderType.EC.getValue())){
          row.setOrderType(OrderType.fromValue(orderHeadline.getOrderType()).getName()+"[PC]");
        }else if (orderHeadline.getOrderType().equals(AllOrderType.JD.getValue())) {
          row.setOrderType(AllOrderType.fromValue(orderHeadline.getOrderType()).getName());
        }else{
          row.setOrderType(OrderType.fromValue(orderHeadline.getOrderType()).getName());
        }
      }
      
      row.setOrderFlgName(OrderFlg.fromValue(orderHeadline.getOrderFlg()).getName());
      // soukai add 2011/12/29 ob end
     
      OrderPaymentInfo paymentInfo = paymentInfoMap.get(orderHeadline.getOrderNo());
      if (paymentInfo != null) {
        row.setSentPaymentReceivedMailList(paymentInfo.getReceivedMailSentDateList());
        row.setSentPaymentReminderMailList(paymentInfo.getReminderMailSentDateList());
      }

//      Long paymentCommission = NumUtil.toPrimitiveLong(orderHeadline.getPaymentCommission(), 0);
//      String totalOrderPrice = NumUtil.toString(NumUtil.toLong(orderHeadline.getTotalAmount()) + paymentCommission);
      // upd by lc 2012-03-30 start
      BigDecimal paymentCommission = NumUtil.coalesce(orderHeadline.getPaymentCommission(), BigDecimal.ZERO);
      String totalOrderPrice = NumUtil.toString(BigDecimalUtil
          .add(NumUtil.parse(orderHeadline.getTotalAmount()), paymentCommission));
      
      if ( orderHeadline.getOrderType().equals(OrderType.EC.getValue())) {
        
        // soukai add 2012/01/31 ob start
        if (StringUtil.hasValue(orderHeadline.getDiscountPrice())) {
          totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(new BigDecimal(orderHeadline.getDiscountPrice()))).toString();
        }
        // soukai add 2012/01/31 ob end
      } else if (orderHeadline.getOrderType().equals(AllOrderType.JD.getValue())) {
        jdOrderHeader = jdservice.getJdOrderHeader(orderHeadline.getOrderNo());
        // 京东订单交易号
        row.setTmallTid(jdOrderHeader.getJdOrderNo());

        if (StringUtil.hasValue(orderHeadline.getDiscountPrice())) {
          totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(new BigDecimal(orderHeadline.getDiscountPrice()))).toString();
        }
        
        if (jdOrderHeader.getDiscountPrice() != null) {
          totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(jdOrderHeader.getDiscountPrice())).toString();
        }
        
        if (jdOrderHeader.getPaidPrice() != null) {
          totalOrderPrice = jdOrderHeader.getPaidPrice().toString();
        }
        
//        jdPurchasingPriceTotal = jdPurchasingPriceTotal.add(jdOrderHeader.getPaidPrice());
        
      } else if (orderHeadline.getOrderType().equals(OrderType.TMALL.getValue())) {
         tmallOrderHeader = service.getTmallOrderHeader(orderHeadline.getOrderNo());
         // 淘宝订单交易号
         row.setTmallTid(tmallOrderHeader.getTmallTid());
         
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
      
      row.setPurchasingPrice(totalOrderPrice);
      // upd by lc 2012-03-30 end
      
      // 计算每个订单总重量
      String orderType = orderHeadline.getOrderNo().substring(0, 1);
      
      if ((!"T".equals(orderType)) && (!"D".equals(orderType))) {
        BigDecimal totalWeight = new BigDecimal(0);
        List<OrderDetail> commodityWeightList = service.getOrderDetailCommodityListWeight(orderHeadline.getOrderNo(), DIContainer.getWebshopConfig().getSiteShopCode());
        for(OrderDetail orderDetail : commodityWeightList) {
          if(orderDetail.getCommodityWeight() != null) {
            totalWeight = totalWeight.add(orderDetail.getCommodityWeight());
          }
        }
        row.setTotalWeight(totalWeight.toString());
      } else if("T".equals(orderType)) {
        BigDecimal totalWeight = new BigDecimal(0);
        List<TmallOrderDetail> tmallCommodityList = tmallService.getTmallOrderDetailCommodityList(tmallOrderHeader.getOrderNo(), DIContainer.getWebshopConfig().getSiteShopCode());
        for(TmallOrderDetail tmallOrderDetail : tmallCommodityList) {
          CommodityDetail commodityDetail = service.getCommodityDetail(tmallOrderDetail.getCommodityCode(), DIContainer.getWebshopConfig().getSiteShopCode());
          if(commodityDetail != null && commodityDetail.getWeight() != null) {
            totalWeight = totalWeight.add(commodityDetail.getWeight());
          }
        }
        row.setTotalWeight(totalWeight.toString());
      } else {
        BigDecimal totalWeight = new BigDecimal(0);
        List<JdOrderDetail> jdCommodityList = jdService.getJdOrderDetailCommodityList(jdOrderHeader.getOrderNo(), DIContainer.getWebshopConfig().getSiteShopCode());
        for(JdOrderDetail jdOrderDetail : jdCommodityList) {
          CommodityDetail commodityDetail = service.getCommodityDetail(jdOrderDetail.getCommodityCode(), DIContainer.getWebshopConfig().getSiteShopCode());
          if(commodityDetail != null && commodityDetail.getWeight() != null) {
            totalWeight = totalWeight.add(commodityDetail.getWeight());
          }
        }
        row.setTotalWeight(totalWeight.toString()); 
      }
      
      
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

      detailList.add(row);
    }
    bean.setOrderSearchedList(detailList);

    //订单类别为京东
//    if (bean.getOrderType().equals("3")) {
//      bean.setPurchasingPriceTotal(jdPurchasingPriceTotal.toString());
//    }
    
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。

   */
  @Override
  public void prerender() {
    OrderListBean bean = (OrderListBean) getRequestBean();

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
            Messages.getString("web.action.back.order.OrderListSearchAction.2")));
      }
      if (completeParam.equals(WebConstantCode.COMPLETE_DELETE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
            Messages.getString("web.action.back.order.OrderListSearchAction.2")));
      }
      if (getRequestParameter().getPathArgs()[0].equals("mail_confirm")) {
        addInformationMessage(WebMessage.get(CompleteMessage.SEND_CONFIRM_MAIL_COMPLETE));
      } else if (getRequestParameter().getPathArgs()[0].equals("mail_remind")) {
        addInformationMessage(WebMessage.get(CompleteMessage.SEND_REMIND_MAIL_COMPLETE));
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
    return Messages.getString("web.action.back.order.OrderListSearchAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021006";
  }

}
