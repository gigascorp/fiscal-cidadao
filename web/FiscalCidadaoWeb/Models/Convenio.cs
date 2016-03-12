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