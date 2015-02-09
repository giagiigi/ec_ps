package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CyncroResult;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCynchroBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040110:商品同期化action
 * 
 * @author System Integrator Corp.
 */
public class CommodityCynchroCynchroAction extends CommodityCynchroBaseAction {

  
  
  /**
   * 初期処理を実行します
   */
  public void init() {
    String[] values = getBean().getCheckCommodityCodes();
    CommodityCynchroBean bean = new CommodityCynchroBean();
    bean.setCheckCommodityCodes(values);
    this.setBean(bean);
    super.init();
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
  }

  /**
   * 从bean  和 url传参中得到多选框的值
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
//    CommodityCynchroBean reqBean = (CommodityCynchroBean) getRequestBean();
    
    if (!StringUtil.hasValueAllOf(getBean().getCheckCommodityCodes())) {
      addErrorMessage("没有选中任何商品");
      return false;
    }
    
    CommodityCynchroBean bean = getBean();
    String[] to = getRequestParameter().getPathArgs();
    
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    //查询最后同期时间
    String ecTimeString = catalogService.getCychroTimeByType("0");
    String tmallTime = catalogService.getCychroTimeByType("1");
    String lastTime = catalogService.getCychroTimeByType("2");
    // 2014/05/02 京东WBS对应 ob_姚 add start
    String jdTime = catalogService.getCychroTimeByType("3");
    // 2014/05/02 京东WBS对应 ob_姚 add end
    //设置最后同期时间
    bean.setLastCynchroTime(lastTime);
    bean.setLastCynchroTimeEc(ecTimeString);
    bean.setLastCynchroTimeTmall(tmallTime);
    // 2014/05/02 京东WBS对应 ob_姚 add start
    bean.setLastCynchroTimeJd(jdTime);
    // 2014/05/02 京东WBS对应 ob_姚 add end
    if (to != null) {
      String target = to[0];
      if ("tmall".equals(target)) {
        List<CCommodityHeader> list = catalogService.getCynchTmallInfo();
        if(list.size()<=0){
          addErrorMessage("tmall无可同期商品");
          return false;
        }
      } else if ("ec".equals(target)) {
        List<CCommodityHeader> eclist = catalogService.getCynchEcInfo();
        if(eclist==null||eclist.size()<=0){
          addErrorMessage("ec无可同期商品");
          return false;
        }
      }
      // 2014/05/02 京东WBS对应 ob_姚 add start
      else if ("jd".equals(target)) {
        List<CCommodityHeader> list = catalogService.getCynchJdInfo();
        if (list == null || list.size() <= 0) {
          addErrorMessage("京东无可同期商品");
          return false;
        }
      }
      // 2014/05/02 京东WBS对应 ob_姚 add end
    }
    
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String[] values = getBean().getCheckCommodityCodes();
    CommodityCynchroBean nextBean = getBean();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    //查询最后同期时间
    String ecTimeString = catalogService.getCychroTimeByType("0");
    String tmallTime = catalogService.getCychroTimeByType("1");
    String lastTime = catalogService.getCychroTimeByType("2");
    // 2014/05/02 京东WBS对应 ob_姚 add start
    String jdTime = catalogService.getCychroTimeByType("3");
    // 2014/05/02 京东WBS对应 ob_姚 add end
    //设置最后同期时间
    nextBean.setLastCynchroTime(lastTime);
    nextBean.setLastCynchroTimeEc(ecTimeString);
    nextBean.setLastCynchroTimeTmall(tmallTime);
    // 2014/05/02 京东WBS对应 ob_姚 add start
    nextBean.setLastCynchroTimeJd(jdTime);
    // 2014/05/02 京东WBS对应 ob_姚 add end
    
    //获取同步EC 还是同步TMALL数据的标志
    CyncroResult serviceResult = new CyncroResult();
    String[] to = getRequestParameter().getPathArgs();
    String target = to[0];
    if("ec".equals(target)){
      serviceResult = catalogService.executeCynchEcByCheckBox(values);
    }else if("tmall".equals(target)){
      serviceResult = catalogService.executeCynchTmallByCheckBox(values);
    }
    // 2014/05/02 京东WBS对应 ob_姚 add start
    else if("jd".equals(target)){
      serviceResult = catalogService.executeCynchJdByCheckBox(values);
    }
    // 2014/05/02 京东WBS对应 ob_姚 add end
    addInformationMessage("同期结束");
    if(serviceResult.hasError()){
      addWarningMessage("本次总同期条数为："+serviceResult.getTotalCount()+"条，成功："+serviceResult.getSeccessCount()+"条,失败："+serviceResult.getFailCount()+"条");
      int count = serviceResult.getErrorList().size()>20?20:serviceResult.getErrorList().size();
      for(int i = 0;i<count;i++){
        addErrorMessage("error : "+serviceResult.getErrorList().get(i).getErrorInfo());
      }
    }else{
      addInformationMessage("本次总同期条数为："+serviceResult.getTotalCount()+"条，成功："+serviceResult.getSeccessCount()+"条,失败："+serviceResult.getFailCount()+"条");
    }
    setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CommodityCynchroBean reqBean = (CommodityCynchroBean) getRequestBean();
    setDisplayControl(reqBean);
    if(reqBean.getEcIsNull()){
    }
    setMessage();
    setRequestBean(reqBean);
  }

  private void setMessage() {
    if (getRequestParameter().getPathArgs().length > 0) {
      if (getRequestParameter().getPathArgs()[0].equals("nodata")) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.catalog.CommodityCychroCychroAction.0")));
      }
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityCychroCychroAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3200004004";
  }       

  
}
