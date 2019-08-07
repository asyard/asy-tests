<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="botDetect" uri="https://captcha.com/java/jsp"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="logic" uri="http://struts.apache.org/tags-logic"%>
<html>
<head>
  <title>Login</title>
</head>
<body>
Login <br/>

<html:form action="/login">

  <logic:messagesPresent>
    <ol>
      <html:messages id="error">
        <li><b><bean:write name="error"/> </b></li>
      </html:messages>
    </ol>
  </logic:messagesPresent>


  <botDetect:captcha id="exampleCaptcha" userInputID="captchaCode" soundEnabled="true" codeLength="5" codeStyle="NUMERIC"/>
  <div class="validationDiv">
    Code : <html:text property="captchaCode" maxlength="20" /> <br/>
    Name : <html:text property="userName" maxlength="20" /> <br/>
    <input type="submit" name="submit" value="Submit" />
  </div>
</html:form>


</body>

<script>
    document.forms[0].setAttribute("AutoComplete","off");
</script>
</html>
