package jp.co.sint.webshop.web.action.back.catalog;


import jp.co.sint.webshop.data.domain.CommoditySyncFlag;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommoditySkuBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040140:商品SKUのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommoditySkuSetpropertyAction extends TmallCommoditySkuBaseAction {

 
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
    TmallCommoditySkuBean reqBean = getBean();
    //判断选择是否正确
    String startard1Id = reqBean.getCommodityStandardDetail1Id();
    String startard2Id = reqBean.getCommodityStandardDetail2Id();
    //如果两个属性都没有选择，，直接返回
    if((startard1Id==null||"".equals(startard1Id))&&(startard2Id==null||"".equals(startard2Id))){
      addErrorMessage("必须选择一个属性");
      return false;
    }
    //判断选择的两个属性是否相同
    if(startard1Id!=null&&startard1Id.equals(startard2Id)){
      addErrorMessage("两个属性不能相同");
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    TmallCommoditySkuBean reqBean = getBean();
    
    // 更新処理
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CCommodityHeader header = new CCommodityHeader();
    header.setShopCode(reqBean.getShopCode());
    header.setCommodityCode(reqBean.getParentCommodityCode());
    header.setTmallCategoryId(reqBean.getHeader().getTmallCategoryId());
    header.setStandard1Id(reqBean.getCommodityStandardDetail1Id());
    header.setStandard2Id(reqBean.getCommodityStandardDetail2Id());
    header.setSyncFlagEc(CommoditySyncFlag.CAN_SYNC.longValue());
    header.setSyncFlagTmall(CommoditySyncFlag.CAN_SYNC.longValue());
    
    ServiceResult result = service.updateCheaderStandardDetailInfo(header);
    // サービスエラーの有無をチェック
    if (result.hasError()) {
      return BackActionResult.SERVICE_ERROR;
    }

    // 商品SKU画面へ遷移する
    setNextUrl("/app/catalog/tmall_commodity_sku/init/" + getBean().getParentCommodityCode() + "/" + getBean().getShopCode() + "/"
        + WebConstantCode.COMPLETE_UPDATE);
    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.5");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104014004";
  }

}
