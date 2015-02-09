package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060110:アンケート管理のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class EnqueteListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @AlphaNum2
  @Length(16)
  @Metadata(name = "アンケートコード(From)", order = 1)
  private String searchEnqueteCodeFrom;

  @AlphaNum2
  @Length(16)
  @Metadata(name = "アンケートコード(To)", order = 2)
  private String searchEnqueteCodeTo;

  @Length(40)
  @Metadata(name = "アンケート名", order = 3)
  private String searchEnqueteName;

  @Datetime
  @Metadata(name = "開始日(From)", order = 4)
  private String searchEnqueteStartDateFrom;

  @Datetime
  @Metadata(name = "開始日(To)", order = 5)
  private String searchEnqueteStartDateTo;

  @Datetime
  @Metadata(name = "終了日(From)", order = 6)
  private String searchEnqueteEndDateFrom;

  @Datetime
  @Metadata(name = "終了日(To)", order = 7)
  private String searchEnqueteEndDateTo;

  private boolean editButtonDisplayFlg;

  private boolean deleteButtonDisplayFlg;

  private PagerValue pagerValue;

  private List<EnqueteListBeanDetail> list = new ArrayList<EnqueteListBeanDetail>();

  /**
   * U1060110:アンケート管理のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class EnqueteListBeanDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String enqueteCode;

    private String enqueteName;

    private String enqueteNameEn;

    private String enqueteNameJp;

    private String enqueteStartDate;

    private String enqueteEndDate;

    private String enqueteAnswerCount;

    private String enqueteStatus;

    /**
     * enqueteAnswerCountを取得します。
     * 
     * @return enqueteAnswerCount
     */
    public String getEnqueteAnswerCount() {
      return enqueteAnswerCount;
    }

    /**
     * enqueteCodeを取得します。
     * 
     * @return enqueteCode
     */
    public String getEnqueteCode() {
      return enqueteCode;
    }

    /**
     * enqueteEndDateを取得します。
     * 
     * @return enqueteEndDate
     */
    public String getEnqueteEndDate() {
      return enqueteEndDate;
    }

    /**
     * enqueteNameを取得します。
     * 
     * @return enqueteName
     */
    public String getEnqueteName() {
      return enqueteName;
    }

    /**
     * enqueteStartDateを取得します。
     * 
     * @return enqueteStartDate
     */
    public String getEnqueteStartDate() {
      return enqueteStartDate;
    }

    /**
     * enqueteStatusを取得します。
     * 
     * @return enqueteStatus
     */
    public String getEnqueteStatus() {
      return enqueteStatus;
    }

    /**
     * enqueteAnswerCountを設定します。
     * 
     * @param enqueteAnswerCount
     *          enqueteAnswerCount
     */
    public void setEnqueteAnswerCount(String enqueteAnswerCount) {
      this.enqueteAnswerCount = enqueteAnswerCount;
    }

    /**
     * enqueteCodeを設定します。
     * 
     * @param enqueteCode
     *          enqueteCode
     */
    public void setEnqueteCode(String enqueteCode) {
      this.enqueteCode = enqueteCode;
    }

    /**
     * enqueteEndDateを設定します。
     * 
     * @param enqueteEndDate
     *          enqueteEndDate
     */
    public void setEnqueteEndDate(String enqueteEndDate) {
      this.enqueteEndDate = enqueteEndDate;
    }

    /**
     * enqueteNameを設定します。
     * 
     * @param enqueteName
     *          enqueteName
     */
    public void setEnqueteName(String enqueteName) {
      this.enqueteName = enqueteName;
    }

    /**
     * enqueteStartDateを設定します。
     * 
     * @param enqueteStartDate
     *          enqueteStartDate
     */
    public void setEnqueteStartDate(String enqueteStartDate) {
      this.enqueteStartDate = enqueteStartDate;
    }

    /**
     * enqueteStatusを設定します。
     * 
     * @param enqueteStatus
     *          enqueteStatus
     */
    public void setEnqueteStatus(String enqueteStatus) {
      this.enqueteStatus = enqueteStatus;
    }

	/**
	 * @param enqueteNameEn the enqueteNameEn to set
	 */
	public void setEnqueteNameEn(String enqueteNameEn) {
		this.enqueteNameEn = enqueteNameEn;
	}

	/**
	 * @return the enqueteNameEn
	 */
	public String getEnqueteNameEn() {
		return enqueteNameEn;
	}

	/**
	 * @param enqueteNameJp the enqueteNameJp to set
	 */
	public void setEnqueteNameJp(String enqueteNameJp) {
		this.enqueteNameJp = enqueteNameJp;
	}

	/**
	 * @return the enqueteNameJp
	 */
	public String getEnqueteNameJp() {
		return enqueteNameJp;
	}

  }

  /**
   * deleteButtonDisplayFlgを取得します。
   * 
   * @return deleteButtonDisplayFlg
   */
  public boolean isDeleteButtonDisplayFlg() {
    return deleteButtonDisplayFlg;
  }

  /**
   * deleteButtonDisplayFlgを設定します。
   * 
   * @param deleteButtonDisplayFlg
   */
  public void setDeleteButtonDisplayFlg(boolean deleteButtonDisplayFlg) {
    this.deleteButtonDisplayFlg = deleteButtonDisplayFlg;
  }

  /**
   * editButtonDisplayFlgを取得します。
   * 
   * @return editButtonDisplayFlg
   */
  public boolean isEditButtonDisplayFlg() {
    return editButtonDisplayFlg;
  }

  /**
   * editButtonDisplayFlgを設定します。
   * 
   * @param editButtonDisplayFlg
   */
  public void setEditButtonDisplayFlg(boolean editButtonDisplayFlg) {
    this.editButtonDisplayFlg = editButtonDisplayFlg;
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<EnqueteListBeanDetail> getList() {
    return list;
  }

  /**
   * searchEnqueteCodeFromを取得します。
   * 
   * @return searchEnqueteCodeFrom
   */
  public String getSearchEnqueteCodeFrom() {
    return searchEnqueteCodeFrom;
  }

  /**
   * searchEnqueteCodeToを取得します。
   * 
   * @return searchEnqueteCodeTo
   */
  public String getSearchEnqueteCodeTo() {
    return searchEnqueteCodeTo;
  }

  /**
   * searchEnqueteEndDateFromを取得します。
   * 
   * @return searchEnqueteEndDateFrom
   */
  public String getSearchEnqueteEndDateFrom() {
    return searchEnqueteEndDateFrom;
  }

  /**
   * searchEnqueteNameを取得します。
   * 
   * @return searchEnqueteName
   */
  public String getSearchEnqueteName() {
    return searchEnqueteName;
  }

  /**
   * searchEnqueteStartDateFromを取得します。
   * 
   * @return searchEnqueteStartDateFrom
   */
  public String getSearchEnqueteStartDateFrom() {
    return searchEnqueteStartDateFrom;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<EnqueteListBeanDetail> list) {
    this.list = list;
  }

  /**
   * searchEnqueteCodeFromを設定します。
   * 
   * @param searchEnqueteCodeFrom
   *          searchEnqueteCodeFrom
   */
  public void setSearchEnqueteCodeFrom(String searchEnqueteCodeFrom) {
    this.searchEnqueteCodeFrom = searchEnqueteCodeFrom;
  }

  /**
   * searchEnqueteCodeToを設定します。
   * 
   * @param searchEnqueteCodeTo
   *          searchEnqueteCodeTo
   */
  public void setSearchEnqueteCodeTo(String searchEnqueteCodeTo) {
    this.searchEnqueteCodeTo = searchEnqueteCodeTo;
  }

  /**
   * searchEnqueteEndDateFromを設定します。
   * 
   * @param searchEnqueteEndDateFrom
   *          searchEnqueteEndDateFrom
   */
  public void setSearchEnqueteEndDateFrom(String searchEnqueteEndDateFrom) {
    this.searchEnqueteEndDateFrom = searchEnqueteEndDateFrom;
  }

  /**
   * searchEnqueteNameを設定します。
   * 
   * @param searchEnqueteName
   *          searchEnqueteName
   */
  public void setSearchEnqueteName(String searchEnqueteName) {
    this.searchEnqueteName = searchEnqueteName;
  }

  /**
   * searchEnqueteStartDateFromを設定します。
   * 
   * @param searchEnqueteStartDateFrom
   *          searchEnqueteStartDateFrom
   */
  public void setSearchEnqueteStartDateFrom(String searchEnqueteStartDateFrom) {
    this.searchEnqueteStartDateFrom = searchEnqueteStartDateFrom;
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
    this.setSearchEnqueteCodeFrom(reqparam.get("searchEnqueteCodeFrom"));
    this.setSearchEnqueteCodeTo(reqparam.get("searchEnqueteCodeTo"));
    this.setSearchEnqueteName(reqparam.get("searchEnqueteName"));
    this.setSearchEnqueteStartDateFrom(reqparam.getDateString("searchEnqueteStartDateFrom"));
    this.setSearchEnqueteStartDateTo(reqparam.getDateString("searchEnqueteStartDateTo"));
    this.setSearchEnqueteEndDateFrom(reqparam.getDateString("searchEnqueteEndDateFrom"));
    this.setSearchEnqueteEndDateTo(reqparam.getDateString("searchEnqueteEndDateTo"));

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1060110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.EnqueteListBean.0");
  }

  /**
   * searchEnqueteStartDateToを取得します。
   * 
   * @return searchEnqueteStartDateTo
   */
  public String getSearchEnqueteStartDateTo() {
    return searchEnqueteStartDateTo;
  }

  /**
   * searchEnqueteStartDateToを設定します。
   * 
   * @param searchEnqueteStartDateTo
   *          searchEnqueteStartDateTo
   */
  public void setSearchEnqueteStartDateTo(String searchEnqueteStartDateTo) {
    this.searchEnqueteStartDateTo = searchEnqueteStartDateTo;
  }

  /**
   * searchEnqueteEndDateToを取得します。
   * 
   * @return searchEnqueteEndDateTo
   */
  public String getSearchEnqueteEndDateTo() {
    return searchEnqueteEndDateTo;
  }

  /**
   * searchEnqueteEndDateToを設定します。
   * 
   * @param searchEnqueteEndDateTo
   *          searchEnqueteStartDateTo
   */
  public void setSearchEnqueteEndDateTo(String searchEnqueteEndDateTo) {
    this.searchEnqueteEndDateTo = searchEnqueteEndDateTo;
  }

  /**
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * @param pagerValue
   *          設定する pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;

  }

}
