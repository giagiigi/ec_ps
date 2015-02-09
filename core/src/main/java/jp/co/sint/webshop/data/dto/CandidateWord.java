package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

public class CandidateWord implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  @PrimaryKey(1)
  @Required
  @Length(256)
  @Metadata(name = "検索内容←実際の検索ワード", order = 1)
  private String searchWord;

  @PrimaryKey(1)
  @Required
  @Length(2)
  @Metadata(name = "言語（JP/CN/EN）", order = 2)
  private String lang;

  @Length(256)
  @Metadata(name = "日：平仮名/片仮名/ローマ字、中：pinyin", order = 3)
  private String pinyin;

  @Required
  @Length(8)
  @Metadata(name = "検索件数", order = 4)
  private long searchCount;

  @Required
  @Length(8)
  @Metadata(name = "HIT商品件数", order = 5)
  private long hitCount;

  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 6)
  private Long ormRowid;

  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 7)
  private String createdUser;

  @Required
  @Metadata(name = "作成日時", order = 8)
  private Date createdDatetime;

  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 9)
  private String updatedUser;

  @Required
  @Metadata(name = "更新日時", order = 10)
  private Date updateDatetime;

  /**
   * @return the searchWord
   */
  public String getSearchWord() {
    return searchWord;
  }

  /**
   * @param searchWord
   *          the searchWord to set
   */
  public void setSearchWord(String searchWord) {
    this.searchWord = searchWord;
  }

  /**
   * @return the lang
   */
  public String getLang() {
    return lang;
  }

  /**
   * @param lang
   *          the lang to set
   */
  public void setLang(String lang) {
    this.lang = lang;
  }

  /**
   * @return the pinyin
   */
  public String getPinyin() {
    return pinyin;
  }

  /**
   * @param pinyin
   *          the pinyin to set
   */
  public void setPinyin(String pinyin) {
    this.pinyin = pinyin;
  }

  /**
   * @return the searchCount
   */
  public long getSearchCount() {
    return searchCount;
  }

  /**
   * @param searchCount
   *          the searchCount to set
   */
  public void setSearchCount(long searchCount) {
    this.searchCount = searchCount;
  }

  /**
   * @return the hitCount
   */
  public long getHitCount() {
    return hitCount;
  }

  /**
   * @param hitCount
   *          the hitCount to set
   */
  public void setHitCount(long hitCount) {
    this.hitCount = hitCount;
  }

  /**
   * @return the updateDatetime
   */
  public Date getUpdateDatetime() {
    return updateDatetime;
  }

  /**
   * @param updateDatetime
   *          the updateDatetime to set
   */
  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = updateDatetime;
  }

  @Override
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(this.createdDatetime);
  }

  @Override
  public String getCreatedUser() {
    return this.createdUser;
  }

  @Override
  public Long getOrmRowid() {
    return this.ormRowid;
  }

  @Override
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(this.updateDatetime);
  }

  @Override
  public String getUpdatedUser() {
    return this.updatedUser;
  }

  @Override
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);

  }

  @Override
  public void setCreatedUser(String val) {
    this.createdUser = val;

  }

  @Override
  public void setOrmRowid(Long val) {
    this.ormRowid = val;

  }

  @Override
  public void setUpdatedDatetime(Date val) {
    this.updateDatetime = DateUtil.immutableCopy(val);

  }

  @Override
  public void setUpdatedUser(String val) {
    this.updatedUser = val;

  }

}
