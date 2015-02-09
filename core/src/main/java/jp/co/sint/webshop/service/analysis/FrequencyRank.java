package jp.co.sint.webshop.service.analysis;

public enum FrequencyRank {
  /** FA */
  FA("A"),
  /** FB */
  FB("B"),
  /** FC */
  FC("C");

  private String value;

  private FrequencyRank(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public static FrequencyRank fromValue(String searchValue) {
    for (FrequencyRank f : FrequencyRank.values()) {
      if (f.getValue().equals(searchValue)) {
        return f;
      }
    }
    return null;
  }
}
