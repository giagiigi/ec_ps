package jp.co.sint.webshop.utility;

import java.io.IOException;
import java.io.Writer;

/**
 * 空の出力先です。
 * 
 * @author System Integrator Corp.
 */
public class VoidWriter extends Writer {

  public void close() throws IOException {
  }

  public void flush() throws IOException {
  }

  public void write(char[] cbuf, int off, int len) throws IOException {
  }

}
