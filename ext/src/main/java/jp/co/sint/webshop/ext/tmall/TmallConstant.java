package jp.co.sint.webshop.ext.tmall;

import java.io.Serializable;

public class TmallConstant implements Serializable {

  private static final long serialVersionUID = 1L;

  private String appKey;

  private String appSercet;

  private String sessionKey;

  private String reqUrl;

  private String apiLogpath;

  public String getReqUrl() {
    return reqUrl;
  }

  public void setReqUrl(String reqUrl) {
    this.reqUrl = reqUrl;
  }

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public String getAppSercet() {
    return appSercet;
  }

  public void setAppSercet(String appSercet) {
    this.appSercet = appSercet;
  }

  public String getSessionKey() {
    return sessionKey;
  }

  public void setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
  }

  public String getApiLogpath() {
    return apiLogpath;
  }

  public void setApiLogpath(String apiLogpath) {
    this.apiLogpath = apiLogpath;
  }

}
