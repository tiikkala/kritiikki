<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:pohja pageTitle="Kayttajalista">    
    <c:if test ="${kirjautunut == null}">
        <jsp:forward page="OhjaaKirjautumiseen"></jsp:forward>
    </c:if>
    <div class="container">
        <div class="row">
            <form class="col-xs-3 form-group" action="Kayttajahaku" method="POST">
                <input name="hakusana" class="form-control" placeholder="Hae"/>
                <input type="submit" style="visibility: hidden;" />
            </form>
        </div>
        <ul>
            <c:forEach items="${kayttajat}" var="kayttaja">
                <div class="row">
                    <li class="col-xs-10" style="padding-bottom: 6px"><c:out value="${kayttaja.id}"/>, <c:out value="${kayttaja.sposti}"/>,
                        <c:out value="${kayttaja.rooli}"/><div class="btn-toolbar pull-right"> <button data-target="#poisto<c:out value="${kayttaja.id}"/>" class="btn btn-sm btn-danger" data-toggle="modal">Poista</button>
                            <button data-target="#muokkaa<c:out value="${kayttaja.id}"/>" class="btn btn-sm btn-primary" data-toggle="modal">Muokkaa oikeuksia</button></div></li>
                </div>
                <div class="row">
                    <div class="modal fade" id="poisto<c:out value="${kayttaja.id}"/>" tabindex="-1" role="dialog"
                         aria-labelldby="Poista">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header"> <button type="button" class="close" data-dismiss="modal"
                                                                   aria-label="Ei"><span aria-hidden="true">&times;</span></button>
                                </div>
                                <div class="modal-body">
                                    <p>Oletko varma, että haluat poistaa käyttäjätilin?</p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Ei</button>
                                    <a class="btn btn-danger btn-ok" href="KayttajanPoisto">Kyllä</a>
                                </div>
                            </div>
                        </div>
                    </div> <!-- modal-poista -->
                </div>
                <div class="row">
                    <div class="modal fade" id="muokkaa<c:out value="${kayttaja.id}"/>" tabindex="-1" role="dialog"
                         aria-labelldby="Poista">
                        <div class="modal-dialog" role="form">
                            <div class="modal-content">
                                <div class="modal-header"> <button type="button" class="close" data-dismiss="modal"
                                                                   aria-label="Ei"><span aria-hidden="true">&times;</span></button>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <form role="form" id="muokkaa<c:out value="${kayttaja.id}"/>" action="RoolinMuokkaus" method="POST">
                                            <div class="form-group col-xs-12">
                                                <label for="muokkaaRoolia">Rooli</label>
                                                <textfield input="text" class="form-control" id="muokkaaRoolia" required name="rooli"><c:out value="${kayttaja.rooli}"/></textfield>
                                                <input type="hidden" name ="id" value="${kayttaja.rooli}"/>
                                            </div>  
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Sulje</button>
                                        <button type="submit" form="muokkaa<c:out value="${kayttaja.id}"/>" class="btn btn-danger btn-ok">Tallenna</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div> <!-- modal-poista -->
            </c:forEach>
        </ul>
    </div>
</t:pohja>
