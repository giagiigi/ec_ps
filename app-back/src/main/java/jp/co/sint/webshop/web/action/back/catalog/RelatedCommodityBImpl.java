package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.RelatedBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseEvent;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 商品関連付けのクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedCommodityBImpl extends RelatedBase {

  /**
   */
  @Override
  public ServiceResult register(RelatedBean reqBean) {
    return null;
  }

  /**
   */
  @Override
  public ServiceResult delete(RelatedBean reqBean, String[] values) {
    return null;
  }

  /**
   * 画面で指定した検索条件にしたがって、指定の商品に関連づいている商品の一覧を取得します<BR>
   */
  @Override
  public SearchResult<RelatedBaseEvent> search(RelatedBean relatedBean, RelatedSearchConditionBaseEvent condithion) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    // 画面名の設定
    relatedBean.setPictureName(Messages.getString("web.action.back.catalog.RelatedCommodityBImpl.0"));

    // 指定された商品コードをキーとして商品情報を取得
    CommodityHeader commodityHeader = service.getCommodityHeader(relatedBean.getSearchShopCode(), getEffectualCode());

    if (commodityHeader == null) {
      return null;

    } else {
      // 取得した商品情報の設定
      relatedBean.setCommodityCode(getEffectualCode());
      relatedBean.setCommodityName(commodityHeader.getCommodityName());

      // 画面で入力された検索条件を元に、検索処理を実行
      SearchResult<RelatedBaseEvent> searchResult = service.getRelatedCommodityBSearchBaseEvent(condithion);

      return searchResult;
    }
  }

  /**
   */
  @Override
  public ServiceResult update(RelatedBean reqBean, String[] values) {
    return null;
  }

  /**
   * isExistかどうかを返します。
   * @param reqBean
   * @return isExistのときはtrue
   */
  public boolean isExist(RelatedBean reqBean) {
    // 商品コードが存在するかをチェックする
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = service.getCommodityHeader(reqBean.getSearchShopCode(), getEffectualCode());

    return commodityHeader != null;
  }

  /**
   * isNotExistかどうかを返します。
   * @param reqBean
   * @return isNotExistのときはtrue
   */
  public boolean isNotExist(RelatedBean reqBean) {
    return !isExist(reqBean);
  }

}
