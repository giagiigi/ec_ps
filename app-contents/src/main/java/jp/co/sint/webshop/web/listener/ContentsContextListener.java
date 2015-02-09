package jp.co.sint.webshop.web.listener;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import jp.co.sint.webshop.code.ConstantResource;
import jp.co.sint.webshop.configure.AdvertValue;
import jp.co.sint.webshop.configure.ExampleValue;
import jp.co.sint.webshop.configure.MutableStatus;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.text.contents.Messages;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;


public class ContentsContextListener extends WebshopContextListener {
  @SuppressWarnings("unchecked")
  public void contextInitialized(ServletContextEvent e) {
    ServletContext context = e.getServletContext();

    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("web.listener.ContentsContextListener.0"));
    try {
      for (Enumeration<String> en = context.getInitParameterNames(); en.hasMoreElements();) {
        String name = en.nextElement();
        String value = context.getInitParameter(name);
        logger.info(Messages.log("web.listener.ContentsContextListener.1")
            + name + Messages.log("web.listener.ContentsContextListener.2") + value);
      }
      logger.info(Messages.log("web.listener.ContentsContextListener.3") + context.getServerInfo());
      logger.info(Messages.log("web.listener.ContentsContextListener.4"));

      // ContextLoaderListenerで起動済みのApplicationContextを取得て
      // DIContainerに設定する。
      String appCtxId = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
      ApplicationContext appCtx = (ApplicationContext) context.getAttribute(appCtxId);
      if (appCtx != null) {
        DIContainer.setApplicationContext(appCtx);
      } else {
        throw new RuntimeException(Messages.getString("web.listener.ContentsContextListener.5"));
      }

      // システムプロパティの設定(EL式で${system.xx})
      WebshopConfig config = DIContainer.getWebshopConfig();
      context.setAttribute(WebFrameworkConstants.ATTRIBUTE_SYSTEM, config);
      logger.info(Messages.log("web.listener.ContentsContextListener.6"));

      // システムリソースクラスの設定(EL式で${resource.xx})
      ConstantResource resource = new ConstantResource();
      context.setAttribute(WebFrameworkConstants.ATTRIBUTE_RESOURCE, resource);
      logger.info(Messages.log("web.listener.ContentsContextListener.7"));

      // 動的リソースの設定(EL式で${status.xx})
      MutableStatus status = new MutableStatus();
      context.setAttribute(WebFrameworkConstants.ATTRIBUTE_STATUS, status);
      logger.info(Messages.log("web.listener.ContentsContextListener.8"));

      // サンプルデータの設定(EL式で${example.xx})
      ExampleValue example = DIContainer.getExampleValue();
      context.setAttribute(WebFrameworkConstants.ATTRIBUTE_EXAMPLE, example);
      logger.info(Messages.log("web.listener.ContentsContextListener.9"));

      //Add by V10-CH start
      AdvertValue advert = DIContainer.getAdvertValue();
      context.setAttribute(WebFrameworkConstants.ATTRIBUTE_ADVERT, advert);
      logger.info(Messages.log("web.listener.ContentsContextListener.12"));
      //Add by V10-CH end
    } catch (RuntimeException ex) {
      logger.fatal(Messages.log("web.listener.ContentsContextListener.10"));
      logger.fatal(ex);
      throw ex;
    }
    logger.info(Messages.log("web.listener.ContentsContextListener.11"));

  }

}
