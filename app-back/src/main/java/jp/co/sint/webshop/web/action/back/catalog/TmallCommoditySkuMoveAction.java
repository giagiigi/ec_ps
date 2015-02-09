package jp.co.sint.webshop.web.action.back.catalog;


import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommoditySkuMoveAction extends TmallCommoditySkuBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
//    validationResult = super.validate();
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String[] args = getRequestParameter().getPathArgs();
    String nextUrl = "/app/catalog";
    if(args!=null&&args.length>0){
      String target = args[0];
      if("list".equals(target)){
        setNextUrl("/app/catalog/tmall_commodity_list");
      }else{
        nextUrl = nextUrl + "/tmall_commodity_edit/select/" + args[0] + "/" + args[1]+"/edit";
        // 遷移元情報をセッションに保存
        //DisplayTransition.add(getBean(),nextUrl,getSessionContainer());
        setNextUrl(nextUrl);
      }
      return BackActionResult.RESULT_SUCCESS;
    }
    
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "商品明细编辑";
    //return Messages.getString("web.action.back.catalog.CommodityEditConfirmAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104024003";
  }

}
