<#include "/public/layout.ftl">

<#macro page_body>
    <#if admin == true>
        <br><h1 class="text-center">${title}</h1><br>
        <div class="align-center">
            <div class="col-6">
                <a class="btn btn-dark" href="/products/new">Add New</a><br>
                <table class="table table-striped table-bordered">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Price</th>
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
                                <a class="btn btn-primary" href="/products/view/${prod.id}">View</a>
                                <#if admin == true>
                                    <a class="btn btn-secondary" href="/products/edit/${prod.id}">Edit</a>
                                    <a class="btn btn-danger" href="/products/delete/${prod.id}">Delete</a>
                                </#if>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    <#else>
        <p class="text-center">You must login as admin to view this page.</p>
    </#if>
</#macro>

<@display_page/>