<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:pohja pageTitle="Etusivu">    
    <c:if test ="${kirjautunut == null}">
        <jsp:forward page="OhjaaKirjautumiseen"></jsp:forward>
    </c:if>
    <div class="container">
        <div class="row">
            <div class="col-xs-8">
                <h4>Omat kritiikit ja kommentit</h4>
                <div class="col-xs-6">
                    <div class="list-group">
                        <c:forEach items="${kommentit}" var="kommentti">
                            <a href="#" class="list-group-item active">
                                <h5 class="list-group-item-heading">
                                    <c:out value="${kommentti.kirjoittaja}, 
                                           ${kommentti.paivays}"/></h5>
                                <p class="list-group-item-text"><c:out value="${kommentti.teksti}"/></p>               
                            </a>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-xs-6">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Kirja</th>
                                <th>Kirjailija</th>
                                <th>Kritiikki</th>
                            </tr>
                            <c:forEach items="${kritiikit}" var="kritiikki" varStatus="status">
                                <c:if test="${not empty kritiikit}">
                                    <tr>
                                        <td><c:out value="${kritikoidutKirjat[status.index].nimi}"/></td>
                                        <td><c:out value="${kritikoidutKirjat[status.index].kirjailija}" default="-"/></td>
                                        <td><a href="Kirja?id=${kritikoidutKirjat[status.index].id}"><c:out value="${kritiikki.otsikko}"/></a></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </thead>
                    </table>
                </div>
            </div>
            <div class="col-xs-4">
                <h4>Arvostelemasi kirjat</h4>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Nimi</th>
                            <th>Kirjailija</th>
                            <th>Pisteet</th>
                        </tr>
                        <c:forEach items="${kirjat}" var="tieto" varStatus="status">
                            <tr>
                                <td><c:out value="${status.index + 1}"/></td>
                                <td><a href="Kirja?id=${tieto.id}"><c:out value="${tieto.nimi}" default="-"/></a></td>
                                <td><c:out value="${tieto.kirjailija}" default="-"/></td>
                                <td><c:out value="${tieto.pisteet eq 0 ? '-': tieto.pisteet}" default="-"/></td>
                            </tr>
                        </c:forEach>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</t:pohja>