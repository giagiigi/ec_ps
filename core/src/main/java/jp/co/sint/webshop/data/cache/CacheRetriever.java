// 10.4.0 X40001 �V�K�쐬
package jp.co.sint.webshop.data.cache;

/**
 * CacheContainer �Ƀf�[�^�L���b�V�����Ȃ������ꍇ�A�f�[�^���擾���邽�߂̃R�[���o�b�N������\���C���^�t�F�[�X�ł��B
 * 
 * @author System Integrator Corp.
 * @param <D>
 *          �R�[���o�b�N�Ŏ擾�ł���f�[�^�^
 */
public interface CacheRetriever<D> {

  /**
   * �f�[�^���擾���܂��B
   * 
   * @return �f�[�^
   */
  D retrieve();

}
