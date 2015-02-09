package jp.co.sint.webshop.web.bean.back.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Percentage;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1030410:顧客グループマスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private CustomerGroupBeanDetail edit = new CustomerGroupBeanDetail();

  private List<CustomerGroupBeanDetail> list = new ArrayList<CustomerGroupBeanDetail>();

  private boolean updateMode;

  private boolean deleteMode;

  /**
   * U1030410:顧客グループマスタのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerGroupBeanDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "顧客グループコード", order = 1)
    private String customerGroupCode;

    @Required
    @Length(40)
    @Metadata(name = "顧客グループ名", order = 2)
    private String customerGroupName;

    // 20120517 shen add start
    @Required
    @Length(40)
    @Metadata(name = "顾客组名(英文)", order = 3)
    private String customerGroupNameEn;

    @Required
    @Length(40)
    @Metadata(name = "顾客组名(日文)", order = 4)
    private String customerGroupNameJp;

    // 20120517 shen add end

    @Required
    @Length(3)
    @Percentage
    @Metadata(name = "ポイント付与率", order = 5)
    private String customerGroupPointRate;

    private String memberShip;

    private String updatedDateTime;

    private String displayMode;

    private String deleteButtonDisplayFlg;

    private Date updatedDatetime;

    /**
     * 更新日時を取得します。
     * 
     * @return updatedDatetime 更新日時
     */
    public Date getUpdatedDatetime() {
      return DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * 更新日時を設定します。
     * 
     * @param updatedDatetime
     *          updatedDatetime 更新日時
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * 顧客グループコードを取得します。
     * 
     * @return customerGroupCode 顧客グループコード
     */
    public String getCustomerGroupCode() {
      return customerGroupCode;
    }

    /**
     * ポイント付与率を取得します。
     * 
     * @return customerGroupPointRate ポイント付与率
     */
    public String getCustomerGroupPointRate() {
      return customerGroupPointRate;
    }

    /**
     * 顧客グループ名を取得します。
     * 
     * @return customerGroupName 顧客グループ名
     */
    public String getCustomerGroupName() {
      return customerGroupName;
    }

    /**
     * memberShipを取得します。
     * 
     * @return memberShip
     */
    public String getMemberShip() {
      return memberShip;
    }

    /**
     * updatedDateTimeを取得します。
     * 
     * @return updatedDateTime
     */
    public String getUpdatedDateTime() {
      return updatedDateTime;
    }

    /**
     * customerGroupCodeを設定します。
     * 
     * @param customerGroupCode
     *          customerGroupCode
     */
    public void setCustomerGroupCode(String customerGroupCode) {
      this.customerGroupCode = customerGroupCode;
    }

    /**
     * customerGroupPointRateを設定します。
     * 
     * @param customerGroupPointRate
     *          customerGroupPointRate
     */
    public void setCustomerGroupPointRate(String customerGroupPointRate) {
      this.customerGroupPointRate = customerGroupPointRate;
    }

    /**
     * customerGroupNameを設定します。
     * 
     * @param customerGroupName
     *          customerGroupName
     */
    public void setCustomerGroupName(String customerGroupName) {
      this.customerGroupName = customerGroupName;
    }

    /**
     * memberShipを設定します。
     * 
     * @param memberShip
     *          memberShip
     */
    public void setMemberShip(String memberShip) {
      this.memberShip = memberShip;
    }

    /**
     * updatedDateTimeを設定します。
     * 
     * @param updatedDateTime
     *          updatedDateTime
     */
    public void setUpdatedDateTime(String updatedDateTime) {
      this.updatedDateTime = updatedDateTime;
    }

    /**
     * displayModeを取得します。
     * 
     * @return displayMode
     */
    public String getDisplayMode() {
      return displayMode;
    }

    /**
     * displayModeを設定します。
     * 
     * @param displayMode
     *          displayMode
     */
    public void setDisplayMode(String displayMode) {
      this.displayMode = displayMode;
    }

    /**
     * deleteButtonDisplayFlgを取得します。
     * 
     * @return deleteButtonDisplayFlg
     */
    public String getDeleteButtonDisplayFlg() {
      return deleteButtonDisplayFlg;
    }

    /**
     * deleteButtonDisplayFlgを設定します。
     * 
     * @param deleteButtondisplayFlg
     *          deleteButtondisplayFlg
     */
    public void setDeleteButtonDisplayFlg(String deleteButtondisplayFlg) {
      this.deleteButtonDisplayFlg = deleteButtondisplayFlg;
    }

    /**
     * @return the customerGroupNameEn
     */
    public String getCustomerGroupNameEn() {
      return customerGroupNameEn;
    }

    /**
     * @param customerGroupNameEn
     *          the customerGroupNameEn to set
     */
    public void setCustomerGroupNameEn(String customerGroupNameEn) {
      this.customerGroupNameEn = customerGroupNameEn;
    }

    /**
     * @return the customerGroupNameJp
     */
    public String getCustomerGroupNameJp() {
      return customerGroupNameJp;
    }

    /**
     * @param customerGroupNameJp
     *          the customerGroupNameJp to set
     */
    public void setCustomerGroupNameJp(String customerGroupNameJp) {
      this.customerGroupNameJp = customerGroupNameJp;
    }

  }

  /**
   * 編集用データを取得します。
   * 
   * @return edit
   */
  public CustomerGroupBeanDetail getEdit() {
    return edit;
  }

  /**
   * 表示用リストを取得します。
   * 
   * @return list 表示用リスト
   */
  public List<CustomerGroupBeanDetail> getList() {
    return list;
  }

  /**
   * 編集用データを設定します。
   * 
   * @param edit
   *          編集用データ
   */
  public void setEdit(CustomerGroupBeanDetail edit) {
    this.edit = edit;
  }

  /**
   * 表示用リストを設定します。
   * 
   * @param list
   *          表示用リスト
   */
  public void setList(List<CustomerGroupBeanDetail> list) {
    this.list = list;
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
    edit.setCustomerGroupCode(reqparam.get("customerGroupCode"));
    edit.setCustomerGroupName(reqparam.get("customerGroupName"));
    edit.setCustomerGroupPointRate(reqparam.get("customerGroupPointRate"));
    // 20120517 shen add start
    edit.setCustomerGroupNameEn(reqparam.get("customerGroupNameEn"));
    edit.setCustomerGroupNameJp(reqparam.get("customerGroupNameJp"));
    // 20120517 shen add end
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1030410";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.customer.CustomerGroupBean.0");
  }

  /**
   * 削除モードかどうかを返します。
   * 
   * @return 削除モードのときはtrue
   */
  public boolean isDeleteMode() {
    return deleteMode;
  }

  /**
   * 削除モードかどうかを設定します。
   * 
   * @param deleteMode
   *          削除モードのときはtrue
   */
  public void setDeleteMode(boolean deleteMode) {
    this.deleteMode = deleteMode;
  }

  /**
   * 更新モードかどうかを返します。
   * 
   * @return 更新モードのときはtrue
   */
  public boolean isUpdateMode() {
    return updateMode;
  }

  /**
   * 更新モードかどうかを設定します。
   * 
   * @param updateMode
   *          更新モードのときはtrue
   */
  public void setUpdateMode(boolean updateMode) {
    this.updateMode = updateMode;
  }

}
