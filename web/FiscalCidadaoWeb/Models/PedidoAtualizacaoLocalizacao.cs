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