package jp.co.sint.webshop.service.data;

import java.io.File;

public class RuleContents implements ContentsInfo {
  
  private File contentsFile;

  public RuleContents() {
  }

  public ContentsType getContentsType() {
    return ContentsType.CONTENT_SITE_RULE;
  }

  
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
   * @param contentsFile 設定する contentsFile
   */
  public void setContentsFile(File contentsFile) {
    this.contentsFile = contentsFile;
  }

}
