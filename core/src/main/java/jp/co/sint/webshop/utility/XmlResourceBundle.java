/**
 * 
 */
package jp.co.sint.webshop.utility;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * @author System Integrator Corp.
 */
public class XmlResourceBundle extends ResourceBundle {

  private Properties props;

  public XmlResourceBundle(InputStream stream) throws IOException {
    props = new Properties();
    props.loadFromXML(stream);
  }

  public Enumeration<String> getKeys() {
    Vector<String> v = new Vector<String>(props.stringPropertyNames());
    return v.elements();
  }

  @Override
  protected Object handleGetObject(String key) {
    return props.getProperty(key);
  }
  
  public static final Control CONTROL = new Control();

  public static class Control extends ResourceBundle.Control {

    public List<String> getFormats(String baseName) {
      if (baseName == null) {
        throw new NullPointerException();
      }
      return Arrays.asList("xml");
    }

    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
        throws IllegalAccessException, InstantiationException, IOException {
      if (baseName == null || locale == null || format == null || loader == null) {
        throw new NullPointerException();
      }
      ResourceBundle bundle = null;
      if (format.equals("xml")) {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, format);
        InputStream stream = null;
        if (reload) {
          URL url = loader.getResource(resourceName);
          if (url != null) {
            URLConnection connection = url.openConnection();
            if (connection != null) {
              connection.setUseCaches(false);
              stream = connection.getInputStream();
            }
          }
        } else {
          stream = loader.getResourceAsStream(resourceName);
        }
        if (stream != null) {
          BufferedInputStream bis = null;
          try {
            bis = new BufferedInputStream(stream);
            bundle = new XmlResourceBundle(bis);
          } catch (IOException ioEx) {
            throw ioEx;
          } finally {
            IOUtil.close(bis);
          }
        }
      }
      return bundle;
    }
  }
}
