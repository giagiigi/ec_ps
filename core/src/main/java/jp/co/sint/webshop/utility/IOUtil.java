package jp.co.sint.webshop.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

/**
 * 入出力処理ストリームの取得およびクローズを行うユーティリティクラスです。
 * 
 * @author System Integrator Corp.
 */
public final class IOUtil {

  /**
   * 入出力モードです。
   * 
   * @author System Integrator Corp.
   */
  public static enum IOMode {
    /** CSV */
    CSV,
    /** ログ */
    LOG,
    /** デフォルト(システムプロパティfile.encodingの値が使われます) */
    OS_DEFAULT;

    /**
     * モードごとのエンコーディングを返します。
     * 
     * @return エンコーディング
     */
    public String getEncoding() {
      switch (this) {
        case CSV:
          return DIContainer.getWebshopConfig().getCsvCharset();
        case LOG:
          return DIContainer.getWebshopConfig().getLogCharset();
        case OS_DEFAULT:
        default:
          return System.getProperty("file.encoding");
      }
    }
  }

  private IOUtil() {
  }

  /**
   * 開放しているInputStremインスタンスをcloseします。
   * 
   * @param input
   *          開放しているInputStremインスタンス
   */
  public static void close(InputStream input) {
    if (input != null) {
      try {
        input.close();
      } catch (Exception ex) {
        Logger.getLogger(IOUtil.class).warn(ex);
      }
    }
  }

  /**
   * 開放しているOutputStreamインスタンスをcloseします。
   * 
   * @param output
   *          開放しているOutputStreamインスタンス
   */
  public static void close(OutputStream output) {
    if (output != null) {
      try {
        output.close();
      } catch (Exception ex) {
        Logger.getLogger(IOUtil.class).warn(ex);
      }
    }
  }

  /**
   * 開放しているReaderインスタンスをcloseします。
   * 
   * @param reader
   *          開放しているReaderインスタンス
   */
  public static void close(Reader reader) {
    if (reader != null) {
      try {
        reader.close();
      } catch (Exception ex) {
        Logger.getLogger(IOUtil.class).warn(ex);
      }
    }
  }

  /**
   * 開放しているWriterインスタンスをcloseします。
   * 
   * @param writer
   *          開放しているWiterインスタンス
   */
  public static void close(Writer writer) {
    if (writer != null) {
      try {
        writer.close();
      } catch (Exception ex) {
        Logger.getLogger(IOUtil.class).warn(ex);
      }
    }
  }

  public static void close(ZipFile file) {
    if (file != null) {
      try {
        file.close();
      } catch (IOException ex) {
        Logger.getLogger(IOUtil.class).warn(ex);
      }
    }
  }

  /**
   * 書き込みバッファをフラッシュします。
   * 
   * @param out
   *          出力ストリーム
   */
  public static void flush(OutputStream out) {
    if (out != null) {
      try {
        out.close();
      } catch (Exception ex) {
        Logger.getLogger(IOUtil.class).warn(ex);
      }
    }
  }

  /**
   * 書き込みバッファをフラッシュします。
   * 
   * @param writer
   *          Writer
   */
  public static void flush(Writer writer) {
    if (writer != null) {
      try {
        writer.close();
      } catch (Exception ex) {
        Logger.getLogger(IOUtil.class).warn(ex);
      }
    }
  }

  /**
   * 引数で受け取ったReaderインスタンスから生成される、BufferedReaderインスタンスを取得します。
   * 生成されるBufferedReaderインスタンスのエンコーディングは、Jis2004に準拠します。
   * 
   * @param reader
   *          BufferedReaderを取得したいReaderインスタンス
   * @return BufferedReaderインスタンス
   * @throws IOException
   */
  public static Reader getReader(Reader reader) throws IOException {
    return new BufferedReader(new FilteringReader(reader));
  }

  /**
   * 引数で受け取ったFileインスタンスから生成される、FileInputStreamインスタンスを取得します。
   * 
   * @param file
   *          FileInputStreamを取得したいFileインスタンス
   * @return FileInputStreamインスタンス
   * @throws IOException
   */
  public static Reader getReader(File file) throws IOException {
    return getReader0(new FileInputStream(file), IOMode.CSV);
  }

  /**
   * 引数で受け取ったFileインスタンスから生成される、FileInputStreamインスタンスを取得します。
   * 
   * @param file
   *          FileInputStreamを取得したいFileインスタンス
   * @param mode
   *          入出力モード
   * @return FileInputStreamインスタンス
   * @throws IOException
   */
  public static Reader getReader(File file, IOMode mode) throws IOException {
    return getReader0(new FileInputStream(file), mode);
  }

  /**
   * 引数で受け取ったInputStreamインスタンスから生成される、BufferedReaderインスタンスを取得します。
   * 生成されるInputStreamインスタンスのエンコーディングは、Jis2004に準拠します。
   * 
   * @param in
   *          BufferedReaderを取得したいInputStreamインスタンス
   * @return BufferedReaderインスタンス
   * @throws IOException
   */
  public static Reader getReader(InputStream in) throws IOException {
    return getReader0(in, IOMode.CSV);
  }

  /**
   * 引数で受け取ったInputStreamインスタンスから生成される、BufferedReaderインスタンスを取得します。
   * 生成されるInputStreamインスタンスのエンコーディングは、Jis2004に準拠します。
   * 
   * @param in
   *          BufferedReaderを取得したいInputStreamインスタンス
   * @param mode
   *          入出力モード
   * @return BufferedReaderインスタンス
   * @throws IOException
   */
  public static Reader getReader(InputStream in, IOMode mode) throws IOException {
    return getReader0(in, mode);
  }

  private static Reader getReader0(InputStream in, IOMode mode) throws IOException {
    String encoding = mode.getEncoding();
    return new BufferedReader(new FilteringReader(new InputStreamReader(in, encoding), ConvertMode.getInstance(encoding)));
  }

  /**
   * 引数で受け取ったWriterインスタンスから生成される、BufferedWriterインスタンスを取得します。
   * 生成されるBufferedWriterインスタンスのエンコーディングは、Jis2004に準拠します。
   * 
   * @param writer
   *          BufferedWriterを取得したいWriterインスタンス
   * @return BufferedWriterインスタンス
   * @throws IOException
   */
  public static Writer getWriter(Writer writer) throws IOException {
    return new BufferedWriter(new FilteringWriter(writer));
  }

  /**
   * 引数で受け取ったFileインスタンスから生成される、FileOutputStreamインスタンスを取得します。
   * 
   * @param file
   *          FileOutputStreamを取得したいFileインスタンス
   * @return FileOutputStreamインスタンス
   * @throws IOException
   */
  public static Writer getWriter(File file) throws IOException {
    return getWriter0(new FileOutputStream(file), IOMode.CSV);
  }

  /**
   * 引数で受け取ったFileインスタンスから生成される、FileOutputStreamインスタンスを取得します。
   * 
   * @param file
   *          FileOutputStreamを取得したいFileインスタンス
   * @return FileOutputStreamインスタンス
   * @throws IOException
   */
  public static Writer getWriter(File file, IOMode mode) throws IOException {
    return getWriter0(new FileOutputStream(file), mode);
  }

  /**
   * 引数で受け取ったOutputStreamインスタンスから生成される、BufferedWriterインスタンスを取得します。<br>
   * 生成されるOutputStreamインスタンスのエンコーディングは、Jis2004に準拠します。
   * 
   * @param out
   *          BufferedWriter
   * @return BufferedWriterインスタンス
   * @throws IOException
   */
  public static Writer getWriter(OutputStream out) throws IOException {
    return getWriter0(out, IOMode.CSV);
  }

  /**
   * 引数で受け取ったOutputStreamインスタンスから生成される、BufferedWriterインスタンスを取得します。<br>
   * 生成されるOutputStreamインスタンスのエンコーディングは、Jis2004に準拠します。
   * 
   * @param out
   *          BufferedWriter
   * @return BufferedWriterインスタンス
   * @throws IOException
   */
  public static Writer getWriter(OutputStream out, IOMode mode) throws IOException {
    return getWriter0(out, mode);
  }

  private static Writer getWriter0(OutputStream out, IOMode mode) throws IOException {
    String encoding = mode.getEncoding();
    return new BufferedWriter(new FilteringWriter(new OutputStreamWriter(out, encoding), ConvertMode.getInstance(encoding)));
  }

  /**
   * リソースバンドルが利用可能かどうかを調べます。
   * 
   * @param resourceBaseName
   *          リソースバンドルのベース名
   * @return クラスパス内にリソースバンドルが存在すればtrue
   */
  public static boolean existsResource(String resourceBaseName) {
    boolean result = false;
    try {
      ResourceBundle rb = ResourceBundle.getBundle(resourceBaseName);
      result = rb != null;
    } catch (MissingResourceException mrEx) {
      result = false;
    } catch (RuntimeException rEx) {
      result = false;
    }
    return result;
  }
}
