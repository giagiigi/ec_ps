package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;
import java.util.List;

import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.PropagandaActivityCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.communication.PropagandaActivityCommodityInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PropagandaActivityRuleEditBean;
import jp.co.sint.webshop.web.bean.back.communication.PropagandaActivityRuleEditBean.CommodityDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 宣传品活动附送商品登录处理
 * 
 * @author System Integrator Corp.
 */
public class PropagandaActivityRuleEditRegisterCommodityAction extends PropagandaActivityRuleEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    return Permission.PROPAGANDA_ACTIVITY_RULE_UPDATE_SHOP.isGranted(getLoginInfo());
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
          addErrorMessage(Messages.getString("web.action.back.communication.PropagandaActivityRuleEditRegisterCommodityAction.0"));
          successFlg = false;
          break;
        }
      }
      
      if(successFlg) {
        CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
        CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
        List<PropagandaActivityCommodityInfo> commodityList = communicationService.getPropagandaActivityCommodityList(getBean().getActivityCode());
        for (int i = 0; i < pageCodes1.length; i++) {
          for (PropagandaActivityCommodityInfo pac: commodityList) {
            if (pageCodes1[i].equals(pac.getCommodityCode())) {
              addErrorMessage(Messages.getString("web.action.back.communication.PropagandaActivityRuleEditRegisterCommodityAction.1") + pageCodes1[i] + Messages.getString("web.action.back.communication.PropagandaActivityRuleEditRegisterCommodityAction.2"));
              successFlg = false;
              break;
            }
          }
          
          CommodityHeader ch = catalogService.getCommodityHeader(getLoginInfo().getShopCode(), pageCodes1[i]);
          if (ch == null || !ch.getCommodityType().equals(CommodityType.PROPAGANDA.longValue())) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format(Messages.getString("web.action.back.communication.PropagandaActivityRuleEditRegisterCommodityAction.3"), pageCodes1[i])));
            successFlg = false;
            break;
          }
          
          CommodityDetailBean commodityDetailBean = new CommodityDetailBean();
          commodityDetailBean.setCommodityCode(pageCodes1[i]);
          commodityDetailBean.setCommodityAmount(getBean().getRelatedCommodityAmount());
          if(!validateBean(commodityDetailBean)) {
            successFlg = false;
          }
          
          if(!successFlg) {
            break;
          }
        }
      }
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED, Messages.getString("web.action.back.communication.PropagandaActivityRuleEditRegisterCommodityAction.4")));
      successFlg = false;
    }
    return successFlg;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    PropagandaActivityRuleEditBean bean = getBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    String[] pageCodes = bean.getRelatedCommodityCode().split("\r\n");
    for (int j = 0; j < pageCodes.length; j++) {
      PropagandaActivityCommodity propagandaActivityCommodity = new PropagandaActivityCommodity();
      propagandaActivityCommodity.setActivityCode(bean.getActivityCode());
      propagandaActivityCommodity.setCommodityCode(pageCodes[j]);
      propagandaActivityCommodity.setPurchasingAmount(NumUtil.toLong(bean.getRelatedCommodityAmount()));
      ServiceResult result = communicationService.insertPropagandaActivityCommodity(propagandaActivityCommodity);

      // 登录失败
      if (result.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          }
        }

        setNextUrl(null);
        addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, pageCodes[j]));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }
    }
    // 登录成功
    setNextUrl("/app/communication/propaganda_activity_rule_edit/select/" + super.REGISTER_COMMODITY + "/" + bean.getActivityCode());

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.PropagandaActivityRuleEditRegisterCommodityAction.5");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106152005";
  }

}
