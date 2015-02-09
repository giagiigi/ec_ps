package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataContainer<T extends JsonObject> implements Serializable, JsonObject {

  public static final String EMPTY_FORMAT = "{\"dictionary\":[],\"maxfetchsize\":\"0\",\"overflow\":\"false\"}";

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private List<T> elements = new ArrayList<T>();

  private boolean overflow;

  private int maxFetchSize;

  /**
   * maxFetchSizeを返します。
   * 
   * @return the maxFetchSize
   */
  public int getMaxFetchSize() {
    return maxFetchSize;
  }

  /**
   * maxFetchSizeを設定します。
   * 
   * @param maxFetchSize
   *          設定する maxFetchSize
   */
  public void setMaxFetchSize(int maxFetchSize) {
    this.maxFetchSize = maxFetchSize;
  }

  /**
   * elementsを返します。
   * 
   * @return the commodityList
   */
  public List<T> getElements() {
    return elements;
  }

  /**
   * elementsを設定します。
   * 
   * @param elements
   *          設定する elements
   */
  public void setElements(List<T> elements) {
    this.elements = elements;
  }

  /**
   * overflowを返します。
   * 
   * @return the overflow
   */
  public boolean isOverflow() {
    return overflow;
  }

  /**
   * overflowを設定します。
   * 
   * @param overflow
   *          設定する overflow
   */
  public void setOverflow(boolean overflow) {
    this.overflow = overflow;
  }

  public String toJsonString() {
    StringBuilder builder = new StringBuilder();
    try {
      builder.append("{\"dictionary\":[");
      String delimiter = "";
      for (T c : elements) {
        builder.append(delimiter + c.toJsonString());
        delimiter = ",";
      }
      builder.append("],");
      builder.append("\"overflow\":\"");
      builder.append(Boolean.toString(isOverflow()));
      // オーバーフローMaxFetchSize
      if (isOverflow()) {
        builder.append("\",");
        builder.append("\"maxfetchsize\":\"");
        builder.append(getMaxFetchSize());
      }
      builder.append("\"}");
    } catch (RuntimeException e) {
      builder.delete(0, builder.length());
      builder.append(EMPTY_FORMAT);
    }
    return builder.toString();
  }
}
