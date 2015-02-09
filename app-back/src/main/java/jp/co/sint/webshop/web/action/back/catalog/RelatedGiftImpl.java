package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.data.dto.GiftCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.RelatedBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseEvent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * ギフト関連付けのクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedGiftImpl extends RelatedBase {

  /**
   * 指定のギフトに商品を関連付けます<BR>
   */
  @Override
  public ServiceResult register(RelatedBean reqBean) {

    // 画面のヘッダー情報コードの設定
    setPictureCode(reqBean.getGiftCode());

    // 登録データの生成
    GiftCommodity giftCommodity = new GiftCommodity();
    giftCommodity.setShopCode(reqBean.getSearchShopCode());
    giftCommodity.setGiftCode(reqBean.getGiftCode());
    giftCommodity.setCommodityCode(reqBean.getEdit().getCommodityCode());

    // 関連付け登録サービスの実行
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    return service.insertRelatedGift(giftCommodity);
  }

  /**
   * 指定の商品をギフトの関連付けから外します<BR>
   * 削除対象の商品を1件ずつ処理し、1件ずつcommitを行います<BR>
   * 例外発生時は、rollbackを行いません。
   */
  @Override
  public ServiceResult delete(RelatedBean reqBean, String[] values) {

    // 画面のヘッダー情報コードの設定
    setPictureCode(reqBean.getGiftCode());

    ServiceResult result = null;
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    for (String commodityCode : values) {

      // 関連付け情報の削除処理を実行
      result = service.deleteRelatedGift(reqBean.getSearchShopCode(), reqBean.getEffectualCode(), commodityCode);

      if (result.hasError()) {
        // 例外発生時はサービスの戻り値をそのまま呼び出し元へ返すのみとし、
        // 実際の例外処理は呼び出しもとのアクションで行う
        break;
      }
    }
    return result;
  }

  /**
   * 画面で指定した検索条件にしたがって、指定のギフトに関連づいている商品の一覧を取得します<BR>
   */
  @Override
  public SearchResult<RelatedBaseEvent> search(RelatedBean relatedBean, RelatedSearchConditionBaseEvent condithion) {

    // 画面名の設定
    relatedBean.setPictureName(Messages.getString("web.action.back.catalog.RelatedGiftImpl.0")); //$NON-NLS-1$

    // 指定されたギフトコードをキーとしてギフト情報を取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    Gift gift = service.getGift(relatedBean.getSearchShopCode(), getEffectualCode());

    if (gift == null) {
      return null;

    } else {

      // 取得したギフト情報を設定
      relatedBean.setGiftCode(getEffectualCode());
      relatedBean.setGiftName(gift.getGiftName());
      relatedBean.setGiftDescription(gift.getGiftDescription());
      relatedBean.setGiftPrice(NumUtil.toString(gift.getGiftPrice()));
      relatedBean.setGiftTaxType(NumUtil.toString(gift.getGiftTaxType()));

      // 検索サービスを実行し、指定のギフトに関連づいている商品の一覧を取得
      return service.getRelatedGiftCommoditySearchBaseEvent(condithion);
    }
  }

  /**
   * 
   */
  @Override
  public ServiceResult update(RelatedBean reqBean, String[] values) {
    return null;
  }

}
