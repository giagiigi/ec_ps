package jp.co.sint.webshop.web.bean.back.shop;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Percentage;
import jp.co.sint.webshop.data.attribute.Point;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050310:ポイントルールのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class PointruleEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @Metadata(name = "ポイントシステム")
  private String enableFlg;

  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "ポイント付与最低購入金額")
  private String punchasePrice;

  @Required
  @Digit
  @Length(3)
  // 10.1.1 10024 修正 ここから
  // @Range(min = 0)
  @Percentage
  // 10.1.1 10024 修正 ここまで
  @Metadata(name = "ポイント付与率")
  private String pointRate;

  @Required
  @Length(11)
  @Point
  @Metadata(name = "会員登録時ポイント")
  private String customerRegisterPoint;
  
  @Required
  @Length(11)
  @Point
  @Metadata(name = "初回購入時ポイント")
  private String firstPoint;

  @Required
  @Point
  @Metadata(name = "レビュー投稿時ポイント")
  private String reviewPoint;

  @Required
  @Digit
  @Length(3)
  // 10.1.1 10024 修正 ここから
  // @Range(min = 0)
  @Percentage
  // 10.1.1 10024 修正 ここまで
  @Metadata(name = "ボーナス時ポイント付与率")
  private String bonusPointRate;

  @Metadata(name = "ボーナス設定日")
  private String bonusPointDate;

  @Datetime
  @Metadata(name = "ボーナス設定期間開始日")
  private String bonusPointStart;

  @Datetime
  @Metadata(name = "ボーナス設定期間終了日")
  private String bonusPointEnd;

  @Required
  @Digit
  @Length(3)
  @Range(min = 0)
  @Metadata(name = "ポイント使用期限")
  private String pointPeriod;

  private String display;

  private boolean registerButtonFlg;

  private Date updatedDatetime;
  
  // add by zhanghaibin start 2010-05-18
//  @Required
//  @Digit
//  @Length(8)
//  @Range(min = 0)
//  @Metadata(name = "最小ポイントを使います")
//  private String smallUsePoint;
//
//  @Required
//  @Digit
//  @Length(3)
//  @Range(min = 0)
//  @Metadata(name = "RMB兑换积分比率")
//  private String rmbPointRate;

//  @Required
//  @Metadata(name = "积分扩大倍数")
//  private String amplificationRate;
  // add by zhanghaibin end 2010-05-18

  
//  public String getAmplificationRate() {
//    return amplificationRate;
//  }
//
//  
//  public void setAmplificationRate(String amplificationRate) {
//    this.amplificationRate = amplificationRate;
//  }

  
//  public String getRmbPointRate() {
//    return rmbPointRate;
//  }
//
//  
//  public void setRmbPointRate(String rmbPointRate) {
//    this.rmbPointRate = rmbPointRate;
//  }
//
//  
//  public String getSmallUsePoint() {
//    return smallUsePoint;
//  }
//
//  
//  public void setSmallUsePoint(String smallUsePoint) {
//    this.smallUsePoint = smallUsePoint;
//  }

  /**
   * updatedDatetimeを取得します。
   * 
   * @return updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * updatedDatetimeを設定します。
   * 
   * @param updatedDatetime
   *          updatedDatetime
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * registerButtonFlgを取得します。
   * 
   * @return registerButtonFlg
   */
  public boolean getRegisterButtonFlg() {
    return registerButtonFlg;
  }

  /**
   * registerButtonFlgを設定します。
   * 
   * @param registerButtonFlg
   *          registerButtonFlg
   */
  public void setRegisterButtonFlg(boolean registerButtonFlg) {
    this.registerButtonFlg = registerButtonFlg;
  }

  /**
   * displayを取得します。
   * 
   * @return display
   */
  public String getDisplay() {
    return display;
  }

  /**
   * displayを設定します。
   * 
   * @param display
   *          display
   */
  public void setDisplay(String display) {
    this.display = display;
  }

  /**
   * bonusPointDateを取得します。
   * 
   * @return bonusPointDate
   */
  public String getBonusPointDate() {
    return bonusPointDate;
  }

  /**
   * bonusPointEndを取得します。
   * 
   * @return bonusPointEnd
   */
  public String getBonusPointEnd() {
    return bonusPointEnd;
  }

  /**
   * bonusPointRateを取得します。
   * 
   * @return bonusPointRate
   */
  public String getBonusPointRate() {
    return bonusPointRate;
  }

  /**
   * bonusPointStartを取得します。
   * 
   * @return bonusPointStart
   */
  public String getBonusPointStart() {
    return bonusPointStart;
  }

  /**
   * customerRegisterPointを取得します。
   * 
   * @return customerRegisterPoint
   */
  public String getCustomerRegisterPoint() {
    return customerRegisterPoint;
  }

  /**
   * enableFlgを取得します。
   * 
   * @return enableFlg
   */
  public String getEnableFlg() {
    return enableFlg;
  }

  /**
   * firstPointを取得します。
   * 
   * @return firstPoint
   */
  public String getFirstPoint() {
    return firstPoint;
  }

  /**
   * pointPeriodを取得します。
   * 
   * @return pointPeriod
   */
  public String getPointPeriod() {
    return pointPeriod;
  }

  /**
   * pointRateを取得します。
   * 
   * @return pointRate
   */
  public String getPointRate() {
    return pointRate;
  }

  /**
   * punchasePriceを取得します。
   * 
   * @return punchasePrice
   */
  public String getPunchasePrice() {
    return punchasePrice;
  }

  /**
   * reviewPointを取得します。
   * 
   * @return reviewPoint
   */
  public String getReviewPoint() {
    return reviewPoint;
  }

  /**
   * bonusPointDateを設定します。
   * 
   * @param bonusPointDate
   *          bonusPointDate
   */
  public void setBonusPointDate(String bonusPointDate) {
    this.bonusPointDate = bonusPointDate;
  }

  /**
   * bonusPointEndを設定します。
   * 
   * @param bonusPointEnd
   *          bonusPointEnd
   */
  public void setBonusPointEnd(String bonusPointEnd) {
    this.bonusPointEnd = bonusPointEnd;
  }

  /**
   * bonusPointRateを設定します。
   * 
   * @param bonusPointRate
   *          bonusPointRate
   */
  public void setBonusPointRate(String bonusPointRate) {
    this.bonusPointRate = bonusPointRate;
  }

  /**
   * bonusPointStartを設定します。
   * 
   * @param bonusPointStart
   *          bonusPointStart
   */
  public void setBonusPointStart(String bonusPointStart) {
    this.bonusPointStart = bonusPointStart;
  }

  /**
   * customerRegisterPointを設定します。
   * 
   * @param customerRegisterPoint
   *          customerRegisterPoint
   */
  public void setCustomerRegisterPoint(String customerRegisterPoint) {
    this.customerRegisterPoint = customerRegisterPoint;
  }

  /**
   * enableFlgを設定します。
   * 
   * @param enableFlg
   *          enableFlg
   */
  public void setEnableFlg(String enableFlg) {
    this.enableFlg = enableFlg;
  }

  /**
   * firstPointを設定します。
   * 
   * @param firstPoint
   *          firstPoint
   */
  public void setFirstPoint(String firstPoint) {
    this.firstPoint = firstPoint;
  }

  /**
   * pointPeriodを設定します。
   * 
   * @param pointPeriod
   *          pointPeriod
   */
  public void setPointPeriod(String pointPeriod) {
    this.pointPeriod = pointPeriod;
  }

  /**
   * pointRateを設定します。
   * 
   * @param pointRate
   *          pointRate
   */
  public void setPointRate(String pointRate) {
    this.pointRate = pointRate;
  }

  /**
   * punchasePriceを設定します。
   * 
   * @param punchasePrice
   *          punchasePrice
   */
  public void setPunchasePrice(String punchasePrice) {
    this.punchasePrice = punchasePrice;
  }

  /**
   * reviewPointを設定します。
   * 
   * @param reviewPoint
   *          reviewPoint
   */
  public void setReviewPoint(String reviewPoint) {
    this.reviewPoint = reviewPoint;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {

  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    setEnableFlg(reqparam.get("enableFlg"));
    setPointPeriod(reqparam.get("pointPeriod"));
    setPointRate(reqparam.get("pointRate"));
    setPunchasePrice(reqparam.get("punchasePrice"));
    setBonusPointRate(reqparam.get("bonusPointRate"));
    setBonusPointStart(reqparam.getDateString(("bonusPointStart")));
    setBonusPointEnd(reqparam.getDateString("bonusPointEnd"));
    setBonusPointDate(reqparam.get("bonusPointDate"));
    setCustomerRegisterPoint(reqparam.get("customerRegisterPoint"));
    setFirstPoint(reqparam.get("firstPoint"));
    setReviewPoint(reqparam.get("reviewPoint"));
    //add by zhanghaibin start 2010-05-18
//    setSmallUsePoint(reqparam.get("smallUsePoint"));
//    //setAmplificationRate(reqparam.get("amplificationRate"));
//    setRmbPointRate(reqparam.get("rmbPointRate"));
    //add by zhanghaibin end   2010-05-18
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.PointruleEditBean.0");
  }

}
