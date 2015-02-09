//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// ���̃t�@�C����EDM�t�@�C�����玩����������܂��B
// ���ڕҏW���Ȃ��ŉ������B
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.CommodityDescribe;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * �u���ߍ���HTML(EMBEDDED_HTML)�v�e�[�u���ւ̃f�[�^�A�N�Z�X��S������DAO(Data Access Object)�ł��B
 *
 * @author System Integrator Corp.
 *
 */
public interface CommodityDescribeDao extends GenericDao<CommodityDescribe, Long> {

  /**
   * �w�肳�ꂽorm_rowid���ߍ���HTML�̃C���X�^���X��擾���܂��B
   *
   * @param id �Ώۂ�orm_rowid
   * @return id�ɑΉ�����CommodityDescribe�̃C���X�^���X
   */
  CommodityDescribe loadByRowid(Long id);

  /**
   * ��L�[��̒l��w�肵�Ė��ߍ���HTML�̃C���X�^���X��擾���܂��B
   *
   * @param shopCode �V���b�v�R�[�h
   * @return ��L�[��̒l�ɑΉ�����CommodityDescribe�̃C���X�^���X
   */
  CommodityDescribe load(String shopCode, String commodity_code);

  /**
   * ��L�[��̒l��w�肵�Ė��ߍ���HTML����ɑ��݂��邩�ǂ�����Ԃ��܂��B
   *
   * @param shopCode �V���b�v�R�[�h
   * @return ��L�[��̒l�ɑΉ�����CommodityDescribe�̍s�����݂����true
   */
  boolean exists(String shopCode, String commodity_code);

  /**
   * �V�KCommodityDescribe��f�[�^�x�[�X�ɒǉB��܂��B
   *
   * @param obj �ǉQΏۂ�CommodityDescribe
   * @param loginInfo ���O�C�����
   * @return �ǉB�������ꍇ��orm_rowid�l
   */
  Long insert(CommodityDescribe obj, LoginInfo loginInfo);

  /**
   * ���ߍ���HTML��X�V���܂��B
   *
   * @param obj �X�V�Ώۂ�CommodityDescribe
   * @param loginInfo ���O�C�����
   */
  void update(CommodityDescribe obj, LoginInfo loginInfo);

  /**
   * ���ߍ���HTML��폜���܂��B
   *
   * @param obj �폜�Ώۂ�CommodityDescribe
   * @param loginInfo ���O�C�����
   */
  void delete(CommodityDescribe obj, LoginInfo loginInfo);

  /**
   * ��L�[��̒l��w�肵�Ė��ߍ���HTML��폜���܂��B
   *
   * @param shopCode �V���b�v�R�[�h
   */
  void delete(String shopCode, String commodityCode);

  /**
   * Query�I�u�W�F�N�g��w�肵�Ė��ߍ���HTML�̃��X�g��擾���܂��B
   *
   * @param query Query�I�u�W�F�N�g
   * @return ����ʂɑ�������CommodityDescribe�̃��X�g
   */
  List<CommodityDescribe> findByQuery(Query query);

  /**
   * SQL��w�肵�Ė��ߍ���HTML�̃��X�g��擾���܂��B
   * @param sqlString �o�C���h�ϐ�(?)��܂�SQL������
   * @param params �o�C���h�ϐ��ɗ^����l�̔z��
   * @return ����ʂɑ�������CommodityDescribe�̃��X�g
   */
  List<CommodityDescribe> findByQuery(String sqlString, Object... params);

  /**
   * �e�[�u���̑S�f�[�^��ꊇ�Ŏ擾���܂��B
   * @return �e�[�u���̑S�f�[�^����CommodityDescribe�̃��X�g
   */
  List<CommodityDescribe> loadAll();

}
