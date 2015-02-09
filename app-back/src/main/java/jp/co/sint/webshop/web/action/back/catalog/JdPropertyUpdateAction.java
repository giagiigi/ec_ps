package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.dao.JdPropertyDao;
import jp.co.sint.webshop.data.dao.JdPropertyValueDao;
import jp.co.sint.webshop.data.domain.IsOrNot;
import jp.co.sint.webshop.data.domain.SyncFlagJd;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.JdCommodityProperty;
import jp.co.sint.webshop.data.dto.JdProperty;
import jp.co.sint.webshop.data.dto.JdPropertyValue;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.JdPropertyBean;
import jp.co.sint.webshop.web.bean.back.catalog.JdPropertyBean.JdPropertyValueBean;
import jp.co.sint.webshop.web.bean.back.catalog.JdPropertyBean.PropertyBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040290:京东属性编辑
 * 
 * @author System Integrator Corp.
 */
public class JdPropertyUpdateAction extends JdPropertyBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  public boolean validate() {
    boolean isValid = true;
    JdPropertyBean reqBean = getBean();

    /**
     * 设置属性值的名称(效验属性和属性值)
     */
    JdPropertyValueDao valueDao = DIContainer.getDao(JdPropertyValueDao.class);
    for (PropertyBean pro : reqBean.getCommodityStandardPopList()) {
      isValid &= validateProperty(pro, valueDao);
    }
    return isValid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    JdPropertyBean reqBean = getBean();

    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = reqBean.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }

    // SKU情報の取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CCommodityHeader header = service.getCCommodityHeader(shopCode, reqBean.getCommodityCode());
    header.setJdCategoryId(reqBean.getJcategoryId());
    // 20141120 hdh add start
    header.setSyncFlagJd(SyncFlagJd.SYNCVISIBLE.longValue());
    // 20141120 hdh add end

    // 更新処理
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    List<JdCommodityProperty> propertys = convertCommodityPropertyBeanListToDto(reqBean.getCommodityStandardPopList());
    // 更新header
    ServiceResult result = catalogService.updateJdCommodityProperty(header, propertys);
    if (result.hasError()) {
      setRequestBean(reqBean);
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.back.catalog.TmallCommodityEditUpdateAction.0")));
        }
      }

      return BackActionResult.SERVICE_ERROR;
    }

    // 次画面のBeanを設定する
    setRequestBean(reqBean);

    // 完了パラメータを渡して、初期画面へ遷移する
    setNextUrl("/app/catalog/jd_property/init/" + getLoginInfo().getShopCode() + "/" + reqBean.getCommodityCode() + "/"
        + WebConstantCode.COMPLETE_UPDATE);

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "京东属性编辑 更新处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104029002";
  }

  private boolean validateProperty(PropertyBean pro, JdPropertyValueDao valueDao) {
    boolean pass = true;
    ValidationSummary summary = BeanValidator.validate(pro);
    if (summary.hasError()) {
      addErrorMessage("京东属性设定错误。");
    }
    JdPropertyDao propertyDao = DIContainer.getDao(JdPropertyDao.class);
    JdProperty jdProperty = propertyDao.load(pro.getPropertyId(), pro.getCategoryId());
    String propertyName = pro.getPropertyName().replace("*", "");
    if (jdProperty != null && IsOrNot.IS.longValue().equals(jdProperty.getIsReq())) {
      if (pro.getValues() == null || pro.getValues().size() == 0) {
        addErrorMessage(propertyName + "必须输入。");
        pass &= false;
      }
      for (JdPropertyValueBean value : pro.getValues()) {
        if (!StringUtil.hasValue(value.getValueId())) {
          addErrorMessage(propertyName + "必须输入。");
          return false;
        }
        if (StringUtil.hasValue(value.getValueName()) && value.getValueName().length() >= 50) {
          addErrorMessage(propertyName + "长度不能大于50位。");
        }
        if (value.getValueName() == null || "".equals(value.getValueName())) {
          JdPropertyValue v = valueDao.load(value.getValueId(), value.getPropertyId());
          if (v != null && v.getValueName() != null) {
            value.setValueName(v.getValueName());
          } else {
            addErrorMessage(propertyName + "必须输入。");
            pass &= false;
          }
        }
      }
    } else {
      for (JdPropertyValueBean value : pro.getValues()) {
        JdPropertyValue v = valueDao.load(value.getValueId(), value.getPropertyId());
        if (v != null && v.getValueName() != null) {
          value.setValueName(v.getValueName());
        }
        if (StringUtil.hasValue(value.getValueName()) && value.getValueName().length() >= 50) {
          addErrorMessage(propertyName + "长度不能大于50位。");
          pass &= false;
        }
      }
    }
    return pass;
  }

}
