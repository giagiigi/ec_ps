package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.ext.text.Messages;
import jp.co.sint.webshop.service.JdService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.jd.order.JdOrderSearchCondition;
import jp.co.sint.webshop.service.jd.order.JdOrderlLists;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.JdOrderListBean;
import jp.co.sint.webshop.web.bean.back.order.JdOrderListBean.JdOrderListList;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;
/**
 * jd订单管理拆分
 */
public class JdOrderListSearchAction extends WebBackAction<JdOrderListBean> {
  private JdOrderSearchCondition condition;
  protected JdOrderSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }
  
  protected JdOrderSearchCondition getSearchCondition() {
    return this.condition;
  }
  /**
   * 初期处理运行
   */
  @Override
  public void init() {
    condition = new JdOrderSearchCondition();
  }
  
  @Override
  public WebActionResult callService() {
    JdOrderListBean bean = getBean();
    
    JdService jdService = ServiceLocator.getJdService(getLoginInfo());
    List<JdOrderListList> jdlists = new ArrayList<JdOrderListList>();
    // 設置查詢條件
    condition = getCondition();
    condition.setSearchFromPaymentDatetime(bean.getSearchFromPaymentDatetime());
    condition.setSearchToPaymentDatetime(bean.getSearchToPaymentDatetime());
    condition.setOrderFromPaymentDatetime(bean.getOrderFromPaymentDatetime());
    condition.setOrderToPaymentDatetime(bean.getOrderToPaymentDatetime());
    SearchResult<JdOrderlLists> result = jdService.getJdSplistLists(condition);
    // 溢出检查
    prepareSearchWarnings(result, SearchWarningType.BOTH);
    // 清空List
    bean.getJdlistlist().clear();
    // 將查詢出的数据保存在BEAN中 
    //保存查询到的数据到bean中
    for (JdOrderlLists jd : result.getRows()) {
      JdOrderListList jdlist=new JdOrderListList();
      jdlist.setCustomerCode(jd.getCustomerCode());
      jdlist.setSubOrderNo(jd.getChildOrderNo());  
      jdlist.setSubOrderMoney(jd.getChildPaidPrice());
      if(jd.getShippingStatus().equals("2")){
        jdlist.setJdSubStatus("发货中");
      }else if(jd.getShippingStatus().equals("3")){
        jdlist.setJdSubStatus("发货完毕");
      }else if(jd.getShippingStatus().equals("1")){
        jdlist.setJdSubStatus("可以发货");
        
      }
      jdlist.setTheOrderNo(jd.getOrderNo());
      jdlist.setTheOrderMoney(jd.getOrderPayment());
      if(jd.getShippingStatus().equals("2")){
        jdlist.setJdTheStatus("发货中");
      }else if(jd.getShippingStatus().equals("3")){
        jdlist.setJdTheStatus("发货完毕");
      }else if(jd.getShippingStatus().equals("1")){
        jdlist.setJdTheStatus("可以发货");
        
      }
      jdlists.add(jdlist);
    }
    bean.setPagerValue(PagerUtil.createValue(result));
    bean.setJdlistlist(jdlists);
      /**
       * 
       */ 
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }
      
  /**
   * 数据模型容纳后检验
   */
  @Override
  public boolean validate() {
    ValidationSummary summary = BeanValidator.validate(getBean());
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
      return false;
    }
    condition = getCondition();
    //日期的大小关系检验
    condition.setSearchFromPaymentDatetime(getBean().getSearchFromPaymentDatetime());
    condition.setSearchToPaymentDatetime(getBean().getSearchToPaymentDatetime());
    //检验
    condition.setOrderFromPaymentDatetime(getBean().getOrderFromPaymentDatetime());
    condition.setOrderToPaymentDatetime(getBean().getOrderToPaymentDatetime());
    if (condition.isValid()) {
      return true;
    } else {
      if (StringUtil.hasValueAllOf(condition.getSearchFromPaymentDatetime(), condition.getSearchToPaymentDatetime())) {
        if (!StringUtil.isCorrectRange(condition.getSearchFromPaymentDatetime(), condition.getSearchToPaymentDatetime())) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "仓库发货日期"));
        }
      }
    if (StringUtil.hasValueAllOf(condition.getOrderFromPaymentDatetime(), condition.getOrderToPaymentDatetime())) {
      if (!StringUtil.isCorrectRange(condition.getOrderFromPaymentDatetime(), condition.getOrderToPaymentDatetime())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "订单日期"));
      }
    }
    }
  
    

    return false;
  }
  /**
   * 采取行动
   * return动作执行的查询结果
   * return动作
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.ORDER_READ_SHOP) || getLoginInfo().hasPermission(Permission.ORDER_READ_SITE);
   // return true;
  }
  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
   return Messages.getString("web.action.back.order.JdOrderListSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102041006";
  }



}
