package jp.co.sint.webshop.sms;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.utility.DateUtil;

/**
 * メール情報へのアクセス手段を提供
 * 
 * @author System Integrator Corp.
 */
public class SmsInfo implements Serializable {

  /** Serial version UID */
  private static final long serialVersionUID = 1L;

  private String mobileNumber; //

  private List<AddressInfo> toInfo; // 宛先情報のリスト

  private List<AddressInfo> ccInfo; // CC情報のリスト

  private List<AddressInfo> bccInfo; // BCC情報のリスト

  private Date sendDate; // 送信日付

  private String text; // 本文

  private List<File> fileList; // 添付ファイルのリスト


  public SmsInfo() {
    // 変数の初期化
    this.sendDate = DateUtil.getSysdate();
    this.text = "";
    this.fileList = new ArrayList<File>();
    this.toInfo = new ArrayList<AddressInfo>();
    this.ccInfo = new ArrayList<AddressInfo>();
    this.bccInfo = new ArrayList<AddressInfo>();
  }

  /**
   * @return fileList
   */
  public List<File> getFileList() {
    return fileList;
  }

  /**
   * @param fileData
   *          設定する fileData
   */
  public void addFileList(File fileData) {
    this.fileList.add(fileData);
  }

  /**
   * @param index
   *          ファイルを削除する index
   */
  public void removeFileList(int index) {
    this.fileList.remove(index);
  }

  /**
   * @return toInfo
   */
  public List<AddressInfo> getToList() {
    return toInfo;
  }

  /**
   * @param address
   *          メールアドレス(address)のみを設定する。
   */
  public void addToList(String address) {
    this.addToList(address, "");
  }

  /**
   * メールアドレス(address)と名前(name)を設定する。
   * 
   * @param address
   * @param name
   */
  public void addToList(String address, String name) {
    AddressInfo tmpToInfo = new AddressInfo();
    tmpToInfo.setAddress(address);
    tmpToInfo.setName(name);
    this.toInfo.add(tmpToInfo);
  }

  /**
   * @return ccInfo
   */
  public List<AddressInfo> getCcList() {
    return ccInfo;
  }

  /**
   * @param address
   *          メールアドレス(address)のみを設定する。
   */
  public void addCcList(String address) {
    this.addCcList(address, "");
  }

  /**
   * @param address
   *          メールアドレス
   * @param name
   *          名前
   */
  public void addCcList(String address, String name) {
    AddressInfo tmpCcInfo = new AddressInfo();
    tmpCcInfo.setAddress(address);
    tmpCcInfo.setName(name);
    this.ccInfo.add(tmpCcInfo);
  }

  /**
   * @return bccInfo
   */
  public List<AddressInfo> getBccList() {
    return bccInfo;
  }

  /**
   * @param address
   *          メールアドレス
   */
  public void addBccList(String address) {
    this.addBccList(address, "");
  }

  /**
   * @param address
   *          メールアドレス
   * @param name
   *          名前
   */
  public void addBccList(String address, String name) {
    AddressInfo tmpBccInfo = new AddressInfo();
    tmpBccInfo.setAddress(address);
    tmpBccInfo.setName(name);
    this.bccInfo.add(tmpBccInfo);
  }

  /**
   * @return sendDate
   */
  public Date getSendDate() {
    return DateUtil.immutableCopy(sendDate);
  }

  /**
   * @param sendDate
   *          設定する sendDate
   */
  public void setSendDate(Date sendDate) {
    this.sendDate = DateUtil.immutableCopy(sendDate);
  }

  /**
   * @return text
   */
  public String getText() {
    return text;
  }

  /**
   * @param text
   *          設定する text
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * @param fileList
   *          設定する fileList
   */
  public void setFileList(List<File> fileList) {
    this.fileList = fileList;
  }

  /**
   * @author System Integrator Corp.
   */
  public static class AddressInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String address;

    private String name;

    public AddressInfo() {
    }

    public AddressInfo(String address, String name) {
      setAddress(address);
      setName(name);
    }

    /**
     * @return address
     */
    public String getAddress() {
      return address;
    }

    /**
     * @param address
     *          設定する address
     */
    public void setAddress(String address) {
      this.address = address;
    }

    /**
     * @return name
     */
    public String getName() {
      return name;
    }

    /**
     * @param name
     *          設定する name
     */
    public void setName(String name) {
      this.name = name;
    }
  }

  /**
   * mobileNumberを取得します。
   *
   * @return mobileNumber mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  
  /**
   * mobileNumberを設定します。
   *
   * @param mobileNumber 
   *          mobileNumber
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

}
