using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    [Table("Denuncia")]
    public class Denuncia
    {
        public int Id { get; set; }

        public string Comentarios { get; set; }

        public DateTime Data { get; set; }

        public List<DenunciaFoto> Fotos { get; set; }

        public int ConvenioId { get; set; }

        public Convenio Convenio { get; set; }

        public int? UsuarioId { get; set; }

        public Usuario Usuario { get; set; }
    }
}