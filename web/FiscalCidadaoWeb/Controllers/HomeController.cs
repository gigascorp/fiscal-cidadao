using FiscalCidadaoWeb.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity.Spatial;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace FiscalCidadaoWeb.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            try
            {
                using (var context = new ApplicationDBContext())
                {
                    var weba = context.Convenio.ToList();

                    var point = DbGeography.FromText("POINT(-122.348111 47.651870)", 4326);

                    //distance em metros, dividindo por 1000 transformo em KM
                    var emilio = context.Convenio.Where(x => (x.Coordenadas.Distance(point) / 1000) < 1000).ToList();

                    var a = 1;

                    //                    NpgsqlTypes.NpgsqlPoint

                }

                //var geos = context.PedidoAtualizacaoLocalizacao.ToList();

                //var weba = DbGeography.FromBinary(geos[0].Coordenadas);
            }
            catch (Exception ex)
            {
                var a = 1;
            }


            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }
    }
}