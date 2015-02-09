package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.Tag;

public class TagCount extends Tag {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 商品の関連付き件数 */
  @Required
  private Long relatedCount;

  /**
   * @return relatedCount
   */
  public Long getRelatedCount() {
    return relatedCount;
  }

  /**
   * @param relatedCount
   *          設定する relatedCount
   */
  public void setRelatedCount(Long relatedCount) {
    this.relatedCount = relatedCount;
  }

}
