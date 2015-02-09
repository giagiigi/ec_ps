package jp.co.sint.webshop.web.action.back.communication;

import java.util.Date;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.FriendCouponRuleCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.CurrencyValidator;
import jp.co.sint.webshop.validation.DigitValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleBean;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleBean.SelectFriendCouponRuleList;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U5106111001:朋友推荐优惠券选择查询 s
 * 
 * @author zhangzhengtao
 */
public class FriendCouponRuleInitAction extends WebBackAction<FriendCouponRuleBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    if (null == login) {
      return false;
    }
    // 没有更新和查看权限,不显示
    if (!Permission.FriendCouponRule_READ_SHOP.isGranted(login)) {
      return false;
    }
    return true;
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
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    // 分页查询
    FriendCouponRuleCondition condition = new FriendCouponRuleCondition();
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);
    SearchResult<FriendCouponRule> result = service.selectAllFriendCouponRule(condition);
    // 封装进bean
    FriendCouponRuleBean bean = new FriendCouponRuleBean();
    bean.setCouponIssueType(CampaignType.PROPORTION.getValue());
    bean.setPagerValue(PagerUtil.createValue(result));
    bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
    for (FriendCouponRule rule : result.getRows()) {
      SelectFriendCouponRuleList detail = new SelectFriendCouponRuleList();
      detail.setFriendCouponRuleNo(rule.getFriendCouponRuleNo());
      detail.setFriendCouponRuleCn(rule.getFriendCouponRuleCn());
      detail.setFriendCouponRuleJp(rule.getFriendCouponRuleJp());
      detail.setFriendCouponRuleEn(rule.getFriendCouponRuleEn());
      detail.setIssueDateType(NumUtil.toString(rule.getIssueDateType()));
      if (rule.getIssueDateType() == 0) {
        String month = null;
        if (rule.getIssueDateNum() < 10) {
          month = "0" + rule.getIssueDateNum();
        } else {
          month = NumUtil.toString(rule.getIssueDateNum());
        }
        detail.setIssueDateNum(month);
        detail.setIssueStartDate(null);
        detail.setIssueEndDate(null);
      } else {
        Date startDate = DateUtil.getMin();
        Date endDate = DateUtil.getMin();
        if (rule.getIssueStartDate() != null) {
          startDate = rule.getIssueStartDate();
        }
        if (rule.getIssueEndDate() != null) {
          endDate = rule.getIssueEndDate();
        }
        detail.setIssueStartDate(DateUtil.toDateTimeString(startDate, "yyyy/MM/dd HH:mm"));
        detail.setIssueEndDate(DateUtil.toDateTimeString(endDate, "yyyy/MM/dd HH:mm"));
        detail.setIssueDateNum(null);
      }
      detail.setOrderHistory(NumUtil.toString(rule.getOrderHistory()));
      detail.setCouponAmountNumeric(NumUtil.toString(rule.getCouponAmount()));
      detail.setPersonalUseLimit(NumUtil.toString(rule.getPersonalUseLimit()));
      detail.setSiteUseLimit(NumUtil.toString(rule.getSiteUseLimit()));
      detail.setMinUseOrderAmount(NumUtil.toString(rule.getMinUseOrderAmount()));
      detail.setUseValidType(NumUtil.toString(rule.getUseValidType()));
      detail.setUseValidNum(NumUtil.toString(rule.getUseValidNum()));
      detail.setApplicableObjects(NumUtil.toString(rule.getApplicableObjects()));
      detail.setObtainPoint(NumUtil.toString(rule.getObtainPoint()));
      detail.setFixChar(rule.getFixChar());
      detail.setMaxUseOrderAmount(NumUtil.toString(rule.getMaxUseOrderAmount()));
      bean.getList().add(detail);
    }
    this.setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 表示状態を取得します
   * 
   * @param displayFlg
   * @return 表示状態（表示/非表示）
   */
  public String getStatus(Long displayFlg) {
    String status = "";
    if (displayFlg.equals(0L)) {
      status = Messages.getString("web.action.back.communication.MailMagazineInitAction.1");
    } else {
      status = Messages.getString("web.action.back.communication.MailMagazineInitAction.2");
    }
    return status;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    FriendCouponRuleBean requestBean = (FriendCouponRuleBean) getRequestBean();
    BackLoginInfo login = getLoginInfo();
    requestBean.setDeleteButtonFlg(false);
    if (Permission.FriendCouponRule_DELETE_SHOP.isGranted(login)) {
      requestBean.setDeleteButtonFlg(true);
    }
    if (!Permission.FriendCouponRule_UPDATE_SHOP.isGranted(login)) {
      requestBean.setDisplayMode(null);
    }
    setRequestBean(requestBean);
  }
  
  

  /**
   * 验证优惠券数值
   */
  public boolean checkCouponAmount(FriendCouponRuleBean bean) {

    // 如果优惠劵类别是比例优惠,取得比例优惠的值
    if (CampaignType.PROPORTION.getValue().equals(bean.getCouponIssueType())) {
      bean.setCouponAmountNumeric(null);

      // 如果比例优惠的值为空,报错
      if (StringUtil.isNullOrEmpty(bean.getRatio())) {

        addErrorMessage(WebMessage.get(CommunicationErrorMessage.RATIO_NULL_ERROR));
        return false;
      } else {

        DigitValidator dv = new DigitValidator();
        // 比例优惠的值合法的情况下
        if (dv.isValid(bean.getRatio())) {
          //20140416 hdh add start
          if(StringUtil.isNullOrEmpty(bean.getMaxUseOrderAmount())){
            addErrorMessage("优惠券类型为比例时，最大使用金额必须填写");
            return false;
          }else {
            if(StringUtil.hasValue(bean.getMinUseOrderAmount())){
              if (NumUtil.parse(bean.getMaxUseOrderAmount()).compareTo(NumUtil.parse(bean.getMinUseOrderAmount()))<0){
                addErrorMessage("最大使用金额必须不于最小使用金额");
                return false;
              }
            }
            
          }
          // 20140416 hdh add end
          bean.setRatio(bean.getRatio());
          setBean(bean);
          setRequestBean(bean);

        } else {

          // 比例输入的数据不正确,报错
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.RATIO_ERROR));
          return false;
        }
      }
    }

    // 如果优惠劵类别是金额优惠,取得金额优惠的值
    if (CampaignType.FIXED.getValue().equals(bean.getCouponIssueType())) {
      bean.setRatio(null);
      // 如果金额优惠的值为空,报错
      if (StringUtil.isNullOrEmpty(bean.getCouponAmountNumeric())) {

        addErrorMessage(WebMessage.get(CommunicationErrorMessage.MONEY_NULL_ERROR));
        return false;
      } else {

        CurrencyValidator cv = new CurrencyValidator();
        // 金额优惠的值合法的情况下
        if (cv.isValid(bean.getCouponAmountNumeric())) {
          // 20140417 hdh add start
          if(StringUtil.hasValueAllOf(bean.getMinUseOrderAmount(),bean.getMaxUseOrderAmount())){
            if (NumUtil.parse(bean.getMaxUseOrderAmount()).compareTo(NumUtil.parse(bean.getMinUseOrderAmount()))<0){
              addErrorMessage("最大使用金额必须不于最小使用金额");
              return false;
            }
          }
          // 20140417 hdh add end
          bean.setCouponAmountNumeric(bean.getCouponAmountNumeric());
          setBean(bean);
          setRequestBean(bean);

        } else {

          // 金额输入的数据不正确,报错
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.MONEY_ERROR));
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.FriendCouponRuleInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106111001";
  }
}
