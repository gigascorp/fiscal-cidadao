using System;
using System.Collections.Generic;
using System.Data.Entity.Spatial;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    public class TelaInicialEnvioViewModel
    {
        public int Quantidade { get; set; }

        public List<ListaConvenioEnvioViewModel> ListaConvenios { get; set; }
    }

    public class ListaConvenioEnvioViewModel
    {
        public int Id { get; set; }

        public string DataInicio { get; set; }

        public string DataFim { get; set; }

        public double Valor { get; set; }

        public string ObjetoDescricao { get; set; }

        public double? Latitude { get; set; }

        public double? Longitude { get; set; }

        public string Situacao { get; set; }

        public string ProponenteNome { get; set; }

        public string ProponenteResponsavel { get; set; }

        public string ProponenteTelefone { get; set; }
    }
}