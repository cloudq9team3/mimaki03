package jp.enpit.cloud.eventspiral.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.util.DBUtils;

public class AccountModel {
    private static final String DB_ACCOUNT_COLLECTION = "account";
    private DB db = DBUtils.getInstance().getDb();
    private DBCollection coll = db.getCollection(DB_ACCOUNT_COLLECTION);

    public Account authenticate(String userId, String password)
            throws TEMFatalException, AuthenticationFailureException{

        DBObject query = new BasicDBObject();
        query.put("userId", userId);
        query.put("pass", password);

        try{
            DBObject result = coll.findOne(query);

            if(result == null){
                String message = String.format(
                    "User { userId: %s, pass: %s } not exist.",
                    userId, password
                );
                throw new AuthenticationFailureException(message);
            }
            Account account = new Account();
            DBUtils.attachProperties(account, result);

            return account;
        } catch(MongoException e){
            throw new TEMFatalException(e);
        }
    }

    public Account registerAccount(String userId, String pass, String role)
            throws TEMFatalException, AccountAlreadyRegisteredException{
        Account account = new Account();
        account.setUserId(userId);
        account.setPass(pass);
        account.setRole(role);

        if(!isUniqueName(userId)){
            String message = String.format("Account: %s is already registered", userId);
            throw new AccountAlreadyRegisteredException(message);
        }
        DBObject object = new BasicDBObject();
        DBUtils.convertToDBObject(object, account);

        try{
            coll.insert(object);
            return account;
        } catch(MongoException e){
            throw new TEMFatalException(e);
        }
    }

    public void registerUser(String userId, String pass)
            throws TEMFatalException, AccountAlreadyRegisteredException{
        registerAccount(userId, pass, "user");
    }

    private boolean isUniqueName(String userId) throws TEMFatalException{
        DBObject query = new BasicDBObject();
        query.put("userId", userId);

        try{
            if(coll.findOne(query) != null){
                return false;
            }
        } catch(MongoException e){
            throw new TEMFatalException(e);
        }
        return true;
    }
}