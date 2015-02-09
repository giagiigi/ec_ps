package jp.co.sint.webshop.utility;

import java.io.Serializable;

import jp.co.sint.webshop.validation.ValidatorUtil;

/**
 * 期間・範囲を操作するための汎用クラスです。
 * 
 * @author System Integrator Corp.
 * @param <T>
 *          期間・範囲のデータ型
 */
public class Range<T extends Serializable & Comparable<T>> implements Serializable {

  private static final long serialVersionUID = 1L;

  private T start;

  private T end;

  /**
   * 新しいRangeクラスのインスタンスを生成します。
   */
  public Range() {
  }

  /**
   * 開始/終了の値を指定して、新しいRangeクラスのインスタンスを生成します。
   * 
   * @param start
   *          開始
   * @param end
   *          終了
   */
  public Range(T start, T end) {
    setStart(start);
    setEnd(end);
  }

  /**
   * データが有効かどうかを返します。
   * 
   * @return start &lt; endの場合、trueを返します。<br>
   *         start &lt; endに以外の場合、falseを返します。
   */
  public boolean isCorrect() {
    return start.compareTo(end) <= 0;
  }

  /**
   * 指定された値がこのRangeインスタンスの示す期間に含まれているかどうかを返します。 値チェックでは期間の両端を含みます。
   * 
   * @param target
   *          検査対象値
   * @return 指定された値がこのRangeインスタンスの示す期間に含まれている場合、trueを返します。<br>
   *         指定された値がこのRangeインスタンスの示す期間に含まれていない場合、falseを返します。
   */
  public boolean includes(T target) {
    return ValidatorUtil.inRange(target, start, end);
  }

  /**
   * 指定された値が開始点より前にあるかどうかを返します。
   * 
   * @param target
   *          検査対象値
   * @return 指定された値が開始点より前にある場合、trueを返します。<br>
   *         指定された値が開始点より前にない場合、falseを返します。
   */
  public boolean beforeStart(T target) {
    return ValidatorUtil.lessThan(getStart(), target);
  }

  /**
   * 指定された値が終了点より後にあるかどうかを返します。
   * 
   * @param target
   *          検査対象値
   * @return 指定された値が終了点より後にある場合、trueを返します。<br>
   *         指定された値が終了点より後にない場合、falseを返します。
   */
  public boolean afterEnd(T target) {
    return ValidatorUtil.moreThan(getEnd(), target);
  }

  /**
   * このRangeインスタンスと、ほかのRangeインスタンスの示す範囲が重複しているかどうかを返します。
   * 
   * @param another
   * @return 範囲が重複している場合、trueを返します。<br>
   *         範囲が重複していない場合、falseを返します。
   */
  public boolean intersect(Range<T> another) {
    return start.compareTo(another.getEnd()) > 0 || end.compareTo(another.getStart()) < 0;
  }

  /**
   * 開始点の値を取得します。
   * 
   * @return 開始点を返します。
   */
  public T getStart() {
    return start;
  }

  /**
   * 開始点の値を設定します。
   * 
   * @param start
   *          開始点
   */
  public void setStart(T start) {
    this.start = start;
  }

  /**
   * 終了点の値を取得します。
   * 
   * @return 終了点を返します。
   */
  public T getEnd() {
    return end;
  }

  /**
   * 終了点の値を設定します。
   * 
   * @param end
   *          終了点
   */
  public void setEnd(T end) {
    this.end = end;
  }

}
