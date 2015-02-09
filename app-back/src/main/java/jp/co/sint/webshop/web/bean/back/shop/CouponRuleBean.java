package jp.co.sint.webshop.web.bean.back.shop;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class CouponRuleBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @Metadata(name = "電子クーポン使用フラグ")
  private String enableFlg;

  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "電子クーポン使用最低受注金額")
  private String punchasePrice;

  @Required
  @Length(2)
  @Digit(allowNegative = false)
  @Metadata(name = "一受注使用可能の電子クーポン枚数")
  private String usebleAmount;

  private String display;

  private boolean registerButtonFlg;
  
  private Date UpdatedDatetime;

  public boolean isRegisterButtonFlg() {
    return registerButtonFlg;
  }

  public void setRegisterButtonFlg(boolean registerButtonFlg) {
    this.registerButtonFlg = registerButtonFlg;
  }

  @Override
  public void setSubJspId() {
    // TODO Auto-generated method stub

  }

  public void createAttributes(RequestParameter reqparam) {
    // TODO Auto-generated method stub
    setEnableFlg(reqparam.get("enableFlg"));
    setUsebleAmount(reqparam.get("usebleAmount"));
    setPunchasePrice(reqparam.get("punchasePrice"));
  }

  public String getModuleId() {
    // TODO Auto-generated method stub
    return "U1051040";
  }

  public String getModuleName() {
    // TODO Auto-generated method stub
    return Messages.getString("web.bean.back.shop.CouponRuleBean.0");
  }

  public String getDisplay() {
    return display;
  }

  public void setDisplay(String display) {
    this.display = display;
  }

  public String getEnableFlg() {
    return enableFlg;
  }

  public void setEnableFlg(String enableFlg) {
    this.enableFlg = enableFlg;
  }

  public String getPunchasePrice() {
    return punchasePrice;
  }

  public void setPunchasePrice(String punchasePrice) {
    this.punchasePrice = punchasePrice;
  }

  public String getUsebleAmount() {
    return usebleAmount;
  }

  public void setUsebleAmount(String usebleAmount) {
    this.usebleAmount = usebleAmount;
  }

  
  public Date getUpdatedDatetime() {
    return UpdatedDatetime;
  }

  
  public void setUpdatedDatetime(Date updatedDatetime) {
    UpdatedDatetime = updatedDatetime;
  }
}
