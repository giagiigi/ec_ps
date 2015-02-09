package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.dao.CCommodityDetailDao;
import jp.co.sint.webshop.data.dao.CommodityDescribeDao;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityDescribe;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.TmallRelatedDescribeBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040230:カテゴリ－関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallRelatedDescribeInitAction extends WebBackAction<TmallRelatedDescribeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization;

    // モードに関わらず、カテゴリの参照権限があれば表示
    BackLoginInfo login = getLoginInfo();
    authorization = Permission.CATEGORY_READ.isGranted(login) || Permission.COMMODITY_READ.isGranted(login);

    if (login.isShop() && getRequestParameter().getPathArgs().length > 0) {

      String shop = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(shop)) {
        return false;
      }

      authorization &= shop.equals(login.getShopCode());
    }

    return authorization;

  }

  private String shopCode = "";

  private String commodityCode = "";

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length >= 2) {
      shopCode = getRequestParameter().getPathArgs()[0];
      commodityCode = getRequestParameter().getPathArgs()[1];
      return true;
    }
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    TmallRelatedDescribeBean bean = new TmallRelatedDescribeBean();
    bean.setDisplayPreview(false);
    bean.setCommodityCode(getRequestParameter().getPathArgs()[1]);
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityDescribeDao dao = DIContainer.getDao(CommodityDescribeDao.class);
    CCommodityDetailDao detailDao = DIContainer.getDao(CCommodityDetailDao.class);
    CommodityDescribe describe = dao.load(shopCode, bean.getCommodityCode());
    CCommodityHeader header = service.getCCommodityheader(shopCode, commodityCode);
    CCommodityDetail detail = detailDao.load(shopCode, commodityCode);
    CommodityHeader commodity =  service.getCommodityHeader(shopCode, commodityCode);
    if (commodity != null ) {
      bean.setEcUseFlg(true);
    }
    if (header == null){
      addErrorMessage("该商品不存在");
      return BackActionResult.SERVICE_VALIDATION_ERROR;
    }
    bean.setTmallCommodityId(header.getTmallCommodityId());
    bean.setTmallUseFlg(detail.getTmallUseFlg());
    
    // 2014/04/30 京东WBS对应 ob_姚 add start
    bean.setJdCommodityId(header.getJdCommodityId());
    bean.setJdUseFlg(detail.getJdUseFlg());
    // 2014/04/30 京东WBS对应 ob_姚 add end
    
    Boolean brFlag = false;
    String descriptionPc = "";
    String descriptionPcEn = "";
    String descriptionPcJp = "";
    if (StringUtil.isNotNull(header.getCommodityDescription1())) {
      brFlag = true;
      descriptionPc = "<font color=\"red\">" + header.getCommodityDescription1() + "</font>";
    }
    if (StringUtil.isNotNull(header.getCommodityDescription2())) {
      if (brFlag) {
        descriptionPc += "<br>";
      }
      descriptionPc += header.getCommodityDescription2();
    }
    if (StringUtil.isNotNull(header.getCommodityDescription3())) {
      if (brFlag) {
        descriptionPc += "<br>";
      }
      descriptionPc += header.getCommodityDescription3();
    }

    if (StringUtil.isNotNull(header.getCommodityDescription1En())) {
      brFlag = true;
      descriptionPcEn = "<font color=\"red\">" + header.getCommodityDescription1En() + "</font>";
    }
    if (StringUtil.isNotNull(header.getCommodityDescription2En())) {
      if (brFlag) {
        descriptionPcEn += "<br>";
      }
      descriptionPcEn += header.getCommodityDescription2En();
    }
    if (StringUtil.isNotNull(header.getCommodityDescription3En())) {
      if (brFlag) {
        descriptionPcEn += "<br>";
      }
      descriptionPcEn += header.getCommodityDescription3En();
    }

    if (StringUtil.isNotNull(header.getCommodityDescription1Jp())) {
      brFlag = true;
      descriptionPcJp = "<font color=\"red\">" + header.getCommodityDescription1Jp() + "</font>";
    }
    if (StringUtil.isNotNull(header.getCommodityDescription2Jp())) {
      if (brFlag) {
        descriptionPcJp += "<br>";
      }
      descriptionPcJp += header.getCommodityDescription2Jp();
    }
    if (StringUtil.isNotNull(header.getCommodityDescription3Jp())) {
      if (brFlag) {
        descriptionPcJp += "<br>";
      }
      descriptionPcJp += header.getCommodityDescription3Jp();
    }
    
    if (describe != null) {
      bean.setShopCode(shopCode);
      if (StringUtil.hasValue(describe.getDecribeCn())) {
        bean.setDecribeCn(describe.getDecribeCn());
      } else {
        bean.setDecribeCn(descriptionPc);
      }
      
      if (StringUtil.hasValue(describe.getDecribeEn())) {
        bean.setDecribeEn(describe.getDecribeEn());
      } else {
        bean.setDecribeEn(descriptionPcEn);
      }

      if (StringUtil.hasValue(describe.getDecribeJp())) {
        bean.setDecribeJp(describe.getDecribeJp());
      } else {
        bean.setDecribeJp(descriptionPcJp);
      }
      
      bean.setDecribeTmall(describe.getDecribeTmall());
      bean.setUpdatedDatetime(bean.getUpdatedDatetime());
      // 2014/05/06 京东WBS对应 ob_卢 add start
      bean.setDecribeJd(describe.getDecribeJd());
      // 2014/05/06 京东WBS对应 ob_卢 add end
    } else {
      bean.setShopCode(shopCode);
      bean.setDecribeCn(descriptionPc);
      bean.setDecribeEn(descriptionPcEn);
      bean.setDecribeJp(descriptionPcJp);
      bean.setDecribeTmall("");
      // 2014/05/06 京东WBS对应 ob_卢 add start
      bean.setDecribeJd("");
      // 2014/05/06 京东WBS对应 ob_卢 add end
      bean.setUpdatedDatetime(bean.getUpdatedDatetime());
    }
    if (getRequestParameter().getPathArgs().length > 0) {
      String parameter = getRequestParameter().getPathArgs()[0];
      if (parameter.equals(WebConstantCode.COMPLETE_UPDATE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "���ߍ���HTML"));
      }
    }
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします <BR>
   * 処理完了パラメータがない場合は、何もメッセージを設定しません<BR>
   * 登録完了時：insert <BR>
   */
  private void setCompleteParameter() {
    String[] param = getRequestParameter().getPathArgs();
    String completeParam = "";
    if (param.length == 3) {
      completeParam = param[2];
    }

    if (completeParam.equals(WebConstantCode.COMPLETE_REGISTER)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.catalog.RelatedCategoryTreeInitAction.1")));
    } else if (completeParam.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.catalog.RelatedCategoryTreeInitAction.1")));
    } else if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage("更新成功");
    }
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    setCompleteParameter();
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("商品描述编辑");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104030001";
  }

}
