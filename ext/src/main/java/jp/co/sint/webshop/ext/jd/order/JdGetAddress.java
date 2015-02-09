package jp.co.sint.webshop.ext.jd.order;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.api.jd.JdApiConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.JdArea;
import jp.co.sint.webshop.data.dto.JdCity;
import jp.co.sint.webshop.data.dto.JdPrefecture;
import jp.co.sint.webshop.ext.jd.JdConnection;
import jp.co.sint.webshop.service.jd.order.JdAreasInfoList;
import jp.co.sint.webshop.service.jd.order.JdGetAreasService;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.address.AreaCityGetRequest;
import com.jd.open.api.sdk.request.address.AreaCountyGetRequest;
import com.jd.open.api.sdk.request.address.AreaProvinceGetRequest;
import com.jd.open.api.sdk.response.address.AreaCityGetResponse;
import com.jd.open.api.sdk.response.address.AreaCountyGetResponse;
import com.jd.open.api.sdk.response.address.AreaListBeanVO;
import com.jd.open.api.sdk.response.address.AreaProvinceGetResponse;

public class JdGetAddress implements JdGetAreasService {

  private JdConnection jconnection  = new JdConnection();
  
  private PreparedStatement insertPrefectureStatement = null;

  private PreparedStatement insertCityStatement = null;

  private PreparedStatement insertAreaStatement = null;

  private static final String PREFECTURE_TABLE_NAME = DatabaseUtil.getTableName(JdPrefecture.class);

  private static final String CITY_TABLE_NAME = DatabaseUtil.getTableName(JdCity.class);

  private static final String AREA_TABLE_NAME = DatabaseUtil.getTableName(JdArea.class);
  
  public AreaProvinceGetResponse getAddress() {

    Logger logger = Logger.getLogger(this.getClass());
    // 获取url
    JdApiConfig jc = DIContainer.get(JdApiConfig.class.getSimpleName());
    JdClient client = new DefaultJdClient(jc.getReqUrl(), jc.getSessionKey(), jc.getAppKey(), jc.getAppSercet());
    AreaProvinceGetRequest proReq = new AreaProvinceGetRequest();
    AreaProvinceGetResponse proRes = null;
    try {
      proRes = client.execute(proReq);
      if (!proRes.getSuccess()) {
        String subMsg = "";
        String msg = "";
        logger.error(proRes.getCode() + "_" + subMsg + "_" + msg);
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return proRes;
  }
  
  public AreaCityGetResponse getCityAddress(Long proId) {

    Logger logger = Logger.getLogger(this.getClass());
    // 获取url
    JdApiConfig jc = DIContainer.get(JdApiConfig.class.getSimpleName());
    JdClient client = new DefaultJdClient(jc.getReqUrl(), jc.getSessionKey(), jc.getAppKey(), jc.getAppSercet());
    AreaCityGetRequest cityReq = new AreaCityGetRequest();
    cityReq.setParentId(proId);
    AreaCityGetResponse cityRes = null;
    try {
      cityRes = client.execute(cityReq);
      if (!cityRes.getSuccess()) {
        String subMsg = "";
        String msg = "";
        logger.error(cityRes.getCode() + "_" + subMsg + "_" + msg);
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cityRes;
  }
  
  public AreaCountyGetResponse getAreaAddress(Long cityId) {

    Logger logger = Logger.getLogger(this.getClass());
    // 获取url
    JdApiConfig jc = DIContainer.get(JdApiConfig.class.getSimpleName());
    JdClient client = new DefaultJdClient(jc.getReqUrl(), jc.getSessionKey(), jc.getAppKey(), jc.getAppSercet());
    AreaCountyGetRequest couReq = new AreaCountyGetRequest();
    couReq.setParentId(cityId);
    AreaCountyGetResponse couRes = null;
    try {
      couRes = client.execute(couReq);
      if (!couRes.getSuccess()) {
        String subMsg = "";
        String msg = "";
        logger.error(couRes.getCode() + "_" + subMsg + "_" + msg);
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return couRes;
  }
  
  // 接口实现方法
  public boolean getJdAreas() {
    boolean flag = false;
    JdAreasInfoList tail = quAreas();
    if (tail != null) {
      jconnection.init();
      initializeResources();

      List<JdPrefecture> prefectureList = tail.getPrefecture();
      List<JdCity> cityList = tail.getCity();
      List<JdArea> areaList = tail.getArea();
      try {
        for (int i = 0; i < prefectureList.size(); i++) {
          JdPrefecture pc = new JdPrefecture();
          pc = (JdPrefecture) prefectureList.get(i);
          executeInsertPrefecture(pc);
        }
        for (int i = 0; i < cityList.size(); i++) {
          JdCity ct = new JdCity();
          ct = (JdCity) cityList.get(i);
          executeInsertCity(ct);
        }
        for (int i = 0; i < areaList.size(); i++) {
          JdArea ae = new JdArea();
          ae = (JdArea) areaList.get(i);
          executeInsertArea(ae);
        }
        jconnection.getConnection().commit();
        flag = true;
      } catch (SQLException e) {
        try {
          jconnection.getConnection().rollback();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
        e.printStackTrace();
      }
      jconnection.dispose();
    }
    return flag;
  }
  
  // 调用api获得地址薄数据集合
  public JdAreasInfoList quAreas() {

    JdAreasInfoList tail = new JdAreasInfoList();

    // 省、直辖市
    List<JdPrefecture> pref = new ArrayList<JdPrefecture>();
    // 城市
    long displayOrder = 1;
    List<JdCity> ci = new ArrayList<JdCity>();
    // 区县
    List<JdArea> ar = new ArrayList<JdArea>();
    
    // 调用api获得结果
    AreaProvinceGetResponse proRes = getAddress();
    AreaListBeanVO[] proList = proRes.getProvinceAreas();
    if (proList != null && proList.length > 0) {
      for (int i = 0; i < proList.length; i++) {
        AreaListBeanVO apiPrefecture = proList[i];
        
        // 需要的省、直辖市
        if (!apiPrefecture.getName().equals("台湾") && !apiPrefecture.getName().equals("香港") 
            && !apiPrefecture.getName().equals("澳门") && !apiPrefecture.getName().equals("钓鱼岛")) {
          JdPrefecture prefecture = new JdPrefecture();
          
          prefecture.setPrefectureName(apiPrefecture.getName());
          prefecture.setPrefectureCode(apiPrefecture.getId().toString());
          pref.add(prefecture);
          
          //市对应
          AreaCityGetResponse cityRes = getCityAddress(apiPrefecture.getId());
          AreaListBeanVO[] cityList = cityRes.getCityAreas();
          if (cityList != null && cityList.length > 0) {
            for (int j = 0; j < cityList.length; j++) {
              AreaListBeanVO apiCity = cityList[j];
              JdCity city = new JdCity();
              city.setCityCode(apiCity.getId().toString());
              city.setCityName(apiCity.getName());
              city.setCountryCode("cn");
              city.setDisplayOrder(displayOrder);
              city.setRegionCode(apiPrefecture.getId().toString());
              ci.add(city);
              
              //区县对应
              AreaCountyGetResponse couRes = getAreaAddress(apiCity.getId());
              AreaListBeanVO[] couList = couRes.getCountyAreas();
              if (couList != null && couList.length > 0) {
                for (int k = 0; k < couList.length; k++) {
                  AreaListBeanVO apiCountry = couList[k];
                  JdArea area = new JdArea();
                  area.setAreaCode(apiCountry.getId().toString());
                  area.setAreaName(apiCountry.getName());
                  area.setCityCode(apiCity.getId().toString());
                  area.setPrefectureCode(apiPrefecture.getId().toString());
                  ar.add(area);
                  
                }
              }
              
              displayOrder++;
            }
          }
        }
      }
    }
    tail.setPrefecture(pref);
    tail.setCity(ci);
    tail.setArea(ar);
    
    return tail;
  }
          
  
  public void initializeResources() {

    Logger logger = Logger.getLogger(this.getClass());

    logger.debug("INSERT statement: " + getInsertPrefectureQuery());
    logger.debug("INSERT statement: " + getInsertCityQuery());
    logger.debug("INSERT statement: " + getInsertAreaQuery());

    try {
      insertPrefectureStatement = jconnection.createPreparedStatement(getInsertPrefectureQuery());
      insertCityStatement = jconnection.createPreparedStatement(getInsertCityQuery());
      insertAreaStatement = jconnection.createPreparedStatement(getInsertAreaQuery());
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }
  
  private String getInsertPrefectureQuery() {
    String insertSql = "" + " INSERT INTO " + PREFECTURE_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'jd_prefecture_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("PREFECTURE_CODE");
    columnList.add("PREFECTURE_NAME");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  private String getInsertCityQuery() {
    String insertSql = "" + " INSERT INTO " + CITY_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'jd_city_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("COUNTRY_CODE");
    columnList.add("REGION_CODE");
    columnList.add("CITY_CODE");
    columnList.add("CITY_NAME");
    columnList.add("DISPLAY_ORDER");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  private String getInsertAreaQuery() {
    String insertSql = "" + " INSERT INTO " + AREA_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'jd_area_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("PREFECTURE_CODE");
    columnList.add("CITY_CODE");
    columnList.add("AREA_CODE");
    columnList.add("AREA_NAME");
    columnList.add("ALLOW_COD_FLG");
    columnList.add("ALLOW_DELIVERY_TIME_FLG");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  public int executeInsertPrefecture(JdPrefecture p) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    params.add(p.getPrefectureCode());
    params.add(p.getPrefectureName());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());

    pstmt = insertPrefectureStatement;

    logger.debug("Table:PREFECTURE INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  public int executeInsertCity(JdCity c) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    params.add(c.getCountryCode());
    params.add(c.getRegionCode());
    params.add(c.getCityCode());
    params.add(c.getCityName());
    params.add(c.getDisplayOrder());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());

    pstmt = insertCityStatement;

    logger.debug("Table:CITY INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  public int executeInsertArea(JdArea a) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    params.add(a.getPrefectureCode());
    params.add(a.getCityCode());
    params.add(a.getAreaCode());
    params.add(a.getAreaName());
    params.add(1L);
    params.add(1L);
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());

    pstmt = insertAreaStatement;

    logger.debug("Table:AREA INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }
  

}
