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
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.Spatial;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWeb.Models
{
    [Table("Convenio")]
    public class Convenio
    {
        public int Id { get; set; }

        public DateTime DataInicio { get; set; }

        public DateTime DataFim { get; set; }

        public double ValorTotal { get; set; }

        public DbGeography Coordenadas { get; set; }

        public string DescricaoObjeto { get; set; }

        public int SincovId { get; set; }

        public int ConcedenteSincovId { get; set; }

        public string ConcedenteNome { get; set; }

        public List<Denuncia> Denuncias { get; set; }

        public List<PedidoAtualizacaoLocalizacao> PedidoAtualizacaoLocalizacao { get; set; }

        public List<HistoricoAtualizacaoLocalizacao> HistoricoAtualizacaoLocalizacao { get; set; }

        public int SituacaoId { get; set; }

        public Situacao Situacao { get; set; }

        public int? ParecerGovernoId { get; set; }

        public ParecerGoverno ParecerGoverno { get; set; }

        public int ProponenteId { get; set; }

        public Proponente Proponente { get; set; }
    }
}