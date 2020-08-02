<#include "/public/layout.ftl">

<#macro page_body>
<br><h1 class="text-center">${title}</h1>
<div class="row justify-content-center">
    <div class="card w-75">
        <h3 class="card-header">Client Information</h3>
        <div class="card-body">
            <form class="form-inline" id="cartform" method="post" action="/cart/sale/">
                <div class="form-group ml-5">
                    <label for="nombre">Client's Name:</label>
                    <#if usr == true>
                    <input type="text" class="form-control mx-sm-3" id="nombre" name="name" value="${usuario.name}" required>
                    <#else>
                    <input type="text" class="form-control mx-sm-3" id="nombre" name="name" required>
                    </#if>
                </div>
            </form>
        </div>
    </div>
</div>
<br>

<div class="row justify-content-center">
    <table class="table table-bordered table-hover" style="width: 80%">
        <thead class="thead-dark text-center">
        <th>Product</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Total ($)</th>
        <th>Action</th>
        </thead>
        <tbody class="text-center">
        <#assign total_carrito = 0>
        <#if carrito.productList?size gt 0>
        <#list carrito.productList as producto>
        <tr>
            <td>${producto.name}</td>
            <td>${producto.price}</td>
            <td>${producto.quantity}</td>
            <#assign total = producto.quantity * producto.price>
            <#assign total_carrito += total>
            <td>${total}</td>
            <td>
                <a class="btn btn-danger" href="/cart/delete/${producto.id}">Delete</a>
            </td>
        </tr>
        </#list>
    </#if>
    <tr class="table-info">
        <td></td>
        <td></td>
        <td></td>
        <td><b>Grand Total: </b></td>
        <td>$ ${total_carrito}</td>
    </tr>
    </tbody>
    </table>
</div>
<br>
<div class="text-center">
    <button type="submit" form="cartform" class="btn btn-primary">Process Sale</button>
    <a class="btn btn-danger" href="/cart/clean">Clean Shopping Cart</a>
</div>

</#macro>

<@display_page/>