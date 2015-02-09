package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060130:アンケート分析のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class EnqueteSummaryBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String enqueteCode;

  private String enqueteName;

  private String enqueteNameEn;

  private String enqueteNameJp;

  private String repliedEnqueteCount;

  private List<EnqueteQuestionListBean> questionList = new ArrayList<EnqueteQuestionListBean>();

  private boolean displayExportButtonFlg;

  /**
   * U1060130:アンケート分析のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class EnqueteQuestionListBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String enqueteCode;

    private String enqueteQuestionNo;

    private String enqueteQuestionContent;

    private String enqueteQuestionContentEn;

    private String enqueteQuestionContentJp;

    private String questionDisplayOrder;

    private String repliedQuestionCount;

    private String enqueteQuestionType;

    private boolean necessaryFlg;

    private String noRepliedQuestionCount;

    private String noRepliedChoicesRate;

    private List<String> commentList = new ArrayList<String>();

    private List<EnqueteChoicesListBean> choicesList = new ArrayList<EnqueteChoicesListBean>();

    /**
     * U1060130:アンケート分析のサブモデルです。
     * 
     * @author System Integrator Corp.
     */
    public static class EnqueteChoicesListBean implements Serializable {

      /** serial version uid */
      private static final long serialVersionUID = 1L;

      private String enqueteQuestionNo;

      private String enqueteChoicesNo;

      private String enqueteChoices;

      private String enqueteChoicesEn;

      private String enqueteChoicesJp;

      private String choicesDisplayOrder;

      private String repliedChoicesRate;

      private String repliedChoicesCount;

      /**
       * choicesDisplayOrderを取得します。
       * 
       * @return choicesDisplayOrder
       */
      public String getChoicesDisplayOrder() {
        return choicesDisplayOrder;
      }

      /**
       * enqueteChoicesを取得します。
       * 
       * @return enqueteChoices
       */
      public String getEnqueteChoices() {
        return enqueteChoices;
      }

      /**
       * enqueteChoicesNoを取得します。
       * 
       * @return enqueteChoicesNo
       */
      public String getEnqueteChoicesNo() {
        return enqueteChoicesNo;
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
       * repliedChoicesCountを取得します。
       * 
       * @return repliedChoicesCount
       */
      public String getRepliedChoicesCount() {
        return repliedChoicesCount;
      }

      /**
       * repliedChoicesRateを取得します。
       * 
       * @return repliedChoicesRate
       */
      public String getRepliedChoicesRate() {
        return repliedChoicesRate;
      }

      /**
       * choicesDisplayOrderを設定します。
       * 
       * @param choicesDisplayOrder
       *          choicesDisplayOrder
       */
      public void setChoicesDisplayOrder(String choicesDisplayOrder) {
        this.choicesDisplayOrder = choicesDisplayOrder;
      }

      /**
       * enqueteChoicesを設定します。
       * 
       * @param enqueteChoices
       *          enqueteChoices
       */
      public void setEnqueteChoices(String enqueteChoices) {
        this.enqueteChoices = enqueteChoices;
      }

      /**
       * enqueteChoicesNoを設定します。
       * 
       * @param enqueteChoicesNo
       *          enqueteChoicesNo
       */
      public void setEnqueteChoicesNo(String enqueteChoicesNo) {
        this.enqueteChoicesNo = enqueteChoicesNo;
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
       * repliedChoicesCountを設定します。
       * 
       * @param repliedChoicesCount
       *          repliedChoicesCount
       */
      public void setRepliedChoicesCount(String repliedChoicesCount) {
        this.repliedChoicesCount = repliedChoicesCount;
      }

      /**
       * repliedChoicesRateを設定します。
       * 
       * @param repliedChoicesRate
       *          repliedChoicesRate
       */
      public void setRepliedChoicesRate(String repliedChoicesRate) {
        this.repliedChoicesRate = repliedChoicesRate;
      }

	/**
	 * @param enqueteChoicesEn the enqueteChoicesEn to set
	 */
	public void setEnqueteChoicesEn(String enqueteChoicesEn) {
		this.enqueteChoicesEn = enqueteChoicesEn;
	}

	/**
	 * @return the enqueteChoicesEn
	 */
	public String getEnqueteChoicesEn() {
		return enqueteChoicesEn;
	}

	/**
	 * @param enqueteChoicesJp the enqueteChoicesJp to set
	 */
	public void setEnqueteChoicesJp(String enqueteChoicesJp) {
		this.enqueteChoicesJp = enqueteChoicesJp;
	}

	/**
	 * @return the enqueteChoicesJp
	 */
	public String getEnqueteChoicesJp() {
		return enqueteChoicesJp;
	}

    }

    /**
     * necessaryFlgを取得します。
     * 
     * @return necessaryFlg
     */
    public boolean isNecessaryFlg() {
      return necessaryFlg;
    }

    /**
     * necessaryFlgを設定します。
     * 
     * @param necessaryFlg
     *          必須フラグ
     */
    public void setNecessaryFlg(boolean necessaryFlg) {
      this.necessaryFlg = necessaryFlg;
    }

    /**
     * noRepliedChoicesRateを取得します。
     * 
     * @return noRepliedChoicesRate
     */
    public String getNoRepliedChoicesRate() {
      return noRepliedChoicesRate;
    }

    /**
     * noRepliedChoicesRateを設定します。
     * 
     * @param noRepliedChoicesRate
     */
    public void setNoRepliedChoicesRate(String noRepliedChoicesRate) {
      this.noRepliedChoicesRate = noRepliedChoicesRate;
    }

    /**
     * noRepliedQuestionCountを取得します。
     * 
     * @return noRepliedQuestionCount
     */
    public String getNoRepliedQuestionCount() {
      return noRepliedQuestionCount;
    }

    /**
     * noRepliedQuestionCountを設定します。
     * 
     * @param noRepliedQuestionCount
     */
    public void setNoRepliedQuestionCount(String noRepliedQuestionCount) {
      this.noRepliedQuestionCount = noRepliedQuestionCount;
    }

    /**
     * choicesListを取得します。
     * 
     * @return choicesList
     */
    public List<EnqueteChoicesListBean> getChoicesList() {
      return choicesList;
    }

    /**
     * commentListを取得します。
     * 
     * @return commentList
     */
    public List<String> getCommentList() {
      return commentList;
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
     * questionDisplayOrderを取得します。
     * 
     * @return questionDisplayOrder
     */
    public String getQuestionDisplayOrder() {
      return questionDisplayOrder;
    }

    /**
     * repliedQuestionCountを取得します。
     * 
     * @return repliedQuestionCount
     */
    public String getRepliedQuestionCount() {
      return repliedQuestionCount;
    }

    /**
     * choicesListを設定します。
     * 
     * @param choicesList
     *          choicesList
     */
    public void setChoicesList(List<EnqueteChoicesListBean> choicesList) {
      this.choicesList = choicesList;
    }

    /**
     * commentListを設定します。
     * 
     * @param commentList
     *          commentList
     */
    public void setCommentList(List<String> commentList) {
      this.commentList = commentList;
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
     * questionDisplayOrderを設定します。
     * 
     * @param questionDisplayOrder
     *          questionDisplayOrder
     */
    public void setQuestionDisplayOrder(String questionDisplayOrder) {
      this.questionDisplayOrder = questionDisplayOrder;
    }

    /**
     * repliedQuestionCountを設定します。
     * 
     * @param repliedQuestionCount
     *          repliedQuestionCount
     */
    public void setRepliedQuestionCount(String repliedQuestionCount) {
      this.repliedQuestionCount = repliedQuestionCount;
    }

	/**
	 * @param enqueteQuestionContentEn the enqueteQuestionContentEn to set
	 */
	public void setEnqueteQuestionContentEn(String enqueteQuestionContentEn) {
		this.enqueteQuestionContentEn = enqueteQuestionContentEn;
	}

	/**
	 * @return the enqueteQuestionContentEn
	 */
	public String getEnqueteQuestionContentEn() {
		return enqueteQuestionContentEn;
	}
 

	/**
	 * @param enqueteQuestionContentJp the enqueteQuestionContentJp to set
	 */
	public void setEnqueteQuestionContentJp(String enqueteQuestionContentJp) {
		this.enqueteQuestionContentJp = enqueteQuestionContentJp;
	}

	/**
	 * @return the enqueteQuestionContentJp
	 */
	public String getEnqueteQuestionContentJp() {
		return enqueteQuestionContentJp;
	}

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
  public List<EnqueteQuestionListBean> getQuestionList() {
    return questionList;
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
  public void setQuestionList(List<EnqueteQuestionListBean> questionList) {
    this.questionList = questionList;
  }

  /**
   * repliedEnqueteCountを取得します。
   * 
   * @return repliedEnqueteCount
   */
  public String getRepliedEnqueteCount() {
    return repliedEnqueteCount;
  }

  /**
   * repliedEnqueteCountを設定します。
   * 
   * @param repliedEnqueteCount
   *          repliedEnqueteCount
   */
  public void setRepliedEnqueteCount(String repliedEnqueteCount) {
    this.repliedEnqueteCount = repliedEnqueteCount;
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
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1060130";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.EnqueteSummaryBean.0");
  }

  /**
   * displayExportButtonFlgかどうかを返します。
   *
   * @return displayExportButtonFlg displayExportButtonFlg
   */
  public boolean isDisplayExportButtonFlg() {
    return displayExportButtonFlg;
  }

  /**
   * displayExportButtonFlgかどうかを設定します。
   *
   * @param displayExportButtonFlg 
   *          displayExportButtonFlg
   */
  public void setDisplayExportButtonFlg(boolean displayExportButtonFlg) {
    this.displayExportButtonFlg = displayExportButtonFlg;
  }

/**
 * @param enqueteNameEn the enqueteNameEn to set
 */
public void setEnqueteNameEn(String enqueteNameEn) {
	this.enqueteNameEn = enqueteNameEn;
}

/**
 * @return the enqueteNameEn
 */
public String getEnqueteNameEn() {
	return enqueteNameEn;
}

/**
 * @param enqueteNameJp the enqueteNameJp to set
 */
public void setEnqueteNameJp(String enqueteNameJp) {
	this.enqueteNameJp = enqueteNameJp;
}

/**
 * @return the enqueteNameJp
 */
public String getEnqueteNameJp() {
	return enqueteNameJp;
}

}
