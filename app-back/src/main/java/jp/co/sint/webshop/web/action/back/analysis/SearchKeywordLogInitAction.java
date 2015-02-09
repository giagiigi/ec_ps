package jp.co.sint.webshop.web.action.back.analysis;

import java.util.Date;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.analysis.SearchKeywordLogBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070910:検索キーワード集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SearchKeywordLogInitAction extends SearchKeywordLogBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // モードに関係なく分析参照_サイトの権限を持つユーザのみ表示可能
    return Permission.ANALYSIS_READ_SITE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    SearchKeywordLogBean bean = new SearchKeywordLogBean();

    Date yesterday = DateUtil.addDate(DateUtil.getSysdate(), -1);
    String yesterdayString = DateUtil.toDateString(yesterday);

    bean.setSearchKeyList(createSearchKeyList());
    bean.setSearchStartDate(yesterdayString);
    bean.setSearchEndDate(yesterdayString);
    bean.setExportAuthority(hasExportAuthority());

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SearchKeywordLogInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107091002";
  }

}
