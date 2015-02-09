package jp.co.sint.webshop.service;

/**
 * パラメータ例外を表すクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ParameterException extends ServiceException {

  /** uid */
  private static final long serialVersionUID = 1L;

  /**
   * 詳細メッセージに null を使用して、新しい例外を構築します。
   */
  public ParameterException() {
    super();
  }

  /**
   * 指定された詳細メッセージを使用して、新しい例外を構築します。
   */
  public ParameterException(Throwable cause) {
    super(cause);
  }

  /**
   * 指定された詳細メッセージおよび原因を使用して新しい例外を構築します。
   * 
   * @param message
   * @param cause
   */
  public ParameterException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * (cause==null ? null : cause.toString()) の指定された原因および詳細メッセージを使用して新しい例外を構築します。
   * 
   * @param message
   */
  public ParameterException(String message) {
    super(message);
  }
}
