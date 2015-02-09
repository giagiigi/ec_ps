package jp.co.sint.webshop.web.action.back.catalog;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.service.catalog.CategoryInfo.CategoryInfoDetail;
import jp.co.sint.webshop.service.result.CatalogServiceErrorContent;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean.CategoryAttributeBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1040210:カテゴリのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CategoryUpdateAction extends WebBackAction<CategoryBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    Logger logger = Logger.getLogger(this.getClass());
    CategoryBean reqBean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    WebshopConfig config = DIContainer.getWebshopConfig();
    Category category = service.getCategory(reqBean.getEdit().getCategoryCode());

    if (category == null) {
      // 存在しないエラー
      logger.error(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.CategoryUpdateAction.0")));
      addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.CategoryUpdateAction.0")));
      setNextUrl(null);
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    if (category.getDepth() == 0L) {
      // ルートカテゴリの場合
      reqBean.getEdit().setParentCategoryCode(category.getParentCategoryCode());

    } else {
      // ルートカテゴリ以外の場合
      // 1．親カテゴリを自分自身に変更しようとした場合
      if (reqBean.getEdit().getCategoryCode().equals(reqBean.getEdit().getParentCategoryCode())) {
        logger.error(WebMessage.get(CatalogErrorMessage.CHANGE_CATEGORY_OWN_ERROR));
        addWarningMessage(WebMessage.get(CatalogErrorMessage.CHANGE_CATEGORY_OWN_ERROR));
        setNextUrl(null);
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }

      // 2．変更対象の親カテゴリコードが存在しない場合
      Category parentCategory = service.getCategory(reqBean.getEdit().getParentCategoryCode());
      if (parentCategory == null) {
        // 存在しないエラー
        logger.error(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.back.catalog.CategoryUpdateAction.1")));
        addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.back.catalog.CategoryUpdateAction.1")));
        setNextUrl(null);
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      } else if (parentCategory.getDepth() >= config.getCategoryMaxDepth()) {
        // カテゴリ階層最大値オーバー
        logger.error(WebMessage
            .get(CatalogErrorMessage.CATEGORY_MAX_DEPTH_OVER_ERROR, String.valueOf(config.getCategoryMaxDepth())));
        addWarningMessage(WebMessage.get(CatalogErrorMessage.CATEGORY_MAX_DEPTH_OVER_ERROR, String.valueOf(config
            .getCategoryMaxDepth())));
        setNextUrl(null);
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }

      // 3．自カテゴリの子に親カテゴリを変更しようとした場合
      // →変更予定の親カテゴリコードに関連付いているパスに自カテゴリコードが含まれている場合
      if ((parentCategory.getPath()).indexOf(reqBean.getEdit().getCategoryCode()) != -1) {
        logger.error(WebMessage.get(CatalogErrorMessage.CHANGE_CATEGORY_OWN_CHILD_ERROR));
        addWarningMessage(WebMessage.get(CatalogErrorMessage.CHANGE_CATEGORY_OWN_CHILD_ERROR));
        setNextUrl(null);
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }
    }

    // 画面から受け取った値をセット
    BackLoginInfo login = getLoginInfo();
    CategoryInfo registerCategory = new CategoryInfo();
    registerCategory.setCategoryCode(reqBean.getEdit().getCategoryCode());
    registerCategory.setParentCategoryCode(reqBean.getEdit().getParentCategoryCode());
    registerCategory.setCategoryNamePc(reqBean.getEdit().getCategoryNamePc());
    // 20120514 tuxinwei add start
    registerCategory.setCategoryNamePcEn(reqBean.getEdit().getCategoryNamePcEn());
    registerCategory.setCategoryNamePcJp(reqBean.getEdit().getCategoryNamePcJp());
    // 20120514 tuxinwei add start
    registerCategory.setCategoryNameMobile(reqBean.getEdit().getCategoryNameMobile());
    registerCategory.setUpdatedUser(login.getLoginId());
    registerCategory.setUpdatedDatetime(reqBean.getEdit().getUpdatedDatetime());
    // 10.1.4 10143 修正 ここから
    // registerCategory.setDisplayOrder(Long.parseLong(reqBean.getEdit().getDisplayOrder()));
    registerCategory.setDisplayOrder(NumUtil.toLong(reqBean.getEdit().getDisplayOrder(), null));
    // 10.1.4 10143 修正 ここから

    // DBから取得した値をセット
    // add by os012 20111221 start 淘宝分类编号
    registerCategory.setCategoryIdTmall(reqBean.getEdit().getCategoryIdTmall());
    // add by os012 20111221 end
    //2014/4/28 京东WBS对应 ob_李 add start
    registerCategory.setCategoryIdJd(reqBean.getEdit().getCategoryIdJd());
    //2014/4/28 京东WBS对应 ob_李 add end
    registerCategory.setMetaKeyword(reqBean.getEdit().getMetaKeyword());
    registerCategory.setMetaDescription(reqBean.getEdit().getMetaDescription());
    registerCategory.setOrmRowid(category.getOrmRowid());
    registerCategory.setCreatedUser(category.getCreatedUser());
    registerCategory.setCreatedDatetime(category.getCreatedDatetime());
    registerCategory.setCommodityCountPc(category.getCommodityCountPc());
    registerCategory.setCommodityCountMobile(category.getCommodityCountMobile());
    registerCategory.setKeywordCn2(reqBean.getEdit().getKeywordCn2());
    registerCategory.setKeywordJp2(reqBean.getEdit().getKeywordJp2());
    registerCategory.setKeywordEn2(reqBean.getEdit().getKeywordEn2());
    // 20130703 txw add start
    registerCategory.setTitle(reqBean.getEdit().getTitle());
    registerCategory.setTitleEn(reqBean.getEdit().getTitleEn());
    registerCategory.setTitleJp(reqBean.getEdit().getTitleJp());
    registerCategory.setDescription(reqBean.getEdit().getDescription());
    registerCategory.setDescriptionEn(reqBean.getEdit().getDescriptionEn());
    registerCategory.setDescriptionJp(reqBean.getEdit().getDescriptionJp());
    registerCategory.setKeyword(reqBean.getEdit().getKeyword());
    registerCategory.setKeywordEn(reqBean.getEdit().getKeywordEn());
    registerCategory.setKeywordJp(reqBean.getEdit().getKeywordJp());
    // 20130703 txw add end
    // カテゴリ属性番号を設定
    // 画面から受け取った配列を登録用Beanに順次セット
    List<CategoryInfoDetail> categoryInfoDetailList = new ArrayList<CategoryInfoDetail>();
    for (CategoryAttributeBean ca : reqBean.getEdit().getAttributeList()) {
      // カテゴリ属性名が入力されている場合のみ更新
      CategoryInfoDetail categoryInfoDetail = new CategoryInfoDetail();
      categoryInfoDetail.setCategoryAttributeNo(NumUtil.toLong(ca.getCategoryAttributeNo()));
      // add by cs_yuli 20120607 start
      categoryInfoDetail.setCategoryAttributeNameEn(ca.getCategoryAttributeNameEn());
      categoryInfoDetail.setCategoryAttributeNameJp(ca.getCategoryAttributeNameJp());
      // add by cs_yuli 20120607 end
      categoryInfoDetail.setCategoryAttributeName(ca.getCategoryAttributeName());

      if (NumUtil.isNull(ca.getOrmRowid())) {
        categoryInfoDetail.setUpdatedDatetime(DateUtil.getSysdate());
      } else {
        categoryInfoDetail.setOrmRowid(ca.getOrmRowid());
        categoryInfoDetail.setUpdatedDatetime(ca.getUpdatedDatetime());
      }
      categoryInfoDetailList.add(categoryInfoDetail);
    }
    registerCategory.setCategoryInfoDetailList(categoryInfoDetailList);

    // 更新サービスの実行

    // delete by os012 20111221 start
    // ServiceResult result = service.updateCategory(registerCategory);
    // delete by os012 20111221 end
    ServiceResult result = service.updateCategoryInfo(registerCategory);
    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.back.catalog.CategoryUpdateAction.2")));
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.catalog.CategoryUpdateAction.2")));
        } else if (errorContent.equals(CatalogServiceErrorContent.UPDATE_CATEGORY_PATH_ERROR)) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.UPDATE_CATEGORY_PATH_ERROR));
        } else if (errorContent.equals(CatalogServiceErrorContent.CATEGORY_MAX_DEPTH_OVER_ERROR)) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.CATEGORY_MAX_DEPTH_OVER_ERROR, String.valueOf(DIContainer
              .getWebshopConfig().getCategoryMaxDepth())));
        } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/catalog/category/select/" + reqBean.getEdit().getCategoryCode() + "/update");
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
    return getLoginInfo().isSite() && Permission.CATEGORY_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = false;
    CategoryBean reqBean = getBean();

    result = validateBean(reqBean.getEdit());
    int i = 0;
    // if (result) {
    for (CategoryAttributeBean cb : reqBean.getEdit().getAttributeList()) {
      result &= validateBean(cb);
      i++;
      if (cb.getCategoryAttributeNo().equals("0")) {
        if (StringUtil.isNullOrEmpty(cb.getCategoryAttributeName())) {
          addErrorMessage(MessageFormat.format(Messages.getString("web.action.back.catalog.CategoryUpdateAction.4"), i));
          result &= false;
        }
        if (StringUtil.isNullOrEmpty(cb.getCategoryAttributeNameEn())) {
          addErrorMessage(MessageFormat.format(Messages.getString("web.action.back.catalog.CategoryUpdateAction.5"), i));
          result &= false;
        }
        if (StringUtil.isNullOrEmpty(cb.getCategoryAttributeNameJp())) {
          addErrorMessage(MessageFormat.format(Messages.getString("web.action.back.catalog.CategoryUpdateAction.6"), i));
          result &= false;
        }
      }

    }
    // }
    // del by lc 2012-04-18 start
    // if (StringUtil.hasValue(reqBean.getEdit().getCategoryIdTmall())) {
    // TmallCategoryDao tmallCategoryDao =
    // DIContainer.getDao(TmallCategoryDao.class);
    // TmallCategory tmallCategory =
    // tmallCategoryDao.load(reqBean.getEdit().getCategoryIdTmall());
    // if (tmallCategory == null) {
    // addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_CATEGORY_ERROR));
    // result &= false;
    // }
    // }
    // del by lc 2012-04-18 end
    return result;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CategoryUpdateAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104021007";
  }

}
