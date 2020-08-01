<#include "/public/layout.ftl">

<#macro page_body>
    <div class="container text-center">
        <br><u><h1>${title}</h1></u><hr>
        <h2 class="text-center">Product Information</h2>
        <div class="card" >
            <#list pictures as foto>
                <img src="data:${foto.mimeType};base64,${foto.pictureBase64}" class="card-img-top" alt="Product Image" width="auto" height="auto" style="height: 50%; width: 50%; display: block; margin-left: auto; margin-right: auto">
            </#list>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4">
                        <h4><b>Name:</b></h4>
                        <input type="text" value="${product.name}" readonly>
                    </div>
                    <div class="col-md-4">
                        <h4><b>Price:</b></h4>
                        <input type="text" value="${product.price}" readonly>
                    </div>
                    <div class="col-md-4">
                        <h4><b>Description:</b></h4>
                        <textarea rows="4" cols="45">${product.description}</textarea>
                    </div>
                </div>
            </div>
        </div>
    </div><br>

    <#if usr == true>
        <div class="row justify-content-center text-center">
            <form action="/products/comment/${product.id}" method="post">
                <br><h2><b>Add comments:</b></h2><br>
                <textarea name="comment" id="comentario" cols="50" rows="4" placeholder="Write you comment here..."></textarea>
                <br><button type="submit" class="btn btn-primary">Add Comment</button>
            </form>
        </div>
    </#if>

    <br><h1 class="text-center"><b>Comments</b></h1><br>
    <div class="row justify-content-center">
        <table class="table table-bordered table-hover" style="width: 80%">
            <thead class="thead-light text-center">
            <th>User</th>
            <th>Date</th>
            <th>Comment</th>
            <th>Action</th>
            </thead>
            <tbody class="text-center">
            <#list comentarios as comment>
                <tr>
                    <td>${comment.userComment}</td>
                    <td>${comment.creationDate}</td>
                    <td>${comment.content}</td>
                    <#if admin == true>
                        <td>
                            <a class="btn btn-danger" href="/products/comment/delete/${comment.id}">Delete</a>
                        </td>
                    </#if>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</#macro>

<@display_page/>