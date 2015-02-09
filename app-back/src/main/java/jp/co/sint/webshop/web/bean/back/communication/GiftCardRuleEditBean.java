package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.GiftCardIssueDetail;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class GiftCardRuleEditBean extends UIBackBean {

  
  private PagerValue pagerValue;
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /** 注册按钮状态 */
  private boolean displayRegistButtonFlg;

  /** 更新按钮状态 */
  private boolean displayUpdateButtonFlg;
  
  private boolean displayExportButtonFlg;

  @Length(16)
  @Digit
  @Metadata(name = "印刷编号条件")
  private String searchCardId;

  @Required
  @AlphaNum2
  @Length(16)
  @Metadata(name = "礼品卡编号", order = 1)
  private String cardCode;

  @Required
  @Length(50)
  @Metadata(name = "礼品卡名称", order = 2)
  private String cardName;

  @Required
  @Length(2)
  @Digit
  @Range(min=1,max=99)
  @Metadata(name = "有效期", order = 3)
  private String effectiveYears;

  @Required
  @Length(9)
  @Currency
  @Range(min=0,max=99999)
  @Precision(precision = 8, scale = 3)
  @Metadata(name = "卡重量", order = 4)
  private String weight;

  @Required
  @Length(11)
  @Currency
  @Precision(precision = 10, scale = 2)
  @Range(min=0,max=99999999)
  @Metadata(name = "单价", order = 5)
  private String unitPrice;

  @Required
  @Length(11)
  @Currency
  @Range(min=0,max=99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "面值", order = 6)
  private String denomination;

  @Length(4)
  @Digit
  @Range(min=1,max=9999)
  @Metadata(name = "发行张数", order = 7)
  private String issueNum;
  
  private String cardHistoryNo;
  
  private GiftCardIssueDetail detail=null;
  
  private List<GiftCardRuleEditBeanDetail> list = new ArrayList<GiftCardRuleEditBeanDetail>();

  private boolean displayRegisterButton;

  private boolean displayUpdateButton;

  private boolean choicesAreaDisplay;
  
  
  
  public static class GiftCardRuleEditBeanDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cardHistoryNo;
    
    private String sueNum;
    
    private String issueDate;
    
    private String csvFlg;
    
    private String cancelFlg;
    
    
    
    /**
     * @return the issueDate
     */
    public String getIssueDate() {
      return issueDate;
    }

    /**
     * @param issueDate
     *          the issueDate to set
     */
    public void setIssueDate(String issueDate) {
      this.issueDate = issueDate;
    }

    /**
     * @return the csvFlg
     */
    public String getCsvFlg() {
      return csvFlg;
    }

    /**
     * @param csvFlg
     *          the csvFlg to set
     */
    public void setCsvFlg(String csvFlg) {
      this.csvFlg = csvFlg;
    }

    /**
     * @return the cardHistoryNo
     */
    public String getCardHistoryNo() {
      return cardHistoryNo;
    }

    /**
     * @param cardHistoryNo
     *          the cardHistoryNo to set
     */
    public void setCardHistoryNo(String cardHistoryNo) {
      this.cardHistoryNo = cardHistoryNo;
    }

    
    /**
     * @return the sueNum
     */
    public String getSueNum() {
      return sueNum;
    }

    
    /**
     * @param sueNum the sueNum to set
     */
    public void setSueNum(String sueNum) {
      this.sueNum = sueNum;
    }

    
    /**
     * @return the cancelFlg
     */
    public String getCancelFlg() {
      return cancelFlg;
    }

    
    /**
     * @param cancelFlg the cancelFlg to set
     */
    public void setCancelFlg(String cancelFlg) {
      this.cancelFlg = cancelFlg;
    }

  }


  /**
   * @return the cardCode
   */
  public String getCardCode() {
    return cardCode;
  }

  /**
   * @param cardCode
   *          the cardCode to set
   */
  public void setCardCode(String cardCode) {
    this.cardCode = cardCode;
  }

  /**
   * @return the cardName
   */
  public String getCardName() {
    return cardName;
  }

  /**
   * @param cardName
   *          the cardName to set
   */
  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  /**
   * @return the effectiveYears
   */
  public String getEffectiveYears() {
    return effectiveYears;
  }

  /**
   * @param effectiveYears
   *          the effectiveYears to set
   */
  public void setEffectiveYears(String effectiveYears) {
    this.effectiveYears = effectiveYears;
  }

  /**
   * @return the weight
   */
  public String getWeight() {
    return weight;
  }

  /**
   * @param weight
   *          the weight to set
   */
  public void setWeight(String weight) {
    this.weight = weight;
  }

  /**
   * @return the unitPrice
   */
  public String getUnitPrice() {
    return unitPrice;
  }

  /**
   * @param unitPrice
   *          the unitPrice to set
   */
  public void setUnitPrice(String unitPrice) {
    this.unitPrice = unitPrice;
  }

  /**
   * @return the denomination
   */
  public String getDenomination() {
    return denomination;
  }

  /**
   * @param denomination
   *          the denomination to set
   */
  public void setDenomination(String denomination) {
    this.denomination = denomination;
  }
  @Override
  public void setSubJspId() {
  }

  @Override
  public String getModuleId() {
    return null;
  }

  @Override
  public String getModuleName() {
    if(displayRegisterButton==true){
      return "礼品卡规则登陆";
    }  
    if(displayUpdateButtonFlg==true){
        return "礼品卡规则更新";
    }
      return "礼品卡规则查看";
  }

  /**
   * @return the displayRegisterButton
   */
  public boolean isDisplayRegisterButton() {
    return displayRegisterButton;
  }

  /**
   * @param displayRegisterButton
   *          the displayRegisterButton to set
   */
  public void setDisplayRegisterButton(boolean displayRegisterButton) {
    this.displayRegisterButton = displayRegisterButton;
  }

  /**
   * @return the displayUpdateButton
   */
  public boolean isDisplayUpdateButton() {
    return displayUpdateButton;
  }

  /**
   * @param displayUpdateButton
   *          the displayUpdateButton to set
   */
  public void setDisplayUpdateButton(boolean displayUpdateButton) {
    this.displayUpdateButton = displayUpdateButton;
  }

  /**
   * @return the choicesAreaDisplay
   */
  public boolean isChoicesAreaDisplay() {
    return choicesAreaDisplay;
  }

  /**
   * @param choicesAreaDisplay
   *          the choicesAreaDisplay to set
   */
  public void setChoicesAreaDisplay(boolean choicesAreaDisplay) {
    this.choicesAreaDisplay = choicesAreaDisplay;
  }

  /**
   * @return the issueNum
   */
  public String getIssueNum() {
    return issueNum;
  }

  /**
   * @param issueNum
   *          the issueNum to set
   */
  public void setIssueNum(String issueNum) {
    this.issueNum = issueNum;
  }

  /**
   * @return the list
   */
  public List<GiftCardRuleEditBeanDetail> getList() {
    return list;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<GiftCardRuleEditBeanDetail> list) {
    this.list = list;
  }

  
  /**
   * @return the searchCardId
   */
  public String getSearchCardId() {
    return searchCardId;
  }

  
  /**
   * @param searchCardId the searchCardId to set
   */
  public void setSearchCardId(String searchCardId) {
    this.searchCardId = searchCardId;
  }

  
  /**
   * @return the pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  
  /**
   * @param pagerValue the pagerValue to set
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
    setSearchCardId(reqparam.get("searchCardId"));
    setCardCode(reqparam.get("cardCode"));
    setCardName(reqparam.get("cardName"));
    setEffectiveYears(reqparam.get("effectiveYears"));
    setWeight(reqparam.get("weight"));
    setUnitPrice(reqparam.get("unitPrice"));
    setDenomination(reqparam.get("denomination"));
    setIssueNum(reqparam.get("issueNum"));
  }

  
  /**
   * @return the cardHistoryNo
   */
  public String getCardHistoryNo() {
    return cardHistoryNo;
  }

  
  /**
   * @param cardHistoryNo the cardHistoryNo to set
   */
  public void setCardHistoryNo(String cardHistoryNo) {
    this.cardHistoryNo = cardHistoryNo;
  }

  
  /**
   * @return the displayRegistButtonFlg
   */
  public boolean isDisplayRegistButtonFlg() {
    return displayRegistButtonFlg;
  }

  
  /**
   * @param displayRegistButtonFlg the displayRegistButtonFlg to set
   */
  public void setDisplayRegistButtonFlg(boolean displayRegistButtonFlg) {
    this.displayRegistButtonFlg = displayRegistButtonFlg;
  }

  
  /**
   * @return the displayUpdateButtonFlg
   */
  public boolean isDisplayUpdateButtonFlg() {
    return displayUpdateButtonFlg;
  }

  
  /**
   * @param displayUpdateButtonFlg the displayUpdateButtonFlg to set
   */
  public void setDisplayUpdateButtonFlg(boolean displayUpdateButtonFlg) {
    this.displayUpdateButtonFlg = displayUpdateButtonFlg;
  }

  
  /**
   * @return the displayExportButtonFlg
   */
  public boolean isDisplayExportButtonFlg() {
    return displayExportButtonFlg;
  }

  
  /**
   * @param displayExportButtonFlg the displayExportButtonFlg to set
   */
  public void setDisplayExportButtonFlg(boolean displayExportButtonFlg) {
    this.displayExportButtonFlg = displayExportButtonFlg;
  }

  
  /**
   * @return the detail
   */
  public GiftCardIssueDetail getDetail() {
    return detail;
  }

  
  /**
   * @param detail the detail to set
   */
  public void setDetail(GiftCardIssueDetail detail) {
    this.detail = detail;
  }



}
