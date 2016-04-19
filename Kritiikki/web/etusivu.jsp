<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:pohja pageTitle="Etusivu">    
    <c:if test ="${kirjautunut == null}">
        <jsp:forward page="OhjaaKirjautumiseen"></jsp:forward>
    </c:if>
    <div class="modal fade" id="addBook" tabindex="-1" role="dialog"
         aria-labelledby="addBook">
        <div class="modal-dialog modal-lg" role="form">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Sulje"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="addBook">Lis�� kirja</h4>                  
                </div>
                <div class="modal-body">
                    <div class="row">
                            <form class="col-xs-7" role="form">
                                <div class="form-group col-xs-12">
                                    <label for="bookName" class="control-label col-md-3 col-xs-4">Nimi</label>
                                    <div class="col-xs-6">
                                        <input type="text" class="form-control" id="nimi" required value="${kirja.nimi}">
                                    </div>                           
                                </div>
                                <div class="form-group col-xs-12">
                                    <label for="author" class="control-label col-md-3 col-xs-4">Kirjailija</label>
                                    <div class="col-xs-6">
                                        <input type="text" class="form-control" id="kirjailija" value="${kirja.kirjailija}">
                                    </div>                           
                                </div>
                                <div class="form-group col-xs-12">
                                    <label for="year" class="control-label col-md-3 col-xs-4">Julkaisuvuosi</label>
                                    <div class="col-xs-6">
                                        <input type="text" class="form-control" id="julkaisuvuosi" value="${kirja.julkaisuvuosi}">
                                    </div>                           
                                </div>
                                <div class="form-group col-xs-12">
                                    <label for="language" class="control-label col-md-3 col-xs-4">Julkaisukieli</label>
                                    <div class="col-xs-6">
                                        <input type="text" class="form-control" id="julkaisukieli" value="${kirja.julkaisukieli}">
                                    </div>                           
                                </div>
                                <div class="form-group col-xs-12">
                                    <label for="publisher" class="control-label col-md-3 col-xs-4">Suomentaja</label>
                                    <div class="col-xs-6">
                                        <input type="text" class="form-control" id="suomentaja" value="${kirja.suomentaja}">
                                    </div>                           
                                </div>
                                <div class="form-group col-xs-12">
                                    <label for="publisher" class="control-label col-md-3 col-xs-4">Pisteet</label>
                                    <div class="col-xs-6">
                                        <input type="number" class="form-control" id="pisteet" value="${kirja.pisteet}">
                                    </div>                           
                                </div>
                            </form>
                            <div>
                                <p>Lataa kirjan kansi</p>
                            </div>
                        </div>
                                <div class="row">
                                    <form class="col-xs-12" role="form">
                                        <h4>Kirjoita kritiikki</h4>
                                        <div class="form-group">
                                            <label for="otsikko">Otsikko</label>
                                            <input type="text" class="form-control" id="otsikko">
                                            <label for="leipateksti">Leip�teksti</label>
                                            <textarea class="form-control" rows="10" id="leipateksti"></textarea>
                                        </div>                            
                                    </form>
                                </div><!-- tekstikentt� kritiikille -->
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Sulje</button>
                                <button type="submit" class="btn btn-primary">Tallenna</button>
                            </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <div class="container">
                <div class="row">
                    <div class="col-xs-3 form-group">
                        <input type="search" class="form-control" placeholder="Hae">              
                    </div>
                    <div class="col-xs-2">
                        <button type="button" class="btn btn-primary" data-toggle="modal" 
                                data-target="#addBook">Lis�� kirja</button>
                    </div>
                </div>
            </div>
            <div class="container">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Nimi</th>
                            <th>Kirjailija</th>
                            <th>Julkaisuvuosi</th>
                            <th>Julkaisukieli</th>
                            <th>Suomentaja</th>
                            <th>Pisteet</th>
                        </tr>
                        <c:forEach items="${kirjat}" var="tieto" varStatus="index">
                            <tr>
                                <td><c:out value="${index.index + 1}"/></td>
                                <td><a href="Kirja?id=${tieto.id}"><c:out value="${tieto.nimi}" default="-"/></a></td>
                                <td><c:out value="${tieto.kirjailija}" default="-"/></td>
                                <td><c:out value="${tieto.julkaisuvuosi}" default="-"/></td>
                                <td><c:out value="${tieto.julkaisukieli}" default="-"/></td>
                                <td><c:out value="${tieto.suomentaja}" default="-"/></td>
                                <td><c:out value="${tieto.pisteet eq 0 ? '-': tieto.pisteet}" default="-"/></td>
                            </tr>
                        </c:forEach>
                    </thead>
                </table>
            </div>
        </t:pohja>

