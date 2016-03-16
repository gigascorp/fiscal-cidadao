using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWeb.Models
{
    public class HomeViewModel
    {
        public string NomeUsuario { get; set; }

        public List<MaisDenunciados> MaisDenunciados { get; set; }

        public int CountDenunciasNaoAnalisadas { get; set; }

        public int CountAtualizacoesEndereco { get; set; }
    }

    public class MaisDenunciados
    {
        public int Id { get; set; }

        public string Objeto { get; set; }

        public int Count { get; set; }
    }
}