package jp.co.sint.webshop.web.action.front.common;

import java.math.RoundingMode;

import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.FriendCouponUse;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.common.FriendCouponUseBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;

/**
 * 
 * @author System OB.
 */
public class FriendCouponUseInitAction extends WebFrontAction<FriendCouponUseBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    FriendCouponUseBean bean = new FriendCouponUseBean();
    String[] url = getRequestParameter().getPathArgs();

    if (url == null || url.length != 1) {
      addErrorMessage(WebMessage.get(MypageErrorMessage.URL_OUT_DATE));
      bean.setError(true);
      setRequestBean(getBean());
      return FrontActionResult.RESULT_SUCCESS;
    } else {
      // 根据优惠券编号取得优惠券信息
      String couponCode = url[0];
      CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
      FriendCouponUse edit = service.getFriendCouponUse(couponCode);
      if (edit == null) {
        addErrorMessage(WebMessage.get(MypageErrorMessage.URL_OUT_DATE));
        bean.setError(true);
        setRequestBean(bean);
        return FrontActionResult.RESULT_SUCCESS;
      }

      // url过期时
      if (!ValidatorUtil.inRange(DateUtil.getSysdate(), edit.getMinUseStartDatetime(), edit.getMinUseEndDatetime())) {
        addErrorMessage(WebMessage.get(MypageErrorMessage.URL_OUT_DATE));
        bean.setError(true);
        setRequestBean(bean);
        return FrontActionResult.RESULT_SUCCESS;
      }

      bean.setCustomerName(edit.getLastName());
      bean.setCouponAmount(edit.getCouponAmount());
      
      // ロケールの言語を取得する
      String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        bean.setFriendCouponRule(edit.getCouponName());

        // 优惠券名称(日文)
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        bean.setFriendCouponRule(edit.getCouponNameJp());

        // 优惠券名称(英文)
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        bean.setFriendCouponRule(edit.getCouponNameEn());
      }
      bean.setCouponCode(edit.getCouponCode());
      bean.setMinUseOrderAmount(edit.getMinUseOrderAmount());
      bean.setMinUseStartDatetime(DateUtil.toDateString(edit.getMinUseStartDatetime()));
      bean.setMinUseEndDatetime(DateUtil.toDateString(edit.getMinUseEndDatetime()));
      bean.setApplicableObjects(edit.getApplicableObjects());
      bean.setCouponIssueType(NumUtil.toString(edit.getCouponIssueType()));
      
//      Long proportion=edit.getCouponProportion();
      bean.setCouponProportion(NumUtil.toString(edit.getCouponProportion()));
      //bean.setCouponProportion(NumUtil.toString(edit.getCouponProportion()));
      if(CampaignType.PROPORTION.getValue().equals(NumUtil.toString(edit.getCouponIssueType()))){
        bean.setCouponAmount(BigDecimalUtil.divide(BigDecimalUtil.multiply(edit.getMinUseOrderAmount(), edit .getCouponProportion()), 100, 2, RoundingMode.HALF_UP));
        bean.setMaxDiscountPrice(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(edit.getMaxUseOrderAmount(), edit .getCouponProportion()), 100, 2, RoundingMode.HALF_UP)));
      }
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }
  }

  @Override
  public void prerender() {

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

}
