package jp.co.sint.webshop.web.action.back.catalog;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.RelatedCommodityA;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.RelatedBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseEvent;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent; // 10.1.1 10031 追加
import jp.co.sint.webshop.service.result.ServiceResultImpl; // 10.1.1 10031 追加
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean.RelatedDetailBean;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * 商品関連付けのクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedCommodityAImpl extends RelatedBase {

  /**
   * 商品と商品を関連付けます<BR>
   */
  @Override
  public ServiceResult register(RelatedBean reqBean) {
    // 画面のヘッダー情報コードの設定
    setPictureCode(reqBean.getCommodityCode());

    // 登録データの生成
    RelatedCommodityA relatedCommodityA = new RelatedCommodityA();
    relatedCommodityA.setShopCode(reqBean.getSearchShopCode());
    relatedCommodityA.setCommodityCode(reqBean.getEffectualCode());
    relatedCommodityA.setLinkCommodityCode(reqBean.getEdit().getCommodityCode());
    if (StringUtil.hasValue(reqBean.getEdit().getDisplayOrder())) {
      relatedCommodityA.setDisplayOrder(Long.parseLong(reqBean.getEdit().getDisplayOrder()));
    }

    // 関連付け登録サービスの実行
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    return service.insertRelatedCommodityA(relatedCommodityA);

  }

  /**
   * 商品同士の関連付けを外します<BR>
   * 削除対象の商品を1件ずつ処理し、1件ずつcommitを行います<BR>
   * 例外発生時は、rollbackを行いません。
   */
  @Override
  public ServiceResult delete(RelatedBean reqBean, String[] values) {

    Logger logger = Logger.getLogger(this.getClass());

    // 画面のヘッダー情報コードの設定
    setPictureCode(reqBean.getCommodityCode());

    ServiceResult result = null;
    try {
      CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
      for (String commodityCode : values) {
        // 関連付け情報の削除処理を実行
        result = service.deleteRelatedCommodityA(reqBean.getSearchShopCode(), reqBean.getCommodityCode(), commodityCode, reqBean
            .isInteractiveDeleteFlg());
        if (result.hasError()) {
          // 例外発生時はサービスの戻り値をそのまま呼び出し元へ返すのみとし、
          // 実際の例外処理は呼び出しもとのアクションで行う
          break;
        }
      }
    } catch (Exception e) {
      logger.error(e);
      throw new RuntimeException(e);
    }
    return result;
  }

  /**
   * 画面で指定した検索条件にしたがって、指定の商品に関連づいている商品の一覧を取得します<BR>
   */
  @Override
  public SearchResult<RelatedBaseEvent> search(RelatedBean relatedBean, RelatedSearchConditionBaseEvent condithion) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    // 画面名の設定
    relatedBean.setPictureName(Messages.getString("web.action.back.catalog.RelatedCommodityAImpl.0")); //$NON-NLS-1$

    // 指定された商品コードをキーとして商品情報を取得
    CommodityHeader commodityHeader = service.getCommodityHeader(relatedBean.getSearchShopCode(), getEffectualCode());

    if (commodityHeader == null) {
      return null;

    } else {
      // 取得した商品情報の設定
      relatedBean.setCommodityCode(getEffectualCode());
      relatedBean.setCommodityName(commodityHeader.getCommodityName());

      // 画面で入力された検索条件を元に、検索処理を実行
      SearchResult<RelatedBaseEvent> searchResult = service.getRelatedCommodityASearchBaseEvent(condithion);

      // 検索結果をリストに設定
      List<RelatedBaseEvent> relatedList = searchResult.getRows();

      // 商品の関連が双方向/一方向のいずれかを設定
      // true:双方向 false:一方向
      for (int i = 0; i < relatedList.size(); i++) {
        RelatedCommodityA relatedCommodityA = service.getRelatedCommodityA(relatedList.get(i).getShopCode(), relatedList.get(i)
            .getCommodityCode(), relatedBean.getCommodityCode());

        if (relatedCommodityA == null) {
          relatedList.get(i).setInteraction(false);
        } else {
          relatedList.get(i).setInteraction(true);
        }
      }

      return searchResult;
    }
  }

  /**
   * 画面で指定した検索条件にしたがって、商品の関連付けを更新します<BR>
   */
  @Override
  public ServiceResult update(RelatedBean reqBean, String[] values) {

    ServiceResult serviceResult = null;

    // 画面のチェックボックスでチェックされた商品コードの一覧を取得
    Set<String> setValues = new HashSet<String>();
    for (int i = 0; i < values.length; i++) {
      setValues.add(values[i]);
    }

    // 画面に表示されている商品コードの一覧を全件取得
    List<RelatedDetailBean> relatedDetailBean = reqBean.getList();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    // リストで取得した表示順を一括で更新
    for (RelatedDetailBean rd : relatedDetailBean) {
      RelatedCommodityA relatedCommodityA = service.getRelatedCommodityA(rd.getShopCode(), reqBean.getCommodityCode(), rd
          .getCommodityCode());
      
      // 10.1.1 10031 追加 ここから
      if (relatedCommodityA == null) {
        serviceResult = new ServiceResultImpl();
        serviceResult.getServiceErrorList().add(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }
      // 10.1.1 10031 追加 ここまで
      
      // 更新情報の設定
      RelatedCommodityA updateBean = new RelatedCommodityA();
      updateBean.setShopCode(relatedCommodityA.getShopCode());
      updateBean.setCommodityCode(relatedCommodityA.getCommodityCode());
      updateBean.setLinkCommodityCode(relatedCommodityA.getLinkCommodityCode());
      updateBean.setDisplayOrder(NumUtil.toLong(rd.getDisplayOrder(), null));
      updateBean.setOrmRowid(relatedCommodityA.getOrmRowid());
      updateBean.setCreatedUser(relatedCommodityA.getCreatedUser());
      updateBean.setCreatedDatetime(relatedCommodityA.getCreatedDatetime());
      updateBean.setUpdatedUser(relatedCommodityA.getUpdatedUser());
      updateBean.setUpdatedDatetime(rd.getUpdatedDatetime());

      // 更新サービスの実行
      serviceResult = service.updateRelatedCommodityA(updateBean);

    }

    return serviceResult;
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
   * isExistかどうかを返します。
   * @param reqBean
   * @return isExistのときはfalse
   */
  public boolean isNotExist(RelatedBean reqBean) {
    return !isExist(reqBean);
  }

}
