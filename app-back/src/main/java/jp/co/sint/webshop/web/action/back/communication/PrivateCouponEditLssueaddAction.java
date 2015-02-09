package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.NewCouponRuleLssueInfo;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060620:PRIVATEクーポンマスタのデータモデルです。
 * 
 * @author OB.
 */
public class PrivateCouponEditLssueaddAction extends PrivateCouponEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.PRIVATE_COUPON_READ_SHOP.isGranted(getLoginInfo())
        && Permission.PRIVATE_COUPON_UPDATE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    PrivateCouponEditBean bean = getBean();
    NewCouponRuleLssueInfo newCouponRuleLssueInfo = new NewCouponRuleLssueInfo();
    String[] codeStringArray = bean.getLssueCommodityCode().split("\r\n");
    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
    if (codeStringArray.length > 0 && StringUtil.hasValue(codeStringArray[0])) {
      if (!StringUtil.hasValue(bean.getCouponCode())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.REGISTER_FAILED_ERROR, "沒有顾客别优惠券发行规则"));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      } else {
        NewCouponRule newCouponRule = svc.getPrivateCoupon(bean.getCouponCode());
        if (newCouponRule == null) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.REGISTER_FAILED_ERROR, "沒有顾客别优惠券发行规则"));
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      ServiceResult serviceResult;

      CommodityHeaderDao chDao = DIContainer.getDao(CommodityHeaderDao.class);
      boolean commodityExit = true;
      for (int i = 0; i < codeStringArray.length; i++) {
        if (StringUtil.hasValue(codeStringArray[i])) {
          if (!chDao.exists(getLoginInfo().getShopCode(), codeStringArray[i])) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format("发行关联商品编号：{0}",
                codeStringArray[i])));
            commodityExit = false;
          }
        }
      }

      if (!commodityExit) {
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      // 添加dto内容
      for (int i = 0; i < codeStringArray.length; i++) {
        if (StringUtil.hasValue(codeStringArray[i])) {
          newCouponRuleLssueInfo.setCouponCode(bean.getCouponCode());
          newCouponRuleLssueInfo.setCommodityCode(codeStringArray[i]);
          List<NewCouponRuleLssueInfo> ruleMax = svc.getMaxCouponLssue(bean.getCouponCode());
          if (ruleMax.size() > 0) {
            newCouponRuleLssueInfo.setCouponUseNo(ruleMax.get(0).getCouponUseNo() + 1);
          } else {
            newCouponRuleLssueInfo.setCouponUseNo(NumUtil.toLong("0"));
          }

          // 登录顾客别优惠券发行规则
          serviceResult = svc.insertNewCouponRuleLssue(newCouponRuleLssueInfo, WebConstantCode.LSSUE_COMPLETE_REGISTER);

          // 登录失败
          if (serviceResult.hasError()) {
            for (ServiceErrorContent error : serviceResult.getServiceErrorList()) {
              if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
                return BackActionResult.SERVICE_VALIDATION_ERROR;
              } else if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
                // 数据重复错误信息
                addErrorMessage(WebMessage.get(ServiceErrorMessage.REGISTER_ERROR, MessageFormat.format("发行关联商品编号：{0}",
                    codeStringArray[i])));
                setRequestBean(bean);
                return BackActionResult.RESULT_SUCCESS;
              }
            }

            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
          }
        }
      }
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED, "发行关联商品"));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }
    setNextUrl("/app/communication/private_coupon_edit/init/edit/" + bean.getCouponCode() + "/"
        + WebConstantCode.LSSUE_COMPLETE_REGISTER);

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
    PrivateCouponEditBean bean = getBean();
    if (!StringUtil.hasValue(bean.getLssueCommodityCode())) {
      List<String> messages = new ArrayList<String>();
      messages.add("发行关联商品编号必须输入！");
      getDisplayMessage().getErrors().addAll(messages);
      return false;
    }
    return true;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "优惠券规则_发行关联信息新建登录";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106062003";
  }

}
