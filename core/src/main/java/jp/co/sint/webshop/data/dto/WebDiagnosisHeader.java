package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「肌肤诊断履历」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * @author ks
 *
 */
public class WebDiagnosisHeader implements Serializable, WebshopEntity  {
  
  /** Serial Version UID */
  private static final long serialVersionUID = -1;
  
  /** 肌肤诊断Header编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "肌肤诊断Header编号", order = 1)
  private String webDiagnosisHeaderNo;
  
  /** 顾客编号 */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顾客编号", order = 2)
  private String customerCode;
  
  
  /** 顾客名称 */
  @Required
  @Length(20)
  @Metadata(name = "顾客名称", order = 3)
  private String customerName;
  
  /** 肌肤诊断项目编号 */
  @Required
  @Length(16)
  @Metadata(name = "肌肤诊断项目编号", order = 4)
  private String webDiagnosisCode;
  
  /** 诊断日期 */
  @Required
  @Metadata(name = "诊断日期", order = 5)
  private Date webDiagnosisDate;  

  /** 诊断主题 */
  @Required
  @Length(50)
  @Metadata(name = "诊断主题", order = 6)
  private String webDiagnosisSubject;
  
  /** 综合结果标题 */
  @Required
  @Length(30)
  @Metadata(name = "综合结果标题", order = 7)
  private String webDiagnosisResultTitle;
  
  /** 综合结果描述 */
  @Required
  @Length(200)
  @Metadata(name = "综合结果描述", order = 8)
  private String webDiagnosisResultComment;
  

  /** 推荐要点1 */
  @Required
  @Length(60)
  @Metadata(name = "推荐要点1", order = 39)
  private String recommendPoint1Title;

  /** 推荐要点1详细 */
  @Required
  @Length(1000)
  @Metadata(name = "推荐要点1详细", order = 40)
  private String recommendPoint1Detail;

  /** 推荐要点2 */
  @Length(60)
  @Metadata(name = "推荐要点2", order = 41)
  private String recommendPoint2Title;

  /** 推荐要点2详细 */
  @Length(1000)
  @Metadata(name = "推荐要点2详细", order = 42)
  private String recommendPoint2Detail;

  /** 推荐要点3 */
  @Length(60)
  @Metadata(name = "推荐要点3", order = 43)
  private String recommendPoint3Title;

  /** 推荐要点3详细 */
  @Length(1000)
  @Metadata(name = "推荐要点3详细", order = 44)
  private String recommendPoint3Detail;

  /** 推荐要点4 */
  @Length(60)
  @Metadata(name = "推荐要点4", order = 45)
  private String recommendPoint4Title;

  /** 推荐要点4详细 */
  @Length(1000)
  @Metadata(name = "推荐要点4详细", order = 46)
  private String recommendPoint4Detail;

  /** 推荐要点5 */
  @Length(60)
  @Metadata(name = "推荐要点5", order = 47)
  private String recommendPoint5Title;

  /** 推荐要点5详细 */
  @Length(1000)
  @Metadata(name = "推荐要点5详细", order = 48)
  private String recommendPoint5Detail;
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 49)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 50)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 51)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 52)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 53)
  private Date updatedDatetime;
  
  

  /**
   * customerCodeを取得します
   *
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * customerCodeを設定します
   * 
   * @param 
   *          customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * webDiagnosisHeaderNoを取得します
   *
   * @return webDiagnosisHeaderNo
   */
  public String getWebDiagnosisHeaderNo() {
    return webDiagnosisHeaderNo;
  }

  /**
   * webDiagnosisHeaderNoを設定します
   * 
   * @param 
   *          webDiagnosisHeaderNo
   */
  public void setWebDiagnosisHeaderNo(String webDiagnosisHeaderNo) {
    this.webDiagnosisHeaderNo = webDiagnosisHeaderNo;
  }

  /**
   * customerNameを取得します
   *
   * @return customerName
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * customerNameを設定します
   * 
   * @param 
   *          customerName
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  /**
   *webDiagnosisDateを取得します
   *
   * @return webDiagnosisDate
   */
  public Date getWebDiagnosisDate() {
    return webDiagnosisDate;
  }

  /**
   * webDiagnosisDateを設定します
   * 
   * @param 
   *          webDiagnosisDate
   */
  public void setWebDiagnosisDate(Date webDiagnosisDate) {
    this.webDiagnosisDate = webDiagnosisDate;
  }

  /**
   * webDiagnosisSubjectを取得します
   *
   * @return webDiagnosisSubject
   */
  public String getWebDiagnosisSubject() {
    return webDiagnosisSubject;
  }

  /**
   * webDiagnosisSubjectを設定します
   * 
   * @param 
   *          webDiagnosisSubject
   */
  public void setWebDiagnosisSubject(String webDiagnosisSubject) {
    this.webDiagnosisSubject = webDiagnosisSubject;
  }

  
  
  /**
   * webDiagnosisResultTitleを取得します
   *
   * @return webDiagnosisResultTitle
   */
  public String getWebDiagnosisResultTitle() {
    return webDiagnosisResultTitle;
  }

  /**
   * webDiagnosisResultTitleを設定します
   * 
   * @param 
   *          webDiagnosisResultTitle
   */
  public void setWebDiagnosisResultTitle(String webDiagnosisResultTitle) {
    this.webDiagnosisResultTitle = webDiagnosisResultTitle;
  }
  /**
   * webDiagnosisResultCommentを取得します
   *
   * @return webDiagnosisResultComment
   */
  public String getWebDiagnosisResultComment() {
    return webDiagnosisResultComment;
  }


  /**
   * webDiagnosisResultCommentを設定します
   * 
   * @param 
   *          webDiagnosisResultComment
   */
  public void setWebDiagnosisResultComment(String webDiagnosisResultComment) {
    this.webDiagnosisResultComment = webDiagnosisResultComment;
  }
  

  /**
   * recommendPoint1Titleを取得します
   *
   * @return webDiagnosisHeaderNo
   */
  public String getRecommendPoint1Title() {
    return recommendPoint1Title;
  }

  /**
   * recommendPoint1Detailを設定します
   * 
   * @param 
   *          recommendPoint1Detail
   */
  public void setRecommendPoint1Title(String recommendPoint1Title) {
    this.recommendPoint1Title = recommendPoint1Title;
  }

  /**
   * recommendPoint1Detailを取得します
   *
   * @return recommendPoint1Detail
   */
  public String getRecommendPoint1Detail() {
    return recommendPoint1Detail;
  }

  /**
   * recommendPoint1Detailを設定します
   * 
   * @param 
   *          recommendPoint1Detail
   */
  public void setRecommendPoint1Detail(String recommendPoint1Detail) {
    this.recommendPoint1Detail = recommendPoint1Detail;
  }

  /**
   * recommendPoint2Titleを取得します
   *
   * @return recommendPoint2Title
   */
  public String getRecommendPoint2Title() {
    return recommendPoint2Title;
  }

  /**
   * recommendPoint2Titleを設定します
   * 
   * @param 
   *          recommendPoint2Title
   */
  public void setRecommendPoint2Title(String recommendPoint2Title) {
    this.recommendPoint2Title = recommendPoint2Title;
  }

  /**
   * recommendPoint2Detailを取得します
   *
   * @return recommendPoint2Detail
   */
  public String getRecommendPoint2Detail() {
    return recommendPoint2Detail;
  }

  /**
   * recommendPoint2Detailを設定します
   * 
   * @param 
   *          recommendPoint2Detail
   */
  public void setRecommendPoint2Detail(String recommendPoint2Detail) {
    this.recommendPoint2Detail = recommendPoint2Detail;
  }

  /**
   * recommendPoint3Titleを取得します
   *
   * @return recommendPoint3Title
   */
  public String getRecommendPoint3Title() {
    return recommendPoint3Title;
  }

  /**
   * recommendPoint3Titleを設定します
   * 
   * @param 
   *          recommendPoint3Title
   */
  public void setRecommendPoint3Title(String recommendPoint3Title) {
    this.recommendPoint3Title = recommendPoint3Title;
  }

  /**
   * recommendPoint3Detailを取得します
   *
   * @return recommendPoint3Detail
   */
  public String getRecommendPoint3Detail() {
    return recommendPoint3Detail;
  }

  /**
   * recommendPoint3Detailを設定します
   * 
   * @param 
   *          recommendPoint3Detail
   */
  public void setRecommendPoint3Detail(String recommendPoint3Detail) {
    this.recommendPoint3Detail = recommendPoint3Detail;
  }

  /**
   * recommendPoint4Titleを取得します
   *
   * @return recommendPoint4Title
   */
  public String getRecommendPoint4Title() {
    return recommendPoint4Title;
  }

  /**
   * recommendPoint4Titleを設定します
   * 
   * @param 
   *          recommendPoint4Title
   */
  public void setRecommendPoint4Title(String recommendPoint4Title) {
    this.recommendPoint4Title = recommendPoint4Title;
  }

  /**
   * recommendPoint4Detailを取得します
   *
   * @return recommendPoint4Detail
   */
  public String getRecommendPoint4Detail() {
    return recommendPoint4Detail;
  }

  /**
   * recommendPoint4Detailを設定します
   * 
   * @param 
   *          recommendPoint4Detail
   */
  public void setRecommendPoint4Detail(String recommendPoint4Detail) {
    this.recommendPoint4Detail = recommendPoint4Detail;
  }

  /**
   * recommendPoint5Titleを取得します
   *
   * @return recommendPoint5Title
   */
  public String getRecommendPoint5Title() {
    return recommendPoint5Title;
  }

  /**
   * recommendPoint5Titleを設定します
   * 
   * @param 
   *          recommendPoint5Title
   */
  public void setRecommendPoint5Title(String recommendPoint5Title) {
    this.recommendPoint5Title = recommendPoint5Title;
  }

  /**
   * recommendPoint5Detailを取得します
   *
   * @return recommendPoint5Detail
   */
  public String getRecommendPoint5Detail() {
    return recommendPoint5Detail;
  }

  /**
   * recommendPoint5Detailを設定します
   * 
   * @param 
   *          recommendPoint5Detail
   */
  public void setRecommendPoint5Detail(String recommendPoint5Detail) {
    this.recommendPoint5Detail = recommendPoint5Detail;
  }

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

  
  /**
   * @return the webDiagnosisCode
   */
  public String getWebDiagnosisCode() {
    return webDiagnosisCode;
  }

  
  /**
   * @param webDiagnosisCode the webDiagnosisCode to set
   */
  public void setWebDiagnosisCode(String webDiagnosisCode) {
    this.webDiagnosisCode = webDiagnosisCode;
  }
}
