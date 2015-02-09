package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.analysis.CountType;
import jp.co.sint.webshop.service.analysis.SalesAmountByShopSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.SalesAmountByShopDailyExportCondition;
import jp.co.sint.webshop.service.data.csv.SalesAmountByShopMonthlyExportCondition;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountShopBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070820:ショップ別売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountShopExportAction extends WebBackAction<SalesAmountShopBean> implements WebExportAction {

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
    return validateBean(getBean());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    BackLoginInfo login = getLoginInfo();
    SalesAmountShopBean bean = getBean();

    String shopCode = bean.getShopCode();

    if (login.isShop()) {
      // 自ショップのショップコードでなかったら
      // 自ショップのショップコードにする
      if (!shopCode.equals(login.getShopCode())) {
        shopCode = login.getShopCode();
      }
    }

    SalesAmountByShopSearchCondition searchCondition = new SalesAmountByShopSearchCondition();
    int year = NumUtil.toLong(bean.getSearchYear()).intValue();
    int month = NumUtil.toLong(bean.getSearchMonth()).intValue();
    searchCondition.setSearchYear(year);
    searchCondition.setSearchMonth(month);
    searchCondition.setClientGroup(bean.getClientGroupCondition());

    if (getConfig().isShop() && shopCode.equals(getConfig().getSiteShopCode())) {
      searchCondition.setPaymentMethodType(bean.getPaymentMethodCondition());
    } else {
      if (StringUtil.hasValue(bean.getPaymentMethodCondition())) {
        searchCondition.setPaymentMethodNo(NumUtil.toLong(bean.getPaymentMethodCondition()));
      }
    }

    searchCondition.setShopCode(shopCode);

    CountType type = null;

    if (bean.getDisplayMode().equals(SalesAmountShopBean.DISPLAY_MODE_DAY)) {
      type = CountType.DAILY;
    } else if (bean.getDisplayMode().equals(SalesAmountShopBean.DISPLAY_MODE_MONTH)) {
      type = CountType.MONTHLY;
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR));
      return BackActionResult.RESULT_SUCCESS;
    }
    searchCondition.setType(type);

    switch (type) {
      case DAILY:
        SalesAmountByShopDailyExportCondition dailyCondition = CsvExportType.EXPORT_SALES_AMOUNT_BY_SHOP_DAILY
            .createConditionInstance();
        dailyCondition.setSearchCondition(searchCondition);
        this.exportCondition = dailyCondition;
        break;
      case MONTHLY:
        SalesAmountByShopMonthlyExportCondition monthlyCondition = CsvExportType.EXPORT_SALES_AMOUNT_BY_SHOP_MONTHLY
            .createConditionInstance();
        monthlyCondition.setSearchCondition(searchCondition);
        this.exportCondition = monthlyCondition;
        break;
      default:
        addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR));
        return BackActionResult.RESULT_SUCCESS;
    }

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
