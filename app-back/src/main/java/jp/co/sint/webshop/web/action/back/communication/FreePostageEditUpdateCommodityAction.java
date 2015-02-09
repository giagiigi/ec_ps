package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;

import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.service.CatalogService;
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
 * 免邮促销关联商品登录处理
 * 
 * @author System Integrator Corp.
 */
public class FreePostageEditUpdateCommodityAction extends FreePostageEditBaseAction {

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
    boolean successFlg = true;
    String[] pageCodes1 = getBean().getRelatedCommodityCode().split("\r\n");
    String[] pageCodes2 = pageCodes1;
    if (pageCodes1.length > 0 && StringUtil.hasValue(pageCodes1[0])) {
      for (String code1 : pageCodes1) {
        int count = 0;
        for (String code2 : pageCodes2) {
          if (code1.equals(code2)) {
            count++;
          }
        }
        if (count > 1) {
          addErrorMessage("不能同时登录相同的促销商品！");
          successFlg = false;
          break;
        }
      }
    }
    return successFlg;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    FreePostageEditBean bean = getBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    FreePostageRule freePostageRule = communicationService.getFreePostageRule(bean.getFreePostageCode());

    String[] pageCodes = bean.getRelatedCommodityCode().split("\r\n");
    String objects = "";
    if (pageCodes.length > 0 && StringUtil.hasValue(pageCodes[0])) {
      if (freePostageRule != null) {
        String[] dbCodes = new String[] {};
        if (StringUtil.hasValue(freePostageRule.getObjectCommodity())) {
          dbCodes = freePostageRule.getObjectCommodity().split(";");
          objects = freePostageRule.getObjectCommodity();
        }

        for (int i = 0; i < pageCodes.length; i++) {
          for (int j = 0; j < dbCodes.length; j++) {
            if (pageCodes[i].equals(dbCodes[j])) {
              addErrorMessage("已经登录过的促销商品！");
              setRequestBean(bean);
              return BackActionResult.RESULT_SUCCESS;
            }
          }

          if (catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), pageCodes[i], true, false) == null) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format("促销商品编号：{0}", pageCodes[i])));
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
          }

          if (StringUtil.hasValue(freePostageRule.getObjectCommodity())) {
            objects = objects + ";" + pageCodes[i];
          } else {
            if (i == 0) {
              objects = pageCodes[i];
            } else {
              objects = objects + ";" + pageCodes[i];
            }
          }
        }

        freePostageRule.setObjectCommodity(objects);

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
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED, "促销商品"));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }
    // 更新成功
    setNextUrl("/app/communication/free_postage_edit/select/" + super.REGISTER_COMMODITY + "/" + bean.getFreePostageCode());

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.FreePostageEditUpdateCommodityAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106131010";
  }

}
