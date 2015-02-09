package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.ArrivalGoodsSearchCondition;
import jp.co.sint.webshop.service.catalog.ArrivalGoodsSubscritionCount;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.ArrivalGoodsBean;
import jp.co.sint.webshop.web.bean.back.catalog.ArrivalGoodsBean.ArrivalGoodsDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040710:入荷お知らせのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ArrivalGoodsInitAction extends WebBackAction<ArrivalGoodsBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
  }

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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    ArrivalGoodsBean bean = new ArrivalGoodsBean();

    // サイト管理者ならショップ一覧を取得
    if (getLoginInfo().isSite()) {
      UtilService service = ServiceLocator.getUtilService(getLoginInfo());
      bean.setShopList(service.getShopNames(true));
    } else {
      bean.setSearchShopCode(getLoginInfo().getShopCode());
      getBean().setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 検索条件の生成
    ArrivalGoodsSearchCondition condition = new ArrivalGoodsSearchCondition();
    condition.setSearchShopCode(getBean().getSearchShopCode());
    condition.setSearchCommodityName(getBean().getSearchCommodityName());
    condition.setSearchSkuCode(getBean().getSearchSkuCode());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // 検索結果の取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<ArrivalGoodsSubscritionCount> result = service.getArrivalGoodsSubcriptionCountList(condition);
    List<ArrivalGoodsSubscritionCount> goodsList = result.getRows();
    // オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.OVERFLOW);

    // 画面表示用Beanを設定
    List<ArrivalGoodsDetailBean> detailList = new ArrayList<ArrivalGoodsDetailBean>();
    for (ArrivalGoodsSubscritionCount goods : goodsList) {
      ArrivalGoodsDetailBean detail = new ArrivalGoodsDetailBean();
      detail.setShopCode(goods.getShopCode());
      String commodityName = goods.getCommodityName();
      if (StringUtil.hasValueAnyOf(goods.getStandardDetail1Name(), goods.getStandardDetail2Name())) {
        commodityName += "(";
        if (StringUtil.hasValue(goods.getStandardDetail1Name())) {
          commodityName += goods.getStandardDetail1Name();
        }
        if (StringUtil.hasValueAllOf(goods.getStandardDetail1Name(), goods.getStandardDetail2Name())) {
          commodityName += ":";
        }
        if (StringUtil.hasValue(goods.getStandardDetail2Name())) {
          commodityName += goods.getStandardDetail2Name();
        }
        commodityName += ")";
      }
      detail.setCommodityName(commodityName);
      detail.setSkuCode(goods.getSkuCode());
      detail.setSubscriptionCount(String.valueOf(goods.getSubscriptionCount()));
      detailList.add(detail);
    }
    bean.setList(detailList);
    bean.setPagerValue(PagerUtil.createValue(result));

    // 画面表示用Beanを次画面Beanを設定
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    // URLパラメータより完了パラメータを取得
    String complete = "";
    if (getRequestParameter().getPathArgs().length > 0) {
      complete = getRequestParameter().getPathArgs()[0];
    }

    // 完了メッセージを設定
    setCompleteMessage(complete);

    // 画面項目の表示/非表示を設定
    ArrivalGoodsBean requestBean = (ArrivalGoodsBean) getRequestBean();
    if (getLoginInfo().isSite()) {
      requestBean.setDisplayShopList(true);
    }
    if (Permission.COMMODITY_DELETE.isGranted(getLoginInfo())) {
      requestBean.setDisplayDeleteButton(true);
    }
    if (Permission.COMMODITY_DATA_IO.isGranted(getLoginInfo())) {
      for (ArrivalGoodsDetailBean detail : requestBean.getList()) {
        if (NumUtil.toLong(detail.getSubscriptionCount()) > 0) {
          detail.setDisplayCsvExportButton(true);
        }
      }
    }

  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします <BR>
   * 処理完了パラメータがない場合は、何もメッセージを設定しません<BR>
   * 削除完了時：delete CSV出力完了時 : csv_export_complete<BR>
   * 
   * @param complete
   *          処理完了パラメータ
   */
  private void setCompleteMessage(String complete) {

    if (complete.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.catalog.ArrivalGoodsInitAction.0")));
    } else if (complete.equals("export")) {
      addInformationMessage(WebMessage.get(CompleteMessage.CSV_EXPORT_COMPLETE,
          Messages.getString("web.action.back.catalog.ArrivalGoodsInitAction.0")));
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.ArrivalGoodsInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104071003";
  }

}
