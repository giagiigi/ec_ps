package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.communication.GiftCardRuleListSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.GiftCardRuleExportCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030110:顧客マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GiftCardRuleEditExportAction extends GiftCardRuleEditBaseAction implements WebExportAction {

  public boolean authorize() {
    if(super.authorize()){
     if (Permission.GIFT_CARD_RULE_IO.isGranted(getLoginInfo()) == false) {
        return false;
      }
    }
    return true;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length != 2) {
      throw new URLNotFoundException();
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String[] parameter = getRequestParameter().getPathArgs();
    GiftCardRuleEditBean searchBean = getBean();
    String searchCardCode = parameter[0];
    String cardHistoryNo = parameter[1];

    // 検索結果リストを取得
    GiftCardRuleListSearchCondition searchCondition = new GiftCardRuleListSearchCondition();
    searchCondition.setSearchCardCode(searchCardCode);
    searchCondition.setCardHistoryNo(cardHistoryNo);
    searchCondition.setUpdateUser(getLoginInfo().getLoginId());

    GiftCardRuleExportCondition condition = CsvExportType.EXPORT_CSV_GIFT_CART_RULE.createConditionInstance();
    condition.setSearchCondition(searchCondition);
    this.exportCondition = condition;

    setNextUrl("/download");
    setRequestBean(searchBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  private CsvExportCondition<? extends CsvSchema> exportCondition;

  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return exportCondition;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.GiftCardRuleEditExportAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106141002";
  }

}
