package jp.co.sint.webshop.web.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sint.webshop.service.data.ContentsPath;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.IOUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;
import java.io.File;

public class ContentsServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    execute(request, response);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    execute(request, response);
  }

  private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    InputStream in = null;
    OutputStream out = null;
    String contentUrl = "";
    try {
      // 遷移先ContentURLの取得
      contentUrl = getContentUrl(request);
      File file = new File(contentUrl);
      in = new FileInputStream(file);

      // inputStreamの展開
      out = response.getOutputStream();

      byte[] b = new byte[256];
      int len = -1;
      while ((len = in.read(b)) >= 0) {
        out.write(b, 0, len);
      }

    } catch (IOException e) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.info("Content is not found. \nSpecify url is " + contentUrl);
    } finally {
      IOUtil.close(in);
      IOUtil.flush(out);
      IOUtil.close(out);
    }
  }

  private String getContentUrl(HttpServletRequest request) {
    String pathInfo = request.getPathInfo();
    String returnUrl = "";

    if (pathInfo.length() > 0) {
      String[] params = pathInfo.split("/");

      for (String param : params) {
        if (StringUtil.hasValue(param)) {
          returnUrl += "/" + param;
        }
      }
      returnUrl += "/index.html";
    }

    ContentsPath path = DIContainer.get("contentsPath");

    return path.getContentsSharedPath() + returnUrl;
  }

}
