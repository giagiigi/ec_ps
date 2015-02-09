package jp.co.sint.webshop.web.action.back.communication;
import jp.co.sint.webshop.service.communication.CampaignSearchCondition;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * 5106101004:活动一览のアクションクラスです
 * 
 * @author KS.
 */
public class NewCampaignListSearchBackAction extends NewCampaignListSearchAction {

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  protected CampaignSearchCondition getCondition() {
    return PagerUtil.createSearchConditionFromBean(getBean(), getSearchCondition());
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
      return "促销活动一览画面检索返回处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
      return "5106101004";
  }
}
