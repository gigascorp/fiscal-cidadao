using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    public class ListaConvenios
    {
        //public int total_registros { get; set; }

        public List<ConvenioViewModel> convenios { get; set; }
    }

    public class ConvenioViewModel
    {
        public double valor_global { get; set; }

        public DateTime data_fim_vigencia { get; set; }

        public DateTime data_inicio_vigencia { get; set; }

        public int id { get; set; }

        public string href { get; set; }

        public OrgaoConcedente orgao_concedente { get; set; }

    }

    public class OrgaoConcedente
    {
        public Orgao orgao { get; set; }
    }

    public class Orgao
    {
        public string href { get; set; }

        public int id { get; set; }
    }
    
    public class Convenios
    {
        public List<ConvenioJSON> convenios { get; set; }
    }

    public class ConvenioJSON
    {
        public string objeto { get; set; }
    }

    public class Concedente
    {
        public List<ConcedenteJSON> orgaos { get; set; }
    }

    public class ConcedenteJSON
    {
        public int MyProperty { get; set; }
        public string nome { get; set; }
    }
}