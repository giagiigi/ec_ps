package jp.co.sint.webshop.api.jd;

/**
 * 京东API用配置文件
 *
 * @author OB Corp.
 */
public class JdApiConfig {

  private static final long serialVersionUID = 1L;

  /** 连接用Key */
  private String appKey;

  /** 连接用secret */
  private String appSercet;

  /** 连接用Token */
  private String sessionKey;

  /** 服务器地址URL */
  private String reqUrl;
  
  /** 上传图片返回地址前置 */
  private String jdImgUrl;
  
  /** 调用api日志地址 */
  private String apiLogpath;

  /**
   * @return the appKey
   */
  public String getAppKey() {
    return appKey;
  }

  /**
   * @param appKey the appKey to set
   */
  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  /**
   * @return the appSercet
   */
  public String getAppSercet() {
    return appSercet;
  }

  /**
   * @param appSercet the appSercet to set
   */
  public void setAppSercet(String appSercet) {
    this.appSercet = appSercet;
  }

  /**
   * @return the sessionKey
   */
  public String getSessionKey() {
    return sessionKey;
  }

  /**
   * @param sessionKey the sessionKey to set
   */
  public void setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
  }

  /**
   * @return the reqUrl
   */
  public String getReqUrl() {
    return reqUrl;
  }

  /**
   * @param reqUrl the reqUrl to set
   */
  public void setReqUrl(String reqUrl) {
    this.reqUrl = reqUrl;
  }

  /**
   * @return the jdImgUrl
   */
  public String getJdImgUrl() {
    return jdImgUrl;
  }

  /**
   * @param jdImgUrl the jdImgUrl to set
   */
  public void setJdImgUrl(String jdImgUrl) {
    this.jdImgUrl = jdImgUrl;
  }

  
  /**
   * @return the apiLogpath
   */
  public String getApiLogpath() {
    return apiLogpath;
  }

  
  /**
   * @param apiLogpath the apiLogpath to set
   */
  public void setApiLogpath(String apiLogpath) {
    this.apiLogpath = apiLogpath;
  }


  
}
