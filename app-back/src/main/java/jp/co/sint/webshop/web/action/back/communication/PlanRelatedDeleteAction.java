package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.PlanDetailType;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PlanRelatedBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public class PlanRelatedDeleteAction extends WebBackAction<PlanRelatedBean>{

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.PLAN_READ_SHOP.isGranted(getLoginInfo()) && Permission.PLAN_UPDATE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    PlanRelatedBean bean = getBean();

    // 選択されたレビューIDを取得する
    String[] values = getRequestParameter().getAll("checkBox");

    // チェックボックスが選択されているか
    if (StringUtil.isNullOrEmpty(values[0])) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED, "关联商品"));
      setNextUrl(null);
      this.setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    List<String> successList = new ArrayList<String>();
    List<String> failureList = new ArrayList<String>();
    String detailCode = "";
	if (PlanDetailType.BRAND.getValue().equals(bean.getDetailType())) {
	    detailCode = bean.getBrandCode();
	 } else if (PlanDetailType.CATEGORY.getValue().equals(bean.getDetailType())) {
		detailCode = bean.getCategoryCode();
	 } else if (PlanDetailType.FREE.getValue().equals(bean.getDetailType())) {
		detailCode = bean.getCode();
	 }
    // 削除処理実行
    for (String commodityCode : values) {
      ServiceResult serviceResult = service.deletePlanCommodity(bean.getPlanCode(), bean.getDetailType(), detailCode, commodityCode);
      if (serviceResult.hasError()) {
	    for (ServiceErrorContent error : serviceResult.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
            failureList.add(Message.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format("关联商品(商品编号：{0})",
            		commodityCode)));
          } 
        }
      } else {
    	successList.add(WebMessage.get(CompleteMessage.DELETE_COMPLETE, MessageFormat.format("关联商品(商品编号：{0})",
    			commodityCode)));
      }
    }
    
    if (failureList.size() > 0 ) {
	  bean.setSuccessList(successList);
	  bean.setFailureList(failureList);
	  setNextUrl("/app/communication/plan_related/init/" + bean.getPlanCode() + 
				"/" + bean.getDetailType() + "/" + detailCode);
	} else {
	  setNextUrl("/app/communication/plan_related/init/" + bean.getPlanCode() + 
				"/" + bean.getDetailType() + "/" + detailCode + "/" + WebConstantCode.COMPLETE_DELETE);
	}
	setRequestBean(bean);
	return BackActionResult.RESULT_SUCCESS;
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
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
	if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
	  return "促销企划管理画面删除处理";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "特集企划管理画面删除处理";
	}
    return "";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
	  return "5106081003";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "5106091003";
	}
    return "";
   }
}
