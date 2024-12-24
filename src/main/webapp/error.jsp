<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>エラー</title>
</head>
<body>
    <h1>エラーが発生しました</h1>
    <p><%= request.getAttribute("error") != null ? request.getAttribute("error") : "不明なエラー" %></p>
    <a href="group.jsp">ホームに戻る</a>
</body>
</html>
