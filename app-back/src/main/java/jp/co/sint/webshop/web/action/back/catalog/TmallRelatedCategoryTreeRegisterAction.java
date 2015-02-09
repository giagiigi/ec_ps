package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.TmallRelatedCategoryTreeBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040230:カテゴリ－関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallRelatedCategoryTreeRegisterAction extends WebBackAction<TmallRelatedCategoryTreeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    TmallRelatedCategoryTreeBean reqBean = getBean();

    List<CategoryCommodity> categoryCommodityList = new ArrayList<CategoryCommodity>();

    String[] values = reqBean.getCheckedList();
    if (StringUtil.hasValueAllOf(values)) {
      for (String value : values) {
        CategoryCommodity categoryCommodity = new CategoryCommodity();
        categoryCommodity.setCategoryCode(value);
        categoryCommodity.setShopCode(reqBean.getShopCode());
        categoryCommodity.setCommodityCode(reqBean.getCommodityCode());
        categoryCommodityList.add(categoryCommodity);
      }
    }

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult result;

    // チェック一つでもありの場合は、カテゴリ陳列商品情報の登録、
    // チェックなしの場合は、カテゴリ陳列商品情報の削除
    String completeParam = "";
    if (categoryCommodityList.size() > 0) {
      result = service.registerCategoryCommodity(categoryCommodityList);
      completeParam = WebConstantCode.COMPLETE_REGISTER;
    } else {
      result = service.deleteCategoryCommodity(reqBean.getShopCode(), reqBean.getCommodityCode());
      completeParam = WebConstantCode.COMPLETE_DELETE;
    }

    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR));
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.catalog.RelatedCategoryTreeRegisterAction.0")));
        } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/catalog/tmall_related_category_tree/init/" + "/" + reqBean.getShopCode()
          + "/" + reqBean.getCommodityCode() + "/" + completeParam);
    }

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.RelatedCategoryTreeRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104030002";
  }

}
