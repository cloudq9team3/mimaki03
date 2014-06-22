package jp.enpit.cloud.eventspiral.controller;

import jp.enpit.cloud.eventspiral.TEMFatalException;
import jp.enpit.cloud.eventspiral.model.AccountModel;
import jp.enpit.cloud.eventspiral.model.AccountAlreadyRegisteredException;
import jp.enpit.cloud.eventspiral.view.IdentifyingAccountForm;
import jp.enpit.cloud.eventspiral.view.TEMViewException;

public class SignUpController{
    public void execute(IdentifyingAccountForm form) throws TEMViewException{
        form.validate();

        try{
            AccountModel accountModel = new AccountModel();
            accountModel.registerUser(form.getUserId(), form.getPass());

        } catch(AccountAlreadyRegisteredException e){
            throw new TEMViewException("指定されたユーザIDは既に存在します", e);
        } catch(TEMFatalException e){
            throw new TEMViewException("システムエラーが発生しました．管理者に連絡してください", e);
        }
    }
}