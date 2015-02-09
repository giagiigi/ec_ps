package jp.co.sint.webshop.message.impl;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.message.MessageType;
import jp.co.sint.webshop.utility.StringUtil;

public class MessageProvider {

  private ResourceBundle bundle;

  public MessageProvider(String resource) {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      Class<?> c = Class.forName(resource);
      bundle = (ResourceBundle) c.newInstance();
    } catch (ClassNotFoundException e) {
      logger.error(e);
    } catch (InstantiationException e) {
      logger.error(e);
    } catch (IllegalAccessException e) {
      logger.error(e);
    }
  }

  public String get(MessageType code, String... params) {
    String pattern = StringUtil.coalesce(bundle.getString(code.getMessagePropertyId()), "");
    MessageFormat format = new MessageFormat(pattern);
    return format.format(params);
  }

}
