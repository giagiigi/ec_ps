package jp.co.sint.webshop.utility;

import java.io.Serializable;

/**
 * ユーザーエージェントのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class UserAgent implements Serializable {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** クライアントグループコード */
  private String clientGroup;

  /** ユーザーエージェント名 */
  private String agentName;

  /** ヘッダに含まれる文字列 */
  private String[] keywords = new String[0];

  /**
   * コンストラクタです。
   */
  public UserAgent() {
  }

  /**
   * clientGroup、agentName、keywordsを設定するコンストラクタです。
   * 
   * @param clientGroup
   *          clientGroup
   * @param agentName
   *          agentName
   * @param keywords
   *          keywords
   */
  public UserAgent(String clientGroup, String agentName, String[] keywords) {
    setClientGroup(clientGroup);
    setAgentName(agentName);
    setKeywords(keywords);
  }

  /**
   * agentNameを取得します。
   * 
   * @return agentName
   */
  public String getAgentName() {
    return agentName;
  }

  /**
   * agentNameを設定します。
   * 
   * @param agentName
   *          agentName
   */
  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

  /**
   * clientGroupを取得します。
   * 
   * @return clientGroup
   */
  public String getClientGroup() {
    return clientGroup;
  }

  /**
   * clientGroupを設定します。
   * 
   * @param clientGroup
   *          clientGroup
   */
  public void setClientGroup(String clientGroup) {
    this.clientGroup = clientGroup;
  }

  /**
   * keywordsを取得します。
   * 
   * @return keywords
   */
  public String[] getKeywords() {
    return ArrayUtil.immutableCopy(keywords);
  }

  /**
   * keywordsを設定します。
   * 
   * @param keywords
   *          keywords
   */
  public void setKeywords(String[] keywords) {
    this.keywords = ArrayUtil.immutableCopy(keywords);
  }

}
