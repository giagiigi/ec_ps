//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.service.catalog; 
import java.io.Serializable;
import java.util.Date; 
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
  
/** 
 * 「タグ(Brand)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class CategorySearchPath implements Serializable{

  /** Serial Version UID */
  private static final long serialVersionUID = -1;


  private String categoryCode;
  
  private Long depth;
  
  private String cat1;
  
  private String cat2;
  
  private String cat3;
  
  private String parentCategoryCode;
  
  private String path;
  
  private Long displayOrder;

  public String getLink1(){
    return cat1.split("~")[0];
  }
  
  public String getLink2(){
    return cat2.split("~")[0];
  }
  
  public String getLink3(){
    return cat3.split("~")[0];
  }
  
  public String getName1(){
    return cat1.split("~")[1];
  }
  
  public String getName2(){
    return cat2.split("~")[1];
  }
  
  public String getName3(){
    return cat3.split("~")[1];
  }
  
  
  /**
   * @return the categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  
  /**
   * @param categoryCode the categoryCode to set
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  
  /**
   * @return the depth
   */
  public Long getDepth() {
    return depth;
  }

  
  /**
   * @param depth the depth to set
   */
  public void setDepth(Long depth) {
    this.depth = depth;
  }

  
  /**
   * @return the cat1
   */
  public String getCat1() {
    return cat1;
  }

  
  /**
   * @param cat1 the cat1 to set
   */
  public void setCat1(String cat1) {
    this.cat1 = cat1;
  }

  
  /**
   * @return the cat2
   */
  public String getCat2() {
    return cat2;
  }

  
  /**
   * @param cat2 the cat2 to set
   */
  public void setCat2(String cat2) {
    this.cat2 = cat2;
  }

  
  /**
   * @return the cat3
   */
  public String getCat3() {
    return cat3;
  }

  
  /**
   * @param cat3 the cat3 to set
   */
  public void setCat3(String cat3) {
    this.cat3 = cat3;
  }

  
  /**
   * @return the parentCategoryCode
   */
  public String getParentCategoryCode() {
    return parentCategoryCode;
  }

  
  /**
   * @param parentCategoryCode the parentCategoryCode to set
   */
  public void setParentCategoryCode(String parentCategoryCode) {
    this.parentCategoryCode = parentCategoryCode;
  }

  
  /**
   * @return the path
   */
  public String getPath() {
    return path;
  }

  
  /**
   * @param path the path to set
   */
  public void setPath(String path) {
    this.path = path;
  }

  
  /**
   * @return the display_order
   */
  public Long getDisplayOrder() {
    return displayOrder;
  }

  
  /**
   * @param display_order the display_order to set
   */
  public void setDisplayOrder(Long displayOrder) {
    this.displayOrder = displayOrder;
  }




}
