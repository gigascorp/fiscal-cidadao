using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;

namespace FiscalCidadaoWeb
{
    public class RouteConfig
    {
        public static void RegisterRoutes(RouteCollection routes)
        {
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");

            routes.MapRoute(
               name: "Listagem Convenios",
               url: "ListagemConvenios",
               defaults: new { controller = "Convenio", action = "ListagemConvenios", id = UrlParameter.Optional }
           );

            routes.MapRoute(
               name: "Detalhes Convenios",
               url: "DetalhesConvenio/{id}",
               defaults: new { controller = "Convenio", action = "DetalhesConvenio", id = UrlParameter.Optional }
           );

            routes.MapRoute(
                name: "Default",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Home", action = "Index", id = UrlParameter.Optional }
            );
        }
    }
}
