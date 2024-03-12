// JSP 는 맨 위에 이 줄이 있음. JSP 형식이라는걸 알리는 줄임
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/jsp/members/save.jsp" method="post">
    username : <input type="text" name="username" />
    age : <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
</body>
</html>

// <% 표시하면 자바 코드를 넣을 수 있음
// <% %> 안에 필요한 로직 코드를 넣으면 됨!

// JSP도 좀 실망스럽다. 같이 짬뽕되어있으므로 더럽다
// 그래서 나온게 MVC패턴이다.