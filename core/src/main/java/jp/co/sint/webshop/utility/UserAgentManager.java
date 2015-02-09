package jp.co.sint.webshop.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * UserAgentを取得・設定するユーティリティクラスです。
 * 
 * @author System Integrator Corp.
 */
public class UserAgentManager implements Serializable {

  /**
   * serial version UID
   */
  private static final long serialVersionUID = 1L;

  // 「その他」のクライアントグループコード
  public static final String OTHERS_CLIENT_GROUP_CODE = "90";

  private List<UserAgent> agents = new ArrayList<UserAgent>();
  
  private List<String> orderType = new ArrayList<String>();

  /**
   * UserAgent文字列から該当するUserAgentを取得します。
   * 
   * @return UserAgentを返します。<br>
   *         該当するUserAgentが存在しない場合、その他のclientGroupに属するUserAgentを返します。
   */
  public UserAgent identifyAgent(String userAgentString) {
    for (UserAgent ua : agents) {
      for (String s : ua.getKeywords()) {
        if (StringUtil.hasValue(s) && userAgentString.indexOf(s) != -1) {
          return ua;
        }
      }
    }
    return fromClientGroup(OTHERS_CLIENT_GROUP_CODE);
  }

  /**
   * 指定されたclientGroupを持つUserAgentを返します。
   * 
   * @return UserAgentを返します。<br>
   *         該当するUserAgentが存在しない場合、新たにUserAgentインスタンスを生成して返します。
   */
  public UserAgent fromClientGroup(String clientGroup) {
    for (UserAgent ua : agents) {
      if (ua.getClientGroup().equals(clientGroup)) {
        return ua;
      }
    }
    return new UserAgent();
  }

  /**
   * UserAgentの一覧を取得します。<br>
   * 全てのUserAgentを返すか、何らかのclientGroupに属しているUserAgentを返すかのどちらかを選択することができます。
   * 
   * @param containsCrawler
   *          true : 全てのUserAgentを返す<br>
   *          false : 何らかのclientGroupに属しているUserAgentを返す
   * @return UserAgentの一覧を返します。
   */
  public List<UserAgent> getUserAgentList(boolean containsCrawler) {
    List<UserAgent> list = new ArrayList<UserAgent>();
    for (UserAgent ua : getAgents()) {
      if (containsCrawler) {
        list.add(ua);
      } else {
        if (StringUtil.hasValue(ua.getClientGroup())) {
          list.add(ua);
        }
      }
    }
    return list;
  }

  /**
   * 何らかのclientGroupに属しているUserAgentの一覧を取得します。<br>
   * 
   * @return UserAgentの一覧を返します。<br>
   *         この一覧に含まれるUserAgentは、何らかのclientGroupに属しているものとします。
   */
  public List<UserAgent> getUserAgentList() {
    return getUserAgentList(false);
  }

  /**
   * agentsを取得します。
   * 
   * @return agents
   */
  public List<UserAgent> getAgents() {
    return agents;
  }

  /**
   * agentsを設定します。
   * 
   * @param agents
   *          agents
   */
  public void setAgents(List<UserAgent> agents) {
    CollectionUtil.copyAll(this.agents, agents);
  }

  
  /**
   * @return the orderType
   */
  public List<String> getOrderType() {
    return orderType;
  }

  
  /**
   * @param orderType the orderType to set
   */
  public void setOrderType(List<String> orderType) {
    this.orderType = orderType;
  }

}
