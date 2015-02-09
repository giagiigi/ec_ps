package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.dto.StockStatus;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.StockStatusCount;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.StockStatusBean;
import jp.co.sint.webshop.web.bean.back.catalog.StockStatusBean.StockStatusDetailBean;

/**
 * U1040610:在庫状況設定のアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class StockStatusBaseAction extends WebBackAction<StockStatusBean> {

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
   * @param ss
   * @param stockStatusDetail
   */
  public void setResultStockStatusList(StockStatusCount ss, StockStatusDetailBean stockStatusDetail) {
    stockStatusDetail.setShopCode(ss.getShopCode());
    stockStatusDetail.setStockStatusNo(String.valueOf(ss.getStockStatusNo()));
    stockStatusDetail.setStockStatusName(ss.getStockStatusName());
    stockStatusDetail.setStockSufficientMessage(ss.getStockSufficientMessage());
    stockStatusDetail.setStockLittleMessage(ss.getStockLittleMessage());
    stockStatusDetail.setOutOfStockMessage(ss.getOutOfStockMessage());
    stockStatusDetail.setStockSufficientThreshold(String.valueOf(ss.getStockSufficientThreshold()));
    stockStatusDetail.setUpdatedDatetime(ss.getUpdatedDatetime());
    setDeleteButtonDisplayControl(ss, stockStatusDetail);
  }

  /**
   * DBより在庫状況一覧を取得します
   * 
   * @param reqBean
   * @return stockStatusList
   */
  public List<StockStatusCount> getStockStatusList(StockStatusBean reqBean) {

    // ショップが持つ在庫状況の一覧を取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    List<StockStatusCount> stockStatusList = service.getStockStatusList(reqBean.getSearchShopCode());

    return stockStatusList;
  }

  /**
   * 画面から取得した値をDTOにセットします
   * 
   * @param detail
   * @param stockStatus
   */
  public void setStockStatusData(StockStatusDetailBean detail, StockStatus stockStatus) {
    stockStatus.setShopCode(detail.getShopCode());
    stockStatus.setStockStatusNo(NumUtil.toLong(detail.getStockStatusNo()));
    stockStatus.setStockStatusName(detail.getStockStatusName());
    stockStatus.setStockSufficientMessage(detail.getStockSufficientMessage());
    stockStatus.setStockLittleMessage(detail.getStockLittleMessage());
    stockStatus.setOutOfStockMessage(detail.getOutOfStockMessage());
    stockStatus.setStockSufficientThreshold(NumUtil.toLong(detail.getStockSufficientThreshold()));
  }

  /**
   * 画面上の各項目の表示/非表示を設定します
   * 
   * @param reqBean
   */
  public void setDisplayControl(StockStatusBean reqBean) {

    // 検索ボタンの設定
    // 一店舗版のときまたはサイト管理者でないときはボタンを表示しない
    if (getLoginInfo().isSite() && !getConfig().isOne()) {
      reqBean.setSearchButtonDisplayFlg(true);
      UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
      reqBean.setShopList(utilService.getShopNames(true));
    } else {
      reqBean.setSearchButtonDisplayFlg(false);
    }

    // 編集テーブル、選択ボタンの設定
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      reqBean.setEditTableDisplayFlg(true);
      reqBean.setSelectButtonDisplayFlg(true);

      // 登録,キャンセル,更新ボタンの設定
      if (reqBean.getMode().equals("new")) {
        reqBean.setRegisterButtonDisplayFlg(true);
        reqBean.setUpdateButtonDisplayFlg(false);
      } else if (reqBean.getMode().equals("update") && StringUtil.hasValue(reqBean.getEdit().getStockStatusNo())) {
        reqBean.setRegisterButtonDisplayFlg(false);
        reqBean.setUpdateButtonDisplayFlg(true);
      } else {
        // modeが"new","update"以外はないが、念のため非表示
        reqBean.setRegisterButtonDisplayFlg(false);
        reqBean.setUpdateButtonDisplayFlg(false);
      }

    } else {
      reqBean.setEditTableDisplayFlg(false);
      reqBean.setSelectButtonDisplayFlg(false);
    }

  }

  /**
   * 画面上に表示される削除ボタンの表示/非表示を制御します。<BR>
   * 「削除権限がある」「在庫状況に商品が1つも関連付いていない」時にボタンを表示します。
   * 
   * @param ss
   * @param stockStatusDetail
   */
  private void setDeleteButtonDisplayControl(StockStatusCount ss, StockStatusDetailBean stockStatusDetail) {
    if (ss.getRelatedCount() == null && Permission.COMMODITY_DELETE.isGranted(getLoginInfo())) {
      stockStatusDetail.setDeleteButtonDisplayFlg(true);
    } else {
      stockStatusDetail.setDeleteButtonDisplayFlg(false);
    }

  }

}
