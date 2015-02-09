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
import jp.co.sint.webshop.data.dto.TmallBrand;
import jp.co.sint.webshop.data.dto.TmallCategory;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.ext.tmall.TmallUtil;
import jp.co.sint.webshop.service.tmall.TmallBrandAndCategoryInfoList;
import jp.co.sint.webshop.service.tmall.TmallInsertCategoryListService;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;

public class TmallGetBrandAndCategoryList implements TmallInsertCategoryListService {

  private TmallConnection tconnection = new TmallConnection();

  private PreparedStatement insertTmallCategoryStatement = null;

  private PreparedStatement insertTmallBrandStatement = null;

  private static final String TMALLCATEGORY_TABLE_NAME = DatabaseUtil.getTableName(TmallCategory.class);

  private static final String TMALLBRAND_TABLE_NAME = DatabaseUtil.getTableName(TmallBrand.class);

  /**
   * 查询B商家被授权品牌列表和类目列表
   */
  private static String createRequestParam() {

    TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();

    // 获取key
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    // 组装协议参数。
    apiparamsMap.put("method", TmallUtil.CATEGORY_BRAND_LIST);
    apiparamsMap.put("app_key", tc.getAppKey());
    apiparamsMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    apiparamsMap.put("session", tc.getSessionKey());
    apiparamsMap.put("format", "xml");
    apiparamsMap.put("v", "2.0");
    // 组装应用参数
    apiparamsMap.put("fields", "brand.vid," + "brand.name," + "item_cat.cid," + "item_cat.name," + "item_cat.status,"
        + "item_cat.sort_order," + "item_cat.parent_cid," + "item_cat.is_parent");
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
  public TmallBrandAndCategoryInfoList getBrandAndCategory(String type) {

    Logger logger = Logger.getLogger(this.getClass());

    TmallBrandAndCategoryInfoList tbacil = null;
    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    // 获得请求参数
    String paramStr = createRequestParam();
    // 调用api获得结果
    String resultStr = TmallUtil.getResult(tc.getReqUrl(), paramStr);
    // 对返回的xml结果字符串，进行解析，组装成java对象
    StringReader read = new StringReader(resultStr);
    InputSource source = new InputSource(read);
    SAXBuilder builder = new SAXBuilder();
    Document document;

    try {
      tbacil = new TmallBrandAndCategoryInfoList();

      document = builder.build(source);
      Element root = document.getRootElement();
      Element elementTitle = root.getChild("seller_authorize");

      if (type.equals("B")) {
        // 添加品牌集合start
        Element elementBrands = elementTitle.getChild("brands");
        List<Element> listElementBrands = elementBrands.getChildren();
        Element elementBrand = null;

        List<TmallBrand> brandList = new ArrayList<TmallBrand>();
        for (int i = 0; i < listElementBrands.size(); i++) {
          TmallBrand tmallBrand = new TmallBrand();
          elementBrand = (Element) listElementBrands.get(i);
          tmallBrand.setTmallBrandName(elementBrand.getChildText("name"));
          tmallBrand.setTmallBrandCode(elementBrand.getChildText("vid"));
          brandList.add(tmallBrand);
        }
        tbacil.setTmallBrand(brandList);
        logger.info("获得商家被授权的品牌列表成功");
        // 添加品牌集合end
      }
      if (type.equals("C")) {
        // 添加一级类目集合start
        Element elementItemCats = elementTitle.getChild("item_cats");
        List<Element> listElementItemCats = elementItemCats.getChildren();
        Element elementItemCat = null;

        List<TmallCategory> tmallCategoryList = new ArrayList<TmallCategory>();
        for (int i = 0; i < listElementItemCats.size(); i++) {
          TmallCategory tmallCategory = new TmallCategory();
          elementItemCat = (Element) listElementItemCats.get(i);
          tmallCategory.setCategoryCode(elementItemCat.getChildText("cid"));
          tmallCategory.setCategoryName(elementItemCat.getChildText("name"));
          tmallCategory.setParentCode(elementItemCat.getChildText("parent_cid"));
          if (elementItemCat.getChildText("is_parent").equals("true")) {
            tmallCategory.setIsParent(1L);
          } else {
            tmallCategory.setIsParent(0L);
          }
          tmallCategoryList.add(tmallCategory);
        }
        // 添加一级类目集合end

        // 添加一级以下类目集合start
        for (int i = 0; i < tmallCategoryList.size(); i++) {
          TmallCategory tmallCategory = new TmallCategory();
          tmallCategory = (TmallCategory) tmallCategoryList.get(i);
          if (tmallCategory.getIsParent() == 1L) {
            TmallGetChildRenCategoryList childRenCategory = new TmallGetChildRenCategoryList();
            List<TmallCategory> childRenCategoryList = childRenCategory.getChildrenCategory(tmallCategory.getCategoryCode());
            for (int j = 0; j < childRenCategoryList.size(); j++) {
              TmallCategory tmallChildRenCategory = new TmallCategory();
              tmallChildRenCategory = (TmallCategory) childRenCategoryList.get(j);
              tmallCategoryList.add(tmallChildRenCategory);
            }
          }
        }
        tbacil.setTmallCategory(tmallCategoryList);
        logger.info("获得商家被授权的类目列表成功");
      }

    } catch (JDOMException e) {
      logger.error("获得商家被授权品牌列表或类目列表失败");
      Logger.getLogger(this.getClass()).error(e);
      e.printStackTrace();
    } catch (IOException e) {
      logger.error("获得商家被授权品牌列表或类目列表失败");
      Logger.getLogger(this.getClass()).error(e);
      e.printStackTrace();
    }
    return tbacil;
  }

  // 接口实现方法：插入类目
  public boolean insertCategoryList() {
    boolean flag = false;
    TmallBrandAndCategoryInfoList tbacil = getBrandAndCategory("C");
    if (tbacil != null) {
      tconnection.init();
      initializeResources("C");

      List<TmallCategory> tmallCategoryList = tbacil.getTmallCategory();
      try {
        for (int i = 0; i < tmallCategoryList.size(); i++) {
          TmallCategory tmallCategory = new TmallCategory();
          tmallCategory = (TmallCategory) tmallCategoryList.get(i);
          // 只将子类目保存到ECDB
          if (tmallCategory.getIsParent() == 0L) {
            executeInsertTmallCategory(tmallCategory);
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

  // 接口实现方法：插入淘宝品牌
  public boolean insertTmallBrandList() {
    boolean flag = false;
    TmallBrandAndCategoryInfoList tbacil = getBrandAndCategory("B");
    List<String> brandCodeList = null;
    if (tbacil != null) {
      tconnection.init();
      initializeResources("B");

      List<TmallBrand> tmallBrandList = tbacil.getTmallBrand();

      try {
        brandCodeList = new ArrayList<String>();
        for (int i = 0; i < tmallBrandList.size(); i++) {
          TmallBrand tmallBrand = new TmallBrand();
          tmallBrand = (TmallBrand) tmallBrandList.get(i);
          boolean isexists = existsTmallBrand(tmallBrand.getTmallBrandCode());
          if (!isexists) {
            brandCodeList.add(tmallBrand.getTmallBrandCode());
            executeInsertTmallBrand(tmallBrand);
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
    // 补充子属性模版
    if(flag && brandCodeList != null && brandCodeList.size() > 0){
      TmallGetPropertyAndValueList tgpavl = new TmallGetPropertyAndValueList();
      tgpavl.insertChildProperty(brandCodeList);
    }
    return flag;
  }

  public void initializeResources(String type) {
    Logger logger = Logger.getLogger(this.getClass());

    if (type.equals("B")) {
      logger.debug("INSERT statement: " + getInsertTmallBrandQuery());
    }
    if (type.equals("C")) {
      logger.debug("INSERT statement: " + getInsertTmallCategoryQuery());
    }
    try {
      if (type.equals("B")) {
        insertTmallBrandStatement = tconnection.createPreparedStatement(getInsertTmallBrandQuery());
      }
      if (type.equals("C")) {
        insertTmallCategoryStatement = tconnection.createPreparedStatement(getInsertTmallCategoryQuery());
      }
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  // Insert类目sql
  private String getInsertTmallCategoryQuery() {
    String insertSql = "" + " INSERT INTO " + TMALLCATEGORY_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'tmall_category_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("CATEGORY_CODE");
    columnList.add("CATEGORY_NAME");
    columnList.add("PARENT_CODE");
    columnList.add("IS_PARENT");
    columnList.add("IS_SHOP_CATEGORY");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  // Insert类目参数
  public int executeInsertTmallCategory(TmallCategory tc) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    params.add(tc.getCategoryCode());
    params.add(tc.getCategoryName());
    params.add(tc.getParentCode());
    params.add(tc.getIsParent());
    params.add(0L);
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());

    pstmt = insertTmallCategoryStatement;

    logger.debug("Table:TMALL_CATEGORY INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  // 是否存在品牌
  public boolean existsTmallBrand(String tmallBrandCode) {
    boolean flag = false;
    Query existsBrandSql = new SimpleQuery("SELECT tmall_brand_code FROM " + TMALLBRAND_TABLE_NAME + " WHERE tmall_brand_code = ?  ",
        tmallBrandCode);
    Object obj = tconnection.executeScalar(existsBrandSql);
    if (obj != null) {
      return true;
    }
    return flag;
  }

  // Insert淘宝品牌sql
  private String getInsertTmallBrandQuery() {
    String insertSql = "" + " INSERT INTO " + TMALLBRAND_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'tmall_brand_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("SHOP_CODE");
    columnList.add("TMALL_BRAND_CODE");
    columnList.add("TMALL_BRAND_NAME");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  // Insert淘宝品牌参数
  public int executeInsertTmallBrand(TmallBrand tb) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    params.add("00000000");
    params.add(tb.getTmallBrandCode());
    params.add(tb.getTmallBrandName());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());

    pstmt = insertTmallBrandStatement;

    logger.debug("Table:TMALL_BRAND INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }
}
