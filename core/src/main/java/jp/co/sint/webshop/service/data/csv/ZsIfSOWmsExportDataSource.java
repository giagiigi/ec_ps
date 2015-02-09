package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.dao.CCommodityHeaderDao;
import jp.co.sint.webshop.data.dao.OrderDetailDao;
import jp.co.sint.webshop.data.dao.OrderHeaderDao;
import jp.co.sint.webshop.data.dao.TmallOrderDetailDao;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

public class ZsIfSOWmsExportDataSource extends SqlExportDataSource<ZsIfSOWmsExportCsvSchema, ZsIfSOWmsExportCondition> {

  WebshopConfig config = DIContainer.getWebshopConfig();
  
  //仅包含四个商品，快递走圆通
  private static final String[] tempStrYuanTong = {"0000522"};

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

  private PreparedStatement updateStatementECWms = null;

  private PreparedStatement updateStatementTMALLWms = null;

  private PreparedStatement updateStatementJDWms = null;

  private Logger logger = Logger.getLogger(this.getClass());

  // EC侧
  String updateEcSql = "UPDATE SHIPPING_HEADER SET SHIPPING_STATUS = ? , SHIPPING_DIRECT_DATE = ?, UPDATED_USER = ?, UPDATED_DATETIME = ?  WHERE SHIPPING_STATUS = ? "
      + " AND  ORDER_NO IN (  SELECT  B.ORDER_NO  FROM  ORDER_HEADER A INNER JOIN SHIPPING_HEADER B "
      + "ON A.SHOP_CODE=B.SHOP_CODE  AND A.ORDER_NO=B.ORDER_NO   AND B.SHIPPING_STATUS=? AND A.ORDER_FLG='1')";

  String updateEcWmsSql = "UPDATE SHIPPING_HEADER SET SHIPPING_STATUS_WMS = ?  WHERE SHIPPING_STATUS = ? "
      + " AND  ORDER_NO IN (  SELECT  B.ORDER_NO  FROM  ORDER_HEADER A INNER JOIN SHIPPING_HEADER B "
      + "ON A.SHOP_CODE=B.SHOP_CODE  AND A.ORDER_NO=B.ORDER_NO   AND B.SHIPPING_STATUS=? AND A.ORDER_FLG='1')";

  // Tmall侧
  String updateTMALLSql = "UPDATE TMALL_SHIPPING_HEADER SET SHIPPING_STATUS = ? , SHIPPING_DIRECT_DATE = ?, UPDATED_USER = ?, UPDATED_DATETIME = ?  WHERE SHIPPING_STATUS = ? "
      + " AND  ORDER_NO IN (  SELECT  B.ORDER_NO  FROM  TMALL_ORDER_HEADER A INNER JOIN TMALL_SHIPPING_HEADER B "
      + "ON A.SHOP_CODE=B.SHOP_CODE  AND A.ORDER_NO=B.ORDER_NO   AND B.SHIPPING_STATUS=?  AND A.ORDER_FLG='1')";

  String updateTMALLWmsSql = "UPDATE TMALL_SHIPPING_HEADER SET SHIPPING_STATUS_WMS = ? WHERE SHIPPING_STATUS = ? "
      + " AND  ORDER_NO IN (  SELECT  B.ORDER_NO  FROM  TMALL_ORDER_HEADER A INNER JOIN TMALL_SHIPPING_HEADER B "
      + "ON A.SHOP_CODE=B.SHOP_CODE  AND A.ORDER_NO=B.ORDER_NO   AND B.SHIPPING_STATUS=?  AND A.ORDER_FLG='1')";

  // JD侧
  String updateJdSql = "UPDATE JD_SHIPPING_HEADER SET SHIPPING_STATUS = ? , SHIPPING_DIRECT_DATE = ?, UPDATED_USER = ?, UPDATED_DATETIME = ?  WHERE SHIPPING_STATUS = ? "
      + " AND  ORDER_NO IN (  SELECT  B.ORDER_NO  FROM  JD_ORDER_HEADER A INNER JOIN JD_SHIPPING_HEADER B "
      + "ON A.SHOP_CODE=B.SHOP_CODE  AND A.ORDER_NO=B.ORDER_NO   AND B.SHIPPING_STATUS=?  AND A.ORDER_FLG='1')";

  String updateJdWmsSql = "UPDATE JD_SHIPPING_HEADER SET SHIPPING_STATUS_WMS = ? WHERE SHIPPING_STATUS = ? "
      + " AND  ORDER_NO IN (  SELECT  B.ORDER_NO  FROM  JD_ORDER_HEADER A INNER JOIN JD_SHIPPING_HEADER B "
      + "ON A.SHOP_CODE=B.SHOP_CODE  AND A.ORDER_NO=B.ORDER_NO   AND B.SHIPPING_STATUS=?  AND A.ORDER_FLG='1')";

  // 截取addr1赋值给addr1,addr2 和addr3
  public void subStringAddrName(List<String> row, List<CsvColumn> columnList) {
    String addr1Str = "";
    String addr2Str = "";
    String addr3Str = "";
    String remarks1 = "";
    String remarks2 = "";
    String remarks3 = "";
    String invoice = "";
    for (int i = 0; i < row.size(); i++) {
      String columnName = columnList.get(i).getPhysicalName();

      if (columnName.equals("ORDER_NO") || columnName.equals("order_no")) {
        OrderHeaderDao ohd = DIContainer.getDao(OrderHeaderDao.class);
        OrderHeader oh = ohd.load(row.get(i));
        if (oh != null && oh.getInvoiceFlg().toString().equals("0")) {
          OrderDetailDao odd = DIContainer.getDao(OrderDetailDao.class);
          CCommodityHeaderDao cchd = DIContainer.getDao(CCommodityHeaderDao.class);
          List<OrderDetail> odList = odd.findByQuery("SELECT * FROM ORDER_DETAIL WHERE ORDER_NO = ?", row.get(i));
          if (odList != null && odList.size() > 0) {
            for (OrderDetail orderDetail : odList) {
              CCommodityHeader ch = cchd.load("00000000", orderDetail.getCommodityCode());
              if (ch != null && ch.getNewReserveCommodityType2() != null) {
                if (ch.getNewReserveCommodityType2().toString().equals("1")) {
                  invoice = "Y;1;个人;家电;;;;;;;";
                }
              }
            }
          }
        }
      }
      // 地址1
      if (columnName.equals("ADDR1") || columnName.equals("addr1")) {
        String columnData = StringUtil.parse(row.get(i));
        addr1Str = StringUtil.subStringByByte(columnData, 200);
        row.set(i, addr1Str);
        addr2Str = columnData.substring(addr1Str.length());
      }
      // 地址2
      if (columnName.equals("ADDR2") || columnName.equals("addr2")) {
        // String columnData = row.get(i);
        String addr2Str2 = StringUtil.subStringByByte(addr2Str, 50);
        row.set(i, addr2Str2);
        addr3Str = addr2Str.substring(addr2Str2.length());
      }
      // 地址3
      if (columnName.equals("ADDR3") || columnName.equals("addr3")) {
        String addr3Str3 = StringUtil.subStringByByte(addr3Str, 50);
        row.set(i, addr3Str3);
      }
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
      // 电话号码分机COUNTY
      if (columnName.equals("COUNTY") || columnName.equals("county")) {
        String phoneExt = StringUtil.formatPhone_Ext(row.get(i));
        row.set(i, phoneExt);
      }
      // Fax电话号码
      if (columnName.equals("MOBILE_NUMBER") || columnName.equals("mobile_number")) {
        String phone = StringUtil.formatPhone(row.get(i));
        row.set(i, phone);
      }
      // 拼接备注
      if (columnName.equals("SALESPERSON1") || columnName.equals("Salesperson1")) {
        String columnData = row.get(i);
        if (!StringUtil.isNullOrEmpty(columnData)) {
          remarks1 = StringUtil.subStringByByte(columnData, 76);
          String remarks2Temp = columnData.substring(remarks1.length());
          if (!StringUtil.isNullOrEmpty(remarks2Temp)) {
            remarks2 = StringUtil.subStringByByte(remarks2Temp, 76);
            String remarks3Temp = remarks2Temp.substring(remarks2.length());
            if (!StringUtil.isNullOrEmpty(remarks3Temp)) {
              remarks3 = StringUtil.subStringByByte(remarks3Temp, 76);
            }
          }
        }
        row.set(i, "");
      }
      // 发票地址
      if (columnName.equals("COMMENTS") || columnName.equals("comments") || columnName.equals("Comments")) {
        String comments = "";
        if (StringUtil.hasValue(row.get(i))) {
          comments = StringUtil.formatComments(row.get(i));
        } else {
          comments = StringUtil.formatComments(invoice);
        }
        comments = comments + remarks1 + ";" + remarks2 + ";" + remarks3 + ";";
        comments = StringUtil.replaceCRLF(comments);
        row.set(i, comments);
      }

      // 商品内容长度处理content
      if (columnName.equals("COMMENT") || columnName.equals("comment") || columnName.equals("Comment")) {
        // String comment = StringUtil.formatComment(row.get(i));
        String comment = StringUtil.formatCommentNew(row.get(i));
        comment = StringUtil.replaceCRLF(comment);
        row.set(i, comment);
      }
      // 顾客姓名处理
      if (columnName.equals("address_last_name") || columnName.equals("ADDRESS_LAST_NAME")) {
        String userName = StringUtil.parse(row.get(i));
        row.set(i, userName);
      }
    }
  }

  /*
   * 导出前把数据库中所有的SHIPPING_STATUS值为1的数据的SHIPPING_STATUS值更新成9
   */
  /*
   * 导出前把数据库中所有的SHIPPING_STATUS值为1的数据的SHIPPING_STATUS值更新成9
   */
  public void beforeExport() {

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    logger.debug("UPDATE SHIPPING_HEADER statement: " + updateEcWmsSql);
    logger.debug("UPDATE TMALL_SHIPPING_HEADER statement: " + updateTMALLWmsSql);
    logger.debug("UPDATE JD_SHIPPING_HEADER statement: " + updateJdWmsSql);
    try {
      // EC侧更新SHIPPING_STATUS_WMS状态
      updateStatementECWms = createPreparedStatement(updateEcWmsSql);
      // TMALL侧更新SHIPPING_STATUS_WMS状态
      updateStatementTMALLWms = createPreparedStatement(updateTMALLWmsSql);
      // JD侧更新SHIPPING_STATUS_WMS状态
      updateStatementJDWms = createPreparedStatement(updateJdWmsSql);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    List<Object> updateShippingWmsParams = new ArrayList<Object>();
    updateShippingWmsParams.add(getShippingStatus());
    updateShippingWmsParams.add(ShippingStatus.READY.longValue());
    updateShippingWmsParams.add(ShippingStatus.READY.longValue());
    logger.debug("Table:SHIPPING_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingWmsParams, Object.class)));
    logger.debug("Table:TMALL_SHIPPING_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingWmsParams, Object.class)));
    logger.debug("Table:JD_SHIPPING_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingWmsParams, Object.class)));

    try {
      // EC侧更新
      DatabaseUtil.bindParameters(updateStatementECWms, ArrayUtil.toArray(updateShippingWmsParams, Object.class));
      int rowsEc = updateStatementECWms.executeUpdate();
      // TMALL侧更新
      DatabaseUtil.bindParameters(updateStatementTMALLWms, ArrayUtil.toArray(updateShippingWmsParams, Object.class));
      int rowsTm = updateStatementTMALLWms.executeUpdate();
      // JD侧更新
      DatabaseUtil.bindParameters(updateStatementJDWms, ArrayUtil.toArray(updateShippingWmsParams, Object.class));
      int rowsJd = updateStatementJDWms.executeUpdate();
      // 判断是不是无数据，只有标题行
      if (rowsEc <= 0 && rowsTm <= 0 && rowsJd <= 0) {
        headerOnly = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    logger.debug("UPDATE SHIPPING_HEADER statement: " + updateEcSql);
    logger.debug("UPDATE TMALL_SHIPPING_HEADER statement: " + updateTMALLSql);
    logger.debug("UPDATE JD_SHIPPING_HEADER statement: " + updateJdSql);
    try {
      // EC侧更新SHIPPING_STATUS状态
      updateStatementEC = createPreparedStatement(updateEcSql);
      // TMALL侧更新SHIPPING_STATUS状态
      updateStatementTMALL = createPreparedStatement(updateTMALLSql);
      // JD侧更新SHIPPING_STATUS状态
      updateStatementJD = createPreparedStatement(updateJdSql);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    List<Object> updateShippingParams = new ArrayList<Object>();
    updateShippingParams.add(getShippingStatus());
    updateShippingParams.add(DateUtil.getSysdate());
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

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    logger.debug("UPDATE SHIPPING_HEADER statement: " + updateEcWmsSql);
    logger.debug("UPDATE TMALL_SHIPPING_HEADER statement: " + updateTMALLWmsSql);
    logger.debug("UPDATE JD_SHIPPING_HEADER statement: " + updateJdWmsSql);
    try {
      // EC侧更新SHIPPING_STATUS_WMS状态
      updateStatementECWms = createPreparedStatement(updateEcWmsSql);
      // TMALL侧更新SHIPPING_STATUS_WMS状态
      updateStatementTMALLWms = createPreparedStatement(updateTMALLWmsSql);
      // JD侧更新SHIPPING_STATUS_WMS状态
      updateStatementJDWms = createPreparedStatement(updateJdWmsSql);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }

    List<Object> updateShippingWmsParams = new ArrayList<Object>();
    updateShippingWmsParams.add(ShippingStatus.READY.longValue());
    updateShippingWmsParams.add(getShippingStatus());
    updateShippingWmsParams.add(getShippingStatus());
    logger.debug("Table:SHIPPING_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingWmsParams, Object.class)));
    logger.debug("Table:TMALL_SHIPPING_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingWmsParams, Object.class)));
    logger.debug("Table:JD_SHIPPING_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingWmsParams, Object.class)));

    try {
      // EC侧更新
      DatabaseUtil.bindParameters(updateStatementECWms, ArrayUtil.toArray(updateShippingWmsParams, Object.class));
      updateStatementECWms.executeUpdate();
      // TMALL侧更新
      DatabaseUtil.bindParameters(updateStatementTMALLWms, ArrayUtil.toArray(updateShippingWmsParams, Object.class));
      updateStatementTMALLWms.executeUpdate();
      // JD侧更新
      DatabaseUtil.bindParameters(updateStatementJDWms, ArrayUtil.toArray(updateShippingWmsParams, Object.class));
      updateStatementJDWms.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    logger.debug("UPDATE SHIPPING_HEADER statement: " + updateEcSql);
    logger.debug("UPDATE TMALL_SHIPPING_HEADER statement: " + updateTMALLSql);
    logger.debug("UPDATE JD_SHIPPING_HEADER statement: " + updateJdSql);
    try {
      // EC侧更新SHIPPING_STATUS状态
      updateStatementEC = createPreparedStatement(updateEcSql);
      // TMALL侧更新SHIPPING_STATUS状态
      updateStatementTMALL = createPreparedStatement(updateTMALLSql);
      // JD侧更新SHIPPING_STATUS状态
      updateStatementJD = createPreparedStatement(updateJdSql);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }

    List<Object> updateShippingParams = new ArrayList<Object>();
    updateShippingParams.add(ShippingStatus.IN_PROCESSING.longValue());
    updateShippingParams.add(DateUtil.getSysdate());
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

  // 分仓补丁对应start
  public void compartmentStockDeal(List<String> row, List<CsvColumn> columnList) {
    // 初始分仓编号【SH代表上海仓；BJ代表北京仓；GZ代表广州仓】
    String areaCode = "SH";
    boolean allSpeCommodityFlg = true;
    boolean allSpeCommodityFlgGz = true;
    
    boolean allSpeCommodityFlgYt = true;  
    // 逻辑处理
    for (int i = 0; i < row.size(); i++) {
      String columnName = columnList.get(i).getPhysicalName();

      // 判断本列是不是订单编号
      if (columnName.equals("ORDER_NO") || columnName.equals("order_no")) {

        // 判断是哪类订单
        String orderType = getTmallOrEcOrJdOrder(row.get(i));

        if (orderType.equals("TMALL")) {
          // 如果是tmall订单
          TmallOrderDetailDao todd = DIContainer.getDao(TmallOrderDetailDao.class);
          List<TmallOrderDetail> todList = todd.findByQuery("SELECT * FROM TMALL_ORDER_DETAIL WHERE ORDER_NO = ?", row.get(i));
          // BJ仓start
          for (TmallOrderDetail tod : todList) {
            boolean allSpeCommodityFlgTemp = false;
            for (String commodityCode : config.getJdSpeCommodityList()) {
              if (StringUtil.hasValue(commodityCode)) {
                commodityCode = commodityCode.trim();
              }
              if (tod.getSkuCode().equals(commodityCode)) {
                allSpeCommodityFlgTemp = true;
                break;
              }
            }
            if (!allSpeCommodityFlgTemp) {
              allSpeCommodityFlg = false;
              break;
            }
          }
          // 20141103 hdh add 圆通判断
          for (TmallOrderDetail tod : todList) {
            boolean allSpeCommodityFlgTemp = false;
            for (String commodityCode : tempStrYuanTong) {
              if (StringUtil.hasValue(commodityCode)) {
                commodityCode = commodityCode.trim();
              }
              if (tod.getSkuCode().equals(commodityCode)) {
                allSpeCommodityFlgTemp = true;
                break;
              }
            }
            if (!allSpeCommodityFlgTemp) {
              allSpeCommodityFlgYt = false;
              break;
            }
          }
          // BJ仓end
          // GZ仓start
          for (TmallOrderDetail tod : todList) {
            boolean allSpeCommodityFlgTempGz = false;
            for (String commodityCode : config.getJdSpeCommodityListTwo()) {
              if (StringUtil.hasValue(commodityCode)) {
                commodityCode = commodityCode.trim();
              }
              if (tod.getSkuCode().equals(commodityCode)) {
                allSpeCommodityFlgTempGz = true;
                break;
              }
            }
            if (!allSpeCommodityFlgTempGz) {
              allSpeCommodityFlgGz = false;
              break;
            }
          }
          // GZ仓end
        } else if (orderType.equals("JD")) {
          // // 如果是JD订单
          // JdOrderDetailDao jodd = DIContainer.getDao(JdOrderDetailDao.class);
          // List<JdOrderDetail> jodList =
          // jodd.findByQuery("SELECT * FROM JD_ORDER_DETAIL WHERE ORDER_NO = ?",
          // row.get(i));
          // for (JdOrderDetail jod : jodList) {
          // boolean allSpeCommodityFlgTemp = false;
          // for (String commodityCode : config.getJdSpeCommodityList()) {
          // if (StringUtil.hasValue(commodityCode)) {
          // commodityCode = commodityCode.trim();
          // }
          // if (jod.getSkuCode().equals(commodityCode)) {
          // allSpeCommodityFlgTemp = true;
          // break;
          // }
          // }
          // if (!allSpeCommodityFlgTemp) {
          // allSpeCommodityFlg = false;
          // break;
          // }
          // }
          allSpeCommodityFlg = false;
          allSpeCommodityFlgGz = false;
          allSpeCommodityFlgYt = false;
          
          //JD折单地址处理
          String orderNo = row.get(i);
          //先查子订单编号，如果为空则再查主订单编号
          SimpleQuery subQuery = new SimpleQuery("SELECT COALESCE(IS_PART,'SH') FROM JD_SHIPPING_HEADER WHERE CHILD_ORDER_NO=?",new Object[]{orderNo});
          String subAddressCode = DatabaseUtil.executeScalar(subQuery, String.class);
          if(StringUtil.isNullOrEmpty(subAddressCode)){
            SimpleQuery mainQuery = new SimpleQuery("SELECT COALESCE(IS_PART,'SH') FROM JD_SHIPPING_HEADER WHERE ORDER_NO=?",new Object[]{orderNo});
            String mainAddressCode = DatabaseUtil.executeScalar(mainQuery, String.class);
            if(StringUtil.hasValue(mainAddressCode)){
              areaCode = mainAddressCode;
            }
          }else{
            areaCode = subAddressCode;
          }

          
          
        } else {
          // 如果是官网订单
          allSpeCommodityFlg = false;
          allSpeCommodityFlgGz = false;
          allSpeCommodityFlgYt = false;
        }
      }
      // 判断省份地址
      if (columnName.equals("SITELLITE") || columnName.equals("sitellite")) {
        String columnData = StringUtil.parse(row.get(i));
        // BJstart
        if (allSpeCommodityFlg) {
          if (columnData.equals("北京") || columnData.equals("天津") || columnData.equals("河北") || columnData.equals("河北省")) {
            areaCode = "BJ";
          }
        }
        
        // BJend
        // GZstart
        if (allSpeCommodityFlgGz) {
          if (columnData.equals("广东省") || columnData.equals("广东")) {
            areaCode = "GZ";
          } 
        }
        // 20141103 hdh add start
        if(allSpeCommodityFlgYt){
          areaCode = "BJ";
        }
        // 20141103 hdh add end
        
        // GZend
        row.set(i, areaCode);
      }
    }
  }

  // 判断EC订单或Tmall订单或JD订单
  private String getTmallOrEcOrJdOrder(String orderNo) {
    String orderTitle = orderNo.substring(0, 1);
    if (orderTitle.equals("T")) {
      return "TMALL";
    } else if (orderTitle.equals("D")) {
      return "JD";
    } else {
      return "EC";
    }
  }

  // 分仓补丁对应end

  public Query getExportQuery() {

    String sql = "" + "SELECT ORDER_NO " // 订单号

        + " ,(CASE WHEN TALL_EC_FLG='0' and GUEST_FLG='1'  THEN '"
        + config.getIfCustCdEcGuest()
        + "' when  TALL_EC_FLG='0' and GUEST_FLG='0' then  substr(customer_code,LENGTH(customer_code)-7,8) "
        + "  when TALL_EC_FLG='1' then '"
        + config.getIfCustCdTmall()
        + "' when TALL_EC_FLG='2' then 'GUESTJD' END) AS CUSTOMER_CODE" // 会员编号

        + " ,(CASE WHEN TALL_EC_FLG='0' and GUEST_FLG='1' THEN '"
        + config.getIfCustCdEcGuest()
        + "' when  TALL_EC_FLG='0' and GUEST_FLG='0' then  substr(customer_code,LENGTH(customer_code)-7,8) "
        + "  when TALL_EC_FLG='1' then '"
        + config.getIfCustCdTmall()
        + "' when TALL_EC_FLG='2' then 'GUESTJD' END) AS CUSTOMER_CODE" // 会员编号

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
        + " , (CASE WHEN TALL_EC_FLG='0' THEN "
        + "'"
        + config.getIfSoChannelEc() // EC
        + "'"
        + " WHEN TALL_EC_FLG='1' THEN "
        + "'"
        + config.getIfSoChannelTmall() // T-Mall
        + "'"
        + " WHEN TALL_EC_FLG='2' THEN "
        + " 'JD'"
        + " END ) AS Channel " // 通道 xml定义T-Mall/EC区分
        + " , ( CASE WHEN PAYMENT_METHOD_TYPE ='02' then "
        + "'"
        + config.getCreditTerms().get(2) // COD
        + "' WHEN PAYMENT_METHOD_TYPE ='12' THEN  "
        + "'"
        + config.getCreditTerms().get(1) // 银联
        + "' WHEN PAYMENT_METHOD_TYPE ='11' AND TALL_EC_FLG='1' THEN  "
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
        + " ,CAUTION AS Salesperson1" // Salesperson 1 销售员
        + " ,'' AS Salesperson2" // Salesperson 2 销售员
        + " ,'' AS Salesperson3" // Salesperson 3 销售员
        + " ,'' AS Salesperson4" // Salesperson 4 销售员
        + " , ( " // 发票希望标志
        + " (CASE WHEN INVOICE_FLG='0' THEN 'N' WHEN INVOICE_FLG='1' THEN 'Y' END) "
        + " || ';'" // 发票希望标志
        + " || (CASE WHEN invoice_type='0' THEN '1' WHEN invoice_type='1' THEN '2' END) "
        + " || ';'" // 发票类型
        + " || (CASE WHEN INVOICE_CUSTOMER_NAME='' OR INVOICE_CUSTOMER_NAME IS NULL THEN '' ELSE INVOICE_CUSTOMER_NAME END )"
        + " || ';'" // 发票抬头
        + " || (case when invoice_commodity_name='' or invoice_commodity_name is null then '' else invoice_commodity_name end ) "
        + " || ';'" // 发票内容
        + " || (case when address='' or address is null then '' else address end)"
        + " || ';'" // 开票地址
        + " || (case when company_name='' or company_name is null then '' else company_name end)"
        + " || ';'" // 开票公司
        + " || (case when bank_name='' or bank_name is null then '' else bank_name end )"
        + " || ';'" // 银行名称
        + " || (case when bank_no='' or bank_no is null then '' else bank_no end )"
        + " || ';'" // 银行编号
        + " || (case when tel='' or tel is null then '' else tel end)"
        + " || ';'" // 联系电话
        + " || (case when taxpayer_code='' or taxpayer_code is null then '' else taxpayer_code end)"
        + " || ';'" // 纳税人识别号
        + " ) AS Comments" // Comments说明
        + " ,((case when customer_delivery_appointed_date ='' or  customer_delivery_appointed_date is null or customer_delivery_appointed_date='休息日' or customer_delivery_appointed_date='平日' or customer_delivery_appointed_date='指定不可' or customer_delivery_appointed_date='不可指定日期' "
        + " then '' else customer_delivery_appointed_date  || ';'  end) "
        + " || "
        + " (case when delivery_appointed_time_start is null  or delivery_appointed_time_end is null then '' "
        + "  else delivery_appointed_time_start || ':00' || '～' || delivery_appointed_time_end || ':00' end) "
        + " ) AS Remarks "
        + " ,delivery_appointed_date AS Required_Date" // Required Date配送希望日
        + " ,'' AS Promised_Date" // Promised Date承诺日期
        + " ,'' AS Pricing_Date" // Pricing Date定价日期
        + " ,'' AS Project" // Project项目
        + " ,'' AS Language" // Language言语
        + " ,(case when TALL_EC_FLG='0' then '' when TALL_EC_FLG='1' or TALL_EC_FLG='2' then CUSTOMER_CODE end) AS Customer_PO" // Customer
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
        + " ,ROW_NUMBER() OVER(PARTITION BY ORDER_NO ORDER BY SHIPPING_DETAIL_NO) AS  SHIPPING_DETAIL_NO" // 发货明细编号,行
        + " ,(CASE WHEN SKU_CODE_FLG='0' THEN SKU_CODE WHEN SKU_CODE_FLG='1' THEN "
        + "'"
        + config.getIfSkuKbnList().get(0) // 运费订单sku
        + "'"
        + " WHEN SKU_CODE_FLG='2' THEN   "
        + "'"
        + config.getIfSkuKbnList().get(1) // 优惠订单sku
        + "'"
        + " WHEN SKU_CODE_FLG='3' THEN   "
        + "'"
        + config.getIfSkuKbnList().get(2) // 积分订单sku
        + "'"
        + " WHEN SKU_CODE_FLG='4' THEN   "
        + "'"
        + config.getIfSkuKbnList().get(3) // 淘宝折扣
        + "'"
        + " WHEN SKU_CODE_FLG='5' THEN   "
        + "'"
        + config.getIfSkuKbnList().get(4) // 淘宝店铺折扣
        + "'"
        + " WHEN SKU_CODE_FLG='6' THEN   "
        + "'"
        + config.getIfSkuKbnList().get(5) // 满就送活动
        + "'"
        + " WHEN SKU_CODE_FLG='7' THEN   "
        + "'"
        + config.getIfSkuKbnList().get(6) // 礼品卡支付
        + "'"
        + " WHEN SKU_CODE_FLG='8' THEN   "
        + "'"
        + config.getIfSkuKbnList().get(7) // 银联外卡支付
        + "'"
        + " WHEN SKU_CODE_FLG='9' THEN   "
        + "'"
        + config.getIfSkuKbnList().get(8) // 京东店铺优惠
        + "' END) AS sku_code" // sku编号
        + " ,purchasing_amount" // Qty Ordered订货数量
        + " , "
        + "'"
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
        + " ,(case when commodity_name is null then '' else commodity_name end ) AS Comment "
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
        + " ,address_last_name" // 收货人名称
        + " ,( CASE WHEN ADDRESS1 IS NULL THEN '' ELSE ADDRESS1 END  || CASE WHEN ADDRESS2 IS NULL THEN '' ELSE ADDRESS2 END || CASE WHEN ADDRESS3 IS NULL THEN '' ELSE ADDRESS3 END || CASE WHEN ADDRESS4 IS NULL THEN '' ELSE ADDRESS4 END) AS ADDR1"
        + " ,'' AS ADDR2" // ADDR2
        + " ,'' AS ADDR3" // ADDR3
        + " ,city_code" // 県?市コード
        + " ,prefecture_code" // 省コード
        + " ,postal_code" // 邮政编码
        + " ,'' AS FORMAT" // FORMAT
        + " ,'CHN' AS COUNTRY" // COUNTRY
        + " ,phone_number AS COUNTY" // COUNTY
        + " ,address_last_name" // 宛名：姓 ,担当者
        + " ,(case when phone_number='' OR phone_number is null then mobile_number else phone_number end) as phone_number" // 電話番号
        + " ,'' AS EXT_NO" // EXT NO
        + " ,(case when mobile_number='' OR mobile_number is null then phone_number else mobile_number end) as mobile_number" // 携帯電話FAX
        + " ,'' AS ATTN2" // ATTN2
        + " ,'' AS PHONE2" // PHONE2
        + " ,'' AS EXT_NO2" // EXT NO2
        + " ,'' AS FAX2" // FAX2
        + " ,'' AS LANG" // LANG
        + " ,address_last_name" // SORTNAME
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
        + " ,Electrical_Type" // 家电区分
        + " ,Line_Invoice_Price" // 行发票金额（去礼品卡，去优惠券，含运费）
        + " ,Line_Invoice_DisPrice" // 行折扣金额（发票专用）
        + " ,ADDRESS1 AS sitellite " // 行折扣金额（发票专用）
        + " FROM export_wms_shipping_info_view ";
    Query q = null;
    sql += " ORDER BY ORDER_NO";
    q = new SimpleQuery(sql);

    return q;

  }
}
