package jp.enpit.cloud.eventspiral.controller;

import jp.enpit.cloud.eventspiral.Session;
import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.model.Account;
import jp.enpit.cloud.eventspiral.model.AccountModel;
import jp.enpit.cloud.eventspiral.model.AuthenticationFailureException;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.RoleEntity;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

public class LoginController{
    public RoleEntity execute(IdentifyingAccountForm form) throws TEMViewException{
        form.validate();

        try{
            AccountModel accountModel = new AccountModel();
            Session session = new Session();

            Account account = accountModel.authenticate(form.getUserId(), form.getPass());
            session.registerSessionId(form.getUserId());

            RoleEntity rEntity = new RoleEntity();
            rEntity.setRole(account.getRole());

            rEntity.validate();

            return rEntity;

        } catch(AuthenticationFailureException e){
            throw new TEMViewException("ユーザIDもしくはパスワードが正しくありません", e);
        } catch(TEMFatalException e){
            throw new TEMViewException("システムエラーが発生しました．管理者に連絡してください", e);
        }
    }
}