package jp.co.sint.webshop.web.bean.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
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
public class OwnerCardBean extends UIFrontBean {
  
  /** serial version uid */
  private static final long serialVersionUID = 1L;
  
  private String totalDenomination;
  
  private String avalibleDenomination;
  
  private List<OwnerCardDetail> list = new ArrayList<OwnerCardDetail>();
  
  private List<String> rechargeList = new ArrayList<String>();
  
  private List<String> endDateList = new ArrayList<String>(); 
  

  @Override
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    
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
   * @return the totalDenomination
   */
  public String getTotalDenomination() {
    return totalDenomination;
  }

  
  /**
   * @param totalDenomination the totalDenomination to set
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
   * @param avalibleDenomination the avalibleDenomination to set
   */
  public void setAvalibleDenomination(String avalibleDenomination) {
    this.avalibleDenomination = avalibleDenomination;
  }

  
  
  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    if(!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)){
       topicPath.add(new NameValue(Messages.getString(
              "web.bean.front.mypage.OrderHistoryBean.1"), "/app/mypage/mypage")); //$NON-NLS-1$ //$NON-NLS-2$
      topicPath.add(new NameValue(Messages.getString("web.action.front.order.GiftCardRule.21"), "/app/mypage/owner_card/init")); //$NON-NLS-1$ //$NON-NLS-2$
    }else{
      topicPath.add(new NameValue(Messages.getString("web.action.front.order.GiftCardRule.21"), "/app/mypage/owner_card/init")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    return topicPath;
  }

  
  /**
   * @return the rechargeList
   */
  public List<String> getRechargeList() {
    return rechargeList;
  }

  
  /**
   * @param rechargeList the rechargeList to set
   */
  public void setRechargeList(List<String> rechargeList) {
    this.rechargeList = rechargeList;
  }

  
  /**
   * @return the endDateMapList
   */
  public List<String> getEndDateList() {
    return endDateList;
  }

  
  /**
   * @param endDateMapList the endDateMapList to set
   */
  public void setEndDateList(List<String> endDateList) {
    this.endDateList = endDateList;
  }

  
  /**
   * @return the list
   */
  public List<OwnerCardDetail> getList() {
    return list;
  }

  
  /**
   * @param list the list to set
   */
  public void setList(List<OwnerCardDetail> list) {
    this.list = list;
  }
  
}
