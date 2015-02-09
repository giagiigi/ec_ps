package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 免邮促销关联内容删除处理
 * 
 * @author Kousen.
 */
public class FreePostageEditDeleteRelatedAction extends FreePostageEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.FREE_POSTAGE_DELETE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // チェックボックスが選択されているか
    String type = "";
    if (getRequestParameter().getPathArgs().length == 1) {
      type = getRequestParameter().getPathArgs()[0];
    } else {
      throw new URLNotFoundException();
    }

    if (type.equals(super.DELETE_COMMODITY)) {
      if (StringUtil.isNullOrEmpty(getRequestParameter().getAll("checkBoxCommodity")[0])) {
        getDisplayMessage().addError(WebMessage.get(ActionErrorMessage.NO_CHECKED, "促销商品"));
        return false;
      }
    } else if (type.equals(super.DELETE_BRAND)) {
      if (StringUtil.isNullOrEmpty(getRequestParameter().getAll("checkBoxBrand")[0])) {
        getDisplayMessage().addError(WebMessage.get(ActionErrorMessage.NO_CHECKED, "促销品牌"));
        return false;
      }
    } else if (type.equals(super.DELETE_CATEGORY)) {
      if (StringUtil.isNullOrEmpty(getRequestParameter().getAll("checkBoxCategory")[0])) {
        getDisplayMessage().addError(WebMessage.get(ActionErrorMessage.NO_CHECKED, "促销分类"));
        return false;
      }
    } else if (type.equals(super.DELETE_ISSUE)) {
      if (StringUtil.isNullOrEmpty(getRequestParameter().getAll("checkBoxIssue")[0])) {
        getDisplayMessage().addError(WebMessage.get(ActionErrorMessage.NO_CHECKED, "免邮代码发布"));
        return false;
      }
    } else {
      throw new URLNotFoundException();
    }

    return true;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * 开始执行动作
   * 
   * @return 返回结果
   */
  @Override
  public WebActionResult callService() {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    FreePostageEditBean bean = getBean();
    FreePostageRule freePostageRule = service.getFreePostageRule(bean.getFreePostageCode());
    if (freePostageRule == null) {
      addErrorMessage("免邮促销不存在！");
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    } else {
      String type = getRequestParameter().getPathArgs()[0];
      String objects = "";
      boolean isHead = true;

      if (type.equals(super.DELETE_COMMODITY)) {
        String[] pageValues = getRequestParameter().getAll("checkBoxCommodity");
        String[] dbValues = freePostageRule.getObjectCommodity().split(";");

        for (String dbValue : dbValues) {
          boolean addFlg = true;
          for (String pageValue : pageValues) {
            if (dbValue.equals(pageValue)) {
              addFlg = false;
              break;
            }
          }

          if (addFlg) {
            if (isHead) {
              objects = dbValue;
              isHead = false;
            } else {
              objects = objects + ";" + dbValue;
            }
          }
        }

        freePostageRule.setObjectCommodity(objects);
        setNextUrl("/app/communication/free_postage_edit/select/" + super.DELETE_COMMODITY + "/" + bean.getFreePostageCode());
      } else if (type.equals(super.DELETE_BRAND)) {
        String[] pageValues = getRequestParameter().getAll("checkBoxBrand");
        String[] dbValues = freePostageRule.getObjectBrand().split(";");

        for (String dbValue : dbValues) {
          boolean addFlg = true;
          for (String pageValue : pageValues) {
            if (dbValue.equals(pageValue)) {
              addFlg = false;
              break;
            }
          }

          if (addFlg) {
            if (isHead) {
              objects = dbValue;
              isHead = false;
            } else {
              objects = objects + ";" + dbValue;
            }
          }
        }

        freePostageRule.setObjectBrand(objects);
        setNextUrl("/app/communication/free_postage_edit/select/" + super.DELETE_BRAND + "/" + bean.getFreePostageCode());
      } else if (type.equals(super.DELETE_CATEGORY)) {
        String[] pageValues = getRequestParameter().getAll("checkBoxCategory");
        String[] dbValues = freePostageRule.getObjectCategory().split(";");

        for (String dbValue : dbValues) {
          boolean addFlg = true;
          for (String pageValue : pageValues) {
            if (dbValue.equals(pageValue)) {
              addFlg = false;
              break;
            }
          }

          if (addFlg) {
            if (isHead) {
              objects = dbValue;
              isHead = false;
            } else {
              objects = objects + ";" + dbValue;
            }
          }
        }

        freePostageRule.setObjectCategory(objects);
        setNextUrl("/app/communication/free_postage_edit/select/" + super.DELETE_CATEGORY + "/" + bean.getFreePostageCode());
      } else if (type.equals(super.DELETE_ISSUE)) {
        String[] pageValues = getRequestParameter().getAll("checkBoxIssue");
        String[] dbValues = freePostageRule.getObjectIssueCode().split(";");
        String[] dbUrls = freePostageRule.getTargetUrl().split(";");
        String urls = "";

        for (int i = 0; i < dbValues.length; i++) {
          boolean addFlg = true;
          for (String pageValue : pageValues) {
            if (dbValues[i].equals(pageValue)) {
              addFlg = false;
              break;
            }
          }

          if (addFlg) {
            if (isHead) {
              objects =  dbValues[i];
              urls =  dbUrls[i];
              isHead = false;
            } else {
              objects = objects + ";" + dbValues[i];
              urls = urls + ";" + dbUrls[i];
            }
          }
        }

        freePostageRule.setObjectIssueCode(objects);
        freePostageRule.setTargetUrl(urls);
        setNextUrl("/app/communication/free_postage_edit/select/" + super.DELETE_ISSUE + "/" + bean.getFreePostageCode());
      }
    }

    ServiceResult result = service.updateFreePostageRule(freePostageRule);

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

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.FreePostageEditDeleteRelatedAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106131014";
  }
}
