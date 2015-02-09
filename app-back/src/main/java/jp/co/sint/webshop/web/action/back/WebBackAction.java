package jp.co.sint.webshop.web.action.back;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.UserAccessLog;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebMainAction;
import jp.co.sint.webshop.web.bean.UIMainBean;
import jp.co.sint.webshop.web.log.back.AccessLogUtil;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;

import org.apache.log4j.Logger;

/**
 * 管理側Actionの親クラス
 * 
 * @author System Integrator Corp.
 * @param <T>
 *          Actionで使用するBeanの型
 */
public abstract class WebBackAction<T extends UIMainBean> extends WebMainAction {

  /**
   * アクション実行後、アクセスログの出力を行います。
   */
  public final void execute() {
    super.execute();
    accessLogOut();
  }

  /**
   * 管理側アクセスログ出力用処理
   */
  private void accessLogOut() {
    if (isAccessLogOut() && AccessLogUtil.isLogOut(getOperationCode())) {
      Logger logger = Logger.getLogger(this.getClass());
      try {
        UserAccessLog accessLog = new UserAccessLog();
        BackLoginInfo login = getLoginInfo();
        accessLog.setUserCode(Long.parseLong(login.getUserCode()));
        accessLog.setShopCode(login.getShopCode());
        accessLog.setUserName(login.getName());
        accessLog.setOperationCode(getOperationCode());
        accessLog.setAccessDatetime(DateUtil.getSysdate());
        accessLog.setIpAddress(getSessionContainer().getIpAddress());
        SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
        ServiceResult result = service.insertUserAccessLog(accessLog);
        if (result.hasError()) {
          logger.debug("AccessLog登録に失敗しました。");
        }
      } catch (Exception e) {
        logger.debug("AccessLog登録に失敗しました。", e);
      }
    }
  }

  /**
   * ログイン情報を取得します。
   * 
   * @return ログイン情報
   */
  public BackLoginInfo getLoginInfo() {
    LoginInfo loginInfo = getSessionContainer().getLoginInfo();
    BackLoginInfo backLoginInfo = null;

    if (loginInfo == null) {
      backLoginInfo = WebLoginManager.createBackNotLoginInfo();
    } else {
      if (loginInfo instanceof BackLoginInfo) {
        backLoginInfo = (BackLoginInfo) loginInfo;
      } else {
        backLoginInfo = WebLoginManager.createBackNotLoginInfo();
      }
    }
    return backLoginInfo;
  }

  /**
   * 呼び出し元JSPのBeanを取得します。
   * 
   * @return 呼び出し元JSPのBean
   */
  @SuppressWarnings("unchecked")
  public T getBean() {
    T bean = (T) super.getBean();
    return bean;
  }

  /**
   * アクセスログ出力の有無を返します。
   * 
   * @return アクセスログ出力の有無 true-出力する false-出力しない
   */
  @Override
  public boolean isAccessLogOut() {
    boolean auth = false;
    if (getSessionContainer().isValid()) {
      BackLoginInfo login = getLoginInfo();
      if (login.isLogin()) {
        auth = true;
      } else {
        auth = false;
      }
    } else {
      auth = false;
    }
    return auth;
  }

  public String getOperationCode() {
    return "";
  }

  public String getActionName() {
    return "";
  }

  /**
   * 検索結果が異常値(ゼロ件またはオーバーフロー)のときに、警告メッセージを設定します。
   * 
   * @param <E>
   *          SearchResultで保持する検索結果要素の型
   * @param result
   *          検索結果
   */
  protected <E>void prepareSearchWarnings(SearchResult<E> result) {
    prepareSearchWarnings(result, SearchWarningType.BOTH);
  }

  protected <E>void prepareSearchWarnings(SearchResult<E> result, SearchWarningType searchWarningType) {
    switch (searchWarningType) {
      case EMPTY:
        setEmptyResultMessage(result);
        break;
      case OVERFLOW:
        setOverflowResultMessage(result);
        break;
      case BOTH:
        setEmptyResultMessage(result);
        setOverflowResultMessage(result);
        break;
      default:
        break;
    }

  }

  private <E>void setEmptyResultMessage(SearchResult<E> result) {
    if (result.isEmpty()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    }
  }

  private <E>void setOverflowResultMessage(SearchResult<E> result) {
    if (result.isOverflow()) {
      this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
          + result.getRowCount()), "" + NumUtil.formatNumber("" + result.getMaxFetchSize())));
    }
  }

  public static enum SearchWarningType {
    EMPTY, OVERFLOW, BOTH
  }
}
