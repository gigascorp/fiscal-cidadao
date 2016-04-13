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
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using System.Data.Entity.Spatial;
using System.ComponentModel.DataAnnotations.Schema;

namespace FiscalCidadaoWeb.Models
{
    [Table("PedidoAtualizacaoLocalizacao")]
    public class PedidoAtualizacaoLocalizacao
    {
        public int Id { get; set; }

        public Byte[] Coordenadas { get; set; }

        public bool Avaliado { get; set; }

        public int UsuarioId { get; set; }

        public Usuario Usuario { get; set; }

        public int HistoricoAtualizacaoLocalizacaoId { get; set; }

        public HistoricoAtualizacaoLocalizacao HistoricoAtualizacaoLocalizacao { get; set; }
    }
}