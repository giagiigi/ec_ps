package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityMasterResult;
import jp.co.sint.webshop.service.catalog.CommodityMasterSearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityMasterListBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityMasterListBean.CommodityMasterList;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
/**
 * TM/JD多商品关联画面检索
 */
public class CommodityMasterListSearchAction extends WebBackAction<CommodityMasterListBean> {
  private CommodityMasterSearchCondition condition;
  protected CommodityMasterSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }
  
  protected CommodityMasterSearchCondition getSearchCondition() {
    return this.condition;
  }
  /**
   *初期运行管理
   */
  @Override
  public void init() {
    condition = new CommodityMasterSearchCondition();
  }
  @Override
  public WebActionResult callService() {
    CommodityMasterListBean bean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    List<CommodityMasterList> CMlists = new ArrayList<CommodityMasterList>();
    // 設置查詢條件
    condition = getCondition();
    condition.setCommodityCode(bean.getCommodityCode());
    condition.setCommodityName(bean.getCommodityName());
    //condition.setOrderWordName(bean.getSearchWord()); 
    SearchResult<CommodityMasterResult> result = service.getCommodityMasterResult(condition);
    // 溢出检查
    prepareSearchWarnings(result, SearchWarningType.BOTH);
    //bean.getUntrueorderwordlist().clear();
    for (CommodityMasterResult cmr : result.getRows()) {
      CommodityMasterList CMlist=new CommodityMasterList();
      CMlist.setCommodityCode(cmr.getCommodityCode());
      CMlist.setCommodityName(cmr.getCommodityName());
      CMlist.setJdCommodityCode(cmr.getJdCommodityCode());
      CMlist.setTmallCommodityCode(cmr.getTmallCommodityCode());
      CMlists.add(CMlist);
    }
    bean.setPagerValue(PagerUtil.createValue(result));
    bean.setCommoditymasterlist(CMlists);
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
    return Permission.COMMODITY_MASTER_LIST.isGranted(getLoginInfo());
  }
  /**
   * Action名の取得 
   * @return Action名
   */
  public String getActionName() {
   return Messages.getString("web.action.back.catalog.CommodityMasterListSearchAction.0");
  }
  /**
   * オペレーションコードの取得
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102081010";
  }
}
