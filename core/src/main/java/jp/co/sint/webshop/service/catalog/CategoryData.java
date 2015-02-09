package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Pattern;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * @author System Integrator Corp.
 */
public class CategoryData implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** カテゴリコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "カテゴリコード", order = 1)
  private String categoryCode;

  /** PC用カテゴリ名称 */
  @Required
  @Length(20)
  @Metadata(name = "PC用カテゴリ名称", order = 2)
  private String categoryNamePc;

  /** 携帯用カテゴリ名称 */
  @Required
  @Length(10)
  @Metadata(name = "携帯用カテゴリ名称", order = 3)
  private String categoryNameMobile;

  /** 親カテゴリコード */
  @Required
  @Length(16)
  @Pattern(value = "[-A-Za-z0-9_\\.]*|/", message = "半角英数字または「/」(ルートカテゴリ)を指定してください")
  @Metadata(name = "親カテゴリコード", order = 4)
  private String parentCategoryCode;

  /** パス */
  @Required
  @Length(256)
  @Metadata(name = "パス", order = 5)
  private String path;

  /** 階層 */
  @Required
  @Length(2)
  @Metadata(name = "階層", order = 6)
  private Long depth;

  /** 表示順 */
  @Length(8)
  @Metadata(name = "表示順", order = 7)
  private Long displayOrder;

  /** PC商品件数 */
  @Length(12)
  @Metadata(name = "PC商品件数", order = 8)
  private Long commodityCountPc;

  /** 携帯商品件数 */
  @Length(12)
  @Metadata(name = "携帯商品件数", order = 9)
  private Long commodityCountMobile;
  
  //20120521 tuxinwei add start 
  /** PC用カテゴリ名称(日文)*/
  @Required
  @Length(20)
  @Metadata(name = "PC用カテゴリ名称(日文)", order = 19)
  private String categoryNamePcJp;
  
  /** PC用カテゴリ名称(英文)*/
  @Required
  @Length(20)
  @Metadata(name = "PC用カテゴリ名称(英文)", order = 20)
  private String categoryNamePcEn;
  //20120521 tuxinwei add end 

  /** アクティブフラグ */
  private boolean active;
  
  private Long commodityCount = 0L;
  
  private Long categoryCount = 0L;
  
  private String categoryStepOneCode;
  
  private String brandCode;

  private String reviewScore;

  private String price;
  
  private String categoryAttribute1;

  private String alignmentSequence;

  private String pageSize;

  private String mode;

  /**
   * カテゴリコードを取得します
   * 
   * @return カテゴリコード
   */
  public String getCategoryCode() {
    return this.categoryCode;
  }

  /**
   * PC用カテゴリ名称を取得します
   * 
   * @return PC用カテゴリ名称
   */
  public String getCategoryNamePc() {
    return this.categoryNamePc;
  }

  /**
   * 携帯用カテゴリ名称を取得します
   * 
   * @return 携帯用カテゴリ名称
   */
  public String getCategoryNameMobile() {
    return this.categoryNameMobile;
  }

  /**
   * 親カテゴリコードを取得します
   * 
   * @return 親カテゴリコード
   */
  public String getParentCategoryCode() {
    return this.parentCategoryCode;
  }

  /**
   * パスを取得します
   * 
   * @return パス
   */
  public String getPath() {
    return this.path;
  }

  /**
   * 階層を取得します
   * 
   * @return 階層
   */
  public Long getDepth() {
    return this.depth;
  }

  /**
   * 表示順を取得します
   * 
   * @return 表示順
   */
  public Long getDisplayOrder() {
    return this.displayOrder;
  }

  /**
   * PC商品件数を取得します
   * 
   * @return PC商品件数
   */
  public Long getCommodityCountPc() {
    return this.commodityCountPc;
  }

  /**
   * 携帯商品件数を取得します
   * 
   * @return 携帯商品件数
   */
  public Long getCommodityCountMobile() {
    return this.commodityCountMobile;
  }

  /**
   * カテゴリがアクティブならtrue、そうでないならfalseを返します
   * 
   * @return アクティブフラグ
   */
  public boolean isActive() {
    return this.active;
  }

  /**
   * カテゴリコードを設定します
   * 
   * @param categoryCode
   *          カテゴリコード
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * PC用カテゴリ名称を設定します
   * 
   * @param categoryNamePc
   *          PC用カテゴリ名称
   */
  public void setCategoryNamePc(String categoryNamePc) {
    this.categoryNamePc = categoryNamePc;
  }

  /**
   * 携帯用カテゴリ名称を設定します
   * 
   * @param categoryNameMobile
   *          携帯用カテゴリ名称
   */
  public void setCategoryNameMobile(String categoryNameMobile) {
    this.categoryNameMobile = categoryNameMobile;
  }

  /**
   * 親カテゴリコードを設定します
   * 
   * @param parentCategoryCode
   *          親カテゴリコード
   */
  public void setParentCategoryCode(String parentCategoryCode) {
    this.parentCategoryCode = parentCategoryCode;
  }

  /**
   * パスを設定します
   * 
   * @param path
   *          パス
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * 階層を設定します
   * 
   * @param depth
   *          階層
   */
  public void setDepth(Long depth) {
    this.depth = depth;
  }

  /**
   * 表示順を設定します
   * 
   * @param displayOrder
   *          表示順
   */
  public void setDisplayOrder(Long displayOrder) {
    this.displayOrder = displayOrder;
  }

  /**
   * PC商品件数を設定します
   * 
   * @param commodityCountPc
   *          PC商品件数
   */
  public void setCommodityCountPc(Long commodityCountPc) {
    this.commodityCountPc = commodityCountPc;
  }

  /**
   * 携帯商品件数を設定します
   * 
   * @param commodityCountMobile
   *          携帯商品件数
   */
  public void setCommodityCountMobile(Long commodityCountMobile) {
    this.commodityCountMobile = commodityCountMobile;
  }

  /**
   * アクティブフラグを設定します
   * 
   * @param active
   *          カテゴリがアクティブならtrue、そうでないならfalse
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  
  /**
   * @return the commodityCount
   */
  public Long getCommodityCount() {
    return commodityCount;
  }

  
  /**
   * @param commodityCount the commodityCount to set
   */
  public void setCommodityCount(Long commodityCount) {
    this.commodityCount = commodityCount;
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
   * @return the price
   */
  public String getPrice() {
    return price;
  }

  
  /**
   * @param price the price to set
   */
  public void setPrice(String price) {
    this.price = price;
  }

  
  /**
   * @return the categoryAttribute1
   */
  public String getCategoryAttribute1() {
    return categoryAttribute1;
  }

  
  /**
   * @param categoryAttribute1 the categoryAttribute1 to set
   */
  public void setCategoryAttribute1(String categoryAttribute1) {
    this.categoryAttribute1 = categoryAttribute1;
  }

  public String getUrlStr() {
    String url = "";
    if (StringUtil.hasValue(brandCode)) {
      url += "B" + brandCode + "/";
    }
    if (StringUtil.hasValue(reviewScore)) {
      url += "K" + reviewScore + "/";
    }
    if (StringUtil.hasValue(price)) {
      url += "D" + price + "/";
    }
    if (StringUtil.hasValue(categoryAttribute1)) {
      url += "T" + categoryAttribute1 + "/";
    }
    if (StringUtil.hasValue(alignmentSequence)) {
      url += "A" + alignmentSequence + "/";
    }
    if (StringUtil.hasValue(mode)) {
      url += "M" + mode + "/";
    }
    if (StringUtil.hasValue(pageSize)) {
      url += "S" + pageSize + "/";
    }
    return url;
  }

  
  /**
   * @return the alignmentSequence
   */
  public String getAlignmentSequence() {
    return alignmentSequence;
  }

  
  /**
   * @param alignmentSequence the alignmentSequence to set
   */
  public void setAlignmentSequence(String alignmentSequence) {
    this.alignmentSequence = alignmentSequence;
  }

  
  /**
   * @return the pageSize
   */
  public String getPageSize() {
    return pageSize;
  }

  
  /**
   * @param pageSize the pageSize to set
   */
  public void setPageSize(String pageSize) {
    this.pageSize = pageSize;
  }

  
  /**
   * @return the mode
   */
  public String getMode() {
    return mode;
  }

  
  /**
   * @param mode the mode to set
   */
  public void setMode(String mode) {
    this.mode = mode;
  }

  
  /**
   * @return the categoryNamePcJp
   */
  public String getCategoryNamePcJp() {
    return categoryNamePcJp;
  }

  
  /**
   * @param categoryNamePcJp the categoryNamePcJp to set
   */
  public void setCategoryNamePcJp(String categoryNamePcJp) {
    this.categoryNamePcJp = categoryNamePcJp;
  }

  
  /**
   * @return the categoryNamePcEn
   */
  public String getCategoryNamePcEn() {
    return categoryNamePcEn;
  }

  
  /**
   * @param categoryNamePcEn the categoryNamePcEn to set
   */
  public void setCategoryNamePcEn(String categoryNamePcEn) {
    this.categoryNamePcEn = categoryNamePcEn;
  }

  
  /**
   * @return the categoryCount
   */
  public Long getCategoryCount() {
    return categoryCount;
  }

  
  /**
   * @param categoryCount the categoryCount to set
   */
  public void setCategoryCount(Long categoryCount) {
    this.categoryCount = categoryCount;
  }

  
  /**
   * @return the categoryStepOneCode
   */
  public String getCategoryStepOneCode() {
    return categoryStepOneCode;
  }

  
  /**
   * @param categoryStepOneCode the categoryStepOneCode to set
   */
  public void setCategoryStepOneCode(String categoryStepOneCode) {
    this.categoryStepOneCode = categoryStepOneCode;
  }
  
}
