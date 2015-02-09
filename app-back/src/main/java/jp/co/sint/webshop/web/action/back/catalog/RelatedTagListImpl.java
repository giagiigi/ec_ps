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
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseCommodity;
import jp.co.sint.webshop.service.catalog.RelatedTag;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedListBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedListBean.RelatedTagListBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * タグ一括関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedTagListImpl extends RelatedListBase {

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
    List<RelatedTagListBean> relatedTagListBean = relatedList.getRelatedTagList();
    List<RelatedTag> relatedTagList = new ArrayList<RelatedTag>();

    for (RelatedTagListBean list : relatedTagListBean) {
      RelatedTag related = new RelatedTag();
      related.setShopCode(list.getShopCode());
      related.setTagCode(list.getTagCode());
      related.setCommodityCode(relatedList.getCommodityCode());

      // チェックが付いているキャンペーン:登録対象のコード
      // チェックが付いていないキャンペーン:空文字
      if (checkSet.contains(list.getTagCode())) {
        related.setCheckCode(list.getTagCode());
      } else {
        related.setCheckCode("");
      }
      relatedTagList.add(related);
    }

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    return service.registerRelatedTag(relatedTagList);
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

    List<RelatedTagListBean> relatedTagList = new ArrayList<RelatedTagListBean>();
    SearchResult<RelatedTag> tagList = service.getRelatedTagCommoditySearch(condition);
    List<RelatedTag> list = new ArrayList<RelatedTag>();

    if (tagList != null) {
      relatedList.setPagerValue(PagerUtil.createValue(tagList));
      list = tagList.getRows();
    }

    for (RelatedTag rc : list) {
      RelatedTagListBean relatedTag = new RelatedTagListBean();
      if (StringUtil.isNullOrEmpty(rc.getCommodityCode())) {
        // タグ対象商品に関連付けられている商品はチェックボックスにチェックをつけない
        relatedTag.setCheckCode("");
      } else {
        // タグ対象商品に関連付けられている商品はチェックボックスにチェックをつける
        relatedTag.setCheckCode(rc.getTagCode());
      }

      relatedTag.setShopCode(rc.getShopCode());
      relatedTag.setTagCode(rc.getTagCode());
      relatedTag.setTagName(rc.getTagName());
      relatedTagList.add(relatedTag);
    }

    relatedList.setPictureName(Messages.getString("web.action.back.catalog.RelatedTagListImpl.0"));
    relatedList.setRelatedTagList(relatedTagList);

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
