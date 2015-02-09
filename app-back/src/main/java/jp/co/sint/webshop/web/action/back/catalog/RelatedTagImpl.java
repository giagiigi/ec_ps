package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.Tag;
import jp.co.sint.webshop.data.dto.TagCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.RelatedBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseEvent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * タグ関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedTagImpl extends RelatedBase {

  /**
   * 指定のタグに商品を関連付けます
   */
  @Override
  public ServiceResult register(RelatedBean reqBean) {

    // 画面のヘッダー情報コードの設定
    setPictureCode(reqBean.getTagCode());

    // 登録情報の設定
    TagCommodity tagCommodity = new TagCommodity();
    tagCommodity.setShopCode(reqBean.getSearchShopCode());
    tagCommodity.setTagCode(reqBean.getTagCode());
    tagCommodity.setCommodityCode(reqBean.getEdit().getCommodityCode());
    if (reqBean.getEdit().getSortNum() != null) {
      tagCommodity.setSortNum(NumUtil.toLong(reqBean.getEdit().getSortNum()));
    }
    if (reqBean.getEdit().getSortNumJp() != null) {
      tagCommodity.setSortNumJp(NumUtil.toLong(reqBean.getEdit().getSortNumJp()));
    }
    if (reqBean.getEdit().getSortNumEn() != null) {
      tagCommodity.setSortNumEn(NumUtil.toLong(reqBean.getEdit().getSortNumEn()));
    }
    String langStr = null;
    for (String str : reqBean.getEdit().getLang()){
      if(StringUtil.hasValue(str)){
        if (langStr == null) {
          langStr = str;
        } else {
          langStr = langStr + "," + str;
        }
      }
    }
    tagCommodity.setLang(langStr);
    // 関連付け登録サービスの実行
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    return service.insertRelatedTag(tagCommodity);
  }

  /**
   * 指定の商品をタグの関連付けから外します<BR>
   * 削除対象の商品を1件ずつ処理し、1件ずつcommitを行います<BR>
   * 例外発生時は、rollbackを行いません。
   */
  @Override
  public ServiceResult delete(RelatedBean reqBean, String[] values) {

    // 画面のヘッダー情報コードの設定
    setPictureCode(reqBean.getTagCode());

    ServiceResult result = null;
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    for (String commodityCode : values) {
      // 関連付け情報の削除処理を実行
      result = service.deleteRelatedTag(reqBean.getSearchShopCode(), reqBean.getEffectualCode(), commodityCode);

      if (result.hasError()) {
        // 例外発生時はサービスの戻り値をそのまま呼び出し元へ返すのみとし、
        // 実際の例外処理は呼び出しもとのアクションで行う
        break;
      }
    }
    return result;
  }

  /**
   * 画面で指定した検索条件にしたがって、指定のタグに関連づいている商品の一覧を取得します<BR>
   */
  @Override
  public SearchResult<RelatedBaseEvent> search(RelatedBean relatedBean, RelatedSearchConditionBaseEvent condithion) {

    // 画面名の設定
    relatedBean.setPictureName(Messages.getString("web.action.back.catalog.RelatedTagImpl.0")); //$NON-NLS-1$

    // 指定されたタグコードをキーとしてタグ情報を取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    Tag tag = service.getTag(relatedBean.getSearchShopCode(), getEffectualCode());

    if (tag == null) {
      return null;
    } else {
      // 取得したタグ情報の設定
      relatedBean.setTagCode(getEffectualCode());
      relatedBean.setTagName(tag.getTagName());

      // 検索サービスを実行し、指定のタグに関連づいている商品の一覧を取得
      return service.getRelatedTagCommoditySearchBaseEvent(condithion);
    }
  }

  /**
   * 将来的にアップデート処理が必要な場合に実装
   */
  @Override
  public ServiceResult update(RelatedBean reqBean, String[] values) {
    return null;
  }

}
