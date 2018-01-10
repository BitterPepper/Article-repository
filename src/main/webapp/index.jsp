<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <title>Article repository</title>
    <style>
        .panelTitle {
            padding-top: 4px;
            padding-bottom: 12px;
            font-weight: bold;
        }

        .leftPanel {
            float: left;
            padding: 10px;
            width: 30%;
        }

        .rightPanel {
            float: left;
            padding: 10px;
            width: 13%;
        }

        .errors {
            color: red;
        }

        .keyList {
            font-weight: normal;
            color: deepskyblue;
        }

        .inputs70 {
            width: 70%;
        }

        .inputs100 {
            width: 100%;
        }

        .formLabel {
            padding-top: 4px;
        }
    </style>
</head>

<body>

<div class="main">
    <div class="leftPanel">
        <div class="panelTitle">
            Add article:
        </div>
        <form action="/articleservice/storeArticle" id="addform" method="POST">
            Key: <input type="text" name="articleKey" autofocus>
            <input type="submit" name="addButton" value="Add">
        </form>
        <span class="formLabel">Article:</span><br>
        <textarea class="inputs100" rows="40" name="article" form="addform"
                  placeholder="Enter article here..."></textarea>

        <div class="errors">
            <%
                if (session.getAttribute("addErrors") != null) {
                    out.println(session.getAttribute("addErrors"));
                    session.setAttribute("addErrors", null);
                }
            %>
        </div>
    </div>
    <div class="leftPanel">
        <div class="panelTitle">
            Get article:
        </div>
        <form action="/articleservice/getArticle" id="getform" method="GET">
            Key: <input type="text" name="articleKey"
                        value='<%= session.getAttribute("articleKey")!=null ? session.getAttribute("articleKey") : ""%>'>
            <input type="submit" name="getButton" value="Get">
            <div class="errors" style="display: inline-block;">
                <%
                    if (session.getAttribute("getNotExist") != null) {
                        out.println(session.getAttribute("getNotExist"));
                        session.setAttribute("getNotExist", null);
                    }
                %>
            </div>
        </form>
        <span class="formLabel">Article:</span><br>
        <textarea class="inputs100" rows="40" name="article" form="getform"
                  disabled><%= session.getAttribute("article") != null ? session.getAttribute("article") : ""%></textarea>

        <div class="errors">
            <%
                if (session.getAttribute("getErrors") != null) {
                    out.println(session.getAttribute("getErrors"));
                    session.setAttribute("getErrors", null);
                }
            %>
        </div>
    </div>
    <div class="rightPanel">
        <div class="panelTitle">
            Stored articles keys:
        </div>
        <div class="keyList">
            <%
                if (session.getAttribute("articleKeys") != null) {
                    out.println(session.getAttribute("articleKeys"));
                }
            %>
        </div>
    </div>
    <div class="rightPanel">
        <div class="panelTitle">
            Find articles:
        </div>
        <form action="/articleservice/findArticles" id="findform" method="POST">
            Enter tokens separated by commas:<br/>
            <input class="inputs70" type="text" name="tokens"
                   value='<%= session.getAttribute("tokens")!=null ? session.getAttribute("tokens") : ""%>'>
            <input type="submit" value="find">
        </form>
        <span>Found Articles keys:<br></span>
        <div class="keyList">
            <%
                if (session.getAttribute("foundKeys") != null) {
                    out.println(session.getAttribute("foundKeys"));
                }
            %>
        </div>
        <div class="errors">
            <%
                if (session.getAttribute("findErrors") != null) {
                    out.println(session.getAttribute("findErrors"));
                    session.setAttribute("findErrors", null);
                }
            %>
        </div>
    </div>
</div>

</body>
</html>