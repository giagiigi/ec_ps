package jp.co.sint.webshop.utility;

import java.io.IOException;
import java.io.Writer;

import jp.co.sint.webshop.configure.WebshopConfig;

/**
 * @author System Integrator Corp.
 */
public class FilteringWriter extends Writer {

  private Writer inner;

  private ConvertMode mode;

  private CharacterConverter converter;
  
  /**
   * @param writer
   *          書込対象
   */
  public FilteringWriter(Writer writer) {
    WebshopConfig config = DIContainer.getWebshopConfig();
    this.converter = DIContainer.getCharacterConverter();
    this.mode = ConvertMode.getInstance(config.getCsvCharset());
    this.inner = writer;
  }

  /**
   * @param writer
   *          書込対象
   */
  public FilteringWriter(Writer writer, ConvertMode mode) {
    this(writer);
    this.mode = mode;
  }

  /**
   * 配列の一部にJis2004エンコーディングに対応した文字を書き込みます。<br>
   * Jis2004で定義されていない文字が読み込まれた場合、「〓」に置き換えます。<br>
   * 書き込みに失敗した場合、IOExceptionを投げます。
   */
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = 0; i < len; i++) {
//      cbuf[i + off] = mode.convertFromUnicode(Jis2004Util.convertChar(cbuf[i + off]));
      cbuf[i + off] = mode.convertFromUnicode(converter.convertChar(cbuf[i + off]));
    }
    inner.write(cbuf, off, len);
  }

  /**
   * ストリームをフラッシュします。
   */
  public void flush() throws IOException {
    inner.flush();
  }

  /**
   * ストリームを閉じてフラッシュします。
   */
  public void close() throws IOException {
    inner.close();
  }

}
