package jp.co.sint.webshop.web.action.back.catalog;

import java.text.MessageFormat;
import java.util.List;

import jp.co.sint.webshop.data.UpdateResult;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityPriceChangeReviewBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.back.WebBackAction;

import org.apache.log4j.Logger;


public class CommodityPriceChangeReviewConfirmAction extends WebBackAction<CommodityPriceChangeReviewBean> {


  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.COMMODITY_PRICE_CHANGE);
  }
  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean validation = true;

    CommodityPriceChangeReviewBean bean = getBean();
    List<String> commodityCodeArray = getBean().getCheckedCommodityList();
    String[] params = getRequestParameter().getPathArgs();
    
    if (commodityCodeArray.size() == 1 && params.length == 0) {
      if (bean.getCheckedCommodityList().get(0).equals("")) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
            Messages.getString("web.action.back.order.OrderConfirmListConfirmAction.0")));
        validation &= false;
        return validation;
      }
    // 当未选中任何商品历史记录且没有任何URL参数（点击的不是单个审核按钮时）
    } else if(commodityCodeArray.size() == 0 && params.length == 0) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.order.OrderConfirmListConfirmAction.0")));
      validation &= false;
      return validation;
    }
    return validation;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    List<String> commodityCodeArray = getBean().getCheckedCommodityList();

    CatalogService catalogservice = ServiceLocator.getCatalogService(getLoginInfo());

    CommodityPriceChangeReviewBean bean = getBean();

    String[] params = getRequestParameter().getPathArgs();

    boolean serviceResultErrorFlg = false;
    ServiceResult result = null;
    // 点击的是单个审核按钮
    if (params.length != 0) {
      String commodityCode = params[0];
      if(StringUtil.hasValue(commodityCode)) {
        result = catalogservice.updateCommodityPriceChangeHistoryReviewFlg(commodityCode, this.getLoginInfo().getRecordingFormat());
        if (result.hasError()) {
          serviceResultErrorFlg = true;
          Logger logger = Logger.getLogger(this.getClass());
          logger.warn(MessageFormat.format(Messages.log("web.action.back.order.CommodityPriceChangeReviewConfirmAction.0"),
              commodityCode));
        }
      }
    } else {
      // 点击的是批量审核按钮
      // serviceの処理結果に一件でもエラーがあるかどうか判別する為のフラグ
      for (String commodityCode : commodityCodeArray) {
        result = catalogservice.updateCommodityPriceChangeHistoryReviewFlg(commodityCode, this.getLoginInfo().getRecordingFormat());
        if (result.hasError()) {
          serviceResultErrorFlg = true;
          Logger logger = Logger.getLogger(this.getClass());
          logger.warn(MessageFormat.format(Messages.log("web.action.back.order.CommodityPriceChangeReviewConfirmAction.0"),
              commodityCode));
        }
      }
    }
    // サービスの処理結果に1件でもエラーが存在した場合、エラー画面遷移
    if (serviceResultErrorFlg) {
      return BackActionResult.SERVICE_ERROR;
    }

    setRequestBean(bean);
    setNextUrl("/app/catalog/commodity_price_change_review/search/" + WebConstantCode.COMPLETE_UPDATE);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.CommodityPriceChangeReviewConfirmAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021007";
  }


}
