package jp.co.sint.webshop.utility;

import java.io.Serializable;

/**
 * �Í����ݒ�p�̃N���X�ł��B
 * 
 * @author System Integrator Corp.
 * (10.2.0 X20038 �V�K�쐬)
 */
public class CipherConfig implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String algorithm;

  private String secretKey;

  public CipherConfig() {
  }

  public CipherConfig(String algorithm, String secretKey) {
    this.algorithm = algorithm;
    this.secretKey = secretKey;
  }

  public String getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(String algorythm) {
    this.algorithm = algorythm;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

}
