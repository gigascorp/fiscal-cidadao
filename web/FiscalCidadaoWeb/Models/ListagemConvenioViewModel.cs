using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWeb.Models
{
    public class ListagemConvenioViewModel
    {
        public int Id { get; set; }

        public string Objeto { get; set; }

        public int SituacaoId { get; set; }

        public Situacao Situacao { get; set; }

        public int CountDenuncias { get; set; }

        public int? ParecerId { get; set; }

        public ParecerGoverno ParecerGoverno { get; set; }
    }
}