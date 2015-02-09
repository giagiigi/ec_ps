package jp.co.sint.webshop.web.log.back;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

/**
 * 管理側アクセスログに出力するオペレーションコードとアクション名の一覧<br />
 * 本クラスで定義されたオペレーションが管理側アクセスログとして出力される<br />
 * 
 * @author System Integrator Corp.
 */
public class OperationName_en_US extends ListResourceBundle {

  private static final Object[][] COMMON = new Object[][] {
      {
          /** ログイン認証処理 */
          "0101011001", "Login authentication"
      }, {
          /** ログイン初期表示処理 */
          "0101011002", "Login initial display"
      }, {
          /** ダッシュボード初期表示処理 */
          "0101021001", "Dashbroad initial display"
      }, {
          /** ログアウト初期表示処理 */
          "0101031001", "Logout initial display"
      },
  };

  private static final Object[][] ORDER = new Object[][] {
      {
          /** 新規受注登録(商品選択)カート追加処理 */
          "1102011001", "New order registration (commodity select) - cart add"
      }, {
          /** 新規受注登録(商品選択)再計算処理 */
          "1102011002", "New order registration (commodity select) - recalcuration"
      }, {
          /** 新規受注登録(商品選択)カートクリア処理 */
          "1102011003", "New order registration (commodity select) - cart clear"
      }, {
          /** 新規受注登録(商品選択)初期表示処理 */
          "1102011004", "New order registration (commodity select) - initial display"
      }, {
          /** 新規受注登録(商品選択)遷移処理 */
          "1102011005", "New order registration (commodity select) - transition"
      }, {
          /** 新規受注登録(商品選択)カート削除処理 */
          "1102011006", "New order registration (commodity select) - cart deletion"
      }, {
          /** 新規受注登録(商品選択)検索処理 */
          "1102011007", "New order registration (commodity select) - search"
      }, {
          /** 新規受注登録（顧客選択）初期表示処理 */
          "1102012001", "New order registration (customer select) - initial display"
      }, {
          /** 新規受注登録（顧客選択）遷移処理 */
          "1102012002", "New order registration (customer select) - transition"
      }, {
          /** 新規受注登録（顧客選択）検索処理 */
          "1102012003", "New order registration (customer select) - search"
      }, {
          /** 新規受注登録(配送先設定)配送先追加処理 */
          "1102013001", "New order registration (delivery address setting) - delivery address add"
      }, {
          /** 新規受注登録(配送先設定)アドレス追加処理 */
          "1102013002", "New order registration (delivery address setting) - address add"
      }, {
          /** 新規受注登録(配送先設定)再計算処理 */
          "1102013003", "New order registration (delivery address setting) - recalcuration"
      }, {
          /** 新規受注登録(配送先設定)初期表示処理 */
          "1102013004", "New order registration (delivery address setting) - initial display"
      }, {
          /** 新規受注登録(配送先設定)遷移処理 */
          "1102013005", "New order registration (delivery address setting) - transition"
      }, {
          /** 新規受注登録(配送先設定)商品削除処理 */
          "1102013006", "New order registration (delivery address setting) - Commodity deletion"
      }, {
          /** 新規受注登録(配送先設定)配送先削除処理 */
          "1102013007", "New order registration (delivery address setting) - delivery address deletion"
      }, {
          /** 新規受注登録(決済方法指定)再計算処理 */
          "1102015001", "New order registration (payment method) - recalcuration"
      }, {
          /** 新規受注登録(決済方法指定)初期表示処理 */
          "1102015002", "New order registration (payment method) - initial display"
      }, {
          /** 新規受注登録(決済方法指定)遷移処理 */
          "1102015003", "New order registration (payment method) - transition"
      }, {
          /** 新規受注登録(確認)初期表示処理 */
          "1102016001", "New order registration (confirmation) - initial display"
      }, {
          /** 新規受注登録(確認)遷移処理 */
          "1102016002", "New order registration (confirmation) - transition"
      }, {
          /** 新規受注登録(確認)登録処理 */
          "1102016003", "New order registration (confirmation) - registration"
      }, {
          /** 新規受注登録(完了画面)初期表示処理 */
          "1102017001", "New order registration (complete) - initial display"
      }, {
          /** 新規受注登録（顧客登録）戻る処理 */
          "1102019001", "New order registration (customer registration) - back"
      }, {
          /** 新規受注登録（顧客登録）確認処理 */
          "1102019002", "New order registration (customer registration) - confirmation"
      }, {
          /** 新規受注登録（顧客登録）初期表示処理 */
          "1102019003", "New order registration (customer registration) - initial display"
      }, {
          /** 新規受注登録（顧客登録）登録処理 */
          "1102019004", "New order registration (customer registration) - registration"
      }, {
          /** 受注入金管理出力処理 */
          "1102021001", "Order payment management - export"
      }, {
          /** 受注入金管理初期表示処理 */
          "1102021002", "Order payment management - initial display"
      }, {
          /** 受注入金管理遷移明細処理 */
          "1102021003", "Order payment management - details transition"
      }, {
          /** 受注入金管理支払情報登録処理 */
          "1102021004", "Order payment management - payment information registration"
      }, {
          /** 受注入金管理支払情報リセット処理 */
          "1102021005", "Order payment management - payment information reset"
      }, {
          /** 受注入金管理検索処理 */
          "1102021006", "Order payment management - search"
      }, {
          /** 受注入金管理検索戻る処理 */
          "1102021007", "Order payment management - search back"
      }, {
          /** 受注入金管理入金確認メール送信処理 */
          "1102021008", "Order payment management - payment confirmation E-mail send"
      }, {
          /** 受注入金管理入金督促メール送信処理 */
          "1102021009", "Order payment management - payment reminder E-mail send"
      }, {
          /** 受注管理明細受注キャンセル処理 */
          "1102022001", "Order details management - order cancel"
      }, {
          /** 受注管理明細入金日クリア処理 */
          "1102022002", "Order details management - payment date clear"
      }, {
          /** 受注管理明細初期表示処理 */
          "1102022003", "Order details management - initial display"
      }, {
          /** 受注管理明細受注修正遷移処理 */
          "1102022004", "Order details management - order modification transition"
      }, {
          /** 受注管理明細受注返品遷移処理 */
          "1102022005", "Order details management - return goods transition"
      }, {
          /** 受注管理明細入金日設定処理 */
          "1102022006", "Order details management - payment date setting"
      }, {
          /** 受注管理明細更新処理 */
          "1102022007", "Order details management - update"
      }, {
          /** 受注修正商品追加処理 */
          "1102023001", "Order modification - commodity add"
      }, {
          /** 受注修正商品クリア処理 */
          "1102023002", "Order modification - commodity clear"
      }, {
          /** 受注修正再計算処理 */
          "1102023003", "Order modification - recalculation"
      }, {
          /** 受注修正再計算ポイント処理 */
          "1102023004", "Order modification - point recalculation"
      }, {
          /** 受注修正初期表示処理 */
          "1102023005", "Order modification - initial display"
      }, {
          /** 受注修正遷移確認処理 */
          "1102023006", "Order modification - transition"
      }, {
          /** 受注修正入金遷移処理 */
          "1102023007", "Order modification - payment transition"
      }, {
          /** 受注修正出荷遷移処理 */
          "1102023008", "Order modification - shipping transition"
      }, {
          /** 受注修正登録処理 */
          "1102023009", "Order modification - registration"
      }, {
          /** 受注返品戻る処理 */
          "1102026001", "Order of return goods - back"
      }, {
          /** 受注返品確認処理 */
          "1102026002", "Order of return goods - confirmation"
      }, {
          /** 受注返品返品削除処理 */
          "1102026003", "Order of return goods - return goods deletion"
      }, {
          /** 受注返品初期表示処理 */
          "1102026004", "Order of return goods - initial display"
      }, {
          /** 受注返品登録処理 */
          "1102026005", "Order of return goods - registration"
      }, {
          /** 出荷管理出荷実績削除処理 */
          "1102041001", "Shipping management - shipping result deletion"
      }, {
          /** 出荷管理出荷指示削除処理 */
          "1102041002", "Shipping management - shipping order deletion"
      }, {
          /** 出荷管理出力処理 */
          "1102041003", "Shipping management - export"
      }, {
          /** 出荷管理取込処理 */
          "1102041004", "Shipping management - import"
      }, {
          /** 出荷管理初期表示処理 */
          "1102041005", "Shipping management - initial display"
      }, {
          /** 出荷管理遷移処理 */
          "1102041006", "Shipping management - transition"
      }, {
          /** 出荷管理出荷実績登録処理 */
          "1102041007", "Shipping management - shipping result registration"
      }, {
          /** 出荷管理検索処理 */
          "1102041008", "Shipping management - search"
      }, {
          /** 出荷管理検索戻る処理 */
          "1102041009", "Shipping management - search back"
      }, {
          /** 出荷管理ショップ選択処理 */
          "1102041010", "Shipping management - shop select"
      }, {
          /** 出荷管理出荷報告メール送信処理 */
          "1102041011", "Shipping details management - shipping report E-mail send"
      }, {
          /** 出荷管理明細初期表示処理 */
          "1102042001", "Shipping details management - initial display"
      }, {
          /** 出荷管理明細遷移処理 */
          "1102042002", "Shipping details management - transition"
      }, {
          /** 出荷管理明細出荷指示登録処理 */
          "1102042003", "Shipping details management - shipping order registration"
      }, {
          /** 出荷管理明細更新処理 */
          "1102042004", "Shipping details management - update"
      },
  };

  private static final Object[][] CUSTOMER = new Object[][] {
      {
          /** 顧客マスタ出力処理 */
          "2103011001", "Customer master - export"
      }, {
          /** 顧客マスタ初期表示処理 */
          "2103011002", "Customer master - initial display"
      }, {
          /** 顧客マスタ遷移処理 */
          "2103011003", "Customer master - transition"
      }, {
          /** 顧客マスタ検索処理 */
          "2103011004", "Customer master - search"
      }, {
          /** 顧客マスタ検索戻る処理 */
          "2103011005", "Customer master - search back"
      }, {
          /** 顧客マスタ退会処理 */
          "2103011006", "Customer master - withdrawal"
      }, {
          /** 顧客登録戻る処理 */
          "2103012001", "Customer registration - back"
      }, {
          /** 顧客登録確認処理 */
          "2103012002", "Customer registration - confirmation"
      }, {
          /** 顧客登録初期表示処理 */
          "2103012003", "Customer registration - initial display"
      }, {
          /** 顧客登録遷移処理 */
          "2103012004", "Customer registration - transition"
      }, {
          /** 顧客登録登録処理 */
          "2103012005", "Customer registration - registration"
      }, {
          /** 顧客登録更新処理 */
          "2103012006", "Customer registration - update"
      }, {
          /** 顧客登録退会処理 */
          "2103012007", "Customer registration - withdrawal"
      }, {
          /** ポイント履歴完了処理 */
          "2103014001", "Point history - completion"
      }, {
          /** ポイント履歴削除処理 */
          "2103014002", "Point history - deletion"
      }, {
          /** ポイント履歴初期表示処理 */
          "2103014003", "Point history - initial display"
      }, {
          /** ポイント履歴登録処理 */
          "2103014004", "Point history - registration"
      }, {
          /** ポイント履歴検索処理 */
          "2103014005", "Point history - search"
      }, {
          /** 注文履歴初期表示処理 */
          "2103015001", "Order history - initial display"
      }, {
          /** 注文履歴初期表示戻る処理 */
          "2103015002", "Order history - initial display back"
      }, {
          /** 注文履歴遷移処理 */
          "2103015003", "Order history - transition"
      }, {
          /** アドレス帳一覧削除処理 */
          "2103021001", "Address book list - deletion"
      }, {
          /** アドレス帳一覧初期表示処理 */
          "2103021002", "Address book list - initial display"
      }, {
          /** アドレス帳一覧初期表示戻る処理 */
          "2103021003", "Address book list - initial display back"
      }, {
          /** アドレス帳一覧遷移処理 */
          "2103021004", "Address book list - transition"
      }, {
          /** アドレス帳登録戻る処理 */
          "2103022001", "Address book registration - back"
      }, {
          /** アドレス帳登録確認処理 */
          "2103022002", "Address book registration - confirmation"
      }, {
          /** アドレス帳登録初期表示処理 */
          "2103022003", "Address book registration - initial display"
      }, {
          /** アドレス帳登録登録処理 */
          "2103022004", "Address book registration - registration"
      }, {
          /** アドレス帳登録更新処理 */
          "2103022005", "Address book registration - update"
      }, {
          /** パスワード変更初期表示処理 */
          "2103023001", "Password modification - initial display"
      }, {
          /** パスワード変更更新処理 */
          "2103023002", "Password modification - update"
      }, {
          /** ポイント利用状況出力処理 */
          "2103031001", "Point use status - export"
      }, {
          /** ポイント利用状況初期表示処理 */
          "2103031002", "Point use status - initial display"
      }, {
          /** ポイント利用状況検索処理 */
          "2103031003", "Point use status - search"
      }, {
          /** 顧客グループマスタ削除処理 */
          "2103041001", "Customer group master - deletion"
      }, {
          /** 顧客グループマスタ初期表示処理 */
          "2103041002", "Customer group master - initial display"
      }, {
          /** 顧客グループマスタ登録処理 */
          "2103041003", "Customer group master - registration"
      }, {
          /** 顧客グループマスタ選択処理 */
          "2103041004", "Customer group master - select"
      }, {
          /** 顧客グループマスタ更新処理 */
          "2103041005", "Customer group master - update"
      }, {
          /** 顧客属性マスタ削除処理 */
          "2103051001", "Customer attribute master - deletion"
      }, {
          /** 顧客属性マスタ削除属性選択処理 */
          "2103051002", "Customer attribute master - attribute deletion select"
      }, {
          /** 顧客属性マスタ初期表示処理 */
          "2103051003", "Customer attribute master - initial display"
      }, {
          /** 顧客属性マスタ新規処理 */
          "2103051004", "Customer attribute master - new"
      }, {
          /** 顧客属性マスタ登録属性処理 */
          "2103051005", "Customer attribute master - registration attribute"
      }, {
          /** 顧客属性マスタ登録属性選択処理 */
          "2103051006", "Customer attribute master - attribute registration select"
      }, {
          /** 顧客属性マスタ選択処理 */
          "2103051007", "Customer attribute master - select"
      }, {
          /** 顧客属性マスタ更新属性処理 */
          "2103051008", "Customer attribute master - update attribute"
      }, {
          /** 顧客属性マスタ更新属性選択処理 */
          "2103051009", "Customer attribute master - attribute update select"
      }, {
          /** 情報メール送信戻る処理 */
          "2103061001", "Information E-mail send - back"
      }, {
          /** 情報メール送信確認処理 */
          "2103061002", "Information E-mail send - confirmation"
      }, {
          /** 情報メール送信初期表示処理 */
          "2103061003", "Information E-mail send - initial display"
      }, {
          /** 情報メール送信メール送信処理 */
          "2103061004", "Information E-mail send - E-mail send"
      }, {
          /** 情報メール送信遷移処理 */
          "2103061005", "Information E-mail send - transition"
      }, {
          /** 情報メール送信プレビュー処理 */
          "2103061006", "Information E-mail send - preview"
      }, {
          /** 情報メール送信選択処理 */
          "2103061007", "Information E-mail send - select"
      },
  };

  private static final Object[][] CATALOG = new Object[][] {
      {
          /** 商品マスタ削除処理 */
          "3104011001", "Commodity master - deletion"
      }, {
          /** 商品マスタ出力処理 */
          "3104011002", "Commodity master - export"
      }, {
          /** 商品マスタ初期表示処理 */
          "3104011003", "Commodity master - initial display"
      }, {
          /** 商品マスタ遷移処理 */
          "3104011004", "Commodity master - transition"
      }, {
          /** 商品マスタ検索処理 */
          "3104011005", "Commodity master - search"
      }, {
          /** 商品マスタ検索戻る処理 */
          "3104011006", "Commodity master - search back"
      }, {
          /** 商品マスタ更新処理 */
          "3104011007", "Commodity master - update"
      }, {
          /** 商品登録戻る処理 */
          "3104012001", "Commodity registration - back"
      }, {
          /** 商品登録キャンセル処理 */
          "3104012002", "Commodity registration - cancel"
      }, {
          /** 商品登録確認処理 */
          "3104012003", "Commodity registration - confirmation"
      }, {
          /** 商品登録初期表示処理 */
          "3104012004", "Commodity registration - initial display"
      }, {
          /** 商品登録遷移処理 */
          "3104012005", "Commodity registration - transition"
      }, {
          /** 商品登録プレビュー処理 */
          "3104012006", "Commodity registration - review"
      }, {
          /** 商品登録登録処理 */
          "3104012007", "Commodity registration - registration"
      }, {
          /** 商品登録選択処理 */
          "3104012008", "Commodity registration - select"
      }, {
          /** 商品登録更新処理 */
          "3104012009", "Commodity registration - update"
      }, {
          /** 商品登録画像削除処理 */
          "3104012010", "Commodity registration - image deletion"
      }, {
          //M17N-0008 追加 ここから
          /** 商品テキスト遷移処理 */
          "3104012011", "Commodity text -transtion"
          //M17N-0008 追加 ここまで
      }, {
          /** 商品削除確認削除処理 */
          "3104013001", "Commodity deletion confirmation - deletion"
      }, {
          /** 商品削除確認初期表示処理 */
          "3104013002", "Commodity deletion confirmation - initial display"
      }, {
          /** 商品SKU削除処理 */
          "3104014001", "Commodity SKU - deletion"
      }, {
          /** 商品SKU初期表示処理 */
          "3104014002", "Commodity SKU - initial display"
      }, {
          /** 商品SKU遷移処理 */
          "3104014003", "Commodity SKU - transition"
      }, {
          /** 商品SKU価格更新処理 */
          "3104014004", "Commodity SKU - price update"
      }, {
          /** 商品SKU登録処理 */
          "3104014005", "Commodity SKU - registration"
      }, {
          /** 商品SKU表示処理 */
          "3104014006", "Commodity SKU - express"
      }, {
          /** 商品SKU更新処理 */
          "3104014007", "Commodity SKU - update"
      }, {
          /** 商品SKU規格名称更新処理 */
          "3104014008", "Commodity SKU - standard name update"
      }, {
          /** 商品SKU画像削除処理 */
          "3104014009", "Commodity SKU - image deletion"
      }, {
          /** 関連付け完了処理 */
          "3104016001", "Relation setting - completion"
      }, {
          /** 関連付け削除処理 */
          "3104016002", "Relation setting - deletion"
      }, {
          /** 関連付け初期表示処理 */
          "3104016003", "Relation setting - initial display"
      }, {
          /** 関連付け登録処理 */
          "3104016004", "Relation setting - registration"
      }, {
          /** 関連付け検索処理 */
          "3104016005", "Relation setting - search"
      }, {
          /** 関連付け更新処理 */
          "3104016006", "Relation setting - update"
      }, {
          /** 関連付け一覧初期表示処理 */
          "3104017001", "Relation setting list - initial display"
      }, {
          /** 関連付け一覧登録処理 */
          "3104017002", "Relation setting list - registration"
      }, {
          //M17N-0008 追加 ここから
          /** 商品テキスト完了処理 */
          "3104018001", "Commodity text - complete"
      }, {
          /** 商品テキスト初期表示処理 */
          "3104018002", "Commodity text - initial display"
      }, {
          /** 商品テキスト検索処理 */
          "3104018003", "Commodity text - search"
      }, {
          /** 商品テキスト更新処理 */
          "3104018004", "Commodity text - update"
          //M17N-0008 追加 ここまで
      // 2012/11/21 促销对应 ob add start
      }, {
          /** セット品設定初期化処理 */
          "3104019001", "Commodity set - initial display"
      }, {
          /** セット品明細登録処理 */
          "3104019002", "Commodity set - registration"
      }, {
          /** セット品明細削除処理 */
          "3104019003", "Commodity set - deletion"
	  // 2012/11/21 促销对应 ob add end
      }, {
          /** カテゴリマスタ削除処理 */
          "3104021001", "Category master - deletion"
      }, {
          /** カテゴリマスタ属性削除処理 */
          "3104021002", "Category master -  attrubution deletion"
      }, {
          /** カテゴリマスタ初期表示処理 */
          "3104021003", "Category master - initial display"
      }, {
          /** カテゴリマスタ遷移処理 */
          "3104021004", "Category master - transition"
      }, {
          /** カテゴリマスタ登録処理 */
          "3104021005", "Category master - registration"
      }, {
          /** カテゴリマスタ選択処理 */
          "3104021006", "Category master - select"
      }, {
          /** カテゴリマスタ更新処理 */
          "3104021007", "Category master - update"
      }, {
          /** カテゴリ－商品関連付け削除処理 */
          "3104022001", "Category-Commodity Relation - deletion"
      }, {
          /** カテゴリ－商品関連付け初期表示処理 */
          "3104022002", "Category-Commodity Relation - initial display"
      }, {
          /** カテゴリ－商品関連付け登録処理 */
          "3104022003", "Category-Commodity Relation - registration"
      }, {
          /** カテゴリ－商品関連付け検索処理 */
          "3104022004", "Category-Commodity Relation - search"
      }, {
          /** カテゴリ－商品関連付け選択処理 */
          "3104022005", "Category-Commodity Relation - select"
      }, {
          /** カテゴリ－商品関連付け更新処理 */
          "3104022006", "Category-Commodity Relation - update"
      }, {
          /** カテゴリツリー関連付け初期表示処理 */
          "3104023001", "Category tree relation setting - initial display"
      }, {
          /** カテゴリツリー関連付け登録処理 */
          "3104023002", "Category tree relation setting - registration"
      }, {
          /** ギフトマスタ削除処理 */
          "3104031001", "Gift master - deletion"
      }, {
          /** ギフトマスタ初期表示処理 */
          "3104031002", "Gift master - initial display"
      }, {
          /** ギフトマスタ遷移処理 */
          "3104031003", "Gift master - transition"
      }, {
          /** ギフトマスタ登録処理 */
          "3104031004", "Gift master - registration"
      }, {
          /** ギフトマスタ検索処理 */
          "3104031005", "Gift master - search"
      }, {
          /** ギフトマスタ選択処理 */
          "3104031006", "Gift master - select"
      }, {
          /** ギフトマスタ更新処理 */
          "3104031007", "Gift master - update"
      }, {
          /** タグマスタ完了処理 */
          "3104041001", "Tag master - completion"
      }, {
          /** タグマスタ削除処理 */
          "3104041002", "Tag master - deletion"
      }, {
          /** タグマスタ初期表示処理 */
          "3104041003", "Tag master - initial espress"
      }, {
          /** タグマスタ遷移処理 */
          "3104041004", "Tag master - transition"
      }, {
          /** タグマスタ登録処理 */
          "3104041005", "Tag master - registration"
      }, {
          /** タグマスタ検索処理 */
          "3104041006", "Tag master - search"
      }, {
          /** タグマスタ検索戻る処理 */
          "3104041007", "Tag master - search back"
      }, {
          /** タグマスタ選択処理 */
          "3104041008", "Tag master - select "
      }, {
          /** タグマスタ更新処理 */
          "3104041009", "Tag master - update"
      }, {
          /** 入出庫管理完了処理 */
          "3104051001", "Stock I/O management - completion"
      }, {
          /** 入出庫管理初期表示処理 */
          "3104051002", "Stock I/O management - registration"
      }, {
          /** 入出庫管理登録処理 */
          "3104051003", "Stock I/O management - registration"
      }, {
          /** 入出庫管理検索処理 */
          "3104051004", "Stock I/O management - search"
      }, {
          /** 在庫状況設定削除処理 */
          "3104061001", "Stock status setting - deletion"
      }, {
          /** 在庫状況設定初期表示処理 */
          "3104061002", "Stock status setting - initial display"
      }, {
          /** 在庫状況設定登録処理 */
          "3104061003", "Stock status setting - registration"
      }, {
          /** 在庫状況設定検索処理 */
          "3104061004", "Stock status setting - search"
      }, {
          /** 在庫状況設定選択処理 */
          "3104061005", "Stock status setting - select"
      }, {
          /** 在庫状況設定更新処理 */
          "3104061006", "Stock status setting - update"
      }, {
          /** 入荷お知らせ削除処理 */
          "3104071001", "Arrival notice - deletion"
      }, {
          /** 入荷お知らせ出力処理 */
          "3104071002", "Arrival notice - export"
      }, {
          /** 入荷お知らせ初期表示処理 */
          "3104071003", "Arrival notice - initial display"
      }, {
          /** 入荷お知らせ検索処理 */
          "3104071004", "Arrival notice - search"
      }, {
          /** 商品詳細レイアウト完了処理 */
          "3104081001", "Commodity details layout - completion"
      }, {
          /** 商品詳細レイアウト初期表示処理 */
          "3104081002", "Commodity details layout - initial display"
      }, {
          /** 商品詳細レイアウト登録処理 */
          "3104081003", "Commodity details layout - registration"
      }, {
          /** 商品詳細レイアウトリセット処理 */
          "3104081004", "Commodity details layout - reset"
      }, {
          /** 商品詳細レイアウト検索処理 */
          "3104081005", "Commodity details layout - search"
      }, {
          //M17N-0008 追加 ここから
          /** 多言語テキスト完了処理 */
          "7108051001", "多语言完成处理"
      }, {
          /** 多言語テキスト初期表示処理 */
          "7108051002", "多语言初期表示处理"
      }, {
          /** 多言語テキスト検索処理 */
          "7108051003", "多语言检索处理"
      }, {
          /** 多言語テキスト更新処理 */
          "7108051004", "多语言更新处理"
         //M17N-0008 追加 ここまで
      },
  };

  private static final Object[][] SHOP = new Object[][] {
      {
          /** サイト設定戻る処理 */
          "4105011001", "Site setting - back"
      }, {
          /** サイト設定確認処理 */
          "4105011002", "Site setting - confirmation"
      }, {
          /** サイト設定初期表示処理 */
          "4105011003", "Site setting - initial display"
      }, {
          /** サイト設定登録処理 */
          "4105011004", "Site setting - registration"
      }, {
          /** ショップマスタ編集処理 */
          "4105021001", "Shop master - edit"
      }, {
          /** ショップマスタ初期表示処理 */
          "4105021002", "Shop master - initial display"
      }, {
          /** ショップマスタ検索処理 */
          "4105021003", "Shop master - search"
      }, {
          /** ショップマスタ検索戻る処理 */
          "4105021004", "Shop master - search back"
      }, {
          /** ショップ登録戻る処理 */
          "4105022001", "Shop registration - back"
      }, {
          /** ショップ登録確認処理 */
          "4105022002", "Shop registration - confirmation"
      }, {
          /** ショップ登録削除処理 */
          "4105022003", "Shop registration - deletion"
      }, {
          /** ショップ登録初期表示処理 */
          "4105022004", "Shop registration - initial display"
      }, {
          /** ショップ登録登録処理 */
          "4105022005", "Shop registration - registration"
      }, {
          /** ショップ登録遷移処理 */
          "4105022006", "Shop registration - transition"
      }, {
          /** ショップ登録更新処理 */
          "4105022007", "Shop registration - update"
      }, {
          /** ショップマスタ削除確認キャンセル処理 */
          "4105024001", "Shop deletion confirmation - cancel"
      }, {
          /** ショップマスタ削除確認削除処理 */
          "4105024002", "Shop deletion confirmation - deletion"
      }, {
          /** ショップマスタ削除確認初期表示処理 */
          "4105024003", "Shop deletion confirmation - initial display"
      }, {
          /** ポイントルール初期表示処理 */
          "4105031001", "Point rule - initial display"
      }, {
          /** ポイントルール登録処理 */
          "4105031002", "Point rule - registration"
      }, {
          /** 消費税マスタ削除処理 */
          "4105041001", "Consumption tax master - deletion"
      }, {
          /** 消費税マスタ初期表示処理 */
          "4105041002", "Consumption tax master - initial display"
      }, {
          /** 消費税マスタ登録処理 */
          "4105041003", "Consumption tax master - registration"
      }, {
          /** 支払方法設定手数料削除処理 */
          "4105051001", "Payment method setting - commission deletion"
      }, {
          /** 支払方法設定支払方法削除処理 */
          "4105051002", "Payment method setting - payment method deletion"
      }, {
          /** 支払方法設定初期表示処理 */
          "4105051003", "Payment method setting - initial display"
      }, {
          /** 支払方法設定遷移編集処理 */
          "4105051004", "Payment method setting - transition edit"
      }, {
          /** 支払方法設定支払情報処理 */
          "4105051005", "Payment method setting - payment information"
      }, {
          /** 支払方法設定登録支払手数料処理 */
          "4105051006", "Payment method setting - commission registration"
      }, {
          /** 支払方法設定更新支払手数料処理 */
          "4105051007", "Payment method setting - commission update"
      }, {
          /** 支払方法詳細初期表示処理 */
          "4105052001", "Payment method details - initial display"
      }, {
          /** 支払方法詳細金融機関設定遷移処理 */
          "4105052002", "Payment method details - Financial institution setting transition"
      }, {
          /** 支払方法詳細登録処理 */
          "4105052003", "Payment method details - registration"
      }, {
          /** 金融機関設定削除処理 */
          "4105053001", "Financial institution setting - deletion"
      }, {
          /** 金融機関設定初期表示処理 */
          "4105053002", "Financial institution setting - initial display"
      }, {
          /** 金融機関設定登録処理 */
          "4105053003", "Financial institution setting - registration"
      }, {
          /** 金融機関設定選択処理 */
          "4105053004", "Financial institution setting - selection"
      }, {
          /** 配送・送料設定配送種別削除処理 */
          "4105061001", "Delivery and Delivery charge setting - delivery type deletion"
      }, {
          /** 配送・送料設定配送指定時間削除処理 */
          "4105061002", "Delivery and Delivery charge setting - delivery appoint time"
      }, {
          /** 配送・送料設定初期表示処理 */
          "4105061003", "Delivery and Delivery charge setting - initial display "
      }, {
          /** 配送・送料設定配送指定時間登録処理 */
          "4105061004", "Delivery and Delivery charge setting - delivery appoint time"
      }, {
          /** 配送・送料設定遷移処理 */
          "4105061005", "Delivery and Delivery charge setting - transition "
      }, {
          /** 配送・送料設定配送指定時間表示処理 */
          "4105061006", "Delivery and Delivery charge setting - delivery appoint time display"
      }, {
          /** 配送・送料設定配送指定時間更新処理 */
          "4105061007", "Delivery and Delivery charge setting - delivery appoint time"
      }, {
          /** 配送種別設定明細削除処理 */
          "4105062001", "Delivery type setting - details deletion"
      }, {
          /** 配送種別設定明細初期表示処理 */
          "4105062002", "Delivery type set  - detailed initial display"
      }, {
          /** 配送種別設定明細登録処理 */
          "4105062003", "Delivery type setting  - details registration"
      }, {
          /** 配送種別設定明細更新処理 */
          "4105062004", "Delivery type setting  - details update"
      }, {
          /** カレンダーマスタ初期表示処理 */
          "4105071001", "Calendar master - initial display"
      }, {
          /** カレンダーマスタ登録処理 */
          "4105071002", "Calendar master - registration"
      }, {
          /** 地域ブロック設定削除処理 */
          "4105081001", "Regional block setting - deletion"
      }, {
          /** 地域ブロック設定初期表示処理 */
          "4105081002", "Regional block setting - initial display"
      }, {
          /** 地域ブロック設定登録処理 */
          "4105081003", "Regional block setting - registration"
      }, {
          /** 地域ブロック設定登録都道府県処理 */
          "4105081004", "Regional block setting - Prefectures registration"
      }, {
          /** 地域ブロック設定更新処理 */
          "4105081005", "Regional block setting - update"
      }, {
          /** 管理ユーザマスタ削除処理 */
          "4105091001", "Management user master - deletion"
      }, {
          /** 管理ユーザマスタ初期表示処理 */
          "4105091002", "Management user master - initial display"
      }, {
          /** 管理ユーザマスタ遷移処理 */
          "4105091003", "Management user master - transition"
      }, {
          /** 管理ユーザマスタ検索処理 */
          "4105091004", "Management user master - search"
      }, {
          /** 管理ユーザマスタ明細戻る処理 */
          "4105092001", "Management user master details - back"
      }, {
          /** 管理ユーザマスタ明細確認処理 */
          "4105092002", "Management user master details - confirmation"
      }, {
          /** 管理ユーザマスタ明細初期表示処理 */
          "4105092003", "Management user master details - initial display"
      }, {
          /** 管理ユーザマスタ明細登録処理 */
          "4105092004", "Management user master details - registration"
      }, {
          /** 管理ユーザマスタ明細更新処理 */
          "4105092005", "Management user master details - update"
      }, {
          /** 管理側アクセスログ初期表示処理 */
          "4105094001", "Management side access log - initial display"
      }, {
          /** 管理側アクセスログ検索処理 */
          "4105094002", "Management side access log - search"
      }, {
          /** メールテンプレート情報メール削除処理 */
          "4105111001", "E-mail template - information E-mail deletion"
      }, {
          /** メールテンプレート初期表示処理 */
          "4105111002", "E-mail template - initial display"
      }, {
          /** メールテンプレート情報メール登録処理 */
          "4105111003", "E-mail template - information E-mail registration"
      }, {
          /** メールテンプレートプレビュー処理 */
          "4105111004", "E-mail template - preview"
      }, {
          /** メールテンプレート登録処理 */
          "4105111005", "E-mail template - registration"
      }, {
          /** メールテンプレート登録プレビュー処理 */
          "4105111006", "E-mail template - registration preview"
      }, {
          /** メールテンプレートメールタイプ選択処理 */
          "4105111007", "E-mail template - mail type selection"
      }, {
          /** お知らせ編集削除処理 */
          "4105121001", "Information edit - deletion"
      }, {
          /** お知らせ編集初期表示処理 */
          "4105121002", "Information edit - initial display"
      }, {
          /** お知らせ編集登録処理 */
          "4105121003", "Information edit - registration"
      }, {
          /** お知らせ編集選択処理 */
          "4105121004", "Information edit - selection"
      },
  };

  private static final Object[][] COMMUNICATION = new Object[][] {
      {
          /** アンケート管理削除処理 */
          "5106011001", "Enquete management - deletion"
      }, {
          /** アンケート管理初期表示処理 */
          "5106011002", "Enquete management - initial display"
      }, {
          /** アンケート管理遷移処理 */
          "5106011003", "Enquete management - transition"
      }, {
          /** アンケート管理検索処理 */
          "5106011004", "Enquete management - search"
      }, {
          /** アンケート管理検索戻る処理 */
          "5106011005", "Enquete management - search back"
      }, {
          /** アンケートマスタ変更設問処理 */
          "5106012001", "Enquete master - change question"
      }, {
          /** アンケートマスタ削除選択処理 */
          "5106012002", "Enquete master - delete selection"
      }, {
          /** アンケートマスタ削除設問処理 */
          "5106012003", "Enquete master - delete question"
      }, {
          /** アンケートマスタ明細処理 */
          "5106012004", "Enquete master - details"
      }, {
          /** アンケートマスタ初期表示処理 */
          "5106012005", "Enquete master - initial display"
      }, {
          /** アンケートマスタ新規選択処理 */
          "5106012006", "Enquete master - new selection"
      }, {
          /** アンケートマスタ登録基礎処理 */
          "5106012007", "Enquete master - register basis"
      }, {
          /** アンケートマスタ登録選択処理 */
          "5106012008", "Enquete master - register selection"
      }, {
          /** アンケートマスタ登録設問処理 */
          "5106012009", "Enquete master - register question"
      }, {
          /** アンケートマスタ選択選択処理 */
          "5106012010", "Enquete master - select selection"
      }, {
          /** アンケートマスタ選択設問処理 */
          "5106012011", "Enquete master - select question"
      }, {
          /** アンケートマスタ更新基礎処理 */
          "5106012012", "Enquete master - update basis"
      }, {
          /** アンケートマスタ更新選択処理 */
          "5106012013", "Enquete master - update selection"
      }, {
          /** アンケートマスタ更新設問処理 */
          "5106012014", "Enquete master - update question"
      }, {
          /** アンケート分析出力処理 */
          "5106013001", "Enquete analysis - export"
      }, {
          /** アンケート分析初期表示処理 */
          "5106013002", "Enquete analysis - initial display"
      }, {
          /** レビュー管理削除処理 */
          "5106021001", "Review management - deletion"
      }, {
          /** レビュー管理初期表示処理 */
          "5106021002", "Review management - initial display"
      }, {
          /** レビュー管理遷移処理 */
          "5106021003", "Review management - transition"
      }, {
          /** レビュー管理検索処理 */
          "5106021004", "Review management - search"
      }, {
          /** レビュー管理検索戻る処理 */
          "5106021005", "Review management - search back"
      }, {
          /** レビュー管理更新処理 */
          "5106021006", "Review management - update"
      }, {
          /** キャンペーン管理削除処理 */
          "5106031001", "Campaign management - deletion"
      }, {
          /** キャンペーン管理初期表示処理 */
          "5106031002", "Campaign management - initial display"
      }, {
          /** キャンペーン管理遷移処理 */
          "5106031003", "Campaign management - transition"
      }, {
          /** キャンペーン管理検索処理 */
          "5106031004", "Campaign management - search"
      }, {
          /** キャンペーン管理検索戻る処理 */
          "5106031005", "Campaign management - search back"
      }, {
          /** キャンペーンマスタ初期表示処理 */
          "5106032001", "Campaign master - initial display"
      }, {
          /** キャンペーンマスタ遷移処理 */
          "5106032002", "Campaign master - transition"
      }, {
          /** キャンペーンマスタ登録処理 */
          "5106032003", "Campaign master - registration"
      }, {
          /** キャンペーン分析出力処理 */
          "5106034001", "Campaign analysis - export"
      }, {
          /** キャンペーン分析初期表示処理 */
          "5106034002", "Campaign analysis - initial display"
      }, {
          /** メールマガジンマスタ削除処理 */
          "5106041001", "E-mail magazine master - deletion"
      }, {
          /** メールマガジンマスタ出力処理 */
          "5106041002", "E-mail magazine master - export"
      }, {
          /** メールマガジンマスタ初期表示処理 */
          "5106041003", "E-mail magazine master - initial display"
      }, {
          /** メールマガジンマスタ登録処理 */
          "5106041004", "E-mail magazine master - registration"
      }, {
          /** メールマガジンマスタ選択処理 */
          "5106041005", "E-mail magazine master - selection"
      }, {
          /** メールマガジンマスタ更新処理 */
          "5106041006", "E-mail magazine master - update"
      }, {
      // 2012/11/21 促销对应 ob add start	  
          /** キャンペーン管理初期表示処理 */
          "5106101001", "Campaign master - initial display"
      }, {
          /** キャンペーン管理遷移処理 */
          "5106101002", "Campaign master - transition"
      }, {
          /** キャンペーン管理検索処理 */
          "5106101003", "Campaign master - search"
      }, {
          /** キャンペーン管理検索戻る処理 */
          "5106101004", "Campaign master - search back"
      }, {
          /** キャンペーン管理削除処理 */
          "5106101005", "Campaign master - deletion"
      }, {
    	  /** キャンペーンマスタ初期表示処理 */
          "5106102001", "Campaign edit - initial display"
      }, {
          /** キャンペーンマスタ登録処理 */
          "5106102002", "Campaign edit - registration"
      }, {
          /** キャンペーンマスタ更新処理 */
          "5106102003", "Campaign edit - update"
      }, {
          /** キャンペーンマスタ条件設定処理 */
          "5106102004", "Campaign edit - condition"
      }, {
          /** キャンペーンマスタ行為設定処理 */
          "5106102005", "Campaign edit - doings"
      }, {
          /** キャンペーンマスタ行為設定処理 */
          "5106102006", "Campaign edit - transition"
      }
      // 2012/11/21 促销对应 ob add end
  };

  private static final Object[][] ANALYSIS = new Object[][] {
      {
          /** アクセスログ集計出力処理 */
          "6107011003", "Access log total - export"
      }, {
          /** アクセスログ集計初期表示処理 */
          "6107011004", "Access log total - initial display"
      }, {
          /** アクセスログ集計遷移処理 */
          "6107011005", "Access log total - transition"
      }, {
          /** アクセスログ集計検索処理 */
          "6107011006", "Access log total - search"
      }, {
          /** リファラー集計出力処理 */
          "6107013001", "Referrer total - export"
      }, {
          /** リファラー集計初期表示処理 */
          "6107013002", "Referrer total - initial display"
      }, {
          /** リファラー集計検索処理 */
          "6107013003", "Referrer total - search"
      }, {
          /** 商品別アクセスログ集計出力処理 */
          "6107021001", "Access log total by commodity - export"
      }, {
          /** 商品別アクセスログ集計初期表示処理 */
          "6107021002", "Access log total by commodity - initial display"
      }, {
          /** 商品別アクセスログ集計検索処理 */
          "6107021003", "Access log total by commodity - search"
      }, {
          /** 顧客嗜好分析出力処理 */
          "6107031001", "Customer preference analysis - export"
      }, {
          /** 顧客嗜好分析初期表示処理 */
          "6107031002", "Customer preference analysis - initial display"
      }, {
          /** 顧客嗜好分析検索処理 */
          "6107031003", "Customer preference analysis - search"
      }, {
          /** 顧客嗜好分析選択処理 */
          "6107031004", "Customer preference analysis - selection"
      }, {
          /** 顧客分析初期表示処理 */
          "6107051001", "Customer analysis - initial display"
      }, {
          /** RFM分析出力処理 */
          "6107071001", "RFM analysis - export"
      }, {
          /** RFM分析初期表示処理 */
          "6107071002", "RFM analysis - initial display"
      }, {
          /** RFM分析検索処理 */
          "6107071003", "RFM analysis - search"
      }, {
          /** ショップ別売上集計出力処理 */
          "6107081001", "Sales total by shop - export"
      }, {
          /** ショップ別売上集計初期表示処理 */
          "6107081002", "Sales total by shop - initial display"
      }, {
          /** ショップ別売上集計遷移処理 */
          "6107081003", "Sales total by shop - transition"
      }, {
          /** ショップ別売上集計検索処理 */
          "6107081004", "Sales total by shop - search"
      }, {
          /** ショップ別売上集計検索戻る処理 */
          "6107081005", "Sales total by shop - search back"
      }, {
          /** 売上集計出力処理 */
          "6107082001", "Sales total - export"
      }, {
          /** 売上集計初期表示処理 */
          "6107082002", "Sales total - initial display"
      }, {
          /** 売上集計検索処理 */
          "6107082003", "Sales total - search"
      }, {
          /** 商品別売上集計出力処理 */
          "6107083001", "Sales total by commodity - export"
      }, {
          /** 商品別売上集計初期表示処理 */
          "6107083002", "Sales total by commodity - initial display"
      }, {
          /** 商品別売上集計検索処理 */
          "6107083003", "Sales total by commodity - search"
      }, {
          /** 検索キーワード集計出力処理 */
          "6107091001", "Search keyword total - export"
      }, {
          /** 検索キーワード集計初期表示処理 */
          "6107091002", "Search keyword total - initial display"
      }, {
          /** 検索キーワード集計検索処理 */
          "6107091003", "Search keyword total - search"
      },
  };

  private static final Object[][] DATA_IO = new Object[][] {
      {
          /** データ一括出力出力処理 */
          "7108011001", "Data batch export - export"
      }, {
          /** データ一括出力初期表示処理 */
          "7108011002", "Data batch export - initial display"
      }, {
          /** データ一括取込完了処理 */
          "7108021001", "Data batch import - completion"
      }, {
          /** データ一括取込初期表示処理 */
          "7108021002", "Data batch import - initial display"
      }, {
          /** ファイルアップロード完了処理 */
          "7108031001", "File upload - completion "
      }, {
          /** ファイルアップロード初期表示処理 */
          "7108031002", "File upload - initial display"
      }, {
          /** ファイルアップロード検索処理 */
          "7108031003", "File upload - search"
      }, {
          /** ファイル削除削除処理 */
          "7108041001", "File deletion - deletion"
      }, {
          /** ファイル削除初期表示処理 */
          "7108041002", "File deletion - initial display"
      }, {
          /** ファイル削除検索処理 */
          "7108041003", "File deletion - search"
      }, {
          //M17N-0008 追加 ここから
          /** 多言語テキスト完了処理 */
          "7108051001", "Multilingual text - complete"
      }, {
          /** 多言語テキスト初期表示処理 */
          "7108051002", "Multilingual text - initial display"
      }, {
          /** 多言語テキスト検索処理 */
          "7108051003", "Multilingual text - search"
      }, {
          /** 多言語テキスト更新処理 */
          "7108051004", "Multilingual text - update"
         //M17N-0008 追加 ここまで
      },
  };

  private static final Object[][] OTHER = new Object[][] {
    {
        /** その他共通処理 */
        "9999999999", "Other Common processing"
    },
  };

  @Override
  protected Object[][] getContents() {
    List<Object[]> list = new ArrayList<Object[]>();
    for (Object[] items : COMMON) {
      list.add(items);
    }
    for (Object[] items : ORDER) {
      list.add(items);
    }
    for (Object[] items : CUSTOMER) {
      list.add(items);
    }
    for (Object[] items : CATALOG) {
      list.add(items);
    }
    for (Object[] items : SHOP) {
      list.add(items);
    }
    for (Object[] items : COMMUNICATION) {
      list.add(items);
    }
    for (Object[] items : ANALYSIS) {
      list.add(items);
    }
    for (Object[] items : DATA_IO) {
      list.add(items);
    }
    for (Object[] items : OTHER) {
      list.add(items);
    }
    Object[][] obj = new Object[list.size()][2];
    for (int i = 0; i < list.size(); i++) {
      Object[] tmp = list.get(i);
      obj[i] = tmp;
    }
    return obj;
  }

}
