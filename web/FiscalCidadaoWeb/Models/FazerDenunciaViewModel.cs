using FiscalCidadaoWCF.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace FiscalCidadaoWCF.Models
{
    [DataContract]
    public class FazerDenunciaViewModel
    {
        [DataMember]
        public string Comentarios { get; set; }

        [DataMember]
        public int ConvenioId { get; set; }

        [DataMember]
        public string Coordenadas { get; set; }

        [DataMember]
        public int? UsuarioId { get; set; }

        [DataMember]
        public List<string> ListaFotos { get; set; }
    }

}