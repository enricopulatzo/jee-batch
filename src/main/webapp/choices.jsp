<%@ page contentType="text/html;charset=UTF-8" language="java" %><!DOCTYPE html>
<html>
<head>
	<title>Job Choices</title>
	<script src='https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/ angular.min.js'></script>
</head>
<body>
	<nav>
		<form action="${pageContext.request.contextPath}/batch_jobs" method="post">
			<select name='job_name' required>
				<option value="log-only-job">log-only-job</option>
				<option value="basic-chunk-job">basic-chunk-job</option>
			</select>
			<input type='hidden' name='action' value='START'>
			<button type='submit'>Start Job</button>
		</form>
		<form action="${pageContext.request.contextPath}/batch_jobs" method="post">
			<input placeholder="some execution id" name="executionId" required>
			<input type='hidden' name='action' value='VIEW'>
			<button type='submit'>View Job</button>
		</form>
		<form action="${pageContext.request.contextPath}/batch_jobs" method="post">
			<input placeholder="some execution id" name="executionId" required>
			<input type='hidden' name='action' value='RESTART'>
			<button type='submit'>Restart Job</button>
		</form>
	</nav>
</body>
</html>