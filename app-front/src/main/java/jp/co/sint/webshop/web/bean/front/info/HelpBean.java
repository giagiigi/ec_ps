package jp.co.sint.webshop.web.bean.front.info;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2050510:ヘルプのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class HelpBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String filePath;
  private String PageTopic;
  
  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2050510";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.info.HelpBean.0");
  }

  
  /**
   * @return the filePath
   */
  public String getFilePath() {
    return filePath;
  }

  
  /**
   * @param filePath the filePath to set
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.info.HelpBean.1"), "/siteinfo/guide"));
    if (! StringUtil.isNullOrEmpty(this.PageTopic)) {
      topicPath.add(new NameValue(this.PageTopic, ""));
    }
    //topicPath.add(new NameValue(this.detailName, ""));
    return topicPath;
  }

  
  /**
   * @return the pageTopic
   */
  public String getPageTopic() {
    return PageTopic;
  }

  
  /**
   * @param pageTopic the pageTopic to set
   */
  public void setPageTopic(String pageTopic) {
    PageTopic = pageTopic;
  }
}
