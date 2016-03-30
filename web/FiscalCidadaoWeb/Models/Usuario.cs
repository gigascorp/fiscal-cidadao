﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace FiscalCidadaoWeb.Models
{
    [Table("Usuario")]
    public class Usuario
    {
        public int Id { get; set; }

        public string Nome { get; set; }

        public double Pontuacao { get; set; }

        public string Email { get; set; }

        public string FacebookId { get; set; }

        public List<Denuncia> Denuncias { get; set; }

        public List<PedidoAtualizacaoLocalizacao> PedidoAtualizacaoLocalizacao { get; set; }

        public List<Perfil> Perfil { get; set; }

    }
}