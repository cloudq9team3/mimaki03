package jp.enpit.cloud.eventspiral.model;

public class Account{
    private String userId;
    private String pass;
    private String role;
    private String sessionId = "";

    public String getUserId(){
        return userId;
    }

    public String getPass(){
        return pass;
    }

    public String getRole(){
        return role;
    }

    public String getSessionId(){
        return sessionId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setPass(String pass){
        this.pass = pass;
    }

    public void setRole(String role){
        this.role = role;
    }

    public void setSessionId(String sessionId){
        this.sessionId = sessionId;
    }
}