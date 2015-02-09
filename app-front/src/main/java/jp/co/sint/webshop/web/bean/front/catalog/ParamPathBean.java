package jp.co.sint.webshop.web.bean.front.catalog;

import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * パンくずリストのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ParamPathBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String categoryCode;

  private String brandCode;

  private String reviewScore;

  private String priceType;

  private String priceStart;

  private String priceEnd;

  private String attribute1;

  private String attribute2;

  private String attribute3;

  private String searchWord;

  private String paramPath;

  private List<CodeAttribute> paramPathList;
  
  private boolean preview = false;

  /**
   * categoryCodeを取得します。
   * 
   * @return categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * categoryCodeを設定します。
   * 
   * @param categoryCode
   *          categoryCode
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  
  /**
   * previewを取得します。
   *
   * @return preview
   */
  public boolean isPreview() {
    return preview;
  }

  
  /**
   * previewを設定します。
   *
   * @param preview 
   *          preview
   */
  public void setPreview(boolean preview) {
    this.preview = preview;
  }

  
  /**
   * @return the brandCode
   */
  public String getBrandCode() {
    return brandCode;
  }

  
  /**
   * @param brandCode the brandCode to set
   */
  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  
  /**
   * @return the reviewScore
   */
  public String getReviewScore() {
    return reviewScore;
  }

  
  /**
   * @param reviewScore the reviewScore to set
   */
  public void setReviewScore(String reviewScore) {
    this.reviewScore = reviewScore;
  }

  
  /**
   * @return the priceType
   */
  public String getPriceType() {
    return priceType;
  }

  
  /**
   * @param priceType the priceType to set
   */
  public void setPriceType(String priceType) {
    this.priceType = priceType;
  }

  
  /**
   * @return the attribute1
   */
  public String getAttribute1() {
    return attribute1;
  }

  
  /**
   * @param attribute1 the attribute1 to set
   */
  public void setAttribute1(String attribute1) {
    this.attribute1 = attribute1;
  }

  
  /**
   * @return the attribute2
   */
  public String getAttribute2() {
    return attribute2;
  }

  
  /**
   * @param attribute2 the attribute2 to set
   */
  public void setAttribute2(String attribute2) {
    this.attribute2 = attribute2;
  }

  
  /**
   * @return the attribute3
   */
  public String getAttribute3() {
    return attribute3;
  }

  
  /**
   * @param attribute3 the attribute3 to set
   */
  public void setAttribute3(String attribute3) {
    this.attribute3 = attribute3;
  }

  
  /**
   * @return the searchWord
   */
  public String getSearchWord() {
    return searchWord;
  }

  
  /**
   * @param searchWord the searchWord to set
   */
  public void setSearchWord(String searchWord) {
    this.searchWord = searchWord;
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
    
  }

  
  /**
   * @return the paramPathList
   */
  public List<CodeAttribute> getParamPathList() {
    return paramPathList;
  }

  
  /**
   * @param paramPathList the paramPathList to set
   */
  public void setParamPathList(List<CodeAttribute> paramPathList) {
    this.paramPathList = paramPathList;
  }

  
  /**
   * @return the priceStart
   */
  public String getPriceStart() {
    return priceStart;
  }

  
  /**
   * @param priceStart the priceStart to set
   */
  public void setPriceStart(String priceStart) {
    this.priceStart = priceStart;
  }

  
  /**
   * @return the priceEnd
   */
  public String getPriceEnd() {
    return priceEnd;
  }

  
  /**
   * @param priceEnd the priceEnd to set
   */
  public void setPriceEnd(String priceEnd) {
    this.priceEnd = priceEnd;
  }


  
  /**
   * @return the paramPath
   */
  public String getParamPath1() {
    return WebUtil.escapeXml(paramPath);
  }
  
  /**
   * @return the paramPath
   */
  public String getParamPath() {
    return paramPath;
  }

  
  /**
   * @param paramPath the paramPath to set
   */
  public void setParamPath(String paramPath) {
    this.paramPath = paramPath;
  }

}
