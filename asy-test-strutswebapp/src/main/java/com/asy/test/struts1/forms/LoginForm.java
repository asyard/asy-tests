package com.asy.test.struts1.forms;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

public class LoginForm extends ActionForm {

    private String userName;
    private String captchaCode;

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        /*ActionErrors actionErrors = new ActionErrors();

        if (userName.isEmpty() || userName.length() < 2) {
            actionErrors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("username invalid"));
        }

        return actionErrors;*/
        return null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }
}
