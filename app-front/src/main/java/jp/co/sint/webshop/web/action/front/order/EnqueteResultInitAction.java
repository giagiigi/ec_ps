package jp.co.sint.webshop.web.action.front.order;

import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.data.ContentsListResult;
import jp.co.sint.webshop.service.data.ContentsPath;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.order.EnqueteResultBean;

/**
 * U2060120:アンケート完了のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteResultInitAction extends WebFrontAction<EnqueteResultBean> {

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
    EnqueteResultBean bean = getBean();

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());

    Enquete enquete = service.getCurrentEnquete();
    if (enquete != null) {
      bean.setEnqueteInvestPoint(NumUtil.toString(enquete.getEnqueteInvestPoint()));
      // add by V10-CH start
      bean.setEnqueteInvestPointDisplayFlg(NumUtil.parse(bean.getEnqueteInvestPoint()).intValue() == 0 ? false : true);
      // add by V10-CH end
    }

    // 広告コンテンツ設定
    DataIOService ioService = ServiceLocator.getDataIOService(getLoginInfo());
    ContentsSearchCondition condition = new ContentsSearchCondition();
    condition.setContentsType(ContentsType.CONTENT_SITE_ENQUETE);
    ContentsPath path = DIContainer.get("contentsPath");
    ContentsListResult result = ioService.getRandomContents(condition);
    if (result.getFileName() == null) {
      result.setFileName("");
    } else {
      bean.setAdvertiseImageUrl(path.getEnquetePath() + "/" + result.getFileName());
      bean.setAdvertiseLinkUrl(ioService.getContentsData(path.getContentsSharedPath() + path.getEnquetePath() + "/"
          + result.getFileName().substring(0, result.getFileName().lastIndexOf(".")) + ".txt"));
    }
    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }
}
