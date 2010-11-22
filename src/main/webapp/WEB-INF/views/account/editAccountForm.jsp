<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<html>
        <%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="message">${message}</div>
<div class="editAccountSection">
    <form:form modelAttribute="personalInformationForm" action="/account/personalInformation.submit" method="post">
        <h2>Personal Information</h2>
        <table>
            <tr>
                <td><form:label for="firstname" path="firstname" cssErrorClass="error">First Name:</form:label></td>
                <td><form:input path="firstname" /></td>
                <td><form:errors path="firstname" /></td>
            </tr>
            <tr>
                <td><form:label for="lastname" path="lastname" cssErrorClass="error">Last Name:</form:label></td>
                <td><form:input path="lastname" /></td>
                <td><form:errors path="lastname" /></td>
            </tr>
            <tr>
                <td><form:label for="writingExperience" path="writingExperience" cssErrorClass="error">Writing Experience:</form:label></td>
                <td><form:select path="writingExperience">
                        <form:option value="-" label="-Select-"/>
                        <form:options items="${writingExperienceList}"/>
                    </form:select></td>
                <td><form:errors path="writingExperience" /></td>
            </tr>
            <tr>
                <td><form:label for="reviewingExperience" path="reviewingExperience" cssErrorClass="error">Reviewing Experience:</form:label></td>
                <td><form:select path="reviewingExperience">
                        <form:option value="-" label="-Select-"/>
                        <form:options items="${reviewingExperienceList}"/>
                    </form:select></td>
                <td><form:errors path="reviewingExperience" /></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Update" /></td>
            </tr>
        </table>
    </form:form>
</div>
<div class="editAccountSection">
    <form:form modelAttribute="contactInformationForm" action="/account/contactInformation.submit" method="post">
        <h2>Contact Information</h2>
        <table>
            <tr>
                <td><form:label for="email" path="email" cssErrorClass="error">Change Email Address:</form:label></td>
                <td><form:input path="email" /></td>
                <td><form:errors path="email" /></td>
            </tr>
            <tr>
                <td><form:label for="confirmEmail" path="confirmEmail" cssErrorClass="error">Confirm Email Address:</form:label></td>
                <td><form:input path="confirmEmail" /></td>
                <td><form:errors path="confirmEmail" /></td>
            </tr>
            <tr>
                <td><form:label for="primaryIM" path="primaryIM" cssErrorClass="error">Primary IM:</form:label></td>
                <td>
                    <form:select path="primaryIM">
                        <form:option value="-" label="-Select-"/>
                        <form:options items="${imProtocolList}"/>
                    </form:select>
                    <form:input path="primaryIMName" size="10" /></td>
                <td><form:errors path="primaryIM" /></td>
            </tr>
            <tr>
                <td><form:label for="secondaryIM" path="secondaryIM" cssErrorClass="error">Secondary IM:</form:label></td>
                <td>
                    <form:select path="secondaryIM">
                        <form:option value="-" label="-Select-"/>
                        <form:options items="${imProtocolList}"/>
                    </form:select>
                    <form:input path="secondaryIMName" size="10" /></td>
                <td><form:errors path="secondaryIM" /></td>
            </tr>
            <tr>
                <td colspan="3" align="center"><input type="submit" value="Update" /></td>
            </tr>
        </table>
    </form:form>
</div>
<div class="editAccountSection">
    <form:form modelAttribute="passwordChangeForm" action="/account/passwordChange.submit" method="post">
        <h2>Password Change</h2>
        <table>
            <tr>
                <td><form:label for="currentPassword" path="currentPassword" cssErrorClass="error">Current Password:</form:label></td>
                <td><form:password path="currentPassword" /></td>
                <td><form:errors path="currentPassword" /></td>
            </tr>
            <tr>
                <td><form:label for="newPassword" path="newPassword" cssErrorClass="error">New Password:</form:label></td>
                <td><form:password path="newPassword" /></td>
                <td><form:errors path="newPassword" /></td>
            </tr>
            <tr>
                <td><form:label for="confirmPassword" path="confirmPassword" cssErrorClass="error">Confirm Password:</form:label></td>
                <td><form:password path="confirmPassword" /></td>
                <td><form:errors path="confirmPassword" /></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit"	value="Update" /></td>
            </tr>
    	</table>
    </form:form>
</div>
    <%@ include file="/WEB-INF/jsp/footer.jsp"%>
    </body>
</html>
