<#include "loginBase.ftl">
<head>
<link href="/templates/css/globalStyles.css" rel="stylesheet">
</head>

<#macro page_body>
    <form class="form-signin" action="/register" method="post" id="formula">
        <h1 class="h3 mb-3 font-weight-normal">Registro</h1>
        <label for="name" class="sr-only">Nombre completo</label>
        <input type="text" id="name" name="name" class="form-control" placeholder="Nombre" required autofocus>
        <label for="user" class="sr-only">Usuario</label>
        <input type="text" id="user" name="user" class="form-control" placeholder="Usuario" required autofocus>
        <label for="password"  class="sr-only">Contrase&#241a</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Contrase&#241a" required>
        <button class="btn btn-lg btn-light btn-block" type="submit" form="formula" onclick="agregarUsuario()">Registrar</button>
        <a class="btn btn-lg btn-secondary btn-block " type="submit" href="/login">Volver Atras</a>
    </form>
</#macro>
<@display_page/>