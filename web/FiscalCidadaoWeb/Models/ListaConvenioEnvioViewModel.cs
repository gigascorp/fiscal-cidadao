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
using System.Data.Entity.Spatial;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    public class TelaInicialEnvioViewModel
    {
        public int Quantidade { get; set; }

        public List<ConvenioEnvioViewModel> ListaConvenios { get; set; }
    }

    public class ConvenioEnvioViewModel
    {
        public int Id { get; set; }

        public string DataInicio { get; set; }

        public string DataFim { get; set; }

        public double Valor { get; set; }

        public string ObjetoDescricao { get; set; }

        public double? Latitude { get; set; }

        public double? Longitude { get; set; }

        public string Situacao { get; set; }

        public string ProponenteNome { get; set; }

        public string ProponenteResponsavel { get; set; }

        public string ProponenteTelefone { get; set; }
    }
}