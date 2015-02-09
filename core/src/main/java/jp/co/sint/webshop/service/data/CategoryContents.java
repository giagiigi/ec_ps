package jp.co.sint.webshop.service.data;

import java.io.File;

public class CategoryContents implements ContentsInfo {

  private String categoryCode;

  private File contentsFile;

  /**
   * contentsFileを取得します。
   * 
   * @return contentsFile
   */
  public File getContentsFile() {
    return contentsFile;
  }

  /**
   * contentsFileを設定します。
   * 
   * @param contentsFile
   *          設定する contentsFile
   */
  public void setContentsFile(File contentsFile) {
    this.contentsFile = contentsFile;
  }

  public CategoryContents() {
  }

  public CategoryContents(String categoryCode) {
    setCategoryCode(categoryCode);
  }

  public ContentsType getContentsType() {
    return ContentsType.CONTENT_SITE_CATEGORY;
  }

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

}
