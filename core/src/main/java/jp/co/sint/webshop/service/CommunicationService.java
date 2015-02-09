package jp.co.sint.webshop.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerCardInfo;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.data.dto.EnqueteChoice;
import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.data.dto.EnqueteReplyInput;
import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.data.dto.FriendCouponExchangeHistory;
import jp.co.sint.webshop.data.dto.FriendCouponIssueHistory;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.data.dto.FriendCouponUseHistory;
import jp.co.sint.webshop.data.dto.GiftCardIssueDetail;
import jp.co.sint.webshop.data.dto.GiftCardIssueHistory;
import jp.co.sint.webshop.data.dto.GiftCardReturnApply;
import jp.co.sint.webshop.data.dto.GiftCardRule;
import jp.co.sint.webshop.data.dto.MailMagazine;
import jp.co.sint.webshop.data.dto.MailMagazineSubscriber;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.NewCouponHistoryUseInfo;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.NewCouponRuleLssueInfo;
import jp.co.sint.webshop.data.dto.NewCouponRuleUseInfo;
import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.data.dto.PlanCommodity;
import jp.co.sint.webshop.data.dto.PlanDetail;
import jp.co.sint.webshop.data.dto.Prefecture;
import jp.co.sint.webshop.data.dto.PropagandaActivityCommodity;
import jp.co.sint.webshop.data.dto.PropagandaActivityRule;
import jp.co.sint.webshop.data.dto.ReviewPost;
import jp.co.sint.webshop.data.dto.ReviewSummary;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.StockHoliday;
import jp.co.sint.webshop.service.analysis.CustomerGroupCampaignSummaryViewList;
import jp.co.sint.webshop.service.analysis.CustomerGroupCampaignSummaryViewSearchCondition;
import jp.co.sint.webshop.service.analysis.GiftCardDetailListSummary;
import jp.co.sint.webshop.service.analysis.PrivateCouponListSummary;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.communication.CampaignHeadLine;
import jp.co.sint.webshop.service.communication.CampaignLine;
import jp.co.sint.webshop.service.communication.CampaignListSearchCondition;
import jp.co.sint.webshop.service.communication.CampaignResearch;
import jp.co.sint.webshop.service.communication.CampaignSearchCondition;
import jp.co.sint.webshop.service.communication.CustomerGroupCampaignHeadLine;
import jp.co.sint.webshop.service.communication.CustomerGroupCampaignSearchCondition;
import jp.co.sint.webshop.service.communication.CustomerMessageSearchCondition;
import jp.co.sint.webshop.service.communication.DiscountHeadLine;
import jp.co.sint.webshop.service.communication.DiscountInfo;
import jp.co.sint.webshop.service.communication.DiscountListSearchCondition;
import jp.co.sint.webshop.service.communication.EnqueteAnswer;
import jp.co.sint.webshop.service.communication.EnqueteList;
import jp.co.sint.webshop.service.communication.EnqueteListSearchCondition;
import jp.co.sint.webshop.service.communication.FreePostageListSearchCondition;
import jp.co.sint.webshop.service.communication.FriendCouponRuleCondition;
import jp.co.sint.webshop.service.communication.FriendCouponUse;
import jp.co.sint.webshop.service.communication.GiftCampaign;
import jp.co.sint.webshop.service.communication.GiftCardDetailListSearchCondition;
import jp.co.sint.webshop.service.communication.GiftCardReturnListSearchCondition;
import jp.co.sint.webshop.service.communication.GiftCardRuleListSearchCondition;
import jp.co.sint.webshop.service.communication.MailMagazineHeadLine;
import jp.co.sint.webshop.service.communication.MessageHeadLine;
import jp.co.sint.webshop.service.communication.MyCouponHistorySearchCondition;
import jp.co.sint.webshop.service.communication.NewCouponHistoryInfo;
import jp.co.sint.webshop.service.communication.OptionalCampaignListSearchCondition;
import jp.co.sint.webshop.service.communication.OrderReview;
import jp.co.sint.webshop.service.communication.PlanDetailHeadLine;
import jp.co.sint.webshop.service.communication.PlanRelatedHeadLine;
import jp.co.sint.webshop.service.communication.PlanSearchCondition;
import jp.co.sint.webshop.service.communication.PrivateCouponListSearchCondition;
import jp.co.sint.webshop.service.communication.PropagandaActivityCommodityInfo;
import jp.co.sint.webshop.service.communication.PropagandaActivityRuleListSearchCondition;
import jp.co.sint.webshop.service.communication.ReviewList;
import jp.co.sint.webshop.service.communication.ReviewListSearchCondition;
import jp.co.sint.webshop.service.communication.ReviewPostAndCustHeadLine;
import jp.co.sint.webshop.service.communication.ReviewPostCountSearchCondition;
import jp.co.sint.webshop.service.communication.ReviewPostCustomerCountSearchCondition;

/**
 * SI Web Shopping 10 CommunicationService(CommunicationService)仕様
 * 
 * @author System Integrator Corp.
 */
public interface CommunicationService {

  /**
   * キャンペーンを一件削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>ショップコードとキャンペーンコードを指定して、キャンペーンを一件削除します。
   * <ol>
   * <li>引数で受け取ったショップコード、キャンペーンコードに一致するキャンペーン情報が存在しない場合、<br>
   * 該当データ未存在エラーを返します。</li>
   * <li>受注明細テーブルから参照されている場合、キャンペーン削除エラーを返します。</li>
   * <li>該当のキャンペーンとキャンペーン商品データを削除します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>ショップコード、キャンペーンコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>指定されたショップコード、キャンペーンコード、に該当するキャンペーン、キャンペーン商品データが削除される。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param campaignCode
   *          キャンペーンコード
   * @return 削除結果
   */
  ServiceResult deleteCampaign(String shopCode, String campaignCode);

  /**
   * アンケートを一件削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>アンケートコードを指定して、それに関連付いているアンケートのデータを全て削除します。
   * <ol>
   * <li>引数で受け取ったアンケートコードに一致するアンケート情報が存在しない場合、<br>
   * 該当データ未存在エラーを返します。</li>
   * <li>該当のアンケート本体、設問、選択肢、回答ヘッダ、回答(選択式、入力式の両方)を削除します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>指定されたアンケートコードに該当するアンケート本体、設問、選択肢、回答ヘッダ、回答(選択式、入力式の両方)が削除される。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          アンケート
   * @return サービス実行結果
   */
  ServiceResult deleteEnquete(String enqueteCode);

  /**
   * アンケートの選択肢を削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>アンケートコード、設問番号、選択肢番号を指定して、アンケート選択肢を削除します。
   * <ol>
   * <li>引数で受け取ったアンケートコード、設問番号、選択肢番号に一致するアンケート選択肢情報が存在しない場合、<br>
   * 該当データ未存在エラーを返します。</li>
   * <li>該当の選択肢、回答ヘッダ、選択式回答を削除します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>アンケートコード、設問番号、選択肢番号がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>指定されたアンケートコード、設問番号、選択肢番号に該当するアンケート選択肢、回答ヘッダ、選択式回答が削除される。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          アンケートコード
   * @param enqueteQuestionNo
   *          アンケート設問番号
   * @param enqueteChoiceNo
   *          アンケート選択肢番号
   * @return サービス実行結果
   */
  ServiceResult deleteEnqueteChoice(String enqueteCode, Long enqueteQuestionNo, Long enqueteChoiceNo);

  /**
   * アンケートの設問を削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>アンケートコード、設問番号を指定して、アンケート設問を削除します。
   * <ol>
   * <li>引数で受け取ったアンケートコード、アンケート設問番号に一致するアンケート設問情報が存在しない場合、<br>
   * 該当データ未存在エラーを返します。</li>
   * <li>該当のアンケート設問、選択肢、回答(選択式、入力式の両方)を削除します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコード、設問番号がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>指定されたアンケートコード、設問番号に該当するアンケート設問、選択肢、回答(選択式、入力式の両方)が削除される。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          アンケートコード
   * @param enqueteQuestionNo
   *          アンケート設問番号
   * @return サービス実行結果
   */
  ServiceResult deleteEnqueteQuestion(String enqueteCode, Long enqueteQuestionNo);

  /**
   * メールマガジンを削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>メールマガジンコードを指定して、メールマガジンを削除します。
   * <ol>
   * <li>引数で受け取ったメールマガジンコードに一致するメールマガジン情報が存在しない場合、<br>
   * 該当データ未存在エラーを返します。</li>
   * <li>該当のメールマガジンとメールマガジン購読者を削除します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>メールマガジンコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>指定されたメールマガジンコードに該当するメールマガジンとメールマガジン購読者が削除される。</dd>
   * </dl>
   * </p>
   * 
   * @param mailMagazineCode
   *          メールマガジンコード
   * @return サービス実行結果
   */
  ServiceResult deleteMailMagazine(String mailMagazineCode);

  /**
   * レビューを削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>レビューIDを指定して、レビューを削除します。
   * <ol>
   * <li>引数で受け取ったレビューIDに一致するレビュー情報を削除します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>レビューIDがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>指定されたレビューIDに該当するレビューが削除される。</dd>
   * </dl>
   * </p>
   * 
   * @param reviewId
   * @return 削除結果
   */
  ServiceResult deleteReviewPost(String reviewId);

  /**
   * キャンペーンを一件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>ショップコードとキャンペーンコードを指定して、キャンペーンを取得します。
   * <ol>
   * <li>引数で受け取ったショップコード、キャンペーンコードに一致するキャンペーン情報を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>ショップコード、キャンペーンコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param campaignCode
   *          キャンペーンコード
   * @return キャンペーン
   */
  Campaign getCampaign(String shopCode, String campaignCode);

  /**
   * 検索条件に該当するキャンペーンのリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>検索条件に該当するキャンペーンのリストを返します。
   * <ol>
   * <li>引数で受け取った検索条件を元に該当するキャンペーンのリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          キャンペーン一覧検索条件
   * @return 取得結果。ショップコードが空の場合は、全ショップのキャンペーンを返します。
   *         それ以外の場合は、該当ショップのキャンペーンのみを返します。
   */
  SearchResult<CampaignHeadLine> getCampaignList(CampaignListSearchCondition condition);

  /**
   * キャンペーン結果を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>ショップコードとキャンペーンコードを指定して、キャンペーン結果を取得します。
   * <ol>
   * <li>引数で受け取ったショップコード、キャンペーンコードに一致するキャンペーン結果のリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>ショップコード、キャンペーンコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param campaignCode
   *          キャンペーンコード
   * @return キャンペーン結果
   */
  List<CampaignResearch> getCampaignResearch(String shopCode, String campaignCode);

  /**
   * 現在実施中のアンケートを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>アンケート開始日が現在日時以前であり、かつアンケート終了日が現在日時以降であるアンケートを取得します。
   * <ol>
   * <li>取得した結果を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>実施中のアンケートは常に一件であること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return 取得結果。該当データが存在しない場合はnullを返します。
   */
  Enquete getCurrentEnquete();

  /**
   * アンケート情報を一件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>アンケートコードを指定してアンケートを一件取得します。
   * <ol>
   * <li>引数で受け取ったアンケートコードに一致するアンケート情報を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          アンケートコード
   * @return アンケート
   */
  Enquete getEnquete(String enqueteCode);

  /**
   * アンケートの回答者数合計を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>アンケートコードを指定して、アンケートの回答者合計を取得します。
   * <ol>
   * <li>引数で受け取ったアンケートコードに一致するアンケート回答ヘッダ情報の件数を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          アンケートコード
   * @return アンケートの回答者数
   */
  Long getEnqueteAnswerCount(String enqueteCode);

  /**
   * アンケートの選択肢を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>アンケートコード、設問番号、選択肢番号を指定して、アンケートの選択肢を取得します。
   * <ol>
   * <li>引数で受け取ったアンケートコード、設問番号、選択肢番号に一致するアンケートの選択肢情報を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコード、設問番号、選択肢番号がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   * @param enqueteQuestionNo
   * @param enqueteChoicesNo
   * @return アンケート選択肢
   */
  EnqueteChoice getEnqueteChoice(String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo);

  /**
   * アンケート選択肢のリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>アンケートコード、設問番号を指定して、アンケート選択肢のリストを取得します。
   * <ol>
   * <li>引数で受け取ったアンケートコード、設問番号に一致するアンケート選択肢のリストを返します。<BR>
   * 返すデータの並び順はアンケートマスタで登録された「表示順」の通りです。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコード、設問番号がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          アンケートコード
   * @param enqueteQuestionNo
   *          アンケート設問番号
   * @return アンケート選択肢のリスト
   */
  List<EnqueteChoice> getEnqueteChoiceList(String enqueteCode, Long enqueteQuestionNo);

  /**
   * 検索条件に該当するアンケート情報のリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>検索条件に該当するアンケートのリストを返します。
   * <ol>
   * <li>引数で受け取った検索条件を元に該当するアンケートのリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return アンケート情報のリスト
   */
  SearchResult<EnqueteList> getEnqueteList(EnqueteListSearchCondition condition);

  /**
   * アンケートの設問を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>アンケートコード、設問番号を指定して、アンケートの設問を取得します。
   * <ol>
   * <li>引数で受け取ったアンケートコード、設問番号に一致するアンケート設問情報を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコード、設問番号がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          アンケートコード
   * @param questionNo
   *          アンケート設問番号
   * @return アンケート設問
   */
  EnqueteQuestion getEnqueteQuestion(String enqueteCode, Long questionNo);

  /**
   * アンケート設問のリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>アンケートコードを指定し、該当するアンケート設問のリストを返します。
   * <ol>
   * <li>引数で受け取ったアンケートコードに一致するアンケート設問のリストを返します。<BR>
   * 返すデータの並び順はアンケートマスタで登録された「表示順」の通りです。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          アンケートコード
   * @return アンケート設問のリスト
   */
  List<EnqueteQuestion> getEnqueteQuestionList(String enqueteCode);

  /**
   * アンケート入力式回答のリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>アンケートコード、設問番号を指定し、該当するアンケート入力式回答のリストを返します。
   * <ol>
   * <li>引数で受け取ったアンケートコード、設問番号に一致するアンケート自由回答のリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコード、設問番号がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          アンケートコード
   * @param enqueteQuestionNo
   *          アンケート設問番号
   * @return アンケート自由回答のリスト
   */
  List<EnqueteReplyInput> getEnqueteReplyInputList(String enqueteCode, Long enqueteQuestionNo);

  /**
   * メールマガジンを一件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>メールマガジンコードを指定して、メールマガジンを取得します。
   * <ol>
   * <li>引数で受け取ったメールマガジンコードに一致するメールマガジン情報を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>メールマガジンコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param mailMagazineCode
   *          メールマガジンコード
   * @return メールマガジン
   */
  MailMagazine getMailMagazine(String mailMagazineCode);

  /**
   * 表示状態に関わらず、現在登録されているメールマガジンを全件取得します。<BR>
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>メールマガジンヘッダーのリストを取得します。
   * <ol>
   * <li>取得した結果を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return メールマガジンヘッダーリスト。 メールマガジンヘッダーには、各メールマガジンの購読人数が含まれます。<BR>
   *         リストは購読人数の降順で返します。
   */
  List<MailMagazineHeadLine> getMailMagazineHeaderList();

  /**
   * 表示状態が「表示」のメールマガジンのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>メールマガジンのリストを取得します。
   * <ol>
   * <li>取得した結果を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return メールマガジンリスト。リストは購読人数の降順で返します。
   */
  List<MailMagazine> getMailMagazineList();

  /**
   * 指定のメールアドレスが購読しているメールマガジンのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>指定のメールアドレスが購読しているメールマガジンのリストを取得します。
   * <ol>
   * <li>引数で受け取ったメールアドレスに関連付いているメールマガジン情報のリストをメールマガジンテーブルから取得します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return メールマガジンリスト。該当のデータが存在しない場合は空のListを返します。
   */
  List<MailMagazine> getSubscribersMailMagazineList(String email);

  /**
   * アンケートの各選択肢における回答者数合計を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>アンケートコード、設問番号、選択肢番号を指定して、選択肢の回答者数合計を取得します。
   * <ol>
   * <li>引数で受け取ったアンケートコード、設問番号、選択肢番号に一致するアンケート回答選択式情報の件数を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコード、設問番号、選択肢番号がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          アンケートコード
   * @param questionNo
   *          アンケート設問番号
   * @param choiceNo
   *          アンケート選択肢番号
   * @return 選択肢の回答者数
   */
  Long getRepliedChoiceCount(String enqueteCode, Long questionNo, Long choiceNo);

  /**
   * アンケートの各設問における回答数の合計を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>アンケートコード、設問番号を指定して、設問の回答数合計を取得します。
   * <ol>
   * <li>引数で受け取ったアンケートコード、設問番号に一致するアンケート回答選択式情報の件数を返します。<BR>
   * 取得結果は、回答者の人数の合計ではなく回答数の合計です。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコード、設問番号がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          アンケートコード
   * @param questionNo
   *          アンケート設問番号
   * @return 各設問の回答数
   */
  Long getRepliedQuestionCount(String enqueteCode, Long questionNo);

  /**
   * アンケートの各設問の回答者数合計を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>アンケートコード、設問番号、設問タイプを指定して、設問の回答数合計を取得します。
   * <ol>
   * <li>設問タイプが入力式の場合は、アンケート回答入力式テーブルから結果を取得します。</li>
   * <li>設問タイプが入力式以外の場合は、アンケート回答選択式テーブルから結果を取得します。<BR>
   * 取得結果は、回答数の合計ではなく回答者数(人数)の合計です。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコード、設問番号、設問タイプがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          アンケートコード
   * @param questionNo
   *          アンケート設問番号
   * @param questionType
   *          設問タイプ
   * @return 設問の回答者数
   */
  Long getRepliedQuestionPersonsCount(String enqueteCode, Long questionNo, Long questionType);

  /**
   * レビュー点数集計情報を取得します。<BR>
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>ショップコード、商品コードを指定して、レビュー点数集計を取得します。
   * <ol>
   * <li>引数で受け取ったショップコード、商品コードに一致するレビュー点数集計を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>アンケートコード、商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return レビュー点数集計情報
   */
  ReviewSummary getReviewCommodity(String shopCode, String commodityCode);

  /**
   * レビュー投稿情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>レビューIDを指定して、レビューを取得します。
   * <ol>
   * <li>引数で受け取ったレビューIDに一致するレビュー投稿情報を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>レビューIDがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param reviewId
   *          レビューID
   * @return レビュー投稿情報
   */
  ReviewPost getReviewPost(String reviewId);

  /**
   * レビュー投稿件数を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>検索条件に該当するレビュー投稿件数を取得します。
   * <ol>
   * <li>引数で受け取った検索条件を元に該当するレビュー投稿件数を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return レビュー投稿件数
   */
  Long getReviewPostCount(ReviewPostCountSearchCondition condition);

  // 20111216 lirong add start
  /**
   * 查看顾客对商品评论的次数。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt> </ol> </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>アンケートコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          查询条件
   * @return 评论次数
   */
  Long getReviewPostCount(ReviewPostCustomerCountSearchCondition condition);

  // 20111216 lirong add end

  /**
   * レビュー投稿情報のリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>検索条件にもとづきレビューのリストを返します。
   * <ol>
   * <li>引数で受け取った検索条件に一致するレビュー投稿情報のリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件： 表示状態(デフォルトは未チェックのもの)、ショップ名 投稿日(from,to)、<BR>
   *          商品コード(from,to)、商品名、タイトル・内容
   * @return レビュー投稿情報のリスト
   */
  SearchResult<ReviewList> getReviewPostList(ReviewListSearchCondition condition);

  // 10.1.4 10189 追加 ここから
  /**
   * 表示状態のレビュー投稿情報のリストを全件返します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>ショップコードと商品コードをもとに表示状態のレビュー投稿情報のリストを返します。
   * <ol>
   * <li>引数で受け取ったショップコードと商品コードをもとにレビュー投稿情報のリストをレビュー投稿日の降順で返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return レビュー投稿情報のリスト
   */
  List<ReviewPost> getDisplayedReviewPostList(String shopCode, String commodityCode);

  // soukai add yyq 2013/09/13 start
  List<ReviewPostAndCustHeadLine> getDisplayedReviewPostAndCustList(String shopCode, String commodityCode);

  // soukai add yyq 2013/09/13 end
  // 10.1.4 10189 追加 ここまで

  /**
   * キャンペーンを追加します。<BR>
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>追加対象キャンペーンのデータ内容をチェックし、エラーがなければキャンペーンを追加します。
   * <ol>
   * <li>引数で受け取ったキャンペーン情報のキャンペーンコードに一致するキャンペーンが存在する場合、重複エラーを返します。</li>
   * <li>引数で受け取ったキャンペーン情報に対してValidationチェックを行います。</li>
   * <li>引数で受け取ったキャンペーン情報をDAOを用いてキャンペーンマスタに登録します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>campaignがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>キャンペーンマスタにデータを登録します。</dd>
   * </dl>
   * </p>
   * 
   * @param campaign
   *          キャンペーン
   * @return サービス実行結果
   */
  ServiceResult insertCampaign(Campaign campaign);

  /**
   * アンケートを追加します。<BR>
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>追加対象データの内容をチェックし、エラーがなければアンケートを追加します。
   * <ol>
   * <li>引数で受け取ったアンケート情報にDatbaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>引数で受け取ったアンケート情報のアンケートコードに一致するアンケートが存在する場合、重複エラーを返します。</li>
   * <li>引数で受け取ったアンケート情報のアンケート実施期間に重複するアンケートが存在する場合、アンケート期間重複エラーを返します。</li>
   * <li>引数で受け取ったアンケート情報に対してValidationチェックを行います。</li>
   * <li>引数で受け取ったアンケート情報をDAOを用いてアンケートマスタに登録します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>enqueteがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>アンケートマスタにデータを登録します。</dd>
   * </dl>
   * </p>
   * 
   * @param enquete
   *          アンケート
   * @return サービス実行結果
   */
  ServiceResult insertEnquete(Enquete enquete);

  /**
   * アンケート選択肢を追加します。<BR>
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>追加対象データの内容をチェックし、エラーがなければアンケート選択肢を追加します。
   * <ol>
   * <li>引数で受け取ったアンケート選択肢名情報のアンケートコードに一致するアンケートが存在しない場合、<br>
   * またはアンケートコード、アンケート設問番号に一致するアンケート設問情報が存在しない場合、<br>
   * 該当データ未存在エラーを返します。</li>
   * <li>引数で受け取ったアンケート選択肢名情報に、UtilSerivceを用いて取得したアンケート選択肢番号のシーケンス番号をセットします。</li>
   * <li>引数で受け取ったアンケート選択肢名情報に、DatbaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>2で値をセットしたアンケート選択肢名情報のValidationチェックを行います。</li>
   * <li>2で値をセットしたアンケート選択肢名情報をDAOを用いてアンケート選択肢名テーブルに登録します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>enqueteChoiceがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>アンケート選択肢名テーブルにデータを登録します。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteChoice
   *          アンケート選択肢名
   * @return サービス実行結果
   */
  ServiceResult insertEnqueteChoice(EnqueteChoice enqueteChoice);

  /**
   * アンケート設問を追加します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>アンケート設問を追加します。
   * <ol>
   * <li>引数で受け取ったアンケート設問情報のアンケートコードに一致するアンケートが存在しない場合、<br>
   * 該当データ未存在エラーを返します。</li>
   * <li>引数で受け取ったアンケート設問情報に、UtilSerivceを用いて取得したアンケート設問番号のシーケンス番号をセットします。</li>
   * <li>引数で受け取ったアンケート設問情報に、DatbaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>2で値をセットしたアンケート設問情報のValidationチェックを行います。</li>
   * <li>2で値をセットしたアンケート設問情報をDAOを用いてアンケート設問テーブルに登録します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>enqueteQuestionがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>アンケート設問テーブルにデータを登録します。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteQuestion
   *          アンケート設問
   * @return サービス実行結果
   */
  ServiceResult insertEnqueteQuestion(EnqueteQuestion enqueteQuestion);

  /**
   * メールマガジンを追加します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>追加対象データの内容をチェックし、エラーがなければメールマガジンを追加します。
   * <ol>
   * <li>引数で受け取ったメールマガジン情報に、DatbaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>引数で受け取ったメールマガジン情報のValidationチェックを行います。</li>
   * <li>引数で受け取ったメールマガジン情報のメールマガジンコードに一致するメールマガジンが存在する場合、<br>
   * 重複エラーを返します。</li>
   * <li>引数で受け取ったメールマガジン情報をDAOを用いてメールマガジンマスタに登録します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>追加対象データがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>メールマガジンマスタにデータを登録します。</dd>
   * </dl>
   * </p>
   * 
   * @param mailMagazine
   *          メールマガジン
   * @return サービス実行結果
   */
  ServiceResult insertMailMagazine(MailMagazine mailMagazine);

  /**
   * メールマガジン購読者を登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>追加対象データの内容をチェックし、エラーがなければメールマガジン購読者を追加します。
   * <ol>
   * <li>引数で受け取ったメールマガジン購読者情報にDatbaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>引数で受け取ったメールマガジン購読者情報のValidationチェックを行います。</li>
   * <li>引数で受け取ったメールマガジン購読者情報のメールマガジンコード、<br>
   * メールアドレスに一致するメールマガジン購読者が存在する場合、重複エラーを返します。</li>
   * <li>引数で受け取ったメールマガジン購読者情報をDAOを用いてメールマガジン購読者テーブルに登録します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>追加対象データがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>メールマガジン購読者テーブルにデータを登録します。</dd>
   * </dl>
   * </p>
   * 
   * @param mailMagazineSubscriber
   *          メールマガジン購読者
   * @return サービス実行結果
   */
  ServiceResult insertMailMagazineSubscriber(MailMagazineSubscriber mailMagazineSubscriber);

  /**
   * 指定されたメールマガジン購読者情報を削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>引数で受け取ったメールマガジン購読者を削除します。
   * <ol>
   * <li>引数で受け取ったメールマガジン購読者情報のメールマガジンコード、メールアドレスに一致するメールマガジン購読者が<BR>
   * 存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>メールマガジン購読者テーブルより、該当のレコードを削除します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>削除対象データがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>メールマガジン購読者テーブルから該当データが削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param mailMagazineSubscriber
   *          メールマガジン購読者
   * @return サービス実行結果
   */
  ServiceResult cancelMailMagazineSubscriber(MailMagazineSubscriber mailMagazineSubscriber);

  /**
   * レビューを追加します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>追加対象データの内容をチェックし、エラーがなければレビューを追加します。
   * <ol>
   * <li>引数で受け取ったレビュー投稿情報に、UtilSerivceを用いて取得したレビューIDのシーケンス番号をセットします。</li>
   * <li>1で値をセットしたレビュー投稿情報に、商品レビュー表示区分、レビューポイント割当ステータス、<br>
   * レビュー投稿日時、DatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>2で値をセットしたレビュー投稿情報のValidationチェックを行います。</li>
   * <li>引数で受け取ったレビュー投稿情報のショップコード、商品コード、顧客コードに一致するレビュー投稿が存在する場合、<br>
   * データ重複エラーを返します。</li>
   * <li>
   * ポイントシステムを使用するかつレビュー投稿時ポイントが1以上の場合、引数で受け取ったレビュー投稿情報の顧客コードに一致する顧客情報の仮発行ポイント、<br>
   * ポイント最終獲得日をセットします。</li>
   * <li>新たに生成したポイント履歴情報に、仮発行ポイントデータをセットします。</li>
   * <li>6で値をセットしたポイント履歴情報のValidationチェックを行います。</li>
   * <li>2で値をセットしたレビュー投稿情報をtransactionManagerを用いてレビュー投稿テーブルを更新します。</li>
   * <li>5で値をセットした顧客情報をtransactionManagerを用いて顧客マスタを更新します。</li>
   * <li>6で値をセットしたポイント履歴情報をtransactionManagerを用いてポイント履歴テーブルに登録します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>追加対象データがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>レビュー投稿テーブル、顧客マスタを更新、ポイント履歴テーブルにデータを登録します。</dd>
   * </dl>
   * </p>
   * 
   * @param reviewPost
   *          レビュー投稿
   * @return サービス実行結果
   */
  ServiceResult insertReviewPost(ReviewPost reviewPost);

  /**
   * アンケート回答セットを投稿します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>追加対象データの内容をチェックし、エラーがなければアンケート回答セットを追加します。
   * 投稿した顧客には、ポイントルールに従ったポイント数が付与されます。
   * <ol>
   * <li>引数で受け取ったアンケート回答ヘッダ情報に対して、DatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>引数で受け取ったアンケート回答選択式情報に対して、DatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>引数で受け取ったアンケート回答入力式情報に対して、DatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>引数で受け取ったアンケート選択肢番号に一致するアンケート選択肢名情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>実施期間中のアンケートが存在しない場合、Validationエラーを返します。</li>
   * <li>引数で受け取ったアンケートのハッシュ値をチェックし、一致しない場合、Validationエラーを返します。</li>
   * <li>1、2、3で値をセットしたアンケート情報のValidationチェックを行います。</li>
   * <li>1で値をセットしたアンケート回答ヘッダ情報をtransactionManagerを用いてアンケート回答ヘッダに登録します。</li>
   * <li>2で値をセットしたアンケート回答選択式情報をtransactionManagerを用いてアンケート回答選択式テーブルに登録します。</li>
   * <li>3で値をセットしたアンケート回答入力式情報をtransactionManagerを用いてアンケート回答入力式テーブルに登録します。</li>
   * <li>ポイントシステムを使用する、アンケート付与ポイント数が1以上、会員の場合、新たに生成したポイント履歴情報に、発行ポイント、顧客コード、<br>
   * ポイント発行ステータス、ポイント発行種別、ショップコードをポイント履歴テーブルへの追加対象データとしてセットします。</li>
   * <li>11で値をセットしたポイント履歴情報に対して、DatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>12値をセットしたポイント履歴情報のValidationチェックを行います。</li>
   * <li>12で値をセットしたポイント履歴情報をtransactionManagerを用いてポイント履歴テーブルに登録します。</li>
   * <li>ポイントシステムを使用する場合、引数で受け取ったアンケート回答情報の顧客コードに一致する顧客情報の発行ポイント、<br>
   * ポイント最終獲得日をTransactionManagerを用いて顧客マスタを更新します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>追加対象データがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>アンケート回答ヘッダ、アンケート回答選択式テーブル、アンケート回答入力式テーブル、ポイント履歴テーブルに<BR>
   * データを登録し、顧客マスタを更新します。</dd>
   * </dl>
   * </p>
   * 
   * @param answer
   *          アンケート回答
   * @return サービス実行結果
   */
  ServiceResult postEnqueteAnswer(EnqueteAnswer answer);

  /**
   * キャンペーンを更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>更新対象データの内容をチェックし、エラーがなければキャンペーンを更新します。
   * <ol>
   * <li>引数で受け取ったキャンペーン情報のショップコード、キャンペーンコードに一致するキャンペーン情報が存在しない場合、<BR>
   * 該当データ未存在エラーを返します。</li>
   * <li>引数で受け取ったキャンペーン情報にDatbaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>引数で受け取ったキャンペーン情報のValidationチェックを行います。</li>
   * <li>エラーがなければキャンペーンマスタの該当データを一件更新します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>更新対象データがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>キャンペーンマスタの該当データが更新されること。</dd>
   * </dl>
   * </p>
   * 
   * @param campaign
   *          キャンペーン
   * @return サービス実行結果
   */
  ServiceResult updateCampaign(Campaign campaign);

  /**
   * アンケートを更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>更新対象データの内容をチェックし、エラーがなければアンケートを更新します。
   * <ol>
   * <li>引数で受け取ったアンケートの実施期間が他アンケートの実施期間と重複しているかどうかのチェックを行います。<BR>
   * 実施期間が他アンケートと重複している場合は期間重複エラーを返します。</li>
   * <li>引数で受け取ったアンケートのvalidationチェックを行います。</li>
   * <li>エラーがなければアンケートマスタの該当データを一件更新します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>更新対象データがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>アンケートマスタの該当データが更新されること。</dd>
   * </dl>
   * </p>
   * 
   * @param enquete
   *          アンケート
   * @return サービス実行結果
   */
  ServiceResult updateEnquete(Enquete enquete);

  /**
   * アンケートの選択肢を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>更新対象データの内容をチェックし、エラーがなければアンケート選択肢を更新します。
   * <ol>
   * <li>引数で受け取ったアンケート選択肢の存在チェックを行います。<BR>
   * データが存在しない場合は該当データ未存在エラーを返します。</li>
   * <li>引数で受け取ったアンケート選択肢のvalidationチェックを行います。</li>
   * <li>エラーがなければアンケート選択肢テーブルの該当データを一件更新します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>更新対象データがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>アンケート選択肢名テーブルの該当データが更新されること。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteChoice
   * @return サービス実行結果
   */
  ServiceResult updateEnqueteChoice(EnqueteChoice enqueteChoice);

  /**
   * アンケートの設問を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>更新対象データの内容をチェックし、エラーがなければアンケート設問を更新します。
   * <ol>
   * <li>引数で受け取ったアンケート設問の存在チェックを行います。<BR>
   * データが存在しない場合は該当データ未存在エラーを返します。</li>
   * <li>引数で受け取ったアンケート設問のvalidationチェックを行います。</li>
   * <li>回答形式を「自由入力」に更新する場合、該当の設問に関連付いている選択肢が存在するかどうかをチェックします。</li>
   * <li>3.の結果がtrueだった場合、関連付いている選択肢をすべて削除します。</li>
   * <li>アンケート設問テーブルの該当データを一件更新します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>更新対象データがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>アンケート設問テーブルの該当データが更新されること。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteQuestion
   *          アンケート設問
   * @return サービス実行結果
   */
  ServiceResult updateEnqueteQuestion(EnqueteQuestion enqueteQuestion);

  /**
   * メールマガジンを更新します。<BR>
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>更新対象データの内容をチェックし、エラーがなければメールマガジンを更新します。
   * <ol>
   * <li>引数で受け取ったメールマガジン情報の存在チェックを行います。<BR>
   * データが存在しない場合は該当データ未存在エラーを返します。</li>
   * <li>引数で受け取ったメールマガジン情報にDatbaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>引数で受け取ったメールマガジン情報のvalidationチェックを行います。</li>
   * <li>エラーがなければメールマガジンマスタの該当データを一件更新します。</li>
   * <li>サービス実行結果を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>更新対象データがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>メールマガジンテーブルの該当データが更新されること。</dd>
   * </dl>
   * </p>
   * 
   * @param mailMagazine
   *          メールマガジン
   * @return サービス実行結果
   */
  ServiceResult updateMailMagazine(MailMagazine mailMagazine);

  /**
   * レビューの表示状態を一括更新し、投稿者のポイント情報を更新します。<BR>
   * <p>
   * <dl>
   * <dt><b>処理概要:</b></dt>
   * <dd>引数で受け取ったレビューのリストを指定された表示状態に更新し、投稿者のポイント情報とポイント履歴情報を更新します。<BR>
   * レビュー投稿者がポイントを獲得するための条件は、レビュー投稿した商品によってポイントを割り当てられたことがなく、<BR>
   * そのレビューが「表示」状態に更新されることです。 <BR>
   * ・表示状態「updateType = 1：表示」の場合、該当レビューの表示状態を「表示」に更新します。<BR>
   * また、該当レビューのポイント割当フラグが「未割当」の場合は、該当顧客にポイントを付与します。<BR>
   * ・表示状態「updateType = 0：非表示」の場合、該当レビューの表示状態を「非表示」に更新します。<BR>
   * <BR>
   * <ol>
   * <li>引数で受け取ったレビューリストのvalidationチェックを行います。</li>
   * <li>引数で受け取ったレビュー投稿情報のリストを一件ずつ取り出して、リストの数だけ5から19までの処理を繰り返します。</li>
   * <li>レビューIDを指定してポイント履歴テーブルからポイント発行状態が「仮発行」のポイント履歴情報を取得します。</li>
   * <li>レビュー投稿情報のレビューポイント割当ステータスが「未割当」かどうかを判定します。</li>
   * <li>4の取得結果を元に、レビュー投稿情報のポイント割当フラグが「未割当」、かつ引数で受け取った表示状態(updateType)が「表示」、<BR>
   * かどうかを判定します。</li>
   * <li>5の判定結果がtrueの場合は、Xで取得したポイント履歴情報の発行ステータスを「仮発行」から「有効」に設定します。<BR>
   * また、判定結果がtrueの場合は12から15の処理は行いません。判定結果がfalseの場合は12の処理に移ります。</li>
   * <li>レビュー投稿情報の顧客コードを元に顧客情報を取得し、取得結果をMapに保持します。</li>
   * <li>7で取得した顧客情報のポイント残高にポイント履歴情報の発行ポイントを加算した値を顧客情報に設定します。</li>
   * <li>7で取得した顧客情報の仮発行ポイントからポイント履歴情報の発行ポイントを減算した値を顧客情報に設定します。</li>
   * <li>7で取得した顧客情報のポイント最終獲得日に現在日時を設定します。</li>
   * <li>レビュー投稿情報のレビューポイント割当ステータスを「割当済」に設定します。</li>
   * <li>5の判定結果がfalseの場合は、引数で受け取った表示状態(updateType)が「非表示」かどうかを判定します。</li>
   * <li>12の判定結果がtrueの場合は、Xで取得したポイント履歴情報の発行ステータスを「仮発行」から「無効」に設定します。</li>
   * <li>レビュー投稿情報の顧客コードを元に顧客情報を取得し、取得結果をMapに保持します。</li>
   * <li>14で取得した顧客情報の仮発行ポイントからポイント履歴情報の発行ポイントを減算した値を顧客情報に設定します。</li>
   * <li>ポイント履歴情報のvalidationチェックを行います。</li>
   * <li>エラーがなければポイント履歴情報を更新します。</li>
   * <li>レビュー投稿情報の商品レビュー表示区分に引数で受け取った表示状態(updateType)をセットし、更新します。</li>
   * <li>レビュー投稿情報のショップコードと商品コードを元に商品ヘッダ情報を取得し、取得結果をMapに設定します。</li>
   * <li>7もしくは14で設定した顧客情報を件数分すべて更新します。</li>
   * <li>19で設定した商品ヘッダ情報を元にレビューの平均点と件数を集計し、レビュー点数集計情報を作成します。</li>
   * <li>21で作成したレビュー点数集計情報をすべて更新します。</li>
   * <li>サービス実行結果を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件:</b></dt>
   * <dd>更新対象データがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件:</b></dt>
   * <dd>レビュー投稿テーブルの該当データが更新されていること。</dd>
   * </dl>
   * </p>
   * 
   * @param reviewIdList
   *          レビュー投稿一覧
   * @param updateType
   *          表示状態
   * @return サービス実行結果
   */
  ServiceResult updateReviewPost(List<ReviewPost> reviewIdList, long updateType);

  /**
   * 顧客が既に答えたアンケートかどうかを判定します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客が既に答えたアンケートかどうかを判定します。</dd>
   * <ol>
   * <li>引数で受け取った顧客コードとアンケートコード(アンケート回答ヘッダの主キー)を指定して <BR>
   * アンケート回答ヘッダのデータを取得します。</li>
   * <li>1で取得したデータが存在しない場合はfalse、存在する場合はtrueを返します。</li>
   * </ol>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCode、enqueteCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   * @param enqueteCode
   * @return 回答済のアンケートであればtrue、未回答であればfalseを返します。
   */
  boolean isAlreadyAnswerEnquete(String customerCode, String enqueteCode);

  /**
   * 顧客が商品のレビューを投稿済みかどうか判定します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取ったデータを元に検索条件を生成し、レビュー投稿テーブルからデータを取得します。<BR>
   * そのデータの有無により投稿済みかどうかを判定します。</dd>
   * <ol>
   * <li>引数で受け取ったショップコード、商品コード、顧客コードを検索条件にセットします。</li>
   * <li>1で生成した検索条件を用いてレビュー投稿テーブルからデータを取得します。</li>
   * <li>2で取得したデータが存在しなければfalse、存在する場合はtrueを返します。</li>
   * </ol>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCode、commodityCode、customerCodeがnullでないこと。</dd>
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
   * @param commodityCode
   * @param customerCode
   * @return 投稿済みであればtrue、未投稿であればfalseを返します。
   */
  boolean isAlreadyPostReview(String shopCode, String commodityCode, String customerCode);

  // 20111219 os013 add start
  /**
   * 顧客が商品のレビューを投稿済みかどうか判定します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取ったデータを元に検索条件を生成し、レビュー投稿テーブルからデータを取得します。<BR>
   * そのデータの有無により投稿済みかどうかを判定します。</dd>
   * <ol>
   * <li>引数で受け取ったショップコード、商品コード、顧客コードを検索条件にセットします。</li>
   * <li>1で生成した検索条件を用いてレビュー投稿テーブルからデータを取得します。</li>
   * <li>2で取得したデータが存在しなければfalse、存在する場合はtrueを返します。</li>
   * </ol>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCode、commodityCode、customerCodeがnullでないこと。</dd>
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
   * @param commodityCode
   * @param customerCode
   * @return 投稿済みであればtrue、未投稿であればfalseを返します。
   */
  boolean isAlreadyPostReview(String shopCode, String commodityCode, String customerCode, String orderNo);

  // 20111219 os013 add end
  /**
   * レビュー平均点、レビュー件数を集計します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>レビュー平均点、レビュー件数を集計するストアドプロシージャを実行します。</dd>
   * <ol>
   * <li>レビュー集計プロシージャを実行します。</li>
   * <li>1の戻り値が1:異常終了の場合は、DBオブジェクト実行エラーをServiceResultに加えます。</li>
   * <li>サービス実行結果を返します。</li>
   * </ol>
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
   * @return サービス実行結果
   */
  ServiceResult generateReviewSummary();

  /**
   * 指定されたアンケートの同一性の判定のために、ハッシュ値を計算します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>与えられたアンケートコードを持つアンケートのハッシュ値を計算し、その結果を返します。
   * <ol>
   * <li>引数で受け取ったアンケートコードに該当するアンケートを取得します。</li>
   * <li>該当のアンケートが存在しない場合は空文字を返し、終了します。</li>
   * <li>該当のアンケートに関連付けられた設問と、その設問に関連付けられた選択肢を取得します。</li>
   * <li>アンケートの更新日時、設問の数、設問の更新日時、選択肢の数、選択肢の更新日時からハッシュ値を計算し、<BR>
   * そのハッシュ値を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>アンケートコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          ハッシュ値計算の対象となるアンケートのアンケートコード
   * @return 計算したハッシュ値
   */
  String calculateEnqueteHash(String enqueteCode);

  // soukai add/update/add ob 2011/12/14 start
  /**
   * 根据优惠券规则编号取得优惠券规则信息
   * 
   * @param couponCode
   *          优惠券规则编号
   * @return 优惠券规则信息
   */
  NewCouponRule getPrivateCoupon(String couponCode);

  // soukai add/update/add ob 2011/12/14 end
  // 2013/04/03 优惠券对应 ob add start
  /**
   * 根据优惠券规则编号取得优惠券规则_发行关联信息
   * 
   * @param couponCode
   *          优惠券规则编号
   * @return 优惠券规则_发行关联信息
   */
  NewCouponRuleLssueInfo getPrivateCouponLssue(String couponCode);

  /**
   * 根据优惠券规则编号删除优惠券规则_发行关联信息
   * 
   * @param couponCode
   *          优惠券规则编号
   */
  ServiceResult deletePrivateCouponLssue(String couponCode, String typeCode, String flg);

  /**
   * 添加新的优惠券规则_发行关联信息
   * 
   * @param 优惠券规则对象
   * @return 结果
   */
  ServiceResult insertNewCouponRuleLssue(NewCouponRuleLssueInfo newCouponRuleLssueInfo, String flg);

  /**
   * 添加新的优惠券规则_使用关联信息
   * 
   * @param 优惠券规则对象
   * @return 结果
   */
  ServiceResult insertNewCouponRuleUse(NewCouponRuleUseInfo newCouponRuleUseInfo);

  /**
   * 根据优惠券规则编号取得优惠券规则_发行关联信息
   * 
   * @param couponCode
   *          优惠券规则编号
   * @param couponCode
   *          商品编号
   * @return 优惠券规则_发行关联信息
   */
  List<NewCouponRuleUseInfo> getPrivateCouponUse(String couponCode, String useCommodityCode, String flg);

  List<NewCouponRuleUseInfo> getMaxCouponUse(String couponCode);

  /**
   * 根据优惠券规则编号取得优惠券规则_发行关联信息
   * 
   * @param couponCode
   *          优惠券规则编号
   * @return 优惠券规则_发行关联信息
   */
  List<NewCouponRuleUseInfo> getPrivateCouponUse(String couponCode, boolean trueOrFalse);

  /**
   * 根据优惠券规则编号删除优惠券规则_使用关联信息
   * 
   * @param couponCode
   *          优惠券规则编号
   * @param useCommodityCode
   *          商品编号
   */
  ServiceResult deletePrivateCouponUse(String couponCode, String useCommodityCode, String flg);

  // 2013/04/03 优惠券对应 ob add end
  // soukai add ob 2011/12/14 start
  /**
   * 添加顾客组别优惠
   * 
   * @param customerGroupCampaign
   *          顾客组别优惠
   * @return 结果
   */
  ServiceResult insertCustomerGroupCampaign(CustomerGroupCampaign customerGroupCampaign);

  /**
   * 添加新的优惠券规则
   * 
   * @param 优惠券规则对象
   * @return 结果
   */
  ServiceResult insertNewCouponRule(NewCouponRule newCouponRule);

  /**
   * 根据查询条件取得优惠券规则信息
   * 
   * @param condition
   *          查询条件
   * @param flag
   *          false:公共优惠劵查询 true:顾客别优惠劵查询
   * @return 优惠券规则信息
   */
  SearchResult<NewCouponRule> searchNewCouponRuleList(PrivateCouponListSearchCondition condition, boolean flag);

  
  SearchResult<OptionalCampaign> searchOptionalCampaignList(OptionalCampaignListSearchCondition condition);
  
  /**
   * 根据查询条件取得优惠券规则信息(分析用)
   * 
   * @param condition
   *          查询条件
   * @param flag
   *          false:公共优惠劵查询 true:顾客别优惠劵查询
   * @return 优惠券规则信息
   */
  SearchResult<PrivateCouponListSummary> searchNewCouponRuleList_analysis(PrivateCouponListSearchCondition condition, boolean flag);
  
  SearchResult<GiftCardDetailListSummary> searchGiftCardList_analysis(GiftCardDetailListSearchCondition condition, boolean flag);
  
  SearchResult<GiftCardReturnApply> searchGiftCardReturnList(GiftCardReturnListSearchCondition condition, boolean flag);

  /**
   * 根据查询条件取得顾客组别优惠信息
   * 
   * @param condition
   *          查询条件
   * @return 顾客组别优惠信息
   */
  SearchResult<CustomerGroupCampaignHeadLine> getCustomerGroupCampaignList(CustomerGroupCampaignSearchCondition condition);

  // soukai add ob 2011/12/14 end

  // soukai add ob 2011/12/15 start
  /**
   * 根据顾客组别编号删除顾客组别优惠信息
   * 
   * @param couponCode
   *          顾客组别编号
   * @return 删除结果
   */
  ServiceResult deleteNewCouponRule(String couponCode);

  /**
   * 更新优惠券规则
   * 
   * @param 优惠券规则对象
   * @return 更新结果
   */
  ServiceResult updateNewCouponRule(NewCouponRule newCouponRule);

  /**
   * 根据优惠活动编号获得优惠活动信息
   * 
   * @param campaignCode
   *          优惠活动编号
   * @return 优惠活动信息
   */
  CustomerGroupCampaign getCustomerGroupCampaign(String campaignCode);

  /**
   * 更新顾客组别优惠
   * 
   * @param 顾客组别优惠
   * @return 更新结果
   */
  ServiceResult updateCustomerGroupCampaign(CustomerGroupCampaign customerGroupCampaign);

  /**
   * 根据优惠活动编号删除优惠活动信息
   * 
   * @param campaignCode
   *          优惠活动编号
   * @return 删除结果
   */
  ServiceResult deleteCustomerGroupCampaign(String campaignCode);

  /**
   * 根据查询条件取得企划信息
   * 
   * @param condition
   *          查询条件
   * @return 企划信息
   */
  SearchResult<Plan> getPlanList(PlanSearchCondition condition);

  // 2012/11/16 促销对应 ob add start
  /**
   * 根据查询条件取得活動信息
   * 
   * @param condition
   *          查询条件
   * @return 活動信息
   */
  SearchResult<CampaignMain> getCampaignList(CampaignSearchCondition condition);

  /**
   * 根据活动编号删除活动信息
   * 
   * @param campaignCode
   *          活动编号
   * @return 删除结果
   */
  ServiceResult deleteCampaign(String campaignCode);

  // 2012/11/16 促销对应 ob add end

  /**
   * 根据企划编号删除企划信息
   * 
   * @param planCode
   *          企划编号
   * @return 删除结果
   */
  ServiceResult deletePlan(String planCode);

  /**
   * 根据企划编号获得企划信息
   * 
   * @param planCode
   *          企划编号
   * @return 企划信息
   */
  Plan getPlan(String planCode);

  /**
   * 插入企划信息
   * 
   * @param plan
   *          企划编号
   * @return 结果
   */
  ServiceResult insertPlan(Plan plan);

  /**
   * 更新企划信息
   * 
   * @param plan
   *          企划编号
   * @return 结果
   */
  ServiceResult updatePlan(Plan plan);

  /**
   * 根据查询条件取得顾客组别优惠活动信息
   * 
   * @param condition
   *          查询条件
   * @return 顾客组别优惠活动信息
   */
  SearchResult<CustomerGroupCampaignSummaryViewList> getCustomerGroupCampaignSummaryViewList(
      CustomerGroupCampaignSummaryViewSearchCondition condition);

  // soukai add ob 2011/12/15 end

  // soukai add ob 2011/12/21 start
  /**
   * 判断是否存在已经登录过的发行期间
   * 
   * @param newCouponRule
   *          顾客别优惠券规则信息
   * @return true：存在 false：不存在
   */
  boolean checkNewCouponRuleDuplicatedRegister(NewCouponRule newCouponRule);

  /**
   * 根据企划编号获得企划明细信息
   * 
   * @param planCode
   *          企划编号
   * @return 企划明细信息列表
   */
  List<PlanDetailHeadLine> getPlanDetailByPlanCode(String planCode);

  /**
   * 获得企划明细信息
   * 
   * @param planCode
   *          企划编号
   * @param detailType
   *          企划明细类别
   * @param detailCode
   *          企划明细编号
   * @return 企划明细信息
   */
  PlanDetail getPlanDetail(String planCode, String detailType, String detailCode);

  List<PlanRelatedHeadLine> getRelatedHeadLine(String planCode, String detailType, String detailCode);

  // 2013/04/03 优惠券对应 ob add start
  List<PlanRelatedHeadLine> getRelatedHeadLineList(String couponCode);

  List<PlanRelatedHeadLine> getRelatedHeadLineListUse(String couponCode);

  // 2013/04/03 优惠券对应 ob add end
  ServiceResult insertPlanDetail(PlanDetail planDetail);

  ServiceResult updatePlanDetail(PlanDetail planDetail);

  ServiceResult insertPlanCommodity(PlanCommodity planCommodity);

  ServiceResult deletePlanCommodity(String planCode, String detailType, String detailCode, String commodityCode);

  ServiceResult deletePlanDetail(String planCode, String detailType, String detailCode);

  /**
   * 根据规则编号和类型得到订单信息
   * 
   * @param discountCode
   *          规则编号
   * @param type
   *          发行类型
   * @return true: 存在 false:不存在
   */
  ServiceResult deleteNewPublicCouponRule(String discountCode, String type);

  /**
   * 根据企划类型获得当前期间内的企划
   * 
   * @param discountCode
   *          规则编号
   * @param type
   *          发行类型
   * @return true: 存在 false:不存在
   */
  Plan getPlan(String planType, String detailType);

  // soukai add ob 2011/12/21 end

  // 20120113 shen add start
  NewCouponRule getNewCouponRule(String couponType);

  // 20120113 shen add end
  List<CustomerGroupCampaignHeadLine> getCustomerGroupCampaignList();

  // 20120716 shen add start
  List<NewCouponRule> getPublicCouponList();

  // 20120716 shen add end

  // 2012/11/20 促销活动 ob add start
  CampaignInfo getCampaignInfo(String campaignCode);

  // 2012/11/20 促销活动 ob add end
  // 2012-11-21 促销对应 ob add start
  // 取得正在促销类型为折扣,活动正在进行中且内容中包含指定商品的所有促销条件信息
  List<CampaignCondition> getSaleOffCampaignByCommodityCode(String commodityCode);

  // 判断商品是否为促销活动的关联商品
  boolean isCampaignExistCommodit(String shopCode, String commodityCode, String campaignCode);

  // 根据特定商品的赠品查询促销活动信息
  CampaignMain getGiftCampaignMainByGiftCode(String giftCode);

  // 取得多重优惠活动
  List<CampaignInfo> getMultipleGiftCampaignInfo(Date date, BigDecimal orderAmount, String advertCode);

  // 取得当前适用的折扣券促销活动信息
  List<CampaignInfo> getCouponCampaignInfo(Date date);

  /**
   * 根据折扣券编号取得促销条件信息
   * 
   * @param couponCode
   *          折扣券编号
   * @return 促销条件信息List
   */
  List<CampaignCondition> getCampaignConditionByCouponCode(String couponCode);

  // 2012-11-21 促销对应 ob add end

  /**
   * 增加促销活动
   * 
   * @param campagin
   *          促销信息
   */
  // 20121118 促销对应 ob add start
  ServiceResult saveCampagin(CampaignLine campaignLine, boolean conditionFlg, boolean doingsFlg);

  CampaignMain loadCampaignMain(String campaignCode);

  // 取得活动信息
  CampaignLine loadCampaignLine(String campaignCode);

  // 查询关联商品
  CampaignCondition searchRelatedCommodity(String campaignCode);

  // 查询赠品
  CampaignDoings searchGiftCommodity(String commodityCode);

  // 区域
  List<Prefecture> loadAll();

  // 更新促销信息
  ServiceResult updateCampaignLine(CampaignLine campaignLine, boolean flg, boolean conditionFlg, boolean doingsFlg);

  ServiceResult updateCampaignCondition(CampaignLine campaignLine, boolean flag);

  // 同一赠品不能同时适用于多个特定商品的促销活动
  List<GiftCampaign> getAllGiftCampaign(String campaignCode, Date startDateTime, Date endDateTime);

  // 20121118 促销对应 ob add end

  ServiceResult updateCampaignDoings(CampaignLine campaignLine);

  // 更新按钮调用
  ServiceResult modifyCampaignLine(CampaignLine campaignLine, boolean conditionFlg, boolean doingsFlg, boolean flg,
      boolean resultflg, boolean comdityFlg, boolean areaFlg);

  // 20121118 促销对应 ob add end
  // 2012/11/20 促销活动 ob add start
  List<CampaignDoings> getCampaignDoings(String campainCode);

  List<CampaignInfo> getCampaignGiftInfoList(Date orderDate, String commodityCode);

  boolean getEnableCampaignFlg(String commodityCode, Date orderDate);

  // 2012/11/20 促销活动 ob add end

  // 2013/04/01 优惠券对应 ob add start
  /**
   * 検索情報によって、クーポンルール情報を取得する
   * 
   * @param condition
   *          検索情報
   * @return クーポンルール情報
   */
  SearchResult<NewCouponHistoryInfo> getCustomerNewCouponList(MyCouponHistorySearchCondition condition);

  // 有効のポイントを取得
  Long getFriendCouponUseHistoryAllPoint(String customerCode);

  // 获取朋友优惠劵使用成绩
  SearchResult<FriendCouponLine> getFriendCouponLine(FriendCouponSearchCondition condition);

  // 根据优惠劵编码查询数据
  NewCouponRule getNewCouponRuleByCouponCode(String couponCode);

  // 根据优惠劵编码查询优惠劵使用履历数据
  FriendCouponUseHistory getFriendCouponUseHistory(String couponCode);

  // 履歴を登録
  ServiceResult insertFriendHistory(NewCouponHistory newCouponDto, FriendCouponExchangeHistory FceDto);

  /**
   * 配送クーポン履歴を登録
   * 
   * @param newCouponDto
   *          配送履歴情報
   * @return サービス実行結果
   */
  ServiceResult insertNewCouponHistory(NewCouponHistory newCouponDto);

  /**
   * 优惠券信息取得
   * 
   * @param customerCode
   *          顾客编号
   * @return 优惠券信息リスト
   */
  List<MyCouponInfo> getMyCoupon(String customerCode);

  MyCouponInfo getMyCoupon(String customerCode, String couponIssueNo);

  /**
   * 使用关联信息(商品编号)リスト取得
   * 
   * @param couponCode
   *          优惠劵编号
   * @return 商品编号リスト
   */
  List<NewCouponHistoryUseInfo> getUseCommodityList(String couponIssueCode);

  /**
   * 使用关联信息(品牌编号)リスト取得
   * 
   * @param couponCode
   *          优惠劵编号
   * @return 商品区分品リスト
   */
  List<NewCouponHistoryUseInfo> getBrandCodeList(String couponIssueCode);

  /**
   * 使用关联信息(商品区分品)リスト取得
   * 
   * @param couponCode
   *          优惠劵编号
   * @return 商品区分品リスト
   */
  List<NewCouponHistoryUseInfo> getImportCommodityTypeList(String couponIssueCode);

  String getBrandCode(String commodityCode);

  /**
   * 指定优惠券是否为使用者本人发行
   * 
   * @param couponCode
   *          优惠劵编号
   * @param customerCode
   *          顾客编号
   * @return true:本人发行 false：非本人发行
   */
  boolean issuedBySelf(String couponCode, String customerCode);

  // 取得优惠劵编号和优惠劵明细编号
  void getCouponIssueNo(NewCouponHistory newCouponDto);

  // 2013/04/01 优惠券对应 ob add end

  // 2013/4/1 优惠券对应 ob add start
  // 通过顾客编号取得发货信息
  List<ShippingHeader> getShippingHeaderList(String customerCode);

  // 取得朋友介绍优惠券使用规则信息
  List<FriendCouponRule> getFriendCouponRuleList();

  FriendCouponRule getFriendCouponRule(String friendCouponRuleNo);

  FriendCouponUse getFriendCouponUse(String couponCode);

  // List<FriendCouponList> getFriendCouponList(String couponCode);

  List<FriendCouponRule> getUnissuedFriendCouponList(String customerCode);

  List<NewCouponRule> getIssuedFriendCouponList(String customerCode);

  // 发行优惠券
  ServiceResult insertCouponFriend(FriendCouponIssueHistory historyDto, NewCouponRule couponDto);

  // 2013/4/1 优惠券对应 ob add end
  // 2013/4/12 优惠券对应 ob add start
  /**
   * 优惠券数据更新
   * 
   * @param newCouponHistory
   *          优惠券发行履历
   * @return
   */
  ServiceResult updateNewCouponHistory(NewCouponHistory newCouponHistory);

  List<FriendCouponUseLine> getFriendCouponUseLine();

  /**
   * 誕生日は今月の会員情報取得
   * 
   * @return Customerリスト
   */
  List<Customer> getCustomerListByBirthday();

  /**
   * 誕生日クーポン情報取得
   * 
   * @return　クーピン情報
   */
  NewCouponRule getNewCouponRule();

  // 2013/4/12 优惠券对应 ob add end

  // 2013/4/19 朋友推荐优惠券 zhangzhengtao add start
  /**
   * 删除朋友推荐优惠券信息
   * 
   * @param id
   * @return
   */
  ServiceResult deleteFriendCouponRule(String id);

  /**
   * 查询一条朋友推荐优惠券信息
   * 
   * @param id
   * @return
   */
  FriendCouponRule selectFriendCouponRule(String id);

  /**
   * 修改朋友推荐优惠券信息
   * 
   * @param rule
   * @return
   */
  ServiceResult updateFriendCouponRule(FriendCouponRule rule);

  /**
   * 新增朋友推荐优惠券信息
   * 
   * @param rule
   * @return
   */
  ServiceResult addFriendCouponRule(FriendCouponRule rule);

  /**
   * 得到所有朋友推荐优惠券信息
   * 
   * @return
   */
  SearchResult<FriendCouponRule> selectAllFriendCouponRule(FriendCouponRuleCondition condition);

  // 2013/4/19 朋友推荐优惠券 zhangzhengtao add end

  // 20130724 txw add start
  List<PlanDetail> getPlanDetailList(String planCode);

  List<PlanCommodity> getPlanCommodityList(String planCode);

  SearchResult<DiscountHeadLine> getDiscountList(DiscountListSearchCondition condition);

  SearchResult<MessageHeadLine> getMessageList(CustomerMessageSearchCondition condition);
  
  ServiceResult deleteDiscount(String discountCode);
  
  ServiceResult deleteMessage(long ormRowid);
  
  DiscountInfo getDiscountInfo(String discountCode);

  DiscountHeader getDiscountHeader(String discountCode);

  ServiceResult insertDiscountHeader(DiscountHeader discountHeader);

  ServiceResult insertDiscountCommodity(DiscountCommodity discountCommodity);

  ServiceResult updateDiscountHeader(DiscountHeader discountHeader);

  DiscountCommodity getDiscountCommodity(String discountCode, String commodityCode);

  ServiceResult updateDiscountCommodity(DiscountCommodity discountCommodity);

  ServiceResult deleteDiscountCommodity(String discountCode, String commodityCode);

  ServiceResult updatePlanCommodity(List<PlanCommodity> list);

  List<Plan> getPlanAllList();

  // 20130724 txw add end
  // 20131009 txw add start
  /**
   * 根据优惠券规则编号取得优惠券规则_发行关联信息
   * 
   * @param couponCode
   *          优惠券规则编号
   * @return 优惠券规则_发行关联信息
   */
  List<NewCouponRuleLssueInfo> getPrivateCouponLssue(String couponCode, boolean isBrand);

  List<NewCouponRuleLssueInfo> getMaxCouponLssue(String couponCode);

  /**
   * 使用关联信息(品牌编号)リスト取得
   * 
   * @param couponCode
   *          优惠劵编号
   * @return 商品区分品リスト
   */
  List<NewCouponHistoryUseInfo> getCategoryCodeList(String couponIssueCode);

  // 20131009 txw add end
  // 20131011 txw add start
  SearchResult<FreePostageRule> getFreePostageList(FreePostageListSearchCondition condition);

  FreePostageRule getFreePostageRule(String freePostageCode);

  ServiceResult deleteFreePostage(String freePostageCode);

  ServiceResult insertFreePostageRule(FreePostageRule freePostageRule);

  ServiceResult updateFreePostageRule(FreePostageRule freePostageRule);

  // 20131011 txw add end

  ServiceResult insertGiftCardRule(GiftCardRule giftCardRule);
  // 201310129 wz add end
  GiftCardRule selectCardCode(String id);
  
  //根据卡编号获取卡信息gift_card_rule
  GiftCardRule getGiftCardRule(String cardCode);
  
  //更新修改卡信息gift_card_rule
  ServiceResult updateGiftCardRule(GiftCardRule giftCardRule);
 
  //根据礼品卡Id查询匹配gift_card_rule
  SearchResult<GiftCardRule> searchGiftCardRuleList(GiftCardRuleListSearchCondition search);
  
  //批量发行礼品卡
  ServiceResult insertGiftCardRuleIssueAction(GiftCardIssueHistory history);
  
  //查询获取了多少条信息，检索用 gift_card_issue_history
  Long getGiftCardIssueHistory(String code);
  
  Long getCouponHistoryCount(String customerCode);
  
  Long getGiftCardCount(String customerCode);
  
  //根据卡编号获取卡信息gift_card_issue_history
  List<GiftCardIssueHistory> getdetail(String cardCode);
  
  //获取卡密码gift_card_issue_detail
  List<String> getPwd();
  
  //根据卡编号或批次查找礼品卡是否已激活
  List<String> getGiftCardIssueDetailCardStatus(String cardCode,String string);
  
  //有条件批量取消
  public ServiceResult updateGiftCardIssueDetailStatus(String cardCode, String cardHistoryNo);
      
  //根据卡ID与批次获得卡信息gift_card_issue_history
  GiftCardIssueHistory getGiftCardIssueHis(String cardCode,String string);
 
  //根据卡ID与批次批量取消gift_card_issue_detail
  ServiceResult updateGiftCardIssueDetail(String cardCode,String cardHistoryNo);
  //根据礼品卡Id查询匹配gift_card_issue_detail
  GiftCardIssueDetail searchGiftCardIssueDetailEdit(String  cardId,String cardCode);
 
  //根据礼品卡批次批量修改礼品卡状态gift_card_issue_history
  ServiceResult updateHistory(GiftCardIssueHistory hist);
  
  //修改礼品卡状态gift_card_issue_detail
  ServiceResult updateDetail(GiftCardIssueDetail detail);
  
  //根据卡ID获得卡信息gift_card_issue_detail
  GiftCardIssueDetail getGiftCardIssueDetail(String cardId);

  ServiceResult insertGiftCardReturnConfirm(GiftCardReturnApply gcra,String confirmAmount);
  
  // 20131106 txw add start
  ServiceResult insertGiftCardReturnApply(GiftCardReturnApply gcra);

  List<CustomerCardInfo> getCustomerCardInfoList(String orderId);

  List<CustomerCardUseInfo> getCustomerCardUseInfoList(String cardId);

  ServiceResult deleteCustomerCardInfo(String cardId);
  // 20131106 txw add end
  // 20140303 txw add start
  SearchResult<PropagandaActivityRule> getPropagandaActivityRuleList(PropagandaActivityRuleListSearchCondition condition);
  
  PropagandaActivityRule getPropagandaActivityRule(String activityCode);
  
  ServiceResult deletePropagandaActivityRule(String activityCode);
  
  ServiceResult insertPropagandaActivityRule(PropagandaActivityRule propagandaActivityRule);
  
  List<PropagandaActivityCommodityInfo> getPropagandaActivityCommodityList(String activityCode);
  
  ServiceResult updatePropagandaActivityRule(PropagandaActivityRule propagandaActivityRule);
  
  ServiceResult insertPropagandaActivityCommodity(PropagandaActivityCommodity propagandaActivityCommodity);
  
  PropagandaActivityCommodity getPropagandaActivityCommodity(String activityCode, String commodityCode);
  
  ServiceResult deletePropagandaActivityCommodity(String activityCode, String commodityCode);
  
  boolean existCommonActivityDate(Long orderType, Long languageCode, Date startTime, Date endTime, String activityCode);
  // 20140303 txw add end
  //发行规则是否已被使用
  boolean existFriendCouponIssueHistory(String friendCouponRuleNo);
  
  // 朋友优惠券发行履历
  List<FriendCouponIssueHistory> getIssueHistoryByCustomerCode(String customerCode);
  
  //新规任意活动
  ServiceResult insertOptionalCampaign(OptionalCampaign obj);
  
  
  OptionalCampaign loadOptionalCampaign(String shopCode,String campaignCode);
  
  ServiceResult upadteOptionalCampaign(OptionalCampaign obj);
  
  List<OptionalCampaign> getOptionalCampaignbyDate(Date starTime,Date endTime);
  
  ServiceResult deleteOptionalCampaign(String shopCode,String campaignCode);
  
  List<DiscountHeader> getActiveDiscountHeaderList();
  
  //获取有效期内的限时限量折扣活动
  List<DiscountHeader> getCrossDiscountHeaderList(Date starTime,Date endTime);
  
  //获取指定折扣编号的商品信息列表
  List<DiscountCommodity> getDiscountCommodityListByCouponCode(String couponCode);
  
  List<OrderReview> getOrderReviewListByCustomerCodeAndOrderNo(String customerCode,String orderNo);
  
  //可用优惠券数量
  Long getAvaliableCouponCount(String customerCode);
  
  //获取 仓库休息日的集合
  List<StockHoliday> getStockHolidayList();
  //新加 仓库休息日
  ServiceResult insertStockHoliday(StockHoliday obj);
  //删除仓库休息日
   ServiceResult deleteStockHoliday(Date shday);
   
   //朋友优惠券是否已发行
   boolean existsFriendCouponIssueHistory(String friendCouponRuleNo, String customerCode);
  
}
