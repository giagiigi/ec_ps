package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.analysis.CountType;
import jp.co.sint.webshop.service.analysis.NewPublicCouponSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.NewPublicCouponExportCondition;
import jp.co.sint.webshop.utility.DateUtil;
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
public class NewPublicCouponExportAction extends WebBackAction<NewPublicCouponBean> implements WebExportAction {

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
    boolean result = true;
    
    result = validateBean(getBean());
    
    NewPublicCouponBean bean = getBean();
    if (bean.getSelectMode().equals("1")) {
      if (bean.getDisplayMode().equals(NewPublicCouponBean.DISPLAY_MODE_DAY)) {
        if(StringUtil.isNullOrEmptyAnyOf(bean.getSearchStartDate(), bean.getSearchEndDate())){
          result = false;
          addErrorMessage("检索日期不能为空");
        }
      } else if (bean.getDisplayMode().equals(NewPublicCouponBean.DISPLAY_MODE_MONTH)) {
        if (StringUtil.isNullOrEmptyAnyOf(bean.getSearchStartYear(), bean.getSearchStartMonth(), bean.getSearchEndYear(), bean.getSearchEndMonth())) {
          result = false;
          addErrorMessage("检索日期不能为空");
        }
      }
    }
    if (bean.getDisplayMode().equals(NewPublicCouponBean.DISPLAY_MODE_DAY)) {
      long days = DateUtil.getDaysFromTwoDateString(bean.getSearchStartDate(), bean.getSearchEndDate());
      if (days > 61) {
        getBean().getSearchResult().clear();
        result = false;
        addErrorMessage("请将检索期间指定在两个月以内");
      }
    } else if (bean.getDisplayMode().equals(NewPublicCouponBean.DISPLAY_MODE_MONTH)) {
      long days = DateUtil.getDaysFromTwoDateString(bean.getSearchStartYear()+"/"+bean.getSearchStartMonth()+"/01", bean.getSearchEndYear()+"/"+bean.getSearchEndMonth()+"/01");
      if (days > 31) {
        getBean().getSearchResult().clear();
        result = false;
        addErrorMessage("请将检索期间指定在两个月以内");
      }
    } 
    
//    return validateBean(getBean());
    return result;
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

    if (login.isShop()) {
      // 自ショップのショップコードでなかったら
      // 自ショップのショップコードにする
      if (!shopCode.equals(login.getShopCode())) {
        shopCode = login.getShopCode();
      }
    }

    NewPublicCouponSearchCondition searchCondition = new NewPublicCouponSearchCondition();
    
    if (StringUtil.isNullOrEmptyAnyOf(bean.getSearchStartYear(),bean.getSearchEndYear(),bean.getSearchStartMonth(),bean.getSearchEndMonth())) {
      String startYear = "";
      String endYear = "";
      String startMonth = "";
      String endMonth = "";


      if (!StringUtil.isNullOrEmptyAnyOf(bean.getSearchStartDate(), bean.getSearchEndDate())) {
        startYear = DateUtil.getYYYY(DateUtil.fromString(bean.getSearchStartDate()));
        endYear = DateUtil.getYYYY(DateUtil.fromString(bean.getSearchEndDate()));
        startMonth = DateUtil.getMM(DateUtil.fromString(bean.getSearchStartDate()));
        endMonth = DateUtil.getMM(DateUtil.fromString(bean.getSearchEndDate()));
      }else{
        startYear = endYear = DateUtil.getYYYY(DateUtil.getSysdate());
        startMonth = endMonth = DateUtil.getMM(DateUtil.getSysdate());
      }
      
      bean.setSearchStartYear(startYear);
      bean.setSearchEndYear(endYear);
      bean.setSearchStartMonth(startMonth);
      bean.setSearchEndMonth(endMonth);
    }
    
    searchCondition.setOrderClientType(bean.getOrderClientType());
    searchCondition.setSearchMobileComputerType(bean.getMobileComputerType());
    searchCondition.setShopCode(shopCode);
    
    if (!StringUtil.isNullOrEmpty(bean.getSearchStartDate())) {
      searchCondition.setSearchStartDate(bean.getSearchStartDate());
    }
    if (!StringUtil.isNullOrEmpty(bean.getSearchEndDate())) {
      searchCondition.setSearchEndDate(bean.getSearchEndDate());
    }
    if (!StringUtil.isNullOrEmpty(bean.getSearchStartYear())) {
      searchCondition.setSearchStartYear(bean.getSearchStartYear());
    }
    if (!StringUtil.isNullOrEmpty(bean.getSearchEndYear())) {
      searchCondition.setSearchEndYear(bean.getSearchEndYear());
    }
    if (!StringUtil.isNullOrEmpty(bean.getSearchStartMonth())) {
      searchCondition.setSearchStartMonth(bean.getSearchStartMonth());
    }
    if (!StringUtil.isNullOrEmpty(bean.getSearchEndMonth())) {
      searchCondition.setSearchEndMonth(bean.getSearchEndMonth());
    }
    
    CountType type = null;

    if (bean.getDisplayMode().equals(NewPublicCouponBean.DISPLAY_MODE_DAY)) {
      type = CountType.DAILY;
    } else if (bean.getDisplayMode().equals(NewPublicCouponBean.DISPLAY_MODE_MONTH)) {
      type = CountType.MONTHLY;
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR));
      return BackActionResult.RESULT_SUCCESS;
    }
    searchCondition.setType(type);
    
    NewPublicCouponExportCondition dailyCondition = CsvExportType.EXPORT_CSV_NEW_PUBLIC_COUPON.createConditionInstance();
    dailyCondition.setSearchCondition(searchCondition);
    this.exportCondition = dailyCondition;

    setRequestBean(bean);
    setNextUrl("/download");
    return BackActionResult.RESULT_SUCCESS;
  }

  private CsvExportCondition<? extends CsvSchema> exportCondition;

  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return this.exportCondition;
  }

//  /**
//   * 遷移先の画面のURLを取得します。
//   * 
//   * @return 遷移先の画面のURL
//   */
//  public String getNextUrl() {
//    return "/download";
//  }


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
