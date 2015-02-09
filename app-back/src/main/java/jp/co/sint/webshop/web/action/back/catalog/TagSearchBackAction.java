package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.catalog.TagSearchCondition;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1040410:タグマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TagSearchBackAction extends TagSearchAction {

  /**
  * beanのcreateAttributeを実行するかどうかの設定
  * 
  * @return true - 実行する false-実行しない
  */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  protected TagSearchCondition getCondition() {
    return PagerUtil.createSearchConditionFromBean(getBean(), getSearchCondition());
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.TagSearchBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104041007";
  }

}
