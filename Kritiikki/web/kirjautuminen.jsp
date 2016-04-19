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
                    <a href="Rekisteröityminen">Rekisteröidy</a>
                </div>
            </div>
        </div>
    </div>
</t:pohja>
