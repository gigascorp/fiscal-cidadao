using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    public class DenunciaEnvioViewModel
    {
        public int Id { get; set; }

        public string Comentarios { get; set; }

        public ConvenioEnvioViewModel Convenio { get; set; }

        public string Usuario { get; set; }

        public string Parecer { get; set; }

        public string Fotos { get; set; }

        public string DataDenuncia { get; set; }
    }
}