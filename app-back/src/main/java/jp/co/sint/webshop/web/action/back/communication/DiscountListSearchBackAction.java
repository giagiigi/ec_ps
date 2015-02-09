package jp.co.sint.webshop.web.action.back.communication;
import jp.co.sint.webshop.service.communication.DiscountListSearchCondition;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1061210:限时限量折扣一览画面返回处理
 * 
 * @author KS.
 */
public class DiscountListSearchBackAction extends DiscountListSearchAction {

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  protected DiscountListSearchCondition getCondition() {
    return PagerUtil.createSearchConditionFromBean(getBean(), getSearchCondition());
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
      return Messages.getString("web.bean.back.communication.DiscountListSearchBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
      return "5106121004";
  }
}
