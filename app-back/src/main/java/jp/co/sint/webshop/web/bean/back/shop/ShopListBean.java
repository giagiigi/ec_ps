package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.EmailForSearch;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050210:ショップマスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ShopListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<NameValue> shopNameList = new ArrayList<NameValue>();

  private ShopDetail searchCondition = new ShopDetail();

  private List<ShopDetail> searchResultList = new ArrayList<ShopDetail>();

  private PagerValue pagerValue;

  private boolean shopNameLinkFlg;

  /**
   * U1050210:ショップマスタのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class ShopDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String shopStatus;

    @AlphaNum2
    @Metadata(name = "ショップコード")
    private String shopCode;

    private String shopName;

    @Length(18)
    @Phone
    @Metadata(name = "電話番号")
    private String tel;

    @Length(11)
    @MobileNumber
    @Metadata(name = "手机号码")
    private String mobileTel;

    @Length(256)
    @EmailForSearch
    @Metadata(name = "メールアドレス")
    private String email;

    /**
     * emailを取得します。
     * 
     * @return email
     */
    public String getEmail() {
      return email;
    }

    /**
     * shopCodeを取得します。
     * 
     * @return shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    /**
     * shopNameを取得します。
     * 
     * @return shopName
     */
    public String getShopName() {
      return shopName;
    }

    /**
     * shopStatusを取得します。
     * 
     * @return shopStatus
     */
    public String getShopStatus() {
      return shopStatus;
    }

    /**
     * telを取得します。
     * 
     * @return tel
     */
    public String getTel() {
      return tel;
    }

    /**
     * emailを設定します。
     * 
     * @param email
     *          email
     */
    public void setEmail(String email) {
      this.email = email;
    }

    /**
     * shopCodeを設定します。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * shopNameを設定します。
     * 
     * @param shopName
     *          shopName
     */
    public void setShopName(String shopName) {
      this.shopName = shopName;
    }

    /**
     * shopStatusを設定します。
     * 
     * @param shopStatus
     *          shopStatus
     */
    public void setShopStatus(String shopStatus) {
      this.shopStatus = shopStatus;
    }

    /**
     * telを設定します。
     * 
     * @param tel
     *          tel
     */
    public void setTel(String tel) {
      this.tel = tel;
    }

    
    /**
     * mobileTelを取得します。
     *
     * @return mobileTel mobileTel
     */
    public String getMobileTel() {
      return mobileTel;
    }

    
    /**
     * mobileTelを設定します。
     *
     * @param mobileTel 
     *          mobileTel
     */
    public void setMobileTel(String mobileTel) {
      this.mobileTel = mobileTel;
    }

  }

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */
  public ShopDetail getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchResultListを取得します。
   * 
   * @return searchResultList
   */
  public List<ShopDetail> getSearchResultList() {
    return searchResultList;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(ShopDetail searchCondition) {
    this.searchCondition = searchCondition;
  }

  /**
   * searchResultListを設定します。
   * 
   * @param searchResultList
   *          searchResultList
   */
  public void setSearchResultList(List<ShopDetail> searchResultList) {
    this.searchResultList = searchResultList;
  }

  /**
   * shopNameListを取得します。
   * 
   * @return shopNameList
   */
  public List<NameValue> getShopNameList() {
    return shopNameList;
  }

  /**
   * shopNameListを設定します。
   * 
   * @param shopNameList
   *          shopNameList
   */
  public void setShopNameList(List<NameValue> shopNameList) {
    this.shopNameList = shopNameList;
  }

  /**
   * shopNameLinkFlgを取得します。
   * 
   * @return shopNameLinkFlg
   */
  public boolean getShopNameLinkFlg() {
    return shopNameLinkFlg;
  }

  /**
   * shopNameLinkFlgを設定します。
   * 
   * @param shopNameLinkFlg
   *          shopNameLinkFlg
   */
  public void setShopNameLinkFlg(boolean shopNameLinkFlg) {
    this.shopNameLinkFlg = shopNameLinkFlg;
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

    // 検索条件を設定
    ShopDetail shopDetail = new ShopDetail();

    shopDetail.setShopCode(reqparam.get("shopCode"));
    shopDetail.setShopName(reqparam.get("shopName"));
    shopDetail.setTel(reqparam.get("tel"));
    //Add by V10-CH start
    shopDetail.setMobileTel(reqparam.get("mobileTel"));
    //Add by V10-CH end
    shopDetail.setEmail(reqparam.get("email"));
    shopDetail.setShopStatus(reqparam.get("shopStatus"));
    setSearchCondition(shopDetail);

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.ShopListBean.0");
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

}
