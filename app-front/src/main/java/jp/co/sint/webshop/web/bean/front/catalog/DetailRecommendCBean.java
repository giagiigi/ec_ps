package jp.co.sint.webshop.web.bean.front.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040510:商品詳細のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class DetailRecommendCBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String recommendCount;
  
  private int maxLineCount;

  private List<List<DetailRecommendBaseBean>> pageList = new ArrayList<List<DetailRecommendBaseBean>>();

  private List<DetailRecommendBaseBean> list = new ArrayList<DetailRecommendBaseBean>();

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<DetailRecommendBaseBean> getList() {
    return list;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<DetailRecommendBaseBean> list) {
    this.list = list;
  }

  /**
   * pageListを取得します。
   * 
   * @return pageList
   */
  public List<List<DetailRecommendBaseBean>> getPageList() {
    return pageList;
  }

  /**
   * pageListを設定します。
   * 
   * @param pageList
   *          pageList
   */
  public void setPageList(List<List<DetailRecommendBaseBean>> pageList) {
    this.pageList = pageList;
  }

  /**
   * recommendCountを取得します。
   * 
   * @return recommendCount
   */
  public String getRecommendCount() {
    return recommendCount;
  }

  /**
   * recommendCountを設定します。
   * 
   * @param recommendCount
   *          recommendCount
   */
  public void setRecommendCount(String recommendCount) {
    this.recommendCount = recommendCount;
  }

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
    return "U2040512";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.DetailRecommendCBean.0");
  }

  
  /**
   * maxLineCountを取得します。
   *
   * @return maxLineCount
   */
  public int getMaxLineCount() {
    return maxLineCount;
  }

  
  /**
   * maxLineCountを設定します。
   *
   * @param maxLineCount 
   *          maxLineCount
   */
  public void setMaxLineCount(int maxLineCount) {
    this.maxLineCount = maxLineCount;
  }

}
