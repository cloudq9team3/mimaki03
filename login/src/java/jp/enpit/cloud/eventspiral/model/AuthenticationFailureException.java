package jp.enpit.cloud.eventspiral.model;

/**
 * UC: ログインする．
 *   AccountDaoで使われる．
 */
public class AuthenticationFailureException extends TEMSystemException {

	private static final long serialVersionUID = 4579762843484417115L;

	public AuthenticationFailureException(String msg) {
		super(msg);
	}
}
