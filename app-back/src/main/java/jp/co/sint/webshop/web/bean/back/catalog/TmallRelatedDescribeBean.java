package jp.co.sint.webshop.web.bean.back.catalog;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040230:カテゴリ－関連付けのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class TmallRelatedDescribeBean extends UIBackBean {

  private static final long serialVersionUID = 1L;

  private String shopCode;
  
  private String commodityCode;

  @Metadata(name = "商品描述中文")
  private String decribeCn;  
  
  @Metadata(name = "商品描述英文")
  private String decribeEn;  
  
  @Metadata(name = "商品描述日文")
  private String decribeJp;  
  
  @Metadata(name = "商品描述Tmall")
  private String decribeTmall;
  
  // 2014/04/30 京东WBS对应 ob_姚 add start
  @Metadata(name = "商品描述京东")
  private String decribeJd;
  
  private Long jdCommodityId;
  
  private Long jdUseFlg;
  // 2014/04/30 京东WBS对应 ob_姚 add end
  
  private Date updatedDatetime;
  
  private Long tmallCommodityId;
  
  private Long tmallUseFlg;
  
  private boolean ecUseFlg = false;
  
  private boolean displayPreview = false;

  private String previewUrl;
  

  /**
   * shopCode��擾���܂��B
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCode��ݒ肵�܂��B
   * 
   * @param shopCode
   *          �ݒ肷��shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * updatedDatetime��擾���܂��B
   * 
   * @return updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * updatedDatetime��ݒ肵�܂��B
   * 
   * @param updatedDatetime
   *          �ݒ肷��updatedDatetime
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }

  @Override
  public void setSubJspId() {

  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
    setDecribeCn(reqparam.get("htmlText"));
    setDecribeEn(reqparam.get("htmlText1"));
    setDecribeJp(reqparam.get("htmlText2"));
    setDecribeTmall(reqparam.get("htmlText3"));
    // 2014/04/30 京东WBS对应 ob_姚 add start
    setDecribeJd(reqparam.get("htmlText4"));
    // 2014/04/30 京东WBS对应 ob_姚 add end
  }

  @Override
  public String getModuleId() {
    return "U1090009";
  }

  @Override
  public String getModuleName() {
    return "商品描述编辑";
  }

  
  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  
  /**
   * @param commodityCode the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  
  /**
   * @return the decribeCn
   */
  public String getDecribeCn() {
    return decribeCn;
  }

  
  /**
   * @param decribeCn the decribeCn to set
   */
  public void setDecribeCn(String decribeCn) {
    this.decribeCn = decribeCn;
  }

  
  /**
   * @return the decribeEn
   */
  public String getDecribeEn() {
    return decribeEn;
  }

  
  /**
   * @param decribeEn the decribeEn to set
   */
  public void setDecribeEn(String decribeEn) {
    this.decribeEn = decribeEn;
  }

  
  /**
   * @return the decribeJp
   */
  public String getDecribeJp() {
    return decribeJp;
  }

  
  /**
   * @param decribeJp the decribeJp to set
   */
  public void setDecribeJp(String decribeJp) {
    this.decribeJp = decribeJp;
  }

  
  /**
   * @return the decribeTmall
   */
  public String getDecribeTmall() {
    return decribeTmall;
  }

  
  /**
   * @param decribeTmall the decribeTmall to set
   */
  public void setDecribeTmall(String decribeTmall) {
    this.decribeTmall = decribeTmall;
  }

  
  /**
   * @return the decribeJd
   */
  public String getDecribeJd() {
    return decribeJd;
  }

  /**
   * @param decribeJd the decribeJd to set
   */
  public void setDecribeJd(String decribeJd) {
    this.decribeJd = decribeJd;
  }

  /**
   * @return the jdCommodityId
   */
  public Long getJdCommodityId() {
    return jdCommodityId;
  }

  /**
   * @param jdCommodityId the jdCommodityId to set
   */
  public void setJdCommodityId(Long jdCommodityId) {
    this.jdCommodityId = jdCommodityId;
  }

  /**
   * @return the jdUseFlg
   */
  public Long getJdUseFlg() {
    return jdUseFlg;
  }

  /**
   * @param jdUseFlg the jdUseFlg to set
   */
  public void setJdUseFlg(Long jdUseFlg) {
    this.jdUseFlg = jdUseFlg;
  }

  /**
   * @return the tmallCommodityId
   */
  public Long getTmallCommodityId() {
    return tmallCommodityId;
  }

  
  /**
   * @param tmallCommodityId the tmallCommodityId to set
   */
  public void setTmallCommodityId(Long tmallCommodityId) {
    this.tmallCommodityId = tmallCommodityId;
  }

  
  /**
   * @return the tmallUseFlg
   */
  public Long getTmallUseFlg() {
    return tmallUseFlg;
  }

  
  /**
   * @param tmallUseFlg the tmallUseFlg to set
   */
  public void setTmallUseFlg(Long tmallUseFlg) {
    this.tmallUseFlg = tmallUseFlg;
  }

  
  /**
   * @return the previewUrl
   */
  public String getPreviewUrl() {
    return previewUrl;
  }

  
  /**
   * @param previewUrl the previewUrl to set
   */
  public void setPreviewUrl(String previewUrl) {
    this.previewUrl = previewUrl;
  }

  
  /**
   * @return the displayPreview
   */
  public boolean isDisplayPreview() {
    return displayPreview;
  }

  
  /**
   * @param displayPreview the displayPreview to set
   */
  public void setDisplayPreview(boolean displayPreview) {
    this.displayPreview = displayPreview;
  }

  
  /**
   * @return the ecUseFlg
   */
  public boolean isEcUseFlg() {
    return ecUseFlg;
  }

  
  /**
   * @param ecUseFlg the ecUseFlg to set
   */
  public void setEcUseFlg(boolean ecUseFlg) {
    this.ecUseFlg = ecUseFlg;
  }

}
