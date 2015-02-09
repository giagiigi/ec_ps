package jp.co.sint.webshop.web.action.front.cart;
 
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityInfo; 
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.cart.BlanketCartBean.BlanketCartDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.cart.CartDisplayMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U2020310:まとめてカートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BlanketCartUpdateAction extends BlanketCartBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    boolean isEmpty = true;
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    for (BlanketCartDetailBean detail : getBean().getDetailList()) {
      if (detail == null) {
        continue;
      } else {
        isEmpty &= false;
      }

      boolean valid = validateBlanketCartDetailBean(detail);

      if (valid) {
        CommodityInfo info = service.getSkuInfo(detail.getShopCode(), detail.getSkuCode());
        if (info == null) {
          addErrorMessage(WebMessage.get(CartDisplayMessage.SKU_NOT_FOUND, detail.getNo()));
        } else {
            //edit by cs_yuli 20120514 start 
        	UtilService utilService=ServiceLocator.getUtilService(getLoginInfo()); 	
			detail.setCommodityName(utilService.getNameByLanguage(info.getHeader().getCommodityName(),info.getDetail().getStandardDetail1NameEn(),info.getDetail().getStandardDetail1NameJp()));
	        detail.setStandardDetail1Name(utilService.getNameByLanguage(info.getDetail().getStandardDetail1Name(),info.getDetail().getStandardDetail1NameEn(),info.getDetail().getStandardDetail1NameJp()));
	        detail.setStandardDetail2Name(utilService.getNameByLanguage(info.getDetail().getStandardDetail2Name(),info.getDetail().getStandardDetail2NameEn(),info.getDetail().getStandardDetail2NameJp()));
    	   //edit by cs_yuli 20120514 end 
          detail.setDisplayDeleteButton(WebConstantCode.VALUE_TRUE);
        }
      }
    }
    
    if (isEmpty) {
      addErrorMessage(WebMessage.get(CartDisplayMessage.NO_INPUT_SKU));
    }

    getBean().setFirstDisplay(false);
    setRequestBean(getBean());

    return FrontActionResult.RESULT_SUCCESS;
  }
}
