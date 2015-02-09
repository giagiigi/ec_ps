package jp.co.sint.webshop.web.message.back;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class Messages extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    return BackMessages.toResource();
  }
}
