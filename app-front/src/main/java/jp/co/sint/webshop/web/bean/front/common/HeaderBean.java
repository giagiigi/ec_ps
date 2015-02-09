package jp.co.sint.webshop.web.bean.front.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2010000:ヘッダーのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class HeaderBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String categoryTreeCondition;

  private List<CodeAttribute> categoryTreeList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> subPathList = new ArrayList<CodeAttribute>();

  private boolean firstRank;

  private int itemCount = 0;
  
  private BigDecimal grandTotal;
  
  private BigDecimal weightTotal;

  private List<CartItem> cartItemList = new ArrayList<CartItem>();

  /**
   * firstRankを取得します。
   * 
   * @return firstRank
   */

  public boolean isFirstRank() {
    return firstRank;
  }

  /**
   * firstRankを設定します。
   * 
   * @param firstRank
   *          firstRank
   */
  public void setFirstRank(boolean firstRank) {
    this.firstRank = firstRank;
  }

  /**
   * subPathListを取得します。
   * 
   * @return subPathList
   */

  public List<CodeAttribute> getSubPathList() {
    return subPathList;
  }

  /**
   * subPathListを設定します。
   * 
   * @param subPathList
   *          subPathList
   */
  public void setSubPathList(List<CodeAttribute> subPathList) {
    this.subPathList = subPathList;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.setCategoryTreeCondition(StringUtil.coalesce(reqparam.get("searchCategoryCode"), this.categoryTreeCondition));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2010000";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.common.HeaderBean.0");
  }

  /**
   * categoryTreeConditionを取得します。
   * 
   * @return categoryTreeCondition
   */
  public String getCategoryTreeCondition() {
    return categoryTreeCondition;
  }

  /**
   * categoryTreeConditionを設定します。
   * 
   * @param categoryTreeCondition
   *          categoryTreeCondition
   */
  public void setCategoryTreeCondition(String categoryTreeCondition) {
    this.categoryTreeCondition = categoryTreeCondition;
  }

  /**
   * categoryTreeListを取得します。
   * 
   * @return categoryTreeList
   */
  public List<CodeAttribute> getCategoryTreeList() {
    return categoryTreeList;
  }

  /**
   * categoryTreeListを設定します。
   * 
   * @param categoryTreeList
   *          categoryTreeList
   */
  public void setCategoryTreeList(List<CodeAttribute> categoryTreeList) {
    this.categoryTreeList = categoryTreeList;
  }

  
  /**
   * @return the itemCount
   */
  public int getItemCount() {
    return itemCount;
  }

  
  /**
   * @param itemCount the itemCount to set
   */
  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  
  /**
   * @return the cartItemList
   */
  public List<CartItem> getCartItemList() {
    return cartItemList;
  }

  
  /**
   * @param cartItemList the cartItemList to set
   */
  public void setCartItemList(List<CartItem> cartItemList) {
    this.cartItemList = cartItemList;
  }

  
  /**
   * @return the grandTotal
   */
  public BigDecimal getGrandTotal() {
    return grandTotal;
  }

  
  /**
   * @param grandTotal the grandTotal to set
   */
  public void setGrandTotal(BigDecimal grandTotal) {
    this.grandTotal = grandTotal;
  }

  
  /**
   * @return the weightTotal
   */
  public BigDecimal getWeightTotal() {
    return weightTotal;
  }

  
  /**
   * @param weightTotal the weightTotal to set
   */
  public void setWeightTotal(BigDecimal weightTotal) {
    this.weightTotal = weightTotal;
  }

}
