package jp.co.sint.webshop.utility;

public enum SystemUserAgent {

  BACK_ORDER("back_order"), OTHER("other");

  private String keyword;

  private SystemUserAgent(String keyword) {
    this.keyword = keyword;
  }

  public String getKeyword() {
    return keyword;
  }
}
