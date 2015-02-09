package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.dao.HotSaleCommodityDao;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.HotSaleCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityHotSaleBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 套餐设定のアクションクラスです
 * 
 * @author KS.
 */
public class CommodityHotSaleRegisterAction extends CommodityHotSaleInitAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  @Override
  public boolean validate() {
    String languageCode = getRequestParameter().getPathArgs()[0];
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHotSaleBean bean = getBean();
    if (!validateBean(bean)) {
      return false;
    }


    
    if (languageCode.equals("zh")) {
      if (StringUtil.isNullOrEmpty(bean.getSearchCommodityCodezh())) {
        addErrorMessage("商品编号不能为空");
        return false;
      } else {
        CommodityHeader header = catalogService.getCommodityHeader("00000000", bean.getSearchCommodityCodezh());
        if (SetCommodityFlg.OBJECTIN.longValue().equals(header.getSetCommodityFlg())) {
          addErrorMessage("套装品不能设定为热销商品");
          return false;
        }
      }
      if (StringUtil.isNullOrEmpty(bean.getInputSortRankzh())) {
        addErrorMessage("排列顺序不能为空");
        return false;
      }
    } else if (languageCode.equals("jp")) {
      if (StringUtil.isNullOrEmpty(bean.getSearchCommodityCodejp())) {
        addErrorMessage("商品编号不能为空");
        return false;
      } else {
        CommodityHeader header = catalogService.getCommodityHeader("00000000", bean.getSearchCommodityCodejp());
        if (SetCommodityFlg.OBJECTIN.longValue().equals(header.getSetCommodityFlg())) {
          addErrorMessage("套装品不能设定为热销商品");
          return false;
        }
      }
      if (StringUtil.isNullOrEmpty(bean.getInputSortRankjp())) {
        addErrorMessage("排列顺序不能为空");
        return false;
      }
    } else if  (languageCode.equals("us")) {
      if (StringUtil.isNullOrEmpty(bean.getSearchCommodityCodeus())) {
        addErrorMessage("商品编号不能为空");
        return false;
      } else {
        CommodityHeader header = catalogService.getCommodityHeader("00000000", bean.getSearchCommodityCodeus());
        if (SetCommodityFlg.OBJECTIN.longValue().equals(header.getSetCommodityFlg())) {
          addErrorMessage("套装品不能设定为热销商品");
          return false;
        }
      }
      if (StringUtil.isNullOrEmpty(bean.getInputSortRankus())) {
        addErrorMessage("排列顺序不能为空");
        return false;
      }
    } else {
      if (StringUtil.isNullOrEmpty(bean.getSearchCommodityCodezh())) {
        addErrorMessage("商品编号不能为空");
        return false;
      } else {
        CommodityHeader header = catalogService.getCommodityHeader("00000000", bean.getSearchCommodityCodezh());
        if (SetCommodityFlg.OBJECTIN.longValue().equals(header.getSetCommodityFlg())) {
          addErrorMessage("套装品不能设定为热销商品");
          return false;
        }
      }
      if (StringUtil.isNullOrEmpty(bean.getInputSortRankzh())) {
        addErrorMessage("排列顺序不能为空");
        return false;
      }
    }

    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommodityHotSaleBean reqBean = getBean();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    HotSaleCommodityDao saleDao = DIContainer.getDao(HotSaleCommodityDao.class);

    String searchCommodityCode = "";
    String dtoLangCode = "";
    String rank = "";
    String languageCode = getRequestParameter().getPathArgs()[0];
    if (languageCode.equals("zh")) {
      searchCommodityCode = reqBean.getSearchCommodityCodezh();
      dtoLangCode = "zh-cn";
      rank = reqBean.getInputSortRankzh();
    } else if (languageCode.equals("jp")) {
      searchCommodityCode = reqBean.getSearchCommodityCodejp();
      dtoLangCode = "ja-jp";
      rank = reqBean.getInputSortRankjp();
    } else if  (languageCode.equals("us")) {
      searchCommodityCode = reqBean.getSearchCommodityCodeus();
      dtoLangCode = "en-us";
      rank = reqBean.getInputSortRankus();
    } else {
      searchCommodityCode = reqBean.getSearchCommodityCodezh();
      dtoLangCode = "zh-cn";
      rank = reqBean.getInputSortRankzh();
    }
    
    HotSaleCommodity hsc = saleDao.load(searchCommodityCode, dtoLangCode);
    if (hsc != null ) {
      addErrorMessage("该商品已经登录");
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }
    
    // 添加商品存在
    CommodityInfo info = catalogService.getCommodityInfo("00000000", searchCommodityCode);
    if (info == null || info.getHeader() == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "商品:" + searchCommodityCode));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // DB登録用DTOを生成する
    HotSaleCommodity dto = new HotSaleCommodity();
    dto.setCommodityCode(searchCommodityCode);
    dto.setCommodityName(info.getHeader().getCommodityName());
    dto.setLanguageCode(dtoLangCode);
    dto.setSortRank(NumUtil.toLong(rank));
    saleDao.insert(dto);

    addInformationMessage(WebMessage.get(ActionErrorMessage.REGISTER_SECCESS, "套餐明细商品:" + searchCommodityCode));
    setRequestBean(reqBean);
    super.callService();
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("套餐明细登录处理");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104019002";
  }

}
