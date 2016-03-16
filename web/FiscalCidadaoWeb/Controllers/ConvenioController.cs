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
            ListagemConvenioViewModel viewModel = new ListagemConvenioViewModel();
            viewModel.Lista = new List<ListaConvenioViewModel>();

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
                        viewModel.Lista.Add(new ListaConvenioViewModel()
                        {
                            Id = convenio.Id,
                            Objeto = convenio.DescricaoObjeto,
                            ParecerGoverno = convenio.ParecerGoverno,
                            ParecerId = convenio.ParecerGovernoId,
                            Situacao = convenio.Situacao,
                            SituacaoId = convenio.SituacaoId,
                            CountDenuncias = convenio.Denuncias.Count
                        });

                        viewModel.Parecer = convenio.ParecerGoverno.Parecer;
                    }


                    viewModel.ListaParecer = GetSelectListItems(GetAllParecer(context));
                }
            }
            catch (Exception ex)
            {

            }

            return View(viewModel);
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
                    retorno.ListaParecer = GetSelectListItems(GetAllParecer(context));
                }
            }
            catch (Exception ex)
            {

            }

            return View(retorno);
        }

        public void AtualizarParecer(string parecer, string convenio)
        {
            try
            {
                using (var context = new ApplicationDBContext())
                {
                    var parecerId = int.Parse(parecer);
                    var convenioId = int.Parse(convenio);

                    var convenioDB = context.Convenio.Single(x => x.Id == convenioId);

                    convenioDB.ParecerGovernoId = parecerId;

                    context.SaveChanges();
                }
            }
            catch (Exception ex)
            {

            }
        }

        [HttpPost]
        public PartialViewResult FiltroConvenios(string filtroDescricao, string dataInicio, string dataFim)
        {
            ListagemConvenioViewModel viewModel = new ListagemConvenioViewModel();
            viewModel.Lista = new List<ListaConvenioViewModel>();

            try
            {
                DateTime inicioOut;
                DateTime fimOut;

                DateTime.TryParse(dataInicio, out inicioOut);
                DateTime.TryParse(dataFim, out fimOut);

                using (var context = new ApplicationDBContext())
                {
                    var listConvenio = context.Convenio.Include(x => x.ParecerGoverno)
                                    .Include(x => x.Situacao)
                                    .Include(x => x.Denuncias)
                                    .Where(x =>
                                        (!string.IsNullOrEmpty(filtroDescricao) ? x.DescricaoObjeto.ToUpper().Contains(filtroDescricao.ToUpper()) : true)
                                        && (!string.IsNullOrEmpty(dataInicio) ? x.DataInicio == inicioOut : true)
                                        && (!string.IsNullOrEmpty(dataFim) ? x.DataFim == fimOut : true))
                                    .ToList();

                    foreach (var convenio in listConvenio)
                    {
                        viewModel.Lista.Add(new ListaConvenioViewModel()
                        {
                            Id = convenio.Id,
                            Objeto = convenio.DescricaoObjeto,
                            ParecerGoverno = convenio.ParecerGoverno,
                            ParecerId = convenio.ParecerGovernoId,
                            Situacao = convenio.Situacao,
                            SituacaoId = convenio.SituacaoId,
                            CountDenuncias = convenio.Denuncias.Count
                        });

                        viewModel.Parecer = convenio.ParecerGoverno.Parecer;
                    }

                    viewModel.ListaParecer = GetSelectListItems(GetAllParecer(context));
                }
            }
            catch (Exception ex)
            {

            }

            return PartialView("_ListagemPartial", viewModel);
        }

        static private Dictionary<string, string> GetAllParecer(ApplicationDBContext context)
        {
            Dictionary<string, string> dic = new Dictionary<string, string>();

            var pareceres = context.ParecerGoverno.ToList();

            foreach (var parecer in pareceres)
            {
                dic.Add(parecer.Id.ToString(), parecer.Parecer);
            }

            return dic;
        }

        private static IEnumerable<SelectListItem> GetSelectListItems(Dictionary<string, string> elements)
        {
            var selectList = new List<SelectListItem>();

            foreach (var element in elements)
            {
                selectList.Add(new SelectListItem
                {
                    Value = element.Key,
                    Text = element.Value
                });
            }

            return selectList;
        }

    }
}