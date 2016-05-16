<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:pohja pageTitle="Kayttajatiedot">
    <c:if test ="${kirjautunut == null}">
        <jsp:forward page="OhjaaKirjautumiseen"></jsp:forward>
    </c:if>
    <div class="container">
        <div class="row">
            <div class=" col-xs-4 col-xs-offset-4">
                <dl class="dl-horizontal">
                    <dt>Käyttäjätunnus:</dt> 
                    <dd><c:out value="${kirjautunut.id}" default="-"/></dd>
                </dl>
            </div>
            <div class="col-xs-12">
                <form id="kirjanTiedot" role="form" action="KayttajaTietojenMuokkaus" method="POST">
                    <div class="form-group col-xs-12">
                        <label for="author" class="control-label col-md-3 col-xs-4">Sähköposti</label>
                        <div class="col-xs-6">
                            <input type="text" class="form-control" name="sposti" value="${kirjautunut.sposti}"/>
                        </div>                           
                    </div>
                    <div class="form-group col-xs-12">
                        <label for="year" class="control-label col-md-3 col-xs-4">Salasana</label>
                        <div class="col-xs-6">
                            <input type="password" class="form-control" name="salasana" value="${kirjautunut.salasana}"/>
                        </div>
                    </div>
                    <input type="hidden" name="id" value="${kirjautunut.id}"/>
                    <div class="form-group col-xs-12">
                        <div class="col-xs-6 col-xs-offset-4">
                            <button type="submit" class="btn btn-primary">Tallenna</button>
                            <a href="#poisto" class="btn btn-danger" data-toggle="modal">Poista</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="modal fade" id="poisto" tabindex="-1" role="dialog"
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
</t:pohja>
