package jp.co.sint.webshop.service;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.service.result.ServiceErrorContent;

/**
 * サービス処理結果インターフェース
 * 
 * @author System Integrator Corp.
 */
public interface ServiceResult extends Serializable {

  /**
   * サービス内で発生したエラー種別を返します。
   * 
   * @return サービス内で発生したエラー種別
   */
  List<ServiceErrorContent> getServiceErrorList();

  /**
   * エラー処理を返します。
   * 
   * @return エラー処理
   */
  boolean hasError();
}
