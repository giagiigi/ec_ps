package jp.co.sint.webshop.ext.cup;

import java.io.File;
import java.io.Serializable;

/**
 * @author System Integrator Corp.
 */
public class CupMerchant implements Serializable {

  /**  */
  private static final long serialVersionUID = 1L;

  private String merchantId;

  private File privateKeyFile;

  public CupMerchant() {
  }

  public CupMerchant(String merchantId, File privateKeyFile) {
    setMerchantId(merchantId);
    setPrivateKeyFile(privateKeyFile);
  }

  /**
   * merchantIdを返します。
   * 
   * @return merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

  /**
   * merchantIdを設定します。
   * 
   * @param merchantId
   *          merchantId
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * privateKeyFileを返します。
   * 
   * @return privateKeyFile
   */
  public File getPrivateKeyFile() {
    return privateKeyFile;
  }

  /**
   * privateKeyFileを設定します。
   * 
   * @param privateKeyFile
   *          privateKeyFile
   */
  public void setPrivateKeyFile(File privateKeyFile) {
    this.privateKeyFile = privateKeyFile;
  }

  
}
