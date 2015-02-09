package jp.co.sint.webshop.ext.cup;

/**
 * @author System Integrator Corp.
 */
public class CupRequest extends CupData {

  /**  */
  private static final long serialVersionUID = 1L;

  private String version;

  /**
   * versionを返します。
   * 
   * @return version
   */
  public String getVersion() {
    return version;
  }

  /**
   * versionを設定します。
   * 
   * @param version
   *          version
   */
  public void setVersion(String version) {
    this.version = version;
  }

}
