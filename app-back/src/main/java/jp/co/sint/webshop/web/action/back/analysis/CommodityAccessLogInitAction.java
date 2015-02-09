package jp.co.sint.webshop.web.action.back.analysis;

import java.util.Date;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.analysis.DisplayScale;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.analysis.CommodityAccessLogBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1070210:商品別アクセスログ集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityAccessLogInitAction extends CommodityAccessLogBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    // 分析参照_ショップの権限を持つユーザは一店舗モードでないときのみ参照可能
    // 分析参照_サイトの権限を持つユーザは常に参照可能
    return (Permission.ANALYSIS_READ_SHOP.isGranted(login) && !getConfig().isOne())
        || Permission.ANALYSIS_READ_SITE.isGranted(login);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommodityAccessLogBean bean = new CommodityAccessLogBean();

    Date yesterday = DateUtil.addDate(DateUtil.getSysdate(), -1);
    String yesterdayString = DateUtil.toDateString(yesterday);

    bean.setSearchStartDate(yesterdayString);
    bean.setSearchEndDate(yesterdayString);
    bean.setShopCodeList(createShopList());
    // 一店舗版のときあるいはショップ管理者のときは自ショップのみを表示する
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      bean.setShopCodeCondition(getLoginInfo().getShopCode());
      bean.setShopCodeDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
    } else {
      bean.setShopCodeDisplayMode(WebConstantCode.DISPLAY_EDIT);
    }
    bean.setClientGroupList(createClientGroupList());
    bean.setClientGroupCondition("");
    bean.setScaleCondition(DisplayScale.SCALE_100.getValue());
    bean.setExportAuthority(hasExportAuthority());

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.CommodityAccessLogInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107021002";
  }

}
