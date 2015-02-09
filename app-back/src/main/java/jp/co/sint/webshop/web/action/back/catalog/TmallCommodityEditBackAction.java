package jp.co.sint.webshop.web.action.back.catalog;


import jp.co.sint.webshop.data.dao.BrandDao;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommodityEditBackAction extends TmallCommodityEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    TmallCommodityEditBean reqBean = getBean();
    
    if(reqBean.getHeader().getCommodityType().equals(CommodityType.GENERALGOODS.longValue())){
      String categoryId = reqBean.getCategoryCode();
      reqBean.getHeader().setTcategoryId(NumUtil.toLong(categoryId));
      if(!StringUtil.hasValue(reqBean.getCommodityBrandName())){
        BrandDao brandDao = DIContainer.getDao(BrandDao.class);
        Brand brand = brandDao.load(reqBean.getShopCode(), reqBean.getHeader().getBrandCode());
        reqBean.setCommodityBrandName(brand.getBrandName());
      }
    }

    reqBean.setShowMode("edit");
    setRequestBean(reqBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    TmallCommodityEditBean reqBean = (TmallCommodityEditBean) getRequestBean();

    setDisplayControl(reqBean);
    reqBean.setDisplayCancelButton(false);
    setRequestBean(reqBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104022001";
  }

}
