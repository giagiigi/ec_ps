package jp.co.sint.webshop.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员咨询情报
 * 
 * @author Swj.
 */
public class MemberInquiryConfig implements Serializable {

  /**
   * serial version UID
   */
  private static final long serialVersionUID = 1L;

  /** 大分类 */
  private List<String> largeCategory = new ArrayList<String>();

  /** 小分类 */
  private HashMap<String, List<String>> smallCategory = new HashMap<String, List<String>>();

  /**
   * largeCategoryを取得します。
   * 
   * @return largeCategory
   */
  public List<String> getLargeCategory() {
    List<String> result = CodeUtil.getListEntry("largeCategory");
    if (result == null || result.isEmpty()) {
      return largeCategory;
    }
    return result;
  }

  /**
   * largeCategoryを設定します。
   * 
   * @param largeCategory
   *          largeCategory
   */
  public void setLargeCategory(List<String> largeCategory) {
    this.largeCategory = largeCategory;
  }

  /**
   * @return the smallCategory
   */
  public HashMap<String, List<String>> getSmallCategory() {
    return smallCategory;
  }

  /**
   * @param smallCategory
   *          the smallCategory to set
   */
  public void setSmallCategory(HashMap<String, List<String>> smallCategory) {
    CollectionUtil.copyAll(this.smallCategory, smallCategory);
  }
  
  public String categoryArray() {
    String categoryArray = "<script type=\"text/javascript\">";
    categoryArray += "var categoryArray = {";
    int largeRow = 1;
    for(Map.Entry<String, List<String>> entry : getSmallCategory().entrySet()) {
      categoryArray += "'" + entry.getKey() + "':{";
      categoryArray += "smallCategory:{";
      List<String> smallCategoryList = entry.getValue();
      int smallRow = 1;
      for (String smallCategoryName : smallCategoryList) {
        categoryArray += "'" + smallCategoryName + "':{name:'" + smallCategoryName + "'}";
        if (smallRow != smallCategoryList.size()) {
          categoryArray += ",";
        }
        smallRow++;
      }
      categoryArray += "}";
      categoryArray += "}";
      if (largeRow != getSmallCategory().size()) {
        categoryArray += ",";
      }
      largeRow++;
    }
    categoryArray += "}";
    categoryArray += "</script>";
    return categoryArray;
  }

}
