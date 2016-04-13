/* Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guiherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

  This file is part of Fiscal Cidadão.

  Fiscal Cidadão is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  Fiscal Cidadão is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with Fiscal Cidadão.  If not, see <http://www.gnu.org/licenses/>. */

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

        public OrgaoProponente proponente { get; set; }
    }

    public class OrgaoProponente
    {
        public ProponenteDetalhes Proponente { get; set; }
    }

    public class ProponenteDetalhes
    {
        public string href { get; set; }

        public string id { get; set; }
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
        public string nome { get; set; }
    }

    public class Proponentes
    {
        public List<ProponenteJSON> proponentes { get; set; }
    }

    public class ProponenteJSON
    {
        public string nome { get; set; }

        public string endereco { get; set; }

        public string cep { get; set; }

        public string id { get; set; }

        public string telefone { get; set; }

        public string nome_responsavel { get; set; }

        public Municipios municipio { get; set; }

        public Pessoa_Responsavel pessoa_responsavel { get; set; }
    }

    public class Pessoa_Responsavel
    {
        public Pessoas_Responsavel PessoaResponsavel { get; set; }
    }

    public class Pessoas_Responsavel
    {
        public string id { get; set; }
    }

    public class Municipios
    {
        public MunicipioLink Municipio { get; set; }
    }

    public class MunicipioLink
    {
        public string href { get; set; }
    }

    public class Municipio
    {
        public List<MunicipioJSON> municipios { get; set; }
    }

    public class MunicipioJSON
    {
        public string nome { get; set; }

        public Uf uf { get; set; }
    }

    public class Uf
    {
        public string sigla { get; set; }
    }

}