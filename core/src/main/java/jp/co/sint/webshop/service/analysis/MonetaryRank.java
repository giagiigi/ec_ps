package jp.co.sint.webshop.service.analysis;

public enum MonetaryRank {
  /** MA */
  MA("A"),
  /** MB */
  MB("B"),
  /** MC */
  MC("C");

  private String value;

  private MonetaryRank(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public static MonetaryRank fromValue(String searchValue) {
    for (MonetaryRank m : MonetaryRank.values()) {
      if (m.getValue().equals(searchValue)) {
        return m;
      }
    }
    return null;
  }

}
