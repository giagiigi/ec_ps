package jp.co.sint.webshop.service.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.service.OptionalCommodity;
import jp.co.sint.webshop.utility.Sku;

public interface Cart extends Serializable {

  /**
   * カートに入れられている全販売商品を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートに入れられている全販売商品を取得します。
   * <ol>
   * <li>カートに入れられている商品のうち、受注商品の物のみをリストにしたものを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return カートに入れられた全受注商品
   */
  List<CartItem> get();

  /**
   * 配送ショップ単位でカートアイテムを取得する。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>配送ショップ単位でカートアイテムを取得する。
   * <ol>
   * <li>カートに入れられている商品のうち、引数で受取ったショップコードの物かつ受注商品であるカートアイテムを<br />
   * リストにしたものを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @return 配送ショップ単位のカートアイテム
   */
  List<CartItem> get(String shopCode);

  /**
   * カートに入れられた販売商品を１件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートに入れられた販売商品を１件取得します。
   * <ol>
   * <li>カートに入れられている商品のうち、引数で受取ったショップコード・SKUコードかつ受注商品の<br />
   * カートアイテムを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          商品のショップコード
   * @param skuCode
   *          SKUコード
   * @return カートに入れられた1件分の販売商品
   */
  CartItem get(String shopCode, String skuCode);

  /**
   * カートに入れられている全予約商品を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートに入れられている全予約商品を取得します。
   * <ol>
   * <li>カートに入れられている商品のうち、予約商品の物のみをリストにしたものを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return カートに入れられた全予約商品
   */
  List<CartItem> getReserve();

  /**
   * カートに入れられた予約商品を１件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートに入れられた予約商品を１件取得します。
   * <ol>
   * <li>カートに入れられている商品のうち、引数で受取ったショップコード・SKUコードかつ予約商品の<br />
   * カートアイテムを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          商品のショップコード
   * @param skuCode
   *          SKUコード
   * @return カートに入れられた1件分の予約商品
   */
  CartItem getReserve(String shopCode, String skuCode);

  /**
   * カートに商品を追加します。 その際、同一商品が存在した場合、商品情報は変更されずに数量のみ追加されます。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートに商品を追加します。 その際、同一商品が存在した場合、商品情報は変更されずに数量のみ追加されます。
   * <ol>
   * <li>引数で受取った商品情報から、ショップコード・SKUコード・注文数量を取得します。</li>
   * <li>取得したショップコード・SKUコード・注文数量より在庫チェックを行い、NGの場合はfalseを返します。</li>
   * <li>取得したショップコード・SKUコード・注文数量より注文毎予約上限数チェックを行い、NGの場合はfalseを返します。</li>
   * <li>カートに入れられた商品の中に、該当のショップコード・SKUコードの商品が存在した場合はカートからその商品を削除します。</li>
   * <li>カートに引数で受取った商品情報を追加します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で受取ったcartItemはnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param cartItem
   *          商品情報
   * @return 追加成功の可否
   */
  boolean add(CartItem cartItem);

  /**
   * カートに商品を追加します。 その際、同一商品が存在した場合、商品情報は変更されずに数量のみ追加されます。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートに商品を追加します。 その際、同一商品が存在した場合、商品情報は変更されずに数量のみ追加されます。
   * <ol>
   * <li>カートに入れられた商品の中から、引数で受取ったショップコード・SKUコードの受注商品を取得します。</li>
   * <li>取得した商品情報がnullだった場合、同じショップコード・SKUコードで予約商品を取得します。</li>
   * <li>受注商品も予約商品も取得できなかった場合、引数で受取ったショップコード・SKUコード・注文数量で<br />
   * 新規にカートアイテムを生成し、生成されたカートアイテムを返します。</li>
   * <li>受注商品又は予約商品が存在した場合、該当の商品に数量を追加し、追加後のカートアイテムを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param quantity
   *          注文数量
   * @return 追加成功の可否
   */
  boolean add(String shopCode, String skuCode, int quantity);

  /**
   * カートに商品を追加します。 その際、同一商品が存在した場合、商品情報は変更されずに数量のみ追加されます。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートに商品を追加します。 その際、同一商品が存在した場合、商品情報は変更されずに数量のみ追加されます。
   * <ol>
   * <li>カートに入れられた商品の中から、引数で受取ったショップコード・SKUコードの受注商品を取得します。</li>
   * <li>取得した商品情報がnullだった場合、同じショップコード・SKUコードで予約商品を取得します。</li>
   * <li>受注商品も予約商品も取得できなかった場合、引数で受取ったショップコード・SKUコード・ギフトコード・注文数量で<br />
   * 新規にカートアイテムを生成し、生成されたカートアイテムを返します。</li>
   * <li>受注商品又は予約商品が存在した場合、該当の商品に数量を追加し、追加後のカートアイテムを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param giftCode
   *          ギフトコード
   * @param quantity
   *          注文数量
   * @return 追加成功の可否
   */
  boolean add(String shopCode, String skuCode, String giftCode, int quantity);

  /**
   * カートに商品を追加します。 その際、同一商品が存在した場合、元の商品情報は破棄し新たに取得しなおします。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートに商品を追加します。 その際、同一商品が存在した場合、元の商品情報は破棄し新たに取得しなおします。
   * <ol>
   * <li>引数で受取ったショップコード・SKUコード・注文数量より在庫チェックを行い、NGだった場合はfalseを返します。</li>
   * <li>引数で受取ったショップコード・SKUコード・注文数量より注文毎予約上限数チェックを行い、NGだった場合はfalseを返します。</li>
   * <li>既にカートの同一商品が存在していた場合、カートから該当商品を削除します。</li>
   * <li>注文数量が0より大きい時、カートに商品を注文数量分だけ追加します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param quantity
   *          注文数量
   * @return 追加成功の可否
   */
  // 20111214 shen update start
  // boolean set(String shopCode, String skuCode, int quantity);
  boolean set(String shopCode, String skuCode, int quantity, String customerCode);
  // 20111214 shen update start
  
  // 2012/12/04 促销对应 ob add start
  boolean set(String shopCode, String skuCode, String giftCode, int quantity, String customerCode, boolean isSet);
  // 2012/12/04 促销对应 ob add end
  
  /**
   * カートに商品を追加します。 その際、同一商品が存在した場合、元の商品情報は破棄し新たに取得しなおします。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートに商品を追加します。 その際、同一商品が存在した場合、元の商品情報は破棄し新たに取得しなおします。
   * <ol>
   * <li>引数で受取ったショップコード・SKUコード・注文数量より在庫チェックを行い、NGだった場合はfalseを返します。</li>
   * <li>引数で受取ったショップコード・SKUコード・注文数量より注文毎予約上限数チェックを行い、NGだった場合はfalseを返します。</li>
   * <li>既にカートの同一商品が存在していた場合、カートから該当商品を削除します。</li>
   * <li>注文数量が0より大きい時、カートに商品を注文数量分だけ追加します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param giftCode
   *          ギフトコード
   * @param quantity
   *          注文数量
   * @return 追加成功の可否
   */
  // 20111215 shen update start
  // boolean set(String shopCode, String skuCode, String giftCode, int quantity);
  boolean set(String shopCode, String skuCode, String giftCode, int quantity, String customerCode);
  // 20111215 shen update end

  /**
   * 該当の商品の数量のみを更新します。<br>
   * 該当の商品が存在しなかった場合、falseを返します。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param isOrder
   *          注文の有無
   * @param quantity
   *          数量
   * @return 更新成功の可否
   */
  boolean updateQuantity(String shopCode, String skuCode, boolean isOrder, int quantity);
  
  // 20111214 shen add start
  boolean updateQuantity(String shopCode, String skuCode, boolean isOrder, int quantity, String customerCode);
  // 20111214 shen add end

  /**
   * カートから対象の商品を削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートから対象の商品を削除します。
   * <ol>
   * <li>カートに引数で渡されたショップコード・SKUコードの商品が存在した場合、カートからその商品を削除します。</li>
   * </dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   */
  // 20111215 shen update start
  // void remove(String shopCode, String skuCode);
  void remove(String shopCode, String skuCode, String customerCode);
  // 20111215 shen update end
  
  /**
   * カートから対象の商品を削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートから対象の商品を削除します。
   * <ol>
   * <li>カートに引数で渡されたショップコード・SKUコードの商品が存在した場合、カートからその商品を削除します。</li>
   * </dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   */
  // 20111215 shen update start
  // void remove(String shopCode, String skuCode, String giftCode);
  void remove(String shopCode, String skuCode, String giftCode, String customerCode);
  // 20111215 shen update end
  
  /**
   * カートの商品を全て削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートの商品を全て削除します。
   * <ol>
   * <li>カートの商品を全て削除します。</li>
   * </dd>
   * </dl>
   * </p>
   */
  void clear();
  
  // 2012/11/22 促销对应 ob add start
  /**
   * 删除已领取的多重促销活动的赠品
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>删除已领取的多重促销活动的赠品
   * <ol>
   * <li>删除已领取的多重促销活动的赠品</li>
   * </dd>
   * </dl>
   * </p>
   */
  void clearAcceptedGift();
  // 2012/11/22 促销对应 ob add end

  /**
   * カートに入れられている商品数を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートに入れられている商品数を取得します。
   * <ol>
   * <li>カートに入れられている全商品の数量を返します。</li>
   * </dd>
   * </dl>
   * </p>
   * 
   * @return カートに投入されている商品の個数
   */
  int getItemCount();
  
  /**
   * カートに入れられている商品数を取得します。（通常商品だけ）
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートに入れられている商品数を取得します。（通常商品だけ）
   * <ol>
   * <li>カートに入れられている通常商品の数量を返します。</li>
   * </dd>
   * </dl>
   * </p>
   * 
   * @return カートに投入されている通常商品の個数
   */
  int getItemCountExceptGift();

  /**
   * カートが空かどうかを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カートが空かどうかを返します。
   * <ol>
   * <li>カートに商品が投入されていなければtrue、一つでも投入されている場合はfalseを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * 
   * @return カートが空の場合true、そうでない場合はfalse。
   */
  boolean isEmpty();

  /**
   * カート内の全合計金額を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カート内の全合計金額を取得します。
   * <ol>
   * <li>全商品の合計金額((販売価格＋ギフト価格)×注文数量)を足し合わせた値を返します。</li>
   * </dd>
   * </dl>
   * </p>
   * 
   * @return カートに投入されている全合計金額を返します。
   */
  BigDecimal getGrandTotal();

  /**
   * 配送ショップコード一覧を取得します。<BR>
   * 本メソッドにて取得されたショップコードには、予約のショップコードは含まれません。
   * 
   * @return 配送ショップコード一覧
   */
  List<String> getShopCodeList();

  /**
   * 予約の配送ショップコード一覧を取得します。<BR>
   * 
   * @return 予約の配送ショップコード一覧
   */
  List<String> getReserveShopCodeList();

  /**
   * カートに入れた商品の履歴を返します。履歴は古い順に並んでいます。
   * 
   * @return カートに入れた商品の履歴
   */
  List<Sku> getHistory();

  /**
   * 購入対象商品の配送方法が全て一致しているかどうかをチェックします。<br>
   * ショップ個別モードの場合、引数のショップコードに紐付く商品が対象となります。
   * 
   * @param selectShopCode
   *          ショップコード
   * @return true:不一致<br>
   *         false:全て一致
   */
  boolean checkDeliveryType(String selectShopCode);
  
  // 20111214 shen add start
  void setCartItemFromHistory(String customerCode);
  
  void insertCartHistory(String customerCode);
  
  void clearCartHistroy(String customerCode);
  // 20111214 shen add end
  
  // 20120221 shen add start
  String getPrefectureCode();
  
  void setPrefectureCode(String prefectureCode);
  // 20120221 shen add end
  
  BigDecimal getOptionalCheapPrice();
  
  void setOptionalCheapPrice(BigDecimal optionalCheapPrice);
  
  Map<String, OptionalCommodity> getOptionalCommodityMap();
  
  void setOptionalCommodityMap(Map<String, OptionalCommodity> optionalCommodityMap);
  
  List<String> getOpCommodityCodeList();
  
  void setOpCommodityCodeList(List<String> opCommodityCodeList);
  
  
}
