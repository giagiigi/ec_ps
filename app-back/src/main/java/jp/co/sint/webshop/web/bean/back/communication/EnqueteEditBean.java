package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Point;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean.QuestionListBean.ChoicesListBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060120:アンケートマスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class EnqueteEditBean extends UIBackBean {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Required
	@AlphaNum2
	@Length(16)
	@Metadata(name = "アンケートコード", order = 1)
	private String enqueteCode;

	@Required
	@Length(40)
	@Metadata(name = "アンケート名", order = 2)
	private String enqueteName;
	// add by cs_yuli 20120516 start
	/******* 问卷调查名称(英文) ********/
	@Required
	@Length(40)
	@Metadata(name = "问卷调查名称(英文)")
	private String enqueteNameEn;
	/****** 问卷调查名称(日文) ********/
	@Required
	@Length(40)
	@Metadata(name = "问卷调查名称(日文)")
	private String enqueteNameJp;
	// add by cs_yuli 20120516 end
	@Required
	@Datetime
	@Metadata(name = "アンケート期間(From)", order = 3)
	private String enqueteStartDate;

	@Required
	@Datetime
	@Metadata(name = "アンケート期間(To)", order = 4)
	private String enqueteEndDate;

	@Required
	@Point
	@Metadata(name = "アンケートポイント", order = 5)
	private String enqueteInvestPoint;

	private List<QuestionListBean> questionList = new ArrayList<QuestionListBean>();

	private List<ChoicesListBean> choicesList = new ArrayList<ChoicesListBean>();

	/** 設問登録部 */
	private QuestionListBean registerQuestion = new QuestionListBean();

	/** 選択肢登録部 */
	private ChoicesListBean registerChoice = new ChoicesListBean();

	/** アンケート登録・更新表示切替フラグ */
	private boolean enqueteButtonDisplay;

	/** 設問登録・更新ボタン表示切替フラグ */
	private boolean questionButtonDisplay;

	/** 設問エリア表示フラグ */
	private boolean questionsAreaDisplay;

	/** 選択肢編集ボタン表示フラグ */
	private boolean choiceEditButtonDisplay;

	/** 選択肢登録・更新ボタン表示切替フラグ */
	private boolean choiceButtonDisplay;

	/** 選択肢エリア表示フラグ */
	private boolean choicesAreaDisplay;

	/** 本体部分編集モード(edit or readonly) */
	private String mainEditMode;

	/** 設問・選択肢部分編集モード(edit or readonly) */
	private String detailEditMode;

	private Date updateDatetime;

	/**
	 * U1060120:アンケートマスタのサブモデルです。
	 * 
	 * @author System Integrator Corp.
	 */
	public static class QuestionListBean implements Serializable {

		/**
     * 
     */
		private static final long serialVersionUID = 1L;

		private String enqueteQuestionNo;

		@Required
		@Length(200)
		@Metadata(name = "設問内容", order = 1)
		private String enqueteQuestionContent;
		// add by cs_yuli 20120516 start
		@Required
		@Length(200)
		@Metadata(name = "設問内容(英文)")
		private String enqueteQuestionContentEn;
		@Required
		@Length(200)
		@Metadata(name = "設問内容(日文)")
		private String enqueteQuestionContentJp;
		// add by cs_yuli 20120516 end
		@Required
		@Range(min = 0, max = 2)
		@Metadata(name = "回答タイプ", order = 2)
		private String enqueteQuestionType;

		@Required
		@Digit
		@Length(8)
		@Range(min = 0, max = 99999999)
		@Metadata(name = "表示順(設問)", order = 3)
		private String questionDisplayOrder;

		@Required
		@Range(min = 0, max = 1)
		private String requiredFlg;

		private List<ChoicesListBean> choicesList = new ArrayList<ChoicesListBean>();

		private Date updateDatetime;

		/**
		 * U1060120:アンケートマスタのサブモデルです。
		 * 
		 * @author System Integrator Corp.
		 */
		public static class ChoicesListBean implements Serializable {

			/**
       * 
       */
			private static final long serialVersionUID = 1L;

			@Digit
			private String questionNo;

			@Length(8)
			@Digit
			@Metadata(name = "選択肢番号", order = 1)
			private String enqueteChoicesNo;

			@Required
			@Length(200)
			@Metadata(name = "選択肢", order = 2)
			private String enqueteChoices;
			// add by cs_yuli 20120516 start
			/**** 选项名称(英文) *****/
			@Required
			@Length(200)
			@Metadata(name = "选项名称(英文)")
			private String enqueteChoicesEn;
			/**** 选项名称(日文) *****/
			@Required
			@Length(200)
			@Metadata(name = "选项名称(日文)")
			private String enqueteChoicesJp;
			// add by cs_yuli 20120516 end
			@Required
			@Digit
			@Length(8)
			@Metadata(name = "表示順(選択肢)", order = 3)
			private String choicesDisplayOrder;

			private Date updateDatetime;

			private List<CodeAttribute> questionContents = new ArrayList<CodeAttribute>();

			/**
			 * questionContentsを取得します。
			 * 
			 * @return questionContents
			 */
			public List<CodeAttribute> getQuestionContents() {
				return questionContents;
			}

			/**
			 * questionContentsを設定します。
			 * 
			 * @param questionContents
			 */
			public void setQuestionContents(List<CodeAttribute> questionContents) {
				this.questionContents = questionContents;
			}

			/**
			 * questionNoを取得します。
			 * 
			 * @return questionNo
			 */
			public String getQuestionNo() {
				return questionNo;
			}

			/**
			 * questionNoを設定します。
			 * 
			 * @param questionNo
			 */
			public void setQuestionNo(String questionNo) {
				this.questionNo = questionNo;
			}

			/**
			 * updateDatetimeを取得します。
			 * 
			 * @return updateDatetime
			 */
			public Date getUpdateDatetime() {
				return DateUtil.immutableCopy(updateDatetime);
			}

			/**
			 * updateDatetimeを設定します。
			 * 
			 * @param updateDatetime
			 */
			public void setUpdateDatetime(Date updateDatetime) {
				this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
			}

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
			 * choicesDisplayOrderを設定します。
			 * 
			 * @param choicesDisplayOrder
			 *            choicesDisplayOrder
			 */
			public void setChoicesDisplayOrder(String choicesDisplayOrder) {
				this.choicesDisplayOrder = choicesDisplayOrder;
			}

			/**
			 * enqueteChoicesを設定します。
			 * 
			 * @param enqueteChoices
			 *            enqueteChoices
			 */
			public void setEnqueteChoices(String enqueteChoices) {
				this.enqueteChoices = enqueteChoices;
			}

			/**
			 * enqueteChoicesNoを設定します。
			 * 
			 * @param enqueteChoicesNo
			 *            enqueteChoicesNo
			 */
			public void setEnqueteChoicesNo(String enqueteChoicesNo) {
				this.enqueteChoicesNo = enqueteChoicesNo;
			}

			/**
			 * @param enqueteChoicesEn
			 *            the enqueteChoicesEn to set
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
			 * @param enqueteChoicesJp
			 *            the enqueteChoicesJp to set
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
		 * updateDatetimeを取得します。
		 * 
		 * @return updateDatetime
		 */
		public Date getUpdateDatetime() {
			return DateUtil.immutableCopy(updateDatetime);
		}

		/**
		 * updateDatetimeを設定します。
		 * 
		 * @param updateDatetime
		 */
		public void setUpdateDatetime(Date updateDatetime) {
			this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
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
		 * requiredFlgを取得します。
		 * 
		 * @return requiredFlg
		 */
		public String getRequiredFlg() {
			return requiredFlg;
		}

		/**
		 * enqueteQuestionContentを設定します。
		 * 
		 * @param enqueteQuestionContent
		 *            enqueteQuestionContent
		 */
		public void setEnqueteQuestionContent(String enqueteQuestionContent) {
			this.enqueteQuestionContent = enqueteQuestionContent;
		}

		/**
		 * enqueteQuestionNoを設定します。
		 * 
		 * @param enqueteQuestionNo
		 *            enqueteQuestionNo
		 */
		public void setEnqueteQuestionNo(String enqueteQuestionNo) {
			this.enqueteQuestionNo = enqueteQuestionNo;
		}

		/**
		 * enqueteQuestionTypeを設定します。
		 * 
		 * @param enqueteQuestionType
		 *            enqueteQuestionType
		 */
		public void setEnqueteQuestionType(String enqueteQuestionType) {
			this.enqueteQuestionType = enqueteQuestionType;
		}

		/**
		 * questionDisplayOrderを設定します。
		 * 
		 * @param questionDisplayOrder
		 *            questionDisplayOrder
		 */
		public void setQuestionDisplayOrder(String questionDisplayOrder) {
			this.questionDisplayOrder = questionDisplayOrder;
		}

		/**
		 * requiredFlgを設定します。
		 * 
		 * @param requiredFlg
		 *            requiredFlg
		 */
		public void setRequiredFlg(String requiredFlg) {
			this.requiredFlg = requiredFlg;
		}

		/**
		 * choicesListを取得します。
		 * 
		 * @return choicesList
		 */
		public List<ChoicesListBean> getChoicesList() {
			return choicesList;
		}

		/**
		 * choicesListを設定します。
		 * 
		 * @param choicesList
		 *            choicesList
		 */
		public void setChoicesList(List<ChoicesListBean> choicesList) {
			this.choicesList = choicesList;
		}

		/**
		 * @param enqueteQuestionContentEn
		 *            the enqueteQuestionContentEn to set
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
		 * @param enqueteQuestionContentJp
		 *            the enqueteQuestionContentJp to set
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
	 * choiceEditButtonDisplayを取得します。
	 * 
	 * @return choiceEditButtonDisplay
	 */
	public boolean isChoiceEditButtonDisplay() {
		return choiceEditButtonDisplay;
	}

	/**
	 * choiceEditButtonDisplayを設定します。
	 * 
	 * @param choiceEditButtonDisplay
	 */
	public void setChoiceEditButtonDisplay(boolean choiceEditButtonDisplay) {
		this.choiceEditButtonDisplay = choiceEditButtonDisplay;
	}

	/**
	 * mainEditModeを取得します。
	 * 
	 * @return mainEditMode
	 */
	public String getMainEditMode() {
		return mainEditMode;
	}

	/**
	 * mainEditModeを設定します。
	 * 
	 * @param mainEditMode
	 */
	public void setMainEditMode(String mainEditMode) {
		this.mainEditMode = mainEditMode;
	}

	/**
	 * detailEditModeを取得します。
	 * 
	 * @return detailEditMode
	 */
	public String getDetailEditMode() {
		return detailEditMode;
	}

	/**
	 * editModeを設定します。
	 * 
	 * @param detailEditMode
	 */
	public void setDetailEditMode(String detailEditMode) {
		this.detailEditMode = detailEditMode;
	}

	/**
	 * updateDatetimeを取得します。
	 * 
	 * @return updateDatetime
	 */
	public Date getUpdateDatetime() {
		return DateUtil.immutableCopy(updateDatetime);
	}

	/**
	 * updateDatetimeを設定します。
	 * 
	 * @param updateDatetime
	 */
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
	}

	/**
	 * choiceButtonDisplayを取得します。
	 * 
	 * @return choiceButtonDisplay
	 */
	public boolean isChoiceButtonDisplay() {
		return choiceButtonDisplay;
	}

	/**
	 * choiceButtonDisplayを設定します。
	 * 
	 * @param choiceButtonDisplay
	 */
	public void setChoiceButtonDisplay(boolean choiceButtonDisplay) {
		this.choiceButtonDisplay = choiceButtonDisplay;
	}

	/**
	 * choicesListを取得します。
	 * 
	 * @return choicesList
	 */
	public List<ChoicesListBean> getChoicesList() {
		return choicesList;
	}

	/**
	 * choicesListを設定します。
	 * 
	 * @param choicesList
	 */
	public void setChoicesList(List<ChoicesListBean> choicesList) {
		this.choicesList = choicesList;
	}

	/**
	 * registerChoiceを取得します。
	 * 
	 * @return registerChoice
	 */
	public ChoicesListBean getRegisterChoice() {
		return registerChoice;
	}

	/**
	 * registerChoiceを設定します。
	 * 
	 * @param registerChoice
	 */
	public void setRegisterChoice(ChoicesListBean registerChoice) {
		this.registerChoice = registerChoice;
	}

	/**
	 * enqueteButtonDisplayを取得します。
	 * 
	 * @return enqueteButtonDisplay
	 */
	public boolean isEnqueteButtonDisplay() {
		return enqueteButtonDisplay;
	}

	/**
	 * enqueteButtonDisplayを設定します。
	 * 
	 * @param enqueteButtonDisplay
	 */
	public void setEnqueteButtonDisplay(boolean enqueteButtonDisplay) {
		this.enqueteButtonDisplay = enqueteButtonDisplay;
	}

	/**
	 * questionButtonDisplayを取得します。
	 * 
	 * @return questionButtonDisplay
	 */
	public boolean isQuestionButtonDisplay() {
		return questionButtonDisplay;
	}

	/**
	 * questionButtonDisplayを設定します。
	 * 
	 * @param questionButtonDisplay
	 */
	public void setQuestionButtonDisplay(boolean questionButtonDisplay) {
		this.questionButtonDisplay = questionButtonDisplay;
	}

	/**
	 * questionsAreaDisplayを取得します。
	 * 
	 * @return questionsAreaDisplay
	 */
	public boolean isQuestionsAreaDisplay() {
		return questionsAreaDisplay;
	}

	/**
	 * questionsAreaDisplayを設定します。
	 * 
	 * @param questionsAreaDisplay
	 */
	public void setQuestionsAreaDisplay(boolean questionsAreaDisplay) {
		this.questionsAreaDisplay = questionsAreaDisplay;
	}

	/**
	 * choicesAreaDisplayを取得します。
	 * 
	 * @return choicesAreaDisplay
	 */
	public boolean isChoicesAreaDisplay() {
		return choicesAreaDisplay;
	}

	/**
	 * choicesAreaDisplayを設定します。
	 * 
	 * @param choicesAreaDisplay
	 */
	public void setChoicesAreaDisplay(boolean choicesAreaDisplay) {
		this.choicesAreaDisplay = choicesAreaDisplay;
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
	 * enqueteEndDateを取得します。
	 * 
	 * @return enqueteEndDate
	 */
	public String getEnqueteEndDate() {
		return enqueteEndDate;
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
	 * enqueteStartDateを取得します。
	 * 
	 * @return enqueteStartDate
	 */
	public String getEnqueteStartDate() {
		return enqueteStartDate;
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
	 * enqueteCodeを設定します。
	 * 
	 * @param enqueteCode
	 *            enqueteCode
	 */
	public void setEnqueteCode(String enqueteCode) {
		this.enqueteCode = enqueteCode;
	}

	/**
	 * enqueteEndDateを設定します。
	 * 
	 * @param enqueteEndDate
	 *            enqueteEndDate
	 */
	public void setEnqueteEndDate(String enqueteEndDate) {
		this.enqueteEndDate = enqueteEndDate;
	}

	/**
	 * enqueteInvestPointを設定します。
	 * 
	 * @param enqueteInvestPoint
	 *            enqueteInvestPoint
	 */
	public void setEnqueteInvestPoint(String enqueteInvestPoint) {
		this.enqueteInvestPoint = enqueteInvestPoint;
	}

	/**
	 * enqueteNameを設定します。
	 * 
	 * @param enqueteName
	 *            enqueteName
	 */
	public void setEnqueteName(String enqueteName) {
		this.enqueteName = enqueteName;
	}

	/**
	 * enqueteStartDateを設定します。
	 * 
	 * @param enqueteStartDate
	 *            enqueteStartDate
	 */
	public void setEnqueteStartDate(String enqueteStartDate) {
		this.enqueteStartDate = enqueteStartDate;
	}

	/**
	 * questionListを設定します。
	 * 
	 * @param questionList
	 *            questionList
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
	 *            リクエストパラメータ
	 */
	public void createAttributes(RequestParameter reqparam) {
		this.setEnqueteCode(WebUtil.escapeXml(reqparam.get("enqueteCode")));
		this.setEnqueteName(reqparam.get("enqueteName"));
		// add by cs_yuli 20120516 start
		this.setEnqueteNameEn(reqparam.get("enqueteNameEn"));
		this.setEnqueteNameJp(reqparam.get("enqueteNameJp"));
		this.getRegisterChoice().setEnqueteChoicesEn(
				reqparam.get("enqueteChoicesEn"));
		this.getRegisterChoice().setEnqueteChoicesJp(
				reqparam.get("enqueteChoicesJp"));
		this.getRegisterQuestion().setEnqueteQuestionContentEn(
				reqparam.get("registerEnqueteQuestionContentEn"));
		this.getRegisterQuestion().setEnqueteQuestionContentJp(
				reqparam.get("registerEnqueteQuestionContentJp"));
		// add by cs_yuli 20120516 start
		this.setEnqueteStartDate(reqparam.getDateString("enqueteStartDate"));
		this.setEnqueteEndDate(reqparam.getDateString("enqueteEndDate"));
		this.setEnqueteInvestPoint(reqparam.get("enqueteInvestPoint"));

		this.getRegisterQuestion().setQuestionDisplayOrder(
				reqparam.get("registerQuestionDisplayOrder"));
		this.getRegisterQuestion().setEnqueteQuestionContent(
				reqparam.get("registerEnqueteQuestionContent"));
		this.getRegisterQuestion().setEnqueteQuestionType(
				reqparam.get("registerEnqueteQuestionType"));

		if (StringUtil.hasValue(reqparam.get("registerRequiredFlg"))) {
			this.getRegisterQuestion().setRequiredFlg(
					reqparam.get("registerRequiredFlg"));
		} else {
			this.getRegisterQuestion().setRequiredFlg("0");
		}

		this.getRegisterChoice().setQuestionNo(
				reqparam.get("enqueteQuestionNo"));
		this.getRegisterChoice().setChoicesDisplayOrder(
				reqparam.get("choicesDisplayOrder"));
		this.getRegisterChoice().setEnqueteChoices(
				reqparam.get("enqueteChoices"));

	}

	/**
	 * モジュールIDを取得します。
	 * 
	 * @return モジュールID
	 */
	public String getModuleId() {
		return "U1060120";
	}

	/**
	 * モジュール名を取得します。
	 * 
	 * @return モジュール名
	 */
	public String getModuleName() {
		return Messages
				.getString("web.bean.back.communication.EnqueteEditBean.0");
	}

	/**
	 * registerQuestionを取得します。
	 * 
	 * @return registerQuestion
	 */
	public QuestionListBean getRegisterQuestion() {
		return registerQuestion;
	}

	/**
	 * registerQuestionを設定します。
	 * 
	 * @param registerQuestion
	 */
	public void setRegisterQuestion(QuestionListBean registerQuestion) {
		this.registerQuestion = registerQuestion;
	}

	/**
	 * @param enqueteNameEn
	 *            the enqueteNameEn to set
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
	 * @param enqueteNameJp
	 *            the enqueteNameJp to set
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
