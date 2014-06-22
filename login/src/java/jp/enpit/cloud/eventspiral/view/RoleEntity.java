package jp.enpit.cloud.eventspiral.view;

import org.hibernate.validator.constraints.NotEmpty;

public class RoleEntity extends AbstractEntity{
    @NotEmpty
    private String role;

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }
}