package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CSynchistory;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityHistorySearchCondition;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCynchrohiBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCynchrohiBean.ResultHistoryInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1040230:商品同期化action
 * 
 * @author System Integrator Corp.
 */
public class CommodityCynchrohiSearchAction extends WebBackAction<CommodityCynchrohiBean> {

  /**
   * 初期処理を実行します
   */
  public void init() {
  }

  CommodityHistorySearchCondition condition;

  
  /**
   * @return the condition
   */
  public CommodityHistorySearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  
  /**
   * @param condition the condition to set
   */
  public void setCondition(CommodityHistorySearchCondition condition) {
    condition.setSearchCynchroStartTime(getBean().getSearchCynchroStartTime());
    condition.setSearchCynchroEndTime(getBean().getSearchCynchroEndTime());
    condition.setQueryType(getBean().getSearchHistoryType());
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
    boolean isValid = true;
    
    // 検索条件のvalidationチェック
    isValid &= validateBean(getBean());
    // 同期開始日時の前後チェック
    if (StringUtil.hasValueAllOf(getBean().getSearchCynchroStartTime(), getBean().getSearchCynchroEndTime())) {
      if (!ValidatorUtil.isCorrectOrder(getBean().getSearchCynchroStartTime(), getBean().getSearchCynchroEndTime())) {
        isValid = false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, Messages
            .getString("web.action.back.catalog.CommodityListSearchAction.1")));
      }
    }
    return isValid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String nextUrl = "/app/catalog";
    CommodityCynchrohiBean nextBean = getBean();
    getRequestParameter().getPathArgs();
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0) {
      nextUrl += "/commodity_cynchro";
      setNextUrl(nextUrl);
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
    SearchResult<CSynchistory> searchResult = catalogService.getCynchroHiSearchResult(condition);
    
    // 検索結果0件チェック
    if (searchResult.getRows().isEmpty()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    }

    // オーバーフローチェック
    if (searchResult.isOverflow()) {
      this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
          + searchResult.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }
    
    List<CSynchistory> list = searchResult.getRows();
    List<ResultHistoryInfo> rlist = new ArrayList<ResultHistoryInfo>();
    Collections.sort(list);
    // 处理集合中的同期时间和 同期处理时间
    for (CSynchistory c : list) {
      DateFormat pattern1 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
      ResultHistoryInfo info = nextBean.new ResultHistoryInfo();
      info.setCreatedUser(c.getCreatedUser());
      info.setFailureCount(c.getFailureCount());
      info.setSuccessCount(c.getSuccessCount());
      info.setSyncCode(c.getSyncCode());
      info.setSyncUser(c.getSyncUser());
      info.setTotalCount(c.getTotalCount());
      // 页面显示的同期时间为 同期开始时间
      info.setSyncTime(pattern1.format(c.getSyncStarttime()));
      // 计算同期处理时间
      Date start = c.getSyncStarttime();
      Date end = c.getSyncEndtime();
      BigDecimal d = new BigDecimal(end.getTime() - start.getTime());
      // 将日期单位由毫秒转换为秒 并且保留两位小数
      double value = d.divide(new BigDecimal(1000), 4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
      // 格式化同期处理时间 00天00时00分00秒 其中如果只有秒那么前面的单位将不显示
      StringBuffer sb = new StringBuffer("");
      formatDateTommSS(value, sb);
      info.setUseTime(sb.toString());
      rlist.add(info);
    }
    nextBean.setList(rlist);
    nextBean.setPagerValue(PagerUtil.createValue(searchResult));
    setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }
  /**
   * 格式化日期字符串
   * @param value：日期double值 
   * @param sb: 日期字符串（00天00时00分00秒）
   */
  private void formatDateTommSS(double value,StringBuffer sb){
    if (value >= 3600 * 24) {
      String va = String.valueOf(value / 86400);
      va = va.length() == 1 ? "0" + va : va;
      sb.append(buildTwoBit(va.substring(0, va.indexOf("."))) + "天");
      formatDateTommSS(value%86400, sb);
    } else if (value >= 3600) {
      String va = String.valueOf(value / 3600);
      va = va.length() == 1 ? "0" + va : va;
      sb.append(buildTwoBit(va.substring(0, va.indexOf("."))) + "时");
      formatDateTommSS(value%3600, sb);
    } else if (value >= 60) {
      String va = String.valueOf(value / 60);
      va = va.length() == 1 ? "0" + va : va;
      sb.append(buildTwoBit(va.substring(0, va.indexOf("."))) + "分");
      formatDateTommSS(value % 60, sb);
    } else {
      sb.append(buildTwoBit(String.valueOf(value > 1 ? Math.round(value) : "1")));
      sb.append("秒");
      return;
    }
    
  }
  /**
   * 如果日期值不足两位补足两位 在前面加0
   * @param 时间值 String
   * @return 
   */
  private String buildTwoBit(String value){
    if (value == null) {
      return "00";
    }
    if (value.length() <= 1) {
      return "0"+value;
    }
    return value;
  }
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CommodityCynchrohiBean reqBean = (CommodityCynchrohiBean) getRequestBean();
    //setDisplayControl(reqBean);
    setMessage();
    setRequestBean(reqBean);
  }

  private void setMessage() {
    if (getRequestParameter().getPathArgs().length > 0) {
      if (getRequestParameter().getPathArgs()[0].equals("nodata")) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.catalog.CommodityCychroHistorySearchAction.0")));
      }
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityCychroHistorySearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3200004006";
  }       

}
