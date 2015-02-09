package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.ext.text.Messages;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.JdOrderListExportCondition;
import jp.co.sint.webshop.service.data.csv.JdOrderListExportConditionTwo;
import jp.co.sint.webshop.service.jd.order.JdOrderSearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.JdOrderListBean;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1020210:受注入金管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class JdOrderListExportpageAction extends WebBackAction<JdOrderListBean> implements WebExportAction {
  
  private JdOrderSearchCondition condition;
  protected JdOrderSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }
  
  protected JdOrderSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    if (getRequestParameter().getPathArgs().length > 0) {
      return false;
    }
    return true;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.ORDER_DATA_IO_SITE)
        || getLoginInfo().hasPermission(Permission.ORDER_DATA_IO_SHOP);
  }


  /**
   * 采取执行
   * @return 执行后的结果
   */
  @Override
  public WebActionResult callService() {
    
    JdOrderListBean bean = getBean();
    condition = new JdOrderSearchCondition();
    condition.setSearchFromPaymentDatetime(bean.getSearchFromPaymentDatetime());
    condition.setSearchToPaymentDatetime(bean.getSearchToPaymentDatetime());
    condition.setOrderFromPaymentDatetime(bean.getOrderFromPaymentDatetime());
    condition.setOrderToPaymentDatetime(bean.getOrderToPaymentDatetime());
    JdOrderListExportConditionTwo jdOrderConditionTwo = CsvExportType.EXPORT_CSV_JD_ISP_TWO.createConditionInstance();
    jdOrderConditionTwo.setCondition(condition);
    this.exportCondition = jdOrderConditionTwo;

    setRequestBean(bean);

    setNextUrl("/download");

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 最后csv导出
   */
  private CsvExportCondition<? extends CsvSchema> exportCondition;
  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return exportCondition;
  }
  


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.JdOrderListExportAction.1");
    
  }
  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102088012";
  }

  @Override
  public boolean validate() {
   return true;
  }
}
