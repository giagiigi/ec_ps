package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1051210:お知らせ編集のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class InformationBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private InformationDetail registerInformation = new InformationDetail();

  private List<InformationDetail> listInformation = new ArrayList<InformationDetail>();

  private boolean deleteButtonFlg;

  private boolean buttonFlg;

  private boolean registerPartDisplayFlg;

  private String selectInformationDetail;

  /**
   * U1051210:お知らせ編集のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class InformationDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Metadata(name = "お知らせ番号")
    private String informationNo;

    @Required
    @Datetime(format = "yyyy/MM/dd HH:mm:ss")
    @Metadata(name = "表示期間(From)")
    private String startDatetime;

    @Required
    @Datetime(format = "yyyy/MM/dd HH:mm:ss")
    @Metadata(name = "表示期間(To)")
    private String endDatetime;

    @Required
    @Metadata(name = "表示区分")
    private String displayClientType;

    @Required
    @Metadata(name = "お知らせ区分")
    private String type;
    // soukai add 2011/12/19 ob start 
    @Required
    @Length(1)
    @Metadata(name = "重要度区分")
    private Long primaryFlg;
    
    @Length(256)
    @Url
    @Metadata(name = "关联URL")
    private String informationUrl;
    
    @Length(256)
    @Url
    @Metadata(name = "关联URL")
    private String informationUrlEn;
    
    @Length(256)
    @Url
    @Metadata(name = "关联URL")
    private String informationUrlJp;
    
    // soukai add 2011/12/19 ob end 
    @Required
    @Length(500)
    @Metadata(name = "本文")
    private String content;
    
    //20120514 tuxinwei add start
    @Required
    @Length(500)
    @Metadata(name = "本文(英文)")
    private String contentEn;
    
    @Required
    @Length(500)
    @Metadata(name = "本文(日本语)")
    private String contentJp;
    //20120514 tuxinwei add end
    
    private Date updateDatetime;

    /**
     * contentを取得します。
     * 
     * @return content
     */
    public String getContent() {
      return content;
    }

    /**
     * displayClientTypeを取得します。
     * 
     * @return displayClientType
     */
    public String getDisplayClientType() {
      return displayClientType;
    }

    /**
     * endDatetimeを取得します。
     * 
     * @return endDatetime
     */
    public String getEndDatetime() {
      return endDatetime;
    }

    /**
     * informationNoを取得します。
     * 
     * @return informationNo
     */
    public String getInformationNo() {
      return informationNo;
    }

    /**
     * startDatetimeを取得します。
     * 
     * @return startDatetime
     */
    public String getStartDatetime() {
      return startDatetime;
    }

    /**
     * typeを取得します。
     * 
     * @return type
     */
    public String getType() {
      return type;
    }

    /**
     * contentを設定します。
     * 
     * @param content
     *          content
     */
    public void setContent(String content) {
      this.content = content;
    }

    /**
     * displayClientTypeを設定します。
     * 
     * @param displayClientType
     *          displayClientType
     */
    public void setDisplayClientType(String displayClientType) {
      this.displayClientType = displayClientType;
    }

    /**
     * endDatetimeを設定します。
     * 
     * @param endDatetime
     *          endDatetime
     */
    public void setEndDatetime(String endDatetime) {
      this.endDatetime = endDatetime;
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
     * startDatetimeを設定します。
     * 
     * @param startDatetime
     *          startDatetime
     */
    public void setStartDatetime(String startDatetime) {
      this.startDatetime = startDatetime;
    }

    /**
     * typeを設定します。
     * 
     * @param type
     *          type
     */
    public void setType(String type) {
      this.type = type;
    }

    /**
     * updateDatetimeを取得します。
     * 
     * @return updateDatetime
     */
    public Date getUpdateDatetime() {
      return DateUtil.immutableCopy(updateDatetime);
    }

    /**
     * updateDatetimeを設定します。
     * 
     * @param updateDatetime
     *          updateDatetime
     */
    public void setUpdateDatetime(Date updateDatetime) {
      this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
    }

	public Long getPrimaryFlg() {
		return primaryFlg;
	}

	public void setPrimaryFlg(Long primaryFlg) {
		this.primaryFlg = primaryFlg;
	}

	public String getInformationUrl() {
		return informationUrl;
	}

	public void setInformationUrl(String informationUrl) {
		this.informationUrl = informationUrl;
	}

  
  /**
   * @return the contentEn
   */
  public String getContentEn() {
    return contentEn;
  }

  
  /**
   * @param contentEn the contentEn to set
   */
  public void setContentEn(String contentEn) {
    this.contentEn = contentEn;
  }

  
  /**
   * @return the contentJp
   */
  public String getContentJp() {
    return contentJp;
  }

  
  /**
   * @param contentJp the contentJp to set
   */
  public void setContentJp(String contentJp) {
    this.contentJp = contentJp;
  }

  
  /**
   * @return the informationUrlEn
   */
  public String getInformationUrlEn() {
    return informationUrlEn;
  }

  
  /**
   * @param informationUrlEn the informationUrlEn to set
   */
  public void setInformationUrlEn(String informationUrlEn) {
    this.informationUrlEn = informationUrlEn;
  }

  
  /**
   * @return the informationUrlJp
   */
  public String getInformationUrlJp() {
    return informationUrlJp;
  }

  
  /**
   * @param informationUrlJp the informationUrlJp to set
   */
  public void setInformationUrlJp(String informationUrlJp) {
    this.informationUrlJp = informationUrlJp;
  }

  }

  /**
   * listInformationを取得します。
   * 
   * @return listInformation
   */
  public List<InformationDetail> getListInformation() {
    return listInformation;
  }

  /**
   * registerInformationを取得します。
   * 
   * @return registerInformation
   */
  public InformationDetail getRegisterInformation() {
    return registerInformation;
  }

  /**
   * listInformationを設定します。
   * 
   * @param listInformation
   *          listInformation
   */
  public void setListInformation(List<InformationDetail> listInformation) {
    this.listInformation = listInformation;
  }

  /**
   * registerInformationを設定します。
   * 
   * @param registerInformation
   *          registerInformation
   */
  public void setRegisterInformation(InformationDetail registerInformation) {
    this.registerInformation = registerInformation;
  }

  /**
   * buttonFlgを取得します。
   * 
   * @return buttonFlg
   */
  public boolean getButtonFlg() {
    return buttonFlg;
  }

  /**
   * buttonFlgを設定します。
   * 
   * @param buttonFlg
   *          buttonFlg
   */
  public void setButtonFlg(boolean buttonFlg) {
    this.buttonFlg = buttonFlg;
  }

  /**
   * deleteButtonFlgを取得します。
   * 
   * @return deleteButtonFlg
   */
  public boolean getDeleteButtonFlg() {
    return deleteButtonFlg;
  }

  /**
   * deleteButtonFlgを設定します。
   * 
   * @param deleteButtonFlg
   *          deleteButtonFlg
   */
  public void setDeleteButtonFlg(boolean deleteButtonFlg) {
    this.deleteButtonFlg = deleteButtonFlg;
  }

  /**
   * registerPartDisplayFlgを取得します。
   * 
   * @return registerPartDisplayFlg
   */
  public boolean getRegisterPartDisplayFlg() {
    return registerPartDisplayFlg;
  }

  /**
   * registerPartDisplayFlgを設定します。
   * 
   * @param registerPartDisplayFlg
   *          registerPartDisplayFlg
   */
  public void setRegisterPartDisplayFlg(boolean registerPartDisplayFlg) {
    this.registerPartDisplayFlg = registerPartDisplayFlg;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {

  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    InformationDetail detail = new InformationDetail();
    detail.setInformationNo(reqparam.get("informationNo"));
    detail.setStartDatetime(reqparam.getDateTimeString("startDatetime"));
    detail.setEndDatetime(reqparam.getDateTimeString("endDatetime"));
    detail.setDisplayClientType(reqparam.get("displayClientType"));
    detail.setType(reqparam.get("type"));
    detail.setContent(reqparam.get("content"));
    //20120514 tuxinwei add start
    detail.setContentEn(reqparam.get("contentEn"));
    detail.setContentJp(reqparam.get("contentJp"));
    //20120514 tuxinwei add end
    detail.setUpdateDatetime(getRegisterInformation().getUpdateDatetime());
    // soukai add 2011/12/19 ob start
    if (StringUtil.isNotNull(reqparam.get("primaryFlg"))) {
    	 detail.setPrimaryFlg(NumUtil.toLong(reqparam.get("primaryFlg")));
    }
    detail.setInformationUrl(reqparam.get("informationUrl"));
    detail.setInformationUrlEn(reqparam.get("informationUrlEn"));
    detail.setInformationUrlJp(reqparam.get("informationUrlJp"));
    // soukai add 2011/12/19 ob end
    setRegisterInformation(detail);
    setSelectInformationDetail(reqparam.get("selectNo"));

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1051210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.InformationBean.0");
  }

  /**
   * selectInformationDetailを取得します。
   * 
   * @return selectInformationDetail
   */
  public String getSelectInformationDetail() {
    return selectInformationDetail;
  }

  /**
   * selectInformationDetailを設定します。
   * 
   * @param selectInformationDetail
   *          selectInformationDetail
   */
  public void setSelectInformationDetail(String selectInformationDetail) {
    this.selectInformationDetail = selectInformationDetail;
  }

}
