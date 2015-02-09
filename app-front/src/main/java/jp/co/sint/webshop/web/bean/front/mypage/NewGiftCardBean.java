package jp.co.sint.webshop.web.bean.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.service.customer.OwnerCardDetail;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030110:マイページのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class NewGiftCardBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Length(15)
  @Metadata(name = "Card passWord ", order = 1)
  private String giftCardPasswd;

  private boolean errorTimesFlg = false;

  private List<OwnerCardDetail> list = new ArrayList<OwnerCardDetail>();

  private List<String> rechargeList = new ArrayList<String>();

  private List<String> endDateList = new ArrayList<String>();

  private String totalDenomination;

  private String avalibleDenomination;

  private String password;

  private String email;

  private String oldPassword;

  private String passwordRepeat;

  private boolean updatePasswordFlg = false;

  private boolean disFlg = false;
  
  //选项卡
  private String tabIndex;
  
  private String giftCardNum;
  
  @Override
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    this.setGiftCardPasswd(reqparam.get("giftCardPasswd"));
    this.setPassword(reqparam.get("newPayPasswd"));
    this.setOldPassword(reqparam.get("payPasswd"));
    
  }

  @Override
  public String getModuleId() {
    return "";
  }

  @Override
  public String getModuleName() {
    return "礼品卡激活";
  }


  /**
   * @return the errorTimesFlg
   */
  public boolean isErrorTimesFlg() {
    return errorTimesFlg;
  }

  /**
   * @param errorTimesFlg
   *          the errorTimesFlg to set
   */
  public void setErrorTimesFlg(boolean errorTimesFlg) {
    this.errorTimesFlg = errorTimesFlg;
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
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
   * @return the list
   */
  public List<OwnerCardDetail> getList() {
    return list;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<OwnerCardDetail> list) {
    this.list = list;
  }

  /**
   * @return the rechargeList
   */
  public List<String> getRechargeList() {
    return rechargeList;
  }

  /**
   * @param rechargeList
   *          the rechargeList to set
   */
  public void setRechargeList(List<String> rechargeList) {
    this.rechargeList = rechargeList;
  }

  /**
   * @return the endDateList
   */
  public List<String> getEndDateList() {
    return endDateList;
  }

  /**
   * @param endDateList
   *          the endDateList to set
   */
  public void setEndDateList(List<String> endDateList) {
    this.endDateList = endDateList;
  }

  /**
   * @return the totalDenomination
   */
  public String getTotalDenomination() {
    return totalDenomination;
  }

  /**
   * @param totalDenomination
   *          the totalDenomination to set
   */
  public void setTotalDenomination(String totalDenomination) {
    this.totalDenomination = totalDenomination;
  }

  /**
   * @return the avalibleDenomination
   */
  public String getAvalibleDenomination() {
    return avalibleDenomination;
  }

  /**
   * @param avalibleDenomination
   *          the avalibleDenomination to set
   */
  public void setAvalibleDenomination(String avalibleDenomination) {
    this.avalibleDenomination = avalibleDenomination;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password
   *          the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the oldPassword
   */
  public String getOldPassword() {
    return oldPassword;
  }

  /**
   * @param oldPassword
   *          the oldPassword to set
   */
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  /**
   * @return the passwordRepeat
   */
  public String getPasswordRepeat() {
    return passwordRepeat;
  }

  /**
   * @param passwordRepeat
   *          the passwordRepeat to set
   */
  public void setPasswordRepeat(String passwordRepeat) {
    this.passwordRepeat = passwordRepeat;
  }

  /**
   * @return the updatePasswordFlg
   */
  public boolean isUpdatePasswordFlg() {
    return updatePasswordFlg;
  }

  /**
   * @param updatePasswordFlg
   *          the updatePasswordFlg to set
   */
  public void setUpdatePasswordFlg(boolean updatePasswordFlg) {
    this.updatePasswordFlg = updatePasswordFlg;
  }

  /**
   * @return the disFlg
   */
  public boolean isDisFlg() {
    return disFlg;
  }

  /**
   * @param disFlg
   *          the disFlg to set
   */
  public void setDisFlg(boolean disFlg) {
    this.disFlg = disFlg;
  }

  
  /**
   * @return the giftCardPasswd
   */
  public String getGiftCardPasswd() {
    return giftCardPasswd;
  }

  
  /**
   * @param giftCardPasswd the giftCardPasswd to set
   */
  public void setGiftCardPasswd(String giftCardPasswd) {
    this.giftCardPasswd = giftCardPasswd;
  }

  
  /**
   * @return the tabIndex
   */
  public String getTabIndex() {
    return tabIndex;
  }

  
  /**
   * @param tabIndex the tabIndex to set
   */
  public void setTabIndex(String tabIndex) {
    this.tabIndex = tabIndex;
  }

  
  /**
   * @return the giftCardNum
   */
  public String getGiftCardNum() {
    return giftCardNum;
  }

  
  /**
   * @param giftCardNum the giftCardNum to set
   */
  public void setGiftCardNum(String giftCardNum) {
    this.giftCardNum = giftCardNum;
  }
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
    addSubJspId("/catalog/topic_path");
  }
  
  
}
