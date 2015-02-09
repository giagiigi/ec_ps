package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * 免邮促销选择表示处理
 * 
 * @author System Integrator Corp.
 */
public class FreePostageEditSelectAction extends FreePostageEditBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    FreePostageEditBean bean = new FreePostageEditBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    String freePostageCode = "";
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    bean.setSelectCategoryList(service.getAllCategory());
    if (getRequestParameter().getPathArgs().length == 1) {
      freePostageCode = getRequestParameter().getPathArgs()[0];
      FreePostageRule freePostageRule = communicationService.getFreePostageRule(freePostageCode);

      if (freePostageRule == null) {
        throw new URLNotFoundException();
      } else {
        setDtoToBean(freePostageRule, bean);
      }
    } else if (getRequestParameter().getPathArgs().length == 2) {
      freePostageCode = getRequestParameter().getPathArgs()[1];
      FreePostageRule freePostageRule = communicationService.getFreePostageRule(freePostageCode);

      if (freePostageRule == null) {
        throw new URLNotFoundException();
      } else {
        setDtoToBean(freePostageRule, bean);
        if (super.REGISTER.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, "免邮促销"));
        } else if (super.UPDATE.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.UPDATE_SUCCESS_INFO, "免邮促销"));
        } else if (super.REGISTER_COMMODITY.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, "促销商品"));
        } else if (super.REGISTER_BRAND.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, "促销品牌"));
        } else if (super.REGISTER_CATEGORY.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, "促销分类"));
        } else if (super.REGISTER_ISSUE.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, "免邮代码发布"));
        } else if (super.DELETE_COMMODITY.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.DELETE_SUCCESS_INFO, "促销商品"));
        } else if (super.DELETE_BRAND.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.DELETE_SUCCESS_INFO, "促销品牌"));
        } else if (super.DELETE_CATEGORY.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.DELETE_SUCCESS_INFO, "促销分类"));
        } else if (super.DELETE_ISSUE.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.DELETE_SUCCESS_INFO, "免邮代码发布"));
        }
      }
    } else {
      throw new URLNotFoundException();
    }

    setRequestBean(bean);
    setBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    FreePostageEditBean bean = (FreePostageEditBean) getRequestBean();

    // 更新権限チェック
    if (Permission.FREE_POSTAGE_UPDATE_SHOP.isGranted(login)) {
      bean.setDisplayUpdateButton(true);
      bean.setDisplayRegisterButton(true);
      bean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      bean.setDisplayUpdateButton(false);
      bean.setDisplayRegisterButton(false);
      bean.setEditMode(WebConstantCode.DISPLAY_READONLY);
    }

    bean.setDisplayMode(WebConstantCode.DISPLAY_BLOCK);
    bean.setDisplayDeleteButton(Permission.FREE_POSTAGE_DELETE_SHOP.isGranted(login));
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.FreePostageEditSelectAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106131008";
  }

  public boolean authorize() {
    return Permission.FREE_POSTAGE_READ_SHOP.isGranted(getLoginInfo());
  }

  public boolean validate() {
    return true;
  }

}
