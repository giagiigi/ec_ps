package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.ShippingListExportCondition;
import jp.co.sint.webshop.service.order.ShippingListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.ShippingListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1020410:出荷管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingListExportAction extends WebBackAction<ShippingListBean> implements WebExportAction {

  private ShippingListSearchCondition condition;

  protected ShippingListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected ShippingListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    if (getRequestParameter().getPathArgs().length > 0) {
      return false;
    }
    return true;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.SHIPPING_DATA_IO_SITE.isGranted(getLoginInfo());
    } else {
      authorization = Permission.SHIPPING_DATA_IO_SHOP.isGranted(getLoginInfo())
          || Permission.SHIPPING_DATA_IO_SITE.isGranted(getLoginInfo());
    }
    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    ValidationSummary summary = BeanValidator.validate(getBean());
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
      return summary.isValid();
    }

    boolean authorization = true;

    // 個別チェック開始
    if (!getBean().getSearchFromShippingDatetime().equals("") && !getBean().getSearchToShippingDatetime().equals("")) {
      if (DateUtil.fromString(getBean().getSearchFromShippingDatetime()).after(
          DateUtil.fromString(getBean().getSearchToShippingDatetime()))) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        authorization = false;
      }
    }
    if (!getBean().getSearchFromShippingDirectDate().equals("") && !getBean().getSearchToShippingDirectDate().equals("")) {
      if (DateUtil.fromString(getBean().getSearchFromShippingDirectDate()).after(
          DateUtil.fromString(getBean().getSearchToShippingDirectDate()))) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        authorization = false;
      }
    }

    return authorization;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    ShippingListBean bean = getBean();

    String searchShopCode = "";
    if (getLoginInfo().isSite()) {
      searchShopCode = bean.getSearchShopCode();
    } else {
      searchShopCode = getLoginInfo().getShopCode();
    }

    // 検索条件作成
    condition = new ShippingListSearchCondition();
    condition.setSearchShippingNo(bean.getSearchShippingNo());
    condition.setSearchOrderNo(bean.getSearchOrderNo());
    condition.setSearchCustomerName(bean.getSearchCustomerName());
    condition.setSearchCustomerNameKana(bean.getSearchCustomerNameKana());
    condition.setSearchAddressName(bean.getSearchAddressName());
    condition.setSearchCustomerPhoneNumber(bean.getSearchCustomerPhoneNumber());
    condition.setSearchShopCode(searchShopCode);
    condition.setSearchDeliveryTypeNo(bean.getSearchDeliveryTypeNo());
    condition.setSearchDeliverySlipNo(bean.getSearchDeliverySlipNo());
    condition.setSearchFromShippingDatetime(bean.getSearchFromShippingDatetime());
    condition.setSearchToShippingDatetime(bean.getSearchToShippingDatetime());
    condition.setSearchFromShippingDirectDate(bean.getSearchFromShippingDirectDate());
    condition.setSearchToShippingDirectDate(bean.getSearchToShippingDirectDate());
    condition.setSearchShippingStatus(bean.getSearchShippingStatus());
    condition.setSearchReturnItemType(bean.getSearchReturnItemType());

    condition = getCondition();

    // 検索実行

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    //add by V10-CH start
    //SiteManagementService siteManagementService = ServiceLocator.getSiteManagementService(getLoginInfo());
    //PointRule pointRule = siteManagementService.getPointRule();
    //condition.setScale(pointRule.getAmplificationRate().intValue());
    //add by V10-CH end

    // 該当データなし
    if (service.getShippingCount(condition).equals(0L)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.CSV_OUTPUT_NO_DATA));

      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    ShippingListExportCondition shippingCondition = CsvExportType.EXPORT_CSV_SHIPPING_LIST.createConditionInstance();
    shippingCondition.setCondition(condition);
    this.exportCondition = shippingCondition;

    setRequestBean(bean);

    setNextUrl("/download");

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
    return Messages.getString("web.action.back.order.ShippingListExportAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102041003";
  }

}
