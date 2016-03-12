using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWeb.Models
{
    public class Perfil
    {
        public int Id { get; set; }

        public string Nome { get; set; }

        public List<Usuario> Usuarios { get; set; }
    }
}