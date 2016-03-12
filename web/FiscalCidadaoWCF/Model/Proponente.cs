using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    [Table("Proponente")]
    public class Proponente
    {
        public int Id { get; set; }

        public string Nome { get; set; }

        public string Endereco { get; set; }

        public string CEP { get; set; }

        public string SincovId { get; set; }

        public string Cidade { get; set; }

        public string Estado { get; set; }

        public string ResponsavelSincovId { get; set; }

        public string ResponsavelNome { get; set; }

        public string ResponsavelTelefone { get; set; }

        public List<Convenio> Convenios { get; set; }
    }
}