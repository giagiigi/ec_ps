package jp.co.sint.webshop.web.action.front.mypage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jp.co.sint.webshop.data.domain.UseStatus;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.CouponHistoryListBean;
import jp.co.sint.webshop.web.bean.front.mypage.CouponHistoryListBean.CouponHistoryListDetail;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * 我的优惠劵
 * 
 * @author
 */
public class CouponhistoryListInitAction extends WebFrontAction<CouponHistoryListBean> {

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
  public WebActionResult callService() {
    CouponHistoryListBean bean = getBean();
    if (bean == null) {
      bean = new CouponHistoryListBean();
    }
    FrontLoginInfo login = getLoginInfo();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    List<NewCouponHistory> list = service.getCouponHistoryList(login.getCustomerCode());

    List<CouponHistoryListDetail> couponHistoryList = new ArrayList<CouponHistoryListDetail>();
    for (NewCouponHistory cc : list) {
      CouponHistoryListDetail couponHistoryListDetail = new CouponHistoryListDetail();
      // 优惠券编号
      couponHistoryListDetail.setCouponIssueNo(cc.getCouponIssueNo());
      // 优惠券规则编号
      couponHistoryListDetail.setCouponCode(cc.getCouponCode());
      // 优惠券明细编
      couponHistoryListDetail.setCouponIssueDetailNo(cc.getCouponIssueDetailNo());
      // 优惠券发行类别
      couponHistoryListDetail.setCouponIssueType(Long.toString(cc.getCouponIssueType()));
      // 优惠券发行日时
      couponHistoryListDetail.setCouponIssueDatetime(DateUtil.toDateString(cc.getCouponIssueDatetime()));
      // 优惠券比例
      couponHistoryListDetail.setCouponProportion(cc.getCouponProportion());
      // 优惠金额
      couponHistoryListDetail.setCouponAmount(cc.getCouponAmount().toString());
      // 优惠券利用最小购买金额
      couponHistoryListDetail.setMinUseOrderAmount(cc.getMinUseOrderAmount().toString());
      // 优惠券利用开始日时
      couponHistoryListDetail.setUseStartDatetime(DateUtil.toDateAndHourString(cc.getUseStartDatetime()));
      // 优惠券利用结束日时
      couponHistoryListDetail.setUseEndDatetime(DateUtil.toDateAndHourString(cc.getUseEndDatetime()));
      // 发行理由
      couponHistoryListDetail.setIssueReason(cc.getIssueReason());
      // 顾客编号
      couponHistoryListDetail.setCustomerCode(cc.getCustomerCode());
      // 使用状态
      
      // 20120131 ysy add start
      // 优惠券名称
      couponHistoryListDetail.setCouponName(cc.getCouponName());
      //当前时间
      Date now = new Date();
      if(cc.getUseStartDatetime().getTime()<=now.getTime()&&cc.getUseEndDatetime().getTime()>=now.getTime()){
        couponHistoryListDetail.setUseStatus(UseStatus.fromValue(cc.getUseStatus()).getName());
      }else if(cc.getUseStartDatetime().getTime()>now.getTime()){
        couponHistoryListDetail.setUseStatus(Messages.getString("web.action.front.mypage.CouponhistoryListInitAction.0"));
      }else {
    	  couponHistoryListDetail.setUseStatus(Messages.getString("web.action.front.mypage.CouponhistoryListInitAction.1"));  
      }
       // 20120131 ysy add end
      couponHistoryListDetail.setIssueOrderNo(cc.getIssueOrderNo());
      couponHistoryList.add(couponHistoryListDetail);
    }
    bean.setList(couponHistoryList);

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
