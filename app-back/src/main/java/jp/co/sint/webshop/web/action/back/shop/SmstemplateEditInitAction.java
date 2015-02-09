package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.SmsContentType;
import jp.co.sint.webshop.data.domain.SmsType;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.sms.SmsTemplateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.SmstemplateEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051110:メールテンプレートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SmstemplateEditInitAction extends WebBackAction<SmstemplateEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    LoginInfo login = getLoginInfo();
    return (Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(login) || Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(login));
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
    SmstemplateEditBean bean = getBean();

    List<CodeAttribute> smsTypeList = new ArrayList<CodeAttribute>();
    smsTypeList.add(new NameValue(Messages.getString(
      "web.action.back.shop.SmstemplateEditInitAction.0"), ""));

    SiteManagementService siteService = ServiceLocator.getSiteManagementService(getLoginInfo());
    PointRule pointRule = siteService.getPointRule();
    boolean notUsePoint = pointRule.getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.DISABLED.longValue());
    for (SmsType type : SmsTemplateUtil.getUsableSmsTypeList(getConfig().getOperatingMode(), getLoginInfo().isSite(), false)) {
      if (type == SmsType.INFORMATION || (notUsePoint && type == SmsType.POINT_EXPIRED)) {
        continue;
      }
      smsTypeList.add(type);
    }
    bean.setSmsTypeList(smsTypeList);

    List<CodeAttribute> informationSmsList = new ArrayList<CodeAttribute>();
    informationSmsList.add(new NameValue(Messages.getString(
      "web.action.back.shop.SmstemplateEditInitAction.0"), ""));
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    List<CodeAttribute> informationSmsListDto = service.getInformationSmsTypeList();
    informationSmsList.addAll(informationSmsListDto);
    informationSmsList.add(new NameValue(Messages.getString(
      "web.action.back.shop.SmstemplateEditInitAction.1"), "new_template"));

    List<CodeAttribute> contentTypeList = new ArrayList<CodeAttribute>();
    contentTypeList.addAll(Arrays.asList(SmsContentType.values()));
    bean.setModeDiv("init");
    bean.setSmsType("");
    bean.setSmsUseFlgDiv(StringUtil.coalesceEmptyValue(bean.getSmsUseFlgDiv(), "1"));
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * PathInfoの指定されたindexのデータを取得する。<BR>
   * データが存在しなければ空文字を返す<BR>
   * 
   * @param index
   *          取得するPathInfoのIndex
   * @return 指定されたIndexのPathInfo
   */
  private String getPathInfo(int index) {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > index) {
      return tmpArgs[index];
    }
    return "";
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    String complete = getPathInfo(0);
    if (complete.equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.shop.MailtemplateEditInitAction.2")));
    } else if (complete.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.shop.MailtemplateEditInitAction.2")));
    } else if (complete.equals(WebConstantCode.COMPLETE_REGISTER)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.shop.MailtemplateEditInitAction.3")));
    }

    SmstemplateEditBean bean = (SmstemplateEditBean) getRequestBean();
    LoginInfo login = getLoginInfo();
    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(login)) {
      bean.setDisplayInsertButton(true);
      bean.setDisplayRegisterButton(true);
    } else if (Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(login)) {
      bean.setDisplayInsertButton(false);
      bean.setDisplayRegisterButton(true);
    } else {
      bean.setDisplayInsertButton(false);
      bean.setDisplayRegisterButton(false);
    }
    bean.setDisplayPreviewButton(true);
    bean.setDisplayDeleteButton(false);
    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.MailtemplateEditInitAction.4");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105111002";
  }

}
