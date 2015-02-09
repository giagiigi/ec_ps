package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class CommodityConstituteBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 原商品编号 */
  @Required
  @Length(16)
  @Metadata(name = "原商品编号")
  private String originalCommodityCode;
  
  private boolean check=false;
  
  /**
   * @return the check
   */
  public boolean isCheck() {
    return check;
  }

  
  /**
   * @param check the check to set
   */
  public void setCheck(boolean check) {
    this.check = check;
  }

  /** 按钮类型 */
  private boolean deleteButtonFlg = false;

  /** 分页记录 */
  private PagerValue pagerValue = new PagerValue();

  private List<CommodityConstituteDetailBean> detail = new ArrayList<CommodityConstituteDetailBean>();

  public static class CommodityConstituteDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 原商品编号 */
    @Length(16)
    @Metadata(name = "原商品编号")
    private String originalCommodityCode;

    /** 组合商品编号 */
    @Length(16)
    @Metadata(name = "组合商品编号")
    private String commodityCode;

    /** 组合商品名称 */
    @Length(50)
    @Metadata(name = "组合商品名称")
    private String commodityName;

    /** 组合数量 */
    @Length(8)
    @Metadata(name = "组合数量")
    private Long combinationAmount;

    /** 组合单件价格 */
    @Length(10)
    @Metadata(name = "组合单件价格")
    private BigDecimal unitPrice;

    /** 组合价格 */
    @Length(10)
    @Metadata(name = "组合价格")
    private BigDecimal sumPrice;

    /** 使用状态(官方) */
    @Length(1)
    @Metadata(name = "使用状态(官方)")
    private Long useFlg;

    /** 使用状态(天猫) */
    @Length(1)
    @Metadata(name = "使用状态(天猫)")
    private Long tmallUseFlg;
    
    private boolean buttonFlg;
    
    // 2014/06/05 库存更新对应 ob_卢 add start
    /** 使用状态(京东) */
    @Length(1)
    @Metadata(name = "使用状态(京东)")
    private Long jdUseFlg;
    
    // 删除按钮使用FLG
    private boolean delBtnFlg;
    // 2014/06/05 库存更新对应 ob_卢 add end

    
    /**
     * @return the delBtnFlg
     */
    public boolean isDelBtnFlg() {
      return delBtnFlg;
    }

    /**
     * @param delBtnFlg the delBtnFlg to set
     */
    public void setDelBtnFlg(boolean delBtnFlg) {
      this.delBtnFlg = delBtnFlg;
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
     * @return the buttonFlg
     */
    public boolean isButtonFlg() {
      return buttonFlg;
    }

    /**
     * @param buttonFlg
     *          the buttonFlg to set
     */
    public void setButtonFlg(boolean buttonFlg) {
      this.buttonFlg = buttonFlg;
    }

    /**
     * @return the originalCommodityCode
     */
    public String getOriginalCommodityCode() {
      return originalCommodityCode;
    }

    /**
     * @param originalCommodityCode
     *          the originalCommodityCode to set
     */
    public void setOriginalCommodityCode(String originalCommodityCode) {
      this.originalCommodityCode = originalCommodityCode;
    }

    /**
     * @return the commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
    }

    /**
     * @param commodityCode
     *          the commodityCode to set
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }

    /**
     * @return the commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * @param commodityName
     *          the commodityName to set
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * @return the combinationAmount
     */
    public Long getCombinationAmount() {
      return combinationAmount;
    }

    /**
     * @param combinationAmount
     *          the combinationAmount to set
     */
    public void setCombinationAmount(Long combinationAmount) {
      this.combinationAmount = combinationAmount;
    }

    /**
     * @return the unitPrice
     */
    public BigDecimal getUnitPrice() {
      return unitPrice;
    }

    /**
     * @param unitPrice
     *          the unitPrice to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
    }

    /**
     * @return the sumPrice
     */
    public BigDecimal getSumPrice() {
      return sumPrice;
    }

    /**
     * @param sumPrice
     *          the sumPrice to set
     */
    public void setSumPrice(BigDecimal sumPrice) {
      this.sumPrice = sumPrice;
    }

    /**
     * @return the useFlg
     */
    public Long getUseFlg() {
      return useFlg;
    }

    /**
     * @param useFlg
     *          the useFlg to set
     */
    public void setUseFlg(Long useFlg) {
      this.useFlg = useFlg;
    }

    /**
     * @return the tmallUseFlg
     */
    public Long getTmallUseFlg() {
      return tmallUseFlg;
    }

    /**
     * @param tmallUseFlg
     *          the tmallUseFlg to set
     */
    public void setTmallUseFlg(Long tmallUseFlg) {
      this.tmallUseFlg = tmallUseFlg;
    }

  }

  /**
   * @return the originalCommodityCode
   */
  public String getOriginalCommodityCode() {
    return originalCommodityCode;
  }

  /**
   * @param originalCommodityCode
   *          the originalCommodityCode to set
   */
  public void setOriginalCommodityCode(String originalCommodityCode) {
    this.originalCommodityCode = originalCommodityCode;
  }

  /**
   * @return the deleteButtonFlg
   */
  public boolean isDeleteButtonFlg() {
    return deleteButtonFlg;
  }

  /**
   * @param deleteButtonFlg
   *          the deleteButtonFlg to set
   */
  public void setDeleteButtonFlg(boolean deleteButtonFlg) {
    this.deleteButtonFlg = deleteButtonFlg;
  }

  /**
   * @return the detail
   */
  public List<CommodityConstituteDetailBean> getDetail() {
    return detail;
  }

  /**
   * @param detail
   *          the detail to set
   */
  public void setDetail(List<CommodityConstituteDetailBean> detail) {
    this.detail = detail;
  }

  @Override
  public void setSubJspId() {
  }

  @Override
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  @Override
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
  }

  @Override
  public String getModuleId() {
    // TODO Auto-generated method stub
    return "U1040130";
  }

  @Override
  public String getModuleName() {
    // TODO Auto-generated method stub
    return Messages.getString("web.bean.back.catalog.CommodityConstituteBean.0");
  }
  
  

}
