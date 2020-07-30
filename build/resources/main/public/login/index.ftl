<#include "/public/layout.ftl">

<#macro page_body>
    <div class="center-content" >
        <div class="card" style="width: 18rem;">
            <div class="card-body">
                <h1 class="display-4">Log in</h1>
                <form action="/login" method="post">
                    <div class="input-group mb-3">
                        <input aria-describedby="basic-addon1" aria-label="Username" class="form-control" name="username"
                               placeholder="Username"
                               type="text">
                    </div>
                    <div class="input-group mb-3">
                        <input aria-describedby="basic-addon1" aria-label="Username" class="form-control" name="password"
                               placeholder="Password"
                               type="password">
                    </div>
                    <div class="input-group mb-3">
                        <input type="checkbox" class="form-check-input" id="recordarme" name="remember" value="yes">
                        <label class="form-check-label" for="recordarme">Remember Me</label>
                    </div>
                    <button class="btn btn-secondary" type="submit">Log In</button>
                </form>
            </div>
        </div>
    </div>
</#macro>

<@display_page/>