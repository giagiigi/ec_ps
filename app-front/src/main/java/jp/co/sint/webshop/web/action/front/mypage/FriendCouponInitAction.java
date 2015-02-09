package jp.co.sint.webshop.web.action.front.mypage;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.IssueDateType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SmsingService;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.mypage.FriendCouponBean;
import jp.co.sint.webshop.web.bean.front.mypage.FriendCouponBean.FriendCouponBeanDetail;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;

/**
 * @author System OB.
 */
public class FriendCouponInitAction extends FriendCouponBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    FriendCouponBean bean = new FriendCouponBean();
    // セッションから顧客コードを取得
    FrontLoginInfo login = getLoginInfo();
    String customerCode = login.getCustomerCode();

    // 会员有无发货记录
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    SmsingService services = ServiceLocator.getSmsingService(getLoginInfo());
    List<ShippingHeader> shippingList = service.getShippingHeaderList(customerCode);

    // 取得未发行朋友介绍优惠券信息
    List<FriendCouponRule> unissuedInfoList = service.getUnissuedFriendCouponList(customerCode);

    // 取得已发行朋友介绍优惠券信息
    List<NewCouponRule> issuedInfoList = service.getIssuedFriendCouponList(customerCode);

    // 无朋友介绍优惠券
    if ((unissuedInfoList == null || unissuedInfoList.isEmpty()) && (issuedInfoList == null || issuedInfoList.isEmpty())) {
      // addErrorMessage(WebMessage.get(MypageErrorMessage.URL_OUT_DATE));
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    } else {
      // 有朋友介绍优惠券
      List<FriendCouponBeanDetail> unissuedList = new ArrayList<FriendCouponBeanDetail>();
      List<FriendCouponBeanDetail> issuedList = new ArrayList<FriendCouponBeanDetail>();

      // 未发行优惠券
      if (shippingList != null && !shippingList.isEmpty()) {
        for (FriendCouponRule friendCouponRule : unissuedInfoList) {
          if (shippingList.size() >= friendCouponRule.getOrderHistory()) {
            FriendCouponBeanDetail detailBean = new FriendCouponBeanDetail();
            String month = "";

            if (IssueDateType.DATE.getValue().equals(NumUtil.toString(friendCouponRule.getIssueDateType()))) {
              if (ValidatorUtil.inRange(DateUtil.getSysdate(), friendCouponRule.getIssueStartDate(), friendCouponRule
                  .getIssueEndDate())) {
                setUnissuedInfo(detailBean, friendCouponRule);
                detailBean.setAbleFlg(true);
                unissuedList.add(detailBean);
              }
            }
            if (IssueDateType.MONTH.getValue().equals(NumUtil.toString(friendCouponRule.getIssueDateType()))) {
              month = DateUtil.getMM(DateUtil.getSysdate());
              if (NumUtil.toLong(month).equals(friendCouponRule.getIssueDateNum())) {
                setUnissuedInfo(detailBean, friendCouponRule);
                detailBean.setAbleFlg(true);
                unissuedList.add(detailBean);
              }
            }
          } else {
            FriendCouponBeanDetail detailBean = new FriendCouponBeanDetail();
            String month = "";

            if (IssueDateType.DATE.getValue().equals(NumUtil.toString(friendCouponRule.getIssueDateType()))) {
              if (ValidatorUtil.inRange(DateUtil.getSysdate(), friendCouponRule.getIssueStartDate(), friendCouponRule
                  .getIssueEndDate())) {
                setUnissuedInfo(detailBean, friendCouponRule);
                detailBean.setAbleFlg(false);
                unissuedList.add(detailBean);
              }
            }
            if (IssueDateType.MONTH.getValue().equals(NumUtil.toString(friendCouponRule.getIssueDateType()))) {
              month = DateUtil.getMM(DateUtil.getSysdate());
              if (NumUtil.toLong(month).equals( friendCouponRule.getIssueDateNum())) {
                setUnissuedInfo(detailBean, friendCouponRule);
                detailBean.setAbleFlg(false);
                unissuedList.add(detailBean);
              }
            }
          }
        }

        // 已发行优惠券
        for (NewCouponRule issueHistory : issuedInfoList) {

          if (ValidatorUtil.lessThanOrEquals(DateUtil.getSysdate(), issueHistory.getMinUseEndDatetime())) {
            FriendCouponBeanDetail detailBean = new FriendCouponBeanDetail();
            // ロケールの言語を取得する
            String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
            if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
              detailBean.setFriendCouponRule(issueHistory.getCouponName());

              // 优惠券名称(日文)
            } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
              detailBean.setFriendCouponRule(issueHistory.getCouponNameJp());

              // 优惠券名称(英文)
            } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
              detailBean.setFriendCouponRule(issueHistory.getCouponNameEn());
            }
            detailBean.setCouponAmount(NumUtil.toString(issueHistory.getCouponAmount()));
            detailBean.setCouponCode(issueHistory.getCouponCode());
            detailBean.setIssueEndDate(DateUtil.toDateString(issueHistory.getMinUseEndDatetime()));
            detailBean.setIssueStartDate(DateUtil.toDateString(issueHistory.getMinUseStartDatetime()));
            detailBean.setMinUseOrderAmount(NumUtil.toString(issueHistory.getMinUseOrderAmount()));
            // 20140408 hdh edit start
            detailBean.setIssueType(issueHistory.getCouponIssueType());
            if(CampaignType.FIXED.getValue().equals(NumUtil.toString(issueHistory.getCouponIssueType()))){
              detailBean.setCouponAmount(NumUtil.toString(issueHistory.getCouponAmount()));
            }else if (CampaignType.PROPORTION.getValue().equals(NumUtil.toString(issueHistory.getCouponIssueType()))){
              
//              Long proportion=100-issueHistory.getCouponProportion();
//              detailBean.setRatio(NumUtil.toString(proportion%10==0?proportion/10:proportion));
              detailBean.setRatio(NumUtil.toString(issueHistory.getCouponProportion()));
              detailBean.setCouponAmount(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(issueHistory.getMinUseOrderAmount(), issueHistory .getCouponProportion()), 100, 2, RoundingMode.HALF_UP)));
              detailBean.setMaxDiscountPrice(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(issueHistory.getMaxUseOrderAmount(), issueHistory .getCouponProportion()), 100, 2, RoundingMode.HALF_UP)));
            }
            // 20140408 hdh edit end;

            // WebshopConfig config = getConfig();
            // String secureUrl = WebUtil.getSecureUrl(config.getHostName(),
            // config.getHttpsPort());
            // secureUrl += getRequestParameter().getContextPath();
            // detailBean.setUrl(secureUrl + "/" + currentLanguageCode +
            // "/app/common/friend_coupon_use/init/");

            String url = getConfig().getCouponUrl() + detailBean.getCouponCode();
            url = url.replaceAll("zh-cn", currentLanguageCode);
            detailBean.setUrl(url);

            detailBean.setApplicableObjects(NumUtil.toString(issueHistory.getApplicableObjects()));
            issuedList.add(detailBean);
          }
        }
        bean.setUnissuedList(unissuedList);
        bean.setIssuedList(issuedList);
      }
    }

    // 无权限发行优惠券
    if ((bean.getUnissuedList() == null || bean.getUnissuedList().isEmpty())
        && (bean.getIssuedList() == null || bean.getIssuedList().isEmpty())) {
      bean.setIssueFlg(false);
      List<FriendCouponRule> friendCouponRuleList = service.getFriendCouponRuleList();
      List<FriendCouponBeanDetail> listBean = new ArrayList<FriendCouponBeanDetail>();
      for (FriendCouponRule rule : friendCouponRuleList) {
        FriendCouponBeanDetail detailBean = new FriendCouponBeanDetail();

        if (IssueDateType.DATE.getValue().equals(NumUtil.toString(rule.getIssueDateType()))) {
          if (ValidatorUtil.inRange(DateUtil.getSysdate(), rule.getIssueStartDate(), rule.getIssueEndDate())) {
            setUnissuedInfo(detailBean, rule);
            listBean.add(detailBean);
          }
        }

        if (IssueDateType.MONTH.getValue().equals(NumUtil.toString(rule.getIssueDateType()))) {
          String month = DateUtil.getMM(DateUtil.getSysdate());
          if (NumUtil.toLong(month).equals(rule.getIssueDateNum())) {
            setUnissuedInfo(detailBean, rule);
            listBean.add(detailBean);
          }
        }
      }

      bean.setList(listBean);
    } else {
      bean.setIssueFlg(false);
    }
    String languageCode = services.getLanguageCode(login.getCustomerCode());
    if (StringUtil.hasValue(languageCode)) {
      bean.setLanguageCode(languageCode);
    }
    // 将用户的手机号码提取
    bean.setTell(login.getMobileNumber());
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * キャンセル完了表示
   */
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
