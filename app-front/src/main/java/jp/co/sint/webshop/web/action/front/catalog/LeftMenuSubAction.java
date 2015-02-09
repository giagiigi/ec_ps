package jp.co.sint.webshop.web.action.front.catalog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.PriceList;
import jp.co.sint.webshop.data.domain.ReviewScore;
import jp.co.sint.webshop.data.dto.CategoryAttribute;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CategoryData;
import jp.co.sint.webshop.service.catalog.LeftMenuListBean;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.LeftMenuBean;
import jp.co.sint.webshop.web.bean.front.catalog.LeftMenuBean.DetailBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;
import jp.co.sint.webshop.web.webutility.SessionUrl;

import org.apache.log4j.Logger;

/**
 * 左边菜单
 * 
 * @author kousen
 *
 */
public class LeftMenuSubAction extends WebSubAction {
   private String categoryAttributeValue;
  @Override
  public void callService() {
    
    String HALF_SPACE = " ";
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前LeftMenuSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前LeftMenuSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }
	 UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    LeftMenuBean reqBean = new LeftMenuBean();
//    if ((StringUtil.hasValue(getRequestParameter().get("searchCategoryCode")) || StringUtil.hasValue(getRequestParameter().get("searchTagCode"))) 
//        && StringUtil.isNullOrEmpty(reqBean.getSearchWord())) {
//      reqBean.setFlag(false);
//    } else {
      SessionUrl url = getSessionContainer().getSessionUrl();
      Map<String, DetailBean> brandMap = new HashMap<String, DetailBean>();
      List<DetailBean> brandList = new ArrayList<DetailBean>();
      Map<String, DetailBean> reciewMap = new HashMap<String, DetailBean>();
      List<DetailBean> reviewList = new ArrayList<DetailBean>();
      Map<String, DetailBean> priceMap = new HashMap<String, DetailBean>();
      List<DetailBean> priceList = new ArrayList<DetailBean>();
      Map<String, Map<String, DetailBean>> attributeMap = new HashMap<String, Map<String, DetailBean>>();
      Map<String, DetailBean> attributeSubMap = new HashMap<String, DetailBean>();
      List<DetailBean> attributeList1 = new ArrayList<DetailBean>();
      List<DetailBean> attributeList2 = new ArrayList<DetailBean>();
      List<DetailBean> attributeList3 = new ArrayList<DetailBean>();
      if (url != null) {
        // 商品目录
        if (StringUtil.hasValue(getRequestParameter().get("searchCategoryCode"))) {
          reqBean.setCategoryCode(getRequestParameter().get("searchCategoryCode"));
          reqBean.setSearchWord(getRequestParameter().get("searchWord"));
        } else if (StringUtil.hasValue(getRequestParameter().get("searchTagCode"))) {
          reqBean.setCategoryCode(getRequestParameter().get("searchCategoryCode"));
          reqBean.setSearchWord(getRequestParameter().get("searchWord"));
        } else {
          reqBean.setCategoryCode(url.getCategoryCode());
          // 品牌编号
          reqBean.setBrandCode(url.getBrandCode());
          // 品店精选
          reqBean.setSelected(url.getSelected());
          // 属性1
          reqBean.setAttribute1(url.getAttribute1());
          // 属性2
          reqBean.setAttribute2(url.getAttribute2());
          // 属性3
          reqBean.setAttribute3(url.getAttribute3());
          // 价格区域
          reqBean.setPriceType(url.getPriceType());
          if (StringUtil.hasValue(url.getPriceStart()) && NumUtil.isDecimal(url.getPriceStart())) {
            reqBean.setPriceStart(NumUtil.parse(url.getPriceStart()).abs().toString());
          }
          if (StringUtil.hasValue(url.getPriceEnd()) && NumUtil.isDecimal(url.getPriceEnd())) {
            reqBean.setPriceEnd(NumUtil.parse(url.getPriceEnd()).abs().toString());
            if (StringUtil.hasValue(url.getPriceStart()) && NumUtil.isDecimal(url.getPriceStart())
                && BigDecimalUtil.isAbove(NumUtil.parse(url.getPriceStart()), NumUtil.parse(url.getPriceEnd()))) {
              String tempSearchPriceStart = reqBean.getPriceStart();
              reqBean.setPriceStart(NumUtil.parse(url.getPriceEnd()).toString());
              reqBean.setPriceEnd(NumUtil.parse(tempSearchPriceStart).toString());
            }
          }
          if (StringUtil.hasValue(url.getPriceStart()) && NumUtil.isDecimal(url.getPriceStart())) {
            if (url.getPriceEnd().equals("")) {
              reqBean.setPriceEnd("9999999999");
            }
          }
          // 评论区域
          reqBean.setReviewScore(url.getReviewScore());
          // 关键字
          reqBean.setSearchWord(url.getSearchWord());
        }
      } else {
        reqBean.setCategoryCode(getRequestParameter().get("searchCategoryCode"));
        reqBean.setSearchWord(getRequestParameter().get("searchWord"));
      }
      String str = reqBean.getSearchWord();
      str = str.replace("/", HALF_SPACE);
      str = str.replace("\\", HALF_SPACE);
      reqBean.setSearchWord(str);
      CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
      
      List<CategoryData> categoryListOld = service.getCategoryTree(null, null, reqBean.getCategoryCode());
      List<CategoryData> categoryList =  new ArrayList<CategoryData>();
      for (int i = 0; i < categoryListOld.size(); i++ ){
        CategoryData cd = (CategoryData)categoryListOld.get(i);
        if(!cd.getCategoryCode().equals("02") && !cd.getCategoryCode().equals("01") && 
            !cd.getCategoryCode().equals("AA")&& !cd.getCategoryCode().equals("ZZ") && !cd.getCategoryCode().equals("98100")){
          categoryList.add(cd);
        }
      }
      List<CategoryAttribute> categoryAttributeList = service.getCategoryAttributeList(StringUtil.isNullOrEmpty(reqBean.getCategoryCode())?"0":reqBean.getCategoryCode());
      

      List<LeftMenuListBean> leftList = service.getLeftMenuInfo(reqBean.getSelected(),reqBean.getCategoryCode(), reqBean.getBrandCode(), reqBean.getReviewScore()
          , reqBean.getPriceType(), reqBean.getPriceStart(), reqBean.getPriceEnd(), reqBean.getAttribute1(), reqBean.getAttribute2(), reqBean.getAttribute3(), reqBean.getSearchWord());
      
//      List<String> codeList = new ArrayList<String>();
//      String longCode = "{";
//      for (int i = 0 ;i< leftList.size() ;i++) {
//        if (i == 0){
//          longCode = longCode + "\""+leftList.get(i).getCommodityCode() + "\"" ;
//        }else{
//          longCode = longCode + ",\""+leftList.get(i).getCommodityCode() + "\"" ;
//        }
//      }      
//      longCode = longCode+ "}";
////      String[] codes = (String[])codeList.toArray(new String[codeList.size()]);
//      List<CategoryData>  CategoryDataList =  service.getCategoryDataList(categoryList.get(1).getDepth(),categoryList.get(0).getCategoryCode(),longCode);
//      
      
//      //add by twh 2013年6月5日  start  左菜单分类商品数量统计
//      if(CategoryDataList != null && CategoryDataList.size()>0){
//        for (CategoryData data : CategoryDataList){
//          for (CategoryData categoryData : categoryList) {
//            Long commodityCount = categoryData.getCategoryCount();
//            if (commodityCount == null) {
//              commodityCount = 0L;
//            }
//            if (categoryData.getCategoryCode().equals(data.getCategoryStepOneCode())) {
//              categoryData.setCommodityCount(data.getCommodityCount());
//            } 
//          }
//        }
//      }
//      
//      //add by twh 2013年6月5日  end 
      for (LeftMenuListBean bean : leftList) {
        if (bean.getCategoryPath() != null) {
          String[] path = bean.getCategoryPath().split("#");
          for (String element : path) {
            String[] path1 = element.split("~");
            List<String> tempList = Arrays.asList(path1);
            boolean flag = false;
            for (CategoryData categoryData : categoryList) {
              Long commodityCount = categoryData.getCommodityCount();
              if (commodityCount == null) {
                commodityCount = 0L;
              }
              if (tempList.contains(categoryData.getCategoryCode())) {
                commodityCount += 1;
                flag = true;
              } 
              if (flag) {
                categoryData.setCommodityCount(commodityCount);
              }
            }
          }
        }
        if (StringUtil.hasValue(bean.getBrandCode())) {
          DetailBean brandDetail = brandMap.get(bean.getBrandCode());
          if (brandDetail == null) {
            brandDetail = new DetailBean();
            brandDetail.setDetailCode(bean.getBrandCode());
          //modified by cs_yuli 20120514 start 
			String brandName = utilService.getNameByLanguage(bean.getBrandName(),bean.getBrandNameEn(),bean.getBrandNameJp());
      		brandDetail.setDetailName(brandName); 
          //modified by cs_yuli 20120514 end  
            brandDetail.setCommodityCount(1L);
            brandDetail.setCommodityPopularRank(bean.getCommodityPopularRank());
            brandMap.put(bean.getBrandCode(), brandDetail);
          } else {
            brandDetail.setCommodityCount(brandDetail.getCommodityCount() + 1L);
            brandDetail.setCommodityPopularRank(brandDetail.getCommodityPopularRank() + bean.getCommodityPopularRank());
            // 20120323 shen add start
            // 防止超出边界
            if (brandDetail.getCommodityPopularRank() > 99999999L) {
              brandDetail.setCommodityPopularRank(99999999L);
            }
            // 20120323 shen add end
          }
        }
        DetailBean reciewDetail = reciewMap.get(bean.getReviewScore());
        if (reciewDetail == null) {
          reciewDetail = new DetailBean();
          reciewDetail.setDetailCode(bean.getReviewScore());
          reciewDetail.setCommodityCount(1L);
          reciewMap.put(bean.getReviewScore(), reciewDetail);
        } else {
          reciewDetail.setCommodityCount(reciewDetail.getCommodityCount() + 1L);
        }
        Long price = NumUtil.parse(bean.getPrice()).longValue();
        String[] prices = PriceList.ONE.getName().split(",");
        if (price >= Long.valueOf(prices[0]) && price <= Long.valueOf(prices[1])) {
          DetailBean priceDetail = priceMap.get("1");
          if (priceDetail == null) {
            priceDetail = new DetailBean();
            priceDetail.setDetailCode("1");
            priceDetail.setCommodityCount(1L);
            priceMap.put("1", priceDetail);
          } else {
            priceDetail.setCommodityCount(priceDetail.getCommodityCount() + 1L);
          }
        }
        prices = PriceList.TWO.getName().split(",");
        if (price >= Long.valueOf(prices[0]) && price <= Long.valueOf(prices[1])) {
          DetailBean priceDetail = priceMap.get("2");
          if (priceDetail == null) {
            priceDetail = new DetailBean();
            priceDetail.setDetailCode("2");
            priceDetail.setCommodityCount(1L);
            priceMap.put("2", priceDetail);
          } else {
            priceDetail.setCommodityCount(priceDetail.getCommodityCount() + 1L);
          }
        }
        prices = PriceList.THREE.getName().split(",");
        if (price >= Long.valueOf(prices[0]) && price <= Long.valueOf(prices[1])) {
          DetailBean priceDetail = priceMap.get("3");
          if (priceDetail == null) {
            priceDetail = new DetailBean();
            priceDetail.setDetailCode("3");
            priceDetail.setCommodityCount(1L);
            priceMap.put("3", priceDetail);
          } else {
            priceDetail.setCommodityCount(priceDetail.getCommodityCount() + 1L);
          }
        }
        prices = PriceList.FOUR.getName().split(",");
        if (price >= Long.valueOf(prices[0]) && price <= Long.valueOf(prices[1])) {
          DetailBean priceDetail = priceMap.get("4");
          if (priceDetail == null) {
            priceDetail = new DetailBean();
            priceDetail.setDetailCode("4");
            priceDetail.setCommodityCount(1L);
            priceMap.put("4", priceDetail);
          } else {
            priceDetail.setCommodityCount(priceDetail.getCommodityCount() + 1L);
          }
        }
        if (price > Long.valueOf(PriceList.FIVE.getName())) {
          DetailBean priceDetail = priceMap.get("5");
          if (priceDetail == null) {
            priceDetail = new DetailBean();
            priceDetail.setDetailCode("5");
            priceDetail.setCommodityCount(1L);
            priceMap.put("5", priceDetail);
          } else {
            priceDetail.setCommodityCount(priceDetail.getCommodityCount() + 1L);
          }
        }
      //modified by cs_yuli 20120618 start 
        String categoryAttribute="";
        if(LanguageCode.Zh_Cn.getValue().equals(DIContainer.getLocaleContext().getCurrentLanguageCode())){
        	categoryAttribute=bean.getCategoryAttribute1();
        }if(LanguageCode.En_Us.getValue().equals(DIContainer.getLocaleContext().getCurrentLanguageCode())){
        	categoryAttribute=bean.getCategoryAttribute1En();
        }if(LanguageCode.Ja_Jp.getValue().equals(DIContainer.getLocaleContext().getCurrentLanguageCode())){
        	categoryAttribute=bean.getCategoryAttribute1Jp();
        }
        if (!StringUtil.isNullOrEmpty(categoryAttribute)) {
        //modified by cs_yuli 20120618 start 
          String[] attribute = categoryAttribute.split("#");
          for (String element : attribute) {
            if (element.indexOf("|") > 0) {
              String name = element.substring(0, element.indexOf("|"));
              String value = element.substring(element.indexOf("|") + 1);
              attributeSubMap = attributeMap.get(name);
              DetailBean attributeDetail = new DetailBean();
              if (attributeSubMap == null) {
                attributeSubMap = new HashMap<String, DetailBean>();
                attributeDetail = new DetailBean();
                attributeDetail.setDetailCode(element);
                attributeDetail.setDetailName(value);
                attributeDetail.setCommodityCount(1L);
                attributeDetail.setCommodityPopularRank(bean.getCommodityPopularRank());
                attributeSubMap.put(value, attributeDetail);
                attributeMap.put(name, attributeSubMap);
              } else {
                attributeDetail = attributeSubMap.get(value);
                if (attributeDetail == null) {
                  attributeDetail = new DetailBean();
                  attributeDetail.setDetailCode(element);
                  attributeDetail.setDetailName(value);
                  attributeDetail.setCommodityCount(1L);
                  attributeDetail.setCommodityPopularRank(bean.getCommodityPopularRank());
                  attributeSubMap.put(value, attributeDetail);
                } else {
                  attributeDetail.setCommodityCount(attributeDetail.getCommodityCount() + 1L);
                  attributeDetail.setCommodityPopularRank(attributeDetail.getCommodityPopularRank() + bean.getCommodityPopularRank());
                }
              }
            }
          }
        }
      }
      if (StringUtil.hasValue(getRequestParameter().get("searchTagCode"))) {
        reqBean.setFlag(false);
      } else {
        if (categoryList.isEmpty()) {
          reqBean.setFlag(false);
        } else {
          for (CategoryData categoryData : categoryList) {
            if (categoryData.getCommodityCount() > 0L) {
              reqBean.setFlag(true);
              break;
            }
          }
        }
      }
      reqBean.setCategoryList(categoryList);
      for (DetailBean detail : brandMap.values()) {
        if (detail.getCommodityCount() > 0L) {
          brandList.add(detail);
        }
      }
      if (brandList.isEmpty()) {
        reqBean.setBrandFlag(false);
      } else {
        reqBean.setBrandFlag(true);
        Collections.sort(brandList, new PopularRankComparator());
        reqBean.setBrandList(brandList);
      }
      
      List<CodeAttribute> reviewScoreList = new ArrayList<CodeAttribute>();
      reviewScoreList.add(ReviewScore.FOUR_STARS);
      reviewScoreList.add(ReviewScore.THREE_STARS);
      reviewScoreList.add(ReviewScore.TWO_STARS);
      reviewScoreList.add(ReviewScore.ONE_STAR);
      Long beforeCount = 0L;
      for (CodeAttribute c : reviewScoreList) {
        DetailBean detail = reciewMap.get(c.getValue());
        if (detail == null) {
          detail = new DetailBean();
          detail.setDetailCode(c.getValue());
          detail.setCommodityCount(0L);
        }
        detail.setCommodityCount(beforeCount + detail.getCommodityCount());
        beforeCount = detail.getCommodityCount();
        reviewList.add(detail);
      }
      reqBean.setReviewList(reviewList);
      
      List<CodeAttribute> price = new ArrayList<CodeAttribute>();
      price.addAll(Arrays.asList(PriceList.values()));
      for (CodeAttribute c : price) {
        DetailBean detail = priceMap.get(c.getValue());
        String[] prices = c.getName().split(",");
        if (detail == null) {
          detail = new DetailBean();
          detail.setDetailCode(c.getValue());
          if (prices.length == 2) {
            detail.setDetailName(prices[0] + "～" + prices[1]);
          } else {
            detail.setDetailName(prices[0]);
          }
          detail.setCommodityCount(0L);
        } else {
          if (prices.length == 2) {
            detail.setDetailName(prices[0] + "～" + prices[1]);
          } else {
            detail.setDetailName(prices[0]);
          }
        }
        priceList.add(detail);
      }
      reqBean.setPriceList(priceList);
      if (categoryAttributeList == null || categoryAttributeList.isEmpty()) {
        reqBean.setAttributeFlag(false);
      } else {
        reqBean.setAttributeFlag(true);
        List<DetailBean> attributeLists = new ArrayList<DetailBean>();
        for (int i = 0; i < categoryAttributeList.size(); i++) {
          CategoryAttribute categoryAttribute = categoryAttributeList.get(i);
           categoryAttributeValue= utilService.getNameByLanguage(categoryAttribute.getCategoryAttributeName(), 
        		   categoryAttribute.getCategoryAttributeNameEn(),categoryAttribute.getCategoryAttributeNameJp());
          attributeLists = new ArrayList<DetailBean>();
          if (i == 0) {
            DetailBean attributeDetail = new DetailBean();
            attributeDetail.setDetailCode("");
            attributeDetail.setDetailName(categoryAttributeValue);
            attributeList1.add(attributeDetail);
            Map<String, DetailBean> subMap = attributeMap.get(categoryAttributeValue);
            if (subMap != null) {
              for (DetailBean detail : subMap.values()) {
                if (detail.getCommodityCount() > 0L) {
                  attributeLists.add(detail);
                }
              }
              if (attributeLists.isEmpty()) {
                reqBean.setAttribute1Flag(false);
              } else {
                reqBean.setAttribute1Flag(true);
                Collections.sort(attributeLists, new PopularRankComparator());
                attributeList1.addAll(attributeLists);
              }
            } else {
              reqBean.setAttribute1Flag(false);
            }
          } else if (i == 1) {
            DetailBean attributeDetail = new DetailBean();
            attributeDetail.setDetailCode("");
            attributeDetail.setDetailName(categoryAttributeValue);
            attributeList2.add(attributeDetail);
            Map<String, DetailBean> subMap = attributeMap.get(categoryAttributeValue);
            if (subMap != null) {
              for (DetailBean detail : subMap.values()) {
                if (detail.getCommodityCount() > 0L) {
                  attributeLists.add(detail);
                }
              }
              if (attributeLists.isEmpty()) {
                reqBean.setAttribute2Flag(false);
              } else {
                reqBean.setAttribute2Flag(true);
                Collections.sort(attributeLists, new PopularRankComparator());
                attributeList2.addAll(attributeLists);
              }
            } else {
              reqBean.setAttribute2Flag(false);
            }
          } else {
            DetailBean attributeDetail = new DetailBean();
            attributeDetail.setDetailCode("");
            attributeDetail.setDetailName(categoryAttributeValue);
            attributeList3.add(attributeDetail);
            Map<String, DetailBean> subMap = attributeMap.get(categoryAttributeValue);
            if (subMap != null) {
              for (DetailBean detail : subMap.values()) {
                if (detail.getCommodityCount() > 0L) {
                  attributeLists.add(detail);
                }
              }
              if (attributeLists.isEmpty()) {
                reqBean.setAttribute3Flag(false);
              } else {
                reqBean.setAttribute3Flag(true);
                Collections.sort(attributeLists, new PopularRankComparator());
                attributeList3.addAll(attributeLists);
              }
            } else {
              reqBean.setAttribute3Flag(false);
            }
          }
        }
        if (!reqBean.isAttribute1Flag() && !reqBean.isAttribute2Flag() && !reqBean.isAttribute3Flag()) {
          reqBean.setAttributeFlag(false);
        }
      }
      Collections.sort(attributeList1, new CommodityCountOrder());
      reqBean.setAttributeList1(attributeList1);
      reqBean.setAttributeList2(attributeList2);
      reqBean.setAttributeList3(attributeList3);
//    }
    setBean(reqBean);
    if(sesContainer.getSession() != null){
      logger.info("当前LeftMenuSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前LeftMenuSubAction:session缺失，结束记录--------------------------------------------------------------------");
    }
  }

  public static class PopularRankComparator implements Serializable, Comparator<DetailBean> {

    private static final long serialVersionUID = 1L;

    public int compare(DetailBean o1, DetailBean o2) {
      // 並べ替える
      BigDecimal b1 = new BigDecimal(o1.getCommodityPopularRank());
      BigDecimal b2 = new BigDecimal(o1.getCommodityCount());
      BigDecimal b3 = new BigDecimal(o2.getCommodityPopularRank());
      BigDecimal b4 = new BigDecimal(o2.getCommodityCount());
      BigDecimal b5 = b1.divide(b2,2, BigDecimal.ROUND_HALF_UP);
      BigDecimal b6 = b3.divide(b4,2, BigDecimal.ROUND_HALF_UP);
      return b5.compareTo(b6);
    }
  }
  
  // list画面排序对比类 
  public static class CommodityCountOrder implements Serializable, Comparator<DetailBean> {

    private static final long serialVersionUID = 1L;

    public int compare(DetailBean o1, DetailBean o2) {
      if (o1.getCommodityCount() != null && o2.getCommodityCount() != null) {
        return -o1.getCommodityCount().compareTo(o2.getCommodityCount());
      } else {
        return -1;
      }
    }
  }

  /**
   * ログイン情報を取得します
   * 
   * @return frontLoginInfo
   */
  private FrontLoginInfo getLoginInfo() {
    LoginInfo loginInfo = getSessionContainer().getLoginInfo();
    FrontLoginInfo frontLoginInfo = null;

    if (loginInfo == null) {
      frontLoginInfo = WebLoginManager.createFrontNotLoginInfo();
    } else {
      if (loginInfo instanceof FrontLoginInfo) {
        frontLoginInfo = (FrontLoginInfo) loginInfo;
      } else {
        frontLoginInfo = WebLoginManager.createFrontNotLoginInfo();
      }
    }
    return frontLoginInfo;
  }
}
