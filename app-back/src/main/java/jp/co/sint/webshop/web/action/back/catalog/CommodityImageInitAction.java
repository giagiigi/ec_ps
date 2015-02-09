package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.FileUtil;
import jp.co.sint.webshop.utility.ImageUploadConfig;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityImageBean;

/**
 * U1040110:商品マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityImageInitAction extends CommodityImageBaseAction {

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
    
    //从配置文件中取出原图片存放路径
    ImageUploadConfig config = DIContainer.getImageUploadConfig();
    
    //判断文件夹中是否存在jpg文件,如果存在,则显示待处理按钮
    int nCount = FileUtil.findFilesByFileType(config.getOrgImgPath(), FILE_TYPE, null);
    if (nCount > 0) {
      bean.setUploadBtnFlg(true);
    } else {
      bean.setUploadBtnFlg(false);
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
    return "3104100001";
  }

}
