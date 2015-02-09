package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1061210:顾客留言管理。
 * 
 * @author KS.
 */
public class CustomerMessageBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();

  @AlphaNum2
  @Length(16)
  @Metadata(name = "顾客编号")
  private String searchCustomerCode;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "搜索留言时间(From)")
  private String searchMessageStartDatetimeFrom;
  

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "搜索留言时间(To)")
  private String searchMessageEndDatetimeTo;
  

  /** 更新权限持有区分 */
  private Boolean updateAuthorizeFlg;

  /** 删除权限持有区分 */
  private Boolean deleteAuthorizeFlg;

  private List<MessageDetailBean> list = new ArrayList<MessageDetailBean>();

  /**
   * @return the searchCustomerCode
   */
  public String getSearchCustomerCode() {
    return searchCustomerCode;
  }

  /**
   * @return the searchMessageDatetime
   */
  public String getSearchMessageStartDatetimeFrom() {
    return searchMessageStartDatetimeFrom;
  }

  
  /**
   * @return the searchMessageEndDatetimeTo
   */
  public String getSearchMessageEndDatetimeTo() {
    return searchMessageEndDatetimeTo;
  }

  
  /**
   * @param searchMessageEndDatetimeTo the searchMessageEndDatetimeTo to set
   */
  public void setSearchMessageEndDatetimeTo(String searchMessageEndDatetimeTo) {
    this.searchMessageEndDatetimeTo = searchMessageEndDatetimeTo;
  }

  /**
   * @return the updateAuthorizeFlg
   */
  public Boolean getUpdateAuthorizeFlg() {
    return updateAuthorizeFlg;
  }

  /**
   * @return the deleteAuthorizeFlg
   */
  public Boolean getDeleteAuthorizeFlg() {
    return deleteAuthorizeFlg;
  }

  /**
   * @return the list
   */
  public List<MessageDetailBean> getList() {
    return list;
  }

  /**
   * @param searchCustomerCode
   *          the searchCustomerCode to set
   */
  public void setSearchCustomerCode(String searchCustomerCode) {
    this.searchCustomerCode = searchCustomerCode;
  }

  /**
   * @param searchMessageDatetime
   *          the searchMessageDatetime to set
   */
  public void setSearchMessageStartDatetimeFrom(String searchMessageStartDatetimeFrom) {
    this.searchMessageStartDatetimeFrom = searchMessageStartDatetimeFrom;
  }

  /**
   * @param updateAuthorizeFlg
   *          the updateAuthorizeFlg to set
   */
  public void setUpdateAuthorizeFlg(Boolean updateAuthorizeFlg) {
    this.updateAuthorizeFlg = updateAuthorizeFlg;
  }

  /**
   * @param deleteAuthorizeFlg
   *          the deleteAuthorizeFlg to set
   */
  public void setDeleteAuthorizeFlg(Boolean deleteAuthorizeFlg) {
    this.deleteAuthorizeFlg = deleteAuthorizeFlg;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<MessageDetailBean> list) {
    this.list = list;
  }

  /**
   * U1061010:促销活动のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class MessageDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // 顾客编号
    private String customerCode;

    // 留言时间
    private String messageDatetime;

    // 顾客留言内容
    private String messageContent;
    
    // 留言的rowid（唯一）
    private long ormRowid;
    

    
    /**
     * @return the ormRowid
     */
    public long getOrmRowid() {
      return ormRowid;
    }

    
    /**
     * @param ormRowid the ormRowid to set
     */
    public void setOrmRowid(long ormRowid) {
      this.ormRowid = ormRowid;
    }

    /**
     * @return the discountCode
     */
    public String getCustomerCode() {
      return customerCode;
    }

    /**
     * @param customerCode
     *          the customerCode to set
     */
    public void setCustomerCode(String customerCode) {
      this.customerCode = customerCode;
    }
    
    public String getMessageContent() {
      return messageContent;
    }
    
    public void setMessageContent(String messageContent) {
      this.messageContent = messageContent;
    }
    
    /**
     * @return the messageDatetime
     */
    public String getMessageDatetime() {
      return messageDatetime;
    }

    /**
     * @param messageDatetime
     *          the messageDatetime to set
     */
    public void setMessageDatetime(String messageDatetime) {
      this.messageDatetime = messageDatetime;
    }
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          設定する pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
    setSearchCustomerCode(reqparam.get("searchCustomerCode"));
    setSearchMessageStartDatetimeFrom(reqparam.getDateTimeString("searchMessageStartDatetimeFrom"));
    setSearchMessageEndDatetimeTo(reqparam.getDateTimeString("searchMessageEndDatetimeTo"));
  }

  @Override
  public String getModuleId() {
    return "U1061220";
  }

  @Override
  public void setSubJspId() {
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.CustomerMessageBean.0");
  }

}
