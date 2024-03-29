package jp.co.sint.webshop.service.communication;

public final class EnqueteQuery {
  /**
   * default constructor
   */
  private EnqueteQuery() {
  }

  private static final long serialVersionUID = 1L;

  public static final String LOAD_QUESTIONS =
    "SELECT ENQUETE_CODE,"
        + " ENQUETE_QUESTION_NO,"
        + " ENQUETE_QUESTION_TYPE,"
        + " ENQUETE_QUESTION_CONTENT,"
        + " ENQUETE_QUESTION_CONTENT_EN,"
        + " ENQUETE_QUESTION_CONTENT_JP,"
        + " DISPLAY_ORDER, NECESSARY_FLG,"
        + " ORM_ROWID, CREATED_USER,"
        + " CREATED_DATETIME, UPDATED_USER,"
        + " UPDATED_DATETIME"
        + " FROM ENQUETE_QUESTION"
        + " WHERE ENQUETE_CODE = ?"
        + " ORDER BY DISPLAY_ORDER";

  public static final String LOAD_CHOICES =
    "SELECT ENQUETE_CODE,"
        + " ENQUETE_QUESTION_NO,"
        + " ENQUETE_CHOICES_NO,"
        + " ENQUETE_CHOICES,"
        + " ENQUETE_CHOICES_EN,"
        + " ENQUETE_CHOICES_JP,"
        + " DISPLAY_ORDER,"
        + " ORM_ROWID,"
        + " CREATED_USER,"
        + " CREATED_DATETIME,"
        + " UPDATED_USER,"
        + " UPDATED_DATETIME"
        + " FROM ENQUETE_CHOICE"
        + " WHERE ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?"
        + " ORDER BY DISPLAY_ORDER";

  public static final String CHECK_PERIOD =
    "SELECT ENQUETE_CODE,"
        + " ENQUETE_NAME,"
        + " ENQUETE_NAME_EN,"
        + " ENQUETE_NAME_JP,"
        + " ENQUETE_START_DATE,"
        + " ENQUETE_END_DATE,"
        + " ENQUETE_INVEST_POINT,"
        + " ORM_ROWID,"
        + " CREATED_USER,"
        + " CREATED_DATETIME,"
        + " UPDATED_USER,"
        + " UPDATED_DATETIME"
        + " FROM ENQUETE"
        + " WHERE (ENQUETE_START_DATE BETWEEN ? AND ?"
        + " OR ENQUETE_END_DATE BETWEEN ? AND ?"
        + " OR (ENQUETE_START_DATE <= ? and ENQUETE_END_DATE >= ?))"
        + " AND ENQUETE_CODE != ?";

  public static final String LOAD_REPLY_CHOICES =
    "SELECT CUSTOMER_CODE,"
        + " ENQUETE_CODE,"
        + " ENQUETE_QUESTION_NO,"
        + " ENQUETE_CHOICES_NO,"
        + " ORM_ROWID, CREATED_USER,"
        + " CREATED_DATETIME,"
        + " UPDATED_USER,"
        + " UPDATED_DATETIME"
        + " FROM ENQUETE_REPLY_CHOICES"
        + " WHERE ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?";

  public static final String LOAD_REPLY_INPUT =
    "SELECT ENQUETE_CODE,"
        + " ENQUETE_QUESTION_NO,"
        + " CUSTOMER_CODE,"
        + " ENQUETE_REPLY,"
        + " ORM_ROWID,"
        + " CREATED_USER,"
        + " CREATED_DATETIME,"
        + " UPDATED_USER,"
        + " UPDATED_DATETIME"
        + " FROM ENQUETE_REPLY_INPUT"
        + " WHERE ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?"
        + " ORDER BY CREATED_DATETIME DESC";

  public static final String LOAD_ENQUETE =
    "SELECT ENQUETE_CODE,"
    + " ENQUETE_NAME,"
    + " ENQUETE_NAME_EN,"
    + " ENQUETE_NAME_JP,"
    + " ENQUETE_START_DATE,"
    + " ENQUETE_END_DATE,"
    + " ENQUETE_INVEST_POINT,"
    + " ORM_ROWID,"
    + " CREATED_USER,"
    + " CREATED_DATETIME,"
    + " UPDATED_USER,"
    + " UPDATED_DATETIME"
    + " FROM ENQUETE"
    + " WHERE ? BETWEEN ENQUETE.ENQUETE_START_DATE"
    + " AND ENQUETE.ENQUETE_END_DATE";  
  
  public static final String COUNT_ENQUETE_ANSWER =
    "SELECT COUNT(CUSTOMER_CODE) FROM ENQUETE_ANSWER_HEADER WHERE ENQUETE_CODE = ?";

  public static final String COUNT_REPLIED_QUESTION_PERSONS_INPUTTYPE =
    "SELECT COUNT(CUSTOMER_CODE) FROM ENQUETE_REPLY_INPUT WHERE ENQUETE_CODE = ? AND ENQUETE_QUESTION_NO = ?";

  public static final String COUNT_REPLIED_QUESTION_PERSONS_CHOICETYPE =
    "SELECT COUNT(DISTINCT CUSTOMER_CODE) FROM ENQUETE_REPLY_CHOICES WHERE ENQUETE_CODE = ? AND ENQUETE_QUESTION_NO = ?";

  public static final String COUNT_REPLIED_QUESTION =
    "SELECT COUNT(CUSTOMER_CODE) FROM ENQUETE_REPLY_CHOICES WHERE ENQUETE_CODE = ? AND ENQUETE_QUESTION_NO = ?";

  public static final String COUNT_REPLIED_CHOICE =
    "SELECT COUNT(CUSTOMER_CODE)"
    + " FROM ENQUETE_REPLY_CHOICES"
    + " WHERE ENQUETE_CODE = ?"
    + " AND ENQUETE_QUESTION_NO = ?"
    + " AND ENQUETE_CHOICES_NO = ?";

  public static final String CSV_OUTPUT_CHOICETYPE =
    "SELECT EC.ENQUETE_CHOICES CHOICE_NAME,"
    + " COUNT(ERC.CUSTOMER_CODE) NUM_OF_ANSWERS"
    + " FROM ENQUETE_CHOICE EC"
    + " LEFT OUTER JOIN ENQUETE_REPLY_CHOICES ERC"
    + " ON ERC.ENQUETE_CODE = EC.ENQUETE_CODE"
    + " AND ERC.ENQUETE_QUESTION_NO = EC.ENQUETE_QUESTION_NO"
    + " AND ERC.ENQUETE_CHOICES_NO = EC.ENQUETE_CHOICES_NO"
    + " WHERE EC.ENQUETE_CODE = ?"
    + " AND EC.ENQUETE_QUESTION_NO = ?"
    + " GROUP BY EC.ENQUETE_CHOICES"
    + " ORDER BY COUNT(ERC.CUSTOMER_CODE) DESC";

  public static final String CSV_OUTPUT_INPUTTYPE =
    "SELECT ERI.ENQUETE_REPLY CHOICE_NAME"
    + " FROM ENQUETE_REPLY_INPUT ERI"
    + " LEFT OUTER JOIN ENQUETE_ANSWER_HEADER EAH"
    + " ON ERI.CUSTOMER_CODE = EAH.CUSTOMER_CODE AND ERI.ENQUETE_CODE = EAH.ENQUETE_CODE "
    + " WHERE ERI.ENQUETE_CODE = ?"
    + " AND ERI.ENQUETE_QUESTION_NO = ?"
    + " ORDER BY EAH.ENQUETE_REPLY_DATE ASC";

}
