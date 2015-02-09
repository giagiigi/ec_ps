package jp.co.sint.webshop.web.bean.front.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2020310:まとめてカートのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class BlanketCartBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<BlanketCartDetailBean> detailList = new ArrayList<BlanketCartDetailBean>();

  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  private boolean isFirstDisplay;

  private static final int MAX_ROWS = 10; // 10.1.6 10268 追加

  /**
   * isFirstDisplayを返します。
   * 
   * @return the isFirstDisplay
   */
  public boolean isFirstDisplay() {
    return isFirstDisplay;
  }

  /**
   * isFirstDisplayを設定します。
   * 
   * @param firstDisplay
   *          設定する isFirstDisplay
   */
  public void setFirstDisplay(boolean firstDisplay) {
    this.isFirstDisplay = firstDisplay;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    List<Map<String, String>> blanketList = reqparam.getListData("no", "shopCode", "skuCode", "amount", "commodityName",
        "standardDetail1Name", "standardDetail2Name", "displayDeleteButton");

    // 10.1.6 10268 追加 ここから
    List<Map<String, String>> adjustedList = new ArrayList<Map<String, String>>();
    for (int i = 0; i < MAX_ROWS; i++) {
      if (blanketList.size() > i) {
        adjustedList.add(blanketList.get(i));
      } else {
        adjustedList.add(new HashMap<String, String>());
      }
    }
    blanketList = adjustedList;
    // 10.1.6 10268 追加 ここまで

    List<BlanketCartDetailBean> details = new ArrayList<BlanketCartDetailBean>();
    for (Map<String, String> blanket : blanketList) {

      String no = blanket.get("no");
      String shopCode = blanket.get("shopCode");
      String skuCode = blanket.get("skuCode");
      String amount = blanket.get("amount");
      String commodityName = blanket.get("commodityName");
      String standardDetail1Name = blanket.get("standardDetail1Name");
      String standardDetail2Name = blanket.get("standardDetail2Name");
      String displayDeleteButton = blanket.get("displayDeleteButton");

      if ((StringUtil.hasValue(shopCode) && DIContainer.getWebshopConfig().getOperatingMode() != OperatingMode.ONE)
          || StringUtil.hasValue(skuCode) || StringUtil.hasValue(amount)) {
        BlanketCartDetailBean detail = new BlanketCartDetailBean();
        detail.setNo(no);
        detail.setShopCode(shopCode);
        detail.setSkuCode(skuCode);
        detail.setAmount(amount);
        detail.setCommodityName(commodityName);
        detail.setStandardDetail1Name(standardDetail1Name);
        detail.setStandardDetail2Name(standardDetail2Name);
        detail.setDisplayDeleteButton(displayDeleteButton);
        details.add(detail);
      } else {
        details.add(null);
      }
    }
    setDetailList(details);
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2020310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.cart.BlanketCartBean.0");
  }

  /**
   * U2020310:まとめてカートのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class BlanketCartDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String no;

    @Required
    @AlphaNum2
    @Length(16)
    @Metadata(name = "ショップ", order = 1)
    private String shopCode;

    @Required
    @AlphaNum2
    @Length(24)
    @Metadata(name = "SKUコード", order = 2)
    private String skuCode;

    private String commodityName;

    private String standardDetail1Name;

    private String standardDetail2Name;

    private String displayDeleteButton;

    @Digit
    @Length(4)
    @Range(min = 1, max = 9999)
    @Metadata(name = "数量", order = 3)
    private String amount;

    /**
     * amountを取得します。
     * 
     * @return amount
     */
    public String getAmount() {
      return amount;
    }

    /**
     * amountを設定します。
     * 
     * @param amount
     *          amount
     */
    public void setAmount(String amount) {
      this.amount = amount;
    }

    /**
     * commodityNameを取得します。
     * 
     * @return commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * commodityNameを設定します。
     * 
     * @param commodityName
     *          commodityName
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
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
     * shopCodeを設定します。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * skuCodeを取得します。
     * 
     * @return skuCode
     */
    public String getSkuCode() {
      return skuCode;
    }

    /**
     * skuCodeを設定します。
     * 
     * @param skuCode
     *          skuCode
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    /**
     * standardDetail1Nameを取得します。
     * 
     * @return standardDetail1Name
     */
    public String getStandardDetail1Name() {
      return standardDetail1Name;
    }

    /**
     * standardDetail1Nameを設定します。
     * 
     * @param standardDetail1Name
     *          standardDetail1Name
     */
    public void setStandardDetail1Name(String standardDetail1Name) {
      this.standardDetail1Name = standardDetail1Name;
    }

    /**
     * standardDetail2Nameを取得します。
     * 
     * @return standardDetail2Name
     */
    public String getStandardDetail2Name() {
      return standardDetail2Name;
    }

    /**
     * standardDetail2Nameを設定します。
     * 
     * @param standardDetail2Name
     *          standardDetail2Name
     */
    public void setStandardDetail2Name(String standardDetail2Name) {
      this.standardDetail2Name = standardDetail2Name;
    }

    /**
     * noを取得します。
     * 
     * @return no
     */
    public String getNo() {
      return no;
    }

    /**
     * noを設定します。
     * 
     * @param no
     *          no
     */
    public void setNo(String no) {
      this.no = no;
    }

    /**
     * displayDeleteButtonを取得します。
     * 
     * @return displayDeleteButton
     */

    public String getDisplayDeleteButton() {
      return displayDeleteButton;
    }

    /**
     * displayDeleteButtonを設定します。
     * 
     * @param displayDeleteButton
     *          displayDeleteButton
     */
    public void setDisplayDeleteButton(String displayDeleteButton) {
      this.displayDeleteButton = displayDeleteButton;
    }

  }

  /**
   * shopListを取得します。
   * 
   * @return shopList
   */
  public List<CodeAttribute> getShopList() {
    return shopList;
  }

  /**
   * shopListを設定します。
   * 
   * @param shopList
   *          shopList
   */
  public void setShopList(List<CodeAttribute> shopList) {
    this.shopList = shopList;
  }

  /**
   * detailListを取得します。
   * 
   * @return detailList
   */
  public List<BlanketCartDetailBean> getDetailList() {
    return detailList;
  }

  /**
   * detailListを設定します。
   * 
   * @param detailList
   *          detailList
   */
  public void setDetailList(List<BlanketCartDetailBean> detailList) {
    this.detailList = detailList;
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.cart.BlanketCartBean.0"), "/app/cart/blanket_cart"));
    return topicPath;
  }
}
