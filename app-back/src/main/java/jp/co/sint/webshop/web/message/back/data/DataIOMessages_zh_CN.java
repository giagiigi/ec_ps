package jp.co.sint.webshop.web.message.back.data;

import java.util.ListResourceBundle;

public class DataIOMessages_zh_CN extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        {
            "file_register_failed", "{0}:文件登录失败了。"
        }, {
            "file_delete_failed", "{0}:文件删除失败了。"
        }, {
            "contents_register_failed", "{0}:内容登录失败了。"
        }, {
            "contents_delete_failed", "{0}:内容删除失败了。"
        }
    };
    return obj;
  }

}
