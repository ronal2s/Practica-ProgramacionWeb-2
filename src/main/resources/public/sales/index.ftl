<#include "/public/layout.ftl">

<#macro page_body>
    <br><h1 class="text-center">${title}</h1><br>
    <#if admin == true>
        <div class="align-center">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-12">
                        <div>
                            <#list sales as sale>
                                <div class="card">
                                    <h4 class="display-4">${sale.clientName} - ${sale.saleDate}</h4>
                                    <table class="table table-striped table-bordered">
                                        <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Price (RD$)</th>
                                            <th scope="col">Quantity</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#assign total_general = 0>
                                        <#list sale.productList as producto>
                                            <#assign total = producto.quantity * producto.price>
                                            <#assign total_general += total>
                                            <tr>
                                                <td>${producto.id}</td>
                                                <td>${producto.name}</td>
                                                <td>${producto.price}</td>
                                                <td>${producto.quantity}</td>
                                            </tr>
                                        </#list>
                                        </tbody>
                                    </table>
                                    <h5 class="align-right">Total: $ ${total_general}</h5>

                                </div>
                                <div class="separator"/>
                            </#list>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        </div>
    <#else>
        <p class="text-center">You must login as admin to view this page.</p>
    </#if>
</#macro>

<@display_page/>