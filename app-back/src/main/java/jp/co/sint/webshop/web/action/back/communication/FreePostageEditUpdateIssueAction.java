package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageEditBean;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageEditBean.IssueDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 免邮促销关联免邮代码发布登录处理
 * 
 * @author System Integrator Corp.
 */
public class FreePostageEditUpdateIssueAction extends FreePostageEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    return Permission.FREE_POSTAGE_UPDATE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    IssueDetailBean bean = new IssueDetailBean();
    boolean flg = true;
    bean.setIssueCode(getBean().getRelatedIssueCode());
    if (!validateBean(bean)) {
      flg = false;
    }
    if (!validateItems(getBean(), "targetUrl")) {
      flg = false;
    }
    if (flg) {
      CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
      String freePostageName = catalogService.getLoginFreePostageName(DateUtil.fromString(getBean().getFreeStartDate(), true),
          DateUtil.fromString(getBean().getFreeEndDate(), true), getBean().getRelatedIssueCode());
      if (StringUtil.hasValue(freePostageName)) {
        addErrorMessage("该免邮代码发布在活动【" + freePostageName + "】已经登录。");
        flg = false;
      }
    }
    return flg;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    FreePostageEditBean bean = getBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    FreePostageRule freePostageRule = communicationService.getFreePostageRule(bean.getFreePostageCode());

    String pageCode = bean.getRelatedIssueCode();
    String objects = "";
    String urls = "";
    if (freePostageRule != null) {
      String[] dbCodes = new String[] {};
      if (StringUtil.hasValue(freePostageRule.getObjectIssueCode())) {
        dbCodes = freePostageRule.getObjectIssueCode().split(";");
        objects = freePostageRule.getObjectIssueCode();
        urls = freePostageRule.getTargetUrl();
      }

      for (int j = 0; j < dbCodes.length; j++) {
        if (pageCode.equals(dbCodes[j])) {
          addErrorMessage("已经登录过的免邮代码发布！");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }

      if (StringUtil.hasValue(freePostageRule.getObjectIssueCode())) {
        objects = objects + ";" + pageCode;
        urls = urls + ";," + bean.getTargetUrl();
      } else {
        objects = pageCode;
        urls = "," + bean.getTargetUrl();
      }

      freePostageRule.setObjectIssueCode(objects);
      freePostageRule.setTargetUrl(urls);

      ServiceResult result = communicationService.updateFreePostageRule(freePostageRule);

      // 更新失败
      if (result.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          }
        }

        setNextUrl(null);
        addErrorMessage(WebMessage.get(ServiceErrorMessage.UPDATE_FAILED, bean.getFreePostageCode()));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }

    } else {
      throw new URLNotFoundException();
    }
    // 更新成功
    setNextUrl("/app/communication/free_postage_edit/select/" + super.REGISTER_ISSUE + "/" + bean.getFreePostageCode());

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.FreePostageEditUpdateIssueAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106131013";
  }

}
