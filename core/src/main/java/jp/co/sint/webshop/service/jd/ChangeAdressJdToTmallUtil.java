package jp.co.sint.webshop.service.jd;

import java.math.BigDecimal;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.JdArea;
import jp.co.sint.webshop.data.dto.JdCity;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.order.DeliveryInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

import com.jd.open.api.sdk.domain.order.OrderSearchInfo;


public final class ChangeAdressJdToTmallUtil {

  public JdOrderHeader ChangeOrderHeaderAdress(OrderSearchInfo info, JdOrderHeader orderHeader ,BigDecimal totalWeight) {
    
      JdCity city = GetSpecialCityCode(info.getConsigneeInfo().getCity());
      JdArea area = null;
      if ( city != null ) {
        area = GetAreaCode(city.getRegionCode(), city.getCityCode(), info.getConsigneeInfo().getCounty());
      }
      orderHeader.setAddress1(info.getConsigneeInfo().getProvince()); // 省/直辖市/自治区
      orderHeader.setAddress2(info.getConsigneeInfo().getCity()); // 市
      orderHeader.setAddress3(info.getConsigneeInfo().getCounty()); // 街道
      //过滤详细地址中的省市街道信息
      String tempArdess = info.getConsigneeInfo().getProvince().trim() + info.getConsigneeInfo().getCity().trim()
        + info.getConsigneeInfo().getCounty();
      String address4 = info.getConsigneeInfo().getFullAddress().replaceAll(tempArdess, "");
      orderHeader.setAddress4(address4);// 详细地址
      orderHeader.setPrefectureCode(city.getRegionCode());
      orderHeader.setCityCode(city.getCityCode());// 城市编码
      orderHeader.setAreaCode(area == null ? "" : area.getAreaCode());// 区县编号
//      if (StringUtil.hasValue(info.getConsigneeInfo().getCity()) && info.getConsigneeInfo().getCity().equals("崇明县")) {
//        if(StringUtil.hasValue(orderHeader.getCaution())){
//          orderHeader.setCaution(orderHeader.getCaution() + "（本订单收货地址为上海崇明）");
//          orderHeader.setJdBuyerMessage(orderHeader.getCaution() + "（本订单收货地址为上海崇明）");
//        }else{
//          orderHeader.setCaution("（本订单收货地址为上海崇明）");
//          orderHeader.setJdBuyerMessage("（本订单收货地址为上海崇明）");
//        }
//      }
      

      WebshopConfig config = DIContainer.getWebshopConfig();
      if ("江苏".equals(orderHeader.getAddress1()) || "浙江".equals(orderHeader.getAddress1())
          || "上海".equals(orderHeader.getAddress1())) {
        if ((orderHeader.getPaidPrice().compareTo(config.getJdPrice1()) > -1
            && orderHeader.getPaidPrice().compareTo(config.getJdPrice2()) == -1 && totalWeight.compareTo(config.getJdWeight1()) > 0)
            || (orderHeader.getPaidPrice().compareTo(config.getJdPrice2()) > -1 && totalWeight.compareTo(config.getJdWeight2()) > 0)) {
      
          if(StringUtil.hasValue(orderHeader.getCaution())){
            orderHeader.setCaution(orderHeader.getCaution() + "（本订单满足金额和重量的拦截条件）");
            orderHeader.setJdBuyerMessage(orderHeader.getCaution());
          }else{
            orderHeader.setCaution("（本订单满足金额和重量的拦截条件）");
            orderHeader.setJdBuyerMessage("（本订单满足金额和重量的拦截条件）");
          }
        }
      } else {
        if(totalWeight.compareTo(config.getJdWeight3()) > 0){
          if(StringUtil.hasValue(orderHeader.getCaution())){
            orderHeader.setCaution(orderHeader.getCaution() + "（本订单满足金额和重量的拦截条件）");
            orderHeader.setJdBuyerMessage(orderHeader.getCaution());
          }else{
            orderHeader.setCaution("（本订单满足金额和重量的拦截条件）");
            orderHeader.setJdBuyerMessage("（本订单满足金额和重量的拦截条件）");
          }
        }

      }
      // 根据JD省份编号mappingTM省份编号start
      orderHeader.setPrefectureCode(getTmallPrefectureCode(city.getRegionCode()));
      // 根据JD省份编号mappingTM省份编号end
      return orderHeader;
    
    
    
//    List<String> specialProvince = new ArrayList<String>();
//    specialProvince.add("北京");
//    specialProvince.add("上海");
//    specialProvince.add("天津");
//    specialProvince.add("重庆");
      
    //崇明对应
//    if (StringUtil.hasValue(info.getConsigneeInfo().getCity()) && info.getConsigneeInfo().getCity().equals("崇明县")) {
//      orderHeader.setAddress1("上海崇明"); // 省/直辖市/自治区
//      orderHeader.setAddress2("崇明县"); // 市
//      orderHeader.setAddress4(info.getConsigneeInfo().getFullAddress());// 详细地址
//      orderHeader.setPrefectureCode("32");
//      orderHeader.setCityCode("365");// 城市编码
//      orderHeader.setAreaCode("");// 区县编号
//      if(StringUtil.hasValue(orderHeader.getCaution())){
//        orderHeader.setCaution(orderHeader.getCaution() + "（本订单收货地址为上海崇明）");
//        orderHeader.setJdBuyerMessage(orderHeader.getJdBuyerMessage() + "（本订单收货地址为上海崇明）");
//      }else{
//        orderHeader.setCaution("（本订单收货地址为上海崇明）");
//        orderHeader.setJdBuyerMessage("（本订单收货地址为上海崇明）");
//      }
//    //直辖市对应
//    } else if (specialProvince.contains(info.getConsigneeInfo().getProvince())   ) {
//      // 直辖市情况下   JD的城市和TMALL的区县相对应
//      String apiCounty1 = info.getConsigneeInfo().getCity().replace("县", "");
//      String apiCounty2 = apiCounty1.replace("区", "");
//      String apiCounty3 = apiCounty2.replace("州", "");
//      String apiCountyName = apiCounty3.replace("市", "");
//      JdCity city = GetSpecialCityCode(info.getConsigneeInfo().getProvince());
//      JdArea area = null;
//      boolean exitFlg = false;
//      if ( city != null ) {
//        area = GetAreaCode(city.getRegionCode(), city.getCityCode(), apiCountyName);
//        if (area != null ) {
//          exitFlg = true;
//        }
//      }
//      if (exitFlg) {
//        orderHeader.setAddress1(info.getConsigneeInfo().getProvince()); // 省/直辖市/自治区
//        orderHeader.setAddress2(city.getCityName()); // 市
//        orderHeader.setAddress3(area.getAreaName()); // 街道
//        orderHeader.setAddress4(info.getConsigneeInfo().getFullAddress());// 详细地址
//        orderHeader.setPrefectureCode(city.getRegionCode());
//        orderHeader.setCityCode(city.getCityCode());// 城市编码
//        orderHeader.setAreaCode(area == null ? "" : area.getAreaCode());// 区县编号
//      } else {
//        orderHeader.setAddress1(info.getConsigneeInfo().getProvince()); // 省/直辖市/自治区
//        orderHeader.setAddress2(info.getConsigneeInfo().getCity()); // 市
//        orderHeader.setAddress3(info.getConsigneeInfo().getCounty()); // 街道
//        orderHeader.setAddress4(info.getConsigneeInfo().getFullAddress());// 详细地址
//        orderHeader.setPrefectureCode("");
//        orderHeader.setCityCode("");// 城市编码
//        orderHeader.setAreaCode("");// 区县编号
//      }
//    //剩余正常地址对应
//    } else {
//      String apiCity1 = info.getConsigneeInfo().getCity().replace("县", "");
//      String apiCity2 = apiCity1.replace("区", "");
//      String apiCity3 = apiCity2.replace("州", "");
//      String apiCityName = apiCity3.replace("市", "");
//      String apiCounty1 = info.getConsigneeInfo().getCounty().replace("县", "");
//      String apiCounty2 = apiCounty1.replace("区", "");
//      String apiCounty3 = apiCounty2.replace("市", "");
//      String apiCounty4 = apiCounty3.replace("新区", "");
//      String apiCountyName = apiCounty4.replace("开发区", "");
//      JdCity city = GetSpecialCityCode(apiCityName);
//      JdArea area = null;
//      boolean exitFlg = false;
//      if ( city != null ) {
//        area = GetAreaCode(city.getRegionCode(), city.getCityCode(), apiCountyName);
//        if (area != null ) {
//          exitFlg = true;
//        }
//      }
//      if (exitFlg) {
//        orderHeader.setAddress1(info.getConsigneeInfo().getProvince()); // 省/直辖市/自治区
//        orderHeader.setAddress2(city.getCityName()); // 市
//        orderHeader.setAddress3(area.getAreaName()); // 街道
//        orderHeader.setAddress4(info.getConsigneeInfo().getFullAddress());// 详细地址
//        orderHeader.setPrefectureCode(city.getRegionCode());
//        orderHeader.setCityCode(city.getCityCode());// 城市编码
//        orderHeader.setAreaCode(area == null ? "" : area.getAreaCode());// 区县编号
//      } else {
//        orderHeader.setAddress1(info.getConsigneeInfo().getProvince()); // 省/直辖市/自治区
//        orderHeader.setAddress2(info.getConsigneeInfo().getCity()); // 市
//        orderHeader.setAddress3(info.getConsigneeInfo().getCounty()); // 街道
//        orderHeader.setAddress4(info.getConsigneeInfo().getFullAddress());// 详细地址
//        orderHeader.setPrefectureCode("");
//        orderHeader.setCityCode("");// 城市编码
//        orderHeader.setAreaCode("");// 区县编号
//      }
//    }
//    
//    return orderHeader;
  }
  
  
  
  public JdShippingHeader ChangeShippingHeaderAdress(OrderSearchInfo info, JdShippingHeader shippingHeader, String totalWeight) {
    
    
    JdCity city = GetSpecialCityCode(info.getConsigneeInfo().getCity());
    JdArea area = null;
    if ( city != null ) {
      area = GetAreaCode(city.getRegionCode(), city.getCityCode(), info.getConsigneeInfo().getCounty());
    }
    String cityCode = "";
    String regionCode = "";
    if (city != null) {
      cityCode = city.getCityCode();
      regionCode = city.getRegionCode();
    }
    String areaCode = "";
    if (area != null) {
      areaCode = area.getAreaCode();
    }
    shippingHeader.setAddress1(info.getConsigneeInfo().getProvince()); // 省/直辖市/自治区
    shippingHeader.setAddress2(info.getConsigneeInfo().getCity()); // 市
    shippingHeader.setAddress3(info.getConsigneeInfo().getCounty()); // 街道
    //过滤详细地址中的省市街道信息
    String tempArdess = info.getConsigneeInfo().getProvince().trim() + info.getConsigneeInfo().getCity().trim()
      + info.getConsigneeInfo().getCounty();
    String address4 = info.getConsigneeInfo().getFullAddress().replaceAll(tempArdess, "");
    shippingHeader.setAddress4(address4);// 详细地址
    shippingHeader.setPrefectureCode(city.getRegionCode());
    shippingHeader.setCityCode(city.getCityCode());// 城市编码
    shippingHeader.setAreaCode(areaCode);// 区县编号
    
    DeliveryInfo dri = GetDeliveryCopNo(1L, regionCode, cityCode, areaCode,totalWeight.toString());
    // 设置运输公司和运输公司编码
    if (dri == null) {
      UtilService utilService = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
      DeliveryCompany deliveryCompany = utilService.getDefaultDeliveryCompany();
      shippingHeader.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
      shippingHeader.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
    } else {
      shippingHeader.setDeliveryCompanyName(dri.getDeliveryCompanyName());// 运输公司
      shippingHeader.setDeliveryCompanyNo(dri.getDeliveryCompanyNo());// 运输公司编码
    }
    // 根据JD省份编号mappingTM省份编号start
    shippingHeader.setPrefectureCode(getTmallPrefectureCode(city.getRegionCode()));
    // 根据JD省份编号mappingTM省份编号end
    return shippingHeader;
    
//    List<String> specialProvince = new ArrayList<String>();
//    specialProvince.add("北京");
//    specialProvince.add("上海");
//    specialProvince.add("天津");
//    specialProvince.add("重庆");
//    JdCity city = null;
//    JdArea area = null;
//    //崇明对应
//    if (StringUtil.hasValue(info.getConsigneeInfo().getCity()) && info.getConsigneeInfo().getCity().equals("崇明县")) {
//      shippingHeader.setAddress1("上海崇明"); // 省/直辖市/自治区
//      shippingHeader.setAddress2("崇明县"); // 市
//      shippingHeader.setAddress4(info.getConsigneeInfo().getFullAddress());// 详细地址
//      shippingHeader.setPrefectureCode("32");
//      shippingHeader.setCityCode("365");// 城市编码
//      shippingHeader.setAreaCode("");// 区县编号
//    //直辖市对应
//    } else if (specialProvince.contains(info.getConsigneeInfo().getProvince())   ) {
//      // 直辖市情况下   JD的城市和TMALL的区县相对应
//      String apiCounty1 = info.getConsigneeInfo().getCity().replace("县", "");
//      String apiCounty2 = apiCounty1.replace("区", "");
//      String apiCounty3 = apiCounty2.replace("州", "");
//      String apiCountyName = apiCounty3.replace("市", "");
//      city = GetSpecialCityCode(info.getConsigneeInfo().getProvince());
//      area = null;
//      boolean exitFlg = false;
//      if ( city != null ) {
//        area = GetAreaCode(city.getRegionCode(), city.getCityCode(), apiCountyName);
//        if (area != null ) {
//          exitFlg = true;
//        }
//      }
//      if (exitFlg) {
//        shippingHeader.setAddress1(info.getConsigneeInfo().getProvince()); // 省/直辖市/自治区
//        shippingHeader.setAddress2(city.getCityName()); // 市
//        shippingHeader.setAddress3(area.getAreaName()); // 街道
//        shippingHeader.setAddress4(info.getConsigneeInfo().getFullAddress());// 详细地址
//        shippingHeader.setPrefectureCode(city.getRegionCode());
//        shippingHeader.setCityCode(city.getCityCode());// 城市编码
//        shippingHeader.setAreaCode(area == null ? "" : area.getAreaCode());// 区县编号
//      } else {
//        shippingHeader.setAddress1(info.getConsigneeInfo().getProvince()); // 省/直辖市/自治区
//        shippingHeader.setAddress2(info.getConsigneeInfo().getCity()); // 市
//        shippingHeader.setAddress3(info.getConsigneeInfo().getCounty()); // 街道
//        shippingHeader.setAddress4(info.getConsigneeInfo().getFullAddress());// 详细地址
//        shippingHeader.setPrefectureCode("");
//        shippingHeader.setCityCode("");// 城市编码
//        shippingHeader.setAreaCode("");// 区县编号
//      }
//    //剩余正常地址对应
//    } else {
//      String apiCity1 = info.getConsigneeInfo().getCity().replace("县", "");
//      String apiCity2 = apiCity1.replace("区", "");
//      String apiCity3 = apiCity2.replace("州", "");
//      String apiCityName = apiCity3.replace("市", "");
//      String apiCounty1 = info.getConsigneeInfo().getCounty().replace("县", "");
//      String apiCounty2 = apiCounty1.replace("区", "");
//      String apiCounty3 = apiCounty2.replace("市", "");
//      String apiCounty4 = apiCounty3.replace("新区", "");
//      String apiCountyName = apiCounty4.replace("开发区", "");
//      city = GetSpecialCityCode(apiCityName);
//      area = null;
//      boolean exitFlg = false;
//      if ( city != null ) {
//        area = GetAreaCode(city.getRegionCode(), city.getCityCode(), apiCountyName);
//        if (area != null ) {
//          exitFlg = true;
//        }
//      }
//      if (exitFlg) {
//        shippingHeader.setAddress1(info.getConsigneeInfo().getProvince()); // 省/直辖市/自治区
//        shippingHeader.setAddress2(city.getCityName()); // 市
//        shippingHeader.setAddress3(area.getAreaName()); // 街道
//        shippingHeader.setAddress4(info.getConsigneeInfo().getFullAddress());// 详细地址
//        shippingHeader.setPrefectureCode(city.getRegionCode());
//        shippingHeader.setCityCode(city.getCityCode());// 城市编码
//        shippingHeader.setAreaCode(area == null ? "" : area.getAreaCode());// 区县编号
//      } else {
//        shippingHeader.setAddress1(info.getConsigneeInfo().getProvince()); // 省/直辖市/自治区
//        shippingHeader.setAddress2(info.getConsigneeInfo().getCity()); // 市
//        shippingHeader.setAddress3(info.getConsigneeInfo().getCounty()); // 街道
//        shippingHeader.setAddress4(info.getConsigneeInfo().getFullAddress());// 详细地址
//        shippingHeader.setPrefectureCode("");
//        shippingHeader.setCityCode("");// 城市编码
//        shippingHeader.setAreaCode("");// 区县编号
//      }
//      
//    }
//    
//    String prefectureCode = "";
//    String cityCode = "";
//    String areaCode = "";
//    if (city != null) {
//      prefectureCode = city.getRegionCode();
//      cityCode = city.getCityCode();
//    }
//    if (area != null) {
//      areaCode = area == null ? "" : area.getAreaCode();
//    }
//    DeliveryInfo dri = GetDeliveryCopNo(1L, prefectureCode, cityCode, areaCode,totalWeight.toString());
//    // 设置运输公司和运输公司编码
//    if (dri == null) {
//      UtilService utilService = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
//      DeliveryCompany deliveryCompany = utilService.getDefaultDeliveryCompany();
//      shippingHeader.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
//      shippingHeader.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
//    } else {
//      shippingHeader.setDeliveryCompanyName(dri.getDeliveryCompanyName());// 运输公司
//      shippingHeader.setDeliveryCompanyNo(dri.getDeliveryCompanyNo());// 运输公司编码
//    }
//    
//    return shippingHeader;
  }
  
  
  public DeliveryInfo GetDeliveryCopNo(Long cod, String RegionCode,String cityCode,String areaCode, String weight) {
    OrderService service = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
    return service.GetJdDeliveryCopNo(cod, RegionCode, cityCode, areaCode, weight);
  }
  
  /**
   * 获取城市编号和地域编号
   * 
   * @param cityName
   * @param districtName
   * @return 城市信息
   */
  public JdCity GetCityCode(String cityName, String districtName) {
    OrderService service = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
    return service.getJdCity(cityName, districtName);
  }
  
  public JdCity GetSpecialCityCode(String cityName) {
    OrderService service = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
    return service.getSpecialJdCity("%" + cityName + "%");
  }

  /**
   * 获取区县编号
   * 
   * @param 省
   *          、直辖市、自治区编号
   * @param 城市编号
   * @param 街道
   * @return 区县编号
   */
  public JdArea GetAreaCode(String prefectureCode, String cityCode, String district) {
    OrderService service = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
    return service.getJdArea(prefectureCode, cityCode, "%" + district + "%");
  }
  
  // 根据JD省份编号mappingTM省份编号start
  public String getTmallPrefectureCode(String jdPrefectureCode) {
    int prekey = Integer.parseInt(jdPrefectureCode);
    String resultCode = jdPrefectureCode;
    switch (prekey) {
      case 1:
        resultCode = "01";
        break;
      case 2:
        resultCode = "09";
        break;
      case 3:
        resultCode = "02";
        break;
      case 4:
        resultCode = "22";
        break;
      case 5:
        resultCode = "03";
        break;
      case 6:
        resultCode = "04";
        break;
      case 7:
        resultCode = "16";
        break;
      case 8:
        resultCode = "06";
        break;
      case 9:
        resultCode = "07";
        break;
      case 10:
        resultCode = "08";
        break;
      case 11:
        resultCode = "05";
        break;
      case 12:
        resultCode = "10";
        break;
      case 13:
        resultCode = "15";
        break;
      case 14:
        resultCode = "12";
        break;
      case 15:
        resultCode = "11";
        break;
      case 16:
        resultCode = "13";
        break;
      case 17:
        resultCode = "17";
        break;
      case 18:
        resultCode = "18";
        break;
      case 19:
        resultCode = "19";
        break;
      case 20:
        resultCode = "20";
        break;
      case 21:
        resultCode = "14";
        break;
      case 22:
        resultCode = "23";
        break;
      case 23:
        resultCode = "21";
        break;
      case 24:
        resultCode = "24";
        break;
      case 25:
        resultCode = "25";
        break;
      case 26:
        resultCode = "26";
        break;
      case 27:
        resultCode = "27";
        break;
      case 28:
        resultCode = "28";
        break;
      case 29:
        resultCode = "29";
        break;
      case 30:
        resultCode = "30";
        break;
      case 31:
        resultCode = "31";
        break;
      default:
        break;
    }
    return resultCode;
  }
  // 根据JD省份编号mappingTM省份编号end

}
