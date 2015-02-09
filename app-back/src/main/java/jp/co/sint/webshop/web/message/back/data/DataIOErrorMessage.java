package jp.co.sint.webshop.web.message.back.data;

import jp.co.sint.webshop.message.MessageType;


public enum DataIOErrorMessage implements MessageType {
  
  /** ファイル登録失敗 */
  FILE_REGISTER_FAILED("file_register_failed"),
  /** ファイル削除失敗 */
  FILE_DELETE_FAILED("file_delete_failed"),
  /** コンテンツ登録失敗 */
  CONTENTS_REGISTER_FAILED("contents_register_failed"),
  /** コンテンツ削除失敗 */
  CONTENTS_DELETE_FAILED("contents_delete_failed");
  
  private String messagePropertyId;

  private DataIOErrorMessage(String messagePropertyId) {
    this.messagePropertyId = messagePropertyId;
  }
  
  public String getMessagePropertyId() {
    return this.messagePropertyId;
  }

  public String getMessageResource() {
    return "jp.co.sint.webshop.web.message.back.data.DataIOMessages";
  }

}
