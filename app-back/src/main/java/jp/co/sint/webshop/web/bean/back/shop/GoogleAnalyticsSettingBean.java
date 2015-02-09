package jp.co.sint.webshop.web.bean.back.shop;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;


public class GoogleAnalyticsSettingBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @Metadata(name = "Google Analytics使用区分")
  private String enableFlg;

  @Length(2000)
  @Metadata(name = "script内容")
  private String scriptText;
  
  private String display;

  private boolean registerButtonFlg;

  private Date updatedDatetime;
  
  private String googleAnalysisNo;
  
  
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
      setGoogleAnalysisNo(reqparam.get("googleAnalysisNo"));
      setScriptText(reqparam.get("scriptText"));
      
    }

    /**
     * モジュールIDを取得します。
     * 
     * @return モジュールID
     */
    public String getModuleId() {
      return "U1051020";
    }

    /**
     * モジュール名を取得します。
     * 
     * @return モジュール名
     */
    public String getModuleName() {
      return Messages.getString("web.bean.back.shop.GoogleAnalyticsSettingBean.0");
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

    
    public boolean isRegisterButtonFlg() {
      return registerButtonFlg;
    }

    
    public void setRegisterButtonFlg(boolean registerButtonFlg) {
      this.registerButtonFlg = registerButtonFlg;
    }

    
    public String getScriptText() {
      return scriptText;
    }

    
    public void setScriptText(String scriptText) {
      this.scriptText = scriptText;
    }

    
    public Date getUpdatedDatetime() {
      return updatedDatetime;
    }

    
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = updatedDatetime;
    }

    
    public String getGoogleAnalysisNo() {
      return googleAnalysisNo;
    }

    
    public void setGoogleAnalysisNo(String googleAnalysisNo) {
      this.googleAnalysisNo = googleAnalysisNo;
    }
}
