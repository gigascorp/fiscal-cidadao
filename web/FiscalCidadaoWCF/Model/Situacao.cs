using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    [Table("Situacao")]
    public class Situacao
    {
        public int Id { get; set; }

        public string Descricao { get; set; }

        public int SincovId { get; set; }

        public List<Convenio> Convenios { get; set; }
    }
}