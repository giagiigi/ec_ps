package jp.co.sint.webshop.web.message.back.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * アップロードを行った処理結果
 * 
 * @author System Integrator Corp.
 */
public class UploadResult implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 成功下のかどうかを表す値 */
  private UploadResultType result;

  /** 「情報」メッセージ */
  private List<String> informationMessage = new ArrayList<String>();

  /** 「警告」メッセージ */
  private List<String> warnMessage = new ArrayList<String>();

  /** 「エラー」メッセージ */
  private List<String> errorMessage = new ArrayList<String>();

  /**
   * serialVersionUIDを取得します。
   * 
   * @return serialVersionUID
   */
  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  /**
   * errorMessageを取得します。
   * 
   * @return errorMessage
   */
  public List<String> getErrorMessage() {
    return errorMessage;
  }

  /**
   * informationMessageを取得します。
   * 
   * @return informationMessage
   */
  public List<String> getInformationMessage() {
    return informationMessage;
  }

  /**
   * resultを取得します。
   * 
   * @return result
   */
  public UploadResultType getResult() {
    return result;
  }

  /**
   * warnMessageを取得します。
   * 
   * @return warnMessage
   */
  public List<String> getWarnMessage() {
    return warnMessage;
  }

  /**
   * errorMessageを設定します。
   * 
   * @param errorMessage
   *          errorMessage
   */
  public void setErrorMessage(List<String> errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * informationMessageを設定します。
   * 
   * @param informationMessage
   *          informationMessage
   */
  public void setInformationMessage(List<String> informationMessage) {
    this.informationMessage = informationMessage;
  }

  /**
   * resultを設定します。
   * 
   * @param result
   *          result
   */
  public void setResult(UploadResultType result) {
    this.result = result;
  }

  /**
   * warnMessageを設定します。
   * 
   * @param warnMessage
   *          warnMessage
   */
  public void setWarnMessage(List<String> warnMessage) {
    this.warnMessage = warnMessage;
  }

}
