﻿/* Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guiherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

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

namespace FiscalCidadaoWCF.Models
{
    public class RetornoRanking
    {
        public int Count { get; set; }

        public List<UsuarionRankingViewModel> Lista { get; set; }

        public string Message { get; set; }

        public int HttpStatus { get; set; }
    }

    public class UsuarionRankingViewModel
    {
        public string Id { get; set; }

        public string Nome { get; set; }

        public double Pontuacao { get; set; }

        public string UrlFoto { get; set; }
    }

}