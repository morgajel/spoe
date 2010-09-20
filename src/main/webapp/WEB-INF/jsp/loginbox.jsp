<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<div id='loginbox'
	style="float: right; position: absolute; right: 5px; top: 5px;">
<sec:authorize access='! isAuthenticated()'>
	<form name='f' action='/j_spring_security_check' method='POST'>
	<table style='border: 1px solid black;'>
		<tr>
			<th colspan='2'>Login with Username and Password</th>
		</tr>
		<tr>
			<td><label for='j_username'>User:</label></td>
			<td><input type='text' name='j_username' value='' /></td>
		</tr>
		<tr>
			<td><label for='j_password'>Password:</label></td>
			<td><input type='password' name='j_password' /></td>
		</tr>
		<tr>
			<td colspan='2'><input type='checkbox'
				name='_spring_security_remember_me' /> <label
				for='_spring_security_remember_me'>Remember me on this
			computer.</label></td>
		</tr>
		<tr>
			<td><input name="submit" type="submit" value='Log In' /></td>
			<td>or <a href="/account/register">Register</a></td>
		</tr>
	</table>
	</form>
</sec:authorize> <sec:authorize access='isAuthenticated()'>
	<p><a href="/profile">profile</a> | <a
		href="/j_spring_security_logout">Logout</a>
</sec:authorize></div>