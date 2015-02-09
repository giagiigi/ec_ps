package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCsvExportBean;
import jp.co.sint.webshop.web.text.back.Messages;


/**
 * U1040710:入荷お知らせのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityCsvExportInitAction extends WebBackAction<CommodityCsvExportBean> {

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

    CommodityCsvExportBean bean = new CommodityCsvExportBean();
    bean.setSearchExportObject("0");
    bean.setSearchAppointExportObject("0");
    // 画面表示用Beanを次画面Beanを設定
    setRequestBean(bean);
    
    if (Permission.COMMODITY_DATA_IO.isGranted(getLoginInfo())) {
      bean.setDisplayCsvExportButton(true);
    }else {
      bean.setDisplayCsvExportButton(false);
    }
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    // URLパラメータより完了パラメータを取得
    String complete = "";
    if (getRequestParameter().getPathArgs().length > 0) {
      complete = getRequestParameter().getPathArgs()[0];
    }

    // 完了メッセージを設定
    setCompleteMessage(complete);

//    // 画面項目の表示/非表示を設定
//    StockListBean requestBean = (StockListBean) getRequestBean();
//    //if (getLoginInfo().isSite()) {
//      requestBean.setDisplayShopList(true);
    //}
//    if (Permission.COMMODITY_DELETE.isGranted(getLoginInfo())) {
//      requestBean.setDisplayDeleteButton(true);
//    }
//    if (Permission.COMMODITY_DATA_IO.isGranted(getLoginInfo())) {
//      for (StockListBean detail : requestBean.getList()) {
//        if (NumUtil.toLong(detail.getSubscriptionCount()) > 0) {
//          detail.setDisplayCsvExportButton(true);
//        }
//      }
//    }
    
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします <BR>
   * 処理完了パラメータがない場合は、何もメッセージを設定しません<BR>
   * 削除完了時：delete CSV出力完了時 : csv_export_complete<BR>
   * 
   * @param complete
   *          処理完了パラメータ
   */
  private void setCompleteMessage(String complete) {

//    if (complete.equals(WebConstantCode.COMPLETE_DELETE)) {
//      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
//          Messages.getString("web.action.back.catalog.ArrivalGoodsInitAction.0")));
//    } else if (complete.equals("export")) {
//      addInformationMessage(WebMessage.get(CompleteMessage.CSV_EXPORT_COMPLETE,
//          Messages.getString("web.action.back.catalog.ArrivalGoodsInitAction.0")));
//    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityCsvExportInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3180004002";
  }

}
