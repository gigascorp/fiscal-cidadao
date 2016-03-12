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
    }
}
