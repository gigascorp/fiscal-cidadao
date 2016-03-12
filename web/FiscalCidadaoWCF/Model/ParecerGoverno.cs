using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    [Table("ParecerGoverno")]
    public class ParecerGoverno
    {
        public int Id { get; set; }

        public string Parecer { get; set; }

        public List<Convenio> Convenios { get; set; }
    }
}