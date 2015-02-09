package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CategoryAttributeValue;
import jp.co.sint.webshop.data.dto.OriginalPlace;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean.CategoryAttributeValueBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040220:カテゴリ－商品関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedCategoryUpdateAction extends WebBackAction<RelatedCategoryBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    RelatedCategoryBean reqBean = getBean();
    BackLoginInfo login = getLoginInfo();

    String shopCode = "";

    // ショップ管理者、または一店舗版の場合はログイン情報からショップコードを取得
    if (login.isShop() || getConfig().isOne()) {
      shopCode = login.getShopCode();
      reqBean.setSearchShopCode(login.getShopCode());
    } else {
      shopCode = reqBean.getEdit().getShopCode();
    }

    if (StringUtil.isNullOrEmpty(shopCode)) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.RelatedCategoryUpdateAction.0")));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    List<CategoryAttributeValue> categoryAttributeValueList = new ArrayList<CategoryAttributeValue>();

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    // カテゴリ属性値に関する情報を設定

    for (int i = 0; i < DIContainer.getWebshopConfig().getCategoryAttributeMaxCount(); i++) {
      if (StringUtil.hasValue(reqBean.getAttributeList().get(i).getCategoryAttributeName())) {
        CategoryAttributeValueBean vb = reqBean.getEdit().getAttributeList().get(i);
        CategoryAttributeValue attribute = service.getCategoryAttributeValue(reqBean.getCategoryCode(), NumUtil.toLong(vb
            .getCategoryAttributeNo()), shopCode, reqBean.getEdit().getCommodityCode()); 
        CategoryAttributeValue categoryAttributeValue = new CategoryAttributeValue();
        categoryAttributeValue.setShopCode(shopCode);
        categoryAttributeValue.setCategoryCode(reqBean.getCategoryCode());
        categoryAttributeValue.setCommodityCode(reqBean.getEdit().getCommodityCode());
        categoryAttributeValue.setCategoryAttributeNo(NumUtil.toLong(vb.getCategoryAttributeNo()));
        categoryAttributeValue.setCategoryAttributeValue(vb.getCategoryAttributeValue());
        //add by cs_yuli 20120608 start
        categoryAttributeValue.setCategoryAttributeValueEn(vb.getCategoryAttributeValueEn());
        categoryAttributeValue.setCategoryAttributeValueJp(vb.getCategoryAttributeValueJp());
        //add by cs_yuli 20120608 end
        categoryAttributeValue.setUpdatedUser(login.getLoginId());

        if (attribute != null) {
          categoryAttributeValue.setCreatedUser(attribute.getCreatedUser());
          categoryAttributeValue.setCreatedDatetime(attribute.getCreatedDatetime());
          categoryAttributeValue.setOrmRowid(attribute.getOrmRowid());
          categoryAttributeValue.setUpdatedDatetime(vb.getUpdatedDatetime());
        }

        categoryAttributeValueList.add(categoryAttributeValue);
      }
    }
 
    ServiceResult result = service.updateCategoryAttributeValue(categoryAttributeValueList);
    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR));
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.catalog.RelatedCategoryUpdateAction.1")));
        } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/catalog/related_category/init/" + WebConstantCode.COMPLETE_UPDATE);
    }

    setRequestBean(reqBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()) || Permission.CATEGORY_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    List<Boolean> result = new ArrayList<Boolean>();
    RelatedCategoryBean relatedCategoryBean = (RelatedCategoryBean) getBean();
    result.add(validateBean(relatedCategoryBean.getEdit()));

  //判断录入的值是否正确
    if(relatedCategoryBean.getEdit().getAttributeList() != null && relatedCategoryBean.getEdit().getAttributeList().size() > 0){
        CategoryAttributeValueBean vb = relatedCategoryBean.getEdit().getAttributeList().get(0);
        if(StringUtil.isNullOrEmpty(vb.getCategoryAttributeValue())){
        	addErrorMessage("商品分类属性1产地中文不能为空");
        	  result.add(false);
        }
        if(StringUtil.isNullOrEmpty(vb.getCategoryAttributeValueEn())){
        	addErrorMessage("商品分类属性1产地英文不能为空");
        	  result.add(false);
        }
        if(StringUtil.isNullOrEmpty(vb.getCategoryAttributeValueJp())){
        	addErrorMessage("商品分类属性1产地日文不能为空");
        	  result.add(false);
        }
        UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
        OriginalPlace op = utilService.getOriginal(vb.getCategoryAttributeValue(), vb.getCategoryAttributeValueEn(),vb.getCategoryAttributeValueJp());
        if(StringUtil.isNullOrEmpty(op)){
        	addErrorMessage("商品分类属性1产地名称（中、英、日）不匹配");
        	  result.add(false);
        }
    }

    if (getLoginInfo().isSite() && !getConfig().isOne()) {
      if (StringUtil.isNullOrEmpty(relatedCategoryBean.getEdit().getShopCode())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR,
            Messages.getString("web.action.back.catalog.RelatedCategoryRegisterAction.3")));
        result.add(false);
      }
    }
    for (int i = 0; i < relatedCategoryBean.getEdit().getAttributeList().size(); i++) {
      result.add(validateBean(relatedCategoryBean.getEdit().getAttributeList().get(i)));
    }

    int errorCount = 0;
    for (Boolean bool : result) {
      if (bool.equals(false)) {
        errorCount += 1;
      }
    }

    return errorCount == 0;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.RelatedCategoryUpdateAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104022006";
  }

}
