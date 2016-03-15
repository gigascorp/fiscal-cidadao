using FiscalCidadaoWeb.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Entity;
using System.Globalization;

namespace FiscalCidadaoWeb.Controllers
{
    public class ConvenioController : Controller
    {
        [HttpGet]
        public ActionResult ListagemConvenios()
        {
            List<ListagemConvenioViewModel> lista = new List<ListagemConvenioViewModel>();

            try
            {
                using (var context = new ApplicationDBContext())
                {
                    var listConvenio = context.Convenio
                        .Include(x => x.ParecerGoverno)
                        .Include(x => x.Situacao)
                        .Include(x => x.Denuncias)
                        .ToList();

                    foreach (var convenio in listConvenio)
                    {
                        lista.Add(new ListagemConvenioViewModel()
                        {
                            Id = convenio.Id,
                            Objeto = convenio.DescricaoObjeto,
                            ParecerGoverno = convenio.ParecerGoverno,
                            ParecerId = convenio.ParecerGovernoId,
                            Situacao = convenio.Situacao,
                            SituacaoId = convenio.SituacaoId,
                            CountDenuncias = convenio.Denuncias.Count
                        });
                    }

                }
            }
            catch (Exception ex)
            {

            }

            return View(lista);
        }

        [HttpGet]
        public ActionResult DetalhesConvenio(string id)
        {
            int convenioId;

            DetalhesConvenioViewModel retorno = new DetalhesConvenioViewModel();

            if (string.IsNullOrEmpty(id) || !int.TryParse(id, out convenioId))
            {
                return View(retorno);
            }

            try
            {
                using (var context = new ApplicationDBContext())
                {
                    var convenio = context.Convenio
                        .Include(x => x.Denuncias)
                        .Include(x => x.Proponente)
                        .Include(x => x.Situacao)
                        .Include(x => x.ParecerGoverno)
                        .FirstOrDefault(x => x.Id == convenioId);

                    retorno.Id = convenioId;
                    retorno.Objeto = convenio.DescricaoObjeto;
                    retorno.DataInicio = convenio.DataInicio;
                    retorno.DataFim = convenio.DataFim;
                    retorno.ConcedenteNome = convenio.ConcedenteNome;
                    retorno.ProponenteNome = convenio.Proponente.Nome;
                    retorno.SincovId = convenio.SincovId;
                    retorno.Situacao = convenio.Situacao.Descricao;
                    retorno.Denuncias = convenio.Denuncias;
                    retorno.Parecer = convenio.ParecerGoverno;

                    retorno.Valor = convenio.ValorTotal.ToString("C2", new CultureInfo("pt-BR").NumberFormat);
                }
            }
            catch (Exception ex)
            {

            }

            return View(retorno);
        }

    }
}