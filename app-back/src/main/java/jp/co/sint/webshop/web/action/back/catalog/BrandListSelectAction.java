package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.dto.Brand; 
import jp.co.sint.webshop.service.catalog.BrandSearchCondition; 
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.BrandListBean; 
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages; 

/**
 * U1040410:タグマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BrandListSelectAction extends BrandListBaseAction {

  private String brandCode;

  /**
   * 初期処理を実行します<BR>
   * セッションからログイン情報を取得します<BR>
   * 画面で選択されたタグコードを取得します
   */
  @Override
  public void init() {
    String[] param = getRequestParameter().getPathArgs();
    if (param.length == 1) {
      brandCode = param[0];
    } else {
      brandCode = "";
    }
  }

  /**
   * 認証処理を実行します<BR>
   * 更新権限があることをチェックします
   */
  @Override
  public boolean authorize() {
    return true;
  }

  /**
   * Validationチェックを実行します<BR>
   */
  @Override
  public boolean validate() {
    if (StringUtil.hasValue(brandCode)) {
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    return false;
  }

  /**
   * 画面で選択されたタグコードに関連付いているタグ情報を取得します<BR>
   * 
   * @return アクションの処理結果
   */
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

    // nextBeanのセット
    setRequestBean(reqBean);

    reqBean.setSearchResultTableDisplayFlg(false);
    reqBean.setSearchTableDisplayFlg(false);
    reqBean.setUpdateButtonDisplayFlg(false);
    reqBean.setRegisterButtonDisplayFlg(true);
    reqBean.setRegisterTableDisplayFlg(true);
//    // 设置编辑属性
//    reqBean.setMode(WebConstantCode.DISPLAY_EDIT);
    return BackActionResult.RESULT_SUCCESS;
  }

  

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.BrandListSelectAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104091008";
  }

}
