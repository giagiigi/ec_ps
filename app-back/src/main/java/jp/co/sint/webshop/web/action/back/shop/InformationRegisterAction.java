package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.dto.Information;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.SiteManagementServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.InformationBean;
import jp.co.sint.webshop.web.bean.back.shop.InformationBean.InformationDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051210:お知らせ編集のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class InformationRegisterAction extends WebBackAction<InformationBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    InformationBean bean = getBean();
    return validateBean(bean.getRegisterInformation());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    InformationBean bean = getBean();

    InformationDetail detailBean = bean.getRegisterInformation();

    if (DateUtil.fromString(detailBean.getStartDatetime(), true).after(DateUtil.fromString(detailBean.getEndDatetime(), true))) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM,
          Messages.getString("web.action.back.shop.InformationRegisterAction.0")));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    Information information = new Information();
    if (detailBean.getInformationNo() != null && !detailBean.getInformationNo().equals("")) {
      information.setInformationNo(Long.valueOf(detailBean.getInformationNo()));
    }
    information.setInformationStartDatetime(DateUtil.fromString(detailBean.getStartDatetime(), true));
    information.setInformationEndDatetime(DateUtil.fromString(detailBean.getEndDatetime(), true));
    information.setDisplayClientType(Long.valueOf(detailBean.getDisplayClientType()));
    information.setInformationType(Long.valueOf(detailBean.getType()));
    information.setInformationContent(detailBean.getContent());
    //20120514 tuxinwei add start
    information.setInformationContentEn(detailBean.getContentEn());
    information.setInformationContentJp(detailBean.getContentJp());
    //20120514 tuxinwei add end
    //soukai add 2011/12/19 ob start
    information.setInformationUrl(detailBean.getInformationUrl());
    information.setInformationUrlEn(detailBean.getInformationUrlEn());
    information.setInformationUrlJp(detailBean.getInformationUrlJp());
    information.setPrimaryFlg(detailBean.getPrimaryFlg());
    //soukai add 2011/12/19 ob end
    information.setUpdatedDatetime(detailBean.getUpdateDatetime());

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());

    ServiceResult result;
    String nextUrl = null;
    if (information.getInformationNo() == null) {
      result = service.insertInformation(information);
      nextUrl = "/app/shop/information/init/" + WebConstantCode.COMPLETE_INSERT;
    } else {
      result = service.updateInformation(information);
      nextUrl = "/app/shop/information/init/" + WebConstantCode.COMPLETE_UPDATE;
    }

    // 登録処理の成功チェック
    if (result.hasError()) {
      setRequestBean(bean);
      setNextUrl(null);

      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(SiteManagementServiceErrorContent.NO_INFORMATION_DATE_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.shop.InformationRegisterAction.1")));
          return BackActionResult.RESULT_SUCCESS;
        }
      }

      return BackActionResult.SERVICE_ERROR;

    } else {
      setNextUrl(nextUrl);
    }
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.InformationRegisterAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105121003";
  }

}
