package jp.co.sint.webshop.web.bean.back.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020110:新建订单(套餐商品)Bean。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCommodityCompositionBean extends UIBackBean implements UISearchBean {

  private PagerValue pagerValue = new PagerValue();

  /** serial version uid */
  private static final long serialVersionUID = 1L;
  /** 套餐名*/
  private String commodityCompositionName = "";
  /** 套餐价格*/
  private BigDecimal commodityCopositionPrice = new BigDecimal(0);
  /** 优惠价格*/
  private BigDecimal commodityCopositionPreferentialPrice = new BigDecimal(0);
  /** 消费税类型*/
  private Long taxType = 0L;
  /** 店编号*/
  private String shopCode = "";
  /** 套餐商品SKU编号*/
  private String skuCode = "";
  /** 套餐商品编号*/
  private String commodityCode = "";

  /** 套餐商品list*/
  private List<CommodityCompositionDetailBean> commodityCompositionDetailBeanList = new ArrayList<CommodityCompositionDetailBean>();

  /**
   * U1020110:新建订单(套餐商品)Bean。
   * 
   * @author System Integrator Corp.
   */
  public static class CommodityCompositionDetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String childShopCode = "";

    private String childCommodityName = "";

    private String childCommodityCode = "";

    private List<CodeAttribute> standardNameList = new ArrayList<CodeAttribute>();

    private String childRetailPrice = "";

    private String childUnitPrice = "";

    private String taxType = "";

    private String selectedSkuCode = "";

    private String childRepresentSkuCode = "";

    private String childRepresentSkuUnitPrice = "";

    /**
     * 取得代表SKU编号。
     * 
     * @return childRepresentSkuCode
     */
    public String getChildRepresentSkuCode() {
      return childRepresentSkuCode;
    }

    /**
     * 设定代表SKU编号。
     * 
     * @param childRepresentSkuCode
     *          childRepresentSkuCode
     */
    public void setChildRepresentSkuCode(String childRepresentSkuCode) {
      this.childRepresentSkuCode = childRepresentSkuCode;
    }

    /**
     * 取得选中的SKU编号。
     * 
     * @return selectedSkuCode
     */
    public String getSelectedSkuCode() {
      return selectedSkuCode;
    }

    /**
     * 设定选中的SKU编号。
     * 
     * @param selectedSkuCode
     *          selectedSkuCode
     */
    public void setSelectedSkuCode(String selectedSkuCode) {
      this.selectedSkuCode = selectedSkuCode;
    }

    /**
     * 取得商品名称。
     * 
     * @return childCommodityName
     */
    public String getChildCommodityName() {
      return childCommodityName;
    }

    /**
     * 设定商品名称。
     * 
     * @param childCommodityName
     *          childCommodityName
     */
    public void setChildCommodityName(String childCommodityName) {
      this.childCommodityName = childCommodityName;
    }

    /**
     * 取得贩卖价格。
     * 
     * @return childRetailPrice
     */
    public String getChildRetailPrice() {
      return childRetailPrice;
    }

    /**
     * 设定贩卖价格。
     * 
     * @param childRetailPrice
     *          childRetailPrice
     */
    public void setChildRetailPrice(String childRetailPrice) {
      this.childRetailPrice = childRetailPrice;
    }

    /**
     * 取得店铺编号。
     * 
     * @return childShopCode
     */
    public String getChildShopCode() {
      return childShopCode;
    }

    /**
     * 设定店铺编号。
     * 
     * @param childShopCode
     *          childShopCode
     */
    public void setChildShopCode(String childShopCode) {
      this.childShopCode = childShopCode;
    }

		public String getChildRepresentSkuUnitPrice() {
			return childRepresentSkuUnitPrice;
		}

		public void setChildRepresentSkuUnitPrice(String childRepresentSkuUnitPrice) {
			this.childRepresentSkuUnitPrice = childRepresentSkuUnitPrice;
		}

    public String getChildCommodityCode() {
			return childCommodityCode;
		}

		public void setChildCommodityCode(String childCommodityCode) {
			this.childCommodityCode = childCommodityCode;
		}

		/**
     * childUnitPriceを取得します。
     * 
     * @return childUnitPrice
     */
    public String getChildUnitPrice() {
      return childUnitPrice;
    }

    /**
     * childUnitPriceを設定します。
     * 
     * @param childUnitPrice
     *          childUnitPrice
     */
    public void setChildUnitPrice(String childUnitPrice) {
      this.childUnitPrice = childUnitPrice;
    }

		/**
     * 取得税区分。
     * 
     * @return taxType
     */
    public String getTaxType() {
      return taxType;
    }

    /**
     * 设定税区分。
     * 
     * @param taxType
     *          taxType
     */
    public void setTaxType(String taxType) {
      this.taxType = taxType;
    }

    /**
     * 取得standardNameList。
     * 
     * @return standardNameList
     */
    public List<CodeAttribute> getStandardNameList() {
      return standardNameList;
    }

    /**
     * 设定standardNameList。
     * 
     * @param standardNameList
     *          standardNameList
     */
    public void setStandardNameList(List<CodeAttribute> standardNameList) {
      this.standardNameList = standardNameList;
    }
  }

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
    return Messages.getString("web.bean.back.order.NeworderSetCommodityCompositionBean.0");
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
   * 取得commodityCompositionDetailBeanList。
   * 
   * @return commodityCompositionDetailBeanList
   */
  public List<CommodityCompositionDetailBean> getCommodityCompositionDetailBeanList() {
		return commodityCompositionDetailBeanList;
	}

  /**
   * 设定commodityCompositionDetailBeanList。
   * 
   * @param commodityCompositionDetailBeanList
   *          commodityCompositionDetailBeanList
   */
	public void setCommodityCompositionDetailBeanList(
			List<CommodityCompositionDetailBean> commodityCompositionDetailBeanList) {
		this.commodityCompositionDetailBeanList = commodityCompositionDetailBeanList;
	}

  /**
   * 取得commodityCompositionName。
   * 
   * @return commodityCompositionName
   */
	public String getCommodityCompositionName() {
		return commodityCompositionName;
	}

  /**
   * 设定commodityCompositionName。
   * 
   * @param commodityCompositionName
   *          commodityCompositionName
   */
	public void setCommodityCompositionName(String commodityCompositionName) {
		this.commodityCompositionName = commodityCompositionName;
	}

  /**
   * 取得commodityCopositionPrice。
   * 
   * @return commodityCopositionPrice
   */
	public BigDecimal getCommodityCopositionPrice() {
		return commodityCopositionPrice;
	}

  /**
   * 设定commodityCopositionPrice。
   * 
   * @param commodityCopositionPrice
   *          commodityCopositionPrice
   */
	public void setCommodityCopositionPrice(BigDecimal commodityCopositionPrice) {
		this.commodityCopositionPrice = commodityCopositionPrice;
	}

  /**
   * 取得commodityCopositionPreferentialPrice。
   * 
   * @return commodityCopositionPreferentialPrice
   */
	public BigDecimal getCommodityCopositionPreferentialPrice() {
		return commodityCopositionPreferentialPrice;
	}

  /**
   * 设定commodityCopositionPreferentialPrice。
   * 
   * @param commodityCopositionPreferentialPrice
   *          commodityCopositionPreferentialPrice
   */
	public void setCommodityCopositionPreferentialPrice(
			BigDecimal commodityCopositionPreferentialPrice) {
		this.commodityCopositionPreferentialPrice = commodityCopositionPreferentialPrice;
	}

  /**
   * 取得税区分taxType。
   * 
   * @return taxType
   */
  public Long getTaxType() {
    return taxType;
  }

  /**
   * 设定税区分taxType。
   * 
   * @param taxType
   *          taxType
   */
  public void setTaxType(Long taxType) {
    this.taxType = taxType;
  }

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}
}
