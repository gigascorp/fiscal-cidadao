using FiscalCidadaoWCF.Models;
using FiscalCidadaoWeb.Models;
//using FiscalCidadaoWeb.Models;
//using FiscalCidadaoWeb.WCF;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Data.Entity.Spatial;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace FiscalCidadaoWCF
{
    public class APIUploadDB : IAPIUploadDB
    {
        public string BySituacao(string situacaoId, string id)
        {
            List<string> arraySituacao = new List<string>(); ;

            arraySituacao.Add(situacaoId);

            return InsertConveniosDB(arraySituacao, id);
        }

        public string AllSituacao(string id)
        {
            List<string> arraySituacao = new List<string>(); ;

            arraySituacao.Add("28");
            arraySituacao.Add("29");
            arraySituacao.Add("30");
            arraySituacao.Add("31");
            arraySituacao.Add("32");
            arraySituacao.Add("33");
            arraySituacao.Add("35");

            return InsertConveniosDB(arraySituacao, id);
        }

        private string InsertConveniosDB(List<string> arraySituacao, string id)
        {
            string urlTodosConvenios = "";

            try
            {
                using (var context = new ApplicationDBContext())
                {
                    foreach (var situacao in arraySituacao)
                    {
                        urlTodosConvenios = "http://api.convenios.gov.br/siconv/v1/consulta/convenios.json?id_proponente=" + id + "&id_situacao=" + situacao;

                        //29138369000147 -> teresopolis // "POINT(-42.9658788 -22.4133074)"
                        //6307102000130 -> prefeitura slz // "POINT(-44.2691611 -2.6258591)"

                        var ReadData = new System.Net.WebClient().DownloadString(urlTodosConvenios);

                        ListaConvenios listaConvenios = JsonConvert.DeserializeObject<ListaConvenios>(ReadData);
                        List<string> listProponentes = new List<string>();

                        foreach (var convenio in listaConvenios.convenios)
                        {
                            string urlConvenioId = convenio.href + ".json";
                            ReadData = new System.Net.WebClient().DownloadString(urlConvenioId);
                            Convenios convenioJson = JsonConvert.DeserializeObject<Convenios>(ReadData);

                            string urlConcedenteId = convenio.orgao_concedente.orgao.href + ".json";
                            ReadData = new System.Net.WebClient().DownloadString(urlConcedenteId);
                            Concedente concedenteJson = JsonConvert.DeserializeObject<Concedente>(ReadData);

                            string proponenteId = convenio.proponente.Proponente.href + ".json";
                            ReadData = new System.Net.WebClient().DownloadString(proponenteId);
                            Proponentes proponenteJson = JsonConvert.DeserializeObject<Proponentes>(ReadData);

                            string municipioId = proponenteJson.proponentes[0].municipio.Municipio.href + ".json";
                            ReadData = new System.Net.WebClient().DownloadString(municipioId);
                            Municipio municipioJson = JsonConvert.DeserializeObject<Municipio>(ReadData);

                            var propId = proponenteJson.proponentes[0].id;
                            var prop = context.Proponente.FirstOrDefault(x => x.SincovId == propId);
                            bool contaisProp = true;

                            Proponente temp = null;

                            if (prop == null && !listProponentes.Contains(propId))
                            {
                                temp = new Proponente
                                {
                                    CEP = proponenteJson.proponentes[0].cep,
                                    Cidade = municipioJson.municipios[0].nome,
                                    Endereco = proponenteJson.proponentes[0].endereco,
                                    Estado = municipioJson.municipios[0].uf.sigla,
                                    Nome = proponenteJson.proponentes[0].nome,
                                    ResponsavelNome = proponenteJson.proponentes[0].nome_responsavel,
                                    ResponsavelSincovId = proponenteJson.proponentes[0].pessoa_responsavel.PessoaResponsavel.id,
                                    ResponsavelTelefone = proponenteJson.proponentes[0].telefone,
                                    SincovId = propId
                                };

                                context.Proponente.Add(temp);
                                context.SaveChanges();

                                listProponentes.Add(propId);
                                contaisProp = false;
                            }

                            var tempSitId = int.Parse(situacao);

                            context.Convenio.Add(new Convenio
                            {
                                DataInicio = convenio.data_inicio_vigencia,
                                DataFim = convenio.data_fim_vigencia,
                                ValorTotal = convenio.valor_global,
                                DescricaoObjeto = convenioJson.convenios.FirstOrDefault().objeto,
                                SincovId = convenio.id,
                                Coordenadas = DbGeography.FromText("POINT(-42.9658788 -22.4133074)"), // mudar
                                SituacaoId = context.Situacao.Where(x => x.SincovId == tempSitId).FirstOrDefault().Id,
                                ProponenteId = (!contaisProp ? temp.Id : prop.Id), // caso nao exista no BD, add o criado anteriormente. Caso exista, add o consultado do banco
                                ParecerGovernoId = 1,
                                ConcedenteSincovId = convenio.orgao_concedente.orgao.id,
                                ConcedenteNome = concedenteJson.orgaos.FirstOrDefault().nome
                            });
                        }
                    }

                    context.SaveChanges();

                    return "Convenios adicionados com sucesso";
                }
            }
            catch (Exception ex)
            {
                return "Erro: " + ex.Message;
            }
        }

    }
}
