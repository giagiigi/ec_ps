package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.SearchKeywordLogBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.analysis.AnalysisErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070910:検索キーワード集計の基底クラスです。
 * 
 * @author System Integrator Corp.
 */
public abstract class SearchKeywordLogBaseAction extends WebBackAction<SearchKeywordLogBean> {

  /**
   * 検索キーの一覧を取得します。
   * 
   * @return 検索キーのリスト
   */
  public List<CodeAttribute> createSearchKeyList() {
    List<CodeAttribute> list = new ArrayList<CodeAttribute>();

    AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());

    List<String> searchKeysList = service.getSearchKey();

    if (searchKeysList.isEmpty()) {
      addErrorMessage(WebMessage.get(AnalysisErrorMessage.NO_SUMMARY_DATA_ERROR,
          Messages.getString("web.action.back.analysis.SearchKeywordLogBaseAction.0")));
    }

    list.add(new NameValue(Messages.getString(
        "web.action.back.analysis.SearchKeywordLogBaseAction.1"), ""));
    for (String s : searchKeysList) {
      list.add(new NameValue(s, s));
    }
    return list;
  }

  /**
   * データ入出力権限を持つかどうか判定します
   * 
   * @return データ入出力権限があればtrue
   */
  public boolean hasExportAuthority() {
    BackLoginInfo login = getLoginInfo();
    boolean auth = false;

    if (Permission.ANALYSIS_DATA_SHOP.isGranted(login) && !getConfig().isOne()) {
      // 分析データ_ショップの権限を持つユーザは一店舗モードでないときのみ権限を持つ
      auth = true;
    } else if (Permission.ANALYSIS_DATA_SITE.isGranted(login)) {
      // 分析データ_サイトの権限を持つユーザは常に権限あり
      auth = true;
    }

    return auth;
  }
}
