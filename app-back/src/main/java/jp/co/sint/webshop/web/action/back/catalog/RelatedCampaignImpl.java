package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CampaignCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.RelatedBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseEvent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * キャンペーン関連付けのクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedCampaignImpl extends RelatedBase {

  /**
   * 指定のキャンペーンに商品を関連付けます<BR>
   */
  @Override
  public ServiceResult register(RelatedBean reqBean) {

    // 画面のヘッダー情報コードの設定
    setPictureCode(reqBean.getCampaignCode());

    // 登録データの生成
    CampaignCommodity campaignCommodity = new CampaignCommodity();
    campaignCommodity.setShopCode(reqBean.getSearchShopCode());
    campaignCommodity.setCampaignCode(reqBean.getEffectualCode());
    campaignCommodity.setCommodityCode(reqBean.getEdit().getCommodityCode());

    // 関連付け登録サービスの実行
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    return service.insertRelatedCampaign(campaignCommodity);
  }

  /**
   * 指定の商品をキャンペーンの関連付けから外します<BR>
   * 削除対象の商品を1件ずつ処理し、1件ずつcommitを行います<BR>
   * 例外発生時は、rollbackを行いません。
   */
  @Override
  public ServiceResult delete(RelatedBean reqBean, String[] values) {

    // 画面のヘッダー情報コードの設定
    setPictureCode(reqBean.getCampaignCode());

    ServiceResult result = null;
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    for (String commodityCode : values) {
      // 関連付け情報の削除処理を実行
      result = service.deleteRelatedCampaign(reqBean.getSearchShopCode(), reqBean.getEffectualCode(), commodityCode);

      if (result.hasError()) {
        // 例外発生時はサービスの戻り値をそのまま呼び出し元へ返すのみとし、
        // 実際の例外処理は呼び出しもとのアクションで行う
        break;
      }
    }

    return result;
  }

  /**
   * 画面で指定した検索条件にしたがって、指定のキャンペーンに関連づいている商品の一覧を取得します<BR>
   */
  @Override
  public SearchResult<RelatedBaseEvent> search(RelatedBean relatedBean, RelatedSearchConditionBaseEvent condithion) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    // 画面名の設定
    relatedBean.setPictureName(Messages.getString("web.action.back.catalog.RelatedCampaignImpl.0"));

    // 指定されたキャンペーンコードをキーとしてキャンペーン情報を取得
    Campaign campaign = communicationService.getCampaign(relatedBean.getSearchShopCode(), getEffectualCode());

    SearchResult<RelatedBaseEvent> searchResult = null;

    if (campaign == null) {
      return null;

    } else {
      // 取得したキャンペーン情報を設定
      relatedBean.setCampaignCode(getEffectualCode());
      relatedBean.setCampaignName(campaign.getCampaignName());
      relatedBean.setMemo(campaign.getMemo());
      relatedBean.setCampaignDate(DateUtil.toDateString(campaign.getCampaignStartDate()) + " - "
          + DateUtil.toDateString(campaign.getCampaignEndDate()));

      // 画面で入力された検索条件を元に、検索処理を実行
      CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
      searchResult = service.getRelatedCampaignCommoditySearchBaseEvent(condithion);

      // 検索結果をリストに設定
      List<RelatedBaseEvent> relatedList = searchResult.getRows();

      // 各商品が現在適用されているキャンペーンのキャンペーンコードを設定
      for (RelatedBaseEvent rb : relatedList) {
        Campaign appliedCampaign = service.getAppliedCampaignInfo(relatedBean.getSearchShopCode(), rb.getCommodityCode());
        if (appliedCampaign != null) {
          rb.setAppliedCampaign(appliedCampaign.getCampaignCode());
        }
      }

      return searchResult;
    }
  }

  /**
   * 更新処理を実行します。
   */
  @Override
  public ServiceResult update(RelatedBean reqBean, String[] values) {
    return null;
  }

}
