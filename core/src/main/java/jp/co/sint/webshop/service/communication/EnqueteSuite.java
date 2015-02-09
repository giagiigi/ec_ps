package jp.co.sint.webshop.service.communication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.dto.EnqueteAnswerHeader;
import jp.co.sint.webshop.data.dto.EnqueteChoice;
import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.utility.CollectionUtil;

public class EnqueteSuite implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** Q:ヘッダ */
  private EnqueteAnswerHeader header;

  /** Q:設問 */
  private List<EnqueteQuestion> questions;

  /** Q:各設問の選択肢 */
  private HashMap<EnqueteQuestion, List<EnqueteChoice>> choices = new HashMap<EnqueteQuestion, List<EnqueteChoice>>();

  /**
   * choicesを取得します。
   * 
   * @return choices
   */
  public Map<EnqueteQuestion, List<EnqueteChoice>> getChoices() {
    return choices;
  }

  /**
   * choicesを設定します。
   * 
   * @param choices
   *          choices
   */
  public void setChoices(Map<EnqueteQuestion, List<EnqueteChoice>> choices) {
    CollectionUtil.copyAll(this.choices, choices);
  }

  /**
   * headerを取得します。
   * 
   * @return header
   */
  public EnqueteAnswerHeader getHeader() {
    return header;
  }

  /**
   * headerを設定します。
   * 
   * @param header
   *          header
   */
  public void setHeader(EnqueteAnswerHeader header) {
    this.header = header;
  }

  /**
   * questionsを取得します。
   * 
   * @return questions
   */
  public List<EnqueteQuestion> getQuestions() {
    return questions;
  }

  /**
   * questionsを設定します。
   * 
   * @param questions
   *          questions
   */
  public void setQuestions(List<EnqueteQuestion> questions) {
    this.questions = questions;
  }

}
