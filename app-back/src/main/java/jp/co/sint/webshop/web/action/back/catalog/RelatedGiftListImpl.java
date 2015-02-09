package jp.co.sint.webshop.web.action.back.catalog;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.RelatedGift;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseCommodity;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedListBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedListBean.RelatedGiftListBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * ギフト一覧関連付けのクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedGiftListImpl extends RelatedListBase {

  /**
   * 登録処理を実行します。
   * 
   * @param relatedList
   * @param values
   * @return サービスの処理結果
   */
  public ServiceResult register(RelatedListBean relatedList, String[] values) {

    // 画面上でチェックされたコードの一覧を取得する
    Set<String> checkSet = new HashSet<String>();
    for (int i = 0; i < values.length; i++) {
      checkSet.add(values[i]);
    }

    // チェックされたコードと、リストで持っているコードを比較する
    // ハッシュセット内に存在すれば登録、存在しなければ削除とする
    List<RelatedGiftListBean> relatedGiftListBean = relatedList.getRelatedGiftList();
    List<RelatedGift> relatedGiftList = new ArrayList<RelatedGift>();

    for (RelatedGiftListBean list : relatedGiftListBean) {
      RelatedGift related = new RelatedGift();
      related.setShopCode(list.getShopCode());
      related.setGiftCode(list.getGiftCode());
      related.setCommodityCode(relatedList.getCommodityCode());

      // チェックが付いているキャンペーン:登録対象のコード
      // チェックが付いていないキャンペーン:空文字
      if (checkSet.contains(list.getGiftCode())) {
        related.setCheckCode(list.getGiftCode());
      } else {
        related.setCheckCode("");
      }
      relatedGiftList.add(related);
    }

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    return service.registerRelatedGift(relatedGiftList);

  }

  /**
   * isExistかどうかを返します。
   * 
   * @param reqListBean
   * @return isExistのときはtrue
   */
  public boolean isExist(RelatedListBean reqListBean) {
    // 受け取った商品コードが存在するかをチェックする
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = service.getCommodityHeader(reqListBean.getSearchShopCode(), reqListBean.getCommodityCode());

    return commodityHeader != null;
  }

  /**
   * isNotExistかどうかを返します。
   * 
   * @param reqBean
   * @return isNotExistのときはtrue
   */
  public boolean isNotExist(RelatedBean reqBean) {
    return !isExist(reqBean);
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

    List<RelatedGiftListBean> relatedGiftList = new ArrayList<RelatedGiftListBean>();
    SearchResult<RelatedGift> giftList = service.getRelatedGiftCommoditySearch(condition);
    List<RelatedGift> list = new ArrayList<RelatedGift>();

    if (giftList != null) {
      relatedList.setPagerValue(PagerUtil.createValue(giftList));
      list = giftList.getRows();
    }

    for (RelatedGift rc : list) {
      RelatedGiftListBean relatedGift = new RelatedGiftListBean();
      if (StringUtil.isNullOrEmpty(rc.getCommodityCode())) {
        // ギフト対象商品に関連付けられている商品はチェックボックスにチェックをつけない
        relatedGift.setCheckCode("");
      } else {
        // ギフト対象商品に関連付けられている商品はチェックボックスにチェックをつける
        relatedGift.setCheckCode(rc.getGiftCode());
      }

      relatedGift.setShopCode(rc.getShopCode());
      relatedGift.setGiftCode(rc.getGiftCode());
      relatedGift.setGiftName(rc.getGiftName());
      relatedGift.setGiftPrice(NumUtil.toString(rc.getGiftPrice()));
      relatedGift.setGiftTaxType(NumUtil.toString(rc.getGiftTaxType()));
      relatedGiftList.add(relatedGift);
    }

    relatedList.setPictureName(Messages.getString("web.action.back.catalog.RelatedGiftListImpl.0"));
    relatedList.setRelatedGiftList(relatedGiftList);

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
