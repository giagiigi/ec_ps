package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.GiftCount;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean.GiftDetailBean;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040310:ギフトのアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class GiftBaseAction extends WebBackAction<GiftBean> {

  /** 処理モード:新規 */
  public static final String MODE_NEW = "new";

  /** 処理モード:更新 */
  public static final String MODE_UPDATE = "update";

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * 検索結果を検索結果リスト用Beanにセットします
   * 
   * @param gift
   *          ギフト情報
   * @param detail
   *          検索結果リスト用Bean
   */
  public void setResultGiftList(GiftCount gift, GiftDetailBean detail) {
    detail.setShopCode(gift.getShopCode());
    detail.setGiftCode(gift.getGiftCode());
    detail.setGiftName(gift.getGiftName());
    detail.setGiftDescription(gift.getGiftDescription());
    detail.setGiftPrice(String.valueOf(gift.getGiftPrice()));
    detail.setGiftTaxType(String.valueOf(gift.getGiftTaxType()));
    detail.setDisplayFlg(String.valueOf(gift.getDisplayFlg()));
    detail.setDisplayOrder(NumUtil.toString(gift.getDisplayOrder()));
    detail.setUpdateDatetime(gift.getUpdatedDatetime());
    setDeleteButtonDisplayControl(gift, detail);

  }

  /**
   * DBより在庫状況一覧を取得します
   * 
   * @param bean
   * @return 在庫状況一覧
   */
  public List<GiftCount> getGiftList(GiftBean bean) {

    // ショップ管理者、または一店舗版の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      bean.setSearchShopCode(getLoginInfo().getShopCode());
    }
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    return service.getGiftList(bean.getSearchShopCode());
  }

  /**
   * 画面から取得した値をDTOにセットします
   * 
   * @param bean
   * @param gift
   */
  public void setGiftData(GiftDetailBean bean, Gift gift) {
    gift.setShopCode(bean.getShopCode());
    gift.setGiftCode(bean.getGiftCode());
    gift.setGiftName(bean.getGiftName());
    gift.setGiftDescription(bean.getGiftDescription());
    gift.setGiftPrice(NumUtil.parse(bean.getGiftPrice()));
    gift.setDisplayOrder(NumUtil.toLong(bean.getDisplayOrder(), null));
    //modify by V10-CH 170 start
    //gift.setGiftTaxType(NumUtil.toLong(bean.getGiftTaxType()));
      gift.setGiftTaxType(TaxType.NO_TAX.longValue());
    //modify by V10-CH 170 start
    gift.setDisplayFlg(NumUtil.toLong(bean.getDisplayFlg()));
  }

  /**
   * 画面に表示するボタンの制御を行います。
   * 
   * @param bean
   */
  public void setDisplayControl(GiftBean bean) {

    // 検索ボタンの設定
    bean.setDisplaySearchButton(getLoginInfo().isSite());

    // 編集テーブル、選択ボタンの設定
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      bean.setDisplayEditTable(true);
      bean.setDisplaySelectButton(true);

      // 登録,キャンセル,更新ボタンの設定
      if (bean.getMode().equals(MODE_NEW)) {
        bean.setGiftCodeDisplayMode(WebConstantCode.DISPLAY_EDIT);
        bean.setDisplayRegisterButton(true);
        bean.setDisplayUpdateButton(false);
      } else if (bean.getMode().equals(MODE_UPDATE)) {
        bean.setGiftCodeDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
        bean.setDisplayRegisterButton(false);
        bean.setDisplayUpdateButton(true);
      } else {
        // MODE_NEW, MODE_UPDATE以外はあり得ないが念のため,
        // 非表示または読み取り専用
        bean.setGiftCodeDisplayMode(WebConstantCode.DISPLAY_READONLY);
        bean.setDisplayRegisterButton(false);
        bean.setDisplayUpdateButton(false);
      }

    }

    // 画像アップロードテープルの設定
    bean.setDisplayUploadTable(Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()));

  }

  /**
   * 画面上に表示される削除ボタンの表示/非表示を制御します。<BR>
   * 「削除権限がある」「ギフトに商品が1つも関連付いていない」時にボタンを表示します。
   */
  private void setDeleteButtonDisplayControl(GiftCount gift, GiftDetailBean detail) {
    if (Permission.COMMODITY_DELETE.isGranted(getLoginInfo()) && (gift.getRelatedCount() == null || gift.getRelatedCount() < 1)) {
      detail.setDisplayDeleteButton(true);
    } else {
      detail.setDisplayDeleteButton(false);
    }
  }

}
