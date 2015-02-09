package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1070510:顧客分析のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerStatisticsBean extends UIBackBean {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** 顧客分析明細 */
  private List<CustomerStatisticsDetail> customerStatisticsResult = new ArrayList<CustomerStatisticsDetail>();

  /**
   * U1070510:顧客分析のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerStatisticsDetail implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    /** 顧客分析種別 */
    private String statisticsGroup;

    /** 結果リスト */
    private List<CustomerStatisticsItem> statisticsDetail = new ArrayList<CustomerStatisticsItem>();

    /**
     * statisticsDetailを取得します。
     * 
     * @return statisticsDetail
     */
    public List<CustomerStatisticsItem> getStatisticsDetail() {
      return statisticsDetail;
    }

    /**
     * statisticsGroupを取得します。
     * 
     * @return statisticsGroup
     */
    public String getStatisticsGroup() {
      return statisticsGroup;
    }

    /**
     * statisticsDetailを設定します。
     * 
     * @param statisticsDetail
     *          statisticsDetail
     */
    public void setStatisticsDetail(List<CustomerStatisticsItem> statisticsDetail) {
      this.statisticsDetail = statisticsDetail;
    }

    /**
     * statisticsGroupを設定します。
     * 
     * @param statisticsGroup
     *          statisticsGroup
     */
    public void setStatisticsGroup(String statisticsGroup) {
      this.statisticsGroup = statisticsGroup;
    }
  }

  /**
   * U1070510:顧客分析のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerStatisticsItem implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    /** 顧客分析種別名 */
    private String statisticsItem;

    /** 顧客数 */
    private String customerAmount;

    /**
     * customerAmountを取得します。
     * 
     * @return customerAmount
     */
    public String getCustomerAmount() {
      return customerAmount;
    }

    /**
     * statisticsItemを取得します。
     * 
     * @return statisticsItem
     */
    public String getStatisticsItem() {
      return statisticsItem;
    }

    /**
     * customerAmountを設定します。
     * 
     * @param customerAmount
     *          customerAmount
     */
    public void setCustomerAmount(String customerAmount) {
      this.customerAmount = customerAmount;
    }

    /**
     * statisticsItemを設定します。
     * 
     * @param statisticsItem
     *          statisticsItem
     */
    public void setStatisticsItem(String statisticsItem) {
      this.statisticsItem = statisticsItem;
    }
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1070510";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.analysis.CustomerStatisticsBean.0");
  }

  /**
   * customerStatisticsResultを取得します。
   * 
   * @return customerStatisticsResult
   */
  public List<CustomerStatisticsDetail> getCustomerStatisticsResult() {
    return customerStatisticsResult;
  }

  /**
   * customerStatisticsResultを設定します。
   * 
   * @param customerStatisticsResult
   *          customerStatisticsResult
   */
  public void setCustomerStatisticsResult(List<CustomerStatisticsDetail> customerStatisticsResult) {
    this.customerStatisticsResult = customerStatisticsResult;
  }

}
