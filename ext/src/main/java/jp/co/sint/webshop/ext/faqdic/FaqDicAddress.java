package jp.co.sint.webshop.ext.faqdic;

import java.io.Serializable;

import jp.co.sint.webshop.utility.PostalAddress;

/**
 * 「FAQ里恵の郵便番号辞書」による郵便番号検索アドレス
 * 
 * @author System Integrator Corp.
 */
public class FaqDicAddress implements PostalAddress, Serializable {

  private static final long serialVersionUID = 1L;

  private String postalCode;

  private String prefectureCode;

  private String prefecture;

  private String city;

  private String address;

  public String getFullAddress() {
    return getPrefecture() + getCity() + getAddress();
  }

  /**
   * postalCodeを返します。
   * 
   * @return the postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * postalCodeを設定します。
   * 
   * @param postalCode
   *          設定する postalCode
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * prefectureCodeを返します。
   * 
   * @return the prefectureCode
   */
  public String getPrefectureCode() {
    return prefectureCode;
  }

  /**
   * prefectureCodeを設定します。
   * 
   * @param prefectureCode
   *          設定する prefectureCode
   */
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  /**
   * prefectureを返します。
   * 
   * @return the prefecture
   */
  public String getPrefecture() {
    return prefecture;
  }

  /**
   * prefectureを設定します。
   * 
   * @param prefecture
   *          設定する prefecture
   */
  public void setPrefecture(String prefecture) {
    this.prefecture = prefecture;
  }

  /**
   * cityを返します。
   * 
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * cityを設定します。
   * 
   * @param city
   *          設定する city
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * addressを返します。
   * 
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * addressを設定します。
   * 
   * @param address
   *          設定する address
   */
  public void setAddress(String address) {
    this.address = address;
  }

}
