//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 商品同期化返回结果
 * 
 * @author System Integrator Corp.
 */
public class CyncroResult implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  private Long seccessCount = 0L;

  private Long totalCount = 0L;

  private Long failCount = 0L;

  private Long startTime = 0L;

  private Long endTime = 0L;

  private String highestError = "";
  
  /**
   * @return the highestError
   */
  public String getHighestError() {
    return highestError;
  }

  /**
   * @param highestError
   *          the highestError to set
   */
  public void setHighestError(String highestError) {
    this.highestError = highestError;
  }

  public boolean hasHightestError() {
    if (this.hasError()) {
      for (CyncroError error : this.errorList) {
        if (error.getRank().equals(CyncroError.ERROR_RANK_HIGHEST)) {
          this.highestError = error.getErrorInfo();
          return true;
        }
      }
    }
    return false;
  }

  /**
   * @return the startTime
   */
  public Long getStartTime() {
    return startTime;
  }

  /**
   * @param startTime
   *          the startTime to set
   */
  public void setStartTime(Long startTime) {
    this.startTime = startTime;
  }

  /**
   * @return the endTime
   */
  public Long getEndTime() {
    return endTime;
  }

  /**
   * @param endTime
   *          the endTime to set
   */
  public void setEndTime(Long endTime) {
    this.endTime = endTime;
  }

  private List<CyncroError> errorList;

  public static class CyncroError {

    /**
     * 错误级别 考虑到同期条数比较多，一般性的错误条数可能会比较多， 所以最高级别的错误才显示到页面，一般性的错误不会显示到页面只保存到日志
     */
    public static final String ERROR_RANK_HIGHEST = "1";

    public static final String ERROR_RANK_GENERAL = "2";

    /**
     * 错误级别
     */
    private String rank = "";

    /**
     * 错误信息
     */
    private String errorInfo = "";

    public CyncroError() {
    }

    public CyncroError(String rank, String info) {
      this.rank = rank;
      this.errorInfo = info;
    }

    /**
     * @return the rank
     */
    public String getRank() {
      return rank;
    }

    /**
     * @param rank
     *          the rank to set
     */
    public void setRank(String rank) {
      this.rank = rank;
    }

    /**
     * @return the errorInfo
     */
    public String getErrorInfo() {
      return errorInfo;
    }

    /**
     * @param errorInfo
     *          the errorInfo to set
     */
    public void setErrorInfo(String errorInfo) {
      this.errorInfo = errorInfo;
    }
  }

  public CyncroResult() {
    errorList = new ArrayList<CyncroError>();
  }

  public void addErrorInfo(CyncroError error) {
    this.errorList.add(error);
  }

  public void addErrorInfo(String rank, String info) {
    CyncroError error = new CyncroError();
    error.setRank(rank);
    error.setErrorInfo(info);
    this.errorList.add(error);
  }

  public boolean hasError() {
    return this.errorList.size() != 0;
  }

  /**
   * @return the seccessCount
   */
  public Long getSeccessCount() {
    return seccessCount;
  }

  /**
   * @param seccessCount
   *          the seccessCount to set
   */
  public void setSeccessCount(Long seccessCount) {
    this.seccessCount = seccessCount;
  }

  /**
   * @return the totalCount
   */
  public Long getTotalCount() {
    return totalCount;
  }

  /**
   * @param totalCount
   *          the totalCount to set
   */
  public void setTotalCount(Long totalCount) {
    this.totalCount = totalCount;
  }

  /**
   * @return the failCount
   */
  public Long getFailCount() {
    return failCount;
  }

  /**
   * @param failCount
   *          the failCount to set
   */
  public void setFailCount(Long failCount) {
    this.failCount = failCount;
  }

  /**
   * @return the errorList
   */
  public List<CyncroError> getErrorList() {
    return errorList;
  }

  /**
   * @param errorList
   *          the errorList to set
   */
  public void setErrorList(List<CyncroError> errorList) {
    this.errorList = errorList;
  }

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 92)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 93)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 94)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 95)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 96)
  private Date updatedDatetime;

  /**
   * データ行IDを取得します
   * 
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return this.ormRowid;
  }

  /**
   * 作成ユーザを取得します
   * 
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return this.createdUser;
  }

  /**
   * 作成日時を取得します
   * 
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(this.createdDatetime);
  }

  /**
   * 更新ユーザを取得します
   * 
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return this.updatedUser;
  }

  /**
   * 更新日時を取得します
   * 
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(this.updatedDatetime);
  }

  /**
   * データ行IDを設定します
   * 
   * @param val
   *          データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   * 
   * @param val
   *          作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   * 
   * @param val
   *          作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   * 
   * @param val
   *          更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   * 
   * @param val
   *          更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

}
