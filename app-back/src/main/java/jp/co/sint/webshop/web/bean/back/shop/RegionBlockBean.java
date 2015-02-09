package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050810:地域ブロック設定のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class RegionBlockBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private RegionBlockDetail regionBlock = new RegionBlockDetail();

  private List<RegionBlockDetail> regionBlockList = new ArrayList<RegionBlockDetail>();

  private List<PrefectureDetail> prefectureList = new ArrayList<PrefectureDetail>();

  private List<CodeAttribute> regionBlockNameList = new ArrayList<CodeAttribute>();

  private boolean displayUpdateButtonFlg;

  private boolean displayDeleteButtonFlg;

  public Region[] getRegions() {
    return Region.values();
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    regionBlock.setRegionBlockName(reqparam.get("registerRegionBlockName"));

    String[] listKey = {
        "regionBlockId", "regionBlockName"
    };
    for (RegionBlockDetail detail : regionBlockList) {
      Map<String, String> map = reqparam.getListDataWithKey(detail.getRegionBlockId(), listKey);
      detail.setRegionBlockId(map.get("regionBlockId"));
      detail.setRegionBlockName(map.get("regionBlockName"));
    }

    listKey = new String[] {
        "prefectureCode", "prefectureRegionBlockId"
    };
    for (PrefectureDetail detail : prefectureList) {
      Map<String, String> map = reqparam.getListDataWithKey(detail.getPrefectureCode(), listKey);
      detail.setRegionBlockId(map.get("prefectureRegionBlockId"));
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050810";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.RegionBlockBean.0");
  }

  /**
   * U1050810:地域ブロック設定のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RegionBlockDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String regionBlockId;

    @Required
    @Length(20)
    @Metadata(name = "地域ブロック名", order = 1)
    private String regionBlockName;

    // @Required
    // @Digit
    // @Length(1)
    // @Metadata(name = "配送遅延日数", order = 2)
    // private String delayDays;

    private boolean daletableFlg;

    private Date updateDatetime;

    // /**
    // * delayDaysを取得します。
    // *
    // * @return delayDays
    // */
    // public String getDelayDays() {
    // return delayDays;
    // }

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

    // /**
    // * delayDaysを設定します。
    // *
    // * @param delayDays
    // * delayDays
    // */
    // public void setDelayDays(String delayDays) {
    // this.delayDays = delayDays;
    // }

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

    /**
     * daletableFlgを取得します。
     * 
     * @return daletableFlg
     */
    public boolean isDaletableFlg() {
      return daletableFlg;
    }

    /**
     * daletableFlgを設定します。
     * 
     * @param daletableFlg
     *          daletableFlg
     */
    public void setDaletableFlg(boolean daletableFlg) {
      this.daletableFlg = daletableFlg;
    }

    /**
     * updateDatetimeを取得します。
     * 
     * @return updateDatetime
     */
    public Date getUpdateDatetime() {
      return DateUtil.immutableCopy(updateDatetime);
    }

    /**
     * updateDatetimeを設定します。
     * 
     * @param updateDatetime
     *          updateDatetime
     */
    public void setUpdateDatetime(Date updateDatetime) {
      this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
    }

  }

  /**
   * U1050810:地域ブロック設定のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PrefectureDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String prefectureCode;

    private String prefecturName;

    @Required
    @Metadata(name = "地域ブロックコード", order = 1)
    private String regionBlockId;

    private Date updateDatetime;

    /**
     * prefectureCodeを取得します。
     * 
     * @return prefectureCode
     */
    public String getPrefectureCode() {
      return prefectureCode;
    }

    /**
     * prefecturNameを取得します。
     * 
     * @return prefecturName
     */
    public String getPrefecturName() {
      return prefecturName;
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
     * prefectureCodeを設定します。
     * 
     * @param prefectureCode
     *          prefectureCode
     */
    public void setPrefectureCode(String prefectureCode) {
      this.prefectureCode = prefectureCode;
    }

    /**
     * prefecturNameを設定します。
     * 
     * @param prefecturName
     *          prefecturName
     */
    public void setPrefecturName(String prefecturName) {
      this.prefecturName = prefecturName;
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
     * updateDatetimeを取得します。
     * 
     * @return updateDatetime
     */
    public Date getUpdateDatetime() {
      return DateUtil.immutableCopy(updateDatetime);
    }

    /**
     * updateDatetimeを設定します。
     * 
     * @param updateDatetime
     *          updateDatetime
     */
    public void setUpdateDatetime(Date updateDatetime) {
      this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
    }

  }

  /**
   * prefectureListを取得します。
   * 
   * @return prefectureList
   */
  public List<PrefectureDetail> getPrefectureList() {
    return prefectureList;
  }

  /**
   * regionBlockを取得します。
   * 
   * @return regionBlock
   */
  public RegionBlockDetail getRegionBlock() {
    return regionBlock;
  }

  /**
   * regionBlockListを取得します。
   * 
   * @return regionBlockList
   */
  public List<RegionBlockDetail> getRegionBlockList() {
    return regionBlockList;
  }

  /**
   * regionBlockNameListを取得します。
   * 
   * @return regionBlockNameList
   */
  public List<CodeAttribute> getRegionBlockNameList() {
    return regionBlockNameList;
  }

  /**
   * prefectureListを設定します。
   * 
   * @param prefectureList
   *          prefectureList
   */
  public void setPrefectureList(List<PrefectureDetail> prefectureList) {
    this.prefectureList = prefectureList;
  }

  /**
   * regionBlockを設定します。
   * 
   * @param regionBlock
   *          regionBlock
   */
  public void setRegionBlock(RegionBlockDetail regionBlock) {
    this.regionBlock = regionBlock;
  }

  /**
   * regionBlockListを設定します。
   * 
   * @param regionBlockList
   *          regionBlockList
   */
  public void setRegionBlockList(List<RegionBlockDetail> regionBlockList) {
    this.regionBlockList = regionBlockList;
  }

  /**
   * regionBlockNameListを設定します。
   * 
   * @param regionBlockNameList
   *          regionBlockNameList
   */
  public void setRegionBlockNameList(List<CodeAttribute> regionBlockNameList) {
    this.regionBlockNameList = regionBlockNameList;
  }

  /**
   * displayDeleteButtonFlgを取得します。
   * 
   * @return displayDeleteButtonFlg
   */
  public boolean getDisplayDeleteButtonFlg() {
    return displayDeleteButtonFlg;
  }

  /**
   * displayDeleteButtonFlgを設定します。
   * 
   * @param displayDeleteButtonFlg
   *          displayDeleteButtonFlg
   */
  public void setDisplayDeleteButtonFlg(boolean displayDeleteButtonFlg) {
    this.displayDeleteButtonFlg = displayDeleteButtonFlg;
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

  /**
   * 「地方」のコード定義を表す列挙クラスです。
   * 
   * @author System Integrator Corp.
   */
  public static enum Region {

    /** 「北海道・東北」を表す値です。 */
    HUA_BEI("华北地区", new PrefectureCode[] {
        //        
        PrefectureCode.BEIJING,
        //
        PrefectureCode.TIANJIN,
        //
        PrefectureCode.HEBEI,
        //
        PrefectureCode.SHANXI1,
        //
        PrefectureCode.NEIMENGGU
    }),

    /** 「関東」を表す値です。 */
    DONG_BEI("东北地区", new PrefectureCode[] {
        PrefectureCode.LIAONING,
        //
        PrefectureCode.JILIN,
        //
        PrefectureCode.HEILONGJIANG
    }),

    /** 「中部」を表す値です。 */
    HUA_DONG("华东地区", new PrefectureCode[] {
        /** 「新潟県」を表す値です。 */
        PrefectureCode.SHANGHAI,
        //
        PrefectureCode.SHANDONG,
        //
        PrefectureCode.JIANGSU,
        //
        PrefectureCode.ZHEJIANG,
        //
        PrefectureCode.ANHUI,
        //
        PrefectureCode.JIANGXI
    }),

    /** 「近畿」を表す値です。 */
    HUA_NAN("华南地区", new PrefectureCode[] {
        PrefectureCode.FUJIAN,
        //
        PrefectureCode.HAINAN,
        //
        PrefectureCode.GUANGDONG
        //
        //PrefectureCode.TAIWAN,
        //
        //PrefectureCode.XIANGGANG,
        //
        //PrefectureCode.AOMEN
        
    }),

    /** 「近畿」を表す値です。 */
    XI_BEI("西北地区", new PrefectureCode[] {
        PrefectureCode.SHANXI,
        //
        PrefectureCode.NINGXIA,
        //
        PrefectureCode.GANSU,
        //
        PrefectureCode.QINGHAI,
        //
        PrefectureCode.XINJIANG
        //
        //PrefectureCode.TAIWAN,
        //
        //PrefectureCode.XIANGGANG,
        //
        //PrefectureCode.AOMEN
        
    }),

    /** 「近畿」を表す値です。 */
    HUA_ZHONG("华中地区", new PrefectureCode[] {
        PrefectureCode.HENAN,
        //
        PrefectureCode.HUBEI,
        //
        PrefectureCode.HUNAN        
    }),

    /** 「近畿」を表す値です。 */
    XI_NAN("西南地区", new PrefectureCode[] {
        PrefectureCode.GUANGXI,
        //
        PrefectureCode.SICHUAN,
        //
        PrefectureCode.GUIZHOU,
        //
        PrefectureCode.YUNNAN,
        //
        PrefectureCode.CHONGQING,
        //
        PrefectureCode.XIZANG   
    });


    private String name;

    private PrefectureCode[] prefectures;

    private Region() {
    }

    private Region(String name, PrefectureCode[] prefectures) {
      this.name = name;
      this.prefectures = prefectures;
    }

    /**
     * コード名称を返します。
     * 
     * @return コード名称
     */
    public String getName() {
      return name;
    }

    public PrefectureCode[] getPrefectures() {
      return ArrayUtil.immutableCopy(this.prefectures);
    }

  }

}
