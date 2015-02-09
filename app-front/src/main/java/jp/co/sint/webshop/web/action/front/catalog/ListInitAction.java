package jp.co.sint.webshop.web.action.front.catalog;

import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.dao.BrandDao;
import jp.co.sint.webshop.data.dao.CategoryDao;
import jp.co.sint.webshop.data.domain.CommodityDisplayOrder;
import jp.co.sint.webshop.data.domain.PriceList;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.CustomerMessage;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityListBean;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.SessionUrl;
import jp.co.sint.webshop.service.CustomerService;

import org.apache.log4j.Logger;

/**
 * U2040410:商品一覧のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ListInitAction extends ListBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String HALF_SPACE = " ";
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if (sesContainer.getSession() != null) {
      logger.info("当前ListInitAction:sessionID=" + sesContainer.getSession().getId()
          + "开始记录--------------------------------------------------------------------");
    } else {
      logger.info("当前ListInitAction:session缺失，开始记录--------------------------------------------------------------------");
    }

    CommodityListBean reqBean = getBean();
    reqBean.setDisplayCategoryFlg(false);
    // used for detect if a customer already left 5 or more message.
    String customerCode = getLoginInfo().getCustomerCode();
    reqBean.getList().clear();
    String[] urlParam = getRequestParameter().getPathArgs();

    if (urlParam.length == 2 && urlParam[0].equals("moveback")) {
      reqBean.setSearchWord(urlParam[1]);
    }else if (urlParam.length >= 1) {
      reqBean.setSearchCategoryCode(urlParam[0]);
    }

    // 検索条件の設定
    reqBean.setAlignmentSequence(CommodityDisplayOrder.BY_POPULAR_RANKING.getValue());
    PagerValue value = new PagerValue();

    if (StringUtil.isNullOrEmpty(getClient()) || getClient().equals(ClientType.OTHER)) {
      value.setPageSize(40);
    } else {
      WebshopConfig config = DIContainer.getWebshopConfig();
      value.setPageSize(config.getMobilePageSize());
    }
    reqBean.setUrl(null);
    if (reqBean.isSessionRead()) {
      SessionUrl url = getSessionContainer().getSessionUrl();
      String uri = getSessionContainer().getSessionUrl().getUrl();
      if (url != null) {
        reqBean.setUrl(uri);
        reqBean.setDisplayCategoryFlg(url.isDisplayCategoryFlg());
        // 品店精选
        if (StringUtil.hasValue(url.getSelected())) {
          reqBean.setSearchSelected(url.getSelected());
          reqBean.setSelectedFlg(true);
        } else {
          reqBean.setSelectedFlg(false);
        }
        // 商品目录
        if (StringUtil.hasValue(url.getCategoryCode())) {
          reqBean.setSearchCategoryCode(url.getCategoryCode());
        }
        // 品牌
        if (StringUtil.hasValue(url.getBrandCode())) {
          reqBean.setSearchBrandCode(url.getBrandCode());
        }
        // 评论区域
        if (StringUtil.hasValue(url.getReviewScore())) {
          reqBean.setSearchReviewScore(url.getReviewScore());
        }
        // 价格区域
        if (StringUtil.hasValue(url.getPriceType())) {
          CodeAttribute price = PriceList.fromValue(url.getPriceType());
          reqBean.setSearchPrice(url.getPriceType());
          if (price != null && !StringUtil.isNullOrEmpty(price.getName())) {
            String[] prices = price.getName().split(",");
            if (prices.length == 2) {
              reqBean.setSearchPriceStart(NumUtil.parse(prices[0]).abs().toString());
              reqBean.setSearchPriceEnd(NumUtil.parse(prices[1]).abs().toString());
            } else {
              reqBean.setSearchPriceStart(NumUtil.parse(prices[0]).abs().toString());
              reqBean.setSearchPriceEnd("9999999999");
            }
          }
        } else {
          if (StringUtil.hasValue(url.getPriceStart()) && NumUtil.isDecimal(url.getPriceStart())) {
            reqBean.setSearchPriceStart(NumUtil.parse(url.getPriceStart()).abs().toString());
            reqBean.setPriceStart(NumUtil.parse(url.getPriceStart()).abs().toString());
          }
          if (StringUtil.hasValue(url.getPriceEnd()) && NumUtil.isDecimal(url.getPriceEnd())) {
            reqBean.setSearchPriceEnd(NumUtil.parse(url.getPriceEnd()).abs().toString());
            reqBean.setPriceEnd(NumUtil.parse(url.getPriceEnd()).abs().toString());
            if (StringUtil.hasValue(url.getPriceStart()) && NumUtil.isDecimal(url.getPriceStart())
                && BigDecimalUtil.isAbove(NumUtil.parse(url.getPriceStart()), NumUtil.parse(url.getPriceEnd()))) {
              String tempSearchPriceStart = reqBean.getSearchPriceStart();
              reqBean.setSearchPriceStart(NumUtil.parse(url.getPriceEnd()).toString());
              reqBean.setSearchPriceEnd(NumUtil.parse(tempSearchPriceStart).toString());
              reqBean.setPriceStart(reqBean.getSearchPriceStart());
              reqBean.setPriceEnd(reqBean.getSearchPriceEnd());
            }
          }
          if (StringUtil.hasValue(url.getPriceStart()) && NumUtil.isDecimal(url.getPriceStart())) {
            if (url.getPriceEnd().equals("")) {
              reqBean.setSearchPriceEnd("9999999999");
            }
          }
        }
        // 属性1
        if (StringUtil.hasValue(url.getAttribute1())) {
          reqBean.setSearchAttribute1(url.getAttribute1());
        }
        // 属性2
        if (StringUtil.hasValue(url.getAttribute2())) {
          reqBean.setSearchAttribute2(url.getAttribute2());
        }
        // 属性3
        if (StringUtil.hasValue(url.getAttribute3())) {
          reqBean.setSearchAttribute3(url.getAttribute3());
        }
        // 排序
        if (StringUtil.hasValue(url.getSort())) {
          reqBean.setAlignmentSequence(url.getSort());
        }
        // 页数
        if (StringUtil.hasValue(url.getPageSize())) {
          if (NumUtil.isNum(url.getPageSize())) {
            value.setPageSize(Integer.parseInt(url.getPageSize()));
          } else {
            value.setPageSize(Integer.parseInt("1"));
          }
        }
        if (StringUtil.hasValue(url.getCurrentPage())) {
          if (NumUtil.isNum(url.getCurrentPage())) {
            value.setCurrentPage(Integer.parseInt(url.getCurrentPage()));
          } else {
            value.setCurrentPage(Integer.parseInt("1"));
          }
        }
        // 关键字
        if (StringUtil.hasValue(url.getSearchWord())) {
          reqBean.setSearchWord(url.getSearchWord());
        }
        // 关键字
        if (StringUtil.hasValue(url.getSearchWord())) {
          reqBean.setEncoded_searchWord(url.getSearchWord());
        }
        // 进口品
        if (StringUtil.hasValue(url.getImportCommodityType())) {
          reqBean.setImportCommodityType(url.getImportCommodityType());
        }
        // 清仓品
        if (StringUtil.hasValue(url.getClearCommodityType())) {
          reqBean.setClearCommodityType(url.getClearCommodityType());
        }
        // asahi商品
        if (StringUtil.hasValue(url.getReserveCommodityType1())) {
          reqBean.setReserveCommodityType1(url.getReserveCommodityType1());
        }
        // hot商品
        if (StringUtil.hasValue(url.getReserveCommodityType2())) {
          reqBean.setReserveCommodityType2(url.getReserveCommodityType2());
        }
        // 商品展示区分
        if (StringUtil.hasValue(url.getReserveCommodityType3())) {
          reqBean.setReserveCommodityType3(url.getReserveCommodityType3());
        }
        // 预留商品区分1
        if (StringUtil.hasValue(url.getNewReserveCommodityType1())) {
          reqBean.setNewReserveCommodityType1(url.getNewReserveCommodityType1());
        }
        // 预留商品区分2
        if (StringUtil.hasValue(url.getNewReserveCommodityType2())) {
          reqBean.setNewReserveCommodityType2(url.getNewReserveCommodityType2());
        }
        // 预留商品区分3
        if (StringUtil.hasValue(url.getNewReserveCommodityType3())) {
          reqBean.setNewReserveCommodityType3(url.getNewReserveCommodityType3());
        }
        // 预留商品区分4
        if (StringUtil.hasValue(url.getNewReserveCommodityType4())) {
          reqBean.setNewReserveCommodityType4(url.getNewReserveCommodityType4());
        }
        // 预留商品区分5
        if (StringUtil.hasValue(url.getNewReserveCommodityType5())) {
          reqBean.setNewReserveCommodityType5(url.getNewReserveCommodityType5());
        }
      }
    }
    reqBean.setPagerValue(value);
    String str = reqBean.getSearchWord();
    str = str.replace("/", HALF_SPACE);
    str = str.replace("\\", HALF_SPACE);
    reqBean.setSearchWord(str);
    reqBean.setEncoded_searchWord(str);

    CommodityContainerCondition condition = setSearchCondition(reqBean);
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("importCommodityType"))) {
      condition.setImportCommodityType(getRequestParameter().get("importCommodityType"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("clearCommodityType"))) {
      condition.setClearCommodityType(getRequestParameter().get("clearCommodityType"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("reserveCommodityType1"))) {
      condition.setReserveCommodityType1(getRequestParameter().get("reserveCommodityType1"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("reserveCommodityType2"))) {
      condition.setReserveCommodityType2(getRequestParameter().get("reserveCommodityType2"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("reserveCommodityType3"))) {
      condition.setReserveCommodityType3(getRequestParameter().get("reserveCommodityType3"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("newReserveCommodityType1"))) {
      condition.setNewReserveCommodityType1(getRequestParameter().get("newReserveCommodityType1"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("newReserveCommodityType2"))) {
      condition.setNewReserveCommodityType2(getRequestParameter().get("newReserveCommodityType2"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("newReserveCommodityType3"))) {
      condition.setNewReserveCommodityType3(getRequestParameter().get("newReserveCommodityType3"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("newReserveCommodityType4"))) {
      condition.setNewReserveCommodityType4(getRequestParameter().get("newReserveCommodityType4"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("newReserveCommodityType5"))) {
      condition.setNewReserveCommodityType5(getRequestParameter().get("newReserveCommodityType5"));
    }

    // 商品一覧の取得
    setCommodityList(reqBean, condition);

    CategoryDao categoryDao = DIContainer.getDao(CategoryDao.class);
    Category category = categoryDao.load(reqBean.getSearchCategoryCode());
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    if (category == null) {
      reqBean.setMetaKeyword("");
      reqBean.setMetaDescription("");
      reqBean.setCategoryName("");
      reqBean.setCategoryFlag(false);
    } else {
      reqBean.setMetaKeyword(category.getMetaKeyword());
      reqBean.setMetaDescription(category.getMetaDescription());
      reqBean.setCategoryName(utilService.getNameByLanguage(category.getCategoryNamePc(), category.getCategoryNamePcEn(), category
          .getCategoryNamePcJp()));
      if (StringUtil.hasValue(reqBean.getSearchCategoryCode()) && StringUtil.isNullOrEmpty(reqBean.getSearchBrandCode())
          && StringUtil.isNullOrEmpty(reqBean.getSearchReviewScore()) && StringUtil.isNullOrEmpty(reqBean.getSearchPriceStart())
          && StringUtil.isNullOrEmpty(reqBean.getSearchPriceEnd()) && StringUtil.isNullOrEmpty(reqBean.getSearchAttribute1())
          && StringUtil.isNullOrEmpty(reqBean.getSearchAttribute2()) && StringUtil.isNullOrEmpty(reqBean.getSearchAttribute3())
          && StringUtil.isNullOrEmpty(reqBean.getSearchWord())) {
        if (category.getDepth() == 1) {
          reqBean.setCategoryFlag(true);
        } else {
          reqBean.setCategoryFlag(false);
        }
      } else {
        reqBean.setCategoryFlag(false);
      }
      // 20130703 txw add start
      reqBean.setTitle(utilService.getNameByLanguage(category.getTitle(), category.getTitleEn(), category.getTitleJp()));
      reqBean.setDescription(utilService.getNameByLanguage(category.getDescription(), category.getDescriptionEn(), category
          .getDescriptionJp()));
      reqBean.setKeyword(utilService.getNameByLanguage(category.getKeyword(), category.getKeywordEn(), category.getKeywordJp()));
      // 20130703 txw add end
    }
    if (StringUtil.hasValue(reqBean.getSearchBrandCode())
        && (StringUtil.isNullOrEmpty(reqBean.getSearchCategoryCode()) || (StringUtil.hasValue(reqBean.getSearchCategoryCode()) && reqBean
            .getSearchCategoryCode().equals("0"))) && StringUtil.isNullOrEmpty(reqBean.getSearchReviewScore())
        && StringUtil.isNullOrEmpty(reqBean.getSearchPriceStart()) && StringUtil.isNullOrEmpty(reqBean.getSearchPriceEnd())
        && StringUtil.isNullOrEmpty(reqBean.getSearchAttribute1()) && StringUtil.isNullOrEmpty(reqBean.getSearchAttribute2())
        && StringUtil.isNullOrEmpty(reqBean.getSearchAttribute3()) && StringUtil.isNullOrEmpty(reqBean.getSearchWord())) {
      reqBean.setBrandFlag(true);
      // 20130809 txw add start
      BrandDao dao = DIContainer.getDao(BrandDao.class);
      Brand brand = dao.load(reqBean.getSearchBrandCode());

      if (brand != null) {
        reqBean.setTitle(utilService.getNameByLanguage(brand.getTitle(), brand.getTitleEn(), brand.getTitleJp()));
        reqBean.setDescription(utilService.getNameByLanguage(brand.getDescription(), brand.getDescriptionEn(), brand
            .getDescriptionJp()));
        reqBean.setKeyword(utilService.getNameByLanguage(brand.getKeyword(), brand.getKeywordEn(), brand.getKeywordJp()));
      }

      // 20130809 txw add end
    } else {
      reqBean.setBrandFlag(false);
    }
    // reqBean.setSearchWord(WebUtil.escapeXml(reqBean.getSearchWord()));
    reqBean.setSearchWord(reqBean.getSearchWord());
    setNextUrl(null);
    reqBean.setSelectCommodityCode(null);
    setRequestBean(reqBean);
    
    // query database to find the user whether left 5 or more message.
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    Date dateStart = DateUtil.getSysdate();
    dateStart.setHours(0);
    dateStart.setMinutes(0);
    dateStart.setSeconds(0);
    Date dateEnd = DateUtil.addDate(dateStart, 1);
    List<CustomerMessage> list = customerService.getCustomerMessageList(dateStart, dateEnd, customerCode);
    if(customerCode != null && customerCode != "" && list.size() >= 5) {
      reqBean.setFiveMessageFlag(false);
    }
    else {
      reqBean.setFiveMessageFlag(true);
    }
      
    if (sesContainer.getSession() != null) {
      logger.info("当前ListInitAction:sessionID=" + sesContainer.getSession().getId()
          + "结束记录--------------------------------------------------------------------");
    } else {
      logger.info("当前ListInitAction:session缺失，结束记录--------------------------------------------------------------------");
    }
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CommodityListBean reqBean = (CommodityListBean) getRequestBean();
    setPictureMode(reqBean);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    /*
     * CommodityListBean reqBean = getBean(); if (!reqBean.isSessionRead()) { if
     * (StringUtil.isNullOrEmpty(reqBean.getSearchWord()) &&
     * StringUtil.isNullOrEmpty(reqBean.getSearchTagCode())) {
     * addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
     * .getString("web.action.front.order.ListInitAction.0"))); return false; }
     * }
     */
    return true;
  }
}
