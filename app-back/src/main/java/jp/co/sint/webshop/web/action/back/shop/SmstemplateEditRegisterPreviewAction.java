package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.web.bean.back.shop.SmstemplateEditBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * メールテンプレートプレビュー処理<BR>
 * 項目タグにEnumで指定されたDummy情報を設定し<BR>
 * 構造タグを展開してプレビュー用HTMLを生成する<BR>
 * U1051110:メールテンプレートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SmstemplateEditRegisterPreviewAction extends SmstemplateEditPreviewAction {

  @Override
  public void prerender() {
    SmstemplateEditBean bean = (SmstemplateEditBean) getRequestBean();
    bean.setDisplayPreviewRegisterButton(true);
    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.SmstemplateEditRegisterPreviewAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105111006";
  }

}
