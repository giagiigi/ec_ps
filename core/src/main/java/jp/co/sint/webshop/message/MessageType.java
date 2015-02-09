package jp.co.sint.webshop.message;

public interface MessageType {
  
  /**
   * メッセージプロパティIDの取得。
   * 
   * @return メッセージプロパティID
   */
  String getMessagePropertyId();

  /**
   * メッセージリソースの取得。
   * 
   * @return メッセージリソース
   */
  String getMessageResource();

}
