package jp.co.sint.webshop.web.action.back.catalog;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import jp.co.sint.webshop.data.domain.CommodityJdUseFlg;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityMaster;
import jp.co.sint.webshop.data.dto.CommoditySku;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityMasterEditBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityMasterEditBean.CommodityMasterEditList;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * @author System Integrator Corp.
 */
public class CommodityMasterEditSelectAction extends WebBackAction<CommodityMasterEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    return Permission.COMMODITY_MASTER_LIST.isGranted(getLoginInfo());
  }
  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    return true;

  }

  /**
   * アクションを実行します。
   * @return アクションの実行結果
   */
  public WebActionResult callService() {  
    //设置edit页面主商品属性
    CommodityMasterEditBean bean = new CommodityMasterEditBean();
    //CommodityMasterEditBean bean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityMaster cm = new CommodityMaster();
    
    String[] param = getRequestParameter().getPathArgs();
    String commoditycode= param[0];
 
    cm = service.getCommodityMasters(commoditycode);
    bean.setCommodityCode(cm.getCommodityCode());
    bean.setCommodityName(cm.getCommodityName());
    bean.setJdCommodityCode(cm.getJdCommodityCode());
    bean.setTmallCommodityCode(cm.getTmallCommodityCode());
    //DiscountInfo discountInfo = new DiscountInfo();
      bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
      bean.setDisplayLoginButtonFlg(false);
      bean.setDisplayUpdateButtonFlg(true);
      bean.setEditMode(WebConstantCode.DISPLAY_EDIT);
      
      //设置edit页面子商品属性
      List<CommodityMasterEditList> cmelists =new ArrayList<CommodityMasterEditList>();
     // bean.getCommoditymastereditlist().clear();
      List<CommoditySku> shList =service.getCommoditySkuList(commoditycode);
      
      for (CommoditySku shlist : shList) {
        CommodityMasterEditList cmelist = new CommodityMasterEditList();
        cmelist.setSkuCode(shlist.getSkuCode());    
        cmelist.setCommodityCode(shlist.getCommodityCode());
        //子商品名称
        List<CommodityHeader> commodityName = service.getCommodityHeaderName(cmelist.getSkuCode());
        if(!commodityName.isEmpty()){
        if(commodityName.size()>1){
          cmelist.setSkuName(" ");
        }else{
          for (CommodityHeader ch : commodityName) {
            cmelist.setSkuName(ch.getCommodityName());
          }
        }
        }
        Long TMuseflg =shlist.getTmallUseFlag();
        if(TMuseflg==0){
          cmelist.setTmallUseFlag("不使用");
        }else if(TMuseflg==1){
          cmelist.setTmallUseFlag("使用");
        }
        Long JDuseflg = shlist.getJdUseFlag();
        if(JDuseflg==0){
          cmelist.setJdUseFlag("不使用");
        }else if(JDuseflg==1){
          cmelist.setJdUseFlag("使用");
        }
        
        cmelists.add(cmelist);
      }
      bean.setJdUseFlg(CommodityJdUseFlg.ISJDSEFLG.getValue());
      bean.setTmallUseFlg(CommodityJdUseFlg.ISJDSEFLG.getValue());
      bean.setCommoditymastereditlist(cmelists);
      setRequestBean(bean);
      
      /**
       * 返回验证提示信息
       */
      String[] params = getRequestParameter().getPathArgs();
      if(params.length>1){
        if("update".equals(params[1])){
          addInformationMessage(MessageFormat.format(Messages
              .getString("web.action.back.catalog.CommodityMasterEditCommodityDeleteAction.1"),true));
        }
        if("commodityregister".equals(params[1])){
          addInformationMessage(MessageFormat.format(Messages
              .getString("web.action.back.catalog.CommodityMasterEditCommodityRegisterAction.1"),true));
        }
        if("register".equals(params[1])){
          addInformationMessage(MessageFormat.format(Messages
              .getString("web.action.back.catalog.CommodityMasterEditRegisterAction.1"),true));
          
        }
      }
      
    return BackActionResult.RESULT_SUCCESS;

  } 

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityMasterEditSelectAction.0");
  
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102081005";
  }

}
