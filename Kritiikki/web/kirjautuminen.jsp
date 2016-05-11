<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Kirjautuminen">
    <div class="container">      
        <form class="form-horizontal" role="form" action="Kirjautuminen" method="POST">
            <div class="form-group" style="max-width:500px">
                <label for="id" class="col-xs-2 control-label">Tunnus</label>
                <div class="col-xs-10">
                    <input type="text" class="form-control"
                           placeholder="Käyttäjätunnus" name="id" value="${kayttaja}">
                </div>
            </div>
            <div class="form-group" style="max-width:500px">
                <label for="salasana" class="col-xs-2 control-label">Salasana</label>
                <div class="col-xs-10">
                    <input type="password" class="form-control"
                           placeholder="Salasana" name="salasana">
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-offset-2 col-xs-2">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox">Muista minut
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-offset-2 col-xs-10">
                    <button type="submit" class="btn btn-default">Kirjaudu</button>
                </div>
            </div>
        </form>
        <div class="container">
            <div class="row">
                <div class="col-xs-offset-2">
                    <a href="#" data-toggle="modal" data-target="#rekisteroityminen">Rekisteröidy</a>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="modal fade" id="rekisteroityminen" tabindex="-1" role="dialog"
                 aria-labelldby="Muokka tietoja">
                <div class="modal-dialog" role="form">
                    <div class="modal-content">
                        <div class="modal-header"> 
                            <button type="button" class="close" data-dismiss="modal"
                                    aria-label="Sulje"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="rekisteroityminen">Anna käyttäjätiedot</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <form class="col-xs-7 col-xs-offset-1" id="rekist" role="form" action="Rekisteroityminen" method="POST">
                                    <div class="form-group col-xs-12">
                                        <label for="kayttajatunnus" class="control-label col-xs-4">Käyttäjätunnus</label>
                                        <div class="col-xs-7 col-xs-offset-1">
                                            <input type="text" class="form-control" name="id" required value="${id}"/>
                                        </div>                           
                                    </div>
                                    <div class="form-group col-xs-12">
                                        <label for="sahkoposti" class="control-label col-xs-4">Sähköposti</label>
                                        <div class="col-xs-7 col-xs-offset-1">
                                            <input type="text" class="form-control" name="sposti" required value="${sposti}"/>
                                        </div>                           
                                    </div>
                                    <div class="form-group col-xs-12">
                                        <label for="salasana" class="control-label col-xs-4">Salasana</label>
                                        <div class="col-xs-7 col-xs-offset-1">
                                            <input type="password" class="form-control" name="salasana" required value="${salasana}"/>
                                        </div>                           
                                    </div>
                                </form>
                            </div>
                        </div>   
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Sulje</button>
                            <button type="submit" form="rekist" class="btn btn-primary">Tallenna</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:pohja>
