package jp.co.sint.webshop.web.action.back.customer;

import java.util.List;

import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.PointIssueStatus;
import jp.co.sint.webshop.data.domain.PointIssueType;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.customer.PointStatusAllSearchInfo;
import jp.co.sint.webshop.service.customer.PointStatusListSearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.PointHistoryBean;
import jp.co.sint.webshop.web.bean.back.customer.PointHistoryBean.PointHistoryDetailList;

/**
 * U1030140:ポイント履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class PointHistoryBaseAction extends WebBackAction<PointHistoryBean> {

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

  /**
   * 検索条件を設定します。<BR>
   * 
   * @param searchBean
   * @param condition
   */
  public void setSearchCondition(PointHistoryBean searchBean, PointStatusListSearchCondition condition) {
    condition.setSearchShopCode(searchBean.getSearchShopCode());
    condition.setSearchIssueType(searchBean.getSearchIssueType());
  }

  /**
   * ポイント履歴の検索結果一覧を作成します。<BR>
   * 
   * @param nextBean
   *          ポイント履歴
   * @param pointList
   *          ポイント履歴一覧
   */
  public void setPointHistoryList(PointHistoryBean nextBean, List<PointStatusAllSearchInfo> pointList) {
    List<PointHistoryDetailList> list = nextBean.getList();

    for (PointStatusAllSearchInfo ph : pointList) {
      PointHistoryDetailList detail = new PointHistoryDetailList();
      detail.setPublisher(PointIssueType.fromValue(ph.getPointIssueType()).getName());
      detail.setPublisherId(getPublisherId(ph));
      detail.setShopCode(ph.getShopCode());
      detail.setShopName(ph.getShopName());
      detail.setDescription(ph.getDescription());
      detail.setPointIssueStatus(PointIssueStatus.fromValue(ph.getPointIssueStatus()).getName());
      detail.setIssuedPoint(ph.getIssuedPoint());
      detail.setPointIssueDatetime(ph.getPointIssueDatetime());

      list.add(detail);
    }

    nextBean.setList(list);
  }

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
    if (obean instanceof PointHistoryBean) {
      PointHistoryBean bean = (PointHistoryBean) obean;
      Boolean usablePoint = bean.getUsablePointSystem();
      if (usablePoint == null) {
        SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
        PointRule rule = service.getPointRule();
        usablePoint = Boolean.valueOf(PointFunctionEnabledFlg.ENABLED.longValue().equals(rule.getPointFunctionEnabledFlg()));
        bean.setUsablePointSystem(usablePoint);
      }
      if (!bean.getUsablePointSystem().booleanValue()) {
        // ポイントシステム使用不可の場合更新・削除処理は不可とする
        bean.setUpdateMode(false);
        bean.setDeleteMode(false);
      }
      setRequestBean(bean);
    }
  }
}
