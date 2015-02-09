//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// ���̃t�@�C����EDM�t�@�C�����玩����������܂��B
// ���ڕҏW���Ȃ��ŉ������B
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.data.dao.CommodityDescribeDao;
import jp.co.sint.webshop.data.dto.CommodityDescribe;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * ���ߍ���HTML
 *
 * @author System Integrator Corp.
 *
 */
public class CommodityDescribeDaoImpl implements CommodityDescribeDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CommodityDescribe, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CommodityDescribeDaoImpl() {
    genericDao = new GenericDaoImpl<CommodityDescribe, Long>(CommodityDescribe.class);
  }

  /**
   * SessionFactory��擾���܂�
   * @return SessionFactory�̃C���X�^���X
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }

  /**
   * SessionFactory��ݒ肵�܂�
   * @param factory SessionFactory�̃C���X�^���X
   */
  public void setSessionFactory(SessionFactory factory) {
    this.sessionFactory = factory;
    genericDao.setSessionFactory(factory);
  }

  /**
   * �w�肳�ꂽorm_rowid���ߍ���HTML�̃C���X�^���X��擾���܂��B
   * @param id �Ώۂ�orm_rowid
   * @return id�ɑΉ�����EmbeddedHtml�̃C���X�^���X
   */
  public CommodityDescribe loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * ��L�[��̒l��w�肵�Ė��ߍ���HTML�̃C���X�^���X��擾���܂��B
   * @param shopCode �V���b�v�R�[�h
   * @return ��L�[��̒l�ɑΉ�����EmbeddedHtml�̃C���X�^���X
   */
  public CommodityDescribe load(String shopCode, String commodity_code) {
    Object[] params = new Object[]{shopCode, commodity_code};
    final String query = "SELECT * FROM Commodity_Describe"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ? ";
    List<CommodityDescribe> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * ��L�[��̒l��w�肵�Ė��ߍ���HTML����ɑ��݂��邩�ǂ�����Ԃ��܂��B
   * @param shopCode �V���b�v�R�[�h
   * @return ��L�[��̒l�ɑΉ�����EmbeddedHtml�̍s�����݂����true
   */
  public boolean exists(String shopCode, String commodity_code) {
    Object[] params = new Object[]{shopCode, commodity_code};
    final String query = "SELECT COUNT(*) FROM Commodity_Describe"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ? ";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * �V�KEmbeddedHtml��f�[�^�x�[�X�ɒǉB��܂��B
   * @param obj �ǉQΏۂ�EmbeddedHtml
   * @return �ǉB�������ꍇ��orm_rowid�l
   */
  public Long insert(CommodityDescribe obj) {
    return genericDao.insert(obj);
  }

  /**
   * �V�KEmbeddedHtml��f�[�^�x�[�X�ɒǉB��܂��B
   * @param obj �ǉQΏۂ�EmbeddedHtml
   * @return �ǉB�������ꍇ��orm_rowid�l
   */
  public Long insert(CommodityDescribe obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * ���ߍ���HTML��X�V���܂��B
   * @param obj �X�V�Ώۂ�EmbeddedHtml
   */
  public void update(CommodityDescribe obj) {
    genericDao.update(obj);
  }

  /**
   * ���ߍ���HTML��X�V���܂��B
   * @param obj �X�V�Ώۂ�EmbeddedHtml
   */
  public void update(CommodityDescribe obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * ���ߍ���HTML��폜���܂��B
   * @param obj �폜�Ώۂ�EmbeddedHtml
   */
  public void delete(CommodityDescribe obj) {
    genericDao.delete(obj);
  }

  /**
   * ���ߍ���HTML��폜���܂��B
   * @param obj �폜�Ώۂ�EmbeddedHtml
   */
  public void delete(CommodityDescribe obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * ��L�[��̒l��w�肵�Ė��ߍ���HTML��폜���܂��B
   * @param shopCode �V���b�v�R�[�h
   */
  public void delete(String shopCode, String commodityCode) {
    Object[] params = new Object[]{shopCode,commodityCode};
    final String query = "DELETE FROM Commodity_Describe"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ? ";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Query�I�u�W�F�N�g��w�肵�Ė��ߍ���HTML�̃��X�g��擾���܂��B
   * @param query Query�I�u�W�F�N�g
   * @return ����ʂɑ�������EmbeddedHtml�̃��X�g
   */
  public List<CommodityDescribe> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQL��w�肵�Ė��ߍ���HTML�̃��X�g��擾���܂��B
   * @param sqlString �o�C���h�ϐ�(?)��܂�SQL������
   * @param params �o�C���h�ϐ��ɗ^����l�̔z��
   * @return ����ʂɑ�������EmbeddedHtml�̃��X�g
   */
  public List<CommodityDescribe> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * �e�[�u���̑S�f�[�^��ꊇ�Ŏ擾���܂��B
   * @return �e�[�u���S�f�[�^����EmbeddedHtml�̃��X�g
   */
  public List<CommodityDescribe> loadAll() {
    return genericDao.loadAll();
  }

}
