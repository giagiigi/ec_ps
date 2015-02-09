package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.DayOfWeek;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;

public class AccessLogData implements Serializable {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private List<PageView> pageViewList = new ArrayList<PageView>();

  private List<Conversion> conversionList = new ArrayList<Conversion>();

  /** 指定条件に当てはまるページビュー情報を取得します。条件に当てはまるものがない場合はページビュー数が0であるデータを作成し返します。 */
  public PageView getPageView(CountType type, String label, String clientGroup) {
    PageView view = new PageView();

    view.setAccessCount(0L);
    view.setClientGroup(clientGroup);

    UserAgentManager manager = DIContainer.getUserAgentManager();
    if (clientGroup.equals(UserAgentManager.OTHERS_CLIENT_GROUP_CODE)) {
      // "その他"クライアントグループを指定されたら、定義外の全てのクライアントグループコードのレコードを取得
      List<String> normalClientGroups = new ArrayList<String>();
      for (UserAgent ua : manager.getUserAgentList(false)) {
        if (!ua.getClientGroup().equals(UserAgentManager.OTHERS_CLIENT_GROUP_CODE)) {
          normalClientGroups.add(ua.getClientGroup());
        }
      }
      Long count = 0L;
      for (PageView p : pageViewList) {
        if (p.getLabel().equals(label) && !normalClientGroups.contains(p.getClientGroup())) {
          count += p.getAccessCount();
        }
      }
      view.setAccessCount(count);
    } else {
      for (PageView p : pageViewList) {
        if (p.getClientGroup().equals(clientGroup) && p.getLabel().equals(label)) {
          view.setAccessCount(p.getAccessCount());
        }
      }
    }

    view.setLabel(createLabel(type, label));

    return view;
  }

  /** 指定条件に当てはまる今バージョン情報を取得します。条件に当てはまるものがない場合はコンバージョン率が0であるデータを作成し返します。 */
  public Conversion getConversion(CountType type, String label) {
    Conversion conversion = new Conversion();

    conversion.setConversionRate("0");

    for (Conversion c : conversionList) {
      if (c.getLabel().equals(label)) {
        conversion.setConversionRate(c.getConversionRate());
      }
    }

    conversion.setLabel(createLabel(type, label));

    return conversion;
  }

  private String createLabel(CountType type, String source) {
    String label = "";
    switch (type) {
      case MONTHLY:
        label = MessageFormat.format(Messages.getString("service.analysis.AccessLogData.0"), source);
        break;
      case EVERY_DAY_OF_WEEK:
        label = DayOfWeek.fromValue(source).getName();
        break;
      case DAILY:
        label = MessageFormat.format(Messages.getString("service.analysis.AccessLogData.1"), source);
        break;
      case HOURLY:
        label = MessageFormat.format(
            Messages.getString("service.analysis.AccessLogData.2"), StringUtil.addZero(source, 2));
        break;
      default:
        break;
    }

    return label;
  }

  /**
   * pageViewListを返します。
   * 
   * @return the pageViewList
   */
  public List<PageView> getPageViewList() {
    return pageViewList;
  }

  /**
   * conversionListを返します。
   * 
   * @return the conversionList
   */
  public List<Conversion> getConversionList() {
    return conversionList;
  }

  /**
   * pageViewListを設定します。
   * 
   * @param pageViewList
   *          設定する pageViewList
   */
  public void setPageViewList(List<PageView> pageViewList) {
    this.pageViewList = pageViewList;
  }

  /**
   * conversionListを設定します。
   * 
   * @param conversionList
   *          設定する conversionList
   */
  public void setConversionList(List<Conversion> conversionList) {
    this.conversionList = conversionList;
  }

}
