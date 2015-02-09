package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean.CategoryAttributeBean;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean.CategoryDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;

/**
 * U1040210:カテゴリのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class CategoryBaseAction extends WebBackAction<CategoryBean> {

  public void setCategoryTree(CategoryBean reqBean, List<CategoryInfo> categoryList) {
    // カテゴリツリー構築用データの生成
    // 階層:表示順:名前:PC件数-Mobile件数
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < categoryList.size(); i++) {
      stringBuilder.append(categoryList.get(i).getCategoryCode() + ":" + categoryList.get(i).getDepth() + ":"
          + categoryList.get(i).getCategoryNamePc() + ":" + categoryList.get(i).getCommodityCountPc() +
          // 20120514 tuxinwei add start
          ":" + categoryList.get(i).getCategoryNamePcEn() + ":" + categoryList.get(i).getCategoryNamePcJp() +
          // 20120514 tuxinwei add end
          "-" + categoryList.get(i).getCommodityCountMobile() + "/");
    }
    reqBean.setCategoryList(stringBuilder.toString());
    // 10.1.2 10089 追加 ここから
    reqBean.setCategoryNodeInfoList(categoryList);
    // 10.1.2 10089 追加 ここまで
  }

  public CategoryDetailBean createCategoryEdit(CategoryBean reqBean) {

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    List<CategoryInfo> categoryList = service.getAllCategory();

    CategoryDetailBean edit = new CategoryDetailBean();
    edit.setCategoryCode(reqBean.getEdit().getCategoryCode());

    if (StringUtil.isNullOrEmpty(reqBean.getEdit().getCategoryCode())) {
      edit.setCategoryCode(categoryList.get(0).getCategoryCode());
    }

    for (CategoryInfo ci : categoryList) {
      if (ci.getCategoryCode().equals(edit.getCategoryCode())) {
        edit.setParentCategoryCode(ci.getParentCategoryCode());
        edit.setCategoryCode(ci.getCategoryCode());
        edit.setCategoryNamePc(ci.getCategoryNamePc());
        // 20120514 tuxinwei add start
        edit.setCategoryNamePcEn(ci.getCategoryNamePcEn());
        edit.setCategoryNamePcJp(ci.getCategoryNamePcJp());
        // 20120514 tuxinwei add end
        edit.setCategoryNameMobile(ci.getCategoryNameMobile());
        edit.setDisplayOrder(NumUtil.toString(ci.getDisplayOrder()));
        edit.setUpdatedDatetime(ci.getUpdatedDatetime());
        // add by os012 20111221 start 淘宝分类编号
        edit.setCategoryIdTmall(ci.getCategoryIdTmall());
        // add by os012 20111221 end
        //2014/4/28 京东WBS对应 ob_李 add start
        edit.setCategoryIdJd(ci.getCategoryIdJd());
        //2014/4/28 京东WBS对应 ob_李 add end
        edit.setMetaKeyword(ci.getMetaKeyword());
        edit.setMetaDescription(ci.getMetaDescription());
        edit.setKeywordCn2(ci.getKeywordCn2());
        edit.setKeywordEn2(ci.getKeywordEn2());
        edit.setKeywordJp2(ci.getKeywordJp2());
        // 20130703 txw add start
        edit.setTitle(ci.getTitle());
        edit.setTitleEn(ci.getTitleEn());
        edit.setTitleJp(ci.getTitleJp());
        edit.setDescription(ci.getDescription());
        edit.setDescriptionEn(ci.getDescriptionEn());
        edit.setDescriptionJp(ci.getDescriptionJp());
        edit.setKeyword(ci.getKeyword());
        edit.setKeywordEn(ci.getKeywordEn());
        edit.setKeywordJp(ci.getKeywordJp());
        // 20130703 txw add end
        // カテゴリ属性番号の設定
        List<CategoryAttributeBean> categoryAttributeDetailList = new ArrayList<CategoryAttributeBean>();
        WebshopConfig config = DIContainer.getWebshopConfig();
        int j = 0;
        for (int i = 0; i < config.getCategoryAttributeMaxCount(); i++) {
          CategoryAttributeBean categoryAttributeDetail = new CategoryAttributeBean();

          if (j < ci.getCategoryInfoDetailList().size()
              && (ci.getCategoryInfoDetailList().get(j).getCategoryAttributeNo()).equals(Long.parseLong(Integer.toString(i)))) {
            categoryAttributeDetail.setCategoryAttributeName(ci.getCategoryInfoDetailList().get(j).getCategoryAttributeName());
            // add by cs_yuli 20120607 start
            categoryAttributeDetail.setCategoryAttributeNameEn(ci.getCategoryInfoDetailList().get(j).getCategoryAttributeNameEn());
            categoryAttributeDetail.setCategoryAttributeNameJp(ci.getCategoryInfoDetailList().get(j).getCategoryAttributeNameJp());
            // add by cs_yuli 20120607 end
            categoryAttributeDetail.setCategoryAttributeNo(NumUtil.toString(ci.getCategoryInfoDetailList().get(j)
                .getCategoryAttributeNo()));
            categoryAttributeDetail.setOrmRowid(ci.getCategoryInfoDetailList().get(j).getOrmRowid());
            categoryAttributeDetail.setUpdatedDatetime(ci.getCategoryInfoDetailList().get(j).getUpdatedDatetime());
            categoryAttributeDetailList.add(categoryAttributeDetail);
            j++;
          } else {
            categoryAttributeDetail.setCategoryAttributeName("");
            // add by cs_yuli 20120607 start
            categoryAttributeDetail.setCategoryAttributeNameEn("");
            categoryAttributeDetail.setCategoryAttributeNameJp("");
            // add by cs_yuli 20120607 end
            categoryAttributeDetail.setCategoryAttributeNo(Integer.toString(i));
            categoryAttributeDetailList.add(categoryAttributeDetail);
          }
        }
        edit.setAttributeList(categoryAttributeDetailList);

      }
    }

    reqBean.setEdit(edit);

    if (StringUtil.isNullOrEmpty(reqBean.getEdit().getCategoryCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.CategoryBaseAction.0")));
    }

    return edit;

  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public abstract boolean authorize();

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public abstract boolean validate();

}
