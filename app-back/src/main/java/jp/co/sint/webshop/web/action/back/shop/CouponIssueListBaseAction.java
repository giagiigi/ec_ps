package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CouponIssue;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.CouponIssueListBean;
import jp.co.sint.webshop.web.bean.back.shop.CouponIssueListBean.CouponIssueDetail;

/**
 * U1050510:支払方法一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class CouponIssueListBaseAction extends WebBackAction<CouponIssueListBean> {

  /**
   * 指定のショップに関連付いている支払方法リストを取得
   * 
   * @param shopCode
   * @return paymentMethodList
   */
  public List<CouponIssueDetail> getCouponIssueDetailList(String shopCode) {
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    List<CouponIssue> couponIssueList = service.getCouponIssueList(shopCode);

    List<CouponIssueDetail> couponIssueDetailList = new ArrayList<CouponIssueDetail>();
    for (CouponIssue couponIssue : couponIssueList) {
      CouponIssueDetail couponIssueDetail = new CouponIssueDetail();
      couponIssueDetail.setShopCode(couponIssue.getShopCode());
      couponIssueDetail.setCouponIssueNo(NumUtil.toString(couponIssue.getCouponIssueNo()));
      couponIssueDetail.setCouponIssueName(couponIssue.getCouponName());
      couponIssueDetail.setCouponPrice(NumUtil.toString(couponIssue.getCouponPrice()));
      couponIssueDetail.setGetCouponPrice(NumUtil.toString(couponIssue.getGetCouponPrice()));
      couponIssueDetail.setBonusCouponStartDate(DateUtil.toDateString(couponIssue.getBonusCouponStartDate()));
      couponIssueDetail.setBonusCouponEndDate(DateUtil.toDateString(couponIssue.getBonusCouponEndDate()));
      couponIssueDetail.setUseCouponStartDate(DateUtil.toDateString(couponIssue.getUseCouponStartDate()));
      couponIssueDetail.setUseCouponEndDate(DateUtil.toDateString(couponIssue.getUseCouponEndDate()));
      couponIssueDetail.setDeleteButtonFlg(true);
      couponIssueDetail.setAnalysisButtonFlg(true);
      couponIssueDetailList.add(couponIssueDetail);
    }
    return couponIssueDetailList;
  }
}
