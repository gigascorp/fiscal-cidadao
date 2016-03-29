using FiscalCidadaoWCF.Models;
//using FiscalCidadaoWeb.Models;
//using FiscalCidadaoWeb.WCF;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Web.Mvc;

namespace FiscalCidadaoWCF
{
    [ServiceContract]
    public interface IFiscalCidadaoWCF
    {
        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "GetConveniosByCoordinate/{latitude}/{longitude}")]
        TelaInicialEnvioViewModel GetConveniosByCoordinate(string latitude, string longitude);

        [OperationContract]
        [WebInvoke(Method = "OPTIONS", UriTemplate = "*")]
        void GetOptions();

        [OperationContract]
        [WebInvoke(Method = "POST", RequestFormat = WebMessageFormat.Json, UriTemplate = "FazerDenuncia")]
        string FazerDenuncia(FazerDenunciaViewModel model);

        [OperationContract]
        [WebInvoke(Method = "GET", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "GetConvenioById/{convenioId}")]
        ConvenioEnvioViewModel GetConvenioById(string convenioId);

        [OperationContract]
        [WebInvoke(Method = "GET", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "GetDenunciaByUsuario/{usuarioId}")]
        List<DenunciaEnvioViewModel> GetDenunciaByUsuario(string usuarioId);

        [OperationContract]
        [WebInvoke(Method = "POST", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, UriTemplate = "Login")]
        RetornoLogin Login(FazerLoginViewModel data);
    }
}
