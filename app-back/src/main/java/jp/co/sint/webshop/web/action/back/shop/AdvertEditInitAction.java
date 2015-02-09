package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.AdvertType;
import jp.co.sint.webshop.data.dto.Advert;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.AdvertEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050110:サイト設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AdvertEditInitAction extends WebBackAction<AdvertEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 権限チェック
    BackLoginInfo login = getLoginInfo();
    return Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(login);
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    AdvertEditBean reqBean = getBean();

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    Advert advert = new Advert();
    if (reqBean.getAdvertType().equals(AdvertType.CUSTOMER_REGISTER_COMPLETE.getValue())) {
      advert = service.getAdvert(NumUtil.toLong(reqBean.getCustomerAdvertType()));
    } else if (reqBean.getAdvertType().equals(AdvertType.ORDER_REGISTER_COMPLETE.getValue())) {
      advert = service.getAdvert(NumUtil.toLong(reqBean.getOrderAdvertType()));
    }
    if (advert != null) {
      reqBean.setAdvertText(advert.getAdvertText());
      reqBean.setEnableFlg(NumUtil.toString(advert.getAdvertEnabledFlg()));
      reqBean.setAdvertNo(NumUtil.toString(advert.getAdvertNo()));
      reqBean.setUpdateDatetimeStr(DateUtil.toDateTimeString(advert.getUpdatedDatetime()));
      reqBean.setUpdateDatetime(advert.getUpdatedDatetime());
      reqBean.setRegisterButtonDisplay(false);
    } else {
      reqBean.setAdvertText("");
      reqBean.setEnableFlg("");
      reqBean.setAdvertNo("");
      reqBean.setUpdateDatetimeStr("");
      reqBean.setRegisterButtonDisplay(false);
    }

    List<CodeAttribute> advertTypeList = new ArrayList<CodeAttribute>();

    for (AdvertType type : AdvertType.values()) {
      advertTypeList.add(new NameValue(type.getName(), type.getValue()));
    }
    reqBean.setAdvertTypeList(advertTypeList);
    if (StringUtil.isNullOrEmpty(reqBean.getAdvertType())) {
      reqBean.setAdvertType(AdvertType.CUSTOMER_REGISTER_COMPLETE.getValue());
    }

    List<CodeAttribute> customerAdvertList = new ArrayList<CodeAttribute>();
    customerAdvertList.add(new NameValue(Messages.getString("web.action.back.shop.AdvertEditInitAction.3"), ""));

    List<Advert> advertList = service.getAdvertByType(AdvertType.CUSTOMER_REGISTER_COMPLETE.getValue());
    for (Advert cAdvert : advertList) {
      customerAdvertList.add(new NameValue(Messages.getString("web.action.back.shop.AdvertEditInitAction.2"), NumUtil.toString(cAdvert
          .getAdvertNo())));
    }
    reqBean.setCustomerAdvertList(customerAdvertList);

    List<CodeAttribute> orderAdvertList = new ArrayList<CodeAttribute>();
    orderAdvertList.add(new NameValue(Messages.getString("web.action.back.shop.AdvertEditInitAction.3"), ""));
    List<Advert> oraerList = service.getAdvertByType(AdvertType.ORDER_REGISTER_COMPLETE.getValue());
    for (Advert oAdvert : oraerList) {
//      orderAdvertList.add(new NameValue(AdvertType.fromValue(oAdvert.getAdvertType()).getName(), NumUtil.toString(oAdvert
//          .getAdvertNo())));
      orderAdvertList.add(new NameValue(Messages.getString("web.action.back.shop.AdvertEditInitAction.2"), NumUtil.toString(oAdvert
          .getAdvertNo())));
    }
    reqBean.setOrderAdvertList(orderAdvertList);
    reqBean.setModeDiv("init");

    setRequestBean(reqBean);

    String[] pathParam = getRequestParameter().getPathArgs();
    String completeMessage = "";
    if (pathParam.length > 0) {
      completeMessage = pathParam[0];
    }
    if (completeMessage.equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.shop.AdvertEditInitAction.0")));
    }
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.AdvertEditInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105011003";
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    AdvertEditBean requestBean = (AdvertEditBean) getRequestBean();

    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      if (!StringUtil.isNullOrEmpty(requestBean.getEnableFlg())) {
        requestBean.setConfirmButtonDisplay(true);
      }
      requestBean.setReadonlyMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      requestBean.setConfirmButtonDisplay(false);
      requestBean.setReadonlyMode(WebConstantCode.DISPLAY_READONLY);
    }
    requestBean.setBackButtonDisplay(false);
  }
}
