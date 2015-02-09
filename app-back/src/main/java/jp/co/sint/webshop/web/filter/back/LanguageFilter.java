package jp.co.sint.webshop.web.filter.back;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import jp.co.sint.webshop.configure.LocaleContext;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.LocaleUtil;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;

/**
 * @author kousen.
 */
public class LanguageFilter implements Filter {

  public void init(FilterConfig config) throws ServletException {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
   
    LocaleContext loc = DIContainer.getLocaleContext();

    String languageCode = loc.getSystemLanguageCode();
    ResourceBundle bundle = ResourceBundle.getBundle("jp.co.sint.webshop.web.text.back.JspResources",
        LocaleUtil.getLocale(languageCode));
    Map<String, String> literal = LocaleUtil.getResourceMap(bundle);
    
    request.setAttribute(WebFrameworkConstants.ATTRIBUTE_LANGUAGE, languageCode);
    request.setAttribute(WebFrameworkConstants.ATTRIBUTE_LITERAL, literal);
    chain.doFilter(request, response);
  }

  public void destroy() {
  }

}
