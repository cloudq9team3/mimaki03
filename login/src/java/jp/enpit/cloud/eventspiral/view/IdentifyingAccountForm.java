package jp.enpit.cloud.eventspiral.view;

import jp.enpit.cloud.eventspiral.util.DBUtils;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class IdentifyingAccountForm extends AbstractForm{
    @Length(min=4, max=12, message="ユーザIDには4から12文字の英数字のみが利用できます")
    @NotEmpty(message="ユーザIDには4から12文字の英数字のみが利用できます")
    private String userId;

    @Length(min=4, max=12, message="パスワードには4から12文字の英数字のみが利用できます")
    @NotEmpty(message="パスワードには4から12文字の英数字のみが利用できます")
    private String pass;

    public String getPass(){
        return pass;
    }

    public String getUserId(){
        return userId;
    }

    public void setPass(String pass){
        this.pass = DBUtils.sanitize(pass);
    }

    public void setUserId(String userId){
        this.userId = DBUtils.sanitize(userId);
    }
}