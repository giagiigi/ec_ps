package jp.co.sint.webshop.utility;

import java.io.IOException;
import java.io.Reader;

import jp.co.sint.webshop.configure.WebshopConfig;

/**
 * @author System Integrator Corp.
 */
public class FilteringReader extends Reader {

  private Reader inner;

  private ConvertMode mode;
  
  private CharacterConverter converter;

  /**
   * @param reader
   *          取込対象
   */
  public FilteringReader(Reader reader) {
    WebshopConfig config = DIContainer.getWebshopConfig();
    this.converter = DIContainer.getCharacterConverter();
    this.mode = ConvertMode.getInstance(config.getCsvCharset());
    this.inner = reader;
  }

  public FilteringReader(Reader reader, ConvertMode mode) {
    this(reader);
    this.mode = mode;
  }

  /**
   * 配列の一部にJis2004エンコーディングに対応した文字を読み込みます。<br>
   * Jis2004で定義されていない文字が読み込まれた場合、「〓」に置き換えます。<br>
   * 読み込みに失敗した場合、IOExceptionを投げます。
   */
  public int read(char[] cbuf, int off, int len) throws IOException {
    int result = inner.read(cbuf, off, len);
    for (int i = 0; i < result; i++) {
//      cbuf[i] = Jis2004Util.convertChar(mode.convertToUnicode(cbuf[i]));
      cbuf[i] = converter.convertChar(mode.convertToUnicode(cbuf[i]));
    }
    return result;
  }

  /**
   * ストリームを閉じます。
   */
  public void close() throws IOException {
    inner.close();
  }

}
