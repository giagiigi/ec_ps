package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.analysis.DisplayScale;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.RefererBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070130:リファラー集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class RefererInitAction extends WebBackAction<RefererBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 運用モードに関係なく、サイト管理者の参照権限がなければ認証エラー
    return Permission.ANALYSIS_READ_SITE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    // InitActionでは必ず初期化する
    RefererBean bean = new RefererBean();

    Date yesterday = DateUtil.addDate(DateUtil.getSysdate(), -1);
    String yesterdayString = DateUtil.toDateString(yesterday);

    bean.setSearchStartDate(yesterdayString);
    bean.setSearchEndDate(yesterdayString);
    bean.setClientGroupCondition("");
    bean.setScaleCondition(DisplayScale.SCALE_100.getValue());

    UserAgentManager manager = DIContainer.getUserAgentManager();

    List<UserAgent> agents = manager.getUserAgentList();

    List<CodeAttribute> clientGroupList = new ArrayList<CodeAttribute>();
    clientGroupList.add(new NameValue(Messages.getString(
        "web.action.back.analysis.RefererInitAction.0"), ""));
    for (UserAgent ua : agents) {
      clientGroupList.add(new NameValue(ua.getAgentName(), ua.getClientGroup()));
    }

    bean.setClientGroupList(clientGroupList);
    setRequestBean(bean);
    setNextUrl(null);

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
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    // データ入出力権限が付与されていれば、CSV出力ボタンを表示する
    if (Permission.ANALYSIS_DATA_SITE.isGranted(login)) {
      RefererBean bean = (RefererBean) getRequestBean();
      bean.setExportAuthority(true);
      setRequestBean(bean);
    }

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.RefererInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107013002";
  }

}
