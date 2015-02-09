package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.domain.MobileComputerType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.service.JdService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.OrderListExportCondition;
import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1020210:受注入金管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderListExportAction extends WebBackAction<OrderListBean> implements WebExportAction {

  private OrderListSearchCondition condition;

  protected OrderListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected OrderListSearchCondition getSearchCondition() {
    return this.condition;
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
    return getLoginInfo().hasPermission(Permission.ORDER_DATA_IO_SITE)
        || getLoginInfo().hasPermission(Permission.ORDER_DATA_IO_SHOP);
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
//    10.1.6 10255 修正 ここから
      // if (Integer.parseInt(getBean().getSearchFromOrderNo()) > Integer.parseInt(getBean().getSearchToOrderNo())) {
      //   addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
      if (NumUtil.toLong(getBean().getSearchFromOrderNo()).longValue() > NumUtil.toLong(getBean().getSearchToOrderNo()).longValue()) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, Messages.getString("web.action.back.order.OrderListExportAction.2")));
      // 10.1.6 10255 修正 ここまで
        authorization = false;
      }
    }
    if (!getBean().getSearchFromOrderDatetime().equals("") && !getBean().getSearchToOrderDatetime().equals("")) {
      if (DateUtil.fromString(getBean().getSearchFromOrderDatetime()).after(
          DateUtil.fromString(getBean().getSearchToOrderDatetime()))) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        authorization = false;
      }
    }
    if (!getBean().getSearchFromPaymentDatetime().equals("") && !getBean().getSearchToPaymentDatetime().equals("")) {
      if (DateUtil.fromString(getBean().getSearchFromPaymentDatetime()).after(
          DateUtil.fromString(getBean().getSearchToPaymentDatetime()))) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        authorization = false;
      }
    }
    if (getBean().getSearchOrderStatus().get(0).equals("")) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.order.OrderListExportAction.0")));
      authorization = false;
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

    OrderListBean bean = getBean();
    
    JdService jdService = ServiceLocator.getJdService(getLoginInfo());
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
    //受注名牌名称商店 ec/tmall
    if(bean.getOrderType().equals(MobileComputerType.Mobile.getValue())){
      condition.setSearchOrderType(OrderType.EC.getValue());
      condition.setSearchMobileComputerType(MobileComputerType.Mobile.getValue());
    }else if(bean.getOrderType().equals(OrderType.EC.getValue()) || bean.getOrderType().equals(OrderType.TMALL.getValue()) ){
      condition.setSearchOrderType(bean.getOrderType());
      condition.setSearchMobileComputerType(MobileComputerType.PC.getValue());
    }else{
      condition.setSearchOrderType(bean.getOrderType());
     // condition.setSearchOrderType(bean.getOrderType());
    }
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
   // condition.setSearchFromJdDid(bean.getSearchFromJdDid());
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
    condition.setSearchOrderClientType(bean.getOrderClientType());
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
    OrderListExportCondition orderCondition = CsvExportType.EXPORT_CSV_ORDER_LIST.createConditionInstance();
    orderCondition.setCondition(condition);
    this.exportCondition = orderCondition;
    setRequestBean(bean);
    setNextUrl("/download");
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

    setRequestBean(bean);
  }

  private CsvExportCondition<? extends CsvSchema> exportCondition;

  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return exportCondition;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderListExportAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021001";
  }

}
