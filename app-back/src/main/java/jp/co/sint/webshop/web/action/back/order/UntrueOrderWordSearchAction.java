package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.order.UntrueOrderWordResult;
import jp.co.sint.webshop.service.order.UntrueOrderWordSearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.UntrueOrderWordBean;
import jp.co.sint.webshop.web.bean.back.order.UntrueOrderWordBean.UntrueOrderWordList;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
/**
 * 虚假订单关键词管理
 */
public class UntrueOrderWordSearchAction extends WebBackAction<UntrueOrderWordBean> {
  private UntrueOrderWordSearchCondition condition;
  protected UntrueOrderWordSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }
  
  protected UntrueOrderWordSearchCondition getSearchCondition() {
    return this.condition;
  }
  /**
   *初期运行管理
   */
  @Override
  public void init() {
    condition = new UntrueOrderWordSearchCondition();
  }
  
  @Override
  public WebActionResult callService() {
    UntrueOrderWordBean bean = getBean();
    OrderService orderservice = ServiceLocator.getOrderService(getLoginInfo());
    List<UntrueOrderWordList> UOWlists = new ArrayList<UntrueOrderWordList>();
    // 設置查詢條件
    condition = getCondition();
    condition.setOrderWordName(bean.getSearchWord());
    SearchResult<UntrueOrderWordResult> result = orderservice.getUntrueOrderWordList(condition);
    // 溢出检查
    prepareSearchWarnings(result, SearchWarningType.BOTH);
    bean.getUntrueorderwordlist().clear();
    for (UntrueOrderWordResult uow : result.getRows()) {
      UntrueOrderWordList UOWlist=new UntrueOrderWordList();
      UOWlist.setOrderWordCode(uow.getOrderWordCode());             
      UOWlist.setOrderWordName(uow.getOrderWordName());
      UOWlists.add(UOWlist);
    }
    bean.setPagerValue(PagerUtil.createValue(result));
    bean.setUntrueorderwordlist(UOWlists);
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }
  /**
   * 数据模型容纳后检验
   */
  @Override
  public boolean validate() {
  return true;
  }
  /**
   * 采取行动
   * return动作执行的查询结果
   * return动作
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.UNTRUE_ORDER_WORD);
  }
  /**
   * Action名の取得 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.UntrueOrderWordSearchAction.0");
  }
  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102051006";
  }
}
