package jp.co.sint.webshop.web.action.back.catalog;

import java.io.FileWriter;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.service.data.ContentsPath;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.TmallRelatedDescribeBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040110:商品マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallRelatedDescribeMoveAction extends WebBackAction<TmallRelatedDescribeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    WebshopConfig config = DIContainer.getWebshopConfig();
    TmallRelatedDescribeBean bean = getBean();
    bean.setDisplayPreview(true);
    String nextUrl = "/app/catalog/tmall_preview_describe";
    
    String previewUrl = WebUtil.getSecureUrl(config.getHostName(), config.getHttpsPort(), config.getBackContext()
        + nextUrl );

    bean.setPreviewUrl(previewUrl);

    String newUrl = "";
    ContentsPath path = DIContainer.get("contentsPath");
    newUrl = path.getContentsSharedPath();
    newUrl = newUrl.replaceAll("contents", "back") + "/catalog/tmall_preview_describe.jsp";
    
    String strStart = "<%@ include file=\"/WEB-INF/include/cache-control.jsp\" %>";
    strStart += "<%@ include file=\"/WEB-INF/include/dtd-def.jsp\" %>";
    strStart += "<%@ page contentType=\"text/html;charset=UTF-8\" pageEncoding=\"UTF-8\" %>";
    strStart += "<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>";
    strStart += "<%@ taglib prefix=\"wsx\" tagdir=\"/WEB-INF/tags\" %>";
    strStart += "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"${lang}\" lang=\"${lang}\">";
    strStart += "<head></head><body>";
    String strEnd = "</body></html>";
    
    String type = getRequestParameter().getPathArgs()[1];
    String htmlStr = "";
    if (type.equals("cn")) {
      htmlStr = strStart + bean.getDecribeCn() + strEnd;
    } else if (type.equals("en")) {
      htmlStr = strStart + bean.getDecribeEn() + strEnd;
    } else if (type.equals("jp")) {
      htmlStr = strStart + bean.getDecribeJp() + strEnd;
    } else if (type.equals("tmall")) {
      strStart = "<%@ page contentType=\"text/html;charset=UTF-8\" pageEncoding=\"UTF-8\" %>";
      strStart += "<!doctype html><html><head>";
      strStart += "<link href='http://a.tbcdn.cn/s/kissy/1.3.0/editor/theme/editor-iframe-min.css?t=1' rel='stylesheet' />";
      strStart += "<style>p{margin:1.12em 0;}body img{vertical-align:top;}.ke_anchor{visibility:hidden;width:1px;height:1px;vertical-align:text-top;overflow:hidden;display:inline-block;*display:inline;*zoom:1;}";
      strStart += "table.ke_show_border, table.ke_show_border > tr > td,  table.ke_show_border > tr > th, table.ke_show_border > tbody > tr > td,  table.ke_show_border > tbody > tr > th, table.ke_show_border > thead > tr > td,  table.ke_show_border > thead > tr > th, table.ke_show_border > tfoot > tr > td,  table.ke_show_border > tfoot > tr > th{border : #d3d3d3 1px dotted}";
      strStart += "</style></head><body class='ks-editor' >";
      htmlStr = strStart + bean.getDecribeTmall() + strEnd;
    }
    
    try {
      FileWriter writer  = new FileWriter(newUrl);
      writer.write(htmlStr);
      writer.flush();
      writer.close();
    
    } catch (Exception e) {
      addErrorMessage("预览过程中出现错误");
      return BackActionResult.RESULT_SUCCESS;
    }
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("描述预览");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104011004";
  }

}
