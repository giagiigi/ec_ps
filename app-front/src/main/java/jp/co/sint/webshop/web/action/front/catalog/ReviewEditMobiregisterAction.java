package jp.co.sint.webshop.web.action.front.catalog;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.ReviewPost;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.ReviewEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.CompleteMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

// import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
// import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U2040620:レビュー確認のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ReviewEditMobiregisterAction extends WebFrontAction<ReviewEditBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
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

    // 商品の販売可能可否チェック
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    if (!catalogService.isListed(getBean().getShopCode(), getBean().getCommodityCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.front.catalog.ReviewConfirmRegisterAction.0")));
      setRequestBean(getBean());
      return FrontActionResult.RESULT_SUCCESS;
    }
 
    
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    
    //20111219 os013 update start
    String orderNo="0";
    //受注履歴ID等于0或空时为商品评论
    if(StringUtil.isNullOrEmpty(getBean().getOrderNo())){
      // 顧客の同一商品への投稿回数のチェック
      if (service.isAlreadyPostReview(getBean().getShopCode(), getBean().getCommodityCode(), getLoginInfo().getCustomerCode(),orderNo)) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DUPLICATED_REVIEW_ERROR));
  
        setRequestBean(getBean());
        return FrontActionResult.RESULT_SUCCESS;
      }
    }else{
      //受注履歴ID不等于0或空时为订单商品评论
      // 顧客の同一订单の同一商品への投稿回数のチェック
      if (service.isAlreadyPostReview(getBean().getShopCode(), getBean().getCommodityCode(), getLoginInfo().getCustomerCode(),getBean().getOrderNo())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DUPLICATED_REVIEW_ERROR));
  
        setRequestBean(getBean());
        return FrontActionResult.RESULT_SUCCESS;
      }
    }
    //20111219 os013 update start
    // 登録用DTOの生成
    ReviewPost reviewPost = new ReviewPost();
    reviewPost.setShopCode(getBean().getShopCode());
    reviewPost.setCommodityCode(getBean().getCommodityCode());
    reviewPost.setCustomerCode(getLoginInfo().getCustomerCode());
    reviewPost.setNickname(getBean().getNickName());
    reviewPost.setReviewTitle(getBean().getReviewTitle());
    reviewPost.setReviewScore(NumUtil.toLong(getBean().getReviewScore()));
    reviewPost.setReviewDescription(getBean().getReviewDescription());
    //20111219 os013 add start
    if(StringUtil.isNullOrEmpty(getBean().getOrderNo())){
      reviewPost.setOrderNo(orderNo);
    }else{
      reviewPost.setOrderNo(getBean().getOrderNo());
    }  
    //20111219 os013 add end
    // 登録処理
    ServiceResult result = service.insertReviewPost(reviewPost);

    if (result.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
          // レビュー登録失敗エラーの表示
          addErrorMessage(WebMessage.get(CatalogErrorMessage.DUPLICATED_REVIEW_ERROR));
        } else if (CustomerServiceErrorContent.POINT_OVERFLOW_ERROR.equals(error)) {
          Length len = BeanUtil.getAnnotation(Customer.class, "temporaryPoint", Length.class);
          String maximum = StringUtil.times("9", len.value());
          addErrorMessage(WebMessage.get(MypageErrorMessage.POINT_OVERFLOW_ERROR, maximum));
        }
        logger.debug(error.toString());
      }

    } else {
      // 登録成功
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.front.catalog.ReviewConfirmRegisterAction.1")));
//      getBean().setDisplayButton(false);
//      getBean().setDisplayBackButton(true);
//      getBean().setDisplayNavi(false);
//      getBean().setReviewComplateFlag(true);
    }

    setRequestBean(getBean());
    if (StringUtil.hasValue(getBean().getCommodityCode())) {
      setNextUrl("/app/catalog/detail/init/" + getBean().getCommodityCode());
    } else {
      setNextUrl("/app/common/index");
    }
    return FrontActionResult.RESULT_SUCCESS;
  }

}
