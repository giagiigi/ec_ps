package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.LanguageType;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.data.dto.PropagandaActivityRule;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PropagandaActivityRuleListBean;
import jp.co.sint.webshop.web.bean.back.communication.PropagandaActivityRuleListBean.PropagandaActivityRuleDetail;

/**
 * 宣传品活动共通处理
 * 
 * @author Kousen.
 */
public abstract class PropagandaActivityRuleListBaseAction extends WebBackAction<PropagandaActivityRuleListBean> {

  /**
   * レビューの検索結果一覧を作成します。<BR>
   * 
   * @param reviewList
   * @param nextBean
   */
  public List<PropagandaActivityRuleDetail> createList(List<PropagandaActivityRule> resultList) {
    List<PropagandaActivityRuleDetail> list = new ArrayList<PropagandaActivityRuleDetail>();
    for (PropagandaActivityRule propagandaActivityRule : resultList) {
      PropagandaActivityRuleDetail detail = new PropagandaActivityRuleDetail();

      detail.setActivityCode(propagandaActivityRule.getActivityCode());
      detail.setActivityName(propagandaActivityRule.getActivityName());
      detail.setActivityStartDate(DateUtil.toDateTimeString(propagandaActivityRule.getActivityStartDatetime()));
      detail.setActivityEndDate(DateUtil.toDateTimeString(propagandaActivityRule.getActivityEndDatetime()));
      detail.setActivityType(OrderType.fromValue(propagandaActivityRule.getOrderType()).getName());
      detail.setLanguageType(LanguageType.fromValue(propagandaActivityRule.getLanguageCode()).getName());

      list.add(detail);
    }

    return list;
  }

}
