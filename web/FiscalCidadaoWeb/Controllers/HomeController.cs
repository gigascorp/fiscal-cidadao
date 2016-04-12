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
                //retorno.MaisDenunciados = new List<MaisDenunciados>
                //{
                //    new MaisDenunciados { Count = 3, Id = 51, Objeto = "Cobertura e Reforma de Vestiário e da Quadra Poliesportiva da UEB São Raimundo." },
                //    new MaisDenunciados { Count = 10, Id = 52, Objeto = "O presente projeto tem como objetivo criar um ambiente saudável e dentro dos padrões técnicos e higiênicos através da implantação de uma feira para comercialização de produtos oriundos das cadeias produtivas da agricultura familiar dos pólos de produção assistidos pela SEMAPA através de suas competências internas formadas por Agrônomos  Veterinários  Técnicos Agrícolas e Administradores para garantir a oferta dos produtos ao consumidor e o aumento da renda familiar dos produtores." },
                //    new MaisDenunciados { Count = 5, Id = 53, Objeto = "Compra de Maquinas e Equipamentos para manutenção de redes de Aguas Pluviais em todo Distrito Federal." }
                //};

                //retorno.CountDenunciasNaoAnalisadas = 1;
                //retorno.CountAtualizacoesEndereco = 4;

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