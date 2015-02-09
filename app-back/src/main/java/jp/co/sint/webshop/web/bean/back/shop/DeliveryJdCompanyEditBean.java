package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.data.domain.UsingFlg;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1051510:配送公司详细
 * 
 * @author cxw
 */
public class DeliveryJdCompanyEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 店铺编号 */
  private String shopCode;

  /** 配送公司编号 */
  @Required
  @AlphaNum2
  @Length(16)
  @Metadata(name = "配送公司编号", order = 1)
  private String deliveryCompanyNo;

  /** 配送公司名称 */
  @Required
  @Length(40)
  @Metadata(name = "配送公司名称", order = 2)
  private String deliveryCompanyName;

  /** 关联URL */
  @Length(256)
  @Url
  @Metadata(name = "关联URL", order = 4)
  private String deliveryCompanyUrl;

  /** 使用区分表示 */
  @Required
  @Metadata(name = "使用区分", order = 5)
  private String displayFlg;
  
  /** 选择模式 */
  private String selectedMode;
  
  /** 选中省份 */
  private String selectedPrefectureCode;
  
  /** 选中城市 */
  private String selectedCityCode;

  /** 添加模式 */
  private boolean insertMode;

  /** 更新模式 */
  private boolean updateMode;

  /** 更新按钮显示 */
  private boolean displayUpdateButtonFlg;

  /** 追加按钮显示 */
  private boolean displayRegister;

  /** 运费设定按钮显示 */
  private boolean displayDateButtonFlg;

  /** 配送公司编号显示 */
  private String deliveryCodeDisplayMode;

  /** 画面入力框数据 */
  private UsingFlg[] displayFlgList = UsingFlg.values();
  
  /** 地域区分集合 */
  private List<RegionBlockCharge> regionBlockChargeList = new ArrayList<RegionBlockCharge>();
  
  /** 选中的区域列表 */
  private List<String> checkedAreaBlockIdList = new ArrayList<String>();
  private List<String> checkedQuXianBlockIdList = new ArrayList<String>();
  private List<CheckedAreas> checkedAreasList = new ArrayList<CheckedAreas>();
  
  private Map<String ,String> ecMinWeList = new HashMap<String ,String>();
  private Map<String ,String> ecMaxWeList = new HashMap<String ,String>();
  private List<NameValue> shippingChargeFlgList = NameValue.asList("0:" + "配送公司" + "/1:" + "配送公司");

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1051510";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return "配送公司详细";
  }

  /**
   * U1051510:配送地域详细
   * 
   * @author cxw
   */
  public static class CheckedAreas implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 配送地域编号 */
    private String areaNo;

    /** 配送地域名称 */
    private String areaName;

    /** 配送地域手续费 */
    @Required
    @Currency
    @Length(11)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "手续费")
    private String charge;

    public String getAreaNo() {
      return areaNo;
    }

    public void setAreaNo(String areaNo) {
      this.areaNo = areaNo;
    }

    public String getAreaName() {
      return areaName;
    }

    public void setAreaName(String areaName) {
      this.areaName = areaName;
    }

    public String getCharge() {
      return charge;
    }

    public void setCharge(String charge) {
      this.charge = charge;
    }
  }

  /**
   * U1050620:配送種別設定明細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RegionBlockCharge implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 都道府県コード */
    private String prefectureCode;

    /** 配送地域编号 */
    private String regionBlockId;

    /** 配送地域名称 */
    private String regionBlockName;

    /** 标识地域是否被选中 */
    private boolean selected;

    /** 城市区分集合 */
    private List<CityBlockCharge> cityBlockChargeList = new ArrayList<CityBlockCharge>();

    /** 选中的城市列表 */
    private List<CityBlockCharge> checkedCityBlockIdList = new ArrayList<CityBlockCharge>();

    private List<CheckedAreas> checkedCitysList = new ArrayList<CheckedAreas>();

    // 选择城市BEAN
    public static class CityBlockCharge implements Serializable {

      /** serial version uid */
      private static final long serialVersionUID = 1L;
      
      /** 标识地域是否被选中 */
      private boolean citySelected;

      /** 城市编号 */
      private String cityCode;

      /** 配送地域编号 */
      private String prefectureCode;

      /** 配送城市名称 */
      private String cityBlockName;
      
      private BigDecimal minWeight;
      
      private BigDecimal maxWeight;
      
      /** 区县区分集合 */
      private List<AreaBlockCharge> areaBlockChargeList = new ArrayList<AreaBlockCharge>();

      /** 选中的区县列表 */
      private List<AreaBlockCharge> checkedAreaBlockIdList = new ArrayList<AreaBlockCharge>();
      
      // 选择区县BEAN
      public static class AreaBlockCharge implements Serializable {

        /** serial version uid */
        private static final long serialVersionUID = 1L;
        
        /** 标识地域是否被选中 */
        private boolean areaSelected;
        
        /** 区县编号 */
        private String areaCode;

        /** 配送地域编号 */
        private String prefectureCode;
        
        /** 城市编号 */
        private String cityCode;

        /** 配送区县名称 */
        private String areaBlockName;

        
        public String getAreaCode() {
          return areaCode;
        }

        
        public void setAreaCode(String areaCode) {
          this.areaCode = areaCode;
        }

        
        public String getPrefectureCode() {
          return prefectureCode;
        }

        
        public void setPrefectureCode(String prefectureCode) {
          this.prefectureCode = prefectureCode;
        }

        
        public String getCityCode() {
          return cityCode;
        }

        
        public void setCityCode(String cityCode) {
          this.cityCode = cityCode;
        }

        
        public String getAreaBlockName() {
          return areaBlockName;
        }

        
        public void setAreaBlockName(String areaBlockName) {
          this.areaBlockName = areaBlockName;
        }
        
        public boolean isAreaSelected() {
          return areaSelected;
        }

        public void setAreaSelected(boolean areaSelected) {
          this.areaSelected = areaSelected;
        }
      
      }

      public String getCityCode() {
        return cityCode;
      }

      public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
      }

      public String getPrefectureCode() {
        return prefectureCode;
      }

      public void setPrefectureCode(String prefectureCode) {
        this.prefectureCode = prefectureCode;
      }

      public String getCityBlockName() {
        return cityBlockName;
      }

      public void setCityBlockName(String cityBlockName) {
        this.cityBlockName = cityBlockName;
      }

      public boolean isSelected() {
        return selected;
      }

      public void setSelected(boolean selected) {
        this.selected = selected;
      }

      /** 标识地域是否被选中 */
      private boolean selected;

      
      
      public boolean isCitySelected() {
        return citySelected;
      }

      
      public void setCitySelected(boolean citySelected) {
        this.citySelected = citySelected;
      }

      public List<AreaBlockCharge> getAreaBlockChargeList() {
        return areaBlockChargeList;
      }

      
      public void setAreaBlockChargeList(List<AreaBlockCharge> areaBlockChargeList) {
        this.areaBlockChargeList = areaBlockChargeList;
      }

      
      public List<AreaBlockCharge> getCheckedAreaBlockIdList() {
        return checkedAreaBlockIdList;
      }

      
      public void setCheckedAreaBlockIdList(List<AreaBlockCharge> checkedAreaBlockIdList) {
        this.checkedAreaBlockIdList = checkedAreaBlockIdList;
      }

      
      /**
       * @return the minWeight
       */
      public BigDecimal getMinWeight() {
        return minWeight;
      }

      
      /**
       * @param minWeight the minWeight to set
       */
      public void setMinWeight(BigDecimal minWeight) {
        this.minWeight = minWeight;
      }

      
      /**
       * @return the maxWeight
       */
      public BigDecimal getMaxWeight() {
        return maxWeight;
      }

      
      /**
       * @param maxWeight the maxWeight to set
       */
      public void setMaxWeight(BigDecimal maxWeight) {
        this.maxWeight = maxWeight;
      }
    }

    /**
     * regionBlockIdを取得します。
     * 
     * @return regionBlockId
     */
    public String getRegionBlockId() {
      return regionBlockId;
    }

    /**
     * regionBlockNameを取得します。
     * 
     * @return regionBlockName
     */
    public String getRegionBlockName() {
      return regionBlockName;
    }

    /**
     * regionBlockIdを設定します。
     * 
     * @param regionBlockId
     *          regionBlockId
     */
    public void setRegionBlockId(String regionBlockId) {
      this.regionBlockId = regionBlockId;
    }

    /**
     * regionBlockNameを設定します。
     * 
     * @param regionBlockName
     *          regionBlockName
     */
    public void setRegionBlockName(String regionBlockName) {
      this.regionBlockName = regionBlockName;
    }

    public boolean isSelected() {
      return selected;
    }

    public void setSelected(boolean selected) {
      this.selected = selected;
    }

    public String getPrefectureCode() {
      return prefectureCode;
    }

    public void setPrefectureCode(String prefectureCode) {
      this.prefectureCode = prefectureCode;
    }

    
    public List<CityBlockCharge> getCityBlockChargeList() {
      return cityBlockChargeList;
    }

    
    public void setCityBlockChargeList(List<CityBlockCharge> cityBlockChargeList) {
      this.cityBlockChargeList = cityBlockChargeList;
    }

    
    public List<CityBlockCharge> getCheckedCityBlockIdList() {
      return checkedCityBlockIdList;
    }

    
    public void setCheckedCityBlockIdList(List<CityBlockCharge> checkedCityBlockIdList) {
      this.checkedCityBlockIdList = checkedCityBlockIdList;
    }

    
    public List<CheckedAreas> getCheckedCitysList() {
      return checkedCitysList;
    }

    
    public void setCheckedCitysList(List<CheckedAreas> checkedCitysList) {
      this.checkedCitysList = checkedCitysList;
    }

  }

  public String getDeliveryCompanyNo() {
    return deliveryCompanyNo;
  }

  public void setDeliveryCompanyNo(String deliveryCompanyNo) {
    this.deliveryCompanyNo = deliveryCompanyNo;
  }

  public String getDeliveryCompanyName() {
    return deliveryCompanyName;
  }

  public void setDeliveryCompanyName(String deliveryCompanyName) {
    this.deliveryCompanyName = deliveryCompanyName;
  }

  public String getDeliveryCompanyUrl() {
    return deliveryCompanyUrl;
  }

  public void setDeliveryCompanyUrl(String deliveryCompanyUrl) {
    this.deliveryCompanyUrl = deliveryCompanyUrl;
  }

  public void setDisplayFlgList(UsingFlg[] displayFlgList) {
    this.displayFlgList = displayFlgList;
  }

  public void setShippingChargeFlgList(List<NameValue> shippingChargeFlgList) {
    this.shippingChargeFlgList = shippingChargeFlgList;
  }

  /**
   * insertModeを取得します。
   * 
   * @return insertMode
   */
  public boolean isInsertMode() {
    return insertMode;
  }

  /**
   * insertModeを設定します。
   * 
   * @param insertMode
   *          insertMode
   */
  public void setInsertMode(boolean insertMode) {
    this.insertMode = insertMode;
  }

  /**
   * updateModeを取得します。
   * 
   * @return updateMode
   */
  public boolean isUpdateMode() {
    return updateMode;
  }

  /**
   * updateModeを設定します。
   * 
   * @param updateMode
   *          updateMode
   */
  public void setUpdateMode(boolean updateMode) {
    this.updateMode = updateMode;
  }

  /**
   * displayFlgListを取得します。
   * 
   * @return displayFlgList
   */
  public UsingFlg[] getDisplayFlgList() {
    return ArrayUtil.immutableCopy(displayFlgList);
  }

  /**
   * shippingChargeFlgListを取得します。
   * 
   * @return shippingChargeFlgList
   */
  public List<NameValue> getShippingChargeFlgList() {
    return shippingChargeFlgList;
  }

  /**
   * deliveryCodeDisplayModeを取得します。
   * 
   * @return deliveryCodeDisplayMode
   */
  public String getDeliveryCodeDisplayMode() {
    return deliveryCodeDisplayMode;
  }

  /**
   * deliveryCodeDisplayModeを設定します。
   * 
   * @param deliveryCodeDisplayMode
   *          deliveryCodeDisplayMode
   */
  public void setDeliveryCodeDisplayMode(String deliveryCodeDisplayMode) {
    this.deliveryCodeDisplayMode = deliveryCodeDisplayMode;
  }

  /**
   * displayUpdateButtonFlgを取得します。
   * 
   * @return displayUpdateButtonFlg
   */
  public boolean getDisplayUpdateButtonFlg() {
    return displayUpdateButtonFlg;
  }

  /**
   * displayUpdateButtonFlgを設定します。
   * 
   * @param displayUpdateButtonFlg
   *          displayUpdateButtonFlg
   */
  public void setDisplayUpdateButtonFlg(boolean displayUpdateButtonFlg) {
    this.displayUpdateButtonFlg = displayUpdateButtonFlg;
  }

  public boolean isDisplayDateButtonFlg() {
    return displayDateButtonFlg;
  }

  public void setDisplayDateButtonFlg(boolean displayDateButtonFlg) {
    this.displayDateButtonFlg = displayDateButtonFlg;
  }

  public boolean isDisplayRegister() {
    return displayRegister;
  }

  public void setDisplayRegister(boolean displayRegister) {
    this.displayRegister = displayRegister;
  }

  public List<RegionBlockCharge> getRegionBlockChargeList() {
    return regionBlockChargeList;
  }

  public void setRegionBlockChargeList(List<RegionBlockCharge> regionBlockChargeList) {
    this.regionBlockChargeList = regionBlockChargeList;
  }

  public List<String> getCheckedAreaBlockIdList() {
    return checkedAreaBlockIdList;
  }

  public void setCheckedAreaBlockIdList(List<String> checkedAreaBlockIdList) {
    this.checkedAreaBlockIdList = checkedAreaBlockIdList;
  }

  public List<CheckedAreas> getCheckedAreasList() {
    return checkedAreasList;
  }

  public void setCheckedAreasList(List<CheckedAreas> checkedAreasList) {
    this.checkedAreasList = checkedAreasList;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public String getDisplayFlg() {
    return displayFlg;
  }

  public void setDisplayFlg(String displayFlg) {
    this.displayFlg = displayFlg;
  }

  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    // 配送公司检索参数
    this.setDeliveryCompanyNo(reqparam.get("deliveryCompanyNo"));
    this.setDeliveryCompanyName(reqparam.get("deliveryCompanyName"));
    this.setDeliveryCompanyUrl(reqparam.get("deliveryCompanyUrl"));
    
    ecMinWeList = new HashMap<String ,String>();
    ecMaxWeList = new HashMap<String ,String>();
    this.setCheckedAreaBlockIdList(Arrays.asList(reqparam.getAll("areaId")));
    this.getCheckedQuXianBlockIdList().clear();
    for(int i= 0;i<checkedAreaBlockIdList.size();i++){
      if(StringUtil.hasValue(checkedAreaBlockIdList.get(i))){
        String[] array=new String[]{};
        array=reqparam.getAll("quxianId_" + checkedAreaBlockIdList.get(i).split("/")[0]);
        for(int j = 0;j<array.length;j++){
          if(StringUtil.hasValue(array[j])){
            String[] codeTmp = array[j].split("/");
            ecMinWeList.put(codeTmp[1], reqparam.get("ec_minWeight_"+codeTmp[1]));
            ecMaxWeList.put(codeTmp[1], reqparam.get("ec_maxWeight_"+codeTmp[1]));
            this.getCheckedQuXianBlockIdList().add(array[j]);
          }
        }
      }
    }
    
    this.setDisplayFlg(reqparam.get("displayFlg"));

  }

  
  public String getSelectedMode() {
    return selectedMode;
  }

  
  public void setSelectedMode(String selectedMode) {
    this.selectedMode = selectedMode;
  }

  
  public String getSelectedPrefectureCode() {
    return selectedPrefectureCode;
  }

  
  public void setSelectedPrefectureCode(String selectedPrefectureCode) {
    this.selectedPrefectureCode = selectedPrefectureCode;
  }

  
  public String getSelectedCityCode() {
    return selectedCityCode;
  }

  
  public void setSelectedCityCode(String selectedCityCode) {
    this.selectedCityCode = selectedCityCode;
  }

  
  public List<String> getCheckedQuXianBlockIdList() {
    return checkedQuXianBlockIdList;
  }

  
  public void setCheckedQuXianBlockIdList(List<String> checkedQuXianBlockIdList) {
    this.checkedQuXianBlockIdList = checkedQuXianBlockIdList;
  }
  /**
   * @return the ecMinWeList
   */
  public Map<String, String> getEcMinWeList() {
    return ecMinWeList;
  }

  
  /**
   * @param ecMinWeList the ecMinWeList to set
   */
  public void setEcMinWeList(Map<String, String> ecMinWeList) {
    this.ecMinWeList = ecMinWeList;
  }

  
  /**
   * @return the ecMaxWeList
   */
  public Map<String, String> getEcMaxWeList() {
    return ecMaxWeList;
  }

  
  /**
   * @param ecMaxWeList the ecMaxWeList to set
   */
  public void setEcMaxWeList(Map<String, String> ecMaxWeList) {
    this.ecMaxWeList = ecMaxWeList;
  }
}
