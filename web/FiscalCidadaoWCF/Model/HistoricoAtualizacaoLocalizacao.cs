using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    [Table("HistoricoAtualizacaoLocalizacao")]
    public class HistoricoAtualizacaoLocalizacao
    {

        public int Id { get; set; }

        public Byte[] Coordenadas { get; set; }

        public DateTime DataCriacao { get; set; }

        public int ConvenioId { get; set; }

        public Convenio Convenio { get; set; }

        public int PedidoAtualizacaoLocalizacaoId { get; set; }

        [Required]
        public PedidoAtualizacaoLocalizacao PedidoAtualizacaoLocalizacao { get; set; }
    }
}