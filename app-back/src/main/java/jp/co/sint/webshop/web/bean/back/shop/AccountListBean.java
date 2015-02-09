package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050910:管理ユーザマスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class AccountListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  private AccountListDetail searchCondition = new AccountListDetail();

  private List<AccountListDetail> searchResult = new ArrayList<AccountListDetail>();

  private AccountListDetail deleteUser = new AccountListDetail();

  private PagerValue pagerValue = new PagerValue();

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
    this.searchCondition.setShopCode(reqparam.get("shopCode"));
    this.searchCondition.setUserLoginId(reqparam.get("userLoginId"));
    this.searchCondition.setUserName(reqparam.get("userName"));

    this.deleteUser.setUserCode(reqparam.get("deleteUserCode"));
    this.deleteUser.setShopCode(reqparam.get("deleteUserShopCode"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050910";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.AccountListBean.0");
  }

  /**
   * U1050910:管理ユーザマスタのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class AccountListDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @AlphaNum2
    @Metadata(name = "ショップコード", order = 1)
    private String shopCode;

    @AlphaNum2
    @Metadata(name = "管理者ID", order = 2)
    private String userLoginId;

    @Metadata(name = "管理ユーザ名", order = 3)
    private String userName;

    private String userCode;

    /**
     * shopCodeを取得します。
     * 
     * @return shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    /**
     * userCodeを取得します。
     * 
     * @return userCode
     */
    public String getUserCode() {
      return userCode;
    }

    /**
     * userLoginIdを取得します。
     * 
     * @return userLoginId
     */
    public String getUserLoginId() {
      return userLoginId;
    }

    /**
     * userNameを取得します。
     * 
     * @return userName
     */
    public String getUserName() {
      return userName;
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
     * userCodeを設定します。
     * 
     * @param userCode
     *          userCode
     */
    public void setUserCode(String userCode) {
      this.userCode = userCode;
    }

    /**
     * userLoginIdを設定します。
     * 
     * @param userLoginId
     *          userLoginId
     */
    public void setUserLoginId(String userLoginId) {
      this.userLoginId = userLoginId;
    }

    /**
     * userNameを設定します。
     * 
     * @param userName
     *          userName
     */
    public void setUserName(String userName) {
      this.userName = userName;
    }

  }

  /**
   * deleteUserを取得します。
   * 
   * @return deleteUser
   */
  public AccountListDetail getDeleteUser() {
    return deleteUser;
  }

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */
  public AccountListDetail getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchResultを取得します。
   * 
   * @return searchResult
   */
  public List<AccountListDetail> getSearchResult() {
    return searchResult;
  }

  /**
   * deleteUserを設定します。
   * 
   * @param deleteUser
   *          deleteUser
   */
  public void setDeleteUser(AccountListDetail deleteUser) {
    this.deleteUser = deleteUser;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(AccountListDetail searchCondition) {
    this.searchCondition = searchCondition;
  }

  /**
   * searchResultを設定します。
   * 
   * @param searchResult
   *          searchResult
   */
  public void setSearchResult(List<AccountListDetail> searchResult) {
    this.searchResult = searchResult;
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue pagerValue
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
   * shopListを取得します。
   * 
   * @return shopList shopList
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

}
