package jp.co.sint.webshop.service.data;

import java.io.File;

/**
 * @author System Integrator Corp.
 */
public class Contents {

  private File contentsFile;

  private ContentsSearchCondition contentsCondition;

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
   *          contentsFile
   */
  public void setContentsFile(File contentsFile) {
    this.contentsFile = contentsFile;
  }

  /**
   * contentsConditionを取得します。
   * 
   * @return contentsCondition
   */
  public ContentsSearchCondition getContentsCondition() {
    return contentsCondition;
  }

  /**
   * contentsConditionを設定します。
   * 
   * @param contentsCondition
   *          contentsCondition
   */
  public void setContentsCondition(ContentsSearchCondition contentsCondition) {
    this.contentsCondition = contentsCondition;
  }

}
