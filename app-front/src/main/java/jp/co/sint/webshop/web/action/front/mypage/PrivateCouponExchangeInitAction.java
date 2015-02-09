package jp.co.sint.webshop.web.action.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.dto.FriendCouponIssueHistory;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.FriendCouponLine;
import jp.co.sint.webshop.service.FriendCouponSearchCondition;
import jp.co.sint.webshop.service.FriendCouponUseLine;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.PrivateCouponExchangeBean;
import jp.co.sint.webshop.web.bean.front.mypage.PrivateCouponExchangeBean.FriendIssueHistoryDetail;
import jp.co.sint.webshop.web.bean.front.mypage.PrivateCouponExchangeBean.PrivateCouponExchangeBeanDetail;
import jp.co.sint.webshop.web.bean.front.mypage.PrivateCouponExchangeBean.PrivateCouponUseDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * @author System Integrator Corp.
 */
public class PrivateCouponExchangeInitAction extends WebFrontAction<PrivateCouponExchangeBean> {

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
   * 初期処理を実行します
   */
  @Override
  public void init() {

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

    PrivateCouponExchangeBean bean = new PrivateCouponExchangeBean();

    condition = new FriendCouponSearchCondition();

    condition = getCondition();
    condition.setCustomerCode(getLoginInfo().getCustomerCode());

    // 有効ポイントを設定する
    CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());
    bean.setGoodPoint(cs.getFriendCouponUseHistoryAllPoint(getLoginInfo().getCustomerCode()));

    // 両替可能のクーポンを取得
    List<FriendCouponUseLine> friendCouponUseList = cs.getFriendCouponUseLine();

    List<PrivateCouponUseDetail> fcuBeanList = new ArrayList<PrivateCouponUseDetail>();

    // friendCouponUseListのデータをBeanに設定する
    for (FriendCouponUseLine fcLine : friendCouponUseList) {
      PrivateCouponUseDetail pceBean = new PrivateCouponUseDetail();
      
      if(CouponIssueType.FIXED.getValue().equals(fcLine.getCouponIssueType())){
        pceBean.setCouponAmount(fcLine.getCouponAmount());
        pceBean.setCouponFlag(false);
      } else if (CouponIssueType.PROPORTION.getValue().equals(fcLine.getCouponIssueType())){
         pceBean.setCouponAmount(fcLine.getCouponProportion());
         pceBean.setCouponFlag(true);
      }
   
      pceBean.setCouponName(fcLine.getCouponName());
      
      String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        pceBean.setCouponName(fcLine.getCouponName());
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        pceBean.setCouponName(fcLine.getCouponNameJp());
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        pceBean.setCouponName(fcLine.getCouponNameEn());
      }
      
      pceBean.setMinUseOrderAmount(fcLine.getMinUseOrderAmount());
      pceBean.setMinUseEndNum(fcLine.getMinUseEndNum());
      pceBean.setCouponCode(fcLine.getCouponCode());
      pceBean.setExchangePointAmount(NumUtil.toString(fcLine.getExchangePointAmount()));

      // クーポン名Link表示制御Flag
      if (bean.getGoodPoint() >= fcLine.getExchangePointAmount()) {
        pceBean.setDisplayLink(true);
      } else {
        pceBean.setDisplayLink(false);
      }
      fcuBeanList.add(pceBean);
    }

    // 一覧表初期化
    SearchResult<FriendCouponLine> resultList = cs.getFriendCouponLine(condition);

    List<FriendCouponLine> fcList = resultList.getRows();


    List<PrivateCouponExchangeBeanDetail> fcBeanList = new ArrayList<PrivateCouponExchangeBeanDetail>();

    // fcListのデータをBeanに設定する
    for (FriendCouponLine fcLine : fcList) {
      PrivateCouponExchangeBeanDetail pceBean = new PrivateCouponExchangeBeanDetail();
      pceBean.setCustomerName(fcLine.getCustomerName());
      pceBean.setCouponAmount(fcLine.getCouponAmount());
      pceBean.setCouponCode(fcLine.getCouponCode());
      pceBean.setCouponIssueDate(fcLine.getCouponIssueDate());
      pceBean.setAllPoint(fcLine.getAllPoint());
      pceBean.setOrderNo(fcLine.getOrderNo());
      fcBeanList.add(pceBean);
    }
    
    // 20140416 hdh add start
    List<FriendCouponIssueHistory>  issueHistory = cs.getIssueHistoryByCustomerCode(getLoginInfo().getCustomerCode());
    List<FriendIssueHistoryDetail> issueDetailList = new ArrayList<FriendIssueHistoryDetail>();
    for(FriendCouponIssueHistory history:issueHistory){
      FriendIssueHistoryDetail detail = new FriendIssueHistoryDetail();
      detail.setCouponCode(history.getCouponCode());
      detail.setCouponIssueType(CampaignType.fromValue(history.getCouponIssueType()).getName());
      detail.setIssueObtainPoint(NumUtil.toString(history.getIssueObtainPoint()));
      detail.setIssueDate(DateUtil.toDateString(history.getCreatedDatetime()));
      if(CampaignType.FIXED.getValue().equals(NumUtil.toString(history.getCouponIssueType()))){
        detail.setCouponAmount(NumUtil.toString(history.getCouponAmount()));
      }else if(CampaignType.PROPORTION.getValue().equals(NumUtil.toString(history.getCouponIssueType()))){
        detail.setCouponProportion(NumUtil.toString(history.getCouponProportion())+"%");
      }
      issueDetailList.add(detail);
      
    }
    bean.setIssueList(issueDetailList);
    // 20140416 hdh add end
    
    bean.setList(fcBeanList);
    bean.setUseList(fcuBeanList);
    
    bean.setPagerValue(PagerUtil.createValue(resultList));
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  public void prerender() {
    String couponName = "";
    if (getRequestParameter().getPathArgs().length >= 2) {
      // クーポンコードを指定してクーポン名を取得します
      String couponCode = getRequestParameter().getPathArgs()[1];
      CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());
      NewCouponRule newCRDto = cs.getNewCouponRuleByCouponCode(couponCode);
      if(newCRDto != null){
        String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
        if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
          couponName = newCRDto.getCouponName(); 
        } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
          couponName = newCRDto.getCouponNameJp(); 
        } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
          couponName = newCRDto.getCouponNameEn(); 
        }
        if (WebConstantCode.COMPLETE_INSERT.equals(getRequestParameter().getPathArgs()[0])) {
          addInformationMessage(WebMessage.get(MypageErrorMessage.COUPON_EXCHANGE_SUCCESS, couponName));
        }
      } else {
        addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      }
    }
  }

}
