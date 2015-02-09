package jp.co.sint.webshop.web.action.front.mypage;

import java.util.Date;

import jp.co.sint.webshop.data.domain.CouponStatus;
import jp.co.sint.webshop.data.domain.CouponUse;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.dto.FriendCouponExchangeHistory;
import jp.co.sint.webshop.data.dto.FriendCouponUseHistory;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.FriendCouponSearchCondition;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.mypage.NewMyCouponBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * @author System Integrator Corp.
 */
public class NewMyCouponPrivateRegisterAction extends NewMyCouponBaseAction {

  // 处理成功消息
  private String curCouponCode;

  private FriendCouponSearchCondition condition;

  protected FriendCouponSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected FriendCouponSearchCondition getSearchCondition() {
    return this.condition;
  }

  protected void setCondition(FriendCouponSearchCondition condition) {
    this.condition = condition;
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    NewMyCouponBean bean = getBean();
    bean.setTabIndex("1");
    String couponCode = "";

    // URLパラメータの取得
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter != null && parameter.length > 0) {
      couponCode = parameter[0];
    }

    // 有効ポイント取得
    CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());
    Long goodPoint = cs.getFriendCouponUseHistoryAllPoint(getLoginInfo().getCustomerCode());

    // 　クーポンルールコードを指定してデータを検索する
    NewCouponRule ncRule = cs.getNewCouponRuleByCouponCode(couponCode);
    FriendCouponUseHistory fcRule = cs.getFriendCouponUseHistory(couponCode);

    if (ncRule != null) {
      if (goodPoint >= ncRule.getExchangePointAmount()) {
        // 履歴を登録【优惠券发行履历】
        NewCouponHistory newCouponHistoryDto = new NewCouponHistory();
        newCouponHistoryDto.setCouponCode(ncRule.getCouponCode());
        cs.getCouponIssueNo(newCouponHistoryDto);
        newCouponHistoryDto.setCouponIssueType(ncRule.getCouponIssueType());
        newCouponHistoryDto.setCouponName(ncRule.getCouponName());
        newCouponHistoryDto.setCouponNameEn(ncRule.getCouponNameEn());
        newCouponHistoryDto.setCouponNameJp(ncRule.getCouponNameJp());
        newCouponHistoryDto.setCouponIssueDatetime(DateUtil.getSysdate());
        newCouponHistoryDto.setCouponProportion(ncRule.getCouponProportion());
        newCouponHistoryDto.setCouponAmount(ncRule.getCouponAmount());
        newCouponHistoryDto.setUseType(ncRule.getUseType());
        Long startNum = 0L;
        Long endNum = 0L;
        if (ncRule.getMinUseStartNum() != null) {
          startNum = ncRule.getMinUseStartNum();
        }
        if (ncRule.getMinUseEndNum() != null) {
          endNum = ncRule.getMinUseEndNum();
        }
        newCouponHistoryDto.setUseStartDatetime(DateUtil.addDate(DateUtil.getSysdate(), Integer
            .parseInt(NumUtil.toString(startNum))));

        Date endDatetimeTemp = DateUtil.addDate(newCouponHistoryDto.getUseStartDatetime(), Integer.parseInt(NumUtil
            .toString(endNum - 1L)));
        Date endDatetime = DateUtil.fromString(DateUtil.getYYYY(endDatetimeTemp) + "/" + DateUtil.getMM(endDatetimeTemp) + "/"
            + DateUtil.getDD(endDatetimeTemp) + " 23:59:59", true);
        newCouponHistoryDto.setUseEndDatetime(endDatetime);
        if (endNum == 0L) {
          newCouponHistoryDto.setUseEndDatetime(endDatetimeTemp);
        }

        newCouponHistoryDto.setIssueReason(ncRule.getIssueReason());
        newCouponHistoryDto.setCustomerCode(getLoginInfo().getCustomerCode());
        newCouponHistoryDto.setUseStatus(NumUtil.toLong(CouponUse.UNUSED.getValue()));
        newCouponHistoryDto.setCouponStatus(NumUtil.toLong(CouponStatus.USED.getValue()));
        if (ncRule.getMaxUseOrderAmount() != null) {
          newCouponHistoryDto.setMaxUseOrderAmount(ncRule.getMaxUseOrderAmount());
        } else {
          newCouponHistoryDto.setMaxUseOrderAmount(DIContainer.getWebshopConfig().getDefaultMaxUseOrderAmount());
        }
        newCouponHistoryDto.setMinUseOrderAmount(ncRule.getMinUseOrderAmount());

        if (fcRule != null) {
          newCouponHistoryDto.setIssueOrderNo("");
          newCouponHistoryDto.setUseOrderNo("");
        }

        // 履歴登録【友達紹介ｸｰﾎﾟﾝの発行友達兑换履历】
        FriendCouponExchangeHistory friendCouponExchangeHistoryDto = new FriendCouponExchangeHistory();
        friendCouponExchangeHistoryDto.setCouponIssueNo(newCouponHistoryDto.getCouponIssueNo());
        friendCouponExchangeHistoryDto.setExchangeDate(DateUtil.getSysdate());
        friendCouponExchangeHistoryDto.setExchangePoint(ncRule.getExchangePointAmount());
        friendCouponExchangeHistoryDto.setCustomerCode(getLoginInfo().getCustomerCode());

        // 履歴を登録
        ServiceResult sResult = cs.insertFriendHistory(newCouponHistoryDto, friendCouponExchangeHistoryDto);

        // 履歴登録結果チェック
        if (sResult.hasError()) {
          addErrorMessage(WebMessage.get(MypageErrorMessage.COUPON_EXCHANGE_UNSUCCESS));
          setRequestBean(bean);
          return FrontActionResult.RESULT_SUCCESS;
        }
      } else {
        addErrorMessage(WebMessage.get(MypageErrorMessage.COUPON_EXCHANGE_UNSUCCESS));
        setRequestBean(bean);
        return FrontActionResult.RESULT_SUCCESS;
      }
    } else {
      addErrorMessage(WebMessage.get(MypageErrorMessage.COUPON_EXCHANGE_UNSUCCESS));
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    // // メールを送信する
    // MailingService sendMail =
    // ServiceLocator.getMailingService(getLoginInfo());
    // sendMail.sendFriendCouponMail(getLoginInfo().getCustomerCode());
    setRequestBean(bean);

    // 履歴登録完了
    // setNextUrl("/app/mypage/new_my_coupon/init/" +
    // WebConstantCode.COMPLETE_INSERT + "/" + ncRule.getCouponCode());
    curCouponCode = ncRule.getCouponCode();

    return FrontActionResult.RESULT_SUCCESS;
  }

  public void prerender() {
    String couponName = "";
    if (StringUtil.hasValue(curCouponCode)) {
      // クーポンコードを指定してクーポン名を取得します
      CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());
      NewCouponRule newCRDto = cs.getNewCouponRuleByCouponCode(curCouponCode);
      if (newCRDto != null) {
        String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
        if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
          couponName = newCRDto.getCouponName();
        } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
          couponName = newCRDto.getCouponNameJp();
        } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
          couponName = newCRDto.getCouponNameEn();
        }
          addInformationMessage(WebMessage.get(MypageErrorMessage.COUPON_EXCHANGE_SUCCESS));
      } else {
        addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      }
    }

  }

  /**
   * @return the curCouponCode
   */
  public String getCurCouponCode() {
    return curCouponCode;
  }

  /**
   * @param curCouponCode
   *          the curCouponCode to set
   */
  public void setCurCouponCode(String curCouponCode) {
    this.curCouponCode = curCouponCode;
  }

}
