package jp.co.sint.webshop.web.action.back.communication;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dao.BrandDao;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponEditBean;
import jp.co.sint.webshop.web.bean.back.communication.PublicCouponEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060320:キャンペーンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PublicCouponEditRelateUpdateBrandAction extends PublicCouponEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    // ショップ管理者で更新権限のあるユーザか、サイト管理者で更新権限があり、かつ一店舗モードの
    // 時のみアクセス可能
    boolean auth = Permission.PUBLIC_COUPON_UPDATE_SHOP.isGranted(login)
        || (Permission.PUBLIC_COUPON_UPDATE_SITE.isGranted(login) && getConfig().isOne());

    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0) {
      if ("delete".equals(getRequestParameter().getPathArgs()[0])) {
        auth = Permission.PUBLIC_COUPON_DELETE_SHOP.isGranted(login) || (Permission.PUBLIC_COUPON_DELETE_SITE.isGranted(login) && getConfig().isOne());
      }
    }

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    PublicCouponEditBean bean = getBean();
    if ("login".equals(getRequestParameter().getPathArgs()[0])){
      if (!StringUtil.hasValue(bean.getBrandDetailBean().getBrandCode())) {
        List<String> messages = new ArrayList<String>();
        messages.add("使用关联品牌编号必须输入！");
        getDisplayMessage().getErrors().addAll(messages);
        return false;
      }
    
      String[] codeStringArray = bean.getBrandDetailBean().getBrandCode().split("\r\n");
      if (codeStringArray.length > 0 && codeStringArray != null) {
        BrandDao brandDao = DIContainer.getDao(BrandDao.class);
        boolean brandExit = true;
        for (int i = 0; i < codeStringArray.length; i++) {
          if (StringUtil.hasValue(codeStringArray[i])) {
            if (!brandDao.exists(getLoginInfo().getShopCode(), codeStringArray[i])) {
              addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format(
                  "使用关联品牌编号：{0}", codeStringArray[i])));
              brandExit = false;
            }
          }
        }
  
        if (!brandExit) {
          setRequestBean(bean);
          return false;
        }
      }
      if (!(StringUtil.isNullOrEmpty(bean.getBrandDetailBean().getLimitedNum()) || NumUtil.isNum(bean.getBrandDetailBean()
          .getLimitedNum()))) {
        addErrorMessage("数量必须是一个整数");
        return false;
      }
    }
    if ("delete".equals(getRequestParameter().getPathArgs()[0])){
        if ( bean.getCheckedBrandCode()==null  || StringUtil.isNullOrEmpty(bean.getCheckedBrandCode().get(0))){
          addErrorMessage("没有选中品牌编号！");
          return false;
        }

    }
    

    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    PublicCouponEditBean bean = getBean();
    NewCouponRule newCouponRule = new NewCouponRule();
    // 为dto设置值
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    boolean flag = false;
    ServiceResult result = null;
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0
        && "login".equals(getRequestParameter().getPathArgs()[0])) {
      newCouponRule = communicationService.getPrivateCoupon(bean.getCouponId());
      if (newCouponRule != null ) {
        newCouponRule.setObjectBrand(setBrandJoin(bean, newCouponRule));
        flag = true;
        this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, "关联品牌"));
      }
    }
    
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0
        && "delete".equals(getRequestParameter().getPathArgs()[0])) {
      newCouponRule = communicationService.getPrivateCoupon(bean.getCouponId());
      if (newCouponRule != null) {
        String str = "";

        for (int i = 0; i < bean.getBrandDetailBeanList().size(); i++) {
          boolean equalFlg = false;
          for (int j = 0; j < bean.getCheckedBrandCode().size(); j++) {
            if (bean.getCheckedBrandCode().get(j).equals(bean.getBrandDetailBeanList().get(i).getBrandCode())) {
              equalFlg = true;
              break;
            }
          }

          if (!equalFlg) {
            String limitNum="";
            if(StringUtil.hasValue(bean.getBrandDetailBeanList().get(i).getLimitedNum())){
              limitNum = bean.getBrandDetailBeanList().get(i).getLimitedNum();
            }
            if (StringUtil.isNullOrEmpty(str)) {
              str += bean.getBrandDetailBeanList().get(i).getBrandCode() + ":" + limitNum;
            } else {
              str += ";" + bean.getBrandDetailBeanList().get(i).getBrandCode() + ":" + limitNum;
            }
          }
        }
        newCouponRule.setObjectBrand(str);
        result = communicationService.updateNewCouponRule(newCouponRule);
        
        if (result.hasError()) {
          this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DELETE_FAILED_ERROR, "关联品牌"));
          return BackActionResult.RESULT_SUCCESS;
        } else {
          setNextUrl("/app/communication/public_coupon_edit/init/" + bean.getCouponId() +"/relatedDelete"+"/succeed");
        }
      }
    }


    if (flag) {
          result = communicationService.updateNewCouponRule(newCouponRule);
      if (result.hasError()) {
        this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.UPDATE_FAILED_ERROR, bean
            .getBrandDetailBean().getBrandCode()));
        return BackActionResult.RESULT_SUCCESS;

      } else {
        
        setNextUrl("/app/communication/public_coupon_edit/init/" + bean.getCouponId() +"/relatedLogin"+"/succeed");
      }
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
    return Messages.getString("web.bean.back.communication.NewCampaignEditInitAction.005");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106102006";
  }

}
