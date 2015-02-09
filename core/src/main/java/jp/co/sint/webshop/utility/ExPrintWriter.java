package jp.co.sint.webshop.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * 改行コードを指定可能な、拡張PrintWriterクラスです。デフォルトの改行コードはCRLF(0x0d,0x0a)です。
 * 
 * @author System Integrator Corp.
 */
public class ExPrintWriter extends PrintWriter {

  private String lineFeed = "\r\n";

  /**
   * 行の自動フラッシュは行わずに、指定のファイルで 新しい PrintWriter を作成します。このコンストラクタでは、必要な中間の
   * OutputStreamWriter を作成します。これにより、この Java 仮想マシンのこのインスタンスの default charset
   * を使って文字をエンコードします。
   * 
   * @param file
   *          このライターの宛先として使用するファイル。ファイルが存在する場合、サイズ 0
   *          に切り捨てられる。ファイルがなければ新しいファイルが作成される。出力はファイルに書き込まれ、バッファに格納される
   * @throws FileNotFoundException
   *           指定のファイルオブジェクトが、既存の書き込み可能な普通のファイルを示さず、その名前を持つ普通のファイルを作成できない場合。またはファイルを開いているときや作成しているときに、その他のエラーが発生した場合
   */
  public ExPrintWriter(File file) throws FileNotFoundException {
    super(file);
  }

  /**
   * 新しい PrintWriter を作成します。
   * 
   * @param out
   *          文字出力ストリーム
   */
  public ExPrintWriter(Writer out) {
    super(out);
  }

  /**
   * 行の自動フラッシュは行わずに、既存の OutputStream から新しい PrintWriter を作成します。このコンストラクタでは、必要な中間の
   * OutputStreamWriter を作成します。これにより、デフォルトの文字エンコーディングを使って文字をバイトに変換します。
   * 
   * @param out
   *          文字出力ストリーム
   */
  public ExPrintWriter(OutputStream out) {
    super(out);
  }

  /**
   * 行の自動フラッシュは行わずに、指定の名前で 新しい PrintWriter を作成します。このコンストラクタでは、必要な中間の
   * OutputStreamWriter を作成します。これにより、この Java 仮想マシンのこのインスタンスの default charset
   * を使って文字をエンコードします。
   * 
   * @param fileName
   *          このライターの宛先として使用するファイルの名前。ファイルが存在する場合、サイズ 0
   *          に切り捨てられる。ファイルがなければ新しいファイルが作成される。出力はファイルに書き込まれ、バッファに格納される
   * @throws FileNotFoundException
   *           指定の文字列が、既存の書き込み可能な普通のファイルを示さず、その名前を持つ普通のファイルを作成できない場合。またはファイルを開いているときや作成しているときに、その他のエラーが発生した場合
   */
  public ExPrintWriter(String fileName) throws FileNotFoundException {
    super(fileName);
  }

  /**
   * 行の自動フラッシュは行わずに、指定のファイルと文字セットで 新しい PrintWriter を作成します。このコンストラクタでは、必要な中間の
   * OutputStreamWriter を作成します。これにより、指定の文字セットを使って文字をエンコードします。
   * 
   * @param file
   *          このライターの宛先として使用するファイル。ファイルが存在する場合、サイズ 0
   *          に切り捨てられる。ファイルがなければ新しいファイルが作成される。出力はファイルに書き込まれ、バッファに格納される
   * @param csn
   *          サポートする charset の名前
   * @throws FileNotFoundException
   *           指定のファイルオブジェクトが、既存の書き込み可能な普通のファイルを示さず、その名前を持つ普通のファイルを作成できない場合。またはファイルを開いているときや作成しているときに、その他のエラーが発生した場合
   * @throws UnsupportedEncodingException
   *           指定された文字セットがサポートされていない場合
   */
  public ExPrintWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
    super(file, csn);
  }

  /**
   * 既存の OutputStream から新しい PrintWriter を作成します。この簡易コンストラクタでは、必要な中間の
   * OutputStreamWriter を作成します。これにより、デフォルトの文字エンコーディングを使って文字をバイトに変換します。
   * 
   * @param out
   *          文字出力ストリーム
   * @param autoFlush
   *          boolean 値。値が true の場合、println() メソッドでは出力バッファをフラッシュする
   */
  public ExPrintWriter(OutputStream out, boolean autoFlush) {
    super(out, autoFlush);
  }

  /**
   * 新しい PrintWriter を作成します。
   * 
   * @param out
   *          文字出力ストリーム
   * @param autoFlush
   *          boolean 値。値が true の場合、println() メソッドでは出力バッファをフラッシュする
   */
  public ExPrintWriter(Writer out, boolean autoFlush) {
    super(out, autoFlush);
  }

  /**
   * 改行コードを返します。
   * 
   * @return the 改行コード
   */
  public String getLineFeed() {
    return lineFeed;
  }

  /**
   * 改行コードを設定します。
   * 
   * @param lineFeed
   *          設定する 改行コード
   */
  public void setLineFeed(String lineFeed) {
    this.lineFeed = lineFeed;
  }

  @Override
  public void println() {
    super.print(getLineFeed());
  };

  @Override
  public void println(boolean x) {
    super.print(x);
    super.print(getLineFeed());
  }

  @Override
  public void println(char x) {
    super.print(x);
    super.print(getLineFeed());
  }

  @Override
  public void println(char[] x) {
    super.print(x);
    super.print(getLineFeed());
  }

  @Override
  public void println(double x) {
    super.print(x);
    super.print(getLineFeed());
  }

  @Override
  public void println(float x) {
    super.print(x);
    super.print(getLineFeed());
  }

  @Override
  public void println(int x) {
    super.print(x);
    super.print(getLineFeed());
  }

  @Override
  public void println(long x) {
    super.print(x);
    super.print(getLineFeed());
  }

  @Override
  public void println(Object x) {
    super.print(x);
    super.print(getLineFeed());
  }

  @Override
  public void println(String x) {
    super.print(x);
    super.print(getLineFeed());
  }

}
