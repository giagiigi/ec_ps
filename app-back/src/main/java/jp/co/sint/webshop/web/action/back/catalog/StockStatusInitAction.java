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
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040610:在庫状況設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class StockStatusInitAction extends StockStatusBaseAction {

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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // nextBean用のBeanを生成
    StockStatusBean reqBean = new StockStatusBean();

    reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    
    // 在庫状況一覧の取得
    List<StockStatusCount> resultList = getStockStatusList(reqBean);

    List<StockStatusDetailBean> list = new ArrayList<StockStatusDetailBean>();

    for (StockStatusCount ss : resultList) {
      StockStatusDetailBean stockStatusDetail = new StockStatusDetailBean();
      setResultStockStatusList(ss, stockStatusDetail);
      list.add(stockStatusDetail);
    }
    reqBean.setList(list);
    
    reqBean.getEdit().setShopCode(reqBean.getSearchShopCode());
    reqBean.getEdit().setStockStatusNo("");
    reqBean.getEdit().setStockStatusName("");
    reqBean.getEdit().setStockSufficientMessage("");
    reqBean.getEdit().setStockLittleMessage("");
    reqBean.getEdit().setOutOfStockMessage("");
    reqBean.getEdit().setStockSufficientThreshold(null);
    reqBean.getEdit().setUpdatedDatetime(null);

    // 遷移先Beanを設定
    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    // 完了メッセージを取得
    // parameter[0]:完了パラメータ
    String[] params = getRequestParameter().getPathArgs();
    String complete = "";
    if (params.length > 0) {
      complete = params[0];
    }
    StockStatusBean reqBean = (StockStatusBean) getRequestBean();
    reqBean.setMode("new");
    
    
    setDisplayControl(reqBean);
    // 完了メッセージのセット
    setCompleteMessage(complete);
    
    setRequestBean(reqBean);

  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします <BR>
   * 処理完了パラメータがない場合は、何もメッセージを設定しません<BR>
   * 登録完了時：insert 更新完了時：update 削除完了時：delete <BR>
   * 
   * @param complete
   *          処理完了パラメータ
   */
  private void setCompleteMessage(String complete) {

    if (WebConstantCode.COMPLETE_INSERT.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.catalog.StockStatusInitAction.0")));
    } else if (WebConstantCode.COMPLETE_UPDATE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
          Messages.getString("web.action.back.catalog.StockStatusInitAction.0")));
    } else if (WebConstantCode.COMPLETE_DELETE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.catalog.StockStatusInitAction.0")));
    }
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.StockStatusInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104061002";
  }

}
