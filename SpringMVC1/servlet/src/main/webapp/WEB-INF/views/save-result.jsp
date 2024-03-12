<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>

    // <li>id=<%=((Member)request.getAttribute("member")).getId()%></li>
    // <li>username=<%=((Member)request.getAttribute("member")).getUsername()%></li>
    // <li>age=<%=((Member)request.getAttribute("member").getAge()%></li>

    // 이게 MVC 패턴일때의 좋은 점임
    // dispatcher로 넘겨받은 Model 객체에서 데이터만 꺼내쓰면 되므로
    // 짬뽕코드가 아닌 굉장히 간편한 코드가 됨

    <li>id=${member.id}</li>
    <li>username=${member.username}</li>
    <li>age=${member.age}</li>

</ul>
<a href="/index.html">메인</a>
</body>
</html>