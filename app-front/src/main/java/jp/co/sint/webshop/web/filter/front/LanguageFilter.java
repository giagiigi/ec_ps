package jp.co.sint.webshop.web.filter.front;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sint.webshop.configure.LocaleContext;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.LocaleUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;

/**
 * @author kousen.
 */
public class LanguageFilter implements Filter {

  public void init(FilterConfig config) throws ServletException {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    HttpServletRequest httpReq = (HttpServletRequest) request;
    // 20120521 shen add start
    HttpServletResponse httpRes = (HttpServletResponse) response;
    // 20120521 shen add end
    String uri = httpReq.getRequestURI();

    LocaleContext loc = DIContainer.getLocaleContext();
    List<String> langSet = loc.getSupportingLanguages();
    boolean isSet = false;

    String dispatchUrl = null;
    for (String lang : langSet) {
      isSet = uri.startsWith(httpReq.getContextPath() + "/" + lang);
      if (isSet) {
        // 20120521 shen add start
        // 当前语言保存到Cookie
        setCookie(httpRes, lang);
        // 20120521 shen add end
        httpReq.setAttribute(WebFrameworkConstants.ATTRIBUTE_LANGUAGE, lang);
        dispatchUrl = httpReq.getRequestURI().replace(httpReq.getContextPath() + "/" + lang, "");

        ResourceBundle bundle = ResourceBundle.getBundle("jp.co.sint.webshop.web.text.front.JspResources", LocaleUtil
            .getLocale(lang));
        Map<String, String> literal = LocaleUtil.getResourceMap(bundle);
        request.setAttribute(WebFrameworkConstants.ATTRIBUTE_LITERAL, literal);
        break;
      }
    }

    // URLで言語が指定されている場合フォワード、されてない場合チェイン
    if (isSet) {
      RequestDispatcher dispatcher = request.getRequestDispatcher(dispatchUrl);
      dispatcher.forward(request, response);
    } else {
      if (httpReq.getAttribute(WebFrameworkConstants.ATTRIBUTE_LANGUAGE) == null) {
        // 20120521 shen update start
        // httpReq.setAttribute(WebFrameworkConstants.ATTRIBUTE_LANGUAGE, loc.getSystemLanguageCode()); // デフォルト言語をリクエストにセット
        String languageCode = "";
        // 取得Cookie的语言
        if (httpReq.getCookies() != null) {
          for (Cookie c : httpReq.getCookies()) {
            if (c.getName().equals("soukaiLang")) {
              languageCode = WebUtil.urlDecode(c.getValue());
              break;
            }
          }
        }

        // 20121127 UPDATE Start
        if (StringUtil.hasValue(httpReq.getContextPath()) && httpReq.getContextPath().equals("/mobile")) {
          // Cookie不保存
          if (StringUtil.isNullOrEmpty(languageCode)) {
            languageCode = "";
          }
        } else {
          // 取得客户端浏览器语言
          if (StringUtil.isNullOrEmpty(languageCode)) {
            String browserLang = httpReq.getLocale().getLanguage();
            if (browserLang.equals("zh")) {
              languageCode = LanguageCode.Zh_Cn.getValue();
            } else if (browserLang.equals("en")) {
              languageCode = LanguageCode.En_Us.getValue();
            } else if (browserLang.equals("ja")) {
              languageCode = LanguageCode.Ja_Jp.getValue();
            }
          }
          // 取得程序默认语言
          if (StringUtil.isNullOrEmpty(languageCode)) {
            languageCode = loc.getSystemLanguageCode();
            // 当前语言保存到Cookie
            setCookie(httpRes, languageCode);
          }
        }
        // 20121127 UPDATE End
        
        httpReq.setAttribute(WebFrameworkConstants.ATTRIBUTE_LANGUAGE, languageCode);
        // 20120521 shen update end
      }
      
      chain.doFilter(request, response);
    }

    
    
//    LocaleContext loc = DIContainer.getLocaleContext();
//
//    String languageCode = loc.getCurrentLanguageCode();
//    ResourceBundle bundle = ResourceBundle.getBundle("jp.co.sint.webshop.web.text.front.JspResources",
//        LocaleUtil.getLocale(languageCode));
//    Map<String, String> literal = LocaleUtil.getResourceMap(bundle);
//    
//    request.setAttribute(WebFrameworkConstants.ATTRIBUTE_LANGUAGE, languageCode);
//    request.setAttribute(WebFrameworkConstants.ATTRIBUTE_LITERAL, literal);
//    // 跳转到下一个
//    chain.doFilter(request, response);
  }

  public void destroy() {
  }

  // 20120521 shen add start
  private void setCookie(HttpServletResponse response, String languageCode) {
    Cookie c = new Cookie("soukaiLang", languageCode);
    c.setMaxAge(3600*60*24*30);
    c.setPath("/");
    response.addCookie(c);
  }
  // 20120521 shen add end
}
