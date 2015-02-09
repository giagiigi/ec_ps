package jp.co.sint.webshop.web.message.back.data;

import java.util.ListResourceBundle;

public class DataIOMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        {
            "file_register_failed", "{0}:File registration falure."
        }, {
            "file_delete_failed", "{0}:File delition falure."
        }, {
            "contents_register_failed", "{0}:Contents registration falure."
        }, {
            "contents_delete_failed", "{0}:Contents deletion falure."
        }
    };
    return obj;
  }

}
