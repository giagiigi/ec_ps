package jp.co.sint.webshop.service.communication;

public final class EnqueteDeleteQuery {

  private static final long serialVersionUID = 1L;

  private static final String DELETE_ENQUETE = "DELETE FROM ENQUETE WHERE ENQUETE_CODE = ?";

  private static final String DELETE_ENQUETE_QUESTION = "DELETE FROM ENQUETE_QUESTION WHERE ENQUETE_CODE = ?";

  private static final String DELETE_ENQUETE_CHOICE = "DELETE FROM ENQUETE_CHOICE WHERE ENQUETE_CODE = ?";

  private static final String DELETE_ENQUETE_ANSWER = "DELETE FROM ENQUETE_ANSWER_HEADER WHERE ENQUETE_CODE = ?";

  private static final String DELETE_ENQUETE_REPLY_CHOICE = "DELETE FROM ENQUETE_REPLY_CHOICES WHERE ENQUETE_CODE = ?";

  private static final String DELETE_ENQUETE_REPLY_INPUT = "DELETE FROM ENQUETE_REPLY_INPUT WHERE ENQUETE_CODE = ?";

  private EnqueteDeleteQuery() {

  }

  public static String[] getDeleteQuery() {

    return new String[] {
        DELETE_ENQUETE,
        DELETE_ENQUETE_QUESTION,
        DELETE_ENQUETE_CHOICE,
        DELETE_ENQUETE_ANSWER,
        DELETE_ENQUETE_REPLY_CHOICE,
        DELETE_ENQUETE_REPLY_INPUT
    };
  }

}
