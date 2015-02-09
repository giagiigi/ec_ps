package jp.co.sint.webshop.web.bean.back.shop;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class CouponIssueDetailBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** shopCode */
  @Metadata(name = "店铺编号", order = 1)
  private String shopCode;

  /** クーポンルール番号 */
  @Length(8)
  @Digit
  @Metadata(name = "クーポン番号", order = 2)
  private String couponIssueNo;

  /** クーポン管理側名 */
  @Required
  @Length(40)
  @Metadata(name = "クーポン管理側名", order = 3)
  private String couponName;

  /** クーポンフラント金額 */
  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "クーポンフラント金額", order = 5)
  private String couponPrice;

  /** クーポンフラント金額 */
  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "クーポン取得金額", order = 5)
  private String getCouponPrice;

  /** クーポン発行開始日 */
  @Required
  @Datetime
  @Metadata(name = "クーポン発行開始日", order = 6)
  private String bonusCouponStartDate;

  /** クーポン発行終了日 */
  @Required
  @Datetime
  @Metadata(name = "クーポン発行終了日", order = 7)
  private String bonusCouponEndDate;

  /** クーポン使用開始日 */
  @Required
  @Datetime
  @Metadata(name = "クーポン使用開始日", order = 8)
  private String useCouponStartDate;

  /** クーポン使用終了日 */
  @Required
  @Datetime
  @Metadata(name = "クーポン使用終了日", order = 9)
  private String useCouponEndDate;

  @Metadata(name = "登録更新モード")
  private boolean updateModeFlg;

  private Date updateDate;

  private boolean updateFlg;

  private String registerModeDisplay;

  private boolean bankMoveButtonFlg;

  public String getBonusCouponEndDate() {
    return bonusCouponEndDate;
  }

  public void setBonusCouponEndDate(String bonusCouponEndDate) {
    this.bonusCouponEndDate = bonusCouponEndDate;
  }

  public String getBonusCouponStartDate() {
    return bonusCouponStartDate;
  }

  public void setBonusCouponStartDate(String bonusCouponStartDate) {
    this.bonusCouponStartDate = bonusCouponStartDate;
  }

  public String getCouponIssueNo() {
    return couponIssueNo;
  }

  public void setCouponIssueNo(String couponIssueNo) {
    this.couponIssueNo = couponIssueNo;
  }

  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public String getUseCouponEndDate() {
    return useCouponEndDate;
  }

  public void setUseCouponEndDate(String useCouponEndDate) {
    this.useCouponEndDate = useCouponEndDate;
  }

  public String getUseCouponStartDate() {
    return useCouponStartDate;
  }

  public void setUseCouponStartDate(String useCouponStartDate) {
    this.useCouponStartDate = useCouponStartDate;
  }

  @Override
  public void setSubJspId() {
    // TODO Auto-generated method stub

  }

  public void createAttributes(RequestParameter reqparam) {
    this.setCouponIssueNo(reqparam.get("couponIssueNo"));
    this.setCouponName(reqparam.get("couponName"));
    this.setCouponPrice(reqparam.get("couponPrice"));
    this.setGetCouponPrice(reqparam.get("getCouponPrice"));
    this.setBonusCouponStartDate(reqparam.getDateString("bonusCouponStartDate"));
    this.setBonusCouponEndDate(reqparam.getDateString("bonusCouponEndDate"));
    this.setUseCouponStartDate(reqparam.getDateString("useCouponStartDate"));
    this.setUseCouponEndDate(reqparam.getDateString("useCouponEndDate"));
  }

  public String getModuleId() {
    // TODO Auto-generated method stub
    return "U1050520";
  }

  public String getModuleName() {
    // TODO Auto-generated method stub
    return Messages.getString("web.bean.back.shop.CouponIssueDetailBean.0");
  }

  public boolean getBankMoveButtonFlg() {
    return bankMoveButtonFlg;
  }

  public void setBankMoveButtonFlg(boolean bankMoveButtonFlg) {
    this.bankMoveButtonFlg = bankMoveButtonFlg;
  }

  public String getRegisterModeDisplay() {
    return registerModeDisplay;
  }

  public void setRegisterModeDisplay(String registerModeDisplay) {
    this.registerModeDisplay = registerModeDisplay;
  }

  public Date getUpdateDate() {
    return DateUtil.immutableCopy(updateDate);
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = DateUtil.immutableCopy(updateDate);
  }

  public boolean getUpdateFlg() {
    return updateFlg;
  }

  public void setUpdateFlg(boolean updateFlg) {
    this.updateFlg = updateFlg;
  }

  public boolean getUpdateModeFlg() {
    return updateModeFlg;
  }

  public void setUpdateModeFlg(boolean updateModeFlg) {
    this.updateModeFlg = updateModeFlg;
  }

  public String getCouponPrice() {
    return couponPrice;
  }

  public void setCouponPrice(String couponPrice) {
    this.couponPrice = couponPrice;
  }

  public String getGetCouponPrice() {
    return getCouponPrice;
  }

  public void setGetCouponPrice(String getCouponPrice) {
    this.getCouponPrice = getCouponPrice;
  }
}
