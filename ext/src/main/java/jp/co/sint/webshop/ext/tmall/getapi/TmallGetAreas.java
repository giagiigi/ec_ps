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

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.Area;
import jp.co.sint.webshop.data.dto.City;
import jp.co.sint.webshop.data.dto.Prefecture;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.ext.tmall.TmallUtil;
import jp.co.sint.webshop.service.tmall.TmallAreasInfoList;
import jp.co.sint.webshop.service.tmall.TmallGetAreasService;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class TmallGetAreas implements TmallGetAreasService {

  private TmallConnection tconnection  = new TmallConnection();

  private PreparedStatement insertPrefectureStatement = null;

  private PreparedStatement insertCityStatement = null;

  private PreparedStatement insertAreaStatement = null;

  private static final String PREFECTURE_TABLE_NAME = DatabaseUtil.getTableName(Prefecture.class);

  private static final String CITY_TABLE_NAME = DatabaseUtil.getTableName(City.class);

  private static final String AREA_TABLE_NAME = DatabaseUtil.getTableName(Area.class);

  /**
   * 获得淘宝地址薄
   */
  private static String createRequestParam() {

    TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();

    // 获取key
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    // 组装协议参数。
    apiparamsMap.put("method", TmallUtil.GET_AREAS);
    apiparamsMap.put("app_key", tc.getAppKey());
    apiparamsMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    apiparamsMap.put("session", tc.getSessionKey());
    apiparamsMap.put("format", "xml");
    apiparamsMap.put("v", "2.0");
    // 组装应用参数
    apiparamsMap.put("fields", "id,type,name,parent_id,zip");
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

  // 调用api获得地址薄数据集合
  @SuppressWarnings("unchecked")
  public TmallAreasInfoList quAreas() {

    TmallAreasInfoList tail = null;
    Logger logger = Logger.getLogger(this.getClass());
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
      tail = new TmallAreasInfoList();

      // 省、直辖市
      int prefCode = 0;
      List<Prefecture> pref = new ArrayList<Prefecture>();
      List<Prefecture> prefTemp = new ArrayList<Prefecture>();
      List<String> prefId = new ArrayList<String>();

      // 城市
      long displayOrder = 1;
      long cityCode = 0 ;
      List<City> ci = new ArrayList<City>();
      List<City> ciTemp = new ArrayList<City>();
      List<String> cityId = new ArrayList<String>();

      // 区县
      int areaCode = 0;
      List<Area> ar = new ArrayList<Area>();

      // 返回集合
      document = builder.build(source);
      Element root = document.getRootElement();
      Element elementAreas = root.getChild("areas");
      List<Element> areas = elementAreas.getChildren();

      // 省、直辖市
      Element elementPref = null;
      for (int i = 0; i < areas.size(); i++) {
        elementPref = (Element) areas.get(i);
        // 需要的省、直辖市
        if (elementPref.getChildText("type").equals("2") && !elementPref.getChildText("name").equals("台湾省")
            && !elementPref.getChildText("name").equals("香港特别行政区") && !elementPref.getChildText("name").equals("澳门特别行政区")
            && !elementPref.getChildText("name").equals("海外")) {
          Prefecture prefecture = new Prefecture();
          Prefecture prefectureTemp = new Prefecture();
          if (prefCode < 9) {
            prefecture.setPrefectureCode("0" + (prefCode + 1));
            prefectureTemp.setPrefectureCode("0" + (prefCode + 1));
          } else {
            prefecture.setPrefectureCode("" + (prefCode + 1));
            prefectureTemp.setPrefectureCode("" + (prefCode + 1));
          }
          prefecture.setPrefectureName(elementPref.getChildText("name"));
          prefectureTemp.setPrefectureName(elementPref.getChildText("id"));
          pref.add(prefecture);
          prefTemp.add(prefectureTemp);
          prefId.add(elementPref.getChildText("id"));
          prefCode++;
        }
      }
      tail.setPrefecture(pref);
      // 城市
      Element elementCi = null;
      for (int i = 0; i < areas.size(); i++) {
        elementCi = (Element) areas.get(i);
        // 需要的城市
        if ((elementCi.getChildText("type").equals("3") || elementCi.getChildText("type").equals("4"))
            && (prefId.indexOf(elementCi.getChildText("parent_id")) != -1)) {
          City city = new City();
          City cityTemp = new City();
          city.setCountryCode("cn");
          city.setCityName(elementCi.getChildText("name"));
          city.setDisplayOrder(displayOrder);
          cityTemp.setCityCode(elementCi.getChildText("id"));
          for (int k = 0; k < prefTemp.size(); k++) {
            if ((elementCi.getChildText("parent_id")).equals(((Prefecture) prefTemp.get(k)).getPrefectureName())) {
              city.setRegionCode(((Prefecture) prefTemp.get(k)).getPrefectureCode());
              cityTemp.setRegionCode(((Prefecture) prefTemp.get(k)).getPrefectureCode());
            }
          }
          if (cityCode < 9) {
            city.setCityCode("00" + (cityCode + 1));
            cityTemp.setCityName("00" + (cityCode + 1));
          } else if (cityCode < 99) {
            city.setCityCode("0" + (cityCode + 1));
            cityTemp.setCityName("0" + (cityCode + 1));
          } else {
            city.setCityCode("" + (cityCode + 1));
            cityTemp.setCityName("" + (cityCode + 1));
          }
          ci.add(city);
          ciTemp.add(cityTemp);
          cityId.add(elementCi.getChildText("id"));
          displayOrder++;
          cityCode++;
        }
      }
      tail.setCity(ci);
      // 区县
      Element elementAr = null;
      for (int i = 0; i < areas.size(); i++) {
        elementAr = (Element) areas.get(i);

        if (elementAr.getChildText("type").equals("4") && (cityId.indexOf(elementAr.getChildText("parent_id")) != -1)) {
          Area area = new Area();
          area.setAreaName(elementAr.getChildText("name"));
          for (int j = 0; j < ciTemp.size(); j++) {
            if ((elementAr.getChildText("parent_id")).equals(((City) ciTemp.get(j)).getCityCode())) {
              area.setPrefectureCode(((City) ciTemp.get(j)).getRegionCode());
              area.setCityCode(((City) ciTemp.get(j)).getCityName());
            }
          }
          if (areaCode < 9) {
            area.setAreaCode("000" + (areaCode + 1));
          } else if (areaCode < 99) {
            area.setAreaCode("00" + (areaCode + 1));
          } else if (areaCode < 999) {
            area.setAreaCode("0" + (areaCode + 1));
          } else {
            area.setAreaCode("" + (areaCode + 1));
          }
          ar.add(area);
          areaCode++;
        }
      }
      tail.setArea(ar);

    } catch (JDOMException e) {
      Logger.getLogger(this.getClass()).error(e);
      e.printStackTrace();
    } catch (IOException e) {
      Logger.getLogger(this.getClass()).error(e);
      e.printStackTrace();
    }
    logger.debug("获取淘宝地址薄完毕");
    return tail;
  }

  // 接口实现方法
  public boolean getTmallAreas() {
    boolean flag = false;
    TmallAreasInfoList tail = quAreas();
    if (tail != null) {
      tconnection.init();
      initializeResources();

      List<Prefecture> prefectureList = tail.getPrefecture();
      List<City> cityList = tail.getCity();
      List<Area> areaList = tail.getArea();
      try {
        for (int i = 0; i < prefectureList.size(); i++) {
          Prefecture pc = new Prefecture();
          pc = (Prefecture) prefectureList.get(i);
          executeInsertPrefecture(pc);
        }
        for (int i = 0; i < cityList.size(); i++) {
          City ct = new City();
          ct = (City) cityList.get(i);
          executeInsertCity(ct);
        }
        for (int i = 0; i < areaList.size(); i++) {
          Area ae = new Area();
          ae = (Area) areaList.get(i);
          executeInsertArea(ae);
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

    logger.debug("INSERT statement: " + getInsertPrefectureQuery());
    logger.debug("INSERT statement: " + getInsertCityQuery());
    logger.debug("INSERT statement: " + getInsertAreaQuery());

    try {
      insertPrefectureStatement = tconnection.createPreparedStatement(getInsertPrefectureQuery());
      insertCityStatement = tconnection.createPreparedStatement(getInsertCityQuery());
      insertAreaStatement = tconnection.createPreparedStatement(getInsertAreaQuery());
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  private String getInsertPrefectureQuery() {
    String insertSql = "" + " INSERT INTO " + PREFECTURE_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'prefecture_seq'") + ", ?, ?, ?, ?) ";

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
        + SqlDialect.getDefault().getNextvalNOprm("'city_seq'") + ", ?, ?, ?, ?) ";

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
        + SqlDialect.getDefault().getNextvalNOprm("'area_seq'") + ", ?, ?, ?, ?) ";

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

  public int executeInsertPrefecture(Prefecture p) throws SQLException {
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

  public int executeInsertCity(City c) throws SQLException {
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

  public int executeInsertArea(Area a) throws SQLException {
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
