package jp.co.sint.webshop.web.action.back.catalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean.RelatedCategoryDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1040220:カテゴリ－商品関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedCategoryDeleteAction extends RelatedCategoryBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.CATEGORY_UPDATE.isGranted(getLoginInfo()) || Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    RelatedCategoryBean reqBean = getBean();
    return validateBean(reqBean);

  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    Logger logger = Logger.getLogger(this.getClass());
    RelatedCategoryBean reqBean = getBean();

    // ショップ管理者、または一店舗版の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    Map<String, RelatedCategoryDetailBean> detailMap = listToMap(reqBean.getList());

    // 削除処理
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    for (String checkedCode : reqBean.getCheckedCodeList()) {
      RelatedCategoryDetailBean detail = detailMap.get(checkedCode);
      ServiceResult result = service.deleteCategoryAttributeValue(reqBean.getCategoryCode(), detail.getShopCode(), detail
          .getCommodityCode());

      if (result.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
            addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
                Messages.getString("web.action.back.catalog.RelatedCategoryDeleteAction.0")));
            setRequestBean(getBean());
            return BackActionResult.RESULT_SUCCESS;
          }
        }
        logger.error(Messages.log("web.action.back.catalog.RelatedCategoryDeleteAction.1"));
        return BackActionResult.SERVICE_ERROR;
      }
    }

    setNextUrl("/app/catalog/related_category/init/" + WebConstantCode.COMPLETE_DELETE);

    setRequestBean(reqBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  private Map<String, RelatedCategoryDetailBean> listToMap(List<RelatedCategoryDetailBean> list) {
    Map<String, RelatedCategoryDetailBean> map = new HashMap<String, RelatedCategoryDetailBean>();
    for (RelatedCategoryDetailBean bean : list) {
      map.put(bean.getCheckedCode(), bean);
    }
    return map;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.RelatedCategoryDeleteAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104022001";
  }

}
