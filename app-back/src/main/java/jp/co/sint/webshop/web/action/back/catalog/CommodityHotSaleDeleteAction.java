package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.dao.HotSaleCommodityDao;
import jp.co.sint.webshop.data.dto.HotSaleCommodity;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityHotSaleBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 套餐设定のアクションクラスです
 * 
 * @author KS.
 */
public class CommodityHotSaleDeleteAction  extends CommodityHotSaleInitAction {

  /**
   * 权限确认。
   * 
   * @return 有删除权限时返回true
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_DELETE.isGranted(getLoginInfo());
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
    CommodityHotSaleBean bean = getBean();
    
    String languageCode = getRequestParameter().getPathArgs()[0];
    String dtoLangCode = "";
    // 选择套餐明细商品的编号取得
    String[] values;
    if (languageCode.equals("zh")) {
      values = getRequestParameter().getAll("checkBox");
      dtoLangCode = "zh-cn";
    } else if (languageCode.equals("jp")) {
      values = getRequestParameter().getAll("checkBoxjp");
      dtoLangCode = "ja-jp";
    } else if (languageCode.equals("us")) {
      values = getRequestParameter().getAll("checkBoxus");
      dtoLangCode = "en-us";
    } else {
      values = getRequestParameter().getAll("checkBox");
      dtoLangCode = "zh-cn";
    }
    
    // チェックボックスが選択されているか
    if (StringUtil.isNullOrEmpty(values[0])) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED, "热销商品"));
      setNextUrl(null);
      this.setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 删除处理
    HotSaleCommodityDao dao = DIContainer.getDao(HotSaleCommodityDao.class);
    for (String childCommodityCode : values) {
      HotSaleCommodity dtoDetail = dao.load(childCommodityCode,dtoLangCode);
      if (dtoDetail != null) {
        dao.delete(dtoDetail);
      }
    }
    
    addErrorMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "热销商品"));
    setRequestBean(bean);
    return super.callService();
  }

  public void prerender() {
    super.prerender();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("热销商品删除处理");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104019003";
  }

}
