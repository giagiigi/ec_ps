package jp.co.sint.webshop.web.action.front.mypage;

import java.math.RoundingMode;

import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.FriendCouponBean;
import jp.co.sint.webshop.web.bean.front.mypage.FriendCouponBean.FriendCouponBeanDetail;

/**
 * 
 * 
 * @author System OB.
 */
public abstract class FriendCouponBaseAction extends WebFrontAction<FriendCouponBean> {

  public void setUnissuedInfo(FriendCouponBeanDetail detailBean, FriendCouponRule friendCouponRule) {
    detailBean.setFriendCouponRuleNo(friendCouponRule.getFriendCouponRuleNo());
    // ロケールの言語を取得する
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
      detailBean.setFriendCouponRule(friendCouponRule.getFriendCouponRuleCn());

      // 优惠券名称(日文)
    } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
      detailBean.setFriendCouponRule(friendCouponRule.getFriendCouponRuleJp());

      // 优惠券名称(英文)
    } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
      detailBean.setFriendCouponRule(friendCouponRule.getFriendCouponRuleEn());
    }
    // 20140408 hdh edit start
    detailBean.setIssueType(friendCouponRule.getCouponIssueType());
    if(CampaignType.FIXED.getValue().equals(NumUtil.toString(friendCouponRule.getCouponIssueType()))){
      detailBean.setCouponAmount(NumUtil.toString(friendCouponRule.getCouponAmount()));
    }else if (CampaignType.PROPORTION.getValue().equals(NumUtil.toString(friendCouponRule.getCouponIssueType()))){
      detailBean.setRatio(NumUtil.toString(friendCouponRule.getCouponProportion()));
      detailBean.setMaxDiscountPrice(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(friendCouponRule.getMaxUseOrderAmount(), friendCouponRule .getCouponProportion()), 100, 2, RoundingMode.HALF_UP)));
    }
    // 20140408 hdh edit end;
    detailBean.setMinUseOrderAmount(NumUtil.toString(friendCouponRule.getMinUseOrderAmount()));
    detailBean.setApplicableObjects(NumUtil.toString(friendCouponRule.getApplicableObjects()));
    detailBean.setUseValidType(friendCouponRule.getUseValidType());
    detailBean.setUseValidNum(friendCouponRule.getUseValidNum());
    
  }

  @Override
  public boolean validate() {
    return true;
  }

}
