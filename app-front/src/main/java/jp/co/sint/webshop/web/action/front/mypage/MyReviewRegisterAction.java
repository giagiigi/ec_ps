package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.domain.ReviewDisplayType;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.ReviewPost;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.MyReviewBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;

/**
 * U2030210:お客様情報登録1のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class MyReviewRegisterAction extends WebFrontAction<MyReviewBean> {


  @Override
  public boolean validate() {
    return validateBean(getBean());
  }



  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    MyReviewBean bean = getBean();
    
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();

    // 顧客存在チェック
    CustomerService customerSv = ServiceLocator.getCustomerService(login);
    if (customerSv.isNotFound(customerCode) || customerSv.isInactive(customerCode)) {
      setNextUrl("/app/common/index");
      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }
    String shopCode = "00000000";
    
    OrderService orderSv = ServiceLocator.getOrderService(login);
    OrderDetail detail = orderSv.getOrderDetail(bean.getOrderNo(), shopCode, bean.getCommodityCode());
    if(detail==null){
      setNextUrl("/app/mypage/my_review");
      return FrontActionResult.RESULT_SUCCESS;
    }

    ReviewPost rp = new ReviewPost();
    rp.setCommodityCode(bean.getCommodityCode());
    rp.setCustomerCode(customerCode);
    rp.setReviewDescription(bean.getContent());
    rp.setReviewTitle(bean.getTitle());
    rp.setReviewScore(NumUtil.toLong(bean.getPoint())*20L);
    rp.setReviewDisplayType(ReviewDisplayType.UNCHECKED.longValue());
    rp.setOrderNo(bean.getOrderNo());
    rp.setReviewContributedDatetime(DateUtil.getSysdate());
    rp.setNickname("ninkName");
    rp.setShopCode(shopCode);
    
    CommunicationService comSv = ServiceLocator.getCommunicationService(login);
    comSv.insertReviewPost(rp);
    setNextUrl("/app/mypage/my_review/init/insert");
    
    
    return FrontActionResult.RESULT_SUCCESS;
  }



}
