package jp.enpit.cloud.eventspiral;

import java.util.logging.Logger;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import jp.enpit.cloud.eventspiral.util.DBUtils;

/**
 * UC: ログインする
 * Session()
 * registerSessionId(String)
 * deleteSessionId()
 * getSessionId()
 * @author fukuyasu
 *
 */
public class Session {
	private final String DB_SESSION_COLLECTION = "account";
	private Logger logger;
	private DB db;
	private DBCollection coll;

	public Session() {
		logger = Logger.getLogger(getClass().getName());
		db = DBUtils.getInstance().getDb();
		coll = db.getCollection(DB_SESSION_COLLECTION);
	}

	/**
	 * セッションオブジェクトをDBへ登録する．
	 * @throws TEMFatalException
	 */
	public void registerSessionId(String userId) throws TEMFatalException {
		logger.info("Session.registerSessionId");

		// DWRの発行するSessionIdの取得
		String sessionId = getSessionId();

		// 登録済みのセッションオブジェクトを消す
		deleteSessionId();

		DBObject query = new BasicDBObject();
		query.put("userId", userId);

		DBObject update = new BasicDBObject();
		update.put("$set", new BasicDBObject("sessionId", sessionId));

		// DB登録
		try {
			coll.update(query, update);
		} catch (MongoException e) {
			logger.severe(e.getMessage());
			throw new TEMFatalException(e);
		}
	}

	/**
	 * 認証済みセッションオブジェクトを消す．
	 * @throws TEMFatalException
	 */
	public void deleteSessionId() throws TEMFatalException {
		logger.info("Session.deleteSessionId");

		// DWRの発行するSessionIdの取得．
		String sessionId = getSessionId();

		// query
		DBObject query = new BasicDBObject();
		query.put("sessionId", sessionId);

		// update セッションを空にする．
		DBObject update = new BasicDBObject();
		update.put("$set", new BasicDBObject("sessionId", ""));

		try {
			// 認証済みの同一セッションを探し全て消す．
			coll.update(query, update);
		} catch (MongoException e) {
			logger.severe(e.getMessage());
			throw new TEMFatalException(e);
		}
	}

	/**
	 * DWRの発行するSessionIdの取得するためのユーティリティメソッド．
	 * ローカルテスト時には定数"THIS_IS_A_TEST_SESSION_ID"を返す．
	 * @return sessionId
	 */
	private String getSessionId() {
		WebContext ctx = WebContextFactory.get();

		// ローカルテスト時
		if (ctx == null) {
			return "THIS_IS_A_TEST_SESSION_ID";
		}

		return ctx.getScriptSession().getId().split("/")[0];
	}

}
