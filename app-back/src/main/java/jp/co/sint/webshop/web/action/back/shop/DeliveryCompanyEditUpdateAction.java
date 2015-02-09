package jp.co.sint.webshop.web.action.back.shop;

import java.math.BigDecimal;
import java.text.MessageFormat;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.DeliveryRegion;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ShopManagementServiceErrorContent;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051510:配送公司详细更新处理
 * 
 * @author cxw
 */
public class DeliveryCompanyEditUpdateAction extends WebBackAction<DeliveryCompanyEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {

    boolean authorization = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
    } else {
      authorization = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo());
    }
    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */

  public boolean validate() {
    boolean bReturn = validateBean(getBean());

    String[] params = getRequestParameter().getPathArgs();
    if (bReturn && params.length > 1) {
      String selectedRowIndex = "charge_" + params[1];
      String Commission = getRequestParameter().get(selectedRowIndex);

      do {
        if (StringUtil.isNullOrEmpty(Commission)) {
          bReturn = false;
          break;
        }
        //Currency
        final String REGEX_PATTERN_DIGIT = "^[0-9\\.]*|-[0-9\\.]+";
        bReturn = ValidatorUtil.patternMatches(REGEX_PATTERN_DIGIT, Commission);
        if (!bReturn) {
          addErrorMessage(Messages.getString("validation.CurrencyValidator.0"));
          break;
        }
        //Precision(precision = 10, scale = 2)
        final int precision = 10;
        final int scale = 2;
        BigDecimal bdValue = new BigDecimal(Commission);
        bReturn = (bdValue.precision() <= precision && bdValue.scale() <= scale)&& (bdValue.precision() - bdValue.scale() <= precision - scale);
        if (!bReturn) {
          addErrorMessage(MessageFormat.format(Messages.getString("validation.PrecisionValidator.0"), precision - scale, scale));
          break;
        }
      } while (false);
    }
    return bReturn;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    // 配送公司详细更新处理
    DeliveryCompanyEditBean editBean = getBean();

    String[] params = getRequestParameter().getPathArgs();

    // 如果页面传递参数大于1,表示需要更新选中地域的手续费
    if (params.length > 1 && "area".equals(params[0])) {
      // 根据公司编号及从页面参数中取得的地域编号从数据库中取出指定的deliveryRegion
      ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
      DeliveryRegion deliveryRegion = service.getDeliveryRegion(editBean.getShopCode(), editBean
          .getDeliveryCompanyNo(), params[1]);
      if (null == deliveryRegion) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "配送公司"));
        setRequestBean(editBean);
        return BackActionResult.RESULT_SUCCESS;
      }

      // 更新deliveryRegion的信息
      String selectedRowIndex = "charge_" + params[1];
      deliveryRegion.setDeliveryDatetimeCommission(NumUtil.parse(getRequestParameter().get(
          selectedRowIndex)));
      ServiceResult result = service.updateDeliveryRegion(deliveryRegion);
      if (result.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          // 重复性错误报告
          if (error.equals(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR)) {
            addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED, "配送公司"));
            return BackActionResult.RESULT_SUCCESS;
          } else if (error.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
            addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, "配送地域"));
            return BackActionResult.RESULT_SUCCESS;
          }
        }
      }
    }

    // 取得选定的配送公司明细
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    DeliveryCompany company = service.getDeliveryCompany(getLoginInfo().getShopCode(), editBean
        .getDeliveryCompanyNo());

    company.setShopCode(getLoginInfo().getShopCode());
    company.setDeliveryCompanyNo(editBean.getDeliveryCompanyNo());
    company.setDeliveryCompanyName(editBean.getDeliveryCompanyName());
    company.setDeliveryDatetimeCommission(NumUtil.parse(editBean.getDeliveryDatetimeCommission()));
    company.setDeliveryCompanyUrl(editBean.getDeliveryCompanyUrl());
    company.setUseFlg(Long.parseLong(editBean.getDisplayFlg()));

    // 更新成功处理
    ServiceResult result = service.updateDeliveryCompany(company);
    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content == CommonServiceErrorContent.NO_DATA_ERROR) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "配送公司"));
          setRequestBean(editBean);
          return BackActionResult.RESULT_SUCCESS;
        } else if (content == CommonServiceErrorContent.VALIDATION_ERROR) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    }

    // 更新后跳转处理
    setNextUrl("/app/shop/delivery_company_edit/init/" + getBean().getDeliveryCompanyNo() + "/"
        + WebConstantCode.COMPLETE_UPDATE);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "配送公司详细更新处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105151005";
  }

}
