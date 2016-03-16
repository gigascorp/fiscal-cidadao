using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace FiscalCidadaoWeb.Models
{
    public class ListagemConvenioViewModel
    {
        public List<ListaConvenioViewModel> Lista { get; set; }

        public string Parecer { get; set; }

        public IEnumerable<SelectListItem> ListaParecer { get; set; }
    }

    public class ListaConvenioViewModel
    {
        public int Id { get; set; }

        public string Objeto { get; set; }

        public int SituacaoId { get; set; }

        public Situacao Situacao { get; set; }

        public int CountDenuncias { get; set; }

        public int? ParecerId { get; set; }

        public ParecerGoverno ParecerGoverno { get; set; }

        public IEnumerable<SelectListItem> ListaParecer { get; set; }
    }
}