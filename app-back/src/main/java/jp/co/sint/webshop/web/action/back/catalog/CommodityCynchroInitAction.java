package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCynchroBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040110:商品同期化action
 * 
 * @author System Integrator Corp.
 */
public class CommodityCynchroInitAction extends CommodityCynchroBaseAction {

  /**
   * 初期処理を実行します
   */
  public void init() {
    CommodityCynchroBean bean = new CommodityCynchroBean();
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
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // nextBean生成
    CommodityCynchroBean nextBean = getBean();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    //查询最后同步时间，用于页面
    String ecTimeString = catalogService.getCychroTimeByType("0");
    String tmallTime = catalogService.getCychroTimeByType("1");
    String lastTime = catalogService.getCychroTimeByType("2");
    // 2014/05/02 京东WBS对应 ob_姚 add start
    String jdTime = catalogService.getCychroTimeByType("3");
    // 2014/05/02 京东WBS对应 ob_姚 add end
    nextBean.setLastCynchroTime(lastTime);
    nextBean.setLastCynchroTimeEc(ecTimeString);
    nextBean.setLastCynchroTimeTmall(tmallTime);
    // 2014/05/02 京东WBS对应 ob_姚 add start
    nextBean.setLastCynchroTimeJd(jdTime);
    // 2014/05/02 京东WBS对应 ob_姚 add end
    
    /**
     * 初始化页面时，需查询 ec和tmall是否还有未同步数据，
     * 如果有数据未同步：在页面显示同步多选框和查询同步数据按钮
     * 否则不显示
     */
    List<CCommodityHeader> eclist = catalogService.getCynchEcInfo();
    List<CCommodityHeader> tmalllist = catalogService.getCynchTmallInfo();
    // 2014/05/02 京东WBS对应 ob_姚 add start
    List<CCommodityHeader> jdlist = catalogService.getCynchJdInfo();
    // 2014/05/02 京东WBS对应 ob_姚 add end
    nextBean.setEcIsNull(eclist.size()==0);
    nextBean.setTmallIsNull(tmalllist.size()==0);
    // 2014/05/02 京东WBS对应 ob_姚 add start
    nextBean.setJdIsNull(jdlist.size()==0);
    // 2014/05/02 京东WBS对应 ob_姚 add end
    setCompleteMessage();
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
    setMessage();
    setRequestBean(reqBean);
  }

  private void setMessage() {
    if (getRequestParameter().getPathArgs().length > 0) {
      if (getRequestParameter().getPathArgs()[0].equals("nodata")) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.catalog.CommodityCychroInitAction.0")));
      }
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityCychroInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3200004001";//U1040230
  }       //3104071002

  /**
   * 同期完成
   * @param complete
   *          処理完了パラメータ
   */
  private void setCompleteMessage() {

    String[] params = getRequestParameter().getPathArgs();
    String complete = "";
    if (params.length > 0) {
      complete = params[0];
    } else {
      return;
    }
    if (CYNCRO.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.COMMODITY_CYNCRO_COMPLETE));
    } 
  }
  
  public static final String CYNCRO = "cyncro";
}
