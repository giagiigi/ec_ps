package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dao.JdStockAllocationDao;
import jp.co.sint.webshop.data.dao.TmallStockAllocationDao;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.JdStockAllocation;
import jp.co.sint.webshop.data.dto.TmallStockAllocation;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteBean.CommodityConstituteDetailBean;
import jp.co.sint.webshop.web.text.back.Messages;

public class CommodityConstituteRegisterAction extends WebBackAction<CommodityConstituteBean> {

  @Override
  public boolean authorize() {
    return Permission.COMMODITY_CONSTITUTE_UPDATE.isGranted(getLoginInfo());
  }

  @Override
  public boolean validate() {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityConstituteBean bean = getBean();
    String code = bean.getOriginalCommodityCode();
    if (!StringUtil.hasValue(code)) {
      String[] path = getRequestParameter().getPathArgs();
      if (path.length > 0) {
        code = path[0];
      }
    }
    if (!StringUtil.hasValue(code)) {
      return validateBean(bean);
    }
    List<CCommodityHeader> list = service.getCCommodityHeaderByOriginalCommodityCode(code);
    if (list.size() > 0) {
      if (StringUtil.hasValue(bean.getOriginalCommodityCode())) {
        for (int i = 0; i < list.size(); i++) {
          if (list.get(i).getCommodityCode().equals(code) && StringUtil.hasValue(list.get(i).getOriginalCommodityCode())) {
            addErrorMessage("该商品是组合商品");
            bean.setDeleteButtonFlg(false);
            return false;
          }
        }
        return validateBean(bean);
      } else {
        return true;
      }
    }
    addErrorMessage("原商品编号不存在");
    bean.setDeleteButtonFlg(false);
    return false;
  }

  @Override
  public WebActionResult callService() {
    CommodityConstituteBean bean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    String code = bean.getOriginalCommodityCode();
    String[] path = getRequestParameter().getPathArgs();
    List<CCommodityHeader> ccheadList = null;
    if (!StringUtil.hasValue(code)) {
      if (StringUtil.hasValueAllOf(path)) {
        code = path[0];
        bean.setOriginalCommodityCode(code);
      }
    }
    ccheadList = service.getCCommodityHeaderByOriginalCommodityCode(code);
    if (ccheadList != null) {
      if (ccheadList.size() > 0) {
        List<CommodityConstituteDetailBean> detailbeanList = new ArrayList<CommodityConstituteDetailBean>();
        for (CCommodityHeader cchead : ccheadList) {
          CCommodityDetail detail = service.getCCommodityDetailByCommodityCode(cchead.getCommodityCode()).get(0);
          BigDecimal price = detail.getUnitPrice();
          BigDecimal amount = BigDecimalUtil.tempFormatLong(cchead.getCombinationAmount());
          CommodityConstituteDetailBean detailbean = new CommodityConstituteDetailBean();
          detailbean.setCommodityCode(cchead.getCommodityCode());
          detailbean.setCommodityName(cchead.getCommodityName());
          detailbean.setOriginalCommodityCode(cchead.getOriginalCommodityCode());
          detailbean.setUnitPrice(price);
          detailbean.setCombinationAmount(cchead.getCombinationAmount());
          if (price != null && amount != null) {
            BigDecimal sumPrice = price.multiply(amount);
            detailbean.setSumPrice(sumPrice);
          }
          detailbean.setUseFlg(detail.getUseFlg());
          detailbean.setTmallUseFlg(detail.getTmallUseFlg());
          // 2014/06/05 库存更新对应 ob_卢 update start
          detailbean.setJdUseFlg(detail.getJdUseFlg());
          // 2014/06/05 库存更新对应 ob_卢 update end
          detailbeanList.add(detailbean);
          if (cchead.getCommodityCode().equals(code)) {
            detailbean.setButtonFlg(false);
          } else {
            detailbean.setButtonFlg(true);
            // 2014/06/05 库存更新对应 ob_卢 add start
            // 删除按钮显示Flg处理
            // 已设定tmall库存比例，并且比例值大于0时
            detailbean.setDelBtnFlg(true);
            TmallStockAllocationDao dao = DIContainer.getDao(TmallStockAllocationDao.class);
            TmallStockAllocation tmallInfo = dao.load(detail.getShopCode(), detail.getSkuCode());
            JdStockAllocationDao jdDao = DIContainer.getDao(JdStockAllocationDao.class);
            JdStockAllocation jdInfo = jdDao.load(detail.getShopCode(), detail.getSkuCode());
            if((tmallInfo != null && !NumUtil.isNull(tmallInfo.getScaleValue()) && tmallInfo.getScaleValue() > 0L)
                || (jdInfo != null && !NumUtil.isNull(jdInfo.getScaleValue()) && jdInfo.getScaleValue() > 0L)){
              detailbean.setDelBtnFlg(false);
            }
            // 2014/06/05 库存更新对应 ob_卢 add end
          }
        }
        bean.setDetail(detailbeanList);
        bean.setDeleteButtonFlg(true);
      }
    }
    if (ccheadList.size() > 1) {
      bean.setCheck(true);
    } else {
      bean.setCheck(false);
    }
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  public String getActionName() {
    return Messages.getString("web.bean.back.catalog.CommodityConstituteInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104015002";
  }

}
