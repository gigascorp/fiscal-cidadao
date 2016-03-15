﻿using FiscalCidadaoWCF.Models;
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
        //        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.WrappedRequest)]
        [WebInvoke(Method = "GET", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped)]
        TelaInicialEnvioViewModel GetConveniosByCoordinate(string latitude, string longitude);

        [OperationContract]
        [WebInvoke(Method = "GET", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped)]
        string FazerDenuncia(string model);
    }
}
