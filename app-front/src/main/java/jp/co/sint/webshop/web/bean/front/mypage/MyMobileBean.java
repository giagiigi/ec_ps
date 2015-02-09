package jp.co.sint.webshop.web.bean.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030210:お客様情報登録1のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class MyMobileBean extends UIFrontBean {


  private static final long serialVersionUID = 1L;

  /** 手机号码 */
  @Length(11)
  @MobileNumber
  @Metadata(name = "旧手机号码")
  private String oldMobileNumber;

  /** 手机号码 */
  @Required
  @Length(11)
  @MobileNumber
  @Metadata(name = "新的手机号码")
  private String mobileNumber;


  // 20111214 lirong add start
  /** 验证码 */
  @Required
  @Length(6)
  @Digit
  @Metadata(name = "验证码")
  private String authCode;

  /**
   */
  /** 语言编号 */
  @Length(5)
  @Metadata(name = "语言信息")
  private String languageCode;
  
  private String url;
  
  
  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
    addSubJspId("/catalog/topic_path");
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    setMobileNumber(reqparam.get("mobileNumber"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2030210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.action.front.common.mobileAuthBean.0");
  }

  /**
   * mobileNumberを取得します。
   * 
   * @return mobileNumber mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  /**
   * mobileNumberを設定します。
   * 
   * @param mobileNumber
   *          mobileNumber
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }


  /**
   * @return the oldMobileNumber
   */
  public String getOldMobileNumber() {
    return oldMobileNumber;
  }

  /**
   * @param oldMobileNumber
   *          the oldMobileNumber to set
   */
  public void setOldMobileNumber(String oldMobileNumber) {
    this.oldMobileNumber = oldMobileNumber;
  }

  /**
   * @return the authCode
   */
  public String getAuthCode() {
    return authCode;
  }

  /**
   * @param authCode
   *          the authCode to set
   */
  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }

  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.OrderHistoryBean.1"), "/app/mypage/mypage")); //$NON-NLS-1$ //$NON-NLS-2$
    if (!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)) {
      topicPath.add(new NameValue(Messages.getString("web.action.front.order.GiftCardRule.20"), "/app/mypage/gift_card/init")); //$NON-NLS-1$ //$NON-NLS-2$
    } else {
      topicPath.add(new NameValue(Messages.getString("web.action.front.order.GiftCardRule.20"), "/app/mypage/gift_card/init")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    return topicPath;
  }

  
  /**
   * @return the languageCode
   */
  public String getLanguageCode() {
    return languageCode;
  }

  
  /**
   * @param languageCode the languageCode to set
   */
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  
  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  
  /**
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

}
