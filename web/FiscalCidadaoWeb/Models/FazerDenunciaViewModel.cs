﻿/* Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guilherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

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

using FiscalCidadaoWCF.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    [DataContract]
    public class FazerDenunciaViewModel
    {
        [DataMember]
        public string Comentarios { get; set; }

        [DataMember]
        public int ConvenioId { get; set; }

        [DataMember]
        public string Coordenadas { get; set; }

        [DataMember]
        public string UsuarioId { get; set; }

        [DataMember]
        public List<string> ListaFotos { get; set; }
    }

}