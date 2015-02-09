package jp.co.sint.webshop.web.bean.front.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040510:商品詳細のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class DetailReviewBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String reviewCount;

  private int reviewInitDisplayCount;

  private List<DetailReviewDetail> list = new ArrayList<DetailReviewDetail>();

  private List<DetailReviewDetail> init = new ArrayList<DetailReviewDetail>();

  /**
   * U2040510:商品詳細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class DetailReviewDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String reviewTitle;

    private String nickName;

    private String reviewScore;

    private String reviewDescription;

    private String reviewContributedDatetime;

    private Long sex;

    /**
     * nickNameを取得します。
     * 
     * @return nickName
     */
    public String getNickName() {
      return nickName;
    }

    /**
     * nickNameを設定します。
     * 
     * @param nickName
     *          nickName
     */
    public void setNickName(String nickName) {
      this.nickName = nickName;
    }

    /**
     * reviewDescriptionを取得します。
     * 
     * @return reviewDescription
     */
    public String getReviewDescription() {
      return reviewDescription;
    }

    /**
     * reviewDescriptionを設定します。
     * 
     * @param reviewDescription
     *          reviewDescription
     */
    public void setReviewDescription(String reviewDescription) {
      this.reviewDescription = reviewDescription;
    }

    /**
     * reviewScoreを取得します。
     * 
     * @return reviewScore
     */
    public String getReviewScore() {
      return reviewScore;
    }

    /**
     * reviewScoreを設定します。
     * 
     * @param reviewScore
     *          reviewScore
     */
    public void setReviewScore(String reviewScore) {
      this.reviewScore = reviewScore;
    }

    /**
     * reviewTitleを取得します。
     * 
     * @return reviewTitle
     */
    public String getReviewTitle() {
      return reviewTitle;
    }

    /**
     * reviewTitleを設定します。
     * 
     * @param reviewTitle
     *          reviewTitle
     */
    public void setReviewTitle(String reviewTitle) {
      this.reviewTitle = reviewTitle;
    }

    /**
     * reviewContributedDatetimeを返します。
     * 
     * @return the reviewContributedDatetime
     */
    public String getReviewContributedDatetime() {
      return reviewContributedDatetime;
    }

    /**
     * reviewContributedDatetimeを設定します。
     * 
     * @param reviewContributedDatetime
     *          設定する reviewContributedDatetime
     */
    public void setReviewContributedDatetime(String reviewContributedDatetime) {
      this.reviewContributedDatetime = reviewContributedDatetime;
    }

    /**
     * @return the sex
     */
    public Long getSex() {
      return sex;
    }

    /**
     * @param sex
     *          the sex to set
     */
    public void setSex(Long sex) {
      this.sex = sex;
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
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2040510";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.DetailReviewBean.0");
  }

  /**
   * レビューリストを取得します。
   * 
   * @return list
   */
  public List<DetailReviewDetail> getList() {
    return list;
  }

  /**
   * レビューリストを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<DetailReviewDetail> list) {
    this.list = list;
  }

  /**
   * initを設定します。
   * 
   * @param init
   *          init
   */
  public void setInit(List<DetailReviewDetail> init) {
    this.init = init;
  }

  /**
   * initを取得します。
   * 
   * @return init
   */
  public List<DetailReviewDetail> getInit() {
    return init;
  }

  /**
   * reviewCountを設定します。
   * 
   * @param reviewCount
   *          reviewCount
   */
  public void setReviewCount(String reviewCount) {
    this.reviewCount = reviewCount;
  }

  /**
   * reviewCountを取得します。
   * 
   * @return reviewCount
   */
  public String getReviewCount() {
    return reviewCount;
  }

  /**
   * reviewInitDisplayCountを取得します。
   * 
   * @return reviewInitDisplayCount
   */
  public int getReviewInitDisplayCount() {
    return reviewInitDisplayCount;
  }

  /**
   * reviewInitDisplayCountを設定します。
   * 
   * @param reviewInitDisplayCount
   *          reviewInitDisplayCount
   */
  public void setReviewInitDisplayCount(int reviewInitDisplayCount) {
    this.reviewInitDisplayCount = reviewInitDisplayCount;
  }

}
