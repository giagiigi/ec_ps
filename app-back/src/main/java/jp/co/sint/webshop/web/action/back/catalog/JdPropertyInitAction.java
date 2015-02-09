package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dao.JdCategoryDao;
import jp.co.sint.webshop.data.dao.JdCommodityPropertyDao;
import jp.co.sint.webshop.data.dao.JdPropertyDao;
import jp.co.sint.webshop.data.domain.JdUseFlg;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.DataContainer;
import jp.co.sint.webshop.data.dto.JdCategory;
import jp.co.sint.webshop.data.dto.JdCommodityProperty;
import jp.co.sint.webshop.data.dto.JdProperty;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.JdPropertyBean;
import jp.co.sint.webshop.web.bean.back.catalog.JdPropertyBean.JdAttributeLableBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;

/**
 * U1040290:京东属性编辑
 * 
 * @author System Integrator Corp.
 */
public class JdPropertyInitAction extends JdPropertyBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    // サイト管理者はカテゴリ参照権限、ショップ管理者は商品参照権限が必要
    return (login.isSite() && Permission.CATALOG_READ.isGranted(login))
        || (login.isShop() && Permission.COMMODITY_READ.isGranted(login));
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // URLパラメータのチェック
    if (getRequestParameter().getPathArgs().length > 1) {
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    JdPropertyBean reqBean = new JdPropertyBean();

    // 得到参数
    String[] path = getRequestParameter().getPathArgs();
    if (path.length > 1) {
      reqBean.setShopCode(path[0]);
      reqBean.setCommodityCode(path[1]);
    }

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    // 商品名の取得
    CCommodityHeader commodityHeader = service.getCCommodityheader(reqBean.getShopCode(), reqBean.getCommodityCode());
    CCommodityDetail commodityDetail = service.getCCommodityDetail(reqBean.getShopCode(), reqBean.getCommodityCode());
    if (commodityHeader == null) {
      setNextUrl("/app/catalog/commodity_list/init/nodata");
      return BackActionResult.RESULT_SUCCESS;
    }
    reqBean.setCommodityName(commodityHeader.getCommodityName());
    if (StringUtil.hasValue(commodityHeader.getJdCategoryId())) {
      reqBean.setJcategoryId(commodityHeader.getJdCategoryId());
    }
    reqBean.setJcategoryId(commodityHeader.getJdCategoryId());
    if (!NumUtil.isNull(commodityHeader.getJdCommodityId())
        || JdUseFlg.DISABLED.longValue().equals(commodityDetail.getJdUseFlg())) {
      reqBean.setDisplayNextButton(false);
    } else {
      reqBean.setDisplayNextButton(true);
    }

    // 查询类别
    List<JdCategory> listCategorys = service.loadAllChildJdCategory();
    reqBean.setListCategorys(listCategorys);

    // 查询类别对应的属性
    JdCategory thisCategory = new JdCategory();
    JdCategoryDao categoryDao = DIContainer.getDao(JdCategoryDao.class);
    thisCategory = categoryDao.load(String.valueOf(reqBean.getJcategoryId()));

    if (thisCategory != null) {

      reqBean.setCategoryCode(thisCategory.getCategoryId());
      StringBuilder sb = new StringBuilder("");
      JdCategory category = new JdCategory();
      category.setCategoryId(thisCategory.getCategoryId());
      category.setCategoryName(thisCategory.getCategoryName());
      category.setParentId(thisCategory.getParentId());
      getFullCategoryName(category, sb);
      String strs[] = sb.toString().split("&&&__");
      StringBuilder nameSb = new StringBuilder("");
      for (int i = strs.length - 1; i > 0; i--) {
        nameSb.append(strs[i]);
        if (i - 1 > 0 && !"".equals(strs[i - 1])) {
          nameSb.append(">");
        }
      }
      // 查询类目名称 各级类目名称都显示出来 格式：父类目名>子类目名....
      reqBean.setCategoryName(nameSb.toString());
    } else {
      addWarningMessage("警告：商品类目没有设置");
    }

    // 查询商品包含的属性
    JdCommodityPropertyDao jdCommodityPropertyDao = DIContainer.getDao(JdCommodityPropertyDao.class);
    List<JdCommodityProperty> ps = jdCommodityPropertyDao.loadByCommodityCode(reqBean.getCommodityCode());

    DataContainer<JdCommodityProperty> datas = new DataContainer<JdCommodityProperty>();
    datas.setElements(ps);
    reqBean.setCommodityPropertyJson(datas.toJsonString().replace("'", "&prime;"));

    List<JdAttributeLableBean> attributeLableList = new ArrayList<JdAttributeLableBean>();
    JdPropertyDao jpDao = DIContainer.getDao(JdPropertyDao.class);
    if (!reqBean.isDisplayNextButton()) {
      List<String> jdCommodiytyPropertyIdList = service.getJdCommodiytyPropertyIdList(reqBean.getCommodityCode());
      if (jdCommodiytyPropertyIdList != null && jdCommodiytyPropertyIdList.size() > 0) {
        for (String propertyId : jdCommodiytyPropertyIdList) {
          JdProperty jpDto = jpDao.load(propertyId, reqBean.getJcategoryId());
          if (jpDto != null && StringUtil.hasValue(jpDto.getPropertyId())) {

            JdAttributeLableBean lableBean = new JdAttributeLableBean();
            lableBean.setPropertyId(jpDto.getPropertyId());
            lableBean.setPropertyName(jpDto.getPropertyName());

            List<String> valueText = new ArrayList<String>();
            List<JdCommodityProperty> jdCommodiytyPropertyList = service.getJdCommodiytyPropertyValueList(
                reqBean.getCommodityCode(), jpDto.getPropertyId());
            if (jdCommodiytyPropertyList != null && jdCommodiytyPropertyList.size() > 0) {
              for (JdCommodityProperty jdCommodityProperty : jdCommodiytyPropertyList) {
                valueText.add(jdCommodityProperty.getValueText());
              }
              lableBean.setValueText(valueText);

              if (valueText != null && valueText.size() > 0) {
                attributeLableList.add(lableBean);
              }
            }

          }
        }
      }
    }

    reqBean.setAttributeLableListBean(attributeLableList);

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  public void getFullCategoryName(JdCategory category, StringBuilder sb) {
    JdCategoryDao dao = DIContainer.getDao(JdCategoryDao.class);
    sb.append("&&&__" + category.getCategoryName());
    if (!"0".equals(category.getParentId())) {
      JdCategory list = dao.load(category.getParentId());
      if (list != null) {
        JdCategory cate = list;
        getFullCategoryName(cate, sb);
      } else {
        return;
      }
    } else {
      return;
    }
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    JdPropertyBean nextBean = (JdPropertyBean) getRequestBean();

    if (nextBean != null) {
      setDisplayControl(nextBean);
      setCompleteMessage();
    }

    setRequestBean(nextBean);
  }

  /**
   * 完了メッセージを設定する
   * 
   * @param complete
   * @param moduleName
   */
  private void setCompleteMessage() {

    String[] params = getRequestParameter().getPathArgs();
    String complete = "";
    if (params.length >= 3) {
      complete = params[2];
    }
    if (complete.equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, 
          Messages.getString("web.action.back.catalog.CommodityEditSelectAction.5")));
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "京东属性编辑 初期化处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104029001";
  }

}
