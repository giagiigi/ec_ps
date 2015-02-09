package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.JdCommodityProperty;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.JdPropertyBean;
import jp.co.sint.webshop.web.bean.back.catalog.JdPropertyBean.JdPropertyValueBean;
import jp.co.sint.webshop.web.bean.back.catalog.JdPropertyBean.PropertyBean;

/**
 * U1040190:京东属性编辑のアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class JdPropertyBaseAction extends WebBackAction<JdPropertyBean> {


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
  public boolean validate() {
    return true;
  }

  public static List<JdCommodityProperty> copyJdCommodityProperty(List<PropertyBean> target,
      List<PropertyBean> source) {
    List<JdCommodityProperty> sourceList1 = convertCommodityPropertyBeanListToDto(source);
    List<JdCommodityProperty> sourceList2 = convertCommodityPropertyBeanListToDto(target);
    for (int i = 0; i < sourceList2.size(); i++) {
      sourceList1.add(sourceList2.get(i));
    }
    return sourceList1;
  }

  public static List<JdCommodityProperty> convertCommodityPropertyBeanListToDto(List<PropertyBean> beans) {
    List<JdCommodityProperty> propertys = new ArrayList<JdCommodityProperty>();
    for (PropertyBean pro : beans) {
      for (JdPropertyValueBean value : pro.getValues()) {
        JdCommodityProperty property = new JdCommodityProperty();
        property.setPropertyId(pro.getPropertyId());
        property.setValueId(value.getValueId());
        property.setValueText(value.getValueName());
        property.setCommodityCode(pro.getCommodityCode());
        propertys.add(property);
      }
    }
    return propertys;
  }
  
  /**
   * 画面に表示するボタンの制御を行います。
   * 
   * @param reqBean
   */
  public void setDisplayControl(JdPropertyBean reqBean) {
    // ショップユーザで商品登録・更新権限を持つ場合
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      reqBean.setDisplayUpdateButton(true);
    }
  }
}
