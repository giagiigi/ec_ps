package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.AccessLogBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070110:アクセスログ集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class AccessLogMoveAction extends WebBackAction<AccessLogBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // モードに関係なく分析参照_サイトの権限を持つユーザのみがアクセス可能
    return Permission.ANALYSIS_READ_SITE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    AccessLogBean searchBean = getBean();
    AccessLogBean bean = new AccessLogBean();

    String today = DateUtil.getSysdateString();
    String[] todayToken = today.split("/");

    bean.setSearchYear(todayToken[0]);
    bean.setSearchMonth(todayToken[1]);
    bean.setSearchDay(todayToken[2]);
    // 分析データ出力_サイトの権限を持つユーザのみがCSV出力可能
    bean.setExportAuthority(Permission.ANALYSIS_DATA_SITE.isGranted(getLoginInfo()));
    bean.setDisplayMode(searchBean.getDisplayMode());
    bean.setSearchResultDisplay(false);

    // クライアントグループリスト
    UserAgentManager manager = DIContainer.getUserAgentManager();
    List<CodeAttribute> clientList = new ArrayList<CodeAttribute>();
    clientList.add(new NameValue(Messages.getString(
        "web.action.back.analysis.AccessLogMoveAction.0"), ""));
    for (UserAgent ua : manager.getUserAgentList()) {
      clientList.add(new NameValue(ua.getAgentName(), ua.getClientGroup()));
    }
    bean.setClientGroupList(clientList);

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
    return Messages.getString("web.action.back.analysis.AccessLogMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107011003";
  }

}
