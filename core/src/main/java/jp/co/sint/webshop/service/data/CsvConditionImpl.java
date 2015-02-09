package jp.co.sint.webshop.service.data;


import jp.co.sint.webshop.data.csv.CsvFilter;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.LoginInfo;

public abstract class CsvConditionImpl<S extends CsvSchema> implements CsvImportCondition<S>, CsvExportCondition<S> {

  private static final long serialVersionUID = 1L; // 10.1.4 K00149 追加

  private CsvFilter filter = new CsvFilter();

  private S schema;

  private boolean header = true;

  private LoginInfo loginInfo;

  private String shopCode;

  // 20130703 shen add start
  private String syncFlagTmall;

  // 20130703 shen add end
  
  // 2014/04/28 京东WBS对应 ob_卢 add start
  private String syncFlagJd;
  // 2014/04/28 京东WBS对应 ob_卢 add end

  // 20130809 yyq add start
  private String shipOrderNo;

  // 20130809 yyq add end

  public S getSchema() {
    return schema;
  }

  public void setSchema(S schema) {
    this.schema = schema;
  }

  public boolean hasHeader() {
    return header;
  }

  public void setHeader(boolean header) {
    this.header = header;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * loginInfoを返します。
   * 
   * @return the loginInfo
   */
  public LoginInfo getLoginInfo() {
    return loginInfo;
  }

  /**
   * loginInfoを設定します。
   * 
   * @param loginInfo
   *          設定する loginInfo
   */
  public void setLoginInfo(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }

  /**
   * filterを返します。
   * 
   * @return the filter
   */
  public CsvFilter getFilter() {
    return filter;
  }

  /**
   * filterを設定します。
   * 
   * @param filter
   *          設定する filter
   */
  public void setFilter(CsvFilter filter) {
    this.filter = filter;
  }

  /**
   * @return the syncFlagTmall
   */
  public String getSyncFlagTmall() {
    return syncFlagTmall;
  }

  /**
   * @param syncFlagTmall
   *          the syncFlagTmall to set
   */
  public void setSyncFlagTmall(String syncFlagTmall) {
    this.syncFlagTmall = syncFlagTmall;
  }

  /**
   * @return the shipOrderNo
   */
  public String getShipOrderNo() {
    return shipOrderNo;
  }

  /**
   * @param shipOrderNo
   *          the shipOrderNo to set
   */
  public void setShipOrderNo(String shipOrderNo) {
    this.shipOrderNo = shipOrderNo;
  }

  /**
   * @return the syncFlagJd
   */
  public String getSyncFlagJd() {
    return syncFlagJd;
  }

  /**
   * @param syncFlagJd the syncFlagJd to set
   */
  public void setSyncFlagJd(String syncFlagJd) {
    this.syncFlagJd = syncFlagJd;
  }

}
