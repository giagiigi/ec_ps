package jp.co.sint.webshop.web.bean.back.shop;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1051010:在线客服のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OnlineserviceEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  //Add by v10-CH start
  
  @Metadata(name = "在线编号")
  private Long online_service_no;
  //Add by V10-CH end
  
  @Required
  @Metadata(name = "在线客服区分")
  private String enableFlg;

  @Length(2000)
  @Metadata(name = "script内容1")
  private String scriptText1;

  @Length(2000)
  @Metadata(name = "script内容2")
  private String scriptText2;
  
  @Length(2000)
  @Metadata(name = "script内容3")
  private String scriptText3;
  
  //Add by V10-CH start
  @Metadata(name = "店铺编号")
  private String shopCode;
  //Add by v10-CH end
  
  private String display;

  private boolean registerButtonFlg;

  private Date updatedDatetime;

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
    setScriptText1(reqparam.get("scriptText1"));
    setScriptText2(reqparam.get("scriptText2"));
    setScriptText3(reqparam.get("scriptText3"));
    if(!reqparam.get("online_service_no").equals("")){
      setOnline_service_no(Long.parseLong(reqparam.get("online_service_no")));
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1051010";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.OnlineserviceEditBean.0");
  }

  
  public String getEnableFlg() {
    return enableFlg;
  }

  
  public void setEnableFlg(String enableFlg) {
    this.enableFlg = enableFlg;
  }

  
  public String getScriptText1() {
    return scriptText1;
  }

  
  public void setScriptText1(String scriptText1) {
    this.scriptText1 = scriptText1;
  }

  
  public String getScriptText2() {
    return scriptText2;
  }

  
  public void setScriptText2(String scriptText2) {
    this.scriptText2 = scriptText2;
  }

  //Add by V10-CH start
  /**
   * scriptText3を取得します。
   *
   * @return scriptText3 scriptText3
   */
  public String getScriptText3() {
    return scriptText3;
  }

  
  /**
   * scriptText3を設定します。
   *
   * @param scriptText3 
   *          scriptText3
   */
  public void setScriptText3(String scriptText3) {
    this.scriptText3 = scriptText3;
  }
   //Add by V10-CH end

  
  /**
   * shopCodeを取得します。
   *
   * @return shopCode shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  
  /**
   * shopCodeを設定します。
   *
   * @param shopCode 
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  
  /**
   * online_service_noを取得します。
   *
   * @return online_service_no online_service_no
   */
  public Long getOnline_service_no() {
    return online_service_no;
  }

  
  /**
   * online_service_noを設定します。
   *
   * @param online_service_no 
   *          online_service_no
   */
  public void setOnline_service_no(Long online_service_no) {
    this.online_service_no = online_service_no;
  }
}
