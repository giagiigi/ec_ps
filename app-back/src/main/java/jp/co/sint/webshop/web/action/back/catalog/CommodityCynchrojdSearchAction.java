package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CCommodityCynchro;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityHistorySearchCondition;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCynchroBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1040230:商品同期化action
 * 
 * @author OB
 */
public class CommodityCynchrojdSearchAction extends CommodityCynchroBaseAction {

  /**
   * 初期処理を実行します
   */
  public void init() {
    CommodityCynchroBean bean = new CommodityCynchroBean();
    this.setBean(bean);
    super.init();
  }

  CommodityHistorySearchCondition condition;

  /**
   * @return the condition
   */
  public CommodityHistorySearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  /**
   * @param condition
   *          the condition to set
   */
  public void setCondition(CommodityHistorySearchCondition condition) {
    this.condition = condition;
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
    String[] values = getRequestParameter().getAll("checkBox");
    String nextUrl = "/app/catalog";
    CommodityCynchroBean nextBean = getBean();
    getRequestParameter().getPathArgs();
    String[] to = getRequestParameter().getPathArgs();
    // 如果得到的参数不为空作页面跳转
    if (to != null && to.length > 0) {
      String string = to[0];
      if (string.equals("history")) {
        nextUrl += "/commodity_cynchrohi";
      } else if (string.equals("ec")) {
        nextUrl += "/commodity_cynchroec/Search";
      } else if (string.equals("mall")) {
        nextUrl += "/commodity_cynchromall/Search";
      } else if (string.equals("Cynchro")) {
        String type = to[1];
        if ("ec".equals(type)) {
          nextUrl += "/commodity_cynchro/Cynchro/ec";
        } else if ("tmall".equals(type)) {
          nextUrl += "/commodity_cynchro/Cynchro/tmall";
        } else {
          nextUrl += "/commodity_cynchro/Cynchro/jd";
        }
      }
      
      nextBean.setCheckCommodityCodes(values);
      setNextUrl(nextUrl);
      setRequestBean(nextBean);
      return BackActionResult.RESULT_SUCCESS;
    }
    // 検索条件の作成
    condition = new CommodityHistorySearchCondition();
    setCondition(condition);

    // ページング情報の追加
    condition = getCondition();

    // 検索条件のバリデーションチェック
    if (!validateBean(condition)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR, Messages
          .getString("web.action.back.catalog.CommodityListSearchAction.4")));
      return BackActionResult.RESULT_SUCCESS;
    }

    // nextBean生成
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    // 分页查询
    SearchResult<CCommodityCynchro> searchResult = catalogService.getCynchJdInfo(condition);
    // 検索結果0件チェック
    if (searchResult.getRows().isEmpty()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    }
    nextBean.setCcList(searchResult.getRows());
    String ecTimeString = catalogService.getCychroTimeByType("0");
    String tmallTime = catalogService.getCychroTimeByType("1");
    String lastTime = catalogService.getCychroTimeByType("2");
    String jdTime = catalogService.getCychroTimeByType("3");
    nextBean.setLastCynchroTime(lastTime);
    nextBean.setLastCynchroTimeEc(ecTimeString);
    nextBean.setLastCynchroTimeTmall(tmallTime);
    nextBean.setLastCynchroTimeJd(jdTime);
    String lastJdTime = "";
    if (!StringUtil.isNullOrEmpty(jdTime)) {
      lastJdTime = jdTime.substring(0, 4) + "/" + jdTime.substring(5, 7) + "/" + jdTime.substring(8, 10)
          + " " + jdTime.substring(11, 13) + ":" + jdTime.substring(14, 16) + ":" + jdTime.substring(17, 19);
    }
    
    for (CCommodityCynchro cccy : nextBean.getCcList()) {
      cccy.setCynchroTime(lastJdTime);// 最后同期时间
    }
    nextBean.setPagerValue(PagerUtil.createValue(searchResult));
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
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.back.catalog.CommodityCychroJdSearchAction.0")));
      }
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityCychroJdSearchAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3200004009";
  }

}
