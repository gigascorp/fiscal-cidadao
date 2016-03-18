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

namespace FiscalCidadaoWCF
{
    [ServiceContract]
    public interface IFiscalCidadaoWCF
    {
        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "GetConveniosByCoordinate/{latitude}/{longitude}")]
        TelaInicialEnvioViewModel GetConveniosByCoordinate(string latitude, string longitude);

        [OperationContract]
        [WebInvoke(Method = "GET", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "FazerDenuncia/{model}")]
        string FazerDenuncia(string model);

        [OperationContract]
        [WebInvoke(Method = "POST", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped)]
        ConvenioEnvioViewModel GetConvenioById(string convenioId);

        [OperationContract]
        [WebInvoke(Method = "GET", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "GetDenunciaByUsuario/{usuarioId}")]
        List<DenunciaEnvioViewModel> GetDenunciaByUsuario(string usuarioId);
    }
}
