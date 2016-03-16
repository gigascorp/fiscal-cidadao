using FiscalCidadaoWCF.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity.Spatial;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data.Entity;
using Newtonsoft.Json;

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

                    // distance em metros, transformando em KM
                    var allConvenios = context.Convenio.Include(x => x.Proponente)
                        .Include(x => x.Situacao)
                        .Where(x => (x.Coordenadas.Distance(point) / 1000) < 100) // menor que 100 km
                        .ToList();

                    List<ConvenioEnvioViewModel> lista = new List<ConvenioEnvioViewModel>();

                    foreach (var convenio in allConvenios)
                    {
                        lista.Add(new ConvenioEnvioViewModel()
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

        public string FazerDenuncia(string model)
        {
            FazerDenunciaViewModel denunciaCliente = JsonConvert.DeserializeObject<FazerDenunciaViewModel>(model);

            return "entrou";
        }

        public void GetConvenioByUsuario(string usuarioId)
        {
            throw new NotImplementedException();
        }

        public ConvenioEnvioViewModel GetConvenioById(string convenioId)
        {
            ConvenioEnvioViewModel retorno = null;

            try
            {
                int id;

                if (string.IsNullOrEmpty(convenioId) || !int.TryParse(convenioId, out id))
                {
                    return retorno;
                }

                using (var context = new ApplicationDBContext())
                {
                    var convenio = context.Convenio
                        .Include(x => x.Situacao)
                        .Include(x => x.Proponente)
                        .Single(x => x.Id == id);

                    retorno = new ConvenioEnvioViewModel
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
                        };

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
