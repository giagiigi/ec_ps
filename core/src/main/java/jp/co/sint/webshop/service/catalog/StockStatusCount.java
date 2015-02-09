package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.dto.StockStatus;

/**
 * @author System Integrator Corp.
 */
public class StockStatusCount extends StockStatus {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 商品の関連付き件数 */
  private Long relatedCount;

  /**
   * relatedCountを返します。
   * 
   * @return the relatedCount
   */
  public Long getRelatedCount() {
    return relatedCount;
  }

  /**
   * relatedCountを設定します。
   * 
   * @param relatedCount
   *          設定する relatedCount
   */
  public void setRelatedCount(Long relatedCount) {
    this.relatedCount = relatedCount;
  }

}
