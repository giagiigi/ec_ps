package jp.co.sint.webshop.web.filter.back;

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
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.webutility.BackTransitionInfo;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * SSL通信用フィルター
 * 
 * @author System Integrator Corp.
 */
public class SSLFilter implements Filter {

  /**
   * 初期処理を実行します。
   * 
   * @param config
   * @throws ServletException
   */
  public void init(FilterConfig config) throws ServletException {
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
      Object obj = session.getAttribute(WebFrameworkConstants.ATTRIBUTE_LOGIN);
      BackLoginInfo login = null;
      if (obj != null && obj instanceof BackLoginInfo) {
        login = (BackLoginInfo) obj;
      }

      // 非SSLページにアクセスした場合強制でSSLにredirectする
      boolean isRedirect = false;
      if (!isSecure(request)) {
        isRedirect = true;
      }

      String nextUrl = "";
      // 未ログインかつログイン外であればログイン画面に遷移
      // 管理側が未ログイン状態でアクセスできる画面はログイン画面のみとする
      if ((login == null || login.isNotLogin()) && !url.startsWith("/app/common/login") && !url.contains("error")
          && url.startsWith("/app")) {
        UIBackBean bean = (UIBackBean) session.getAttribute(WebFrameworkConstants.ATTRIBUTE_BEAN);

        BackTransitionInfo afterLoginInfo = new BackTransitionInfo();
        afterLoginInfo.setBean(bean);
        // 途中のアクションに遷移しようとしていた場合indexに強制変換する
        String actionId = getActionId(url);
        if (actionId.length() > 0 && !actionId.equals("index") && !actionId.equals("init")) {
          url = "/app/" + getPackageId(url) + "/" + getJspId(url) + "/";
        }
        afterLoginInfo.setUrl(url);

        if (getPackageId(url).equals("common")) {
          session.setAttribute(WebFrameworkConstants.ATTRIBUTE_AFTER_LOGIN_URL, null);
        } else {
          session.setAttribute(WebFrameworkConstants.ATTRIBUTE_AFTER_LOGIN_URL, afterLoginInfo);
        }

        nextUrl = httpRequest.getContextPath() + "/app/common/login";

        isRedirect = true;
      } else {
        nextUrl = httpRequest.getContextPath() + url;
      }

      if (isRedirect) {
        WebshopConfig config = DIContainer.getWebshopConfig();
        String destination = WebUtil.getSecureUrl(config.getBackHostName(), config.getBackHttpsPort());
        nextUrl = destination + nextUrl;
        nextUrl = httpResponse.encodeRedirectURL(nextUrl);
        httpResponse.sendRedirect(nextUrl);
        return;
      }
    }

    chain.doFilter(request, response);
  }

  private String getPackageId(String pathInfo) {
    // 1つ目はPackageId
    return getId(pathInfo, 2);
  }

  private String getJspId(String pathInfo) {
    // 2つ目はJspId
    return getId(pathInfo, 3);
  }

  private String getActionId(String pathInfo) {
    // 3つ目はActionID
    return getId(pathInfo, 4);
  }

  private String getId(String pathInfo, int no) {
    String id = "";
    if (pathInfo.length() > 0) {
      String[] params = pathInfo.split("/");
      if (params.length > no) {
        id = params[no];
      }
    }
    return id;
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

}
