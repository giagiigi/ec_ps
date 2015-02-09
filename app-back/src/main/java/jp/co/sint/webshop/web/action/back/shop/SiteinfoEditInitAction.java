package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.SiteinfoEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1050110:サイト設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SiteinfoEditInitAction extends WebBackAction<SiteinfoEditBean> {

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
    SiteinfoEditBean reqBean = new SiteinfoEditBean();

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    Shop site = service.getSite();

    // サイト未登録時
    if (site == null) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn(WebMessage.get(ShopErrorMessage.NO_SITE_ERROR));
      return BackActionResult.SERVICE_ERROR;
    }

    // requestBeanにデータをコピー
    reqBean.setShopCode(site.getShopCode());
    reqBean.setSiteName(site.getShopName());
    reqBean.setShortSiteName(site.getShortShopName());
//   
//    String UpdatedDatetimeStr = DateUtil.iso8601Datetime(site.getUpdatedDatetime());
//    Date UpdatedDatetime = null;   
//    UpdatedDatetime = DateUtil.fromString(UpdatedDatetimeStr);
    
    reqBean.setUpdateDatetimeStr(DateUtil.toDateTimeString(site.getUpdatedDatetime()));
    reqBean.setUpdateDatetime(site.getUpdatedDatetime());
    reqBean.setPrefectureCode(site.getPrefectureCode());
    reqBean.setAddress1(site.getAddress1());
    reqBean.setAddress2(site.getAddress2());
    reqBean.setAddress3(site.getAddress3());
    reqBean.setAddress4(site.getAddress4());
//  add by shikui start 2010/04/27
    reqBean.setIcpCode(site.getIcpCode());
//  add by shikui end 2010/04/27    
    reqBean.setPostalCode(site.getPostalCode());;
    //modify by V10-CH 170 start   
    if(StringUtil.hasValue(site.getPhoneNumber())){
    String[] phoneNumber = site.getPhoneNumber().split("-");
    if (phoneNumber.length == 2) {
      reqBean.setPhoneNumber1(phoneNumber[0]);
      reqBean.setPhoneNumber2(phoneNumber[1]);
      //modify by V10-CH 170 end   
    }else if(phoneNumber.length == 3){
      reqBean.setPhoneNumber1(phoneNumber[0]);
      reqBean.setPhoneNumber2(phoneNumber[1]);
      reqBean.setPhoneNumber3(phoneNumber[2]);
    }
    }else{
      site.setPhoneNumber("");
      reqBean.setPhoneNumber1("");
      reqBean.setPhoneNumber2("");
      reqBean.setPhoneNumber3("");
    }
    //Add by V10-CH start
    if(StringUtil.hasValue(site.getMobileNumber())){
      reqBean.setMobileNumber(site.getMobileNumber());
    }else{
      reqBean.setMobileNumber("");
    }
    //Add by V10-CH end
    reqBean.setCityCode(site.getCityCode());
    reqBean.setMailAddress(site.getEmail());
    reqBean.setPersonInCharge(site.getPersonInCharge());
    reqBean.setRegisterButtonDisplay(false);
    reqBean.setCustomerCancelableFlg(site.getCustomerCancelableFlg());
    reqBean.setShopIntroducedUrl(site.getShopIntroducedUrl());

    // modify by V10-CH 170 start 
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setCityList(s.getCityNames(reqBean.getPrefectureCode()));
    // modify by V10-CH 170 end
    setRequestBean(reqBean);

    String[] pathParam = getRequestParameter().getPathArgs();
    String completeMessage = "";
    if (pathParam.length > 0) {
      completeMessage = pathParam[0];
    }
    if (completeMessage.equals(WebConstantCode.COMPLETE_REGISTER)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
          .getString("web.action.back.shop.SiteinfoEditInitAction.0")));
    }
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    SiteinfoEditBean requestBean = (SiteinfoEditBean) getRequestBean();

    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      requestBean.setConfirmButtonDisplay(true);
      requestBean.setReadonlyMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      requestBean.setConfirmButtonDisplay(false);
      requestBean.setReadonlyMode(WebConstantCode.DISPLAY_READONLY);
    }
    requestBean.setBackButtonDisplay(false);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.SiteinfoEditInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105011003";
  }

}
