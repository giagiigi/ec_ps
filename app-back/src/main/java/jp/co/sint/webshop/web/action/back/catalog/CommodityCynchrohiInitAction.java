package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.CommodityHistorySearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCynchrohiBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1040230:商品同期化action
 * 
 * @author System Integrator Corp.
 */
public class CommodityCynchrohiInitAction extends WebBackAction<CommodityCynchrohiBean> {

  /**
   * 初期処理を実行します
   */
  public void init() {
    CommodityCynchrohiBean bean = new CommodityCynchrohiBean();
    this.setBean(bean);
    super.init();
  }

  CommodityHistorySearchCondition condition;

  
  /**
   * @return the condition
   */
  public CommodityHistorySearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  
  /**
   * @param condition the condition to set
   */
  public void setCondition(CommodityHistorySearchCondition condition) {
    this.condition = condition;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean isValid = true;
    
    return isValid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String nextUrl = "/app/catalog/";
    CommodityCynchrohiBean nextBean = new CommodityCynchrohiBean();
    getRequestParameter().getPathArgs();
    if(getRequestParameter().getPathArgs()!=null&&getRequestParameter().getPathArgs().length>0){
      nextUrl+="/commodity_cynchro";
      setNextUrl(nextUrl);
      return BackActionResult.RESULT_SUCCESS; 
    }
    setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }
  
  
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    setMessage();
  }

  private void setMessage() {
    if (getRequestParameter().getPathArgs().length > 0) {
      if (getRequestParameter().getPathArgs()[0].equals("nodata")) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.catalog.CommodityCychroHistorySearchAction.0")));
      }
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityCychroHistorySearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3200004005";
  }       

}
