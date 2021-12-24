package expensesheetsystem

class UrlMappings {


    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/landing") //maps the default landing page to landing.gsp
        "/logged"(view: "/users/defaultLanding")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
