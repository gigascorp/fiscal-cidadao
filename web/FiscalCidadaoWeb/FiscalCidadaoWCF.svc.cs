using FiscalCidadaoWeb.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity.Spatial;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data.Entity;
using Newtonsoft.Json;
using FiscalCidadaoWCF.Models;
using System.ServiceModel.Web;
using System.IO;
using System.Web;
using System.Drawing;
using System.Drawing.Imaging;
using Facebook;
using System.Web.Mvc;
using System.Net;

namespace FiscalCidadaoWCF
{
    [ServiceBehavior(MaxItemsInObjectGraph = 2147483647)]
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

        public void GetOptions()
        {
            WebOperationContext.Current.OutgoingResponse.Headers.Add("Access-Control-Allow-Origin", "*");
            WebOperationContext.Current.OutgoingResponse.Headers.Add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            WebOperationContext.Current.OutgoingResponse.Headers.Add("Access-Control-Allow-Headers", "Content-Type");
        }

        public string FazerDenuncia(FazerDenunciaViewModel model)
        {
            try
            {
                using (var context = new ApplicationDBContext())
                {
                    Denuncia denuncia = new Denuncia
                    {
                        Comentarios = model.Comentarios,
                        ConvenioId = model.ConvenioId,
                        Data = DateTime.Now,
                        Usuario = (model.UsuarioId != null ? context.Usuario.FirstOrDefault(x => x.FacebookId == model.UsuarioId) : null)
                    };

                    context.Denuncia.Add(denuncia);

                    context.SaveChanges();

                    int count = 1;

                    foreach (var foto in model.ListaFotos)
                    {
                        var array = System.Convert.FromBase64String(foto.Replace("\n", string.Empty));
                        var fileName = denuncia.Id.ToString() + "_" + count++ + ".jpg";

                        string path = Path.Combine(System.Web.Hosting.HostingEnvironment.MapPath("~/ImagensDenuncias/"),
                            fileName); // nome do arquivo

                        using (FileStream fileStream = new FileStream(path, FileMode.Create, FileAccess.ReadWrite))
                        {
                            fileStream.Write(array, 0, array.Length);
                        }

                        context.DenunciaFoto.Add(new DenunciaFoto
                        {
                            DenunciaId = denuncia.Id,
                            Arquivo = fileName
                        });

                    }

                    context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                return "500";
            }

            return "200";
        }

        public List<DenunciaEnvioViewModel> GetDenunciaByUsuario(string usuarioId)
        {
            List<DenunciaEnvioViewModel> retorno = new List<DenunciaEnvioViewModel>();

            try
            {
                if (string.IsNullOrEmpty(usuarioId))
                {
                    return retorno;
                }

                using (var context = new ApplicationDBContext())
                {
                    var listaDenuncia = context.Denuncia
                        .Include(x => x.Usuario)
                        .Include(x => x.Convenio.ParecerGoverno)
                        .Include(x => x.Convenio.Situacao)
                        .Include(x => x.Convenio.Proponente)
                        .Where(x => x.Usuario.FacebookId == usuarioId)
                        .ToList();

                    foreach (var denuncia in listaDenuncia)
                    {
                        retorno.Add(new DenunciaEnvioViewModel
                        {
                            Id = denuncia.Id,
                            Comentarios = denuncia.Comentarios,
                            DataDenuncia = String.Format("{0:dd/MM/yy}", denuncia.Data),
                            Usuario = denuncia.Usuario.Nome,
                            Convenio = new ConvenioEnvioViewModel
                            {
                                Id = denuncia.Convenio.Id,
                                DataInicio = String.Format("{0:dd/MM/yy}", denuncia.Convenio.DataInicio),
                                DataFim = String.Format("{0:dd/MM/yy}", denuncia.Convenio.DataFim),
                                Valor = denuncia.Convenio.ValorTotal,
                                ObjetoDescricao = denuncia.Convenio.DescricaoObjeto,
                                Latitude = denuncia.Convenio.Coordenadas.Latitude,
                                Longitude = denuncia.Convenio.Coordenadas.Longitude,
                                Situacao = denuncia.Convenio.Situacao.Descricao,
                                ProponenteNome = denuncia.Convenio.Proponente.Nome,
                                ProponenteResponsavel = denuncia.Convenio.Proponente.ResponsavelNome,
                                ProponenteTelefone = denuncia.Convenio.Proponente.ResponsavelTelefone
                            },
                            Parecer = denuncia.Convenio.ParecerGoverno.Parecer
                        });
                    }

                }
            }
            catch (Exception ex)
            {
                var a = 1;
            }

            return retorno;
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

        public RetornoLogin Login(FazerLoginViewModel data)
        {
            RetornoLogin retorno = new RetornoLogin();

            try
            {
                using (var context = new ApplicationDBContext())
                {
                    var usuario = context.Usuario.FirstOrDefault(x => x.FacebookId.Equals(data.Id));

                    var fb = new FacebookClient(data.AccessToken);

                    dynamic result = fb.Get("me?fields=name,picture{url},email");

                    if (result.email != null && result.name != null && result.picture != null)
                    {
                        retorno.Nome = result.name;
                        retorno.FotoPerfil = result.picture.data.url;
                        retorno.HttpStatus = 200;
                        retorno.Message = "OK";

                        if (usuario == null) // caso for primeiro acesso, grava no banco
                        {
                            context.Usuario.Add(new Usuario
                            {
                                FacebookId = data.Id,
                                Pontuacao = 0,
                                Nome = result.name,
                                Email = result.email
                            });

                            context.SaveChanges();
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                retorno.HttpStatus = 500;
                retorno.Message = "Error: " + ex.Message;

                return retorno;
            }

            return retorno;
        }
    }
}
