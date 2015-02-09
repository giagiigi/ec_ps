package jp.co.sint.webshop.service.data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * 正規表現によるFilenameFilterの実装クラスです。
 * 
 * @author System Integrator Corp.
 */
public class RegexFilenameFilter implements FilenameFilter {

  private String regex;

  public RegexFilenameFilter(String regex) {
    this.regex = regex;
  }

  public boolean accept(File dir, String name) {
    return Pattern.matches(regex, name);
  }

  /**
   * regexを取得します。
   * 
   * @return regex
   */

  public String getRegex() {
    return regex;
  }

}
