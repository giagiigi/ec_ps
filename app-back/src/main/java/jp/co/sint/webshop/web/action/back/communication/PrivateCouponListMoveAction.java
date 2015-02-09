package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;

import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1060610:PRIVATEクーポン管理のアクションクラスです
 * 
 * @author OB.
 */
public class PrivateCouponListMoveAction extends WebBackAction<PrivateCouponListBean> {
	
	private final String UPDATE = "edit";
	
	private final String REGISTER = "new";
	
	private final String PURCHASE = "purchase";
	
	private final String SPECIAL = "special";
	
  private final String BIRTHDAY = "birthday";

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.PRIVATE_COUPON_READ_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] urlParam = getRequestParameter().getPathArgs();
    
    if(urlParam.length == 2 && (REGISTER.equals(urlParam[0]) || UPDATE.equals(urlParam[0])) ){
    	if(REGISTER.equals(urlParam[0])){
    		if(!PURCHASE.equals(urlParam[1]) && !SPECIAL.equals(urlParam[1]) && !BIRTHDAY.equals(urlParam[1])){
    			addErrorMessage(Messages.getString("validation.UrlValidator.0"));
    			return false;
    		}
    	}
    	return true;
    }
    addErrorMessage(Messages.getString("validation.UrlValidator.0"));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String[] urlParam = getRequestParameter().getPathArgs();

    ////修改顾客别优惠劵规则的时候判断顾客别优惠劵规则信息是否存在
    if (UPDATE.equals(urlParam[0])) { 
      CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
      NewCouponRule newCouponRule = svc.getPrivateCoupon(urlParam[1]);
      if (newCouponRule == null) {
    	  addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format(
		     "顾客别优惠券发行规则(优惠券规则编号:{0})", urlParam[1])));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }
    }else{
    	//新建顾客别优惠劵规则的时候
        if (!PURCHASE.equals(urlParam[1]) && !SPECIAL.equals(urlParam[1]) && !BIRTHDAY.equals(urlParam[1])) {
          addErrorMessage(Messages.getString("validation.UrlValidator.0"));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
    }

    setNextUrl("/app/communication/private_coupon_edit/init/" + urlParam[0] + "/" + urlParam[1]);
    
    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/communication/private_coupon_list/search_back", getSessionContainer());
    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "顾客别优惠券发行规详细信息跳转处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106061003";
  }

}
