package jp.co.sint.webshop.service;

import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CyncroResult;
import jp.co.sint.webshop.data.dto.ImageUploadHistory;
import jp.co.sint.webshop.data.dto.JdBatchTime;
import jp.co.sint.webshop.data.dto.JdCouponDetail;
import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdShippingDetail;
import jp.co.sint.webshop.data.dto.JdShippingDetailComposition;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.JdStock;
import jp.co.sint.webshop.data.dto.JdStockAllocation;
import jp.co.sint.webshop.data.dto.JdSuitCommodity;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.service.jd.order.JdOrderSearchCondition;
import jp.co.sint.webshop.service.jd.order.JdOrderlLists;
import jp.co.sint.webshop.service.order.MyOrder;
import jp.co.sint.webshop.service.order.MyOrderListSearchCondition;

import com.jd.open.api.sdk.domain.category.AttValue;

public interface JdService {
  
  boolean getJdAreas();
  
  /**
   * 京东商品同期化处理
   * @param commodityCodes 选中的商品编号
   * @return CyncroResult 同期化结果
   */
  CyncroResult jdCommodityCyncro(String[] commodityCodes);
  
  /**
   * 商家类目获取API处理
   * @return ServiceResult
   */
  ServiceResult getJdCategoryApi();
  
  // 京东批量发货
  boolean getJdShippingApiList(String order_id,String logistics_id,String waybill);
  
  /**
   * 类目属性获取API处理
   * @return ServiceResult
   */
  ServiceResult getJdPropertyApi();
  
  /**
   * @param 类目ID
   * 类目属性获取API处理
   * @return ServiceResult
   */
  ServiceResult getJdPropertyApi(String categoryId);
  
  /**
   * @param 类目ID
   * @param 类目属性ID
   * 类目属性获取API处理
   * @return ServiceResult
   */
  ServiceResult getJdPropertyApi(String categoryId, String attributeId);
  
  /**
   * @param 属性id串
   * @param 存放取得的京东类目属性值List
   * 类目属性值获取API处理
   * @return ServiceResult
   */
  ServiceResult getJdPropertyValueApi(String avs, List<AttValue> attValueList);
  
  /**
   * 商家已授权品牌查询API处理
   * @return ServiceResult
   */
  ServiceResult getVenderBrandApi();
  
  /**
   * 上传主图
   * @param jdCommodityID 京东商品ID
   * @param imageFile 上传图片文件
   * @param addDto 保存上传成功信息
   * @param delDto 上传前商品图片履历信息
   * @return ServiceResult
   */
  ServiceResult addMainCommodityImage(String jdCommodityID, String imageFile, 
      ImageUploadHistory addDto, ImageUploadHistory delDto);
  
  /**
   * 上传辅图
   * @param jdCommodityID 京东商品ID
   * @param skuCode 商品SKU编号
   * @param fix 图片后缀
   * @param imageFile 上传图片文件
   * @param addDto 保存上传成功信息
   * @return ServiceResult
   */
  ServiceResult addAssistCommodityImage(String jdCommodityID, String skuCode, String fix, String imageFile, 
      ImageUploadHistory addDto);
  
  /**
   * 商品图片增加API处理
   * @param jdCommodityID 京东商品ID
   * @param isMainPic 是否为主图
   * @param imageFile 上传图片文件
   * @param dto 保存上传成功信息
   * @return ServiceResult
   */
  ServiceResult addCommodityImage(String jdCommodityID, boolean isMainPic, String imageFile, ImageUploadHistory dto);
  
  /**
   * 商品图片删除API处理
   * @param jdCommodityID 京东商品ID
   * @param attributeValueId 属性值Id
   * @param imageId 图片Id
   * @return
   */
  ServiceResult deleteCommodityImage(String jdCommodityID, String imageId);
  
  /**
   * @param 图片路径
   * @param 图片名称
   * 上传单张图片至京东空间API处理
   * @return ServiceResult
   */
  String ImgzonePictureUploadApi(String imagePath, String imageName);
  
  void updateCommodityApi(CyncroResult resultMap, CCommodityHeader header);
  
  void updateCommodityApi(CyncroResult resultMap, CCommodityHeader header, boolean isDesc);
  
  CCommodityDetail getByJdCommodityCode(String jdCommodityCode);
  
  CCommodityDetail getBySkuCode(String skuCode);
  
  JdOrderHeader getJdOrderHeaderByJdOrderNo(String jdOrderNo);
  
  List<JdShippingHeader> getJdShippingHeaderByJdOrderNo(String jdOrderNo);
  
  JdOrderDetail getJdOrderDetailByOrderNo(String orderNo,String shopCode,String skuCode);
  
  JdShippingDetail getJdShippingDetailByOrderNo(String orderNo,String shopCode,String skuCode);
  
  List<JdOrderDetail> getJdOrderDetailCommodityList(String orderCode, String shopCode);
  
  void insertJdCouponDetail(JdCouponDetail obj);
  
  void insertJdOrderHeader(JdOrderHeader obj);
  
  void insertJdOrderDetail(JdOrderDetail obj);
  
  void insertJdShippingHeader(JdShippingHeader obj);
  
  void insertJdShippingDetail(JdShippingDetail obj);
  
  void updateJdOrderHeader(JdOrderHeader obj);
  
  void updateJdShippingHeader(JdShippingHeader obj);
  
  /**
   * 订单下载
   * @return
   */
  public  int  jdOrderDownlaod(String startDate,String endDate);
  
  JdBatchTime getJdBatchTime();

  /**
   * 操作batch时间信息
   * @param date
   * @return
   */
  ServiceResult OperateJdBatchTime(Date date, Long status);

  JdOrderHeader getJdOrderHeader(String orderNo);
  
  void insertJdOrderInvoice(OrderInvoice obj);
  
  List<JdShippingHeader> getJdShippingHeaderList();

  CommodityHeader getCommodityHeader(String shopCode, String commodityCode);
  
  JdStockAllocation loadJdStockAllocation(String shopCode, String skuCode);
  
  void updateJdStockAllocation(JdStockAllocation obj);
  
  JdSuitCommodity loadJdSuitCommodity(String commodityCode);
  
  void updateJdSuitCommodity(JdSuitCommodity obj);
  
  JdStock loadJdStock(String shopCode, String skuCode);
  
  void updateJdStock(JdStock obj);
  
  // 2014/06/06 库存更新对应 ob_李先超 add start
  /**
   * 商品库存同期到京东系统
   * @param jdCommodityId 京东商品ID
   * @param jdEffectiveStock 京东有效库存
   * @return true 成功
   *         false 失败
   */
  boolean jdStockCyncroApi(Long jdCommodityId, Long jdEffectiveStock);
  // 2014/06/06 库存更新对应 ob_李先超 add end
  CCommodityDetail loadCCommodityDetail(String shopCode, String skuCode);
  
  void insertJdShippingDetailComposition(JdShippingDetailComposition obj);
 
  SearchResult<JdOrderlLists> getJdSplistLists(JdOrderSearchCondition condition);
}
