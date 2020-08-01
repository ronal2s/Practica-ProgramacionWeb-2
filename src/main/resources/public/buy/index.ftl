<#include "/public/layout.ftl">

<#macro page_body>
    <br><h1 class="text-center">${title}</h1><br>
    <div class="row justify-content-center">
        <div class="col-12">
            <table class="table table-striped table-bordered">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <#list products as prod>
                    <tr>
                        <td>${prod.id}</td>
                        <td>${prod.name}</td>
                        <td>${prod.price}</td>
                        <td>
                            <form method="post" id="form${prod.id}" action="/cart/new/${prod.id}">
                                <input type="number" min="1" class="form-control" id="cantidad" name="cantidad" value="1">
                            </form>
                        </td>
                        <td>
                            <a class="btn btn-primary" href="/products/view/${prod.id}">View</a>
                            <button type="submit" form="form${prod.id}" class="btn btn-success">Add to Cart</button>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</#macro>

<@display_page/>