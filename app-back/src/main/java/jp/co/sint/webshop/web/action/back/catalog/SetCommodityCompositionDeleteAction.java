package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dao.SetCommodityCompositionDao;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.SetCommodityCompositionBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 套餐设定のアクションクラスです
 * 
 * @author KS.
 */
public class SetCommodityCompositionDeleteAction extends SetCommodityCompositionInitAction {

  /**
   * 权限确认。
   * 
   * @return 有删除权限时返回true
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_DELETE.isGranted(getLoginInfo());
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
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SetCommodityCompositionBean bean = getBean();
    CommodityInfo info = service.getCommodityInfo(getLoginInfo().getShopCode(), bean.getCommodityCode());
    // 选择套餐明细商品的编号取得
    String[] values = getRequestParameter().getAll("checkBox");
    // チェックボックスが選択されているか
    if (StringUtil.isNullOrEmpty(values[0])) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED, "套餐明细商品"));
      setNextUrl(null);
      this.setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 删除处理
    List<SetCommodityComposition> list = new ArrayList<SetCommodityComposition>();
    BigDecimal price = new BigDecimal(BigDecimal.ZERO.doubleValue());
    for (String childCommodityCode : values) {
      SetCommodityCompositionDao dao = DIContainer.getDao(SetCommodityCompositionDao.class);
      SetCommodityComposition dtoDetail = dao.load(getLoginInfo().getShopCode(), bean.getCommodityCode(),
          childCommodityCode);
      if (dtoDetail != null) {
        price = BigDecimalUtil.add(price, dtoDetail.getRetailPrice());
        list.add(dtoDetail);
      }
    }
    if (list.size() == 0) {
      addErrorMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "套餐明细商品"));
      setRequestBean(bean);
      return super.callService();
    }
    
    info.getHeader().setRepresentSkuUnitPrice(info.getDetail().getUnitPrice().subtract(price));
    info.getDetail().setUnitPrice(info.getDetail().getUnitPrice().subtract(price));
    ServiceResult result = service.deleteSetCommodityComposition(list, info.getHeader(), info.getDetail());
    
    if (result.hasError()) {
      this.addErrorMessage(WebMessage.get(CatalogErrorMessage.DELETE_COMMODITY_ERROR, bean.getSearchCommodityCode()));
      return super.callService();
    }
    
    addErrorMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "套餐明细商品"));
    setRequestBean(bean);
    return super.callService();
  }

  public void prerender() {
    super.prerender();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("套餐明细删除处理");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104019003";
  }

}
