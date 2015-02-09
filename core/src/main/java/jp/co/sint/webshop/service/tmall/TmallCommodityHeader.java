package jp.co.sint.webshop.service.tmall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TmallCommodityHeader implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 淘宝商品型号ID（必填）
  private String numiid;

  // EC的商品型号ID（必填）
  private String outerNumiid;

  // 商品库存数量（必填）
  private String num;

  // 商品价格（必填）
  private String price;

  // 发布类型一口价（必填）
  private String type = "fixed";

  // 商品为新品（必填）
  private String stuff_status = "new";

  // 商品标题（必填）
  private String title;

  // 商品描述（必填）
  private String desc;

  // 商品所属省份（必填）
  private String locationState;

  // 商品所属城市（必填）
  private String locationCity;

  // 商品上传后状态默认为销售中onsale，instock（仓库中）（必填）
  private String approveStatus;

  // 叶子类目ID（必填）
  private String cid;

  // 属性串，（必填属性）
  private String props;

  // 属性串，（关键属性）发布产品专用
  private String keyProps;

  // 属性串，（非关键属性）发布产品专用
  private String noKeyProps;

  // 属性串，（自定义属性）发布产品专用
  private String CustomerProps;

  // 属性串，（销售属性）发布产品专用
  private String saleProps;

  // 浏览上传产品图片的URL(必传参数)发布产品专用
  private String imageUrl;

  // 产品名称(必传参数)发布产品专用
  private String proName;

  // 产品ID
  private String proId;

  // 需要发票（必填）
  private String hasInvoice = "true";

  // 商城积分返点比例（必填）
  private String auctionPoint = "5";

  // 拍下减库存（必填）
  private String subStock = "1";

  // 会员打折标志true为打折false不打折
  private String hasDiscount;

  // 商品重量
  private String weight;

  // 运费承担方式 可选值:seller（卖家承担）,buyer(买家承担);默认值:seller
  private String freightPayer = "buyer";

  // 运费模板
  private String postageId;

  // 自定义属性pid串
  private String inputPids;

  /*
   * 用户自行输入的子属性名和属性值，结构:"父属性值;一级子属性名;一级子属性值;二级子属性名;
   * 自定义输入值,....",如：“耐克;耐克系列;科比系列;科比系列;2K5,Nike乔丹鞋;乔丹系列;乔丹鞋系列;
   * 乔丹鞋系列;json5”，多个自定义属性用','分割，input_str需要与input_pids一一对应，注：
   * 通常一个类目下用户可输入的关键属性不超过1个
   */
  // 自定义输入内容
  private String inputStr;

  // 商品所属的店铺类目列表。按逗号分隔。结构:",cid1,cid2,...,"，如果店铺类目存在二级类目，必须传入子类目cids
  private String sellerCids;

  /* 退换货标志 */
  private String sellPromise;

  // 上架时间
  private Date listTime;

  // SKUList
  private List<TmallCommoditySku> tmallCommoditySkuList = new ArrayList<TmallCommoditySku>();

  /*
   * 食品安全新增商品字段
   */
  // 生产许可证号
  private String foodSecurityPrdLicenseNo;

  // 产品标准号
  private String foodSecurityDesignCode;

  // 厂名
  private String foodSecurityFactory;

  // 厂址
  private String foodSecurityFactorySite;

  // 厂家联系方式
  private String foodSecurityContact;

  // 配料表
  private String foodSecurityMix;

  // 储藏方法
  private String foodSecurityPlanStorage;

  // 保质期
  private String foodSecurityPeriod;

  // 食品添加剂
  private String foodSecurityFoodAdditive;

  // 供货商
  private String foodSecuritySupplier;

  // 生产开始日期,格式必须为yyyy-MM-dd
  private String foodSecurityProductDateStart;

  // 生产结束日期,格式必须为yyyy-MM-dd
  private String foodSecurityProductDateEnd;

  // 进货开始日期，要在生产日期之后，格式必须为yyyy-MM-dd
  private String foodSecurityStockDateStart;

  // 进货结束日期，要在生产日期之后，格式必须为yyyy-MM-dd
  private String foodSecurityStockDateEnd;

  // sku销售属性
  private String skuPro;

  private String outerId;

  public String getOuterId() {
    return outerId;
  }

  public void setOuterId(String outerId) {
    this.outerId = outerId;
  }

  public String getSkuPro() {
    return skuPro;
  }

  public void setSkuPro(String skuPro) {
    this.skuPro = skuPro;
  }

  public String getFoodSecurityPrdLicenseNo() {
    return foodSecurityPrdLicenseNo;
  }

  public void setFoodSecurityPrdLicenseNo(String foodSecurityPrdLicenseNo) {
    this.foodSecurityPrdLicenseNo = foodSecurityPrdLicenseNo;
  }

  public String getFoodSecurityDesignCode() {
    return foodSecurityDesignCode;
  }

  public void setFoodSecurityDesignCode(String foodSecurityDesignCode) {
    this.foodSecurityDesignCode = foodSecurityDesignCode;
  }

  public String getFoodSecurityFactory() {
    return foodSecurityFactory;
  }

  public void setFoodSecurityFactory(String foodSecurityFactory) {
    this.foodSecurityFactory = foodSecurityFactory;
  }

  public String getFoodSecurityFactorySite() {
    return foodSecurityFactorySite;
  }

  public void setFoodSecurityFactorySite(String foodSecurityFactorySite) {
    this.foodSecurityFactorySite = foodSecurityFactorySite;
  }

  public String getFoodSecurityContact() {
    return foodSecurityContact;
  }

  public void setFoodSecurityContact(String foodSecurityContact) {
    this.foodSecurityContact = foodSecurityContact;
  }

  public String getFoodSecurityMix() {
    return foodSecurityMix;
  }

  public void setFoodSecurityMix(String foodSecurityMix) {
    this.foodSecurityMix = foodSecurityMix;
  }

  public String getFoodSecurityPlanStorage() {
    return foodSecurityPlanStorage;
  }

  public void setFoodSecurityPlanStorage(String foodSecurityPlanStorage) {
    this.foodSecurityPlanStorage = foodSecurityPlanStorage;
  }

  public String getFoodSecurityPeriod() {
    return foodSecurityPeriod;
  }

  public void setFoodSecurityPeriod(String foodSecurityPeriod) {
    this.foodSecurityPeriod = foodSecurityPeriod;
  }

  public String getFoodSecurityFoodAdditive() {
    return foodSecurityFoodAdditive;
  }

  public void setFoodSecurityFoodAdditive(String foodSecurityFoodAdditive) {
    this.foodSecurityFoodAdditive = foodSecurityFoodAdditive;
  }

  public String getFoodSecuritySupplier() {
    return foodSecuritySupplier;
  }

  public void setFoodSecuritySupplier(String foodSecuritySupplier) {
    this.foodSecuritySupplier = foodSecuritySupplier;
  }

  public String getFoodSecurityProductDateStart() {
    return foodSecurityProductDateStart;
  }

  public void setFoodSecurityProductDateStart(String foodSecurityProductDateStart) {
    this.foodSecurityProductDateStart = foodSecurityProductDateStart;
  }

  public String getFoodSecurityProductDateEnd() {
    return foodSecurityProductDateEnd;
  }

  public void setFoodSecurityProductDateEnd(String foodSecurityProductDateEnd) {
    this.foodSecurityProductDateEnd = foodSecurityProductDateEnd;
  }

  public String getFoodSecurityStockDateStart() {
    return foodSecurityStockDateStart;
  }

  public void setFoodSecurityStockDateStart(String foodSecurityStockDateStart) {
    this.foodSecurityStockDateStart = foodSecurityStockDateStart;
  }

  public String getFoodSecurityStockDateEnd() {
    return foodSecurityStockDateEnd;
  }

  public void setFoodSecurityStockDateEnd(String foodSecurityStockDateEnd) {
    this.foodSecurityStockDateEnd = foodSecurityStockDateEnd;
  }

  public List<TmallCommoditySku> getTmallCommoditySkuList() {
    return tmallCommoditySkuList;
  }

  public void setTmallCommoditySkuList(List<TmallCommoditySku> tmallCommoditySkuList) {
    this.tmallCommoditySkuList = tmallCommoditySkuList;
  }

  public String getSellerCids() {
    return sellerCids;
  }

  public void setSellerCids(String sellerCids) {
    this.sellerCids = sellerCids;
  }

  public String getInputPids() {
    return inputPids;
  }

  public Date getListTime() {
    return listTime;
  }

  public void setListTime(Date listTime) {
    this.listTime = listTime;
  }

  public void setInputPids(String inputPids) {
    this.inputPids = inputPids;
  }

  public String getInputStr() {
    return inputStr;
  }

  public void setInputStr(String inputStr) {
    this.inputStr = inputStr;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public String getHasDiscount() {
    return hasDiscount;
  }

  public void setHasDiscount(String hasDiscount) {
    this.hasDiscount = hasDiscount;
  }

  public String getOuterNumiid() {
    return outerNumiid;
  }

  public void setOuterNumiid(String outerNumiid) {
    this.outerNumiid = outerNumiid;
  }

  public String getNumiid() {
    return numiid;
  }

  public void setNumiid(String numiid) {
    this.numiid = numiid;
  }

  public String getNum() {
    return num;
  }

  public void setNum(String num) {
    this.num = num;
  }

  public String getProName() {
    return proName;
  }

  public void setProName(String proName) {
    this.proName = proName;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getKeyProps() {
    return keyProps;
  }

  public void setKeyProps(String keyProps) {
    this.keyProps = keyProps;
  }

  public String getStuff_status() {
    return stuff_status;
  }

  public void setStuff_status(String stuff_status) {
    this.stuff_status = stuff_status;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDesc() {
    return desc;
  }

  public String getCustomerProps() {
    return CustomerProps;
  }

  public void setCustomerProps(String customerProps) {
    CustomerProps = customerProps;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getLocationState() {
    return locationState;
  }

  public void setLocationState(String locationState) {
    this.locationState = locationState;
  }

  public String getLocationCity() {
    return locationCity;
  }

  public void setLocationCity(String locationCity) {
    this.locationCity = locationCity;
  }

  public String getApproveStatus() {
    return approveStatus;
  }

  public void setApproveStatus(String approveStatus) {
    this.approveStatus = approveStatus;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getCid() {
    return cid;
  }

  public void setCid(String cid) {
    this.cid = cid;
  }

  public String getProps() {
    return props;
  }

  public void setProps(String props) {
    this.props = props;
  }

  public String getHasInvoice() {
    return hasInvoice;
  }

  public void setHasInvoice(String hasInvoice) {
    this.hasInvoice = hasInvoice;
  }

  public String getAuctionPoint() {
    return auctionPoint;
  }

  public void setAuctionPoint(String auctionPoint) {
    this.auctionPoint = auctionPoint;
  }

  public String getSubStock() {
    return subStock;
  }

  public String getNoKeyProps() {
    return noKeyProps;
  }

  public void setNoKeyProps(String noKeyProps) {
    this.noKeyProps = noKeyProps;
  }

  public String getSaleProps() {
    return saleProps;
  }

  public void setSaleProps(String saleProps) {
    this.saleProps = saleProps;
  }

  public void setSubStock(String subStock) {
    this.subStock = subStock;
  }

  public String getFreightPayer() {
    return freightPayer;
  }

  public void setFreightPayer(String freightPayer) {
    this.freightPayer = freightPayer;
  }

  public String getPostageId() {
    return postageId;
  }

  public void setPostageId(String postageId) {
    this.postageId = postageId;
  }

  /**
   * @return the sellPromise
   */
  public String getSellPromise() {
    return sellPromise;
  }

  public String getProId() {
    return proId;
  }

  public void setProId(String proId) {
    this.proId = proId;
  }

  /**
   * @param sellPromise
   *          the sellPromise to set
   */
  public void setSellPromise(String sellPromise) {
    this.sellPromise = sellPromise;
  }
}
