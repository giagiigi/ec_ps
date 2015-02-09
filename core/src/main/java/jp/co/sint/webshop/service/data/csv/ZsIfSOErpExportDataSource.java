package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;

public class ZsIfSOErpExportDataSource extends SqlExportDataSource<ZsIfSOErpExportCsvSchema, ZsIfSOErpExportCondition> {

  WebshopConfig config = DIContainer.getWebshopConfig();

  private boolean headerOnly = false;

  // 导出过程中临时设定的shippingStatus值
  private Long shippingStatus = Long.parseLong("9");

  /**
   * shippingStatusを取得します。
   * 
   * @return shippingStatus
   */
  public Long getShippingStatus() {
    return shippingStatus;
  }

  private PreparedStatement updateStatementEC = null;

  private PreparedStatement updateStatementTMALL = null;
  
  private PreparedStatement updateStatementJD = null;

  private Logger logger = Logger.getLogger(this.getClass());

  // EC侧
  String updateEcSql = "UPDATE SHIPPING_HEADER SET SHIPPING_STATUS_WMS = ? , UPDATED_USER = ?, UPDATED_DATETIME = ?  WHERE SHIPPING_STATUS_WMS = ? "
      + " AND  ORDER_NO IN (  SELECT  B.ORDER_NO  FROM  ORDER_HEADER A INNER JOIN SHIPPING_HEADER B "
      + "ON A.SHOP_CODE=B.SHOP_CODE  AND A.ORDER_NO=B.ORDER_NO   AND B.SHIPPING_STATUS_WMS=? AND A.ORDER_FLG='1')";

  // Tmall侧
  String updateTMALLSql = "UPDATE TMALL_SHIPPING_HEADER SET SHIPPING_STATUS_WMS = ? , UPDATED_USER = ?, UPDATED_DATETIME = ?  WHERE SHIPPING_STATUS_WMS = ? "
      + " AND  ORDER_NO IN (  SELECT  B.ORDER_NO  FROM  TMALL_ORDER_HEADER A INNER JOIN TMALL_SHIPPING_HEADER B "
      + "ON A.SHOP_CODE=B.SHOP_CODE  AND A.ORDER_NO=B.ORDER_NO   AND B.SHIPPING_STATUS_WMS=?  AND A.ORDER_FLG='1')";
  
  // JD侧
//  String updateJDSql = "UPDATE JD_SHIPPING_HEADER SET SHIPPING_STATUS_WMS = ? , UPDATED_USER = ?, UPDATED_DATETIME = ?  WHERE SHIPPING_STATUS_WMS = ? "
//      + " AND  ORDER_NO IN (  SELECT  B.ORDER_NO  FROM  JD_ORDER_HEADER A INNER JOIN JD_SHIPPING_HEADER B "
//      + "ON A.SHOP_CODE=B.SHOP_CODE  AND A.ORDER_NO=B.ORDER_NO   AND B.SHIPPING_STATUS_WMS=?  AND A.ORDER_FLG='1')";
  String updateJDSql = "UPDATE JD_SHIPPING_HEADER SET SHIPPING_STATUS_WMS = ? , UPDATED_USER = ?, UPDATED_DATETIME = ?  WHERE SHIPPING_STATUS_WMS = ? "
    + " AND (CASE WHEN CHILD_ORDER_NO IS NOT NULL THEN CHILD_ORDER_NO ELSE ORDER_NO END IN ( SELECT CASE WHEN B.CHILD_ORDER_NO IS NOT NULL THEN B.CHILD_ORDER_NO ELSE B.ORDER_NO END AS ORDER_NO "
      + " FROM JD_ORDER_HEADER A INNER JOIN JD_SHIPPING_HEADER B ON A.SHOP_CODE=B.SHOP_CODE" +
          " WHERE A.ORDER_NO=B.ORDER_NO AND B.SHIPPING_STATUS_WMS = ? AND A.ORDER_FLG='1' ))";

  // 截取addr1赋值给addr1,addr2 和addr3
  public void subStringAddrName(List<String> row, List<CsvColumn> columnList) {
      // 20140304 del start
//    String addr1Str = "";
//    String addr2Str = "";
//    String addr3Str = "";
//    String remarks1 = "";
//    String remarks2 = "";
//    String remarks3 = "";
      // 20140304 del end
    for (int i = 0; i < row.size(); i++) {
      String columnName = columnList.get(i).getPhysicalName();
      // 20140304 del start
//      // 地址1
//      if (columnName.equals("ADDR1") || columnName.equals("addr1")) {
//        String columnData = StringUtil.parse(row.get(i));
//        addr1Str = StringUtil.subStringByByte(columnData, 28);
//        row.set(i, addr1Str);
//        addr2Str = columnData.substring(addr1Str.length());
//      }
//      // 地址2
//      if (columnName.equals("ADDR2") || columnName.equals("addr2")) {
//        // String columnData = row.get(i);
//        String addr2Str2 = StringUtil.subStringByByte(addr2Str, 28);
//        row.set(i, addr2Str2);
//        addr3Str = addr2Str.substring(addr2Str2.length());
//      }
//      // 地址3
//      if (columnName.equals("ADDR3") || columnName.equals("addr3")) {
//        String addr3Str3 = StringUtil.subStringByByte(addr3Str, 28);
//        row.set(i, addr3Str3);
//      }
      // 20140304 del end
      // 电话号码
      if (columnName.equals("PHONE_NUMBER") || columnName.equals("phone_number")) {
        String phone = StringUtil.formatPhone(row.get(i));
        row.set(i, phone);
      }
      // 电话号码分机EXT_NO
      // if (columnName.equals("EXT_NO") || columnName.equals("ext_no")) {
      // String phoneExt = StringUtil.formatPhone_Ext(row.get(i));
      // row.set(i, phoneExt);
      // }
      // 20140304 del start
//      // 电话号码分机COUNTY
//      if (columnName.equals("COUNTY") || columnName.equals("county")) {
//        String phoneExt = StringUtil.formatPhone_Ext(row.get(i));
//        row.set(i, phoneExt);
//      }
      // 20140304 del end
      // Fax电话号码
      if (columnName.equals("MOBILE_NUMBER") || columnName.equals("mobile_number")) {
        String phone = StringUtil.formatPhone(row.get(i));
        row.set(i, phone);
      }
      // 20140304 del start
//      // 拼接备注
//      if (columnName.equals("SALESPERSON1") || columnName.equals("Salesperson1")) {
//        String columnData = row.get(i);
//        if (!StringUtil.isNullOrEmpty(columnData)) {
//          remarks1 = StringUtil.subStringByByte(columnData, 76);
//          String remarks2Temp = columnData.substring(remarks1.length());
//          if (!StringUtil.isNullOrEmpty(remarks2Temp)) {
//            remarks2 = StringUtil.subStringByByte(remarks2Temp, 76);
//            String remarks3Temp = remarks2Temp.substring(remarks2.length());
//            if (!StringUtil.isNullOrEmpty(remarks3Temp)) {
//              remarks3 = StringUtil.subStringByByte(remarks3Temp, 76);
//            }
//          }
//        }
//        row.set(i, "");
//      }
    
      // 发票地址
//      if (columnName.equals("COMMENTS") || columnName.equals("comments") || columnName.equals("Comments")) {
//        String comments = StringUtil.formatComments(row.get(i));
//        comments = comments + remarks1 + ";" + remarks2 + ";" + remarks3 + ";";
//        comments = StringUtil.replaceCRLF(comments);
//        row.set(i, comments);
//      }
     
//      // 商品内容长度处理content
//      if (columnName.equals("COMMENT") || columnName.equals("comment") || columnName.equals("Comment")) {
//        // String comment = StringUtil.formatComment(row.get(i));
//        String comment = StringUtil.formatCommentNew(row.get(i));
//        comment = StringUtil.replaceCRLF(comment);
//        row.set(i, comment);
//      }
     
//      // 顾客姓名处理
//      if (columnName.equals("address_last_name") || columnName.equals("ADDRESS_LAST_NAME")) {
//        String userName = StringUtil.parse(row.get(i));
//        row.set(i, userName);
//      }
      // 20140304 del end
    }
  }

  /*
   * 导出前把数据库中所有的SHIPPING_STATUS值为1的数据的SHIPPING_STATUS值更新成9
   */
  public void beforeExport() {

    logger.debug("UPDATE SHIPPING_HEADER statement: " + updateEcSql + " AND ORDER_NO IN (" + getCondition().getShipOrderNo() + ")");
    logger.debug("UPDATE TMALL_SHIPPING_HEADER statement: " + updateTMALLSql + " AND ORDER_NO IN (" + getCondition().getShipOrderNo() + ")");
    logger.debug("UPDATE JD_SHIPPING_HEADER statement: " + updateJDSql + " AND CASE WHEN CHILD_ORDER_NO IS NOT NULL THEN CHILD_ORDER_NO ELSE ORDER_NO END  IN (" + getCondition().getShipOrderNo() + ")");
    try {
      // EC侧更新SHIPPING_STATUS状态
      updateStatementEC = createPreparedStatement(updateEcSql + " AND ORDER_NO IN (" + getCondition().getShipOrderNo() + ")");
      // TMALL侧更新SHIPPING_STATUS状态
      updateStatementTMALL = createPreparedStatement(updateTMALLSql + " AND ORDER_NO IN (" + getCondition().getShipOrderNo() + ")");
      // JD侧更新SHIPPING_STATUS状态
      updateStatementJD = createPreparedStatement(updateJDSql + " AND CASE WHEN CHILD_ORDER_NO IS NOT NULL THEN CHILD_ORDER_NO ELSE ORDER_NO END IN (" + getCondition().getShipOrderNo() + ")");
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    List<Object> updateShippingParams = new ArrayList<Object>();
    updateShippingParams.add(getShippingStatus());
    updateShippingParams.add("BATCH:0:0:0");
    updateShippingParams.add(DateUtil.getSysdate());
    updateShippingParams.add(ShippingStatus.READY.longValue());
    updateShippingParams.add(ShippingStatus.READY.longValue());
    logger.debug("Table:SHIPPING_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));
    logger.debug("Table:TMALL_SHIPPING_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));
    logger.debug("Table:JD_SHIPPING_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));

    try {
      // EC侧更新
      DatabaseUtil.bindParameters(updateStatementEC, ArrayUtil.toArray(updateShippingParams, Object.class));
      int rowsEc = updateStatementEC.executeUpdate();
      // TMALL侧更新
      DatabaseUtil.bindParameters(updateStatementTMALL, ArrayUtil.toArray(updateShippingParams, Object.class));
      int rowsTm = updateStatementTMALL.executeUpdate();
      // JD侧更新
      DatabaseUtil.bindParameters(updateStatementJD, ArrayUtil.toArray(updateShippingParams, Object.class));
      int rowsJd = updateStatementJD.executeUpdate();
      // 判断是不是无数据，只有标题行
      if (rowsEc <= 0 && rowsTm <= 0 && rowsJd <= 0) {
        headerOnly = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // 判断是不是无数据，只有标题行实现方法
  public boolean headerOnly() {
    return headerOnly;
  }

  /*
   * 导出成功后把数据库中所有的SHIPPING_STATUS值为9的数据的SHIPPING_STATUS值更新成2
   */
  public void afterExport() {

    logger.debug("UPDATE SHIPPING_HEADER statement: " + updateEcSql + " AND ORDER_NO IN (" + getCondition().getShipOrderNo() + ")");
    logger.debug("UPDATE TMALL_SHIPPING_HEADER statement: " + updateTMALLSql + " AND ORDER_NO IN (" + getCondition().getShipOrderNo() + ")");
    logger.debug("UPDATE JD_SHIPPING_HEADER statement: " + updateJDSql + " AND CASE WHEN CHILD_ORDER_NO IS NOT NULL THEN CHILD_ORDER_NO ELSE ORDER_NO END IN(" + getCondition().getShipOrderNo() + ")");
    try {
      // EC侧更新SHIPPING_STATUS状态
      updateStatementEC = createPreparedStatement(updateEcSql + " AND ORDER_NO IN (" + getCondition().getShipOrderNo() + ")");
      // TMALL侧更新SHIPPING_STATUS状态
      updateStatementTMALL = createPreparedStatement(updateTMALLSql + " AND ORDER_NO IN (" + getCondition().getShipOrderNo() + ")");
      // JD侧更新SHIPPING_STATUS状态
      updateStatementJD = createPreparedStatement(updateJDSql + " AND CASE WHEN CHILD_ORDER_NO IS NOT NULL THEN CHILD_ORDER_NO ELSE ORDER_NO END IN(" + getCondition().getShipOrderNo() + ")");
    } catch (Exception e) {
      throw new DataAccessException(e);
    }

    List<Object> updateShippingParams = new ArrayList<Object>();
    updateShippingParams.add(ShippingStatus.IN_PROCESSING.longValue());
    updateShippingParams.add("BATCH:0:0:0");
    updateShippingParams.add(DateUtil.getSysdate());
    updateShippingParams.add(getShippingStatus());
    updateShippingParams.add(getShippingStatus());
    logger.debug("Table:SHIPPING_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));
    logger.debug("Table:TMALL_SHIPPING_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));
    logger.debug("Table:JD_SHIPPING_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));

    try {
      // EC侧更新
      DatabaseUtil.bindParameters(updateStatementEC, ArrayUtil.toArray(updateShippingParams, Object.class));
      updateStatementEC.executeUpdate();
      // TMALL侧更新
      DatabaseUtil.bindParameters(updateStatementTMALL, ArrayUtil.toArray(updateShippingParams, Object.class));
      updateStatementTMALL.executeUpdate();
      // JD侧更新
      DatabaseUtil.bindParameters(updateStatementJD, ArrayUtil.toArray(updateShippingParams, Object.class));
      updateStatementJD.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Query getExportQuery() {

    String sql = "" + "SELECT ORDER_NO " // 订单号
        + " ,(CASE WHEN TALL_EC_FLG='0' and GUEST_FLG='1'  THEN '"
        + config.getIfCustCdEcGuest()
        + "' when  TALL_EC_FLG='0' and GUEST_FLG='0' then  substr(customer_code,LENGTH(customer_code)-7,8) "
        + " when TALL_EC_FLG='1' then '"
        + config.getIfCustCdTmall()
        + "' WHEN TALL_EC_FLG='2' THEN 'TMALL' WHEN TALL_EC_FLG='3' THEN 'GUESTJD' END) AS CUSTOMER_CODE" // 会员编号

        + " ,(CASE WHEN TALL_EC_FLG='0' and GUEST_FLG='1' THEN '"
        + config.getIfCustCdEcGuest()
        + "' when  TALL_EC_FLG='0' and GUEST_FLG='0' then  substr(customer_code,LENGTH(customer_code)-7,8) "
        + " when TALL_EC_FLG='1' then '"
        + config.getIfCustCdTmall()
        + "' when TALL_EC_FLG='2' then 'TMALL' WHEN TALL_EC_FLG='3' THEN 'GUESTJD' END) AS CUSTOMER_CODE" // 会员编号

        + " ,ORDER_NO" // 订单号
        + " ,ORDER_DATETIME" // 订单制作日
        + " ,PAYMENT_LIMIT_DATE" // 到期日
        + " , '' AS PRICE_LIST_CODE" // Manuals手册
        + " , "
        + "'"
        + config.getIfSite()
        + "'"
        + " AS SITE " // Site地点
        + " , 'Y' AS CONFIRMED" // Confirmed确认
        + " , 'RMB' AS CURRENCY" // Currency货币
        + ", Channel " // 通道 定义T-Mall/EC区分
        + " , ( CASE WHEN PAYMENT_METHOD_TYPE ='02' then "
        + "'"
        + config.getCreditTerms().get(2) // COD
        + "' WHEN PAYMENT_METHOD_TYPE ='12' THEN  "
        + "'"
        + config.getCreditTerms().get(1) // 银联
        + "' WHEN PAYMENT_METHOD_TYPE ='11' AND (TALL_EC_FLG='1' OR TALL_EC_FLG='2' ) THEN  "
        + "'"
        + config.getCreditTerms().get(4) // Tmall支付宝
        + "' WHEN PAYMENT_METHOD_TYPE ='11' AND TALL_EC_FLG='0' THEN  "
        + "'"
        + config.getCreditTerms().get(0) // EC支付宝
        + "' WHEN PAYMENT_METHOD_TYPE ='00'  THEN  "
        + "'"
        + config.getCreditTerms().get(5) // 礼品卡支付
        + "' WHEN PAYMENT_METHOD_TYPE ='14'  THEN  "
        + "'"
        + config.getCreditTerms().get(6) // 银联外卡支付
        + "' WHEN PAYMENT_METHOD_TYPE ='15'  THEN  "
        + "'"
        + config.getCreditTerms().get(7) // 支付宝无线支付
        + "' WHEN PAYMENT_METHOD_TYPE ='16'  THEN  "
        + "'"
        + config.getCreditTerms().get(8) // 银联信用卡内卡
        + "' WHEN PAYMENT_METHOD_TYPE ='17'  THEN  "
        + "'"
        + config.getCreditTerms().get(9) // 京东在线支付
        + "' END)  AS Credit_Terms " // Credit Terms 信贷条件COD/银联/淘宝
        
        // 20140304 add start
        + " ,'' AS Salesperson1" // Salesperson 1 销售员
        // 20140304 add end
        // 20140304 del start
        //+ " ,CAUTION AS Salesperson1" // Salesperson 1 销售员
        // 20140304 del end
        + " ,'' AS Salesperson2" // Salesperson 2 销售员
        + " ,'' AS Salesperson3" // Salesperson 3 销售员
        + " ,'' AS Salesperson4" // Salesperson 4 销售员
        // 20140304 add start
        + " ,'' AS Comments" // Comments 发票信息null
        + " ,'' AS Remarks" // Remarks 备注ull
        // 20140304 add end
        // 20140304 del start
//        + " , ( " // 发票希望标志
//        + " (CASE WHEN INVOICE_FLG='0' THEN 'N' WHEN INVOICE_FLG='1' THEN 'Y' END) "
//        + " || ';'" // 发票希望标志
//        + " || (CASE WHEN invoice_type='0' THEN '1' WHEN invoice_type='1' THEN '2' END) "
//        + " || ';'" // 发票类型
//        + " || (CASE WHEN INVOICE_CUSTOMER_NAME='' OR INVOICE_CUSTOMER_NAME IS NULL THEN '' ELSE INVOICE_CUSTOMER_NAME END )"
//        + " || ';'" // 发票抬头
//        + " || (case when invoice_commodity_name='' or invoice_commodity_name is null then '' else invoice_commodity_name end ) "
//        + " || ';'" // 发票内容
//        + " || (case when address='' or address is null then '' else address end)"
//        + " || ';'" // 开票地址
//        + " || (case when company_name='' or company_name is null then '' else company_name end)"
//        + " || ';'" // 开票公司
//        + " || (case when bank_name='' or bank_name is null then '' else bank_name end )"
//        + " || ';'" // 银行名称
//        + " || (case when bank_no='' or bank_no is null then '' else bank_no end )"
//        + " || ';'" // 银行编号
//        + " || (case when tel='' or tel is null then '' else tel end)"
//        + " || ';'" // 联系电话
//        + " || (case when taxpayer_code='' or taxpayer_code is null then '' else taxpayer_code end)"
//        + " || ';'" // 纳税人识别号
//        + " ) AS Comments" // Comments说明
//        + " ,((case when customer_delivery_appointed_date ='' or  customer_delivery_appointed_date is null or customer_delivery_appointed_date='休息日' or customer_delivery_appointed_date='平日' or customer_delivery_appointed_date='指定不可' or customer_delivery_appointed_date='不可指定日期' "
//        + " then '' else customer_delivery_appointed_date  || ';'  end) "
//        + " || "
//        + " (case when delivery_appointed_time_start is null  or delivery_appointed_time_end is null then '' "
//        + "  else delivery_appointed_time_start || ':00' || '～' || delivery_appointed_time_end || ':00' end) "
//        + " ) AS Remarks "
        // 20140304 del end
        + " ,delivery_appointed_date AS Required_Date" // Required Date配送希望日
        + " ,'' AS Promised_Date" // Promised Date承诺日期
        + " ,'' AS Pricing_Date" // Pricing Date定价日期
        + " ,'' AS Project" // Project项目
        + " ,'' AS Language" // Language言语
        + " ,(case when TALL_EC_FLG='0' then '' when (TALL_EC_FLG='1' OR TALL_EC_FLG='2' OR TALL_EC_FLG='3' ) then CUSTOMER_CODE end) AS Customer_PO" // Customer
        // PO客户采购单
        + " ,'' AS SO_Credit_Interest" // SO Credit Interest %支付方式利息
        + " ,'Y' AS Fixed_Price" // 固定价格
        + " ,'' AS Frieght_List" // Frieght List运价表
        + " ,'' AS Frt_Min_Wgt" // Frt Min Wgt运货最轻重量
        + " ,'' AS Freight_Terms" // Freight Terms运输条件
        + " ,'' AS Calculate_Freight" // Calculate Freight计算运费
        + " ,'' AS Display_Weight" // Display Weight显示重量
        + " ,'' AS Consume_Forecast" // Consume Forecast消耗预测量
        + " ,'' AS Detail_Allocations" // Detail Allocations详细分配
        + " ,'' AS Allocate_days" // Allocate days分配天数
        + " ,'N' AS Import_Export" // Import/Export输入/输出
        + " ,'TAX-CH' AS Tax_Usage" // Tax Usage税用途
        + " ,'CH-TAX' AS Tax_Environmen" // Tax Environment纳税环境
        + " ,'VAT' AS Tax_Class" // Tax Class税收类别
        + " ,order_datetime" // Tax Date定税日期
        + " ,'Y' AS Taxable" // Taxable应纳税
        + " ,'Y' AS Tax_In" // Tax In含税
        //+ " ,shipping_detail_no" // 发货明细编号,行
        + " ,ROW_NUMBER() OVER(PARTITION BY ORDER_NO ORDER BY SHIPPING_DETAIL_NO) AS  SHIPPING_DETAIL_NO" // 发货明细编号,行
        + " ,(CASE WHEN SKU_CODE_FLG='0' THEN SKU_CODE WHEN SKU_CODE_FLG='1' THEN " + "'"
        + config.getIfSkuKbnList().get(0) // 运费订单sku
        + "'" + " WHEN SKU_CODE_FLG='2' THEN   " + "'"
        + config.getIfSkuKbnList().get(1) // 优惠订单sku
        + "'" + " WHEN SKU_CODE_FLG='3' THEN   " + "'"
        + config.getIfSkuKbnList().get(2) // 积分订单sku
        + "'" + " WHEN SKU_CODE_FLG='4' THEN   " + "'"
        + config.getIfSkuKbnList().get(3) // 淘宝折扣
        + "'" + " WHEN SKU_CODE_FLG='5' THEN   " + "'"
        + config.getIfSkuKbnList().get(4) // 淘宝店铺折扣
        + "'" + " WHEN SKU_CODE_FLG='6' THEN   " + "'"
        + config.getIfSkuKbnList().get(5) // 满就送活动
        + "'" + " WHEN SKU_CODE_FLG='7' THEN   " + "'"
        + config.getIfSkuKbnList().get(6) // 礼品卡支付
        + "'" + " WHEN SKU_CODE_FLG='8' THEN   " + "'"
        + config.getIfSkuKbnList().get(7) // 银联外卡支付
        + "'" + " WHEN SKU_CODE_FLG='9' THEN   " + "'"
        + config.getIfSkuKbnList().get(8) // 京东店铺优惠
        + "' END) AS sku_code" // sku编号
        + " ,purchasing_amount" // Qty Ordered订货数量
        + " , " + "'"
        + config.getIfCmdtyUm()
        + "'"
        + "  AS erpUm" // Um 单位
        + " ,retail_price" // 販売価格List Price标价
        + " ,'' AS Discount" // Discount折扣
        + " ,'' AS Net_Price" // Net Price净价
        + " , "
        + "'"
        + config.getIfLocationForEc()
        + "'  AS Loc" // Loc
        + " ,'' AS Lot_Serial" // Lot/Serial
        + " ,'' AS Qty_Allocated" // Qty Allocated
        + " ,'' AS Commission1" // Commission 1
        + " ,'' AS Commission2" // Commission 2
        + " ,'' AS Commission3" // Commission 3
        + " ,'' AS Commission4" // Commission 4
        + " ,'' AS Sales_Account" // Sales_Account
        + " ,'' AS Sales_Sub_Account" // Sales sub-account
        + " ,'' AS Sales_Cost_Center" // Sales Cost Center
        + " ,'' AS Sales_Project" // Sales Project
        + " ,'' AS Discount_Account" // Discount Account
        + " ,'' AS Discount_sub_account" // Discount_sub_account
        + " ,'' AS Discount_Cost_Center" // Discount Cost Center
        + " ,'' AS Discount_Project" // Discount Project
        + " ,'Y' AS Confirmed" // Confirmed
        + " ,'' AS Required" // Required
        + " ,'' AS Promised" // Promised
        + " ,'' AS Due_Date" // Due Date
        + " ,'' AS Perform_Date" // Perform Date
        + " ,'Y' AS Fixed_Price" // Fixed Price
        + " ,'' AS Ship_Type" // Ship Type
        + " ,'' AS UM_Conversion" // UM Conversion
        + " ,'' AS Consume_Forecast" // Consume Forecast
        + " ,'' AS Detail_Allocation" // Detail Allocation
        + " ,'' AS Taxable" // Taxable
        + " ,'' AS Tax_Class" // Tax Class
        + " ,'' AS Freight_List" // Freight List
        // 20140304 add start
        + " ,'' AS Comment" //Comment
        // 20140304 add end
        // 20140304 del start
        // + " ,(case when commodity_name is null then '' else commodity_name end ) AS Comment "
        // 20140304 del end
        + " ,'' AS Discount" // Discount %
        + " ,'' AS SO_Trailer1" // SO Trailer 1
        + " ,'' AS Trailer_Amount1" // Trailer Amount 1
        + " ,'' AS SO_Trailer2" // SO Trailer 2
        + " ,'' AS Trailer_Amount2" // Trailer Amount 2
        + " ,'' AS SO_Trailer3" // SO Trailer 3
        + " ,'' AS Trailer_Amount3" // Trailer Amount 3
        + " ,'' AS Invoice_Flag" // Invoice Flag发票标记
        + " ,'' AS Action_Status" // Action Status执行状态
        + " ,'' AS Print_Sales_Order" // 打印销售单
        + " ,'' AS Credit_Initials" // 信用缩写签署
        + " ,'' AS Revision" // 版本
        + " ,'' AS Partial_Ok" // 分批发货
        + " ,'' AS Print_invoice_History" // 打印发票历史记录
        + " ,'' AS Print_Pack_List" // 打印货运单
        + " ,'' AS EDI_invoice_History" // 电子数据交换发票历史
        + " ,'' AS AR_Account" // 应收账户
        + " ,'' AS erpA" // 空白列
        + " ,'' AS erpB" // 空白列
        + " ,'' AS Prepaid" // 已预付
        + " ,'' AS FOB_Point" // 离岸价格易主
        + " ,delivery_company_no" // 运输代理
        + " ,'' AS BOL" // 提单
        + " ,'' AS Customer_Confirm" // 客户确认
        + " ,order_no" // Ship to code 代码
        // 20140304 add start
        + " ,'ECUSER' AS address_last_name" // 收货人名称
        + " ,'china' AS ADDR1" // 收货地址
        // 20140304 add end
        // 20140304 del start
        //+ " ,address_last_name" // 收货人名称
        //+ " ,( CASE WHEN ADDRESS1 IS NULL THEN '' ELSE ADDRESS1 END  || CASE WHEN ADDRESS2 IS NULL THEN '' ELSE ADDRESS2 END || CASE WHEN ADDRESS3 IS NULL THEN '' ELSE ADDRESS3 END || CASE WHEN ADDRESS4 IS NULL THEN '' ELSE ADDRESS4 END) AS ADDR1"
        // 20140304 del end
        + " ,'' AS ADDR2" // ADDR2
        + " ,'' AS ADDR3" // ADDR3
        + " ,city_code" // 県?市コード
        + " ,prefecture_code" // 省コード
        + " ,postal_code" // 邮政编码
        + " ,'' AS FORMAT" // FORMAT
        + " ,'CHN' AS COUNTRY" // COUNTRY
        // 20140304 add start
        + " ,'' AS COUNTY" // COUNTY
        + " ,'ECUSER' AS address_last_name" // 收货人名称
        // 20140304 add end
        // 20140304 del start
        // + " ,phone_number AS COUNTY" // COUNTY
        // + " ,address_last_name" // 宛名：姓 ,担当者
        // 20140304 del end
        + " ,(case when phone_number='' OR phone_number is null then mobile_number else phone_number end) as phone_number" // 電話番号
        + " ,'' AS EXT_NO" // EXT NO
        + " ,(case when mobile_number='' OR mobile_number is null then phone_number else mobile_number end) as mobile_number" // 携帯電話FAX
        + " ,'' AS ATTN2" // ATTN2
        + " ,'' AS PHONE2" // PHONE2
        + " ,'' AS EXT_NO2" // EXT NO2
        + " ,'' AS FAX2" // FAX2
        + " ,'' AS LANG" // LANG
        // 20140304 add start
        + " ,'ECUSER' AS address_last_name" // 收货人名称
        // 20140304 add end
        // 20140304 del start
        // + " ,address_last_name" // SORTNAME
        // 20140304 del end
        + " ,'Y' AS Taxable" // Taxable
        + " ,'CH' AS Tax_Zone" // Taxable
        + " ,'VAT' AS Tax_Class" // Tax Class
        + " ,'TAX-CH' AS Tax_Usage" // Tax Usage
        + " ,'Y' AS Tax_In" // Tax In
        + " ,'' AS Tax_ID" // Tax ID
        + " ,'' AS Tax_ID_State" // Tax ID State
        + " ,'' AS MISC_ID1" // MISC ID1
        + " ,'' AS MISC_ID2" // MISC ID2
        + " ,'' AS MISC_ID3" // MISC ID3
        + " ,'' AS Tax_In_City" // Tax In City
        + " ,Line_Total_Amt" // 行总金额
        + " ,Order_Total_Amt" // 订单总金额
        + " FROM export_erp_shipping_info_view where ORDER_NO IN (" + getCondition().getShipOrderNo() + ")";
    Query q = null;
    sql += " ORDER BY ORDER_NO";
    q = new SimpleQuery(sql);

    return q;

  }
}
