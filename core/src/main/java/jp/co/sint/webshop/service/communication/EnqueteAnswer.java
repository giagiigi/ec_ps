package jp.co.sint.webshop.service.communication;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.dto.EnqueteAnswerHeader;
import jp.co.sint.webshop.data.dto.EnqueteReplyChoices;
import jp.co.sint.webshop.data.dto.EnqueteReplyInput;

public class EnqueteAnswer implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** A:ヘッダ */
  private EnqueteAnswerHeader answerHeader;

  /** A:選択式 */
  private List<EnqueteReplyChoices> answerChoices;

  /** A:自由回答 */
  private List<EnqueteReplyInput> answerInputs;

  /** アンケートのハッシュ値 */
  private String hashValue;

  /** 回答者が会員かどうか */
  private boolean customer;

  public List<EnqueteReplyChoices> getAnswerChoices() {
    return answerChoices;
  }

  public EnqueteAnswerHeader getAnswerHeader() {
    return answerHeader;
  }

  public List<EnqueteReplyInput> getAnswerInputs() {
    return answerInputs;
  }

  public void setAnswerChoices(List<EnqueteReplyChoices> answerChoices) {
    this.answerChoices = answerChoices;
  }

  public void setAnswerHeader(EnqueteAnswerHeader answerHeader) {
    this.answerHeader = answerHeader;
  }

  public void setAnswerInputs(List<EnqueteReplyInput> answerInputs) {
    this.answerInputs = answerInputs;
  }

  public String getHashValue() {
    return hashValue;
  }

  public void setHashValue(String hashValue) {
    this.hashValue = hashValue;
  }

  public boolean isCustomer() {
    return customer;
  }

  public void setCustomer(boolean customer) {
    this.customer = customer;
  }

}
