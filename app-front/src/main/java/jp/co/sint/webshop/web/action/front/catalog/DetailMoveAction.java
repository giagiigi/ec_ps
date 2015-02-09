package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2040510:商品詳細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DetailMoveAction extends DetailBaseAction {
  
  /** モード定数:お問合せボタンクリック時 */
  private static final String MODE_INQUIRY = "inquiry";
  
  /** モード定数:お気に入りボタンクリック時 */
  private static final String MODE_FAVORITE = "favorite";
  
  /** モード定数:カテゴリリンククリック時 */
  private static final String MODE_CATEGORY = "category";
  
  /** モード定数:タグリンククリック時 */
  private static final String MODE_TAG = "tag";
  
  private static final String MODE_CUSTOMER = "customer";

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommodityDetailBean bean = getBean();

    String[] urlParam = getRequestParameter().getPathArgs();

    if (urlParam.length > 2) {
      String shopCode = urlParam[1];
      String commodityCode = urlParam[2];

      if (urlParam[0].equals(MODE_INQUIRY)) {
        setNextUrl("/app/customer/inquiry_edit/init/commodity/" + shopCode + "/" + commodityCode);
        
      } else if (urlParam[0].equals(MODE_FAVORITE)) {
        String skuCode = "";
        if (urlParam.length > 3) {
          skuCode = urlParam[3];
        }
        bean.setShopCode(shopCode);
        bean.setCommodityCode(commodityCode);
        bean.setSkuCode(skuCode);
        if (isNotSelectedSku(bean)) {
          setBean(bean);
        } else {
          // 10.1.2 10018 修正 ここから
          // setNextUrl("/app/catalog/detail/addfavorite/" + shopCode + "/" + commodityCode
          //     + "/" + skuCode);
          setNextUrl("/app/catalog/detail/addfavorite/" + commodityCode
              + "/" + skuCode + "?selectedSkuCode=" + skuCode);
          // 10.1.2 10018 修正 ここまで
        }
        return FrontActionResult.RESULT_SUCCESS;
      } else if (urlParam[0].equals(MODE_CUSTOMER)) {
        bean.setShopCode(shopCode);
        bean.setCommodityCode(commodityCode);
        bean.setSkuCode(commodityCode);
        setNextUrl("/app/catalog/detail/addcustomer/" + commodityCode + "/" );
        return FrontActionResult.RESULT_SUCCESS;
      }
      CommodityDetailBean nextBean = getBean();
      setRequestBean(nextBean);

      // 遷移元情報をセッションに保存
      DisplayTransition.add(getBean(), "/app/catalog/detail/init/" + commodityCode,
          getSessionContainer());

    } else if (urlParam.length > 1) {
      String pictureMode = urlParam[0];
      
      CommodityListBean nextBean = new CommodityListBean();
      
      if (pictureMode.equals(MODE_CATEGORY)) {
        String categoryCode = urlParam[1];
        nextBean.setSearchCategoryCode(categoryCode);
        setNextUrl("/app/catalog/category/init" + nextBean.toQueryString());
        
      } else if (pictureMode.equals(MODE_TAG)) {
        String tagCode = urlParam[1];
        nextBean.setSearchShopCode(bean.getShopCode());
        nextBean.setSearchTagCode(tagCode);
        setNextUrl("/app/catalog/list/init" + nextBean.toQueryString());
        
      }
    }

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      CommodityDetailBean nextBean = getBean();
      setRequestBean(nextBean);
      return false;
    }
    return true;
  }
}
