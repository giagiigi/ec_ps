package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dao.CategoryDao;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.CategoryAttribute;
import jp.co.sint.webshop.data.dto.CategoryAttributeValue;
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.data.dto.OriginalPlace;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.catalog.CategoryAttributeValueImport;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class CategoryAttributeValueDataImportDataSource extends
    SqlImportDataSource<CategoryAttributeValueDataCsvSchema, CategoryAttributeValueDataImportCondition> {

  WebshopConfig config = DIContainer.getWebshopConfig();

  // カテゴリ属性値
  private static final String CATEGORY_ATTRIBUTE_VALUE_TABLE_NAME = DatabaseUtil.getTableName(CategoryAttributeValue.class);

  // カテゴリ陳列商品
  private static final String CATEGORY_COMMODITY_TABLE_NAME = DatabaseUtil.getTableName(CategoryCommodity.class);

  // 商品基本表
  private static final String HEADER_TABLE_NAME = DatabaseUtil.getTableName(CCommodityHeader.class);

  /** カテゴリ属性値INSERT用Statement */
  private PreparedStatement insertCategoryAttributeValueStatement = null;

  /** カテゴリ属性値全部删除用Statement */
  private PreparedStatement deleteAllCategoryAttributeValueStatement = null;

  /** カテゴリ属性値单条删除用Statement */
  private PreparedStatement deleteCategoryAttributeValueStatement = null;

  /** カテゴリ属性値UPDATE用Statement */
  private PreparedStatement updateCategoryAttributeValueStatement = null;

  /** カテゴリ属性値SELECT用Statement */
  private PreparedStatement selectCategoryAttributeValueStatement = null;

  /** 商品ヘッダSELECT用Statement */
  private PreparedStatement selectHeaderStatement = null;

  /** 商品ヘッダupdate用Statement */
  private PreparedStatement updateHeaderStatement = null;

  /** カテゴリ陳列商品SELECT用Statement */
  private PreparedStatement selectCategoryCommodityStatement = null;

  /** カテゴリ陳列商品SELECT所有用Statement */
  @SuppressWarnings("unused")
private PreparedStatement selectAllCategoryCommodityStatement = null;

  /** カテゴリ陳列商品INSERT用Statement */
  private PreparedStatement insertCategoryCommodityStatement = null;

  /** カテゴリ陳列商品DELETE用Statement */
  private PreparedStatement deleteCategoryCommodityStatement = null;

  /** カテゴリ属性値 */
  private CategoryAttributeValueImport categoryAttributeValue = null;

  String path = "";

  boolean exists1 = false;

  boolean exists2 = false;

  boolean exists3 = false;

  // false时为删除，true时为更新或新增
  boolean importFlag = false;

  // 商品基本表
  boolean existsHeader = false;

  // Category_Commodity表
  boolean existsCategoryCommodity = false;

  // csv不存在category_attribute_value1列
  boolean categoryAttributeValue1 = false;

  // csv不存在category_attribute_value2列
  boolean categoryAttributeValue2 = false;

  // csv不存在category_attribute_value3列
  boolean categoryAttributeValue3 = false;

  @Override
  protected void initializeResources() {
    Logger logger = Logger.getLogger(this.getClass());

    logger.debug("DELETE statement: " + getDeleteCategoryAttributeValueQuery());
    logger.debug("CHECK  statement: " + getSelectCategoryAttributeValueQuery());
    logger.debug("INSERT statement: " + getInsertCategoryAttributeValueQuery());
    logger.debug("UPDATE statement: " + getUpdateCategoryAttributeValueQuery());

    logger.debug("INSERT statement: " + getInsertCategoryCommodityQuery());
    logger.debug("DELETE statement: " + getDeleteCategoryCommodityQuery());
    logger.debug("CHECK  statement: " + getSelectCategoryCommodityQuery());
    logger.debug("CHECK  statement: " + getSelectAllCategoryCommodityQuery());

    logger.debug("UPDATE statement: " + getUpdateCCommodityHeaderQuery());
    logger.debug("CHECK  statement: " + getSelectCCommodityHeaderQuery());

    try {

      // カテゴリ属性値全删除
      deleteAllCategoryAttributeValueStatement = createPreparedStatement(getDeleteAllCategoryAttributeValueQuery());
      // カテゴリ属性値单条删除
      deleteCategoryAttributeValueStatement = createPreparedStatement(getDeleteCategoryAttributeValueQuery());
      // カテゴリ属性値修改
      updateCategoryAttributeValueStatement = createPreparedStatement(getUpdateCategoryAttributeValueQuery());
      // カテゴリ属性値查询
      selectCategoryAttributeValueStatement = createPreparedStatement(getSelectCategoryAttributeValueQuery());
      // カテゴリ属性値追加
      insertCategoryAttributeValueStatement = createPreparedStatement(getInsertCategoryAttributeValueQuery());

      // 商品ヘッダ查询
      selectHeaderStatement = createPreparedStatement(getSelectCCommodityHeaderQuery());
      // 商品ヘッダ修改
      updateHeaderStatement = createPreparedStatement(getUpdateCCommodityHeaderQuery());

      // カテゴリ陳列商品查询
      selectCategoryCommodityStatement = createPreparedStatement(getSelectCategoryCommodityQuery());
      // カテゴリ陳列商品查询所有
      selectAllCategoryCommodityStatement = createPreparedStatement(getSelectAllCategoryCommodityQuery());
      // カテゴリ陳列商品追加
      insertCategoryCommodityStatement = createPreparedStatement(getInsertCategoryCommodityQuery());
      // カテゴリ陳列商品删除
      deleteCategoryCommodityStatement = createPreparedStatement(getDeleteCategoryCommodityQuery());
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public boolean setSchema(List<String> csvLine) {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    for (String column : csvLine) {
      //
      if (column.equals("shop_code")) {
        columns.add(new CsvColumnImpl("shop_code", 
            Messages.getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.0"),CsvDataType.STRING, false, false, true, null));
        // 更新区分
      } else if (column.equals("import_flag")) {
        columns.add(new CsvColumnImpl("import_flag", 
            Messages.getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.23"),CsvDataType.STRING));
        importFlag = true;
        // 商品编号
      } else if (column.equals("commodity_code")) {
        columns.add(new CsvColumnImpl("commodity_code", 
            Messages.getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.1"), CsvDataType.STRING, false, false, true, null));
        // 分类编号
      } else if (column.equals("category_code")) {
        columns.add(new CsvColumnImpl("category_code",
            Messages.getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.2"), CsvDataType.STRING, false, false, true,
            null));
        // 分类属性值1category_attribute_value1
      } else if (column.equals("original_code")) {
        columns.add(new CsvColumnImpl("original_code", 
            Messages.getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.19"), CsvDataType.STRING));
        categoryAttributeValue1 = true;
        // 分类属性值2
      } else if (column.equals("category_attribute_value2")) {
        columns.add(new CsvColumnImpl("category_attribute_value2", 
            Messages.getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.20"), CsvDataType.STRING));
        categoryAttributeValue2 = true;
        // 分类属性值2英文
      } else if (column.equals("category_attribute_value2_en")) {
          columns.add(new CsvColumnImpl("category_attribute_value2_en", 
              Messages.getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.55"), CsvDataType.STRING));
            // 分类属性值2日文
     } else if (column.equals("category_attribute_value2_jp")) {
         columns.add(new CsvColumnImpl("category_attribute_value2_jp", 
            Messages.getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.58"), CsvDataType.STRING));
             // 分类属性值3
      } else if (column.equals("category_attribute_value3")) {
        columns.add(new CsvColumnImpl("category_attribute_value3", 
            Messages.getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.21"), CsvDataType.STRING));
        categoryAttributeValue3 = true; 
        // 分类属性值3英文
      } else if (column.equals("category_attribute_value3_en")) {
          columns.add(new CsvColumnImpl("category_attribute_value3_en", 
              Messages.getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.56"), CsvDataType.STRING));
       // 分类属性值3日文
     } else if (column.equals("category_attribute_value3_jp")) {
         columns.add(new CsvColumnImpl("category_attribute_value3_jp", 
            Messages.getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.59"), CsvDataType.STRING));
      }
    }
    getSchema().setColumns(columns);
    return true;
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      categoryAttributeValue = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CategoryAttributeValueImport.class);
      
      if(!StringUtil.isNullOrEmpty(categoryAttributeValue.getOriginalCode())){
    	  Query query = new SimpleQuery("SELECT * FROM ORIGINAL_PLACE WHERE ORIGINAL_CODE=?", 
        		  categoryAttributeValue.getOriginalCode());
          OriginalPlace op = DatabaseUtil.loadAsBean(query, OriginalPlace.class);
          if(StringUtil.isNullOrEmpty(op)){
          	summary.getErrors().add(new ValidationResult(null, null, "输入的产地CODE不存在"));
              return summary;
          }else{
        	  categoryAttributeValue.setCategoryAttributeValue1(op.getOriginalPlaceNameCn());
        	  categoryAttributeValue.setCategoryAttributeValue1En(op.getOriginalPlaceNameEn());
        	  categoryAttributeValue.setCategoryAttributeValue1Jp(op.getOriginalPlaceNameJp());
          }
      }
     
      if (StringUtil.hasValue(categoryAttributeValue.getCommodityCode())) {
        categoryAttributeValue.setCommodityCode(categoryAttributeValue.getCommodityCode().replace("'", ""));
      }
      // 存在csv更新区分列并且为空、不等于1或0时报错
      if (importFlag) {
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getImportFlag())) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.IMPORT_FLAG, categoryAttributeValue.getCommodityCode())));
          return summary;
        } else {
          if (!(categoryAttributeValue.getImportFlag().equals("1") || categoryAttributeValue.getImportFlag().equals("0"))) {
            summary.getErrors().add(
                new ValidationResult(null, null, Message.get(CsvMessage.IMPORT_FLAG, categoryAttributeValue.getCommodityCode())));
            return summary;
          }
        }
      }
      // 查询商品是否存在
      existsHeader = exists(selectHeaderStatement, categoryAttributeValue.getCommodityCode());
      if (!existsHeader) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.COMMODITY_CODE_NOT_EXIST, categoryAttributeValue.getCommodityCode())));
        return summary;
      }

      // 查询カテゴリID在category表中是否存在
      SimpleQuery CategoryCodeExistsQuery = new SimpleQuery(" SELECT PATH FROM CATEGORY WHERE CATEGORY_CODE = ? ");
      CategoryCodeExistsQuery.setParameters(categoryAttributeValue.getCategoryCode());
      Object object = executeScalar(CategoryCodeExistsQuery);
      if (object == null) {
        // existsCategory=exists(selectCategoryStatement,categoryAttributeValue.getCategoryCode());
        // if(!existsCategory){
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, categoryAttributeValue.getCategoryCode())));
        return summary;
      } else {
        path = object.toString()+"~"+categoryAttributeValue.getCategoryCode();
      }
      // 查询カテゴリID在カテゴリ陳列商品表中是否存在，不存在追加，存在不做任何操作

      existsCategoryCommodity = exists(selectCategoryCommodityStatement, categoryAttributeValue.getCategoryCode(),categoryAttributeValue.getCommodityCode());
      // 判断分类属性值1是否存在
      exists1 = exists(selectCategoryAttributeValueStatement, categoryAttributeValue.getCommodityCode(), categoryAttributeValue.getCategoryCode(), "0");

      // 判断分类属性值2是否存在
      exists2 = exists(selectCategoryAttributeValueStatement, categoryAttributeValue.getCommodityCode(), categoryAttributeValue.getCategoryCode(), "1");

      // 判断分类属性3是否存在
      exists3 = exists(selectCategoryAttributeValueStatement, categoryAttributeValue.getCommodityCode(), categoryAttributeValue.getCategoryCode(), "2");
    } catch (CsvImportException e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }

    if (summary.hasError()) {
      return summary;
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    try {
      if (StringUtil.isNullOrEmpty(categoryAttributeValue.getImportFlag()) || categoryAttributeValue.getImportFlag().equals("1")) {
        if (existsHeader) {
          SimpleQuery categoryAttributeExistsQuery = new SimpleQuery(
              " SELECT CATEGORY_ATTRIBUTE_NAME FROM CATEGORY_ATTRIBUTE WHERE CATEGORY_CODE = ? AND CATEGORY_ATTRIBUTE_NO = ? ");
          String value = ""; 
          
          // 存在列
          if (categoryAttributeValue1) {
            // 分类属性值1
            categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategoryAttributeValue1());
            // 分类属性值1英文
            categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategoryAttributeValue1En());
            // 分类属性值1日文
            categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategoryAttributeValue1Jp());
            
            categoryAttributeValue.setCategoryAttributeNo(0L);
            // 分类属性值1为空就删除，不为空时新增或更新
            if (StringUtil.isNullOrEmpty(categoryAttributeValue.getOriginalCode())) {
              executeDeleteCategoryAttributeValue("categoryAttributeNo");
            } else {
              int updCategoryAttributeValueCount1 = executeUpdateCategoryAttributeValue(exists1);
              if (updCategoryAttributeValueCount1 != 1) {
                throw new CsvImportException();
              }
              categoryAttributeExistsQuery.setParameters(categoryAttributeValue.getCategoryCode(), 0L);
              Object object = executeScalar(categoryAttributeExistsQuery);
              if (object != null) {
                value = object.toString();
              }
            }
          }
          // 存在列
          if (categoryAttributeValue2) {
            // 分类属性值2
            categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategoryAttributeValue2());
            // 分类属性值2英文
            categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategoryAttributeValue2En());
            // 分类属性值2日文
            categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategoryAttributeValue2Jp());
            categoryAttributeValue.setCategoryAttributeNo(1L);
            // 分类属性值2为空就删除，不为空时新增或更新
            if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategoryAttributeValue())||
            		StringUtil.isNullOrEmpty(categoryAttributeValue.getCategoryAttributeValueEn())||
            		StringUtil.isNullOrEmpty(categoryAttributeValue.getCategoryAttributeValueJp())) {
              executeDeleteCategoryAttributeValue("categoryAttributeNo");
            } else {
              int updCategoryAttributeValueCount2 = executeUpdateCategoryAttributeValue(exists1);
              if (updCategoryAttributeValueCount2 != 1) {
                throw new CsvImportException();
              }
              categoryAttributeExistsQuery.setParameters(categoryAttributeValue.getCategoryCode(), 1L);
              Object object = executeScalar(categoryAttributeExistsQuery);
              if (object != null) {
                if (StringUtil.isNullOrEmpty(value)) {
                  value = value + object.toString();
                } else {
                  value = value + "|" + object.toString();
                }
              }
            }
          }
          // 存在列
          if (categoryAttributeValue3) {
            // 分类属性值3
            categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategoryAttributeValue3());
            // 分类属性值3英文
            categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategoryAttributeValue3En());
            // 分类属性值3日文
            categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategoryAttributeValue3Jp());
            categoryAttributeValue.setCategoryAttributeNo(2L);
            // 分类属性值3为空就删除，不为空时新增或更新
            if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategoryAttributeValue())||
            		StringUtil.isNullOrEmpty(categoryAttributeValue.getCategoryAttributeValueEn())||
            		StringUtil.isNullOrEmpty(categoryAttributeValue.getCategoryAttributeValueJp())) {
              executeDeleteCategoryAttributeValue("categoryAttributeNo");
            } else {
              int updCategoryAttributeValueCount3 = executeUpdateCategoryAttributeValue(exists1);
              if (updCategoryAttributeValueCount3 != 1) {
                throw new CsvImportException();
              }
              categoryAttributeExistsQuery.setParameters(categoryAttributeValue.getCategoryCode(), 2L);
              Object object = executeScalar(categoryAttributeExistsQuery);
              if (object != null) {
                if (StringUtil.isNullOrEmpty(value)) {
                  value = value + object.toString();
                } else {
                  value = value + "|" + object.toString();
                }
              }
            }
          }
          // 不存在追加陈列商品表
          if (!existsCategoryCommodity) {
            int updCategoryAttributeCount = executeInsertCategoryCommodity();
            if (updCategoryAttributeCount != 1) {
              throw new CsvImportException();
            }
          }
            // 取得商品所有的検索用カテゴリパス
          String headerCategorySearchPath = null;
          headerCategorySearchPath = getPath("insert");
          
          if (!existsCategoryCommodity) {
            if(StringUtil.isNullOrEmpty(headerCategorySearchPath)){
              headerCategorySearchPath = path;
            }
            
          }
          String categoryAttribute = null;
          categoryAttribute = getValue();
          
          if (!StringUtil.isNullOrEmpty(value)) {
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              categoryAttribute = value;
            } else {
              categoryAttribute = categoryAttribute + "#" + value;
            }
          }
          // 更新商品基本表path
          int updHeaderCount = executeUpdateHeader(headerCategorySearchPath, categoryAttribute);
          if (updHeaderCount != 1) {
            throw new CsvImportException();
          }
          
        }
      } else {
        // 删除カテゴリ陳列商品
        executeDeleteCategoryCommodity();
        // 删除カテゴリ属性値所有数据
        executeDeleteCategoryAttributeValue("ALL");
        // 取得商品所有的検索用カテゴリパス
        String headerCategorySearchPath = getPath("delete");
        String categoryAttribute = getValue();
        // 更新商品基本表path
        int updHeaderCount = executeUpdateHeader(headerCategorySearchPath, categoryAttribute);
        if (updHeaderCount != 1) {
          throw new CsvImportException();
        }
      }
    } catch (SQLException e) {
      logger.debug(e.getMessage());
      throw new CsvImportException(e);
    } catch (CsvImportException e) {
      logger.debug(e.getMessage());
      throw e;
    } catch (RuntimeException e) {
      logger.debug(e.getMessage());
      throw new CsvImportException(e);
    }

  }

  private String getPath(String insertAndDelete) {
    // 查找出同一商品编号所有的path值并以#号连接起
    String headerCategorySearchPath = null;
    CategoryDao catDao = DIContainer.getDao(CategoryDao.class);
    Query query;
    if (insertAndDelete.equals("insert")) {
      query = new SimpleQuery(CatalogQuery.GET_CATEGORY_COMMODITY_INSERT_LIST, config.getSiteShopCode(), categoryAttributeValue.getCommodityCode());
    } else {
      query = new SimpleQuery(CatalogQuery.GET_CATEGORY_COMMODITY_DELETE_LIST, config.getSiteShopCode(), categoryAttributeValue.getCommodityCode(), categoryAttributeValue.getCategoryCode());
    }
    List<CategoryCommodity> categoryListOld = loadAsBeanList(query, CategoryCommodity.class);
    List<CategoryCommodity> categoryList = new ArrayList<CategoryCommodity>();
    List<CategoryCommodity> categoryList2 = new ArrayList<CategoryCommodity>();
    for (CategoryCommodity category : categoryListOld) {
      if (category.getCategoryCode().equals("02001001") || category.getCategoryCode().equals("01001001")) {
        categoryList.add(category);
      } else {
        categoryList2.add(category);
      }
    }
    categoryList.addAll(categoryList2);
    
    for (CategoryCommodity category : categoryList) {
      //add by os011 修改类目没有加三级类目 start 2012/04/10
      Category cat = catDao.load(category.getCategoryCode());
      if (category == categoryList.get(0)) {
        headerCategorySearchPath = cat.getPath()+"~"+category.getCategoryCode();
      } else {
        if ((headerCategorySearchPath + "#" + cat.getPath()+"~"+category.getCategoryCode() + "#" + path).length() < 256) {
          headerCategorySearchPath = headerCategorySearchPath + "#" + cat.getPath()+"~"+category.getCategoryCode();
        }
      }
    }
  //add by os011 修改类目没有加三级类目 end 2012/04/10
    return headerCategorySearchPath;
  }

  /**
   * 修改商品基本表的path
   */
  private int executeUpdateHeader(String categorySearchPath, String categoryAttribute) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;

    params.add(categorySearchPath);
    params.add(categoryAttribute);

    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());

    params.add(categoryAttributeValue.getCommodityCode());

    pstmt = updateHeaderStatement;
    logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  /**
   * 陈列商品表追加
   */
  private int executeInsertCategoryCommodity() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;
    // ショップコード
    params.add(config.getSiteShopCode());
    // カテゴリコード
    params.add(categoryAttributeValue.getCategoryCode());
    // 商品コード
    params.add(categoryAttributeValue.getCommodityCode());
    // 検索用カテゴリパス
    params.add(path);

    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());

    pstmt = insertCategoryCommodityStatement;
    logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  /**
   * 陈列商品表删除
   */
  private int executeDeleteCategoryCommodity() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;

    params.add(categoryAttributeValue.getCommodityCode());
    params.add(categoryAttributeValue.getCategoryCode());

    pstmt = deleteCategoryCommodityStatement;
    logger.debug("DELETE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  /**
   * 追加更新属性值表
   */
  private int executeUpdateCategoryAttributeValue(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;
    if (exists) {
      // カテゴリ属性値
      params.add(categoryAttributeValue.getCategoryAttributeValue());
      // カテゴリ属性英文値
      params.add(categoryAttributeValue.getCategoryAttributeValueEn());
      // カテゴリ属性日文値
      params.add(categoryAttributeValue.getCategoryAttributeValueJp());

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      // 商品コード
      params.add(categoryAttributeValue.getCommodityCode());
      // カテゴリコード
      params.add(categoryAttributeValue.getCategoryCode());
      // カテゴリ属性番号
      params.add(categoryAttributeValue.getCategoryAttributeNo());

      pstmt = updateCategoryAttributeValueStatement;
      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else {
      // ショップコード
      params.add(config.getSiteShopCode());
      // カテゴリコード
      params.add(categoryAttributeValue.getCategoryCode());
      // カテゴリ属性番号
      params.add(categoryAttributeValue.getCategoryAttributeNo());
      // 商品コード
      params.add(categoryAttributeValue.getCommodityCode());
      // カテゴリ属性値
      params.add(categoryAttributeValue.getCategoryAttributeValue());
      // カテゴリ属性英文値
      params.add(categoryAttributeValue.getCategoryAttributeValueEn());
      // カテゴリ属性日文値
      params.add(categoryAttributeValue.getCategoryAttributeValueJp());

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      pstmt = insertCategoryAttributeValueStatement;
      logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();

  }

  /**
   * カテゴリ属性値删除
   */
  private int executeDeleteCategoryAttributeValue(String all) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;

    params.add(categoryAttributeValue.getCommodityCode());
    params.add(categoryAttributeValue.getCategoryCode());
    if (all.equals("all")) {
      pstmt = deleteAllCategoryAttributeValueStatement;
    } else {
      params.add(categoryAttributeValue.getCategoryAttributeNo());
      pstmt = deleteCategoryAttributeValueStatement;
    }

    logger.debug("DELETE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  /**
   * カテゴリ陳列商品表追加Query
   */
  private String getInsertCategoryCommodityQuery() {
    String insertSql = "" + " INSERT INTO " + CATEGORY_COMMODITY_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'category_commodity_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    // ショップコード
    columnList.add("SHOP_CODE");
    // カテゴリコード
    columnList.add("CATEGORY_CODE");
    // 商品コード
    columnList.add("COMMODITY_CODE");
    // 検索用カテゴリパス
    columnList.add("CATEGORY_SEARCH_PATH");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  /**
   * カテゴリ陳列商品查询Query
   */
  private String getSelectCategoryCommodityQuery() {
    String selectSql = "SELECT COUNT(*) FROM " + CATEGORY_COMMODITY_TABLE_NAME 
      + " WHERE CATEGORY_CODE = ? AND COMMODITY_CODE = ? ";
    return selectSql;
  }

  /**
   * カテゴリ陳列商品查询所有Query
   */
  private String getSelectAllCategoryCommodityQuery() {
    String selectSql = "SELECT CATEGORY_CODE FROM " + CATEGORY_COMMODITY_TABLE_NAME 
      + " WHERE COMMODITY_CODE = ? ";
    return selectSql;
  }

  /**
   * カテゴリ陳列商品删除Query
   */
  private String getDeleteCategoryCommodityQuery() {
    String deleteSql = "" + "DELETE FROM " + CATEGORY_COMMODITY_TABLE_NAME 
      + " WHERE COMMODITY_CODE = ? AND CATEGORY_CODE = ? ";
    return deleteSql;
  }

  /**
   * 商品ヘッダ查询Query
   */
  private String getSelectCCommodityHeaderQuery() {
    String selectSql = "" + " SELECT COUNT(*) FROM " + HEADER_TABLE_NAME 
      + " WHERE COMMODITY_CODE = ? ";
    return selectSql;
  }

  /**
   * 商品ヘッダ修改Query
   */
  private String getUpdateCCommodityHeaderQuery() {
    String selectSql = "" + " UPDATE " + HEADER_TABLE_NAME
        + " SET CATEGORY_SEARCH_PATH = ? ,CATEGORY_ATTRIBUTE_VALUE = ?," 
        // 20130703 shen update start
        // + " SYNC_FLAG_EC = 1,   EXPORT_FLAG_ERP = 1,EXPORT_FLAG_WMS = 1, " 
        + " SYNC_FLAG_EC = 1,   EXPORT_FLAG_ERP = 1,EXPORT_FLAG_WMS = 0, " 
        // 20130703 shen update end
        + " UPDATED_USER = ?, UPDATED_DATETIME = ? " 
        + " WHERE COMMODITY_CODE = ? ";
    return selectSql;
  }

  /**
   * カテゴリ属性値更新Query
   */
  private String getUpdateCategoryAttributeValueQuery() {
    String updateSql = "" + " UPDATE " + CATEGORY_ATTRIBUTE_VALUE_TABLE_NAME
        + " SET CATEGORY_ATTRIBUTE_VALUE = ? ,CATEGORY_ATTRIBUTE_VALUE_EN = ? ,CATEGORY_ATTRIBUTE_VALUE_JP = ? ," 
        + " UPDATED_USER = ?, UPDATED_DATETIME = ? "
        + " WHERE COMMODITY_CODE = ? AND CATEGORY_CODE = ? AND CATEGORY_ATTRIBUTE_NO = ?";
    return updateSql;
  }

  /**
   * カテゴリ属性値追加Query
   */
  private String getInsertCategoryAttributeValueQuery() {
    String insertSql = "" + " INSERT INTO " + CATEGORY_ATTRIBUTE_VALUE_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'category_attribute_value_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    // ショップコード
    columnList.add("SHOP_CODE");
    // カテゴリコード
    columnList.add("CATEGORY_CODE");
    // カテゴリ属性番号
    columnList.add("CATEGORY_ATTRIBUTE_NO");
    // 商品コード
    columnList.add("COMMODITY_CODE");
    // カテゴリ属性値
    columnList.add("CATEGORY_ATTRIBUTE_VALUE");
    // カテゴリ属性英文値
    columnList.add("CATEGORY_ATTRIBUTE_VALUE_EN");
    // カテゴリ属性日文値
    columnList.add("CATEGORY_ATTRIBUTE_VALUE_JP");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  /**
   * カテゴリ属性値查询Query
   */
  private String getSelectCategoryAttributeValueQuery() {
    String selectSql = "" + " SELECT COUNT(*) FROM " + CATEGORY_ATTRIBUTE_VALUE_TABLE_NAME
        + " WHERE COMMODITY_CODE = ? AND CATEGORY_CODE = ? AND CATEGORY_ATTRIBUTE_NO = ?";
    return selectSql;
  }

  /**
   * カテゴリ属性値全删除Query
   */
  private String getDeleteAllCategoryAttributeValueQuery() {
    String deleteSql = "" + "DELETE FROM " + CATEGORY_ATTRIBUTE_VALUE_TABLE_NAME
        + " WHERE COMMODITY_CODE = ? AND CATEGORY_CODE = ? ";
    return deleteSql;
  }

  /**
   * カテゴリ属性値删除一条Query
   */
  private String getDeleteCategoryAttributeValueQuery() {
    String deleteSql = "" + "DELETE FROM " + CATEGORY_ATTRIBUTE_VALUE_TABLE_NAME
        + " WHERE COMMODITY_CODE = ? AND CATEGORY_CODE = ? AND CATEGORY_ATTRIBUTE_NO = ?";
    return deleteSql;
  }

  private String getValue() {
    // 查找出同一商品编号所有的商品の分類属性値
    String categoryAttribute = null;
    Query query = new SimpleQuery(CatalogQuery.GET_CATEGORY_CODE_LIST, categoryAttributeValue.getCommodityCode(),categoryAttributeValue.getCategoryCode());
    List<CategoryAttributeValue> list = loadAsBeanList(query, CategoryAttributeValue.class);
    String temp="";
    for (int i = 0; i < list.size(); i++) {
      query = new SimpleQuery(CatalogQuery.GET_CATEGORY_ATTRIBUTE_NAME_LIST, list.get(i).getCategoryCode(), list.get(i).getCategoryAttributeNo());
      CategoryAttribute bean = loadAsBean(query, CategoryAttribute.class);
//      if ((i + 1) < list.size()) {
//        if (list.get(i).getCategoryCode().equals(list.get(i + 1).getCategoryCode())) {
//          if (i == 0) {
//            categoryAttribute = bean.getCategoryAttributeName();
//          } else {
//            categoryAttribute = categoryAttribute + "|" + bean.getCategoryAttributeName();
//          }
//        } else {
//          categoryAttribute = categoryAttribute + "#" + bean.getCategoryAttributeName();
//        }
//      } else {
//        if (list.get(i - 1).getCategoryCode().equals(list.get(i).getCategoryCode())) {
//          categoryAttribute = categoryAttribute + "|" + bean.getCategoryAttributeName();
//        } else {
//          categoryAttribute = categoryAttribute + "#" + bean.getCategoryAttributeName();
//        }
//      }
      //########## 以上代码有问题修改如下
      if(bean==null){
        continue;
      }
      if (i == 0) {
      categoryAttribute = bean.getCategoryAttributeName();
    } else {
      if(temp.equals(bean.getCategoryCode())){
        categoryAttribute = categoryAttribute + "|" + bean.getCategoryAttributeName(); 
      }else{
        categoryAttribute = categoryAttribute + "#" + bean.getCategoryAttributeName();
      }
    }
      temp=bean.getCategoryCode();
    }
    return categoryAttribute;
  }
}
