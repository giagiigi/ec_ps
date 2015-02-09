package jp.co.sint.webshop.web.action.back.shop;

import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.CouponIssue;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.shop.CouponResearch;
import jp.co.sint.webshop.service.shop.CouponResearchInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.CouponResearchBean;
import jp.co.sint.webshop.web.bean.back.shop.CouponResearchBean.CouponCommodityResearchBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1050520:支払方法詳細のアクションクラスです

 * 
 * @author System Integrator Corp.
 */
public class CouponResearchInitAction extends WebBackAction<CouponResearchBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。

   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;

    OperatingMode operatingMode = getConfig().getOperatingMode();

    if (operatingMode.equals(OperatingMode.MALL) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (operatingMode.equals(OperatingMode.MALL) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }
    if (operatingMode.equals(OperatingMode.SHOP) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (operatingMode.equals(OperatingMode.SHOP) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (operatingMode.equals(OperatingMode.ONE) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 0) {

      String shopCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(shopCode)) {
        return false;
      }

      authorization &= shopCode.equals(getLoginInfo().getShopCode());
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
    boolean validation = true;
    return validation;
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {


    String[] path = getRequestParameter().getPathArgs();

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    CouponIssue couponIssue = service.getCouponIssue(path[0], NumUtil.toLong(path[1]));

    if (couponIssue == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.shop.CouponResearchInitAction.0")));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    CouponResearchBean bean = new CouponResearchBean();
    bean.setBonusCouponEndDate(DateUtil.toDateString(couponIssue.getBonusCouponEndDate()));
    bean.setBonusCouponStartDate(DateUtil.toDateString(couponIssue.getBonusCouponStartDate()));
    bean.setCouponIssueNo(Long.toString(couponIssue.getCouponIssueNo()));
    bean.setCouponName(couponIssue.getCouponName());
    bean.setCouponPrice(NumUtil.toString(couponIssue.getCouponPrice()));
    bean.setUseCouponIssueEndDate(DateUtil.toDateString(couponIssue.getUseCouponEndDate()));
    bean.setUseCoupoStartDate(DateUtil.toDateString(couponIssue.getUseCouponStartDate()));

    int orderAmount = 0;
    BigDecimal totalSales = BigDecimal.ZERO;

    List<CouponResearch> list = service.getCouponResearch(path[0], path[1]);
    for (CouponResearch c : list) {
      CouponCommodityResearchBean commodity = new CouponCommodityResearchBean();
      commodity.setCommodityCode(c.getCommodityCode());
      commodity.setCommodityName(c.getCommodityName());
      commodity.setCommoditySalesAmount(c.getCommoditySalesAmount());
      orderAmount += Integer.parseInt(c.getCommodityOrderAmount());
      //totalSales += Integer.parseInt(c.getCommoditySalesAmount());
      totalSales = totalSales.add(NumUtil.parse(c.getCommoditySalesAmount()));
      bean.getList().add(commodity);
    }

    CouponResearchInfo dataBean = service.getCouponResearchInfo(path[0], path[1]);
    bean.setUsedTotalPrice(dataBean.getUsedTotalPrice());
    bean.setIssueTotalPrice(dataBean.getIssueTotalPrice());
    bean.setEnableTotalPrice(dataBean.getEnableTotalPrice());
    bean.setDisableTotalPrice(dataBean.getDisableTotalPrice());
    bean.setOverdueTotalPrice(dataBean.getOverdueTotalPrice());
    bean.setPhantomTotalPrice(dataBean.getPhantomTotalPrice());
    //bean.setOrderAmount(String.valueOf(orderAmount));
    //bean.setTotalSales(String.valueOf(totalSales));

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。

   */
  @Override
  public void prerender() {
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.CouponResearchInitAction.1");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105052001";
  }


}
