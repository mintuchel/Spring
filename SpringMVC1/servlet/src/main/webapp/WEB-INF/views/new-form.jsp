<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

// 여기서 form의 action을 보면 절대경로(/로 시작)이 아니라 상대경로인 것을 확인할 수 있다.
// 이렇게 상대경로를 사용하면 폼 전송 시 현재 URL이 속한 계층 경로 + save가 호출된다.
<form actions="save" method="post">
    username: <input tye="text" name="username" />
    age:      <input type="text" name="age" /n>
    <button type = "submit">전송</button>
</form>

</body>
</html>