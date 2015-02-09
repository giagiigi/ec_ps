package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.CountType;
import jp.co.sint.webshop.service.analysis.NewPublicCouponSearchCondition;
import jp.co.sint.webshop.service.analysis.NewPublicCouponSummary;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.NewPublicCouponBean;
import jp.co.sint.webshop.web.bean.back.analysis.NewPublicCouponBean.NewPublicCouponBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070820:ショップ別売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NewPublicCouponSearchAction extends WebBackAction<NewPublicCouponBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.ANALYSIS_READ_SITE.isGranted(login) || Permission.ANALYSIS_READ_SHOP.isGranted(login);
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
          getBean().getSearchResult().clear();
          addErrorMessage("检索日期不能为空");
        }
      } else if (bean.getDisplayMode().equals(NewPublicCouponBean.DISPLAY_MODE_MONTH)) {
        if (StringUtil.isNullOrEmptyAnyOf(bean.getSearchStartYear(), bean.getSearchStartMonth(), bean.getSearchEndYear(), bean.getSearchEndMonth())) {
          getBean().getSearchResult().clear();
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
    
    
    AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());
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

    List<NewPublicCouponSummary> result = service.getNewPublicCoupon(searchCondition);
    if (result.size() == 0 && (StringUtil.isNotNull(bean.getSelectMode()) && bean.getSelectMode().equals("1"))) {
      this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
      bean.getSearchResult().clear();
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }
    List<NewPublicCouponBeanDetail> detailList = new ArrayList<NewPublicCouponBeanDetail>();
    for (NewPublicCouponSummary summary : result) {
      NewPublicCouponBeanDetail detail = new NewPublicCouponBeanDetail();
      detail.setOrderDate(summary.getOrderDate());
      detail.setDiscountCode(summary.getDiscountCode());
      detail.setTotalCall(NumUtil.toString(summary.getTotalCall()));
      detail.setReturnCall(NumUtil.toString(summary.getReturnCall()));
      detail.setZhCall(NumUtil.toString(summary.getZhCall()));
      detail.setJpCall(NumUtil.toString(summary.getJpCall()));
      detail.setEnCall(NumUtil.toString(summary.getEnCall()));
      detail.setDiscountPrice(NumUtil.toString(summary.getDiscountPrice()));
      detail.setPaidPrice(NumUtil.toString(summary.getPaidPrice()));
      detail.setShippingCharge(NumUtil.toString(summary.getShippingCharge()));

      detailList.add(detail);
    }

    bean.setSearchResultDisplay(true);
    bean.setSearchResult(detailList);
    bean.setExportAuthority(Permission.ANALYSIS_DATA_SITE.isGranted(login) || Permission.ANALYSIS_DATA_SHOP.isGranted(login));

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SalesAmountShopSearchAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107082003";
  }

}
