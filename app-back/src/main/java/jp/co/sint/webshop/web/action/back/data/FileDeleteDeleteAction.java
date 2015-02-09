package jp.co.sint.webshop.web.action.back.data;

import java.io.File;
import java.util.List;

import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.data.CampaignContents;
import jp.co.sint.webshop.service.data.CategoryContents;
import jp.co.sint.webshop.service.data.Contents;
import jp.co.sint.webshop.service.data.ContentsInfo;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.service.data.EnqueteContents;
import jp.co.sint.webshop.service.data.GuideContents;
import jp.co.sint.webshop.service.data.LoginContents;
import jp.co.sint.webshop.service.data.RuleContents;
import jp.co.sint.webshop.service.data.ShopComplianceContents;
import jp.co.sint.webshop.service.data.ShopinfoContents;
import jp.co.sint.webshop.service.data.ThankyouContents;
import jp.co.sint.webshop.service.data.TopContents;
import jp.co.sint.webshop.service.result.DataIOServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.bean.back.data.FileDeleteBean;
import jp.co.sint.webshop.web.bean.back.data.FileDeleteBean.FileDeleteDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1080410:ファイル削除のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class FileDeleteDeleteAction extends FileDeleteSearchAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    FileDeleteBean bean = getBean();

    ContentsSearchCondition condition = new ContentsSearchCondition();
    condition.setContentsType(ContentsType.fromValue(bean.getContentsTypeCondition()));
    condition.setShopCode(bean.getShopCode());
    condition.setCategoryCode(bean.getCategoryTopCondition());
    condition.setCampaignCode(bean.getCampaignCondition());

    DataIOService service = ServiceLocator.getDataIOService(getLoginInfo());

    Contents contents = new Contents();
    contents.setContentsCondition(condition);

    ContentsInfo contentsInfo = null;
    ServiceResult results = new ServiceResultImpl();

    if (condition.getContentsType() == null) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.data.FileDeleteDeleteAction.0")));
      this.setRequestBean(getBean());
      return super.callService();
    }
    if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_TOPPAGE)) {
      // トップページコンテンツの場合
      contentsInfo = new TopContents();
      results = service.deleteContents(contentsInfo);
    } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_GUIDE)) {
      // ヘルプコンテンツの場合
      contentsInfo = new GuideContents();
      results = service.deleteContents(contentsInfo);
    } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_RULE)) {
      // 利用規約コンテンツの場合
      contentsInfo = new RuleContents();
      results = service.deleteContents(contentsInfo);
//    2010/04/27 ShiKui Add Start.
//    } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_PRIVACY)) {
//      // 個人情報保護方針コンテンツの場合
//      contentsInfo = new PrivacyContents();
//      results = service.deleteContents(contentsInfo);
//    2010/04/27 ShiKui Add End.      
//    } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_COMPLIANCE)) {
//      // 特定商取引法に基づく表記(サイト)コンテンツの場合
//      contentsInfo = new ComplianceContents();
//      results = service.deleteContents(contentsInfo);
    } else if (condition.getContentsType().equals(ContentsType.CONTENT_SHOP_COMPLIANCE)) {
      // 特定商取引法に基づく表記(ショップ)コンテンツの場合
      contentsInfo = new ShopComplianceContents(bean.getShopCode());
      results = service.deleteContents(contentsInfo);
    } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_CATEGORY)) {
      // カテゴリトップコンテンツの場合
      contentsInfo = new CategoryContents(bean.getCategoryTopCondition());
      results = service.deleteContents(contentsInfo);
    } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_LOGIN)) {
      // ログイン広告コンテンツの場合
      contentsInfo = new LoginContents();
      results = service.deleteContents(contentsInfo);
    } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_THANKYOU)) {
      // 注文完了広告コンテンツの場合
      contentsInfo = new ThankyouContents();
      results = service.deleteContents(contentsInfo);
    } else if (condition.getContentsType().equals(ContentsType.CONTENT_SITE_ENQUETE)) {
      // アンケート完了広告コンテンツの場合
      contentsInfo = new EnqueteContents();
      results = service.deleteContents(contentsInfo);
    } else if (condition.getContentsType().equals(ContentsType.CONTENT_SHOP_CAMPAIGN_SITE)
        || condition.getContentsType().equals(ContentsType.CONTENT_SHOP_CAMPAIGN_SHOP)) {
      // キャンペーンコンテンツの場合
      contentsInfo = new CampaignContents(bean.getShopCode(), bean.getCampaignCondition());
      results = service.deleteContents(contentsInfo);
    } else if (condition.getContentsType().equals(ContentsType.CONTENT_SHOP_SHOPINFO_SITE)
        || condition.getContentsType().equals(ContentsType.CONTENT_SHOP_SHOPINFO_SHOP)) {
      // ショップ情報コンテンツの場合
      contentsInfo = new ShopinfoContents(bean.getShopCode());
      results = service.deleteContents(contentsInfo);
      // 画像系,テキストコンテンツの場合
    } else if (condition.getContentsType().equals(ContentsType.IMAGE_DATA_SHOP_COMMODITY)
        || condition.getContentsType().equals(ContentsType.IMAGE_DATA_SHOP_GIFT)
        || condition.getContentsType().equals(ContentsType.CONTENT_SITE_MOBILE)
        || condition.getContentsType().equals(ContentsType.CONTENT_SHOP_MOBILE)) {
      String[] checkBoxArray = getRequestParameter().getAll("checkBoxId");
      // チェックボックスが選択されているか
      if (!StringUtil.hasValueAllOf(checkBoxArray)) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
            Messages.getString("web.action.back.data.FileDeleteDeleteAction.1")));
        this.setRequestBean(getBean());
        return super.callService();
      }
      if (checkBoxArray.length > 0) {
        for (String id : checkBoxArray) {
          String checkBoxId = id;
          List<FileDeleteDetailBean> detailBean = bean.getSearchResultList();
          String deleteFile = detailBean.get(Integer.parseInt(checkBoxId)).getUploadFilePath();
          File file = new File(deleteFile);
          contents = new Contents();
          contents.setContentsFile(file);
          contents.setContentsCondition(condition);
          service.deleteImage(contents);
        }
      }
    }

    if (results.hasError()) {
      for (ServiceErrorContent result : results.getServiceErrorList()) {
        if (result.equals(DataIOServiceErrorContent.CONTENTS_DELETE_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.data.FileDeleteDeleteAction.2")));
        }
      }
    } else {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.data.FileDeleteDeleteAction.2")));
    }

    // 再検索する
    return super.callService();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.data.FileDeleteDeleteAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "7108041001";
  }

}
