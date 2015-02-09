package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.analysis.CountType;
import jp.co.sint.webshop.service.analysis.NewPublicCouponDetailsCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.NewPublicCouponDetailsExportCondition;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.NewPublicCouponBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070820:ショップ別売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NewPublicCouponDetailsExportAction extends WebBackAction<NewPublicCouponBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    return Permission.ANALYSIS_DATA_SITE.isGranted(login) || Permission.ANALYSIS_DATA_SHOP.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length < 3) {
      return false;
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
    BackLoginInfo login = getLoginInfo();
    NewPublicCouponBean bean = getBean();

    String shopCode = bean.getShopCode();
    String[] tmp = getRequestParameter().getPathArgs();
    if (login.isShop()) {
      // 自ショップのショップコードでなかったら
      // 自ショップのショップコードにする
      if (!shopCode.equals(login.getShopCode())) {
        shopCode = login.getShopCode();
      }
    }

    NewPublicCouponDetailsCondition searchCondition = new NewPublicCouponDetailsCondition();
    
    if (!StringUtil.isNullOrEmpty(tmp[0])) {
      searchCondition.setSearchOrderDate(tmp[0]);
    }
    if (!StringUtil.isNullOrEmpty(tmp[1])) {
      searchCondition.setSearchDiscountCode(tmp[1]);
    }
  
    
    CountType type = null;

    if (tmp[2].equals(NewPublicCouponBean.DISPLAY_MODE_DAY)) {
      type = CountType.DAILY;
    } else if (tmp[2].equals(NewPublicCouponBean.DISPLAY_MODE_MONTH)) {
      type = CountType.MONTHLY;
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR));
      return BackActionResult.RESULT_SUCCESS;
    }
    
    // 如果页面链接参数没有第三个参数则捕捉异常
    try {
      if (!StringUtil.isNullOrEmpty(tmp[3])) {
        searchCondition.setSearchMobileComputerType(tmp[3]);
      }
    } catch (Exception e) {
     // 数组超出范围，属于页面没有传递参数过来，无需做特殊处理  
    }
    searchCondition.setOrderClientType(bean.getOrderClientType());
    searchCondition.setType(type);
    
    NewPublicCouponDetailsExportCondition dailyCondition = CsvExportType.EXPORT_CSV_NEW_PUBLIC_COUPON_DETAILS.createConditionInstance();
    dailyCondition.setSearchCondition(searchCondition);
    this.exportCondition = dailyCondition;

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  private CsvExportCondition<? extends CsvSchema> exportCondition;

  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return this.exportCondition;
  }

  /**
   * 遷移先の画面のURLを取得します。
   * 
   * @return 遷移先の画面のURL
   */
  public String getNextUrl() {
    return "/download";
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SalesAmountShopExportAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107082001";
  }

}
