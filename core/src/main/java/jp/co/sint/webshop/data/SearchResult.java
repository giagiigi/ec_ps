package jp.co.sint.webshop.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 検索結果を表すオブジェクトです。
 * 
 * @author System Integrator Corp.
 * @param <T>検索結果1行分のレコードを表すオブジェクトの型情報
 */
public class SearchResult<T> implements Serializable {

  /** uid */
  private static final long serialVersionUID = 1L;

  /** 結果取得した表示ページ番号 */
  private int currentPage;

  /** １ページあたりの表示サイズ */
  private int pageSize;

  /** 検索結果のデータ件数 */
  private int rowCount;

  /** 検索結果の行データ */
  private List<T> rows;

  /** 最大フェッチ件数を超えたかどうか */
  private boolean overflow;

  /** 最大フェッチ件数 */
  private int maxFetchSize;

  public SearchResult() {
    rows = new ArrayList<T>();
  }

  /**
   * コンストラクタ
   * 
   * @param rowCount
   *          最大データサイズ
   * @param rows
   *          検索結果
   * @param overflow
   *          オーバーフローしたかどうか true-オーバーフロー false-正常
   */
  public SearchResult(int rowCount, List<T> rows, boolean overflow) {
    this();
    setRowCount(rowCount);
    setRows(rows);
    setOverflow(overflow);
  }

  /**
   * 検索結果が最大フェッチ件数を超過しているかどうかを返します。
   * 
   * @return 最大フェッチ件数を超過していればtrue
   */
  public boolean isOverflow() {
    return overflow;
  }

  /**
   * 検索結果がゼロ件(空)かどうかを返します。
   * 
   * @return 検索結果がゼロ件(空)ならtrue
   */
  public boolean isEmpty() {
    return this.getRows() == null || this.getRows().isEmpty();
  }

  /**
   * 検索結果が最大フェッチ件数を超過しているかどうかを設定します。
   * 
   * @param overflow
   *          設定する overflow
   */
  public void setOverflow(boolean overflow) {
    this.overflow = overflow;
  }

  /**
   * 検索結果のリストを返します。
   * 
   * @return rows 検索結果のリスト
   */
  public List<T> getRows() {
    return rows;
  }

  /**
   * 検索結果のリストを設定します。
   * 
   * @param rows
   *          検索結果のリスト
   */
  public void setRows(List<T> rows) {
    this.rows = rows;
  }

  /**
   * 取得した検索結果の、現在のページ番号を取得します。
   * 
   * @return 取得した検索結果の、現在のページ番号
   */
  public int getCurrentPage() {
    return currentPage;
  }

  /**
   * 取得した検索結果の、現在のページ番号を設定します。
   * 
   * @param currentPage
   *          取得した検索結果の、現在のページ番号
   */
  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  /**
   * 取得した検索結果のページサイズを取得します。
   * 
   * @return 取得した検索結果のページサイズ
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * 取得した検索結果のページサイズを設定します。
   * 
   * @param pageSize
   *          取得した検索結果のページサイズ
   */
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * 検索結果の件数をを取得します。
   * 検索結果リスト(getRows().size())の件数は最大フェッチ件数を上限とするため、このメソッドが返す値とは一致しないことがあります。
   * 
   * @return rowCount
   */
  public int getRowCount() {
    return rowCount;
  }

  /**
   * 検索結果の件数をを設定します。
   * 
   * @param rowCount
   *          検索結果の件数
   */
  public void setRowCount(int rowCount) {
    this.rowCount = rowCount;
  }

  /**
   * 最大フェッチ件数を返します。
   * 
   * @return 最大フェッチ件数
   */
  public int getMaxFetchSize() {
    return maxFetchSize;
  }

  /**
   * 最大フェッチ件数を設定します。
   * 
   * @param maxFetchSize
   *          最大フェッチ件数
   */
  public void setMaxFetchSize(int maxFetchSize) {
    this.maxFetchSize = maxFetchSize;
  }

}
