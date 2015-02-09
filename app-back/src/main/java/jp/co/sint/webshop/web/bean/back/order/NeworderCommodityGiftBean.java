package jp.co.sint.webshop.web.bean.back.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020110:新建订单(赠品)Bean。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCommodityGiftBean extends UIBackBean implements UISearchBean {

  private PagerValue pagerValue = new PagerValue();

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 赠品list*/
  private List<GiftCommodityDetailBean> giftCommodityBeanList = new ArrayList<GiftCommodityDetailBean>();

  /** 验证赠品list*/
  private List<GiftCommodityDetailBean> giftCommodityBeanCheckList = new ArrayList<GiftCommodityDetailBean>();


  /**
   * U1020110:新建订单(赠品)Bean。
   * 
   * @author System Integrator Corp.
   */
  public static class GiftCommodityDetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String shopCode = "";

    private String commodityCode = "";

    private String skuCode = "";

    private String commodityName = "";

    private String campainCode = "";

    private String campainName = "";


    /**
     * 取得商品编号。
     * 
     * @return commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
    }

    /**
     * 设定商品编号。
     * 
     * @param commodityCode
     *          commodityCode
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }

    /**
     * 取得商品名称。
     * 
     * @return commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * 设定商品名称。
     * 
     * @param commodityName
     *          commodityName
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }


    /**
     * 取得店铺编号。
     * 
     * @return shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    /**
     * 设定店铺编号。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * 取得SKU编号。
     * 
     * @return skuCode
     */
    public String getSkuCode() {
      return skuCode;
    }

    /**
     * 设定SKU编号。
     * 
     * @param skuCode
     *          skuCode
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    /**
     * 取得活动名称。
     * 
     * @return campainName
     */
    public String getCampainName() {
      return campainName;
    }

    /**
     * 设定活动名称。
     * 
     * @param campainName
     *          campainName
     */
    public void setCampainName(String campainName) {
      this.campainName = campainName;
    }
    /**
     * 取得活动编号。
     * 
     * @return campainCode
     */
    public String getCampainCode() {
      return campainCode;
    }

    /**
     * 设定活动编号。
     * 
     * @param campainCode
     *          campainCode
     */
    public void setCampainCode(String campainCode) {
      this.campainCode = campainCode;
    }
  }

  /**
   * 取得URL参数。
   * 
   * @param reqparam
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
  }

  @Override
  public void setSubJspId() {
  }

  public String getModuleId() {
    return "U1020110";
  }

  public String getModuleName() {
    return Messages.getString("web.bean.back.order.NeworderGiftCommodityBean.0");
  }
  /**
   * 取得pagerValue。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * 设定pagerValue。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * 取得giftCommodityBeanList。
   * 
   * @return giftCommodityBeanList
   */
	public List<GiftCommodityDetailBean> getGiftCommodityBeanList() {
		return giftCommodityBeanList;
	}

  /**
   * 设定giftCommodityBeanList。
   * 
   * @param giftCommodityBeanList
   *          giftCommodityBeanList
   */
	public void setGiftCommodityBeanList(
			List<GiftCommodityDetailBean> giftCommodityBeanList) {
		this.giftCommodityBeanList = giftCommodityBeanList;
	}

  /**
   * 取得giftCommodityBeanCheckList。
   * 
   * @return giftCommodityBeanCheckList
   */
	public List<GiftCommodityDetailBean> getGiftCommodityBeanCheckList() {
		return giftCommodityBeanCheckList;
	}

  /**
   * 设定giftCommodityBeanCheckList。
   * 
   * @param giftCommodityBeanCheckList
   *          giftCommodityBeanCheckList
   */
	public void setGiftCommodityBeanCheckList(
			List<GiftCommodityDetailBean> giftCommodityBeanCheckList) {
		this.giftCommodityBeanCheckList = giftCommodityBeanCheckList;
	}
}
