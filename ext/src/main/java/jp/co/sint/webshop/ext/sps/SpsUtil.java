package jp.co.sint.webshop.ext.sps;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.dao.PaymentMethodDao;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.LocaleUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PasswordUtil;

/**
 * @author System Integrator Corp.
 */
public final class SpsUtil {

  private SpsUtil() {
  }

  public static SpsMerchant getMerchant(String shopCode, Long paymentMethodNo) {
    SpsMerchant merchant = new SpsMerchant();
    try {
      PaymentMethodDao dao = DIContainer.getDao(PaymentMethodDao.class);
      PaymentMethod pm = dao.load(shopCode, paymentMethodNo);
      String merchantId = pm.getMerchantId();
      String serviceId = pm.getServiceId();
      merchant.setMerchantId(merchantId);
      merchant.setServiceId(serviceId);
      merchant.setHashKey(pm.getSecretKey());
    } catch (Exception e) {
      Logger.getLogger(SpsUtil.class).error(e);
    }
    return merchant;
  }

  public static boolean validateCheckSum(SpsResponse response) {
    return SpsUtil.getCheckSum(response).equals(response.getSpsHashcode());
  }

  public static String getCheckSum(String value) {
    String spsHashCode = "";
    if (value != null) {
      try {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] b = value.getBytes("UTF-8");
        spsHashCode = toHexString(md.digest(b));
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }
    Logger.getLogger(SpsUtil.class).debug("input : " + value);
    Logger.getLogger(SpsUtil.class).debug("digest: " + spsHashCode);
    return spsHashCode.toUpperCase(Locale.US);
  }

  public static String getCheckSum(SpsRequest spsRequest) {
    StringBuilder builder = new StringBuilder();
    builder.append(adjustValue(spsRequest.getPayMethod()));
    builder.append(adjustValue(spsRequest.getMerchantId()));
    builder.append(adjustValue(spsRequest.getServiceId()));
    builder.append(adjustValue(spsRequest.getCustCode()));
    builder.append(adjustValue(spsRequest.getSpsCustNo()));
    builder.append(adjustValue(spsRequest.getSpsPaymentNo()));
    builder.append(adjustValue(spsRequest.getOrderId()));
    builder.append(adjustValue(spsRequest.getItemId()));
    builder.append(adjustValue(spsRequest.getPayItemId()));
    builder.append(adjustValue(spsRequest.getItemName()));
    builder.append(adjustValue(spsRequest.getTax()));
    builder.append(adjustValue(spsRequest.getAmount()));
    builder.append(adjustValue(spsRequest.getPayType()));
    builder.append(adjustValue(spsRequest.getAutoChargeType()));
    builder.append(adjustValue(spsRequest.getServiceType()));
    builder.append(adjustValue(spsRequest.getDivSettele()));
    builder.append(adjustValue(spsRequest.getLastChargeMonth()));
    builder.append(adjustValue(spsRequest.getCampType()));
    builder.append(adjustValue(spsRequest.getTrackingId()));
    builder.append(adjustValue(spsRequest.getTerminalType()));
    builder.append(adjustValue(spsRequest.getSuccessUrl()));
    builder.append(adjustValue(spsRequest.getCancelUrl()));
    builder.append(adjustValue(spsRequest.getErrorUrl()));
    builder.append(adjustValue(spsRequest.getPageconUrl()));
    builder.append(adjustValue(spsRequest.getFree1()));
    builder.append(adjustValue(spsRequest.getFree2()));
    builder.append(adjustValue(spsRequest.getFree3()));
    builder.append(adjustValue(spsRequest.getFreeCsv()));
    for (SpsRequestDetail d : spsRequest.getDetails()) {
      builder.append(adjustValue(d.getDtlRowno()));
      builder.append(adjustValue(d.getDtlItemId()));
      builder.append(adjustValue(d.getDtlItemName()));
      builder.append(adjustValue(d.getDtlItemCount()));
      builder.append(adjustValue(d.getDtlTax()));
      builder.append(adjustValue(d.getDtlAmount()));
      builder.append(adjustValue(d.getDtlFree1()));
      builder.append(adjustValue(d.getDtlFree2()));
      builder.append(adjustValue(d.getDtlFree3()));
    }
    builder.append(adjustValue(spsRequest.getRequestDate()));
    builder.append(adjustValue(spsRequest.getLimitSecond()));
    builder.append(adjustValue(spsRequest.getPassword()));
    return SpsUtil.getCheckSum(builder.toString());
  }

  public static String getCheckSum(SpsResponse spsResponse) {
    StringBuilder builder = new StringBuilder();
    builder.append(adjustValue(spsResponse.getPayMethod()));
    builder.append(adjustValue(spsResponse.getMerchantId()));
    builder.append(adjustValue(spsResponse.getServiceId()));
    builder.append(adjustValue(spsResponse.getCustCode()));
    builder.append(adjustValue(spsResponse.getSpsCustNo()));
    builder.append(adjustValue(spsResponse.getSpsPaymentNo()));
    builder.append(adjustValue(spsResponse.getOrderId()));
    builder.append(adjustValue(spsResponse.getItemId()));
    builder.append(adjustValue(spsResponse.getPayItemId()));
    builder.append(adjustValue(spsResponse.getItemName()));
    builder.append(adjustValue(spsResponse.getTax()));
    builder.append(adjustValue(spsResponse.getAmount()));
    builder.append(adjustValue(spsResponse.getPayType()));
    builder.append(adjustValue(spsResponse.getAutoChargeType()));
    builder.append(adjustValue(spsResponse.getServiceType()));
    builder.append(adjustValue(spsResponse.getDivSettele()));
    builder.append(adjustValue(spsResponse.getLastChargeMonth()));
    builder.append(adjustValue(spsResponse.getCampType()));
    builder.append(adjustValue(spsResponse.getTrackingId()));
    builder.append(adjustValue(spsResponse.getTerminalType()));
    builder.append(adjustValue(spsResponse.getFree1()));
    builder.append(adjustValue(spsResponse.getFree2()));
    builder.append(adjustValue(spsResponse.getFree3()));
    builder.append(adjustValue(spsResponse.getFreeCsv()));

    for (SpsResponseDetail d : spsResponse.getDetails()) {
      builder.append(adjustValue(d.getDtlRowno()));
      builder.append(adjustValue(d.getDtlItemId()));
      builder.append(adjustValue(d.getDtlItemName()));
      builder.append(adjustValue(d.getDtlItemCount()));
      builder.append(adjustValue(d.getDtlTax()));
      builder.append(adjustValue(d.getDtlAmount()));
      builder.append(adjustValue(d.getDtlFree1()));
      builder.append(adjustValue(d.getDtlFree2()));
      builder.append(adjustValue(d.getDtlFree3()));
    }
    builder.append(adjustValue(spsResponse.getRequestDate()));
    builder.append(adjustValue(spsResponse.getResPayMethod()));
    builder.append(adjustValue(spsResponse.getResResult()));
    builder.append(adjustValue(spsResponse.getResTrackingId()));
    builder.append(adjustValue(spsResponse.getResSpsCustNo()));
    builder.append(adjustValue(spsResponse.getResSpsPaymentNo()));
    builder.append(adjustValue(spsResponse.getResPayinfoKey()));
    builder.append(adjustValue(spsResponse.getResPaymentDate()));
    builder.append(adjustValue(spsResponse.getResErrCode()));
    builder.append(adjustValue(spsResponse.getResDate()));

    builder.append(adjustValue(spsResponse.getLimitSecond()));
    builder.append(adjustValue(spsResponse.getPassword()));
    return SpsUtil.getCheckSum(builder.toString());
  }

  public static SpsResponse create(Map<String, String[]> requestMap) {
    SpsResponse res = new SpsResponse();
    res.setFree1(get("free1", requestMap));
    res.setFree2(get("free2", requestMap));
    res.setFree3(get("free3", requestMap));
    res.setFreeCsv(get("freeCsv", requestMap));
    res.setItemId(get("item_id", requestMap));
    res.setItemName(get("item_name", requestMap));
    res.setLastChargeMonth(get("last_charge_month", requestMap));
    res.setLimitSecond(get("limit_second", requestMap));
    res.setMerchantId(get("merchant_id", requestMap));
    res.setOrderId(get("order_id", requestMap));
    res.setPayItemId(get("pay_item_id", requestMap));
    res.setPayMethod(get("pay_method", requestMap));
    res.setPayType(get("pay_type", requestMap));
    res.setRequestDate(get("request_date", requestMap));
    res.setResDate(get("res_date", requestMap));
    res.setResErrCode(get("res_err_code", requestMap));
    res.setResPayMethod(get("res_pay_method", requestMap));
    res.setResPayinfoKey(get("res_payinfo_key", requestMap));
    res.setResPaymentDate(get("res_payment_date", requestMap));
    res.setResResult(get("res_result", requestMap));
    res.setResSpsCustNo(get("res_sps_cust_no", requestMap));
    res.setResSpsPaymentNo(get("res_sps_payment_no", requestMap));
    res.setResTrackingId(get("res_tracking_id", requestMap));
    res.setServiceId(get("service_id", requestMap));
    res.setServiceType(get("service_type", requestMap));
    res.setSpsCustNo(get("sps_cust_no", requestMap));
    res.setSpsPaymentNo(get("sps_payment_no", requestMap));
    res.setTax(get("tax", requestMap));
    res.setTerminalType(get("terminal_type", requestMap));
    res.setTrackingId(get("tracking_id", requestMap));
    res.setAmount(get("amount", requestMap));
    res.setAutoChargeType(get("auto_charge_type", requestMap));
    res.setCampType(get("camp_type", requestMap));
    res.setCustCode(get("cust_code", requestMap));
    res.setDivSettele(get("div_settele", requestMap));
    res.setSpsHashcode(get("sps_hashcode", requestMap));

    String[] details = requestMap.get("dtl_rowno");
    if (details != null) {
      for (int i = 0; i < details.length; i++) {
        SpsResponseDetail detail = new SpsResponseDetail();
        createDetail(requestMap, i);
        res.getDetails().add(detail);
      }
    }
    return res;
  }

  public static SpsResponseDetail createDetail(Map<String, String[]> requestMap, int index) {
    SpsResponseDetail res = new SpsResponseDetail();
    res.setDtlAmount(get("dtl_amount", requestMap, index));
    res.setDtlFree1(get("dtl_free1", requestMap, index));
    res.setDtlFree2(get("dtl_free2", requestMap, index));
    res.setDtlFree3(get("dtl_free3", requestMap, index));
    res.setDtlItemCount(get("dtl_item_count", requestMap, index));
    res.setDtlItemId(get("dtl_item_id", requestMap, index));
    res.setDtlItemName(get("dtl_item_name", requestMap, index));
    res.setDtlRowno(get("dtl_rowno", requestMap, index));
    res.setDtlTax(get("dtl_tax", requestMap, index));
    return res;
  }

  private static String toHexString(byte[] bytes) {
    return PasswordUtil.toHexString(bytes);
  }

  private static String adjustValue(String s) {
    String v = "";
    if (s != null) {
      v = s.trim();
    }
    return v;
  }

  private static String base64Encode(String s, String enc) {
    String result = "";
    try {
      result = PasswordUtil.base64Encode(s.getBytes(enc));
    } catch (UnsupportedEncodingException e) {
      Logger.getLogger(SpsUtil.class).error(e);
      result = "";
    } catch (Exception e) {
      Logger.getLogger(SpsUtil.class).error(e);
      result = "";
    }
    return result;
  }

  public static String getEncodedItemName(String s) {
    return base64Encode("item_name=" + s, "GBK");
  }

  public static SpsRequest createRefundRequest(OrderContainer container) {
    // 対応しない

    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static SpsRequest createPaymentRequest(OrderContainer container, Locale locale) {

    SpsRequest sps = new SpsRequest();
    try {
      SpsConfig config = DIContainer.get(SpsConfig.class.getSimpleName());
      OrderHeader header = container.getOrderHeader();
      SpsMerchant merchant = getMerchant(header.getShopCode(), header.getPaymentMethodNo());

      // sps.setItemName("");
      sps.setItemId(container.getOrderNo()); // WSは複数商品を買うので注文番号で代替

      sps.setPurchaseRequestUrl(config.getPurchaseRequestUrl());
      sps.setSuccessUrl(config.getSuccessUrl() + "?lang=" + LocaleUtil.getLanguageCode(locale));
      sps.setCancelUrl(config.getCancelUrl() + "?lang=" + LocaleUtil.getLanguageCode(locale));
      sps.setErrorUrl(config.getErrorUrl() + "?lang=" + LocaleUtil.getLanguageCode(locale));
      sps.setPageconUrl(config.getResponseUrl());

      // fix here if you want to change sps payment type
      SpsPaymentMethodType spsType = SpsPaymentMethodType.ALIPAY;

      // 50132 added start
      // modify by V10-CH start
      // BigDecimal totalAmount =
      // BigDecimalUtil.tempFormatLong(container.getTotalAmount());
      BigDecimal totalAmount = container.getTotalAmount();
      // modify by V10-CH end
      BigDecimal usedPoint = NumUtil.coalesce(container.getOrderHeader().getUsedPoint(), BigDecimal.ZERO);
      BigDecimal paymentTotalPrice = BigDecimalUtil.subtract(totalAmount, usedPoint);
      // 50132 added end
      // 20120202 shen add start
      BigDecimal discountPrice = NumUtil.coalesce(container.getOrderHeader().getDiscountPrice(), BigDecimal.ZERO);
      paymentTotalPrice = BigDecimalUtil.subtract(paymentTotalPrice, discountPrice);
      // 20120202 shen add end

      sps.setPayMethod(spsType.getValue());
      sps.setMerchantId(merchant.getMerchantId());
      sps.setServiceId(merchant.getServiceId());
      sps.setCustCode(container.getOrderHeader().getCustomerCode());
      sps.setSpsPaymentNo(""); // 設定しない

      sps.setSpsCustNo(""); // 設定しない

      sps.setOrderId(container.getOrderHeader().getOrderNo());
      // 50132 modified start
      // sps.setAmount(NumUtil.toString(container.getTotalAmount()));
      sps.setAmount(NumUtil.toString(paymentTotalPrice));
      // 50132 modified end
      sps.setTax(NumUtil.toString(getTaxAmount(container)));
      sps.setPayType("0"); // 0:都度、1:継続
      sps.setServiceType("0"); // 0:売上
      sps.setPayItemId("");
      sps.setAutoChargeType(""); // 設定しない

      sps.setDivSettele(""); // 設定しない

      sps.setLastChargeMonth(""); // 設定しない

      sps.setCampType(""); // 設定しない

      sps.setTrackingId(""); // 設定しない

      sps.setTerminalType(getTerminalType(container));

      String reqDate = DateUtil.toDateTimeString(DateUtil.getSysdate(), "yyyyMMddHHmmss");
      sps.setRequestDate(reqDate);
      sps.setLimitSecond(Integer.toString(config.getLimitSecond()));
      sps.setPassword(merchant.getHashKey());

      String ItemUrl = Payment.CreateUrl(sps.getPurchaseRequestUrl(), sps.getServiceId(), sps.getSign_type(), sps.getOrderId(), sps
          .get_input_charset(), sps.getMerchantId(), sps.getPassword(), sps.getShow_url(), sps.getBody(), sps.getAmount(), sps
          .getPayment_type(), sps.getSeller_email(), sps.getItemName(), sps.getPageconUrl(), sps.getSuccessUrl());

      // sps.setSpsHashCode(getCheckSum(sps));
      sps.setItemUrl(ItemUrl);
    } catch (Exception e) {
      Logger.getLogger(SpsUtil.class).error(e);
    }

    return sps;
  }

  private static String getTerminalType(OrderContainer container) {
    // NOTE 多言語版はPC限定なので0（携帯なら1を返す）
    return "0";
  }

  private static BigDecimal getTaxAmount(OrderContainer container) {
    BigDecimal taxAmount = BigDecimal.ZERO;
    OrderHeader oh = container.getOrderHeader();
    taxAmount = BigDecimalUtil.add(taxAmount, oh.getPaymentCommissionTax());
    for (ShippingContainer sc : container.getShippings()) {
      ShippingHeader sh = sc.getShippingHeader();
      taxAmount = BigDecimalUtil.add(taxAmount, sh.getShippingChargeTax());
      for (ShippingDetail sd : sc.getShippingDetails()) {
        taxAmount = BigDecimalUtil.add(taxAmount, sd.getRetailTax());
        taxAmount = BigDecimalUtil.add(taxAmount, sd.getGiftTax());
      }
    }
    return taxAmount;
  }

  private static String get(String name, Map<String, String[]> src) {
    return get(name, src, 0);
  }

  private static String get(String name, Map<String, String[]> src, int index) {
    String result = null;
    if (src != null) {
      String[] results = src.get(name);
      if (results != null && results.length > index) {
        result = results[index];
      }
    }
    return result;
  }

  public static enum SpsPaymentMethodType implements CodeAttribute {

    /** クレジットカード */
    CREDIT("クレジット", "credit"),

    /** Alipay国際決済 */
    ALIPAY("alipay", "alipay");

    private String name;

    private String value;

    public String getValue() {
      return this.value;
    }

    public String getName() {
      return this.name;
    }

    private SpsPaymentMethodType(String name, String value) {
      this.name = name;
      this.value = value;
    }
  }
}
