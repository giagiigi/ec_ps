package jp.co.sint.webshop.service.data;

import java.io.Serializable;

import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;

public class ContentsListResult implements Serializable, Comparable<ContentsListResult> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** ディレクトリパス */
  private String directoryPath;

  /** ファイル名 */
  private String fileName;

  /** ファイルサイズ */
  private String size;

  /** 更新日時 */
  private String updateDateTime;

  /** 階層レベル */
  private Long depthLevel;

  /** ディレクトリ有無 true:ディレクトリ false:ファイル */
  private boolean directory;

  /**
   * depthLevelを取得します。
   * 
   * @return depthLevel
   */
  public Long getDepthLevel() {
    return depthLevel;
  }

  /**
   * depthLevelを設定します。
   * 
   * @param depthLevel
   *          depthLevel
   */
  public void setDepthLevel(Long depthLevel) {
    this.depthLevel = depthLevel;
  }

  /**
   * directoryPathを取得します。
   * 
   * @return directoryPath
   */
  public String getDirectoryPath() {
    return directoryPath;
  }

  /**
   * directoryPathを設定します。
   * 
   * @param directoryPath
   *          directoryPath
   */
  public void setDirectoryPath(String directoryPath) {
    this.directoryPath = directoryPath;
  }

  /**
   * fileNameを取得します。
   * 
   * @return fileName
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * fileNameを設定します。
   * 
   * @param fileName
   *          fileName
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * sizeを取得します。
   * 
   * @return size
   */
  public String getSize() {
    return size;
  }

  /**
   * sizeを設定します。
   * 
   * @param size
   *          size
   */
  public void setSize(String size) {
    this.size = size;
  }

  /**
   * updateDateTimeを取得します。
   * 
   * @return updateDateTime
   */
  public String getUpdateDateTime() {
    return updateDateTime;
  }

  /**
   * updateDateTimeを設定します。
   * 
   * @param updateDateTime
   *          updateDateTime
   */
  public void setUpdateDateTime(String updateDateTime) {
    this.updateDateTime = updateDateTime;
  }

  /**
   * directoryを取得します。
   * 
   * @return directory
   */
  public boolean isDirectory() {
    return directory;
  }

  /**
   * directoryを設定します。
   * 
   * @param directory
   *          directory
   */
  public void setDirectory(boolean directory) {
    this.directory = directory;
  }

  public int compareTo(ContentsListResult arg0) {
    int result = 0;
    try {
      if (this.equals(arg0)) {
        return 0;
      }
      result = this.getDirectoryPath().compareTo(arg0.getDirectoryPath());
      if (result == 0) {
        result = this.getFileName().compareTo(arg0.getFileName());
      }
    } catch (RuntimeException e) {
      result = 0;
    }
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ContentsListResult)) {
      return false;
    }
    return equals((ContentsListResult) obj);
  }

  public boolean equals(ContentsListResult obj) {
    boolean result = true;
    result &= ValidatorUtil.areEqualOrNull(this.getDepthLevel(), obj.getDepthLevel());
    result &= ValidatorUtil.areEqualOrNull(this.getDirectoryPath(), obj.getDirectoryPath());
    result &= ValidatorUtil.areEqualOrNull(this.getFileName(), obj.getFileName());
    result &= ValidatorUtil.areEqualOrNull(this.getSize(), obj.getSize());
    result &= ValidatorUtil.areEqualOrNull(this.getUpdateDateTime(), obj.getUpdateDateTime());
    return result;
  }

  @Override
  public int hashCode() {
    return BeanUtil.generateInstantHashCode(
        this.getDepthLevel(), this.getDirectoryPath(),
        this.getFileName(), this.getSize(), this.getUpdateDateTime());
  }
}
