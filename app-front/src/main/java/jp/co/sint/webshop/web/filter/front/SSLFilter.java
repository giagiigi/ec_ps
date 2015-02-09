package jp.co.sint.webshop.web.filter.front;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.BackTransitionInfo;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * SSL通信用フィルター
 * 
 * @author System Integrator Corp.
 */
public class SSLFilter implements Filter {

  private String[] sslPagePath = new String[0];

  private String[] requirePagePath = new String[0];

  private String[] unwantedLoginPagePath = new String[0];

  /**
   * 初期処理を実行します。
   * 
   * @param config
   * @throws ServletException
   */
  public void init(FilterConfig config) throws ServletException {
    String sslPagePattern = config.getInitParameter("SSLPage");
    String requireLoginPagePattern = config.getInitParameter("RequireLoginPage");
    String unwantedLoginPagePattern = config.getInitParameter("UnwantedLoginPage");

    sslPagePath = sslPagePattern.split(";");
    requirePagePath = requireLoginPagePattern.split(";");
    unwantedLoginPagePath = unwantedLoginPagePattern.split(";");
  }

  /**
   * フィルタリングを実行します。
   * 
   * @param request
   * @param response
   * @param chain
   * @throws IOException
   * @throws ServletException
   */
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      // URLはコンテキストパスを取り除いたものとする
      String contextPath = httpRequest.getContextPath();
      String url = WebUtil.getRemovedContextUri(contextPath, httpRequest.getRequestURI());
      HttpSession session = httpRequest.getSession();

      boolean sslPage = false;
      for (String pagePath : sslPagePath) {
        if (url.startsWith(pagePath)) {
          sslPage = true;
        }
      }

      boolean requireLogin = false;
      for (String pagePath : requirePagePath) {
        if (url.startsWith(pagePath)) {
          requireLogin = true;
        }
      }

      // 戻るボタン対応(httpもhttpsも両方アクセスする為)
      if (url.equals("/app/common/util/back")) {
        chain.doFilter(request, response);
        return;
      }
      boolean secureRequest = isSecure(request);

      // SSL保護ページであればSSLへ
      // String nextUrl = "";
      String host = "";
      boolean isRedirect = false;
      WebshopConfig config = DIContainer.getWebshopConfig();
      if (sslPage) {
        if (!secureRequest) {
          host = WebUtil.getSecureUrl(config.getFrontHostName(), config.getFrontHttpsPort());
          isRedirect = true;
        }
      } else if (secureRequest) {
        host = WebUtil.getNormalUrl(config.getFrontHostName(), config.getFrontHttpPort());
        sessionRenew(httpRequest);
        isRedirect = true;
      }
      // 10.1.6 10251 追加 ここから
      if (!isOrderFlow(httpRequest)) {
        removeGuestLoginInfo(httpRequest);
      }
      // 10.1.6 10251 追加 ここまで

      // SSL保護ページへの遷移でなければ、ログイン後遷移URLを消去する
      if (!sslPage) {
        session.removeAttribute(WebFrameworkConstants.ATTRIBUTE_AFTER_LOGIN_URL);
      }

      LoginInfo loginInfo = (LoginInfo) httpRequest.getSession(true).getAttribute(WebFrameworkConstants.ATTRIBUTE_LOGIN);
      // ログイン必須ページかSSL保護かつ未ログインであればログイン画面に遷移
      // 但し、ログイン画面と顧客パッケージは未ログインでもアクセス可
      String nextUrl = "";
      boolean isRequiredLogin = requireLogin && (!secureRequest || isNotLogin(loginInfo));
      boolean isSslProtected = sslPage && isNotLogin(loginInfo) && !isNotLoginPage(url);
      if (isRequiredLogin || isSslProtected) {
        Object oBean = session.getAttribute(WebFrameworkConstants.ATTRIBUTE_BEAN);
        BackTransitionInfo afterLoginInfo = new BackTransitionInfo();
        if (oBean instanceof UIFrontBean) {
          UIFrontBean bean = (UIFrontBean) oBean;
          afterLoginInfo.setBean(bean);
        }
        afterLoginInfo.setUrl(url);

        session.setAttribute(WebFrameworkConstants.ATTRIBUTE_AFTER_LOGIN_URL, afterLoginInfo);

        nextUrl += (httpRequest.getContextPath() + "/app/common/login");
        // 受注系の場合ゲスト用ログインの為、必要パラメータをログイン遷移に付加する
        if (url.startsWith("/app/cart/cart/move/shipping")) {
          nextUrl += "/init/order";
          String[] param = url.split("/");
          for (int i = 6; i < param.length; i++) {
            nextUrl += ("/" + param[i]);
          }
        }
        isRedirect = true;
      } else {
        nextUrl = httpRequest.getContextPath() + url;
      }

      if (isRedirect && StringUtil.hasValue(nextUrl)) {
        String qs = httpRequest.getQueryString();
        if (StringUtil.hasValue(qs)) {
          nextUrl = nextUrl + "?" + WebUtil.replaceLineFeeds(qs, "");
        }
        // add m17n start
        nextUrl = nextUrl.replace("/app", "/" + httpRequest.getAttribute(WebFrameworkConstants.ATTRIBUTE_LANGUAGE) + "/app");
        // add m17n end
        httpResponse.sendRedirect(host + httpResponse.encodeRedirectURL(nextUrl));
        return;
      }
    }

    chain.doFilter(request, response);
  }

  private boolean isNotLoginPage(String url) {
    boolean result = false;
    for (String page : unwantedLoginPagePath) {
      if (url.startsWith(page)) {
        result = true;
      }
    }
    return result;
  }

  private boolean isNotLogin(LoginInfo login) {
    return login == null || login.isNotLogin();
  }

  // @SuppressWarnings("unchecked") // 10.1.5 K00192 削除
  private void sessionRenew(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    httpSession.removeAttribute(WebFrameworkConstants.ATTRIBUTE_BEAN);
    httpSession.removeAttribute(WebFrameworkConstants.ATTRIBUTE_TEMP_BEAN);

    // ゲストの場合ログイン情報と画面入力情報は全て破棄する
    FrontLoginInfo loginInfo = (FrontLoginInfo) request.getSession(true).getAttribute(WebFrameworkConstants.ATTRIBUTE_LOGIN);
    if (loginInfo != null && loginInfo.isGuest()) {
      httpSession.setAttribute(WebFrameworkConstants.ATTRIBUTE_LOGIN, WebLoginManager.createFrontNotLoginInfo());
    }

  }

  private boolean isSecure(ServletRequest request) {
    String secureAttribute = (String) request.getAttribute(WebFrameworkConstants.ATTRIBUTE_SECURE);
    return StringUtil.hasValue(secureAttribute) && secureAttribute.equalsIgnoreCase(WebConstantCode.VALUE_TRUE);
  }

  /**
   * 終了処理を実行します。
   */
  public void destroy() {

  }

  // 10.1.6 10251 追加 ここから
  /**
   * セッションからゲストログイン情報を破棄します。
   */
  private void removeGuestLoginInfo(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    FrontLoginInfo loginInfo = (FrontLoginInfo) session.getAttribute(WebFrameworkConstants.ATTRIBUTE_LOGIN);
    if (loginInfo != null && loginInfo.isGuest()) {
      session.setAttribute(WebFrameworkConstants.ATTRIBUTE_LOGIN, WebLoginManager.createFrontNotLoginInfo());
    }
  }
  
  /**
   * リクエストが 受注フローの中であるかどうかを判定します。
   */
  private boolean isOrderFlow(HttpServletRequest httpRequest) {
    String url = WebUtil.getRemovedContextUri(httpRequest.getContextPath(), httpRequest.getRequestURI());
    return url.startsWith("/app/order");
  }
  // 10.1.6 10251 追加 ここまで
}
