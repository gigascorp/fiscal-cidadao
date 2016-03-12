using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWeb.Models
{
    [Table("DenunciaFoto")]
    public class DenunciaFoto
    {
        public int Id { get; set; }

        public string Arquivo { get; set; }

        public int DenunciaId { get; set; }

        public Denuncia Denuncia { get; set; }
    }
}