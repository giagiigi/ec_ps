package jp.co.sint.webshop.web.bean.front.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.domain.EnqueteQuestionType;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.bean.front.order.EnqueteEditBean.QuestionListBean.EnqueteListAnswerBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2060110:アンケートのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class EnqueteEditBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String customerCode;

  /** アンケートコード */
  private String enqueteCode;

  /** アンケート名 */
  private String enqueteName;

  /** アンケートポイント */
  private String enqueteInvestPoint;

  // add by V10-CH start
  /** アンケートポイント表示フラグ */
  private boolean enqueteInvestPointDisplayFlg;

  // add by V10-CH end

  /** アンケートが正常かどうか */
  private boolean correct;

  /** アンケートのハッシュ値 */
  private String hashvalue;

  /** 設問リスト */
  private List<QuestionListBean> questionList = new ArrayList<QuestionListBean>();

  /** 遷移元が注文完了画面か判定 */
  private String transitionFromOrder;

  /**
   * U2060110:アンケートのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class QuestionListBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** アンケート設問番号 */
    private String enqueteQuestionNo;

    /** アンケート設問内容 */
    private String enqueteQuestionContent;

    /** アンケート設問区分 */
    private String enqueteQuestionType;

    /** 必須フラグ */
    private String necessaryFlg;

    /** アンケート選択肢リスト */
    private List<CodeAttribute> choicesList = new ArrayList<CodeAttribute>();

    /** アンケート回答 */
    @Length(2000)
    @Metadata(name = "アンケート回答")
    private String enqueteReply;

    /** アンケート選択肢番号回答 */
    @Length(8)
    @Digit
    @Metadata(name = "アンケート選択肢番号")
    private String enqueteAnswer;

    /** アンケート選択肢回答リスト表示用 */
    private List<String> enqueteAnswerItem = new ArrayList<String>();

    /** アンケート選択肢回答リスト */
    private List<EnqueteListAnswerBean> enqueteAnswerList = new ArrayList<EnqueteListAnswerBean>();

    /**
     * U2060110:アンケートのサブモデルです。
     * 
     * @author System Integrator Corp.
     */
    public static class EnqueteListAnswerBean implements Serializable {

      /** serial version uid */
      private static final long serialVersionUID = 1L;

      /** アンケート選択肢回答(リスト) */
      @Length(8)
      @Digit
      @Metadata(name = "アンケート選択肢番号")
      private String enqueteAnswerList;

      /**
       * enqueteAnswerListを取得します。
       * 
       * @return enqueteAnswerList
       */
      public String getEnqueteAnswerList() {
        return enqueteAnswerList;
      }

      /**
       * enqueteAnswerListを設定します。
       * 
       * @param enqueteAnswerList
       *          enqueteAnswerList
       */
      public void setEnqueteAnswerList(String enqueteAnswerList) {
        this.enqueteAnswerList = enqueteAnswerList;
      }

    }

    /** 設問入力テキスト名 */
    private String questionTextName;

    /**
     * enqueteAnswerを取得します。
     * 
     * @return enqueteAnswer
     */
    public String getEnqueteAnswer() {
      return enqueteAnswer;
    }

    /**
     * enqueteAnswerを設定します。
     * 
     * @param enqueteAnswer
     *          enqueteAnswer
     */
    public void setEnqueteAnswer(String enqueteAnswer) {
      this.enqueteAnswer = enqueteAnswer;
    }

    /**
     * questionTextNameを取得します。
     * 
     * @return questionTextName
     */
    public String getQuestionTextName() {
      return questionTextName;
    }

    /**
     * questionTextNameを設定します。
     * 
     * @param questionTextName
     *          questionTextName
     */
    public void setQuestionTextName(String questionTextName) {
      this.questionTextName = questionTextName;
    }

    /**
     * choicesListを取得します。
     * 
     * @return choicesList
     */
    public List<CodeAttribute> getChoicesList() {
      return choicesList;
    }

    /**
     * enqueteQuestionContentを取得します。
     * 
     * @return enqueteQuestionContent
     */
    public String getEnqueteQuestionContent() {
      return enqueteQuestionContent;
    }

    /**
     * enqueteQuestionNoを取得します。
     * 
     * @return enqueteQuestionNo
     */
    public String getEnqueteQuestionNo() {
      return enqueteQuestionNo;
    }

    /**
     * enqueteQuestionTypeを取得します。
     * 
     * @return enqueteQuestionType
     */
    public String getEnqueteQuestionType() {
      return enqueteQuestionType;
    }

    /**
     * enqueteReplyを取得します。
     * 
     * @return enqueteReply
     */
    public String getEnqueteReply() {
      return enqueteReply;
    }

    /**
     * necessaryFlgを取得します。
     * 
     * @return necessaryFlg
     */
    public String getNecessaryFlg() {
      return necessaryFlg;
    }

    /**
     * choicesListを設定します。
     * 
     * @param choicesList
     *          choicesList
     */
    public void setChoicesList(List<CodeAttribute> choicesList) {
      this.choicesList = choicesList;
    }

    /**
     * enqueteQuestionContentを設定します。
     * 
     * @param enqueteQuestionContent
     *          enqueteQuestionContent
     */
    public void setEnqueteQuestionContent(String enqueteQuestionContent) {
      this.enqueteQuestionContent = enqueteQuestionContent;
    }

    /**
     * enqueteQuestionNoを設定します。
     * 
     * @param enqueteQuestionNo
     *          enqueteQuestionNo
     */
    public void setEnqueteQuestionNo(String enqueteQuestionNo) {
      this.enqueteQuestionNo = enqueteQuestionNo;
    }

    /**
     * enqueteQuestionTypeを設定します。
     * 
     * @param enqueteQuestionType
     *          enqueteQuestionType
     */
    public void setEnqueteQuestionType(String enqueteQuestionType) {
      this.enqueteQuestionType = enqueteQuestionType;
    }

    /**
     * enqueteReplyを設定します。
     * 
     * @param enqueteReply
     *          enqueteReply
     */
    public void setEnqueteReply(String enqueteReply) {
      this.enqueteReply = enqueteReply;
    }

    /**
     * necessaryFlgを設定します。
     * 
     * @param necessaryFlg
     *          necessaryFlg
     */
    public void setNecessaryFlg(String necessaryFlg) {
      this.necessaryFlg = necessaryFlg;
    }

    /**
     * enqueteAnswerListを取得します。
     * 
     * @return enqueteAnswerList
     */
    public List<EnqueteListAnswerBean> getEnqueteAnswerList() {
      return enqueteAnswerList;
    }

    /**
     * enqueteAnswerListを設定します。
     * 
     * @param enqueteAnswerList
     *          enqueteAnswerList
     */
    public void setEnqueteAnswerList(List<EnqueteListAnswerBean> enqueteAnswerList) {
      this.enqueteAnswerList = enqueteAnswerList;
    }

    /**
     * enqueteAnswerItemを取得します。
     * 
     * @return enqueteAnswerItem
     */
    public List<String> getEnqueteAnswerItem() {
      return enqueteAnswerItem;
    }

    /**
     * enqueteAnswerItemを設定します。
     * 
     * @param enqueteAnswerItem
     *          enqueteAnswerItem
     */
    public void setEnqueteAnswerItem(List<String> enqueteAnswerItem) {
      this.enqueteAnswerItem = enqueteAnswerItem;
    }

  }

  /**
   * customerCodeを取得します。
   * 
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * enqueteCodeを取得します。
   * 
   * @return enqueteCode
   */
  public String getEnqueteCode() {
    return enqueteCode;
  }

  /**
   * enqueteInvestPointを取得します。
   * 
   * @return enqueteInvestPoint
   */
  public String getEnqueteInvestPoint() {
    return enqueteInvestPoint;
  }

  /**
   * enqueteNameを取得します。
   * 
   * @return enqueteName
   */
  public String getEnqueteName() {
    return enqueteName;
  }

  /**
   * questionListを取得します。
   * 
   * @return questionList
   */
  public List<QuestionListBean> getQuestionList() {
    return questionList;
  }

  /**
   * customerCodeを設定します。
   * 
   * @param customerCode
   *          customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * enqueteCodeを設定します。
   * 
   * @param enqueteCode
   *          enqueteCode
   */
  public void setEnqueteCode(String enqueteCode) {
    this.enqueteCode = enqueteCode;
  }

  /**
   * enqueteInvestPointを設定します。
   * 
   * @param enqueteInvestPoint
   *          enqueteInvestPoint
   */
  public void setEnqueteInvestPoint(String enqueteInvestPoint) {
    this.enqueteInvestPoint = enqueteInvestPoint;
  }

  /**
   * enqueteNameを設定します。
   * 
   * @param enqueteName
   *          enqueteName
   */
  public void setEnqueteName(String enqueteName) {
    this.enqueteName = enqueteName;
  }

  /**
   * questionListを設定します。
   * 
   * @param questionList
   *          questionList
   */
  public void setQuestionList(List<QuestionListBean> questionList) {
    this.questionList = questionList;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    for (QuestionListBean q : getQuestionList()) {
      if (q.getEnqueteQuestionType().equals(EnqueteQuestionType.RADIO.getValue())) {
        // 単一選択の場合
        q.setEnqueteAnswer(reqparam.get(q.getQuestionTextName()));
      } else if (q.getEnqueteQuestionType().equals(EnqueteQuestionType.CHECK.getValue())) {
        // 複数選択の場合
        List<String> answerItem = new ArrayList<String>();
        List<EnqueteListAnswerBean> answerList = new ArrayList<EnqueteListAnswerBean>();

        String[] paramList = reqparam.getAll(q.getQuestionTextName());
        if (StringUtil.hasValueAllOf(paramList)) {
          for (String a : paramList) {
            EnqueteListAnswerBean answerEdit = new EnqueteListAnswerBean();
            answerEdit.setEnqueteAnswerList(a);
            answerList.add(answerEdit);
            answerItem.add(a);
          }
        }
        q.setEnqueteAnswerList(answerList);
        q.setEnqueteAnswerItem(answerItem); // リスト表示用
      } else {
        // 自由入力の場合
        q.setEnqueteReply(reqparam.get(q.getQuestionTextName()));
      }
    }
    setQuestionList(getQuestionList());
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2060110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.order.EnqueteEditBean.0");
  }

  /**
   * correctを取得します。
   * 
   * @return correct
   */
  public boolean isCorrect() {
    return correct;
  }

  /**
   * correctを設定します。
   * 
   * @param correct
   *          correct
   */
  public void setCorrect(boolean correct) {
    this.correct = correct;
  }

  /**
   * hashvalueを取得します。
   * 
   * @return hashvalue
   */
  public String getHashvalue() {
    return hashvalue;
  }

  /**
   * hashvalueを設定します。
   * 
   * @param hashvalue
   *          hashvalue
   */
  public void setHashvalue(String hashvalue) {
    this.hashvalue = hashvalue;
  }

  /**
   * transitionFromOrderを返します。
   * 
   * @return the transitionFromOrder
   */
  public String getTransitionFromOrder() {
    return transitionFromOrder;
  }

  /**
   * transitionFromOrderを設定します。
   * 
   * @param transitionFromOrder
   *          設定する transitionFromOrder
   */
  public void setTransitionFromOrder(String transitionFromOrder) {
    this.transitionFromOrder = transitionFromOrder;
  }

  public boolean isEnqueteInvestPointDisplayFlg() {
    return enqueteInvestPointDisplayFlg;
  }

  public void setEnqueteInvestPointDisplayFlg(boolean enqueteInvestPointDisplayFlg) {
    this.enqueteInvestPointDisplayFlg = enqueteInvestPointDisplayFlg;
  }

}
