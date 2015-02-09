package jp.co.sint.webshop.web.action.back.service;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dao.GiftCardReturnApplyDao;
import jp.co.sint.webshop.data.dto.GiftCardReturnApply;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.communication.GiftCardReturnListSearchCondition;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.GiftCardReturnConfirmBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

public class GiftCardReturnConfirmRegisterAction extends WebBackAction<GiftCardReturnConfirmBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.SERVICE_GIFT_CARD_CONFIRM);
  }
  
  private GiftCardReturnListSearchCondition condition;
  
  private String confirmAmount = "0.00";

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    GiftCardReturnApplyDao dao = DIContainer.getDao(GiftCardReturnApplyDao.class);
    String[] tmpArgs = getRequestParameter().getPathArgs();
    String orderNo = tmpArgs[0];
    for (GiftCardReturnApply gcra : getBean().getList()) {
      if (gcra.getOrderNo().equals(orderNo)) {
        confirmAmount = gcra.getConfirmAmount().toString();
      }
    }
    GiftCardReturnApply gcra = dao.load(orderNo);
    // 0：客服退款   1：审核退款
    if (gcra.getConfirmFlg()==1L) {
      addErrorMessage("已完成退款操作");
      return false;
    }
    
    if (StringUtil.isNullOrEmpty(confirmAmount)) {
      addErrorMessage("审核退款金额必须输入");
      return false;
    }
    
    if (!NumUtil.isDecimal(confirmAmount)) {
      addErrorMessage("审核退款金额必须为数字");
      return false;
    }
    
    if (BigDecimalUtil.isBelowOrEquals(new BigDecimal(confirmAmount), BigDecimal.ZERO)) {
      addErrorMessage("审核退款金额必须大于0");
      return false;
    }
    
    if (BigDecimalUtil.isAbove(new BigDecimal(confirmAmount), gcra.getCardUseAmount())) {
      addErrorMessage("审核退款金额不能大于礼品卡使用金额");
      return  false;
    }
    return true;
    
  }
  

  protected GiftCardReturnListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected GiftCardReturnListSearchCondition getSearchCondition() {
    return this.condition;
  }
  
  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new GiftCardReturnListSearchCondition();
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    GiftCardReturnConfirmBean bean = getBean();
    condition = getCondition();

    condition.setSearchOrderNo(bean.getSearchOrderNo());
    condition.setSearchReturnFlg(bean.getSearchReturnFlg());
    condition.setSearchReturnDatetimeFrom(bean.getSearchReturnDatetimeFrom());
    condition.setSearchReturnDatetimeTo(bean.getSearchReturnDatetimeTo());

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    GiftCardReturnApplyDao dao = DIContainer.getDao(GiftCardReturnApplyDao.class);
    String[] tmpArgs = getRequestParameter().getPathArgs();
    String orderNo = tmpArgs[0];

    GiftCardReturnApply gcra = dao.load(orderNo);
//    for (GiftCardReturnApply gc : bean.getList()) {
//      if (gc.getOrderNo().equals(orderNo)) {
//        gc.setConfirmFlg(1L);
//      }
//    }
    
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult serviceResult = communicationService.insertGiftCardReturnConfirm(gcra,confirmAmount);

    // 登录失败
    if (serviceResult.hasError()) {
      setNextUrl(null);
      for (ServiceErrorContent error : serviceResult.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }
      addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, orderNo));
      setRequestBean(getBean());
      setBean(getBean());
      return BackActionResult.SERVICE_ERROR;
    }
    
    SearchResult<GiftCardReturnApply> result = service.searchGiftCardReturnList(condition, true);

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    bean.setPagerValue(PagerUtil.createValue(result));

    bean.getList().clear();
    bean.getDateString().clear();
    bean.getConfirmDateString().clear();
    // 生成画面一览信息
    
    for (GiftCardReturnApply row : result.getRows()) {
      bean.getDateString().add( DateUtil.toDateTimeString(row.getReturnDate(),"yyyy-MM-dd"));
      if (row.getConfirmFlg().equals(0L)) {
        bean.getConfirmDateString().add(" ");
      } else {
        bean.getConfirmDateString().add( DateUtil.toDateTimeString(row.getUpdatedDatetime(),"yyyy-MM-dd HH:mm:ss"));
      }
      

      bean.getList().add(row);
    }
    setRequestBean(bean);
    
    
    
    addInformationMessage("退款成功");
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("礼品卡退款登录");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109051002";
  }
}
