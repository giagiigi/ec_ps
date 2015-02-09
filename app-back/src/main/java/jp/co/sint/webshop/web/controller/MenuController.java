package jp.co.sint.webshop.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.menu.back.BackMenuController;
import jp.co.sint.webshop.web.menu.back.TabMenu;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;
import jp.co.sint.webshop.web.webutility.SessionContainer;

import org.apache.log4j.Logger;

/**
 * Menu遷移用Servlet
 * 
 * @author System Integrator Corp.
 */
public class MenuController extends HttpServlet {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /**
   * POST処理を実行します。
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      execute(request, response);
    } catch (Throwable e) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(Messages.log("web.controller.MenuController.0") + e.getMessage());
      logger.error(e);

      DateFormat pattern = new SimpleDateFormat("yyyyMMddHHmmssSS");
      String exceptionNo = pattern.format(DateUtil.getSysdate());
      request.setAttribute(WebFrameworkConstants.ATTRIBUTE_EXCEPTION, 
          Messages.getString("web.controller.MenuController.1") + exceptionNo);

      RequestDispatcher reqJSP = getServletContext().getRequestDispatcher("/common/error.jsp");

      // エラー画面に遷移(Forward)
      reqJSP.forward(request, response);
    }
  }

  /**
   * GET処理を実行します。
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      execute(request, response);
    } catch (Throwable e) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(Messages.log("web.controller.MenuController.0") + e.getMessage());
      logger.error(e);

      DateFormat pattern = new SimpleDateFormat("yyyyMMddHHmmssSS");
      String exceptionNo = pattern.format(DateUtil.getSysdate());
      request.setAttribute(WebFrameworkConstants.ATTRIBUTE_EXCEPTION, 
          Messages.log("web.controller.MenuController.1")+ exceptionNo);

      RequestDispatcher reqJSP = getServletContext().getRequestDispatcher("/common/error.jsp");

      // エラー画面に遷移(Forward)
      reqJSP.forward(request, response);
    }
  }

  /**
   * servlet実行処理
   * 
   * @param request
   * @param response
   */
  private void execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {

    Logger logger = Logger.getLogger(MenuController.class);
    logger.info(Messages.log("web.controller.MenuController.2") + request.getRequestURI());
    String pathInfo = request.getPathInfo();

    String packageId = getId(pathInfo, 1);
    String jpsId = getId(pathInfo, 2);

    WebshopConfig config = DIContainer.getWebshopConfig();
    String destination = WebUtil.getSecureUrl(config.getBackHostName(), config.getBackHttpsPort());

    // セッション情報を取得
    SessionContainer sessionContainer = new CommonSessionContainer(request);
    if (!sessionContainer.isValid()) {
      logger.error(Messages.log("web.controller.MenuController.3"));
      response.sendRedirect(destination + request.getContextPath() + "/app/common/login");
      return;
    }

    String nextUrl = "";
    if (pathInfo.endsWith("index")) {
      // タブメニュー押下時は遷移先をタブメニュー列挙より取得
      BackMenuController controller = new BackMenuController((LoginInfo) request.getSession().getAttribute(
          WebFrameworkConstants.ATTRIBUTE_LOGIN), (WebshopConfig) request.getSession().getServletContext().getAttribute(
          WebFrameworkConstants.ATTRIBUTE_SYSTEM));
      for (TabMenu menu : TabMenu.values()) {
        if (menu.getSubsystemName().equals(packageId)) {
          nextUrl = request.getContextPath() + controller.getIndexUrl(menu);
        }
      }
    } else {
      nextUrl = request.getContextPath() + "/app/" + packageId + "/" + jpsId;
    }

    CommonSessionContainer sesContainer = new CommonSessionContainer(request);
    sesContainer.clearBackTransitionInfoList();
    sesContainer.setTempBean(null);
    request.getSession().removeAttribute(WebFrameworkConstants.ATTRIBUTE_BEAN);

    response.sendRedirect(destination + nextUrl);
  }

  /**
   * PathInfoからIDを取得する。
   * 
   * @param pathInfo
   *          PathInfo
   * @param no
   *          何番目の情報を取得するか
   * @return ID
   */
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
}
