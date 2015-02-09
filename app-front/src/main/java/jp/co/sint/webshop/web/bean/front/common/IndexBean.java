package jp.co.sint.webshop.web.bean.front.common;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040110:トップページのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class IndexBean extends UIFrontBean {

  private boolean isIpad = false;

  private String endDate;
  
  private String startDate;
  
  @Override
  public void setSubJspId() {
    super.setSubJspId();
    addSubJspId("/common/information");
    //addSubJspId("/catalog/category_tree");
    // del by lc 2012-09-17 start
    //addSubJspId("/catalog/sales_charts");
    // del by lc 2012-09-17 end
    //addSubJspId("/catalog/sales_plan");
    addSubJspId("/catalog/sales_tag_category");
    //addSubJspId("/common/group_campaign");
    // add by lc 2012-03-28 start
    //addSubJspId("/common/review_index");
    if (!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)) {
      addSubJspId("/catalog/sale_plan_mobile");
      ClientType clientType = getClient();
      if (ClientType.IPAD.equals(clientType)) {
        isIpad = true;
      }
    }
    // add by lc 2012-03-28 end
  }

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
  }

  /**
   * @return the isIpad
   */
  public boolean isIpad() {
    return isIpad;
  }

  /**
   * @param isIpad
   *          the isIpad to set
   */
  public void setIpad(boolean isIpad) {
    this.isIpad = isIpad;
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2040110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.common.IndexBean.0");
  }

  
  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  
  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  
  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  
  /**
   * @return the startDate
   */
  public String getStartDate() {
    return startDate;
  }

}
