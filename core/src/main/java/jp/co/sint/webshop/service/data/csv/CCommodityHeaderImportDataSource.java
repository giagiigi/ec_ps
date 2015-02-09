package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.configure.WebShopSpecialConfig;
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
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.OriginalPlace;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.catalog.CCommodityHeaderImport;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class CCommodityHeaderImportDataSource extends
    SqlImportDataSource<CCommodityHeaderCsvSchema, CCommodityHeaderImportCondition> {

  private static final String HEADER_TABLE_NAME = DatabaseUtil.getTableName(CCommodityHeader.class);


  /** ヘッダUPDATE用Statement */
  private PreparedStatement updateHeaderStatement = null;

  /** ヘッダ存在チェック用Statement */
  private PreparedStatement selectHeaderStatement = null;

  /** 商品ヘッダ */
  private CCommodityHeaderImport header = null;

  boolean existsHeader = false;
  
  // 20130703 shen add start
  private PreparedStatement updateSyncFlag = null;
  // 20130703 shen add end
  
  // 2014/05/06 京东WBS对应 ob_卢 add start
  private PreparedStatement updateSyncFlagJd = null;
  // 2014/05/06 京东WBS对应 ob_卢 add end

  @Override
  protected void initializeResources() {
    // Logger logger = Logger.getLogger(this.getClass());
    // logger.debug("INSERT statement: " + getInsertHeaderQuery());
    // logger.debug("UPDATE statement: " + getUpdateHeaderQuery());
    // logger.debug("CHECK  statement: " + getSelectHeaderQuery());

    try {
      // insertHeaderStatement =
      // createPreparedStatement(getInsertHeaderQuery());
      // updateHeaderStatement = createPreparedStatement(getUpdateHeaderQuery());
      selectHeaderStatement = createPreparedStatement(getSelectHeaderQuery());
      
      // 20130703 shen add start
      updateSyncFlag = createPreparedStatement(getUpdateSyncFlagQuery());
      // 20130703 shen add end
      
      // 2014/05/06 京东WBS对应 ob_卢 add start
      updateSyncFlagJd = createPreparedStatement(getUpdateSyncFlagJdQuery());
      // 2014/05/06 京东WBS对应 ob_卢 add end
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  // 用于判断csv文件是否有商品名称列
  boolean existsCommodityName = false;
  boolean existsCommodityNameEn = false;
  boolean existsCommodityNameJp = false;

  // 用于判断csv文件是否有代表SKU単価列
  boolean existsRepresentSkuUnitPrice = false;

  // 用于判断csv文件是否有大物フラグ列
  boolean existsBigflag = false;

  // 用于判断csv文件是否有商品説明2列
  boolean existsCommodityDescription2 = false;

  // 用于判断csv文件是否有返品不可フラグ列
  boolean existsReturnFlgCust = false;
  boolean existsReturnFlgSupp = false;
  boolean existsChangeFlgSupp = false;
  // 用于判断csv文件是否有品牌列
  boolean existsBrandCode = false;

  // 用于判断csv文件是否有ワーニング区分列
  boolean existsWarningFlag = false;

  // 用于判断csv文件是否有商品期限管理列
  boolean shelfLifeFlag = false;

  //用于判断csv文件是否有EC販売フラグ列
  boolean existsSaleFlgEc = false;
  
  // 用于判断csv文件是否有取引先コード列
  boolean existsSupplierCode = false;

  // 用于判断csv文件是否有仕入担当者コード列
  boolean existsbuyerCode = false;

  public boolean setSchema(List<String> csvLine) {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    for (String column : csvLine) {
      // ショップコード
      if (column.equals("shop_code")) {
        columns.add(new CsvColumnImpl("shop_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
        // 商品コード
      } else if (column.equals("commodity_code")) {
        columns.add(new CsvColumnImpl("commodity_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.1"), CsvDataType.STRING, false, false, true, null));
        // 商品名称
      } else if (column.equals("commodity_name")) {
        columns.add(new CsvColumnImpl("commodity_name", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.2"), CsvDataType.STRING, false, false, true, null));
        existsCommodityName = true;
        // 商品名称英字
      } else if (column.equals("commodity_name_en")) {
        columns.add(new CsvColumnImpl("commodity_name_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.3"), CsvDataType.STRING));
        existsCommodityNameEn = true;
        // 商品名称日文
      } else if (column.equals("commodity_name_jp")) {
        columns.add(new CsvColumnImpl("commodity_name_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.86"), CsvDataType.STRING));
        existsCommodityNameJp = true;
        // 代表SKUコード
      } else if (column.equals("represent_sku_code")) {
        columns.add(new CsvColumnImpl("represent_sku_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.4"), CsvDataType.STRING));
        // 代表SKU単価
      } else if (column.equals("represent_sku_unit_price")) {
        columns.add(new CsvColumnImpl("represent_sku_unit_price", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.5"), CsvDataType.BIGDECIMAL));
        existsRepresentSkuUnitPrice = true;
        // 商品説明1
      } else if (column.equals("commodity_description1")) {
        columns.add(new CsvColumnImpl("commodity_description1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.6"), CsvDataType.STRING));
        // 商品説明1英文
      } else if (column.equals("commodity_description1_en")) {
        columns.add(new CsvColumnImpl("commodity_description1_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.87"), CsvDataType.STRING));
        // 商品説明1日文
      } else if (column.equals("commodity_description1_jp")) {
        columns.add(new CsvColumnImpl("commodity_description1_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.88"), CsvDataType.STRING));
        // 商品説明2
      } else if (column.equals("commodity_description2")) {
        columns.add(new CsvColumnImpl("commodity_description2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.7"), CsvDataType.STRING));
        existsCommodityDescription2 = true;
        // 商品説明2英文
      } else if (column.equals("commodity_description2_en")) {
        columns.add(new CsvColumnImpl("commodity_description2_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.89"), CsvDataType.STRING));
        existsCommodityDescription2 = true;
        // 商品説明2日文
      } else if (column.equals("commodity_description2_jp")) {
        columns.add(new CsvColumnImpl("commodity_description2_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.90"), CsvDataType.STRING));
        existsCommodityDescription2 = true;
        // 商品説明3
      } else if (column.equals("commodity_description3")) {
        columns.add(new CsvColumnImpl("commodity_description3", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.8"), CsvDataType.STRING));
        // 商品説明3英文
      } else if (column.equals("commodity_description3_en")) {
        columns.add(new CsvColumnImpl("commodity_description3_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.91"), CsvDataType.STRING));
        // 商品説明3日文
      } else if (column.equals("commodity_description3_jp")) {
        columns.add(new CsvColumnImpl("commodity_description3_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.92"), CsvDataType.STRING));
        // 商品説明(一覧用）
      } else if (column.equals("commodity_description_short")) {
        columns.add(new CsvColumnImpl("commodity_description_short", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.9"), CsvDataType.STRING));
        // 商品説明(一覧用）英文
      } else if (column.equals("commodity_description_short_en")) {
        columns.add(new CsvColumnImpl("commodity_description_short_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.93"), CsvDataType.STRING));
        // 商品説明(一覧用）日文
      } else if (column.equals("commodity_description_short_jp")) {
        columns.add(new CsvColumnImpl("commodity_description_short_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.94"), CsvDataType.STRING));
        // 商品検索ワード
      } else if (column.equals("commodity_search_words")) {
        columns.add(new CsvColumnImpl("commodity_search_words",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.10"), CsvDataType.STRING));
        // 販売開始日時
      } else if (column.equals("sale_start_datetime")) {
        columns.add(new CsvColumnImpl("sale_start_datetime", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.11"), CsvDataType.STRING));
        // 販売終了日時
      } else if (column.equals("sale_end_datetime")) {
        columns.add(new CsvColumnImpl("sale_end_datetime", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.12"), CsvDataType.STRING));
        // 特別価格開始日時
      } else if (column.equals("discount_price_start_datetime")) {
        columns.add(new CsvColumnImpl("discount_price_start_datetime", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.13"), CsvDataType.STRING));
        // 特別価格終了日時
      } else if (column.equals("discount_price_end_datetime")) {
        columns.add(new CsvColumnImpl("discount_price_end_datetime", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.14"), CsvDataType.STRING));
        // 規格1名称ID(TMALLの属性ID）
      } else if (column.equals("standard1_id")) {
        columns.add(new CsvColumnImpl("standard1_id", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.15"), CsvDataType.STRING));
        // 規格1名称
      } else if (column.equals("standard1_name")) {
        columns.add(new CsvColumnImpl("standard1_name", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.16"), CsvDataType.STRING));
        // 規格1名称英文
      } else if (column.equals("standard1_name_en")) {
        columns.add(new CsvColumnImpl("standard1_name_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.95"), CsvDataType.STRING));
        // 規格1名称日文
      } else if (column.equals("standard1_name_jp")) {
        columns.add(new CsvColumnImpl("standard1_name_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.96"), CsvDataType.STRING));
        // 規格2名称ID(TMALLの属性ID）
      } else if (column.equals("standard2_id")) {
        columns.add(new CsvColumnImpl("standard2_id", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.17"), CsvDataType.STRING));
        // 規格2名称
      } else if (column.equals("standard2_name")) {
        columns.add(new CsvColumnImpl("standard2_name", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.18"), CsvDataType.STRING));
        // 規格2名称英文
      } else if (column.equals("standard2_name_en")) {
        columns.add(new CsvColumnImpl("standard2_name_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.97"), CsvDataType.STRING));
        // 規格2名称日文
      } else if (column.equals("standard2_name_jp")) {
        columns.add(new CsvColumnImpl("standard2_name_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.98"), CsvDataType.STRING));
        // EC販売フラグ
      } else if (column.equals("sale_flg_ec")) {
        columns.add(new CsvColumnImpl("sale_flg_ec", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.19"), CsvDataType.NUMBER));
        existsSaleFlgEc = true;
        // 返品不可フラグ
        //供货商换货
      } else if (column.equals("return_flg_cust")) {
        columns.add(new CsvColumnImpl("return_flg_cust", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.83"),CsvDataType.STRING));
        existsReturnFlgCust = true;
        //供货商退货
      } else if (column.equals("return_flg_supp")) {
        columns.add(new CsvColumnImpl("return_flg_supp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.84"),CsvDataType.STRING));
        existsReturnFlgSupp = true;
        //顾客退货
      } else if (column.equals("change_flg_supp")) {
        columns.add(new CsvColumnImpl("change_flg_supp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.85"),CsvDataType.STRING));
        existsChangeFlgSupp = true;
        // ワーニング区分
      } else if (column.equals("warning_flag")) {
        columns.add(new CsvColumnImpl("warning_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.22"), CsvDataType.STRING));
        existsWarningFlag = true;
        // リードタイム
      } else if (column.equals("lead_time")) {
        columns.add(new CsvColumnImpl("lead_time", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.23"), CsvDataType.NUMBER));
        // セール区分
      } else if (column.equals("sale_flag")) {
        columns.add(new CsvColumnImpl("sale_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.24"), CsvDataType.STRING));
        // 特集区分
      } else if (column.equals("spec_flag")) {
        columns.add(new CsvColumnImpl("spec_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.25"), CsvDataType.STRING));
        // ブランドコード
      } else if (column.equals("brand_code")) {
        columns.add(new CsvColumnImpl("brand_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.26"), CsvDataType.STRING));
        existsBrandCode = true;
        // TMALL商品ID（APIの戻り値）
      } else if (column.equals("tmall_commodity_id")) {
        columns.add(new CsvColumnImpl("tmall_commodity_id", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.27"), CsvDataType.NUMBER));
        // TMALL代表SKU単価
      } else if (column.equals("tmall_represent_sku_price")) {
        columns.add(new CsvColumnImpl("tmall_represent_sku_price", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.28"), CsvDataType.BIGDECIMAL));
        // TMALLのカテゴリID
      } else if (column.equals("tmall_category_id")) {
        columns.add(new CsvColumnImpl("tmall_category_id", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.29"), CsvDataType.NUMBER));
        // 取引先コード
      } else if (column.equals("supplier_code")) {
        columns.add(new CsvColumnImpl("supplier_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.30"), CsvDataType.STRING, false, false, true, null));
        existsSupplierCode = false;
        // 仕入担当者コード
      } else if (column.equals("buyer_code")) {
        columns.add(new CsvColumnImpl("buyer_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.31"), CsvDataType.STRING));
        existsbuyerCode = true;
        // 産地
      } else if (column.equals("original_code")) {
        columns.add(new CsvColumnImpl("original_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.33"), CsvDataType.STRING));
       // 成分1
      } else if (column.equals("ingredient_name1")) {
        columns.add(new CsvColumnImpl("ingredient_name1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.34"), CsvDataType.STRING));
        // 成分量1
      } else if (column.equals("ingredient_val1")) {
        columns.add(new CsvColumnImpl("ingredient_val1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.35"), CsvDataType.STRING));
        // 成分2
      } else if (column.equals("ingredient_name2")) {
        columns.add(new CsvColumnImpl("ingredient_name2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.36"), CsvDataType.STRING));
        // 成分量2
      } else if (column.equals("ingredient_val2")) {
        columns.add(new CsvColumnImpl("ingredient_val2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.37"), CsvDataType.STRING));
        // 成分3
      } else if (column.equals("ingredient_name3")) {
        columns.add(new CsvColumnImpl("ingredient_name3", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.38"), CsvDataType.STRING));
        // 成分量3
      } else if (column.equals("ingredient_val3")) {
        columns.add(new CsvColumnImpl("ingredient_val3", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.39"), CsvDataType.STRING));
        // 成分4
      } else if (column.equals("ingredient_name4")) {
        columns.add(new CsvColumnImpl("ingredient_name4", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.40"), CsvDataType.STRING));
        // 成分量4
      } else if (column.equals("ingredient_val4")) {
        columns.add(new CsvColumnImpl("ingredient_val4", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.41"), CsvDataType.STRING));
        // 成分5
      } else if (column.equals("ingredient_name5")) {
        columns.add(new CsvColumnImpl("ingredient_name5", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.42"), CsvDataType.STRING));
        // 成分量5
      } else if (column.equals("ingredient_val5")) {
        columns.add(new CsvColumnImpl("ingredient_val5", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.43"), CsvDataType.STRING));
        // 成分6
      } else if (column.equals("ingredient_name6")) {
        columns.add(new CsvColumnImpl("ingredient_name6", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.44"), CsvDataType.STRING));
        // 成分量6
      } else if (column.equals("ingredient_val6")) {
        columns.add(new CsvColumnImpl("ingredient_val6", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.45"), CsvDataType.STRING));
        // 成分7
      } else if (column.equals("ingredient_name7")) {
        columns.add(new CsvColumnImpl("ingredient_name7", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.46"), CsvDataType.STRING));
        // 成分量7
      } else if (column.equals("ingredient_val7")) {
        columns.add(new CsvColumnImpl("ingredient_val7", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.47"), CsvDataType.STRING));
        // 成分8
      } else if (column.equals("ingredient_name8")) {
        columns.add(new CsvColumnImpl("ingredient_name8", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.48"), CsvDataType.STRING));
        // 成分量8
      } else if (column.equals("ingredient_val8")) {
        columns.add(new CsvColumnImpl("ingredient_val8", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.49"), CsvDataType.STRING));
        // 成分9
      } else if (column.equals("ingredient_name9")) {
        columns.add(new CsvColumnImpl("ingredient_name9", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.50"), CsvDataType.STRING));
        // 成分量9
      } else if (column.equals("ingredient_val9")) {
        columns.add(new CsvColumnImpl("ingredient_val9", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.51"), CsvDataType.STRING));
        // 成分10
      } else if (column.equals("ingredient_name10")) {
        columns.add(new CsvColumnImpl("ingredient_name10", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.52"), CsvDataType.STRING));
        // 成分量10
      } else if (column.equals("ingredient_val10")) {
        columns.add(new CsvColumnImpl("ingredient_val10", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.53"), CsvDataType.STRING));
        // 成分11
      } else if (column.equals("ingredient_name11")) {
        columns.add(new CsvColumnImpl("ingredient_name11", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.54"), CsvDataType.STRING));
        // 成分量11
      } else if (column.equals("ingredient_val11")) {
        columns.add(new CsvColumnImpl("ingredient_val11", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.55"), CsvDataType.STRING));
        // 成分12
      } else if (column.equals("ingredient_name12")) {
        columns.add(new CsvColumnImpl("ingredient_name12", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.56"), CsvDataType.STRING));
        // 成分量12
      } else if (column.equals("ingredient_val12")) {
        columns.add(new CsvColumnImpl("ingredient_val12", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.57"), CsvDataType.STRING));
        // 成分13
      } else if (column.equals("ingredient_name13")) {
        columns.add(new CsvColumnImpl("ingredient_name13", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.58"), CsvDataType.STRING));
        // 成分量13
      } else if (column.equals("ingredient_val13")) {
        columns.add(new CsvColumnImpl("ingredient_val13", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.59"), CsvDataType.STRING));
        // 成分14
      } else if (column.equals("ingredient_name14")) {
        columns.add(new CsvColumnImpl("ingredient_name14", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.60"), CsvDataType.STRING));
        // 成分量14
      } else if (column.equals("ingredient_val14")) {
        columns.add(new CsvColumnImpl("ingredient_val14", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.61"), CsvDataType.STRING));
        // 成分15
      } else if (column.equals("ingredient_name15")) {
        columns.add(new CsvColumnImpl("ingredient_name15", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.62"), CsvDataType.STRING));
        // 成分量15
      } else if (column.equals("ingredient_val15")) {
        columns.add(new CsvColumnImpl("ingredient_val15", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.63"), CsvDataType.STRING));
        // 原材料1
      } else if (column.equals("material1")) {
        columns.add(new CsvColumnImpl("material1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.64"), CsvDataType.STRING));
        // 原材料2
      } else if (column.equals("material2")) {
        columns.add(new CsvColumnImpl("material2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.65"), CsvDataType.STRING));
        // 原材料3
      } else if (column.equals("material3")) {
        columns.add(new CsvColumnImpl("material3", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.66"), CsvDataType.STRING));
        // 原材料4
      } else if (column.equals("material4")) {
        columns.add(new CsvColumnImpl("material4", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.67"), CsvDataType.STRING));
        // 原材料5
      } else if (column.equals("material5")) {
        columns.add(new CsvColumnImpl("material5", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.68"), CsvDataType.STRING));
        // 原材料6
      } else if (column.equals("material6")) {
        columns.add(new CsvColumnImpl("material6", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.69"), CsvDataType.STRING));
        // 原材料7
      } else if (column.equals("material7")) {
        columns.add(new CsvColumnImpl("material7", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.70"), CsvDataType.STRING));
        // 原材料8
      } else if (column.equals("material8")) {
        columns.add(new CsvColumnImpl("material8", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.71"), CsvDataType.STRING));
        // 原材料9
      } else if (column.equals("material9")) {
        columns.add(new CsvColumnImpl("material9", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.72"), CsvDataType.STRING));
        // 原材料10
      } else if (column.equals("material10")) {
        columns.add(new CsvColumnImpl("material10", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.73"), CsvDataType.STRING));
        // 原材料11
      } else if (column.equals("material11")) {
        columns.add(new CsvColumnImpl("material11", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.74"), CsvDataType.STRING));
        // 原材料12
      } else if (column.equals("material12")) {
        columns.add(new CsvColumnImpl("material12",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.75"), CsvDataType.STRING));
        // 原材料13
      } else if (column.equals("material13")) {
        columns.add(new CsvColumnImpl("material13", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.76"), CsvDataType.STRING));
        // 原材料14
      } else if (column.equals("material14")) {
        columns.add(new CsvColumnImpl("material14", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.77"), CsvDataType.STRING));
        // 原材料15
      } else if (column.equals("material15")) {
        columns.add(new CsvColumnImpl("material15", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.78"), CsvDataType.STRING));
        // 商品期限管理フラグ 0管理しない/1賞味期限日/2製造日＋保管日数
      } else if (column.equals("shelf_life_flag")) {
        columns.add(new CsvColumnImpl("shelf_life_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.79"), CsvDataType.NUMBER));
        shelfLifeFlag = true;
        // 保管日数
      } else if (column.equals("shelf_life_days")) {
        columns.add(new CsvColumnImpl("shelf_life_days", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.80"), CsvDataType.NUMBER));
        // 大物フラグ
      } else if (column.equals("big_flag")) {
        columns.add(new CsvColumnImpl("big_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.82"), CsvDataType.NUMBER));
        existsBigflag = true;
      } else if (column.equals("food_security_prd_license_no")) { //生产许可证号
        columns.add(new CsvColumnImpl("food_security_prd_license_no", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.104"), CsvDataType.STRING));
      } else if (column.equals("food_security_design_code")) { //产品标准号
        columns.add(new CsvColumnImpl("food_security_design_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.105"), CsvDataType.STRING));
      } else if (column.equals("food_security_factory")) { //厂名
        columns.add(new CsvColumnImpl("food_security_factory", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.106"), CsvDataType.STRING));
      } else if (column.equals("food_security_factory_site")) { //厂址
        columns.add(new CsvColumnImpl("food_security_factory_site", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.107"), CsvDataType.STRING));
      } else if (column.equals("food_security_contact")) { //厂家联系方式
        columns.add(new CsvColumnImpl("food_security_contact", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.108"), CsvDataType.STRING));
      } else if (column.equals("food_security_mix")) { //配料表
        columns.add(new CsvColumnImpl("food_security_mix", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.109"), CsvDataType.STRING));
      } else if (column.equals("food_security_plan_storage")) { //储藏方法
        columns.add(new CsvColumnImpl("food_security_plan_storage", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.110"), CsvDataType.STRING));
      } else if (column.equals("food_security_period")) { //保质期
        columns.add(new CsvColumnImpl("food_security_period", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.111"), CsvDataType.STRING));
      } else if (column.equals("food_security_food_additive")) { //食品添加剂
        columns.add(new CsvColumnImpl("food_security_food_additive", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.112"), CsvDataType.STRING));
      } else if (column.equals("food_security_supplier")) { //供货商
        columns.add(new CsvColumnImpl("food_security_supplier", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.113"), CsvDataType.STRING));
      } else if (column.equals("food_security_product_date_start")) { //生产开始日期
        columns.add(new CsvColumnImpl("food_security_product_date_start", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.114"), CsvDataType.STRING));
      } else if (column.equals("food_security_product_date_end")) { //生产结束日期
        columns.add(new CsvColumnImpl("food_security_product_date_end", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.115"), CsvDataType.STRING));
      } else if (column.equals("food_security_stock_date_start")) { //进货开始日期
        columns.add(new CsvColumnImpl("food_security_stock_date_start", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.116"), CsvDataType.STRING));
      } else if (column.equals("food_security_stock_date_end")) { //进货结束日期
        columns.add(new CsvColumnImpl("food_security_stock_date_end", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.117"), CsvDataType.STRING));
      } else if (column.equals("in_bound_life_days")) { //入库效期
        columns.add(new CsvColumnImpl("in_bound_life_days", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.121"), CsvDataType.NUMBER));
      } else if (column.equals("out_bound_life_days")) { //出库效期
        columns.add(new CsvColumnImpl("out_bound_life_days", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.122"), CsvDataType.NUMBER));
      } else if (column.equals("shelf_life_alert_days")) { //失效预警
        columns.add(new CsvColumnImpl("shelf_life_alert_days", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.123"), CsvDataType.NUMBER));
      } else if (column.equals("tmall_mjs_flg")) { // tmall满就送(赠品标志)0:非赠品 1:赠品
        columns.add(new CsvColumnImpl("tmall_mjs_flg", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.124"), CsvDataType.NUMBER));
      } else if (column.equals("import_commodity_type")) { // 进口商品区分(1:特别推荐、2:全进口、3:海外品品牌、4:普通国产)
        columns.add(new CsvColumnImpl("import_commodity_type", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.125"), CsvDataType.NUMBER));
      } else if (column.equals("clear_commodity_type")) { //  清仓商品区分(1:清仓商品、2:普通商品)
        columns.add(new CsvColumnImpl("clear_commodity_type", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.126"), CsvDataType.NUMBER));
      } else if (column.equals("reserve_commodity_type1")) { // Asahi商品区分  1:是 9:默认不是
        columns.add(new CsvColumnImpl("reserve_commodity_type1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.127"), CsvDataType.NUMBER));
      } else if (column.equals("reserve_commodity_type2")) { // hot商品区分
        columns.add(new CsvColumnImpl("reserve_commodity_type2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.128"), CsvDataType.NUMBER));
      } else if (column.equals("reserve_commodity_type3")) { // 商品展示区分
        columns.add(new CsvColumnImpl("reserve_commodity_type3", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.129"), CsvDataType.NUMBER));
      } else if (column.equals("new_reserve_commodity_type1")) { // 预留区分1*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type1", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.130"), CsvDataType.NUMBER));
      } else if (column.equals("new_reserve_commodity_type2")) { // 预留区分2*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type2", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.131"), CsvDataType.NUMBER));
      } else if (column.equals("new_reserve_commodity_type3")) { // 预留区分3*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type3", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.132"), CsvDataType.NUMBER));
      } else if (column.equals("new_reserve_commodity_type4")) { // 预留区分4*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type4", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.133"), CsvDataType.NUMBER));
      } else if (column.equals("new_reserve_commodity_type5")) { // 预留区分5*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type5", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.134"), CsvDataType.NUMBER));
      } else if (column.equals("tmall_commodity_search_words")) { // TMALL检索关键字
        columns.add(new CsvColumnImpl("tmall_commodity_search_words", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.135"), CsvDataType.STRING));
      } else if (column.equals("hot_flg_en")) { // English热卖标志
        columns.add(new CsvColumnImpl("hot_flg_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.136"), CsvDataType.NUMBER));
      } else if (column.equals("hot_flg_jp")) { // Japan热卖标志
        columns.add(new CsvColumnImpl("hot_flg_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.137"), CsvDataType.NUMBER));
        // 20130809 txw add start
      } else if (column.equals("title")) { // TITLE
        columns.add(new CsvColumnImpl("title", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.138"), CsvDataType.STRING));
      } else if (column.equals("title_en")) { // TITLE(英文)
        columns.add(new CsvColumnImpl("title_en",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.139"), CsvDataType.STRING));
      } else if (column.equals("title_jp")) { // TITLE(日文)
        columns.add(new CsvColumnImpl("title_jp",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.140"), CsvDataType.STRING));
      } else if (column.equals("description")) { // description
        columns.add(new CsvColumnImpl("description",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.141"), CsvDataType.STRING));
      } else if (column.equals("description_en")) { // description(英文)
        columns.add(new CsvColumnImpl("description_en",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.142"), CsvDataType.STRING));
      } else if (column.equals("description_jp")) { // description(日文)
        columns.add(new CsvColumnImpl("description_jp",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.143"), CsvDataType.STRING));
      } else if (column.equals("keyword")) { // keyword
        columns.add(new CsvColumnImpl("keyword",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.144"), CsvDataType.STRING));
      } else if (column.equals("keyword_en")) { // keyword(英文)
        columns.add(new CsvColumnImpl("keyword_en",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.145"), CsvDataType.STRING));
      } else if (column.equals("keyword_jp")) { // keyword(日文)
        columns.add(new CsvColumnImpl("keyword_jp",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.146"), CsvDataType.STRING));
      } else if (column.equals("original_commodity_code")) { // 原商品编号
        columns.add(new CsvColumnImpl("original_commodity_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.147"), CsvDataType.STRING));
      } else if (column.equals("combination_amount")) { // 组合数
        columns.add(new CsvColumnImpl("combination_amount", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.148"), CsvDataType.NUMBER));
      } else if (column.equals("keyword_cn2")) { // 検索Keyword中文
        columns.add(new CsvColumnImpl("keyword_cn2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.149"), CsvDataType.STRING));
      } else if (column.equals("keyword_jp2")) { // 検索Keyword日文
        columns.add(new CsvColumnImpl("keyword_jp2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.150"), CsvDataType.STRING));
      } else if (column.equals("keyword_en2")) { // 検索Keyword英文
        columns.add(new CsvColumnImpl("keyword_en2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.151"), CsvDataType.STRING));
      }
      // 20130809 txw add end
      // 2014/04/28 京东WBS对应 ob_卢 add start
      else if (column.equals("advert_content")) { // 京东广告词
        columns.add(new CsvColumnImpl("advert_content", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.152"), CsvDataType.STRING));
      }
      // 2014/04/28 京东WBS对应 ob_卢 add end
      
    }
    getSchema().setColumns(columns);
    return true;
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      header = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CCommodityHeaderImport.class);
      
      if (header.getSaleStartDatetimeStr() != null && !DateUtil.isCorrect(header.getSaleStartDatetimeStr().replace("'", "").replaceAll("-", "/"))) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.DATETIMEERR, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.11"))));
        return summary;
      }
      if (header.getSaleEndDatetimeStr() != null && !DateUtil.isCorrect(header.getSaleEndDatetimeStr().replace("'", "").replaceAll("-", "/"))) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.DATETIMEERR, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.12"))));
        return summary;
      }
      if (header.getDiscountPriceStartDatetimeStr() != null && !DateUtil.isCorrect(header.getDiscountPriceStartDatetimeStr().replace("'", "").replaceAll("-", "/"))) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.DATETIMEERR, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.13"))));
        return summary;
      }
      if (header.getDiscountPriceEndDatetimeStr() != null && !DateUtil.isCorrect(header.getDiscountPriceEndDatetimeStr().replace("'", "").replaceAll("-", "/"))) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.DATETIMEERR, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.14"))));
        return summary;
      }
      // 商品编号必须输入
      if (header.getCommodityCode() == null) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.1"))));
        return summary;
      }
      header.setCommodityCode(header.getCommodityCode().replace("'", ""));
      if(!StringUtil.isNullOrEmpty(header.getOriginalCommodityCode())){
        header.setOriginalCommodityCode(header.getOriginalCommodityCode().replace("'", ""));
      }
      if(!StringUtil.isNullOrEmpty(header.getRepresentSkuCode())){
        header.setRepresentSkuCode(header.getRepresentSkuCode().replace("'", ""));
      }
      existsHeader = exists(selectHeaderStatement, header.getCommodityCode());
      if (!existsHeader) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.COMMODITY_CODE_NOT_EXIST, header.getCommodityCode())));
        return summary;
      } else {
        // 商品存在取出返品不可フラグ的值
        SimpleQuery returnFlgExistsQuery = new SimpleQuery("SELECT RETURN_FLG FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE = ? ");
        returnFlgExistsQuery.setParameters(header.getCommodityCode());
        Object object = executeScalar(returnFlgExistsQuery);
        if (object != null) {
          header.setReturnFlg(Long.parseLong(object.toString()));
        }
      }
      if(header.getInBoundLifeDays() != null){
        if(header.getInBoundLifeDays() > 9999){
          summary.getErrors().add(new ValidationResult(null, null, "入库效期值不能大于9999"));
        }
      }
      if(header.getOutBoundLifeDays() != null){
        if(header.getOutBoundLifeDays() > 9999){
          summary.getErrors().add(new ValidationResult(null, null, "出库效期值不能大于9999"));
        }
      }
      if(header.getShelfLifeAlertDays() != null){
        if(header.getShelfLifeAlertDays() > 9999){
          summary.getErrors().add(new ValidationResult(null, null, "失效预警值不能大于9999"));
        }
      }
      if(header.getTmallMjsFlg() != null){
        if(header.getTmallMjsFlg() != 0 && header.getTmallMjsFlg() != 1){
          summary.getErrors().add(new ValidationResult(null, null, "tmall满就送(赠品标志)设定错误"));
        }
      }
      if(header.getImportCommodityType() != null){
        if(header.getImportCommodityType() != 1 && header.getImportCommodityType() != 2 &&  header.getImportCommodityType() != 9){
          summary.getErrors().add(new ValidationResult(null, null, "进口商品区分(只能为1:全进口、2:海外品品牌、9:普通国产)"));
        }
      }
      if(header.getImportCommodityType() == null){
        header.setImportCommodityType(Long.valueOf("9"));
      }
      if(header.getClearCommodityType() != null){
        if(header.getClearCommodityType() != 1 &&  header.getClearCommodityType() != 9){
          summary.getErrors().add(new ValidationResult(null, null, "清仓商品区分(只能为1:清仓商品、9:普通商品)"));
        }
      }
      if(header.getClearCommodityType() == null ){
        header.setClearCommodityType(Long.valueOf("9"));
      }
      if(header.getReserveCommodityType1() != null){
        if(header.getReserveCommodityType1() != 1 &&  header.getReserveCommodityType1() != 9){
          summary.getErrors().add(new ValidationResult(null, null, "Asahi商品区分(只能为1:是、9:默认不是)"));
        }
      }
      if(header.getReserveCommodityType1() == null ){
        header.setReserveCommodityType1(Long.valueOf("9"));
      }
      if(header.getReserveCommodityType2() != null){
          if(header.getReserveCommodityType2() != 1 &&  header.getReserveCommodityType2() != 9){
            summary.getErrors().add(new ValidationResult(null, null, "hot商品区分(只能为1:是、9:默认不是)"));
          }
        }
      if(header.getReserveCommodityType2() == null ){
          header.setReserveCommodityType2(Long.valueOf("9"));
      }
      if(header.getReserveCommodityType3() != null){
            if(header.getReserveCommodityType3() != 1 &&  header.getReserveCommodityType3() != 9){
              summary.getErrors().add(new ValidationResult(null, null, "商品展示区分(只能为1:是、9:默认不是)"));
            }
      }
      if(header.getReserveCommodityType3() == null ){
            header.setReserveCommodityType3(Long.valueOf("9"));
      }
      if(header.getNewReserveCommodityType1() != null){
          if(header.getNewReserveCommodityType1() != 1 &&  header.getNewReserveCommodityType1() != 9){
            summary.getErrors().add(new ValidationResult(null, null, "预留区分1*(只能为1:是、9:默认不是)"));
          }
      }
      if(header.getNewReserveCommodityType1() == null ){
          header.setNewReserveCommodityType1(Long.valueOf("9"));
      }
      if(header.getNewReserveCommodityType2() != null){
          if(header.getNewReserveCommodityType2() != 1 &&  header.getNewReserveCommodityType2() != 9){
            summary.getErrors().add(new ValidationResult(null, null, "预留区分2*(只能为1:是、9:默认不是)"));
          }
      }
      if(header.getNewReserveCommodityType2() == null ){
          header.setNewReserveCommodityType2(Long.valueOf("9"));
      }
      if(header.getNewReserveCommodityType3() != null){
          if(header.getNewReserveCommodityType3() != 1 &&  header.getNewReserveCommodityType3() != 9){
            summary.getErrors().add(new ValidationResult(null, null, "预留区分3*(只能为1:是、9:默认不是)"));
          }
      }
      if(header.getNewReserveCommodityType3() == null ){
          header.setNewReserveCommodityType3(Long.valueOf("9"));
      }
      if(header.getNewReserveCommodityType4() != null){
          if(header.getNewReserveCommodityType4() != 1 &&  header.getNewReserveCommodityType4() != 9){
            summary.getErrors().add(new ValidationResult(null, null, "预留区分4*(只能为1:是、9:默认不是)"));
          }
      }
      if(header.getNewReserveCommodityType4() == null ){
          header.setNewReserveCommodityType4(Long.valueOf("9"));
      }
      if(header.getNewReserveCommodityType5() != null){
          if(header.getNewReserveCommodityType5() != 1 &&  header.getNewReserveCommodityType5() != 9){
            summary.getErrors().add(new ValidationResult(null, null, "预留区分5*(只能为1:是、9:默认不是)"));
          }
      }
      if(header.getNewReserveCommodityType5() == null ){
          header.setNewReserveCommodityType5(Long.valueOf("9"));
      }    
      if(!StringUtil.isNullOrEmpty(header.getOriginalCode())){
          Query query = new SimpleQuery("select * from original_place where original_code=?", 
          		header.getOriginalCode());
          OriginalPlace op = DatabaseUtil.loadAsBean(query, OriginalPlace.class);
          if(StringUtil.isNullOrEmpty(op)){
          	summary.getErrors().add(new ValidationResult(null, null, "输入的产地CODE不存在"));
              return summary;
          }
      } 
      
      SimpleQuery query = new SimpleQuery("SELECT ORIGINAL_COMMODITY_CODE , COMBINATION_AMOUNT  FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE = ? ");
      query.setParameters(header.getCommodityCode());
      CCommodityHeader commodity = loadAsBean(query,CCommodityHeader.class);
      if (StringUtil.hasValue(commodity.getOriginalCommodityCode())){//导入组合品
        if (StringUtil.hasValue(header.getOriginalCommodityCode()) && header.getCombinationAmount() != null){
          if (!header.getOriginalCommodityCode().equals(commodity.getOriginalCommodityCode().replace("'", "")) 
              || !header.getCombinationAmount().equals(commodity.getCombinationAmount())){
            summary.getErrors().add(new ValidationResult(null, null, "组合品导入时，原商品编号和组合数必须一致"));
            return summary;
          }
        } else {
          summary.getErrors().add(new ValidationResult(null, null, "组合品导入时，原商品编号和组合数不能为空"));
          return summary;
        }
      } else {//导入普通品
        if ( StringUtil.hasValue(header.getOriginalCommodityCode()) || header.getCombinationAmount() != null) {
          summary.getErrors().add(new ValidationResult(null, null, "普通商品导入时，原商品编号和组合数必须为空"));
          return summary;
        }
      }
      
    } catch (CsvImportException e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    
    

    List<String> errorMessageList = new ArrayList<String>();
    //判断必须项是否为空
    checkExist(header,errorMessageList);
    
    // 10.1.1 10006 追加 ここから
    // 各期間の日付範囲チェック
    checkDateRange(header, errorMessageList);
    // 10.1.1 10006 追加 ここまで

    // 各期間の開始/終了日時の順序チェック
    checkTermsRules(header, errorMessageList);

    checkSalesTermAndDiscountRules(header, errorMessageList);
    for (String error : errorMessageList) {
      summary.getErrors().add(new ValidationResult(null, null, error));
    }
    // 商品期限管理フラグは2の場合保管日数必須
    checkShelfLife(summary);

    //成对检查
    pairCheck(summary);
    
    if (summary.hasError()) {
      return summary;
    }
    
    // 代表SKUに指定したコードが、別の商品コードに紐付くSKUとして登録されていないかをチェック

    SimpleQuery skuExistsQuery = new SimpleQuery("SELECT COMMODITY_CODE FROM C_COMMODITY_DETAIL WHERE SKU_CODE = ? ");
    skuExistsQuery.setParameters(header.getRepresentSkuCode());

    Object object = executeScalar(skuExistsQuery);
    if (object != null) {
      String commodityCode = object.toString();
      if (!commodityCode.equals(header.getCommodityCode())) {
        summary.getErrors().add(new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.1")));
      }
    }
    if(existsBrandCode){
      // 判断品牌编号是否存在
      SimpleQuery brandExistsQuery = new SimpleQuery("SELECT BRAND_CODE FROM BRAND WHERE  BRAND_CODE = ?");
      brandExistsQuery.setParameters(header.getBrandCode());
      object = executeScalar(brandExistsQuery);
      if (object == null) {
        summary.getErrors().add(new ValidationResult(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.62"), null, Message.get(CsvMessage.NOT_EXIST, header.getBrandCode())));
      }
    }
    
    // 20150120 hdh add start 特定用户才能更新商品有效期
    boolean shelfLifeUpdateFlag = false; 
    WebShopSpecialConfig speiConfig = DIContainer.getWebShopSpecialConfig();
    if(speiConfig.getShelfLifeUserList()!=null && speiConfig.getShelfLifeUserList().size()>0){
      for(String loginId:speiConfig.getShelfLifeUserList()){
        if(StringUtil.isNull(loginId)){
          continue;
        }
        if(loginId.equals(getCondition().getLoginInfo().getLoginId())){
          shelfLifeUpdateFlag = true;
          break;
        }
      }
    }
    if(!shelfLifeUpdateFlag){
      if(StringUtil.hasValue(header.getCommodityCode())){
        //查询原先的值填充
        SimpleQuery shelfLifQuery = new SimpleQuery(" SELECT * FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE = ? ");
        shelfLifQuery.setParameters(header.getCommodityCode());
        CCommodityHeader oldHeader = loadAsBean(shelfLifQuery,CCommodityHeader.class);
        if(oldHeader !=null){
          header.setShelfLifeFlag(oldHeader.getShelfLifeFlag());
          header.setShelfLifeDays(oldHeader.getShelfLifeDays());
        }
      }
    }
    // 20150120 hdh add end
    
    return summary;
  }

@Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    try {

      // 商品基本表修改
      int updHeaderCount = executeUpdateHeader(existsHeader);

      if (updHeaderCount != 1) {
        throw new CsvImportException();
      }
      
      // 20130703 shen add start
      // 同期标志更新
      int updateSyncFlagCount = executeUpdateSyncFlag();
      if (updateSyncFlagCount != 1) {
        throw new CsvImportException();
      }
      // 20130703 shen add end
      
   // 2014/05/06 京东WBS对应 ob_卢 add start(delete by zhangfeng on 2014/7/23)
      // 京东同期标志更新
      // 京东同期标志为不同期时，不更新；
//      if(SyncFlagJd.SYNCVISIBLE.getValue().equals(getCondition().getSyncFlagJd())){
//        // 京东同期表示为同期 而且 商品Header.JD商品ID！=null 
//        // 而且 CSV导入后商品Detail.JD使用标志为1：使用时，更新为1：待同期；反之，不更新
//        CCommodityHeaderDao cHeaderDao = DIContainer.getDao(CCommodityHeaderDao.class);
//        CCommodityDetailDao cDetailDao = DIContainer.getDao(CCommodityDetailDao.class);
//
//        CCommodityHeader cHeader = cHeaderDao.load(DIContainer.getWebshopConfig().getSiteShopCode(), header.getCommodityCode());
//        if(cHeader == null){
//          throw new CsvImportException();
//        }
//        CCommodityDetail cDetail = cDetailDao.load(DIContainer.getWebshopConfig().getSiteShopCode(), cHeader.getCommodityCode());
//        if(cDetail == null){
//          throw new CsvImportException();
//        }
//        if(cHeader.getJdCommodityId() != null && JdUseFlg.ENABLED.longValue().equals(cDetail.getJdUseFlg())){
//          int updateSyncFlagJdCount = executeUpdateSyncFlagJd();
//          
//          if (updateSyncFlagJdCount != 1) {
//            throw new CsvImportException();
//          }
//        }
//      }

      // 2014/05/06 京东WBS对应 ob_卢 add end(delete by zhangfeng on 2014/7/23)

    int updateSyncFlagJdCount = executeUpdateSyncFlagJd();
    if (updateSyncFlagJdCount != 1) {
      throw new CsvImportException();
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

  /**
   * 判断必须项是否为空
   */
  private void checkExist(CCommodityHeaderImport bean, List<String> errorMessageList) {
    // 判断CSV文件中商品名称是否存在
    if (existsCommodityName) {
      // 商品名称必须输入
      if (StringUtil.isNullOrEmpty(header.getCommodityName())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.2")));
      } else {
        // 商品名称特殊字符转换：（单引号变换为）、（双引号变换为）、（百分号变换为）、（逗号变换为）
        // 重新赋值
        header.setCommodityName(StringUtil.parse(header.getCommodityName()));
      }
    }  
    // 判断CSV文件中商品名称是否存在（英文）
    if (existsCommodityNameEn) {
      // 商品名称必须输入（英文）
      if (StringUtil.isNullOrEmpty(header.getCommodityNameEn())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.3")));
      } else {
        // 商品名称特殊字符转换：（单引号变换为）、（双引号变换为）、（百分号变换为）、（逗号变换为）
        // 重新赋值
        header.setCommodityNameEn(StringUtil.parse(header.getCommodityNameEn()));
      }
    }   
    // 判断CSV文件中商品名称是否存在（日文）
    if (existsCommodityNameJp) {
      // 商品名称必须输入
      if (StringUtil.isNullOrEmpty(header.getCommodityNameJp())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.86")));
      } else {
        // 商品名称特殊字符转换：（单引号变换为）、（双引号变换为）、（百分号变换为）、（逗号变换为）
        // 重新赋值
        header.setCommodityNameJp(StringUtil.parse(header.getCommodityNameJp()));
      }
    }
    // 判断CSV文件中代表SKU単価列是否存在
    if (existsRepresentSkuUnitPrice) {
      // 存在则必须输入
      if (header.getRepresentSkuUnitPrice() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.5")));
      }
    }
    // 判断CSV文件中商品说明2是否存在
    if (existsCommodityDescription2) {
      // 存在则必须输入
      if (header.getCommodityDescription2() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.7")));
        // 商品说明2长度必须大于5
      } else if (header.getCommodityDescription2().length() < 5) {
        errorMessageList.add(Message.get(CsvMessage.COMMODITY_DESCRIPTION2_LENGTH_REQUEST_GREATER, header.getCommodityCode()));
      }
    }
    // 将返品不可フラグ的值转换成二进制
    String returnFlg = Integer.toBinaryString(header.getReturnFlg().intValue());
    // 顾客退货
    String returnFlgCust;
    // 供货商退货
    String returnFlgSupp;
    // 供货商换货
    String changeFlgSupp;
    if (returnFlg.length() < 2) {
      changeFlgSupp = "0";
      returnFlgSupp = "0";
      returnFlgCust = returnFlg;
    } else if (returnFlg.length() == 2) {
      char str[] = returnFlg.toCharArray();
      changeFlgSupp = "0";
      returnFlgSupp = "" + str[0];
      returnFlgCust = "" + str[1];
    } else {
      char str[] = returnFlg.toCharArray();
      changeFlgSupp = "" + str[0];
      returnFlgSupp = "" + str[1];
      returnFlgCust = "" + str[2];
    }
    // 判断CSV文件中供货商换货列是否存在
    if (existsReturnFlgCust) {
      // 存在必须输入
      if (StringUtil.isNullOrEmpty(header.getReturnFlgCust())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.83")));
        // 供货商换货必须为1或0
      } else if (!header.getReturnFlgCust().equals("1") && !header.getReturnFlgCust().equals("0")) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.83"), "0", "1"));
      } else if (header.getReturnFlgCust().equals("1") || header.getReturnFlgCust().equals("0")) {
        returnFlgCust = header.getReturnFlgCust();
      }
    }
    // 判断CSV文件中供货商退货列是否存在
    if (existsReturnFlgSupp) {
      // 存在必须输入
      if (StringUtil.isNullOrEmpty(header.getReturnFlgSupp())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.84")));
        // 供货商退货必须为1或0
      } else if (!header.getReturnFlgSupp().equals("1") && !header.getReturnFlgSupp().equals("0")) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.84"), "0", "1"));
      } else if (header.getReturnFlgSupp().equals("1") || header.getReturnFlgSupp().equals("0")) {
        returnFlgSupp = header.getReturnFlgSupp();
      }
    }
    // 判断CSV文件中顾客退货列是否存在
    if (existsChangeFlgSupp) {
      // 存在必须输入
      if (StringUtil.isNullOrEmpty(header.getChangeFlgSupp())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.85")));
        // 顾客退货必须为1或0
      } else if (!header.getChangeFlgSupp().equals("1") && !header.getChangeFlgSupp().equals("0")) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.85"), "0", "1"));
      } else if (header.getChangeFlgSupp().equals("1") || header.getChangeFlgSupp().equals("0")) {
        changeFlgSupp = header.getChangeFlgSupp();
      }
    }
    // 将供货商换货、供货商退货、顾客退货拼接成一个三位的二进制数
    returnFlg = changeFlgSupp + returnFlgSupp + returnFlgCust   ;
    // 将二进制数转换成十进制的数赋给返品不可フラグ
    header.setReturnFlg(Integer.valueOf(returnFlg, 2).longValue());
//    if (existsReturnFlg) {
//      // 存在必须输入
//      if (header.getReturnFlg() == null) {
//        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.21")));
//        // 返品不可フラグ必须为1或0
//      } else if (header.getReturnFlg() != 1 && header.getReturnFlg() != 0) {
//        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.21"), "0", "1"));
//      }
//    }
    // 判断CSV文件中品牌是否存在
    if (existsBrandCode) {
      // 存在必须输入
      if (header.getBrandCode() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.26")));
      }
    }
    // CSV文件存在大物フラグ列
    if (existsBigflag) {
      // 存在列，则不能为空
      if (header.getBigFlag() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.82")));
      }
      // 必须等于0或1
      if (header.getBigFlag() != 1 && header.getBigFlag() != 0) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.82"), "0", "1"));
      }
    }
    // CSV文件存在商品期限管理列
    if(shelfLifeFlag){
      if(header.getShelfLifeFlag()==null){
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.79")));
      }else {
        if(header.getShelfLifeFlag()!=0&&header.getShelfLifeFlag()!=1&&header.getShelfLifeFlag()!=2){
          errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.79"), "0", "2"));
        }
      }
    }
    if (existsWarningFlag) {
      // ワーニング区分必须为0、1或2
      if (!StringUtil.isNullOrEmpty(header.getWarningFlag())) {
        if (!header.getWarningFlag().equals("0") && !header.getWarningFlag().equals("1") && !header.getWarningFlag().equals("2")) {
          errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.22"), "0", "2"));
        }
      }
    }
    // リードタイム为0到99的数值
    if (header.getLeadTime() != null) {
      if (header.getLeadTime() < 0 || header.getLeadTime() > 99) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.66"), "0", "99"));
      }
    }
    // CSV文件存在EC販売フラグ列
    if (existsSaleFlgEc) {
      if (header.getSaleFlgEc() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.19"))); 
      } else {
        if (header.getSaleFlgEc() != 0 && header.getSaleFlgEc() != 1 && header.getSaleFlgEc() != 2) {
          errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.19"), "0", "2"));
        }
      }
    }
    // CSV文件存在取引先コード列
    if (existsSupplierCode) {
      if (StringUtil.isNullOrEmpty(header.getSupplierCode())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.30"))); 
      }
    }
    // CSV文件存在仕入担当者コード列
    if (existsbuyerCode) {
      if(StringUtil.isNullOrEmpty(header.getBuyerCode())){
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.31"))); 
      }
    }
  }
  
  // 10.1.1 10006 追加 ここから
  private void checkDateRange(CCommodityHeaderImport bean, List<String> errorMessageList) {
    String min = Integer.toString(DIContainer.getWebshopConfig().getApplicationMinYear());
    String max = Integer.toString(DIContainer.getWebshopConfig().getApplicationMaxYear());

    if (!DateUtil.isCorrectAppDate(bean.getSaleStartDatetime(), false) && !bean.getSaleStartDatetime().equals(DateUtil.getMin())) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.48"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getSaleEndDatetime(), false) && !bean.getSaleEndDatetime().equals(DateUtil.getMax())) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.49"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getDiscountPriceStartDatetime(), true)) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.51"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getDiscountPriceEndDatetime(), true)) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.52"), min, max));
    }
  }

  // 10.1.1 10006 追加 ここまで

  private void checkTermsRules(CCommodityHeaderImport bean, List<String> errorMessageList) {
    if (bean.getSaleStartDatetime() != null && bean.getSaleEndDatetime() != null
        && bean.getSaleStartDatetime().after(bean.getSaleEndDatetime())) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.18"), bean.getCommodityCode()));
    }
    if (bean.getDiscountPriceStartDatetime() != null && bean.getDiscountPriceEndDatetime() != null
        && bean.getDiscountPriceStartDatetime().after(bean.getDiscountPriceEndDatetime())) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.19"), bean.getCommodityCode()));
    }
  }

  private void checkSalesTermAndDiscountRules(CCommodityHeaderImport bean, List<String> errorMessageList) {
    if (bean.getSaleStartDatetime() != null) {
      if (bean.getDiscountPriceStartDatetime() != null && bean.getSaleStartDatetime().after(bean.getDiscountPriceStartDatetime())) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.22"), bean.getCommodityCode()));
      }
      if ((!bean.getSaleStartDatetime().equals(DateUtil.getMin())) && bean.getDiscountPriceStartDatetime() == null
          && bean.getDiscountPriceEndDatetime() != null) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.23"), bean.getCommodityCode()));
      }
    }
    if (bean.getSaleEndDatetime() != null) {
      if (bean.getDiscountPriceEndDatetime() != null && bean.getDiscountPriceEndDatetime().after(bean.getSaleEndDatetime())) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.24"), bean.getCommodityCode()));
      }
      if ((!bean.getSaleEndDatetime().equals(DateUtil.getMax())) && bean.getDiscountPriceEndDatetime() == null
          && bean.getDiscountPriceStartDatetime() != null) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.25"), bean.getCommodityCode()));
      }
    }
  }

  /**
   * 商品期限管理フラグは2の場合保管日数必須
   */
  private void checkShelfLife(ValidationSummary summary) {
    if (header.getShelfLifeFlag() != null && header.getShelfLifeFlag() == 2) {
      if (header.getShelfLifeDays() == null) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.65"))));
      } else {
        if (header.getShelfLifeDays() < 0) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.67"))));
        }
      }
    }
  }
  
  /**
   * 成对检查
   */
  private void pairCheck(ValidationSummary summary){
    //成分名1，成分量1
    if((StringUtil.isNullOrEmpty(header.getIngredientName1())&&StringUtil.hasValue(header.getIngredientVal1()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal1())&&StringUtil.hasValue(header.getIngredientName1()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.34"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.35"))));
    }
    //成分名2，成分量2
    if((StringUtil.isNullOrEmpty(header.getIngredientName2())&&StringUtil.hasValue(header.getIngredientVal2()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal2())&&StringUtil.hasValue(header.getIngredientName2()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.36"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.37"))));
    }
    //成分名3，成分量3
    if((StringUtil.isNullOrEmpty(header.getIngredientName3())&&StringUtil.hasValue(header.getIngredientVal3()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal3())&&StringUtil.hasValue(header.getIngredientName3()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.38"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.39"))));
    }
    //成分名4，成分量4
    if((StringUtil.isNullOrEmpty(header.getIngredientName4())&&StringUtil.hasValue(header.getIngredientVal4()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal4())&&StringUtil.hasValue(header.getIngredientName4()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.40"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.41"))));
    }
    //成分名5，成分量5
    if((StringUtil.isNullOrEmpty(header.getIngredientName5())&&StringUtil.hasValue(header.getIngredientVal5()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal5())&&StringUtil.hasValue(header.getIngredientName5()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.42"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.43"))));
    }
    //成分名6，成分量6
    if((StringUtil.isNullOrEmpty(header.getIngredientName6())&&StringUtil.hasValue(header.getIngredientVal6()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal6())&&StringUtil.hasValue(header.getIngredientName6()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.44"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.45"))));
    }
    //成分名7，成分量7
    if((StringUtil.isNullOrEmpty(header.getIngredientName7())&&StringUtil.hasValue(header.getIngredientVal7()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal7())&&StringUtil.hasValue(header.getIngredientName7()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.46"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.47"))));
    }
    //成分名8，成分量8
    if((StringUtil.isNullOrEmpty(header.getIngredientName8())&&StringUtil.hasValue(header.getIngredientVal8()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal8())&&StringUtil.hasValue(header.getIngredientName8()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.48"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.49"))));
    }
    //成分名9，成分量9
    if((StringUtil.isNullOrEmpty(header.getIngredientName9())&&StringUtil.hasValue(header.getIngredientVal9()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal9())&&StringUtil.hasValue(header.getIngredientName9()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.50"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.51"))));
    }
    //成分名10，成分量10
    if((StringUtil.isNullOrEmpty(header.getIngredientName10())&&StringUtil.hasValue(header.getIngredientVal10()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal10())&&StringUtil.hasValue(header.getIngredientName10()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.52"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.53"))));
    }
    //成分名11，成分量11
    if((StringUtil.isNullOrEmpty(header.getIngredientName11())&&StringUtil.hasValue(header.getIngredientVal11()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal11())&&StringUtil.hasValue(header.getIngredientName11()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.54"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.55"))));
    }
    //成分名12，成分量12
    if((StringUtil.isNullOrEmpty(header.getIngredientName12())&&StringUtil.hasValue(header.getIngredientVal12()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal12())&&StringUtil.hasValue(header.getIngredientName12()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.56"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.57"))));
    }
    //成分名13，成分量13
    if((StringUtil.isNullOrEmpty(header.getIngredientName13())&&StringUtil.hasValue(header.getIngredientVal13()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal13())&&StringUtil.hasValue(header.getIngredientName13()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.58"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.59"))));
    }
    //成分名14，成分量14
    if((StringUtil.isNullOrEmpty(header.getIngredientName14())&&StringUtil.hasValue(header.getIngredientVal14()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal14())&&StringUtil.hasValue(header.getIngredientName14()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.60"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.61"))));
    }
    //成分名15，成分量15
    if((StringUtil.isNullOrEmpty(header.getIngredientName15())&&StringUtil.hasValue(header.getIngredientVal15()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal15())&&StringUtil.hasValue(header.getIngredientName15()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.62"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.63"))));
    }
  }
  
  /**
   * 商品基本表更新
   */
  private int executeUpdateHeader(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;

    if (exists) {
      for (int i = 0; i < getSchema().getColumns().size(); i++) {
        CsvColumn column = getSchema().getColumns().get(i);
        if (!column.getPhysicalName().equals("return_flg_cust") && 
            !column.getPhysicalName().equals("return_flg_supp") && 
            !column.getPhysicalName().equals("change_flg_supp")) {
          params.add(BeanUtil.invokeGetter(header, StringUtil.toCamelFormat(column.getPhysicalName())));
        }
      }
      
      params.add(header.getReturnFlg());
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      params.add(header.getCommodityCode());
      updateHeaderStatement = createPreparedStatement(getUpdateHeaderQuery());
      pstmt = updateHeaderStatement;
      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();

  }

  /**
   * 商品基本表查询存在
   */
  private String getSelectHeaderQuery() {
    String insertSql = "" + " SELECT COUNT(*) FROM " + HEADER_TABLE_NAME 
    // 20130617 txw update start
      + " WHERE COMMODITY_CODE = ? ";
    // 20130617 txw update end
    return insertSql;
  }

  /**
   * 商品基本表更新Query
   */
  private String getUpdateHeaderQuery() {
    String updateSql = "" + " UPDATE " + HEADER_TABLE_NAME
        + " SET {0} SYNC_FLAG_EC = 1, SYNC_FLAG_TMALL = 1, EXPORT_FLAG_ERP = 1,EXPORT_FLAG_WMS = 1, " 
        + " UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";

    StringBuilder setPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    for (int i = 0; i < getSchema().getColumns().size(); i++) {
      CsvColumn column = getSchema().getColumns().get(i);
      if (!column.getPhysicalName().equals("return_flg_cust") && 
          !column.getPhysicalName().equals("return_flg_supp") && 
          !column.getPhysicalName().equals("change_flg_supp")) {
        columnList.add(column.getPhysicalName());
      }
    }
    columnList.add("return_flg");
    for (String column : columnList) {
      setPart.append(column + " = ?, ");
    }

    return MessageFormat.format(updateSql, setPart.toString());

  }
  
  // 20130703 shen add start
  private int executeUpdateSyncFlag() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;
    params.add(getCondition().getSyncFlagTmall());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());
    params.add(header.getCommodityCode());

    pstmt = updateSyncFlag;

    logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }
  
  private String getUpdateSyncFlagQuery() {
    String updateSql = " UPDATE " + HEADER_TABLE_NAME
      + " SET SYNC_FLAG_EC = 1, SYNC_FLAG_TMALL = ?, UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
    
    return updateSql;
  }
  // 20130703 shen add end
  
  // 2014/05/06 京东WBS对应 ob_卢 add start
  private int executeUpdateSyncFlagJd() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;
    params.add(getCondition().getSyncFlagJd());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());
    params.add(header.getCommodityCode());

    pstmt = updateSyncFlagJd;

    logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  
  private String getUpdateSyncFlagJdQuery() {
  String updateSql = " UPDATE " + HEADER_TABLE_NAME
    + " SET SYNC_FLAG_EC = 1, SYNC_FLAG_JD = ?, UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
    return updateSql;
  }
  // 2014/05/06 京东WBS对应 ob_卢 add end
}
