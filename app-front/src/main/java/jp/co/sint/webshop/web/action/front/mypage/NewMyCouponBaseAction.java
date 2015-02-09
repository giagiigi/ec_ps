package jp.co.sint.webshop.web.action.front.mypage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.CouponStatus;
import jp.co.sint.webshop.data.domain.CouponUse;
import jp.co.sint.webshop.data.domain.IssueDateType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.dto.FriendCouponIssueHistory;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.FriendCouponSearchCondition;
import jp.co.sint.webshop.service.FriendCouponUseLine;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SmsingService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.communication.MyCouponHistorySearchCondition;
import jp.co.sint.webshop.service.communication.NewCouponHistoryInfo;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.NewMyCouponBean;
import jp.co.sint.webshop.web.bean.front.mypage.NewMyCouponBean.FriendCouponBeanDetail;
import jp.co.sint.webshop.web.bean.front.mypage.NewMyCouponBean.FriendIssueHistoryDetail;
import jp.co.sint.webshop.web.bean.front.mypage.NewMyCouponBean.MyCouponHistoryDetail;
import jp.co.sint.webshop.web.bean.front.mypage.NewMyCouponBean.PrivateCouponUseDetail;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageDisplayMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * クーポン履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class NewMyCouponBaseAction extends WebFrontAction<NewMyCouponBean> {

  
  private static final String PROPORTION_FLG = "%";
  
  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

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

  public NewMyCouponBean initNewMyCouponBean(NewMyCouponBean bean) {
    
    UtilService utilSev = ServiceLocator.getUtilService(getLoginInfo());
    
    

    // 我的优惠券模块
     
    // ロケールの言語を取得する
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();

    // ログイン情報
    FrontLoginInfo login = getLoginInfo();

    // 顧客サービス
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    MyCouponHistorySearchCondition myCouponCondition = new MyCouponHistorySearchCondition();

    // 顧客番号
    myCouponCondition.setCustomerCode(login.getCustomerCode());
    myCouponCondition = PagerUtil.createSearchCondition(getRequestParameter(), myCouponCondition);

    // クーポンルール情報を取得する
    SearchResult<NewCouponHistoryInfo> result = service.getCustomerNewCouponList(myCouponCondition);

    // 画面情報を取得する
    bean.getCouponIssueList().clear();
    MyCouponHistoryDetail couponDetail = null;
    for (NewCouponHistoryInfo coupon : result.getRows()) {
      couponDetail = new MyCouponHistoryDetail();
      // 优惠券名称
      couponDetail.setCouponName(utilSev.getNameByLanguage(coupon.getCouponName(), coupon.getCouponNameEn(),coupon.getCouponNameJp()));
      
      // 优惠金額
      if (CouponIssueType.FIXED.getValue().equals(coupon.getCouponIssueType())) {
        couponDetail.setFixedFlg(Boolean.TRUE);
        couponDetail.setCouponAmount(coupon.getCouponAmount());
      } else {
        if (StringUtil.hasValue(coupon.getCouponProportion())) {
          couponDetail.setCouponAmount(coupon.getCouponProportion() + PROPORTION_FLG);
        } else {
          couponDetail.setCouponAmount(StringUtil.EMPTY);
        }
      }
      // 优惠券使用最小购买金額
      couponDetail.setCouponBeginAmount(coupon.getMinUseOrderAmount());
      // 利用开始日时
      couponDetail.setUseStartDatetime(DateUtil.toDateString(coupon.getUseStartDatetime()));
      // 利用结束日时
      couponDetail.setUseEndDatetime(DateUtil.toDateString(coupon.getUseEndDatetime()));

      if (CouponStatus.USED.getValue().equals(coupon.getCouponStatus())) {
        if (coupon.getUseStatus().equals(CouponUse.UNUSED.getValue())
            && StringUtil.isCorrectRange(DateUtil.toDateTimeString(coupon.getUseEndDatetime()), DateUtil.toDateTimeString(DateUtil
                .getSysdate()))) {
          couponDetail.setUseStatus(CouponUse.OVERDATE.getName());
        } else {
          couponDetail.setUseStatus(CouponUse.fromValue(coupon.getUseStatus()).getName());
        }
      } else {
        couponDetail.setUseStatus(CouponStatus.fromValue(coupon.getCouponStatus()).getName());
      }
      couponDetail.setCouponCode(coupon.getCouponCode());
      
      bean.getCouponIssueList().add(couponDetail);
    }
    // ページ情報の追加
    bean.setPagerValue(PagerUtil.createValue(result));

    bean.setHasValue(true);
    if (bean.getCouponIssueList().isEmpty()) {
      bean.setHasValue(Boolean.FALSE);
    }
    // 検索結果が最大フェッチ件数を設定する
    if (result.isOverflow()) {
      this.addWarningMessage(WebMessage.get(MypageDisplayMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
          + result.getRowCount()), "" + NumUtil.formatNumber("" + myCouponCondition.getMaxFetchSize())));
    }
    
    
    
    //积分对换优惠券模块
    FriendCouponSearchCondition friendCoupoCcondition = PagerUtil.createSearchCondition(getRequestParameter(), new FriendCouponSearchCondition());

    friendCoupoCcondition.setCustomerCode(getLoginInfo().getCustomerCode());

    // 有効ポイントを設定する
    CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());
    bean.setGoodPoint(cs.getFriendCouponUseHistoryAllPoint(getLoginInfo().getCustomerCode()));

    // 両替可能のクーポンを取得
    List<FriendCouponUseLine> friendCouponUseList = cs.getFriendCouponUseLine();

    List<PrivateCouponUseDetail> fcuBeanList = new ArrayList<PrivateCouponUseDetail>();

    for (FriendCouponUseLine fcLine : friendCouponUseList) {
      PrivateCouponUseDetail pceBean = new PrivateCouponUseDetail();

      pceBean.setIssueType(fcLine.getCouponIssueType()); //类型
      if (CouponIssueType.FIXED.getValue().equals(fcLine.getCouponIssueType())) {
        if(StringUtil.hasValue(fcLine.getCouponAmount()) && fcLine.getCouponAmount().endsWith(".00")){
          pceBean.setCouponAmount(fcLine.getCouponAmount().replace(".00", ""));
        }else{
          pceBean.setCouponAmount(fcLine.getCouponAmount());
        }
        
      } else if (CouponIssueType.PROPORTION.getValue().equals(fcLine.getCouponIssueType())) {
        pceBean.setCouponProportion(fcLine.getCouponProportion());
        pceBean.setCouponAmount(new BigDecimal(fcLine.getCouponProportion())
        .multiply(new BigDecimal(fcLine.getMaxUseOrderAmount())).divide(new BigDecimal("100")).setScale(0,BigDecimal.ROUND_DOWN).toString());
      }
      pceBean.setCouponName(fcLine.getCouponName());

      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        pceBean.setCouponName(fcLine.getCouponName());
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        pceBean.setCouponName(fcLine.getCouponNameJp());
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        pceBean.setCouponName(fcLine.getCouponNameEn());
      }

      
      if(StringUtil.hasValue(fcLine.getMinUseOrderAmount()) && fcLine.getMinUseOrderAmount().endsWith(".00")){
        pceBean.setMinUseOrderAmount(fcLine.getMinUseOrderAmount().replace(".00", ""));
      }else{
        pceBean.setMinUseOrderAmount(fcLine.getMinUseOrderAmount());
      }
      pceBean.setMinUseEndNum(fcLine.getMinUseEndNum());
      pceBean.setCouponCode(fcLine.getCouponCode());
      pceBean.setExchangePointAmount(NumUtil.toString(fcLine.getExchangePointAmount()));

      // クーポン名Link表示制御Flag
      if (bean.getGoodPoint() >= fcLine.getExchangePointAmount()) {
        pceBean.setDisplayLink(true);
      } else {
        pceBean.setDisplayLink(false);
      }
      pceBean.setIssueType(fcLine.getCouponIssueType());
      
      
      fcuBeanList.add(pceBean);
    }

    // 一覧表初期化
//    SearchResult<FriendCouponLine> resultList = cs.getFriendCouponLine(friendCoupoCcondition);

//    List<FriendCouponLine> fcList = resultList.getRows();
//
//    List<PrivateCouponExchangeBeanDetail> fcBeanList = new ArrayList<PrivateCouponExchangeBeanDetail>();

    // fcListのデータをBeanに設定する
//    for (FriendCouponLine fcLine : fcList) {
//      PrivateCouponExchangeBeanDetail pceBean = new PrivateCouponExchangeBeanDetail();
//      pceBean.setCustomerName(fcLine.getCustomerName());
//      if(StringUtil.hasValue(fcLine.getCouponAmount()) && fcLine.getCouponAmount().endsWith(".00")){
//        pceBean.setCouponAmount(fcLine.getCouponAmount().replace(".00", ""));
//      }
//      pceBean.setCouponCode(fcLine.getCouponCode());
//      pceBean.setCouponIssueDate(fcLine.getCouponIssueDate());
//      pceBean.setAllPoint(fcLine.getAllPoint());
//      pceBean.setOrderNo(fcLine.getOrderNo());
//      fcBeanList.add(pceBean);
//    }

    // 20140416 hdh add start
    List<FriendCouponIssueHistory> issueHistory = cs.getIssueHistoryByCustomerCode(getLoginInfo().getCustomerCode());
    List<FriendIssueHistoryDetail> issueDetailList = new ArrayList<FriendIssueHistoryDetail>();
    for (FriendCouponIssueHistory history : issueHistory) {
      FriendIssueHistoryDetail detail = new FriendIssueHistoryDetail();
      detail.setCouponCode(history.getCouponCode());
      detail.setCouponIssueType(CampaignType.fromValue(history.getCouponIssueType()).getName());
      detail.setIssueObtainPoint(NumUtil.toString(history.getIssueObtainPoint()));
      detail.setIssueDate(DateUtil.toDateString(history.getCreatedDatetime()));
      if (CampaignType.FIXED.getValue().equals(NumUtil.toString(history.getCouponIssueType()))) {
        detail.setCouponAmount(NumUtil.toString(history.getCouponAmount()));
      } else if (CampaignType.PROPORTION.getValue().equals(NumUtil.toString(history.getCouponIssueType()))) {
        detail.setCouponProportion(NumUtil.toString(history.getCouponProportion()) + "%");
      }
      issueDetailList.add(detail);

    }
    bean.setIssueFriendList(issueDetailList);

//    bean.setPrivateList(fcBeanList);
    bean.setUsePrivateList(fcuBeanList);

    // 好友优惠券模块
    
    
   
    
    // 会员有无发货记录
    String customerCode = getLoginInfo().getCustomerCode();
    SmsingService services = ServiceLocator.getSmsingService(getLoginInfo());
    List<ShippingHeader> shippingList = service.getShippingHeaderList(customerCode);

    // 取得未发行朋友介绍优惠券信息
    List<FriendCouponRule> unissuedInfoList = service.getUnissuedFriendCouponList(customerCode);

    // 取得已发行朋友介绍优惠券信息
    List<NewCouponRule> issuedInfoList = service.getIssuedFriendCouponList(customerCode);

    // 无朋友介绍优惠券
    if ((unissuedInfoList == null || unissuedInfoList.isEmpty()) && (issuedInfoList == null || issuedInfoList.isEmpty())) {

    } else {
      // 有朋友介绍优惠券
      List<FriendCouponBeanDetail> unissuedList = new ArrayList<FriendCouponBeanDetail>();
      List<FriendCouponBeanDetail> issuedList = new ArrayList<FriendCouponBeanDetail>();

      // 未发行优惠券  (无发货信息则无权限创建优惠券)
      if (shippingList != null && !shippingList.isEmpty()) {
        for (FriendCouponRule friendCouponRule : unissuedInfoList) {
          //订单数量(发货)天于优惠券要求订单数量
          if (shippingList.size() >= friendCouponRule.getOrderHistory()) {
            FriendCouponBeanDetail detailBean = new FriendCouponBeanDetail();
            String month = "";

            if (IssueDateType.DATE.getValue().equals(NumUtil.toString(friendCouponRule.getIssueDateType()))) {
              if (ValidatorUtil.inRange(DateUtil.getSysdate(), friendCouponRule.getIssueStartDate(), friendCouponRule.getIssueEndDate())) {
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
              if (NumUtil.toLong(month).equals(friendCouponRule.getIssueDateNum())) {
                setUnissuedInfo(detailBean, friendCouponRule);
                detailBean.setAbleFlg(false);
                unissuedList.add(detailBean);
              }
            }
          }
        }

        // 已发行优惠券   
        for (NewCouponRule issueHistoryCoupon : issuedInfoList) {
          //未过期
          if (ValidatorUtil.lessThanOrEquals(DateUtil.getSysdate(), issueHistoryCoupon.getMinUseEndDatetime())) {
            FriendCouponBeanDetail detailBean = new FriendCouponBeanDetail();
            detailBean.setFriendCouponRule(utilSev.getNameByLanguage(issueHistoryCoupon.getCouponName(), issueHistoryCoupon.getCouponNameEn(),
                issueHistoryCoupon.getCouponNameJp()));
            detailBean.setCouponAmount(NumUtil.toString(issueHistoryCoupon.getCouponAmount()));
            detailBean.setCouponCode(issueHistoryCoupon.getCouponCode());
            detailBean.setIssueEndDate(DateUtil.toDateString(issueHistoryCoupon.getMinUseEndDatetime()));
            detailBean.setIssueStartDate(DateUtil.toDateString(issueHistoryCoupon.getMinUseStartDatetime()));
            detailBean.setMinUseOrderAmount(NumUtil.toString(issueHistoryCoupon.getMinUseOrderAmount()));
            // 20140408 hdh edit start
            detailBean.setIssueType(issueHistoryCoupon.getCouponIssueType());
            if (CampaignType.FIXED.getValue().equals(NumUtil.toString(issueHistoryCoupon.getCouponIssueType()))) {
              detailBean.setCouponAmount(NumUtil.toString(issueHistoryCoupon.getCouponAmount()));
            } else if (CampaignType.PROPORTION.getValue().equals(NumUtil.toString(issueHistoryCoupon.getCouponIssueType()))) {
              detailBean.setRatio(NumUtil.toString(issueHistoryCoupon.getCouponProportion()));
              detailBean.setCouponAmount(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(issueHistoryCoupon
                  .getMinUseOrderAmount(), issueHistoryCoupon.getCouponProportion()), 100, 2, RoundingMode.HALF_UP)));
              detailBean.setMaxDiscountPrice(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(issueHistoryCoupon
                  .getMaxUseOrderAmount(), issueHistoryCoupon.getCouponProportion()), 100, 2, RoundingMode.HALF_UP)));
            }
            // 20140408 hdh edit end;

            String url = getConfig().getCouponUrl() + detailBean.getCouponCode();
            url = url.replaceAll("zh-cn", currentLanguageCode);
            detailBean.setUrl(url);

            detailBean.setApplicableObjects(NumUtil.toString(issueHistoryCoupon.getApplicableObjects()));
            issuedList.add(detailBean);
          }
        }
        bean.setUnissuedFriendList(unissuedList);
        bean.setIssuedFriendList(issuedList);
      }
    }

    // 无权限发行优惠券
    if ((bean.getUnissuedFriendList() == null || bean.getUnissuedFriendList().isEmpty())
        && (bean.getIssuedFriendList() == null || bean.getIssuedFriendList().isEmpty())) {
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

      bean.setFriendList(listBean);
    } else {
      bean.setIssueFlg(false);
    }
    String languageCode = services.getLanguageCode(customerCode);
    if (StringUtil.hasValue(languageCode)) {
      bean.setLanguageCode(languageCode);
    }
    // 将用户的手机号码提取
    bean.setTell(getLoginInfo().getMobileNumber());

    return bean;
  }

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
    if (CampaignType.FIXED.getValue().equals(NumUtil.toString(friendCouponRule.getCouponIssueType()))) {
      detailBean.setCouponAmount(NumUtil.toString(friendCouponRule.getCouponAmount()));
    } else if (CampaignType.PROPORTION.getValue().equals(NumUtil.toString(friendCouponRule.getCouponIssueType()))) {
      detailBean.setRatio(NumUtil.toString(friendCouponRule.getCouponProportion()));
      detailBean.setMaxDiscountPrice(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(friendCouponRule
          .getMaxUseOrderAmount(), friendCouponRule.getCouponProportion()), 100, 2, RoundingMode.HALF_UP)));
    }
    // 20140408 hdh edit end;
    detailBean.setMinUseOrderAmount(NumUtil.toString(friendCouponRule.getMinUseOrderAmount()));
    detailBean.setApplicableObjects(NumUtil.toString(friendCouponRule.getApplicableObjects()));
    detailBean.setUseValidType(friendCouponRule.getUseValidType());
    detailBean.setUseValidNum(friendCouponRule.getUseValidNum());

  }
}
