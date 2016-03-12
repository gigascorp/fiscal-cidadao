using FiscalCidadaoWCF.Models;
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
        public string BySituacao(string situacaoId)
        {
            List<string> arraySituacao = new List<string>(); ;

            arraySituacao.Add(situacaoId);

            return InsertConveniosDB(arraySituacao);
        }

        public string AllSituacao()
        {
            List<string> arraySituacao = new List<string>(); ;

            arraySituacao.Add("28");
            arraySituacao.Add("29");
            arraySituacao.Add("30");
            arraySituacao.Add("31");
            arraySituacao.Add("32");
            arraySituacao.Add("33");
            arraySituacao.Add("35");

            return InsertConveniosDB(arraySituacao);
        }

        private string InsertConveniosDB(List<string> arraySituacao)
        {
            string urlTodosConvenios = "";

            try
            {
                using (var context = new ApplicationDBContext())
                {
                    foreach (var situacao in arraySituacao)
                    {
                        // convenios de Sao Luis - MA
                        urlTodosConvenios = "http://api.convenios.gov.br/siconv/v1/consulta/convenios.json?id_proponente=6307102000130&id_situacao=" + situacao;

                        var ReadData = new System.Net.WebClient().DownloadString(urlTodosConvenios);

                        ListaConvenios listaConvenios = JsonConvert.DeserializeObject<ListaConvenios>(ReadData);

                        foreach (var convenio in listaConvenios.convenios)
                        {
                            string urlConvenioId = convenio.href + ".json";
                            ReadData = new System.Net.WebClient().DownloadString(urlConvenioId);
                            Convenios convenioJson = JsonConvert.DeserializeObject<Convenios>(ReadData);

                            string urlConcedenteId = convenio.orgao_concedente.orgao.href + ".json";
                            ReadData = new System.Net.WebClient().DownloadString(urlConcedenteId);
                            Concedente concedenteJson = JsonConvert.DeserializeObject<Concedente>(ReadData);

                            var tempSitId = int.Parse(situacao);

                            context.Convenio.Add(new Convenio
                            {
                                DataInicio = convenio.data_inicio_vigencia,
                                DataFim = convenio.data_fim_vigencia,
                                ValorTotal = convenio.valor_global,
                                DescricaoObjeto = convenioJson.convenios.FirstOrDefault().objeto,
                                SincovId = convenio.id,
                                Coordenadas = DbGeography.FromText("POINT(-44.2691611 -2.6258591)"), // mudar
                                SituacaoId = context.Situacao.Where(x => x.SincovId == tempSitId).FirstOrDefault().Id,
                                ProponenteId = 2, // mudar
                                ParecerGovernoId = 1, // mudar,
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
