package jp.co.sint.webshop.web.action.back.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.dao.JdAreaDao;
import jp.co.sint.webshop.data.dao.JdCityDao;
import jp.co.sint.webshop.data.dao.JdDeliveryLocationDao;
import jp.co.sint.webshop.data.dao.JdPrefectureDao;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.JdArea;
import jp.co.sint.webshop.data.dto.JdCity;
import jp.co.sint.webshop.data.dto.JdDeliveryLocation;
import jp.co.sint.webshop.data.dto.JdDeliveryRegion;
import jp.co.sint.webshop.data.dto.JdPrefecture;
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
import jp.co.sint.webshop.web.bean.back.shop.DeliveryJdCompanyEditBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryJdCompanyEditBean.CheckedAreas;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryJdCompanyEditBean.RegionBlockCharge;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryJdCompanyEditBean.RegionBlockCharge.CityBlockCharge;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryJdCompanyEditBean.RegionBlockCharge.CityBlockCharge.AreaBlockCharge;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051510:配送公司详细初期表示处理
 * 
 * @author cxw
 */
public class DeliveryJdCompanyEditInitAction extends WebBackAction<DeliveryJdCompanyEditBean> {

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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
	  
    DeliveryJdCompanyEditBean editBean = null;
    //判定如果没有页面参数传递过来,直接报错
    String[] params = getRequestParameter().getPathArgs();

    boolean bReturn = false;
    // 如果页面传递参数大于1,表示有地域被选中
    if (params.length > 1 && "area".equals(params[1])) {
        // 获取选中的地域列表将设置到页面中
        editBean = getBean();

        bReturn = updateRegionBlockInfoFromPage(editBean);
    } else { //如果不是则表示应该显示初始画面
      editBean = new DeliveryJdCompanyEditBean();

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
  private boolean setRegionBlockInfoPage(DeliveryJdCompanyEditBean bean) {
	  
    // 取得指定配送公司的地域及手续费信息列表
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    List<JdDeliveryRegion> deliveryRegionList = service.getDeliveryRegionListByDeliveryCompanyNoJd(bean.getShopCode(), bean.getDeliveryCompanyNo());

    // 如果取得的列表不是空的,则去bean里的地域列表中将上面查询到的地域标记为选中
    List<RegionBlockCharge> regionBlockChargeList = bean.getRegionBlockChargeList();
    // 同时可以把已选中区域的手续费信息一起设置到Bean里
    List<CheckedAreas> checkedAreasList = new ArrayList<CheckedAreas>();
    for (RegionBlockCharge regionBlock : regionBlockChargeList) {
      regionBlock.setSelected(false);
    }
    
    for (JdDeliveryRegion selectedRegion : deliveryRegionList) {
      for (RegionBlockCharge regionBlock : regionBlockChargeList) {
        if (regionBlock.getPrefectureCode().equals(selectedRegion.getPrefectureCode())) {
          regionBlock.setSelected(true);

          CheckedAreas areaInfo = new CheckedAreas();
          areaInfo.setAreaNo(regionBlock.getPrefectureCode());
          areaInfo.setAreaName(regionBlock.getRegionBlockName());
          checkedAreasList.add(areaInfo);
          break;
        }
      }
    }
    bean.setRegionBlockChargeList(regionBlockChargeList);
    bean.setCheckedAreasList(checkedAreasList);
    return true;
  }

  // 更新delivery_region表的信息
  private boolean updateRegionBlockInfoFromPage(DeliveryJdCompanyEditBean bean) {
    List<String> checkedAreaBlockIdList = bean.getCheckedAreaBlockIdList();
    List<CheckedAreas> checkedAreasList = new ArrayList<CheckedAreas>();
    List<JdDeliveryRegion> deliveryRegionList = new ArrayList<JdDeliveryRegion>();
    List<String> checkedQuXianBlockIdList = bean.getCheckedQuXianBlockIdList();
    Map<String,String> ecMaxWeList = bean.getEcMaxWeList();
    Map<String,String> ecMinWeList = bean.getEcMinWeList();
    List<JdDeliveryLocation> deliveryLocationList = new ArrayList<JdDeliveryLocation>();
    
    // 2013/04/15 配送公司设定对应 ob add start
      //从页面获取选中的地域信息
      for (String areaName : checkedAreaBlockIdList) {
        CheckedAreas areaInfo = new CheckedAreas();
        JdDeliveryRegion deliveryRegion = new JdDeliveryRegion();
        String[] tmp = areaName.split("/");
        if (tmp.length > 1) {
          deliveryRegion.setPrefectureCode(tmp[0]);
          deliveryRegion.setShopCode(bean.getShopCode());
          deliveryRegion.setDeliveryCompanyNo(bean.getDeliveryCompanyNo());
          areaInfo.setAreaNo(tmp[0]);
          areaInfo.setAreaName(tmp[1]);
        }

        checkedAreasList.add(areaInfo);
        deliveryRegionList.add(deliveryRegion);
      }
      
      ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
      
      //从页面获取选中区县信息
      for(String areaCode : checkedQuXianBlockIdList){
        JdDeliveryLocation deliveryLocation =new JdDeliveryLocation();
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
      ServiceResult result = service.insertDeliveryRegionListJd(deliveryRegionList);
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
      ServiceResult results = service.insertDeliveryLocationListJd(deliveryLocationList);
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

    // 2013/04/15 配送公司设定对应 ob add end
    return true;
  }
  
  private boolean initPage(DeliveryJdCompanyEditBean editBean) {
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
      editBean.setDeliveryCompanyUrl(deliveryCompany.getDeliveryCompanyUrl());

      editBean.setRegionBlockChargeList(getRegionBlockChargeOrgList(editBean));
      editBean.setDisplayFlg(deliveryCompany.getUseFlg().toString());
      editBean.setInsertMode(false);
      editBean.setUpdateMode(true);
      editBean.setDeliveryCodeDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
    } else {
      editBean.setDisplayFlg(DisplayFlg.VISIBLE.getValue());
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
  private List<RegionBlockCharge> getRegionBlockChargeOrgList(DeliveryJdCompanyEditBean bean) {
    JdPrefectureDao prefectureDao = DIContainer.getDao(JdPrefectureDao.class);
    JdDeliveryLocationDao deliveryLocationDao = DIContainer.getDao(JdDeliveryLocationDao.class);
    // 2013/04/11 配送公司设定对应 ob add start
    JdCityDao cityDao = DIContainer.getDao(JdCityDao.class);
    JdAreaDao areaDao = DIContainer.getDao(JdAreaDao.class);
    // 2013/04/11 配送公司设定对应 ob add end
    List<JdPrefecture> prefectureList = prefectureDao.loadAll();
    List<JdDeliveryLocation> deliveryLocationList = deliveryLocationDao.load(bean.getShopCode(), bean.getDeliveryCompanyNo());
    List<RegionBlockCharge> regionBlockList = new ArrayList<RegionBlockCharge>();
    for (JdPrefecture block : prefectureList) {
      RegionBlockCharge charge = new RegionBlockCharge();
      charge.setRegionBlockName(block.getPrefectureName());
      charge.setPrefectureCode(block.getPrefectureCode());
      charge.setSelected(false);
      // 2013/04/11 配送公司设定对应 ob add start
      //获得配送城市
      List<JdCity> cityList = cityDao.load(block.getPrefectureCode());
      List<CityBlockCharge> cityBlockList = new ArrayList<CityBlockCharge>();
      if(cityList != null && cityList.size() >0){
        for(JdCity city : cityList){
          CityBlockCharge cityCharge = new CityBlockCharge();
          cityCharge.setCityBlockName(city.getCityName());
          cityCharge.setCityCode(city.getCityCode());
          cityCharge.setPrefectureCode(city.getRegionCode());
          if(deliveryLocationList != null){
            for(JdDeliveryLocation deliveryLocation : deliveryLocationList){
              if(deliveryLocation.getCityCode().equals(city.getCityCode())){
                cityCharge.setCitySelected(true);
                cityCharge.setMinWeight(deliveryLocation.getMinWeight());
                cityCharge.setMaxWeight(deliveryLocation.getMaxWeight());
                break;
              }
            }
          }
          //获得配送区县
          List<JdArea> areaList = areaDao.load(block.getPrefectureCode(),city.getCityCode());
          List<AreaBlockCharge> areaBlockList = new ArrayList<AreaBlockCharge>();
          if(areaList != null && areaList.size() > 0 ){
            for(JdArea aray : areaList){
              AreaBlockCharge areaBlockCharge = new AreaBlockCharge();
              areaBlockCharge.setPrefectureCode(aray.getPrefectureCode());
              areaBlockCharge.setCityCode(aray.getCityCode());
              areaBlockCharge.setAreaCode(aray.getAreaCode());
              areaBlockCharge.setAreaBlockName(aray.getAreaName());
              if(deliveryLocationList != null){
                for(JdDeliveryLocation deliveryLocation : deliveryLocationList){
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

    DeliveryJdCompanyEditBean bean = (DeliveryJdCompanyEditBean) getRequestBean();

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
