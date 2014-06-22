package jp.enpit.cloud.eventspiral.view;

/**
 * UC: ログインする
 * @author takata
 *
 */
public interface Entity {
    public boolean validate() throws TEMValidationException;
}
