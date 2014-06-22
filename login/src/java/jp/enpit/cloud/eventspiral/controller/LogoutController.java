package jp.enpit.cloud.eventspiral.controller;

import jp.enpit.cloud.eventspiral.Session;
import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

public class LogoutController{
    public void execute() throws TEMViewException{
        try{
            Session session = new Session();
            session.deleteSessionId();

        } catch(TEMFatalException e){
            throw new TEMViewException("システムエラーが発生しました．管理者に連絡してください", e);
        }
    }
}