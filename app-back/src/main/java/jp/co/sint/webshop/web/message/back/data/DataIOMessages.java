package jp.co.sint.webshop.web.message.back.data;

import java.util.ListResourceBundle;

public class DataIOMessages extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        {
            "file_register_failed", "{0}:ファイル登録に失敗しました。"
        }, {
            "file_delete_failed", "{0}:ファイル削除に失敗しました。"
        }, {
            "contents_register_failed", "{0}:コンテンツ登録に失敗しました。"
        }, {
            "contents_delete_failed", "{0}:コンテンツ削除に失敗しました。"
        }
    };
    return obj;
  }

}
