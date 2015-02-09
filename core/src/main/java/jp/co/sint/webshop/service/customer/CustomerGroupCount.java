package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.CustomerGroup;

public class CustomerGroupCount extends CustomerGroup {

  /**
   * 「顧客グループ(CUSTOMER_GROUP)」の拡張Bean
   */
  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 顧客グループ別会員数集計結果 */
  @Required
  private Long memberShip;

  public Long getMemberShip() {
    return memberShip;
  }

  public void setMemberShip(Long memberShip) {
    this.memberShip = memberShip;
  }

}
