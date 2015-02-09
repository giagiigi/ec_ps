package jp.co.sint.webshop.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.configure.MobileDomain;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.dao.BankDao;
import jp.co.sint.webshop.data.dao.BroadcastMailqueueHeaderDao;
import jp.co.sint.webshop.data.dao.CommodityDetailDao;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dao.MailTemplateHeaderDao;
import jp.co.sint.webshop.data.dao.PointRuleDao;
import jp.co.sint.webshop.data.dao.ShopDao;
import jp.co.sint.webshop.data.domain.AccountType;
import jp.co.sint.webshop.data.domain.ClientMailType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.CouponStatus;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.data.domain.MailContentType;
import jp.co.sint.webshop.data.domain.MailSendStatus;
import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.Bank;
import jp.co.sint.webshop.data.dto.BroadcastMailqueueDetail;
import jp.co.sint.webshop.data.dto.BroadcastMailqueueHeader;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.OrderMailHistory;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.data.dto.Reminder;
import jp.co.sint.webshop.data.dto.RespectiveMailqueue;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingDetail;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.mail.ArrivalTag;
import jp.co.sint.webshop.mail.BirthDayTag;
import jp.co.sint.webshop.mail.CommodityDetailTag;
import jp.co.sint.webshop.mail.CommodityHeaderTag;
import jp.co.sint.webshop.mail.CouponEndTag;
import jp.co.sint.webshop.mail.CouponStartTag;
import jp.co.sint.webshop.mail.CustomerBaseTag;
import jp.co.sint.webshop.mail.CustomerTag;
import jp.co.sint.webshop.mail.CustomerWithdrawalRequestTag;
import jp.co.sint.webshop.mail.CustomerWithdrawalTag;
import jp.co.sint.webshop.mail.GroupChangeTag;
import jp.co.sint.webshop.mail.MailComposition;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.mail.MailSender;
import jp.co.sint.webshop.mail.MailTagDataList;
import jp.co.sint.webshop.mail.MailTemplateExpander;
import jp.co.sint.webshop.mail.MailTemplateTag;
import jp.co.sint.webshop.mail.OrderHeaderTag;
import jp.co.sint.webshop.mail.PaymentConfirmTag;
import jp.co.sint.webshop.mail.PaymentReminderTag;
import jp.co.sint.webshop.mail.PaymentTag;
import jp.co.sint.webshop.mail.PointExpiredTag;
import jp.co.sint.webshop.mail.ReissuePasswordTag;
import jp.co.sint.webshop.mail.ShippingDetailTag;
import jp.co.sint.webshop.mail.ShippingHeaderTag;
import jp.co.sint.webshop.mail.ShippingTag;
import jp.co.sint.webshop.mail.ShopInfoTag;
import jp.co.sint.webshop.mail.TmallShippingDetailTag;
import jp.co.sint.webshop.mail.TmallShippingHeaderTag;
import jp.co.sint.webshop.mail.TmallShippingTag;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeCvs;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeDigitalCash;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerSearchInfo;
import jp.co.sint.webshop.service.customer.CustomerSearchQuery;
import jp.co.sint.webshop.service.mail.BroadcastMailqueueSuite;
import jp.co.sint.webshop.service.mail.ClientMailTypeCondition;
import jp.co.sint.webshop.service.mail.MailingServiceQuery;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderHeadline;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.service.order.PaymentProviderManager;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.MailingServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.service.shop.MailTemplateSuite;
import jp.co.sint.webshop.service.shop.ShopManagementSimpleSql;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.ExPrintWriter;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class MailingServiceImpl extends AbstractServiceImpl implements MailingService {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public ServiceResult sendImmediate(MailInfo mail) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    MailSender sender = DIContainer.getMailSender();
    try {
      sender.sendMail(mail);
      logger.info(Messages.log("service.impl.MailingServiceImpl.0"));
    } catch (Exception e) {
      logger.error(e);
    }
    return result;
  }

  public ServiceResult deleteBroadcastMailqueue(int month) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    Date systemDate = DateUtil.getSysdate();
    Date deleteDate = DateUtil.addMonth(systemDate, month * (-1));

    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(this.getLoginInfo());

      manager.executeUpdate(MailingServiceQuery.DELETE_BROADCAST_MAILQUEUE_DETAIL, deleteDate);

      manager.executeUpdate(MailingServiceQuery.DELETE_BROADCAST_MAILQUE_HEADER);

      manager.commit();
    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }

    return serviceResult;
  }

  public ServiceResult deleteRespectiveMailqueue(int month) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    Date systemDate = DateUtil.getSysdate();
    Date deleteDate = DateUtil.addMonth(systemDate, month * (-1));

    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(this.getLoginInfo());

      manager.executeUpdate(MailingServiceQuery.DELETE_RESPECTIVE_MAILQUEUE, deleteDate);

      manager.commit();
    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }
    return serviceResult;
  }

  public List<BroadcastMailqueueSuite> getNoSentBroadcastMailqueueDetailList() {
    List<BroadcastMailqueueSuite> suiteList = new ArrayList<BroadcastMailqueueSuite>();
    // 全て送信済み以外のメールキューヘッダ情報を取得

    Query q = new SimpleQuery(MailingServiceQuery.LOAD_NO_SENT_BROADCAST_MAILQUEUE_HEADER);
    List<BroadcastMailqueueHeader> headerList = DatabaseUtil.loadAsBeanList(q, BroadcastMailqueueHeader.class);
    for (BroadcastMailqueueHeader header : headerList) {
      BroadcastMailqueueSuite suite = new BroadcastMailqueueSuite();
      suite.setHeader(header);

      // 明細の取得

      Query detailQuery = new SimpleQuery(MailingServiceQuery.LOAD_NO_SENT_BROADCAST_MAILQUEUE_DETAIL, header.getMailQueueId());
      List<BroadcastMailqueueDetail> detailList = DatabaseUtil.loadAsBeanList(detailQuery, BroadcastMailqueueDetail.class);
      suite.setDetailList(detailList);

      suiteList.add(suite);
    }
    return suiteList;
  }

  public ServiceResult updateBroadcastMailqueue(BroadcastMailqueueSuite suite) {
    Logger logger = Logger.getLogger(this.getClass());

    ServiceResultImpl serviceResult = new ServiceResultImpl();
    // 存在チェック&登録者・日付・データ行IDコピー

    BroadcastMailqueueHeader header = suite.getHeader();
    BroadcastMailqueueHeaderDao headerDao = DIContainer.getDao(BroadcastMailqueueHeaderDao.class);
    BroadcastMailqueueHeader org = headerDao.load(header.getMailQueueId());
    if (org == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    } else {
      header.setMailSendStatus(suite.getMailSendStatus().longValue()); // 10.1.1
      // 10015
      // 追加
      header.setCreatedDatetime(org.getCreatedDatetime());
      header.setCreatedUser(org.getCreatedUser());
      header.setOrmRowid(org.getOrmRowid());
      setUserStatus(header);
    }
    // 10.1.1 10015 削除 ここから
    // List<BroadcastMailqueueDetail> detailList = suite.getDetailList();
    // BroadcastMailqueueDetailDao detailDao =
    // DIContainer.getDao(BroadcastMailqueueDetailDao.class);
    // for (BroadcastMailqueueDetail detail : detailList) {
    // BroadcastMailqueueDetail orgDetail =
    // detailDao.load(detail.getMailQueueId(), detail.getCustomerCode());
    // if (orgDetail == null) {
    // serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
    // return serviceResult;
    // } else {
    // detail.setCreatedUser(orgDetail.getCreatedUser());
    // detail.setCreatedDatetime(orgDetail.getCreatedDatetime());
    // detail.setOrmRowid(orgDetail.getOrmRowid());
    // setUserStatus(detail);
    // }
    // }
    // 10.1.1 10015 削除 ここまで

    // validate
    ValidationSummary summary = BeanValidator.validate(header);
    if (summary.hasError()) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String message : summary.getErrorMessages()) {
        logger.error(message);
      }
      return serviceResult;
    }
    // 10.1.1 10015 削除 ここから
    // for (BroadcastMailqueueDetail detail : detailList) {
    // summary = BeanValidator.validate(detail);
    // if (summary.hasError()) {
    // serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
    // for (String message : summary.getErrorMessages()) {
    // logger.error(message);
    // }
    // return serviceResult;
    // }
    // }
    // 10.1.1 10015 削除 ここまで

    // 更新処理
    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(this.getLoginInfo());

      manager.update(header);

      // 10.1.1 10015 修正 ここから
      // for (BroadcastMailqueueDetail detail : detailList) {
      // manager.update(detail);
      // }
      List<BroadcastMailqueueDetail> detailList = suite.getDetailList();
      for (BroadcastMailqueueDetail detail : detailList) {
        Long updateStatus = null;
        String updatedUser = getLoginInfo().getRecordingFormat();
        Date updatedDatetime = DateUtil.getSysdate();
        if (BeanValidator.validate(detail).hasError() || suite.getFailed().contains(detail.getCustomerCode())) {
          updateStatus = MailSendStatus.FAILED_ALL.longValue();
        } else {
          updateStatus = MailSendStatus.SENT_ALL.longValue();
        }
        Object[] params = {
            // 10.1.7 10302 修正 ここから
            // updateStatus, updatedUser, updatedDatetime,
            // detail.getMailQueueId(), detail.getCustomerCode()
            updateStatus, updatedUser, updatedDatetime, detail.getMailSentDatetime(), detail.getMailQueueId(),
            detail.getCustomerCode()
        // 10.1.7 10302 修正 ここまで
        };
        int updateCount = manager.executeUpdate(MailingServiceQuery.UPDATE_MAIL_SEND_STATUS, params);
        if (updateCount == 0) {
          logger.warn(Messages.log("service.impl.MailingServiceImpl.34") + detail.getMailQueueId()
              + Messages.log("service.impl.MailingServiceImpl.35") + detail.getCustomerCode());
        }
      }
      // 10.1.1 10015 修正 ここまで

      manager.commit();
    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }

    return serviceResult;
  }

  public ServiceResult sendRespectiveMail(RespectiveMailqueue mailQueue) {
    return sendRespectiveMail(mailQueue, null);
  }

  private ServiceResult sendRespectiveMail(RespectiveMailqueue mailQueue, OrderMailHistory history) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    setUserStatus(mailQueue);

    Long mailQueueSeq = DatabaseUtil.generateSequence(SequenceType.RESPECTIVE_MAIL_QUEUE_ID);
    mailQueue.setMailQueueId(mailQueueSeq);

    // Validateチェック
    ValidationSummary validResult = BeanValidator.validate(mailQueue);
    if (validResult.hasError()) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String message : validResult.getErrorMessages()) {
        logger.error(message);
      }
      return serviceResult;
    }

    TransactionManager manager = DIContainer.getTransactionManager();
    MailSender sender = DIContainer.getMailSender();
    try {
      manager.begin(this.getLoginInfo());

      MailInfo info = new MailInfo();

      info.setSubject(mailQueue.getMailSubject());
      info.setFromInfo(mailQueue.getFromAddress(), mailQueue.getMailSenderName());
      info.setSendDate(DateUtil.getSysdate());

      MailContentType mct = MailContentType.fromValue(mailQueue.getMailContentType());
      if (mct != null) {
        info.setContentType(MailInfo.getContentType(mct));
      } else {
        info.setContentType(MailInfo.CONTENT_TYPE_HTML);
      }

      info.addToList(mailQueue.getToAddress());

      if (StringUtil.hasValue(mailQueue.getBccAddress())) {
        info.addBccList(mailQueue.getBccAddress());
      }
      info.setText(mailQueue.getMailText());
      mailQueue.setMailSentDatetime(DateUtil.getSysdate()); // 10.1.7 10302 追加
      try {
        sender.sendMail(info);
        // mailQueueに必要な情報を設定する

        mailQueue.setMailSendStatus(Long.parseLong(MailSendStatus.SENT_ALL.getValue()));
        // 10.1.7 10302 修正 ここから
        // mailQueue.setMailSentDatetime(DatabaseUtil.getSystemDatetime());
        // 10.1.7 10302 追加 ここまで
        logger.info(Messages.log("service.impl.MailingServiceImpl.1"));
      } catch (MessagingException e) {
        logger.error(Messages.log("service.impl.MailingServiceImpl.2"));
        mailQueue.setMailSendStatus(Long.parseLong(MailSendStatus.FAILED_ALL.getValue()));
      } catch (UnsupportedEncodingException e) {
        logger.error(Messages.log("service.impl.MailingServiceImpl.3"));
        mailQueue.setMailSendStatus(Long.parseLong(MailSendStatus.FAILED_ALL.getValue()));
      }

      manager.insert(mailQueue);
      if (history != null) {
        history.setMailQueueId(mailQueue.getMailQueueId());
        manager.insert(history);
      }
      manager.commit();
    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }

    return serviceResult;
  }

  public ServiceResult sendNewOrderMailMobile(OrderContainer order, CashierPayment payment, Shop shop) {
    return sendOrderMail(order, payment, shop, true, ClientMailType.MOBILE);
  }

  public ServiceResult sendNewOrderMailPc(OrderContainer order, CashierPayment payment, Shop shop) {
    return sendOrderMail(order, payment, shop, true, ClientMailType.PC);
  }

  public ServiceResult sendUpdateOrderMail(OrderContainer order, CashierPayment payment, Shop shop) {
    ClientMailTypeCondition condition = new ClientMailTypeCondition();
    condition.setMailAddress(order.getOrderHeader().getEmail());
    return sendOrderMail(order, payment, shop, false, getClientMailType(condition));
  }

  private ServiceResult sendOrderMail(OrderContainer orderContainer, CashierPayment payment, Shop shop, boolean newOrderFlg,
      ClientMailType clientMailType) {

    ServiceResultImpl result = new ServiceResultImpl();

    OrderHeader orderHeader = orderContainer.getOrderHeader();

    String toAddress = "";

    // 顧客の場合、顧客テーブルからメールアドレスと取得する

    if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
      if (orderHeader.getCustomerCode() == null) {
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return result;
      }
      CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
      Customer customer = customerDao.load(orderHeader.getCustomerCode());
      if (customer == null) {
        result.addServiceError(MailingServiceErrorContent.NO_CUSTOMER_TO_SEND_MAIL);
        return result;
      }
      toAddress = customer.getEmail();
    } else {
      toAddress = orderHeader.getEmail();
    }
    orderContainer.getOrderHeader().setEmail(toAddress);

    MailType mailType;

    if (orderContainer.getOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
      mailType = MailType.RESERVATION_DETAIL;
    } else if (clientMailType == ClientMailType.PC) {
      mailType = MailType.ORDER_DETAILS_PC;
    } else {
      mailType = MailType.ORDER_DETAILS_MOBILE;
    }

    if (noSetMailTemplate(shop.getShopCode(), mailType.getValue(), 0L)) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }

    MailTemplateSuite mailTemplateSuite = createMailTemplate(shop.getShopCode(), mailType.getValue(), 0L, orderHeader
        .getCustomerCode());

    MailComposition mainComposition = null;
    @SuppressWarnings("unused")
    MailComposition modifyMessageComposition = null;
    MailComposition shippingHeaderComposition = null;
    MailComposition shippingDetailComposition = null;
    MailComposition paymentComposition = null;
    MailComposition signComposition = null;

    if (orderContainer.getOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
      mainComposition = MailComposition.RESERVATION_DETAIL_MAIN;
      shippingHeaderComposition = MailComposition.RESERVATION_DETAIL_SHIPPING_HEADER;
      shippingDetailComposition = MailComposition.RESERVATION_DETAIL_SHIPPING_DETAIL;
      paymentComposition = MailComposition.RESERVATION_DETAIL_PAYMENT;
      signComposition = MailComposition.RESERVATION_DETAIL_SIGN;
    } else if (clientMailType == ClientMailType.PC) {
      mainComposition = MailComposition.ORDER_DETAILS_PC_MAIN;
      shippingHeaderComposition = MailComposition.ORDER_DETAILS_PC_SHIPPING_HEADER;
      shippingDetailComposition = MailComposition.ORDER_DETAILS_PC_SHIPPING_DETAIL;
      paymentComposition = MailComposition.ORDER_DETAILS_PC_PAYMENT;
      signComposition = MailComposition.ORDER_DETAILS_PC_SIGN;
    } else {
      mainComposition = MailComposition.ORDER_DETAILS_MOBILE_MAIN;
      modifyMessageComposition = MailComposition.ORDER_DETAILS_MOBILE_MODIFY_MESSAGE;
      shippingHeaderComposition = MailComposition.ORDER_DETAILS_MOBILE_SHIPPING_HEADER;
      shippingDetailComposition = MailComposition.ORDER_DETAILS_MOBILE_SHIPPING_DETAIL;
      paymentComposition = MailComposition.ORDER_DETAILS_MOBILE_PAYMENT;
      signComposition = MailComposition.ORDER_DETAILS_MOBILE_SIGN;

    }

    // 税込価格表示を追記

    setTaxIncludedPriceDisplay(mailTemplateSuite);

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    setOrderHeaderTag(expander, mainComposition, orderContainer);

    if (orderContainer.getOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
      setReservationShippingTag(expander, shippingHeaderComposition, orderContainer, shippingDetailComposition);
    } else {
      setShippingHeaderTag(expander, shippingHeaderComposition, orderContainer, shippingDetailComposition);
    }
    setPaymentTag(expander, paymentComposition, orderHeader, payment);
    setShopInfoTag(expander, signComposition, shop);

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, toAddress, MailSendStatus.NOT_SENT.longValue(), expander
        .expandTemplate());

    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        result.addServiceError(error);
      }
    }

    return result;
  }

  /**
   * 税込価格表記追記用メソッド。 MailTemplateSuiteを受取り、templateSuite内の枝番が1番の、
   * テンプレート明細の本文末尾に税込価格表記を追記する。
   */
  private void setTaxIncludedPriceDisplay(MailTemplateSuite mailTemplateSuite) {
    Long contentType = mailTemplateSuite.getMailTemplateHeader().getMailContentType();
    String lineFeed = "";
    if (contentType.equals(MailContentType.TEXT.longValue())) {
      lineFeed = "\r\n";
    } else if (contentType.equals(MailContentType.HTML.longValue())) {
      lineFeed = "<br/>";
    }
    for (MailTemplateDetail detail : mailTemplateSuite.getMailTemplateDetail()) {
      if (detail.getMailTemplateBranchNo().equals(1L)) {
        // detail.setMailText(detail.getMailText() + lineFeed +
        // Messages.getString("service.impl.MailingServiceImpl.4"));
        detail.setMailText(detail.getMailText() + lineFeed);
      }
    }

  }

  public ServiceResult insertBroadcastMailqueue(BroadcastMailqueueSuite suite) {
    Logger logger = Logger.getLogger(this.getClass());

    ServiceResultImpl serviceResult = new ServiceResultImpl();
    // 存在チェック&登録者・日付・データ行IDコピー

    BroadcastMailqueueHeader header = suite.getHeader();
    setUserStatus(header);

    List<BroadcastMailqueueDetail> detailList = suite.getDetailList();
    if (detailList.size() < 1) {
      return serviceResult;
    }

    for (BroadcastMailqueueDetail detail : detailList) {
      setUserStatus(detail);
    }

    // validate
    ValidationSummary summary = BeanValidator.validate(header);
    if (summary.hasError()) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String message : summary.getErrorMessages()) {
        logger.error(message);
      }
      return serviceResult;
    }
    for (BroadcastMailqueueDetail detail : detailList) {
      summary = BeanValidator.validate(detail);
      if (summary.hasError()) {
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String message : summary.getErrorMessages()) {
          logger.error(message);
        }
        return serviceResult;
      }
    }

    // 登録処理
    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(this.getLoginInfo());

      manager.insert(header);

      for (BroadcastMailqueueDetail detail : detailList) {
        manager.insert(detail);
      }

      manager.commit();
    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }

    return serviceResult;
  }

  public ServiceResult sendArrivalInformationMail(CommodityHeader commodityHeader, CommodityDetail commodityDetail, Shop shop,
      List<Customer> customerList) {
    ServiceResultImpl result = new ServiceResultImpl();

    if (noSetMailTemplate(shop.getShopCode(), MailType.ARRIVAL_INFORMATION.getValue(), 0L)) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }

    List<Customer> pcCustomerList = new ArrayList<Customer>();
    List<Customer> mobileCustomerList = new ArrayList<Customer>();

    for (Customer customer : customerList) {
      if (StringUtil.isNullOrEmpty(customer.getCustomerCode())) {
        customer.setCustomerCode(CommonLogic.generateGuestCode());
      }
      MobileDomain domain = DIContainer.get("MobileDomain");
      if (domain.getClientMailType(customer.getEmail()) == ClientMailType.PC.longValue().intValue()) {
        pcCustomerList.add(customer);
      } else {
        mobileCustomerList.add(customer);
      }

      MailTemplateSuite mailTemplateSuite = createMailTemplate(shop.getShopCode(), MailType.ARRIVAL_INFORMATION.getValue(), 0L,
          customer.getCustomerCode());
      MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

      setCommodityTag(expander, MailComposition.ARRIVAL_GOODS_MAIN, commodityHeader, commodityDetail);
      setShopInfoTag(expander, MailComposition.ARRIVAL_GOODS_SIGN, shop);

      MailTemplateExpander mobileExpander = new MailTemplateExpander(mailTemplateSuite);

      setMobileCommodityTag(mobileExpander, MailComposition.ARRIVAL_GOODS_MAIN, commodityHeader, commodityDetail);
      setShopInfoTag(mobileExpander, MailComposition.ARRIVAL_GOODS_SIGN, shop);

      BroadcastMailqueueSuite queue = createBroadcastMailqueueSuite(mailTemplateSuite, pcCustomerList, MailSendStatus.NOT_SENT
          .longValue(), expander.expandTemplate());

      ServiceResult sendMailResult = insertBroadcastMailqueue(queue);

      if (sendMailResult.hasError()) {
        for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
          result.addServiceError(error);
        }
      }
      BroadcastMailqueueSuite mobileQueue = createBroadcastMailqueueSuite(mailTemplateSuite, mobileCustomerList,
          MailSendStatus.NOT_SENT.longValue(), mobileExpander.expandTemplate());

      ServiceResult sendMobileMailResult = insertBroadcastMailqueue(mobileQueue);

      if (sendMobileMailResult.hasError()) {
        for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
          result.addServiceError(error);
        }
      }
    }
    return result;
  }

  private void setCommodityTag(MailTemplateExpander expander, MailComposition composition, CommodityHeader commodityHeader,
      CommodityDetail commodityDetail) {
    setCommodityTag(expander, composition, commodityHeader, commodityDetail, true);
  }

  // saikou 20111230 add start
  private void setCouponStartTag(MailTemplateExpander expander, MailComposition composition, NewCouponHistory newCouponHistory,
      Customer customer) {
    MailTagDataList couponStartTagList = new MailTagDataList();
    couponStartTagList.add(CouponStartTag.LAST_NAME, customer.getLastName());
    // 2013/04/15 优惠券对应 ob add start
    // 优惠劵名称
    String lang = getLanguageCode(customer.getCustomerCode());
    if (!StringUtil.isNullOrEmpty(lang)) {
      if (lang.equals("zh-cn")) {
        couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponName());
      } else if (lang.equals("ja-jp")) {
        couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponNameJp());
      } else {
        couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponNameEn());
      }
    } else {
      couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponName());
    }
    // 2013/04/15 优惠券对应 ob add end
    couponStartTagList.add(CouponStartTag.COUPON_CODE, newCouponHistory.getCouponIssueNo());
    if (StringUtil.hasValue(newCouponHistory.getIssueReason())) {
      couponStartTagList.add(CouponStartTag.REASON, newCouponHistory.getIssueReason());
    } else {
      couponStartTagList.add(CouponStartTag.REASON, Messages.getString("service.impl.UtilServiceImpl.8"));
    }

    couponStartTagList.add(CouponStartTag.MIN_AMOUNT, NumUtil.toString(newCouponHistory.getMinUseOrderAmount()));
    if (CouponIssueType.FIXED.longValue().equals(newCouponHistory.getCouponIssueType())) {
      if (!StringUtil.isNullOrEmpty(lang)) {
        if (lang.equals("zh-cn")) {
          couponStartTagList.add(CouponStartTag.AMOUNT, "优惠金额：" + NumUtil.toString(newCouponHistory.getCouponAmount()) + "元");
        } else if (lang.equals("ja-jp")) {
          couponStartTagList.add(CouponStartTag.AMOUNT, "割引額：" + NumUtil.toString(newCouponHistory.getCouponAmount()) + "元");
        } else {
          couponStartTagList.add(CouponStartTag.AMOUNT, "discount value is：" + NumUtil.toString(newCouponHistory.getCouponAmount())
              + "rmb.");
        }
      } else {
        couponStartTagList.add(CouponStartTag.AMOUNT, "优惠金额：" + NumUtil.toString(newCouponHistory.getCouponAmount()) + "元");
      }
      // couponStartTagList.add(CouponStartTag.AMOUNT,
      // NumUtil.toString(newCouponHistory.getCouponAmount())
      // + Messages.getString("numUtil.formatCurrency.0"));
    } else {
      if (!StringUtil.isNullOrEmpty(lang)) {
        if (lang.equals("zh-cn")) {
          couponStartTagList.add(CouponStartTag.AMOUNT, "使用该优惠券享受"
              + NumUtil.toString((100 - newCouponHistory.getCouponProportion()) / 10)
              + "折优惠，最高折扣金额"
              + NumUtil.toString(newCouponHistory.getCouponProportion() * 0.01
                  * newCouponHistory.getMaxUseOrderAmount().doubleValue()) + "元");
        } else if (lang.equals("ja-jp")) {
          couponStartTagList.add(CouponStartTag.AMOUNT, "割引額："
              + NumUtil.toString(newCouponHistory.getCouponProportion())
              + "%OFF 、最大"
              + NumUtil.toString(newCouponHistory.getCouponProportion() * 0.01
                  * newCouponHistory.getMaxUseOrderAmount().doubleValue()) + "元まで割引できます。");
        } else {
          couponStartTagList.add(CouponStartTag.AMOUNT, "Use this coupon get "
              + NumUtil.toString(newCouponHistory.getCouponProportion())
              + "% OFF,maximum discount value is "
              + NumUtil.toString(newCouponHistory.getCouponProportion() * 0.01
                  * newCouponHistory.getMaxUseOrderAmount().doubleValue()) + "rmb.");
        }
      } else {
        couponStartTagList.add(CouponStartTag.AMOUNT, "使用该优惠券享受"
            + NumUtil.toString((100 - newCouponHistory.getCouponProportion()) / 10)
            + "折优惠，最高折扣金额"
            + NumUtil.toString(newCouponHistory.getCouponProportion() * 0.01
                * newCouponHistory.getMaxUseOrderAmount().doubleValue()) + "元");
      }
      // couponStartTagList.add(CouponStartTag.AMOUNT,
      // NumUtil.toString(newCouponHistory.getCouponProportion()) + "%");
    }
    couponStartTagList.add(CouponStartTag.USE_START_DATE, DateUtil.toDateTimeString(newCouponHistory.getUseStartDatetime()));
    couponStartTagList.add(CouponStartTag.USE_END_DATE, DateUtil.toDateTimeString(newCouponHistory.getUseEndDatetime()));
    expander.addTagDataList(composition.getSubstitutionTag(), couponStartTagList);
  }

  private void setCouponEndTag(MailTemplateExpander expander, MailComposition composition, NewCouponHistory newCouponHistory,
      Customer customer) {
    MailTagDataList couponStartTagList = new MailTagDataList();
    couponStartTagList.add(CouponEndTag.LAST_NAME, customer.getLastName());
    // 2013/04/15 优惠券对应 ob add start
    // 优惠劵名称
    String lang = getLanguageCode(customer.getCustomerCode());
    if (!StringUtil.isNullOrEmpty(lang)) {
      if (lang.equals("zh-cn")) {
        couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponName());
      } else if (lang.equals("ja-jp")) {
        couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponNameJp());
      } else {
        couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponNameEn());
      }
    } else {
      couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponName());
    }
    // 2013/04/15 优惠券对应 ob add end
    couponStartTagList.add(CouponEndTag.COUPON_CODE, newCouponHistory.getCouponIssueNo());
    if (StringUtil.hasValue(newCouponHistory.getIssueReason())) {
      couponStartTagList.add(CouponEndTag.REASON, newCouponHistory.getIssueReason());
    } else {
      couponStartTagList.add(CouponEndTag.REASON, Messages.getString("service.impl.UtilServiceImpl.8"));
    }
    couponStartTagList.add(CouponEndTag.MIN_AMOUNT, NumUtil.toString(newCouponHistory.getMinUseOrderAmount()));
    if (CouponIssueType.FIXED.longValue().equals(newCouponHistory.getCouponIssueType())) {
      couponStartTagList.add(CouponEndTag.AMOUNT, NumUtil.toString(newCouponHistory.getCouponAmount())
          + Messages.getString("numUtil.formatCurrency.0"));
    } else {
      couponStartTagList.add(CouponEndTag.AMOUNT, NumUtil.toString(newCouponHistory.getCouponProportion()) + "%");
    }
    couponStartTagList.add(CouponEndTag.USE_START_DATE, DateUtil.toDateTimeString(newCouponHistory.getUseStartDatetime()));
    couponStartTagList.add(CouponEndTag.USE_END_DATE, DateUtil.toDateTimeString(newCouponHistory.getUseEndDatetime()));
    expander.addTagDataList(composition.getSubstitutionTag(), couponStartTagList);
  }

  // saikou 20111230 add start

  private void setMobileCommodityTag(MailTemplateExpander expander, MailComposition composition, CommodityHeader commodityHeader,
      CommodityDetail commodityDetail) {
    setCommodityTag(expander, composition, commodityHeader, commodityDetail, false);
  }

  private void setCommodityTag(MailTemplateExpander expander, MailComposition composition, CommodityHeader commodityHeader,
      CommodityDetail commodityDetail, boolean pc) {
    MailTagDataList commodityTagList = new MailTagDataList();

    commodityTagList.add(CommodityHeaderTag.COMMODITY_CODE, commodityHeader.getCommodityCode());
    commodityTagList.add(CommodityHeaderTag.COMMODITY_DESCRIPTION_MOBILE, commodityHeader.getCommodityDescriptionMobile());
    commodityTagList.add(CommodityHeaderTag.COMMODITY_DESCRIPTION_PC, commodityHeader.getCommodityDescriptionPc());
    commodityTagList.add(CommodityHeaderTag.COMMODITY_NAME, commodityHeader.getCommodityName());

    commodityTagList.add(CommodityHeaderTag.COMMODITY_STANDARD1_NAME, commodityHeader.getCommodityStandard1Name());
    commodityTagList.add(CommodityHeaderTag.COMMODITY_STANDARD2_NAME, commodityHeader.getCommodityStandard2Name());

    String linkUrl = "";
    if (pc) {
      linkUrl = DIContainer.getWebshopConfig().getTopPageUrl() + "/commodity/" + commodityHeader.getShopCode() + "/"
          + commodityHeader.getCommodityCode() + "?selectedSkuCode=" + commodityDetail.getSkuCode();
    } else {
      linkUrl = DIContainer.getWebshopConfig().getMobileTopPageUrl() + "/app/catalog/detail/init/" + commodityHeader.getShopCode()
          + "/" + commodityHeader.getCommodityCode() + "/" + commodityDetail.getSkuCode() + "/";
    }

    commodityTagList.add(CommodityHeaderTag.LINK_URL, linkUrl);

    commodityTagList.add(CommodityDetailTag.JAN_CODE, commodityDetail.getJanCode());
    commodityTagList.add(CommodityDetailTag.SKU_CODE, commodityDetail.getSkuCode());
    commodityTagList.add(CommodityDetailTag.STANDARD_DETAIL1_NAME, commodityDetail.getStandardDetail1Name());
    commodityTagList.add(CommodityDetailTag.STANDARD_DETAIL2_NAME, commodityDetail.getStandardDetail2Name());
    commodityTagList
        .add(CommodityDetailTag.RETAIL_PRICE, StringUtil.coalesce(Price.getFormatPrice(commodityDetail.getUnitPrice())));

    if (commodityHeader.getDiscountPriceStartDatetime() != null || commodityHeader.getDiscountPriceEndDatetime() != null) {
      if (DateUtil.isPeriodDate(commodityHeader.getDiscountPriceStartDatetime(), commodityHeader.getDiscountPriceEndDatetime())) {
        commodityTagList.add(CommodityDetailTag.RETAIL_PRICE, StringUtil.coalesce(Price.getFormatPrice(commodityDetail
            .getDiscountPrice())));
      }
    }

    expander.addTagDataList(composition.getSubstitutionTag(), commodityTagList);
  }

  public ServiceResult sendPaymentReceivedMail(OrderContainer orderContainer, OrderSummary orderSummary, Shop shop) {
    ServiceResultImpl result = new ServiceResultImpl();

    OrderHeader orderHeader = orderContainer.getOrderHeader();
    String toAddress = "";

    // 会員購入の場合、顧客テーブルからメールアドレスと取得する

    if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
      if (orderHeader.getCustomerCode() == null) {
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return result;
      }
      CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
      Customer customer = customerDao.load(orderHeader.getCustomerCode());
      if (customer == null) {
        result.addServiceError(MailingServiceErrorContent.NO_CUSTOMER_TO_SEND_MAIL);
        return result;
      }
      toAddress = customer.getEmail();
    } else {
      toAddress = orderHeader.getEmail();
    }
    orderContainer.getOrderHeader().setEmail(toAddress);

    // createMailTemplateにショップコード、MailType、テンプレート番号(情報メール以外は0なので基本0L)をセットしてtemplateSuiteを取得する

    MailTemplateSuite mailTemplateSuite = createMailTemplate(shop.getShopCode(), MailType.RECEIVED_PAYMENT.getValue(), 0L,
        orderHeader.getCustomerCode());

    setTaxIncludedPriceDisplay(mailTemplateSuite);

    if (noSetMailTemplate(shop.getShopCode(), MailType.RECEIVED_PAYMENT.getValue(), 0L)) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }

    // MailTemplateExpanderにtemplateSuiteを読み込ませ、expanderを生成する

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    // 各メールたぐごとに値を設定していく
    setPaymentConfirmTag(expander, MailComposition.RECEIVED_PAYMENT_MAIN, orderHeader, orderSummary, toAddress);
    setShippingHeaderTag(expander, MailComposition.RECEIVED_PAYMENT_SHIPPING, orderContainer,
        MailComposition.RECEIVED_PAYMENT_SHIPPING_COMMODITY);
    setShopInfoTag(expander, MailComposition.RECEIVED_PAYMENT_SIGN, shop);

    // createMailQueueにtemplateSuite、送信先アドレス、メール送信ステータス(未送信)、expanderから生成したメール本文をセットし、queueを生成する。

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, toAddress, MailSendStatus.NOT_SENT.longValue(), expander
        .expandTemplate());

    OrderMailHistory history = new OrderMailHistory();
    history.setOrderNo(orderHeader.getOrderNo());
    history.setOrderMailHistoryId(DatabaseUtil.generateSequence(SequenceType.ORDER_MAIL_HISTORY_ID));

    ServiceResult sendMailResult = sendRespectiveMail(queue, history);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        result.addServiceError(error);
      }
    }

    return result;
  }

  public ServiceResult sendPaymentReminderMail(OrderContainer orderContainer, OrderSummary orderSummary, Shop shop,
      CashierPayment payment) {
    ServiceResultImpl result = new ServiceResultImpl();

    if (payment.getSelectPayment() == null) {
      // 支払方法が削除されている場合エラーを返す
      result.addServiceError(OrderServiceErrorContent.PAYMENT_METHOD_NOT_FOUND_ERROR);
      return result;
    }

    OrderHeader orderHeader = orderContainer.getOrderHeader();

    String toAddress = "";

    // 会員購入の場合、顧客テーブルからメールアドレスと取得する

    if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
      if (orderHeader.getCustomerCode() == null) {
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return result;
      }
      CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
      Customer customer = customerDao.load(orderHeader.getCustomerCode());
      if (customer == null) {
        result.addServiceError(MailingServiceErrorContent.NO_CUSTOMER_TO_SEND_MAIL);
        return result;
      }
      toAddress = customer.getEmail();
    } else {
      toAddress = orderHeader.getEmail();
    }
    orderContainer.getOrderHeader().setEmail(toAddress);

    MailTemplateSuite mailTemplateSuite = createMailTemplate(shop.getShopCode(), MailType.PAYMENT_REMINDER.getValue(), 0L,
        orderHeader.getCustomerCode());

    setTaxIncludedPriceDisplay(mailTemplateSuite);

    if (noSetMailTemplate(shop.getShopCode(), MailType.PAYMENT_REMINDER.getValue(), 0L)) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    setPaymentReminderTag(expander, MailComposition.PAYMENT_REMINDER_MAIN, orderHeader, orderSummary, toAddress);
    setPaymentTag(expander, MailComposition.PAYMENT_REMINDER_PAYMENT_INFO, orderHeader, payment);
    setShippingHeaderTag(expander, MailComposition.PAYMENT_REMINDER_SHIPPING, orderContainer,
        MailComposition.PAYMENT_REMINDER_SHIPPING_COMMODITY);

    setShopInfoTag(expander, MailComposition.PAYMENT_REMINDER_SIGN, shop);

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, toAddress, MailSendStatus.NOT_SENT.longValue(), expander
        .expandTemplate());

    OrderMailHistory history = new OrderMailHistory();
    history.setOrderNo(orderHeader.getOrderNo());
    history.setOrderMailHistoryId(DatabaseUtil.generateSequence(SequenceType.ORDER_MAIL_HISTORY_ID));

    ServiceResult sendMailResult = sendRespectiveMail(queue, history);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        result.addServiceError(error);
      }
    }

    return result;
  }

  public ServiceResult sendShippingReceivedMail(OrderContainer orderContainer, OrderContainer allContainer, Shop shop,
      CashierPayment payment, String shippingNo) {
    ServiceResultImpl result = new ServiceResultImpl();

    OrderHeader orderHeader = orderContainer.getOrderHeader();

    String toAddress = "";

    // 会員購入の場合、顧客テーブルからメールアドレスと取得する

    if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
      if (orderHeader.getCustomerCode() == null) {
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return result;
      }
      CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
      Customer customer = customerDao.load(orderHeader.getCustomerCode());
      if (customer == null) {
        result.addServiceError(MailingServiceErrorContent.NO_CUSTOMER_TO_SEND_MAIL);
        return result;
      }
      toAddress = customer.getEmail();
    } else {
      toAddress = orderHeader.getEmail();
    }
    orderContainer.getOrderHeader().setEmail(toAddress);

    MailTemplateSuite mailTemplateSuite = createMailTemplate(shop.getShopCode(), MailType.SHIPPING_INFORMATION.getValue(), 0L,
        orderHeader.getCustomerCode());
    // setTaxIncludedPriceDisplay(mailTemplateSuite);

    if (noSetMailTemplate(shop.getShopCode(), MailType.SHIPPING_INFORMATION.getValue(), 0L)) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    setShippingTag(expander, MailComposition.SHIPPING_REPORT_MAIN, orderContainer, allContainer);
    setShippingHeaderTag(expander, MailComposition.SHIPPING_REPORT_HEADER, orderContainer, MailComposition.SHIPPING_REPORT_DETAIL);
    setShopInfoTag(expander, MailComposition.SHIPPING_REPORT_SIGN, shop);

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, toAddress, MailSendStatus.NOT_SENT.longValue(), expander
        .expandTemplate());

    OrderMailHistory history = new OrderMailHistory();
    history.setOrderNo(orderHeader.getOrderNo());
    history.setShippingNo(shippingNo);
    history.setOrderMailHistoryId(DatabaseUtil.generateSequence(SequenceType.ORDER_MAIL_HISTORY_ID));

    ServiceResult sendMailResult = sendRespectiveMail(queue, history);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        result.addServiceError(error);
      }
    }

    return result;
  }

  // 2012-02-06 yyq add start desc:tmall发送邮件
  public ServiceResult sendTmallShippingReceivedMail(OrderContainer orderContainer, OrderContainer allContainer, Shop shop,
      CashierPayment payment, String shippingNo) {
    ServiceResultImpl result = new ServiceResultImpl();

    TmallOrderHeader tmallOrderHeader = orderContainer.getTmallOrderHeader();

    String toAddress = "";

    if (CustomerConstant.isCustomer(tmallOrderHeader.getCustomerCode())) {
      if (tmallOrderHeader.getCustomerCode() == null) {
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return result;
      }
      CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
      Customer customer = customerDao.load(tmallOrderHeader.getCustomerCode());
      if (customer == null) {
        result.addServiceError(MailingServiceErrorContent.NO_CUSTOMER_TO_SEND_MAIL);
        return result;
      }
      toAddress = customer.getEmail();
    } else {
      toAddress = tmallOrderHeader.getEmail();
    }
    orderContainer.getTmallOrderHeader().setEmail(toAddress);

    MailTemplateSuite mailTemplateSuite = createMailTemplate(shop.getShopCode(), MailType.SHIPPING_INFORMATION_TM.getValue(), 0L,
        tmallOrderHeader.getCustomerCode());
    // setTaxIncludedPriceDisplay(mailTemplateSuite);

    if (noSetMailTemplate(shop.getShopCode(), MailType.SHIPPING_INFORMATION_TM.getValue(), 0L)) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    setTmallShippingTag(expander, MailComposition.TM_SHIPPING_REPORT_MAIN, orderContainer, allContainer);
    setTmallShippingHeaderTag(expander, MailComposition.TM_SHIPPING_REPORT_HEADER, orderContainer,
        MailComposition.TM_SHIPPING_REPORT_DETAIL);
    setShopInfoTag(expander, MailComposition.TM_SHIPPING_REPORT_SIGN, shop);

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, toAddress, MailSendStatus.NOT_SENT.longValue(), expander
        .expandTemplate());

    OrderMailHistory history = new OrderMailHistory();
    history.setOrderNo(tmallOrderHeader.getOrderNo());
    history.setShippingNo(shippingNo);
    history.setOrderMailHistoryId(DatabaseUtil.generateSequence(SequenceType.ORDER_MAIL_HISTORY_ID));

    ServiceResult sendMailResult = sendRespectiveMail(queue, history);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        result.addServiceError(error);
      }
    }

    return result;
  }

  // 2012-02-06 yyq add end desc:tmall发送邮件

  private void setShopInfoTag(MailTemplateExpander expander, MailComposition composition, Shop shop) {
    MailTagDataList shopInfoTagList = new MailTagDataList();

    shopInfoTagList.add(ShopInfoTag.SHOP_NAME, shop.getShopName());
    String shopIntroducedUrl = "";
    WebshopConfig webshopConfig = DIContainer.getWebshopConfig();
    if (shop.getShopCode().equals(webshopConfig.getSiteShopCode())) {
      shopIntroducedUrl = webshopConfig.getTopPageUrl() + "/app/common/index";
    } else {
      shopIntroducedUrl = shop.getShopIntroducedUrl();
    }
    shopInfoTagList.add(ShopInfoTag.SHOP_INTRODUCED_URL, shopIntroducedUrl);
    shopInfoTagList.add(ShopInfoTag.EMAIL, shop.getEmail());
    shopInfoTagList.add(ShopInfoTag.POSTAL_CODE, WebUtil.convertPostalCode(shop.getPostalCode()));
    shopInfoTagList.add(ShopInfoTag.ADDRESS1, shop.getAddress1());
    shopInfoTagList.add(ShopInfoTag.ADDRESS2, shop.getAddress2());
    shopInfoTagList.add(ShopInfoTag.ADDRESS3, shop.getAddress3());
    // modify by V10-CH 170 start
    // shopInfoTagList.add(ShopInfoTag.ADDRESS4, shop.getAddress4());
    shopInfoTagList.add(ShopInfoTag.PHONE_NUMBER, shop.getPhoneNumber());
    shopInfoTagList.add(ShopInfoTag.MOBILE_NUMBER, shop.getMobileNumber());
    // modify by V10-CH end
    shopInfoTagList.add(ShopInfoTag.PERSON_IN_CHARGE, shop.getPersonInCharge());
    expander.addTagDataList(composition.getSubstitutionTag(), shopInfoTagList);

  }

  private void setShippingHeaderTag(MailTemplateExpander expander, MailComposition headerComposition,
      OrderContainer orderContainer, MailComposition detailComposition) {

    String detailValue = detailComposition.getSubstitutionTag().replace("#", "@");

    for (ShippingContainer shipping : orderContainer.getShippings()) {
      ShippingHeader shippingHeader = shipping.getShippingHeader();
      MailTagDataList shippingHeaderTagList = new MailTagDataList();

      String lang = getLanguageCode(shippingHeader.getCustomerCode());

      if (!StringUtil.isNullOrEmpty(lang)) {
        if (lang.equals("zh-cn")) {
          DIContainer.getLocaleContext().setCurrentLocale(Locale.CHINA);
        } else if (lang.equals("ja-jp")) {
          DIContainer.getLocaleContext().setCurrentLocale(Locale.JAPAN);
        } else {
          DIContainer.getLocaleContext().setCurrentLocale(Locale.US);
        }
      } else {
        DIContainer.getLocaleContext().setCurrentLocale(Locale.CHINA);
      }

      shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS_LAST_NAME, shippingHeader.getAddressLastName());
      // modify by V10-CH 170 start
      // shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS_FIRST_NAME,
      // shippingHeader.getAddressFirstName());
      // shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS_FIRST_NAME_KANA,
      // shippingHeader.getAddressFirstNameKana());
      // shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS_LAST_NAME_KANA,
      // shippingHeader.getAddressLastNameKana());
      // modify by V10-CH 170 end
      shippingHeaderTagList.add(ShippingHeaderTag.POSTAL_CODE, WebUtil.convertPostalCode(shippingHeader.getPostalCode()));
      shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS1, shippingHeader.getAddress1());
      shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS2, shippingHeader.getAddress2());
      shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS3, shippingHeader.getAddress3() + shippingHeader.getAddress4());
      shippingHeaderTagList.add(ShippingHeaderTag.DELIVERY_TYPE_NAME, StringUtil.coalesce(shippingHeader.getDeliveryCompanyName(),
          ""));
      // modify by V10-CH 170 start
      // shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS4,
      // StringUtil.coalesce(shippingHeader.getAddress4(), ""));
      // modify by V10-CH 170 end
      shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_CHARGE, StringUtil.coalesce(Price.getFormatPrice(shippingHeader
          .getShippingCharge())));
      if (shippingHeader.getDeliveryCompanyNo().equals("D006") && lang.equals("en-us")) {
        shippingHeaderTagList.add(ShippingHeaderTag.DELIVERY_TYPE_NAME, "クロネコヤマト便(雅玛多-Yamato Time Specify)");
      } else {
        shippingHeaderTagList.add(ShippingHeaderTag.DELIVERY_TYPE_NAME, StringUtil.coalesce(shippingHeader.getDeliveryCompanyName(),
        ""));
      }
      shippingHeaderTagList.add(ShippingHeaderTag.PHONE_NUMBER, shippingHeader.getPhoneNumber());
      // Add by V10-CH start
      shippingHeaderTagList.add(ShippingHeaderTag.MOBILE_NUMBER, shippingHeader.getMobileNumber());
      // Add by V10-CH end
      String deliveryAppointedDate = Messages.getString("service.impl.MailingServiceImpl.5");
      // 20111230 shen update start
      // if
      // (StringUtil.hasValue(DateUtil.toDateString(shippingHeader.getDeliveryAppointedDate())))
      // {
      // deliveryAppointedDate =
      // DateUtil.toDateString(shippingHeader.getDeliveryAppointedDate());
      // }
      if (StringUtil.hasValue(shippingHeader.getDeliveryAppointedDate())) {
        deliveryAppointedDate = shippingHeader.getDeliveryAppointedDate();
      }
      // 20111230 shen update end
      shippingHeaderTagList.add(ShippingHeaderTag.DELIVERY_APPOINTED_DATE, deliveryAppointedDate);
      if (StringUtil.isNullOrEmptyAnyOf(NumUtil.toString(shippingHeader.getDeliveryAppointedTimeStart()), NumUtil
          .toString(shippingHeader.getDeliveryAppointedTimeEnd()))) {
        shippingHeaderTagList.add(ShippingHeaderTag.DELIVERY_APPOINTED_TIME, Messages
            .getString("service.impl.MailingServiceImpl.6"));
      } else {
        shippingHeaderTagList.add(ShippingHeaderTag.DELIVERY_APPOINTED_TIME, NumUtil.toString(shippingHeader
            .getDeliveryAppointedTimeStart())
            + ":00～" + NumUtil.toString(shippingHeader.getDeliveryAppointedTimeEnd()) + ":00");
      }
      String arrivalDate = Messages.getString("service.impl.MailingServiceImpl.9");
      if (StringUtil.hasValue(DateUtil.toDateString(shippingHeader.getArrivalDate()))) {
        arrivalDate = DateUtil.toDateString(shippingHeader.getArrivalDate());
      }
      shippingHeaderTagList.add(ArrivalTag.ARRIVAL_DATE, arrivalDate);
      if (StringUtil.isNullOrEmptyAnyOf(NumUtil.toString(shippingHeader.getArrivalTimeStart()), NumUtil.toString(shippingHeader
          .getArrivalTimeEnd()))) {
        shippingHeaderTagList.add(ArrivalTag.ARRIVAL_TIME, Messages.getString("service.impl.MailingServiceImpl.10"));
      } else {
        shippingHeaderTagList.add(ArrivalTag.ARRIVAL_TIME, NumUtil.toString(shippingHeader.getArrivalTimeStart())
            + Messages.getString("service.impl.MailingServiceImpl.11") + NumUtil.toString(shippingHeader.getArrivalTimeEnd())
            + Messages.getString("service.impl.MailingServiceImpl.12"));
      }
      // 10.1.3 10137 追加 ここから
      // 20130311 add by yyq start
      String deliverySlipNoStr = "";
      if (!StringUtil.isNullOrEmpty(shippingHeader.getDeliverySlipNo())) {
        String dsn[] = shippingHeader.getDeliverySlipNo().split(";");
        if (dsn.length > 1) {
          for (int i = 0; i < dsn.length; i++) {
            if (dsn.length - i > 1) {
              deliverySlipNoStr += dsn[i] + "\r\n";
            } else {
              deliverySlipNoStr += dsn[i];
            }

          }
        } else {
          deliverySlipNoStr = shippingHeader.getDeliverySlipNo();
        }
      }
      shippingHeaderTagList.add(ShippingHeaderTag.DELIVERY_SLIP_NO, StringUtil.coalesce(deliverySlipNoStr, ""));
      // 20130311 add by yyq end
      // shippingHeaderTagList.add(ShippingHeaderTag.DELIVERY_SLIP_NO,
      // StringUtil.coalesce(shippingHeader.getDeliverySlipNo(), ""));
      // 10.1.3 10137 追加 ここまで

      // 配送先小計を取得（ （（販売価格＋ギフト価格）×商品数量）×配送先）＋配送手数料）

      BigDecimal shippingSumPrice = BigDecimal.ZERO;
      BigDecimal shippingTotalCommodity = BigDecimal.ZERO;
      BigDecimal shippingTotalGift = BigDecimal.ZERO;
      for (ShippingDetail detail : shipping.getShippingDetails()) {
        shippingSumPrice = shippingSumPrice.add(BigDecimalUtil.multiply(detail.getRetailPrice().add(detail.getGiftPrice()), detail
            .getPurchasingAmount()));
        shippingTotalCommodity = shippingTotalCommodity.add(BigDecimalUtil.multiply(detail.getRetailPrice(), detail
            .getPurchasingAmount()));
        shippingTotalGift = shippingTotalGift.add(BigDecimalUtil.multiply(detail.getGiftPrice(), detail.getPurchasingAmount()));
      }
      shippingSumPrice = shippingSumPrice.add(shippingHeader.getShippingCharge());
      shippingSumPrice = shippingSumPrice.subtract(orderContainer.getOrderHeader().getDiscountPrice());
      shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_SUM_PRICE, StringUtil.coalesce(Price.getFormatPrice(shippingSumPrice),
          ""));
      shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_COMMODITY_SUM_PRICE, StringUtil.coalesce(Price
          .getFormatPrice(shippingTotalCommodity)));
      shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_GIFT_SUM_PRICE, StringUtil.coalesce(Price
          .getFormatPrice(shippingTotalGift)));

      // 出荷明細情報は展開した情報を出荷ヘッダ部の項目タグとして処理する

      String shippingDetailText = setShippingDetailTag(expander, detailComposition, shipping.getShippingDetails(), orderContainer
          .getOrderDetails(), lang);
      MailTemplateTag shippingDetailTag = new ShippingDetailCustomTag(detailComposition.getName(), detailValue, shippingDetailText);
      shippingHeaderTagList.add(shippingDetailTag, shippingDetailText);

      expander.addTagDataList(headerComposition.getSubstitutionTag(), shippingHeaderTagList);
    }
    // 出荷ヘッダ部の出荷明細情報構造タグ部を項目タグとして設定する
    MailTemplateDetail template = expander.getMailTemplateDetail(headerComposition.getSubstitutionTag());
    String headerText = template.getMailText().replace(detailComposition.getSubstitutionTag(), detailValue);
    template.setMailText(headerText);
    expander.setMailTemplateDetail(template);

  }

  // 2012-02-06 yyq add start desc:tmall发送邮件
  private void setTmallShippingHeaderTag(MailTemplateExpander expander, MailComposition headerComposition,
      OrderContainer orderContainer, MailComposition detailComposition) {

    String detailValue = detailComposition.getSubstitutionTag().replace("#", "@");

    for (ShippingContainer shipping : orderContainer.getShippings()) {
      TmallShippingHeader tmallShippingHeader = shipping.getTmallShippingHeader();
      MailTagDataList shippingHeaderTagList = new MailTagDataList();

      shippingHeaderTagList.add(TmallShippingHeaderTag.ADDRESS_LAST_NAME, tmallShippingHeader.getAddressLastName());
      shippingHeaderTagList.add(TmallShippingHeaderTag.POSTAL_CODE, WebUtil.convertPostalCode(tmallShippingHeader.getPostalCode()));
      shippingHeaderTagList.add(TmallShippingHeaderTag.ADDRESS1, tmallShippingHeader.getAddress1());
      shippingHeaderTagList.add(TmallShippingHeaderTag.ADDRESS2, tmallShippingHeader.getAddress2());
      shippingHeaderTagList.add(TmallShippingHeaderTag.ADDRESS3, tmallShippingHeader.getAddress3());
      shippingHeaderTagList.add(TmallShippingHeaderTag.SHIPPING_CHARGE, StringUtil.coalesce(Price
          .getFormatPrice(tmallShippingHeader.getShippingCharge())));
      shippingHeaderTagList.add(TmallShippingHeaderTag.DELIVERY_TYPE_NAME, StringUtil.coalesce(tmallShippingHeader
          .getDeliveryCompanyName(), ""));
      shippingHeaderTagList.add(TmallShippingHeaderTag.PHONE_NUMBER, tmallShippingHeader.getPhoneNumber());
      shippingHeaderTagList.add(TmallShippingHeaderTag.MOBILE_NUMBER, tmallShippingHeader.getMobileNumber());
      String deliveryAppointedDate = Messages.getString("service.impl.MailingServiceImpl.5");
      if (StringUtil.hasValue(tmallShippingHeader.getDeliveryAppointedDate())) {
        deliveryAppointedDate = tmallShippingHeader.getDeliveryAppointedDate();
      }
      shippingHeaderTagList.add(TmallShippingHeaderTag.DELIVERY_APPOINTED_DATE, deliveryAppointedDate);
      if (StringUtil.isNullOrEmptyAnyOf(NumUtil.toString(tmallShippingHeader.getDeliveryAppointedTimeStart()), NumUtil
          .toString(tmallShippingHeader.getDeliveryAppointedTimeEnd()))) {
        shippingHeaderTagList.add(TmallShippingHeaderTag.DELIVERY_APPOINTED_TIME, Messages
            .getString("service.impl.MailingServiceImpl.6"));
      } else {
        shippingHeaderTagList.add(TmallShippingHeaderTag.DELIVERY_APPOINTED_TIME, NumUtil.toString(tmallShippingHeader
            .getDeliveryAppointedTimeStart())
            + Messages.getString("service.impl.MailingServiceImpl.7")
            + NumUtil.toString(tmallShippingHeader.getDeliveryAppointedTimeEnd())
            + Messages.getString("service.impl.MailingServiceImpl.8"));
      }
      String arrivalDate = Messages.getString("service.impl.MailingServiceImpl.9");
      if (StringUtil.hasValue(DateUtil.toDateString(tmallShippingHeader.getArrivalDate()))) {
        arrivalDate = DateUtil.toDateString(tmallShippingHeader.getArrivalDate());
      }
      shippingHeaderTagList.add(ArrivalTag.ARRIVAL_DATE, arrivalDate);
      if (StringUtil.isNullOrEmptyAnyOf(NumUtil.toString(tmallShippingHeader.getArrivalTimeStart()), NumUtil
          .toString(tmallShippingHeader.getArrivalTimeEnd()))) {
        shippingHeaderTagList.add(ArrivalTag.ARRIVAL_TIME, Messages.getString("service.impl.MailingServiceImpl.10"));
      } else {
        shippingHeaderTagList.add(ArrivalTag.ARRIVAL_TIME, NumUtil.toString(tmallShippingHeader.getArrivalTimeStart())
            + Messages.getString("service.impl.MailingServiceImpl.11") + NumUtil.toString(tmallShippingHeader.getArrivalTimeEnd())
            + Messages.getString("service.impl.MailingServiceImpl.12"));
      }

      // 20130311 add by yyq start
      String deliverySlipNoStr = "";
      if (!StringUtil.isNullOrEmpty(tmallShippingHeader.getDeliverySlipNo())) {
        String dsn[] = tmallShippingHeader.getDeliverySlipNo().split(";");
        if (dsn.length > 1) {
          for (int i = 0; i < dsn.length; i++) {
            if (dsn.length - i > 1) {
              deliverySlipNoStr += dsn[i] + "\r\n";
            } else {
              deliverySlipNoStr += dsn[i];
            }

          }
        } else {
          deliverySlipNoStr = tmallShippingHeader.getDeliverySlipNo();
        }
      }
      shippingHeaderTagList.add(TmallShippingHeaderTag.DELIVERY_SLIP_NO, StringUtil.coalesce(deliverySlipNoStr, ""));
      // 20130311 add by yyq end
      // shippingHeaderTagList.add(TmallShippingHeaderTag.DELIVERY_SLIP_NO,
      // StringUtil.coalesce(tmallShippingHeader
      // .getDeliverySlipNo(), ""));

      // 配送先小計を取得（ （（販売価格＋ギフト価格）×商品数量）×配送先）＋配送手数料）

      BigDecimal shippingSumPrice = BigDecimal.ZERO;
      BigDecimal shippingTotalCommodity = BigDecimal.ZERO;
      BigDecimal shippingTotalGift = BigDecimal.ZERO;
      for (TmallShippingDetail detail : shipping.getTmallShippingDetails()) {
        shippingSumPrice = shippingSumPrice.add(BigDecimalUtil.multiply(detail.getRetailPrice().add(detail.getGiftPrice()), detail
            .getPurchasingAmount()));
        shippingTotalCommodity = shippingTotalCommodity.add(BigDecimalUtil.multiply(detail.getRetailPrice(), detail
            .getPurchasingAmount()));
        shippingTotalGift = shippingTotalGift.add(BigDecimalUtil.multiply(detail.getGiftPrice(), detail.getPurchasingAmount()));
      }
      shippingSumPrice = shippingSumPrice.add(tmallShippingHeader.getShippingCharge());
      shippingHeaderTagList.add(TmallShippingHeaderTag.SHIPPING_SUM_PRICE, StringUtil.coalesce(Price
          .getFormatPrice(shippingSumPrice), ""));
      shippingHeaderTagList.add(TmallShippingHeaderTag.SHIPPING_COMMODITY_SUM_PRICE, StringUtil.coalesce(Price
          .getFormatPrice(shippingTotalCommodity)));
      shippingHeaderTagList.add(TmallShippingHeaderTag.SHIPPING_GIFT_SUM_PRICE, StringUtil.coalesce(Price
          .getFormatPrice(shippingTotalGift)));

      // 出荷明細情報は展開した情報を出荷ヘッダ部の項目タグとして処理する

      String shippingDetailText = setTmallShippingDetailTag(expander, detailComposition, shipping.getTmallShippingDetails(),
          orderContainer.getTmallIOrderDetails());
      MailTemplateTag shippingDetailTag = new ShippingDetailCustomTag(detailComposition.getName(), detailValue, shippingDetailText);
      shippingHeaderTagList.add(shippingDetailTag, shippingDetailText);

      expander.addTagDataList(headerComposition.getSubstitutionTag(), shippingHeaderTagList);
    }
    // 出荷ヘッダ部の出荷明細情報構造タグ部を項目タグとして設定する
    MailTemplateDetail template = expander.getMailTemplateDetail(headerComposition.getSubstitutionTag());
    String headerText = template.getMailText().replace(detailComposition.getSubstitutionTag(), detailValue);
    template.setMailText(headerText);
    expander.setMailTemplateDetail(template);

  }

  // 2012-02-06 yyq add end desc:tmall发送邮件

  private void setReservationShippingTag(MailTemplateExpander expander, MailComposition headerComposition,
      OrderContainer orderContainer, MailComposition detailComposition) {

    String detailValue = detailComposition.getSubstitutionTag().replace("#", "@");

    for (ShippingContainer shipping : orderContainer.getShippings()) {
      ShippingHeader shippingHeader = shipping.getShippingHeader();
      MailTagDataList shippingHeaderTagList = new MailTagDataList();

      shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS_LAST_NAME, shippingHeader.getAddressLastName());
      // modify by V10-CH 170 start
      // shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS_FIRST_NAME,
      // shippingHeader.getAddressFirstName());
      // shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS_FIRST_NAME_KANA,
      // shippingHeader.getAddressFirstNameKana());
      // shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS_LAST_NAME_KANA,
      // shippingHeader.getAddressLastNameKana());
      // modify by V10-CH 170 end
      shippingHeaderTagList.add(ShippingHeaderTag.POSTAL_CODE, WebUtil.convertPostalCode(shippingHeader.getPostalCode()));
      shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS1, shippingHeader.getAddress1());
      shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS2, shippingHeader.getAddress2());
      shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS3, shippingHeader.getAddress3());
      // modify by V10-CH 170 start
      // shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS4,
      // StringUtil.coalesce(shippingHeader.getAddress4(), ""));
      // modify by V10-CH 170 end
      shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_CHARGE, StringUtil.coalesce(Price.getFormatPrice(shippingHeader
          .getShippingCharge())));
      shippingHeaderTagList
          .add(ShippingHeaderTag.DELIVERY_TYPE_NAME, StringUtil.coalesce(shippingHeader.getDeliveryTypeName(), ""));
      shippingHeaderTagList.add(ShippingHeaderTag.PHONE_NUMBER, shippingHeader.getPhoneNumber());
      // Add by V10-CH start
      shippingHeaderTagList.add(ShippingHeaderTag.MOBILE_NUMBER, shippingHeader.getMobileNumber());
      // Add by V10-CH end
      // 配送先小計を取得（ （（販売価格＋ギフト価格）×商品数量）×配送先）＋配送手数料）

      BigDecimal shippingSumPrice = BigDecimal.ZERO;
      BigDecimal shippingTotalCommodity = BigDecimal.ZERO;
      BigDecimal shippingTotalGift = BigDecimal.ZERO;
      for (ShippingDetail detail : shipping.getShippingDetails()) {
        shippingSumPrice = shippingSumPrice.add(BigDecimalUtil.multiply(detail.getRetailPrice().add(detail.getGiftPrice()), detail
            .getPurchasingAmount()));
        shippingTotalCommodity = shippingTotalCommodity.add(BigDecimalUtil.multiply(detail.getRetailPrice(), detail
            .getPurchasingAmount()));
        shippingTotalGift = shippingTotalGift.add(BigDecimalUtil.multiply(detail.getGiftPrice(), detail.getPurchasingAmount()));
      }
      shippingSumPrice = shippingSumPrice.add(shippingHeader.getShippingCharge());
      shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_SUM_PRICE, StringUtil.coalesce(Price.getFormatPrice(shippingSumPrice),
          ""));
      shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_COMMODITY_SUM_PRICE, StringUtil.coalesce(Price
          .getFormatPrice(shippingTotalCommodity)));
      shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_GIFT_SUM_PRICE, StringUtil.coalesce(Price
          .getFormatPrice(shippingTotalGift)));

      // 出荷明細情報は展開した情報を出荷ヘッダ部の項目タグとして処理する

      String shippingDetailText = setShippingDetailTag(expander, detailComposition, shipping.getShippingDetails(), orderContainer
          .getOrderDetails(), "");
      MailTemplateTag shippingDetailTag = new ShippingDetailCustomTag(detailComposition.getName(), detailValue, shippingDetailText);
      shippingHeaderTagList.add(shippingDetailTag, shippingDetailText);

      expander.addTagDataList(headerComposition.getSubstitutionTag(), shippingHeaderTagList);
    }
    // 出荷ヘッダ部の出荷明細情報構造タグ部を項目タグとして設定する
    MailTemplateDetail template = expander.getMailTemplateDetail(headerComposition.getSubstitutionTag());
    String headerText = template.getMailText().replace(detailComposition.getSubstitutionTag(), detailValue);
    template.setMailText(headerText);
    expander.setMailTemplateDetail(template);

  }

  private String setShippingDetailTag(MailTemplateExpander expander, MailComposition composition,
      List<ShippingDetail> shippingDetails, List<OrderDetail> orderDetails, String lang) {

    MailTemplateDetail detail = expander.getMailTemplateDetail(composition.getSubstitutionTag());
    String shippingDetailText = "";

    for (ShippingDetail shippingDetail : shippingDetails) {

      // 受注明細から商品名を取得
      String commodityName = "";
      for (OrderDetail orderDetail : orderDetails) {
        if (shippingDetail.getShopCode().equals(orderDetail.getShopCode())
            && shippingDetail.getSkuCode().equals(orderDetail.getSkuCode())) {
          CommodityHeaderDao chd = DIContainer.getDao(CommodityHeaderDao.class);
          CommodityHeader ch = chd.load(orderDetail.getShopCode(), orderDetail.getCommodityCode());
          CommodityDetailDao cdd = DIContainer.getDao(CommodityDetailDao.class);
          CommodityDetail cd = cdd.load(orderDetail.getShopCode(), orderDetail.getSkuCode());
          String standard1Name = "";
          String standard2Name = "";
          if (!StringUtil.isNullOrEmpty(lang)) {
            if (lang.equals("zh-cn")) {
              commodityName = ch.getCommodityName();
              standard1Name = cd.getStandardDetail1Name();
              standard2Name = cd.getStandardDetail2Name();
            } else if (lang.equals("ja-jp")) {
              commodityName = ch.getCommodityNameJp();
              standard1Name = cd.getStandardDetail1NameJp();
              standard2Name = cd.getStandardDetail2NameJp();
            } else {
              commodityName = ch.getCommodityNameEn();
              standard1Name = cd.getStandardDetail1NameEn();
              standard2Name = cd.getStandardDetail2NameEn();
            }
          } else {
            commodityName = ch.getCommodityName();
            standard1Name = cd.getStandardDetail1Name();
            standard2Name = cd.getStandardDetail2Name();
          }
          if (StringUtil.hasValueAllOf(standard1Name, standard2Name)) {
            commodityName += "(" + standard1Name + "/" + standard2Name + ")";
          } else if (StringUtil.hasValue(standard1Name)) {
            commodityName += "(" + standard1Name + ")";
          }
          break;

          // commodityName = orderDetail.getCommodityName();
          // String standard1Name = orderDetail.getStandardDetail1Name();
          // String standard2Name = orderDetail.getStandardDetail2Name();
          // if (StringUtil.hasValueAllOf(standard1Name, standard2Name)) {
          // commodityName += "(" + standard1Name + "/" + standard2Name + ")";
          // } else if (StringUtil.hasValue(standard1Name)) {
          // commodityName += "(" + standard1Name + ")";
          // }
          // break;
        }
      }

      MailTagDataList shippingDetailTagList = new MailTagDataList();
      shippingDetailTagList.add(ShippingDetailTag.UNIT_NAME, StringUtil.coalesce(commodityName, ""));
      shippingDetailTagList.add(ShippingDetailTag.PURCHASING_AMOUNT, shippingDetail.getPurchasingAmount().toString());
      shippingDetailTagList.add(ShippingDetailTag.RETAIL_PRICE, StringUtil.coalesce(Price.getFormatPrice(shippingDetail
          .getRetailPrice()), ""));
      shippingDetailTagList.add(ShippingDetailTag.GIFT_NAME, StringUtil.coalesce(shippingDetail.getGiftName(), ""));
      shippingDetailTagList.add(ShippingDetailTag.GIFT_PRICE, StringUtil.coalesce(Price.getFormatPrice(shippingDetail
          .getGiftPrice())));

      shippingDetailText += expander.expandItemTags(detail.getMailText(), shippingDetailTagList) + "\r\n";
    }

    return shippingDetailText;

  }

  // tmall新增
  private String setTmallShippingDetailTag(MailTemplateExpander expander, MailComposition composition,
      List<TmallShippingDetail> shippingDetails, List<TmallOrderDetail> orderDetails) {

    MailTemplateDetail detail = expander.getMailTemplateDetail(composition.getSubstitutionTag());
    String shippingDetailText = "";

    for (TmallShippingDetail shippingDetail : shippingDetails) {

      // 受注明細から商品名を取得
      String commodityName = "";
      for (TmallOrderDetail orderDetail : orderDetails) {
        if (shippingDetail.getShopCode().equals(orderDetail.getShopCode())
            && shippingDetail.getSkuCode().equals(orderDetail.getSkuCode())) {
          commodityName = orderDetail.getCommodityName();
          String standard1Name = orderDetail.getStandardDetail1Name();
          String standard2Name = orderDetail.getStandardDetail2Name();
          if (StringUtil.hasValueAllOf(standard1Name, standard2Name)) {
            commodityName += "(" + standard1Name + "/" + standard2Name + ")";
          } else if (StringUtil.hasValue(standard1Name)) {
            commodityName += "(" + standard1Name + ")";
          }
          break;
        }
      }

      MailTagDataList shippingDetailTagList = new MailTagDataList();
      shippingDetailTagList.add(TmallShippingDetailTag.UNIT_NAME, StringUtil.coalesce(commodityName, ""));
      shippingDetailTagList.add(TmallShippingDetailTag.PURCHASING_AMOUNT, shippingDetail.getPurchasingAmount().toString());
      shippingDetailTagList.add(TmallShippingDetailTag.RETAIL_PRICE, StringUtil.coalesce(Price.getFormatPrice(shippingDetail
          .getRetailPrice()), ""));
      shippingDetailTagList.add(TmallShippingDetailTag.GIFT_NAME, StringUtil.coalesce(shippingDetail.getGiftName(), ""));
      shippingDetailTagList.add(TmallShippingDetailTag.GIFT_PRICE, StringUtil.coalesce(Price.getFormatPrice(shippingDetail
          .getGiftPrice())));

      shippingDetailText += expander.expandItemTags(detail.getMailText(), shippingDetailTagList) + "\r\n";
    }

    return shippingDetailText;

  }

  private void setPaymentConfirmTag(MailTemplateExpander expander, MailComposition composition, OrderHeader orderHeader,
      OrderSummary orderSummary, String toMailAddress) {
    MailTagDataList paymentConfirmTagList = new MailTagDataList();

    paymentConfirmTagList.add(PaymentConfirmTag.ORDER_NO, orderHeader.getOrderNo());
    paymentConfirmTagList.add(PaymentConfirmTag.LAST_NAME, orderHeader.getLastName());
    // modify by V10-CH 170 start
    // paymentConfirmTagList.add(PaymentConfirmTag.FIRST_NAME,
    // orderHeader.getFirstName());
    // paymentConfirmTagList.add(PaymentConfirmTag.LAST_NAME_KANA,
    // orderHeader.getLastNameKana());
    // paymentConfirmTagList.add(PaymentConfirmTag.FIRST_NAME_KANA,
    // orderHeader.getFirstNameKana());
    // modify by V10-CH 170 start
    paymentConfirmTagList.add(PaymentConfirmTag.EMAIL, toMailAddress);
    paymentConfirmTagList.add(PaymentConfirmTag.PHONE_NUMBER, orderHeader.getPhoneNumber());
    // Add by V10-CH start
    paymentConfirmTagList.add(PaymentConfirmTag.MOBILE_NUMBER, orderHeader.getMobileNumber());
    // Add by V10-CH end
    paymentConfirmTagList.add(PaymentConfirmTag.POSTAL_CODE, WebUtil.convertPostalCode(orderHeader.getPostalCode()));
    paymentConfirmTagList.add(PaymentConfirmTag.ADDRESS1, orderHeader.getAddress1());
    paymentConfirmTagList.add(PaymentConfirmTag.ADDRESS2, orderHeader.getAddress2());
    paymentConfirmTagList.add(PaymentConfirmTag.ADDRESS3, orderHeader.getAddress3());
    // modify by V10-CH 170 start
    // paymentConfirmTagList.add(PaymentConfirmTag.ADDRESS4,
    // StringUtil.coalesce(orderHeader.getAddress4(), ""));
    // modify by V10-CH 170 end
    paymentConfirmTagList.add(PaymentConfirmTag.PAYMENT_METHOD_NAME, StringUtil.coalesce(orderHeader.getPaymentMethodName(), ""));
    paymentConfirmTagList.add(PaymentConfirmTag.PAYMENT_COMMISSION, StringUtil.coalesce(Price.getFormatPrice(orderHeader
        .getPaymentCommission())));
    paymentConfirmTagList.add(PaymentConfirmTag.ORDER_DATETIME, DateUtil.toDateTimeString(orderHeader.getOrderDatetime()));
    paymentConfirmTagList.add(PaymentConfirmTag.SUM_COMMODITY_PRICE, StringUtil.coalesce(Price.getFormatPrice(orderSummary
        .getRetailPrice())));
    paymentConfirmTagList.add(PaymentConfirmTag.SUM_SHIPPING_CHARGE, StringUtil.coalesce(Price.getFormatPrice(orderSummary
        .getShippingCharge())));
    BigDecimal total = BigDecimalUtil.addAll(orderSummary.getRetailPrice(), orderSummary.getShippingCharge(), orderSummary
        .getGiftPrice(), orderHeader.getPaymentCommission());

    PointRuleDao dao = DIContainer.getDao(PointRuleDao.class);

    if (dao.loadAll().get(0).getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.ENABLED.longValue())
        || !BigDecimalUtil.isAbove(orderSummary.getUsedPoint(), BigDecimal.ZERO)) {
      StringWriter s = new StringWriter();
      PrintWriter out = new ExPrintWriter(s);
      out.println(Messages.getString("service.impl.MailingServiceImpl.13") + NumUtil.formatNumber(orderSummary.getUsedPoint())
          + Messages.getString("service.impl.MailingServiceImpl.14"));
      out.println(Messages.getString("service.impl.MailingServiceImpl.15")
          + StringUtil.coalesce(Price.getFormatPrice(PointUtil.getTotalPyamentPrice(total, orderSummary.getUsedPoint()))));
      paymentConfirmTagList.add(PaymentConfirmTag.SUM_ALL_PRICE, s.toString());
    } else {
      paymentConfirmTagList.add(PaymentConfirmTag.SUM_ALL_PRICE, Messages.getString("service.impl.MailingServiceImpl.16")
          + StringUtil.coalesce(Price.getFormatPrice(BigDecimalUtil.subtract(total, orderSummary.getUsedPoint()))));
    }

    paymentConfirmTagList.add(PaymentConfirmTag.SUM_GIFT_PRICE, StringUtil.coalesce(Price.getFormatPrice(orderSummary
        .getGiftPrice())));

    expander.addTagDataList(composition.getSubstitutionTag(), paymentConfirmTagList);

  }

  private void setPaymentReminderTag(MailTemplateExpander expander, MailComposition composition, OrderHeader orderHeader,
      OrderSummary orderSummary, String toMailAddress) {
    MailTagDataList paymentReminderTagList = new MailTagDataList();

    paymentReminderTagList.add(PaymentReminderTag.ORDER_NO, orderHeader.getOrderNo());
    paymentReminderTagList.add(PaymentReminderTag.LAST_NAME, orderHeader.getLastName());
    // modify by V10-CH 170 start
    // paymentReminderTagList.add(PaymentReminderTag.FIRST_NAME,
    // orderHeader.getFirstName());
    // paymentReminderTagList.add(PaymentReminderTag.LAST_NAME_KANA,
    // orderHeader.getLastNameKana());
    // paymentReminderTagList.add(PaymentReminderTag.FIRST_NAME_KANA,
    // orderHeader.getFirstNameKana());
    // modify by V10-CH 170 start
    paymentReminderTagList.add(PaymentReminderTag.EMAIL, toMailAddress);
    paymentReminderTagList.add(PaymentReminderTag.PHONE_NUMBER, orderHeader.getPhoneNumber());
    // Add by V10-CH start
    paymentReminderTagList.add(PaymentReminderTag.MONILE_NUMBER, orderHeader.getMobileNumber());
    // Add by V10-CH end
    paymentReminderTagList.add(PaymentReminderTag.POSTAL_CODE, WebUtil.convertPostalCode(orderHeader.getPostalCode()));
    paymentReminderTagList.add(PaymentReminderTag.ADDRESS1, orderHeader.getAddress1());
    paymentReminderTagList.add(PaymentReminderTag.ADDRESS2, orderHeader.getAddress2());
    paymentReminderTagList.add(PaymentReminderTag.ADDRESS3, orderHeader.getAddress3());
    // modify by V10-CH 170 start
    // paymentReminderTagList.add(PaymentReminderTag.ADDRESS4,
    // StringUtil.coalesce(orderHeader.getAddress4(), ""));
    // modify by V10-CH 170 end
    paymentReminderTagList.add(PaymentReminderTag.ORDER_DATETIME, DateUtil.toDateTimeString(orderHeader.getOrderDatetime()));
    paymentReminderTagList.add(PaymentReminderTag.SUM_COMMODITY_PRICE, StringUtil.coalesce(Price.getFormatPrice(orderSummary
        .getRetailPrice())));
    paymentReminderTagList.add(PaymentReminderTag.SUM_SHIPPING_CHARGE, StringUtil.coalesce(Price.getFormatPrice(orderSummary
        .getShippingCharge())));
    BigDecimal total = BigDecimalUtil.addAll(orderSummary.getRetailPrice(), orderSummary.getShippingCharge(), orderSummary
        .getGiftPrice(), orderHeader.getPaymentCommission());
    PointRuleDao dao = DIContainer.getDao(PointRuleDao.class);

    if (dao.loadAll().get(0).getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.ENABLED.longValue())
        || BigDecimalUtil.isAbove(orderSummary.getUsedPoint(), BigDecimal.ZERO)) {
      StringWriter s = new StringWriter();
      PrintWriter out = new ExPrintWriter(s);
      out.println(Messages.getString("service.impl.MailingServiceImpl.17") + NumUtil.formatNumber(orderSummary.getUsedPoint())
          + Messages.getString("service.impl.MailingServiceImpl.18"));
      out.println(Messages.getString("service.impl.MailingServiceImpl.19")
          + StringUtil.coalesce(Price.getFormatPrice(BigDecimalUtil.subtract(total, orderSummary.getUsedPoint()))));
      paymentReminderTagList.add(PaymentReminderTag.SUM_ALL_PRICE, s.toString());
    } else {
      paymentReminderTagList.add(PaymentReminderTag.SUM_ALL_PRICE, Messages.getString("service.impl.MailingServiceImpl.20")
          + StringUtil.coalesce(Price.getFormatPrice(BigDecimalUtil.subtract(total, orderSummary.getUsedPoint()))));
    }

    paymentReminderTagList.add(PaymentReminderTag.SUM_GIFT_PRICE, StringUtil.coalesce(Price.getFormatPrice(orderSummary
        .getGiftPrice())));

    expander.addTagDataList(composition.getSubstitutionTag(), paymentReminderTagList);

  }

  private void setOrderHeaderTag(MailTemplateExpander expander, MailComposition composition, OrderContainer orderContainer) {

    MailTagDataList orderHeaderTagList = new MailTagDataList();
    orderHeaderTagList.add(OrderHeaderTag.ORDER_NO, orderContainer.getOrderHeader().getOrderNo());
    orderHeaderTagList.add(OrderHeaderTag.ORDER_DATETIME, DateUtil.toDateTimeString(orderContainer.getOrderHeader()
        .getOrderDatetime()));
    orderHeaderTagList.add(OrderHeaderTag.LAST_NAME, orderContainer.getOrderHeader().getLastName());
    orderHeaderTagList.add(OrderHeaderTag.LAST_NAME, orderContainer.getOrderHeader().getLastName());
    orderHeaderTagList.add(OrderHeaderTag.EMAIL, orderContainer.getOrderHeader().getEmail());
    orderHeaderTagList.add(OrderHeaderTag.LAST_NAME, orderContainer.getOrderHeader().getLastName());
    orderHeaderTagList.add(OrderHeaderTag.POSTAL_CODE, WebUtil.convertPostalCode(orderContainer.getOrderHeader().getPostalCode()));
    orderHeaderTagList.add(OrderHeaderTag.ADDRESS1, orderContainer.getOrderHeader().getAddress1());
    orderHeaderTagList.add(OrderHeaderTag.ADDRESS2, orderContainer.getOrderHeader().getAddress2());
    orderHeaderTagList.add(OrderHeaderTag.ADDRESS3, orderContainer.getOrderHeader().getAddress3());
    orderHeaderTagList.add(OrderHeaderTag.PHONE_NUMBER, orderContainer.getOrderHeader().getPhoneNumber());
    orderHeaderTagList.add(OrderHeaderTag.MOBILE_NUMBER, orderContainer.getOrderHeader().getMobileNumber());
    orderHeaderTagList.add(OrderHeaderTag.MESSAGE, orderContainer.getOrderHeader().getMessage());
    // ご注文合計金額（全配送先小計＋支払い手数料）
    BigDecimal orderTotalPrice = BigDecimal.ZERO;
    BigDecimal orderTotalAcquiredPoint = BigDecimal.ZERO;
    BigDecimal orderTotalCommodityprice = BigDecimal.ZERO;
    BigDecimal orderTotalShippingCharge = BigDecimal.ZERO;
    BigDecimal orderTotalGiftPrice = BigDecimal.ZERO;
    for (ShippingContainer shipping : orderContainer.getShippings()) {
      ShippingHeader header = shipping.getShippingHeader();
      for (ShippingDetail detail : shipping.getShippingDetails()) {
        BigDecimal retailPrice = detail.getRetailPrice();
        BigDecimal giftPrice = detail.getGiftPrice();
        orderTotalPrice = orderTotalPrice.add(BigDecimalUtil.multiply(retailPrice.add(giftPrice), detail.getPurchasingAmount()));
        orderTotalCommodityprice = orderTotalCommodityprice.add(BigDecimalUtil.multiply(retailPrice, detail.getPurchasingAmount()));
        orderTotalGiftPrice = orderTotalGiftPrice.add(BigDecimalUtil.multiply(giftPrice, detail.getPurchasingAmount()));
      }
      orderTotalPrice = orderTotalPrice.add(header.getShippingCharge());
      orderTotalAcquiredPoint = BigDecimalUtil.add(orderTotalAcquiredPoint, header.getAcquiredPoint());
      orderTotalShippingCharge = orderTotalShippingCharge.add(header.getShippingCharge());
    }
    orderTotalPrice = orderTotalPrice.add(orderContainer.getOrderHeader().getPaymentCommission());
    // - 优惠金额
    orderTotalPrice = orderTotalPrice.subtract(orderContainer.getOrderHeader().getDiscountPrice());
    if (orderContainer.getOrderHeader().getGiftCardUsePrice() != null) {
      orderTotalPrice = orderTotalPrice.subtract(orderContainer.getOrderHeader().getGiftCardUsePrice());
    }
    if (orderContainer.getOrderHeader().getOuterCardPrice() != null ) {
      orderTotalPrice = orderTotalPrice.add(orderContainer.getOrderHeader().getOuterCardPrice());
    }

    // お支払い合計金額
    // modify by V10-CH start
    BigDecimal paymentPrice = PointUtil.getTotalPyamentPrice(orderTotalPrice, orderContainer.getOrderHeader().getUsedPoint());
    orderHeaderTagList.add(OrderHeaderTag.PAYMENT_TOTAL_PRICE, StringUtil.coalesce(Price.getFormatPrice(paymentPrice)));

    orderHeaderTagList.add(OrderHeaderTag.COMMODITY_TOTAL_PRICE, StringUtil
        .coalesce(Price.getFormatPrice(orderTotalCommodityprice)));
    orderHeaderTagList.add(OrderHeaderTag.SHIPPING_CHARGE_TOTAL_PRICE, StringUtil.coalesce(Price
        .getFormatPrice(orderTotalShippingCharge)));
    orderHeaderTagList.add(OrderHeaderTag.GIFT_TOTAL_PRICE, StringUtil.coalesce(Price.getFormatPrice(orderTotalGiftPrice)));
    orderHeaderTagList.add(OrderHeaderTag.COMMISSION_PRICE, StringUtil.coalesce(Price.getFormatPrice(orderContainer
        .getOrderHeader().getPaymentCommission())));
    orderHeaderTagList.add(OrderHeaderTag.COUPON_TOTAL_PRICE, StringUtil.coalesce(Price.getFormatPrice(orderContainer
        .getOrderHeader().getDiscountPrice())));
    orderHeaderTagList.add(OrderHeaderTag.Gift_CARD_USE_PRICE, StringUtil.coalesce(Price.getFormatPrice(orderContainer
        .getOrderHeader().getGiftCardUsePrice())));
    orderHeaderTagList.add(OrderHeaderTag.OUTER_CARD_PRICE, StringUtil.coalesce(Price.getFormatPrice(orderContainer
        .getOrderHeader().getOuterCardPrice())));
    expander.addTagDataList(composition.getSubstitutionTag(), orderHeaderTagList);

  }

  private void setShippingTag(MailTemplateExpander expander, MailComposition composition, OrderContainer orderContainer,
      OrderContainer allContainer) {

    MailTagDataList shippingTagList = new MailTagDataList();
    shippingTagList.add(ShippingTag.ORDER_NO, orderContainer.getOrderHeader().getOrderNo());
    shippingTagList.add(ShippingTag.ORDER_DATETIME, DateUtil.toDateTimeString(orderContainer.getOrderHeader().getOrderDatetime()));
    shippingTagList.add(ShippingTag.LAST_NAME, orderContainer.getOrderHeader().getLastName());
    shippingTagList.add(ShippingTag.LAST_NAME, orderContainer.getOrderHeader().getLastName());
    shippingTagList.add(ShippingTag.EMAIL, orderContainer.getOrderHeader().getEmail());
    shippingTagList.add(ShippingTag.LAST_NAME, orderContainer.getOrderHeader().getLastName());
    shippingTagList.add(ShippingTag.POSTAL_CODE, WebUtil.convertPostalCode(orderContainer.getOrderHeader().getPostalCode()));
    shippingTagList.add(ShippingTag.ADDRESS1, orderContainer.getOrderHeader().getAddress1());
    shippingTagList.add(ShippingTag.ADDRESS2, orderContainer.getOrderHeader().getAddress2());
    shippingTagList.add(ShippingTag.ADDRESS3, orderContainer.getOrderHeader().getAddress3());
    shippingTagList.add(ShippingTag.PAYMENT_METHOD_NAME, orderContainer.getOrderHeader().getPaymentMethodName());
    shippingTagList.add(ShippingTag.PHONE_NUMBER, orderContainer.getOrderHeader().getPhoneNumber());
    shippingTagList.add(ShippingTag.MOBILE_NUMBER, orderContainer.getOrderHeader().getMobileNumber());
    shippingTagList.add(ShippingTag.MESSAGE, orderContainer.getOrderHeader().getMessage());
    shippingTagList.add(ShippingTag.COMMISSION_PRICE, StringUtil.coalesce(Price.getFormatPrice(orderContainer.getOrderHeader()
        .getPaymentCommission())));
    shippingTagList.add(ShippingTag.DISCOUNT_PRICE, Price.getFormatPrice(orderContainer.getOrderHeader().getDiscountPrice()));
    shippingTagList.add(ShippingTag.GIFT_CARD_USE_PRICE, Price
        .getFormatPrice(orderContainer.getOrderHeader().getGiftCardUsePrice()));
    shippingTagList.add(ShippingTag.OUTER_CARD_PRICE, Price
        .getFormatPrice(orderContainer.getOrderHeader().getOuterCardPrice()));
    // ご注文合計金額（全配送先小計＋支払い手数料）
    BigDecimal orderTotalPrice = BigDecimal.ZERO;
    for (ShippingContainer shipping : allContainer.getShippings()) {
      ShippingHeader header = shipping.getShippingHeader();
      for (ShippingDetail detail : shipping.getShippingDetails()) {
        BigDecimal retailPrice = detail.getRetailPrice();
        BigDecimal giftPrice = detail.getGiftPrice();
        orderTotalPrice = orderTotalPrice.add(BigDecimalUtil.multiply(retailPrice.add(giftPrice), detail.getPurchasingAmount()));
      }
      orderTotalPrice = orderTotalPrice.add(header.getShippingCharge());
    }
    orderTotalPrice = orderTotalPrice.add(orderContainer.getOrderHeader().getPaymentCommission());
    // お支払い合計金額
    BigDecimal paymentPrice = PointUtil.getTotalPyamentPrice(orderTotalPrice, orderContainer.getOrderHeader().getUsedPoint());
    BigDecimal disPrice = orderContainer.getOrderHeader().getDiscountPrice();
    if (disPrice != null) {
      paymentPrice = paymentPrice.subtract(disPrice);
    }
    BigDecimal giftPrice = orderContainer.getOrderHeader().getGiftCardUsePrice();
    if (giftPrice != null) {
      paymentPrice = paymentPrice.subtract(giftPrice);
    }
    BigDecimal outerPrice = orderContainer.getOrderHeader().getOuterCardPrice();
    if (outerPrice != null) {
      paymentPrice = paymentPrice.add(outerPrice);
    }
    
    shippingTagList.add(ShippingTag.PAYMENT_TOTAL_PRICE, StringUtil.coalesce(Price.getFormatPrice(paymentPrice), ""));

    expander.addTagDataList(composition.getSubstitutionTag(), shippingTagList);

  }

  // 2012-02-06 yyq add start desc:tmall发送邮件
  private void setTmallShippingTag(MailTemplateExpander expander, MailComposition composition, OrderContainer orderContainer,
      OrderContainer allContainer) {

    MailTagDataList shippingTagList = new MailTagDataList();
    shippingTagList.add(TmallShippingTag.ORDER_NO, orderContainer.getTmallOrderHeader().getCustomerCode());
    shippingTagList.add(TmallShippingTag.ORDER_DATETIME, DateUtil.toDateTimeString(orderContainer.getTmallOrderHeader()
        .getOrderDatetime()));
    shippingTagList.add(TmallShippingTag.LAST_NAME, orderContainer.getTmallOrderHeader().getLastName());
    shippingTagList.add(TmallShippingTag.LAST_NAME, orderContainer.getTmallOrderHeader().getLastName());
    shippingTagList.add(TmallShippingTag.EMAIL, orderContainer.getTmallOrderHeader().getEmail());
    shippingTagList.add(TmallShippingTag.LAST_NAME, orderContainer.getTmallOrderHeader().getLastName());
    shippingTagList.add(TmallShippingTag.POSTAL_CODE, WebUtil.convertPostalCode(orderContainer.getTmallOrderHeader()
        .getPostalCode()));
    shippingTagList.add(TmallShippingTag.ADDRESS1, orderContainer.getTmallOrderHeader().getAddress1());
    shippingTagList.add(TmallShippingTag.ADDRESS2, orderContainer.getTmallOrderHeader().getAddress2());
    shippingTagList.add(TmallShippingTag.ADDRESS3, orderContainer.getTmallOrderHeader().getAddress3());
    shippingTagList.add(TmallShippingTag.ADDRESS4, orderContainer.getTmallOrderHeader().getAddress4());
    shippingTagList.add(TmallShippingTag.PHONE_NUMBER, orderContainer.getTmallOrderHeader().getPhoneNumber());
    shippingTagList.add(TmallShippingTag.MOBILE_NUMBER, orderContainer.getTmallOrderHeader().getMobileNumber());
    shippingTagList.add(TmallShippingTag.MESSAGE, orderContainer.getTmallOrderHeader().getMessage());
    shippingTagList.add(TmallShippingTag.COMMISSION_PRICE, StringUtil.coalesce(Price.getFormatPrice(orderContainer
        .getTmallOrderHeader().getPaymentCommission())));
    shippingTagList.add(TmallShippingTag.PAYMENT_METHOD_NAME, orderContainer.getTmallOrderHeader().getPaymentMethodName());
    // 2012-04-01 yyq add start
    BigDecimal paymentPrice = BigDecimal.ZERO;
    BigDecimal tmallShopPrice = BigDecimal.ZERO;
    BigDecimal tmallCityPrice = BigDecimal.ZERO;
    BigDecimal tmallUsedPoint = BigDecimal.ZERO;
    BigDecimal tmallMjsPrice = BigDecimal.ZERO;
    // 店铺优惠金额、商城优惠金额、商城使用积分、优惠金额
    tmallShopPrice = BigDecimalUtil.add(orderContainer.getTmallOrderHeader().getDiscountPrice(), orderContainer
        .getTmallOrderHeader().getAdjustFee());
    tmallCityPrice = orderContainer.getTmallOrderHeader().getTmallDiscountPrice();
    tmallUsedPoint = orderContainer.getTmallOrderHeader().getPointConvertPrice();
    tmallMjsPrice = orderContainer.getTmallOrderHeader().getMjsDiscount();
    if (tmallMjsPrice == null) {
      tmallMjsPrice = BigDecimal.ZERO;
    }
    shippingTagList.add(TmallShippingTag.TMALL_SHOP_PRICE, StringUtil.coalesce(Price.getFormatPrice(tmallShopPrice), ""));
    shippingTagList.add(TmallShippingTag.TMALL_CITY_PRICE, StringUtil.coalesce(Price.getFormatPrice(tmallCityPrice), ""));
    shippingTagList.add(TmallShippingTag.TMALL_USED_POINT, StringUtil.coalesce(Price.getFormatPrice(tmallUsedPoint), ""));
    shippingTagList.add(TmallShippingTag.TMALL_DISCOUNT_PRICE, StringUtil.coalesce(Price.getFormatPrice(BigDecimalUtil.add(
        tmallShopPrice, tmallCityPrice)), ""));
    shippingTagList.add(TmallShippingTag.TMALL_MJS_DISCOUNT, StringUtil.coalesce(Price.getFormatPrice(tmallMjsPrice), ""));
    // 2012-04-01 yyq add end

    BigDecimal orderTotalPrice = BigDecimal.ZERO;
    for (ShippingContainer shipping : allContainer.getShippings()) {
      TmallShippingHeader header = shipping.getTmallShippingHeader();
      for (TmallShippingDetail detail : shipping.getTmallShippingDetails()) {
        BigDecimal retailPrice = detail.getRetailPrice();
        BigDecimal giftPrice = detail.getGiftPrice();
        orderTotalPrice = orderTotalPrice.add(BigDecimalUtil.multiply(retailPrice.add(giftPrice), detail.getPurchasingAmount()));
      }
      orderTotalPrice = orderTotalPrice.add(header.getShippingCharge());
    }
    orderTotalPrice = orderTotalPrice.add(orderContainer.getTmallOrderHeader().getPaymentCommission());

    paymentPrice = orderTotalPrice.subtract(BigDecimalUtil.add(BigDecimalUtil.add(tmallShopPrice, tmallCityPrice), tmallUsedPoint));
    paymentPrice = paymentPrice.subtract(tmallMjsPrice);
    shippingTagList.add(ShippingTag.PAYMENT_TOTAL_PRICE, StringUtil.coalesce(Price.getFormatPrice(paymentPrice), ""));

    expander.addTagDataList(composition.getSubstitutionTag(), shippingTagList);

  }

  // 2012-02-06 yyq add end desc:tmall发送邮件

  private void setPaymentTag(MailTemplateExpander expander, MailComposition composition, OrderHeader header, CashierPayment pay) {

    MailTagDataList paymentTagList = new MailTagDataList();
    paymentTagList.add(PaymentTag.PAYMENT_METHOD_NAME, header.getPaymentMethodName());
    paymentTagList.add(PaymentTag.PAYMENT_COMMISSION, StringUtil.coalesce(Price.getFormatPrice(header.getPaymentCommission())));
    // modify by V10-CH 170 start
    // paymentTagList.add(PaymentTag.PAYMENT_COMMISSION_TAX_PRICE,
    // StringUtil.coalesce(Price.getFormatPrice(header
    // .getPaymentCommissionTax())));
    // modify by V10-CH 170 start
    paymentTagList.add(PaymentTag.PAYMENT_DETAIL, getPaymentDetailText(composition, pay, header));
    expander.addTagDataList(composition.getSubstitutionTag(), paymentTagList);

  }

  private String getPaymentDetailText(MailComposition composition, CashierPayment payment, OrderHeader header) {
    // StringBuffer bufferText = new StringBuffer("");
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);

    if (payment.getSelectPayment().isBanking()) {
      BankDao bankDao = DIContainer.getDao(BankDao.class);
      List<Bank> bankList = bankDao.findByQuery(new SimpleQuery(ShopManagementSimpleSql.LOAD_BANK_LIST, payment.getShopCode(),
          payment.getPaymentMethodCode()));

      int bankSize = bankList.size();

      out.println(Messages.getString("service.impl.MailingServiceImpl.29"));

      int i = 1;
      for (Bank bank : bankList) {
        if (bankSize <= 1) {
          out.println(Messages.getString("service.impl.MailingServiceImpl.30"));
        } else {
          out.println(Messages.getString("service.impl.MailingServiceImpl.31") + Integer.valueOf(i).toString());
        }
        out.println(Messages.getString("service.impl.MailingServiceImpl.32") + StringUtil.coalesce(bank.getBankName(), ""));
        out.println(Messages.getString("service.impl.MailingServiceImpl.33") + StringUtil.coalesce(bank.getBankBranchName(), ""));
        // out.println("口座番号：（" +
        // StringUtil.coalesce(AccountType.fromValue(bank.getAccountType()).getName(),
        // "") + "）"
        // + StringUtil.coalesce(bank.getAccountNo(), ""));
        out.println(MessageFormat.format(Messages.getString("service.impl.MailingServiceImpl.37"), StringUtil.coalesce(AccountType
            .fromValue(bank.getAccountType()).getName(), ""), StringUtil.coalesce(bank.getAccountNo(), "")));
        // out.println("口座名義：" + StringUtil.coalesce(bank.getAccountName()));
        // out.println(MessageFormat.format(Messages.getString("service.impl.MailingServiceImpl.38"),
        // StringUtil.coalesce(bank.getAccountName())));
        out.println();
        i += 1;
      }
      if (header.getPaymentLimitDate() != null) {
        if (header.getPaymentLimitDate().before(DateUtil.fromString(DateUtil.getSysdateString()))) {
          // 支払期限が切れている場合
          // out.println("支払期限日:支払期限が過ぎています。");
          out.println(Messages.getString("service.impl.MailingServiceImpl.39"));
        } else {
          // out.println("支払期限日:" +
          // DateUtil.toDateString(header.getPaymentLimitDate()));
          out.println(MessageFormat.format(Messages.getString("service.impl.MailingServiceImpl.40"), DateUtil.toDateString(header
              .getPaymentLimitDate())));
        }
      }
    } else if (payment.getSelectPayment().isCreditcard()) {
      // クレジットカードの場合、支払方法名だけの表示だけでよいので追記せず
      out.println("");

    } else if (payment.getSelectPayment().isCvsPayment()) {
      String cvsName = "";
      CashierPaymentTypeBase cashier = new CashierPaymentTypeCvs();
      List<CodeAttribute> cvsList = PaymentProviderManager.getCodeList(cashier);
      for (CodeAttribute cvs : cvsList) {
        if (cvs.getValue().equals(header.getCvsCode())) {
          cvsName = cvs.getName();
        }
      }
      // out.println("支払明細");
      out.println(Messages.getString("service.impl.MailingServiceImpl.41"));
      // out.println("コンビニ名称:" + StringUtil.coalesce(cvsName, ""));
      out.println(MessageFormat.format(Messages.getString("service.impl.MailingServiceImpl.42"), StringUtil.coalesce(cvsName, "")));
      // out.println("コンビニ払込票番号:" +
      // StringUtil.coalesce(header.getPaymentReceiptNo(), ""));
      out.println(MessageFormat.format(Messages.getString("service.impl.MailingServiceImpl.43"), StringUtil.coalesce(header
          .getPaymentReceiptNo(), "")));
      if (StringUtil.hasValue(header.getPaymentReceiptUrl())) {
        // out.println("コンビニ払込票URL:" +
        // StringUtil.coalesce(header.getPaymentReceiptUrl(), ""));
        out.println(MessageFormat.format(Messages.getString("service.impl.MailingServiceImpl.44"), StringUtil.coalesce(header
            .getPaymentReceiptUrl(), "")));
      }
      if (header.getPaymentLimitDate().before(DateUtil.fromString(DateUtil.getSysdateString()))) {
        // 支払期限が切れている場合
        // out.println("支払期限日:支払期限が過ぎています。");
        out.println(Messages.getString("service.impl.MailingServiceImpl.45"));
      } else {
        // out.println("支払期限日:" +
        // DateUtil.toDateString(header.getPaymentLimitDate()));
        out.println(MessageFormat.format(Messages.getString("service.impl.MailingServiceImpl.46"), DateUtil.toDateString(header
            .getPaymentLimitDate())));
      }

    } else if (payment.getSelectPayment().isDigitalCash()) {
      String digitalName = "";
      CashierPaymentTypeBase cashier = new CashierPaymentTypeDigitalCash();
      List<CodeAttribute> digitalList = PaymentProviderManager.getCodeList(cashier);
      for (CodeAttribute digital : digitalList) {
        if (digital.getValue().equals(header.getDigitalCashType())) {
          digitalName = digital.getName();
        }
      }
      // out.println("支払明細");
      out.println(Messages.getString("service.impl.MailingServiceImpl.47"));
      // out.println("電子マネー種類:" + StringUtil.coalesce(digitalName, ""));
      out.println(MessageFormat.format(Messages.getString("service.impl.MailingServiceImpl.48"), StringUtil.coalesce(digitalName,
          "")));
      if (header.getPaymentLimitDate().before(DateUtil.fromString(DateUtil.getSysdateString()))) {
        // 支払期限が切れている場合
        // out.println("支払期限日:支払期限が過ぎています。");
        out.println(Messages.getString("service.impl.MailingServiceImpl.49"));
      } else {
        // out.println("支払期限日:" +
        // DateUtil.toDateString(header.getPaymentLimitDate()));
        out.println(MessageFormat.format(Messages.getString("service.impl.MailingServiceImpl.50"), DateUtil.toDateString(header
            .getPaymentLimitDate())));
      }

    } else if (payment.getSelectPayment().isCashOnDelivery()) {

      // 代引の場合、支払方法名だけの表示だけでよいので追記せず
      out.println("");
    } else if (payment.getSelectPayment().isPointInFull()) {

      // 全額ポイント払いの場合、支払方法名だけの表示だけでよいので追記せず
      out.println("");
    } else if (payment.getSelectPayment().isNoPayment()) {

      // 支払不要の場合、支払方法名だけの表示だけでよいので追記せず
      out.println("");
    }

    return s.toString();
  }

  // private MailTemplateSuite createMailTemplate(String shopCode, String
  // mailType, Long mailTemplateNo) {
  // MailTemplateSuite suite = new MailTemplateSuite();
  //
  // MailTemplateHeaderDao dao =
  // DIContainer.getDao(MailTemplateHeaderDao.class);
  // MailTemplateHeader header = dao.load(shopCode, mailType, mailTemplateNo);
  // suite.setMailTemplateHeader(header);
  //
  // Query query = new
  // SimpleQuery(ShopManagementSimpleSql.LOAD_MAIL_TEMPLATE_DETAIL, shopCode,
  // mailType, mailTemplateNo);
  // List<MailTemplateDetail> detailList = DatabaseUtil.loadAsBeanList(query,
  // MailTemplateDetail.class);
  // suite.setMailTemplateDetail(detailList);
  //
  // return suite;
  // }

  private RespectiveMailqueue createMailQueue(MailTemplateSuite mailTemplateSuite, String toAddress, Long mailSendStatus,
      String mailText) {
    RespectiveMailqueue queue = new RespectiveMailqueue();

    queue.setMailType(mailTemplateSuite.getMailTemplateHeader().getMailType());
    queue.setMailSubject(mailTemplateSuite.getMailTemplateHeader().getMailSubject());
    queue.setMailSenderName(mailTemplateSuite.getMailTemplateHeader().getMailSenderName());
    queue.setFromAddress(mailTemplateSuite.getMailTemplateHeader().getFromAddress());
    queue.setBccAddress(mailTemplateSuite.getMailTemplateHeader().getBccAddress());
    queue.setToAddress(toAddress);
    queue.setMailSendStatus(mailSendStatus);
    queue.setMailContentType(mailTemplateSuite.getMailTemplateHeader().getMailContentType());
    queue.setMailText(mailText);

    return queue;
  }

  private BroadcastMailqueueSuite createBroadcastMailqueueSuite(MailTemplateSuite mailTemplateSuite, List<Customer> customerList,
      Long mailSendStatus, String mailText) {
    BroadcastMailqueueSuite suite = new BroadcastMailqueueSuite();

    BroadcastMailqueueHeader header = new BroadcastMailqueueHeader();
    Long mailQueueSeq = DatabaseUtil.generateSequence(SequenceType.RESPECTIVE_MAIL_QUEUE_ID);
    header.setMailQueueId(mailQueueSeq);
    header.setMailType(mailTemplateSuite.getMailTemplateHeader().getMailType());
    header.setMailSubject(mailTemplateSuite.getMailTemplateHeader().getMailSubject());
    header.setMailSenderName(mailTemplateSuite.getMailTemplateHeader().getMailSenderName());
    header.setFromAddress(mailTemplateSuite.getMailTemplateHeader().getFromAddress());
    header.setMailContentType(mailTemplateSuite.getMailTemplateHeader().getMailContentType());
    header.setMailSendStatus(mailSendStatus);
    header.setMailText(mailText);

    List<BroadcastMailqueueDetail> detailList = new ArrayList<BroadcastMailqueueDetail>();

    for (Customer customer : customerList) {
      // 10.1.6 10253 修正 ここから
      // BroadcastMailqueueDetail detail = new BroadcastMailqueueDetail();
      // detail.setMailQueueId(header.getMailQueueId());
      // detail.setCustomerCode(customer.getCustomerCode());
      // detail.setBccAddress(mailTemplateSuite.getMailTemplateHeader().getBccAddress());
      // detail.setToAddress(customer.getEmail());
      // detail.setMailSendStatus(mailSendStatus);
      // detailList.add(detail);
      // 游客身份
      if (customer.getLoginLockedFlg() == null) {
        BroadcastMailqueueDetail detail = new BroadcastMailqueueDetail();
        detail.setMailQueueId(header.getMailQueueId());
        detail.setCustomerCode(customer.getCustomerCode());
        detail.setBccAddress(mailTemplateSuite.getMailTemplateHeader().getBccAddress());
        detail.setToAddress(customer.getEmail());
        detail.setMailSendStatus(mailSendStatus);
        detailList.add(detail);
      } else {
        if (customer.getLoginLockedFlg().equals(NumUtil.toLong(LoginLockedFlg.UNLOCKED.getValue()))) {
          BroadcastMailqueueDetail detail = new BroadcastMailqueueDetail();
          detail.setMailQueueId(header.getMailQueueId());
          detail.setCustomerCode(customer.getCustomerCode());
          detail.setBccAddress(mailTemplateSuite.getMailTemplateHeader().getBccAddress());
          detail.setToAddress(customer.getEmail());
          detail.setMailSendStatus(mailSendStatus);
          detailList.add(detail);
        } else {
          Logger logger = Logger.getLogger(this.getClass());
          logger.info(Messages.getString("service.impl.MailingServiceImpl.51") + customer.getCustomerCode()
              + Messages.getString("service.impl.MailingServiceImpl.52"));
        }
      }
      // 10.1.6 10253 修正 ここまで
    }
    suite.setHeader(header);
    suite.setDetailList(detailList);

    return suite;
  }

  private void setCustomerTag(MailTemplateExpander expander, MailComposition composition, Customer customer) {
    MailTagDataList customerTagList = new MailTagDataList();

    customerTagList.add(CustomerBaseTag.LAST_NAME, customer.getLastName());
    customerTagList.add(CustomerTag.CUSTOMER_EMAIL, customer.getEmail());
    PointRuleDao dao = DIContainer.getDao(PointRuleDao.class);

    if (dao.loadAll().get(0).getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.ENABLED.longValue())) {
      customerTagList.add(CustomerTag.REST_POINT, NumUtil.formatNumber(BigDecimalUtil.divide(customer.getRestPoint(), DIContainer
          .getWebshopConfig().getPointMultiple()))
          + "pt");
    } else {
      customerTagList.add(CustomerTag.REST_POINT, "");
    }

    expander.addTagDataList(composition.getSubstitutionTag(), customerTagList);
  }

  private void setCustomerWithdrawalRequestTag(MailTemplateExpander expander, MailComposition composition, Customer customer) {
    MailTagDataList customerWithdrawalRequestTagList = new MailTagDataList();

    customerWithdrawalRequestTagList.add(CustomerBaseTag.LAST_NAME, customer.getLastName());
    customerWithdrawalRequestTagList.add(CustomerWithdrawalRequestTag.WITHDRAWAL_REQUEST_DATE, DateUtil.toDateString(customer
        .getWithdrawalRequestDate()));

    expander.addTagDataList(composition.getSubstitutionTag(), customerWithdrawalRequestTagList);
  }

  private void setCustomerWithdrawalTag(MailTemplateExpander expander, MailComposition composition, Customer customer) {
    MailTagDataList customerWithdrawalTagList = new MailTagDataList();

    customerWithdrawalTagList.add(CustomerWithdrawalTag.WITHDRAWAL_REQUEST_DATE, DateUtil.toDateString(customer
        .getWithdrawalRequestDate()));
    customerWithdrawalTagList.add(CustomerWithdrawalTag.WITHDRAWAL_DATE, DateUtil.toDateString(customer.getWithdrawalDate()));

    expander.addTagDataList(composition.getSubstitutionTag(), customerWithdrawalTagList);
  }

  private void setPasswordConfirmationTag(MailTemplateExpander expander, MailComposition composition, Customer customer) {
    MailTagDataList passwordTagList = new MailTagDataList();

    passwordTagList.add(CustomerBaseTag.LAST_NAME, customer.getLastName());
    expander.addTagDataList(composition.getSubstitutionTag(), passwordTagList);
  }

  private void setReissuePasswordTag(MailTemplateExpander expander, MailComposition composition, Customer customer,
      Reminder reminder, String contextPath) {
    MailTagDataList reissuePasswordTagList = new MailTagDataList();

    reissuePasswordTagList.add(CustomerBaseTag.LAST_NAME, customer.getLastName());
    reissuePasswordTagList.add(ReissuePasswordTag.REISSUE_PASSWORD_URL, contextPath + reminder.getReissuanceKey());

    expander.addTagDataList(composition.getSubstitutionTag(), reissuePasswordTagList);
  }

  public ServiceResult sendCustomerRegisterdMail(Customer customer, Shop shop) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    MailTemplateSuite mailTemplateSuite = createMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(),
        MailType.CUSTOMER_REGISTERED.getValue(), 0L, customer.getCustomerCode());

    if (noSetMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(), MailType.CUSTOMER_REGISTERED.getValue(), 0L)) {
      serviceResult.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return serviceResult;
    }

    // MailTemplateExpanderにtemplateSuiteを読み込ませ、expanderを生成する

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    // 各メールタグごとに値を設定していく
    setCustomerTag(expander, MailComposition.CUSTOMER_REGISTERED_MAIN, customer);
    setShopInfoTag(expander, MailComposition.CUSTOMER_REGISTERED_SIGN, shop);

    // createMailQueueにtemplateSuite、送信先アドレス、メール送信ステータス(未送信)、expanderから生成したメール本文をセットし、queueを生成する。

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, customer.getEmail(), MailSendStatus.NOT_SENT.longValue(),
        expander.expandTemplate());

    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        serviceResult.addServiceError(error);
      }
    }

    return serviceResult;
  }

  public ServiceResult sendPasswordChangeMail(Customer customer, Shop shop) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // createMailTemplateにショップコード、MailType、テンプレート番号(情報メール以外は0なので基本0L)をセットしてtemplateSuiteを取得する

    MailTemplateSuite mailTemplateSuite = createMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(),
        MailType.PASSWORD_CONFIRMATION.getValue(), 0L, customer.getCustomerCode());

    if (noSetMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(), MailType.PASSWORD_CONFIRMATION.getValue(), 0L)) {
      serviceResult.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return serviceResult;
    }

    // MailTemplateExpanderにtemplateSuiteを読み込ませ、expanderを生成する

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    // 各メールタグごとに値を設定していく
    setPasswordConfirmationTag(expander, MailComposition.PASSWORD_CONFIRMATION_MAIN, customer);
    setShopInfoTag(expander, MailComposition.PASSWORD_CONFIRMATION_SIGN, shop);

    // createMailQueueにtemplateSuite、送信先アドレス、メール送信ステータス(未送信)、expanderから生成したメール本文をセットし、queueを生成する。

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, customer.getEmail(), MailSendStatus.NOT_SENT.longValue(),
        expander.expandTemplate());

    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        serviceResult.addServiceError(error);
      }
    }

    return serviceResult;
  }

  public ServiceResult sendPasswordSendMail(Customer customer, Reminder reminder, Shop shop, String contextPath, String langStr) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // createMailTemplateにショップコード、MailType、テンプレート番号(情報メール以外は0なので基本0L)をセットしてtemplateSuiteを取得する

    MailTemplateSuite mailTemplateSuite = createMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(),
        MailType.REISSUE_PASSWORD.getValue(), 0L, customer.getCustomerCode());

    if (noSetMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(), MailType.REISSUE_PASSWORD.getValue(), 0L)) {
      serviceResult.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return serviceResult;
    }

    // MailTemplateExpanderにtemplateSuiteを読み込ませ、expanderを生成する

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    // 各メールタグごとに値を設定していく
    setReissuePasswordTag(expander, MailComposition.REISSUE_PASSWORD_MAIN, customer, reminder, contextPath + "/" + langStr
        + "/app/customer/customer_initpassword/init/");
    setShopInfoTag(expander, MailComposition.REISSUE_PASSWORD_SIGN, shop);

    // createMailQueueにtemplateSuite、送信先アドレス、メール送信ステータス(未送信)、expanderから生成したメール本文をセットし、queueを生成する。

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, customer.getEmail(), MailSendStatus.NOT_SENT.longValue(),
        expander.expandTemplate());

    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        serviceResult.addServiceError(error);
      }
    }

    return serviceResult;
  }

  public ServiceResult sendPaymentPasswordSendMail(Customer customer, Reminder reminder, Shop shop, String contextPath,
      String langStr) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // createMailTemplateにショップコード、MailType、テンプレート番号(情報メール以外は0なので基本0L)をセットしてtemplateSuiteを取得する

    MailTemplateSuite mailTemplateSuite = createMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(),
        MailType.PAYMENT_PASSWORD_CHANGED.getValue(), 0L, customer.getCustomerCode());

    if (noSetMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(), MailType.PAYMENT_PASSWORD_CHANGED.getValue(), 0L)) {
      serviceResult.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return serviceResult;
    }

    // MailTemplateExpanderにtemplateSuiteを読み込ませ、expanderを生成する

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    // 各メールタグごとに値を設定していく
    setReissuePasswordTag(expander, MailComposition.PAYMENT_PASSWORD_CHANGED_MAIN, customer, reminder, contextPath + "/" + langStr
        + "/app/mypage/customer_initpaymentpassword/init/");
    setShopInfoTag(expander, MailComposition.PAYMENT_PASSWORD_CHANGED_SIGN, shop);

    // createMailQueueにtemplateSuite、送信先アドレス、メール送信ステータス(未送信)、expanderから生成したメール本文をセットし、queueを生成する。

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, customer.getEmail(), MailSendStatus.NOT_SENT.longValue(),
        expander.expandTemplate());

    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        serviceResult.addServiceError(error);
      }
    }

    return serviceResult;
  }

  public ServiceResult sendWithdrawalRequestMail(Customer customer, Shop shop) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // createMailTemplateにショップコード、MailType、テンプレート番号(情報メール以外は0なので基本0L)をセットしてtemplateSuiteを取得する

    MailTemplateSuite mailTemplateSuite = createMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(),
        MailType.RECEIVED_WITHDRAWAL_NOTICE.getValue(), 0L, customer.getCustomerCode());

    if (noSetMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(), MailType.RECEIVED_WITHDRAWAL_NOTICE.getValue(), 0L)) {
      serviceResult.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return serviceResult;
    }

    // MailTemplateExpanderにtemplateSuiteを読み込ませ、expanderを生成する

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    // 各メールタグごとに値を設定していく
    setCustomerWithdrawalRequestTag(expander, MailComposition.RECEIVED_WITHDRAWAL_NOTICE_MAIN, customer);
    setShopInfoTag(expander, MailComposition.RECEIVED_WITHDRAWAL_NOTICE_SIGN, shop);

    // createMailQueueにtemplateSuite、送信先アドレス、メール送信ステータス(未送信)、expanderから生成したメール本文をセットし、queueを生成する。

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, customer.getEmail(), MailSendStatus.NOT_SENT.longValue(),
        expander.expandTemplate());

    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        serviceResult.addServiceError(error);
      }
    }

    return serviceResult;
  }

  public ServiceResult sendWithdrawalMail(Customer customer, Shop shop) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // createMailTemplateにショップコード、MailType、テンプレート番号(情報メール以外は0なので基本0L)をセットしてtemplateSuiteを取得する

    MailTemplateSuite mailTemplateSuite = createMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(),
        MailType.COMPLETED_WITHDRAWAL.getValue(), 0L, customer.getCustomerCode());

    if (noSetMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(), MailType.COMPLETED_WITHDRAWAL.getValue(), 0L)) {
      serviceResult.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return serviceResult;
    }

    // MailTemplateExpanderにtemplateSuiteを読み込ませ、expanderを生成する

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    // 各メールタグごとに値を設定していく
    setCustomerWithdrawalTag(expander, MailComposition.COMPLETED_WITHDRAWAL_MAIN, customer);
    setShopInfoTag(expander, MailComposition.COMPLETED_WITHDRAWAL_SIGN, shop);

    // createMailQueueにtemplateSuite、送信先アドレス、メール送信ステータス(未送信)、expanderから生成したメール本文をセットし、queueを生成する。

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, customer.getEmail(), MailSendStatus.NOT_SENT.longValue(),
        expander.expandTemplate());

    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        serviceResult.addServiceError(error);
      }
    }

    return serviceResult;
  }

  /** 2013/05/13 优惠券对应 ob update start */
  // public ServiceResult sendBirthdayMail(List<Customer>
  // customerList,NewCouponRule newCouponRule,Shop shop) {
  public ServiceResult sendBirthdayMail(List<Customer> customerList, List<NewCouponHistory> historyList, Shop shop) {
    /** 2013/05/13 优惠券对应 ob update end */
    ServiceResultImpl result = new ServiceResultImpl();

    WebshopConfig config = DIContainer.getWebshopConfig();

    if (noSetMailTemplate(config.getSiteShopCode(), MailType.BIRTHDAY.getValue(), 0L)) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }
    // String monthDay =
    // DateUtil.toDateTimeString(DateUtil.addDate(DateUtil.getSysdate(),
    // beforeDays), "MMdd");
    // List<Customer> customerList = DatabaseUtil.loadAsBeanList(new
    // SimpleQuery(MailingServiceQuery.LOAD_CUSTOMER_BY_BIRTH_DATE,
    // new Object[] {
    // monthDay
    // }), Customer.class);
    /** 2013/05/13 优惠券对应 ob update start */
    // if(newCouponRule != null){
    if (historyList != null && historyList.size() > 0) {
      int index = 0;
      /** 2013/05/13 优惠券对应 ob update end */
      // メール送信します
      for (Customer customer : customerList) {
        List<Customer> newCustomerList = new ArrayList<Customer>();
        MailTemplateSuite mailTemplateSuite = createMailTemplate(config.getSiteShopCode(), MailType.BIRTHDAY.getValue(), 0L,
            customer.getCustomerCode());

        MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);
        setShopInfoTag(expander, MailComposition.BIRTHDAY_SIGN, shop);
        setBirthDayTag(expander, MailComposition.BIRTHDAY_MAIN, historyList.get(index), customer.getLastName(), customer
            .getLanguageCode());
        index++;
        newCustomerList.add(customer);

        BroadcastMailqueueSuite suite = createBroadcastMailqueueSuite(mailTemplateSuite, newCustomerList, MailSendStatus.NOT_SENT
            .longValue(), expander.expandTemplate());

        ServiceResult insertResult = insertBroadcastMailqueue(suite);
        if (insertResult.hasError()) {
          for (ServiceErrorContent error : insertResult.getServiceErrorList()) {
            result.addServiceError(error);
          }
        }
      }
    }
    return result;
  }

  /** 2013/04/16 优惠券对应 ob add start */
  private void setBirthDayTag(MailTemplateExpander expander, MailComposition composition, NewCouponHistory history,
      String lastName, String languageCode) {
    MailTagDataList birthDayList = new MailTagDataList();

    if (StringUtil.isNullOrEmpty(languageCode)) {
      languageCode = "zh-cn";
    }
    if (languageCode.equals("zh-cn")) {
      birthDayList.add(BirthDayTag.COUPON_NAME, history.getCouponName());
    } else if (languageCode.equals("ja-jp")) {
      birthDayList.add(BirthDayTag.COUPON_NAME, history.getCouponNameJp());
    } else if (languageCode.equals("en-us")) {
      birthDayList.add(BirthDayTag.COUPON_NAME, history.getCouponNameEn());
    }
    birthDayList.add(BirthDayTag.COUPON_START_DATE, DateUtil.toDateString(history.getUseStartDatetime()));
    birthDayList.add(BirthDayTag.COUPON_END_DATE, DateUtil.toDateString(history.getUseEndDatetime()));
    birthDayList.add(BirthDayTag.LAST_NAME, lastName);
    expander.addTagDataList(composition.getSubstitutionTag(), birthDayList);
  }

  /** 2013/04/16 优惠券对应 ob add end */

  public ServiceResult sendPointExpiredMail(int beforeDay) {
    ServiceResultImpl result = new ServiceResultImpl();

    PointRuleDao ruleDao = DIContainer.getDao(PointRuleDao.class);

    PointRule rule = ruleDao.loadAll().get(0);
    if (PointFunctionEnabledFlg.fromValue(rule.getPointFunctionEnabledFlg()).equals(PointFunctionEnabledFlg.DISABLED)) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.error("現在、ポイントルールは使用されていません");
      return result;
    }

    WebshopConfig config = DIContainer.getWebshopConfig();

    if (noSetMailTemplate(config.getSiteShopCode(), MailType.POINT_EXPIRED.getValue(), 0L)) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }

    // 本日のbeforeDays日後
    Date baseDate = DateUtil.truncateDate(DateUtil.addDate(DateUtil.getSysdate(), beforeDay));

    // 本日のbeforeDays日後に失効するポイントが付与された日
    Date pointAcquiredDate = DateUtil.addMonth(baseDate, -1 * rule.getPointPeriod().intValue());

    List<Customer> customerList = DatabaseUtil.loadAsBeanList(new SimpleQuery(
        MailingServiceQuery.LOAD_CUSTOMER_BY_LATEST_POINT_ACQUIRED_DATE, new Object[] {
          pointAcquiredDate
        }), Customer.class);

    ShopDao dao = DIContainer.getDao(ShopDao.class);
    Shop shop = dao.load(config.getSiteShopCode());

    for (Customer customer : customerList) {
      List<Customer> newCustomerList = new ArrayList<Customer>();
      MailTemplateSuite mailTemplateSuite = createMailTemplate(config.getSiteShopCode(), MailType.POINT_EXPIRED.getValue(), 0L,
          customer.getCustomerCode());

      MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);
      setShopInfoTag(expander, MailComposition.POINT_EXPIRED_SIGN, shop);
      setPointExpiredTag(expander, MailComposition.POINT_EXPIRED_MAIN, DateUtil.toDateString(baseDate));

      newCustomerList.add(customer);

      BroadcastMailqueueSuite suite = createBroadcastMailqueueSuite(mailTemplateSuite, newCustomerList, MailSendStatus.NOT_SENT
          .longValue(), expander.expandTemplate());

      ServiceResult insertResult = insertBroadcastMailqueue(suite);
      if (insertResult.hasError()) {
        for (ServiceErrorContent error : insertResult.getServiceErrorList()) {
          result.addServiceError(error);
        }
      }
    }
    return result;
  }

  private void setPointExpiredTag(MailTemplateExpander expander, MailComposition composition, String pointExpiredDate) {
    MailTagDataList pointTagList = new MailTagDataList();

    pointTagList.add(PointExpiredTag.POINT_EXPIRED_DATE, pointExpiredDate);

    expander.addTagDataList(composition.getSubstitutionTag(), pointTagList);
  }

  /**
   * ショップコード、メールタイプ、メールテンプレート番号を受け取り、該当するメールテンプレートが存在しない場合はtrueを返します。
   */
  private boolean noSetMailTemplate(String shopCode, String mailType, Long mailTemplateNo) {
    MailTemplateHeaderDao dao = DIContainer.getDao(MailTemplateHeaderDao.class);
    MailTemplateHeader header = dao.load(shopCode, mailType, mailTemplateNo);

    Query query = new SimpleQuery(ShopManagementSimpleSql.LOAD_MAIL_TEMPLATE_DETAIL, shopCode, mailType, mailTemplateNo);
    List<MailTemplateDetail> detailList = DatabaseUtil.loadAsBeanList(query, MailTemplateDetail.class);

    boolean checkMail = true;

    if (header != null && detailList != null) {
      checkMail = false;
    }

    return checkMail;
  }

  /**
   * 出荷明細情報展開用項目タグ情報
   * 
   * @author System Integrator Corp.
   */
  private static class ShippingDetailCustomTag implements MailTemplateTag {

    private String name;

    private String value;

    private String dummyData;

    private boolean required;

    public ShippingDetailCustomTag(String name, String value, String dummyData) {
      this.name = name;
      this.value = value;
      this.dummyData = dummyData;
      this.required = false;
    }

    public String getDummyData() {
      return dummyData;
    }

    public String getName() {
      return name;
    }

    public String getTagDiv() {
      return SHIPPING_HEADER;
    }

    public String getValue() {
      return value;
    }

    public boolean isRequired() {
      return required;
    }

  }

  public ServiceResult sendInformationMail(CustomerSearchCondition condition, MailTemplateSuite mailTemplateSuite) {
    ServiceResultImpl result = new ServiceResultImpl();

    MailTemplateHeader mailTemplateHeader = mailTemplateSuite.getMailTemplateHeader();

    WebshopConfig config = DIContainer.getWebshopConfig();

    if (noSetMailTemplate(config.getSiteShopCode(), MailType.INFORMATION.getValue(), mailTemplateHeader.getMailTemplateNo())) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }

    // 情報メール送信対象顧客が存在しない場合
    CustomerSearchQuery customerQuery = new CustomerSearchQuery(condition, CustomerSearchQuery.LOAD_INFORMATION_SEND_MAIL_QUERY);
    SearchResult<CustomerSearchInfo> customerResult = DatabaseUtil.executeSearch(customerQuery);
    if (customerResult.getRowCount() == 0) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // 同胞配信メールキューヘッダを取得

    BroadcastMailqueueSuite suite = createBroadcastMailqueueSuite(mailTemplateSuite, new ArrayList<Customer>(),
        MailSendStatus.NOT_SENT.longValue(), mailTemplateSuite.getMailTemplateDetail().get(0).getMailText() + "\r\n"
            + mailTemplateSuite.getMailTemplateDetail().get(1).getMailText());
    BroadcastMailqueueHeader header = suite.getHeader();
    header.setMailContentType(header.getMailContentType());
    setUserStatus(header);
    // validate
    ValidationSummary summary = BeanValidator.validate(header);
    if (summary.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String message : summary.getErrorMessages()) {
        logger.error(message);
      }
      return result;
    }

    // 同胞配信メールキュー明細登録SQLを作成

    String insertQuery = CustomerSearchQuery.INSERT_INFORMATION_SEND_MAIL_INSERT_QUERY
        + CustomerSearchQuery.INSERT_INFORMATION_SEND_MAIL_FROM_QUERY + customerQuery.getSqlString()
        + CustomerSearchQuery.INSERT_INFORMATION_SEND_MAIL_WHERE_QUERY;

    List<Object> insertParams = new ArrayList<Object>();
    insertParams.add(mailTemplateHeader.getBccAddress());
    insertParams.add(MailSendStatus.NOT_SENT.getValue());
    insertParams.add(getLoginInfo().getLoginId());
    insertParams.add(DatabaseUtil.getSystemDatetime());
    insertParams.add(getLoginInfo().getLoginId());
    insertParams.add(DatabaseUtil.getSystemDatetime());
    for (Object list : customerQuery.getParameters()) {
      insertParams.add(list);
    }
    insertParams.add(MailType.INFORMATION.getValue());
    insertParams.add(mailTemplateHeader.getMailTemplateNo());
    insertParams.add(header.getMailQueueId());

    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(this.getLoginInfo());
      // 同胞配信メールキューヘッダ新規登録
      txMgr.insert(header);
      // 同胞配信メールキュー明細新規登録
      txMgr.executeUpdate(insertQuery, insertParams.toArray());

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public ServiceResult sendCancelOrderMail(OrderContainer orderContainer, CashierPayment payment, Shop shop) {

    ServiceResultImpl result = new ServiceResultImpl();

    OrderHeader orderHeader = orderContainer.getOrderHeader();

    String toAddress = "";

    // 顧客の場合、顧客テーブルからメールアドレスと取得する

    if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
      if (orderHeader.getCustomerCode() == null) {
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return result;
      }
      CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
      Customer customer = customerDao.load(orderHeader.getCustomerCode());
      if (customer == null) {
        result.addServiceError(MailingServiceErrorContent.NO_CUSTOMER_TO_SEND_MAIL);
        return result;
      }
      toAddress = customer.getEmail();
    } else {
      toAddress = orderHeader.getEmail();
    }
    orderContainer.getOrderHeader().setEmail(toAddress);

    MailType mailType;

    boolean isReserve = false;
    if (orderContainer.getOrderDetails().size() == 1) {
      CommodityHeaderDao commodityHeaderDao = DIContainer.getDao(CommodityHeaderDao.class);
      CommodityHeader commodityHeader = commodityHeaderDao.load(orderContainer.getOrderDetails().get(0).getShopCode(),
          orderContainer.getOrderDetails().get(0).getCommodityCode());
      if (commodityHeader.getReservationStartDatetime() == null && commodityHeader.getReservationEndDatetime() == null) {
        isReserve = false;
      } else if (DateUtil.isPeriodDate(commodityHeader.getReservationStartDatetime(), commodityHeader.getReservationEndDatetime(),
          DateUtil.getSysdate())) {
        isReserve = true;
      }
    }

    if (isReserve) {
      mailType = MailType.CANCELLED_RESERVATION;
    } else {
      mailType = MailType.CANCELLED_ORDER;
    }

    if (noSetMailTemplate(shop.getShopCode(), mailType.getValue(), 0L)) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }

    MailTemplateSuite mailTemplateSuite = createMailTemplate(shop.getShopCode(), mailType.getValue(), 0L, orderHeader
        .getCustomerCode());
    setTaxIncludedPriceDisplay(mailTemplateSuite);

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    MailComposition mainComposition = null;
//    MailComposition shippingHeaderComposition = null;
//    MailComposition shippingDetailComposition = null;
//    MailComposition paymentComposition = null;
//    MailComposition signComposition = null;

    if (isReserve) {
      mainComposition = MailComposition.CANCELLED_RESERVATION_MAIN;
//      shippingHeaderComposition = MailComposition.CANCELLED_RESERVATION_SHIPPING_HEADER;
//      shippingDetailComposition = MailComposition.CANCELLED_RESERVATION_SHIPPING_DETAIL;
//      paymentComposition = MailComposition.CANCELLED_RESERVATION_PAYMENT;
//      signComposition = MailComposition.CANCELLED_RESERVATION_SIGN;
    } else {
      mainComposition = MailComposition.CANCELLED_ORDER_MAIN;
//      shippingHeaderComposition = MailComposition.CANCELLED_ORDER_SHIPPING_HEADER;
//      shippingDetailComposition = MailComposition.CANCELLED_ORDER_SHIPPING_DETAIL;
//      paymentComposition = MailComposition.CANCELLED_ORDER_PAYMENT;
//      signComposition = MailComposition.CANCELLED_ORDER_SIGN;
    }

    setOrderHeaderTag(expander, mainComposition, orderContainer);

//    if (isReserve) {
//      setReservationShippingTag(expander, shippingHeaderComposition, orderContainer, shippingDetailComposition);
//    } else {
//      setShippingHeaderTag(expander, shippingHeaderComposition, orderContainer, shippingDetailComposition);
//    }
//    setPaymentTag(expander, paymentComposition, orderHeader, payment);
//    setShopInfoTag(expander, signComposition, shop);

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, toAddress, MailSendStatus.NOT_SENT.longValue(), expander
        .expandTemplate());

    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        result.addServiceError(error);
      }
    }

    return result;

  }

  private ClientMailType getClientMailType(ClientMailTypeCondition condition) {
    if (condition.getClientMailType() != null) {
      return condition.getClientMailType();
    }

    MobileDomain domain = DIContainer.get("MobileDomain");
    String mailType = Integer.toString(domain.getClientMailType(condition.getMailAddress()));
    ClientMailType clientMailType = ClientMailType.fromValue(mailType);
    if (clientMailType != null) {
      return clientMailType;
    }
    return ClientMailType.PC;
  }

  // Add by V10-CH start
  public ServiceResult sendCustomerChangeGroupMail(Customer customer, Shop shop, String customerGroupName, String groupChangeType,
      String month) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    MailTemplateSuite mailTemplateSuite = createMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(),
        MailType.GROUP_CHANGE.getValue(), 0L, customer.getCustomerCode());

    if (noSetMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(), MailType.GROUP_CHANGE.getValue(), 0L)) {
      serviceResult.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return serviceResult;
    }

    // MailTemplateExpanderにtemplateSuiteを読み込ませ、expanderを生成する

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    // 各メールタグごとに値を設定していく
    setGroupChangeTag(expander, MailComposition.GROUP_CHANGE_MAIN, customer, customerGroupName, null, groupChangeType, month);
    setShopInfoTag(expander, MailComposition.GROUP_CHANGE_SIGN, shop);

    // createMailQueueにtemplateSuite、送信先アドレス、メール送信ステータス(未送信)、expanderから生成したメール本文をセットし、queueを生成する。

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, customer.getEmail(), MailSendStatus.NOT_SENT.longValue(),
        expander.expandTemplate());

    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        serviceResult.addServiceError(error);
      }
    }

    return serviceResult;
  }

  // Add by lc 2012-04-09 start
  public ServiceResult sendCustomerChangeGroupMailToLower(Customer customer, Shop shop, String customerGroupName,
      String groupChangeType, String month, String groupRange) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    MailTemplateSuite mailTemplateSuite = createMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(),
        MailType.GROUP_CHANGE_TO_LOWER.getValue(), 0L, customer.getCustomerCode());

    if (noSetMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(), MailType.GROUP_CHANGE_TO_LOWER.getValue(), 0L)) {
      serviceResult.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return serviceResult;
    }

    // MailTemplateExpanderにtemplateSuiteを読み込ませ、expanderを生成する

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    // 各メールタグごとに値を設定していく
    setGroupChangeTag(expander, MailComposition.GROUP_CHANGE_MAIN_TO_LOWER, customer, customerGroupName, groupRange,
        groupChangeType, month);
    setShopInfoTag(expander, MailComposition.GROUP_CHANGE_SIGN_TO_LOWER, shop);

    // createMailQueueにtemplateSuite、送信先アドレス、メール送信ステータス(未送信)、expanderから生成したメール本文をセットし、queueを生成する。

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, customer.getEmail(), MailSendStatus.NOT_SENT.longValue(),
        expander.expandTemplate());

    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        serviceResult.addServiceError(error);
      }
    }

    return serviceResult;
  }

  // Add by lc 2012-04-09 end

  private void setGroupChangeTag(MailTemplateExpander expander, MailComposition composition, Customer customer,
      String customerGroupName, String groupRange, String groupChangeType, String month) {
    MailTagDataList groupChangeTagList = new MailTagDataList();

    groupChangeTagList.add(GroupChangeTag.LAST_NAME, customer.getLastName());
    groupChangeTagList.add(GroupChangeTag.GROUP_NAME, customerGroupName);
    groupChangeTagList.add(GroupChangeTag.MONTH, month);
    groupChangeTagList.add(GroupChangeTag.GROUP_CHANGE_TYPE, groupChangeType);
    groupChangeTagList.add(GroupChangeTag.GROUP_RANGE, groupRange);

    expander.addTagDataList(composition.getSubstitutionTag(), groupChangeTagList);
  }

  // Add by V10-CH end

  // Add by V10-CH start ysy
  public ServiceResult sendCustomerUpgradesRemindMail(Customer customer, Shop shop, String customerGroupName, String groupRange,
      String groupChangeType, String month) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    MailTemplateSuite mailTemplateSuite = createMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(),
        MailType.GROUP_CHANGE_INFORMATION.getValue(), 0L, customer.getCustomerCode());

    if (noSetMailTemplate(DIContainer.getWebshopConfig().getSiteShopCode(), MailType.GROUP_CHANGE_INFORMATION.getValue(), 0L)) {
      serviceResult.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return serviceResult;
    }

    // MailTemplateExpanderにtemplateSuiteを読み込ませ、expanderを生成する

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);

    // 各メールタグごとに値を設定していく
    setGroupChangeTag(expander, MailComposition.GROUP_CHANGE_INFORMATION_MAIN, customer, customerGroupName, groupRange,
        groupChangeType, month);
    setShopInfoTag(expander, MailComposition.GROUP_CHANGE_INFORMATION_SIGN, shop);

    // createMailQueueにtemplateSuite、送信先アドレス、メール送信ステータス(未送信)、expanderから生成したメール本文をセットし、queueを生成する。

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, customer.getEmail(), MailSendStatus.NOT_SENT.longValue(),
        expander.expandTemplate());

    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        serviceResult.addServiceError(error);
      }
    }

    return serviceResult;
  }

  // Add by V10-CH end ysy

  // 20111230 ob add start

  public ServiceResult sendCouponStartBeforeMail(NewCouponHistory newCouponHistory, Shop shop, Customer customer) {
    ServiceResultImpl result = new ServiceResultImpl();
    MailTemplateSuite mailTemplateSuite = createMailTemplate(shop.getShopCode(), MailType.COUPON_START.getValue(), 0L, customer
        .getCustomerCode());

    if (noSetMailTemplate(shop.getShopCode(), MailType.COUPON_START.getValue(), 0L)) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);
    setCouponStartTag(expander, MailComposition.COUPON_START_INFORMATION_MAIN, newCouponHistory, customer);
    setShopInfoTag(expander, MailComposition.COUPON_START_INFORMATION_SIGN, shop);

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, customer.getEmail(), MailSendStatus.NOT_SENT.longValue(),
        expander.expandTemplate());
    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        result.addServiceError(error);
      }
    }

    return result;
  }

  public ServiceResult sendCouponEndBeforeMail(NewCouponHistory newCouponHistory, Shop shop, Customer customer) {
    ServiceResultImpl result = new ServiceResultImpl();
    MailTemplateSuite mailTemplateSuite = createMailTemplate(shop.getShopCode(), MailType.COUPON_END.getValue(), 0L, customer
        .getCustomerCode());

    if (noSetMailTemplate(shop.getShopCode(), MailType.COUPON_END.getValue(), 0L)) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);
    setCouponEndTag(expander, MailComposition.COUPON_END_INFORMATION_MAIN, newCouponHistory, customer);
    setShopInfoTag(expander, MailComposition.COUPON_END_INFORMATION_SIGN, shop);

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, customer.getEmail(), MailSendStatus.NOT_SENT.longValue(),
        expander.expandTemplate());
    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        result.addServiceError(error);
      }
    }

    return result;
  }

  // 20111230 ob add end

  /**
   * 创建邮件模版(根据用户语言偏好，选择适当语言模版)
   * 
   * @param shopCode
   * @param mailType
   * @param mailTemplateNo
   * @param languageType
   * @return MailTemplateSuite
   */
  private MailTemplateSuite createMailTemplate(String shopCode, String mailType, Long mailTemplateNo, String customerCode) {
    MailTemplateSuite suite = new MailTemplateSuite();
    Query queryHeader = null;
    Query queryDetail = null;
    Query queryCustomer = null;
    MailTemplateHeader header = null;
    List<MailTemplateDetail> detailList = null;

    queryCustomer = new SimpleQuery("SELECT LANGUAGE_CODE FROM CUSTOMER WHERE CUSTOMER_CODE = ?", customerCode);
    Customer customerBean = DatabaseUtil.loadAsBean(queryCustomer, Customer.class);

    if (customerBean != null) {
      if (customerBean.getLanguageCode().equals(LanguageCode.En_Us.getValue())) {
        queryHeader = new SimpleQuery(ShopManagementSimpleSql.LOAD_MAIL_TEMPLATE_HEARDER_US, shopCode, mailType, mailTemplateNo);
        header = DatabaseUtil.loadAsBean(queryHeader, MailTemplateHeader.class);

        queryDetail = new SimpleQuery(ShopManagementSimpleSql.LOAD_MAIL_TEMPLATE_DETAIL_US, shopCode, mailType, mailTemplateNo);
        detailList = DatabaseUtil.loadAsBeanList(queryDetail, MailTemplateDetail.class);
      } else if (customerBean.getLanguageCode().equals(LanguageCode.Ja_Jp.getValue())) {
        queryHeader = new SimpleQuery(ShopManagementSimpleSql.LOAD_MAIL_TEMPLATE_HEARDER_JP, shopCode, mailType, mailTemplateNo);
        header = DatabaseUtil.loadAsBean(queryHeader, MailTemplateHeader.class);

        queryDetail = new SimpleQuery(ShopManagementSimpleSql.LOAD_MAIL_TEMPLATE_DETAIL_JP, shopCode, mailType, mailTemplateNo);
        detailList = DatabaseUtil.loadAsBeanList(queryDetail, MailTemplateDetail.class);
      } else {
        MailTemplateHeaderDao dao = DIContainer.getDao(MailTemplateHeaderDao.class);
        header = dao.load(shopCode, mailType, mailTemplateNo);

        queryDetail = new SimpleQuery(ShopManagementSimpleSql.LOAD_MAIL_TEMPLATE_DETAIL, shopCode, mailType, mailTemplateNo);
        detailList = DatabaseUtil.loadAsBeanList(queryDetail, MailTemplateDetail.class);
      }
    } else {
      MailTemplateHeaderDao dao = DIContainer.getDao(MailTemplateHeaderDao.class);
      header = dao.load(shopCode, mailType, mailTemplateNo);

      queryDetail = new SimpleQuery(ShopManagementSimpleSql.LOAD_MAIL_TEMPLATE_DETAIL, shopCode, mailType, mailTemplateNo);
      detailList = DatabaseUtil.loadAsBeanList(queryDetail, MailTemplateDetail.class);
    }

    suite.setMailTemplateHeader(header);
    suite.setMailTemplateDetail(detailList);

    return suite;
  }

  private String getLanguageCode(String customerCode) {
    Query queryCustomer = null;
    String resStr = "zh-cn";
    queryCustomer = new SimpleQuery("SELECT LANGUAGE_CODE FROM CUSTOMER WHERE CUSTOMER_CODE = ?", customerCode);
    Customer customerBean = DatabaseUtil.loadAsBean(queryCustomer, Customer.class);

    if (customerBean != null) {
      if (customerBean.getLanguageCode().equals(LanguageCode.Zh_Cn.getValue())) {
        resStr = "zh-cn";
      } else if (customerBean.getLanguageCode().equals(LanguageCode.Ja_Jp.getValue())) {
        resStr = "ja-jp";
      } else {
        resStr = "en-us";
      }
    }
    return resStr;
  }

  // 顧客コードを指定してメールを送信
  public ServiceResult sendFriendCouponMail(String customerCode) {
    ServiceResultImpl result = new ServiceResultImpl();

    WebshopConfig config = DIContainer.getWebshopConfig();

    if (noSetMailTemplate(config.getSiteShopCode(), MailType.POINT_EXPIRED.getValue(), 0L)) {
      result.addServiceError(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR);
      return result;
    }

    CustomerDao cuDao = DIContainer.getDao(CustomerDao.class);
    Customer cuDto = cuDao.load(customerCode);

    // shopを取得する
    ShopDao dao = DIContainer.getDao(ShopDao.class);
    Shop shop = dao.load(config.getSiteShopCode());

    MailTemplateSuite mailTemplateSuite = createMailTemplate(config.getSiteShopCode(), MailType.CUSTOMER_REGISTERED.getValue(), 0L,
        cuDto.getCustomerCode());

    MailTemplateExpander expander = new MailTemplateExpander(mailTemplateSuite);
    setShopInfoTag(expander, MailComposition.CUSTOMER_REGISTERED_SIGN, shop);
    setCustomerTag(expander, MailComposition.CUSTOMER_REGISTERED_MAIN, cuDto);

    RespectiveMailqueue queue = createMailQueue(mailTemplateSuite, cuDto.getEmail(), MailSendStatus.NOT_SENT.longValue(), expander
        .expandTemplate());

    ServiceResult sendMailResult = sendRespectiveMail(queue);

    if (sendMailResult.hasError()) {
      for (ServiceErrorContent error : sendMailResult.getServiceErrorList()) {
        result.addServiceError(error);
      }
    }

    return result;
  }

  // ポイントステータス更新
  public ServiceResult updatePointStatus() {
    ServiceResultImpl result = new ServiceResultImpl();

    Logger logger = Logger.getLogger(this.getClass());
    int day = Integer.parseInt(DIContainer.getWebshopConfig().getFriendCouponCodeDay());
    // 準備更新のデータを取得
    // Query queryCustomer = null;
    // queryCustomer = new SimpleQuery("SELECT    A.ORDER_NO,"
    // + "          A.USE_HISTORY_ID "
    // + "FROM      FRIEND_COUPON_USE_HISTORY A "
    // + "WHERE     A.POINT_STATUS = ? "
    // + "AND       DATE(A.ISSUE_DATE + INTERVAL ? ) <= ? ",
    // CouponStatus.TEMPORARY.getValue(),day,DateUtil.getSysdate());

    String dateStr = DateUtil.getDateStamp(DateUtil.addDate(DateUtil.getSysdate(), -day));
    String sql = "SELECT B.ORDER_NO, B.RETURN_STATUS_SUMMARY, B.SHIPPING_STATUS_SUMMARY, B.ORDER_STATUS "
        + "FROM FRIEND_COUPON_USE_HISTORY A INNER JOIN ORDER_SUMMARY_LANGUAGE_VIEW2 B ON A.ORDER_NO = B.ORDER_NO "
        + "WHERE A.POINT_STATUS = ? AND TO_CHAR(B.ORDER_DATETIME, 'yyyyMMdd') <= ?";
    Query queryOrder = new SimpleQuery(sql, CouponStatus.TEMPORARY.getValue(), dateStr);
    List<OrderHeadline> ohLineList = DatabaseUtil.loadAsBeanList(queryOrder, OrderHeadline.class);

    TransactionManager txMgr = DIContainer.getTransactionManager();
    txMgr.begin(getLoginInfo());
    try {
      for (OrderHeadline ohLine : ohLineList) {
        logger.info("OrderNo: " + ohLine.getOrderNo() + " ShippingStatus: " + ohLine.getShippingStatusSummary() + " ReturnStatus: "
            + ohLine.getReturnStatusSummary());
        // 取消订单、全部返品
        if (ohLine.getOrderStatus().equals(OrderStatus.CANCELLED.getValue())
            || ReturnStatusSummary.RETURNED_ALL.getValue().equals(ohLine.getReturnStatusSummary())) {
          Query queryUpdate = new SimpleQuery(
              "UPDATE FRIEND_COUPON_USE_HISTORY SET POINT_STATUS = ?, UPDATED_USER = ?,  UPDATED_DATETIME = ? WHERE ORDER_NO = ?",
              CouponStatus.CANCEL.getValue(), getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), ohLine.getOrderNo());
          txMgr.executeUpdate(queryUpdate);
        } else if (ohLine.getOrderStatus().equals(OrderStatus.ORDERED.getValue())
            && ShippingStatusSummary.SHIPPED_ALL.getValue().equals(ohLine.getShippingStatusSummary())) {
          Query queryUpdate = new SimpleQuery(
              "UPDATE FRIEND_COUPON_USE_HISTORY SET POINT_STATUS = ?, UPDATED_USER = ?,  UPDATED_DATETIME = ? WHERE  ORDER_NO = ?",
              CouponStatus.USED.getValue(), getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), ohLine.getOrderNo());
          txMgr.executeUpdate(queryUpdate);
        }
      }
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    // List<FriendCouponUseHistory> friendCouponUseHistoryList =
    // DatabaseUtil.loadAsBeanList(queryCustomer, FriendCouponUseHistory.class);

    // if (friendCouponUseHistoryList != null &&
    // friendCouponUseHistoryList.size() != 0) {
    // for (FriendCouponUseHistory friendCouponUseHistorycDto :
    // friendCouponUseHistoryList) {
    // OrderHeaderDao orderHeaderDao = DIContainer.getDao(OrderHeaderDao.class);
    // ShippingHeaderDao shippingHeaderDao =
    // DIContainer.getDao(ShippingHeaderDao.class);
    //
    // // 受注番号を取得
    // OrderHeader orderDto =
    // orderHeaderDao.load(friendCouponUseHistorycDto.getOrderNo());
    //
    // // 出荷データを取得
    // ShippingHeader shippingDto =
    // shippingHeaderDao.loadByOrderNo(friendCouponUseHistorycDto.getOrderNo());
    //
    // OrderHeadline orderHeaderline = new OrderHeadline();
    // Query q = new
    // SimpleQuery("SELECT RETURN_STATUS_SUMMARY FROM ORDER_SUMMARY_LANGUAGE_VIEW2 WHERE ORDER_NO = ?",
    // friendCouponUseHistorycDto.getOrderNo());
    // orderHeaderline = DatabaseUtil.loadAsBean(q, OrderHeadline.class);
    //
    // // 出荷日時と受注ステータスチェック
    // if (shippingDto.getShippingDate() == null ||
    // CouponStatus.CANCEL.getValue().equals(orderDto.getOrderStatus())) {
    // //クーポンステータスは「２：キャンセル」に更新する
    // TransactionManager txMgr = DIContainer.getTransactionManager();
    // try {
    // Query queryUpdate = new
    // SimpleQuery("UPDATE  FRIEND_COUPON_USE_HISTORY SET POINT_STATUS = ? "
    // + "WHERE   USE_HISTORY_ID = ? ",
    // CouponStatus.CANCEL.getValue(),friendCouponUseHistorycDto.getUseHistoryId());
    // txMgr.begin(getLoginInfo());
    // txMgr.executeUpdate(queryUpdate);
    // txMgr.commit();
    // } catch (ConcurrencyFailureException e) {
    // txMgr.rollback();
    // throw e;
    // } catch (RuntimeException e) {
    // txMgr.rollback();
    // result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    // } finally {
    // txMgr.dispose();
    // }
    // }
    // // 出荷日チェック
    // else if (shippingDto.getShippingDate() != null) {
    // //返品ステータスチェック
    // if
    // (ReturnStatusSummary.RETURNED_ALL.getValue().equals(orderHeaderline.getReturnStatusSummary()))
    // {
    // //クーポンステータスは「２：キャンセル」に更新する
    // TransactionManager txMgr = DIContainer.getTransactionManager();
    // try {
    // Query queryUpdate = new
    // SimpleQuery("UPDATE FRIEND_COUPON_USE_HISTORY SET POINT_STATUS = ? "
    // + "WHERE  USE_HISTORY_ID = ? ",
    // CouponStatus.CANCEL.getValue(),friendCouponUseHistorycDto.getUseHistoryId());
    // txMgr.begin(getLoginInfo());
    // txMgr.executeUpdate(queryUpdate);
    // txMgr.commit();
    // } catch (ConcurrencyFailureException e) {
    // txMgr.rollback();
    // throw e;
    // } catch (RuntimeException e) {
    // txMgr.rollback();
    // result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    // } finally {
    // txMgr.dispose();
    // }
    // } else
    // if(ReturnStatusSummary.NOT_RETURNED.getValue().equals(orderHeaderline.getReturnStatusSummary())
    // ||
    // ReturnStatusSummary.PARTIAL_RETURNED.getValue().equals(orderHeaderline.getReturnStatusSummary())){
    // //　クーポンステータスは「１：有効」に更新する
    // TransactionManager txMgr = DIContainer.getTransactionManager();
    // try {
    // Query queryUpdate = new
    // SimpleQuery("UPDATE FRIEND_COUPON_USE_HISTORY SET POINT_STATUS = ?"
    // + "WHERE  USE_HISTORY_ID = ?",
    // CouponStatus.USED.getValue(),friendCouponUseHistorycDto.getUseHistoryId());
    // txMgr.begin(getLoginInfo());
    // txMgr.executeUpdate(queryUpdate);
    // txMgr.commit();
    // } catch (ConcurrencyFailureException e) {
    // txMgr.rollback();
    // throw e;
    // } catch (RuntimeException e) {
    // txMgr.rollback();
    // result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    // } finally {
    // txMgr.dispose();
    // }
    // }
    // }
    // }
    // }
    return result;
  }

}
