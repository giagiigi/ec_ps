package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.communication.PrivateCouponListSearchCondition;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060310:PRIVATEクーポン管理のアクションクラスです
 * 
 * @author OB.
 */
public class PrivateCouponListSearchBackAction extends PrivateCouponListSearchAction {

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  protected PrivateCouponListSearchCondition getCondition() {
    return PagerUtil.createSearchConditionFromBean(getBean(), getSearchCondition());
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "返回顾客别优惠券发行规则检索返回处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106061005";
  }

}
