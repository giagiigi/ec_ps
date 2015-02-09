package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class PrivateCouponExportCondition extends CsvConditionImpl<PrivateCouponAnalysisCsvSchema> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** 优惠券规则编号 */
    private String couponCode;

    /** 优惠券规则名称 */
    private String couponName;
    
    /** 优惠券类别 */
    private String couponType;

    /** 发行件数 */
    private String wholesaleCount;

    // 订单金额
    private String orderAmount;

    // 订单件数
    private Long orderCount;

    // 订单单价
    private String orderPrice;

    // 优惠金额
    private String campaignAmount;
    
    // 新订单件数
    private Long newOrderCount;
    
    // 旧订单件数
    private Long oldOrderCount;

    // 已取消订单金额
    private String abrogateOrderAmount;

    // 已取消订单件数
    private Long abrogateOrderCount;

    // 已取消订单单价
    private String abrogateOrderPrice;

    // 已取消优惠金额
    private String abrogateCampaignAmount;

    /**
     * couponCodeを取得します。
     * 
     * @return couponCode
     */
    public String getCouponCode() {
      return couponCode;
    }

    /**
     * couponCodeを設定します。
     * 
     * @param couponCode
     *          couponCode
     */
    public void setCouponCode(String couponCode) {
      this.couponCode = couponCode;
    }

    /**
     * couponNameを取得します。
     * 
     * @return couponName
     */
    public String getCouponName() {
      return couponName;
    }

    /**
     * couponNameを設定します。
     * 
     * @param couponName
     *          couponName
     */
    public void setCouponName(String couponName) {
      this.couponName = couponName;
    }

    /**
     * couponTypeを取得します。
     * 
     * @return couponType
     */
    public String getCouponType() {
      return couponType;
    }

    /**
     * couponTypeを設定します。
     * 
     * @param couponType
     *          couponType
     */
    public void setCouponType(String couponType) {
      this.couponType = couponType;
    }

    public Long getNewOrderCount() {
      return newOrderCount;
    }

    public void setNewOrderCount(Long newOrderCount) {
      this.newOrderCount = newOrderCount;
    }

    public Long getOldOrderCount() {
      return oldOrderCount;
    }

    public void setOldOrderCount(Long oldOrderCount) {
      this.oldOrderCount = oldOrderCount;
    }

    public String getOrderAmount() {
      return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
      this.orderAmount = orderAmount;
    }

    public Long getOrderCount() {
      return orderCount;
    }

    public void setOrderCount(Long orderCount) {
      this.orderCount = orderCount;
    }

    public String getOrderPrice() {
      return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
      this.orderPrice = orderPrice;
    }

    public String getCampaignAmount() {
      return campaignAmount;
    }

    public void setCampaignAmount(String campaignAmount) {
      this.campaignAmount = campaignAmount;
    }

    public String getAbrogateOrderAmount() {
      return abrogateOrderAmount;
    }

    public void setAbrogateOrderAmount(String abrogateOrderAmount) {
      this.abrogateOrderAmount = abrogateOrderAmount;
    }

    public Long getAbrogateOrderCount() {
      return abrogateOrderCount;
    }

    public void setAbrogateOrderCount(Long abrogateOrderCount) {
      this.abrogateOrderCount = abrogateOrderCount;
    }

    public String getAbrogateOrderPrice() {
      return abrogateOrderPrice;
    }

    public void setAbrogateOrderPrice(String abrogateOrderPrice) {
      this.abrogateOrderPrice = abrogateOrderPrice;
    }

    public String getAbrogateCampaignAmount() {
      return abrogateCampaignAmount;
    }

    public void setAbrogateCampaignAmount(String abrogateCampaignAmount) {
      this.abrogateCampaignAmount = abrogateCampaignAmount;
    }

	public String getWholesaleCount() {
		return wholesaleCount;
	}

	public void setWholesaleCount(String wholesaleCount) {
		this.wholesaleCount = wholesaleCount;
	}
  

}
