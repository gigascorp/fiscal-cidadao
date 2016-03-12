using FiscalCidadaoWCF.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity.Spatial;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data.Entity;

namespace FiscalCidadaoWCF
{
    public class FiscalCidadaoWCF : IFiscalCidadaoWCF
    {
        public TelaInicialEnvioViewModel GetConveniosByCoordinate(string latitude, string longitude) // -2.510954 -44.285454
        {
            TelaInicialEnvioViewModel retorno = new TelaInicialEnvioViewModel();

            try
            {
                //-44.285454 -2.510954 -> Sao Luis Shopping -> LONG e LAT
                using (var context = new ApplicationDBContext())
                {
                    var point = DbGeography.FromText("POINT(" + longitude + " " + latitude + ")", 4326);

                    var weba = context.Convenio.ToList();

                    // distance em metros, transformando em KM
                    var allConvenios = context.Convenio.Include(x => x.Proponente)
                        .Include(x => x.Situacao)
                        .Where(x => (x.Coordenadas.Distance(point) / 1000) < 100) // menor que 100 km
                        .ToList();

                    List<ListaConvenioEnvioViewModel> lista = new List<ListaConvenioEnvioViewModel>();

                    foreach (var convenio in allConvenios)
                    {
                        lista.Add(new ListaConvenioEnvioViewModel()
                        {
                            Id = convenio.Id,
                            DataInicio = String.Format("{0:dd/MM/yy}", convenio.DataInicio),
                            DataFim = String.Format("{0:dd/MM/yy}", convenio.DataFim),
                            Valor = convenio.ValorTotal,
                            ObjetoDescricao = convenio.DescricaoObjeto,
                            Latitude = convenio.Coordenadas.Latitude,
                            Longitude = convenio.Coordenadas.Longitude,
                            Situacao = convenio.Situacao.Descricao,
                            ProponenteNome = convenio.Proponente.Nome,
                            ProponenteResponsavel = convenio.Proponente.ResponsavelNome,
                            ProponenteTelefone = convenio.Proponente.ResponsavelTelefone
                        });
                    }

                    retorno.ListaConvenios = lista;
                    retorno.Quantidade = lista.Count;
                }
            }
            catch (Exception ex)
            {
                var a = 1;
            }

            return retorno;
        }

    }
}
