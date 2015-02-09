package jp.co.sint.webshop.web.action.back.catalog;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.GiftCount;
import jp.co.sint.webshop.service.data.GiftImage;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.DataIOServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean.GiftDetailBean;
import jp.co.sint.webshop.web.file.UploadContents;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040310:ギフトのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GiftDeleteAction extends GiftBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_DELETE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length < 1) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED,
          Messages.getString("web.action.back.catalog.GiftDeleteAction.0")));
      return false;
    }

    String giftCode = getRequestParameter().getPathArgs()[0];
    for (GiftDetailBean detail : getBean().getList()) {
      if (detail.getGiftCode().equals(giftCode)) {
        return true;
      }

    }

    addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED,
        Messages.getString("web.action.back.catalog.GiftDeleteAction.0")));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String giftCode = getRequestParameter().getPathArgs()[0];

    // 削除用DTOの作成
    Gift gift = new Gift();
    for (GiftDetailBean bean : getBean().getList()) {
      if (bean.getGiftCode().equals(giftCode)) {
        setGiftData(bean, gift);
        gift.setUpdatedUser(getLoginInfo().getUserCode());
        gift.setUpdatedDatetime(getBean().getEdit().getUpdateDatetime());
        break;
      }
    }

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult result = service.deleteGift(gift.getShopCode(), gift.getGiftCode());
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.catalog.GiftDeleteAction.0")));

          // ギフト一覧を更新
          GiftBean bean = getBean();
          List<GiftDetailBean> detailList = new ArrayList<GiftDetailBean>();
          List<CodeAttribute> giftImageList = new ArrayList<CodeAttribute>();

          for (GiftCount g : getGiftList(bean)) {
            GiftDetailBean detail = new GiftDetailBean();
            setResultGiftList(g, detail);
            detailList.add(detail);
            giftImageList.add(new NameValue(g.getGiftName(), g.getGiftCode() + "/" + UploadContents.GIFT_IMAGE));
          }
          bean.setList(detailList);
          bean.setGiftImageList(giftImageList);

          setRequestBean(bean);

          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;

    } else {
      GiftImage info = new GiftImage(gift.getShopCode(), gift.getGiftCode());

      DataIOService ioService = ServiceLocator.getDataIOService(getLoginInfo());
      ServiceResult ioResult = ioService.deleteImage(info);
      if (ioResult.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          if (error == DataIOServiceErrorContent.FILE_DELETE_ERROR) {
            addWarningMessage(WebMessage.get(DataIOErrorMessage.FILE_DELETE_FAILED, 
                MessageFormat.format(Messages.getString("web.action.back.catalog.GiftDeleteAction.1"),
                    gift.getGiftCode())));
          }
        }
      }
    }

    setNextUrl("/app/catalog/gift/init/" + WebConstantCode.COMPLETE_DELETE);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.GiftDeleteAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104031001";
  }

}
