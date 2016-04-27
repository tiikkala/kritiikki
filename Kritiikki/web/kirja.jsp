<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:pohja pageTitle="Kirja">
    <c:if test ="${kirjautunut == null}">
        <jsp:forward page="OhjaaKirjautumiseen"></jsp:forward>
    </c:if>
    <div class="container">
        <div class="row">
            <button-glyphicon class="col-xs-offset-2"><i class="glyphicon glyphicon-pencil" data-target="#muokkaus" data-toggle="modal"></i></button-glyhicon>
                <button-glyphicon><i class="glyphicon glyphicon-remove" data-target="#poisto" data-toggle="modal"></i></button-glyhicon>
                    <dl class="dl-horizontal col-xs-3">
                        <dt>Nimi</dt> 
                        <dd><c:out value="${kirja.nimi}" default="-"/>                 
                        </dd>
                        <dt>Kirjailija</dt> 
                        <dd><c:out value="${kirja.kirjailija}" default="-"/></dd>    
                        <dt>Pisteet</dt>
                        <dd><c:out value="${kirja.pisteet eq 0 ? '-': kirja.pisteet}" default="-"/></dd>    
                        <dt>Julkaisuvuosi</dt>
                        <dd><c:out value="${kirja.julkaisuvuosi}" default="-"/></dd>  
                        <dt>Julkaisukieli</dt>
                        <dd><c:out value="${kirja.julkaisukieli}" default="-"/></dd>
                        <dt>Suomentaja</dt>
                        <dd><c:out value="${kirja.suomentaja}" default="-"/></dd>
                    </dl>
                    <div class="img-responsive col-xs-6 pull-right">
                        <h4>Kirjan kansi</h4>
                    </div>
                    </div>
                    </div>
                    <div class="row">
                        <div class="modal fade" id="poisto" tabindex="-1" role="dialog"
                             aria-labelldby="Poista">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header"> <button type="button" class="close" data-dismiss="modal"
                                                                       aria-label="Ei"><span aria-hidden="true">&times;</span></button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Oletko varma, että haluat poistaa kaikki kirjaan liittyvät tiedot?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Ei</button>
                                        <a class="btn btn-danger btn-ok" href="KirjanPoisto">Kyllä</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="modal fade" id="muokkaus" tabindex="-1" role="dialog"
                                 aria-labelldby="Muokka tietoja">
                                <div class="modal-dialog modal-lg" role="form">
                                    <div class="modal-content">
                                        <div class="modal-header"> <button type="button" class="close" data-dismiss="modal"
                                                                           aria-label="Sulje"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="avaaKritiikkiKenttä">Muokkaa kirjan tietoja</h4>
                                        </div>
                                        <div class="modal-body">
                                            <div class="row">
                                                <form class="col-xs-7" id="kirjanTiedot" role="form" action="KirjanMuokkaus" method="POST">
                                                    <div class="form-group col-xs-12">
                                                        <label for="bookName" class="control-label col-md-3 col-xs-4">Nimi</label>
                                                        <div class="col-xs-6">
                                                            <input type="text" class="form-control" name="nimi" required value="${kirja.nimi}">
                                                        </div>                           
                                                    </div>
                                                    <div class="form-group col-xs-12">
                                                        <label for="author" class="control-label col-md-3 col-xs-4">Kirjailija</label>
                                                        <div class="col-xs-6">
                                                            <input type="text" class="form-control" name="kirjailija" value="${kirja.kirjailija}">
                                                        </div>                           
                                                    </div>
                                                    <div class="form-group col-xs-12">
                                                        <label for="year" class="control-label col-md-3 col-xs-4">Julkaisuvuosi</label>
                                                        <div class="col-xs-6">
                                                            <input type="text" class="form-control" name="julkaisuvuosi" value="${kirja.julkaisuvuosi}">
                                                        </div>                           
                                                    </div>
                                                    <div class="form-group col-xs-12">
                                                        <label for="language" class="control-label col-md-3 col-xs-4">Julkaisukieli</label>
                                                        <div class="col-xs-6">
                                                            <input type="text" class="form-control" name="julkaisukieli" value="${kirja.julkaisukieli}">
                                                        </div>                           
                                                    </div>
                                                    <div class="form-group col-xs-12">
                                                        <label for="publisher" class="control-label col-md-3 col-xs-4">Suomentaja</label>
                                                        <div class="col-xs-6">
                                                            <input type="text" class="form-control" name="suomentaja" value="${kirja.suomentaja}">
                                                        </div>                           
                                                    </div>
                                                </form>
                                                <div>
                                                    <p>Lataa kirjan kansi</p>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Sulje</button>
                                                <button type="submit" class="btn btn-primary" form="kirjanTiedot">Tallenna</button>
                                            </div>
                                        </div><!--modal-body-->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <form class="form-inline col-xs-12" role="form">
                                <div class="form-group col-xs-12 col-xs-offset-2">
                                    <label for="points">Arvostele:</label>
                                    <input type="number" class="form-control" id="points" style="max-width:100px"
                                           placeholder="4-10">
                                    <button type="submit" class="btn btn-primary">Tallenna</button>
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#kritiikkikentta">
                                        Kirjoita kritiikki
                                    </button> <!-- Button trigger modal -->
                                </div>
                            </form>
                        </div>
                        <div class="help-block"></div>
                    </div>
                    <div class="col-xs-offset-1">
                    </div>
                    <div class="modal fade" id="kritiikkikentta" tabindex="-1" role="dialog"
                         aria-labelledby="Avaa krtitiikkiruutu">
                        <div class="modal-dialog modal-lg" role="form">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-label="Sulje"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="avaaKritiikkiKenttä">Kirjoita kritiikki</h4>                  
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <form role="form" id="kritiikinLisays" action="KritiikinLisays" method="POST">
                                            <div class="form-group col-xs-12">
                                                <label for="otsikko">Otsikko</label>
                                                <input type="text" class="form-control" id="otsikko" required name="otsikko">
                                                <label for="kritiikki">Leipäteksti</label>
                                                <textarea class="form-control" rows="30" id="kritiikki" required name="teksti"></textarea>
                                            </div>                            
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Sulje</button>
                                        <button type="submit" form="kritiikinLisays" class="btn btn-primary">Tallenna</button>
                                    </div>
                                </div><!--.modal-body-->
                            </div><!--.modal-content-->
                        </div><!--.modal-dialob-->
                    </div>
                    </div><!-- container -->
                    <div class="help-block"></div>
                    <div class="container">
                        <c:forEach items="${kritiikit}" var="kritiikki">
                            <div class="row">
                                <div class = "col-xs-12">
                                    <div class="panel-group" id="kritiikit">
                                        <div class="panel panel-default">
                                            <div class="panel-heading" id="heading">
                                                <h4 class="panel-title"><c:out value="${kritiikki.kirjoittaja}, ${kritiikki.paivays}"/></h4>
                                            </div>
                                            <div class="panel-body">
                                                <h5><c:out value="${kritiikki.otsikko}"/></h5>
                                                <article>
                                                    <p><c:out value="${kritiikki.teksti}"/></p>
                                                </article>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div><!-- container -->
                    <script>$("article").readmore({
                            collapsedHeight: 120,
                            moreLink: '<a href="#">Lue lisää</a>',
                            lessLink: '<a href="#">Sulje</a>'
                        });</script>
                </t:pohja>