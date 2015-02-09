package jp.co.sint.webshop.ext.sps;

import java.io.Serializable;

/**
 * @author System Integrator Corp.
 */
public class SpsResponse extends SpsHeader<SpsResponseDetail> implements Serializable {

  private static final long serialVersionUID = 1L;

  private String resDate = "";

  private String resErrCode = "";

  private String resPayMethod = "";

  private String resPayinfoKey = "";

  private String resPaymentDate = "";

  private String resResult = "";

  private String resSpsCustNo = "";

  private String resSpsPaymentNo = "";

  private String resTrackingId = "";

  private String spsHashcode = "";

  /**
   * resDateを返します。
   * 
   * @return resDate
   */
  public String getResDate() {
    return resDate;
  }

  /**
   * resDateを設定します。
   * 
   * @param resDate
   *          resDate
   */
  public void setResDate(String resDate) {
    this.resDate = resDate;
  }

  /**
   * resErrCodeを返します。
   * 
   * @return resErrCode
   */
  public String getResErrCode() {
    return resErrCode;
  }

  /**
   * resErrCodeを設定します。
   * 
   * @param resErrCode
   *          resErrCode
   */
  public void setResErrCode(String resErrCode) {
    this.resErrCode = resErrCode;
  }

  /**
   * resPayMethodを返します。
   * 
   * @return resPayMethod
   */
  public String getResPayMethod() {
    return resPayMethod;
  }

  /**
   * resPayMethodを設定します。
   * 
   * @param resPayMethod
   *          resPayMethod
   */
  public void setResPayMethod(String resPayMethod) {
    this.resPayMethod = resPayMethod;
  }

  /**
   * resPayinfoKeyを返します。
   * 
   * @return resPayinfoKey
   */
  public String getResPayinfoKey() {
    return resPayinfoKey;
  }

  /**
   * resPayinfoKeyを設定します。
   * 
   * @param resPayinfoKey
   *          resPayinfoKey
   */
  public void setResPayinfoKey(String resPayinfoKey) {
    this.resPayinfoKey = resPayinfoKey;
  }

  /**
   * resPaymentDateを返します。
   * 
   * @return resPaymentDate
   */
  public String getResPaymentDate() {
    return resPaymentDate;
  }

  /**
   * resPaymentDateを設定します。
   * 
   * @param resPaymentDate
   *          resPaymentDate
   */
  public void setResPaymentDate(String resPaymentDate) {
    this.resPaymentDate = resPaymentDate;
  }

  /**
   * resResultを返します。
   * 
   * @return resResult
   */
  public String getResResult() {
    return resResult;
  }

  /**
   * resResultを設定します。
   * 
   * @param resResult
   *          resResult
   */
  public void setResResult(String resResult) {
    this.resResult = resResult;
  }

  /**
   * resSpsCustNoを返します。
   * 
   * @return resSpsCustNo
   */
  public String getResSpsCustNo() {
    return resSpsCustNo;
  }

  /**
   * resSpsCustNoを設定します。
   * 
   * @param resSpsCustNo
   *          resSpsCustNo
   */
  public void setResSpsCustNo(String resSpsCustNo) {
    this.resSpsCustNo = resSpsCustNo;
  }

  /**
   * resSpsPaymentNoを返します。
   * 
   * @return resSpsPaymentNo
   */
  public String getResSpsPaymentNo() {
    return resSpsPaymentNo;
  }

  /**
   * resSpsPaymentNoを設定します。
   * 
   * @param resSpsPaymentNo
   *          resSpsPaymentNo
   */
  public void setResSpsPaymentNo(String resSpsPaymentNo) {
    this.resSpsPaymentNo = resSpsPaymentNo;
  }

  /**
   * resTrackingIdを返します。
   * 
   * @return resTrackingId
   */
  public String getResTrackingId() {
    return resTrackingId;
  }

  /**
   * resTrackingIdを設定します。
   * 
   * @param resTrackingId
   *          resTrackingId
   */
  public void setResTrackingId(String resTrackingId) {
    this.resTrackingId = resTrackingId;
  }

  /**
   * spsHashCodeを取得します。
   * 
   * @return spsHashCode spsHashCode
   */
  public String getSpsHashcode() {
    return spsHashcode;
  }

  /**
   * spsHashCodeを設定します。
   * 
   * @param spsHashCode
   *          spsHashCode
   */
  public void setSpsHashcode(String spsHashcode) {
    this.spsHashcode = spsHashcode;
  }

}
