package jp.co.sint.webshop.web.action.back.catalog;

import java.io.InputStream;

import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseCommodity;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

/**
 * 一括関連付けのアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class RelatedListBase {

  private BackLoginInfo loginInfo;

  private String pictureCode;

  abstract RelatedListBean search(RelatedListBean relatedList, RelatedSearchConditionBaseCommodity condition);

  abstract ServiceResult register(RelatedListBean relatedList, String[] values);

  abstract ServiceResult csvImport(InputStream fileUrl);

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
  public static RelatedListBase createNewInstance(String pictureMode, BackLoginInfo loginInfo) {
    RelatedListBase related = DIContainer.get("related_" + pictureMode);
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
