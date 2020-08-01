<#include "/public/layout.ftl">

<#macro page_body>
    <br><h1 class="text-center">${title}</h1><br>
    <#if admin == true>
        <div class="row justify-content-center">
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <form action="/products/edit/${product.id}" method="post">
                        <h5 >Edit product</h5>
                        <div class="input-group mb-3">
                            <input  aria-describedby="basic-addon1" name="id"
                                    placeholder="ID" value="${product.id}"
                                    type="text" class="hide" >
                        </div>
                        <div class="input-group mb-3">
                            <input aria-describedby="basic-addon1" class="form-control" name="name"
                                   placeholder="Name" value="${product.name}"
                                   type="text">
                        </div>
                        <div class="input-group mb-3">
                            <input aria-describedby="basic-addon1" class="form-control" name="price"
                                   placeholder="Price" value="${product.price}"
                                   type="text">
                        </div>
                        <button class="btn btn-secondary" type="submit">Edit</button>
                    </form>

                </div>
            </div>
        </div>
    <#else>
        <p class="text-center">You must login as admin to view this page.</p>
    </#if>
</#macro>

<@display_page/>