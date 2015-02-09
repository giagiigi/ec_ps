package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CouponFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.CouponUsedFlg;
import jp.co.sint.webshop.data.domain.PointIssueType;
import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.customer.PointStatusAllSearchInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CouponListBean;
import jp.co.sint.webshop.web.bean.back.customer.CouponListBean.CouponDetailBean;

/**
 * U1030140:ポイント履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class CouponListBaseAction extends WebBackAction<CouponListBean> {

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
  public abstract WebActionResult callService();

//  /**
//   * 検索条件を設定します。<BR>
//   * 
//   * @param searchBean
//   * @param condition
//   */
//  public void setSearchCondition(PointHistoryBean searchBean, PointStatusListSearchCondition condition) {
//    condition.setSearchShopCode(searchBean.getSearchShopCode());
//    condition.setSearchIssueType(searchBean.getSearchIssueType());
//  }
//
//  /**
//   * ポイント履歴の検索結果一覧を作成します。<BR>
//   * 
//   * @param nextBean
//   *          ポイント履歴
//   * @param pointList
//   *          ポイント履歴一覧
//   */
//  public void setPointHistoryList(PointHistoryBean nextBean, List<PointStatusAllSearchInfo> pointList) {
//    List<PointHistoryDetailList> list = nextBean.getList();
//
//    for (PointStatusAllSearchInfo ph : pointList) {
//      PointHistoryDetailList detail = new PointHistoryDetailList();
//      detail.setPublisher(PointIssueType.fromValue(ph.getPointIssueType()).getName());
//      detail.setPublisherId(getPublisherId(ph));
//      detail.setShopCode(ph.getShopCode());
//      detail.setShopName(ph.getShopName());
//      detail.setDescription(ph.getDescription());
//      detail.setPointIssueStatus(PointIssueStatus.fromValue(ph.getPointIssueStatus()).getName());
//      detail.setIssuedPoint(ph.getIssuedPoint());
//      detail.setPointIssueDatetime(ph.getPointIssueDatetime());
//
//      list.add(detail);
//    }
//
//    nextBean.setList(list);
//  }

  /**
   * 発行IDを取得します
   * 
   * @param ph
   * @return 発行ID
   */
  public String getPublisherId(PointStatusAllSearchInfo ph) {
    String publisherId = "";
    if (ph.getPointIssueType().equals(PointIssueType.ORDER.getValue())) {
      publisherId = ph.getOrderNo();
    } else if (ph.getPointIssueType().equals(PointIssueType.REVIEW.getValue())) {
      publisherId = ph.getReviewId();
    } else if (ph.getPointIssueType().equals(PointIssueType.ENQUETE.getValue())) {
      publisherId = ph.getEnqueteCode();
    }

    return publisherId;
  }

  @Override
  public void prerender() {
    Object obean = getRequestBean();
    if (obean instanceof CouponListBean) {
      CouponListBean bean = (CouponListBean) obean;
      SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
      CouponRule rule = service.getCouponRule();
      Boolean usableCoupon = Boolean.valueOf(CouponFunctionEnabledFlg.ENABLED.longValue().equals(rule.getCouponFunctionEnabledFlg()));
      bean.setUsableCouponSystem(usableCoupon);
      if (!bean.getUsableCouponSystem()) {
        // ポイントシステム使用不可の場合更新・削除処理は不可とする
        bean.setUpdateMode(false);
        bean.setDeleteMode(false);
      } else {
        bean.setUpdateMode(true);
        bean.setDeleteMode(true);
      }
      setRequestBean(bean);
    }
  } 
  
  protected void setCouponHistoryList(CouponListBean nextBean, List<CustomerCoupon> couponList) {
    List<CouponDetailBean> list = new  ArrayList<CouponDetailBean>();
    for (CustomerCoupon cc : couponList) {
      CouponDetailBean cdb = new CouponDetailBean();
      cdb.setCustomerCouponId(NumUtil.toString(cc.getCustomerCouponId()));
      cdb.setCouponName(cc.getCouponName());
      cdb.setCouponPrice(NumUtil.toString(cc.getCouponPrice()));
      cdb.setCouponStatus(CouponUsedFlg.fromValue(cc.getUseFlg()).getName());
      cdb.setUseCouponEndDate(DateUtil.toDateString(cc.getUseCouponEndDate()));
      cdb.setUseCouponStartDate(DateUtil.toDateString(cc.getUseCouponStartDate()));
      cdb.setUseDate(DateUtil.toDateString(cc.getUseDate()));
      cdb.setIssueDate(DateUtil.toDateString(cc.getIssueDate()));
      cdb.setGetCouponOrderNo(cc.getGetCouponOrderNo());
      cdb.setUseCouponOrderNo(cc.getOrderNo());
      list.add(cdb);
    }
    nextBean.setList(list);
  }
}
