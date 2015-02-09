package jp.co.sint.webshop.web.action.back.catalog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.FileUtil;
import jp.co.sint.webshop.utility.ImageUploadConfig;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityImageBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;

/**
 * U1040110:商品マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityImageUploadAction extends CommodityImageBaseAction {
  
  private int totalOperNum = 0;
  private int errorNum = 0;

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return super.authorize();
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

    CommodityImageBean bean = getBean();

    // 从配置文件中取出原图片存放路径
    ImageUploadConfig config = DIContainer.getImageUploadConfig();
    
    if (null == config) {
      setRequestBean(bean);

      return BackActionResult.SERVICE_ERROR;
    }
    
    // 取得待上传文件夹下所有jpg文件的文件名
    List<File> orgFileList = new ArrayList<File>();
    FileUtil.findFilesByFileType(config.getOrgImgPath(), super.FILE_TYPE, orgFileList);
    totalOperNum = orgFileList.size();
    
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    
    List<String> errMsgList = new ArrayList<String>();
    ServiceResult result = service.uploadImgBatch(getLoginInfo().getShopCode(), errMsgList);
    
    errorNum = errMsgList.size();
    
    addErrorMessage(WebMessage.get(ActionErrorMessage.IMG_UPLOAD_OVER, DatabaseUtil.getSystemDatetime().toString()));
    addErrorMessage(WebMessage.get(ActionErrorMessage.IMG_UPLOAD_SUCCESS, new Integer(totalOperNum-errorNum).toString()));
    
    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          setRequestBean(bean);
          return BackActionResult.SERVICE_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR) && errorNum > 0) {
          for (String msg :errMsgList) {
            String msgs[] = msg.split(",");
            if (msgs.length > 1) { 
              addErrorMessage(WebMessage.get(ActionErrorMessage.IMG_ERR, msgs[0], msgs[1]));
            }
          }
        }
      }
    }
    
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    super.prerender();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "图片管理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "9104011001";
  }

}
