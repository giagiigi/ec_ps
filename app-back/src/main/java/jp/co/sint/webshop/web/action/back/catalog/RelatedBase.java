package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.RelatedBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseEvent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

/**
 * 関連付けのアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class RelatedBase {

  private BackLoginInfo loginInfo;

  private String pictureCode;

  private String effectualCode;

  abstract SearchResult<RelatedBaseEvent> search(RelatedBean relatedBean, RelatedSearchConditionBaseEvent condithion);

  abstract ServiceResult register(RelatedBean reqBean);

  abstract ServiceResult delete(RelatedBean reqBean, String[] values);

  abstract ServiceResult update(RelatedBean reqBean, String[] values);

  /**
   * 関連付けの種類を引数pictureModeで指定し、それに応じた<BR>
   * RelatedBaseの子クラスのインスタンスを生成します。<BR>
   * 本メソッドが返すインスタンスはログイン情報を持ちます。<BR>
   * pictureMode:<BR>
   * tag タグ<BR>
   * gift ギフト<BR>
   * campaign キャンペーン<BR>
   * 
   * @param pictureMode
   * @param loginInfo
   * @return 生成した関連付けインスタンス
   */
  public static RelatedBase createNewInstance(String pictureMode, BackLoginInfo loginInfo) {
    RelatedBase related = DIContainer.get("related_" + pictureMode);
    related.setLoginInfo(loginInfo);
    return related;
  }

  /**
   * isExistかどうかを返します。
   * 
   * @param reqBean
   * @return isExistのときはtrue
   */
  public boolean isExist(RelatedBean reqBean) {
    return false;
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
   * isCommodityExistかどうかを返します。
   * 
   * @param reqBean
   * @return isCommodityExistのときはtrue
   */
  public boolean isCommodityExist(RelatedBean reqBean) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = service.getCommodityHeader(reqBean.getEdit().getShopCode(), reqBean.getEdit()
        .getCommodityCode());

    return commodityHeader != null;
  }

  /**
   * isNotCommodityExistかどうかを返します。
   * 
   * @param reqBean
   * @return isNotCommodityExistのときはtrue
   */
  public boolean isNotCommodityExist(RelatedBean reqBean) {
    return !isCommodityExist(reqBean);
  }

  /**
   * @param pictureCode
   *          設定する pictureCode
   */
  public void setPictureCode(String pictureCode) {
    this.pictureCode = pictureCode;
  }

  /**
   * @return pictureCode
   */
  public String getPictureCode() {
    return pictureCode;
  }

  /**
   * @param effectualCode
   *          設定する effectualCode
   */
  public void setEffectualCode(String effectualCode) {
    this.effectualCode = effectualCode;
  }

  /**
   * @return effectualCode
   */
  public String getEffectualCode() {
    return effectualCode;
  }

  /**
   * ログインユーザ情報を取得します。
   * 
   * @return ログインユーザ情報
   */
  public BackLoginInfo getLoginInfo() {
    return loginInfo;
  }

  private void setLoginInfo(BackLoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }
}
