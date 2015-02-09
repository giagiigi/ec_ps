package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;

public class WeekGraphData implements Serializable {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private List<WeekGraphSummary> weeklyList = new ArrayList<WeekGraphSummary>();

  public WeekGraphSummary getGraphData(String day, String clientGroup) {
    WeekGraphSummary summary = new WeekGraphSummary();
    summary.setClientGroup(clientGroup);
    summary.setCountDate(DateUtil.toDateTimeString(DateUtil.fromString(day), "MM/dd"));
    summary.setCountResult(0L);

    if (clientGroup.equals(UserAgentManager.OTHERS_CLIENT_GROUP_CODE)) {
      UserAgentManager manager = DIContainer.getUserAgentManager();
      List<String> normalClients = new ArrayList<String>();
      for (UserAgent ua : manager.getUserAgentList(false)) {
        if (!ua.getClientGroup().equals(UserAgentManager.OTHERS_CLIENT_GROUP_CODE)) {
          normalClients.add(ua.getClientGroup());
        }
      }
      Long count = 0L;
      for (WeekGraphSummary w : getWeeklyList()) {
        if (day.equals(w.getCountDate()) && !normalClients.contains(w.getClientGroup())) {
          count += w.getCountResult();
        }
      }
      summary.setCountResult(count);
    } else {
      for (WeekGraphSummary w : getWeeklyList()) {
        if (day.equals(w.getCountDate()) && clientGroup.equals(w.getClientGroup())) {
          summary.setCountResult(w.getCountResult());
        }
      }
    }
    return summary;
  }

  public WeekGraphSummary getGraphData(String day) {
    WeekGraphSummary summary = new WeekGraphSummary();
    summary.setCountDate(DateUtil.toDateTimeString(DateUtil.fromString(day), "MM/dd"));
    summary.setCountResult(0L);

    for (WeekGraphSummary w : getWeeklyList()) {
      if (day.equals(w.getCountDate())) {
        summary.setCountResult(w.getCountResult());
      }
    }
    return summary;
  }

  /**
   * weeklyListを返します。
   * 
   * @return the weeklyList
   */
  public List<WeekGraphSummary> getWeeklyList() {
    return weeklyList;
  }

  /**
   * weeklyListを設定します。
   * 
   * @param weeklyList
   *          設定する weeklyList
   */
  public void setWeeklyList(List<WeekGraphSummary> weeklyList) {
    this.weeklyList = weeklyList;
  }

}
