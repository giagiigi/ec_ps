package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.CCommodityDataExportCondition;
import jp.co.sint.webshop.service.data.csv.CCommodityDetailExportCondition;
import jp.co.sint.webshop.service.data.csv.CCommodityHeaderExportCondition;
import jp.co.sint.webshop.service.data.csv.CategoryAttributeValueDataExportCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCsvExportBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040710:入荷お知らせのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityCsvExportExportAction extends WebBackAction<CommodityCsvExportBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommodityCsvExportBean bean = getBean();
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    String sql = "";
    // 下载商品基本表
    if (bean.getSearchExportObject().equals("0")) {
      // 商品コード
      columns.add(new CsvColumnImpl("commodity_code", "service.data.csv.CCommodityHeaderCsvSchema.1", CsvDataType.STRING, false,
          false, true, null));
      for (String item : bean.getHeaderItem()) { // 商品名称
        if (item.equals("1")) {
          columns.add(new CsvColumnImpl("commodity_name", "service.data.csv.CCommodityHeaderCsvSchema.2", CsvDataType.STRING,
              false, false, true, null));
          sql += ",CH.COMMODITY_NAME";
          // 商品名称英字
        } else if (item.equals("2")) {
          columns.add(new CsvColumnImpl("commodity_name_en", "service.data.csv.CCommodityHeaderCsvSchema.3", CsvDataType.STRING));
          sql += ",CH.COMMODITY_NAME_EN";
          // 商品名称日字
        } else if (item.equals("3")) {
          columns.add(new CsvColumnImpl("commodity_name_jp", "service.data.csv.CCommodityHeaderCsvSchema.86", CsvDataType.STRING));
          sql += ",CH.COMMODITY_NAME_JP";
          // ブランドコード
        } else if (item.equals("4")) {
          columns.add(new CsvColumnImpl("brand_code", "service.data.csv.CCommodityHeaderCsvSchema.26", CsvDataType.STRING));
          sql += ",CH.BRAND_CODE";
          // TMALLのカテゴリID
        } else if (item.equals("5")) {
          columns.add(new CsvColumnImpl("tmall_category_id", "service.data.csv.CCommodityHeaderCsvSchema.29", CsvDataType.NUMBER));
          sql += ",CH.TMALL_CATEGORY_ID";
          // 取引先コード
        } else if (item.equals("6")) {
          columns.add(new CsvColumnImpl("supplier_code", "service.data.csv.CCommodityHeaderCsvSchema.30", CsvDataType.STRING));
          sql += ",CH.SUPPLIER_CODE";
          // セール区分
        } else if (item.equals("7")) {
          columns.add(new CsvColumnImpl("sale_flag", "service.data.csv.CCommodityHeaderCsvSchema.24", CsvDataType.STRING));
          sql += ",CH.SALE_FLAG";
          // 特集区分
        } else if (item.equals("8")) {
          columns.add(new CsvColumnImpl("spec_flag", "service.data.csv.CCommodityHeaderCsvSchema.25", CsvDataType.STRING));
          sql += ",CH.SPEC_FLAG";
          // 商品説明1
        } else if (item.equals("9")) {
          columns.add(new CsvColumnImpl("commodity_description1", "service.data.csv.CCommodityHeaderCsvSchema.6",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION1";
          // 商品説明1 英字
        } else if (item.equals("10")) {
          columns.add(new CsvColumnImpl("commodity_description1_en", "service.data.csv.CCommodityHeaderCsvSchema.87",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION1_EN";
          // 商品説明1 日字
        } else if (item.equals("11")) {
          columns.add(new CsvColumnImpl("commodity_description1_jp", "service.data.csv.CCommodityHeaderCsvSchema.88",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION1_JP";
          // 商品説明2
        } else if (item.equals("12")) {
          columns.add(new CsvColumnImpl("commodity_description2", "service.data.csv.CCommodityHeaderCsvSchema.7",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION2";
          // 商品説明2 英字
        } else if (item.equals("13")) {
          columns.add(new CsvColumnImpl("commodity_description2_en", "service.data.csv.CCommodityHeaderCsvSchema.89",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION2_EN";
          // 商品説明2 日字
        } else if (item.equals("14")) {
          columns.add(new CsvColumnImpl("commodity_description2_jp", "service.data.csv.CCommodityHeaderCsvSchema.90",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION2_JP";
          // 商品説明3
        } else if (item.equals("15")) {
          columns.add(new CsvColumnImpl("commodity_description3", "service.data.csv.CCommodityHeaderCsvSchema.8",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION3";
          // 商品説明3 英字
        } else if (item.equals("16")) {
          columns.add(new CsvColumnImpl("commodity_description3_en", "service.data.csv.CCommodityHeaderCsvSchema.91",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION3_EN";
          // 商品説明3 英字
        } else if (item.equals("17")) {
          columns.add(new CsvColumnImpl("commodity_description3_jp", "service.data.csv.CCommodityHeaderCsvSchema.92",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION3_JP";
          // 商品説明(一覧用）
        } else if (item.equals("18")) {
          columns.add(new CsvColumnImpl("commodity_description_short", "service.data.csv.CCommodityHeaderCsvSchema.9",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION_SHORT";
          // 商品説明(一覧用） 英字
        } else if (item.equals("19")) {
          columns.add(new CsvColumnImpl("commodity_description_short_en", "service.data.csv.CCommodityHeaderCsvSchema.93",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION_SHORT_EN";
          // 商品説明(一覧用） 英字
        } else if (item.equals("20")) {
          columns.add(new CsvColumnImpl("commodity_description_short_jp", "service.data.csv.CCommodityHeaderCsvSchema.94",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION_SHORT_JP";
          // 商品検索ワード
        } else if (item.equals("21")) {
          columns.add(new CsvColumnImpl("commodity_search_words", "service.data.csv.CCommodityHeaderCsvSchema.10",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_SEARCH_WORDS";
          // 販売開始日時
        } else if (item.equals("22")) {
          columns
              .add(new CsvColumnImpl("sale_start_datetime", "service.data.csv.CCommodityHeaderCsvSchema.11", CsvDataType.STRING));
          sql += ", CASE CH.SALE_START_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMin())
              + "' THEN NULL ELSE '''' || CH.SALE_START_DATETIME END AS SALE_START_DATETIME ";
          // 販売終了日時
        } else if (item.equals("23")) {
          columns.add(new CsvColumnImpl("sale_end_datetime", "service.data.csv.CCommodityHeaderCsvSchema.12", CsvDataType.STRING));
          sql += ",CASE CH.SALE_END_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMax())
              + "' THEN NULL ELSE '''' || CH.SALE_END_DATETIME END AS SALE_END_DATETIME ";
          // 特別価格開始日時
        } else if (item.equals("24")) {
          columns.add(new CsvColumnImpl("discount_price_start_datetime", "service.data.csv.CCommodityHeaderCsvSchema.13",
              CsvDataType.STRING));
          sql += ",CASE CH.DISCOUNT_PRICE_START_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMin())
              + "' THEN NULL ELSE '''' || CH.DISCOUNT_PRICE_START_DATETIME END AS DISCOUNT_PRICE_START_DATETIME ";
          // 特別価格終了日時
        } else if (item.equals("25")) {
          columns.add(new CsvColumnImpl("discount_price_end_datetime", "service.data.csv.CCommodityHeaderCsvSchema.14",
              CsvDataType.STRING));
          sql += ",CASE CH.DISCOUNT_PRICE_END_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMax())
              + "' THEN NULL ELSE '''' || CH.DISCOUNT_PRICE_END_DATETIME END AS DISCOUNT_PRICE_END_DATETIME ";
          // ワーニング区分
        } else if (item.equals("26")) {
          columns.add(new CsvColumnImpl("warning_flag", "service.data.csv.CCommodityHeaderCsvSchema.22", CsvDataType.STRING));
          sql += ",CH.WARNING_FLAG";
          // 規格1名称ID(TMALLの属性ID）
        } else if (item.equals("27")) {
          columns.add(new CsvColumnImpl("standard1_id", "service.data.csv.CCommodityHeaderCsvSchema.15", CsvDataType.STRING));
          sql += ",CH.STANDARD1_ID";
          // 規格1名称
        } else if (item.equals("28")) {
          columns.add(new CsvColumnImpl("standard1_name", "service.data.csv.CCommodityHeaderCsvSchema.16", CsvDataType.STRING));
          sql += ",CH.STANDARD1_NAME";
          // 規格1名称 英字
        } else if (item.equals("29")) {
          columns.add(new CsvColumnImpl("standard1_name_en", "service.data.csv.CCommodityHeaderCsvSchema.95", CsvDataType.STRING));
          sql += ",CH.STANDARD1_NAME_EN";
          // 規格1名称 日字
        } else if (item.equals("30")) {
          columns.add(new CsvColumnImpl("standard1_name_jp", "service.data.csv.CCommodityHeaderCsvSchema.96", CsvDataType.STRING));
          sql += ",CH.STANDARD1_NAME_JP";
          // 規格2名称ID(TMALLの属性ID）
        } else if (item.equals("31")) {
          columns.add(new CsvColumnImpl("standard2_id", "service.data.csv.CCommodityHeaderCsvSchema.17", CsvDataType.STRING));
          sql += ",CH.STANDARD2_ID";
          // 規格2名称
        } else if (item.equals("32")) {
          columns.add(new CsvColumnImpl("standard2_name", "service.data.csv.CCommodityHeaderCsvSchema.18", CsvDataType.STRING));
          sql += ",CH.STANDARD2_NAME";
          // 規格2名称 英文
        } else if (item.equals("33")) {
          columns.add(new CsvColumnImpl("standard2_name_en", "service.data.csv.CCommodityHeaderCsvSchema.97", CsvDataType.STRING));
          sql += ",CH.STANDARD2_NAME_EN";
          // 規格2名称 日文
        } else if (item.equals("34")) {
          columns.add(new CsvColumnImpl("standard2_name_jp", "service.data.csv.CCommodityHeaderCsvSchema.98", CsvDataType.STRING));
          sql += ",CH.STANDARD2_NAME_JP";
          // EC販売フラグ
        } else if (item.equals("35")) {
          columns.add(new CsvColumnImpl("sale_flg_ec", "service.data.csv.CCommodityHeaderCsvSchema.19", CsvDataType.NUMBER));
          sql += ",CH.SALE_FLG_EC";
          // 返品不可フラグ
          // 供货商换货
        } else if (item.equals("38")) {
          columns.add(new CsvColumnImpl("change_flg_supp", "service.data.csv.CCommodityHeaderCsvSchema.85", CsvDataType.STRING));
          sql += ",CASE WHEN CH.RETURN_FLG = 0 THEN 0 " + "      WHEN CH.RETURN_FLG = 1 THEN 0 "
              + "      WHEN CH.RETURN_FLG = 2 THEN 0 " + "      WHEN CH.RETURN_FLG = 3 THEN 0 ELSE 1 END CHANGE_FLG_SUPP";
          // 供货商退货
        } else if (item.equals("37")) {
          columns.add(new CsvColumnImpl("return_flg_supp", "service.data.csv.CCommodityHeaderCsvSchema.84", CsvDataType.STRING));
          sql += ",CASE WHEN CH.RETURN_FLG = 0 THEN 0 " + "      WHEN CH.RETURN_FLG = 1 THEN 0 "
              + "      WHEN CH.RETURN_FLG = 4 THEN 0 " + "      WHEN CH.RETURN_FLG = 5 THEN 0 ELSE 1 END RETURN_FLG_SUPP";
          // 顾客退货
        } else if (item.equals("36")) {
          columns.add(new CsvColumnImpl("return_flg_cust", "service.data.csv.CCommodityHeaderCsvSchema.83", CsvDataType.STRING));
          sql += ",CASE WHEN CH.RETURN_FLG = 0 THEN 0 " + "      WHEN CH.RETURN_FLG = 2 THEN 0 "
              + "      WHEN CH.RETURN_FLG = 4 THEN 0 " + "      WHEN CH.RETURN_FLG = 6 THEN 0 ELSE 1 END RETURN_FLG_CUST ";
          // リードタイム
        } else if (item.equals("39")) {
          columns.add(new CsvColumnImpl("lead_time", "service.data.csv.CCommodityHeaderCsvSchema.23", CsvDataType.NUMBER));
          sql += ",CH.LEAD_TIME";
          // 産地
        } else if (item.equals("40")) {
          columns.add(new CsvColumnImpl("original_code", "service.data.csv.CCommodityHeaderCsvSchema.33", CsvDataType.STRING));
          sql += ",CH.ORIGINAL_CODE";
          // 大物フラグ
        } else if (item.equals("41")) {
          columns.add(new CsvColumnImpl("big_flag", "service.data.csv.CCommodityHeaderCsvSchema.82", CsvDataType.NUMBER));
          sql += ",CH.BIG_FLAG";
          // 商品期限管理フラグ 0管理しない/1賞味期限日/2製造日＋保管日数
        } else if (item.equals("42")) {
          columns.add(new CsvColumnImpl("shelf_life_flag", "service.data.csv.CCommodityHeaderCsvSchema.79", CsvDataType.NUMBER));
          sql += ",CH.SHELF_LIFE_FLAG";
          // 保管日数
        } else if (item.equals("43")) {
          columns.add(new CsvColumnImpl("shelf_life_days", "service.data.csv.CCommodityHeaderCsvSchema.80", CsvDataType.NUMBER));
          sql += ",CH.SHELF_LIFE_DAYS";
          // 仕入担当者コード
        } else if (item.equals("44")) {
          columns.add(new CsvColumnImpl("buyer_code", "service.data.csv.CCommodityHeaderCsvSchema.31", CsvDataType.STRING));
          sql += ",CH.BUYER_CODE";
          // 原材料1
        } else if (item.equals("45")) {
          columns.add(new CsvColumnImpl("material1", "service.data.csv.CCommodityHeaderCsvSchema.64", CsvDataType.STRING));
          sql += ",CH.MATERIAL1";
          //
        } else if (item.equals("46")) {
          columns.add(new CsvColumnImpl("material2", "service.data.csv.CCommodityHeaderCsvSchema.65", CsvDataType.STRING));
          sql += ",CH.MATERIAL2";
          //
        } else if (item.equals("47")) {
          columns.add(new CsvColumnImpl("material3", "service.data.csv.CCommodityHeaderCsvSchema.66", CsvDataType.STRING));
          sql += ",CH.MATERIAL3";
          //
        } else if (item.equals("48")) {
          columns.add(new CsvColumnImpl("material4", "service.data.csv.CCommodityHeaderCsvSchema.67", CsvDataType.STRING));
          sql += ",CH.MATERIAL4";
          //
        } else if (item.equals("49")) {
          columns.add(new CsvColumnImpl("material5", "service.data.csv.CCommodityHeaderCsvSchema.68", CsvDataType.STRING));
          sql += ",CH.MATERIAL5";
          //
        } else if (item.equals("50")) {
          columns.add(new CsvColumnImpl("material6", "service.data.csv.CCommodityHeaderCsvSchema.69", CsvDataType.STRING));
          sql += ",CH.MATERIAL6";
          //
        } else if (item.equals("51")) {
          columns.add(new CsvColumnImpl("material7", "service.data.csv.CCommodityHeaderCsvSchema.70", CsvDataType.STRING));
          sql += ",CH.MATERIAL7";
          //
        } else if (item.equals("52")) {
          columns.add(new CsvColumnImpl("material8", "service.data.csv.CCommodityHeaderCsvSchema.71", CsvDataType.STRING));
          sql += ",CH.MATERIAL8";
          //
        } else if (item.equals("53")) {
          columns.add(new CsvColumnImpl("material9", "service.data.csv.CCommodityHeaderCsvSchema.72", CsvDataType.STRING));
          sql += ",CH.MATERIAL9";
          // 原材料10
        } else if (item.equals("54")) {
          columns.add(new CsvColumnImpl("material10", "service.data.csv.CCommodityHeaderCsvSchema.73", CsvDataType.STRING));
          sql += ",CH.MATERIAL10";
          // 原材料11
        } else if (item.equals("55")) {
          columns.add(new CsvColumnImpl("material11", "service.data.csv.CCommodityHeaderCsvSchema.74", CsvDataType.STRING));
          sql += ",CH.MATERIAL11";
          // 原材料12
        } else if (item.equals("56")) {
          columns.add(new CsvColumnImpl("material12", "service.data.csv.CCommodityHeaderCsvSchema.75", CsvDataType.STRING));
          sql += ",CH.MATERIAL12";
          // 原材料13
        } else if (item.equals("57")) {
          columns.add(new CsvColumnImpl("material13", "service.data.csv.CCommodityHeaderCsvSchema.76", CsvDataType.STRING));
          sql += ",CH.MATERIAL13";
          // 原材料14
        } else if (item.equals("58")) {
          columns.add(new CsvColumnImpl("material14", "service.data.csv.CCommodityHeaderCsvSchema.77", CsvDataType.STRING));
          sql += ",CH.MATERIAL14";
          // 原材料15
        } else if (item.equals("59")) {
          columns.add(new CsvColumnImpl("material15", "service.data.csv.CCommodityHeaderCsvSchema.78", CsvDataType.STRING));
          sql += ",CH.MATERIAL15";
          // 成分1
        } else if (item.equals("60")) {
          columns.add(new CsvColumnImpl("ingredient_name1", "service.data.csv.CCommodityHeaderCsvSchema.34", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME1";
          // 成分量1
        } else if (item.equals("61")) {
          columns.add(new CsvColumnImpl("ingredient_val1", "service.data.csv.CCommodityHeaderCsvSchema.35", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL1";
          //
        } else if (item.equals("62")) {
          columns.add(new CsvColumnImpl("ingredient_name2", "service.data.csv.CCommodityHeaderCsvSchema.36", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME2";
          //
        } else if (item.equals("63")) {
          columns.add(new CsvColumnImpl("ingredient_val2", "service.data.csv.CCommodityHeaderCsvSchema.37", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL2";
          //
        } else if (item.equals("64")) {
          columns.add(new CsvColumnImpl("ingredient_name3", "service.data.csv.CCommodityHeaderCsvSchema.38", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME3";
          //
        } else if (item.equals("65")) {
          columns.add(new CsvColumnImpl("ingredient_val3", "service.data.csv.CCommodityHeaderCsvSchema.39", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL3";
          //
        } else if (item.equals("66")) {
          columns.add(new CsvColumnImpl("ingredient_name4", "service.data.csv.CCommodityHeaderCsvSchema.40", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME4";
          //
        } else if (item.equals("67")) {
          columns.add(new CsvColumnImpl("ingredient_val4", "service.data.csv.CCommodityHeaderCsvSchema.41", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL4";
          //
        } else if (item.equals("68")) {
          columns.add(new CsvColumnImpl("ingredient_name5", "service.data.csv.CCommodityHeaderCsvSchema.42", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME5";
          //
        } else if (item.equals("69")) {
          columns.add(new CsvColumnImpl("ingredient_val5", "service.data.csv.CCommodityHeaderCsvSchema.43", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL5";
          //
        } else if (item.equals("70")) {
          columns.add(new CsvColumnImpl("ingredient_name6", "service.data.csv.CCommodityHeaderCsvSchema.44", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME6";
          //
        } else if (item.equals("71")) {
          columns.add(new CsvColumnImpl("ingredient_val6", "service.data.csv.CCommodityHeaderCsvSchema.45", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL6";
          //
        } else if (item.equals("72")) {
          columns.add(new CsvColumnImpl("ingredient_name7", "service.data.csv.CCommodityHeaderCsvSchema.46", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME7";
          //
        } else if (item.equals("73")) {
          columns.add(new CsvColumnImpl("ingredient_val7", "service.data.csv.CCommodityHeaderCsvSchema.47", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL7";
          //
        } else if (item.equals("74")) {
          columns.add(new CsvColumnImpl("ingredient_name8", "service.data.csv.CCommodityHeaderCsvSchema.48", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME8";
          //
        } else if (item.equals("75")) {
          columns.add(new CsvColumnImpl("ingredient_val8", "service.data.csv.CCommodityHeaderCsvSchema.49", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL8";
          //
        } else if (item.equals("76")) {
          columns.add(new CsvColumnImpl("ingredient_name9", "service.data.csv.CCommodityHeaderCsvSchema.50", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME9";
          //
        } else if (item.equals("77")) {
          columns.add(new CsvColumnImpl("ingredient_val9", "service.data.csv.CCommodityHeaderCsvSchema.51", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL9";
          // 成分10
        } else if (item.equals("78")) {
          columns.add(new CsvColumnImpl("ingredient_name10", "service.data.csv.CCommodityHeaderCsvSchema.52", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME10";
          // 成分量10
        } else if (item.equals("79")) {
          columns.add(new CsvColumnImpl("ingredient_val10", "service.data.csv.CCommodityHeaderCsvSchema.53", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL10";
          // 成分11
        } else if (item.equals("80")) {
          columns.add(new CsvColumnImpl("ingredient_name11", "service.data.csv.CCommodityHeaderCsvSchema.54", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME11";
          // 成分量11
        } else if (item.equals("81")) {
          columns.add(new CsvColumnImpl("ingredient_val11", "service.data.csv.CCommodityHeaderCsvSchema.55", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL11";
          // 成分12
        } else if (item.equals("82")) {
          columns.add(new CsvColumnImpl("ingredient_name12", "service.data.csv.CCommodityHeaderCsvSchema.56", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME12";
          // 成分量12
        } else if (item.equals("83")) {
          columns.add(new CsvColumnImpl("ingredient_val12", "service.data.csv.CCommodityHeaderCsvSchema.57", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL12";
          // 成分13
        } else if (item.equals("84")) {
          columns.add(new CsvColumnImpl("ingredient_name13", "service.data.csv.CCommodityHeaderCsvSchema.58", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME13";
          // 成分量13
        } else if (item.equals("85")) {
          columns.add(new CsvColumnImpl("ingredient_val13", "service.data.csv.CCommodityHeaderCsvSchema.59", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL13";
          // 成分14
        } else if (item.equals("86")) {
          columns.add(new CsvColumnImpl("ingredient_name14", "service.data.csv.CCommodityHeaderCsvSchema.60", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME14";
          // 成分量14
        } else if (item.equals("87")) {
          columns.add(new CsvColumnImpl("ingredient_val14", "service.data.csv.CCommodityHeaderCsvSchema.61", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL14";
          // 成分15
        } else if (item.equals("88")) {
          columns.add(new CsvColumnImpl("ingredient_name15", "service.data.csv.CCommodityHeaderCsvSchema.62", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME15";
          // 成分量15
        } else if (item.equals("89")) {
          columns.add(new CsvColumnImpl("ingredient_val15", "service.data.csv.CCommodityHeaderCsvSchema.63", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL15";
        } else if (item.equals("90")) { // 生产许可证号
          columns.add(new CsvColumnImpl("food_security_prd_license_no", "service.data.csv.CCommodityHeaderCsvSchema.104",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_PRD_LICENSE_NO";
        } else if (item.equals("91")) { // 产品标准号
          columns.add(new CsvColumnImpl("food_security_design_code", "service.data.csv.CCommodityHeaderCsvSchema.105",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_DESIGN_CODE";
        } else if (item.equals("92")) { // 厂名
          columns.add(new CsvColumnImpl("food_security_factory", "service.data.csv.CCommodityHeaderCsvSchema.106",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_FACTORY";
        } else if (item.equals("93")) { // 厂址
          columns.add(new CsvColumnImpl("food_security_factory_site", "service.data.csv.CCommodityHeaderCsvSchema.107",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_FACTORY_SITE";
        } else if (item.equals("94")) { // 厂家联系方式
          columns.add(new CsvColumnImpl("food_security_contact", "service.data.csv.CCommodityHeaderCsvSchema.108",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_CONTACT";
        } else if (item.equals("95")) { // 配料表
          columns.add(new CsvColumnImpl("food_security_mix", "service.data.csv.CCommodityHeaderCsvSchema.109", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_MIX";
        } else if (item.equals("96")) { // 储藏方法
          columns.add(new CsvColumnImpl("food_security_plan_storage", "service.data.csv.CCommodityHeaderCsvSchema.110",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_PLAN_STORAGE";
        } else if (item.equals("97")) { // 保质期
          columns.add(new CsvColumnImpl("food_security_period", "service.data.csv.CCommodityHeaderCsvSchema.111",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_PERIOD";
        } else if (item.equals("98")) { // 食品添加剂
          columns.add(new CsvColumnImpl("food_security_food_additive", "service.data.csv.CCommodityHeaderCsvSchema.112",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_FOOD_ADDITIVE";
        } else if (item.equals("99")) { // 供货商
          columns.add(new CsvColumnImpl("food_security_supplier", "service.data.csv.CCommodityHeaderCsvSchema.113",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_SUPPLIER";
        } else if (item.equals("100")) { // 生产开始日期
          columns.add(new CsvColumnImpl("food_security_product_date_start", "service.data.csv.CCommodityHeaderCsvSchema.114",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_PRODUCT_DATE_START";
        } else if (item.equals("101")) { // 生产结束日期
          columns.add(new CsvColumnImpl("food_security_product_date_end", "service.data.csv.CCommodityHeaderCsvSchema.115",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_PRODUCT_DATE_END";
        } else if (item.equals("102")) { // 进货开始日期
          columns.add(new CsvColumnImpl("food_security_stock_date_start", "service.data.csv.CCommodityHeaderCsvSchema.116",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_STOCK_DATE_START";
        } else if (item.equals("103")) { // 进货结束日期
          columns.add(new CsvColumnImpl("food_security_stock_date_end", "service.data.csv.CCommodityHeaderCsvSchema.117",
              CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_STOCK_DATE_END";
        } else if (item.equals("104")) { // 入库效期
          columns
              .add(new CsvColumnImpl("in_bound_life_days", "service.data.csv.CCommodityHeaderCsvSchema.121", CsvDataType.NUMBER));
          sql += ",CH.IN_BOUND_LIFE_DAYS";
        } else if (item.equals("105")) { // 入库效期
          columns
              .add(new CsvColumnImpl("out_bound_life_days", "service.data.csv.CCommodityHeaderCsvSchema.122", CsvDataType.NUMBER));
          sql += ",CH.OUT_BOUND_LIFE_DAYS";
        } else if (item.equals("106")) { // 入库效期
          columns.add(new CsvColumnImpl("shelf_life_alert_days", "service.data.csv.CCommodityHeaderCsvSchema.123",
              CsvDataType.NUMBER));
          sql += ",CH.SHELF_LIFE_ALERT_DAYS";
        } else if (item.equals("107")) { // tmall满就送(赠品标志)
          columns.add(new CsvColumnImpl("tmall_mjs_flg", "service.data.csv.CCommodityHeaderCsvSchema.124", CsvDataType.NUMBER));
          sql += ",CH.TMALL_MJS_FLG";
        } else if (item.equals("108")) { // 商品区分
          columns.add(new CsvColumnImpl("commodity_type", "service.data.csv.CCommodityHeaderCsvSchema.120", CsvDataType.NUMBER));
          sql += ",CH.COMMODITY_TYPE";
        } else if (item.equals("109")) { // 进口商品区分
          columns.add(new CsvColumnImpl("import_commodity_type", "service.data.csv.CCommodityHeaderCsvSchema.125",
              CsvDataType.NUMBER));
          sql += ",CH.IMPORT_COMMODITY_TYPE";
        } else if (item.equals("110")) { // 清仓商品区分
          columns.add(new CsvColumnImpl("clear_commodity_type", "service.data.csv.CCommodityHeaderCsvSchema.126",
              CsvDataType.NUMBER));
          sql += ",CH.CLEAR_COMMODITY_TYPE";
        } else if (item.equals("111")) { // Asahi商品区分
          columns.add(new CsvColumnImpl("reserve_commodity_type1", "service.data.csv.CCommodityHeaderCsvSchema.127",
              CsvDataType.NUMBER));
          sql += ",CH.RESERVE_COMMODITY_TYPE1";
        } else if (item.equals("112")) { // hot商品区分
          columns.add(new CsvColumnImpl("reserve_commodity_type2", "service.data.csv.CCommodityHeaderCsvSchema.128",
              CsvDataType.NUMBER));
          sql += ",CH.RESERVE_COMMODITY_TYPE2";
        } else if (item.equals("113")) { // 商品展示区分
          columns.add(new CsvColumnImpl("reserve_commodity_type3", "service.data.csv.CCommodityHeaderCsvSchema.129",
              CsvDataType.NUMBER));
          sql += ",CH.RESERVE_COMMODITY_TYPE3";
        } else if (item.equals("114")) { // 预留区分1*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type1", "service.data.csv.CCommodityHeaderCsvSchema.130",
              CsvDataType.NUMBER));
          sql += ",CH.NEW_RESERVE_COMMODITY_TYPE1";
        } else if (item.equals("115")) { // 预留区分2*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type2", "service.data.csv.CCommodityHeaderCsvSchema.131",
              CsvDataType.NUMBER));
          sql += ",CH.NEW_RESERVE_COMMODITY_TYPE2";
        } else if (item.equals("116")) { // 预留区分3*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type3", "service.data.csv.CCommodityHeaderCsvSchema.132",
              CsvDataType.NUMBER));
          sql += ",CH.NEW_RESERVE_COMMODITY_TYPE3";
        } else if (item.equals("117")) { // 预留区分4*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type4", "service.data.csv.CCommodityHeaderCsvSchema.133",
              CsvDataType.NUMBER));
          sql += ",CH.NEW_RESERVE_COMMODITY_TYPE4";
        } else if (item.equals("118")) { // 预留区分5*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type5", "service.data.csv.CCommodityHeaderCsvSchema.134",
              CsvDataType.NUMBER));
          sql += ",CH.NEW_RESERVE_COMMODITY_TYPE5";
        } else if (item.equals("119")) { // TMALL检索关键字
          columns.add(new CsvColumnImpl("tmall_commodity_search_words", "service.data.csv.CCommodityHeaderCsvSchema.135",
              CsvDataType.STRING));
          sql += ",CH.TMALL_COMMODITY_SEARCH_WORDS";
          // 20130809 txw add start
        } else if (item.equals("122")) { // TITLE
          columns.add(new CsvColumnImpl("title", "service.data.csv.CCommodityHeaderCsvSchema.138", CsvDataType.STRING));
          sql += ",CH.TITLE";
        } else if (item.equals("123")) { // TITLE(英文)
          columns.add(new CsvColumnImpl("title_en", "service.data.csv.CCommodityHeaderCsvSchema.139", CsvDataType.STRING));
          sql += ",CH.TITLE_EN";
        } else if (item.equals("124")) { // TITLE(日文)
          columns.add(new CsvColumnImpl("title_jp", "service.data.csv.CCommodityHeaderCsvSchema.140", CsvDataType.STRING));
          sql += ",CH.TITLE_JP";
        } else if (item.equals("125")) { // DESCRIPTION
          columns.add(new CsvColumnImpl("description", "service.data.csv.CCommodityHeaderCsvSchema.141", CsvDataType.STRING));
          sql += ",CH.DESCRIPTION";
        } else if (item.equals("126")) { // DESCRIPTION(英文)
          columns.add(new CsvColumnImpl("description_en", "service.data.csv.CCommodityHeaderCsvSchema.142", CsvDataType.STRING));
          sql += ",CH.DESCRIPTION_EN";
        } else if (item.equals("127")) { // DESCRIPTION(日文)
          columns.add(new CsvColumnImpl("description_jp", "service.data.csv.CCommodityHeaderCsvSchema.143", CsvDataType.STRING));
          sql += ",CH.DESCRIPTION_JP";
        } else if (item.equals("128")) { // KEYWORD
          columns.add(new CsvColumnImpl("keyword", "service.data.csv.CCommodityHeaderCsvSchema.144", CsvDataType.STRING));
          sql += ",CH.KEYWORD";
        } else if (item.equals("129")) { // KEYWORD(英文)
          columns.add(new CsvColumnImpl("keyword_en", "service.data.csv.CCommodityHeaderCsvSchema.145", CsvDataType.STRING));
          sql += ",CH.KEYWORD_EN";
        } else if (item.equals("130")) { // KEYWORD(日文)
          columns.add(new CsvColumnImpl("keyword_jp", "service.data.csv.CCommodityHeaderCsvSchema.146", CsvDataType.STRING));
          sql += ",CH.KEYWORD_JP";
        } else if (item.equals("120")) { //English热卖标志
          columns.add(new CsvColumnImpl("hot_flg_en",
              "service.data.csv.CCommodityHeaderCsvSchema.136", CsvDataType.NUMBER));
          sql += ",CH.hot_flg_en";
        } else if (item.equals("121")) { //Japan热卖标志
          columns.add(new CsvColumnImpl("hot_flg_jp",
              "service.data.csv.CCommodityHeaderCsvSchema.137", CsvDataType.NUMBER));
          sql += ",CH.hot_flg_jp";
        } else if (item.equals("131")) { //原商品编号
          columns.add(new CsvColumnImpl("original_commodity_code",
              "service.data.csv.CCommodityHeaderCsvSchema.147", CsvDataType.STRING));
          sql += ",'''' || CH.original_commodity_code as original_commodity_code";
        } else if (item.equals("132")) { //组合数量
          columns.add(new CsvColumnImpl("combination_amount",
              "service.data.csv.CCommodityHeaderCsvSchema.148", CsvDataType.NUMBER));
          sql += ",CH.combination_amount";
        } else if (item.equals("133")) { //検索Keyword中文
          columns.add(new CsvColumnImpl("keyword_cn2",
              "service.data.csv.CCommodityHeaderCsvSchema.149", CsvDataType.STRING));
          sql += ",CH.keyword_cn2";
        } else if (item.equals("134")) { //検索Keyword日文
          columns.add(new CsvColumnImpl("keyword_jp2",
              "service.data.csv.CCommodityHeaderCsvSchema.150", CsvDataType.STRING));
          sql += ",CH.keyword_jp2";
        } else if (item.equals("135")) { //検索Keyword英文
          columns.add(new CsvColumnImpl("keyword_en2",
              "service.data.csv.CCommodityHeaderCsvSchema.151", CsvDataType.STRING));
          sql += ",CH.keyword_en2";
        }
        // 20130809 txw add end
        
        // 2014/04/25 京东WBS对应 ob_卢 add start
        else if (item.equals("136")) { //广告词
          columns.add(new CsvColumnImpl("advert_content",
              "service.data.csv.CCommodityHeaderCsvSchema.152", CsvDataType.STRING));
          sql += ",CH.advert_content";
        }
        // 2014/04/25 京东WBS对应 ob_卢 add end
      }
      CCommodityHeaderExportCondition condition = CsvExportType.EXPORT_CSV_COMMODITY_HEAD_DATA.createConditionInstance();
      condition.setCommodityCode(bean.getSearchCommodityCode());
      condition.setSkuCode(bean.getSearchSkuCode());
      condition.setColumns(columns);
      condition.setCombineType(bean.getCombineObject());
      condition.setSqlString(sql);
      if (bean.getSearchExportTemplateFlg().equals("2")) {
        condition.setHeaderOnly(true);
      } else {
        condition.setHeaderOnly(false);
      }
      this.exportCondition = condition;
      // 下载商品明细表
    } else if (bean.getSearchExportObject().equals("1")) {
      // SKUコード
      columns.add(new CsvColumnImpl("sku_code", "service.data.csv.CCommodityDetailCsvSchema.0", CsvDataType.STRING, false, false,
          true, null));
      for (String item : bean.getDetailItem()) {
        // SKU名称
        if (item.equals("1")) {
          columns.add(new CsvColumnImpl("sku_name", "service.data.csv.CCommodityDetailCsvSchema.1", CsvDataType.STRING));
          sql += ",CD.SKU_NAME";
          // 仕入価格
        } else if (item.equals("2")) {
          columns.add(new CsvColumnImpl("purchase_price", "service.data.csv.CCommodityDetailCsvSchema.5", CsvDataType.STRING));
          sql += ",CD.PURCHASE_PRICE";
          // 希望小売価格
        } else if (item.equals("3")) {
          columns.add(new CsvColumnImpl("suggeste_price", "service.data.csv.CCommodityDetailCsvSchema.4", CsvDataType.BIGDECIMAL));
          sql += ",CD.SUGGESTE_PRICE";
          // 下限売価
        } else if (item.equals("4")) {
          columns.add(new CsvColumnImpl("min_price", "service.data.csv.CCommodityDetailCsvSchema.23", CsvDataType.BIGDECIMAL));
          sql += ",CD.MIN_PRICE";
          // 定価フラグ0：非定価　1：定価
        } else if (item.equals("5")) {
          columns.add(new CsvColumnImpl("fixed_price_flag", "service.data.csv.CCommodityDetailCsvSchema.3", CsvDataType.NUMBER));
          sql += ",CD.FIXED_PRICE_FLAG";
          // EC商品単価
        } else if (item.equals("6")) {
          columns.add(new CsvColumnImpl("unit_price", "service.data.csv.CCommodityDetailCsvSchema.6", CsvDataType.BIGDECIMAL));
          sql += ",CD.UNIT_PRICE";
          // TMALLの商品単価
        } else if (item.equals("7")) {
          columns.add(new CsvColumnImpl("tmall_unit_price", "service.data.csv.CCommodityDetailCsvSchema.21", CsvDataType.NUMBER));
          sql += ",CD.TMALL_UNIT_PRICE";
          // EC特別価格
        } else if (item.equals("8")) {
          columns.add(new CsvColumnImpl("discount_price", "service.data.csv.CCommodityDetailCsvSchema.7", CsvDataType.BIGDECIMAL));
          sql += ",CD.DISCOUNT_PRICE";
          // TMALLの特別価格
        } else if (item.equals("9")) {
          columns
              .add(new CsvColumnImpl("tmall_discount_price", "service.data.csv.CCommodityDetailCsvSchema.22", CsvDataType.NUMBER));
          sql += ",CD.TMALL_DISCOUNT_PRICE";
          // 取扱いフラグ(EC)
        } else if (item.equals("10")) {
          columns.add(new CsvColumnImpl("use_flg", "service.data.csv.CCommodityDetailCsvSchema.15", CsvDataType.BIGDECIMAL));
          sql += ",CD.USE_FLG";
          // 取扱いフラグ(TMALL)
        } else if (item.equals("11")) {
          columns.add(new CsvColumnImpl("tmall_use_flg", "service.data.csv.CCommodityDetailCsvSchema.31", CsvDataType.BIGDECIMAL));
          sql += ",CD.TMALL_USE_FLG";
          // 規格1値のID(=TMALL属性値ID)
        } else if (item.equals("12")) {
          columns.add(new CsvColumnImpl("standard_detail1_id", "service.data.csv.CCommodityDetailCsvSchema.8", CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL1_ID";
          // 規格1値の文字列(値のIDなければ、これを利用）
        } else if (item.equals("13")) {
          columns
              .add(new CsvColumnImpl("standard_detail1_name", "service.data.csv.CCommodityDetailCsvSchema.9", CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL1_NAME";
          // 規格1値の文字列(値のIDなければ、これを利用） 英文
        } else if (item.equals("14")) {
          columns.add(new CsvColumnImpl("standard_detail1_name_en", "service.data.csv.CCommodityDetailCsvSchema.99",
              CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL1_NAME_EN";
          // 規格1値の文字列(値のIDなければ、これを利用） 日文
        } else if (item.equals("15")) {
          columns.add(new CsvColumnImpl("standard_detail1_name_jp", "service.data.csv.CCommodityDetailCsvSchema.100",
              CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL1_NAME_JP";
          // 規格2値のID(=TMALL属性値ID)
        } else if (item.equals("16")) {
          columns
              .add(new CsvColumnImpl("standard_detail2_id", "service.data.csv.CCommodityDetailCsvSchema.10", CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL2_ID";
          // 規格2値の文字列(値のIDなければ、これを利用）
        } else if (item.equals("17")) {
          columns.add(new CsvColumnImpl("standard_detail2_name", "service.data.csv.CCommodityDetailCsvSchema.11",
              CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL2_NAME";
          // 規格2値の文字列(値のIDなければ、これを利用）英文
        } else if (item.equals("18")) {
          columns.add(new CsvColumnImpl("standard_detail2_name_en", "service.data.csv.CCommodityDetailCsvSchema.101",
              CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL2_NAME_EN";
          // 規格2値の文字列(値のIDなければ、これを利用）日文
        } else if (item.equals("19")) {
          columns.add(new CsvColumnImpl("standard_detail2_name_jp", "service.data.csv.CCommodityDetailCsvSchema.102",
              CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL2_NAME_JP";
          // 在庫警告日数
        } else if (item.equals("20")) {
          columns.add(new CsvColumnImpl("stock_warning", "service.data.csv.CCommodityDetailCsvSchema.19", CsvDataType.NUMBER));
          sql += ",CD.STOCK_WARNING";
          // 最小仕入数
        } else if (item.equals("21")) {
          columns.add(new CsvColumnImpl("minimum_order", "service.data.csv.CCommodityDetailCsvSchema.16", CsvDataType.NUMBER));
          sql += ",CD.MINIMUM_ORDER";
          // 最大仕入数
        } else if (item.equals("22")) {
          columns.add(new CsvColumnImpl("maximum_order", "service.data.csv.CCommodityDetailCsvSchema.17", CsvDataType.NUMBER));
          sql += ",CD.MAXIMUM_ORDER";
          // 最小単位の仕入数
        } else if (item.equals("23")) {
          columns.add(new CsvColumnImpl("order_multiple", "service.data.csv.CCommodityDetailCsvSchema.18", CsvDataType.NUMBER));
          sql += ",CD.ORDER_MULTIPLE";
          // 商品重量(単位はKG)、未設定の場合、商品HEADの重量を利用。
        } else if (item.equals("24")) {
          columns.add(new CsvColumnImpl("weight", "service.data.csv.CCommodityDetailCsvSchema.12", CsvDataType.BIGDECIMAL));
          sql += ",CD.WEIGHT";
          // 税率区分
        } else if (item.equals("25")) {
          columns.add(new CsvColumnImpl("tax_class", "service.data.csv.CCommodityDetailCsvSchema.32", CsvDataType.STRING));
          sql += ",CD.TAX_CLASS";
          // 箱规
        } else if (item.equals("26")) {
          columns.add(new CsvColumnImpl("unit_box", "service.data.csv.CCommodityDetailCsvSchema.103", CsvDataType.NUMBER));
          sql += ",CD.UNIT_BOX";
          // 入数
        } else if (item.equals("27")) {
          columns.add(new CsvColumnImpl("inner_quantity", "service.data.csv.CCommodityDetailCsvSchema.27", CsvDataType.NUMBER));
          sql += ",CD.INNER_QUANTITY";
          // 入数単位
        } else if (item.equals("28")) {
          columns
              .add(new CsvColumnImpl("inner_quantity_unit", "service.data.csv.CCommodityDetailCsvSchema.28", CsvDataType.STRING));
          sql += ",CD.INNER_QUANTITY_UNIT";
          // WEB表示単価単位入数
        } else if (item.equals("29")) {
          columns
              .add(new CsvColumnImpl("inner_unit_for_price", "service.data.csv.CCommodityDetailCsvSchema.29", CsvDataType.NUMBER));
          sql += ",CD.INNER_UNIT_FOR_PRICE";
          // 容量
        } else if (item.equals("30")) {
          columns.add(new CsvColumnImpl("volume", "service.data.csv.CCommodityDetailCsvSchema.13", CsvDataType.NUMBER));
          sql += ",CD.VOLUME";
          // 容量単位
        } else if (item.equals("31")) {
          columns.add(new CsvColumnImpl("volume_unit", "service.data.csv.CCommodityDetailCsvSchema.14", CsvDataType.STRING));
          sql += ",CD.VOLUME_UNIT";
          // WEB表示単価単位容量
        } else if (item.equals("32")) {
          columns.add(new CsvColumnImpl("volume_unit_for_price", "service.data.csv.CCommodityDetailCsvSchema.30",
              CsvDataType.NUMBER));
          sql += ",CD.VOLUME_UNIT_FOR_PRICE";
        // 2014/04/29 京东WBS对应 ob_卢 add start
        } else if (item.equals("33")) { //JD使用标志
          columns.add(new CsvColumnImpl("jd_use_flg",
              "service.data.csv.CCommodityDetailCsvSchema.104", CsvDataType.NUMBER));
          sql += ",CD.JD_USE_FLG";
        // 2014/04/29 京东WBS对应 ob_卢 add end
          /**
           * 库存
           */
          // EC在庫割合(0-100)
        // 2014/04/29 京东WBS对应 ob_卢 update start
        //} else if (item.equals("33")) {
        } else if (item.equals("34")) {
        // 2014/04/29 京东WBS对应 ob_卢 update end
       // 2014/06/11 库存更新对应 ob_yuan update start
          //columns.add(new CsvColumnImpl("share_ratio", "service.data.csv.CCommodityStockDataCsvSchema.12", CsvDataType.NUMBER));
          //sql += ",SK.SHARE_RATIO";
          columns.add(new CsvColumnImpl("share_ratio", "service.data.csv.CCommodityStockDataCsvSchema.12", CsvDataType.STRING));
          sql += ",get_stock_ratio_func(SK.SHOP_CODE,SK.COMMODITY_CODE)";
       // 2014/06/11 库存更新对应 ob_yuan update end
          
          // 安全在庫
          // 2014/04/29 京东WBS对应 ob_卢 update start
        //} else if (item.equals("34")) {
        } else if (item.equals("35")) {
          // 2014/04/29 京东WBS对应 ob_卢 update end
          columns.add(new CsvColumnImpl("stock_threshold", "service.data.csv.CCommodityStockDataCsvSchema.7", CsvDataType.NUMBER));
          sql += ",SK.STOCK_THRESHOLD";

        } else if(item.equals("36")) {
          columns.add(new CsvColumnImpl("average_cost", "service.data.csv.CCommodityStockDataCsvSchema.15", CsvDataType.NUMBER));
          sql += ",CD.AVERAGE_COST";
        }

      }
      CCommodityDetailExportCondition condition = CsvExportType.EXPORT_CSV_COMMODITY_DETAIL_DATA.createConditionInstance();
      condition.setSkuCode(bean.getSearchSkuCode());
      condition.setCommodityCode(bean.getSearchCommodityCode());
      condition.setColumns(columns);
      condition.setCombineType(bean.getCombineObject());
      condition.setSqlString(sql);
      if (bean.getSearchExportTemplateFlg().equals("2")) {
        condition.setHeaderOnly(true);
      } else {
        condition.setHeaderOnly(false);
      }
      this.exportCondition = condition;
      // 下载商品基本+明细
    } else if (bean.getSearchExportObject().equals("2")) {
      // 商品コード
      columns.add(new CsvColumnImpl("commodity_code", "service.data.csv.CCommodityDetailCsvSchema.2", CsvDataType.STRING, false,
          false, true, null));
      // SKUコード
      columns.add(new CsvColumnImpl("sku_code", "service.data.csv.CCommodityDetailCsvSchema.0", CsvDataType.STRING, false, false,
          true, null));
      sql += ",'''' || CD.SKU_CODE AS SKU_CODE";
      for (String item : bean.getHeaderItem()) {// 商品名称
        if (item.equals("1")) {
          columns.add(new CsvColumnImpl("commodity_name", "service.data.csv.CCommodityHeaderCsvSchema.2", CsvDataType.STRING,
              false, false, true, null));
          sql += ",CH.COMMODITY_NAME";
          // 商品名称英字
        } else if (item.equals("2")) {
          columns.add(new CsvColumnImpl("commodity_name_en", "service.data.csv.CCommodityHeaderCsvSchema.3", CsvDataType.STRING));
          sql += ",CH.COMMODITY_NAME_EN";
          // 商品名称日字
        } else if (item.equals("3")) {
          columns.add(new CsvColumnImpl("commodity_name_jp", "service.data.csv.CCommodityHeaderCsvSchema.86", CsvDataType.STRING));
          sql += ",CH.COMMODITY_NAME_JP";
          // ブランドコード
        } else if (item.equals("4")) {
          columns.add(new CsvColumnImpl("brand_code", "service.data.csv.CCommodityHeaderCsvSchema.26", CsvDataType.STRING));
          sql += ",CH.BRAND_CODE";
          // TMALLのカテゴリID
        } else if (item.equals("5")) {
          columns.add(new CsvColumnImpl("tmall_category_id", "service.data.csv.CCommodityHeaderCsvSchema.29", CsvDataType.NUMBER));
          sql += ",CH.TMALL_CATEGORY_ID";
          // 取引先コード
        } else if (item.equals("6")) {
          columns.add(new CsvColumnImpl("supplier_code", "service.data.csv.CCommodityHeaderCsvSchema.30", CsvDataType.STRING));
          sql += ",CH.SUPPLIER_CODE";
          // セール区分
        } else if (item.equals("7")) {
          columns.add(new CsvColumnImpl("sale_flag", "service.data.csv.CCommodityHeaderCsvSchema.24", CsvDataType.STRING));
          sql += ",CH.SALE_FLAG";
          // 特集区分
        } else if (item.equals("8")) {
          columns.add(new CsvColumnImpl("spec_flag", "service.data.csv.CCommodityHeaderCsvSchema.25", CsvDataType.STRING));
          sql += ",CH.SPEC_FLAG";
          // 商品説明1
        } else if (item.equals("9")) {
          columns.add(new CsvColumnImpl("commodity_description1", "service.data.csv.CCommodityHeaderCsvSchema.6",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION1";
          // 商品説明1 英字
        } else if (item.equals("10")) {
          columns.add(new CsvColumnImpl("commodity_description1_en", "service.data.csv.CCommodityHeaderCsvSchema.87",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION1_EN";
          // 商品説明1 日字
        } else if (item.equals("11")) {
          columns.add(new CsvColumnImpl("commodity_description1_jp", "service.data.csv.CCommodityHeaderCsvSchema.88",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION1_JP";
          // 商品説明2
        } else if (item.equals("12")) {
          columns.add(new CsvColumnImpl("commodity_description2", "service.data.csv.CCommodityHeaderCsvSchema.7",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION2";
          // 商品説明2 英字
        } else if (item.equals("13")) {
          columns.add(new CsvColumnImpl("commodity_description2_en", "service.data.csv.CCommodityHeaderCsvSchema.89",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION2_EN";
          // 商品説明2 日字
        } else if (item.equals("14")) {
          columns.add(new CsvColumnImpl("commodity_description2_jp", "service.data.csv.CCommodityHeaderCsvSchema.90",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION2_JP";
          // 商品説明3
        } else if (item.equals("15")) {
          columns.add(new CsvColumnImpl("commodity_description3", "service.data.csv.CCommodityHeaderCsvSchema.8",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION3";
          // 商品説明3 英字
        } else if (item.equals("16")) {
          columns.add(new CsvColumnImpl("commodity_description3_en", "service.data.csv.CCommodityHeaderCsvSchema.91",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION3_EN";
          // 商品説明3 英字
        } else if (item.equals("17")) {
          columns.add(new CsvColumnImpl("commodity_description3_jp", "service.data.csv.CCommodityHeaderCsvSchema.92",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION3_JP";
          // 商品説明(一覧用）
        } else if (item.equals("18")) {
          columns.add(new CsvColumnImpl("commodity_description_short", "service.data.csv.CCommodityHeaderCsvSchema.9",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION_SHORT";
          // 商品説明(一覧用） 英字
        } else if (item.equals("19")) {
          columns.add(new CsvColumnImpl("commodity_description_short_en", "service.data.csv.CCommodityHeaderCsvSchema.93",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION_SHORT_EN";
          // 商品説明(一覧用） 英字
        } else if (item.equals("20")) {
          columns.add(new CsvColumnImpl("commodity_description_short_jp", "service.data.csv.CCommodityHeaderCsvSchema.94",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_DESCRIPTION_SHORT_JP";
          // 商品検索ワード
        } else if (item.equals("21")) {
          columns.add(new CsvColumnImpl("commodity_search_words", "service.data.csv.CCommodityHeaderCsvSchema.10",
              CsvDataType.STRING));
          sql += ",CH.COMMODITY_SEARCH_WORDS";
          // 販売開始日時
        } else if (item.equals("22")) {
          columns
              .add(new CsvColumnImpl("sale_start_datetime", "service.data.csv.CCommodityHeaderCsvSchema.11", CsvDataType.STRING));
          sql += ", CASE CH.SALE_START_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMin())
              + "' THEN NULL ELSE '''' || CH.SALE_START_DATETIME END AS SALE_START_DATETIME ";
          // 販売終了日時
        } else if (item.equals("23")) {
          columns.add(new CsvColumnImpl("sale_end_datetime", "service.data.csv.CCommodityHeaderCsvSchema.12", CsvDataType.STRING));
          sql += ",CASE CH.SALE_END_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMax())
              + "' THEN NULL ELSE '''' || CH.SALE_END_DATETIME END AS SALE_END_DATETIME ";
          // 特別価格開始日時
        } else if (item.equals("24")) {
          columns.add(new CsvColumnImpl("discount_price_start_datetime", "service.data.csv.CCommodityHeaderCsvSchema.13",
              CsvDataType.STRING));
          sql += ",CASE CH.DISCOUNT_PRICE_START_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMin())
              + "' THEN NULL ELSE '''' || CH.DISCOUNT_PRICE_START_DATETIME END AS DISCOUNT_PRICE_START_DATETIME ";
          // 特別価格終了日時
        } else if (item.equals("25")) {
          columns.add(new CsvColumnImpl("discount_price_end_datetime", "service.data.csv.CCommodityHeaderCsvSchema.14",
              CsvDataType.STRING));
          sql += ",CASE CH.DISCOUNT_PRICE_END_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMax())
              + "' THEN NULL ELSE '''' || CH.DISCOUNT_PRICE_END_DATETIME END AS DISCOUNT_PRICE_END_DATETIME ";
          // ワーニング区分
        } else if (item.equals("26")) {
          columns.add(new CsvColumnImpl("warning_flag", "service.data.csv.CCommodityHeaderCsvSchema.22", CsvDataType.STRING));
          sql += ",CH.WARNING_FLAG";
          // 規格1名称ID(TMALLの属性ID）
        } else if (item.equals("27")) {
          columns.add(new CsvColumnImpl("standard1_id", "service.data.csv.CCommodityHeaderCsvSchema.15", CsvDataType.STRING));
          sql += ",CH.STANDARD1_ID";
          // 規格1名称
        } else if (item.equals("28")) {
          columns.add(new CsvColumnImpl("standard1_name", "service.data.csv.CCommodityHeaderCsvSchema.16", CsvDataType.STRING));
          sql += ",CH.STANDARD1_NAME";
          // 規格1名称 英字
        } else if (item.equals("29")) {
          columns.add(new CsvColumnImpl("standard1_name_en", "service.data.csv.CCommodityHeaderCsvSchema.95", CsvDataType.STRING));
          sql += ",CH.STANDARD1_NAME_EN";
          // 規格1名称 日字
        } else if (item.equals("30")) {
          columns.add(new CsvColumnImpl("standard1_name_jp", "service.data.csv.CCommodityHeaderCsvSchema.96", CsvDataType.STRING));
          sql += ",CH.STANDARD1_NAME_JP";
          // 規格2名称ID(TMALLの属性ID）
        } else if (item.equals("31")) {
          columns.add(new CsvColumnImpl("standard2_id", "service.data.csv.CCommodityHeaderCsvSchema.17", CsvDataType.STRING));
          sql += ",CH.STANDARD2_ID";
          // 規格2名称
        } else if (item.equals("32")) {
          columns.add(new CsvColumnImpl("standard2_name", "service.data.csv.CCommodityHeaderCsvSchema.18", CsvDataType.STRING));
          sql += ",CH.STANDARD2_NAME";
          // 規格2名称 英文
        } else if (item.equals("33")) {
          columns.add(new CsvColumnImpl("standard2_name_en", "service.data.csv.CCommodityHeaderCsvSchema.97", CsvDataType.STRING));
          sql += ",CH.STANDARD2_NAME_EN";
          // 規格2名称 日文
        } else if (item.equals("34")) {
          columns.add(new CsvColumnImpl("standard2_name_jp", "service.data.csv.CCommodityHeaderCsvSchema.98", CsvDataType.STRING));
          sql += ",CH.STANDARD2_NAME_JP";
          // EC販売フラグ
        } else if (item.equals("35")) {
          columns.add(new CsvColumnImpl("sale_flg_ec", "service.data.csv.CCommodityHeaderCsvSchema.19", CsvDataType.NUMBER));
          sql += ",CH.SALE_FLG_EC";
          // 返品不可フラグ
          // 供货商换货
        } else if (item.equals("38")) {
          columns.add(new CsvColumnImpl("change_flg_supp", "service.data.csv.CCommodityHeaderCsvSchema.85", CsvDataType.STRING));
          sql += ",CASE WHEN CH.RETURN_FLG = 0 THEN 0 " + "      WHEN CH.RETURN_FLG = 1 THEN 0 "
              + "      WHEN CH.RETURN_FLG = 2 THEN 0 " + "      WHEN CH.RETURN_FLG = 3 THEN 0 ELSE 1 END CHANGE_FLG_SUPP";
        
          // 供货商退货
        } else if (item.equals("37")) {
          columns.add(new CsvColumnImpl("return_flg_supp", "service.data.csv.CCommodityHeaderCsvSchema.84", CsvDataType.STRING));
          sql += ",CASE WHEN CH.RETURN_FLG = 0 THEN 0 " + "      WHEN CH.RETURN_FLG = 1 THEN 0 "
              + "      WHEN CH.RETURN_FLG = 4 THEN 0 " + "      WHEN CH.RETURN_FLG = 5 THEN 0 ELSE 1 END RETURN_FLG_SUPP";
          // 顾客退货
        } else if (item.equals("36")) {
          columns.add(new CsvColumnImpl("return_flg_cust", "service.data.csv.CCommodityHeaderCsvSchema.83", CsvDataType.STRING));
          sql += ",CASE WHEN CH.RETURN_FLG = 0 THEN 0 " + "      WHEN CH.RETURN_FLG = 2 THEN 0 "
              + "      WHEN CH.RETURN_FLG = 4 THEN 0 " + "      WHEN CH.RETURN_FLG = 6 THEN 0 ELSE 1 END RETURN_FLG_CUST ";
          // リードタイム
        } else if (item.equals("39")) {
          columns.add(new CsvColumnImpl("lead_time", "service.data.csv.CCommodityHeaderCsvSchema.23", CsvDataType.NUMBER));
          sql += ",CH.LEAD_TIME";
          // 産地
        } else if (item.equals("40")) {
          columns.add(new CsvColumnImpl("original_code", "service.data.csv.CCommodityHeaderCsvSchema.33", CsvDataType.STRING));
          sql += ",CH.ORIGINAL_CODE";
          // 大物フラグ
        } else if (item.equals("41")) {
          columns.add(new CsvColumnImpl("big_flag", "service.data.csv.CCommodityHeaderCsvSchema.82", CsvDataType.NUMBER));
          sql += ",CH.BIG_FLAG";
          // 商品期限管理フラグ 0管理しない/1賞味期限日/2製造日＋保管日数
        } else if (item.equals("42")) {
          columns.add(new CsvColumnImpl("shelf_life_flag", "service.data.csv.CCommodityHeaderCsvSchema.79", CsvDataType.NUMBER));
          sql += ",CH.SHELF_LIFE_FLAG";
          // 保管日数
        } else if (item.equals("43")) {
          columns.add(new CsvColumnImpl("shelf_life_days", "service.data.csv.CCommodityHeaderCsvSchema.80", CsvDataType.NUMBER));
          sql += ",CH.SHELF_LIFE_DAYS";
          // 仕入担当者コード
        } else if (item.equals("44")) {
          columns.add(new CsvColumnImpl("buyer_code", "service.data.csv.CCommodityHeaderCsvSchema.31", CsvDataType.STRING));
          sql += ",CH.BUYER_CODE";
          // 原材料1
        } else if (item.equals("45")) {
          columns.add(new CsvColumnImpl("material1", "service.data.csv.CCommodityHeaderCsvSchema.64", CsvDataType.STRING));
          sql += ",CH.MATERIAL1";
          //
        } else if (item.equals("46")) {
          columns.add(new CsvColumnImpl("material2", "service.data.csv.CCommodityHeaderCsvSchema.65", CsvDataType.STRING));
          sql += ",CH.MATERIAL2";
          //
        } else if (item.equals("47")) {
          columns.add(new CsvColumnImpl("material3", "service.data.csv.CCommodityHeaderCsvSchema.66", CsvDataType.STRING));
          sql += ",CH.MATERIAL3";
          //
        } else if (item.equals("48")) {
          columns.add(new CsvColumnImpl("material4", "service.data.csv.CCommodityHeaderCsvSchema.67", CsvDataType.STRING));
          sql += ",CH.MATERIAL4";
          //
        } else if (item.equals("49")) {
          columns.add(new CsvColumnImpl("material5", "service.data.csv.CCommodityHeaderCsvSchema.68", CsvDataType.STRING));
          sql += ",CH.MATERIAL5";
          //
        } else if (item.equals("50")) {
          columns.add(new CsvColumnImpl("material6", "service.data.csv.CCommodityHeaderCsvSchema.69", CsvDataType.STRING));
          sql += ",CH.MATERIAL6";
          //
        } else if (item.equals("51")) {
          columns.add(new CsvColumnImpl("material7", "service.data.csv.CCommodityHeaderCsvSchema.70", CsvDataType.STRING));
          sql += ",CH.MATERIAL7";
          //
        } else if (item.equals("52")) {
          columns.add(new CsvColumnImpl("material8", "service.data.csv.CCommodityHeaderCsvSchema.71", CsvDataType.STRING));
          sql += ",CH.MATERIAL8";
          //
        } else if (item.equals("53")) {
          columns.add(new CsvColumnImpl("material9", "service.data.csv.CCommodityHeaderCsvSchema.72", CsvDataType.STRING));
          sql += ",CH.MATERIAL9";
          // 原材料10
        } else if (item.equals("54")) {
          columns.add(new CsvColumnImpl("material10", "service.data.csv.CCommodityHeaderCsvSchema.73", CsvDataType.STRING));
          sql += ",CH.MATERIAL10";
          // 原材料11
        } else if (item.equals("55")) {
          columns.add(new CsvColumnImpl("material11", "service.data.csv.CCommodityHeaderCsvSchema.74", CsvDataType.STRING));
          sql += ",CH.MATERIAL11";
          // 原材料12
        } else if (item.equals("56")) {
          columns.add(new CsvColumnImpl("material12", "service.data.csv.CCommodityHeaderCsvSchema.75", CsvDataType.STRING));
          sql += ",CH.MATERIAL12";
          // 原材料13
        } else if (item.equals("57")) {
          columns.add(new CsvColumnImpl("material13", "service.data.csv.CCommodityHeaderCsvSchema.76", CsvDataType.STRING));
          sql += ",CH.MATERIAL13";
          // 原材料14
        } else if (item.equals("58")) {
          columns.add(new CsvColumnImpl("material14", "service.data.csv.CCommodityHeaderCsvSchema.77", CsvDataType.STRING));
          sql += ",CH.MATERIAL14";
          // 原材料15
        } else if (item.equals("59")) {
          columns.add(new CsvColumnImpl("material15", "service.data.csv.CCommodityHeaderCsvSchema.78", CsvDataType.STRING));
          sql += ",CH.MATERIAL15";
          // 成分1
        } else if (item.equals("60")) {
          columns.add(new CsvColumnImpl("ingredient_name1", "service.data.csv.CCommodityHeaderCsvSchema.34", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME1";
          // 成分量1
        } else if (item.equals("61")) {
          columns.add(new CsvColumnImpl("ingredient_val1", "service.data.csv.CCommodityHeaderCsvSchema.35", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL1";
          //
        } else if (item.equals("62")) {
          columns.add(new CsvColumnImpl("ingredient_name2", "service.data.csv.CCommodityHeaderCsvSchema.36", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME2";
          //
        } else if (item.equals("63")) {
          columns.add(new CsvColumnImpl("ingredient_val2", "service.data.csv.CCommodityHeaderCsvSchema.37", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL2";
          //
        } else if (item.equals("64")) {
          columns.add(new CsvColumnImpl("ingredient_name3", "service.data.csv.CCommodityHeaderCsvSchema.38", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME3";
          //
        } else if (item.equals("65")) {
          columns.add(new CsvColumnImpl("ingredient_val3", "service.data.csv.CCommodityHeaderCsvSchema.39", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL3";
          //
        } else if (item.equals("66")) {
          columns.add(new CsvColumnImpl("ingredient_name4", "service.data.csv.CCommodityHeaderCsvSchema.40", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME4";
          //
        } else if (item.equals("67")) {
          columns.add(new CsvColumnImpl("ingredient_val4", "service.data.csv.CCommodityHeaderCsvSchema.41", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL4";
          //
        } else if (item.equals("68")) {
          columns.add(new CsvColumnImpl("ingredient_name5", "service.data.csv.CCommodityHeaderCsvSchema.42", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME5";
          //
        } else if (item.equals("69")) {
          columns.add(new CsvColumnImpl("ingredient_val5", "service.data.csv.CCommodityHeaderCsvSchema.43", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL5";
          //
        } else if (item.equals("70")) {
          columns.add(new CsvColumnImpl("ingredient_name6", "service.data.csv.CCommodityHeaderCsvSchema.44", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME6";
          //
        } else if (item.equals("71")) {
          columns.add(new CsvColumnImpl("ingredient_val6", "service.data.csv.CCommodityHeaderCsvSchema.45", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL6";
          //
        } else if (item.equals("72")) {
          columns.add(new CsvColumnImpl("ingredient_name7", "service.data.csv.CCommodityHeaderCsvSchema.46", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME7";
          //
        } else if (item.equals("73")) {
          columns.add(new CsvColumnImpl("ingredient_val7", "service.data.csv.CCommodityHeaderCsvSchema.47", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL7";
          //
        } else if (item.equals("74")) {
          columns.add(new CsvColumnImpl("ingredient_name8", "service.data.csv.CCommodityHeaderCsvSchema.48", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME8";
          //
        } else if (item.equals("75")) {
          columns.add(new CsvColumnImpl("ingredient_val8", "service.data.csv.CCommodityHeaderCsvSchema.49", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL8";
          //
        } else if (item.equals("76")) {
          columns.add(new CsvColumnImpl("ingredient_name9", "service.data.csv.CCommodityHeaderCsvSchema.50", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME9";
          //
        } else if (item.equals("77")) {
          columns.add(new CsvColumnImpl("ingredient_val9", "service.data.csv.CCommodityHeaderCsvSchema.51", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL9";
          // 成分10
        } else if (item.equals("78")) {
          columns.add(new CsvColumnImpl("ingredient_name10", "service.data.csv.CCommodityHeaderCsvSchema.52", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME10";
          // 成分量10
        } else if (item.equals("79")) {
          columns.add(new CsvColumnImpl("ingredient_val10", "service.data.csv.CCommodityHeaderCsvSchema.53", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL10";
          // 成分11
        } else if (item.equals("80")) {
          columns.add(new CsvColumnImpl("ingredient_name11", "service.data.csv.CCommodityHeaderCsvSchema.54", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME11";
          // 成分量11
        } else if (item.equals("81")) {
          columns.add(new CsvColumnImpl("ingredient_val11", "service.data.csv.CCommodityHeaderCsvSchema.55", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL11";
          // 成分12
        } else if (item.equals("82")) {
          columns.add(new CsvColumnImpl("ingredient_name12", "service.data.csv.CCommodityHeaderCsvSchema.56", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME12";
          // 成分量12
        } else if (item.equals("83")) {
          columns.add(new CsvColumnImpl("ingredient_val12", "service.data.csv.CCommodityHeaderCsvSchema.57", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL12";
          // 成分13
        } else if (item.equals("84")) {
          columns.add(new CsvColumnImpl("ingredient_name13", "service.data.csv.CCommodityHeaderCsvSchema.58", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME13";
          // 成分量13
        } else if (item.equals("85")) {
          columns.add(new CsvColumnImpl("ingredient_val13", "service.data.csv.CCommodityHeaderCsvSchema.59", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL13";
          // 成分14
        } else if (item.equals("86")) {
          columns.add(new CsvColumnImpl("ingredient_name14", "service.data.csv.CCommodityHeaderCsvSchema.60", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME14";
          // 成分量14
        } else if (item.equals("87")) {
          columns.add(new CsvColumnImpl("ingredient_val14", "service.data.csv.CCommodityHeaderCsvSchema.61", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL14";
          // 成分15
        } else if (item.equals("88")) {
          columns.add(new CsvColumnImpl("ingredient_name15", "service.data.csv.CCommodityHeaderCsvSchema.62", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_NAME15";
          // 成分量15
        } else if (item.equals("89")) {
          columns.add(new CsvColumnImpl("ingredient_val15", "service.data.csv.CCommodityHeaderCsvSchema.63", CsvDataType.STRING));
          sql += ",CH.INGREDIENT_VAL15";
        } else if (item.equals("90")) { // 生产许可证号
          columns.add(new CsvColumnImpl("prd_license_no", "service.data.csv.CCommodityHeaderCsvSchema.104", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_PRD_LICENSE_NO";
        } else if (item.equals("91")) { // 产品标准号
          columns.add(new CsvColumnImpl("design_code", "service.data.csv.CCommodityHeaderCsvSchema.105", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_DESIGN_CODE";
        } else if (item.equals("92")) { // 厂名
          columns.add(new CsvColumnImpl("factory", "service.data.csv.CCommodityHeaderCsvSchema.106", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_FACTORY";
        } else if (item.equals("93")) { // 厂址
          columns.add(new CsvColumnImpl("factory_site", "service.data.csv.CCommodityHeaderCsvSchema.107", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_FACTORY_SITE";
        } else if (item.equals("94")) { // 厂家联系方式
          columns.add(new CsvColumnImpl("contact", "service.data.csv.CCommodityHeaderCsvSchema.108", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_CONTACT";
        } else if (item.equals("95")) { // 配料表
          columns.add(new CsvColumnImpl("mix", "service.data.csv.CCommodityHeaderCsvSchema.109", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_MIX";
        } else if (item.equals("96")) { // 储藏方法
          columns.add(new CsvColumnImpl("plan_storage", "service.data.csv.CCommodityHeaderCsvSchema.110", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_PLAN_STORAGE";
        } else if (item.equals("97")) { // 保质期
          columns.add(new CsvColumnImpl("period", "service.data.csv.CCommodityHeaderCsvSchema.111", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_PERIOD";
        } else if (item.equals("98")) { // 食品添加剂
          columns.add(new CsvColumnImpl("food_additive", "service.data.csv.CCommodityHeaderCsvSchema.112", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_FOOD_ADDITIVE";
        } else if (item.equals("99")) { // 供货商
          columns.add(new CsvColumnImpl("supplier", "service.data.csv.CCommodityHeaderCsvSchema.113", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_SUPPLIER";
        } else if (item.equals("100")) { // 生产开始日期
          columns
              .add(new CsvColumnImpl("product_date_start", "service.data.csv.CCommodityHeaderCsvSchema.114", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_PRODUCT_DATE_START";
        } else if (item.equals("101")) { // 生产结束日期
          columns.add(new CsvColumnImpl("product_date_end", "service.data.csv.CCommodityHeaderCsvSchema.115", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_PRODUCT_DATE_END";
        } else if (item.equals("102")) { // 进货开始日期
          columns.add(new CsvColumnImpl("stock_date_start", "service.data.csv.CCommodityHeaderCsvSchema.116", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_STOCK_DATE_START";
        } else if (item.equals("103")) { // 进货结束日期
          columns.add(new CsvColumnImpl("stock_date_end", "service.data.csv.CCommodityHeaderCsvSchema.117", CsvDataType.STRING));
          sql += ",CH.FOOD_SECURITY_STOCK_DATE_END";
        } else if (item.equals("104")) { // 入库效期
          columns
              .add(new CsvColumnImpl("in_bound_life_days", "service.data.csv.CCommodityHeaderCsvSchema.121", CsvDataType.NUMBER));
          sql += ",CH.IN_BOUND_LIFE_DAYS";
        } else if (item.equals("105")) { // 入库效期
          columns
              .add(new CsvColumnImpl("out_bound_life_days", "service.data.csv.CCommodityHeaderCsvSchema.122", CsvDataType.NUMBER));
          sql += ",CH.OUT_BOUND_LIFE_DAYS";
        } else if (item.equals("106")) { // 入库效期
          columns.add(new CsvColumnImpl("shelf_life_alert_days", "service.data.csv.CCommodityHeaderCsvSchema.123",
              CsvDataType.NUMBER));
          sql += ",CH.SHELF_LIFE_ALERT_DAYS";
        } else if (item.equals("107")) { // tmall满就送(赠品标志)
          columns.add(new CsvColumnImpl("tmall_mjs_flg", "service.data.csv.CCommodityHeaderCsvSchema.124", CsvDataType.NUMBER));
          sql += ",CH.TMALL_MJS_FLG";
        } else if (item.equals("108")) { // 商品区分
          columns.add(new CsvColumnImpl("commodity_type", "service.data.csv.CCommodityHeaderCsvSchema.120", CsvDataType.NUMBER));
          sql += ",CH.COMMODITY_TYPE";
        } else if (item.equals("109")) { // 进口商品区分
          columns.add(new CsvColumnImpl("import_commodity_type", "service.data.csv.CCommodityHeaderCsvSchema.125",
              CsvDataType.NUMBER));
          sql += ",CH.IMPORT_COMMODITY_TYPE";
        } else if (item.equals("110")) { // 清仓商品区分
          columns.add(new CsvColumnImpl("clear_commodity_type", "service.data.csv.CCommodityHeaderCsvSchema.126",
              CsvDataType.NUMBER));
          sql += ",CH.CLEAR_COMMODITY_TYPE";
        } else if (item.equals("111")) { // Asahi商品区分
          columns.add(new CsvColumnImpl("reserve_commodity_type1", "service.data.csv.CCommodityHeaderCsvSchema.127",
              CsvDataType.NUMBER));
          sql += ",CH.RESERVE_COMMODITY_TYPE1";
        } else if (item.equals("112")) { // hot商品区分
          columns.add(new CsvColumnImpl("reserve_commodity_type2", "service.data.csv.CCommodityHeaderCsvSchema.128",
              CsvDataType.NUMBER));
          sql += ",CH.RESERVE_COMMODITY_TYPE2";
        } else if (item.equals("113")) { // 商品展示区分
          columns.add(new CsvColumnImpl("reserve_commodity_type3", "service.data.csv.CCommodityHeaderCsvSchema.129",
              CsvDataType.NUMBER));
          sql += ",CH.RESERVE_COMMODITY_TYPE3";
        } else if (item.equals("114")) { // 预留区分1*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type1", "service.data.csv.CCommodityHeaderCsvSchema.130",
              CsvDataType.NUMBER));
          sql += ",CH.NEW_RESERVE_COMMODITY_TYPE1";
        } else if (item.equals("115")) { // 预留区分2*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type2", "service.data.csv.CCommodityHeaderCsvSchema.131",
              CsvDataType.NUMBER));
          sql += ",CH.NEW_RESERVE_COMMODITY_TYPE2";
        } else if (item.equals("116")) { // 预留区分3*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type3", "service.data.csv.CCommodityHeaderCsvSchema.132",
              CsvDataType.NUMBER));
          sql += ",CH.NEW_RESERVE_COMMODITY_TYPE3";
        } else if (item.equals("117")) { // 预留区分4*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type4", "service.data.csv.CCommodityHeaderCsvSchema.133",
              CsvDataType.NUMBER));
          sql += ",CH.NEW_RESERVE_COMMODITY_TYPE4";
        } else if (item.equals("118")) { // 预留区分5*
          columns.add(new CsvColumnImpl("new_reserve_commodity_type5", "service.data.csv.CCommodityHeaderCsvSchema.134",
              CsvDataType.NUMBER));
          sql += ",CH.NEW_RESERVE_COMMODITY_TYPE5";
        } else if (item.equals("119")) { // TMALL检索关键字
          columns.add(new CsvColumnImpl("tmall_commodity_search_words", "service.data.csv.CCommodityHeaderCsvSchema.135",
              CsvDataType.STRING));
          sql += ",CH.TMALL_COMMODITY_SEARCH_WORDS";
          // 20130809 txw add start
        } else if (item.equals("122")) { // TITLE
          columns.add(new CsvColumnImpl("title", "service.data.csv.CCommodityHeaderCsvSchema.138", CsvDataType.STRING));
          sql += ",CH.TITLE";
        } else if (item.equals("123")) { // TITLE(英文)
          columns.add(new CsvColumnImpl("title_en", "service.data.csv.CCommodityHeaderCsvSchema.139", CsvDataType.STRING));
          sql += ",CH.TITLE_EN";
        } else if (item.equals("124")) { // TITLE(日文)
          columns.add(new CsvColumnImpl("title_jp", "service.data.csv.CCommodityHeaderCsvSchema.140", CsvDataType.STRING));
          sql += ",CH.TITLE_JP";
        } else if (item.equals("125")) { // DESCRIPTION
          columns.add(new CsvColumnImpl("description", "service.data.csv.CCommodityHeaderCsvSchema.141", CsvDataType.STRING));
          sql += ",CH.DESCRIPTION";
        } else if (item.equals("126")) { // DESCRIPTION(英文)
          columns.add(new CsvColumnImpl("description_en", "service.data.csv.CCommodityHeaderCsvSchema.142", CsvDataType.STRING));
          sql += ",CH.DESCRIPTION_EN";
        } else if (item.equals("127")) { // DESCRIPTION(日文)
          columns.add(new CsvColumnImpl("description_jp", "service.data.csv.CCommodityHeaderCsvSchema.143", CsvDataType.STRING));
          sql += ",CH.DESCRIPTION_JP";
        } else if (item.equals("128")) { // KEYWORD
          columns.add(new CsvColumnImpl("keyword", "service.data.csv.CCommodityHeaderCsvSchema.144", CsvDataType.STRING));
          sql += ",CH.KEYWORD";
        } else if (item.equals("129")) { // KEYWORD(英文)
          columns.add(new CsvColumnImpl("keyword_en", "service.data.csv.CCommodityHeaderCsvSchema.145", CsvDataType.STRING));
          sql += ",CH.KEYWORD_EN";
        } else if (item.equals("130")) { // KEYWORD(日文)
          columns.add(new CsvColumnImpl("keyword_jp", "service.data.csv.CCommodityHeaderCsvSchema.146", CsvDataType.STRING));
          sql += ",CH.KEYWORD_JP";
        } else if (item.equals("120")) { //English热卖标志
          columns.add(new CsvColumnImpl("hot_flg_en",
              "service.data.csv.CCommodityHeaderCsvSchema.136", CsvDataType.NUMBER));
          sql += ",CH.hot_flg_en";
        } else if (item.equals("121")) { //Japan热卖标志
          columns.add(new CsvColumnImpl("hot_flg_jp",
              "service.data.csv.CCommodityHeaderCsvSchema.137", CsvDataType.NUMBER));
          sql += ",CH.hot_flg_jp";
        } else if (item.equals("131")) { //原商品编号
          columns.add(new CsvColumnImpl("original_commodity_code",
              "service.data.csv.CCommodityHeaderCsvSchema.147", CsvDataType.STRING));
          sql += ",'''' || CH.original_commodity_code as original_commodity_code";
        } else if (item.equals("132")) { //组合数量
          columns.add(new CsvColumnImpl("combination_amount",
              "service.data.csv.CCommodityHeaderCsvSchema.148", CsvDataType.NUMBER));
          sql += ",CH.combination_amount";
        } else if (item.equals("133")) { //検索Keyword中文
          columns.add(new CsvColumnImpl("keyword_cn2",
              "service.data.csv.CCommodityHeaderCsvSchema.149", CsvDataType.STRING));
          sql += ",CH.keyword_cn2";
        } else if (item.equals("134")) { //検索Keyword日文
          columns.add(new CsvColumnImpl("keyword_jp2",
              "service.data.csv.CCommodityHeaderCsvSchema.150", CsvDataType.STRING));
          sql += ",CH.keyword_jp2";
        } else if (item.equals("135")) { //検索Keyword英文
          columns.add(new CsvColumnImpl("keyword_en2",
              "service.data.csv.CCommodityHeaderCsvSchema.151", CsvDataType.STRING));
          sql += ",CH.keyword_en2";
        }
        // 20130809 txw add end
        
        // 2014/04/25 京东WBS对应 ob_卢 add start
        else if (item.equals("136")) { //广告词
          columns.add(new CsvColumnImpl("advert_content",
              "service.data.csv.CCommodityHeaderCsvSchema.152", CsvDataType.STRING));
          sql += ",CH.advert_content";
        }
        // 2014/04/25 京东WBS对应 ob_卢 add end
      }
      for (String item1 : bean.getDetailItem()) {
        // SKU名称
        if (item1.equals("1")) {
          columns.add(new CsvColumnImpl("sku_name", "service.data.csv.CCommodityDetailCsvSchema.1", CsvDataType.STRING));
          sql += ",CD.SKU_NAME";
          // 仕入価格
        } else if (item1.equals("2")) {
          columns.add(new CsvColumnImpl("purchase_price", "service.data.csv.CCommodityDetailCsvSchema.5", CsvDataType.STRING));
          sql += ",CD.PURCHASE_PRICE";
          // 希望小売価格
        } else if (item1.equals("3")) {
          columns.add(new CsvColumnImpl("suggeste_price", "service.data.csv.CCommodityDetailCsvSchema.4", CsvDataType.BIGDECIMAL));
          sql += ",CD.SUGGESTE_PRICE";
          // 下限売価
        } else if (item1.equals("4")) {
          columns.add(new CsvColumnImpl("min_price", "service.data.csv.CCommodityDetailCsvSchema.23", CsvDataType.BIGDECIMAL));
          sql += ",CD.MIN_PRICE";
          // 定価フラグ0：非定価　1：定価
        } else if (item1.equals("5")) {
          columns.add(new CsvColumnImpl("fixed_price_flag", "service.data.csv.CCommodityDetailCsvSchema.3", CsvDataType.NUMBER));
          sql += ",CD.FIXED_PRICE_FLAG";
          // EC商品単価
        } else if (item1.equals("6")) {
          columns.add(new CsvColumnImpl("unit_price", "service.data.csv.CCommodityDetailCsvSchema.6", CsvDataType.BIGDECIMAL));
          sql += ",CD.UNIT_PRICE";
          // TMALLの商品単価
        } else if (item1.equals("7")) {
          columns.add(new CsvColumnImpl("tmall_unit_price", "service.data.csv.CCommodityDetailCsvSchema.21", CsvDataType.NUMBER));
          sql += ",CD.TMALL_UNIT_PRICE";
          // EC特別価格
        } else if (item1.equals("8")) {
          columns.add(new CsvColumnImpl("discount_price", "service.data.csv.CCommodityDetailCsvSchema.7", CsvDataType.BIGDECIMAL));
          sql += ",CD.DISCOUNT_PRICE";
          // TMALLの特別価格
        } else if (item1.equals("9")) {
          columns
              .add(new CsvColumnImpl("tmall_discount_price", "service.data.csv.CCommodityDetailCsvSchema.22", CsvDataType.NUMBER));
          sql += ",CD.TMALL_DISCOUNT_PRICE";
          // 取扱いフラグ(EC)
        } else if (item1.equals("10")) {
          columns.add(new CsvColumnImpl("use_flg", "service.data.csv.CCommodityDetailCsvSchema.15", CsvDataType.BIGDECIMAL));
          sql += ",CD.USE_FLG";
          // 取扱いフラグ(TMALL)
        } else if (item1.equals("11")) {
          columns.add(new CsvColumnImpl("tmall_use_flg", "service.data.csv.CCommodityDetailCsvSchema.31", CsvDataType.BIGDECIMAL));
          sql += ",CD.TMALL_USE_FLG";
          // 規格1値のID(=TMALL属性値ID)
        } else if (item1.equals("12")) {
          columns.add(new CsvColumnImpl("standard_detail1_id", "service.data.csv.CCommodityDetailCsvSchema.8", CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL1_ID";
          // 規格1値の文字列(値のIDなければ、これを利用）
        } else if (item1.equals("13")) {
          columns
              .add(new CsvColumnImpl("standard_detail1_name", "service.data.csv.CCommodityDetailCsvSchema.9", CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL1_NAME";
          // 規格1値の文字列(値のIDなければ、これを利用） 英文
        } else if (item1.equals("14")) {
          columns.add(new CsvColumnImpl("standard_detail1_name_en", "service.data.csv.CCommodityDetailCsvSchema.99",
              CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL1_NAME_EN";
          // 規格1値の文字列(値のIDなければ、これを利用） 日文
        } else if (item1.equals("15")) {
          columns.add(new CsvColumnImpl("standard_detail1_name_jp", "service.data.csv.CCommodityDetailCsvSchema.100",
              CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL1_NAME_JP";
          // 規格2値のID(=TMALL属性値ID)
        } else if (item1.equals("16")) {
          columns
              .add(new CsvColumnImpl("standard_detail2_id", "service.data.csv.CCommodityDetailCsvSchema.10", CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL2_ID";
          // 規格2値の文字列(値のIDなければ、これを利用）
        } else if (item1.equals("17")) {
          columns.add(new CsvColumnImpl("standard_detail2_name", "service.data.csv.CCommodityDetailCsvSchema.11",
              CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL2_NAME";
          // 規格2値の文字列(値のIDなければ、これを利用）英文
        } else if (item1.equals("18")) {
          columns.add(new CsvColumnImpl("standard_detail2_name_en", "service.data.csv.CCommodityDetailCsvSchema.101",
              CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL2_NAME_EN";
          // 規格2値の文字列(値のIDなければ、これを利用）日文
        } else if (item1.equals("19")) {
          columns.add(new CsvColumnImpl("standard_detail2_name_jp", "service.data.csv.CCommodityDetailCsvSchema.102",
              CsvDataType.STRING));
          sql += ",CD.STANDARD_DETAIL2_NAME_JP";
          // 在庫警告日数
        } else if (item1.equals("20")) {
          columns.add(new CsvColumnImpl("stock_warning", "service.data.csv.CCommodityDetailCsvSchema.19", CsvDataType.NUMBER));
          sql += ",CD.STOCK_WARNING";
          // 最小仕入数
        } else if (item1.equals("21")) {
          columns.add(new CsvColumnImpl("minimum_order", "service.data.csv.CCommodityDetailCsvSchema.16", CsvDataType.NUMBER));
          sql += ",CD.MINIMUM_ORDER";
          // 最大仕入数
        } else if (item1.equals("22")) {
          columns.add(new CsvColumnImpl("maximum_order", "service.data.csv.CCommodityDetailCsvSchema.17", CsvDataType.NUMBER));
          sql += ",CD.MAXIMUM_ORDER";
          // 最小単位の仕入数
        } else if (item1.equals("23")) {
          columns.add(new CsvColumnImpl("order_multiple", "service.data.csv.CCommodityDetailCsvSchema.18", CsvDataType.NUMBER));
          sql += ",CD.ORDER_MULTIPLE";
          // 商品重量(単位はKG)、未設定の場合、商品HEADの重量を利用。
        } else if (item1.equals("24")) {
          columns.add(new CsvColumnImpl("weight", "service.data.csv.CCommodityDetailCsvSchema.12", CsvDataType.BIGDECIMAL));
          sql += ",CD.WEIGHT";
          // 税率区分
        } else if (item1.equals("25")) {
          columns.add(new CsvColumnImpl("tax_class", "service.data.csv.CCommodityDetailCsvSchema.32", CsvDataType.STRING));
          sql += ",CD.TAX_CLASS";
          // 箱规
        } else if (item1.equals("26")) {
          columns.add(new CsvColumnImpl("unit_box", "service.data.csv.CCommodityDetailCsvSchema.103", CsvDataType.NUMBER));
          sql += ",CD.UNIT_BOX";
          // 入数
        } else if (item1.equals("27")) {
          columns.add(new CsvColumnImpl("inner_quantity", "service.data.csv.CCommodityDetailCsvSchema.27", CsvDataType.NUMBER));
          sql += ",CD.INNER_QUANTITY";
          // 入数単位
        } else if (item1.equals("28")) {
          columns
              .add(new CsvColumnImpl("inner_quantity_unit", "service.data.csv.CCommodityDetailCsvSchema.28", CsvDataType.STRING));
          sql += ",CD.INNER_QUANTITY_UNIT";
          // WEB表示単価単位入数
        } else if (item1.equals("29")) {
          columns
              .add(new CsvColumnImpl("inner_unit_for_price", "service.data.csv.CCommodityDetailCsvSchema.29", CsvDataType.NUMBER));
          sql += ",CD.INNER_UNIT_FOR_PRICE";
          // 容量
        } else if (item1.equals("30")) {
          columns.add(new CsvColumnImpl("volume", "service.data.csv.CCommodityDetailCsvSchema.13", CsvDataType.NUMBER));
          sql += ",CD.VOLUME";
          // 容量単位
        } else if (item1.equals("31")) {
          columns.add(new CsvColumnImpl("volume_unit", "service.data.csv.CCommodityDetailCsvSchema.14", CsvDataType.STRING));
          sql += ",CD.VOLUME_UNIT";
          // WEB表示単価単位容量
        } else if (item1.equals("32")) {
          columns.add(new CsvColumnImpl("volume_unit_for_price", "service.data.csv.CCommodityDetailCsvSchema.30",
              CsvDataType.NUMBER));
          sql += ",CD.VOLUME_UNIT_FOR_PRICE";
          // 2014/04/29 京东WBS对应 ob_卢 add start
        } else if (item1.equals("33")) { //JD使用标志
            columns.add(new CsvColumnImpl("jd_use_flg", "service.data.csv.CCommodityDetailCsvSchema.104", CsvDataType.NUMBER));
            sql += ",CD.JD_USE_FLG";
          // 2014/04/29 京东WBS对应 ob_卢 add end
          /**
           * 库存
           */
          // EC在庫割合(0-100)
        // 2014/04/29 京东WBS对应 ob_卢 update start
        //} else if (item1.equals("33")) {
        } else if (item1.equals("34")) {
        // 2014/04/29 京东WBS对应 ob_卢 update end
       // 2014/06/11 库存更新对应 ob_yuan update start
          //columns.add(new CsvColumnImpl("share_ratio", "service.data.csv.CCommodityStockDataCsvSchema.12", CsvDataType.NUMBER));
          //sql += ",SK.SHARE_RATIO";
          columns.add(new CsvColumnImpl("share_ratio", "service.data.csv.CCommodityStockDataCsvSchema.12", CsvDataType.STRING));
          sql += ",get_stock_ratio_func(SK.SHOP_CODE,SK.COMMODITY_CODE)";
       // 2014/06/11 库存更新对应 ob_yuan update end
          // 安全在庫
        // 2014/04/29 京东WBS对应 ob_卢 update start
        //} else if (item1.equals("34")) {
        } else if (item1.equals("35")) {
        // 2014/04/29 京东WBS对应 ob_卢 update end
          columns.add(new CsvColumnImpl("stock_threshold", "service.data.csv.CCommodityStockDataCsvSchema.7", CsvDataType.NUMBER));
          sql += ",SK.STOCK_THRESHOLD";
        } else if(item1.equals("36")) {
          columns.add(new CsvColumnImpl("average_cost", "service.data.csv.CCommodityStockDataCsvSchema.15", CsvDataType.NUMBER));
          sql += ",CD.AVERAGE_COST";
        }

      }
      CCommodityDataExportCondition condition = CsvExportType.EXPORT_CSV_COMMODITY_DATA_DATA.createConditionInstance();
      condition.setSkuCode(bean.getSearchSkuCode());
      condition.setCommodityCode(bean.getSearchCommodityCode());
      condition.setColumns(columns);
      condition.setCombineType(bean.getCombineObject());
      condition.setSqlString(sql);
      if (bean.getSearchExportTemplateFlg().equals("2")) {
        condition.setHeaderOnly(true);
      } else {
        condition.setHeaderOnly(false);
      }
      this.exportCondition = condition;
      // 商品分类设置
    } else if (bean.getSearchExportObject().equals("3")) {
      // 20120206 os013 add start
      columns.add(new CsvColumnImpl("commodity_code", "service.data.csv.CategoryAttributeValueDataCsvSchema.1", CsvDataType.STRING,
          false, false, true, null));
      columns.add(new CsvColumnImpl("category_code", "service.data.csv.CategoryAttributeValueDataCsvSchema.2", CsvDataType.STRING,
          false, false, true, null));
      for (String item : bean.getCategoryAttributeValueItem()) {
        if (item.equals("2")) {
          columns.add(new CsvColumnImpl("original_code", "service.data.csv.CategoryAttributeValueDataCsvSchema.60",
              CsvDataType.STRING));
          sql += ",cav.original_code as  original_code";
        } else if (item.equals("3")) {
          columns.add(new CsvColumnImpl("category_attribute_value", "service.data.csv.CategoryAttributeValueDataCsvSchema.20",
              CsvDataType.STRING));
          sql += ",max(category_attribute_value2) as  category_attribute_value2";
        } else if (item.equals("4")) {
          columns.add(new CsvColumnImpl("category_attribute_value", "service.data.csv.CategoryAttributeValueDataCsvSchema.55",
              CsvDataType.STRING));
          sql += ",max(category_attribute_value2_en) as  category_attribute_value2_en";
        } else if (item.equals("5")) {
          columns.add(new CsvColumnImpl("category_attribute_value", "service.data.csv.CategoryAttributeValueDataCsvSchema.58",
              CsvDataType.STRING));
          sql += ",max(category_attribute_value2_jp) as  category_attribute_value2_jp";
        } else if (item.equals("6")) {
          columns.add(new CsvColumnImpl("category_attribute_value", "service.data.csv.CategoryAttributeValueDataCsvSchema.21",
              CsvDataType.STRING));
          sql += ",max(category_attribute_value3) as  category_attribute_value3";
        } else if (item.equals("7")) {
          columns.add(new CsvColumnImpl("category_attribute_value", "service.data.csv.CategoryAttributeValueDataCsvSchema.56",
              CsvDataType.STRING));
          sql += ",max(category_attribute_value3_en) as  category_attribute_value3_en";
        } else if (item.equals("8")) {
          columns.add(new CsvColumnImpl("category_attribute_value", "service.data.csv.CategoryAttributeValueDataCsvSchema.59",
              CsvDataType.STRING));
          sql += ",max(category_attribute_value3_jp) as  category_attribute_value3_jp";
        }
      }
      columns.add(new CsvColumnImpl("import_flag", "service.data.csv.CategoryAttributeValueDataCsvSchema.23", CsvDataType.STRING));
      sql += ",1 as import_flag";
      CategoryAttributeValueDataExportCondition condition = CsvExportType.EXPORT_CSV_CATEGORY_ATTRIBUTE_VALUE_DATA
          .createConditionInstance();
      condition.setCommodityCode(bean.getSearchCommodityCode());
      condition.setSkuCode(bean.getSearchSkuCode());
      condition.setColumns(columns);
      condition.setSqlString(sql);
      if (bean.getSearchExportTemplateFlg().equals("2")) {
        condition.setHeaderOnly(true);
      } else {
        condition.setHeaderOnly(false);
      }
      this.exportCondition = condition;
      // 20120206 os013 add end
      // 指定模版
    } else {
      // 商品信息模版，销售属性模版，销售设定模版，特卖商品设定模版
      if (bean.getSearchAppointExportObject().equals("0") || bean.getSearchAppointExportObject().equals("1")
          || bean.getSearchAppointExportObject().equals("2") || bean.getSearchAppointExportObject().equals("4")) {
        columns.add(new CsvColumnImpl("commodity_code", "service.data.csv.CCommodityDetailCsvSchema.2", CsvDataType.STRING, false,
            false, true, null));
        if (!bean.getSearchAppointExportObject().equals("4")) {
          columns.add(new CsvColumnImpl("sku_code", "service.data.csv.CCommodityDetailCsvSchema.0", CsvDataType.STRING, false,
              false, true, null));
          sql += ",CD.SKU_CODE";
        }
        // 特卖商品设定模版时セール区分必选
        if (bean.getSearchAppointExportObject().equals("4")) {
          columns.add(new CsvColumnImpl("sale_flag", "service.data.csv.CCommodityHeaderCsvSchema.24", CsvDataType.STRING));
          sql += ",CH.SALE_FLAG";
        }
        for (String item : bean.getHeaderItem()) {
          if (item.equals("0")) {
            columns.add(new CsvColumnImpl("commodity_code", "service.data.csv.CCommodityHeaderCsvSchema.1", CsvDataType.STRING,
                false, false, true, null));
            sql += ",CH.COMMODITY_CODE";
            // 商品名称
          } else if (item.equals("1")) {
            columns.add(new CsvColumnImpl("commodity_name", "service.data.csv.CCommodityHeaderCsvSchema.2", CsvDataType.STRING,
                false, false, true, null));
            sql += ",CH.COMMODITY_NAME";
            // 商品名称英字
          } else if (item.equals("2")) {
            columns.add(new CsvColumnImpl("commodity_name_en", "service.data.csv.CCommodityHeaderCsvSchema.3", CsvDataType.STRING));
            sql += ",CH.COMMODITY_NAME_EN";
            // ブランドコード
          } else if (item.equals("4")) {
            columns.add(new CsvColumnImpl("brand_code", "service.data.csv.CCommodityHeaderCsvSchema.26", CsvDataType.STRING));
            sql += ",CH.BRAND_CODE";
            // TMALLのカテゴリID
          } else if (item.equals("5")) {
            columns
                .add(new CsvColumnImpl("tmall_category_id", "service.data.csv.CCommodityHeaderCsvSchema.29", CsvDataType.NUMBER));
            sql += ",CH.TMALL_CATEGORY_ID";
            // 取引先コード
          } else if (item.equals("6")) {
            columns.add(new CsvColumnImpl("supplier_code", "service.data.csv.CCommodityHeaderCsvSchema.30", CsvDataType.STRING));
            sql += ",CH.SUPPLIER_CODE";
            // セール区分
          } else if (item.equals("7")) {
            columns.add(new CsvColumnImpl("sale_flag", "service.data.csv.CCommodityHeaderCsvSchema.24", CsvDataType.STRING));
            sql += ",CH.SALE_FLAG";
            // 特集区分
          } else if (item.equals("8")) {
            columns.add(new CsvColumnImpl("spec_flag", "service.data.csv.CCommodityHeaderCsvSchema.25", CsvDataType.STRING));
            sql += ",CH.SPEC_FLAG";
            // 商品説明1
          } else if (item.equals("9")) {
            columns.add(new CsvColumnImpl("commodity_description1", "service.data.csv.CCommodityHeaderCsvSchema.6",
                CsvDataType.STRING));
            sql += ",CH.COMMODITY_DESCRIPTION1";
            // 商品説明2
          } else if (item.equals("12")) {
            columns.add(new CsvColumnImpl("commodity_description2", "service.data.csv.CCommodityHeaderCsvSchema.7",
                CsvDataType.STRING));
            sql += ",CH.COMMODITY_DESCRIPTION2";
            // 商品説明3
          } else if (item.equals("15")) {
            columns.add(new CsvColumnImpl("commodity_description3", "service.data.csv.CCommodityHeaderCsvSchema.8",
                CsvDataType.STRING));
            sql += ",CH.COMMODITY_DESCRIPTION3";
            // 商品説明(一覧用）
          } else if (item.equals("18")) {
            columns.add(new CsvColumnImpl("commodity_description_short", "service.data.csv.CCommodityHeaderCsvSchema.9",
                CsvDataType.STRING));
            sql += ",CH.COMMODITY_DESCRIPTION_SHORT";
            // 商品検索ワード
          } else if (item.equals("21")) {
            columns.add(new CsvColumnImpl("commodity_search_words", "service.data.csv.CCommodityHeaderCsvSchema.10",
                CsvDataType.STRING));
            sql += ",CH.COMMODITY_SEARCH_WORDS";
            // 販売開始日時
          } else if (item.equals("22")) {
            columns.add(new CsvColumnImpl("sale_start_datetime", "service.data.csv.CCommodityHeaderCsvSchema.11",
                CsvDataType.DATETIME));
            sql += ", CASE CH.SALE_START_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMin())
                + "' THEN NULL ELSE CH.SALE_START_DATETIME END AS SALE_START_DATETIME ";
            // 販売終了日時
          } else if (item.equals("23")) {
            columns.add(new CsvColumnImpl("sale_end_datetime", "service.data.csv.CCommodityHeaderCsvSchema.12",
                CsvDataType.DATETIME));
            sql += ",CASE CH.SALE_END_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMax())
                + "' THEN NULL ELSE CH.SALE_END_DATETIME END AS SALE_END_DATETIME ";
            // 特別価格開始日時
          } else if (item.equals("24")) {
            columns.add(new CsvColumnImpl("discount_price_start_datetime", "service.data.csv.CCommodityHeaderCsvSchema.13",
                CsvDataType.DATETIME));
            sql += ",CASE CH.DISCOUNT_PRICE_START_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMin())
                + "' THEN NULL ELSE CH.DISCOUNT_PRICE_START_DATETIME END AS DISCOUNT_PRICE_START_DATETIME ";
            // 特別価格終了日時
          } else if (item.equals("25")) {
            columns.add(new CsvColumnImpl("discount_price_end_datetime", "service.data.csv.CCommodityHeaderCsvSchema.14",
                CsvDataType.DATETIME));
            sql += ",CASE CH.DISCOUNT_PRICE_END_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMax())
                + "' THEN NULL ELSE CH.DISCOUNT_PRICE_END_DATETIME END AS DISCOUNT_PRICE_END_DATETIME ";
            // ワーニング区分
          } else if (item.equals("26")) {
            columns.add(new CsvColumnImpl("warning_flag", "service.data.csv.CCommodityHeaderCsvSchema.22", CsvDataType.STRING));
            sql += ",CH.WARNING_FLAG";
            // 規格1名称ID(TMALLの属性ID）
          } else if (item.equals("27")) {
            columns.add(new CsvColumnImpl("standard1_id", "service.data.csv.CCommodityHeaderCsvSchema.15", CsvDataType.STRING));
            sql += ",CH.STANDARD1_ID";
            // 規格1名称
          } else if (item.equals("28")) {
            columns.add(new CsvColumnImpl("standard1_name", "service.data.csv.CCommodityHeaderCsvSchema.16", CsvDataType.STRING));
            sql += ",CH.STANDARD1_NAME";
            // 規格2名称ID(TMALLの属性ID）
          } else if (item.equals("31")) {
            columns.add(new CsvColumnImpl("standard2_id", "service.data.csv.CCommodityHeaderCsvSchema.17", CsvDataType.STRING));
            sql += ",CH.STANDARD2_ID";
            // 規格2名称
          } else if (item.equals("32")) {
            columns.add(new CsvColumnImpl("standard2_name", "service.data.csv.CCommodityHeaderCsvSchema.18", CsvDataType.STRING));
            sql += ",CH.STANDARD2_NAME";
            // EC販売フラグ
          } else if (item.equals("35")) {
            columns.add(new CsvColumnImpl("sale_flg_ec", "service.data.csv.CCommodityHeaderCsvSchema.19", CsvDataType.NUMBER));
            sql += ",CH.SALE_FLG_EC";
            // 返品不可フラグ
            // 供货商换货
          } else if (item.equals("38")) {
            columns.add(new CsvColumnImpl("change_flg_supp", "service.data.csv.CCommodityHeaderCsvSchema.85", CsvDataType.STRING));
            sql += ",CASE WHEN CH.RETURN_FLG = 0 THEN 0 " + "      WHEN CH.RETURN_FLG = 1 THEN 0 "
                + "      WHEN CH.RETURN_FLG = 2 THEN 0 " + "      WHEN CH.RETURN_FLG = 3 THEN 0 ELSE 1 END CHANGE_FLG_SUPP";
            // 供货商退货
          } else if (item.equals("37")) {
            columns.add(new CsvColumnImpl("return_flg_supp", "service.data.csv.CCommodityHeaderCsvSchema.84", CsvDataType.STRING));
            sql += ",CASE WHEN CH.RETURN_FLG = 0 THEN 0 " + "      WHEN CH.RETURN_FLG = 1 THEN 0 "
                + "      WHEN CH.RETURN_FLG = 4 THEN 0 " + "      WHEN CH.RETURN_FLG = 5 THEN 0 ELSE 1 END RETURN_FLG_SUPP";
            // 顾客退货
          } else if (item.equals("36")) {
            columns.add(new CsvColumnImpl("return_flg_cust", "service.data.csv.CCommodityHeaderCsvSchema.83", CsvDataType.STRING));
            sql += ",CASE WHEN CH.RETURN_FLG = 0 THEN 0 " + "      WHEN CH.RETURN_FLG = 2 THEN 0 "
                + "      WHEN CH.RETURN_FLG = 4 THEN 0 " + "      WHEN CH.RETURN_FLG = 6 THEN 0 ELSE 1 END RETURN_FLG_CUST ";
            
            // リードタイム
          } else if (item.equals("39")) {
            columns.add(new CsvColumnImpl("lead_time", "service.data.csv.CCommodityHeaderCsvSchema.23", CsvDataType.NUMBER));
            sql += ",CH.LEAD_TIME";
            // 産地
          } else if (item.equals("40")) {
            columns.add(new CsvColumnImpl("original_code", "service.data.csv.CCommodityHeaderCsvSchema.33", CsvDataType.STRING));
            sql += ",CH.ORIGINAL_CODE";
            // 大物フラグ
          } else if (item.equals("41")) {
            columns.add(new CsvColumnImpl("big_flag", "service.data.csv.CCommodityHeaderCsvSchema.82", CsvDataType.NUMBER));
            sql += ",CH.BIG_FLAG";
            // 商品期限管理フラグ 0管理しない/1賞味期限日/2製造日＋保管日数
          } else if (item.equals("42")) {
            columns.add(new CsvColumnImpl("shelf_life_flag", "service.data.csv.CCommodityHeaderCsvSchema.79", CsvDataType.NUMBER));
            sql += ",CH.SHELF_LIFE_FLAG";
            // 保管日数
          } else if (item.equals("43")) {
            columns.add(new CsvColumnImpl("shelf_life_days", "service.data.csv.CCommodityHeaderCsvSchema.80", CsvDataType.NUMBER));
            sql += ",CH.SHELF_LIFE_DAYS";
            // 仕入担当者コード
          } else if (item.equals("44")) {
            columns.add(new CsvColumnImpl("buyer_code", "service.data.csv.CCommodityHeaderCsvSchema.31", CsvDataType.STRING));
            sql += ",CH.BUYER_CODE";
            // 原材料1
          } else if (item.equals("45")) {
            columns.add(new CsvColumnImpl("material1", "service.data.csv.CCommodityHeaderCsvSchema.64", CsvDataType.STRING));
            sql += ",CH.MATERIAL1";
            //
          } else if (item.equals("46")) {
            columns.add(new CsvColumnImpl("material2", "service.data.csv.CCommodityHeaderCsvSchema.65", CsvDataType.STRING));
            sql += ",CH.MATERIAL2";
            //
          } else if (item.equals("47")) {
            columns.add(new CsvColumnImpl("material3", "service.data.csv.CCommodityHeaderCsvSchema.66", CsvDataType.STRING));
            sql += ",CH.MATERIAL3";
            //
          } else if (item.equals("48")) {
            columns.add(new CsvColumnImpl("material4", "service.data.csv.CCommodityHeaderCsvSchema.67", CsvDataType.STRING));
            sql += ",CH.MATERIAL4";
            //
          } else if (item.equals("49")) {
            columns.add(new CsvColumnImpl("material5", "service.data.csv.CCommodityHeaderCsvSchema.68", CsvDataType.STRING));
            sql += ",CH.MATERIAL5";
            //
          } else if (item.equals("50")) {
            columns.add(new CsvColumnImpl("material6", "service.data.csv.CCommodityHeaderCsvSchema.69", CsvDataType.STRING));
            sql += ",CH.MATERIAL6";
            //
          } else if (item.equals("51")) {
            columns.add(new CsvColumnImpl("material7", "service.data.csv.CCommodityHeaderCsvSchema.70", CsvDataType.STRING));
            sql += ",CH.MATERIAL7";
            //
          } else if (item.equals("52")) {
            columns.add(new CsvColumnImpl("material8", "service.data.csv.CCommodityHeaderCsvSchema.71", CsvDataType.STRING));
            sql += ",CH.MATERIAL8";
            //
          } else if (item.equals("53")) {
            columns.add(new CsvColumnImpl("material9", "service.data.csv.CCommodityHeaderCsvSchema.72", CsvDataType.STRING));
            sql += ",CH.MATERIAL9";
            // 原材料10
          } else if (item.equals("54")) {
            columns.add(new CsvColumnImpl("material10", "service.data.csv.CCommodityHeaderCsvSchema.73", CsvDataType.STRING));
            sql += ",CH.MATERIAL10";
            // 原材料11
          } else if (item.equals("55")) {
            columns.add(new CsvColumnImpl("material11", "service.data.csv.CCommodityHeaderCsvSchema.74", CsvDataType.STRING));
            sql += ",CH.MATERIAL11";
            // 原材料12
          } else if (item.equals("56")) {
            columns.add(new CsvColumnImpl("material12", "service.data.csv.CCommodityHeaderCsvSchema.75", CsvDataType.STRING));
            sql += ",CH.MATERIAL12";
            // 原材料13
          } else if (item.equals("57")) {
            columns.add(new CsvColumnImpl("material13", "service.data.csv.CCommodityHeaderCsvSchema.76", CsvDataType.STRING));
            sql += ",CH.MATERIAL13";
            // 原材料14
          } else if (item.equals("58")) {
            columns.add(new CsvColumnImpl("material14", "service.data.csv.CCommodityHeaderCsvSchema.77", CsvDataType.STRING));
            sql += ",CH.MATERIAL14";
            // 原材料15
          } else if (item.equals("59")) {
            columns.add(new CsvColumnImpl("material15", "service.data.csv.CCommodityHeaderCsvSchema.78", CsvDataType.STRING));
            sql += ",CH.MATERIAL15";
            // 成分1
          } else if (item.equals("60")) {
            columns.add(new CsvColumnImpl("ingredient_name1", "service.data.csv.CCommodityHeaderCsvSchema.34", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME1";
            // 成分量1
          } else if (item.equals("61")) {
            columns.add(new CsvColumnImpl("ingredient_val1", "service.data.csv.CCommodityHeaderCsvSchema.35", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL1";
            //
          } else if (item.equals("62")) {
            columns.add(new CsvColumnImpl("ingredient_name2", "service.data.csv.CCommodityHeaderCsvSchema.36", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME2";
            //
          } else if (item.equals("63")) {
            columns.add(new CsvColumnImpl("ingredient_val2", "service.data.csv.CCommodityHeaderCsvSchema.37", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL2";
            //
          } else if (item.equals("64")) {
            columns.add(new CsvColumnImpl("ingredient_name3", "service.data.csv.CCommodityHeaderCsvSchema.38", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME3";
            //
          } else if (item.equals("65")) {
            columns.add(new CsvColumnImpl("ingredient_val3", "service.data.csv.CCommodityHeaderCsvSchema.39", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL3";
            //
          } else if (item.equals("66")) {
            columns.add(new CsvColumnImpl("ingredient_name4", "service.data.csv.CCommodityHeaderCsvSchema.40", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME4";
            //
          } else if (item.equals("67")) {
            columns.add(new CsvColumnImpl("ingredient_val4", "service.data.csv.CCommodityHeaderCsvSchema.41", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL4";
            //
          } else if (item.equals("68")) {
            columns.add(new CsvColumnImpl("ingredient_name5", "service.data.csv.CCommodityHeaderCsvSchema.42", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME5";
            //
          } else if (item.equals("69")) {
            columns.add(new CsvColumnImpl("ingredient_val5", "service.data.csv.CCommodityHeaderCsvSchema.43", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL5";
            //
          } else if (item.equals("70")) {
            columns.add(new CsvColumnImpl("ingredient_name6", "service.data.csv.CCommodityHeaderCsvSchema.44", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME6";
            //
          } else if (item.equals("71")) {
            columns.add(new CsvColumnImpl("ingredient_val6", "service.data.csv.CCommodityHeaderCsvSchema.45", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL6";
            //
          } else if (item.equals("72")) {
            columns.add(new CsvColumnImpl("ingredient_name7", "service.data.csv.CCommodityHeaderCsvSchema.46", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME7";
            //
          } else if (item.equals("73")) {
            columns.add(new CsvColumnImpl("ingredient_val7", "service.data.csv.CCommodityHeaderCsvSchema.47", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL7";
            //
          } else if (item.equals("74")) {
            columns.add(new CsvColumnImpl("ingredient_name8", "service.data.csv.CCommodityHeaderCsvSchema.48", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME8";
            //
          } else if (item.equals("75")) {
            columns.add(new CsvColumnImpl("ingredient_val8", "service.data.csv.CCommodityHeaderCsvSchema.49", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL8";
            //
          } else if (item.equals("76")) {
            columns.add(new CsvColumnImpl("ingredient_name9", "service.data.csv.CCommodityHeaderCsvSchema.50", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME9";
            //
          } else if (item.equals("77")) {
            columns.add(new CsvColumnImpl("ingredient_val9", "service.data.csv.CCommodityHeaderCsvSchema.51", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL9";
            // 成分10
          } else if (item.equals("78")) {
            columns
                .add(new CsvColumnImpl("ingredient_name10", "service.data.csv.CCommodityHeaderCsvSchema.52", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME10";
            // 成分量10
          } else if (item.equals("79")) {
            columns.add(new CsvColumnImpl("ingredient_val10", "service.data.csv.CCommodityHeaderCsvSchema.53", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL10";
            // 成分11
          } else if (item.equals("80")) {
            columns
                .add(new CsvColumnImpl("ingredient_name11", "service.data.csv.CCommodityHeaderCsvSchema.54", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME11";
            // 成分量11
          } else if (item.equals("81")) {
            columns.add(new CsvColumnImpl("ingredient_val11", "service.data.csv.CCommodityHeaderCsvSchema.55", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL11";
            // 成分12
          } else if (item.equals("82")) {
            columns
                .add(new CsvColumnImpl("ingredient_name12", "service.data.csv.CCommodityHeaderCsvSchema.56", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME12";
            // 成分量12
          } else if (item.equals("83")) {
            columns.add(new CsvColumnImpl("ingredient_val12", "service.data.csv.CCommodityHeaderCsvSchema.57", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL12";
            // 成分13
          } else if (item.equals("84")) {
            columns
                .add(new CsvColumnImpl("ingredient_name13", "service.data.csv.CCommodityHeaderCsvSchema.58", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME13";
            // 成分量13
          } else if (item.equals("85")) {
            columns.add(new CsvColumnImpl("ingredient_val13", "service.data.csv.CCommodityHeaderCsvSchema.59", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL13";
            // 成分14
          } else if (item.equals("86")) {
            columns
                .add(new CsvColumnImpl("ingredient_name14", "service.data.csv.CCommodityHeaderCsvSchema.60", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME14";
            // 成分量14
          } else if (item.equals("87")) {
            columns.add(new CsvColumnImpl("ingredient_val14", "service.data.csv.CCommodityHeaderCsvSchema.61", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL14";
            // 成分15
          } else if (item.equals("88")) {
            columns
                .add(new CsvColumnImpl("ingredient_name15", "service.data.csv.CCommodityHeaderCsvSchema.62", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME15";
            // 成分量15
          } else if (item.equals("89")) {
            columns.add(new CsvColumnImpl("ingredient_val15", "service.data.csv.CCommodityHeaderCsvSchema.63", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL15";
          }
        }
        for (String item1 : bean.getDetailItem()) {
          if (item1.equals("0")) {
            columns.add(new CsvColumnImpl("sku_code", "service.data.csv.CCommodityDetailCsvSchema.0", CsvDataType.STRING));
            sql += ",CD.SKU_CODE";
            // SKU名称
          } else if (item1.equals("1")) {
            columns.add(new CsvColumnImpl("sku_name", "service.data.csv.CCommodityDetailCsvSchema.1", CsvDataType.STRING));
            sql += ",CD.SKU_NAME";
            // 仕入価格
          } else if (item1.equals("2")) {
            columns.add(new CsvColumnImpl("purchase_price", "service.data.csv.CCommodityDetailCsvSchema.5", CsvDataType.NUMBER));
            sql += ",CD.PURCHASE_PRICE";
            // 希望小売価格
          } else if (item1.equals("3")) {
            columns
                .add(new CsvColumnImpl("suggeste_price", "service.data.csv.CCommodityDetailCsvSchema.4", CsvDataType.BIGDECIMAL));
            sql += ",CD.SUGGESTE_PRICE";
            // 下限売価
          } else if (item1.equals("4")) {
            columns.add(new CsvColumnImpl("min_price", "service.data.csv.CCommodityDetailCsvSchema.23", CsvDataType.BIGDECIMAL));
            sql += ",CD.MIN_PRICE";
            // 定価フラグ0：非定価　1：定価
          } else if (item1.equals("5")) {
            columns.add(new CsvColumnImpl("fixed_price_flag", "service.data.csv.CCommodityDetailCsvSchema.3", CsvDataType.NUMBER));
            sql += ",CD.FIXED_PRICE_FLAG";
            // EC商品単価
          } else if (item1.equals("6")) {
            columns.add(new CsvColumnImpl("unit_price", "service.data.csv.CCommodityDetailCsvSchema.6", CsvDataType.BIGDECIMAL));
            sql += ",CD.UNIT_PRICE";
            // TMALLの商品単価
          } else if (item1.equals("7")) {
            columns.add(new CsvColumnImpl("tmall_unit_price", "service.data.csv.CCommodityDetailCsvSchema.21", CsvDataType.NUMBER));
            sql += ",CD.TMALL_UNIT_PRICE";
            // EC特別価格
          } else if (item1.equals("8")) {
            columns
                .add(new CsvColumnImpl("discount_price", "service.data.csv.CCommodityDetailCsvSchema.7", CsvDataType.BIGDECIMAL));
            sql += ",CD.DISCOUNT_PRICE";
            // TMALLの特別価格
          } else if (item1.equals("9")) {
            columns.add(new CsvColumnImpl("tmall_discount_price", "service.data.csv.CCommodityDetailCsvSchema.22",
                CsvDataType.NUMBER));
            sql += ",CD.TMALL_DISCOUNT_PRICE";
            // 取扱いフラグ(EC)
          } else if (item1.equals("10")) {
            columns.add(new CsvColumnImpl("use_flg", "service.data.csv.CCommodityDetailCsvSchema.15", CsvDataType.BIGDECIMAL));
            sql += ",CD.USE_FLG";
            // 取扱いフラグ(TMALL)
          } else if (item1.equals("11")) {
            columns
                .add(new CsvColumnImpl("tmall_use_flg", "service.data.csv.CCommodityDetailCsvSchema.31", CsvDataType.BIGDECIMAL));
            sql += ",CD.TMALL_USE_FLG";
            // 規格1値のID(=TMALL属性値ID)
          } else if (item1.equals("12")) {
            columns
                .add(new CsvColumnImpl("standard_detail1_id", "service.data.csv.CCommodityDetailCsvSchema.8", CsvDataType.STRING));
            sql += ",CD.STANDARD_DETAIL1_ID";
            // 規格1値の文字列(値のIDなければ、これを利用）
          } else if (item1.equals("13")) {
            columns.add(new CsvColumnImpl("standard_detail1_name", "service.data.csv.CCommodityDetailCsvSchema.9",
                CsvDataType.STRING));
            sql += ",CD.STANDARD_DETAIL1_NAME";
            // 規格2値のID(=TMALL属性値ID)
          } else if (item1.equals("16")) {
            columns.add(new CsvColumnImpl("standard_detail2_id", "service.data.csv.CCommodityDetailCsvSchema.10",
                CsvDataType.STRING));
            sql += ",CD.STANDARD_DETAIL2_ID";
            // 規格2値の文字列(値のIDなければ、これを利用）
          } else if (item1.equals("17")) {
            columns.add(new CsvColumnImpl("standard_detail2_name", "service.data.csv.CCommodityDetailCsvSchema.11",
                CsvDataType.STRING));
            sql += ",CD.STANDARD_DETAIL2_NAME";
            // 在庫警告日数
          } else if (item1.equals("20")) {
            columns.add(new CsvColumnImpl("stock_warning", "service.data.csv.CCommodityDetailCsvSchema.19", CsvDataType.NUMBER));
            sql += ",CD.STOCK_WARNING";
            // 最小仕入数
          } else if (item1.equals("21")) {
            columns.add(new CsvColumnImpl("minimum_order", "service.data.csv.CCommodityDetailCsvSchema.16", CsvDataType.NUMBER));
            sql += ",CD.MINIMUM_ORDER";
            // 最大仕入数
          } else if (item1.equals("22")) {
            columns.add(new CsvColumnImpl("maximum_order", "service.data.csv.CCommodityDetailCsvSchema.17", CsvDataType.NUMBER));
            sql += ",CD.MAXIMUM_ORDER";
            // 最小単位の仕入数
          } else if (item1.equals("23")) {
            columns.add(new CsvColumnImpl("order_multiple", "service.data.csv.CCommodityDetailCsvSchema.18", CsvDataType.NUMBER));
            sql += ",CD.ORDER_MULTIPLE";
            // 商品重量(単位はKG)、未設定の場合、商品HEADの重量を利用。
          } else if (item1.equals("24")) {
            columns.add(new CsvColumnImpl("weight", "service.data.csv.CCommodityDetailCsvSchema.12", CsvDataType.BIGDECIMAL));
            sql += ",CD.WEIGHT";
            // 税率区分
          } else if (item1.equals("25")) {
            columns.add(new CsvColumnImpl("tax_class", "service.data.csv.CCommodityDetailCsvSchema.32", CsvDataType.STRING));
            sql += ",CD.TAX_CLASS";
            // 入数
          } else if (item1.equals("27")) {
            columns.add(new CsvColumnImpl("inner_quantity", "service.data.csv.CCommodityDetailCsvSchema.27", CsvDataType.NUMBER));
            sql += ",CD.INNER_QUANTITY";
            // 入数単位
          } else if (item1.equals("28")) {
            columns.add(new CsvColumnImpl("inner_quantity_unit", "service.data.csv.CCommodityDetailCsvSchema.28",
                CsvDataType.STRING));
            sql += ",CD.INNER_QUANTITY_UNIT";
            // WEB表示単価単位入数
          } else if (item1.equals("29")) {
            columns.add(new CsvColumnImpl("inner_unit_for_price", "service.data.csv.CCommodityDetailCsvSchema.29",
                CsvDataType.NUMBER));
            sql += ",CD.INNER_UNIT_FOR_PRICE";
            // 容量
          } else if (item1.equals("30")) {
            columns.add(new CsvColumnImpl("volume", "service.data.csv.CCommodityDetailCsvSchema.13", CsvDataType.NUMBER));
            sql += ",CD.VOLUME";
            // 容量単位
          } else if (item1.equals("31")) {
            columns.add(new CsvColumnImpl("volume_unit", "service.data.csv.CCommodityDetailCsvSchema.14", CsvDataType.STRING));
            sql += ",CD.VOLUME_UNIT";
            // WEB表示単価単位容量
          } else if (item1.equals("32")) {
            columns.add(new CsvColumnImpl("volume_unit_for_price", "service.data.csv.CCommodityDetailCsvSchema.30",
                CsvDataType.NUMBER));
            sql += ",CD.VOLUME_UNIT_FOR_PRICE";
            /**
             * 库存
             */
            // EC在庫割合(0-100)
          } else if (item1.equals("33")) {
         // 2014/06/11 库存更新对应 ob_yuan update start
            //columns.add(new CsvColumnImpl("share_ratio", "service.data.csv.CCommodityStockDataCsvSchema.12", CsvDataType.NUMBER));
            //sql += ",SK.SHARE_RATIO";
            columns.add(new CsvColumnImpl("share_ratio", "service.data.csv.CCommodityStockDataCsvSchema.12", CsvDataType.STRING));
            sql += ",get_stock_ratio_func(SK.SHOP_CODE,SK.COMMODITY_CODE)";
         // 2014/06/11 库存更新对应 ob_yuan update end
            // 安全在庫
          } else if (item1.equals("34")) {
            columns
                .add(new CsvColumnImpl("stock_threshold", "service.data.csv.CCommodityStockDataCsvSchema.7", CsvDataType.NUMBER));
            sql += ",SK.STOCK_THRESHOLD";
          }
        }
        CCommodityDataExportCondition condition = CsvExportType.EXPORT_CSV_COMMODITY_DATA_DATA.createConditionInstance();
        condition.setSkuCode(bean.getSearchSkuCode());
        condition.setCommodityCode(bean.getSearchCommodityCode());
        condition.setColumns(columns);
        condition.setSqlString(sql);
        if (bean.getSearchExportTemplateFlg().equals("2")) {
          condition.setHeaderOnly(true);
        } else {
          condition.setHeaderOnly(false);
        }
        this.exportCondition = condition;
        // 商品显示设定模版，特集商品设定模版
      } else if (bean.getSearchAppointExportObject().equals("3") || bean.getSearchAppointExportObject().equals("5")) {
        columns.add(new CsvColumnImpl("commodity_code", "service.data.csv.CCommodityHeaderCsvSchema.1", CsvDataType.STRING, false,
            false, true, null));
        // 特集商品设定模版时特集区分必选
        if (bean.getSearchAppointExportObject().equals("5")) {
          columns.add(new CsvColumnImpl("spec_flag", "service.data.csv.CCommodityHeaderCsvSchema.25", CsvDataType.STRING));
          sql += ",CH.SPEC_FLAG";
        }
        for (String item : bean.getHeaderItem()) {
          // 商品名称
          if (item.equals("1")) {
            columns.add(new CsvColumnImpl("commodity_name", "service.data.csv.CCommodityHeaderCsvSchema.2", CsvDataType.STRING,
                false, false, true, null));
            sql += ",CH.COMMODITY_NAME";
            // 商品名称英字
          } else if (item.equals("2")) {
            columns.add(new CsvColumnImpl("commodity_name_en", "service.data.csv.CCommodityHeaderCsvSchema.3", CsvDataType.STRING));
            sql += ",CH.COMMODITY_NAME_EN";
            // ブランドコード
          } else if (item.equals("3")) {
            columns.add(new CsvColumnImpl("brand_code", "service.data.csv.CCommodityHeaderCsvSchema.26", CsvDataType.STRING));
            sql += ",CH.BRAND_CODE";
            // TMALLのカテゴリID
          } else if (item.equals("4")) {
            columns
                .add(new CsvColumnImpl("tmall_category_id", "service.data.csv.CCommodityHeaderCsvSchema.29", CsvDataType.NUMBER));
            sql += ",CH.TMALL_CATEGORY_ID";
            // 取引先コード
          } else if (item.equals("5")) {
            columns.add(new CsvColumnImpl("supplier_code", "service.data.csv.CCommodityHeaderCsvSchema.30", CsvDataType.STRING));
            sql += ",CH.SUPPLIER_CODE";
            // セール区分
          } else if (item.equals("6")) {
            columns.add(new CsvColumnImpl("sale_flag", "service.data.csv.CCommodityHeaderCsvSchema.24", CsvDataType.STRING));
            sql += ",CH.SALE_FLAG";
            // 特集区分
          } else if (item.equals("7")) {
            columns.add(new CsvColumnImpl("spec_flag", "service.data.csv.CCommodityHeaderCsvSchema.25", CsvDataType.STRING));
            sql += ",CH.SPEC_FLAG";
            // 商品説明1
          } else if (item.equals("8")) {
            columns.add(new CsvColumnImpl("commodity_description1", "service.data.csv.CCommodityHeaderCsvSchema.6",
                CsvDataType.STRING));
            sql += ",CH.COMMODITY_DESCRIPTION1";
            // 商品説明2
          } else if (item.equals("9")) {
            columns.add(new CsvColumnImpl("commodity_description2", "service.data.csv.CCommodityHeaderCsvSchema.7",
                CsvDataType.STRING));
            sql += ",CH.COMMODITY_DESCRIPTION2";
            // 商品説明3
          } else if (item.equals("10")) {
            columns.add(new CsvColumnImpl("commodity_description3", "service.data.csv.CCommodityHeaderCsvSchema.8",
                CsvDataType.STRING));
            sql += ",CH.COMMODITY_DESCRIPTION3";
            // 商品説明(一覧用）
          } else if (item.equals("11")) {
            columns.add(new CsvColumnImpl("commodity_description_short", "service.data.csv.CCommodityHeaderCsvSchema.9",
                CsvDataType.STRING));
            sql += ",CH.COMMODITY_DESCRIPTION_SHORT";
            // 商品検索ワード
          } else if (item.equals("12")) {
            columns.add(new CsvColumnImpl("commodity_search_words", "service.data.csv.CCommodityHeaderCsvSchema.10",
                CsvDataType.STRING));
            sql += ",CH.COMMODITY_SEARCH_WORDS";
            // 販売開始日時
          } else if (item.equals("13")) {
            columns.add(new CsvColumnImpl("sale_start_datetime", "service.data.csv.CCommodityHeaderCsvSchema.11",
                CsvDataType.DATETIME));
            sql += ", CASE CH.SALE_START_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMin())
                + "' THEN NULL ELSE CH.SALE_START_DATETIME END AS SALE_START_DATETIME ";
            // 販売終了日時
          } else if (item.equals("14")) {
            columns.add(new CsvColumnImpl("sale_end_datetime", "service.data.csv.CCommodityHeaderCsvSchema.12",
                CsvDataType.DATETIME));
            sql += ",CASE CH.SALE_END_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMax())
                + "' THEN NULL ELSE CH.SALE_END_DATETIME END AS SALE_END_DATETIME ";
            // 特別価格開始日時
          } else if (item.equals("15")) {
            columns.add(new CsvColumnImpl("discount_price_start_datetime", "service.data.csv.CCommodityHeaderCsvSchema.13",
                CsvDataType.DATETIME));
            sql += ",CASE CH.DISCOUNT_PRICE_START_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMin())
                + "' THEN NULL ELSE CH.DISCOUNT_PRICE_START_DATETIME END AS DISCOUNT_PRICE_START_DATETIME ";
            // 特別価格終了日時
          } else if (item.equals("16")) {
            columns.add(new CsvColumnImpl("discount_price_end_datetime", "service.data.csv.CCommodityHeaderCsvSchema.14",
                CsvDataType.DATETIME));
            sql += ",CASE CH.DISCOUNT_PRICE_END_DATETIME WHEN '" + DateUtil.toDateTimeString(DateUtil.getMax())
                + "' THEN NULL ELSE CH.DISCOUNT_PRICE_END_DATETIME END AS DISCOUNT_PRICE_END_DATETIME ";
            // ワーニング区分
          } else if (item.equals("17")) {
            columns.add(new CsvColumnImpl("warning_flag", "service.data.csv.CCommodityHeaderCsvSchema.22", CsvDataType.STRING));
            sql += ",CH.WARNING_FLAG";
            // 規格1名称ID(TMALLの属性ID）
          } else if (item.equals("18")) {
            columns.add(new CsvColumnImpl("standard1_id", "service.data.csv.CCommodityHeaderCsvSchema.15", CsvDataType.STRING));
            sql += ",CH.STANDARD1_ID";
            // 規格1名称
          } else if (item.equals("19")) {
            columns.add(new CsvColumnImpl("standard1_name", "service.data.csv.CCommodityHeaderCsvSchema.16", CsvDataType.STRING));
            sql += ",CH.STANDARD1_NAME";
            // 規格2名称ID(TMALLの属性ID）
          } else if (item.equals("20")) {
            columns.add(new CsvColumnImpl("standard2_id", "service.data.csv.CCommodityHeaderCsvSchema.17", CsvDataType.STRING));
            sql += ",CH.STANDARD2_ID";
            // 規格2名称
          } else if (item.equals("21")) {
            columns.add(new CsvColumnImpl("standard2_name", "service.data.csv.CCommodityHeaderCsvSchema.18", CsvDataType.STRING));
            sql += ",CH.STANDARD2_NAME";
            // EC販売フラグ
          } else if (item.equals("22")) {
            columns.add(new CsvColumnImpl("sale_flg_ec", "service.data.csv.CCommodityHeaderCsvSchema.19", CsvDataType.NUMBER));
            sql += ",CH.SALE_FLG_EC";
            // 返品不可フラグ
            // 供货商换货
          } else if (item.equals("24")) {
            // columns.add(new CsvColumnImpl("return_flg",
            // "service.data.csv.CCommodityHeaderCsvSchema.21",
            // CsvDataType.NUMBER));
            // sql += ",CH.RETURN_FLG";
            // 201200306 os013 add start
            columns.add(new CsvColumnImpl("return_flg_cust", "service.data.csv.CCommodityHeaderCsvSchema.83", CsvDataType.STRING));
            sql += ",CASE WHEN CH.RETURN_FLG = 0 THEN 0 " + "      WHEN CH.RETURN_FLG = 1 THEN 0 "
                + "      WHEN CH.RETURN_FLG = 2 THEN 0 " + "      WHEN CH.RETURN_FLG = 3 THEN 0 ELSE 1 END RETURN_FLG_CUST ";
            // 供货商退货
          } else if (item.equals("25")) {
            columns.add(new CsvColumnImpl("return_flg_supp", "service.data.csv.CCommodityHeaderCsvSchema.84", CsvDataType.STRING));
            sql += ",CASE WHEN CH.RETURN_FLG = 0 THEN 0 " + "      WHEN CH.RETURN_FLG = 1 THEN 0 "
                + "      WHEN CH.RETURN_FLG = 4 THEN 0 " + "      WHEN CH.RETURN_FLG = 5 THEN 0 ELSE 1 END RETURN_FLG_SUPP";
            // 顾客退货
          } else if (item.equals("26")) {
            columns.add(new CsvColumnImpl("change_flg_supp", "service.data.csv.CCommodityHeaderCsvSchema.85", CsvDataType.STRING));
            sql += ",CASE WHEN CH.RETURN_FLG = 0 THEN 0 " + "      WHEN CH.RETURN_FLG = 2 THEN 0 "
                + "      WHEN CH.RETURN_FLG = 4 THEN 0 " + "      WHEN CH.RETURN_FLG = 6 THEN 0 ELSE 1 END CHANGE_FLG_SUPP";
            // 201200306 os013 add end
            // リードタイム
          } else if (item.equals("27")) {
            columns.add(new CsvColumnImpl("lead_time", "service.data.csv.CCommodityHeaderCsvSchema.23", CsvDataType.NUMBER));
            sql += ",CH.LEAD_TIME";
            // 産地
          } else if (item.equals("28")) {
            columns.add(new CsvColumnImpl("original_code", "service.data.csv.CCommodityHeaderCsvSchema.33", CsvDataType.STRING));
            sql += ",CH.ORIGINAL_CODE";
            // 大物フラグ
          } else if (item.equals("29")) {
            columns.add(new CsvColumnImpl("big_flag", "service.data.csv.CCommodityHeaderCsvSchema.82", CsvDataType.NUMBER));
            sql += ",CH.BIG_FLAG";
            // 商品期限管理フラグ 0管理しない/1賞味期限日/2製造日＋保管日数
          } else if (item.equals("30")) {
            columns.add(new CsvColumnImpl("shelf_life_flag", "service.data.csv.CCommodityHeaderCsvSchema.79", CsvDataType.NUMBER));
            sql += ",CH.SHELF_LIFE_FLAG";
            // 保管日数
          } else if (item.equals("31")) {
            columns.add(new CsvColumnImpl("shelf_life_days", "service.data.csv.CCommodityHeaderCsvSchema.80", CsvDataType.NUMBER));
            sql += ",CH.SHELF_LIFE_DAYS";
            // 仕入担当者コード
          } else if (item.equals("32")) {
            columns.add(new CsvColumnImpl("buyer_code", "service.data.csv.CCommodityHeaderCsvSchema.31", CsvDataType.STRING));
            sql += ",CH.BUYER_CODE";
            // 原材料1
          } else if (item.equals("33")) {
            columns.add(new CsvColumnImpl("material1", "service.data.csv.CCommodityHeaderCsvSchema.64", CsvDataType.STRING));
            sql += ",CH.MATERIAL1";
            //
          } else if (item.equals("34")) {
            columns.add(new CsvColumnImpl("material2", "service.data.csv.CCommodityHeaderCsvSchema.65", CsvDataType.STRING));
            sql += ",CH.MATERIAL2";
            //
          } else if (item.equals("35")) {
            columns.add(new CsvColumnImpl("material3", "service.data.csv.CCommodityHeaderCsvSchema.66", CsvDataType.STRING));
            sql += ",CH.MATERIAL3";
            //
          } else if (item.equals("36")) {
            columns.add(new CsvColumnImpl("material4", "service.data.csv.CCommodityHeaderCsvSchema.67", CsvDataType.STRING));
            sql += ",CH.MATERIAL4";
            //
          } else if (item.equals("37")) {
            columns.add(new CsvColumnImpl("material5", "service.data.csv.CCommodityHeaderCsvSchema.68", CsvDataType.STRING));
            sql += ",CH.MATERIAL5";
            //
          } else if (item.equals("38")) {
            columns.add(new CsvColumnImpl("material6", "service.data.csv.CCommodityHeaderCsvSchema.69", CsvDataType.STRING));
            sql += ",CH.MATERIAL6";
            //
          } else if (item.equals("39")) {
            columns.add(new CsvColumnImpl("material7", "service.data.csv.CCommodityHeaderCsvSchema.70", CsvDataType.STRING));
            sql += ",CH.MATERIAL7";
            //
          } else if (item.equals("40")) {
            columns.add(new CsvColumnImpl("material8", "service.data.csv.CCommodityHeaderCsvSchema.71", CsvDataType.STRING));
            sql += ",CH.MATERIAL8";
            //
          } else if (item.equals("41")) {
            columns.add(new CsvColumnImpl("material9", "service.data.csv.CCommodityHeaderCsvSchema.72", CsvDataType.STRING));
            sql += ",CH.MATERIAL9";
            // 原材料10
          } else if (item.equals("42")) {
            columns.add(new CsvColumnImpl("material10", "service.data.csv.CCommodityHeaderCsvSchema.73", CsvDataType.STRING));
            sql += ",CH.MATERIAL10";
            // 原材料11
          } else if (item.equals("43")) {
            columns.add(new CsvColumnImpl("material11", "service.data.csv.CCommodityHeaderCsvSchema.74", CsvDataType.STRING));
            sql += ",CH.MATERIAL11";
            // 原材料12
          } else if (item.equals("44")) {
            columns.add(new CsvColumnImpl("material12", "service.data.csv.CCommodityHeaderCsvSchema.75", CsvDataType.STRING));
            sql += ",CH.MATERIAL12";
            // 原材料13
          } else if (item.equals("45")) {
            columns.add(new CsvColumnImpl("material13", "service.data.csv.CCommodityHeaderCsvSchema.76", CsvDataType.STRING));
            sql += ",CH.MATERIAL13";
            // 原材料14
          } else if (item.equals("46")) {
            columns.add(new CsvColumnImpl("material14", "service.data.csv.CCommodityHeaderCsvSchema.77", CsvDataType.STRING));
            sql += ",CH.MATERIAL14";
            // 原材料15
          } else if (item.equals("47")) {
            columns.add(new CsvColumnImpl("material15", "service.data.csv.CCommodityHeaderCsvSchema.78", CsvDataType.STRING));
            sql += ",CH.MATERIAL15";
            // 成分1
          } else if (item.equals("48")) {
            columns.add(new CsvColumnImpl("ingredient_name1", "service.data.csv.CCommodityHeaderCsvSchema.34", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME1";
            // 成分量1
          } else if (item.equals("49")) {
            columns.add(new CsvColumnImpl("ingredient_val1", "service.data.csv.CCommodityHeaderCsvSchema.35", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL1";
            //
          } else if (item.equals("50")) {
            columns.add(new CsvColumnImpl("ingredient_name2", "service.data.csv.CCommodityHeaderCsvSchema.36", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME2";
            //
          } else if (item.equals("51")) {
            columns.add(new CsvColumnImpl("ingredient_val2", "service.data.csv.CCommodityHeaderCsvSchema.37", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL2";
            //
          } else if (item.equals("52")) {
            columns.add(new CsvColumnImpl("ingredient_name3", "service.data.csv.CCommodityHeaderCsvSchema.38", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME3";
            //
          } else if (item.equals("53")) {
            columns.add(new CsvColumnImpl("ingredient_val3", "service.data.csv.CCommodityHeaderCsvSchema.39", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL3";
            //
          } else if (item.equals("54")) {
            columns.add(new CsvColumnImpl("ingredient_name4", "service.data.csv.CCommodityHeaderCsvSchema.40", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME4";
            //
          } else if (item.equals("55")) {
            columns.add(new CsvColumnImpl("ingredient_val4", "service.data.csv.CCommodityHeaderCsvSchema.41", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL4";
            //
          } else if (item.equals("56")) {
            columns.add(new CsvColumnImpl("ingredient_name5", "service.data.csv.CCommodityHeaderCsvSchema.42", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME5";
            //
          } else if (item.equals("57")) {
            columns.add(new CsvColumnImpl("ingredient_val5", "service.data.csv.CCommodityHeaderCsvSchema.43", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL5";
            //
          } else if (item.equals("58")) {
            columns.add(new CsvColumnImpl("ingredient_name6", "service.data.csv.CCommodityHeaderCsvSchema.44", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME6";
            //
          } else if (item.equals("59")) {
            columns.add(new CsvColumnImpl("ingredient_val6", "service.data.csv.CCommodityHeaderCsvSchema.45", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL6";
            //
          } else if (item.equals("60")) {
            columns.add(new CsvColumnImpl("ingredient_name7", "service.data.csv.CCommodityHeaderCsvSchema.46", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME7";
            //
          } else if (item.equals("61")) {
            columns.add(new CsvColumnImpl("ingredient_val7", "service.data.csv.CCommodityHeaderCsvSchema.47", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL7";
            //
          } else if (item.equals("62")) {
            columns.add(new CsvColumnImpl("ingredient_name8", "service.data.csv.CCommodityHeaderCsvSchema.48", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME8";
            //
          } else if (item.equals("63")) {
            columns.add(new CsvColumnImpl("ingredient_val8", "service.data.csv.CCommodityHeaderCsvSchema.49", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL8";
            //
          } else if (item.equals("64")) {
            columns.add(new CsvColumnImpl("ingredient_name9", "service.data.csv.CCommodityHeaderCsvSchema.50", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME9";
            //
          } else if (item.equals("65")) {
            columns.add(new CsvColumnImpl("ingredient_val9", "service.data.csv.CCommodityHeaderCsvSchema.51", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL9";
            // 成分10
          } else if (item.equals("66")) {
            columns
                .add(new CsvColumnImpl("ingredient_name10", "service.data.csv.CCommodityHeaderCsvSchema.52", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME10";
            // 成分量10
          } else if (item.equals("67")) {
            columns.add(new CsvColumnImpl("ingredient_val10", "service.data.csv.CCommodityHeaderCsvSchema.53", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL10";
            // 成分11
          } else if (item.equals("68")) {
            columns
                .add(new CsvColumnImpl("ingredient_name11", "service.data.csv.CCommodityHeaderCsvSchema.54", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME11";
            // 成分量11
          } else if (item.equals("69")) {
            columns.add(new CsvColumnImpl("ingredient_val11", "service.data.csv.CCommodityHeaderCsvSchema.55", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL11";
            // 成分12
          } else if (item.equals("70")) {
            columns
                .add(new CsvColumnImpl("ingredient_name12", "service.data.csv.CCommodityHeaderCsvSchema.56", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME12";
            // 成分量12
          } else if (item.equals("71")) {
            columns.add(new CsvColumnImpl("ingredient_val12", "service.data.csv.CCommodityHeaderCsvSchema.57", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL12";
            // 成分13
          } else if (item.equals("72")) {
            columns
                .add(new CsvColumnImpl("ingredient_name13", "service.data.csv.CCommodityHeaderCsvSchema.58", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME13";
            // 成分量13
          } else if (item.equals("73")) {
            columns.add(new CsvColumnImpl("ingredient_val13", "service.data.csv.CCommodityHeaderCsvSchema.59", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL13";
            // 成分14
          } else if (item.equals("74")) {
            columns
                .add(new CsvColumnImpl("ingredient_name14", "service.data.csv.CCommodityHeaderCsvSchema.60", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME14";
            // 成分量14
          } else if (item.equals("75")) {
            columns.add(new CsvColumnImpl("ingredient_val14", "service.data.csv.CCommodityHeaderCsvSchema.61", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL14";
            // 成分15
          } else if (item.equals("76")) {
            columns
                .add(new CsvColumnImpl("ingredient_name15", "service.data.csv.CCommodityHeaderCsvSchema.62", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_NAME15";
            // 成分量15
          } else if (item.equals("77")) {
            columns.add(new CsvColumnImpl("ingredient_val15", "service.data.csv.CCommodityHeaderCsvSchema.63", CsvDataType.STRING));
            sql += ",CH.INGREDIENT_VAL15";
          }
        }
        CCommodityHeaderExportCondition condition = CsvExportType.EXPORT_CSV_COMMODITY_HEAD_DATA.createConditionInstance();
        condition.setCommodityCode(bean.getSearchCommodityCode());
        condition.setSkuCode(bean.getSearchSkuCode());
        condition.setColumns(columns);
        condition.setSqlString(sql);
        if (bean.getSearchExportTemplateFlg().equals("2")) {
          condition.setHeaderOnly(true);
        } else {
          condition.setHeaderOnly(false);
        }
        this.exportCondition = condition;
        // 商品分类关联设置模版
      } else if (bean.getSearchAppointExportObject().equals("6")) {
        // 20120206 os013 add start

        columns.add(new CsvColumnImpl("commodity_code", "service.data.csv.CategoryAttributeValueDataCsvSchema.1",
            CsvDataType.STRING, false, false, true, null));
        columns.add(new CsvColumnImpl("category_code", "service.data.csv.CategoryAttributeValueDataCsvSchema.2",
            CsvDataType.STRING, false, false, true, null));
        for (String item : bean.getCategoryAttributeValueItem()) {
          if (item.equals("2")) {
            columns.add(new CsvColumnImpl("original_code", "service.data.csv.CategoryAttributeValueDataCsvSchema.60",
                CsvDataType.STRING));
            sql += ",cav.original_code as  original_code";
          } else if (item.equals("3")) {
            columns.add(new CsvColumnImpl("category_attribute_value", "service.data.csv.CategoryAttributeValueDataCsvSchema.20",
                CsvDataType.STRING));
            sql += ",max(category_attribute_value2) as  category_attribute_value2";
          } else if (item.equals("4")) {
            columns.add(new CsvColumnImpl("category_attribute_value", "service.data.csv.CategoryAttributeValueDataCsvSchema.55",
                CsvDataType.STRING));
            sql += ",max(category_attribute_value2_en) as  category_attribute_value2_en";
          } else if (item.equals("5")) {
            columns.add(new CsvColumnImpl("category_attribute_value", "service.data.csv.CategoryAttributeValueDataCsvSchema.58",
                CsvDataType.STRING));
            sql += ",max(category_attribute_value2_jp) as  category_attribute_value2_jp";
          } else if (item.equals("6")) {
            columns.add(new CsvColumnImpl("category_attribute_value", "service.data.csv.CategoryAttributeValueDataCsvSchema.21",
                CsvDataType.STRING));
            sql += ",max(category_attribute_value3) as  category_attribute_value3";
          } else if (item.equals("7")) {
            columns.add(new CsvColumnImpl("category_attribute_value", "service.data.csv.CategoryAttributeValueDataCsvSchema.56",
                CsvDataType.STRING));
            sql += ",max(category_attribute_value3_en) as  category_attribute_value3_en";
          } else if (item.equals("8")) {
            columns.add(new CsvColumnImpl("category_attribute_value", "service.data.csv.CategoryAttributeValueDataCsvSchema.59",
                CsvDataType.STRING));
            sql += ",max(category_attribute_value3_jp) as  category_attribute_value3_jp";
          } else if (item.equals("9")) {
            columns.add(new CsvColumnImpl("import_flag", "service.data.csv.CategoryAttributeValueDataCsvSchema.23",
                CsvDataType.STRING));
            sql += ",1 as import_flag";
          }
        }
        CategoryAttributeValueDataExportCondition condition = CsvExportType.EXPORT_CSV_CATEGORY_ATTRIBUTE_VALUE_DATA
            .createConditionInstance();
        condition.setCommodityCode(bean.getSearchCommodityCode());
        condition.setSkuCode(bean.getSearchSkuCode());
        condition.setColumns(columns);
        condition.setSqlString(sql);
        if (bean.getSearchExportTemplateFlg().equals("2")) {
          condition.setHeaderOnly(true);
        } else {
          condition.setHeaderOnly(false);
        }
        this.exportCondition = condition;
        // 20120206 os013 add end
      }

    }

    // 画面表示用Beanを次画面Beanを設定
    setRequestBean(bean);
    setNextUrl("/download");

    return BackActionResult.RESULT_SUCCESS;
  }

  private CsvExportCondition<? extends CsvSchema> exportCondition;

  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return exportCondition;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityCsvExportInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3180004003";
  }

}
