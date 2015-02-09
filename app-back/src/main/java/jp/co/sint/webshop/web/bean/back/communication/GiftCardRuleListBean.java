package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class GiftCardRuleListBean extends UIBackBean implements UISearchBean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue;

  private List<GiftCardRuleListBeanDetail> list = new ArrayList<GiftCardRuleListBeanDetail>();
 
  @Required
  @AlphaNum2
  @Length(16)
  @Metadata(name = "礼品卡编号条件")
  private String searchCardCode;

  @Required
  @Length(50)
  @Metadata(name = "礼品卡名称条件")
  private String searchCardName;
  
  private boolean registerNewDisplayFlg;
  
  
  
  public static class GiftCardRuleListBeanDetail implements Serializable {
  
        
          private static final long serialVersionUID = 1L;
        
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
          @Metadata(name = "有效期", order = 3)
          private String effectiveYears;
        
          @Required
          @Length(11)
          @Metadata(name = "卡重量", order = 4)
          private String weight;
        
          @Required
          @Length(11)
          @Metadata(name = "单价", order = 5)
          private String unitPrice;
        
          @Required
          @Length(11)
          @Metadata(name = "面值", order = 6)
          private String denomination;

          
          /**
           * @return the cardCode
           */
          public String getCardCode() {
            return cardCode;
          }

          
          /**
           * @param cardCode the cardCode to set
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
           * @param cardName the cardName to set
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
           * @param effectiveYears the effectiveYears to set
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
           * @param weight the weight to set
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
           * @param unitPrice the unitPrice to set
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
           * @param denomination the denomination to set
           */
          public void setDenomination(String denomination) {
            this.denomination = denomination;
          }

  }
  /**
   * @return the list
   */

  public List<GiftCardRuleListBeanDetail> getList() {
    return list;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<GiftCardRuleListBeanDetail> list) {
    this.list = list;
  }

  @Override
  public void setSubJspId() {
    // TODO Auto-generated method stub

  }

  @Override
  public String getModuleId() {
    // TODO Auto-generated method stub
    return "U1061410";
  }

  @Override
  public String getModuleName() {
    // TODO Auto-generated method stub
    return "礼品卡管理";
  }

  /**
   * @return the pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * @param pagerValue
   *          the pagerValue to set
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * @return the searchCardCode
   */
  public String getSearchCardCode() {
    return searchCardCode;
  }

  /**
   * @param searchCardCode
   *          the searchCardCode to set
   */
  public void setSearchCardCode(String searchCardCode) {
    this.searchCardCode = searchCardCode;
  }

  /**
   * @return the searchCardName
   */
  public String getSearchCardName() {
    return searchCardName;
  }

  /**
   * @param searchCardName
   *          the searchCardName to set
   */
  public void setSearchCardName(String searchCardName) {
    this.searchCardName = searchCardName;
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
    // TODO Auto-generated method stub
    reqparam.copy(this);
  }

  
  /**
   * @return the registerNewDisplayFlg
   */
  public boolean isRegisterNewDisplayFlg() {
    return registerNewDisplayFlg;
  }

  
  /**
   * @param registerNewDisplayFlg the registerNewDisplayFlg to set
   */
  public void setRegisterNewDisplayFlg(boolean registerNewDisplayFlg) {
    this.registerNewDisplayFlg = registerNewDisplayFlg;
  }

}
