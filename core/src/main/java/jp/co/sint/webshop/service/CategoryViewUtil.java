package jp.co.sint.webshop.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.TmallProperty;
import jp.co.sint.webshop.data.dto.TmallPropertyValue;

/**
 * 生成页面属性HTML
 * 
 * @author kousen
 */
public class CategoryViewUtil {

  /**
   * 用于定义商品最大属性数目
   */
  public static final int MAXPROPERTYSIZE = 8;

  /**
   * 用于定义表格头的样式
   */
  public static final String TD_HEADER_CSS_STRING = "tbl_label";
  
  /**
   * 用于定义表格内容的样式
   */
  public static final String TD_CONTEXT_CSS_STRING = "list_row1";
  /**
   * 属性Id 页面元素前缀
   */
  public static final String PROPERTY_ID_PROFIX = "p";

  /**
   * 属性名称 页面元素前缀
   */
  public static final String PROPERTY_NAME_PROFIX = "pn";

  /**
   * 属性值Id 页面元素前缀
   */
  public static final String VALUE_ID_PROFIX = "v";

  /**
   * 属性值名称 页面元素前缀
   */
  public static final String VALUE_NAME_PROFIX = "vn";

  /**
   * 手动输入多选框 页面元素前缀
   */
  public static final String MANUAL_CHECKBAX_PROFIX = "manual_checkbox";

  /**
   * 手动输入文本框 页面元素前缀
   */
  public static final String MANUAL_TEXT_PROFIX = "manual_text";

  /**
   * 手动输入的属性值的固定ID
   */
  public static final String MANUAL_ID = "INPUT";

  private static String buildTableHeader() {
    return "<table   cellpadding='3' cellspacing='1' width='100%' class='list' summary='editTable'>";
  }

  public String buildHtml(List<TmallProperty> proList) {
    
    StringBuffer sb = new StringBuffer("");
    sb.append(buildTableHeader());
    if (proList == null || proList.size() == 0) {
      return sb.append("<td class='"+TD_CONTEXT_CSS_STRING+"' colspan='4'><font color='red'>该类目数据没录入！</font></td></tr></table>").toString();
    }
    for (int i = 0; i < proList.size(); i++) {
      TmallProperty property = proList.get(i);
      sb.append("<tr>");
      sb.append(buildPropertyHtml(property, i));
      if (i++ < (proList.size() - 1)) {
        sb.append(buildPropertyHtml(proList.get(i), i));
      } else {
        sb.append(emptyTd());
        sb.append(emptyTd());
      }
      sb.append("</tr>");
    }
    sb.append("</table>");
    return sb.toString();
  }

  int index = 0;

  public String buildPropertyHtml(TmallProperty property, int i) {
    StringBuffer sb = new StringBuffer("");

    // td开始
    sb.append(buildTdHeader(TD_HEADER_CSS_STRING));
    // 添加页面显示的属性名称
    sb.append(property.getPropertyName());
    // 添加属性ID的隐藏表单域
    sb.append(buildInputHtml("hidden", PROPERTY_ID_PROFIX + index, property.getPropertyId(), null));
    // 添加属性名称表单域
    sb.append(buildInputHtml("hidden", PROPERTY_NAME_PROFIX + index, property.getPropertyName(), null));
    // td结束
    sb.append("</td>");
    if (property.getPropertyValueContain() == null || property.getPropertyValueContain().size() <= 0) {
      sb.append("<td class='"+TD_CONTEXT_CSS_STRING+"'><font color='red'>该属性数据没录入！</font></td>");
      return sb.toString();
    }
    // 第二列td开始
    sb.append(buildTdHeader());
    // 生成下拉列表的HTML
    if (property.isSelect() || property.getIsSelectCanManual()) {
      if (property.getIsSelectCanManual()) {
        // 添加手动输入的文本框
        sb.append(buildInputHtml("text", MANUAL_TEXT_PROFIX + index, null, null, null, "disable"));
        // 添加确认是否手动输入的多选框
        sb.append(buildInputHtml("checkbox", MANUAL_CHECKBAX_PROFIX + index, "1", null, "changeElementDisable('"
            + MANUAL_TEXT_PROFIX + index + "',this,'" + (VALUE_ID_PROFIX + index) + "')", null));
        sb.append("选中手动输入<br>");
      }

      if (property.getPropertyValueContain().size() != 0) {
        sb.append("<select name='" + VALUE_ID_PROFIX + index + "'>");
        for (TmallPropertyValue value : property.getPropertyValueContain()) {
          if (value.getIsSelect()) {
            sb.append("<option selected='selected' value='").append(value.getValueId()).append("'>").append(value.getValueName())
                .append("</option>");
          } else {
            sb.append("<option value='").append(value.getValueId()).append("'>").append(value.getValueName()).append("</option>");
          }
        }
        sb.append("</select>");
      } else {
        emptyTd();
      }
    } else if (property.isCheckbox() || property.getIsCheckedCanManual()) {// 生成checkboxHTML
      if (property.getIsCheckedCanManual()) {
        // 添加手动输入的文本框
        sb.append(buildInputHtml("text", MANUAL_TEXT_PROFIX + index, null, null));
        // 添加确认是否手动输入的多选框
        sb.append(buildInputHtml("checkbox", MANUAL_CHECKBAX_PROFIX + index, "1", null, "changeElementDisable('"
            + MANUAL_TEXT_PROFIX + index + "',this,'" + (VALUE_ID_PROFIX + index) + "')", null));
        sb.append("选中手动输入<br>");
      }
      if (property.getPropertyValueContain().size() != 0) {
        for (int j = 0; j < property.getPropertyValueContain().size(); j++) {
          TmallPropertyValue value = property.getPropertyValueContain().get(j);
          if (value.getIsSelect()) {
            sb.append(buildInputHtml("checkbox", VALUE_ID_PROFIX + index, value.getValueId(), "checked"));
            sb.append(complementBit(value.getValueName(), 3));
          } else {
            sb.append(buildInputHtml("checkbox", VALUE_ID_PROFIX + index, value.getValueId(), null));
            sb.append(complementBit(value.getValueName(), 3));
          }
          if (j > 0 && (j + 1) % 3 == 0 && j < property.getPropertyValueContain().size()) {
            sb.append("<br>");
          }
        }
      } else {
        emptyTd();
      }
    } else {
      for (TmallPropertyValue value : property.getPropertyValueContain()) {
        sb.append(value.getValueId()).append(value.getValueName()).append("&nbsp;");
      }
    }
    // 第二列td结束
    sb.append("</td>");
    index++;
    return sb.toString();
  }

  public String complementBit(String value, int bitNum) {
    StringBuffer sb = new StringBuffer(value);
    if (value.length() < bitNum) {
      for (int i = 0; i < 5; i++) {
        sb.append("&nbsp;");
      }
    } else {
      for (int i = 0; i < 2; i++) {
        sb.append("&nbsp;");
      }
    }
    return sb.toString();
  }

  /**
   * 产品属性与属性值的实体集合（propertyId valueId）
   * 
   * @author kousen
   */
  public class PropertyKeys {

    private List<PropertyKey> result = new ArrayList<PropertyKey>();

    public PropertyKeys() {
    }

    public void addKey(PropertyKey key) {
      this.result.add(key);
    }

    /**
     * 清除对象中为空的元素
     */
    public void clearNullElement() {
      List<PropertyKey> temp = new ArrayList<PropertyKey>();
      for (int i = 0; i < this.size(); i++) {
        PropertyKey key = this.result.get(i);
        if (key != null && !"".equals(key.pId) && !"".equals(key.pName) && key.getVids().size() != 0
            && !(key.getVids().size() == 1 && "".equals(key.getVids().get(0)))) {
          temp.add(key);
        }
      }
      this.result = temp;
    }

    public String getPropertyId(int index) {
      if (index > this.size()) {
        return null;
      }
      return result.get(index).getPId();
    }

    public PropertyKey get(int index) {
      return result.get(index);
    }

    public List<String> getValueList(int index) {
      if (index > this.size()) {
        return null;
      }
      return result.get(index).getVids();
    }

    public void copyKeyList(List<PropertyKey> list) {
      for (int i = 0; i < list.size(); i++) {
        result.add(list.get(i));
      }
    }

    public void addKey(String pid, String pName, List<String> vid, List<String> vNames) {
      this.result.add(new PropertyKey(pid, pName, vid, vNames));
    }

    public List<PropertyKey> getProKeyList() {
      return result;
    }

    public int size() {
      return this.result.size();
    }
  }

  /**
   * 属性与属性值
   * 
   * @author kousen
   */
  public class PropertyKey {

    private String pId = "";

    private List<String> vids;

    private String pName = "";

    private List<String> vNames;

    public PropertyKey() {
    }

    /**
     * 构造器
     * 
     * @param pid
     *          属性ID
     * @param pName
     *          属性名
     * @param vid
     *          属性包含的属性值ID集合
     * @param vNames
     *          属性包含的属性值名称集合
     */
    public PropertyKey(String pid, String pName, List<String> vid, List<String> vNames) {
      this.build(pid, pName, vid, vNames);
    }

    /**
     * @return the pId
     */
    public String getPId() {
      return pId;
    }

    public void build(String pid, String pName, List<String> vid, List<String> vNames) {
      this.setPId(pid);
      this.setVids(vid);
      this.setPName(pName);
      this.setVNames(vNames);
    }

    /**
     * @return the pName
     */
    public String getPName() {
      return pName;
    }

    /**
     * @param name
     *          the pName to set
     */
    public void setPName(String name) {
      pName = name;
    }

    /**
     * @param id
     *          the pId to set
     */
    public void setPId(String id) {
      pId = id;
    }

    /**
     * @return the vids
     */
    public List<String> getVids() {
      return vids;
    }

    /**
     * @return the vNames
     */
    public List<String> getVNames() {
      return vNames;
    }

    /**
     * @param names
     *          the vNames to set
     */
    public void setVNames(List<String> names) {
      vNames = names;
    }

    /**
     * @param vids
     *          the vids to set
     */
    public void setVids(List<String> vids) {
      this.vids = vids;
    }
  }

  public String buildConfirmHTML(PropertyKeys keys) {
    StringBuffer sb = new StringBuffer("");
    sb.append(buildTableHeader());
    for (int i = 0; i < keys.size(); i++) {
      sb.append("<tr>");
      sb.append(buildTdHtml(keys.get(i)));
      if (i++ < (keys.size() - 1)) {
        sb.append(buildTdHtml(keys.get(i)));
      } else if (keys.size() % 4 > 0) {
        sb.append(emptyTd()).append(emptyTd());
      }
    }
    sb.append("</tr>");
    sb.append("</table>");
    return sb.toString();
  }

  private static String emptyTd() {
    return emptyTd(TD_CONTEXT_CSS_STRING);
  }

  private static String emptyTd(String classType) {
    return buildTdHeader(classType) + "&nbsp;</td>";
  }

  private static String buildTdHeader() {
    return buildTdHeader(TD_CONTEXT_CSS_STRING);
  }

  private static String buildTdHeader(String classType) {
    return "<td width='25%' class='" + classType + "'>";
  }

  int index2 = 0;

  /**
   * 根据传入的属性与属性值集合对象生成页面td元素
   * 
   * @param key
   * @return
   */
  private String buildTdHtml(PropertyKey key) {
    if (key.getVids().size() == 0)
      return "";
    StringBuilder sb = new StringBuilder(buildTdHeader() + key.getPName() + "</td>");
    sb.append(buildInputHtml("hidden", "p" + index2, key.getPId(), null));
    sb.append(buildTdHeader());
    for (int i = 0; i < key.getVids().size(); i++) {
      String vId = key.getVids().get(i);
      String vName = key.getVNames().get(i);
      sb.append(vName);
      sb.append(buildInputHtml("hidden", "v" + index2, vId, null));
      if (i < key.getVids().size() - 1) {
        sb.append(",");
      }
    }
    sb.append("</td>");
    return sb.toString();
  }


  /**
   * 组装页面INPUT元素
   * 
   * @param type
   *          类型
   * @param name
   *          名称
   * @param value
   *          值
   * @param selected
   *          是否被选中
   * @param event
   *          单击事件的 function
   * @param param
   *          参数
   * @return
   */
  private static String buildInputHtml(String type, String name, String value, String selected, String event, String disable) {
    StringBuilder sb = new StringBuilder();
    sb.append("<input ");
    String inputType = !isNull(type) ? type : "text";
    sb.append(" type='" + inputType + "' ");
    if (!isNull(name)) {
      sb.append(" name='" + name + "' ");
    }
    if (!isNull(value)) {
      sb.append(" value='" + value + "'");
    }
    if (!isNull(selected)) {
      sb.append(" " + selected + "='" + selected + "' ");
    }
    if (!isNull(event)) {
      sb.append(" onclick=" + event);
    }
    if (!isNull(disable)) {
      sb.append(" disabled=true ");
    }
    sb.append(" />");
    return sb.toString();
  }

  private static String buildInputHtml(String type, String name, String value, String selected) {
    StringBuilder sb = new StringBuilder();
    sb.append("<input ");
    String inputType = !isNull(type) ? type : "text";
    sb.append(" type='" + inputType + "' ");
    if (!isNull(name)) {
      sb.append(" name='" + name + "' ");
    }
    if (!isNull(value)) {
      sb.append(" value='" + value + "'");
    }
    if (!isNull(selected)) {
      sb.append(" " + selected + "='" + selected + "' ");
    }
    sb.append(" />");
    return sb.toString();
  }

  private static boolean isNull(String value) {
    return value == null || "".equals(value);
  }
}
