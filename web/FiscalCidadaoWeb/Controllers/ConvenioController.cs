/* Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guiherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

  This file is part of Fiscal Cidadão.

  Fiscal Cidadão is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  Fiscal Cidadão is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with Fiscal Cidadão.  If not, see <http://www.gnu.org/licenses/>. */

using FiscalCidadaoWeb.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Entity;
using System.Globalization;
using System.IO;

namespace FiscalCidadaoWeb.Controllers
{
    public class ConvenioController : Controller
    {
        [HttpGet]
        public ActionResult ListagemConvenios(string status)
        {
            ListagemConvenioViewModel viewModel = new ListagemConvenioViewModel();
            viewModel.Lista = new List<ListaConvenioViewModel>();

            try
            {
                using (var context = new ApplicationDBContext())
                {
                    List<Convenio> listConvenio = null;

                    if (string.IsNullOrEmpty(status))
                    {
                       listConvenio = context.Convenio
                            .Include(x => x.ParecerGoverno)
                            .Include(x => x.Situacao)
                            .Include(x => x.Denuncias)
                            .ToList();
                    }
                    else
                    {
                        listConvenio = context.Convenio
                            .Include(x => x.ParecerGoverno)
                            .Include(x => x.Situacao)
                            .Include(x => x.Denuncias)
                            .Where(x => x.ParecerGoverno.Parecer == status && x.Denuncias.Count > 0)
                            .ToList();
                    }

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
                        .Include(x => x.Denuncias.Select(y => y.Fotos))
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

                    retorno.Denuncias = new List<DenunciaViewModel>();

                    foreach (var denuncia in convenio.Denuncias)
                    {
                        retorno.Denuncias.Add(new DenunciaViewModel
                        {
                            Id = denuncia.Id,
                            Comentarios = denuncia.Comentarios,
                            Data = denuncia.Data,
                            TemFoto = (denuncia.Fotos.Count > 0 ? true : false)
                        });
                    }

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

        [HttpGet]
        public JsonResult GetImageBase64(string id)
        {
            int denunciaId;

            if (string.IsNullOrEmpty(id) || !int.TryParse(id, out denunciaId))
            {
                return null;
            }

            List<string> listImages = new List<string>();

            using (var context = new ApplicationDBContext())
            {
                var result = context.DenunciaFoto.Where(x => x.DenunciaId == denunciaId).ToList();

                foreach (var foto in result)
                {
                    if (result != null)
                    {
                        listImages.Add(System.Convert.ToBase64String // converte para 64 e add na lista
                            (System.IO.File.ReadAllBytes // le os bytes
                                (Directory.GetFiles // pega o arquivo
                                    (Server.MapPath("~/ImagensDenuncias/"), foto.Arquivo).FirstOrDefault()))); // procura no path pelo nome do arquivo
                    }
                }
            }

            return Json(listImages, JsonRequestBehavior.AllowGet);
        }

    }
}