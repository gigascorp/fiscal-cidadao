using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    public class RetornoRanking
    {
        public int Count { get; set; }

        public List<UsuarionRankingViewModel> Lista { get; set; }

        public string Message { get; set; }

        public int HttpStatus { get; set; }
    }

    public class UsuarionRankingViewModel
    {
        public string Id { get; set; }

        public string Nome { get; set; }

        public double Pontuacao { get; set; }

        public string UrlFoto { get; set; }
    }

}