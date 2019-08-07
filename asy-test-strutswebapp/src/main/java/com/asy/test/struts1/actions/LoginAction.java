package com.asy.test.struts1.actions;

import com.asy.test.struts1.forms.LoginForm;
import com.captcha.botdetect.web.servlet.Captcha;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        if ("hey".equals(mapping.getParameter())) {
            LoginForm loginForm = (LoginForm) form;
            loginForm.setUserName("");
            loginForm.setCaptchaCode("");

            return mapping.findForward("success");
        } else if ("login".equals(mapping.getParameter())) {
            LoginForm loginForm = (LoginForm) form;
            Captcha captcha = Captcha.load(request, "exampleCaptcha");
            boolean isHuman = captcha.validate(loginForm.getCaptchaCode());
            if (isHuman) {
                return mapping.findForward("success");
            }

            return mapping.findForward("invalid");
        } else if ("welcome".equals(mapping.getParameter())) {
            return mapping.findForward("success");
        } else {
            return mapping.findForward("invalidparameter");
        }

    }
}
