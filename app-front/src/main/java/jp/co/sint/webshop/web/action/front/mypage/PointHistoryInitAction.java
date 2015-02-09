package jp.co.sint.webshop.web.action.front.mypage;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.PointIssueStatus;
import jp.co.sint.webshop.data.domain.PointIssueType;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.customer.CustomerPointInfo;
import jp.co.sint.webshop.service.customer.PointStatusAllSearchInfo;
import jp.co.sint.webshop.service.customer.PointStatusListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil; // 10.1.3 10102 追加
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.PointHistoryBean;
import jp.co.sint.webshop.web.bean.front.mypage.PointHistoryBean.PointHistoryDetail;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U2030710:ポイント履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PointHistoryInitAction extends WebFrontAction<PointHistoryBean> {

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
    // ポイントルール無効の場合、トップ画面へ遷移
    SiteManagementService shopSv = ServiceLocator.getSiteManagementService(getLoginInfo());
    PointRule pointRule = shopSv.getPointRule();
    if (pointRule.getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.DISABLED.longValue())) {
      setNextUrl("/app/common/index");
      setRequestBean(getBean());

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    PointHistoryBean bean = new PointHistoryBean();
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    // セッションから顧客コードを取得
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();

    if (cs.isNotFound(customerCode) || cs.isInactive(customerCode)) {
      setNextUrl("/app/common/index");
      setRequestBean(getBean());

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    // 顧客ポイント関連情報表示
    CustomerPointInfo info = cs.getCustomerPointInfo(customerCode);

    // 存在チェック
    if (info == null) {
      setNextUrl("/app/common/index");
      setRequestBean(getBean());

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    bean.setLastName(info.getLastName());
    bean.setFirstName(info.getFirstName());
    if (info.getRestPoint() != null) {
      // 10.1.4 10170 修正 ここから
      // bean.setRestPoint(Long.toString(info.getRestPoint()));
      bean.setRestPoint(NumUtil.toString(cs.getCustomer(customerCode, true).getCustomer().getRestPoint()));
      // 10.1.4 10170 修正 ここまで
    }
    if (info.getExpiredPointDate() != null) {
      bean.setPointExpirationDate(DateUtil.toDateString(info.getExpiredPointDate()));
    }

    // 検索条件を取得(有効なポイント履歴だけ表示し、仮発行/無効の履歴は表示しない)
    PointStatusListSearchCondition condition = new PointStatusListSearchCondition();
    condition.setSearchCustomerCode(customerCode);
    condition.setSearchPointIssueStatus(PointIssueStatus.ENABLED.getValue());

    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // ポイント履歴取得
    SearchResult<PointStatusAllSearchInfo> pList = cs.findPointStatusCustomerInfo(condition);

    PointHistoryBean nextBean = bean;
    nextBean.getList().clear();

    if (pList != null) {
      nextBean.setPagerValue(PagerUtil.createValue(pList));

      List<PointStatusAllSearchInfo> pointList = pList.getRows();
      List<PointHistoryDetail> list = nextBean.getList();

      for (PointStatusAllSearchInfo ph : pointList) {
        PointHistoryDetail detail = new PointHistoryDetail();

        detail.setIssuedTypes(PointIssueType.fromValue(ph.getPointIssueType()).getName());
        // 10.1.3 10102 修正 ここから
        // detail.setPointIssueDatetime(ph.getPointIssueDatetime());
        detail.setPointIssueDatetime(DateUtil.toDateString(DateUtil.fromString(ph.getPointIssueDatetime())));
        // 10.1.3 10102 修正 ここまで
        detail.setOrderNo(ph.getOrderNo());
        // 10.1.3 10102 追加 ここから
        // 発行ポイント数の絶対値を取得する。
        // 発行ポイント数が整数でない場合は、空文字にする(画面に何も表示させない)。
        String issuedPoint = "";
        if (NumUtil.isDecimal(ph.getIssuedPoint())) {
//          issuedPoint = NumUtil.toString(Math.abs(NumUtil.toLong(ph.getIssuedPoint())));
          issuedPoint = ph.getIssuedPoint();
        }
        // 10.1.3 10102 追加 ここまで
        if (ph.getPointUsedDatetime() == null) {
          // 10.1.3 10102 修正 ここから
          // detail.setAcquisitionPoint(ph.getIssuedPoint());
          detail.setAcquisitionPoint(issuedPoint);
          // 10.1.3 10102 修正 ここまで
        } else {
          // 10.1.3 10102 修正 ここから
          // detail.setUsePoint(ph.getIssuedPoint());
          detail.setUsePoint(issuedPoint);
          // 10.1.3 10102 修正 ここまで
        }

        list.add(detail);
      }

      nextBean.setList(list);
    }

    this.setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

}
