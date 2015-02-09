package jp.co.sint.webshop.service;

import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import jp.co.sint.webshop.data.CsvResult;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.data.Contents;
import jp.co.sint.webshop.service.data.ContentsInfo;
import jp.co.sint.webshop.service.data.ContentsListResult;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvImportCondition;
import jp.co.sint.webshop.service.data.ImageInfo;
import jp.co.sint.webshop.service.fastpay.FastpayAlipayResultBean;

/**
 * SI Web Shopping 10 データ入出力サービス(DataIOService)仕様
 * 
 * @author System Integrator Corp.
 */
public interface DataIOService {

  /**
   * 画像ファイルを追加します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>画像ファイルをコンテンツフォルダにアップロードします。
   * <ol>
   * <li>コンテンツの配置パスを取得します。フォルダが存在しない場合は作成します。</li>
   * <li>ファイル名が命名規約に違反していないか正規表現でのチェックを行います。<br>
   * 違反する場合は、ファイル名不正エラーを返します。</li>
   * <li>拡張子のチェックを行います。拡張子が「jpg, zip」以外の場合は、拡張子不正エラーを返します。</li>
   * <li>3.で拡張子が「jpg」の場合、コンテンツの配置パスに画像ファイルをアップロードします。<br>
   * コンテンツ条件からファイル名が取得できる場合は、そのファイル名に置換してアップロードします。</li>
   * <li>3.で拡張子が「zip」の場合、ファイルを読み込み用で開きます。<br>
   * 開けない場合は、ZIP解凍エラーを返します。</li>
   * <li>ZIPファイル内のファイルが命名規約に違反していないか正規表現でのチェックを行います。<br>
   * 違反する場合は、ファイル名不正エラーとします。</li>
   * <li>ZIPファイル内のファイルの拡張子が「jpg」かのチェックを行います。<br>
   * 違反する場合は、拡張子不正エラーとします。</li>
   * <li>6と7のチェックでエラーが発生しない場合は、<br>
   * ZIPファイル内の画像ファイルをコンテンツの配置パスにアップロードします。<br>
   * エラーがある場合は、ZIP解凍エラーを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>コンテンツ情報がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>指定のコンテンツ配置パスに、画像ファイルがアップロードされます。</dd>
   * <dd>InputStreamのcloseをメソッドの呼び出し元で行います。</dd>
   * </dl>
   * </p>
   * 
   * @param stream
   *          入力ストリーム
   * @param info
   *          コンテンツ条件
   * @return サービス実行結果
   */
  ServiceResult addImage(InputStream stream, ImageInfo info);

  /**
   * テキストファイルを追加します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>テキストファイルをコンテンツフォルダにアップロードします。
   * <ol>
   * <li>コンテンツの配置パスを取得します。フォルダが存在しない場合は作成します。</li>
   * <li>拡張子のチェックを行います。拡張子が「txt」以外の場合は、拡張子不正エラーを返します。</li>
   * <li>ファイル名が命名規約に違反していないか正規表現でのチェックを行います。<br>
   * 違反する場合は、ファイル名不正エラーを返します。</li>
   * <li>コンテンツの配置パスにテキストファイルをアップロードします。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>コンテンツ情報がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>指定のコンテンツ配置パスに、テキストファイルがアップロードされます。</dd>
   * <dd>InputStreamのcloseをメソッドの呼び出し元で行います。</dd>
   * </dl>
   * </p>
   * 
   * @param stream
   *          入力ストリーム
   * @param info
   *          コンテンツ条件
   * @return サービス実行結果
   */
  ServiceResult addText(InputStream stream, ContentsInfo info);

  /**
   * コンテンツを追加します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>コンテンツ(ZIP圧縮)を解凍してコンテンツフォルダにアップロードします。
   * <ol>
   * <li>コンテンツの配置パスを取得します。フォルダが存在しない場合は作成します。</li>
   * <li>拡張子のチェックを行います。拡張子が「zip」以外の場合は、拡張子不正エラーを返します。</li>
   * <li>ZIPファイル内のファイルが命名規約に違反していないか正規表現でのチェックを行います。<br>
   * 違反する場合は、ファイル名不正エラーとします。</li>
   * <li>ZIPファイル内のファイルの拡張子が違反していないか正規表現でのチェックを行います。<br>
   * 違反する場合は、拡張子不正エラーとします。</li>
   * <li>3と4のチェックでエラーが発生しない場合は、<br>
   * ZIPファイル内のファイルをコンテンツの配置パスにアップロードします。<br>
   * エラーがある場合は、ZIP解凍エラーを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>コンテンツ情報がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>指定のコンテンツ配置パスに、コンテンツが展開されます。</dd>
   * </dl>
   * </p>
   * 
   * @param info
   *          コンテンツ条件
   * @return サービス実行結果
   */
  ServiceResult addContents(ContentsInfo info);

  /**
   * コンテンツを削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>該当のコンテンツを削除します。
   * <ol>
   * <li>コンテンツの配置パスを取得します。<br>
   * 削除対象コンテンツが存在しない場合は、コンテンツ削除エラーを返します。</li>
   * <li>コンテンツの削除を行います。エラーが発生した場合は、コンテンツ削除エラーを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>コンテンツ情報がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>該当のコンテンツが削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param info
   *          コンテンツ条件
   * @return サービス実行結果
   */
  ServiceResult deleteContents(ContentsInfo info);

  /**
   * 画像ファイルを削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>コンテンツ情報に指定した画像ファイルを削除します。
   * <ol>
   * <li>画像ファイルが存在するかチェックします。</li>
   * <li>画像ファイルがフォルダでないかチェックします。</li>
   * <li>1と2で削除対象ファイルが画像ファイルの場合に削除処理を行います。<br>
   * 削除時にエラーが発生した場合は、コンテンツ削除エラーを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>コンテンツ情報がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>該当の画像ファイルが削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param contents
   *          コンテンツ条件
   * @return サービス実行結果
   */
  ServiceResult deleteImage(Contents contents);

  /**
   * 検索条件に指定したフォルダ内のコンテンツのリストを全件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>コンテンツ取得条件に指定されたコンテンツの配置パスを取得します。</li>
   * <li>1で取得したコンテンツ配置パス内のファイルを取得します。</li>
   * <li>フォルダの場合は、フォルダ内のファイルを取得します。</li>
   * <li>ファイルのサイズをKB単位で取得します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>コンテンツ取得条件がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          コンテンツ取得条件。ページング情報は無視されます。
   * @return コンテンツのリスト
   */
  List<ContentsListResult> getContentsList(ContentsSearchCondition condition);

  /**
   * ページを指定して、検索条件に指定したフォルダ内のコンテンツのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>コンテンツ取得条件に指定されたコンテンツの配置パスを取得します。</li>
   * <li>1で取得したコンテンツ配置パス内のファイルを取得します。</li>
   * <li>フォルダの場合は、フォルダ内のファイルを取得します。</li>
   * <li>ファイルのサイズをKB単位で取得します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>コンテンツ取得条件がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          コンテンツ取得条件
   * @return コンテンツのリスト
   */
  SearchResult<ContentsListResult> getPartialContentsList(ContentsSearchCondition condition);

  /**
   * 検索条件に指定したコンテンツのURLを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>検索条件に指定したコンテンツのURLを取得します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>コンテンツ取得条件がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          コンテンツ条件
   * @return コンテンツのURL
   */
  String getContentsUrl(ContentsSearchCondition condition);

  /**
   * 検索条件に指定したコンテンツの存在チェックを行います。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>指定された条件でコンテンツを検索してファイルの存在を判定します。</li>
   * <li>ファイルが存在すれば、「true」を返します。</li>
   * <li>存在しない場合は、「false」を返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>コンテンツ取得条件がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          コンテンツ取得条件
   * @return コンテンツが存在する場合は「true」、存在しない場合は「false」が返ります。
   */
  boolean contentsExists(ContentsSearchCondition condition);

  /**
   * テキストファイルを読込んで文字列として返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>指定されたパスのファイルを開きます。</li>
   * <li>ファイル内のテキストを読み込んで、文字列を作成して返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ファイルパスがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param path
   *          ファイルパス
   * @return テキストファイル内の文字列
   */
  String getContentsData(String path);

  /**
   * 検索条件に指定したコンテンツの配置パスを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>検索条件に指定したコンテンツの配置パスを取得します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>コンテンツ条件がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param info
   *          コンテンツ条件
   * @return コンテンツ配置パス
   */
  String getContentsDirectoryPath(ContentsInfo info);

  /**
   * 検索条件で指定された商品のサムネイル画像の存在チェックを行います。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>指定された条件でサムネイル画像を検索してファイルの存在を判定します。</li>
   * <li>ファイルが存在すれば、「true」を返します。</li>
   * <li>存在しない場合は、「false」を返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコードがnullでないこと。</dd>
   * <dd>商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param isPc
   *          チェック対象。true:PC画像 false:携帯画像
   * @return 画像が登録されている場合は「true」、存在しない場合は「false」が返ります。
   */
  boolean thumbnailImageExists(String shopCode, String commodityCode, boolean isPc);

  /**
   * 検索条件で指定された商品の商品画像の存在チェックを行います。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>指定された条件で商品画像を検索してファイルの存在を判定します。</li>
   * <li>ファイルが存在すれば、「true」を返します。</li>
   * <li>存在しない場合は、「false」を返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコードがnullでないこと。</dd>
   * <dd>SKUコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param isPc
   *          チェック対象。true:PC画像 false:携帯画像
   * @return 画像が登録されている場合は「true」、存在しない場合は「false」が返ります。
   */
  boolean commodityImageExists(String shopCode, String commodityCode, boolean isPc);

  /**
   * 検索条件に指定したSKU画像の存在チェックを行います。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>指定された条件でSKU画像を検索してファイルの存在を判定します。</li>
   * <li>ファイルが存在すれば、「true」を返します。</li>
   * <li>存在しない場合は、「false」を返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコードがnullでないこと。</dd>
   * <dd>SKUコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param isPc
   *          チェック対象。true:PC画像 false:携帯画像
   * @return 画像が登録されている場合は「true」、存在しない場合は「false」が返ります。
   */
  boolean skuImageExists(String shopCode, String skuCode, boolean isPc);
  List<String> readDetailImages(String shopCode, String skuCode, boolean isPc);

  /**
   * 検索条件に指定したコンテンツのフォルダ内からファイルをランダムで取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>検索条件に指定したコンテンツの配置パスを取得します。</li>
   * <li>コンテンツのフォルダ内からランダムでファイルを取得します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>コンテンツ条件がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param info
   *          コンテンツ条件
   * @return コンテンツ取得結果
   */
  ContentsListResult getRandomContents(ContentsSearchCondition info);

  <S extends CsvSchema, C extends CsvExportCondition<S>>ServiceResult exportCsv(S schema, Writer writer, C condition);

  <S extends CsvSchema, C extends CsvImportCondition<S>>CsvResult importCsv(S schema, Reader reader, C condition);
  // 2012-01-09 yyq add start desc:接口使用的csv导入共通
  <S extends CsvSchema, C extends CsvImportCondition<S>>CsvResult importCsvIf(S schema, Reader reader, C condition);
  // 2012-02-29 OS011 add start desc:接口使用的csv导入共通SKU重复
  <S extends CsvSchema, C extends CsvImportCondition<S>>CsvResult importCsvIfSkuExist(S schema, Reader reader, C condition);

  /**
   * 画像ファイルを削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>コンテンツ情報に指定した画像ファイルを削除します。
   * <ol>
   * <li>画像ファイルが存在するかチェックします。</li>
   * <li>画像ファイルがフォルダでないかチェックします。</li>
   * <li>1と2で削除対象ファイルが画像ファイルの場合に削除処理を行います。<br>
   * 削除時にエラーが発生した場合は、コンテンツ削除エラーを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>コンテンツ情報がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>該当の画像ファイルが削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param info
   *          コンテンツ条件
   * @return サービス実行結果
   */
  ServiceResult deleteImage(ImageInfo info);
  //20111223 os013 add start 
  /**
   * 支付宝快捷登录
   */
  FastpayAlipayResultBean getFastpayAlipayBean ();
  //20111223 os013 add end
}
