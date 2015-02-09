package jp.co.sint.webshop.service.catalog;

public class ResultBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private Long allCnt;

  /**
   * @return the allCnt
   */
  public Long getAllCnt() {
    return allCnt;
  }

  /**
   * @param allCnt
   *          the allCnt to set
   */
  public void setAllCnt(Long allCnt) {
    this.allCnt = allCnt;
  }

  public ResultBean(Long allCnt) {
    this.allCnt = allCnt;
  }

  public ResultBean() {
  }

}
