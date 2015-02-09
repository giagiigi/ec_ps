package jp.co.sint.webshop.web.action.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CouponUsedFlg;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.CouponListBean;
import jp.co.sint.webshop.web.bean.front.mypage.CouponListBean.CouponIssueDetail;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;

/**
 * U2030610:ご注文履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CouponListInitAction extends WebFrontAction<CouponListBean> {


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
    CouponListBean bean = getBean();
    if (bean == null) {
      bean = new CouponListBean();
    }
    FrontLoginInfo login = getLoginInfo();
    
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    List<CustomerCoupon> list =service.getCustomerCouponList(login.getCustomerCode());
    List<CouponIssueDetail> couponList = new ArrayList<CouponIssueDetail>();
    for (CustomerCoupon cc: list) {
      CouponIssueDetail couponIssueDetail = new CouponIssueDetail();
      couponIssueDetail.setCustomerCouponId(NumUtil.toString(cc.getCustomerCouponId()));
      couponIssueDetail.setCouponIssueName(cc.getCouponName());
      couponIssueDetail.setUseCouponStartDate(DateUtil.toDateString(cc.getUseCouponStartDate()));
      couponIssueDetail.setUseCouponEndDate(DateUtil.toDateString(cc.getUseCouponEndDate()));
      couponIssueDetail.setCouponPrice(NumUtil.toString(cc.getCouponPrice()));
      couponIssueDetail.setOrderNo(cc.getOrderNo());
      couponIssueDetail.setUseDate(DateUtil.toDateString(cc.getUseDate()));
      couponIssueDetail.setUseFlg(CouponUsedFlg.fromValue(cc.getUseFlg()).getName());
      couponList.add(couponIssueDetail);
    }
    bean.setCouponIssueList(couponList);

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
