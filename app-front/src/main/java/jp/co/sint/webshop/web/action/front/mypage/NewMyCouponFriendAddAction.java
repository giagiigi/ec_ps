package jp.co.sint.webshop.web.action.front.mypage;

import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.domain.BeforeAfterDiscountType;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.Day;
import jp.co.sint.webshop.data.domain.Month;
import jp.co.sint.webshop.data.domain.UseValidType;
import jp.co.sint.webshop.data.domain.Year;
import jp.co.sint.webshop.data.dto.FriendCouponIssueHistory;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * 
 * @author System OB.
 */
public class NewMyCouponFriendAddAction extends NewMyCouponBaseAction {
  
  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    getBean().setTabIndex("2");
    String[] url = getRequestParameter().getPathArgs();
    if (url == null || url.length != 1) {
      addErrorMessage(WebMessage.get(MypageErrorMessage.NOT_USED_REMINDER));
      setRequestBean(initNewMyCouponBean(getBean()));
      return FrontActionResult.RESULT_SUCCESS;
    } else {
      String friendCouponRuleNo = url[0];
      // 取得选中朋友介绍优惠券信息
      CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
      FriendCouponRule ruleDto = service.getFriendCouponRule(friendCouponRuleNo);

      if (ruleDto == null) {
        addErrorMessage(WebMessage.get(MypageErrorMessage.NOT_USED_REMINDER));
        setRequestBean(initNewMyCouponBean(getBean()));
        return FrontActionResult.RESULT_SUCCESS;
      }
      
     // 会员有无发货记录
      List<ShippingHeader> shippingList = service.getShippingHeaderList(getLoginInfo().getCustomerCode());
      
      if (shippingList == null || shippingList.size() < 1 || shippingList.size() < ruleDto.getOrderHistory()) {
        addErrorMessage(Messages.getString("web.action.front.mypage.FriendCouponAddAction.0"));
        setRequestBean(initNewMyCouponBean(getBean()));
        return FrontActionResult.RESULT_SUCCESS;
      } 
      

      // 随机取得优惠券规则编号的重复性check
      String couponCode = "";
      while (couponCode == "" || service.getNewCouponRuleByCouponCode(couponCode) != null) {
        // 0-9 a-z A-Z中随机出n个字符 放入数组
        String random = StringUtil.getRandom(3);

        // 发行规则编号取得
        String sYear = "";
        String sMonth = "";
        String sDay = "";
        Date date = new Date();
        String year = DateUtil.getYYYY(date);
        String month = DateUtil.getMM(date);
        String day = DateUtil.getDD(date);
        sYear = Year.fromValue(year).getName();
        sMonth = Month.fromValue(month).getName();
        sDay = Day.fromValue(day).getName();
        couponCode = ruleDto.getFixChar() + sYear + sMonth + sDay + random;
      }

      // 朋友介绍发行履历DTO做成
      FriendCouponIssueHistory historyDto = setFriendCouponIssueHistoryDto(ruleDto);
      historyDto.setCouponCode(couponCode);

      // 优惠券规则DTO做成
      NewCouponRule newCouponRuleDto = setNewCouponRuleDto(ruleDto);
      newCouponRuleDto.setCouponCode(couponCode);

      // 优惠券发行操作
      ServiceResult result = service.insertCouponFriend(historyDto, newCouponRuleDto);
      if (result.hasError()) {
        setRequestBean(initNewMyCouponBean(getBean()));
        for (ServiceErrorContent error : result.getServiceErrorList()) {

          if (CommonServiceErrorContent.VALIDATION_ERROR.equals(error)) {
            return FrontActionResult.SERVICE_VALIDATION_ERROR;
          }
          if (CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR.equals(error)) {
            return FrontActionResult.SERVICE_ERROR;
          }

        }
      }else{
        //UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
        //String couponName = utilService.getNameByLanguage(ruleDto.getFriendCouponRuleCn(),ruleDto.getFriendCouponRuleEn(),ruleDto.getFriendCouponRuleJp());
        addInformationMessage(Messages.getString("web.action.front.mypage.NewMyCouponFriendAddAction.0"));
      }
    }
    setRequestBean(initNewMyCouponBean(getBean()));
//    setNextUrl("/app/mypage/new_my_coupon/init");

    return FrontActionResult.RESULT_SUCCESS;
  }

  // 朋友介绍发行履历DTO做成
  private FriendCouponIssueHistory setFriendCouponIssueHistoryDto(FriendCouponRule friendCouponRuleDto) {
    FriendCouponIssueHistory historyDto = new FriendCouponIssueHistory();
    
    historyDto.setFriendCouponRuleNo(friendCouponRuleDto.getFriendCouponRuleNo());
    historyDto.setCouponIssueDate(DateUtil.getSysdate());
    historyDto.setCustomerCode(getLoginInfo().getCustomerCode());
    historyDto.setObtainPoint(friendCouponRuleDto.getObtainPoint());
    // 20140409 hdh add start
    historyDto.setFormerUsePoint(friendCouponRuleDto.getFormerUsePoint());
    historyDto.setIssueObtainPoint(friendCouponRuleDto.getIssueObtainPoint());
    historyDto.setCouponIssueType(friendCouponRuleDto.getCouponIssueType());
    historyDto.setCouponUseNum(friendCouponRuleDto.getCouponUseNum());
    if(CampaignType.PROPORTION.getValue().equals(NumUtil.toString(friendCouponRuleDto.getCouponIssueType()))){
      historyDto.setCouponProportion(friendCouponRuleDto.getCouponProportion());
      historyDto.setCouponAmount(BigDecimalUtil.divide(BigDecimalUtil.multiply(friendCouponRuleDto.getMinUseOrderAmount(), friendCouponRuleDto .getCouponProportion()), 100, 2, RoundingMode.HALF_UP));
      
      
    }else if(CampaignType.FIXED.getValue().equals(NumUtil.toString(friendCouponRuleDto.getCouponIssueType()))){
      historyDto.setCouponAmount(friendCouponRuleDto.getCouponAmount());
    }
    
    // 20140409 hdh add end
    return historyDto;
  }

  // 优惠券规则DTO做成
  private NewCouponRule setNewCouponRuleDto(FriendCouponRule friendCouponRuleDto) {
    NewCouponRule couponDto = new NewCouponRule();
    couponDto.setCouponName(friendCouponRuleDto.getFriendCouponRuleCn());
    couponDto.setCouponNameEn(friendCouponRuleDto.getFriendCouponRuleEn());
    couponDto.setCouponNameJp(friendCouponRuleDto.getFriendCouponRuleJp());
    couponDto.setMinIssueStartDatetime(DateUtil.getSysdate());
    couponDto.setPersonalUseLimit(BigDecimalUtil.tempFormatLong(friendCouponRuleDto.getPersonalUseLimit(), null));
    couponDto.setSiteUseLimit(BigDecimalUtil.tempFormatLong(friendCouponRuleDto.getSiteUseLimit(), null));
    couponDto.setCouponType(CouponType.COMMON_DISTRIBUTION.longValue());
    //couponDto.setCouponIssueType(CouponIssueType.FIXED.longValue());
    couponDto.setBeforeAfterDiscountType(BeforeAfterDiscountType.BEFOREDISCOUNT.longValue());
    couponDto.setApplicableObjects(friendCouponRuleDto.getApplicableObjects());
   
    couponDto.setMinUseOrderAmount(friendCouponRuleDto.getMinUseOrderAmount());
    // 20140416 hdh update start
    couponDto.setMaxUseOrderAmount(friendCouponRuleDto.getMaxUseOrderAmount());
    // 20140416 hdh update end
    couponDto.setMinUseStartDatetime(DateUtil.getSysdate());
    // 20140409 hdh add start
    couponDto.setCouponIssueType(friendCouponRuleDto.getCouponIssueType());
    if(CampaignType.FIXED.getValue().equals(NumUtil.toString(friendCouponRuleDto.getCouponIssueType()))){
      couponDto.setCouponAmount(friendCouponRuleDto.getCouponAmount());
    }else if(CampaignType.PROPORTION.getValue().equals(NumUtil.toString(friendCouponRuleDto.getCouponIssueType()))){
      couponDto.setCouponProportion(friendCouponRuleDto.getCouponProportion());
    }
    // 20140409 hdh add end;
    Date endDatetimeTemp = new Date();
    
    // 有效期按月计算时
    if (UseValidType.MONTH.getValue().equals(NumUtil.toString(friendCouponRuleDto.getUseValidType()))) {
      endDatetimeTemp = DateUtil.addMonth(couponDto.getMinUseStartDatetime(), Integer.valueOf(NumUtil.toString(friendCouponRuleDto.getUseValidNum(), "0")));
    } else {// 有效期按天计算
      endDatetimeTemp = DateUtil.addDate(couponDto.getMinUseStartDatetime(), Integer.valueOf(NumUtil.toString(friendCouponRuleDto.getUseValidNum(), "0")));
    }
    endDatetimeTemp = DateUtil.addDate(endDatetimeTemp, -1);
    
    Date endDatetime = DateUtil.fromString(DateUtil.getYYYY(endDatetimeTemp) + "/" + DateUtil.getMM(endDatetimeTemp) + "/" + DateUtil.getDD(endDatetimeTemp) + " 23:59:59", true);
    couponDto.setMinUseEndDatetime(endDatetime);
    
    couponDto.setMinIssueEndDatetime(endDatetime);
    
    // 20140922 hdh add start  朋友优惠券中相关商品品牌分类
    couponDto.setUseType(friendCouponRuleDto.getUseType());
    couponDto.setObjectBrand(friendCouponRuleDto.getObjectBrand());
    couponDto.setObjectCategory(friendCouponRuleDto.getObjectCategory());
    couponDto.setObjectCommodities(friendCouponRuleDto.getObjectCommodities());
    // 20140922 hdh add end

    return couponDto;
  }

  /**
   * 
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
    String[] url = getRequestParameter().getPathArgs();
    if (url.length == 1) {
      String friendCouponRuleNo = url[0];
      // 该优惠券是否已发行
      CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
      if (service.existsFriendCouponIssueHistory(friendCouponRuleNo, getLoginInfo().getCustomerCode())) {
        return false;
      }
    }
    return true;
  }

}
