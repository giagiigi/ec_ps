package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.service.analysis.GiftCardUseLogSearchCondition;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1020210
 * 
 * @author System Integrator Corp.
 */
public class GiftCardUseLogSearchBackAction extends GiftCardUseLogSearchAction {

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  protected GiftCardUseLogSearchCondition getCondition() {
    return PagerUtil.createSearchConditionFromBean(getBean(), getSearchCondition());
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("礼品卡明细统计");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021007";
  }


}
