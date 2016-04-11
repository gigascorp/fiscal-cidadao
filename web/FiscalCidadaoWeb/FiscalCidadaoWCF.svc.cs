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
                        Data = TimeZoneInfo.ConvertTime(DateTime.Now, TimeZoneInfo.FindSystemTimeZoneById("E. South America Standard Time")),
                        UsuarioId = model.UsuarioId
                    };

                    context.Denuncia.Add(denuncia);

                    if (!string.IsNullOrEmpty(model.UsuarioId))
                    {
                        Usuario usuario = context.Usuario.Include(x => x.Denuncias)
                            .FirstOrDefault(x => x.FacebookId == model.UsuarioId);

                        if (usuario != null)
                        {
                            usuario.Pontuacao += 10;

                            if (usuario.Denuncias == null)
                            {
                                usuario.Denuncias = new List<Denuncia>();
                            }

                            usuario.Denuncias.Add(denuncia);
                        }
                        else
                        {
                            Usuario usu = new Usuario
                            {
                                FacebookId = model.UsuarioId,
                                Pontuacao = 10,
                                Nome = "Usuário Teste",
                                Email = "Email Teste",
                                Denuncias = new List<Denuncia> { denuncia }
                            };

                            context.Usuario.Add(usu);
                        }
                    }

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
                        .Include(x => x.Convenio.ParecerGoverno)
                        .Include(x => x.Convenio.Situacao)
                        .Include(x => x.Convenio.Proponente)
                        .Include(x => x.Fotos)
                        .Where(x => x.UsuarioId == usuarioId)
                        .ToList();

                    foreach (var denuncia in listaDenuncia)
                    {
                        var fotos = new List<string>();

                        foreach (var foto in denuncia.Fotos)
                        {
                            fotos.Add("http://www.fiscalcidadao.site/ImagensDenuncias/" + foto.Arquivo);
                        }

                        retorno.Add(new DenunciaEnvioViewModel
                        {
                            Id = denuncia.Id,
                            Comentarios = denuncia.Comentarios,
                            DataDenuncia = String.Format("{0:dd/MM/yy}", denuncia.Data),
                            Fotos = fotos,
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

        //public RetornoLogin Login(FazerLoginViewModel data)
        //{
        //    RetornoLogin retorno = new RetornoLogin();

        //    try
        //    {
        //        using (var context = new ApplicationDBContext())
        //        {
        //            var usuario = context.Usuario.FirstOrDefault(x => x.FacebookId.Equals(data.Id));

        //            var fb = new FacebookClient(data.AccessToken);

        //            dynamic result = fb.Get("me?fields=name,picture{url},email");

        //            if (result.email != null && result.name != null && result.picture != null)
        //            {
        //                retorno.Nome = result.name;
        //                retorno.FotoPerfil = result.picture.data.url;
        //                retorno.HttpStatus = 200;
        //                retorno.Message = "OK";

        //                if (usuario == null) // caso for primeiro acesso, grava no banco
        //                {
        //                    context.Usuario.Add(new Usuario
        //                    {
        //                        FacebookId = data.Id,
        //                        Pontuacao = 0,
        //                        Nome = result.name,
        //                        Email = result.email
        //                    });

        //                    context.SaveChanges();
        //                }
        //            }
        //        }
        //    }
        //    catch (Exception ex)
        //    {
        //        retorno.HttpStatus = 500;
        //        retorno.Message = "Error: " + ex.Message;

        //        return retorno;
        //    }

        //    return retorno;
        //}

        public RetornoLogin Login(string usuarioId)
        {
            RetornoLogin retorno = new RetornoLogin();

            try
            {
                using (var context = new ApplicationDBContext())
                {
                    var usuario = context.Usuario.FirstOrDefault(x => x.FacebookId.Equals(usuarioId));

                    if (usuario == null)
                    {
                        context.Usuario.Add(new Usuario
                        {
                            FacebookId = usuarioId,
                            Pontuacao = 0,
                            Nome = "Usuário Teste",
                            Email = "Email Teste",
                        });
                    }

                    context.SaveChanges();
                }

                retorno.HttpStatus = 200;
                retorno.Message = "OK";
            }
            catch (Exception ex)
            {
                retorno.HttpStatus = 500;
                retorno.Message = "Error: " + ex.Message;

                return retorno;
            }

            return retorno;
        }

        public RetornoGetUsuario GetUsuario(string usuarioId)
        {
            RetornoGetUsuario retorno = new RetornoGetUsuario();

            try
            {
                using (var context = new ApplicationDBContext())
                {
                    var user = context.Usuario.Include(x => x.Denuncias)
                        .FirstOrDefault(x => x.FacebookId == usuarioId);

                    if (user != null)
                    {
                        retorno.Id = user.FacebookId;
                        retorno.Nome = user.Nome;
                        retorno.Email = user.Email;
                        retorno.Pontuacao = user.Pontuacao;
                        retorno.UrlFoto = "http://www.fiscalcidadao.site/ImagensDenuncias/perfil.png";

                        if (user.Denuncias != null)
                        {
                            retorno.CountDenuncias = user.Denuncias.Count;

                            if (user.Denuncias.Count > 0)
                            {
                                retorno.DataCadastro = user.Denuncias.OrderBy(x => x.Data).FirstOrDefault().Data.ToString();
                            }
                            else
                            {
                                retorno.DataCadastro = TimeZoneInfo.ConvertTime(DateTime.Now, TimeZoneInfo.FindSystemTimeZoneById("E. South America Standard Time")).ToString("dd/MM/yy");
                            }
                        }
                    }
                    else
                    {
                        Usuario usu = new Usuario
                        {
                            FacebookId = usuarioId,
                            Pontuacao = 0,
                            Nome = "Usuário Teste",
                            Email = "Email Teste",
                        };

                        context.Usuario.Add(usu);
                        context.SaveChanges();

                        retorno.Id = usu.FacebookId;
                        retorno.Nome = usu.Nome;
                        retorno.Email = usu.Email;
                        retorno.Pontuacao = usu.Pontuacao;
                        retorno.UrlFoto = "http://www.fiscalcidadao.site/ImagensDenuncias/perfil.png";
                        retorno.CountDenuncias = 0;
                        retorno.DataCadastro = TimeZoneInfo.ConvertTime(DateTime.Now, TimeZoneInfo.FindSystemTimeZoneById("E. South America Standard Time")).ToString();
                    }

                    retorno.Message = "OK";
                    retorno.HttpStatus = 200;
                }
            }
            catch (Exception ex)
            {
                retorno.Message = "Error: " + ex.Message;
                retorno.HttpStatus = 500;
            }

            return retorno;
        }

        public RetornoRanking GetRanking(string usuarioId)
        {
            RetornoRanking retorno = new RetornoRanking();
            var lista = new List<UsuarionRankingViewModel>();

            try
            {
                using (var context = new ApplicationDBContext())
                {
                    var user = context.Usuario.Include(x => x.Denuncias)
                        .FirstOrDefault(x => x.FacebookId == usuarioId);

                    UsuarionRankingViewModel usuario = new UsuarionRankingViewModel();

                    if (user != null)
                    {
                        usuario.Id = user.FacebookId;
                        usuario.Nome = user.Nome;
                        usuario.Pontuacao = user.Pontuacao;
                        usuario.UrlFoto = "http://www.fiscalcidadao.site/ImagensDenuncias/perfil.png";

                        lista.Add(usuario);
                    }
                    else
                    {
                        Usuario usu = new Usuario
                        {
                            FacebookId = usuarioId,
                            Pontuacao = 0,
                            Nome = "Usuário Teste",
                            Email = "Email Teste",
                        };

                        context.Usuario.Add(usu);
                        context.SaveChanges();

                        usuario.Id = usu.FacebookId;
                        usuario.Nome = usu.Nome;
                        usuario.Pontuacao = usu.Pontuacao;
                        usuario.UrlFoto = "http://www.fiscalcidadao.site/ImagensDenuncias/perfil.png";

                        lista.Add(usuario);
                    }
                }

                string[] id = new string[] { "1", "2", "3", "4" };
                string[] nomes = new string[] { "Márcio", "Andréa", "Guilherme", "Wallas" };
                double[] pontuacao = new double[] { 14, 68, 27, 45 };
                string[] fotos = new string[] { "marcio.jpg", "dea.jpg", "guilherme.jpg", "wallas.jpg" };

                for (int i = 0; i < 4; i++)
                {
                    UsuarionRankingViewModel usuario = new UsuarionRankingViewModel();

                    usuario.Id = id[i];
                    usuario.Nome = nomes[i];
                    usuario.Pontuacao = pontuacao[i];
                    usuario.UrlFoto = "http://www.fiscalcidadao.site/ImagensDenuncias/" + fotos[i];

                    lista.Add(usuario);
                }

                retorno.Lista = lista.OrderByDescending(x => x.Pontuacao).ToList();
                retorno.Count = lista.Count;

                retorno.Message = "OK";
                retorno.HttpStatus = 200;
            }
            catch (Exception ex)
            {
                retorno.Message = "Error: " + ex.Message;
                retorno.HttpStatus = 500;
            }

            return retorno;
        }
    }
}
