package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020130:新規受注(配送先設定)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderShippingInitAction extends NeworderShippingBaseAction {

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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    setRequestBean(createBeanFromCashier());
    
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    getBean().getAdditionalAddressEdit().setAdditionalCityList(s.getCityNames(getBean().getAdditionalAddressEdit().getAdditionalPrefectureCode()));
    // 发票信息设定
    List<CodeAttribute> commodityNameList = new ArrayList<CodeAttribute>();
    commodityNameList.add(new NameValue("请选择", ""));
    for (String invoiceCommodityName : DIContainer.getWebshopConfig().getInvoiceCommodityNameList()) {
        commodityNameList.add(new NameValue(invoiceCommodityName, invoiceCommodityName));
      }
    getBean().setInvoiceCommodityNameList(commodityNameList);
    
    getBean().setAddressScript(s.createAddressScript());
    getBean().setAddressPrefectureList(s.createPrefectureList());
    getBean().setAddressCityList(s.createCityList(getBean().getAdditionalAddressEdit().getAdditionalPrefectureCode()));
    getBean().setAddressAreaList(s.createAreaList(getBean().getAdditionalAddressEdit().getAdditionalPrefectureCode(), 
    		getBean().getAdditionalAddressEdit().getAdditionalCityCode()));
    if (Permission.CUSTOMER_UPDATE.isGranted(getLoginInfo())) {
      getBean().setDisplayAdditionalAddress(true);
    } else {
      getBean().setDisplayAdditionalAddress(false);
    }

    getBean().setAdditionalBlock(WebConstantCode.DISPLAY_NONE);
    createOutCardPrice();
    if (isCashOnDeliveryOnly()) {
      setRequestBean(getBean());
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderShippingInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102013004";
  }

}
