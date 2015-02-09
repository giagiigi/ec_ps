package jp.co.sint.webshop.ext.tmall.getapi;

import java.io.IOException;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.dto.TmallProperty;
import jp.co.sint.webshop.data.dto.TmallPropertyValue;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.ext.tmall.TmallUtil;
import jp.co.sint.webshop.service.tmall.TmallInsertProAndValueListService;
import jp.co.sint.webshop.service.tmall.TmallPropertyAndValueInfoList;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

public class TmallGetPropertyAndValueList implements TmallInsertProAndValueListService {

  private TmallConnection tconnection = new TmallConnection();

  private PreparedStatement insertTmallPropertyStatement = null;

  private PreparedStatement insertTmallPropertyStatement2 = null;

  private PreparedStatement insertTmallPropertyValueStatement = null;

  private static final String TMALL_PROPERTY_TABLE_NAME = DatabaseUtil.getTableName(TmallProperty.class);

  private static final String TMALL_PROPERTY_VALUE_TABLE_NAME = DatabaseUtil.getTableName(TmallPropertyValue.class);

  /**
   * 获得子类目下的的属性名称及对应属性值集合
   */
  private static String createRequestParam(String cid, int proType) {

    TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();

    // 获取key
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    // 组装协议参数。
    apiparamsMap.put("method", TmallUtil.CATEGORY_PROPRTIES);
    apiparamsMap.put("app_key", tc.getAppKey());
    apiparamsMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    apiparamsMap.put("session", tc.getSessionKey());
    apiparamsMap.put("format", "xml");
    apiparamsMap.put("v", "2.0");
    // 组装应用参数
    apiparamsMap
        .put(
            "fields",
            "child_template,pid,parent_pid,parent_vid,name,must,multi,prop_values,is_key_prop,is_sale_prop,is_color_prop,is_enum_prop,is_input_prop");
    // 传入类目
    apiparamsMap.put("cid", cid);
    if (proType == 0) {
      apiparamsMap.put("is_key_prop", "true");
    } else if (proType == 1) {
      apiparamsMap.put("is_sale_prop", "true");
    } else {
      apiparamsMap.put("is_key_prop", "false");
    }
    // 生成签名
    String sign = TmallUtil.sign(apiparamsMap, tc.getAppSercet());
    // 组装协议参数sign
    apiparamsMap.put("sign", sign);

    StringBuilder param = new StringBuilder();
    for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet().iterator(); it.hasNext();) {
      Map.Entry<String, String> e = it.next();
      param.append("&").append(e.getKey()).append("=").append(e.getValue());
    }
    return param.toString().substring(1);
  }

  @SuppressWarnings("unchecked")
  public TmallPropertyAndValueInfoList getPropertyAndValue() {

    Logger logger = Logger.getLogger(this.getClass());

    TmallPropertyAndValueInfoList tpavil = null;
    List<TmallProperty> propertyList = null;
    List<TmallPropertyValue> valueList = null;

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    List<String> listCategoryCode = getChildRenCategory();

    if (listCategoryCode != null) {

      tpavil = new TmallPropertyAndValueInfoList();
      propertyList = new ArrayList<TmallProperty>();
      valueList = new ArrayList<TmallPropertyValue>();
      // 错误数
      int error = 0;
      for (int category = 0; category < listCategoryCode.size(); category++) {
        for (int proType = 0; proType < 3; proType++) {
          // 获得请求参数
          String paramStr = createRequestParam(listCategoryCode.get(category), proType);
          // 调用api获得结果
          String resultStr = TmallUtil.getResult(tc.getReqUrl(), paramStr);
          // 对返回的xml结果字符串，进行解析，组装成java对象
          StringReader read = new StringReader(resultStr);
          InputSource source = new InputSource(read);
          SAXBuilder builder = new SAXBuilder();

          Document document;
          try {

            document = builder.build(source);
            Element root = document.getRootElement();
            Element elementItemProps = root.getChild("item_props");
            if (elementItemProps != null) {

              List<Element> listElementItemProps = elementItemProps.getChildren();
              Element elementItemProp = null;

              for (int i = 0; i < listElementItemProps.size(); i++) {
                TmallProperty tmallProperty = new TmallProperty();
                elementItemProp = (Element) listElementItemProps.get(i);

                if ((elementItemProp.getChildText("must").equals("true")
                    || elementItemProp.getChildText("is_sale_prop").equals("true") || elementItemProp.getChildText("is_key_prop")
                    .equals("true"))
                    && !elementItemProp.getChildText("pid").equals("20000")) {
                  // 属性ID
                  tmallProperty.setPropertyId(elementItemProp.getChildText("pid"));
                  // 上级属性ID
                  tmallProperty.setParentPid(elementItemProp.getChildText("parent_pid"));
                  // 上级属性值ID
                  tmallProperty.setParentVid(elementItemProp.getChildText("parent_vid"));
                  // 属性名称
                  tmallProperty.setPropertyName(elementItemProp.getChildText("name"));
                  // 所属类目ID
                  tmallProperty.setCategoryId(listCategoryCode.get(category));

                  // 是否是必须属性
                  if (elementItemProp.getChildText("must").equals("true")) {
                    tmallProperty.setIsMust(1L);
                  } else {
                    tmallProperty.setIsMust(0L);
                  }
                  // 是否是关键属性
                  if (elementItemProp.getChildText("is_key_prop").equals("true")) {
                    tmallProperty.setIsKey(1L);
                  } else {
                    tmallProperty.setIsKey(0L);
                  }
                  // 是否是销售属性
                  if (elementItemProp.getChildText("is_sale_prop").equals("true")) {
                    tmallProperty.setIsSale(1L);
                  } else {
                    tmallProperty.setIsSale(0L);
                  }
                  // 是否是枚举属性
                  if (elementItemProp.getChildText("is_enum_prop").equals("true")) {
                    if (elementItemProp.getChildText("is_input_prop").equals("true")) {
                      tmallProperty.setIsEnum(2L);
                    } else {
                      tmallProperty.setIsEnum(1L);
                    }
                  } else {
                    tmallProperty.setIsEnum(0L);
                  }
                  // 是多选属性还是单选属性
                  if (elementItemProp.getChildText("multi").equals("true")) {
                    tmallProperty.setIsMulti(1L);
                  } else {
                    tmallProperty.setIsMulti(0L);
                  }
                  propertyList.add(tmallProperty);
                  // 以上为属性名表集合添加
                  // 以上为属性值表集合添加
                  Element elementPropValues = elementItemProp.getChild("prop_values");
                  if (elementPropValues != null) {
                    List<Element> listElementPropValues = elementPropValues.getChildren();
                    Element elementPropValue = null;
                    for (int j = 0; j < listElementPropValues.size(); j++) {
                      TmallPropertyValue tmallPropertyValue = new TmallPropertyValue();
                      elementPropValue = (Element) listElementPropValues.get(j);
                      tmallPropertyValue.setValueId(elementPropValue.getChildText("vid"));
                      tmallPropertyValue.setValueName(elementPropValue.getChildText("name"));
                      tmallPropertyValue.setPropertyId(elementItemProp.getChildText("pid"));
                      tmallPropertyValue.setAliasName(elementPropValue.getChildText("name"));
                      tmallPropertyValue.setCategoryId(listCategoryCode.get(category));
                      tmallPropertyValue.setDeleteFlag(0L);
                      valueList.add(tmallPropertyValue);
                    }
                  }
                }
              }
            }
          } catch (JDOMException e) {
            error++;
            Logger.getLogger(this.getClass()).error(e);
            e.printStackTrace();
          } catch (IOException e) {
            error++;
            Logger.getLogger(this.getClass()).error(e);
            e.printStackTrace();
          }
        }
      }
      if (error == 0) {
        logger.info("获取淘宝类目关联属性成功");
        tpavil.setTmallProperty(propertyList);
        tpavil.setTmallPropertyValue(valueList);
      } else {
        logger.error("获取淘宝类目关联属性失败");
      }
    }
    return tpavil;
  }

  // 接口实现方法
  public boolean insertPropertyAndValue() {
    boolean flag = false;
    TmallPropertyAndValueInfoList tpavil = getPropertyAndValue();
    if (tpavil != null) {
      tconnection.init();
      initializeResources();

      List<TmallProperty> tmallPropertyList = tpavil.getTmallProperty();
      List<TmallPropertyValue> tmallPropertyValueList = tpavil.getTmallPropertyValue();
      try {
        for (int i = 0; i < tmallPropertyList.size(); i++) {
          TmallProperty tmallProperty = new TmallProperty();
          tmallProperty = (TmallProperty) tmallPropertyList.get(i);
          boolean isexists = existsProperty(tmallProperty.getPropertyId(), tmallProperty.getCategoryId());
          if (!isexists) {
            executeInsertTmallProperty(tmallProperty);
          }
        }
        for (int i = 0; i < tmallPropertyValueList.size(); i++) {
          TmallPropertyValue tmallPropertyValue = new TmallPropertyValue();
          tmallPropertyValue = (TmallPropertyValue) tmallPropertyValueList.get(i);
          boolean isexists = existsValue(tmallPropertyValue.getValueId(), tmallPropertyValue.getPropertyId(), tmallPropertyValue
              .getCategoryId());
          if (!isexists) {
            executeInsertTmallPropertyValue(tmallPropertyValue);
          }
        }
        tconnection.getConnection().commit();
        flag = true;
      } catch (SQLException e) {
        try {
          tconnection.getConnection().rollback();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
        e.printStackTrace();
      }
      tconnection.dispose();
    }
    return flag;
  }

  public void initializeResources() {
    Logger logger = Logger.getLogger(this.getClass());

    logger.debug("INSERT statement: " + getInsertTmallPropertyQuery());
    logger.debug("INSERT statement: " + getInsertTmallPropertyValueQuery());

    try {
      insertTmallPropertyStatement = tconnection.createPreparedStatement(getInsertTmallPropertyQuery());
      insertTmallPropertyValueStatement = tconnection.createPreparedStatement(getInsertTmallPropertyValueQuery());
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  // 获得叶子类目ID集合
  public List<String> getChildRenCategory() {

    List<String> obj = new ArrayList<String>();
    try {
      tconnection.init();
      Query existsValueSql = new SimpleQuery("SELECT CATEGORY_CODE FROM TMALL_CATEGORY WHERE IS_PARENT = ? ", "0");
      obj = tconnection.executeScalarGetList(existsValueSql);
      if (obj == null || obj.size() <= 0) {
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (tconnection != null) {
        tconnection.dispose();
      }
    }
    return obj;
  }

  // 是否存在属性值
  public boolean existsValue(String valueId, String propertyId, String categoryId) {
    boolean flag = false;
    Query existsValueSql = new SimpleQuery("SELECT VALUE_ID FROM " + TMALL_PROPERTY_VALUE_TABLE_NAME
        + " WHERE  VALUE_ID = ? AND PROPERTY_ID = ? AND CATEGORY_ID = ? ", valueId, propertyId, categoryId);
    Object obj = tconnection.executeScalar(existsValueSql);
    if (obj != null) {
      return true;
    }
    return flag;
  }

  // 是否存在属性
  public boolean existsProperty(String propertyId, String categoryId) {
    boolean flag = false;
    Query existsPropertySql = new SimpleQuery("SELECT PROPERTY_ID FROM " + TMALL_PROPERTY_TABLE_NAME
        + " WHERE PROPERTY_ID = ? AND CATEGORY_ID = ? ", propertyId, categoryId);
    Object obj = tconnection.executeScalar(existsPropertySql);
    if (obj != null) {
      return true;
    }
    return flag;
  }

  private String getInsertTmallPropertyQuery() {
    String insertSql = "" + " INSERT INTO " + TMALL_PROPERTY_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'tmall_property_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("PROPERTY_ID");
    columnList.add("CATEGORY_ID");
    columnList.add("PROPERTY_NAME");
    columnList.add("PARENT_PID");
    columnList.add("PARENT_VID");
    columnList.add("IS_MUST");
    columnList.add("IS_SALE");
    columnList.add("IS_ENUM");
    columnList.add("IS_MULTI");
    columnList.add("IS_KEY");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  private String getInsertTmallPropertyValueQuery() {
    String insertSql = "" + " INSERT INTO " + TMALL_PROPERTY_VALUE_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'tmall_property_value_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("VALUE_ID");
    columnList.add("VALUE_NAME");
    columnList.add("PROPERTY_ID");
    columnList.add("CATEGORY_ID");
    columnList.add("ALIAS_NAME");
    columnList.add("DELETE_FLAG");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  public int executeInsertTmallProperty(TmallProperty tp) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    params.add(tp.getPropertyId());
    params.add(tp.getCategoryId());
    params.add(tp.getPropertyName());
    params.add(tp.getParentPid());
    params.add(tp.getParentVid());
    params.add(tp.getIsMust());
    params.add(tp.getIsSale());
    params.add(tp.getIsEnum());
    params.add(tp.getIsMulti());
    params.add(tp.getIsKey());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());

    pstmt = insertTmallPropertyStatement;

    logger.debug("Table:TMALLPROPERTY INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  public int executeInsertTmallPropertyValue(TmallPropertyValue tpv) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    params.add(tpv.getValueId());
    params.add(tpv.getValueName());
    params.add(tpv.getPropertyId());
    params.add(tpv.getCategoryId());
    params.add(tpv.getAliasName());
    params.add(tpv.getDeleteFlag());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());

    pstmt = insertTmallPropertyValueStatement;

    logger.debug("Table:TMALLPROPERTYVALUE INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  // 2012-05-24 add start desc:子属性模板下载

  public void initializeResourcesProType() {
    Logger logger = Logger.getLogger(this.getClass());

    try {

      logger.debug("INSERT statement: " + getInsertTmallPropertySql());
      insertTmallPropertyStatement = tconnection.createPreparedStatement(getInsertTmallPropertySql());
      logger.debug("INSERT statement: " + getInsertTmallPropertySql2());
      insertTmallPropertyStatement2 = tconnection.createPreparedStatement(getInsertTmallPropertySql2());

    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  // 获得淘宝授权品牌集合
  public List<String> getTmallBrand() {

    List<String> obj = new ArrayList<String>();
    try {
      tconnection.init();
      Query existsValueSql = new SimpleQuery("SELECT TMALL_BRAND_CODE FROM TMALL_BRAND ");
      obj = tconnection.executeScalarGetList(existsValueSql);
      if (obj == null || obj.size() <= 0) {
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (tconnection != null) {
        tconnection.dispose();
      }
    }
    return obj;
  }

  // 是否存在属性1
  public boolean existsPropertyChildTemplate(TmallProperty tp) {
    boolean flag = false;
    Query existsPropertySql = new SimpleQuery("SELECT PROPERTY_ID FROM " + TMALL_PROPERTY_TABLE_NAME
        + " WHERE CATEGORY_ID = ? AND property_name = ? AND parent_pid = ? AND parent_vid = ? ", tp.getCategoryId(), tp
        .getPropertyName(), "20000", tp.getParentVid());
    Object obj = tconnection.executeScalar(existsPropertySql);
    if (obj != null) {
      return true;
    }
    return flag;
  }

  // 是否存在属性2
  public boolean existsPropertyChildTemplateByBrand(TmallProperty tp) {
    boolean flag = false;
    Query existsPropertySql = new SimpleQuery("SELECT PROPERTY_ID FROM " + TMALL_PROPERTY_TABLE_NAME
        + " WHERE CATEGORY_ID = ? AND PROPERTY_ID = ?  ", tp.getCategoryId(), tp.getPropertyId());
    Object obj = tconnection.executeScalar(existsPropertySql);
    if (obj != null) {
      return true;
    }
    return flag;
  }

  @SuppressWarnings("unchecked")
  public List<TmallProperty> getChildTemplate(List<String> tmallBrandCode) {

    Logger logger = Logger.getLogger(this.getClass());

    List<TmallProperty> propertyList = null;

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    List<String> listCategoryCode = getChildRenCategory();

    if (listCategoryCode != null) {

      propertyList = new ArrayList<TmallProperty>();
      // 错误数
      int error = 0;
      for (int category = 0; category < listCategoryCode.size(); category++) {
        // 获得请求参数
        String paramStr = createRequestParam(listCategoryCode.get(category), 0);
        // 调用api获得结果
        String resultStr = TmallUtil.getResult(tc.getReqUrl(), paramStr);
        // 对返回的xml结果字符串，进行解析，组装成java对象
        StringReader read = new StringReader(resultStr);
        InputSource source = new InputSource(read);
        SAXBuilder builder = new SAXBuilder();

        Document document;
        try {

          document = builder.build(source);
          Element root = document.getRootElement();
          Element elementItemProps = root.getChild("item_props");
          if (elementItemProps != null) {

            List<Element> listElementItemProps = elementItemProps.getChildren();
            Element elementItemProp = null;
            boolean flag = true;
            for (int i = 0; i < listElementItemProps.size(); i++) {

              elementItemProp = (Element) listElementItemProps.get(i);

              if (!StringUtil.isNullOrEmpty(elementItemProp.getChildText("child_template"))) {
                flag = false;
                String childTemplateStr = elementItemProp.getChildText("child_template");
                String[] childTemplateContents = childTemplateStr.split(";");

                List<String> brandStr = new ArrayList<String>();

                if (tmallBrandCode != null) {
                  brandStr = tmallBrandCode;
                } else {
                  brandStr = getTmallBrand();
                }

                for (int j = 0; j < brandStr.size(); j++) {
                  for (int z = 0; z < childTemplateContents.length; z++) {
                    TmallProperty tmallProperty = new TmallProperty();
                    String childStr = childTemplateContents[z];
                    tmallProperty.setParentPid("20000");
                    tmallProperty.setParentVid(brandStr.get(j));
                    tmallProperty.setCategoryId(listCategoryCode.get(category));
                    tmallProperty.setPropertyName(childStr);
                    tmallProperty.setIsMust(1L);
                    if (elementItemProp.getChildText("is_key_prop").equals("true")) {
                      tmallProperty.setIsKey(1L);
                    } else {
                      tmallProperty.setIsKey(0L);
                    }
                    tmallProperty.setIsSale(0L);
                    tmallProperty.setIsEnum(0L);
                    tmallProperty.setIsMulti(0L);
                    propertyList.add(tmallProperty);
                  }
                }
              }
            }
            if (flag) {
              TmallProperty tmallProperty = new TmallProperty();
              tmallProperty.setPropertyId("20000");
              tmallProperty.setParentPid("0");
              tmallProperty.setParentVid("0");
              tmallProperty.setCategoryId(listCategoryCode.get(category));
              tmallProperty.setPropertyName("请输入品牌名称");
              tmallProperty.setIsMust(1L);
              tmallProperty.setIsKey(1L);
              tmallProperty.setIsSale(0L);
              tmallProperty.setIsEnum(0L);
              tmallProperty.setIsMulti(0L);
              propertyList.add(tmallProperty);
            }
          }
        } catch (JDOMException e) {
          error++;
          Logger.getLogger(this.getClass()).error(e);
          e.printStackTrace();
        } catch (IOException e) {
          error++;
          Logger.getLogger(this.getClass()).error(e);
          e.printStackTrace();
        }
      }
      if (error == 0) {
        logger.info("获取淘宝子属性模板成功");
      } else {
        logger.error("获取淘宝子属性模板失败");
      }
    }
    return propertyList;
  }

  // 接口实现方法(插入子属性模板入口)
  public boolean insertChildProperty(List<String> tmallBrandCode) {
    boolean flag = false;
    List<TmallProperty> tpList = getChildTemplate(tmallBrandCode);
    if (tpList != null) {
      tconnection.init();
      initializeResourcesProType();

      try {
        for (int i = 0; i < tpList.size(); i++) {
          TmallProperty tmallProperty = new TmallProperty();
          tmallProperty = (TmallProperty) tpList.get(i);
          if (StringUtil.isNullOrEmpty(tmallProperty.getPropertyId())) {
            boolean isexists = existsPropertyChildTemplate(tmallProperty);
            if (!isexists) {
              executeInsertTmallPropertyChild(tmallProperty);
            }
          } else {
            boolean isexists = existsPropertyChildTemplateByBrand(tmallProperty);
            if (!isexists) {
              executeInsertTmallPropertyChild(tmallProperty);
            }
          }
        }
        tconnection.getConnection().commit();
        flag = true;
      } catch (SQLException e) {
        try {
          tconnection.getConnection().rollback();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
        e.printStackTrace();
      }
      tconnection.dispose();
    }
    return flag;
  }

  private String getInsertTmallPropertySql() {
    String cont = "''S'' || " + SqlDialect.getDefault().getNextvalNOprm("'TMALL_CHILD_TEMPLATE_SEQ'");
    String insertSql = "" + " INSERT INTO " + TMALL_PROPERTY_TABLE_NAME
        + " ({0} PROPERTY_ID,ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + cont + "," + SqlDialect.getDefault().getNextvalNOprm("'TMALL_PROPERTY_SEQ'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("CATEGORY_ID");
    columnList.add("PROPERTY_NAME");
    columnList.add("PARENT_PID");
    columnList.add("PARENT_VID");
    columnList.add("IS_MUST");
    columnList.add("IS_SALE");
    columnList.add("IS_ENUM");
    columnList.add("IS_MULTI");
    columnList.add("IS_KEY");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  private String getInsertTmallPropertySql2() {
    String insertSql = "" + " INSERT INTO " + TMALL_PROPERTY_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ( {1} "
        + SqlDialect.getDefault().getNextvalNOprm("'TMALL_PROPERTY_SEQ'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("PROPERTY_ID");
    columnList.add("CATEGORY_ID");
    columnList.add("PROPERTY_NAME");
    columnList.add("PARENT_PID");
    columnList.add("PARENT_VID");
    columnList.add("IS_MUST");
    columnList.add("IS_SALE");
    columnList.add("IS_ENUM");
    columnList.add("IS_MULTI");
    columnList.add("IS_KEY");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  public int executeInsertTmallPropertyChild(TmallProperty tp) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    if (!StringUtil.isNullOrEmpty(tp.getPropertyId())) {
      params.add(tp.getPropertyId());
    }
    params.add(tp.getCategoryId());
    params.add(tp.getPropertyName());
    params.add(tp.getParentPid());
    params.add(tp.getParentVid());
    params.add(tp.getIsMust());
    params.add(tp.getIsSale());
    params.add(tp.getIsEnum());
    params.add(tp.getIsMulti());
    params.add(tp.getIsKey());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());

    if (!StringUtil.isNullOrEmpty(tp.getPropertyId())) {
      pstmt = insertTmallPropertyStatement2;
    } else {
      pstmt = insertTmallPropertyStatement;
    }

    logger.debug("Table:TMALLPROPERTY INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }
  // 2012-05-24 add end desc:子属性模板下载

}
