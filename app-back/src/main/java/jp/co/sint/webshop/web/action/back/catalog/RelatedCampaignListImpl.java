package jp.co.sint.webshop.web.action.back.catalog;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.RelatedCampaign;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseCommodity;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedListBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedListBean.RelatedCampaignListBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * キャンペーンリスト関連付けのクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedCampaignListImpl extends RelatedListBase {

  /**
   * 登録処理を実行します。
   * 
   * @param relatedList
   * @param values
   * @return サービスの実行結果
   */
  public ServiceResult register(RelatedListBean relatedList, String[] values) {

    // 画面上でチェックされたコードの一覧を取得する
    Set<String> checkSet = new HashSet<String>();
    for (int i = 0; i < values.length; i++) {
      checkSet.add(values[i]);
    }

    // チェックされたコードと、リストで持っているコードを比較する
    // ハッシュセット内に存在すれば登録、存在しなければ削除とする
    List<RelatedCampaignListBean> relatedCampaignListBean = relatedList.getRelatedCampaignList();
    List<RelatedCampaign> relatedCampaignList = new ArrayList<RelatedCampaign>();

    for (RelatedCampaignListBean list : relatedCampaignListBean) {
      RelatedCampaign related = new RelatedCampaign();
      related.setShopCode(list.getShopCode());
      related.setCampaignCode(list.getCampaignCode());
      related.setCommodityCode(relatedList.getCommodityCode());

      // チェックが付いているキャンペーン:登録対象のコード
      // チェックが付いていないキャンペーン:空文字
      if (checkSet.contains(list.getCampaignCode())) {
        related.setCheckCode(list.getCampaignCode());
      } else {
        related.setCheckCode("");
      }
      relatedCampaignList.add(related);
    }

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    return service.registerRelatedCampaign(relatedCampaignList);
  }

  /**
   * 検索処理を実行します。
   * 
   * @param relatedList
   * @param condition
   * @return relatedList
   */
  public RelatedListBean search(RelatedListBean relatedList, RelatedSearchConditionBaseCommodity condition) {

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    List<RelatedCampaignListBean> relatedCampaignList = new ArrayList<RelatedCampaignListBean>();

    SearchResult<RelatedCampaign> campaignList = service.getRelatedCampaignCommoditySearch(condition);
    List<RelatedCampaign> list = new ArrayList<RelatedCampaign>();
    if (campaignList != null) {
      // ページ情報を追加
      relatedList.setPagerValue(PagerUtil.createValue(campaignList));
      list = campaignList.getRows();
    }

    for (RelatedCampaign rc : list) {
      RelatedCampaignListBean relatedCampaign = new RelatedCampaignListBean();
      if (StringUtil.isNullOrEmpty(rc.getCommodityCode())) {
        // キャンペーン対象商品に関連付けられている商品はチェックボックスにチェックをつけない
        relatedCampaign.setCheckCode("");
      } else {
        // キャンペーン対象商品に関連付けられている商品はチェックボックスにチェックをつける
        relatedCampaign.setCheckCode(rc.getCampaignCode());
      }
      relatedCampaign.setShopCode(rc.getShopCode());
      relatedCampaign.setCampaignCode(rc.getCampaignCode());
      relatedCampaign.setCampaignName(rc.getCampaignName());
      relatedCampaign.setCampaignStartDateTime(DateUtil.toDateString(rc.getCampaignStartDate()));
      relatedCampaign.setCampaignEndDateTime(DateUtil.toDateString(rc.getCampaignEndDate()));
      relatedCampaign.setCampaignDiscountRate(NumUtil.toString(rc.getCampaignDiscountRate()));
      relatedCampaignList.add(relatedCampaign);
    }

    relatedList.setPictureName(Messages.getString("web.action.back.catalog.RelatedCampaignListImpl.0"));
    relatedList.setRelatedCampaignList(relatedCampaignList);

    return relatedList;
  }

  /**
   * CSV取込処理を実行します。
   */
  @Override
  public ServiceResult csvImport(InputStream fileUrl) {
    return null;
  }

}
