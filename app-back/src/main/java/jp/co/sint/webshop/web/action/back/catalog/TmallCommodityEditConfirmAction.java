package jp.co.sint.webshop.web.action.back.catalog;



import java.util.List;

import jp.co.sint.webshop.data.dao.BrandDao;
import jp.co.sint.webshop.data.dao.TmallPropertyValueDao;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.TmallPropertyValue;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallPropertyBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallPropertyValueBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommodityEditConfirmAction extends TmallCommodityEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  private boolean validationResult = false;

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    validationResult = super.validate();
//    if(getBean().getHeader().getCombinationAmount()==0){
//      addErrorMessage("组合数量输入不正确");
//      validationResult &= false;
//    }
    if(!getBean().isPagePd()){
      CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
      List<CCommodityHeader> listc=catalogService.getCCommodityHeaderByOriginalCommodityCode(getBean().getCommodityCode());
      if(listc.size()>0){
        addErrorMessage("该编号已存在。");
        validationResult &= false;
      }
    }
    if(StringUtil.hasValue(getBean().getHeader().getOriginalCommodityCode())){
      if(StringUtil.isNullOrEmpty(getBean().getHeader().getCombinationAmount())){
        addErrorMessage("组合数量必须输入。");
        validationResult &= false;
      }
    }
    
    if(!getBean().getHeader().getImportCommodityTypeEc().equals("1") && !getBean().getHeader().getImportCommodityTypeEc().equals("2") && !getBean().getHeader().getImportCommodityTypeEc().equals("3") && !getBean().getHeader().getImportCommodityTypeEc().equals("9")){
      addErrorMessage("进口商品区分不能为空");
      validationResult &= false;
    }
    
    if(!getBean().getHeader().getClearCommodityTypeEc().equals("1") && !getBean().getHeader().getClearCommodityTypeEc().equals("9") ){
      addErrorMessage("清仓商品区分不能为空");
      validationResult &= false;
    }
    
    /**
     * 校验输入的品牌编码是否正确
     */
    if (getBean().getHeader().getBrandCode() != null&&!"".equals(getBean().getHeader().getBrandCode())) {
      BrandDao brandDao = DIContainer.getDao(BrandDao.class);
      Brand brand = brandDao.load(getBean().getShopCode(), getBean().getHeader().getBrandCode());
      if(brand==null){
        addErrorMessage(WebMessage.get(ActionErrorMessage.C_COMMODITY_HEADER_EDIT_BRANDCODE_ERROR,
            Messages.getString("web.bean.back.catalog.TmallCommodityEditBean.0")));
        validationResult &= false;
      }
    }
    
    if (!StringUtil.isNullOrEmpty(getBean().getCommodityDiscountPrice()) ) {
      if (Double.parseDouble(getBean().getCommodityDiscountPrice()) > Double.parseDouble(getBean().getHeader().getRepresentSkuUnitPrice().toString())) {
        addErrorMessage("官网特价大于代表SKU单价");
        validationResult &= false;
      } 
    }
    
    /*
    if (!StringUtil.isNullOrEmpty(getBean().getCommodityTmallDiscountPrice())) {
      if (Double.parseDouble(getBean().getCommodityTmallDiscountPrice()) > Double.parseDouble(getBean().getHeader().getRepresentSkuUnitPrice().toString())) {
        addErrorMessage("淘宝特价大于代表SKU单价");
        validationResult &= false;
      } 
    }
    */
    
    /**
     * 设置属性值的名称(效验属性和属性值)
     */
    TmallPropertyValueDao valueDao = DIContainer.getDao(TmallPropertyValueDao.class);
    for (TmallPropertyBean pro:getBean().getCommodityPropertysList()) {
      validationResult &= validateProperty(pro,valueDao);
    }
    for (TmallPropertyBean pro:getBean().getCommodityStandardPopList()) {
      validationResult &= validateProperty(pro,valueDao);
    }
    return validationResult;
  }
  private boolean validateProperty(TmallPropertyBean pro,TmallPropertyValueDao valueDao){
    boolean pass = true;
    ValidationSummary stockSummary = BeanValidator.validate(pro);
    if(stockSummary.hasError()){
      
    }
    if (pro.getValues() == null || pro.getValues().size() == 0) {
      addErrorMessage(pro.getPropertyName()+"必须输入");
      pass &= false;
    }
    for (TmallPropertyValueBean value : pro.getValues()) {
      
      if(!StringUtil.hasValue(value.getValueId())){
        addErrorMessage(pro.getPropertyName()+"必须输入");
        return false;
      }
      if (value.getValueName() == null || "".equals(value.getValueName())) {
        TmallPropertyValue v = valueDao.load(pro.getCategoryId(), pro.getPropertyId(), value.getValueId());
        if (v != null && v.getValueName() != null) {
          value.setValueName(v.getValueName());
        } else {
          addErrorMessage(pro.getPropertyName()+"必须输入");
          pass &= false;
        }
      }
    }
    return pass;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

//    CategoryViewUtil util = new CategoryViewUtil();
    TmallCommodityEditBean reqBean = getBean();
    reqBean.setShowMode("confirm");
    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = reqBean.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }
    reqBean.setShopCode(shopCode);
    
    // 生成属性与属性值集合
//    PropertyKeys keys = buildPropertyKeys();
//    keys.clearNullElement();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodity = catalogService.getCCommodityInfo(getLoginInfo().getShopCode(), reqBean.getOldCommodityCode());
    
//    catalogService.findValueName(NumUtil.toString(reqBean.getHeader().getTcategoryId()), keys);
//    reqBean.setPropertyKeys(keys);
//    String confirmHtml = util.buildConfirmHTML(keys);
//    reqBean.setPropertyHtml(confirmHtml);
//    if (reqBean.getMode().equals(MODE_NEW)) {
//
//      // 重複チェック
//      if (commodity != null) {
//        addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
//            .getString("web.action.back.catalog.CommodityEditConfirmAction.0")));
//        validationResult &= false;
//      }
//
//    } else if (reqBean.getMode().equals(MODE_UPDATE)) {
      // 存在チェック
      if (commodity == null || commodity.getCheader() == null || commodity.getCdetail() == null) {
        setNextUrl("/app/catalog/commodity_list/init/nodata");
        return BackActionResult.RESULT_SUCCESS;
      }

      String[] args = getRequestParameter().getPathArgs();
      if (args != null && args.length > 0) {
        // URLパラメータに従い、遷移先を制御
        String nextUrl = "/app/catalog/tmall_commodity_sku";
        setNextUrl(nextUrl);
      }
//    }
    setRequestBean(reqBean);
    if (validationResult) { // エラーがひとつも存在しなければ確認メッセージを表示する
      addWarningMessage(WebMessage.get(InformationMessage.REGISTER_CONFIRM));
    }
    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    TmallCommodityEditBean reqBean = (TmallCommodityEditBean) getRequestBean();

    if (validationResult) {
      reqBean.setCommodityCodeDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
      reqBean.setCommodityEditDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
      reqBean.setDisplayNextButton(false);
      reqBean.setDisplayPreviewButton(false);
      if (reqBean.getMode().equals(MODE_NEW)) {
        reqBean.setDisplayRegisterButton(true);
        reqBean.setDisplayUpdateButton(false);
        reqBean.setDisplayCancelButton(true);
      } else if (reqBean.getMode().equals(MODE_UPDATE)) {
        reqBean.setDisplayRegisterButton(false);
        reqBean.setDisplayUpdateButton(true);
        reqBean.setDisplayCancelButton(true);
      } else {
        reqBean.setDisplayNextButton(false);
        reqBean.setDisplayRegisterButton(false);
        reqBean.setDisplayUpdateButton(false);
        reqBean.setDisplayCancelButton(false);
      }
      
     if(!reqBean.isPagePd()){
        reqBean.setDisplayNextButton(false);
        reqBean.setDisplayRegisterButton(false);
        reqBean.setDisplayUpdateButton(true);
        reqBean.setDisplayCancelButton(true);
      }
    } else {
      reqBean = getBean();
    }

    if(validationResult){
      reqBean.setDisplayMoveSkuButton(false);
    }
    

    setRequestBean(reqBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditConfirmAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104022003";
  }

}
