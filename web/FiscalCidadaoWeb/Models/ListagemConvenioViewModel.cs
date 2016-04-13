/* Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guilherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

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

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace FiscalCidadaoWeb.Models
{
    public class ListagemConvenioViewModel
    {
        public List<ListaConvenioViewModel> Lista { get; set; }

        public string Parecer { get; set; }

        public IEnumerable<SelectListItem> ListaParecer { get; set; }
    }

    public class ListaConvenioViewModel
    {
        public int Id { get; set; }

        public string Objeto { get; set; }

        public int SituacaoId { get; set; }

        public Situacao Situacao { get; set; }

        public int CountDenuncias { get; set; }

        public int? ParecerId { get; set; }

        public ParecerGoverno ParecerGoverno { get; set; }

        public IEnumerable<SelectListItem> ListaParecer { get; set; }
    }
}