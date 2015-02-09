package jp.co.sint.webshop.service.communication;

public final class MailMagazineQuery {
  
  /**
   * default constructor
   */
  private MailMagazineQuery() {
  }

  private static final long serialVersionUID = 1L;

  public static final String LOAD_SUBSCRIBERS_MAGAZINE_LIST =
    "SELECT M.MAIL_MAGAZINE_CODE,"
        + " M.MAIL_MAGAZINE_TITLE,"
        + " M.MAIL_MAGAZINE_DESCRIPTION,"
        + " M.DISPLAY_FLG,"
        + " M.ORM_ROWID,"
        + " M.CREATED_USER,"
        + " M.CREATED_DATETIME,"
        + " M.UPDATED_USER,"
        + " M.UPDATED_DATETIME"
        + " FROM MAIL_MAGAZINE_SUBSCRIBER MS"
        + " INNER JOIN MAIL_MAGAZINE M"
        + " ON M.MAIL_MAGAZINE_CODE = MS.MAIL_MAGAZINE_CODE"
        + " WHERE MS.EMAIL = ?";

  public static final String LOAD_HEADER_LIST =
    "SELECT M.MAIL_MAGAZINE_CODE,"
        + " M.MAIL_MAGAZINE_TITLE,"
        + " M.MAIL_MAGAZINE_DESCRIPTION,"
        + " M.DISPLAY_FLG,"
        + " M.ORM_ROWID,"
        + " M.CREATED_USER,"
        + " M.CREATED_DATETIME,"
        + " M.UPDATED_USER,"
        + " M.UPDATED_DATETIME,"
        + " COUNT(MS.EMAIL) AS SUBSCRIBER_AMOUNT"
        + " FROM MAIL_MAGAZINE M"
        + " LEFT OUTER JOIN MAIL_MAGAZINE_SUBSCRIBER MS"
        + " ON M.MAIL_MAGAZINE_CODE = MS.MAIL_MAGAZINE_CODE"
        + " GROUP BY"
        + " M.MAIL_MAGAZINE_CODE,"
        + " M.MAIL_MAGAZINE_TITLE,"
        + " M.MAIL_MAGAZINE_DESCRIPTION,"
        + " M.DISPLAY_FLG,"
        + " M.ORM_ROWID,"
        + " M.CREATED_USER,"
        + " M.CREATED_DATETIME,"
        + " M.UPDATED_USER,"
        + " M.UPDATED_DATETIME"
        + " ORDER BY M.MAIL_MAGAZINE_CODE ASC";

    public static final String LOAD_DISPLAY_LIST =
      "SELECT M.MAIL_MAGAZINE_CODE,"
          + " M.MAIL_MAGAZINE_TITLE,"
          + " M.MAIL_MAGAZINE_DESCRIPTION,"
          + " M.DISPLAY_FLG,"
          + " M.ORM_ROWID,"
          + " M.CREATED_USER,"
          + " M.CREATED_DATETIME,"
          + " M.UPDATED_USER,"
          + " M.UPDATED_DATETIME,"
          + " COUNT(MS.EMAIL) AS SUBSCRIBER_AMOUNT"
          + " FROM MAIL_MAGAZINE M"
          + " LEFT OUTER JOIN MAIL_MAGAZINE_SUBSCRIBER MS"
          + " ON M.MAIL_MAGAZINE_CODE = MS.MAIL_MAGAZINE_CODE"
          + " WHERE M.DISPLAY_FLG = '1'"
          + " GROUP BY"
          + " M.MAIL_MAGAZINE_CODE,"
          + " M.MAIL_MAGAZINE_TITLE,"
          + " M.MAIL_MAGAZINE_DESCRIPTION,"
          + " M.DISPLAY_FLG,"
          + " M.ORM_ROWID,"
          + " M.CREATED_USER,"
          + " M.CREATED_DATETIME,"
          + " M.UPDATED_USER,"
          + " M.UPDATED_DATETIME"
          + " ORDER BY COUNT(MS.EMAIL) DESC";

    public static final String DELETE_SUBSCRIBERS = "DELETE FROM MAIL_MAGAZINE_SUBSCRIBER WHERE MAIL_MAGAZINE_CODE = ?";

}