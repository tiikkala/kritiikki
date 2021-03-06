<%@tag description="Kritiikki-sivujen tägi" pageEncoding="UTF-8"
       trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="pageTitle"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-theme.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="omatMuotoilut.css" rel="stylesheet">
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/readmore.js" type="text/javascript"></script>
        <title>${pageTitle}</title>
        <c:if test="${ilmoitus != null}">
        <div class="alert alert-info">${ilmoitus}</div>
    </c:if>      
</head>   
<body>
    <nav class="navbar navbar-inverse">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/Kritiikki/Etusivu">Kirjatietokanta Kritiikki</a>
            </div>
            <c:if test="${kirjautunut != null}">
                <div id="navbar" class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li <c:if test="${pageTitle eq 'Etusivu'}">class="active"</c:if>><a href="/Kritiikki/Etusivu">Etusivu</a></li>
                        <li <c:if test="${pageTitle eq 'Uutiset'}">class="active"</c:if>><a href="#">Uutiset</a></li>
                        <li <c:if test="${pageTitle eq 'Profiili'}">class="active"</c:if>><a href="/Kritiikki/Profiili">Profiili</a></li>
                        <li <c:if test="${pageTitle eq 'Kayttäjatiedot'}">class="active"</c:if>><a href="/Kritiikki/Kayttajatiedot">Käyttäjätiedot</a></li>
                            <c:if test="${kirjautunut.rooli eq 'yllapitaja'}">
                            <li <c:if test="${pageTitle eq 'Kayttajalista'}">class="active"</c:if>><a href="/Kritiikki/Kayttajalista">Käyttäjälista</a></li>
                            </c:if>
                        <li><a href="/Kritiikki/Uloskirjautuminen"><span class="glyphicon glyphicon-log-out"></span>Kirjaudu ulos</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </c:if>
        </div>
    </nav>
    <jsp:doBody/>
</body>
</html>