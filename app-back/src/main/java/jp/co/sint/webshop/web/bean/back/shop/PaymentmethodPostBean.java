package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050530:金融機関設定のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodPostBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PostDetail registerPost = new PostDetail();

  private PostDetail deletePost = new PostDetail();

  private List<PostDetail> postList = new ArrayList<PostDetail>();

  private boolean buttonDisplayFlg;

  private String displayMode;

  private String shopCode;

  private String paymentMethodNo;

  private String processMode;

  private boolean deleteButtonDisplayFlg;

  public static class PostDetail implements Serializable{    

    /** serial version uid */
    private static final long serialVersionUID = 1L;
        
    private Date updatedDatetime;
    
    @Required
    @Length(7)
    @Digit(allowNegative = false)
    @Metadata(name = "商户客户号")
    private String postAccountNo;
    
    @Required
    @Length(40)
    @Metadata(name = "收款人姓名")
    private String postAccountName;
    
    private List<CodeAttribute> accountTypeList = new ArrayList<CodeAttribute>();
    
    /**
     * accountTypeListを取得します。
     * 
     * @return accountTypeList
     */
    public List<CodeAttribute> getAccountTypeList() {
      return accountTypeList;
    }

    /**
     * accountTypeListを設定します。
     * 
     * @param accountTypeList
     *          accountTypeList
     */
    public void setAccountTypeList(List<CodeAttribute> accountTypeList) {
      this.accountTypeList = accountTypeList;
    }
      
  /**
  *
  * @return postAccountNo
  */
 public String getPostAccountNo() {
   return postAccountNo;
 }
 public void setPostAccountNo(String postAccountNo){
   this.postAccountNo = postAccountNo;
 }
  
 /**
  * 
  * 
  */
 public String getPostAccountName(){
   return postAccountName;
 }
 
 public void setPostAccountName(String postAccountName){
   this.postAccountName = postAccountName;
 }

    /**
     * updatedDatetimeを取得します。
     * 
     * @return updatedDatetime
     */
    public Date getUpdatedDatetime() {
      return DateUtil.immutableCopy(updatedDatetime);
    }
    
    /**
     * updatedDatetimeを設定します。
     * 
     * @param updatedDatetime
     *          updatedDatetime
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }
    }
  
  public List<PostDetail> getPostList() {
    return postList;
  }

  /**
   * registerPostを取得します。
   * 
   * @return registerPost
   */
  public PostDetail getRegisterPost() {
    return registerPost;
  }

  /**
   * PostListを設定します。
   * 
   * @param postList
   *          postList
   */
  public void setPostList(List<PostDetail> postList) {
    this.postList = postList;
  }

  /**
   * registerPostを設定します。
   * 
   * @param registerPost
   *          registerPost
   */
  public void setRegisterPost(PostDetail registerPost) {
    this.registerPost = registerPost;
  }

  /**
   * buttonDisplayFlgを取得します。
   * 
   * @return buttonDisplayFlg
   */
  public boolean getButtonDisplayFlg() {
    return buttonDisplayFlg;
  }

  /**
   * buttonDisplayFlgを設定します。
   * 
   * @param buttonDisplayFlg
   *          buttonDisplayFlg
   */
  public void setButtonDisplayFlg(boolean buttonDisplayFlg) {
    this.buttonDisplayFlg = buttonDisplayFlg;
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
   * paymentMethodNoを取得します。
   * 
   * @return paymentMethodNo
   */
  public String getPaymentMethodNo() {
    return paymentMethodNo;
  }

  /**
   * paymentMethodNoを設定します。
   * 
   * @param paymentMethodNo
   *          paymentMethodNo
   */
  public void setPaymentMethodNo(String paymentMethodNo) {
    this.paymentMethodNo = paymentMethodNo;
  }

  /**
   * deletePostを取得します。
   * 
   * @return deletePost
   */
  public PostDetail getDeletePost() {
    return deletePost;
  }

  /**
   * deletePostを設定します。
   * 
   * @param deletePost
   *          deletePost
   */
  public void setDeletePost(PostDetail deletePost) {
    this.deletePost = deletePost;
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
    deletePost.setPostAccountNo(reqparam.get("deletePostAccountNo"));
    deletePost.setPostAccountName(reqparam.get("deletePostAccountName"));
    registerPost.setPostAccountNo(reqparam.get("postAccountNo"));
    registerPost.setPostAccountName(reqparam.get("postAccountName"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050531";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.PaymentmethodPostBean.0");
  }

  /**
   * processModeを取得します。
   * 
   * @return processMode
   */
  public String getProcessMode() {
    return processMode;
  }

  /**
   * processModeを設定します。
   * 
   * @param processMode
   *          processMode
   */
  public void setProcessMode(String processMode) {
    this.processMode = processMode;
  }

  /**
   * deleteButtonDisplayFlgを取得します。
   * 
   * @return deleteButtonDisplayFlg
   */
  public boolean isDeleteButtonDisplayFlg() {
    return deleteButtonDisplayFlg;
  }

  /**
   * deleteButtonDisplayFlgを設定します。
   * 
   * @param deleteButtonDisplayFlg
   *          deleteButtonDisplayFlg
   */
  public void setDeleteButtonDisplayFlg(boolean deleteButtonDisplayFlg) {
    this.deleteButtonDisplayFlg = deleteButtonDisplayFlg;
  }

}
