package jp.co.sint.webshop.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.DuplicatedInsertException;
import jp.co.sint.webshop.data.PointInsertProcedure;
import jp.co.sint.webshop.data.PointUpdateProcedure;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.ReviewSummaryUpdateProcedure;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.StoredProceduedResultType;
import jp.co.sint.webshop.data.StoredProcedureResult;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.dao.CampaignConditionDao;
import jp.co.sint.webshop.data.dao.CampaignDao;
import jp.co.sint.webshop.data.dao.CampaignDoingsDao;
import jp.co.sint.webshop.data.dao.CampaignMainDao;
import jp.co.sint.webshop.data.dao.CustomerCardUseInfoDao;
import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dao.CustomerGroupCampaignDao;
import jp.co.sint.webshop.data.dao.CustomerMessageDao;
import jp.co.sint.webshop.data.dao.DiscountCommodityDao;
import jp.co.sint.webshop.data.dao.DiscountHeaderDao;
import jp.co.sint.webshop.data.dao.EnqueteAnswerHeaderDao;
import jp.co.sint.webshop.data.dao.EnqueteChoiceDao;
import jp.co.sint.webshop.data.dao.EnqueteDao;
import jp.co.sint.webshop.data.dao.EnqueteQuestionDao;
import jp.co.sint.webshop.data.dao.EnqueteReplyChoicesDao;
import jp.co.sint.webshop.data.dao.EnqueteReplyInputDao;
import jp.co.sint.webshop.data.dao.FreePostageRuleDao;
import jp.co.sint.webshop.data.dao.FriendCouponExchangeHistoryDao;
import jp.co.sint.webshop.data.dao.FriendCouponIssueHistoryDao;
import jp.co.sint.webshop.data.dao.FriendCouponRuleDao;
import jp.co.sint.webshop.data.dao.FriendCouponUseHistoryDao;
import jp.co.sint.webshop.data.dao.GiftCardIssueDetailDao;
import jp.co.sint.webshop.data.dao.GiftCardIssueHistoryDao;
import jp.co.sint.webshop.data.dao.GiftCardRuleDao;
import jp.co.sint.webshop.data.dao.MailMagazineDao;
import jp.co.sint.webshop.data.dao.MailMagazineSubscriberDao;
import jp.co.sint.webshop.data.dao.NewCouponHistoryDao;
import jp.co.sint.webshop.data.dao.NewCouponRuleDao;
import jp.co.sint.webshop.data.dao.NewCouponRuleLssueInfoDao;
import jp.co.sint.webshop.data.dao.NewCouponRuleUseInfoDao;
import jp.co.sint.webshop.data.dao.OptionalCampaignDao;
import jp.co.sint.webshop.data.dao.OrderDetailDao;
import jp.co.sint.webshop.data.dao.PlanCommodityDao;
import jp.co.sint.webshop.data.dao.PlanDao;
import jp.co.sint.webshop.data.dao.PlanDetailDao;
import jp.co.sint.webshop.data.dao.PrefectureDao;
import jp.co.sint.webshop.data.dao.PropagandaActivityCommodityDao;
import jp.co.sint.webshop.data.dao.PropagandaActivityRuleDao;
import jp.co.sint.webshop.data.dao.ReviewPostDao;
import jp.co.sint.webshop.data.dao.ReviewSummaryDao;
import jp.co.sint.webshop.data.dao.ShippingHeaderDao;
import jp.co.sint.webshop.data.dao.StockHolidayDao;
import jp.co.sint.webshop.data.domain.CampaignConditionType;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CancelFlg;
import jp.co.sint.webshop.data.domain.CouponStatus;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.EnqueteQuestionType;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.PointIssueStatus;
import jp.co.sint.webshop.data.domain.PointIssueType;
import jp.co.sint.webshop.data.domain.RelatedCommodityFlg;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.ReviewDisplayType;
import jp.co.sint.webshop.data.domain.ReviewPointAllocatedStatus;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerCardInfo;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.CustomerMessage;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.data.dto.EnqueteAnswerHeader;
import jp.co.sint.webshop.data.dto.EnqueteChoice;
import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.data.dto.EnqueteReplyChoices;
import jp.co.sint.webshop.data.dto.EnqueteReplyInput;
import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.data.dto.FriendCouponExchangeHistory;
import jp.co.sint.webshop.data.dto.FriendCouponIssueHistory;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.data.dto.FriendCouponUseHistory;
import jp.co.sint.webshop.data.dto.GiftCardIssueDetail;
import jp.co.sint.webshop.data.dto.GiftCardIssueHistory;
import jp.co.sint.webshop.data.dto.GiftCardReturnApply;
import jp.co.sint.webshop.data.dto.GiftCardReturnConfirm;
import jp.co.sint.webshop.data.dto.GiftCardRule;
import jp.co.sint.webshop.data.dto.MailMagazine;
import jp.co.sint.webshop.data.dto.MailMagazineSubscriber;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.NewCouponHistoryUseInfo;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.NewCouponRuleLssueInfo;
import jp.co.sint.webshop.data.dto.NewCouponRuleUseInfo;
import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.data.dto.PlanCommodity;
import jp.co.sint.webshop.data.dto.PlanDetail;
import jp.co.sint.webshop.data.dto.PointHistory;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.data.dto.Prefecture;
import jp.co.sint.webshop.data.dto.PropagandaActivityCommodity;
import jp.co.sint.webshop.data.dto.PropagandaActivityRule;
import jp.co.sint.webshop.data.dto.ReviewPost;
import jp.co.sint.webshop.data.dto.ReviewSummary;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.StockHoliday;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.FriendCouponLine;
import jp.co.sint.webshop.service.FriendCouponQuery;
import jp.co.sint.webshop.service.FriendCouponSearchCondition;
import jp.co.sint.webshop.service.FriendCouponUseLine;
import jp.co.sint.webshop.service.MyCouponInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.analysis.CustomerGroupCampaignSummaryViewList;
import jp.co.sint.webshop.service.analysis.CustomerGroupCampaignSummaryViewSearchCondition;
import jp.co.sint.webshop.service.analysis.CustomerGroupCampaignSummaryViewSearchQuery;
import jp.co.sint.webshop.service.analysis.GiftCardDetailListSummary;
import jp.co.sint.webshop.service.analysis.GiftCardDetailSearchQuery;
import jp.co.sint.webshop.service.analysis.GiftCardReturnSearchQuery;
import jp.co.sint.webshop.service.analysis.PrivateCouponListSummary;
import jp.co.sint.webshop.service.analysis.PrivateCouponSearchQuery;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.campain.CampainQuery;
import jp.co.sint.webshop.service.communication.CampaignHeadLine;
import jp.co.sint.webshop.service.communication.CampaignLine;
import jp.co.sint.webshop.service.communication.CampaignListSearchCondition;
import jp.co.sint.webshop.service.communication.CampaignListSearchQuery;
import jp.co.sint.webshop.service.communication.CampaignQuery;
import jp.co.sint.webshop.service.communication.CampaignResearch;
import jp.co.sint.webshop.service.communication.CampaignSearchCondition;
import jp.co.sint.webshop.service.communication.CampaignSearchQuery;
import jp.co.sint.webshop.service.communication.CommunicationServiceQuery;
import jp.co.sint.webshop.service.communication.CustomerGroupCampaignHeadLine;
import jp.co.sint.webshop.service.communication.CustomerGroupCampaignSearchCondition;
import jp.co.sint.webshop.service.communication.CustomerGroupCampaignSearchQuery;
import jp.co.sint.webshop.service.communication.CustomerMessageSearchCondition;
import jp.co.sint.webshop.service.communication.CustomerMessageSearchQuery;
import jp.co.sint.webshop.service.communication.DiscountHeadLine;
import jp.co.sint.webshop.service.communication.DiscountInfo;
import jp.co.sint.webshop.service.communication.DiscountListSearchCondition;
import jp.co.sint.webshop.service.communication.DiscountListSearchQuery;
import jp.co.sint.webshop.service.communication.EnqueteAnswer;
import jp.co.sint.webshop.service.communication.EnqueteDeleteQuery;
import jp.co.sint.webshop.service.communication.EnqueteList;
import jp.co.sint.webshop.service.communication.EnqueteListSearchCondition;
import jp.co.sint.webshop.service.communication.EnqueteQuery;
import jp.co.sint.webshop.service.communication.EnqueteSearchQuery;
import jp.co.sint.webshop.service.communication.FreePostageListSearchCondition;
import jp.co.sint.webshop.service.communication.FreePostageListSearchQuery;
import jp.co.sint.webshop.service.communication.FriendCouponListQuery;
import jp.co.sint.webshop.service.communication.FriendCouponRuleCondition;
import jp.co.sint.webshop.service.communication.FriendCouponUse;
import jp.co.sint.webshop.service.communication.GiftCampaign;
import jp.co.sint.webshop.service.communication.GiftCardDetailListSearchCondition;
import jp.co.sint.webshop.service.communication.GiftCardReturnListSearchCondition;
import jp.co.sint.webshop.service.communication.GiftCardRuleListSearchCondition;
import jp.co.sint.webshop.service.communication.GiftCardRuleListSearchQuery;
import jp.co.sint.webshop.service.communication.MailMagazineHeadLine;
import jp.co.sint.webshop.service.communication.MailMagazineQuery;
import jp.co.sint.webshop.service.communication.MessageHeadLine;
import jp.co.sint.webshop.service.communication.MyCouponHistorySearchCondition;
import jp.co.sint.webshop.service.communication.MyCouponHistorySearchQuery;
import jp.co.sint.webshop.service.communication.NewCampaignQuery;
import jp.co.sint.webshop.service.communication.NewCouponHistoryInfo;
import jp.co.sint.webshop.service.communication.OptionalCampaignListSearchCondition;
import jp.co.sint.webshop.service.communication.OptionalCampaignListSearchQuery;
import jp.co.sint.webshop.service.communication.OrderReview;
import jp.co.sint.webshop.service.communication.PlanDetailHeadLine;
import jp.co.sint.webshop.service.communication.PlanRelatedHeadLine;
import jp.co.sint.webshop.service.communication.PlanSearchCondition;
import jp.co.sint.webshop.service.communication.PlanSearchQuery;
import jp.co.sint.webshop.service.communication.PrivateCouponListSearchCondition;
import jp.co.sint.webshop.service.communication.PrivateCouponListSearchQuery;
import jp.co.sint.webshop.service.communication.PropagandaActivityCommodityInfo;
import jp.co.sint.webshop.service.communication.PropagandaActivityRuleListSearchCondition;
import jp.co.sint.webshop.service.communication.PropagandaActivityRuleListSearchQuery;
import jp.co.sint.webshop.service.communication.ReviewList;
import jp.co.sint.webshop.service.communication.ReviewListSearchCondition;
import jp.co.sint.webshop.service.communication.ReviewPostAndCustHeadLine;
import jp.co.sint.webshop.service.communication.ReviewPostCountSearchCondition;
import jp.co.sint.webshop.service.communication.ReviewPostCountSearchQuery;
import jp.co.sint.webshop.service.communication.ReviewPostCustomerCountSearchCondition;
import jp.co.sint.webshop.service.communication.ReviewPostCustomerCountSearchQuery;
import jp.co.sint.webshop.service.communication.ReviewSearchQuery;
import jp.co.sint.webshop.service.communication.ReviewSummaryProcedure;
import jp.co.sint.webshop.service.communication.DiscountInfo.DiscountDetail;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.FriendCouponRuleQuery;
import jp.co.sint.webshop.service.customer.PointHistoryQuery;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderHeadline;
import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CommunicationServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.service.result.SiteManagementServiceErrorContent;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.RandomUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.validation.ValidatorUtil;

import org.apache.log4j.Logger;

public class CommunicationServiceImpl extends AbstractServiceImpl implements CommunicationService {

  private static final long serialVersionUID = 1L;

  public ServiceResult deleteCampaign(String shopCode, String campaignCode) {

    Logger logger = Logger.getLogger(this.getClass());

    CampaignDao dao = DIContainer.getDao(CampaignDao.class);
    ServiceResultImpl result = new ServiceResultImpl();

    // 削除データ存在チェック
    if (dao.exists(shopCode, campaignCode)) {
      logger.debug("shop=" + shopCode + ", campaign=" + campaignCode + ": was found.");

      // 受注明細テーブルから参照されている場合は削除不可
      OrderDetailDao orderDao = DIContainer.getDao(OrderDetailDao.class);

      List<OrderDetail> orders = orderDao.findByQuery(CommunicationServiceQuery.LOAD_CAMPAIGN_ORDER, shopCode, campaignCode);

      if (!orders.isEmpty()) {
        result.addServiceError(CommunicationServiceErrorContent.DELETE_CAMPAIGN_ERROR);
        return result;
      }

      TransactionManager txm = DIContainer.getTransactionManager();
      try {
        txm.begin(this.getLoginInfo());

        txm.executeUpdate(new SimpleQuery(CommunicationServiceQuery.DELETE_CAMPAIGN_COMMODITY, shopCode, campaignCode));
        txm.executeUpdate(new SimpleQuery(CommunicationServiceQuery.DELETE_CAMPAIGN, shopCode, campaignCode));

        txm.commit();
        logger.debug("success.");
      } catch (RuntimeException e) {
        txm.rollback();
      } finally {
        txm.dispose();
      }

    } else {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      logger.debug("shop=" + shopCode + ", campaign=" + campaignCode + ": was NOT found.");
    }

    return result;
  }

  public ServiceResult deleteEnquete(String enqueteCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    // 存在チェック
    EnqueteDao enqueteDao = DIContainer.getDao(EnqueteDao.class);
    if (!enqueteDao.exists(enqueteCode)) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try { // enqueteCodeに関連付いているデータを削除する
      txMgr.begin(this.getLoginInfo());
      for (String query : EnqueteDeleteQuery.getDeleteQuery()) {
        txMgr.executeUpdate(query, enqueteCode);
      }
      txMgr.commit();
      logger.debug("succeed");
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.debug("failed");
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public EnqueteChoice getEnqueteChoice(String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo) {
    EnqueteChoiceDao choiceDao = DIContainer.getDao(EnqueteChoiceDao.class);
    return choiceDao.load(enqueteCode, enqueteQuestionNo, enqueteChoicesNo);
  }

  public ServiceResult deleteEnqueteChoice(String enqueteCode, Long questionNo, Long choiceNo) {

    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    EnqueteChoiceDao choiceDao = DIContainer.getDao(EnqueteChoiceDao.class);
    EnqueteReplyChoicesDao replyChoicesDao = DIContainer.getDao(EnqueteReplyChoicesDao.class);

    // 削除対象データを取得（選択肢、選択式回答）
    EnqueteChoice choice = choiceDao.load(enqueteCode, questionNo, choiceNo);
    List<EnqueteReplyChoices> replyChoices = replyChoicesDao.findByQuery(EnqueteQuery.LOAD_REPLY_CHOICES, enqueteCode, questionNo);

    if (choice == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // 選択肢と、それに関連付いている回答を削除
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());
      txMgr.delete(choice);
      for (EnqueteReplyChoices rc : replyChoices) {
        txMgr.delete(rc);
      }
      txMgr.commit();
      logger.debug("succeed");
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.debug("failed");
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public ServiceResult deleteEnqueteQuestion(String enqueteCode, Long enqueteQuestionNo) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    EnqueteQuestionDao questionDao = DIContainer.getDao(EnqueteQuestionDao.class);
    EnqueteChoiceDao choiceDao = DIContainer.getDao(EnqueteChoiceDao.class);
    EnqueteReplyChoicesDao replyChoicesDao = DIContainer.getDao(EnqueteReplyChoicesDao.class);
    EnqueteReplyInputDao replyInputDao = DIContainer.getDao(EnqueteReplyInputDao.class);

    // 削除対象データを取得（設問、選択肢、選択式回答、自由入力式回答）
    EnqueteQuestion question = questionDao.load(enqueteCode, enqueteQuestionNo);
    List<EnqueteChoice> choices = choiceDao.findByQuery(EnqueteQuery.LOAD_CHOICES, enqueteCode, enqueteQuestionNo);
    List<EnqueteReplyChoices> replyChoices = replyChoicesDao.findByQuery(EnqueteQuery.LOAD_REPLY_CHOICES, enqueteCode,
        enqueteQuestionNo);
    List<EnqueteReplyInput> replyInputs = replyInputDao.findByQuery(EnqueteQuery.LOAD_REPLY_INPUT, enqueteCode, enqueteQuestionNo);

    // 設問存在チェック
    if (question == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // 該当設問と、それに関連付いている選択肢、回答を削除
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());
      txMgr.delete(question);
      for (EnqueteChoice c : choices) {
        txMgr.delete(c);
      }
      for (EnqueteReplyChoices rc : replyChoices) {
        txMgr.delete(rc);
      }
      for (EnqueteReplyInput ri : replyInputs) {
        txMgr.delete(ri);
      }
      txMgr.commit();
      logger.debug("succeed");
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.debug("failed");
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public ServiceResult deleteReviewPost(String reviewId) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      // 削除前にレビューをロードする
      ReviewPostDao dao = DIContainer.getDao(ReviewPostDao.class);
      ReviewPost post = dao.load(Long.valueOf(reviewId));

      txMgr.begin(this.getLoginInfo());
      // レビュー削除時は、仮発行のポイント履歴がある場合は無効にする。
      Query query = new SimpleQuery(PointHistoryQuery.LOAD_REVIEWPOINT_HISTORY_QUERY, reviewId);
      PointHistory pointHistory = DatabaseUtil.loadAsBean(query, PointHistory.class);
      if (pointHistory != null) {
        pointHistory.setPointIssueStatus(PointIssueStatus.DISABLED.longValue());
        pointHistory.setDescription(Messages.log("service.impl.CommunicationServiceImpl.0"));
        setUserStatus(pointHistory);

        PointUpdateProcedure pointUpdate = new PointUpdateProcedure(pointHistory);
        txMgr.executeProcedure(pointUpdate);
      }

      // レビュー削除
      txMgr.executeUpdate(new SimpleQuery(CommunicationServiceQuery.DELETE_REVIEW, reviewId));

      // レビュー集計の更新
      if (post != null) {
        ReviewSummaryUpdateProcedure proc = new ReviewSummaryUpdateProcedure();
        proc.setShopCode(post.getShopCode());
        proc.setCommodityCode(post.getCommodityCode());
        proc.setUpdatedUser(this.getLoginInfo().getRecordingFormat());
        txMgr.executeProcedure(proc);
      } else {
        Logger.getLogger(this.getClass()).warn("REVIEW NOT FOUND: reviewID=" + reviewId);
      }

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public Campaign getCampaign(String shopCode, String campaignCode) {

    CampaignDao dao = DIContainer.getDao(CampaignDao.class);

    Campaign campaign = dao.load(shopCode, campaignCode);

    return campaign;
  }

  public SearchResult<CampaignHeadLine> getCampaignList(CampaignListSearchCondition condition) {

    return DatabaseUtil.executeSearch(new CampaignListSearchQuery(condition));

  }

  public List<CampaignResearch> getCampaignResearch(String shopCode, String campaignCode) {
    Query query = new SimpleQuery(CampaignQuery.getLoadCampaignResearchQuery(), shopCode, campaignCode);
    return DatabaseUtil.loadAsBeanList(query, CampaignResearch.class);
  }

  public Enquete getCurrentEnquete() {
    EnqueteDao dao = DIContainer.getDao(EnqueteDao.class);

    List<Enquete> enqueteList = dao.findByQuery(new SimpleQuery(EnqueteQuery.LOAD_ENQUETE, DateUtil.truncateDate(DateUtil
        .getSysdate())));

    Enquete result = null;
    if (!enqueteList.isEmpty()) {
      result = enqueteList.get(0);
    }
    return result;
  }

  public Enquete getEnquete(String enqueteCode) {
    EnqueteDao enqueteDao = DIContainer.getDao(EnqueteDao.class);
    return enqueteDao.load(enqueteCode);
  }

  public SearchResult<EnqueteList> getEnqueteList(EnqueteListSearchCondition condition) {
    EnqueteSearchQuery query = new EnqueteSearchQuery(condition);
    return DatabaseUtil.executeSearch(query);
  }

  public EnqueteQuestion getEnqueteQuestion(String enqueteCode, Long questionNo) {
    EnqueteQuestionDao questionDao = DIContainer.getDao(EnqueteQuestionDao.class);
    return questionDao.load(enqueteCode, questionNo);
  }

  public List<EnqueteQuestion> getEnqueteQuestionList(String enqueteCode) {
    EnqueteQuestionDao questionDao = DIContainer.getDao(EnqueteQuestionDao.class);
    return questionDao.findByQuery(EnqueteQuery.LOAD_QUESTIONS, enqueteCode);
  }

  public List<EnqueteReplyInput> getEnqueteReplyInputList(String enqueteCode, Long enqueteQuestionNo) {

    EnqueteReplyInputDao dao = DIContainer.getDao(EnqueteReplyInputDao.class);
    return dao.findByQuery(EnqueteQuery.LOAD_REPLY_INPUT, enqueteCode, enqueteQuestionNo);
  }

  public ReviewPost getReviewPost(String reviewId) {
    ReviewPostDao dao = DIContainer.getDao(ReviewPostDao.class);
    return dao.load(Long.parseLong(reviewId));
  }

  public SearchResult<ReviewList> getReviewPostList(ReviewListSearchCondition condition) {

    // 10.1.3 10140 修正 ここから
    // ReviewSearchQuery query = new ReviewSearchQuery(condition);
    // return DatabaseUtil.executeSearch(query);
    SearchResult<ReviewList> searchResult = new SearchResult<ReviewList>();
    ReviewSearchQuery query = new ReviewSearchQuery(condition);
    SearchResult<ReviewList> result = DatabaseUtil.executeSearch(query);
    // ReviewList rl = new ReviewList(); // 10.1.4 K00168 削除
    for (ReviewList r : result.getRows()) {
      // 10.1.4 K00168 修正 ここから
      ReviewList rl = DatabaseUtil.loadAsBean(query.getReviewDiscriptionQuery(r.getReviewId().toString()), ReviewList.class);
      // 10.1.4 K00168 修正 ここまで
      r.setReviewDescription(rl.getReviewDescription());
    }
    searchResult.setCurrentPage(result.getCurrentPage());
    searchResult.setMaxFetchSize(result.getMaxFetchSize());
    searchResult.setOverflow(result.isOverflow());
    searchResult.setPageSize(result.getPageSize());
    searchResult.setRowCount(result.getRowCount());
    searchResult.setRows(result.getRows());
    return searchResult;
    // 10.1.3 10140 修正 ここまで
  }

  // 10.1.4 10189 追加 ここから
  public List<ReviewPost> getDisplayedReviewPostList(String shopCode, String commodityCode) {
    ReviewPostDao dao = DIContainer.getDao(ReviewPostDao.class);
    return dao.findByQuery(CommunicationServiceQuery.DISPLAYED_REVIEW_POST_LIST, shopCode, commodityCode);
  }

  // 10.1.4 10189 追加 ここまで

  public List<ReviewPostAndCustHeadLine> getDisplayedReviewPostAndCustList(String shopCode, String commodityCode) {
    Query query = new SimpleQuery(CommunicationServiceQuery.DISPLAYED_REVIEW_POST_AND_CUST_LIST, shopCode, commodityCode);
    return DatabaseUtil.loadAsBeanList(query, ReviewPostAndCustHeadLine.class);
  }

  // soukai add yyq 2013/09/13 start
  public ReviewSummary getReviewCommodity(String shopCode, String commodityCode) {
    ReviewSummaryDao dao = DIContainer.getDao(ReviewSummaryDao.class);
    return dao.load(shopCode, commodityCode);
  }

  // soukai add yyq 2013/09/13 end

  public ServiceResult insertCampaign(Campaign campaign) {

    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    CampaignDao dao = DIContainer.getDao(CampaignDao.class);

    // 重複エラーチェック
    if (dao.load(campaign.getShopCode(), campaign.getCampaignCode()) != null) {
      result.addServiceError(CommunicationServiceErrorContent.DUPLICATED_CODE_ERROR);
    }

    // validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(campaign).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
    }

    if (result.hasError()) {
      return result;
    }

    dao.insert(campaign, getLoginInfo());

    return result;
  }

  public ServiceResult updateCampaign(Campaign campaign) {

    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    CampaignDao dao = DIContainer.getDao(CampaignDao.class);

    // 更新対象データが存在しない場合はエラー
    Campaign c = dao.load(campaign.getShopCode(), campaign.getCampaignCode());
    if (c == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      logger.debug("no data. shopCode:" + campaign.getShopCode() + ", campaignCode:" + campaign.getCampaignCode());
      return result;
    }

    setUserStatus(campaign);

    // validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(campaign).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
    }

    if (result.hasError()) {
      return result;
    }

    dao.update(campaign, getLoginInfo());
    return result;
  }

  public ServiceResult insertEnquete(Enquete enquete) {
    ServiceResultImpl result = new ServiceResultImpl();
    EnqueteDao enqueteDao = DIContainer.getDao(EnqueteDao.class);
    setUserStatus(enquete);

    // アンケートコード重複チェック
    if (enqueteDao.load(enquete.getEnqueteCode()) != null) {
      result.addServiceError(CommunicationServiceErrorContent.DUPLICATED_CODE_ERROR);
    }

    // アンケート期間重複チェック
    Date startDate = enquete.getEnqueteStartDate();
    Date endDate = enquete.getEnqueteEndDate();
    Object[] params = {
        startDate, endDate, startDate, endDate, startDate, endDate, enquete.getEnqueteCode()
    };
    if (enqueteDao.findByQuery(EnqueteQuery.CHECK_PERIOD, params).size() >= 1) {
      result.addServiceError(CommunicationServiceErrorContent.DUPLICATED_PERIOD_ERROR);
    }

    List<ValidationResult> resultList = BeanValidator.validate(enquete).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
    }

    if (result.hasError()) {
      return result;
    }

    enqueteDao.insert(enquete, getLoginInfo());
    return result;
  }

  public ServiceResult insertEnqueteChoice(EnqueteChoice enqueteChoice) {
    ServiceResultImpl result = new ServiceResultImpl();

    if (getEnquete(enqueteChoice.getEnqueteCode()) == null
        || getEnqueteQuestion(enqueteChoice.getEnqueteCode(), enqueteChoice.getEnqueteQuestionNo()) == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    Long choicesNo = DatabaseUtil.generateSequence(SequenceType.ENQUETE_CHOICES_NO);
    enqueteChoice.setEnqueteChoicesNo(choicesNo);
    setUserStatus(enqueteChoice);

    List<ValidationResult> resultList = BeanValidator.validate(enqueteChoice).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return result;
    }

    EnqueteChoiceDao choiceDao = DIContainer.getDao(EnqueteChoiceDao.class);
    choiceDao.insert(enqueteChoice, getLoginInfo());

    return result;
  }

  public ServiceResult insertEnqueteQuestion(EnqueteQuestion enqueteQuestion) {
    ServiceResultImpl result = new ServiceResultImpl();

    // アンケートが存在しない場合はアンケート設問を登録できない
    Enquete enquete = getEnquete(enqueteQuestion.getEnqueteCode());
    if (enquete == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    Long questionNo = DatabaseUtil.generateSequence(SequenceType.ENQUETE_QUESTION_NO);
    enqueteQuestion.setEnqueteQuestionNo(questionNo);
    setUserStatus(enqueteQuestion);

    List<ValidationResult> resultList = BeanValidator.validate(enqueteQuestion).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return result;
    }

    EnqueteQuestionDao questionDao = DIContainer.getDao(EnqueteQuestionDao.class);
    questionDao.insert(enqueteQuestion, getLoginInfo());
    return result;
  }

  public ServiceResult insertReviewPost(ReviewPost reviewPost) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    long reviewId = DatabaseUtil.generateSequence(SequenceType.REVIEW_ID);
    reviewPost.setReviewId(reviewId);
    reviewPost.setReviewDisplayType(ReviewDisplayType.UNCHECKED.longValue());
    // レビューポイント割当フラグのデフォルトを0:未割当に設定
    reviewPost.setReviewPointAllocatedStatus(ReviewPointAllocatedStatus.NOT_ALLOCATED.longValue());
    reviewPost.setReviewContributedDatetime(DateUtil.getSysdate());
    setUserStatus(reviewPost);

    ValidationSummary summary = BeanValidator.validate(reviewPost);
    if (summary.hasError()) {
      for (String error : summary.getErrorMessages()) {
        logger.debug(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    // 同一ユーザの同一商品への投稿かどうかをチェック
    ReviewListSearchCondition condition = new ReviewListSearchCondition();
    condition.setSearchShopCode(reviewPost.getShopCode());
    condition.setSearchCommodityCode(reviewPost.getCommodityCode());
    condition.setSearchCustomerCode(reviewPost.getCustomerCode());
    // 有订单时
    if (StringUtil.hasValue(reviewPost.getOrderNo())) {
      condition.setSearchOrderNo(reviewPost.getOrderNo());
    }
    Query query = new ReviewSearchQuery(condition);
    logger.debug(query.getSqlString());
    ReviewPostDao dao = DIContainer.getDao(ReviewPostDao.class);
    List<ReviewPost> reviewPostList = dao.findByQuery(query);
    if (reviewPostList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }
    PointRule pointRule = CommonLogic.getPointRule(getLoginInfo());
    PointHistory pointHistory = null;
    Customer customer = null;
    long usePoint = Long.parseLong(PointFunctionEnabledFlg.ENABLED.getValue());
    if (pointRule.getPointFunctionEnabledFlg().equals(usePoint)
        && ValidatorUtil.moreThan(pointRule.getReviewContributedPoint(), BigDecimal.ZERO)) {
      // && pointRule.getReviewContributedPoint() > 0L) {
      // ポイント履歴
      CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
      customer = customerDao.load(reviewPost.getCustomerCode());

      pointHistory = new PointHistory();
      pointHistory.setReviewId(reviewId);
      // modify by V10-CH start
      // pointHistory.setIssuedPoint(pointRule.getReviewContributedPoint());
      // 20111221 os013 update start
      // pointHistory.setIssuedPoint(pointRule.getReviewContributedPoint());
      // 积分设为0
      pointHistory.setIssuedPoint(new BigDecimal(0));
      // 20111221 os013 update end
      // modify by V10-CH end
      pointHistory.setCustomerCode(customer.getCustomerCode());
      pointHistory.setPointHistoryId((DatabaseUtil.generateSequence(SequenceType.POINT_HISTORY_ID)));
      pointHistory.setPointIssueStatus(Long.parseLong(PointIssueStatus.PROVISIONAL.getValue()));
      pointHistory.setPointIssueType(Long.parseLong(PointIssueType.REVIEW.getValue()));
      pointHistory.setShopCode(DIContainer.getWebshopConfig().getSiteShopCode());
      pointHistory.setPointIssueDatetime(DateUtil.fromString(DateUtil.getSysdateString()));
      setUserStatus(pointHistory);

      // Validationチェック
      ValidationSummary validatePoint = BeanValidator.validate(pointHistory);
      if (validatePoint.hasError()) {
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String rs : validatePoint.getErrorMessages()) {
          logger.debug(rs);
        }
        return result;
      }
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());
      txMgr.insert(reviewPost);

      // ポイント履歴
      if (pointRule.getPointFunctionEnabledFlg().equals(usePoint)
      // && pointRule.getReviewContributedPoint() > 0L) {
          && ValidatorUtil.moreThan(pointRule.getReviewContributedPoint(), BigDecimal.ZERO)) {
        PointInsertProcedure pointInsert = new PointInsertProcedure(pointHistory);
        txMgr.executeProcedure(pointInsert);

        // ポイントのオーバーフロー時処理
        if (pointInsert.getResult() == CustomerConstant.POINT_OVERFLOW_CODE) {
          txMgr.rollback();
          result.addServiceError(CustomerServiceErrorContent.POINT_OVERFLOW_ERROR);
          return result;
        }
      }

      txMgr.commit();
      logger.debug("succeed");
      CommonLogic.verifyPointDifference(reviewPost.getCustomerCode(), PointIssueStatus.PROVISIONAL); // 10.1.3
      // 10174
      // 追加
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.debug("failed");
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public ServiceResult postEnqueteAnswer(EnqueteAnswer answer) {

    Logger logger = Logger.getLogger(this.getClass());

    ServiceResultImpl result = new ServiceResultImpl();
    setUserStatus(answer.getAnswerHeader());
    for (EnqueteReplyChoices choice : answer.getAnswerChoices()) {
      setUserStatus(choice);
    }
    for (EnqueteReplyInput reply : answer.getAnswerInputs()) {
      setUserStatus(reply);
    }

    // アンケート存在チェック
    for (EnqueteReplyChoices r : answer.getAnswerChoices()) {
      boolean existResult = false;
      List<EnqueteChoice> choiceList = getEnqueteChoiceList(r.getEnqueteCode(), r.getEnqueteQuestionNo());
      for (EnqueteChoice c : choiceList) {
        if (r.getEnqueteChoicesNo().equals(c.getEnqueteChoicesNo())) {
          existResult = true;
          break;
        }
      }

      if (!existResult) {
        result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return result;
      }
    }

    Enquete currentEnquete = getCurrentEnquete();

    if (currentEnquete == null) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    } else {
      String hashValue = calculateEnqueteHash(currentEnquete.getEnqueteCode());
      if (!hashValue.equals(answer.getHashValue())) {
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return result;
      }
    }

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(answer).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return result;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());
      // アンケート回答ヘッダ
      txMgr.insert(answer.getAnswerHeader());

      // アンケート選択式
      for (EnqueteReplyChoices c : answer.getAnswerChoices()) {
        txMgr.insert(c);
      }

      // アンケート自由回答
      for (EnqueteReplyInput r : answer.getAnswerInputs()) {
        txMgr.insert(r);
      }

      // 会員、かつポイント機能使用時は、ポイントを付与する
      Long pointFlg = CommonLogic.getPointRule(getLoginInfo()).getPointFunctionEnabledFlg();
      if (answer.isCustomer() && pointFlg.equals(PointFunctionEnabledFlg.ENABLED.longValue())
      // && currentEnquete.getEnqueteInvestPoint() > 0L) {
          && ValidatorUtil.moreThan(currentEnquete.getEnqueteInvestPoint(), BigDecimal.ZERO)) {
        // ポイント履歴
        PointHistory point = new PointHistory();
        point.setPointHistoryId((DatabaseUtil.generateSequence(SequenceType.POINT_HISTORY_ID)));
        point.setIssuedPoint(currentEnquete.getEnqueteInvestPoint());
        point.setCustomerCode(answer.getAnswerHeader().getCustomerCode());
        point.setPointIssueStatus(NumUtil.toLong(PointIssueStatus.ENABLED.getValue()));
        point.setPointIssueType(NumUtil.toLong(PointIssueType.ENQUETE.getValue()));
        point.setEnqueteCode(answer.getAnswerHeader().getEnqueteCode());
        point.setShopCode(DIContainer.getWebshopConfig().getSiteShopCode());
        point.setPointIssueDatetime(DateUtil.fromString(DateUtil.getSysdateString()));
        setUserStatus(point);

        // Validationチェック
        resultList = BeanValidator.validate(point).getErrors();
        if (resultList.size() > 0) {
          result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
          for (ValidationResult rs : resultList) {
            logger.debug(rs.getFormedMessage());
          }
          return result;
        }

        // ポイント更新処理
        PointInsertProcedure pointInsert = new PointInsertProcedure(point);
        txMgr.executeProcedure(pointInsert);

        // ポイントのオーバーフロー時処理
        if (pointInsert.getResult() == CustomerConstant.POINT_OVERFLOW_CODE) {
          txMgr.rollback();
          result.addServiceError(CustomerServiceErrorContent.POINT_OVERFLOW_ERROR);
          return result;
        }
      }

      txMgr.commit();
      CommonLogic.verifyPointDifference(answer.getAnswerHeader().getCustomerCode(), PointIssueStatus.ENABLED); // 10.1.3
      // 10174
      // 追加
    } catch (DuplicatedInsertException e) {
      txMgr.rollback();
      logger.debug(e);
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
    } catch (Exception e) {
      txMgr.rollback();
      logger.debug(e);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public ServiceResult updateEnquete(Enquete enquete) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    EnqueteDao enqueteDao = DIContainer.getDao(EnqueteDao.class);

    // アンケート期間重複チェック
    Date startDate = enquete.getEnqueteStartDate();
    Date endDate = enquete.getEnqueteEndDate();
    Object[] params = {
        startDate, endDate, startDate, endDate, startDate, endDate, enquete.getEnqueteCode()
    };
    if (enqueteDao.findByQuery(EnqueteQuery.CHECK_PERIOD, params).size() >= 1) {
      result.addServiceError(CommunicationServiceErrorContent.DUPLICATED_PERIOD_ERROR);
    }

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(enquete).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return result;
    }

    if (result.hasError()) {
      return result;
    }

    enqueteDao.update(enquete, getLoginInfo());
    return result;
  }

  public ServiceResult updateEnqueteChoice(EnqueteChoice enqueteChoice) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    EnqueteChoiceDao choiceDao = DIContainer.getDao(EnqueteChoiceDao.class);

    // 存在チェック
    EnqueteChoice eResult = choiceDao.load(enqueteChoice.getEnqueteCode(), enqueteChoice.getEnqueteQuestionNo(), enqueteChoice
        .getEnqueteChoicesNo());
    if (eResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    } else {
      enqueteChoice.setOrmRowid(eResult.getOrmRowid());
    }

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(enqueteChoice).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return result;
    }

    choiceDao.update(enqueteChoice, getLoginInfo());
    return result;
  }

  public ServiceResult updateEnqueteQuestion(EnqueteQuestion enqueteQuestion) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    EnqueteQuestionDao questiondDao = DIContainer.getDao(EnqueteQuestionDao.class);

    String enqueteCode = enqueteQuestion.getEnqueteCode();
    Long enqueteQuestionNo = enqueteQuestion.getEnqueteQuestionNo();

    // 存在チェック
    EnqueteQuestion eResult = questiondDao.load(enqueteCode, enqueteQuestionNo);
    if (eResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    } else {
      enqueteQuestion.setOrmRowid(eResult.getOrmRowid());
    }

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(enqueteQuestion).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return result;
    }

    // 選択式から自由入力に更新する場合は、設問に関連付いている選択肢を削除する
    boolean deleteChoice = false;
    List<EnqueteChoice> choices = new ArrayList<EnqueteChoice>();
    if (enqueteQuestion.getEnqueteQuestionType().equals(EnqueteQuestionType.FREE.longValue())) {
      deleteChoice = getEnqueteChoiceList(enqueteQuestion.getEnqueteCode(), enqueteQuestion.getEnqueteQuestionNo()).size() > 0;
      EnqueteChoiceDao choiceDao = DIContainer.getDao(EnqueteChoiceDao.class);
      choices = choiceDao.findByQuery(EnqueteQuery.LOAD_CHOICES, enqueteCode, enqueteQuestionNo);
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());
      if (deleteChoice) { // 関連付いている選択肢と回答を削除
        for (EnqueteChoice c : choices) {
          txMgr.delete(c);
        }
      }
      txMgr.update(enqueteQuestion);
      txMgr.commit();
      logger.debug("succeed");
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.debug("failed");
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public ServiceResult updateReviewPost(List<ReviewPost> reviewList, long updateType) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(reviewList).getErrors();
    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    // 表示状態の更新
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());
      for (ReviewPost reviewDto : reviewList) {

        // ポイント発行状態が「仮発行」のレビューポイント履歴を取得
        Query query = new SimpleQuery(PointHistoryQuery.LOAD_REVIEWPOINT_HISTORY_QUERY, reviewDto.getReviewId());
        PointHistory pointHistory = DatabaseUtil.loadAsBean(query, PointHistory.class);
        if (pointHistory != null) {

          boolean noAllocatedCustomer = reviewDto.getReviewPointAllocatedStatus().equals(
              ReviewPointAllocatedStatus.NOT_ALLOCATED.longValue());
          // ポイント未割当、かつ更新状態が「表示」の場合はポイントを割当てる
          if (noAllocatedCustomer && ReviewDisplayType.DISPLAY.longValue().equals(updateType)) {
            pointHistory.setPointIssueStatus(PointIssueStatus.ENABLED.longValue());
            setUserStatus(pointHistory);

            reviewDto.setReviewPointAllocatedStatus(ReviewPointAllocatedStatus.ALLOCATED.longValue()); // ポイント割当済み
          } else if (ReviewDisplayType.HIDDEN.longValue().equals(updateType)) { // 非表示に更新する場合は仮発行ポイントを無効にする
            pointHistory.setPointIssueStatus(PointIssueStatus.DISABLED.longValue());
            setUserStatus(pointHistory);
          }
          // ポイント履歴更新
          // Validationチェック
          resultList = BeanValidator.validate(pointHistory).getErrors();
          if (resultList.size() > 0) {
            serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
            for (ValidationResult rs : resultList) {
              logger.debug(rs.getFormedMessage());
            }
            return serviceResult;
          }
          PointUpdateProcedure pointUpdate = new PointUpdateProcedure(pointHistory);
          txMgr.executeProcedure(pointUpdate);

          // ポイントのオーバーフロー時処理
          if (pointUpdate.getResult() == CustomerConstant.POINT_OVERFLOW_CODE) {
            txMgr.rollback();
            serviceResult.addServiceError(CustomerServiceErrorContent.POINT_OVERFLOW_ERROR);
            return serviceResult;
          }
        }
        reviewDto.setReviewDisplayType(updateType);
        setUserStatus(reviewDto);
        txMgr.update(reviewDto);

        // レビュー集計の更新
        ReviewSummaryUpdateProcedure proc = new ReviewSummaryUpdateProcedure();
        proc.setShopCode(reviewDto.getShopCode());
        proc.setCommodityCode(reviewDto.getCommodityCode());
        proc.setUpdatedUser(this.getLoginInfo().getRecordingFormat());
        txMgr.executeProcedure(proc);

      }

      txMgr.commit();
      // 10.1.3 10174 追加 ここから
      for (ReviewPost rp : reviewList) {
        CommonLogic.verifyPointDifference(rp.getCustomerCode(), PointIssueStatus.ENABLED);
      }
      // 10.1.3 10174 追加 ここまで

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  /*
   * アンケートの回答者数合計を取得します
   */
  public Long getEnqueteAnswerCount(String enqueteCode) {
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(EnqueteQuery.COUNT_ENQUETE_ANSWER, enqueteCode));
    return NumUtil.parseLong(obj);
  }

  /*
   * 各設問の回答者数合計を取得します(人数の合計)
   */
  public Long getRepliedQuestionPersonsCount(String enqueteCode, Long questionNo, Long questionType) {
    String query = "";
    if (questionType.equals(2L)) {
      query = EnqueteQuery.COUNT_REPLIED_QUESTION_PERSONS_INPUTTYPE;
    } else {
      // 複数選択の場合、顧客コードの重複があり得るためDISTINCTで取得
      query = EnqueteQuery.COUNT_REPLIED_QUESTION_PERSONS_CHOICETYPE;
    }
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(query, enqueteCode, questionNo));
    return NumUtil.parseLong(obj);
  }

  /*
   * 各設問の回答者数合計を取得します(回答数の合計)
   */
  public Long getRepliedQuestionCount(String enqueteCode, Long questionNo) {
    String query = EnqueteQuery.COUNT_REPLIED_QUESTION;
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(query, enqueteCode, questionNo));
    return NumUtil.parseLong(obj);
  }

  /*
   * 各選択肢の回答者数合計を取得します
   */
  public Long getRepliedChoiceCount(String enqueteCode, Long questionNo, Long choiceNo) {
    String query = EnqueteQuery.COUNT_REPLIED_CHOICE;
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(query, enqueteCode, questionNo, choiceNo));
    return NumUtil.parseLong(obj);
  }

  public ServiceResult deleteMailMagazine(String mailMagazineCode) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();
    MailMagazineDao dao = DIContainer.getDao(MailMagazineDao.class);
    Logger logger = Logger.getLogger(this.getClass());

    // 削除対象データが存在しない場合はエラー
    MailMagazine mailMagazine = dao.load(mailMagazineCode);
    if (mailMagazine == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      logger.debug("no data. mailMagazineCode:" + mailMagazineCode);
      return serviceResult;
    }

    // メールマガジンとメール購読者を削除
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());
      txMgr.delete(dao.load(mailMagazineCode));
      txMgr.executeUpdate(MailMagazineQuery.DELETE_SUBSCRIBERS, mailMagazineCode);
      txMgr.commit();
      logger.debug("success.");
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.debug("failed");
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public MailMagazine getMailMagazine(String mailMagazineCode) {
    MailMagazineDao dao = DIContainer.getDao(MailMagazineDao.class);
    return dao.load(mailMagazineCode);
  }

  public List<MailMagazine> getMailMagazineList() {
    MailMagazineDao dao = DIContainer.getDao(MailMagazineDao.class);
    return dao.findByQuery(MailMagazineQuery.LOAD_DISPLAY_LIST);
  }

  public List<MailMagazine> getSubscribersMailMagazineList(String email) {
    Query query = new SimpleQuery(MailMagazineQuery.LOAD_SUBSCRIBERS_MAGAZINE_LIST, email);
    return DatabaseUtil.loadAsBeanList(query, MailMagazine.class);
  }

  public List<MailMagazineHeadLine> getMailMagazineHeaderList() {
    Query query = new SimpleQuery(MailMagazineQuery.LOAD_HEADER_LIST);
    return DatabaseUtil.loadAsBeanList(query, MailMagazineHeadLine.class);
  }

  public ServiceResult insertMailMagazine(MailMagazine mailMagazine) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    MailMagazineDao dao = DIContainer.getDao(MailMagazineDao.class);

    setUserStatus(mailMagazine);

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(mailMagazine).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    // 登録済みデータの場合はエラーとする
    if (dao.load(mailMagazine.getMailMagazineCode()) != null) {
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return serviceResult;
    }
    dao.insert(mailMagazine, getLoginInfo());
    return serviceResult;
  }

  public ServiceResult updateMailMagazine(MailMagazine mailMagazine) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    MailMagazineDao dao = DIContainer.getDao(MailMagazineDao.class);

    // 更新対象データが存在しない場合はエラーとする
    MailMagazine mResult = getMailMagazine(mailMagazine.getMailMagazineCode());
    if (mResult == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    } else {
      mailMagazine.setOrmRowid(mResult.getOrmRowid());
      mailMagazine.setCreatedUser(mResult.getCreatedUser());
      mailMagazine.setCreatedDatetime(mResult.getCreatedDatetime());
    }

    setUserStatus(mailMagazine);

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(mailMagazine).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    dao.update(mailMagazine, getLoginInfo());
    return serviceResult;
  }

  public ServiceResult insertMailMagazineSubscriber(MailMagazineSubscriber mailMagazineSubscriber) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    MailMagazineSubscriberDao dao = DIContainer.getDao(MailMagazineSubscriberDao.class);

    setUserStatus(mailMagazineSubscriber);

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(mailMagazineSubscriber).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    // 登録済みデータの場合はエラーとする
    if (dao.load(mailMagazineSubscriber.getMailMagazineCode(), mailMagazineSubscriber.getEmail()) != null) {
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return serviceResult;
    }

    dao.insert(mailMagazineSubscriber, getLoginInfo());
    return serviceResult;
  }

  public ServiceResult cancelMailMagazineSubscriber(MailMagazineSubscriber mailMagazineSubscriber) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    MailMagazineSubscriberDao subscriberDao = DIContainer.getDao(MailMagazineSubscriberDao.class);
    String mailMagazineCode = mailMagazineSubscriber.getMailMagazineCode();
    String email = mailMagazineSubscriber.getEmail();

    if (subscriberDao.load(mailMagazineCode, email) == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    subscriberDao.delete(mailMagazineCode, email);
    return serviceResult;
  }

  public Long getReviewPostCount(ReviewPostCountSearchCondition condition) {
    return NumUtil.parseLong(DatabaseUtil.executeScalar(new ReviewPostCountSearchQuery(condition)));
  }

  public boolean isAlreadyAnswerEnquete(String customerCode, String enqueteCode) {
    EnqueteAnswerHeaderDao dao = DIContainer.getDao(EnqueteAnswerHeaderDao.class);
    EnqueteAnswerHeader header = dao.load(customerCode, enqueteCode);
    if (header == null) {
      return false;
    }
    return true;
  }

  public ServiceResult generateReviewSummary() {

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    try {
      StoredProcedureResult procResult = DatabaseUtil.executeProcedure(new ReviewSummaryProcedure(this.getLoginInfo()
          .getRecordingFormat()));
      if (procResult.getParameters().get("r_result").toString().equals(StoredProceduedResultType.FAILED.getValue())) {
        serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      }
    } catch (DataAccessException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }

    return serviceResult;
  }

  public List<EnqueteChoice> getEnqueteChoiceList(String enqueteCode, Long enqueteQuestionNo) {
    EnqueteChoiceDao choiceDao = DIContainer.getDao(EnqueteChoiceDao.class);
    return choiceDao.findByQuery(EnqueteQuery.LOAD_CHOICES, enqueteCode, enqueteQuestionNo);
  }

  public boolean isAlreadyPostReview(String shopCode, String commodityCode, String customerCode) {
    ReviewListSearchCondition condition = new ReviewListSearchCondition();
    condition.setSearchShopCode(shopCode);
    condition.setSearchCommodityCode(commodityCode);
    condition.setSearchCustomerCode(customerCode);
    SearchResult<ReviewList> result = getReviewPostList(condition);
    if (result.getRows().isEmpty()) {
      return false;
    }
    return true;

  }

  // 20111219 os013 add start
  public boolean isAlreadyPostReview(String shopCode, String commodityCode, String customerCode, String orderNo) {
    ReviewListSearchCondition condition = new ReviewListSearchCondition();
    condition.setSearchShopCode(shopCode);
    condition.setSearchCommodityCode(commodityCode);
    condition.setSearchCustomerCode(customerCode);
    condition.setSearchOrderNo(orderNo);
    SearchResult<ReviewList> result = getReviewPostList(condition);
    if (result.getRows().isEmpty()) {
      return false;
    }
    return true;

  }

  // 20111219 os013 add end
  // 20111216 lirong add start
  /**
   * 查看顾客对商品评论的次数
   * 
   * @param condition
   *          查询条件
   * @return 评论次数
   */
  public Long getReviewPostCount(ReviewPostCustomerCountSearchCondition condition) {
    return NumUtil.parseLong(DatabaseUtil.executeScalar(new ReviewPostCustomerCountSearchQuery(condition)));
  }

  // 20111216 lirong add end
  public String calculateEnqueteHash(String enqueteCode) {
    Enquete enquete = getEnquete(enqueteCode);

    if (enquete == null) {
      return "";
    }

    Long input = enquete.getUpdatedDatetime().getTime();

    List<EnqueteQuestion> questionList = getEnqueteQuestionList(enqueteCode);
    for (EnqueteQuestion q : questionList) {
      input += q.getUpdatedDatetime().getTime() / questionList.size();
      if (!q.getEnqueteQuestionType().equals(EnqueteQuestionType.FREE.longValue())) {
        List<EnqueteChoice> choicesList = getEnqueteChoiceList(enqueteCode, q.getEnqueteQuestionNo());
        for (EnqueteChoice c : choicesList) {
          input += c.getUpdatedDatetime().getTime() / choicesList.size();
        }
      }
    }

    return PasswordUtil.getDigest("" + input);
  }

  // soukai add ob 2011/12/14 start
  /**
   * 根据优惠券规则编号取得优惠券规则信息
   * 
   * @param couponCode
   *          优惠券规则编号
   * @return 优惠券规则信息
   */
  public NewCouponRule getPrivateCoupon(String couponCode) {
    NewCouponRuleDao choiceDao = DIContainer.getDao(NewCouponRuleDao.class);
    return choiceDao.load(couponCode);
  }

  // 2013/04/03 优惠券对应 ob add start
  /**
   * 根据优惠券规则编号取得优惠券规则_发行关联信息
   * 
   * @param couponCode
   *          优惠券规则编号
   * @return 优惠券规则信息
   */
  public NewCouponRuleLssueInfo getPrivateCouponLssue(String couponCode) {
    NewCouponRuleLssueInfoDao choiceDao = DIContainer.getDao(NewCouponRuleLssueInfoDao.class);
    return choiceDao.load(couponCode);
  }

  /**
   * 根据优惠券规则编号取得优惠券规则_发行关联信息
   * 
   * @param couponCode
   *          优惠券规则编号
   * @return 优惠券规则信息
   */
  public ServiceResult deletePrivateCouponLssue(String couponCode, String typeCode, String flg) {

    // 判断优惠券信息存在时删除
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    // 判断优惠券信息是否存在
    NewCouponRuleLssueInfoDao dao = DIContainer.getDao(NewCouponRuleLssueInfoDao.class);
    NewCouponRuleLssueInfo newCouponRuleLssueInfo = dao.load(couponCode, typeCode, flg);
    if (newCouponRuleLssueInfo == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    try {
      txMgr.begin(getLoginInfo());

      // 顾客组别优惠信息删除
      txMgr.delete(newCouponRuleLssueInfo);

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public ServiceResult deletePrivateCouponUse(String couponCode, String useCommodityCode, String flg) {
    // 判断优惠券信息存在时删除
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    // 判断优惠券信息是否存在
    NewCouponRuleUseInfoDao dao = DIContainer.getDao(NewCouponRuleUseInfoDao.class);
    List<NewCouponRuleUseInfo> newCouponRuleUseInfo = dao.load(couponCode, useCommodityCode, flg);
    if (newCouponRuleUseInfo == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }
    try {
      txMgr.begin(getLoginInfo());

      // 顾客组别优惠信息删除
      for (NewCouponRuleUseInfo CouponRuleUseInfo : newCouponRuleUseInfo) {
        txMgr.delete(CouponRuleUseInfo);
      }

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  /**
   * 添加新的优惠券规则_发行关联信息
   * 
   * @param 优惠券规则对象
   * @return 结果
   */
  public ServiceResult insertNewCouponRuleLssue(NewCouponRuleLssueInfo newCouponRuleLssueInfo, String flg) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    // 判断优惠券信息是否存在
    NewCouponRuleLssueInfoDao dao = DIContainer.getDao(NewCouponRuleLssueInfoDao.class);
    NewCouponRuleLssueInfo couponRuleLssueInfo = null;

    if ("lssueBrandRegister".equals(flg) || "lssueBrandDelete".equals(flg)) {
      couponRuleLssueInfo = dao.load(newCouponRuleLssueInfo.getCouponCode(), newCouponRuleLssueInfo.getBrandCode(), flg);
    } else if ("lssueCategoryRegister".equals(flg) || "lssueCategoryDelete".equals(flg)) {
      couponRuleLssueInfo = dao.load(newCouponRuleLssueInfo.getCouponCode(), newCouponRuleLssueInfo.getCategoryCode(), flg);
    } else if ("lssueRegister".equals(flg) || "delete".equals(flg)) {
      couponRuleLssueInfo = dao.load(newCouponRuleLssueInfo.getCouponCode(), newCouponRuleLssueInfo.getCommodityCode(), flg);
    }

    if (couponRuleLssueInfo != null) {
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return serviceResult;
    }
    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(newCouponRuleLssueInfo);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }
    setUserStatus(newCouponRuleLssueInfo);
    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(newCouponRuleLssueInfo);
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  /**
   * 添加新的优惠券规则_使用关联信息
   * 
   * @param 优惠券规则对象
   * @return 结果
   */
  public ServiceResult insertNewCouponRuleUse(NewCouponRuleUseInfo newCouponRuleUseInfo) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    setUserStatus(newCouponRuleUseInfo);
    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(newCouponRuleUseInfo);
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  /**
   * 根据优惠券规则编号取得优惠券规则_发行关联信息
   * 
   * @param couponCode
   *          优惠券规则编号
   * @return 优惠券规则_发行关联信息
   */
  public List<NewCouponRuleUseInfo> getPrivateCouponUse(String couponCode, String useCommodityCode, String flg) {
    NewCouponRuleUseInfoDao choiceDao = DIContainer.getDao(NewCouponRuleUseInfoDao.class);
    return choiceDao.load(couponCode, useCommodityCode, flg);
  }

  public List<NewCouponRuleUseInfo> getMaxCouponUse(String couponCode) {
    NewCouponRuleUseInfoDao choiceDao = DIContainer.getDao(NewCouponRuleUseInfoDao.class);
    return choiceDao.load(couponCode, "Max");
  }

  /**
   * 根据优惠券规则编号取得优惠券规则_发行关联信息
   * 
   * @param couponCode
   *          优惠券规则编号
   * @return 优惠券规则_发行关联信息
   */
  public List<NewCouponRuleUseInfo> getPrivateCouponUse(String couponCode, boolean trueOrFalse) {
    NewCouponRuleUseInfoDao choiceDao = DIContainer.getDao(NewCouponRuleUseInfoDao.class);
    return choiceDao.load(couponCode, trueOrFalse);
  }

  // 2013/04/03 优惠券对应 ob add end
  /**
   * 添加顾客组别优惠
   * 
   * @param customerGroupCampaign
   *          顾客组别优惠
   * @return 结果
   */
  public ServiceResult insertCustomerGroupCampaign(CustomerGroupCampaign customerGroupCampaign) {
    ServiceResultImpl result = new ServiceResultImpl();

    // 优惠活动编号重复判断
    if (getCustomerGroupCampaign(customerGroupCampaign.getCampaignCode()) != null) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }

    // 相同顾客组别，是否存在重复优惠活动check
    Long count = getCustomerGroupCampaignCount(customerGroupCampaign);
    if (count > 0L) {
      result.addServiceError(CommunicationServiceErrorContent.DUPLICATED_PERIOD_ERROR);
      return result;
    }

    ValidationSummary validateCustomer = BeanValidator.validate(customerGroupCampaign);
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      return result;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      // 顾客组别优惠登录
      txMgr.insert(customerGroupCampaign);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }
    return result;

  }

  /**
   * 根据顾客组别和活动期间获得顾客组别优惠的数量
   * 
   * @param customerGroupCampaign
   * @return
   */
  private Long getCustomerGroupCampaignCount(CustomerGroupCampaign customerGroupCampaign) {
    SimpleQuery query;
    if (StringUtil.isNullOrEmpty(customerGroupCampaign.getCustomerGroupCode())) {
      query = new SimpleQuery(CustomerGroupCampaignSearchQuery.COUNT_SQL2, customerGroupCampaign.getCampaignStartDatetime(),
          customerGroupCampaign.getCampaignEndDatetime(), customerGroupCampaign.getCampaignCode());
    } else {
      query = new SimpleQuery(CustomerGroupCampaignSearchQuery.COUNT_SQL, customerGroupCampaign.getCustomerGroupCode(),
          customerGroupCampaign.getCampaignStartDatetime(), customerGroupCampaign.getCampaignEndDatetime(), customerGroupCampaign
              .getCampaignCode());
    }
    Object obj = DatabaseUtil.executeScalar(query);
    return NumUtil.parseLong(obj);

  }

  /**
   * 添加新的优惠券规则
   * 
   * @param 优惠券规则对象
   * @return 结果
   */
  public ServiceResult insertNewCouponRule(NewCouponRule newCouponRule) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(newCouponRule);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return null;
    }

    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(newCouponRule);
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  /**
   * 根据查询条件取得优惠券规则信息
   * 
   * @param condition
   *          查询条件
   * @param flag
   *          false:公共优惠劵查询 true:顾客别优惠劵查询
   * @return 优惠券规则信息
   */
  public SearchResult<NewCouponRule> searchNewCouponRuleList(PrivateCouponListSearchCondition condition, boolean flag) {
    return DatabaseUtil.executeSearch(new PrivateCouponListSearchQuery(condition, flag));
  }
  
  public SearchResult<OptionalCampaign> searchOptionalCampaignList(OptionalCampaignListSearchCondition condition) {
    return DatabaseUtil.executeSearch(new OptionalCampaignListSearchQuery(condition));
  }

  public SearchResult<CustomerGroupCampaignHeadLine> getCustomerGroupCampaignList(CustomerGroupCampaignSearchCondition condition) {
    return DatabaseUtil.executeSearch(new CustomerGroupCampaignSearchQuery(condition));
  }

  // soukai add ob 2011/12/14 end

  // soukai add ob 2011/12/15 start

  /**
   * 根据顾客组别编号删除顾客组别优惠信息
   * 
   * @param couponCode
   *          顾客组别编号
   * @return 删除结果
   */
  public ServiceResult deleteNewCouponRule(String couponCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    // 判断优惠券信息是否存在
    NewCouponRuleDao dao = DIContainer.getDao(NewCouponRuleDao.class);
    NewCouponRule newCouponRule = dao.load(couponCode);
    if (newCouponRule == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }
    try {
      txMgr.begin(getLoginInfo());

      // 顾客组别优惠信息删除
      txMgr.delete(newCouponRule);

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  /**
   * 更新优惠券规则
   * 
   * @param 优惠券规则对象
   * @return 更新结果
   */
  public ServiceResult updateNewCouponRule(NewCouponRule newCouponRule) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(newCouponRule);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      // 更新优惠券规则
      txMgr.update(newCouponRule);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;

  }

  @Override
  public CustomerGroupCampaign getCustomerGroupCampaign(String campaignCode) {
    CustomerGroupCampaignDao customerGroupCampaignDao = DIContainer.getDao(CustomerGroupCampaignDao.class);
    return customerGroupCampaignDao.load(campaignCode);
  }

  @Override
  public ServiceResult updateCustomerGroupCampaign(CustomerGroupCampaign customerGroupCampaign) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 存在性判断
    if (getCustomerGroupCampaign(customerGroupCampaign.getCampaignCode()) == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    // 相同顾客组别，是否存在重复优惠活动check
    Long count = getCustomerGroupCampaignCount(customerGroupCampaign);
    if (count > 0L) {
      serviceResult.addServiceError(CommunicationServiceErrorContent.DUPLICATED_PERIOD_ERROR);
      return serviceResult;
    }

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(customerGroupCampaign);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      // 顾客组别优惠登录
      txMgr.update(customerGroupCampaign);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  @Override
  public ServiceResult deleteCustomerGroupCampaign(String campaignCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {

      txMgr.begin(getLoginInfo());

      CustomerGroupCampaign campaign = getCustomerGroupCampaign(campaignCode);
      // 顾客组别优惠信息删除
      if (campaign != null) {
        txMgr.delete(getCustomerGroupCampaign(campaignCode));
        // 判断优惠券信息是否应经被占用
        Object obj = DatabaseUtil.executeScalar(new SimpleQuery(PrivateCouponListSearchQuery.SELECT_ORDER_HEADE_BY_DISCOUNT_CODE,
            campaignCode, 9));

        if (NumUtil.parseLong(obj) > 0) {
          serviceResult.addServiceError(CommunicationServiceErrorContent.NEWCOUPONRULE_USE_ERROR);
          return serviceResult;
        }
      } else {
        Logger.getLogger(this.getClass()).warn("CAMPAIGN NOT FOUND: campaignCode=" + campaignCode);
        serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  /**
   * 根据查询条件取得顾客组别优惠活动信息
   * 
   * @param condition
   *          查询条件
   * @return 顾客组别优惠活动信息
   */
  public SearchResult<CustomerGroupCampaignSummaryViewList> getCustomerGroupCampaignSummaryViewList(
      CustomerGroupCampaignSummaryViewSearchCondition condition) {
    return DatabaseUtil.executeSearch(new CustomerGroupCampaignSummaryViewSearchQuery(condition));
  }

  // 2012/11/16 促销对应 ob add start
  /**
   * 根据查询条件取得促销活动信息
   * 
   * @param condition
   *          查询条件
   * @return 促销活动信息
   */
  @Override
  public SearchResult<CampaignMain> getCampaignList(CampaignSearchCondition condition) {
    return DatabaseUtil.executeSearch(new CampaignSearchQuery(condition));
  }

  /**
   * 根据活动编号删除促销活动信息
   * 
   * @param campaignCode
   *          删除条件（活动编号）
   * @return 删除信息
   */
  public ServiceResult deleteCampaign(String campaignCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    Query query = new SimpleQuery(CommunicationServiceQuery.CAMPAIGN_MAIN_COUNT_SEARCH, campaignCode);
    Long usedCount = DatabaseUtil.executeScalar(query, Long.class);
    if (usedCount >= 1L) {
      serviceResult.addServiceError(CommunicationServiceErrorContent.PLAN_USE_ERROR);
      return serviceResult;
    }
    try {

      txMgr.begin(getLoginInfo());
      CampaignInfo campaignInfo = getCampaignInfo(campaignCode);

      // 活动删除
      if (campaignInfo != null && campaignInfo.getCampaignMain() != null
          && StringUtil.hasValue(campaignInfo.getCampaignMain().getCampaignCode())) {
        txMgr.delete(campaignInfo.getCampaignMain());
        for (CampaignCondition campaignCondition : campaignInfo.getConditionList()) {
          txMgr.delete(campaignCondition);
        }
        txMgr.delete(campaignInfo.getCampaignDoings());
      } else {
        serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  // 2012/11/16 促销对应 ob add end
  @Override
  public SearchResult<Plan> getPlanList(PlanSearchCondition condition) {
    return DatabaseUtil.executeSearch(new PlanSearchQuery(condition));
  }

  @Override
  public ServiceResult deletePlan(String planCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    Query query = new SimpleQuery(CommunicationServiceQuery.PLAN_USED_COUNT_SEARCH, planCode, planCode);
    Long usedCount = DatabaseUtil.executeScalar(query, Long.class);
    query = new SimpleQuery(CommunicationServiceQuery.TMALL_PLAN_USED_COUNT_SEARCH, planCode, planCode);
    usedCount = usedCount + DatabaseUtil.executeScalar(query, Long.class);
    if (usedCount >= 1L) {
      serviceResult.addServiceError(CommunicationServiceErrorContent.PLAN_USE_ERROR);
      return serviceResult;
    }
    try {

      txMgr.begin(getLoginInfo());

      Plan plan = getPlan(planCode);
      PlanDetailDao planDetailDao = DIContainer.getDao(PlanDetailDao.class);
      List<PlanDetail> detailList = planDetailDao.findByQuery("SELECT * FROM PLAN_DETAIL WHERE PLAN_CODE = ?", planCode);
      // 企划删除
      if (plan != null) {
        txMgr.delete(plan);

        for (int i = 0; i < detailList.size(); i++) {
          txMgr.delete(detailList.get(i));
          PlanCommodityDao dao = DIContainer.getDao(PlanCommodityDao.class);
          List<PlanCommodity> commodityList = dao.findByQuery(PlanSearchQuery.FIND_PLAN_COMMODITY_BY_DETAIL, plan.getPlanCode(),
              detailList.get(i).getDetailType(), detailList.get(i).getDetailCode());
          for (int j = 0; j < commodityList.size(); j++) {
            txMgr.delete(commodityList.get(j));
          }
        }
      } else {
        serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public Plan getPlan(String planCode) {
    PlanDao planDao = DIContainer.getDao(PlanDao.class);
    return planDao.load(planCode);
  }

  public SearchResult<PrivateCouponListSummary> searchNewCouponRuleList_analysis(PrivateCouponListSearchCondition condition,
      boolean flag) {
    return DatabaseUtil.executeSearch(new PrivateCouponSearchQuery(condition, flag));
  }
  
  public SearchResult<GiftCardDetailListSummary> searchGiftCardList_analysis(GiftCardDetailListSearchCondition condition,
      boolean flag) {
    return DatabaseUtil.executeSearch(new GiftCardDetailSearchQuery(condition, flag));
  }
  
  public SearchResult<GiftCardReturnApply> searchGiftCardReturnList(GiftCardReturnListSearchCondition condition,
      boolean flag) {
    return DatabaseUtil.executeSearch(new GiftCardReturnSearchQuery(condition, flag));
  }

  @Override
  public ServiceResult insertPlan(Plan plan) {
    ServiceResultImpl result = new ServiceResultImpl();

    // 企划编号重复判断
    if (getPlan(plan.getPlanCode()) != null) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }

    // 是否存在重复企划check
    Long count = getPlanCount(plan);
    if (count > 0L) {
      result.addServiceError(CommunicationServiceErrorContent.DUPLICATED_PERIOD_ERROR);
      return result;
    }

    ValidationSummary validateCustomer = BeanValidator.validate(plan);
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      return result;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      // 顾客组别优惠登录
      txMgr.insert(plan);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  @Override
  public ServiceResult updatePlan(Plan plan) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 存在性判断
    if (getPlan(plan.getPlanCode()) == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    // 是否存在重复企划check
    Long count = getPlanCount(plan);
    if (count > 0L) {
      serviceResult.addServiceError(CommunicationServiceErrorContent.DUPLICATED_PERIOD_ERROR);
      return serviceResult;
    }

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(plan);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      // 促销企划登录
      txMgr.update(plan);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  private Long getPlanCount(Plan plan) {
    SimpleQuery query = new SimpleQuery(PlanSearchQuery.COUNT_SQL, plan.getPlanType(), plan.getPlanDetailType(), plan
        .getPlanStartDatetime(), plan.getPlanEndDatetime(), plan.getPlanCode());
    Object obj = DatabaseUtil.executeScalar(query);
    return NumUtil.parseLong(obj);
  }

  // soukai add ob 2011/12/15 end

  // soukai add ob 2011/12/21 start
  /**
   * 判断是否存在已经登录过的发行期间
   * 
   * @param newCouponRule
   *          顾客别优惠券规则信息
   * @return true：存在 false：不存在
   */
  public boolean checkNewCouponRuleDuplicatedRegister(NewCouponRule newCouponRule) {
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(PrivateCouponListSearchQuery.CHECK_DUPLICATED_REGISTER, newCouponRule
        .getMinIssueOrderAmount(), newCouponRule.getMinIssueEndDatetime(), newCouponRule.getMinIssueStartDatetime(), newCouponRule
        .getCouponCode(), newCouponRule.getCouponType()));
    if (NumUtil.parseLong(obj) > 0) {
      return true;
    }
    return false;
  }

  @Override
  public List<PlanDetailHeadLine> getPlanDetailByPlanCode(String planCode) {
    SimpleQuery query = new SimpleQuery(PlanSearchQuery.FIND_PLAN_DETAIL, planCode);
    return DatabaseUtil.loadAsBeanList(query, PlanDetailHeadLine.class);
  }

  public PlanDetail getPlanDetail(String planCode, String detailType, String detailCode) {
    PlanDetailDao planDetailDao = DIContainer.getDao(PlanDetailDao.class);
    return planDetailDao.load(planCode, detailType, detailCode);
  }

  public List<PlanRelatedHeadLine> getRelatedHeadLine(String planCode, String detailType, String detailCode) {
    SimpleQuery query = new SimpleQuery(PlanSearchQuery.PLAN_RELATED_COMMODITY, planCode, detailType, detailCode);
    return DatabaseUtil.loadAsBeanList(query, PlanRelatedHeadLine.class);
  }

  // 2013/04/03 优惠券对应 ob add start
  public List<PlanRelatedHeadLine> getRelatedHeadLineList(String couponCode) {
    SimpleQuery query = new SimpleQuery(PlanSearchQuery.COUPON_RELATED_COMMODITY, couponCode);
    return DatabaseUtil.loadAsBeanList(query, PlanRelatedHeadLine.class);
  }

  public List<PlanRelatedHeadLine> getRelatedHeadLineListUse(String couponCode) {
    SimpleQuery query = new SimpleQuery(PlanSearchQuery.USE_RELATED_COMMODITY, couponCode);
    return DatabaseUtil.loadAsBeanList(query, PlanRelatedHeadLine.class);
  }

  // 2013/04/03 优惠券对应 ob add end
  @Override
  public ServiceResult insertPlanDetail(PlanDetail planDetail) {
    ServiceResultImpl result = new ServiceResultImpl();

    // 企划明细重复判断
    if (getPlanDetail(planDetail.getPlanCode(), NumUtil.toString(planDetail.getDetailType()), planDetail.getDetailCode()) != null) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }

    ValidationSummary validateCustomer = BeanValidator.validate(planDetail);
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      return result;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      // 企划明细
      txMgr.insert(planDetail);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public ServiceResult updatePlanDetail(PlanDetail planDetail) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 存在性判断
    if (getPlanDetail(planDetail.getPlanCode(), NumUtil.toString(planDetail.getDetailType()), planDetail.getDetailCode()) == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(planDetail);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      // 企划明细更新
      txMgr.update(planDetail);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public ServiceResult insertPlanCommodity(PlanCommodity planCommodity) {
    ServiceResultImpl result = new ServiceResultImpl();

    // 数据check开始

    // 品牌关联商品存在性check
    /*
     * if
     * (PlanDetailType.BRAND.longValue().equals(planCommodity.getDetailType()))
     * { // BrandCDao brandDao = DIContainer.getDao(BrandDao.class); //
     * brandDao. } else if
     * (PlanDetailType.CATEGORY.longValue().equals(planCommodity
     * .getDetailType())) { CategoryDao categoryDao =
     * DIContainer.getDao(CategoryDao.class); Category category =
     * categoryDao.load(planCommodity.getDetailCode()); SimpleQuery query = new
     * SimpleQuery(PlanSearchQuery.CATEGORY_COMMOIDTY_EXISTS,
     * planCommodity.getCommodityCode(), category.getPath() + "%"); Object obj =
     * DatabaseUtil.executeScalar(query); if (NumUtil.parseLong(obj) == 0) {
     * result.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
     * return result; } }
     */
    PlanCommodityDao pcDao = DIContainer.getDao(PlanCommodityDao.class);
    PlanCommodity pc = pcDao.load(planCommodity.getPlanCode(), planCommodity.getDetailType().toString(), planCommodity
        .getDetailCode(), planCommodity.getCommodityCode());
    if (pc != null) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }

    // 数据check结束
    ValidationSummary validateCustomer = BeanValidator.validate(planCommodity);
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      return result;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      // 企划关联商品
      txMgr.insert(planCommodity);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  private PlanCommodity getPlanCommodity(String planCode, String detailType, String detailCode, String commodityCode) {
    PlanCommodityDao dao = DIContainer.getDao(PlanCommodityDao.class);
    return dao.load(planCode, detailType, detailCode, commodityCode);
  }

  public ServiceResult deletePlanCommodity(String planCode, String detailType, String detailCode, String commodityCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {

      txMgr.begin(getLoginInfo());

      PlanCommodity planCommodity = getPlanCommodity(planCode, detailType, detailCode, commodityCode);
      // 企划明细关联删除
      if (planCommodity != null) {
        txMgr.delete(planCommodity);
      } else {
        serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public ServiceResult deletePlanDetail(String planCode, String detailType, String detailCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {

      txMgr.begin(getLoginInfo());

      PlanDetail planDetail = getPlanDetail(planCode, detailType, detailCode);
      // 企划明细删除
      if (planDetail != null) {
        txMgr.delete(planDetail);
        PlanCommodityDao dao = DIContainer.getDao(PlanCommodityDao.class);
        List<PlanCommodity> commodityList = dao.findByQuery(PlanSearchQuery.FIND_PLAN_COMMODITY_BY_DETAIL, planCode, detailType,
            detailCode);
        for (int j = 0; j < commodityList.size(); j++) {
          txMgr.delete(commodityList.get(j));
        }
      } else {
        serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  /**
   * 根据公共优惠劵规则编号删除公共优惠劵信息
   * 
   * @param couponCode
   *          公共规则编号
   * @param type
   *          公共发行类型
   * @return 删除结果
   */
  public ServiceResult deleteNewPublicCouponRule(String couponCode, String type) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    // 判断优惠券信息是否存在
    NewCouponRuleDao dao = DIContainer.getDao(NewCouponRuleDao.class);
    NewCouponRule newCouponRule = dao.load(couponCode);
    if (newCouponRule == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }
    // 判断优惠券信息是否应经被占用
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(PrivateCouponListSearchQuery.SELECT_ORDER_HEADE_BY_DISCOUNT_CODE,
        couponCode, type));

    if (NumUtil.parseLong(obj) > 0) {
      serviceResult.addServiceError(CommunicationServiceErrorContent.NEWCOUPONRULE_USE_ERROR);
      return serviceResult;
    }

    try {
      txMgr.begin(getLoginInfo());

      // 信息删除
      txMgr.delete(newCouponRule);

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public Plan getPlan(String planType, String detailType) {
    PlanDao dao = DIContainer.getDao(PlanDao.class);
    List<Plan> list = dao.findByQuery(CommunicationServiceQuery.GET_PLAN_INFO, planType, detailType);
    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  // soukai add ob 2011/12/21 end

  // 20120113 shen add start
  public NewCouponRule getNewCouponRule(String couponType) {
    Query q = new SimpleQuery(PrivateCouponListSearchQuery.LOAD_NEW_COUPON_RULE_QUERY, couponType);
    return DatabaseUtil.loadAsBean(q, NewCouponRule.class);
  }

  // 20120113 shen add end

  public List<CustomerGroupCampaignHeadLine> getCustomerGroupCampaignList() {
    SimpleQuery query = new SimpleQuery(CustomerGroupCampaignSearchQuery.CAMPAIGN_LIST);
    return DatabaseUtil.loadAsBeanList(query, CustomerGroupCampaignHeadLine.class);
  }

  // 20120716 shen add start
  public List<NewCouponRule> getPublicCouponList() {
    Query query = new SimpleQuery(PrivateCouponListSearchQuery.LOAD_PUBLIC_COUPON_LIST_QUERY, CouponType.COMMON_DISTRIBUTION
        .getValue());
    return DatabaseUtil.loadAsBeanList(query, NewCouponRule.class);
  }

  // 20120716 shen add end
  // 2012/11/20 促销活动 ob add start
  public CampaignInfo getCampaignInfo(String campaignCode) {
    CampaignInfo info = new CampaignInfo();
    CampaignMainDao maindao = DIContainer.getDao(CampaignMainDao.class);
    CampaignConditionDao conditiondao = DIContainer.getDao(CampaignConditionDao.class);
    CampaignDoingsDao doingsdao = DIContainer.getDao(CampaignDoingsDao.class);
    info.setCampaignMain(maindao.load(campaignCode));
    info.setConditionList(conditiondao.findByQuery(CampainQuery.GET_CAMPAIGN_CONDITION_BY_CAMPAIGN_CODE, campaignCode));
    info.setCampaignDoings(doingsdao.load(campaignCode));
    return info;
  }

  public List<CampaignCondition> getSaleOffCampaignByCommodityCode(String commodityCode) {
    if (StringUtil.isNullOrEmpty(commodityCode)) {
      return new ArrayList<CampaignCondition>();
    }
    List<CampaignCondition> campaignConditionList = new ArrayList<CampaignCondition>();

    CampaignMainDao camMainDao = DIContainer.getDao(CampaignMainDao.class);
    CampaignConditionDao camConditionDao = DIContainer.getDao(CampaignConditionDao.class);
    List<CampaignCondition> camConditionList = camConditionDao.loadAll();
    for (CampaignCondition condition : camConditionList) {
      // 先从条件表找特定商品列表包含了该商品的记录
      String assCommodityList = condition.getAttributrValue();
      String[] tmpList = assCommodityList.split(",");
      boolean bExist = false;
      for (String tmp : tmpList) {
        if (commodityCode.equals(tmp)) {
          bExist = true;
          break;
        }
      }
      // 根据促销编号从促销表查找该促销活动是否在进行中
      if (bExist) {
        CampaignMain camMain = camMainDao.load(condition.getCampaignCode());
        if (DateUtil.isPeriodDate(camMain.getCampaignStartDate(), camMain.getCampaignEndDate())
            && CampaignMainType.SALE_OFF.longValue().equals(camMain.getCampaignType())) {
          campaignConditionList.add(condition);
        }
      }
    }
    return campaignConditionList;
  }

  public boolean isCampaignExistCommodit(String shopCode, String commodityCode, String campaignCode) {
    if (StringUtil.isNullOrEmptyAnyOf(shopCode, commodityCode, campaignCode)) {
      return false;
    }

    CampaignConditionDao camConditionDao = DIContainer.getDao(CampaignConditionDao.class);
    CampaignCondition condition = camConditionDao.load(campaignCode);
    if (null == condition) {
      return false;
    }

    String assCommodityList = condition.getAttributrValue();
    String[] tmpList = assCommodityList.split(",");
    boolean bExist = false;
    for (String tmp : tmpList) {
      if (commodityCode.equals(tmp)) {
        bExist = true;
        break;
      }
    }

    return bExist;
  }

  public CampaignMain getGiftCampaignMainByGiftCode(String giftCode) {
    if (StringUtil.isNullOrEmpty(giftCode)) {
      return new CampaignMain();
    }
    CampaignMainDao camMainDao = DIContainer.getDao(CampaignMainDao.class);
    CampaignDoingsDao doingDao = DIContainer.getDao(CampaignDoingsDao.class);
    List<CampaignDoings> camDoingList = doingDao.loadAll();
    CampaignMain camMain = new CampaignMain();
    for (CampaignDoings doingInfo : camDoingList) {
      // 从行为表的所有特定商品活动里找到赠品所在的那条记录
      if (CampaignMainType.GIFT.longValue().equals(doingInfo.getCampaignType())) {
        String assCommodityList = doingInfo.getAttributrValue();
        String[] tmpList = assCommodityList.split(",");

        boolean bExist = false;
        for (String tmp : tmpList) {
          if (giftCode.equals(tmp)) {
            bExist = true;
            break;
          }
        }
        // 如果找到了,根据活动编号找到促销信息
        if (bExist) {
          camMain = camMainDao.load(doingInfo.getCampaignCode());
          break;
        }
      }
    }
    return camMain;
  }

  public List<CampaignInfo> getMultipleGiftCampaignInfo(Date date, BigDecimal orderAmount, String advertCode) {
    List<CampaignInfo> infoList = new ArrayList<CampaignInfo>();
    CampaignMainDao maindao = DIContainer.getDao(CampaignMainDao.class);
    CampaignConditionDao conditiondao = DIContainer.getDao(CampaignConditionDao.class);
    CampaignDoingsDao doingsdao = DIContainer.getDao(CampaignDoingsDao.class);
    List<CampaignMain> campaignList = new ArrayList<CampaignMain>();
    if (StringUtil.hasValue(advertCode)) {
      campaignList.addAll(maindao.findByQuery(CampainQuery.GET_MULTIPLE_GIFT_CAMPAIGN_SQL1, date, orderAmount, advertCode));
    } else {
      campaignList.addAll(maindao.findByQuery(CampainQuery.GET_MULTIPLE_GIFT_CAMPAIGN_SQL2, date, orderAmount));
    }
    for (CampaignMain main : campaignList) {
      CampaignInfo info = new CampaignInfo();
      info.setCampaignMain(main);
      List<CampaignCondition> conditionList = new ArrayList<CampaignCondition>();
      conditionList.add(conditiondao.load(main.getCampaignCode()));
      info.setConditionList(conditionList);
      info.setCampaignDoings(doingsdao.load(main.getCampaignCode()));
      infoList.add(info);
    }
    return infoList;
  }

  public List<CampaignInfo> getCouponCampaignInfo(Date date) {
    List<CampaignInfo> infoList = new ArrayList<CampaignInfo>();
    CampaignMainDao maindao = DIContainer.getDao(CampaignMainDao.class);
    CampaignConditionDao conditiondao = DIContainer.getDao(CampaignConditionDao.class);
    CampaignDoingsDao doingsdao = DIContainer.getDao(CampaignDoingsDao.class);
    List<CampaignMain> campaignList = new ArrayList<CampaignMain>();

    campaignList.addAll(maindao.findByQuery(CampainQuery.GET_COUPON_CAMPAIGN_SQL, date));

    for (CampaignMain main : campaignList) {
      CampaignInfo info = new CampaignInfo();
      info.setCampaignMain(main);
      List<CampaignCondition> conditionList = new ArrayList<CampaignCondition>();
      conditionList.add(conditiondao.load(main.getCampaignCode()));
      info.setConditionList(conditionList);
      info.setCampaignDoings(doingsdao.load(main.getCampaignCode()));
      infoList.add(info);
    }

    return infoList;

  }

  public List<CampaignCondition> getCampaignConditionByCouponCode(String couponCode) {
    Query query = new SimpleQuery(CampainQuery.GET_COUPON_CAMPAIGN_SQL2, couponCode);
    return DatabaseUtil.loadAsBeanList(query, CampaignCondition.class);
  }

  // 2012/11/20 促销活动 ob add end

  // 增加促销活动
  // 20121118 促销对应 ob add start
  public ServiceResult saveCampagin(CampaignLine campaignLine, boolean conditionFlg, boolean doingsFlg) {
    ServiceResultImpl result = new ServiceResultImpl();
    CampaignMainDao dao = DIContainer.getDao(CampaignMainDao.class);
    // 重複エラーチェック
    if (dao.load(campaignLine.getCampaignMain().getCampaignCode()) != null) {
      result.addServiceError(CommunicationServiceErrorContent.DUPLICATED_CODE_ERROR);
    }

    if (result.hasError()) {
      return result;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      CampaignMain campaignmain = campaignLine.getCampaignMain();
      CampaignDoings campaignDoings = campaignLine.getCampaignDoings();

      setUserStatus(campaignmain);
      txMgr.insert(campaignmain);

      // 条件表
      if (conditionFlg) {
        List<CampaignCondition> list = campaignLine.getConditionList();
        if (list != null && list.size() > 0) {
          for (int i = 0; i < list.size(); i++) {
            CampaignCondition campaignCondition = list.get(i);
            setUserStatus(campaignCondition);
            txMgr.insert(campaignCondition);
          }
        }
      }
      if (doingsFlg) {
        setUserStatus(campaignDoings);
        txMgr.insert(campaignDoings);
      }

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } catch (RuntimeException e) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public CampaignMain loadCampaignMain(String campaignCode) {
    if (StringUtil.isNullOrEmpty(campaignCode)) {
      return null;
    }
    CampaignMainDao campaignMainDao = DIContainer.getDao(CampaignMainDao.class);
    CampaignMain campaignMain = campaignMainDao.load(campaignCode);
    return campaignMain;
  }

  public CampaignLine loadCampaignLine(String campaignCode) {
    if (StringUtil.isNullOrEmpty(campaignCode)) {
      return null;
    }
    CampaignMainDao campaignMainDao = DIContainer.getDao(CampaignMainDao.class);
    CampaignMain campaignMain = campaignMainDao.load(campaignCode);

    if (campaignMain == null) {
      return null;
    }

    CampaignConditionDao campaignConditionDao = DIContainer.getDao(CampaignConditionDao.class);
    List<CampaignCondition> conditionList = campaignConditionDao.loadList(campaignCode);

    CampaignDoingsDao campaignDoingsDao = DIContainer.getDao(CampaignDoingsDao.class);
    CampaignDoings campaignDoings = campaignDoingsDao.load(campaignCode);

    CampaignLine campaignLine = new CampaignLine();

    campaignLine.setCampaignMain(campaignMain);
    campaignLine.setConditionList(conditionList);
    campaignLine.setCampaignDoings(campaignDoings);

    return campaignLine;
  }

  public List<CampaignDoings> searchGiftCommodtyAll() {
    List<CampaignDoings> list = new ArrayList<CampaignDoings>();
    CampaignDoingsDao campaignDoingsDao = DIContainer.getDao(CampaignDoingsDao.class);
    list = campaignDoingsDao.loadAll();
    if (list != null && list.size() > 0) {
      return list;
    }
    return null;
  }

  public CampaignDoings searchGiftCommodity(String campaignCode) {
    CampaignDoingsDao campaignDoingsDao = DIContainer.getDao(CampaignDoingsDao.class);
    CampaignDoings campaignDoings = campaignDoingsDao.load(campaignCode);
    return campaignDoings;
  }

  public CampaignCondition searchRelatedCommodity(String campaignCode) {
    CampaignConditionDao campaignConditionDao = DIContainer.getDao(CampaignConditionDao.class);
    CampaignCondition campaignCondition = campaignConditionDao.load(campaignCode);
    return campaignCondition;
  }

  public ServiceResult updateCampaignLine(CampaignLine campaignLine, boolean flg, boolean conditionFlg, boolean doingsFlg) {

    ServiceResultImpl result = new ServiceResultImpl();
    CampaignConditionDao campaignConditionDao = DIContainer.getDao(CampaignConditionDao.class);
    CampaignDoingsDao campaignDoingsDao = DIContainer.getDao(CampaignDoingsDao.class);

    List<CampaignCondition> conditionList = campaignConditionDao.loadList(campaignLine.getCampaignMain().getCampaignCode());
    CampaignDoings doings = campaignDoingsDao.load(campaignLine.getCampaignMain().getCampaignCode());

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      CampaignMain campaignmain = campaignLine.getCampaignMain();

      CampaignDoings campaignDoings = campaignLine.getCampaignDoings();

      if (flg) {
        txMgr.update(campaignmain);
      }

      // 条件表
      if (conditionFlg) {
        if (conditionList != null && conditionList.size() > 0) {
          for (int i = 0; i < conditionList.size(); i++) {
            CampaignCondition campaign = conditionList.get(i);
            txMgr.delete(campaign);
          }
        }

        List<CampaignCondition> list = campaignLine.getConditionList();
        if (list != null && list.size() > 0) {
          for (int i = 0; i < list.size(); i++) {
            CampaignCondition campaignCondition = list.get(i);
            setUserStatus(campaignCondition);
            txMgr.insert(campaignCondition);
          }
        }
      }
      if (doingsFlg) {
        txMgr.delete(doings);
        setUserStatus(campaignDoings);
        txMgr.insert(campaignDoings);
      }
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public List<Prefecture> loadAll() {
    PrefectureDao dao = DIContainer.getDao(PrefectureDao.class);
    List<Prefecture> list = dao.loadAll();
    return list;
  }

  public List<GiftCampaign> getAllGiftCampaign(String campaignCode, Date startDateTime, Date endDateTime) {
    Object[] param = new Object[] {
        campaignCode, CampaignMainType.GIFT.getValue(), startDateTime, endDateTime, startDateTime, endDateTime, startDateTime,
        endDateTime
    };
    Query query = new SimpleQuery(NewCampaignQuery.getLoadCampaignResearchQuery(), param);
    List<GiftCampaign> list = DatabaseUtil.loadAsBeanList(query, GiftCampaign.class);
    return list;
  }

  public ServiceResult updateCampaignDoings(CampaignLine campaignLine) {
    ServiceResultImpl result = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.update(campaignLine.getCampaignMain());
      txMgr.update(campaignLine.getCampaignDoings());
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public ServiceResult modifyCampaignLine(CampaignLine campaignLine, boolean conditionFlg, boolean doingsFlg, boolean flg,
      boolean resultFlg, boolean comdityFlg, boolean areaFlg) {

    ServiceResultImpl result = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      CampaignMain campaignmain = campaignLine.getCampaignMain();

      CampaignDoings campaignDoings = campaignLine.getCampaignDoings();

      txMgr.update(campaignmain);

      // 条件表
      if (conditionFlg) {

        List<CampaignCondition> list = campaignLine.getConditionList();
        if (list != null && list.size() > 0) {
          for (int i = 0; i < list.size(); i++) {
            CampaignCondition campaignCondition = list.get(i);
            if (flg && CampaignConditionType.ORDER_COMMODITY.longValue().equals(campaignCondition.getCampaignConditionType())) {
              txMgr.delete(campaignCondition);
            } else if (resultFlg
                && CampaignConditionType.ORDER_ADDRESS.longValue().equals(campaignCondition.getCampaignConditionType())) {
              txMgr.delete(campaignCondition);
            } else if (comdityFlg
                && CampaignConditionType.ORDER_COMMODITY.longValue().equals(campaignCondition.getCampaignConditionType())) {
              setUserStatus(campaignCondition);
              txMgr.insert(campaignCondition);
            } else if (areaFlg
                && CampaignConditionType.ORDER_ADDRESS.longValue().equals(campaignCondition.getCampaignConditionType())) {
              setUserStatus(campaignCondition);
              txMgr.insert(campaignCondition);
            } else {
              txMgr.update(campaignCondition);
            }
          }
        }
      }
      if (doingsFlg) {
        txMgr.update(campaignDoings);
      }

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public ServiceResult updateCampaignCondition(CampaignLine campaignLine, boolean flag) {
    ServiceResultImpl result = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.update(campaignLine.getCampaignMain());

      if (flag) {
        if (campaignLine.getConditionList() != null && campaignLine.getConditionList().size() > 0) {
          for (int i = 0; i < campaignLine.getConditionList().size(); i++) {
            txMgr.update(campaignLine.getConditionList().get(i));
          }
        }

      } else {
        setUserStatus(campaignLine.getConditionList().get(0));
        txMgr.insert(campaignLine.getConditionList().get(0));
      }

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  // 2012/11/20 促销活动 ob add start
  public List<CampaignDoings> getCampaignDoings(String campainCode) {
    List<CampaignDoings> cmList = null;
    Query query = new SimpleQuery(CampainQuery.GET_CAMPAIN_DOING_INFO, campainCode);
    cmList = DatabaseUtil.loadAsBeanList(query, CampaignDoings.class);
    return cmList;
  }

  /**
   * 订单日期间内的赠品优惠活动
   * 
   * @param orderDate
   *          订单日
   * @param commodityCode
   *          关联商品编号
   * @return
   */
  public List<CampaignInfo> getCampaignGiftInfoList(Date orderDate, String commodityCode) {
    List<CampaignInfo> list = new ArrayList<CampaignInfo>();
    Query query = new SimpleQuery(CampainQuery.GET_CAMPAIGN_CODE_SQL, orderDate);
    List<CampaignCondition> listCondition = DatabaseUtil.loadAsBeanList(query, CampaignCondition.class);
    for (CampaignCondition condition : listCondition) {
      List<String> commodityList = Arrays.asList(condition.getAttributrValue().trim().split(","));
      if (commodityList != null && commodityList.contains(commodityCode)) {
        list.add(this.getCampaignInfo(condition.getCampaignCode()));
      }
    }
    return list;
  }

  /**
   * 订单日期间内的折扣券活动
   * 
   * @param orderDate
   *          订单日
   * @param commodityCode
   *          关联商品编号
   * @return
   */
  public boolean getEnableCampaignFlg(String commodityCode, Date orderDate) {
    Query query = new SimpleQuery(CampainQuery.GET_CAMPAIGN_DISCOUNT_SQL, orderDate);
    List<CampaignCondition> listCondition = DatabaseUtil.loadAsBeanList(query, CampaignCondition.class);
    for (CampaignCondition condition : listCondition) {
      List<String> commodityList = Arrays.asList(condition.getAttributrValue().trim().split(","));
      if (commodityList != null && commodityList.contains(commodityCode)) {
        return true;
      }
    }
    return false;
  }

  // 2012/11/20 促销活动 ob add end
  // 2013/04/01 优惠券对应 ob add start
  /**
   * 検索情報によって、クーポンルール情報を取得する
   * 
   * @param condition
   *          検索情報
   * @return クーポンルール情報
   */
  public SearchResult<NewCouponHistoryInfo> getCustomerNewCouponList(MyCouponHistorySearchCondition condition) {
    return DatabaseUtil.executeSearch(new MyCouponHistorySearchQuery(condition));
  }

  /**
   * 有効ポイントを計算する
   */
  public Long getFriendCouponUseHistoryAllPoint(String customerCode) {

    Long goodPoint = -1L;

    FriendCouponExchangeHistoryDao exchangeDao = DIContainer.getDao(FriendCouponExchangeHistoryDao.class);
    FriendCouponUseHistoryDao useDao = DIContainer.getDao(FriendCouponUseHistoryDao.class);

    if (getAllPoint(customerCode) != null && exchangeDao.getAllExchangePoint(customerCode) != null) {
      // 有効ポイント計算
      goodPoint = useDao.getAllPoint(customerCode) - exchangeDao.getAllExchangePoint(customerCode) + useDao.getIssueObtainPoint(customerCode);
    }

    if (goodPoint >= 0L) {
      return goodPoint;
    }
    return 0L;
  }

  /**
   *　成績紹介一覧表検索
   */
  public SearchResult<FriendCouponLine> getFriendCouponLine(FriendCouponSearchCondition condition) {
    FriendCouponQuery query = new FriendCouponQuery(condition);
    SearchResult<FriendCouponLine> result = DatabaseUtil.executeSearch(query);
    return result;
  }

  /**
   * 両替可能のクーポンを取得
   */
  public List<FriendCouponUseLine> getFriendCouponUseLine() {
    List<FriendCouponUseLine> friendCouponUse = new ArrayList<FriendCouponUseLine>();
    Query q = new SimpleQuery(FriendCouponQuery.GET_FRIEND_COUPON_USE, DateUtil.getSysdate());
    friendCouponUse = DatabaseUtil.loadAsBeanList(q, FriendCouponUseLine.class);
    return friendCouponUse;
  }

  /**
   * クーポンルールコードを指定してクーポンルールデータを検索する
   */
  public NewCouponRule getNewCouponRuleByCouponCode(String couponCode) {
    NewCouponRuleDao NewDao = DIContainer.getDao(NewCouponRuleDao.class);
    NewCouponRule result = NewDao.load(couponCode);
    return result;
  }

  /**
   * クーポンルールコードを指定してクーポン使用履歴データを検索する
   */
  public FriendCouponUseHistory getFriendCouponUseHistory(String couponCode) {
    FriendCouponUseHistoryDao FcDao = DIContainer.getDao(FriendCouponUseHistoryDao.class);
    FriendCouponUseHistory result = FcDao.loadByCouponCode(couponCode);
    return result;
  }

  /**
   * 配送クーポン履歴を登録
   * 
   * @param newCouponDto
   *          配送履歴情報
   * @return サービス実行結果
   */
  public ServiceResult insertNewCouponHistory(NewCouponHistory newCouponHistoryDto) {
    ServiceResultImpl result = new ServiceResultImpl();
    // DTOデータチェック
    List<ValidationResult> resultNewCouponList = BeanValidator.validate(newCouponHistoryDto).getErrors();

    if (resultNewCouponList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultNewCouponList) {
        logger.debug(rs.getFormedMessage());
      }
      return result;
    }

    setUserStatus(newCouponHistoryDto);
    NewCouponHistoryDao newCouponHistoryDao = DIContainer.getDao(NewCouponHistoryDao.class);
    NewCouponHistory newCouponHistoryChk = newCouponHistoryDao.load(newCouponHistoryDto.getCouponIssueNo());

    // 优惠券编号重複設定する
    while (newCouponHistoryChk != null) {
      getCouponIssueNo(newCouponHistoryDto);
      newCouponHistoryChk = newCouponHistoryDao.load(newCouponHistoryDto.getCouponIssueNo());
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(newCouponHistoryDto);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  /**
   * 履歴を登録
   */
  public ServiceResult insertFriendHistory(NewCouponHistory newCouponDto, FriendCouponExchangeHistory fcehDto) {
    ServiceResultImpl result = new ServiceResultImpl();

    // DTOデータチェック
    List<ValidationResult> resultNewCouponList = BeanValidator.validate(newCouponDto).getErrors();
    List<ValidationResult> resultfceList = BeanValidator.validate(fcehDto).getErrors();
    if (resultNewCouponList.size() > 0 || resultfceList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultNewCouponList) {
        logger.debug(rs.getFormedMessage());
      }
      for (ValidationResult rs : resultfceList) {
        logger.debug(rs.getFormedMessage());
      }
      return result;
    }

    setUserStatus(fcehDto);
    FriendCouponExchangeHistoryDao fcDao = DIContainer.getDao(FriendCouponExchangeHistoryDao.class);
    FriendCouponExchangeHistory fcDto = fcDao.load(fcehDto.getCouponIssueNo());

    setUserStatus(newCouponDto);
    NewCouponHistoryDao NcDao = DIContainer.getDao(NewCouponHistoryDao.class);
    NewCouponHistory ncDto = NcDao.load(newCouponDto.getCouponIssueNo());

    // 重複チェック
    if (fcDto != null || ncDto != null) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(fcehDto);
      txMgr.insert(newCouponDto);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public void getCouponIssueNo(NewCouponHistory newCouponDto) {
    // 0~9999の乱数を作成する
    Long number = Math.round(Math.random() * 8999 + 1000);
    // 把生成的随机数填充成四位数
    String num = number.toString();
    // クーポン明細コード
    int code = 0;

    List<NewCouponHistory> list = DatabaseUtil.loadAsBeanList(new SimpleQuery(
        CommunicationServiceQuery.GET_MAIL_COUPON_ISSUE_DETAIL_NO), NewCouponHistory.class);
    if (list != null && list.size() > 0) {
      code = Integer.parseInt(list.get(0).getCouponIssueDetailNo());
    }

    String couponIssueDetailNo = String.valueOf(code + 1);

    // 优惠券编号
    String couponIssueNo = newCouponDto.getCouponCode() + num + StringUtil.toFormatString(couponIssueDetailNo, 6, false, "0");

    newCouponDto.setCouponIssueNo(couponIssueNo);
    newCouponDto.setCouponIssueDetailNo(couponIssueDetailNo);
  }

  public List<MyCouponInfo> getMyCoupon(String customerCode) {
    Query query = new SimpleQuery(CommunicationServiceQuery.LOAD_PRIVATE_COUPON_BY_CUSTOMER_CODE_QUERY, customerCode);
    return DatabaseUtil.loadAsBeanList(query, MyCouponInfo.class);
  }

  public MyCouponInfo getMyCoupon(String customerCode, String couponIssueNo) {
    Query query = new SimpleQuery(CommunicationServiceQuery.LOAD_NEW_COUPON_HISTORY_QUERY, customerCode, couponIssueNo);
    return DatabaseUtil.loadAsBean(query, MyCouponInfo.class);
  }

  public List<NewCouponHistoryUseInfo> getUseCommodityList(String couponIssueCode) {
    Query query = new SimpleQuery(CommunicationServiceQuery.LOAD_USE_COMMODITY_LIST_QUERY, couponIssueCode);
    return DatabaseUtil.loadAsBeanList(query, NewCouponHistoryUseInfo.class);
  }

  public List<NewCouponHistoryUseInfo> getBrandCodeList(String couponIssueCode) {
    Query query = new SimpleQuery(CommunicationServiceQuery.LOAD_BRAND_LIST_QUERY, couponIssueCode);
    return DatabaseUtil.loadAsBeanList(query, NewCouponHistoryUseInfo.class);
  }

  public List<NewCouponHistoryUseInfo> getImportCommodityTypeList(String couponIssueCode) {
    Query query = new SimpleQuery(CommunicationServiceQuery.LOAD_IMPORT_COMMODITY_TYPE_LIST_QUERY, couponIssueCode);
    return DatabaseUtil.loadAsBeanList(query, NewCouponHistoryUseInfo.class);
  }

  public String getBrandCode(String commodityCode) {
    Query query = new SimpleQuery(CommunicationServiceQuery.GET_BRAND_CODE_QUERY, commodityCode);
    return (String) DatabaseUtil.executeScalar(query);
  }

  public boolean issuedBySelf(String couponCode, String customerCode) {
    FriendCouponIssueHistoryDao dao = DIContainer.getDao(FriendCouponIssueHistoryDao.class);
    return dao.exists(couponCode, customerCode);
  }

  // 通过顾客编号取得发货信息
  public List<ShippingHeader> getShippingHeaderList(String customerCode) {
    ShippingHeaderDao shippingHeaderDao = DIContainer.getDao(ShippingHeaderDao.class);
    List<ShippingHeader> result = shippingHeaderDao.findByQuery(CommunicationServiceQuery.SHIPPING_HEADER_QUERY, customerCode);
    return result;
  }

  // 取得朋友介绍优惠券使用规则信息
  public List<FriendCouponRule> getFriendCouponRuleList() {
    FriendCouponRuleDao friendCouponRuleDao = DIContainer.getDao(FriendCouponRuleDao.class);
    List<FriendCouponRule> result = friendCouponRuleDao.loadAll();
    return result;
  }

  public FriendCouponRule getFriendCouponRule(String friendCouponRuleNo) {
    FriendCouponRuleDao friendCouponRuleDao = DIContainer.getDao(FriendCouponRuleDao.class);
    FriendCouponRule result = friendCouponRuleDao.load(friendCouponRuleNo);
    return result;
  }

  public FriendCouponUse getFriendCouponUse(String couponCode) {
    FriendCouponUse result = new FriendCouponUse();
    Query query = new SimpleQuery(FriendCouponListQuery.FRIEND_COUPON_USE_QUERY, couponCode);
    result = DatabaseUtil.loadAsBean(query, FriendCouponUse.class);
    return result;
  }

  // public List<FriendCouponList> getFriendCouponList(String couponCode) {
  // List<FriendCouponList> list = new ArrayList<FriendCouponList>();
  // Query query = new
  // SimpleQuery(FriendCouponListQuery.FRIEND_COUPON_LIST_QUERY,couponCode);
  // list = DatabaseUtil.loadAsBeanList(query, FriendCouponList.class);
  // return list;
  // }

  public List<FriendCouponRule> getUnissuedFriendCouponList(String customerCode) {
    List<FriendCouponRule> list = new ArrayList<FriendCouponRule>();
    Query query = new SimpleQuery(FriendCouponListQuery.GET_UNISSUED_FRIEND_COUPON_LIST_QUERY, customerCode);
    list = DatabaseUtil.loadAsBeanList(query, FriendCouponRule.class);
    return list;
  }

  public List<NewCouponRule> getIssuedFriendCouponList(String customerCode) {
    List<NewCouponRule> list = new ArrayList<NewCouponRule>();
    Query query = new SimpleQuery(FriendCouponListQuery.GET_ISSUED_FRIEND_COUPON_LIST_QUERY, customerCode);
    list = DatabaseUtil.loadAsBeanList(query, NewCouponRule.class);
    return list;
  }

  public List<FriendCouponRule> getFriendCouponList() {
    List<FriendCouponRule> list = new ArrayList<FriendCouponRule>();
    Query query = new SimpleQuery(FriendCouponListQuery.GET_FRIEND_COUPON_LIST_QUERY);
    list = DatabaseUtil.loadAsBeanList(query, FriendCouponRule.class);
    return list;
  }

  // 发行优惠券
  public ServiceResult insertCouponFriend(FriendCouponIssueHistory historyDto, NewCouponRule couponDto) {
    ServiceResultImpl result = new ServiceResultImpl();

    ValidationSummary validateFriendCouponIssueHistory = BeanValidator.validate(historyDto);
    if (validateFriendCouponIssueHistory.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateFriendCouponIssueHistory.getErrorMessages()) {
        logger.debug(rs);
      }
      return result;
    }

    ValidationSummary validateNewCouponRule = BeanValidator.validate(couponDto);
    if (validateNewCouponRule.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateNewCouponRule.getErrorMessages()) {
        logger.debug(rs);
      }
      return result;
    }

    setUserStatus(historyDto);
    setUserStatus(couponDto);
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(historyDto);
      txMgr.insert(couponDto);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  /**
   * 両替可能のクーポンを取得
   */
  public static Long getAllPoint(String customerCode) {
    String query = "SELECT SUM(B.POINT) " + "FROM FRIEND_COUPON_ISSUE_HISTORY A "
        + "INNER JOIN FRIEND_COUPON_USE_HISTORY B ON A.COUPON_CODE = B.COUPON_CODE " + "WHERE A.CUSTOMER_CODE = ? "
        + "AND B.POINT_STATUS = " + "'" + CouponStatus.USED.getValue() + "'";

    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(query, customerCode));
    if (obj != null) {
      return ((Number) obj).longValue();
    }
    return 0L;

  }

  // 2013/4/1 优惠券对应 ob add end
  // 2013/4/11 优惠券对应 ob add start
  /**
   * 优惠券规则信息判定
   * 
   * @param couponCode
   *          优惠券编号
   * @param orderNo
   *          订单编号
   * @reutn 符合条件:true,不符合条件:false
   */
  public boolean isIssueDisable(String couponCode, String orderNo) {
    boolean isIssueDisale = false;
    NewCouponRule couponRule = null;
    NewCouponRuleLssueInfoDao ruleLssueInfoDao = DIContainer.getDao(NewCouponRuleLssueInfoDao.class);
    // 优惠券规则DTO
    NewCouponRuleDao newCouponRuleDao = DIContainer.getDao(NewCouponRuleDao.class);

    // 根据(优惠券规则编号)查询优惠券规则
    couponRule = newCouponRuleDao.load(couponCode);

    // 优惠券规则_发行关联信息管理商品取得
    List<NewCouponRuleLssueInfo> couponHistoryList = ruleLssueInfoDao.findByQuery(new SimpleQuery(
        MyCouponHistorySearchQuery.GET_COMMODITY_CODE_BY_LSSUE, couponCode));
    // 指定商品
    List<OrderDetail> couponOrderDetailList = new ArrayList<OrderDetail>(0);
    // 全部商品
    List<OrderDetail> couponOrderDetailAllList = new ArrayList<OrderDetail>(0);
    // 受注管理サービスを取得します。
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());

    BigDecimal totalPrice = BigDecimal.ZERO;
    // 取得指定商品
    for (NewCouponRuleLssueInfo ruleLssueInfo : couponHistoryList) {
      // 取得商品详细信息
      List<OrderDetail> orderDetailList = orderService.getOrderDetailList(orderNo);
      for (OrderDetail detail : orderDetailList) {
        if (ruleLssueInfo.getCommodityCode().equals(detail.getCommodityCode())) {
          couponOrderDetailList.add(detail);
          totalPrice = totalPrice.add(BigDecimalUtil.multiply(detail.getRetailPrice(), detail.getPurchasingAmount()));
        }
        couponOrderDetailAllList.add(detail);
      }
    }

    // 优惠券利用最小购买金额判定
    if (BigDecimalUtil.isAboveOrEquals(totalPrice, couponRule.getMinUseOrderAmount())) {
      isIssueDisale = true;
    } else {
      isIssueDisale = false;
    }

    // 优惠券利用时间有效性Check
    if (isIssueDisale && DateUtil.isPeriodDate(couponRule.getMinUseStartDatetime(), couponRule.getMinUseEndDatetime())) {
      isIssueDisale = true;
    } else {
      isIssueDisale = false;
    }
    if (!isIssueDisale) {
      return isIssueDisale;
    }
    // 指定商品发行优惠券 1：指定
    if (RelatedCommodityFlg.HAVE.getValue().equals(couponRule.getRelatedCommodityFlg())) {
      if (couponOrderDetailList.size() < 1) {
        isIssueDisale = false;
      } else {
        isIssueDisale = true;
      }
    } else {// 指定商品发行优惠券0：无指定
      if (couponOrderDetailList.size() < 1) {
        isIssueDisale = false;
      } else {
        isIssueDisale = true;
      }
    }
    return isIssueDisale;
  }

  /**
   * 有无指定商品
   * 
   * @param couponCode
   *          优惠券编号
   * @param orderNo
   *          订单编号
   * @return true:有指定商品 false:无指定商品
   */
  public boolean designationCoupon(String couponCode, String orderNo) {
    NewCouponRuleLssueInfoDao ruleLssueInfoDao = DIContainer.getDao(NewCouponRuleLssueInfoDao.class);
    // 优惠券规则_发行关联信息管理商品取得
    List<NewCouponRuleLssueInfo> couponHistoryList = ruleLssueInfoDao.findByQuery(new SimpleQuery(
        MyCouponHistorySearchQuery.GET_COMMODITY_CODE_BY_LSSUE, couponCode));
    // 受注管理サービスを取得します。
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    // 全部商品
    List<OrderDetail> couponOrderDetailList = new ArrayList<OrderDetail>(0);
    // 取得商品详细信息
    List<OrderDetail> orderDetailList = orderService.getOrderDetailList(orderNo);
    // 取得指定商品
    for (NewCouponRuleLssueInfo ruleLssueInfo : couponHistoryList) {
      for (OrderDetail detail : orderDetailList) {
        if (ruleLssueInfo.getCommodityCode().equals(detail.getCommodityCode())) {
          couponOrderDetailList.add(detail);
        }
      }
    }

    // 是否含有指定商品判定
    if (couponOrderDetailList.size() > 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 部分退货后，无指定商品
   * 
   * @param couponCode
   *          优惠券编号
   * @param orderNo
   *          订单编号
   * @return true:有指定商品 false:无指定商品
   */
  public boolean designationCommodity(String couponCode, String orderNo) {
    // 受注管理サービスを取得します。
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    OrderContainer orderContainer = orderService.getOrder(orderNo);
    // 商品信息
    List<OrderDetail> detailAllList = orderContainer.getOrderDetails();
    // 所有商品信息
    List<OrderDetail> orderDetailCommdityList = new ArrayList<OrderDetail>(0);
    // 返品商品详细信息
    List<ShippingDetail> shippingCommdityList = new ArrayList<ShippingDetail>(0);

    // 返品商品详细信息取得
    for (ShippingContainer shippingContainer : orderContainer.getShippings()) {
      // 返品データの場合、次の出荷情報へ
      if (shippingContainer.getShippingHeader().getReturnItemType().equals(ReturnItemType.RETURNED.longValue())) {
        for (ShippingDetail shippingDetailInfo : shippingContainer.getShippingDetails()) {
          shippingCommdityList.add(shippingDetailInfo);
        }
      }
    }

    // 取得部分退货后的商品
    OrderDetail orderDetail = new OrderDetail();
    for (ShippingDetail shippingDetail : shippingCommdityList) {
      boolean hasCommodity = false;
      for (int i = 0; i < detailAllList.size(); i++) {
        orderDetail = detailAllList.get(0);
        if (orderDetail.getSkuCode().equals(shippingDetail.getSkuCode())) {
          hasCommodity = true;
          break;
        }
        if (!hasCommodity) {
          orderDetailCommdityList.add(orderDetail);
        }
      }
    }

    NewCouponRuleLssueInfoDao ruleLssueInfoDao = DIContainer.getDao(NewCouponRuleLssueInfoDao.class);
    // 优惠券规则_发行关联信息管理商品取得
    List<NewCouponRuleLssueInfo> couponHistoryList = ruleLssueInfoDao.findByQuery(new SimpleQuery(
        MyCouponHistorySearchQuery.GET_COMMODITY_CODE_BY_LSSUE, couponCode));
    // 全部商品
    List<OrderDetail> couponOrderDetailList = new ArrayList<OrderDetail>(0);

    // 取得指定商品
    for (NewCouponRuleLssueInfo ruleLssueInfo : couponHistoryList) {
      for (OrderDetail detail : orderDetailCommdityList) {
        if (ruleLssueInfo.getCommodityCode().equals(detail.getCommodityCode())) {
          couponOrderDetailList.add(detail);
        }
      }
    }

    // 是否含有指定商品判定
    if (couponOrderDetailList.size() > 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 优惠券数据更新
   * 
   * @param newCouponHistory
   *          优惠券发行履历
   * @return
   */
  public ServiceResult updateNewCouponHistory(NewCouponHistory newCouponHistory) {
    ServiceResultImpl result = new ServiceResultImpl();
    // クーポンステータス
    String couponStatus = "";
    // 返品状況を取得する
    String returnStatus = "";
    // 受注ステータス
    String orderStatus = "";
    // 受注日時
    // Date orderDatetime = null;

    TransactionManager txMgr = DIContainer.getTransactionManager();
    // 受注管理サービスを取得します。
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    OrderListSearchCondition condition = new OrderListSearchCondition();
    condition.setOrderNoStart(newCouponHistory.getIssueOrderNo());
    condition.setOrderNoEnd(newCouponHistory.getIssueOrderNo());

    String[] orderStatusArray = new String[] {
      OrderStatus.ORDERED.getValue()
    };
    condition.setOrderStatus(orderStatusArray);

    String[] shippingStatusSummaryArray = new String[] {
        ShippingStatusSummary.CANCELLED.getValue(), ShippingStatusSummary.IN_PROCESSING.getValue(),
        ShippingStatusSummary.NOT_SHIPPED.getValue(), ShippingStatusSummary.PARTIAL_SHIPPED.getValue(),
        ShippingStatusSummary.SHIPPED_ALL.getValue()
    };
    condition.setShippingStatusSummary(shippingStatusSummaryArray);

    String[] returnStatusSummaryArray = new String[] {
        ReturnStatusSummary.NOT_RETURNED.getValue(), ReturnStatusSummary.PARTIAL_RETURNED.getValue(),
        ReturnStatusSummary.RETURNED_ALL.getValue()
    };
    condition.setReturnStatusSummary(returnStatusSummaryArray);

    condition.setShopCode("00000000");
    // 受注 情報を取得する
    SearchResult<OrderHeadline> orderResult = orderService.searchOrderList(condition);
    if (orderResult != null && orderResult.getRowCount() > 0) {
      // 返品状況を取得する
      returnStatus = orderResult.getRows().get(0).getReturnStatusSummary();
      // 受注ステータス
      orderStatus = orderResult.getRows().get(0).getOrderStatus();
      // 受注日時
      // orderDatetime =
      // DateUtil.parseString(orderResult.getRows().get(0).getOrderDatetime(),DateUtil.DEFAULT_DATETIME_FORMAT);

      List<ShippingContainer> shippingList = orderService.getShippingList(newCouponHistory.getIssueOrderNo());

      if (shippingList == null || shippingList.size() <= 0) {
        return result;
      }
      // 出荷日
      Date shippingDate = shippingList.get(0).getShippingHeader().getShippingDate();

      // 受注ステータスがキャンセル　または　出荷ステータスが未出荷
      if (OrderStatus.CANCELLED.getValue().equals(orderStatus) || shippingDate == null) {
        couponStatus = CouponStatus.CANCEL.getValue();

        // 已发货/无退货
      } else if (shippingDate != null && ReturnStatusSummary.NOT_RETURNED.getValue().equals(returnStatus)) {
        couponStatus = CouponStatus.USED.getValue();

        // 部分退货处理
      } else if (!ReturnStatusSummary.NOT_RETURNED.getValue().equals(returnStatus)) {
        // 有设定指定商品
        if (designationCoupon(newCouponHistory.getCouponCode(), newCouponHistory.getIssueOrderNo())) {
          // 部分退货后，
          if (ReturnStatusSummary.PARTIAL_RETURNED.getValue().equals(returnStatus)) {
            // 无指定商品 0：临时 -> 2：取消
            if (!designationCommodity(newCouponHistory.getCouponCode(), newCouponHistory.getIssueOrderNo())) {
              couponStatus = CouponStatus.CANCEL.getValue();
              // 仍含指定商品0：临时 -> 1：有效
            } else {
              couponStatus = CouponStatus.USED.getValue();
            }
            // 全部退货0：临时 -> 2：取消
          } else if (ReturnStatusSummary.RETURNED_ALL.getValue().equals(returnStatus)) {
            couponStatus = CouponStatus.CANCEL.getValue();
          }
          // 无设定指定商品
        } else {
          if (ReturnStatusSummary.PARTIAL_RETURNED.getValue().equals(returnStatus)) {
            couponStatus = CouponStatus.USED.getValue();
          } else if (ReturnStatusSummary.RETURNED_ALL.getValue().equals(returnStatus)) {
            couponStatus = CouponStatus.CANCEL.getValue();
          }
        }
      }
    }
    if (!StringUtil.hasValue(couponStatus)) {
      return result;
    }
    // 优惠券发行履历信息
    NewCouponHistoryDao newCouponHistoryDao = DIContainer.getDao(NewCouponHistoryDao.class);
    NewCouponHistory newCouponHistoryDto = newCouponHistoryDao.load(newCouponHistory.getCouponIssueNo());

    // 优惠券规则表
    // NewCouponRuleDao newCouponRuleDao =
    // DIContainer.getDao(NewCouponRuleDao.class);
    // NewCouponRule newCouponRule =
    // newCouponRuleDao.load(newCouponHistory.getCouponCode());
    try {
      txMgr.begin(getLoginInfo());
      newCouponHistoryDto.setCouponStatus(NumUtil.toLong(couponStatus));
      // //优惠券状态被设定为1：有效
      // if (CouponStatus.USED.getValue().equals(couponStatus)) {
      // // 优惠券利用开始日时
      // if (newCouponRule.getMinUseStartNum() != null) {
      // newCouponHistoryDto.setUseStartDatetime(DateUtil.addDate(orderDatetime,
      // newCouponRule.getMinUseStartNum().intValue() - 1));
      // }
      //        
      // // 优惠券利用结束日时
      // if (newCouponRule.getMinUseStartNum() != null &&
      // newCouponRule.getMinUseEndNum() != null) {
      // newCouponHistoryDto.setUseEndDatetime(DateUtil.addDate(newCouponHistoryDto.getUseStartDatetime(),
      // newCouponRule.getMinUseEndNum().intValue()-1));
      // }
      // }
      txMgr.update(newCouponHistoryDto);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  @Override
  public List<Customer> getCustomerListByBirthday() {
    // 今は「何月」を取得する
    String month = DateUtil.getMM(DateUtil.getSysdate());
    List<Customer> customerList = new ArrayList<Customer>();
    Query qCustomer = new SimpleQuery("SELECT DISTINCT C.CUSTOMER_CODE  FROM CUSTOMER C,SHIPPING_HEADER S WHERE C.CUSTOMER_CODE=S.CUSTOMER_CODE "
        + "AND TO_CHAR(BIRTH_DATE,'MM') = ? AND TO_CHAR(BIRTH_DATE,'YYYY-MM-DD') <> '3000-01-01' " 
        +	"AND CUSTOMER_STATUS <> ? " + "AND (S.SHIPPING_STATUS = 2 OR S.SHIPPING_STATUS = 3)",
        month, CustomerStatus.WITHDRAWED.getValue());
    customerList = DatabaseUtil.loadAsBeanList(qCustomer, Customer.class);
    return customerList;
  }

  // 誕生日クーポン情報取得
  public NewCouponRule getNewCouponRule() {
    NewCouponRule newCouponRule = new NewCouponRule();
    Query qRule = new SimpleQuery("SELECT * FROM NEW_COUPON_RULE " + "WHERE  COUPON_TYPE = ? "
        + "AND    ? BETWEEN MIN_ISSUE_START_DATETIME AND MIN_ISSUE_END_DATETIME ", CouponType.BIRTHDAY_DISTRIBUTION.getValue(),
        DateUtil.getSysdate());
    newCouponRule = DatabaseUtil.loadAsBean(qRule, NewCouponRule.class);
    return newCouponRule;
  }

  // 2013/4/11 优惠券对应 ob add end

  // 2013/4/19 朋友推荐优惠券 zhangzhengtao add start

  // @Override
  // public ServiceResult addFriendCouponRule(FriendCouponRule rule) {
  // ServiceResultImpl result = new ServiceResultImpl();
  // FriendCouponRuleDao dao = DIContainer.getDao(FriendCouponRuleDao.class);
  // dao.insert(rule);
  // return result;
  // }
  //
  @Override
  public ServiceResult deleteFriendCouponRule(String id) {
    ServiceResultImpl result = new ServiceResultImpl();
    FriendCouponRuleDao dao = DIContainer.getDao(FriendCouponRuleDao.class);
    dao.delete(id);
    return result;
  }

  public ServiceResult addFriendCouponRule(FriendCouponRule rule) {
    FriendCouponRuleDao dao = DIContainer.getDao(FriendCouponRuleDao.class);

    // お知らせ情報のSEQを取得
    setUserStatus(rule);

    ServiceResultImpl serviceResult = new ServiceResultImpl();
    List<ValidationResult> resultList = BeanValidator.validate(rule).getErrors();
    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }
    dao.insert(rule, getLoginInfo());

    return serviceResult;
  }

  @Override
  public FriendCouponRule selectFriendCouponRule(String id) {
    FriendCouponRuleDao dao = DIContainer.getDao(FriendCouponRuleDao.class);
    return dao.load(id);
  }

  @Override
  public ServiceResult updateFriendCouponRule(FriendCouponRule rule) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    FriendCouponRuleDao dao = DIContainer.getDao(FriendCouponRuleDao.class);
    FriendCouponRule friendCouponRule = dao.load(rule.getFriendCouponRuleNo());

    // お知らせ情報未登録エラー
    if (friendCouponRule == null) {
      serviceResult.addServiceError(SiteManagementServiceErrorContent.NO_INFORMATION_DATE_ERROR);
      return serviceResult;
    }

    // データのコピーを行う
    rule.setOrmRowid(friendCouponRule.getOrmRowid());
    rule.setCreatedDatetime(friendCouponRule.getCreatedDatetime());
    rule.setCreatedUser(friendCouponRule.getCreatedUser());
    rule.setUpdatedUser(friendCouponRule.getUpdatedUser());
    rule.setUpdatedDatetime(friendCouponRule.getUpdatedDatetime());

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(rule).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    dao.update(rule, getLoginInfo());

    return serviceResult;
  }

  @Override
  public SearchResult<FriendCouponRule> selectAllFriendCouponRule(FriendCouponRuleCondition condition) {
    FriendCouponRuleQuery query = new FriendCouponRuleQuery(condition);
    return DatabaseUtil.executeSearch(query);
  }

  // 2013/4/19 朋友推荐优惠券 zhangzhengtao add end

  public List<PlanDetail> getPlanDetailList(String planCode) {
    PlanDetailDao dao = DIContainer.getDao(PlanDetailDao.class);
    return dao.findByQuery(CommunicationServiceQuery.GET_PLAN_DETAIL_LIST_BY_PLAN_CODE_QUERY, planCode);
  }

  public List<PlanCommodity> getPlanCommodityList(String planCode) {
    PlanCommodityDao dao = DIContainer.getDao(PlanCommodityDao.class);
    return dao.findByQuery(CommunicationServiceQuery.GET_PLAN_COMODITY_LIST_BY_PLAN_CODE_QUERY, planCode);
  }

  public SearchResult<DiscountHeadLine> getDiscountList(DiscountListSearchCondition condition) {

    return DatabaseUtil.executeSearch(new DiscountListSearchQuery(condition));

  }
  // zhangfeng add 2014/4/8 begin
  public SearchResult<MessageHeadLine> getMessageList(CustomerMessageSearchCondition condition) {

    return DatabaseUtil.executeSearch(new CustomerMessageSearchQuery(condition));

  }
  // zhangfeng add 2014/4/8 end


  public ServiceResult deleteMessage(long ormRowid) {

    Logger logger = Logger.getLogger(this.getClass());
    CustomerMessageDao dao = DIContainer.getDao(CustomerMessageDao.class);
    ServiceResultImpl result = new ServiceResultImpl();

    // 削除データ存在チェック

      logger.debug("ormRowid=" + ormRowid + ": was found.");

      CustomerMessage cm = dao.loadByRowid(ormRowid);

      TransactionManager txm = DIContainer.getTransactionManager();
      try {
        txm.begin(this.getLoginInfo());
        txm.executeUpdate(new SimpleQuery(CommunicationServiceQuery.GET_DELETE_CUSTOMER_MESSAGE_BY_ORM_ROWID_QUERY,
            ormRowid));
        // commented by zhangfeng : 
        // txm.executeUpdate(new SimpleQuery(CommunicationServiceQuery.GET_DELETE_DISCOUNT_COMMODITY_BY_DISCOUNT_CODE_QUERY, ormRowid));

        txm.commit();
        logger.debug("success.");
      } catch (RuntimeException e) {
        txm.rollback();
      } finally {
        txm.dispose();
      }
    // Temporarily delete check sentence.
    //} else {
    // result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
    //  logger.debug("discount=" + discountCode + ": was NOT found.");
    // }

    return result;
  }
  
  public ServiceResult deleteDiscount(String discountCode) {

    Logger logger = Logger.getLogger(this.getClass());

    DiscountHeaderDao dao = DIContainer.getDao(DiscountHeaderDao.class);
    ServiceResultImpl result = new ServiceResultImpl();

    // 削除データ存在チェック
    if (dao.exists(discountCode)) {
      logger.debug("discount=" + discountCode + ": was found.");

      DiscountHeader dh = dao.load(discountCode);

      if (DateUtil.isPeriodDate(dh.getDiscountStartDatetime(), dh.getDiscountEndDatetime())) {
        result.addServiceError(CommunicationServiceErrorContent.DELETE_DISCOUNT_ERROR);
        return result;
      }

      TransactionManager txm = DIContainer.getTransactionManager();
      try {
        txm.begin(this.getLoginInfo());

        txm
            .executeUpdate(new SimpleQuery(CommunicationServiceQuery.GET_DELETE_DISCOUNT_HEADER_BY_DISCOUNT_CODE_QUERY,
                discountCode));
        txm.executeUpdate(new SimpleQuery(CommunicationServiceQuery.GET_DELETE_DISCOUNT_COMMODITY_BY_DISCOUNT_CODE_QUERY,
            discountCode));

        txm.commit();
        logger.debug("success.");
      } catch (RuntimeException e) {
        txm.rollback();
      } finally {
        txm.dispose();
      }

    } else {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      logger.debug("discount=" + discountCode + ": was NOT found.");
    }

    return result;
  }

  public DiscountInfo getDiscountInfo(String discountCode) {
    DiscountInfo di = new DiscountInfo();
    DiscountHeaderDao dao = DIContainer.getDao(DiscountHeaderDao.class);
    if (dao.exists(discountCode)) {
      di.setDiscountHeader(dao.load(discountCode));
      Query query = new SimpleQuery(CommunicationServiceQuery.GET_DISCOUNT_DETAIL_BY_DISCOUNT_CODE_QUERY, discountCode);
      di.setDetailList(DatabaseUtil.loadAsBeanList(query, DiscountDetail.class));
    }
    return di;
  }

  public DiscountHeader getDiscountHeader(String discountCode) {
    DiscountHeaderDao dao = DIContainer.getDao(DiscountHeaderDao.class);
    return dao.load(discountCode);
  }

  public ServiceResult insertDiscountHeader(DiscountHeader discountHeader) {
    ServiceResultImpl result = new ServiceResultImpl();

    setUserStatus(discountHeader);

    DiscountHeaderDao dao = DIContainer.getDao(DiscountHeaderDao.class);

    // 重复判断
    if (dao.exists(discountHeader.getDiscountCode())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }

    ValidationSummary validateCustomer = BeanValidator.validate(discountHeader);
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      return result;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());

      txMgr.insert(discountHeader);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public ServiceResult insertDiscountCommodity(DiscountCommodity discountCommodity) {
    ServiceResultImpl result = new ServiceResultImpl();

    setUserStatus(discountCommodity);

    DiscountCommodityDao dao = DIContainer.getDao(DiscountCommodityDao.class);

    // 重复判断
    if (dao.exists(discountCommodity.getDiscountCode(), discountCommodity.getCommodityCode())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }

    ValidationSummary validateCustomer = BeanValidator.validate(discountCommodity);
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      return result;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());

      txMgr.insert(discountCommodity);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public ServiceResult updateDiscountHeader(DiscountHeader discountHeader) {

    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    DiscountHeaderDao dao = DIContainer.getDao(DiscountHeaderDao.class);

    // 更新対象データが存在しない場合はエラー
    DiscountHeader dh = dao.load(discountHeader.getDiscountCode());
    if (dh == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      logger.debug("no data. discountCode:" + discountHeader.getDiscountCode());
      return result;
    }

    discountHeader.setOrmRowid(dh.getOrmRowid());
    discountHeader.setCreatedUser(dh.getCreatedUser());
    discountHeader.setCreatedDatetime(dh.getCreatedDatetime());
    discountHeader.setUpdatedUser(dh.getUpdatedUser());
    discountHeader.setUpdatedDatetime(dh.getUpdatedDatetime());
    setUserStatus(discountHeader);

    // validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(discountHeader).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
    }

    if (result.hasError()) {
      return result;
    }

    dao.update(discountHeader, getLoginInfo());
    return result;
  }

  public DiscountCommodity getDiscountCommodity(String discountCode, String commodityCode) {
    DiscountCommodityDao dao = DIContainer.getDao(DiscountCommodityDao.class);
    return dao.load(discountCode, commodityCode);
  }

  public ServiceResult updateDiscountCommodity(DiscountCommodity discountCommodity) {

    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    DiscountCommodityDao dao = DIContainer.getDao(DiscountCommodityDao.class);

    // 更新対象データが存在しない場合はエラー
    DiscountCommodity dc = dao.load(discountCommodity.getDiscountCode(), discountCommodity.getCommodityCode());
    if (dc == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      logger.debug("no data. discountCode:" + discountCommodity.getDiscountCode() + ", commodityCode:"
          + discountCommodity.getCommodityCode());
      return result;
    }

    dc.setDiscountPrice(discountCommodity.getDiscountPrice());
    dc.setCustomerMaxTotalNum(discountCommodity.getCustomerMaxTotalNum());
    dc.setSiteMaxTotalNum(discountCommodity.getSiteMaxTotalNum());
    dc.setUseFlg(discountCommodity.getUseFlg());
    dc.setDiscountDirectionsCn(discountCommodity.getDiscountDirectionsCn());
    dc.setDiscountDirectionsJp(discountCommodity.getDiscountDirectionsJp());
    dc.setDiscountDirectionsEn(discountCommodity.getDiscountDirectionsEn());
    dc.setRankCn(discountCommodity.getRankCn());
    dc.setRankJp(discountCommodity.getRankJp());
    dc.setRankEn(discountCommodity.getRankEn());
    setUserStatus(dc);

    // validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(dc).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
    }

    if (result.hasError()) {
      return result;
    }

    dao.update(dc, getLoginInfo());
    return result;
  }

  public ServiceResult deleteDiscountCommodity(String discountCode, String commodityCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    DiscountCommodityDao dao = DIContainer.getDao(DiscountCommodityDao.class);
    DiscountCommodity discountCommodity = dao.load(discountCode, commodityCode);
    if (discountCommodity == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }
    try {
      txMgr.begin(getLoginInfo());

      txMgr.delete(discountCommodity);

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public ServiceResult updatePlanCommodity(List<PlanCommodity> list) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(list).getErrors();
    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    // 表示状態の更新
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());
      for (PlanCommodity planCommodity : list) {
        setUserStatus(planCommodity);
        txMgr.update(planCommodity);
      }

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public List<Plan> getPlanAllList() {
    PlanDao dao = DIContainer.getDao(PlanDao.class);
    return dao.findByQuery(CommunicationServiceQuery.GET_PLAN_ALL_LIST_QUERY);
  }

  public List<NewCouponRuleLssueInfo> getPrivateCouponLssue(String couponCode, boolean isBrand) {
    String querySql = "";

    if (isBrand) {
      querySql = CampainQuery.GET_PRIVATE_COUPON_ISSUE_BRAND_SQL;
    } else {
      querySql = CampainQuery.GET_PRIVATE_COUPON_ISSUE_CATEGORY_SQL;
    }

    Query query = new SimpleQuery(querySql, couponCode);
    return DatabaseUtil.loadAsBeanList(query, NewCouponRuleLssueInfo.class);
  }

  public List<NewCouponRuleLssueInfo> getMaxCouponLssue(String couponCode) {
    Query query = new SimpleQuery(CampainQuery.GET_MAX_COUPON_ISSUE_SQL, couponCode);
    return DatabaseUtil.loadAsBeanList(query, NewCouponRuleLssueInfo.class);
  }

  public List<NewCouponHistoryUseInfo> getCategoryCodeList(String couponIssueCode) {
    Query query = new SimpleQuery(CommunicationServiceQuery.LOAD_CATEGORY_LIST_QUERY, couponIssueCode);
    return DatabaseUtil.loadAsBeanList(query, NewCouponHistoryUseInfo.class);
  }

  public SearchResult<FreePostageRule> getFreePostageList(FreePostageListSearchCondition condition) {
    FreePostageListSearchQuery query = new FreePostageListSearchQuery(condition);
    SearchResult<FreePostageRule> result = DatabaseUtil.executeSearch(query);
    return result;
  }

  public FreePostageRule getFreePostageRule(String freePostageCode) {
    FreePostageRuleDao dao = DIContainer.getDao(FreePostageRuleDao.class);
    FreePostageRule result = dao.load(freePostageCode);
    return result;
  }

  public ServiceResult deleteFreePostage(String freePostageCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());

      txMgr.executeUpdate(new SimpleQuery(CommunicationServiceQuery.GET_DELETE_FREE_POSTAGE_RULE_QUERY, freePostageCode));

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public ServiceResult insertFreePostageRule(FreePostageRule freePostageRule) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(freePostageRule);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(freePostageRule);
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  public ServiceResult updateFreePostageRule(FreePostageRule freePostageRule) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(freePostageRule);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());

      txMgr.update(freePostageRule);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;

  }


  public ServiceResult insertGiftCardRule(GiftCardRule giftCardRule) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(giftCardRule);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(giftCardRule);
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  @Override
  public GiftCardRule selectCardCode(String id) {
    GiftCardRuleDao dao = DIContainer.getDao(GiftCardRuleDao.class);
    return dao.load(id);
  }

  @Override
  public GiftCardRule getGiftCardRule(String cardCode) {
    GiftCardRuleDao choiceDao = DIContainer.getDao(GiftCardRuleDao.class);
    return choiceDao.load(cardCode);
  }

  @Override
  public ServiceResult updateGiftCardRule(GiftCardRule giftCardRule) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(giftCardRule);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());

      txMgr.update(giftCardRule);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public SearchResult<GiftCardRule> searchGiftCardRuleList(GiftCardRuleListSearchCondition condition) {
    return DatabaseUtil.executeSearch(new GiftCardRuleListSearchQuery(condition));
  }

  @Override
  public ServiceResult insertGiftCardRuleIssueAction(GiftCardIssueHistory history) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    // Validationチェック
    setUserStatus(history);
    ValidationSummary validate = BeanValidator.validate(history);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }
    List<GiftCardIssueDetail> detailList = new ArrayList<GiftCardIssueDetail>();
    String pwd;
    List<String> pwdAll = getPwd();
    for (int i = 0; i < history.getIssueNum(); i++) {
      GiftCardIssueDetail detail = new GiftCardIssueDetail();
      detail.setCardCode(history.getCardCode());
      detail.setEffectiveYears(getGiftCardRule(history.getCardCode()).getEffectiveYears());
      detail.setCardName(getGiftCardRule(history.getCardCode()).getCardName());
      detail.setCardHistoryNo(getGiftCardIssueHistory(history.getCardCode()));
      detail.setUnitPrice(getGiftCardRule(history.getCardCode()).getUnitPrice());
      // 密码不能重复。记得判断
      for (int k = 1; k < 2; k++) {
        pwd = RandomUtil.getCharAndNumber(15);
        if (!pwdAll.contains(pwd) || pwdAll.size() == 0) {
          detail.setPassWord(pwd);
          pwdAll.add(pwd);
          break;
        }
        k = 0;
      }

      detail.setDenomination(getGiftCardRule(history.getCardCode()).getDenomination());
      detail.setIssueDate(history.getIssueDate());
      detail.setCardStatus(0L);
      detail.setCancelFlg(CancelFlg.NOT_CANCELLED.longValue());
      Long cardId = DatabaseUtil.generateSequence(SequenceType.CARD_ID);
      detail.setCardId(NumUtil.toString(cardId));
      detailList.add(detail);
      // Validationチェック
      setUserStatus(detail);
      ValidationSummary tempValidate = BeanValidator.validate(detail);
      if (tempValidate.hasError()) {
        Logger logger = Logger.getLogger(this.getClass());
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String rs : tempValidate.getErrorMessages()) {
          logger.debug(rs);
        }
        return serviceResult;
      }
    }

    try {
      txMgr.begin(getLoginInfo());

      for (GiftCardIssueDetail gift : detailList) {
        txMgr.insert(gift);
      }
      txMgr.insert(history);
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;

  }

  public Long getGiftCardIssueHistory(String cardCode) {
    Query query = new SimpleQuery(CampainQuery.GET_GIFT_CARD_ISSUE_HISTORY_COUNT, cardCode);
    return NumUtil.parseLong(DatabaseUtil.executeScalar(query));
  }
  
  public Long getCouponHistoryCount(String customerCode) {
    Query query = new SimpleQuery(CampainQuery.GET_COUPON_HISTORY_COUNT, customerCode);
    return NumUtil.parseLong(DatabaseUtil.executeScalar(query));
  }
  
  public Long getGiftCardCount(String customerCode) {
    Query query = new SimpleQuery(CampainQuery.GET_GIFT_CARD_COUNT, customerCode);
    return NumUtil.parseLong(DatabaseUtil.executeScalar(query));
  }

  @Override
  public List<String> getPwd() {
    Query query = new SimpleQuery(CommunicationServiceQuery.GIFT_CARD_ISSUE_HISTORY_PWD);
    return DatabaseUtil.loadAsStringList(query);
  }

  @Override
  public ServiceResult updateHistory(GiftCardIssueHistory hist) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    ValidationSummary validate = BeanValidator.validate(hist);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());

      txMgr.update(hist);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  @Override
  public GiftCardIssueHistory getGiftCardIssueHis(String cardCode, String cardHistoryNo) {
    GiftCardIssueHistoryDao dao = DIContainer.getDao(GiftCardIssueHistoryDao.class);
    return dao.load(cardCode, NumUtil.toLong(cardHistoryNo));
  }

  @Override
  public GiftCardIssueDetail searchGiftCardIssueDetailEdit(String  cardId,String cardCode) {
    GiftCardIssueDetailDao dao = DIContainer.getDao(GiftCardIssueDetailDao.class);
    return dao.load(cardId, cardCode);
  }

  public ServiceResult updateDetail(GiftCardIssueDetail detail) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    ValidationSummary validate = BeanValidator.validate(detail);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());

      txMgr.update(detail);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  @Override
  public GiftCardIssueDetail getGiftCardIssueDetail(String cardId) {
    GiftCardIssueDetailDao choiceDao = DIContainer.getDao(GiftCardIssueDetailDao.class);
    return choiceDao.load(cardId);
  }

  @Override
  public List<GiftCardIssueHistory> getdetail(String cardCode) {
    Query query = new SimpleQuery(CampainQuery.GET_GIFT_CARD_ISSUE_HISTORY, cardCode);
    return DatabaseUtil.loadAsBeanList(query, GiftCardIssueHistory.class);
  }

  public ServiceResult insertGiftCardReturnApply(GiftCardReturnApply gcra) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    setUserStatus(gcra);
    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(gcra);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    try {
      gcra.setReturnDate(gcra.getCreatedDatetime());
      txMgr.begin(getLoginInfo());
      txMgr.insert(gcra);
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }
  
  public ServiceResult insertGiftCardReturnConfirm(GiftCardReturnApply gcra,String confirmAmount) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    BigDecimal confirmPrice = new BigDecimal(confirmAmount);
    CustomerCardUseInfoDao ccuiDao = DIContainer.getDao(CustomerCardUseInfoDao.class);
    try {
      txMgr.begin(getLoginInfo());
      gcra.setConfirmAmount(confirmPrice);
      gcra.setConfirmFlg(1L);
      txMgr.update(gcra);

      List<CustomerCardUseInfo> ccuiList = ccuiDao.loadByOrderNoOrderBy(gcra.getOrderNo() ,"", " ORDER BY USE_AMOUNT" );
      if (ccuiList != null && ccuiList.size() > 0) {
        if (BigDecimalUtil.equals(confirmPrice, gcra.getCardUseAmount())) {
          for (CustomerCardUseInfo info : ccuiList) {
            //0: 正常  1：取消
            info.setUseStatus(1L);
            txMgr.update(info);
          }
        } else {
          List<String> cardIdList = new ArrayList<String>(); 
            
            for (CustomerCardUseInfo info : ccuiList) {
              GiftCardReturnConfirm gcrc = new GiftCardReturnConfirm();
              gcrc.setCustomerCode(info.getCustomerCode());
              gcrc.setOrderNo(info.getOrderNo());
              if ( BigDecimalUtil.isAboveOrEquals(confirmPrice, info.getUseAmount()) ) {
                gcrc.setCardId(info.getCardId());
                cardIdList.add(info.getCardId());
                confirmPrice = confirmPrice.subtract(info.getUseAmount());
                gcrc.setReturnAmount(info.getUseAmount());
              } else {
                
                String notIn = "( ";
                for (int i = 0; i < cardIdList.size() ; i++) {
                  if (i == cardIdList.size() -1) {
                    notIn += "'" + cardIdList.get(i) + "'";
                  } else {
                    notIn += "'" + cardIdList.get(i) + "',";
                  }
                }
                notIn = notIn + ")";
                String innerJoin = " inner join customer_card_info cci on ccui.card_id = cci.card_id ";;
                String orderBy ;
                if (cardIdList.size() == 0) {
                  notIn = "";
                  orderBy = "  ORDER BY cci.card_end_date ";
                } else {
                  orderBy = " and ccui.card_id not in " + notIn +" ORDER BY cci.card_end_date ";
                }

                List<CustomerCardUseInfo> leftCcuiList = ccuiDao.loadByOrderNoOrderBy(gcra.getOrderNo() , innerJoin , orderBy);
                gcrc.setCardId(leftCcuiList.get(0).getCardId());
                gcrc.setReturnAmount(confirmPrice);
                confirmPrice = BigDecimal.ZERO;
              }
              setUserStatus(gcrc);
              txMgr.insert(gcrc);
              if (BigDecimalUtil.equals(confirmPrice, BigDecimal.ZERO)) {
                break;
              }
            }
            

          
        }
      }
      
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }
  

  public List<CustomerCardInfo> getCustomerCardInfoList(String orderNo) {
    Query query = new SimpleQuery(CommunicationServiceQuery.GET_CUSTOMER_CARD_INFO_LIST_QUERY, orderNo);
    return DatabaseUtil.loadAsBeanList(query, CustomerCardInfo.class);
  }

  public List<CustomerCardUseInfo> getCustomerCardUseInfoList(String cardId) {
    Query query = new SimpleQuery(CommunicationServiceQuery.GET_CUSTOMER_CARD_USE_INFO_LIST_QUERY, cardId);
    return DatabaseUtil.loadAsBeanList(query, CustomerCardUseInfo.class);
  }

  public ServiceResult deleteCustomerCardInfo(String cardId) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());

      txMgr.executeUpdate(new SimpleQuery(CommunicationServiceQuery.GET_DELETE_CUSTOMER_CARD_INFO_QUERY, cardId));

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  @Override
  public ServiceResult updateGiftCardIssueDetail(String cardCode, String cardHistoryNo) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());

      txMgr.executeUpdate(new SimpleQuery(CampainQuery.GIFT_CARD_ISSUE_DETAIL_UPDATE,CancelFlg.CANCELLED.longValue(),cardCode,cardHistoryNo));

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public ServiceResult updateGiftCardIssueDetailStatus(String cardCode, String cardHistoryNo) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());

      txMgr.executeUpdate(new SimpleQuery(CampainQuery.GIFT_CARD_ISSUE_DETAIL_UPDATE_STATUS,CancelFlg.CANCELLED.longValue(),cardCode,cardHistoryNo));

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }
  

  public List<String>  getGiftCardIssueDetailCardStatus(String cardCode,String cardHistoryNo){
      Query query = new SimpleQuery(CampainQuery.GIFT_CARD_ISSUE_DETAIL_CARD_STATUS, cardCode,cardHistoryNo);
      return DatabaseUtil.loadAsStringList(query);
  }
  
  public SearchResult<PropagandaActivityRule> getPropagandaActivityRuleList(PropagandaActivityRuleListSearchCondition condition) {
    PropagandaActivityRuleListSearchQuery query = new PropagandaActivityRuleListSearchQuery(condition);
    SearchResult<PropagandaActivityRule> result = DatabaseUtil.executeSearch(query);
    return result;
  }
  
  public PropagandaActivityRule getPropagandaActivityRule(String activityCode) {
    PropagandaActivityRuleDao dao = DIContainer.getDao(PropagandaActivityRuleDao.class);
    PropagandaActivityRule result = dao.load(activityCode);
    return result;
  }

  public ServiceResult deletePropagandaActivityRule(String activityCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());

      txMgr.executeUpdate(new SimpleQuery(CommunicationServiceQuery.GET_DELETE_PROPAGANDA_ACTIVITY_RULE_QUERY, activityCode));

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }
  
  public ServiceResult insertPropagandaActivityRule(PropagandaActivityRule propagandaActivityRule) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(propagandaActivityRule);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(propagandaActivityRule);
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }
  
  public List<PropagandaActivityCommodityInfo> getPropagandaActivityCommodityList(String activityCode) {
    Query query = new SimpleQuery(CommunicationServiceQuery.GET_PROPAGANDA_ACTIVITY_COMMODITY_LIST_QUERY, activityCode);
    return DatabaseUtil.loadAsBeanList(query, PropagandaActivityCommodityInfo.class);
  }
  
  public ServiceResult updatePropagandaActivityRule(PropagandaActivityRule propagandaActivityRule) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(propagandaActivityRule);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());

      txMgr.update(propagandaActivityRule);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }
  
  public ServiceResult insertPropagandaActivityCommodity(PropagandaActivityCommodity propagandaActivityCommodity) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(propagandaActivityCommodity);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(propagandaActivityCommodity);
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }
  
  public PropagandaActivityCommodity getPropagandaActivityCommodity(String activityCode, String commodityCode) {
    PropagandaActivityCommodityDao dao = DIContainer.getDao(PropagandaActivityCommodityDao.class);
    PropagandaActivityCommodity result = dao.load(activityCode, commodityCode);
    return result;
  }
  
  public ServiceResult deletePropagandaActivityCommodity(String activityCode, String commodityCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());

      txMgr.executeUpdate(new SimpleQuery(CommunicationServiceQuery.GET_DELETE_PROPAGANDA_ACTIVITY_COMMODITY_QUERY, activityCode, commodityCode));

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public boolean existCommonActivityDate(Long orderType, Long languageCode, Date startTime, Date endTime, String activityCode) {
    Object[] params = {
        orderType, languageCode, startTime, endTime, startTime, endTime, activityCode
    };
    Query query = new SimpleQuery(CommunicationServiceQuery.GET_COMMON_PROPAGANDA_ACTIVITY_QUERY, params);
    List<PropagandaActivityRule> list = DatabaseUtil.loadAsBeanList(query, PropagandaActivityRule.class);
    return list.size() > 0;
  }
  
  @Override
  public boolean existFriendCouponIssueHistory(String friendCouponRuleNo) {
    FriendCouponUseHistoryDao fcuhd = DIContainer.getDao(FriendCouponUseHistoryDao.class);
    return fcuhd.existFriendCouponIssueHistory(friendCouponRuleNo);
  }

  @Override
  public List<FriendCouponIssueHistory> getIssueHistoryByCustomerCode(String customerCode) {
    Object[] params = {
        customerCode
    };
    Query query = new SimpleQuery(CommunicationServiceQuery.GET_ISSUE_HISTORY_BY_CUSTOMER_CODE, params);
    return DatabaseUtil.loadAsBeanList(query, FriendCouponIssueHistory.class);
  }

  @Override
  public ServiceResult insertOptionalCampaign(OptionalCampaign obj) {
    ServiceResultImpl result = new ServiceResultImpl();

    List<ValidationResult> resultList = BeanValidator.validate(obj).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return result;
    }

    OptionalCampaignDao dao = DIContainer.getDao(OptionalCampaignDao.class);
    dao.insert(obj);
    
    return result;
    
  }

  @Override
  public OptionalCampaign loadOptionalCampaign(String shopCode,String campaignCode) {
    if(StringUtil.isNullOrEmpty(campaignCode)){
      return null;
    }
    OptionalCampaignDao dao = DIContainer.getDao(OptionalCampaignDao.class);
    return dao.load(shopCode, campaignCode);
  }

  @Override
  public ServiceResult upadteOptionalCampaign(OptionalCampaign obj) {
    ServiceResultImpl result = new ServiceResultImpl();

    List<ValidationResult> resultList = BeanValidator.validate(obj).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return result;
    }
    OptionalCampaignDao dao = DIContainer.getDao(OptionalCampaignDao.class);
    dao.update(obj);
    
    return result;
  }

  @Override
  public List<OptionalCampaign> getOptionalCampaignbyDate(Date starTime, Date endTime) {
    Object[] params = {
        starTime,endTime
    };
    Query query = new SimpleQuery(CommunicationServiceQuery.GET_OPTIONAL_CAMPAIGN_BY_DATE, params);
    return DatabaseUtil.loadAsBeanList(query, OptionalCampaign.class);
  }

  @Override
  public ServiceResult deleteOptionalCampaign(String shopCode,String campaignCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    OptionalCampaign campaign = loadOptionalCampaign(shopCode, campaignCode);
    if (campaign == null) {
      serviceResult.addServiceError(CommunicationServiceErrorContent.PLAN_USE_ERROR);
      return serviceResult;
    }
    try {

      txMgr.begin(getLoginInfo());
      // 活动删除
      txMgr.delete(campaign);

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  @Override
  public List<DiscountHeader> getActiveDiscountHeaderList() {
    Query query = new SimpleQuery(CommunicationServiceQuery.GET_ACTIVE_DISCOUNT_HEADER_LIST);
    return DatabaseUtil.loadAsBeanList(query, DiscountHeader.class);
  }

  @Override
  public List<DiscountHeader> getCrossDiscountHeaderList(Date starTime, Date endTime) {
    Object[] params = {starTime,endTime};
    Query query = new SimpleQuery(CommunicationServiceQuery.GET_CROSS_DISCOUNT_HEADER_LIST, params);
    return DatabaseUtil.loadAsBeanList(query, DiscountHeader.class);
  }

  @Override
  public List<DiscountCommodity> getDiscountCommodityListByCouponCode(String couponCode) {
    Object[] params = {couponCode};
    Query query = new SimpleQuery(CommunicationServiceQuery.GET_DISCOUNT_COMMODITY_LIST_BY_COUPON_CODE, params);
    return DatabaseUtil.loadAsBeanList(query, DiscountCommodity.class);
  }

  @Override
  public List<OrderReview> getOrderReviewListByCustomerCodeAndOrderNo(String  customerCode, String orderNo) {
    Object[] params = {customerCode,orderNo};
    Query query = new SimpleQuery(CommunicationServiceQuery.GET_ORDER_REVIEW_LIST_BY_ORDER_NO_AND_CUSTOMER_CODE, params);
    return DatabaseUtil.loadAsBeanList(query, OrderReview.class);
  }
  
  public Long getAvaliableCouponCount(String customerCode) {
    Query query = new SimpleQuery(CampainQuery.GET_AVALIABLE_COUPON_COUNT, customerCode);
    return NumUtil.parseLong(DatabaseUtil.executeScalar(query));
  }


  //库存休息日集合service
  @Override
  public List<StockHoliday> getStockHolidayList() {
    StockHolidayDao shdao=DIContainer.getDao(StockHolidayDao.class);
    return shdao.loadAll();
  }
  //新加库存休息日
  @Override
  public ServiceResult insertStockHoliday(StockHoliday sh) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    StockHolidayDao shdao=DIContainer.getDao(StockHolidayDao.class);
    shdao.insert(sh);
    return serviceResult;
 
  }
  //删除库存休息日
  @Override
  public ServiceResult deleteStockHoliday(Date shday) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    StockHolidayDao shdao=DIContainer.getDao(StockHolidayDao.class);
    shdao.delete(shday);
    return serviceResult;
  }

  @Override
  public boolean existsFriendCouponIssueHistory(String friendCouponRuleNo, String customerCode) {
    Object[] params = {friendCouponRuleNo, customerCode};
    Query query = new SimpleQuery(CommunicationServiceQuery.EXISTS_FRIEND_COUPON_ISSUE_HISTORY,params);
    Long result = DatabaseUtil.executeScalar(query,Long.class);
    return  result.intValue() > 0;
  }
}
