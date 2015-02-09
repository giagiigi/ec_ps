package jp.co.sint.webshop.data;

/**
 * ページング指定されたな検索クエリを表すオブジェクトです。
 * 
 * @author System Integrator Corp.
 * @param <T>検索結果1行分のレコードを表すオブジェクトの型情報
 */
public interface SearchQuery<T> extends Query {

  /**
   * 検索結果1行分のレコードを表すオブジェクトの型情報を返します。
   * 
   * @return 検索結果1行分のレコードを表すオブジェクトの型情報
   */
  Class<T> getRowType();

  /**
   * ページサイズを取得します。
   * 
   * @return ページサイズ
   */
  int getPageSize();

  /**
   * ページ番号を取得します。
   * 
   * @return ページ番号
   */
  int getPageNumber();

  /**
   * このクエリが取得可能な最大フェッチ件数を取得します。
   * 
   * @return 最大フェッチ件数
   */
  int getMaxFetchSize();

}
