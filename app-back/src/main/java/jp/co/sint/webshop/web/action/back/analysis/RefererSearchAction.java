package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.RefererSearchCondition;
import jp.co.sint.webshop.service.analysis.RefererSummary;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.RefererBean;
import jp.co.sint.webshop.web.bean.back.analysis.RefererBean.RefererBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

import org.apache.log4j.Logger;

/**
 * U1070130:リファラー集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class RefererSearchAction extends WebBackAction<RefererBean> {

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
    Logger logger = Logger.getLogger(this.getClass());

    RefererBean bean = getBean();

    UserAgentManager manager = DIContainer.getUserAgentManager();
    List<UserAgent> agents = manager.getUserAgentList();
    List<CodeAttribute> clientGroupList = new ArrayList<CodeAttribute>();
    clientGroupList.add(new NameValue(Messages.getString(
        "web.action.back.analysis.RefererSearchAction.0"), ""));
    for (UserAgent ua : agents) {
      clientGroupList.add(new NameValue(ua.getAgentName(), ua.getClientGroup()));
    }

    bean.setClientGroupList(clientGroupList);

    RefererSearchCondition condition = new RefererSearchCondition();
    condition.setSearchStartDate(DateUtil.fromString(bean.getSearchStartDate()));
    condition.setSearchEndDate(DateUtil.fromString(bean.getSearchEndDate()));
    condition.setClientGroup(bean.getClientGroupCondition());

    PagerUtil.createSearchCondition(getRequestParameter(), condition);

    List<ValidationResult> beanValidate = BeanValidator.validate(condition).getErrors();

    if (beanValidate.size() > 0) {
      for (ValidationResult r : beanValidate) {
        this.addErrorMessage(r.getFormedMessage());
      }
      bean.setSearchResultList(new ArrayList<RefererBeanDetail>());
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    try {
      AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());
      SearchResult<RefererSummary> result = service.getReferer(condition);
      bean.setPagerValue(PagerUtil.createValue(result));

      if (result.getRowCount() < 1) {
        this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
        bean.setSearchResultList(new ArrayList<RefererBeanDetail>());
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
      if (result.isOverflow()) {
        this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
            + result.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
      }

      long scale = NumUtil.toLong(bean.getScaleCondition());

      List<RefererSummary> list = result.getRows();
      List<RefererBeanDetail> resultList = new ArrayList<RefererBeanDetail>();
      for (RefererSummary r : list) {
        RefererBeanDetail detail = new RefererBeanDetail();
        detail.setRefererUrl(r.getRefererUrl());
        detail.setRefererCount(NumUtil.toString(r.getRefererCount()));

        // スケールが0のときは除算を行わない
        if (scale != 0L) {
          detail.setGraphCount(NumUtil.toString(r.getRefererCount() / scale));
          detail.setFraction(r.getRefererCount() % scale != 0);
        } else {
          detail.setGraphCount("0");
        }
        resultList.add(detail);
      }
      bean.setSearchResultList(resultList);
      setRequestBean(bean);
      setNextUrl(null);

    } catch (Exception e) {
      logger.error(e);
      return BackActionResult.SERVICE_ERROR;

    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    RefererBean bean = getBean();
    boolean result = false;

    if (validateBean(bean)) {
      if (StringUtil.isCorrectRange(bean.getSearchStartDate(), bean.getSearchEndDate())) {
        result = true;
      } else {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        result = false;
      }
    }
    return result;
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
    return Messages.getString("web.action.back.analysis.RefererSearchAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107013003";
  }

}
