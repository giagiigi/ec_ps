package jp.co.sint.webshop.web.bean.front.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * インフォメーションのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class InformationBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<InformationDetailBean> informationList = new ArrayList<InformationDetailBean>();
  
  //20120111 os013 add start
  //重要通知
  private List<InformationDetailBean> importantInformationList= new ArrayList<InformationDetailBean>();
  //20120111 os013 add end
  private List<InformationDetailBean> businessInformation = new ArrayList<InformationDetailBean>();

  private List<InformationDetailBean> systemInformation = new ArrayList<InformationDetailBean>();

  /**
   * インフォメーションのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class InformationDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String informationNo;

    private Date informationStartDatetime;

    private String informationContent;

    private String informationType;

    // 20120111 os013 add start
    // 关联URL
    private String informationUrl;

    public String getInformationUrl() {
      return informationUrl;
    }

    public void setInformationUrl(String informationUrl) {
      this.informationUrl = informationUrl;
    }

    // 20120111 os013 add end
    /**
     * informationNoを取得します。
     * 
     * @return informationNo
     */
    public String getInformationNo() {
      return informationNo;
    }

    /**
     * informationStartDatetimeを取得します。
     * 
     * @return informationStartDatetime
     */
    public Date getInformationStartDatetime() {
      return DateUtil.immutableCopy(informationStartDatetime);
    }

    /**
     * inormationContentを取得します。
     * 
     * @return inormationContent
     */
    public String getInformationContent() {
      return informationContent;
    }

    /**
     * informationNoを設定します。
     * 
     * @param informationNo
     *          informationNo
     */
    public void setInformationNo(String informationNo) {
      this.informationNo = informationNo;
    }

    /**
     * informationStartDatetimeを設定します。
     * 
     * @param informationStartDatetime
     *          informationStartDatetime
     */
    public void setInformationStartDatetime(Date informationStartDatetime) {
      this.informationStartDatetime = DateUtil.immutableCopy(informationStartDatetime);
    }

    /**
     * inormationContentを設定します。
     * 
     * @param informationContent
     *          informationContent
     */
    public void setInformationContent(String informationContent) {
      this.informationContent = informationContent;
    }

    /**
     * informationTypeを取得します。
     * 
     * @return informationType
     */
    public String getInformationType() {
      return informationType;
    }

    /**
     * informationTypeを設定します。
     * 
     * @param informationType
     *          informationType
     */
    public void setInformationType(String informationType) {
      this.informationType = informationType;
    }
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
  }

  /**
   * informationListを取得します。
   * 
   * @return informationList
   */
  public List<InformationDetailBean> getInformationList() {
    return informationList;
  }

  /**
   * informationListを設定します。
   * 
   * @param informationList
   *          informationList
   */
  public void setInformationList(List<InformationDetailBean> informationList) {
    this.informationList = informationList;
  }

  /**
   * businessInformationを取得します。
   * 
   * @return businessInformation
   */
  public List<InformationDetailBean> getBusinessInformation() {
    return businessInformation;
  }

  /**
   * businessInformationを設定します。
   * 
   * @param businessInformation
   *          businessInformation
   */
  public void setBusinessInformation(List<InformationDetailBean> businessInformation) {
    this.businessInformation = businessInformation;
  }

  /**
   * systemInformationを取得します。
   * 
   * @return systemInformation
   */
  public List<InformationDetailBean> getSystemInformation() {
    return systemInformation;
  }

  /**
   * systemInformationを設定します。
   * 
   * @param systemInformation
   *          systemInformation
   */
  public void setSystemInformation(List<InformationDetailBean> systemInformation) {
    this.systemInformation = systemInformation;
  }

  public List<InformationDetailBean> getImportantInformationList() {
    return importantInformationList;
  }

  public void setImportantInformationList(List<InformationDetailBean> importantInformationList) {
    this.importantInformationList = importantInformationList;
  }
}
