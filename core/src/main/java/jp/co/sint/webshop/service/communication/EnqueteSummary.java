package jp.co.sint.webshop.service.communication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.data.dto.EnqueteReplyInput;
import jp.co.sint.webshop.utility.CollectionUtil;

public class EnqueteSummary implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 設問セット */
  private EnqueteSuite enqueteSuite;

  /** 自由回答セット */
  private List<EnqueteReplyInput> enqueteReplyInputs;

  /** アンケート回答者数 */
  private String repliedEnqueteCount;

  /** アンケート設問別回答者数 */
  private List<String> repliedQuestionCount;
  
  /** アンケート設問別未回答者数 */
  private List<String> noRepliedQuestionCount;
  
  /** アンケート選択肢別回答者数 */
  private HashMap<EnqueteQuestion, List<String>> repliedChoicesCount = new HashMap<EnqueteQuestion, List<String>>();
  
  /** アンケート選択肢別百分率 */
  private HashMap<EnqueteQuestion, List<String>> repliedChoicesRate = new HashMap<EnqueteQuestion, List<String>>();


  public List<EnqueteReplyInput> getEnqueteReplyInputs() {
    return enqueteReplyInputs;
  }

  public void setEnqueteReplyInputs(List<EnqueteReplyInput> enqueteReplyInputs) {
    this.enqueteReplyInputs = enqueteReplyInputs;
  }

  public EnqueteSuite getEnqueteSuite() {
    return enqueteSuite;
  }

  public void setEnqueteSuite(EnqueteSuite enqueteSuite) {
    this.enqueteSuite = enqueteSuite;
  }

  public List<String> getNoRepliedQuestionCount() {
    return noRepliedQuestionCount;
  }

  public void setNoRepliedQuestionCount(List<String> noRepliedQuestionCount) {
    this.noRepliedQuestionCount = noRepliedQuestionCount;
  }

  public Map<EnqueteQuestion, List<String>> getRepliedChoicesCount() {
    return repliedChoicesCount;
  }

  public void setRepliedChoicesCount(Map<EnqueteQuestion, List<String>> repliedChoicesCount) {
    CollectionUtil.copyAll(this.repliedChoicesCount, repliedChoicesCount);
  }

  public Map<EnqueteQuestion, List<String>> getRepliedChoicesRate() {
    return repliedChoicesRate;
  }

  public void setRepliedChoicesRate(Map<EnqueteQuestion, List<String>> repliedChoicesRate) {
    CollectionUtil.copyAll(this.repliedChoicesRate, repliedChoicesRate);
  }

  public String getRepliedEnqueteCount() {
    return repliedEnqueteCount;
  }

  public void setRepliedEnqueteCount(String repliedEnqueteCount) {
    this.repliedEnqueteCount = repliedEnqueteCount;
  }

  public List<String> getRepliedQuestionCount() {
    return repliedQuestionCount;
  }

  public void setRepliedQuestionCount(List<String> repliedQuestionCount) {
    this.repliedQuestionCount = repliedQuestionCount;
  }

}
