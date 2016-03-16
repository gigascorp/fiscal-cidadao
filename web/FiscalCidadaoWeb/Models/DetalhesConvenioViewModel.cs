using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace FiscalCidadaoWeb.Models
{
    public class DetalhesConvenioViewModel
    {
        public int Id { get; set; }

        public string Objeto { get; set; }

        public string Situacao { get; set; }

        public DateTime DataInicio { get; set; }

        public DateTime DataFim { get; set; }

        public string Valor { get; set; }

        public int SincovId { get; set; }

        public string ConcedenteNome { get; set; }

        public string ProponenteNome { get; set; }

        public List<Denuncia> Denuncias { get; set; }

        public ParecerGoverno Parecer { get; set; }

        public IEnumerable<SelectListItem> ListaParecer { get; set; }
    }
}