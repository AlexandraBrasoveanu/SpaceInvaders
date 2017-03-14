<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head></head>
<body>
<h1>Space Invader Radar</h1>

<stripes:form beanclass="com.companyname.testsimple.RadarActionBean" focus="">
    <stripes:file name="radasSnapshot" />
    <br>
    <stripes:submit name="findInvader" value="Find it"/>
    <%--<table>--%>
        <%--<tr>--%>
            <%--<td>Number 1:</td>--%>
            <%--<td><stripes:text name="numberOne"/></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td>Number 2:</td>--%>
            <%--<td><stripes:text name="numberTwo"/></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td colspan="2">--%>
                <%--<stripes:submit name="addition" value="Add"/>--%>
            <%--</td>--%>
        <%--</tr>--%>

    <%--</table>--%>
</stripes:form>
</body>
</html>