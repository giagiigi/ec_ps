package jp.co.sint.webshop.web.action.back.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import jp.co.sint.webshop.data.dao.AreaDao;
import jp.co.sint.webshop.data.dao.CityDao;
import jp.co.sint.webshop.data.dao.DeliveryLocationDao;
import jp.co.sint.webshop.data.dao.PrefectureDao;
import jp.co.sint.webshop.data.dao.TmallDeliveryLocationDao;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.EcOrTmallFlg;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Area;
import jp.co.sint.webshop.data.dto.City;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.DeliveryLocation;
import jp.co.sint.webshop.data.dto.DeliveryRegion;
import jp.co.sint.webshop.data.dto.Prefecture;
import jp.co.sint.webshop.data.dto.TmallDeliveryLocation;
import jp.co.sint.webshop.data.dto.TmallDeliveryRegion;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ShopManagementServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean.CheckedAreas;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean.RegionBlockCharge;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean.RegionBlockCharge.CityBlockCharge;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean.RegionBlockCharge.CityBlockCharge.AreaBlockCharge;

/**
 * U1051510:配送公司详细初期表示处理
 * 
 * @author cxw
 */
public class DeliveryCompanyEditInitAction extends WebBackAction<DeliveryCompanyEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {

    boolean authorization = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo());
    } else {
      authorization = Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo());
    }
    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
//    return validateBean(getBean());
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
	  
    DeliveryCompanyEditBean editBean = null;
    //判定如果没有页面参数传递过来,直接报错
    String[] params = getRequestParameter().getPathArgs();

    boolean bReturn = false;
    // 如果页面传递参数大于1,表示有地域被选中
    if (params.length > 1 && "area".equals(params[1])) {
        // 获取选中的地域列表将设置到页面中
        editBean = getBean();

        bReturn = updateRegionBlockInfoFromPage(editBean);
    } else { //如果不是则表示应该显示初始画面
      editBean = new DeliveryCompanyEditBean();

      bReturn = initPage(editBean);
    }

    // 如果前面从delivery_region表中取得配送公司所有的地域及手续费信息列表成功
    // 则将结果表示到页面
    if (bReturn) {
      setRegionBlockInfoPage(editBean);
    }
    
    setRequestBean(editBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  // 将delivery_region表中的信息设置到画面
  private boolean setRegionBlockInfoPage(DeliveryCompanyEditBean bean) {
	  
    // 取得指定配送公司的地域及手续费信息列表
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    List<DeliveryRegion> deliveryRegionList = service.getDeliveryRegionListByDeliveryCompanyNo(bean.getShopCode(), bean.getDeliveryCompanyNo());
    List<TmallDeliveryRegion> deliveryRegionListTmall = service.getDeliveryRegionListByDeliveryCompanyNoTmall(bean.getShopCode(), bean.getDeliveryCompanyNo());

    // 如果取得的列表不是空的,则去bean里的地域列表中将上面查询到的地域标记为选中
    List<RegionBlockCharge> regionBlockChargeList = bean.getRegionBlockChargeList();
    List<RegionBlockCharge> regionBlockChargeListTmall = bean.getRegionBlockChargeListTmall();
    // 同时可以把已选中区域的手续费信息一起设置到Bean里
    List<CheckedAreas> checkedAreasList = new ArrayList<CheckedAreas>();
    List<CheckedAreas> checkedAreasListTmall = new ArrayList<CheckedAreas>();
    for (RegionBlockCharge regionBlock : regionBlockChargeList) {
      regionBlock.setSelected(false);
    }
    
    for (DeliveryRegion selectedRegion : deliveryRegionList) {
      for (RegionBlockCharge regionBlock : regionBlockChargeList) {
        if (regionBlock.getPrefectureCode().equals(selectedRegion.getPrefectureCode())) {
          regionBlock.setSelected(true);

          CheckedAreas areaInfo = new CheckedAreas();
          areaInfo.setAreaNo(regionBlock.getPrefectureCode());
          areaInfo.setAreaName(regionBlock.getRegionBlockName());
          areaInfo.setCharge(selectedRegion.getDeliveryDatetimeCommission().toString());
          checkedAreasList.add(areaInfo);
          break;
        }
      }
    }
    bean.setRegionBlockChargeList(regionBlockChargeList);
    bean.setCheckedAreasList(checkedAreasList);
    for (TmallDeliveryRegion selectedRegion : deliveryRegionListTmall) {
      for (RegionBlockCharge regionBlock : regionBlockChargeListTmall) {
        if (regionBlock.getPrefectureCode().equals(selectedRegion.getPrefectureCode())) {
          regionBlock.setSelected(true);
          
          CheckedAreas areaInfo = new CheckedAreas();
          areaInfo.setAreaNo(regionBlock.getPrefectureCode());
          areaInfo.setAreaName(regionBlock.getRegionBlockName());
          checkedAreasListTmall.add(areaInfo);
          break;
        }
      }
    }
    bean.setRegionBlockChargeListTmall(regionBlockChargeListTmall);
    bean.setCheckedAreasListTmall(checkedAreasListTmall);
    return true;
  }

  // 更新delivery_region表的信息
  private boolean updateRegionBlockInfoFromPage(DeliveryCompanyEditBean bean) {
    List<String> checkedAreaBlockIdList = bean.getCheckedAreaBlockIdList();
    List<String> checkedAreaBlockIdListTmall = bean.getCheckedAreaBlockIdListTmall();
    List<CheckedAreas> checkedAreasList = new ArrayList<CheckedAreas>();
    List<CheckedAreas> checkedAreasListTmall = new ArrayList<CheckedAreas>();
    List<DeliveryRegion> deliveryRegionList = new ArrayList<DeliveryRegion>();
    List<TmallDeliveryRegion> deliveryRegionListTmall = new ArrayList<TmallDeliveryRegion>();
    List<String> checkedQuXianBlockIdList = bean.getCheckedQuXianBlockIdList();
    List<String> checkedQuXianBlockIdListTmall = bean.getCheckedQuXianBlockIdListTmall();
    Map<String,String> maxWeList = bean.getMaxWeList();
    Map<String,String> minWeList = bean.getMinWeList();
    Map<String,String> ecMaxWeList = bean.getEcMaxWeList();
    Map<String,String> ecMinWeList = bean.getEcMinWeList();
    List<DeliveryLocation> deliveryLocationList = new ArrayList<DeliveryLocation>();
    List<TmallDeliveryLocation> deliveryLocationListTmall = new ArrayList<TmallDeliveryLocation>();
    
    // 2013/04/15 配送公司设定对应 ob add start
    String ecOrTmall = bean.getEcOrTmallFlg();
    if (ecOrTmall.equals("0")){
      //从页面获取选中的地域信息
      for (String areaName : checkedAreaBlockIdList) {
        CheckedAreas areaInfo = new CheckedAreas();
        DeliveryRegion deliveryRegion = new DeliveryRegion();
        String[] tmp = areaName.split("/");
        if (tmp.length > 1) {
          deliveryRegion.setPrefectureCode(tmp[0]);
          deliveryRegion.setShopCode(bean.getShopCode());
          deliveryRegion.setDeliveryCompanyNo(bean.getDeliveryCompanyNo());
          deliveryRegion.setDeliveryDatetimeCommission(NumUtil.parse(bean.getDeliveryDatetimeCommission()));
          areaInfo.setAreaNo(tmp[0]);
          areaInfo.setAreaName(tmp[1]);
        }

        checkedAreasList.add(areaInfo);
        deliveryRegionList.add(deliveryRegion);
      }
      
      ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
      
      //从页面获取选中区县信息
      for(String areaCode : checkedQuXianBlockIdList){
        DeliveryLocation deliveryLocation =new DeliveryLocation();
        String[] codeTmp = areaCode.split("/");
        if(codeTmp.length >1){
          
          deliveryLocation.setPrefectureCode(codeTmp[0]);
          deliveryLocation.setCityCode(codeTmp[1]);
          
          if (StringUtil.hasValue(ecMinWeList.get(codeTmp[1]))){
            if (!NumUtil.isDecimal(ecMinWeList.get(codeTmp[1]))){
              addErrorMessage("重量只能是数字！");
              return false;
            }
            deliveryLocation.setMinWeight(new BigDecimal(ecMinWeList.get(codeTmp[1])));
          }
          if (StringUtil.hasValue(ecMaxWeList.get(codeTmp[1]))){
            if (!NumUtil.isDecimal(ecMaxWeList.get(codeTmp[1]))){
              addErrorMessage("重量只能是数字！");
              return false;
            }
            deliveryLocation.setMaxWeight(new BigDecimal(ecMaxWeList.get(codeTmp[1])));
          }
          if (StringUtil.hasValue(ecMaxWeList.get(codeTmp[1])) && StringUtil.hasValue(ecMinWeList.get(codeTmp[1]))){
            if (BigDecimalUtil.isAboveOrEquals(new BigDecimal(ecMinWeList.get(codeTmp[1])), new BigDecimal(ecMaxWeList.get(codeTmp[1])))){
                addErrorMessage("最小重量必须小于最大重量！");
                return false;
            }
          }
          
          if (codeTmp.length >2) {
            deliveryLocation.setAreaCode(codeTmp[2]);
          } else {
            deliveryLocation.setAreaCode("");
          }
          deliveryLocation.setShopCode(bean.getShopCode());
          deliveryLocation.setDeliveryCompanyNo(bean.getDeliveryCompanyNo());
        }
        deliveryLocationList.add(deliveryLocation);
      }
      
      //将获取的地域信息登录到数据库
      ServiceResult result = service.insertDeliveryRegionList(deliveryRegionList);
      if (result.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          // 重复性错误报告
          if (error.equals(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR)) {
            addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED, "配送公司"));
            return false;
          } else if (error.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
            addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, "配送地域"));
            return false;
          }
        }
      }
      
      //将获取的地域信息登录到数据库
      ServiceResult results = service.insertDeliveryLocationList(deliveryLocationList);
      if (results.hasError()) {
        for (ServiceErrorContent error : results.getServiceErrorList()) {
          // 重复性错误报告
          if (error.equals(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR)) {
            addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED, "配送公司"));
            return false;
          } else if (error.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
            addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, "配送区县"));
            return false;
          }
        }
      }else{
        bean.setCheckedAreasList(checkedAreasList);
        initPage(bean);
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "配送地域"));
      }
    } else {//选中Tmall单选按钮时只更新Tmall对应的数据库
      
      for (String areaName : checkedAreaBlockIdListTmall) {
        CheckedAreas areaInfo = new CheckedAreas();
        TmallDeliveryRegion deliveryRegion = new TmallDeliveryRegion();
        String[] tmp = areaName.split("/");
        if (tmp.length > 1) {
          deliveryRegion.setPrefectureCode(tmp[0]);
          deliveryRegion.setShopCode(bean.getShopCode());
          deliveryRegion.setDeliveryCompanyNo(bean.getDeliveryCompanyNo());
          areaInfo.setAreaNo(tmp[0]);
          areaInfo.setAreaName(tmp[1]);
        }

        checkedAreasListTmall.add(areaInfo);
        deliveryRegionListTmall.add(deliveryRegion);
      }
      
      ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
      
      //从页面获取选中区县信息
      for(String areaCode : checkedQuXianBlockIdListTmall){
        TmallDeliveryLocation deliveryLocation =new TmallDeliveryLocation();
        String[] codeTmp = areaCode.split("/");
        if(codeTmp.length >1){
          
          deliveryLocation.setPrefectureCode(codeTmp[0]);
          deliveryLocation.setCityCode(codeTmp[1]);
          if (StringUtil.hasValue(minWeList.get(codeTmp[1]))){
            if (!NumUtil.isDecimal(minWeList.get(codeTmp[1]))){
              addErrorMessage("重量只能是数字！");
              return false;
            }
            deliveryLocation.setMinWeight(new BigDecimal(minWeList.get(codeTmp[1])));
          }
          if (StringUtil.hasValue(maxWeList.get(codeTmp[1]))){
            if (!NumUtil.isDecimal(maxWeList.get(codeTmp[1]))){
              addErrorMessage("重量只能是数字！");
              return false;
            }
            deliveryLocation.setMaxWeight(new BigDecimal(maxWeList.get(codeTmp[1])));
          }
          if (StringUtil.hasValue(maxWeList.get(codeTmp[1])) && StringUtil.hasValue(minWeList.get(codeTmp[1]))){
            if (BigDecimalUtil.isAboveOrEquals(new BigDecimal(minWeList.get(codeTmp[1])), new BigDecimal(maxWeList.get(codeTmp[1])))){
                addErrorMessage("最小重量必须小于最大重量！");
                return false;
            }
          }
          
          if (codeTmp.length >2) {
            deliveryLocation.setAreaCode(codeTmp[2]);
          } else {
            deliveryLocation.setAreaCode("");
          }
          
          deliveryLocation.setShopCode(bean.getShopCode());
          deliveryLocation.setDeliveryCompanyNo(bean.getDeliveryCompanyNo());
        }
        deliveryLocationListTmall.add(deliveryLocation);
      }
      
      //将获取的地域信息登录到数据库
      ServiceResult result = service.insertDeliveryRegionListTmall(deliveryRegionListTmall);
      if (result.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          // 重复性错误报告
          if (error.equals(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR)) {
            addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED, "配送公司"));
            return false;
          } else if (error.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
            addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, "配送地域"));
            return false;
          }
        }
      }
      
      //将获取的地域信息登录到数据库
      ServiceResult results = service.insertDeliveryLocationListTmall(deliveryLocationListTmall);
      if (results.hasError()) {
        for (ServiceErrorContent error : results.getServiceErrorList()) {
          // 重复性错误报告
          if (error.equals(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR)) {
            addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED, "配送公司"));
            return false;
          } else if (error.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
            addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, "配送区县"));
            return false;
          }
        }
      }else{
        bean.setCheckedAreasListTmall(checkedAreasListTmall);
        initPage(bean);
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "配送地域"));
      }
      
    }

    // 2013/04/15 配送公司设定对应 ob add end
    return true;
  }
  
  private boolean initPage(DeliveryCompanyEditBean editBean) {
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    // 配送公司信息更新时处理
    if (getDeliveryCompanyNo().length() > 0) {

      DeliveryCompany deliveryCompany = service.getDeliveryCompany(getLoginInfo().getShopCode(),
          getDeliveryCompanyNo());

      if (deliveryCompany == null) {
        addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED, "配送公司"));

        return false;
      }

      editBean.setShopCode(getLoginInfo().getShopCode());
      editBean.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
      editBean.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
      editBean.setDeliveryDatetimeCommission(deliveryCompany.getDeliveryDatetimeCommission().toString());
      editBean.setDeliveryCompanyUrl(deliveryCompany.getDeliveryCompanyUrl());

      editBean.setRegionBlockChargeList(getRegionBlockChargeOrgList(editBean));
      editBean.setRegionBlockChargeListTmall(getRegionBlockChargeOrgListTmall(editBean));
      editBean.setDisplayFlg(deliveryCompany.getUseFlg().toString());
      editBean.setEcOrTmallFlg(EcOrTmallFlg.EC.getValue());
      editBean.setInsertMode(false);
      editBean.setUpdateMode(true);
      editBean.setDeliveryCodeDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
    } else {
      editBean.setDisplayFlg(DisplayFlg.VISIBLE.getValue());
      editBean.setEcOrTmallFlg(EcOrTmallFlg.EC.getValue());
      editBean.setInsertMode(true);
      editBean.setUpdateMode(false);
      editBean.setDeliveryCodeDisplayMode(WebConstantCode.DISPLAY_EDIT);
    }
    
    return true;
  }

  /**
   * 配送地域一览
   * 
   * @return regionBlockList
   */
  private List<RegionBlockCharge> getRegionBlockChargeOrgList(DeliveryCompanyEditBean bean) {
    PrefectureDao prefectureDao = DIContainer.getDao(PrefectureDao.class);
    DeliveryLocationDao deliveryLocationDao = DIContainer.getDao(DeliveryLocationDao.class);
    // 2013/04/11 配送公司设定对应 ob add start
    CityDao cityDao = DIContainer.getDao(CityDao.class);
    AreaDao areaDao = DIContainer.getDao(AreaDao.class);
    // 2013/04/11 配送公司设定对应 ob add end
    List<Prefecture> prefectureList = prefectureDao.loadAll();
    List<DeliveryLocation> deliveryLocationList = deliveryLocationDao.load(bean.getShopCode(), bean.getDeliveryCompanyNo());
    List<RegionBlockCharge> regionBlockList = new ArrayList<RegionBlockCharge>();
    for (Prefecture block : prefectureList) {
      RegionBlockCharge charge = new RegionBlockCharge();
      charge.setRegionBlockName(block.getPrefectureName());
      charge.setPrefectureCode(block.getPrefectureCode());
      charge.setSelected(false);
      // 2013/04/11 配送公司设定对应 ob add start
      //获得配送城市
      List<City> cityList = cityDao.load(block.getPrefectureCode());
      List<CityBlockCharge> cityBlockList = new ArrayList<CityBlockCharge>();
      if(cityList != null && cityList.size() >0){
        for(City city : cityList){
          CityBlockCharge cityCharge = new CityBlockCharge();
          cityCharge.setCityBlockName(city.getCityName());
          cityCharge.setCityCode(city.getCityCode());
          cityCharge.setPrefectureCode(city.getRegionCode());
          if(deliveryLocationList != null){
            for(DeliveryLocation deliveryLocation : deliveryLocationList){
              if(deliveryLocation.getCityCode().equals(city.getCityCode())){
                cityCharge.setCitySelected(true);
                cityCharge.setMinWeight(deliveryLocation.getMinWeight());
                cityCharge.setMaxWeight(deliveryLocation.getMaxWeight());
                break;
              }
            }
          }
          //获得配送区县
          List<Area> areaList = areaDao.load(block.getPrefectureCode(),city.getCityCode());
          List<AreaBlockCharge> areaBlockList = new ArrayList<AreaBlockCharge>();
          if(areaList != null && areaList.size() > 0 ){
            for(Area aray : areaList){
              AreaBlockCharge areaBlockCharge = new AreaBlockCharge();
              areaBlockCharge.setPrefectureCode(aray.getPrefectureCode());
              areaBlockCharge.setCityCode(aray.getCityCode());
              areaBlockCharge.setAreaCode(aray.getAreaCode());
              areaBlockCharge.setAreaBlockName(aray.getAreaName());
              if(deliveryLocationList != null){
                for(DeliveryLocation deliveryLocation : deliveryLocationList){
                  if(deliveryLocation.getAreaCode().equals(aray.getAreaCode())){
                    areaBlockCharge.setAreaSelected(true);
                    break;
                  }
                }
              }
              areaBlockList.add(areaBlockCharge);
            }
          }
          cityCharge.setAreaBlockChargeList(areaBlockList);
          cityBlockList.add(cityCharge);
        }
      }
      charge.setCheckedCityBlockIdList(cityBlockList);
     // 2013/04/11 配送公司设定对应 ob add end
      regionBlockList.add(charge);
    }
    return regionBlockList;
  }
  
  
  private List<RegionBlockCharge> getRegionBlockChargeOrgListTmall(DeliveryCompanyEditBean bean) {
    PrefectureDao prefectureDao = DIContainer.getDao(PrefectureDao.class);
    TmallDeliveryLocationDao deliveryLocationDao = DIContainer.getDao(TmallDeliveryLocationDao.class);
    // 2013/04/11 配送公司设定对应 ob add start
    CityDao cityDao = DIContainer.getDao(CityDao.class);
    AreaDao areaDao = DIContainer.getDao(AreaDao.class);
    // 2013/04/11 配送公司设定对应 ob add end
    List<Prefecture> prefectureList = prefectureDao.loadAll();
    List<TmallDeliveryLocation> deliveryLocationList = deliveryLocationDao.load(bean.getShopCode(), bean.getDeliveryCompanyNo());
    List<RegionBlockCharge> regionBlockList = new ArrayList<RegionBlockCharge>();
    for (Prefecture block : prefectureList) {
      RegionBlockCharge charge = new RegionBlockCharge();
      charge.setRegionBlockName(block.getPrefectureName());
      charge.setPrefectureCode(block.getPrefectureCode());
      charge.setSelected(false);
      // 2013/04/11 配送公司设定对应 ob add start
      //获得配送城市
      List<City> cityList = cityDao.load(block.getPrefectureCode());
      List<CityBlockCharge> cityBlockList = new ArrayList<CityBlockCharge>();
      if(cityList != null && cityList.size() >0){
        for(City city : cityList){
          CityBlockCharge cityCharge = new CityBlockCharge();
          cityCharge.setCityBlockName(city.getCityName());
          cityCharge.setCityCode(city.getCityCode());
          cityCharge.setPrefectureCode(city.getRegionCode());
          if(deliveryLocationList != null){
            for(TmallDeliveryLocation deliveryLocation : deliveryLocationList){
              if(deliveryLocation.getCityCode().equals(city.getCityCode())){
                cityCharge.setCitySelected(true);
                cityCharge.setMinWeight(deliveryLocation.getMinWeight());
                cityCharge.setMaxWeight(deliveryLocation.getMaxWeight());
                break;
              }
            }
          }
          //获得配送区县
          List<Area> areaList = areaDao.load(block.getPrefectureCode(),city.getCityCode());
          List<AreaBlockCharge> areaBlockList = new ArrayList<AreaBlockCharge>();
          if(areaList != null && areaList.size() > 0 ){
            for(Area aray : areaList){
              AreaBlockCharge areaBlockCharge = new AreaBlockCharge();
              areaBlockCharge.setPrefectureCode(aray.getPrefectureCode());
              areaBlockCharge.setCityCode(aray.getCityCode());
              areaBlockCharge.setAreaCode(aray.getAreaCode());
              areaBlockCharge.setAreaBlockName(aray.getAreaName());
              if(deliveryLocationList != null){
                for(TmallDeliveryLocation deliveryLocation : deliveryLocationList){
                  if(deliveryLocation.getAreaCode().equals(aray.getAreaCode())){
                    areaBlockCharge.setAreaSelected(true);
                    break;
                  }
                }
              }
              areaBlockList.add(areaBlockCharge);
            }
          }
          cityCharge.setAreaBlockChargeList(areaBlockList);
          cityBlockList.add(cityCharge);
        }
      }
      charge.setCheckedCityBlockIdList(cityBlockList);
     // 2013/04/11 配送公司设定对应 ob add end
      regionBlockList.add(charge);
    }
    return regionBlockList;
  }

  /**
   * URLパラメータに設定された配送種別コードを取得する
   * 
   * @return ""
   */
  private String getDeliveryCompanyNo() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length >= 1) {
      return tmpArgs[0];
    }
    return "";
  }

  private String getCompleteParam() {
    String[] pathInfoParams = getRequestParameter().getPathArgs();
    if (pathInfoParams.length >= 2) {
      return pathInfoParams[1];
    }
    return "";
  }

  private Boolean isRegister() {
    String[] pathInfoParams = getRequestParameter().getPathArgs();
    String param1 = "";
    if (pathInfoParams.length >= 2) {
      param1 = pathInfoParams[1];
    }
    return WebConstantCode.COMPLETE_INSERT.equals(param1);
  }

  /**
   * 画面表示必要项初始化
   */
  public void prerender() {
    if (isRegister()) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "配送公司"));
    } else if (getCompleteParam().equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "配送公司相关地域及手续费信息"));
    } else if (getCompleteParam().equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "配送地域"));
    }

    DeliveryCompanyEditBean bean = (DeliveryCompanyEditBean) getRequestBean();

    boolean displayUpdateButtonFlg = false;

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
        displayUpdateButtonFlg = true;
      }
    } else {
      if (Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())) {
        displayUpdateButtonFlg = true;
      }

    }

    bean.setDisplayUpdateButtonFlg(displayUpdateButtonFlg);
    bean.setDisplayDateButtonFlg(true);

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "配送公司详细初期表示处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105151001";
  }

}
