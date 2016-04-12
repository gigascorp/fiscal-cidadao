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

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace FiscalCidadaoWeb.Models
{
    public class DetalhesConvenioViewModel
    {
        public int Id { get; set; }

        public string Objeto { get; set; }

        public string Situacao { get; set; }

        public DateTime DataInicio { get; set; }

        public DateTime DataFim { get; set; }

        public string Valor { get; set; }

        public int SincovId { get; set; }

        public string ConcedenteNome { get; set; }

        public string ProponenteNome { get; set; }

        public List<DenunciaViewModel> Denuncias { get; set; }

        public ParecerGoverno Parecer { get; set; }

        public IEnumerable<SelectListItem> ListaParecer { get; set; }
    }

    public class DenunciaViewModel
    {
        public int Id { get; set; }

        public string Comentarios { get; set; }

        public DateTime Data { get; set; }

        public bool TemFoto { get; set; }
    }

}