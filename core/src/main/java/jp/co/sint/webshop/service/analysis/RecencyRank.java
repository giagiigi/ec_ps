package jp.co.sint.webshop.service.analysis;

public enum RecencyRank {
  /** RA */
  RA("A"),
  /** RB */
  RB("B"),
  /** RC */
  RC("C");

  private String value;

  private RecencyRank(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public static RecencyRank fromValue(String searchValue) {
    for (RecencyRank r : RecencyRank.values()) {
      if (r.getValue().equals(searchValue)) {
        return r;
      }
    }
    return null;
  }

}
