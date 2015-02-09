package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * @author System Integrator Corp.
 */
public class CategoryInfo extends Category {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private List<CategoryInfoDetail> categoryInfoDetailList = new ArrayList<CategoryInfoDetail>();

  public static class CategoryInfoDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** カテゴリ属性番号 */
    @Required
    @Digit
    @Length(38)
    private Long categoryAttributeNo;

    /** カテゴリ属性名 */
    @Required
    @Length(15)
    private String categoryAttributeName;

    /** カテゴリ属性英文名 */
    @Required
    @Length(15)
    private String categoryAttributeNameEn;

    /** カテゴリ属性日文名 */
    @Required
    @Length(15)
    private String categoryAttributeNameJp;

    /** データ行ID */
    @Required
    @Length(38)
    @Metadata(name = "データ行ID")
    private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

    /** 更新日時 */
    @Required
    @Metadata(name = "更新日時")
    private Date updatedDatetime;

    /**
     * @return the ormRowid
     */
    public Long getOrmRowid() {
      return ormRowid;
    }

    /**
     * @param ormRowid
     *          the ormRowid to set
     */
    public void setOrmRowid(Long ormRowid) {
      this.ormRowid = ormRowid;
    }

    /**
     * @return the updatedDatetime
     */
    public Date getUpdatedDatetime() {
      return DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * @param updatedDatetime
     *          the updatedDatetime to set
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * @return the categoryAttributeName
     */
    public String getCategoryAttributeName() {
      return categoryAttributeName;
    }

    /**
     * @param categoryAttributeName
     *          the categoryAttributeName to set
     */
    public void setCategoryAttributeName(String categoryAttributeName) {
      this.categoryAttributeName = categoryAttributeName;
    }

    /**
     * @return the categoryAttributeNo
     */
    public Long getCategoryAttributeNo() {
      return categoryAttributeNo;
    }

    /**
     * @param categoryAttributeNo
     *          the categoryAttributeNo to set
     */
    public void setCategoryAttributeNo(Long categoryAttributeNo) {
      this.categoryAttributeNo = categoryAttributeNo;
    }

	/**
	 * @param categoryAttributeNameEn the categoryAttributeNameEn to set
	 */
	public void setCategoryAttributeNameEn(String categoryAttributeNameEn) {
		this.categoryAttributeNameEn = categoryAttributeNameEn;
	}

	/**
	 * @return the categoryAttributeNameEn
	 */
	public String getCategoryAttributeNameEn() {
		return categoryAttributeNameEn;
	}

	/**
	 * @param categoryAttributeNameJp the categoryAttributeNameJp to set
	 */
	public void setCategoryAttributeNameJp(String categoryAttributeNameJp) {
		this.categoryAttributeNameJp = categoryAttributeNameJp;
	}

	/**
	 * @return the categoryAttributeNameJp
	 */
	public String getCategoryAttributeNameJp() {
		return categoryAttributeNameJp;
	}

  }

  /**
   * @return the categoryInfoDetailList
   */
  public List<CategoryInfoDetail> getCategoryInfoDetailList() {
    return categoryInfoDetailList;
  }

  /**
   * @param categoryInfoDetailList
   *          the categoryInfoDetailList to set
   */
  public void setCategoryInfoDetailList(List<CategoryInfoDetail> categoryInfoDetailList) {
    this.categoryInfoDetailList = categoryInfoDetailList;
  }

}
