package jp.co.sint.webshop.web.action.back.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CouponUseStatus;
import jp.co.sint.webshop.data.domain.CustomerKbn;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.InquiryStatus;
import jp.co.sint.webshop.data.domain.InquiryWay;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.CustomerGroup;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.customer.MemberCouponHistory;
import jp.co.sint.webshop.service.customer.MemberInquiryHistory;
import jp.co.sint.webshop.service.customer.MemberSearchCondition;
import jp.co.sint.webshop.service.customer.MemberShippingHistory;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.MemberInfoBean;
import jp.co.sint.webshop.web.bean.back.service.MemberInfoBean.CouponSearchedBean;
import jp.co.sint.webshop.web.bean.back.service.MemberInfoBean.CustomerSearchedBean;
import jp.co.sint.webshop.web.bean.back.service.MemberInfoBean.InquirySearchedBean;
import jp.co.sint.webshop.web.bean.back.service.MemberInfoBean.ShippingSearchedBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

public class MemberInfoHistoryAction extends WebBackAction<MemberInfoBean> {

  public static String SHIPPING_MODE = "shipping";

  public static String INQUIRY_MODE = "inquiry";

  public static String COUPON_MODE = "coupon";

  private MemberSearchCondition condition;

  protected MemberSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected MemberSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    getBean().setShippingList(new ArrayList<ShippingSearchedBean>());
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.SERVICE_USER_DATA_READ);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (StringUtil.hasValue(getBean().getCustomerInfo().getCustomerCode())) {
      return true;
    }
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    MemberInfoBean bean = getBean();
    String targetMode = targetMode();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    String customerCode = bean.getCustomerInfo().getCustomerCode();

    // 会员存在验证
    if (StringUtil.isNullOrEmpty(customerCode) || service.isNotFound(customerCode)) {
      setNextUrl("/app/service/member_info/init/delete");
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 会员信息设定
    CustomerInfo customerInfo = service.getCustomer(customerCode);
    if (customerInfo.getCustomer() == null) {
      setNextUrl("/app/service/member_info/init/delete");
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    CustomerSearchedBean customerSearchedBean = new CustomerSearchedBean();
    customerSearchedBean.setCustomerCode(customerInfo.getCustomer().getCustomerCode());
    customerSearchedBean.setCustomerName(customerInfo.getCustomer().getLastName());
    CodeAttribute customerStatus = CustomerStatus.fromValue(customerInfo.getCustomer().getCustomerStatus());
    if (customerStatus != null) {
      customerSearchedBean.setCustomerStatus(customerStatus.getName());
    }
    CodeAttribute sex = Sex.fromValue(customerInfo.getCustomer().getSex());
    if (sex != null) {
      customerSearchedBean.setSex(sex.getName());
    }
    // 20120327 ysy add start

    if (customerInfo.getCustomer().getCustomerKbn() != null) {
      CodeAttribute kbn = CustomerKbn.fromValue(customerInfo.getCustomer().getCustomerKbn());
      if (kbn.getValue().equals("1")) {
        customerSearchedBean.setCustomerKbn("是");
      } else {
        customerSearchedBean.setCustomerKbn("否");
      }
    } else {
      customerSearchedBean.setCustomerKbn("否");
    }
    CustomerGroup customerGroup = service.getCustomerGroup(customerInfo.getCustomer().getCustomerGroupCode());
    if (customerGroup.getCustomerGroupCode() != null) {
      customerSearchedBean.setCustomerGroupCode(customerGroup.getCustomerGroupName());
    }
    // 20120327 ysy add end
    // soukai update 2012/02/01 ob start
    customerSearchedBean.setEmail(customerInfo.getCustomer().getEmail());
    // customerSearchedBean.setPostCode(customerInfo.getAddress().getPostalCode());
    // customerSearchedBean.setAddress(customerInfo.getAddress().getAddress1() +
    // customerInfo.getAddress().getAddress2()
    // + customerInfo.getAddress().getAddress3() +
    // customerInfo.getAddress().getAddress4());

    // customerSearchedBean.setMobile(customerInfo.getAddress().getMobileNumber());
    // customerSearchedBean.setTel(customerInfo.getAddress().getPhoneNumber());
    // soukai update 2012/02/01 ob end
    customerSearchedBean.setCaution(customerInfo.getCustomer().getCaution());
    bean.setCustomerInfo(customerSearchedBean);

    if (StringUtil.isNullOrEmpty(targetMode)) {
      targetMode = bean.getDisplayHistoryMode();
    }

    condition = new MemberSearchCondition();
    condition.setSearchCustomerCode(bean.getCustomerInfo().getCustomerCode());
    condition = getCondition();
    if (!bean.getDisplayHistoryMode().equals(targetMode)) {
      condition.setCurrentPage(1);
      condition.setPageSize(10);
    }

    // 検索結果リストを取得
    if (SHIPPING_MODE.equals(targetMode)) {
      // 订单发货履历
      condition.setSearchOrderType(OrderType.EC.getValue());
      condition.setSearchWithOutReturnInfo(!bean.isDisplayReturnButton());
      condition.setSearchWithOutExchangeOrder(true);
      condition.setSearchShippingMode(true);
      condition.setSearchWithOutCancel(false);
      SearchResult<MemberShippingHistory> result = service.getMemberShippingHistoryList(condition);
      bean.setPagerValue(PagerUtil.createValue(result));
      List<MemberShippingHistory> resultList = result.getRows();
      getShippingList(resultList, bean);
    } else if (INQUIRY_MODE.equals(targetMode)) {
      // 咨询履历
      SearchResult<MemberInquiryHistory> result = service.getMemberInquiryHistoryList(condition);
      bean.setPagerValue(PagerUtil.createValue(result));
      List<MemberInquiryHistory> resultList = result.getRows();
      getInquiryList(resultList, bean);
    } else if (COUPON_MODE.equals(targetMode)) {
      // 代金券履历
      SearchResult<MemberCouponHistory> result = service.getMemberCouponHistoryList(condition);
      bean.setPagerValue(PagerUtil.createValue(result));
      List<MemberCouponHistory> resultList = result.getRows();
      getCouponList(resultList, bean);
    }

    bean.setDisplayHistoryMode(targetMode);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  private void getShippingList(List<MemberShippingHistory> resultList, MemberInfoBean bean) {
    List<ShippingSearchedBean> shippingList = new ArrayList<ShippingSearchedBean>();
    for (MemberShippingHistory memberShippingHistory : resultList) {
      ShippingSearchedBean shippingSearchedBean = new ShippingSearchedBean();
      // 发货相关信息
      shippingSearchedBean.setOrderNo(memberShippingHistory.getOrderNo());
      shippingSearchedBean.setShippingNo(memberShippingHistory.getShippingNo());
      shippingSearchedBean.setOrderDate(memberShippingHistory.getOrderDatetime());
      if (PaymentMethodType.ALIPAY.getValue().equals(memberShippingHistory.getPaymentMethodType())
          || PaymentMethodType.CHINA_UNIONPAY.getValue().equals(memberShippingHistory.getPaymentMethodType())) {
        if (StringUtil.hasValue(memberShippingHistory.getPaymentDate())) {
          shippingSearchedBean.setPaymentDate(memberShippingHistory.getPaymentDate());
        } else {
          shippingSearchedBean.setPaymentDate(Messages.getString("web.action.back.service.MemberInfoHistoryAction.1"));
        }
      }
      shippingSearchedBean.setPaymentMethodName(memberShippingHistory.getPaymentMethodName());
      BigDecimal paymentCommission = NumUtil.coalesce(memberShippingHistory.getPaymentCommission(), BigDecimal.ZERO);
      String totalOrderPrice = NumUtil.toString(BigDecimalUtil
          .add(NumUtil.parse(memberShippingHistory.getTotalAmount()), paymentCommission));
      shippingSearchedBean.setOrderPrice(totalOrderPrice);
      CodeAttribute shippingStatus = ShippingStatus.fromValue(memberShippingHistory.getShippingStatus());
      if (shippingStatus != null) {
        shippingSearchedBean.setShippingStatus(shippingStatus.getValue());
        if (shippingStatus.getValue().equals(ShippingStatus.NOT_READY.getValue())) {
          shippingSearchedBean.setShippingStatusName(ShippingStatusSummary.NOT_SHIPPED.getName());
        } else {
          shippingSearchedBean.setShippingStatusName(shippingStatus.getName());
        }
        // 20131106 txw add start
        if (memberShippingHistory.getGiftCardUsePrice() != null 
            && BigDecimalUtil.isAbove(new BigDecimal(memberShippingHistory.getGiftCardUsePrice()), BigDecimal.ZERO)  
            && getLoginInfo().hasPermission(Permission.MEMBER_INFO_REFUND)
            && ( shippingStatus.getValue().equals(ShippingStatus.SHIPPED.getValue()) 
            || ( shippingStatus.getValue().equals(ShippingStatus.CANCELLED.getValue()) && StringUtil.hasValue(memberShippingHistory.getShippingDirectDate()) )) ) {
          shippingSearchedBean.setDisplayRefundLink(true);
        }
        // 20131106 txw add end
      }
      shippingSearchedBean.setDeliveryTypeName(memberShippingHistory.getDeliveryTypeName());
      shippingSearchedBean.setDeliveryCompanyName(memberShippingHistory.getDeliveryCompanyName());
      shippingSearchedBean.setDeliverySlipNo(memberShippingHistory.getDeliverySlipNo());
      shippingSearchedBean.setShippingDirectDate(memberShippingHistory.getShippingDirectDate());
      shippingSearchedBean.setShippingDate(memberShippingHistory.getShippingDate());
      shippingSearchedBean.setArrivalDate(memberShippingHistory.getArrivalDate());
      CodeAttribute orderStatus = OrderStatus.fromValue(memberShippingHistory.getOrderStatus());
      if (orderStatus != null) {
        shippingSearchedBean.setOrderStatus(orderStatus.getName());
      }
      // soukai add 2012/01/31 ob start
      CodeAttribute fixedSalesStatus = ReturnStatusSummary.fromValue(memberShippingHistory.getFixedSalesStatus());
      if (fixedSalesStatus != null) {
        shippingSearchedBean.setFixedSalesStatus(fixedSalesStatus.getName());
      }
      // soukai add 2012/01/31 ob end
      shippingList.add(shippingSearchedBean);
    }
    bean.setShippingList(shippingList);
  }

  private void getInquiryList(List<MemberInquiryHistory> resultList, MemberInfoBean bean) {
    List<InquirySearchedBean> inquiryList = new ArrayList<InquirySearchedBean>();
    for (MemberInquiryHistory memberInquiryHistory : resultList) {
      InquirySearchedBean inquirySearchedBean = new InquirySearchedBean();
      inquirySearchedBean.setInquiryHeaderNo(memberInquiryHistory.getInquiryHeaderNo());
      inquirySearchedBean.setAcceptDate(memberInquiryHistory.getAcceptDate());
      inquirySearchedBean.setLargeCategory(memberInquiryHistory.getLargeCategory());
      inquirySearchedBean.setSmallCategory(memberInquiryHistory.getSmallCategory());
      CodeAttribute inquiryWay = InquiryWay.fromValue(memberInquiryHistory.getInquiryWay());
      if (inquiryWay != null) {
        inquirySearchedBean.setInquiryWay(inquiryWay.getName());
      }
      inquirySearchedBean.setInquirySubject(memberInquiryHistory.getInquirySubject());
      inquirySearchedBean.setPersonInChargeName(memberInquiryHistory.getPersonInChargeName());
      inquirySearchedBean.setPersonInChargeNo(memberInquiryHistory.getPersonInChargeNo());
      inquirySearchedBean.setAcceptUpdate(memberInquiryHistory.getAcceptUpdate());
      CodeAttribute inquiryStatus = InquiryStatus.fromValue(memberInquiryHistory.getInquiryStatus());
      if (inquiryStatus != null) {
        inquirySearchedBean.setInquiryStatus(inquiryStatus.getName());
      }

      inquiryList.add(inquirySearchedBean);
    }
    bean.setInquiryList(inquiryList);
  }

  // 20110708 shiseido add start
  private void getCouponList(List<MemberCouponHistory> resultList, MemberInfoBean bean) {
    // 結果一覧を作成
    List<CouponSearchedBean> couponList = new ArrayList<CouponSearchedBean>();
    for (MemberCouponHistory memberCouponHistory : resultList) {
      CouponSearchedBean couponSearchedBean = new CouponSearchedBean();
      couponSearchedBean.setCouponOrderNo(memberCouponHistory.getCouponOrderNo());
      couponSearchedBean.setCouponRuleNo(memberCouponHistory.getCouponRuleNo());
      // soukai add 2012/02/01 ob start
      couponSearchedBean.setCouponIssueNo(memberCouponHistory.getCouponIssueNo());
      couponSearchedBean.setCouponRuleName(memberCouponHistory.getCouponRuleName());
      // soukai add 2012/02/01 ob end
      couponSearchedBean.setCouponIssueDetailNo(memberCouponHistory.getCouponIssueDetailNo());
      couponSearchedBean.setCouponUseStartDate(memberCouponHistory.getCouponUseStartDate());
      couponSearchedBean.setCouponUseEndDate(memberCouponHistory.getCouponUseEndDate());
      couponSearchedBean.setCouponIssueDate(memberCouponHistory.getCouponIssueDate());
      couponSearchedBean.setCouponPrice(memberCouponHistory.getCouponPrice());
      // 20111107 yuyongqiang add start
      couponSearchedBean.setCouponIssueReason(memberCouponHistory.getCouponIssueReason());
      // 20111107 yuyongqiang add end
      couponSearchedBean.setCouponInvestPurchasePrice(memberCouponHistory.getCouponInvestPurchasePrice());
      couponSearchedBean.setCouponUseOrderNo(memberCouponHistory.getCouponUseOrderNo());
      // soukai add 2012/02/01 ob start
      String couponStatus = memberCouponHistory.getCouponUse();
      Date couponStartDate = DateUtil.fromString(memberCouponHistory.getCouponUseStartDate());
      Date couponEndDate = DateUtil.fromString(memberCouponHistory.getCouponUseEndDate());
      Date currSysDate = DatabaseUtil.getSystemDatetime();
      if (couponStatus.equals("0")) {
        if (couponStartDate.after(currSysDate)) {
          // 不可用 同时 显示回收
          couponSearchedBean.setCouponUse(CouponUseStatus.CANNTUSE.getName());
          couponSearchedBean.setDispalyReturnCouponStatus(true);
        } else if (couponEndDate.before(currSysDate)) {
          // 过期
          couponSearchedBean.setCouponUse(CouponUseStatus.OVERDATE.getName());
          couponSearchedBean.setDispalyReturnCouponStatus(false);
        } else {
          // 未使用 同时 显示回收
          couponSearchedBean.setCouponUse(CouponUseStatus.UNUSED.getName());
          couponSearchedBean.setDispalyReturnCouponStatus(true);
        }
      } else if (couponStatus.equals("1")) {
        // 已使用
        couponSearchedBean.setCouponUse(CouponUseStatus.USEED.getName());
        couponSearchedBean.setDispalyReturnCouponStatus(false);
      } else if (couponStatus.equals("2")) {
        // 已取消
        couponSearchedBean.setCouponUse(CouponUseStatus.CANCEL.getName());
        couponSearchedBean.setDispalyReturnCouponStatus(false);
      }

      // soukai add 2012/02/01 ob end
      couponList.add(couponSearchedBean);
    }
    bean.setCouponList(couponList);
  }

  // 20110708 shiseido add end

  /**
   * 从URL取得显示履历模式。
   * 
   * @return targetMode
   */
  private String targetMode() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.MemberInfoHistoryAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109011004";
  }
}
