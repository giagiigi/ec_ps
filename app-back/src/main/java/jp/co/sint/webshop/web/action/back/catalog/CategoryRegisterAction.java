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
public class CategoryRegisterAction extends WebBackAction<CategoryBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    Logger logger = Logger.getLogger(this.getClass());
    CategoryBean reqBean = getBean();
    CategoryInfo category = new CategoryInfo();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    WebshopConfig config = DIContainer.getWebshopConfig();

    // 登録用データの作成
    category.setCategoryCode(reqBean.getEdit().getCategoryCode());
    category.setCategoryNamePc(reqBean.getEdit().getCategoryNamePc());
    // 20120514 tuxinwei add start
    category.setCategoryNamePcEn(reqBean.getEdit().getCategoryNamePcEn());
    category.setCategoryNamePcJp(reqBean.getEdit().getCategoryNamePcJp());
    // 20120514 tuxinwei add end
    category.setCategoryNameMobile(reqBean.getEdit().getCategoryNameMobile());
    // 10.1.4 10143 修正 ここから
    // category.setDisplayOrder(Long.parseLong(reqBean.getEdit().getDisplayOrder()));
    category.setDisplayOrder(NumUtil.toLong(reqBean.getEdit().getDisplayOrder(), null));
    // 10.1.4 10143 修正 ここまで
    category.setParentCategoryCode(reqBean.getEdit().getParentCategoryCode());
    // add by os012 20111221 start 淘宝分类编号
    category.setCategoryIdTmall(reqBean.getEdit().getCategoryIdTmall());
    // add by os012 20111221 end
    //2014/4/28 京东WBS对应 ob_李 add start
    category.setCategoryIdJd(reqBean.getEdit().getCategoryIdJd());
    //2014/4/28 京东WBS对应 ob_李 add end
    category.setMetaKeyword(reqBean.getEdit().getMetaKeyword());
    category.setMetaDescription(reqBean.getEdit().getMetaDescription());
    // 新規登録時は自カテゴリに関連付いている商品の件数を0件で登録
    category.setCommodityCountPc(0L);
    category.setCommodityCountMobile(0L);
    category.setKeywordCn2(reqBean.getEdit().getKeywordCn2());
    category.setKeywordEn2(reqBean.getEdit().getKeywordEn2());
    category.setKeywordJp2(reqBean.getEdit().getKeywordJp2());
    // 20130703 txw add start
    category.setTitle(reqBean.getEdit().getTitle());
    category.setTitleEn(reqBean.getEdit().getTitleEn());
    category.setTitleJp(reqBean.getEdit().getTitleJp());
    category.setDescription(reqBean.getEdit().getDescription());
    category.setDescriptionEn(reqBean.getEdit().getDescriptionEn());
    category.setDescriptionJp(reqBean.getEdit().getDescriptionJp());
    category.setKeyword(reqBean.getEdit().getKeyword());
    category.setKeywordEn(reqBean.getEdit().getKeywordEn());
    category.setKeywordJp(reqBean.getEdit().getKeywordJp());
    // 20130703 txw add end
    // カテゴリ属性番号を0から採番
    List<CategoryInfoDetail> categoryInfoDetailList = new ArrayList<CategoryInfoDetail>();
    for (int i = 0; i < reqBean.getEdit().getAttributeList().size(); i++) {
      CategoryAttributeBean attribute = reqBean.getEdit().getAttributeList().get(i);
      // カテゴリ属性名が入力されている場合のみ登録
      if (StringUtil.hasValue(attribute.getCategoryAttributeName())) {
        CategoryInfoDetail categoryInfoDetail = new CategoryInfoDetail();
        categoryInfoDetail.setCategoryAttributeNo(Long.valueOf(i));
        categoryInfoDetail.setCategoryAttributeName(attribute.getCategoryAttributeName());
        categoryInfoDetailList.add(categoryInfoDetail);
      }
    }

    // 2．変更対象の親カテゴリコードが存在しない場合
    Category parentCategory = service.getCategory(reqBean.getEdit().getParentCategoryCode());
    if (parentCategory == null) {
      // 存在しないエラー
      logger.error(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.CategoryRegisterAction.0")));
      addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.CategoryRegisterAction.0")));
      setNextUrl(null);
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    } else if (parentCategory.getDepth() >= config.getCategoryMaxDepth()) {
      // カテゴリ階層最大値オーバー
      logger.error(WebMessage.get(CatalogErrorMessage.CATEGORY_MAX_DEPTH_OVER_ERROR, String.valueOf(config.getCategoryMaxDepth())));
      addWarningMessage(WebMessage.get(CatalogErrorMessage.CATEGORY_MAX_DEPTH_OVER_ERROR, String.valueOf(config
          .getCategoryMaxDepth())));
      setNextUrl(null);
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    category.setCategoryInfoDetailList(categoryInfoDetailList);

    // 登録処理実行
    // delete by os012 20111220 start
    // ServiceResult result = service.insertCategory(category);
    // delete by os012 20111220 end
    ServiceResult result = service.insertCategoryinfo(category);

    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.back.catalog.CategoryRegisterAction.1")));
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.catalog.CategoryRegisterAction.1")));
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
      setNextUrl("/app/catalog/category/select/" + reqBean.getEdit().getCategoryCode() + "/insert");
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
    BackLoginInfo login = getLoginInfo();
    return login.isSite() && Permission.CATEGORY_UPDATE.isGranted(login);
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
    // del by lc 2012-04-20 start
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
    // del by lc 2012-04-20 end
    return result;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CategoryRegisterAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104021005";
  }

}
