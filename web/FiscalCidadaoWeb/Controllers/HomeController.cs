using FiscalCidadaoWeb.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity.Spatial;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Entity;

namespace FiscalCidadaoWeb.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            HomeViewModel retorno = new HomeViewModel();
            try
            {
                using (var context = new ApplicationDBContext())
                {
                    retorno.NomeUsuario = "Renier"; // tem que mudar

                    retorno.MaisDenunciados = context.Denuncia.Include(x => x.Convenio)
                        .GroupBy(x => new { x.ConvenioId, x.Convenio.DescricaoObjeto })
                        .Select(x => new MaisDenunciados()
                        {
                            Id = x.Key.ConvenioId,
                            Objeto = x.Key.DescricaoObjeto,
                            Count = x.Count()
                        })
                        .OrderByDescending(x => x.Count)
                        .Take(3) // top 3
                        .ToList();

                    retorno.CountDenunciasNaoAnalisadas = context.Convenio.Include(x => x.ParecerGoverno)
                        .Where(x => x.ParecerGoverno.Id == 1 && x.Denuncias.Count > 0) // Nao analisado e denunciado
                        .Count();

                    retorno.CountAtualizacoesEndereco = context.PedidoAtualizacaoLocalizacao
                        .Where(x => !x.Avaliado) // Nao avaliado
                        .Count();
                }

            }
            catch (Exception ex)
            {
                var a = 1;
            }

            return View(retorno);
        }

    }
}