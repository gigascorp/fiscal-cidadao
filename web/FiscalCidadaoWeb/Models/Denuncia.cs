using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWeb.Models
{
    [Table("Denuncia")]
    public class Denuncia
    {
        public int Id { get; set; }

        public string Comentarios { get; set; }

        public List<DenunciaFoto> Fotos { get; set; }

        public int ConvenioId { get; set; }

        public Convenio Convenio { get; set; }
    }
}