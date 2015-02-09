package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
 
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040110:商品マスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityImageBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;
  
  //控制上传图片按钮是否显示
  private boolean uploadBtnFlg;
  
  //图片上传时间
  private String searchUploadDate;
  
  //上传图片对应的SKU编号
  private String searchSKUList;
  
  private List<CommodityListResult> commodityListResult = new ArrayList<CommodityListResult>();

  private PagerValue pagerValue;
  
  public static class CommodityListResult implements Serializable {

    private static final long serialVersionUID = 1L;
    //SKU编号
    private String SKUCode;

    //商品名
    private String commodityName;
    
    //代表SKU
    private String mainSKU;
    
    //local
    private List<String> localCommodityImgList = new ArrayList<String>();
    
    //commodity
    private List<String> commodityImgList = new ArrayList<String>();
    
    //淘宝商城
    private String tmallImg;
    
    // 2014/05/06 京东WBS对应 ob_李先超 add start
    //京东商城
    private String jdImg;
    // 2014/05/06 京东WBS对应 ob_李先超 add end
    
    //官网1
    private List<ImgInfo> ECImgList1 = new ArrayList<ImgInfo>();
  
    //官网2
    private List<ImgInfo> ECImgList2 = new ArrayList<ImgInfo>();
    
    @SuppressWarnings("serial")
    public static class ImgInfo implements Serializable {
      //淘宝商城
      private String imgName;
      //淘宝商城
      private String imgURL;
      public String getImgName() {
        return imgName;
      }
      public void setImgName(String imgName) {
        this.imgName = imgName;
      }
      public String getImgURL() {
        return imgURL;
      }
      public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
      }
    }

    /**
     * @return the sKUCode
     */
    public String getSKUCode() {
      return SKUCode;
    }

    /**
     * @param code the sKUCode to set
     */
    public void setSKUCode(String code) {
      SKUCode = code;
    }

    /**
     * @return the commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * @param commodityName the commodityName to set
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * @return the mainSKU
     */
    public String getMainSKU() {
      return mainSKU;
    }

    /**
     * @param mainSKU the mainSKU to set
     */
    public void setMainSKU(String mainSKU) {
      this.mainSKU = mainSKU;
    }

    /**
     * @return the commodityImgList
     */
    public List<String> getCommodityImgList() {
      return commodityImgList;
    }

    /**
     * @param commodityImgList the commodityImgList to set
     */
    public void setCommodityImgList(List<String> commodityImgList) {
      this.commodityImgList = commodityImgList;
    }

    /**
     * @return the localCommodityImgList
     */
    public List<String> getLocalCommodityImgList() {
      return localCommodityImgList;
    }

    /**
     * @param localCommodityImgList the localCommodityImgList to set
     */
    public void setLocalCommodityImgList(List<String> localCommodityImgList) {
      this.localCommodityImgList = localCommodityImgList;
    }

    /**
     * @return the tmallImg
     */
    public String getTmallImg() {
      return tmallImg;
    }

    /**
     * @param tmallImg the tmallImg to set
     */
    public void setTmallImg(String tmallImg) {
      this.tmallImg = tmallImg;
    }

    public List<ImgInfo> getECImgList1() {
      return ECImgList1;
    }

    public void setECImgList1(List<ImgInfo> imgList1) {
      ECImgList1 = imgList1;
    }

    public List<ImgInfo> getECImgList2() {
      return ECImgList2;
    }

    public void setECImgList2(List<ImgInfo> imgList2) {
      ECImgList2 = imgList2;
    }
    
    // 2014/05/06 京东WBS对应 ob_李先超 add start
    /**
     * @return the jdImg
     */
    public String getJdImg() {
      return jdImg;
    }

    /**
     * @param jdImg the jdImg to set
     */
    public void setJdImg(String jdImg) {
      this.jdImg = jdImg;
    }
    // 2014/05/06 京东WBS对应 ob_李先超 add end

  }
  
   /**
   * @return the uploadBtnFlg
   */
  public boolean isUploadBtnFlg() {
    return uploadBtnFlg;
  }

  /**
   * @param uploadBtnFlg the uploadBtnFlg to set
   */
  public void setUploadBtnFlg(boolean uploadBtnFlg) {
    this.uploadBtnFlg = uploadBtnFlg;
  }

  /**
   * @return the searchUploadDate
   */
  public String getSearchUploadDate() {
    return searchUploadDate;
  }

  /**
   * @param searchUploadDate the searchUploadDate to set
   */
  public void setSearchUploadDate(String searchUploadDate) {
    this.searchUploadDate = searchUploadDate;
  }

  /**
   * @return the searchSKUList
   */
  public String getSearchSKUList() {
    return searchSKUList;
  }

  /**
   * @param searchSKUList the searchSKUList to set
   */
  public void setSearchSKUList(String searchSKUList) {
    this.searchSKUList = searchSKUList;
  }

  /**
   * @return the commodityListResult
   */
  public List<CommodityListResult> getCommodityListResult() {
    return commodityListResult;
  }

  /**
   * @param commodityListResult the commodityListResult to set
   */
  public void setCommodityListResult(List<CommodityListResult> commodityListResult) {
    this.commodityListResult = commodityListResult;
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
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
    setSearchUploadDate(reqparam.getDateString("uploadDate"));
    setSearchSKUList(reqparam.get("skucodeList"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1041010";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.CommodityImageBean.0");
  }
}
