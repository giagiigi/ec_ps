// 10.4.0 X40001 �V�K�쐬
package jp.co.sint.webshop.data.cache;

/**
 * �ꎞ�I�ȃf�[�^�L���b�V�����Ǘ�����R���e�i�̃C���^�t�F�[�X�ł��B
 * 
 * @author System Integrator Corp.
 */
public interface CacheContainer {

  /**
   * �R���e�i����f�[�^���擾���܂��B
   * 
   * @param key
   *          �f�[�^���w�肷��L�[
   * @return �L���b�V���f�[�^�B�f�[�^�����݂��Ȃ��A���邢�͊����؂�(expired)�̏ꍇ��null��Ԃ��܂��B
   */
  <V>V get(CacheKey key);

  /**
   * �R���e�i����f�[�^���擾���A�L���b�V����������Ȃ��ꍇ�̓R�[���o�b�N�����s���ăf�[�^���R���e�i�ɓo�^���܂��B
   * 
   * @param key
   *          �f�[�^���w�肷��L�[
   * @param callback
   *          �R�[���o�b�N�����C���^�t�F�[�X
   * @return �L���b�V���f�[�^�B�f�[�^�����݂��Ȃ��A���邢�͊����؂�(expired)�̏ꍇ��
   *         �R�[���o�b�N�����C���^�t�F�[�X�̎��s���ʂ�Ԃ��܂��B�R�[���o�b�N���������s���ꂽ�ꍇ�A
   *         �R�[���o�b�N�����ɂ���Ė߂��ꂽ�f�[�^���L���b�V���Ƃ��ēo�^���܂��B
   *         �L���b�V���̎擾���ʂƃR�[���o�b�N�����̌��ʂ��������null�ł���ꍇ�Anull��Ԃ��܂��B
   */
  <V>V get(CacheKey key, CacheRetriever<V> callback);

  /**
   * �R���e�i�Ƀf�[�^��o�^���܂��B
   * 
   * @param key
   *          �f�[�^���w�肷��L�[
   * @param value
   *          �L���b�V���ɓo�^����f�[�^
   */
  <V>V put(CacheKey key, V value);

}
