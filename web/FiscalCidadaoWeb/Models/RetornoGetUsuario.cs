using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    public class RetornoGetUsuario
    {
        public string Id { get; set; }

        public string Nome { get; set; }

        public string Email { get; set; }

        public int CountDenuncias { get; set; }

        public double Pontuacao { get; set; }

        public string Message { get; set; }

        public int HttpStatus { get; set; }
    }
}