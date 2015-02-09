package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.dto.Brand; 
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.BrandSearchCondition;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.BrandListBean; 
import jp.co.sint.webshop.web.text.back.Messages; 
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040410:タグマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BrandListMoveAction extends BrandListBaseAction {

  private String shopCode;

  private String brandCode;

  /**
   * 初期処理を実行します<BR>
   * セッションからログイン情報を取得します<BR>
   * 画面で選択されたタグコードを取得します
   */
  public void init() {
    String[] param = getRequestParameter().getPathArgs();
    if (param.length == 2) {
      setShopCode(param[0]);
      setBrandCode(param[1]);
    } else {
      setShopCode("");
      setBrandCode("");
    }
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    boolean authorization = true;

    if (getLoginInfo().isShop()) {
      authorization &= this.shopCode.equals(getLoginInfo().getShopCode());
    }

    return authorization;

  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    boolean result = false;

    // URLパラメータにショップコードとタグコードが存在しない場合はエラーとする
    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(brandCode)) {
      result = false;
      addWarningMessage(Messages.getString("web.action.back.catalog.BrandListMoveAction.0"));
    } else {
      result = true;
    }
    return result;
  }

  /**
   * タグ関連付け画面へ遷移します<BR>
   */
  @Override
  public WebActionResult callService() {

    BrandListBean reqBean = getBean();

    // ショップ管理者、または一店舗版の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 検索条件の設定
    BrandSearchCondition condition = new BrandSearchCondition();
    condition.setShopCode(reqBean.getSearchShopCode());
    condition.setSearchBrandCode(reqBean.getSearchBrandCode());
    condition.setSearchBrandName(reqBean.getSearchBrandName());
    condition.setSearchBrandEnglishName(reqBean.getSearchBrandEnglishName());
    //20120515 tuxinwei add start
    condition.setSearchBrandJapaneseName(reqBean.getSearchBrandJapaneseName());
    //20120515 tuxinwei add end
    condition.setPageSize(reqBean.getPagerValue().getPageSize());
    condition.setCurrentPage(reqBean.getPagerValue().getCurrentPage());
    condition.setMaxFetchSize(reqBean.getPagerValue().getMaxFetchSize());
    
    // タグ一覧の取得
    List<Brand> BrandList = getBrandList(reqBean, condition);

    // 画面表示用Beanを生成
    createSelectNextBean(reqBean, BrandList, brandCode);
    reqBean.setMode(WebConstantCode.DISPLAY_READONLY);
    // nextBeanのセット
    setRequestBean(reqBean);
    //upd by lc 2012-04-18 start
    reqBean.setSearchResultTableDisplayFlg(false);
    reqBean.setSearchTableDisplayFlg(false);
    reqBean.setUpdateButtonDisplayFlg(false);
    reqBean.setRegisterButtonDisplayFlg(false);
    reqBean.setRegisterTableDisplayFlg(true);
    
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      reqBean.setDisplayNextButton(true);
      reqBean.setBrandEditDisplayMode(WebConstantCode.DISPLAY_EDIT);
    }else{
      reqBean.setMode(WebConstantCode.DISPLAY_HIDDEN);
      reqBean.setBrandEditDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
      reqBean.setDisplayNextButton(false);
    }
    //upd by lc 2012-04-18 end    
    return BackActionResult.RESULT_SUCCESS;

  }

  
  
  
  public String getShopCode() {
    return shopCode;
  }

  
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  
  public String getBrandCode() {
    return brandCode;
  }

  
  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.BrandListMoveAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104091004";
  }

}
