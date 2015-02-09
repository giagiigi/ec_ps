package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.StockStatusCount;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.StockStatusBean;
import jp.co.sint.webshop.web.bean.back.catalog.StockStatusBean.StockStatusDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040610:在庫状況設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class StockStatusSearchAction extends StockStatusBaseAction {

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
    return validateBean(getBean());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    // nextBean用のBeanを生成
    StockStatusBean reqBean = getBean();
    
    reqBean.getList().clear();

    // 在庫状況一覧の取得
    List<StockStatusCount> resultList = getStockStatusList(reqBean);
    if (resultList.isEmpty()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    }

    List<StockStatusDetailBean> list = new ArrayList<StockStatusDetailBean>();    
    for (StockStatusCount ss : resultList) {
      StockStatusDetailBean stockStatusDetail = new StockStatusDetailBean();
      setResultStockStatusList(ss, stockStatusDetail);
      list.add(stockStatusDetail);
    }
    reqBean.setList(list);

    // 遷移先Beanを設定
    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    StockStatusBean reqBean = (StockStatusBean) getRequestBean();
    reqBean.setMode("new");
        
    setDisplayControl(reqBean);
    
    setRequestBean(reqBean);

  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.StockStatusSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104061004";
  }

}
