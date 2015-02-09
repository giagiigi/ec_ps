package jp.co.sint.webshop.web.bean.front.common;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2010110:ログインのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class LoginBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** ログインID */
  @Required
  @Length(256)
  @Metadata(name = "ログインID", order = 1)
  private String loginId;

  /** パスワード */
  @Required
  @Length(50)
  @Metadata(name = "パスワード", order = 2)
  private String password;
  
  private String advertiseLinkUrl;
  
  private String advertiseImageUrl;

  private boolean guestOrderButtonDisplayFlg;

  private String guestOrderUrlParameter;
  //20111222 os013 add start
  private Object fastpayFormObject;
  
  private boolean failed = false;
  
  public Object getFastpayFormObject() {
    return fastpayFormObject;
  }

  
  public void setFastpayFormObject(Object fastpayFormObject) {
    this.fastpayFormObject = fastpayFormObject;
  }

  //20111222 os013 add end
  /**
   * guestOrderUrlParameterを取得します。
   * 
   * @return guestOrderUrlParameter
   */
  public String getGuestOrderUrlParameter() {
    return guestOrderUrlParameter;
  }

  /**
   * guestOrderUrlParameterを設定します。
   * 
   * @param guestOrderUrlParameter
   *          guestOrderUrlParameter
   */
  public void setGuestOrderUrlParameter(String guestOrderUrlParameter) {
    this.guestOrderUrlParameter = guestOrderUrlParameter;
  }

  /**
   * guestOrderButtonDisplayFlgを取得します。
   * 
   * @return guestOrderButtonDisplayFlg
   */
  public boolean isGuestOrderButtonDisplayFlg() {
    return guestOrderButtonDisplayFlg;
  }

  /**
   * guestOrderButtonDisplayFlgを設定します。
   * 
   * @param guestOrderButtonDisplayFlg
   *          設定する guestOrderButtonDisplayFlg
   */
  public void setGuestOrderButtonDisplayFlg(boolean guestOrderButtonDisplayFlg) {
    this.guestOrderButtonDisplayFlg = guestOrderButtonDisplayFlg;
  }

  /**
   * loginIdを取得します。
   * 
   * @return loginId
   */
  public String getLoginId() {
    return loginId;
  }

  /**
   * loginIdを設定します。
   * 
   * @param loginId
   *          loginId
   */
  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  /**
   * passwordを取得します。
   * 
   * @return password
   */
  public String getPassword() {
    return password;
  }

  /**
   * passwordを設定します。
   * 
   * @param password
   *          password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    if (reqparam.isGetFlg()) {
      return;
    }
    this.setLoginId(StringUtil.coalesceEmptyValue(reqparam.get("loginId"), this.getLoginId()));
    this.setPassword(StringUtil.coalesceEmptyValue(reqparam.get("password"), this.getPassword()));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2010110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
  if (StringUtil.isNullOrEmpty(getClient()) || getClient().equals(ClientType.OTHER)) {
		return Messages.getString("web.bean.front.common.LoginBean.0");
	}else{
		return Messages.getString("web.bean.mobile.common.LoginBean.0");
	}
    
  }

  
  /**
   * advertiseImageUrlを取得します。
   *
   * @return advertiseImageUrl
   */
  public String getAdvertiseImageUrl() {
    return advertiseImageUrl;
  }

  
  /**
   * advertiseImageUrlを設定します。
   *
   * @param advertiseImageUrl
   *          advertiseImageUrl
   */
  public void setAdvertiseImageUrl(String advertiseImageUrl) {
    this.advertiseImageUrl = advertiseImageUrl;
  }

  
  /**
   * advertiseLinkUrlを取得します。
   *
   * @return advertiseLinkUrl
   */
  public String getAdvertiseLinkUrl() {
    return advertiseLinkUrl;
  }

  
  /**
   * advertiseLinkUrlを設定します。
   *
   * @param advertiseLinkUrl
   *          advertiseLinkUrl
   */
  public void setAdvertiseLinkUrl(String advertiseLinkUrl) {
    this.advertiseLinkUrl = advertiseLinkUrl;
  }
  
  
  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    if(!StringUtil.isNullOrEmpty(getClient()) && !this.getClient().equals(ClientType.OTHER)){
    topicPath.add(new NameValue(Messages.getString(
        "web.bean.mobile.common.LoginBean.0"), "/app/common/login"));
    }
    return topicPath;
  }


  
  /**
   * @return the failed
   */
  public boolean isFailed() {
    return failed;
  }


  
  /**
   * @param failed the failed to set
   */
  public void setFailed(boolean failed) {
    this.failed = failed;
  }

}
