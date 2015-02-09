package jp.co.sint.webshop.web.action.back.service;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.GiftCardReturnApply;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.GiftCardReturnListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.GiftCardReturnConfirmBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060610:PRIVATEクーポン管理のアクションクラスです
 * 
 * @author System OB.
 */
public class GiftCardReturnConfirmSearchAction extends WebBackAction<GiftCardReturnConfirmBean> {

  private GiftCardReturnListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new GiftCardReturnListSearchCondition();
  }

  protected GiftCardReturnListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected GiftCardReturnListSearchCondition getSearchCondition() {
    return this.condition;
  }

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
    return Permission.SERVICE_GIFT_CARD_CONFIRM.isGranted(login);
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

    // 根据查询条件取得顾客别优惠券发行规则信息List
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

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean isValid = true;
    GiftCardReturnConfirmBean bean = getBean();

    // 検索条件のvalidationチェック
    isValid &= validateBean(bean);

    // 日付の大小関係チェック
    if (isValid) {

      if (StringUtil.hasValueAllOf(bean.getSearchReturnDatetimeFrom(), bean
          .getSearchReturnDatetimeTo())) {
        if (!ValidatorUtil.isCorrectOrder(bean.getSearchReturnDatetimeFrom(), bean
            .getSearchReturnDatetimeTo())) {
          isValid &= false;
          addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "退款申请时间"));
        }
      }
      
    }

    return isValid;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "礼品卡退款一览画面检索处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107111002";
  }

}
