<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:pohja pageTitle="Kirja">
    <c:if test ="${kirjautunut == null}">
        <jsp:forward page="OhjaaKirjautumiseen"></jsp:forward>
    </c:if>
    <div class="container">
        <div class="row">
            <div class="col-xs-9">
                <dl class="dl-horizontal">
                    <dt>Nimi</dt>
                    <dd><c:out value="${kirja.nimi}" default="-"/></dd>
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
            </div>
            <div class="img-responsive col-xs-3">
                <h4>Kirjan kansi</h4>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <form class="form-inline" role="form">
                    <div class="form-group">
                        <label for="points">Arvostele:</label>
                        <input type="number" class="form-control" id="points" style="max-width:100px"
                               placeholder="4-10">
                        <button type="submit" class="btn btn-primary">Tallenna</button>
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#openCritiquField">
                            Kirjoita kritiikki
                        </button> <!-- Button trigger modal -->
                    </div>
                </form>
            </div>
            <div class="help-block"></div>
        </div>
        <div class="col-md-offset-1">
        </div>
        <div class="modal fade" id="avaaKritiikkiKentt‰" tabindex="-1" role="dialog"
             aria-labelledby="avaaKritiiki">
            <div class="modal-dialog modal-lg" role="form">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-label="Sulje"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="addCritique">Kirjoita kritiikki</h4>                  
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <form role="form">
                                <div class="form-group col-xs-12">
                                    <label for="otsikko">Otsikko</label>
                                    <input type="text" class="form-control" id="otsikko">
                                    <label for="kritiikki">Leip‰teksti</label>
                                    <textarea class="form-control" rows="30" id="kritiikki"></textarea>
                                </div>                            
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Sulje</button>
                            <button type="submit" class="btn btn-primary">Tallenna</button>
                        </div>
                    </div><!--.modal-body-->
                </div><!--.modal-content-->
            </div><!--.modal-dialob-->
        </div>
    </div><!-- container -->
    <div class="help-block"></div>
    <div class="container">
        <div class="row">
            <div class = "col-xs-12">
                <div class="panel-group" id="kritiikit">
                    <div class="panel panel-default">
                        <div class="panel-heading" id="heading">
                            <h4 class="panel-title">Kritiikin kirjoittaja, p‰iv‰m‰‰r‰</h4>
                        </div>
                        <div class="panel-body">
                            <h5>Otsikko</h5>
                            <article><p>Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson 
                                    ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. 
                                    Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua 
                                    put a bird on it squid single-origin coffee nulla assumenda shoreditch et. 
                                    Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente 
                                    ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft 
                                    beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't 
                                    heard of them accusamus labore sustainable VHS.Anim pariatur cliche reprehenderit, 
                                    enim eiusmod high life accusamus terry richardson ad squid.
                                    3 wolf moon officia aute, non cupidatat skateboard dolor brunch. 
                                    Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua 
                                    put a bird on it squid single-origin coffee nulla assumenda shoreditch et. 
                                    Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente 
                                    ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft 
                                    beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't 
                                    heard of them accusamus labore sustainable VHS.Anim pariatur cliche reprehenderit, 
                                    enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, 
                                    non cupidatat skateboard dolor brunch.</p> 
                                <p>Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua 
                                    put a bird on it squid single-origin coffee nulla assumenda shoreditch et. 
                                    Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente 
                                    ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft 
                                    beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't 
                                    heard of them accusamus labore sustainable VHS.Anim pariatur cliche reprehenderit, 
                                    enim eiusmod high life accusamus terry richardson ad squid.
                                    3 wolf moon officia aute, non cupidatat skateboard dolor brunch. 
                                    Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua 
                                    put a bird on it squid single-origin coffee nulla assumenda shoreditch et. 
                                    Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente 
                                    ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft 
                                    beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't 
                                    heard of them accusamus labore sustainable VHS.</p></article>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- container -->
    <script>$("article").readmore({
            collapsedHeight: 120,
            moreLink: '<a href="#">Lue lis‰‰</a>',
            lessLink: '<a href="#">Sulje</a>'
        });</script>
</t:pohja>