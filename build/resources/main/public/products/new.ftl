<#include "/public/layout.ftl">

<#macro page_body>
    <br><h1 class="text-center">${title}</h1><br>
    <#if admin == true>
        <div class="row justify-content-center">
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <form action="/products/new" method="post" enctype="multipart/form-data">
                        <h5 >New product</h5>
                        <div class="input-group mb-3">
                            <input aria-describedby="basic-addon1" aria-label="Username" class="form-control" name="name"
                                   placeholder="Name"
                                   type="text">
                        </div>
                        <div class="input-group mb-3">
                            <input aria-describedby="basic-addon1" aria-label="Username" class="form-control" name="price"
                                   placeholder="Price"
                                   type="number">
                        </div>
                        <div class="input-group mb-3">
                            <input aria-describedby="basic-addon1" aria-label="Username" class="form-control" name="description"
                                   placeholder="Description"
                                   type="text">
                        </div>
                        <div class="input-group mb-3">
                            <label for="fotos" class="col-form-label">Picture(s):</label>
                            <input type="file" class="form-control" id="fotos" name="pictures" multiple required>
                        </div>
                        <button class="btn btn-secondary" type="submit">Add</button>
                    </form>
                </div>
            </div>
        </div>
    <#else>
        <p class="text-center">You must login as admin to view this page.</p>
    </#if>
</#macro>

<@display_page/>