package jp.co.sint.webshop.web.action.back.catalog;


import java.math.BigDecimal;

import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.CategoryViewUtil.PropertyKeys;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallCommodityEditHeaderBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommodityEditRegisterAction extends TmallCommodityEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }
  @Override
  public boolean validate() {
    return super.validate();
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    TmallCommodityEditBean reqBean = getBean();
    TmallCommodityEditHeaderBean header = reqBean.getHeader();

    String shopCode = "";

      shopCode = getLoginInfo().getShopCode();

    // DB登録用DTOを生成する

    // DTOに入力値をセット

    // 登録処理
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    
    
    CCommodityHeader cheader = buildHeader(header); 
    if(cheader.getShopCode()==null||"".equals(cheader.getShopCode())){
      cheader.setShopCode(shopCode);
    }
    //初始值设定
    cheader.setRepresentSkuUnitPrice(new BigDecimal(0));
    //ec是否可同期标志
    cheader.setSyncFlagEc(0l);
    //tmall是否可同期标志
    cheader.setSyncFlagTmall(0l);
    cheader.setExportFlagErp(1l);
    cheader.setExportFlagWms(1l);
    cheader.setRepresentSkuCode("000000");
    
    ServiceResult sResult = catalogService.addCcheader(cheader);
    if (sResult.hasError()) {
      setRequestBean(reqBean);
      for (ServiceErrorContent error : sResult.getServiceErrorList()) {
        if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.catalog.TmallCommodityEditRegisterAction.0")));
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    /**
     * 更新商品属性 tmall_commodity_property
     * 
     */
    //获取属性与属性值集合
    PropertyKeys keys = reqBean.getPropertyKeys();
    ServiceResult updatePropertyResult = catalogService.updateCommodityPropertys(NumUtil.toString(cheader.getTmallCategoryId()),reqBean.getCommodityCode(), shopCode, keys);
    if(updatePropertyResult.hasError()){
      setRequestBean(reqBean);
      for (ServiceErrorContent error : updatePropertyResult.getServiceErrorList()) {
        if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.back.catalog.TmallCommodityEditRegisterAction.0")));
        }
      }
    
      return BackActionResult.SERVICE_ERROR;
    }
    // 次画面のBeanを設定する
    setRequestBean(reqBean);

    

    // 完了パラメータを渡して、初期画面へ遷移する
    setNextUrl("/app/catalog/tmall_commodity_edit/select/" + shopCode + "/"
        + reqBean.getCommodityCode() + "/edit" + "/"
        + WebConstantCode.COMPLETE_INSERT);

    return BackActionResult.RESULT_SUCCESS;

  }
  
  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104012007";
  }

}
