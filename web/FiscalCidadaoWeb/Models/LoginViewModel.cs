using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    public class FazerLoginViewModel
    {
        public string Id { get; set; }

        public string AccessToken { get; set; }
    }

    public class RetornoLogin
    {
        public string Nome { get; set; }

        public string FotoPerfil { get; set; }

        public int HttpStatus { get; set; }

        public string Message { get; set; }
    }
}