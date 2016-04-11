//
//  DataController.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 18/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit



protocol DataObserver : Observer
{
    
}

class DataController: BaseController
{
    
    static let sharedInstance = DataController()
    
    let baseUrl = "http://www.fiscalcidadao.site/FiscalCidadaoWCF.svc/"
    let getConveniosPath = "GetConveniosByCoordinate"
    let sendDenunciaPath = "FazerDenuncia"
    let getDenunciaPath = "GetDenunciaByUsuario"
    
    var allConvenios : [Convenio] = []
    
    func getConvenio(id : Int) -> Convenio?
    {
        for c in allConvenios
        {
            if c.id == id
            {
                return c
            }
        }
        return nil
    }
    
    func parseConvenio(item : [String: AnyObject]) -> Convenio?
    {
        var id : Int?
        var startDate : String?
        var endDate : String?
        var latitude : Double?
        var longitude : Double?
        var desc : String?
        var responsible : String?
        var proponent : String?
        var responsiblePhone : String?
        var status : String?
        var value : Float?
        
        if let nNumber = item["Id"] as? NSNumber
        {
            id = Int(nNumber.intValue)
        }
        
        if let strDate = item["DataInicio"] as? String
        {
            startDate = strDate
        }
        
        if let strDate = item["DataFim"] as? String
        {
            endDate = strDate
        }
        
        if let coordNumber = item["Latitude"] as? NSNumber
        {
            latitude = coordNumber.doubleValue
        }
        
        if let coordNumber = item["Longitude"] as? NSNumber
        {
            longitude = coordNumber.doubleValue
        }
        
        if let strDesc = item["ObjetoDescricao"] as? String
        {
            desc = strDesc
        }
        
        if let strResp = item["ProponenteNome"] as? String
        {
            proponent = strResp
        }
        
        if let strResp = item["ProponenteResponsavel"] as? String
        {
            responsible = strResp
        }
        
        if let strPhone = item["ProponenteTelefone"] as? String
        {
            responsiblePhone = strPhone
        }
        
        if let strStatus = item["Situacao"] as? String
        {
            status = strStatus
        }
        
        if let valueNumber = item["Valor"] as? NSNumber
        {
            value = valueNumber.floatValue
        }
        
        if id != nil && latitude != nil && longitude != nil && responsible != nil && value != nil && desc != nil
        {
            let newConvenio = Convenio(id: id!, startDate: startDate, endDate: endDate, location: (latitude!, longitude!), desc: desc!, proponent: proponent!, responsible: responsible!, responsiblePhone: responsiblePhone, status: status, value: value!)
            return newConvenio
//            convenios.append(newConvenio)
        }
        return nil

    }
    
    func parseDenuncias(data : NSData, userId : String) -> [Denuncia]
    {
        do{
            var jsonData : AnyObject!
            
            let strData = String(data: data, encoding: NSUTF8StringEncoding)
            print(strData)
            try jsonData = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions())
            
            
            if(jsonData == nil)
            {
                print("nil data!")
                return []
            }
            else
            {
                if let denunciasDic = jsonData["GetDenunciaByUsuarioResult"] as? [[String: AnyObject]]
                {
                    var denuncias = [Denuncia]()
                    
                    for item in denunciasDic
                    {
                        let denuncia = Denuncia(convenioId: 0)
                        
                        if let denunciaComment = item["Comentarios"] as? String
                        {
                            denuncia.comments = denunciaComment
                        }
                        
                        if let convenio = item["Convenio"] as? [String: AnyObject]
                        {
                            denuncia.convenio = parseConvenio(convenio)
                        }
                        
                        if let listImage = item["Fotos"] as? [String]
                        {
                            denuncia.photos = listImage
                        }
                        
                        if let id = item["Id"] as? NSNumber
                        {
                            denuncia.id = Int(id)
                        }
                        
                        if let denunciaDate = item["DataDenuncia"] as? String
                        {
                            denuncia.denunciaDate = denunciaDate
                        }
                        
                        if let status = item["Parecer"] as? String
                        {
                            denuncia.status = status
                        }
                        
                        if(denuncia.convenio != nil)
                        {
                            denuncia.convenioId = denuncia.convenio!.id
                        }
                        
                        denuncias.append(denuncia)
                    }
                    return denuncias
                }
            }
        }
        catch
        {
            print("failed to parse data!")
            return []
        }
        
        return []

    }
    
    func parseConvenios(data : NSData) -> [Convenio]
    {
        do{
            var jsonData : AnyObject!
            
            let strData = String(data: data, encoding: NSUTF8StringEncoding)
            print(strData)
            try jsonData = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions())
            
            
            if(jsonData == nil)
            {
                print("nil data!")
                return []
            }
            else
            {
                print("successfully parsed data")
                
                if let root = jsonData["GetConveniosByCoordinateResult"] as? [String: AnyObject]
                {
                    if let list = root["ListaConvenios"] as? [[String: AnyObject]]
                    {
                        var convenios = [Convenio]()
                        for item in list
                        {
                            var id : Int?
                            var startDate : String?
                            var endDate : String?
                            var latitude : Double?
                            var longitude : Double?
                            var desc : String?
                            var responsible : String?
                            var proponent : String?
                            var responsiblePhone : String?
                            var status : String?
                            var value : Float?
                            
                            if let nNumber = item["Id"] as? NSNumber
                            {
                                id = Int(nNumber.intValue)
                            }
                            
                            if let strDate = item["DataInicio"] as? String
                            {
                                startDate = strDate
                            }
                            
                            if let strDate = item["DataFim"] as? String
                            {
                                endDate = strDate
                            }
                            
                            if let coordNumber = item["Latitude"] as? NSNumber
                            {
                                latitude = coordNumber.doubleValue
                            }
                            
                            if let coordNumber = item["Longitude"] as? NSNumber
                            {
                                longitude = coordNumber.doubleValue
                            }
                        
                            if let strDesc = item["ObjetoDescricao"] as? String
                            {
                                desc = strDesc
                            }
                            
                            if let strResp = item["ProponenteNome"] as? String
                            {
                                proponent = strResp
                            }
                            
                            if let strResp = item["ProponenteResponsavel"] as? String
                            {
                                responsible = strResp
                            }
                            
                            if let strPhone = item["ProponenteTelefone"] as? String
                            {
                                responsiblePhone = strPhone
                            }
                            
                            if let strStatus = item["Situacao"] as? String
                            {
                                status = strStatus
                            }
                            
                            if let valueNumber = item["Valor"] as? NSNumber
                            {
                                value = valueNumber.floatValue
                            }
                            
                            if id != nil && latitude != nil && longitude != nil && responsible != nil && value != nil && desc != nil
                            {
                                let newConvenio = Convenio(id: id!, startDate: startDate, endDate: endDate, location: (latitude!, longitude!), desc: desc!, proponent: proponent!, responsible: responsible!, responsiblePhone: responsiblePhone, status: status, value: value!)
                                convenios.append(newConvenio)
                            }
                            
                            
                        }
                        return convenios;
                    }
                }
                
            }
            
        }
        catch
        {
            print("failed to parse data!")
            return []
        }
        
        return []
    }
    
    func sendDenuncia(denuncia : Denuncia, onCompletion: (Bool) -> Void)
    {
        let route = baseUrl + sendDenunciaPath
        var denunciaDic = [String: AnyObject]()
        
        denunciaDic["Comentarios"] = denuncia.comments
        denunciaDic["ConvenioId"] = denuncia.convenioId
        denunciaDic["UsuarioId"] = denuncia.userId
        denunciaDic["ListaFotos"] = denuncia.photos
//        denunciaDic["ListaFotos"] = [String]()
        
        do
        {
            var jsonData : NSData!
            try jsonData = NSJSONSerialization.dataWithJSONObject(denunciaDic, options: NSJSONWritingOptions())
            
            
            if(denuncia.photos.isEmpty)
            {
                let str = String(data: jsonData, encoding: NSUTF8StringEncoding)!
                print(str)
            }
            
            makeHTTPGetRequest(route, body: jsonData , onCompletion:
            {
                data, err in
                
                let dataStr = String(data: data, encoding: NSUTF8StringEncoding)
                print(dataStr)
                
                if err == nil
                {
                    let parser = NSXMLParser(data: data)
                    let xmlDelegate = XmlParser();
                    parser.delegate = xmlDelegate
                    if(parser.parse())
                    {
                        onCompletion(xmlDelegate.statusCode == 200)
                    }
                    else
                    {
                        onCompletion(false);
                    }
                }
                else
                {
                    onCompletion(false);
                }
                
            })

        }
        catch
        {
            
        }
        
    }
    
    func getConvenios( location : (Double, Double), onCompletion: ([Convenio]) -> Void)
    {
        let (latitude, longitude) = location;
        let route = baseUrl + getConveniosPath + "/" + String(latitude) + "/" + String(longitude)
        print("Route : \(route)")
        
        makeHTTPGetRequest(route, body: nil, onCompletion:
        {
            data, err in
            let convenios  = self.parseConvenios(data)
            self.allConvenios = convenios;
            onCompletion(convenios)
            
        })
    }
    
    func loadConvenios(location : (Double, Double))
    {
        getConvenios(location, onCompletion: { (convenios) -> Void in
            self.allConvenios = convenios
            print("loaded \(self.allConvenios.count) convenios")
        })
    }
    
    func getDenuncias(id : String, onCompletion: ([Denuncia]) -> Void)
    {
        let route = baseUrl + getDenunciaPath + "/" + id
        
        print("Denuncias route \(route)")
        
        makeHTTPGetRequest(route, body: nil, onCompletion:
        {
            data, err in
            let str  = String(data: data, encoding: NSUTF8StringEncoding)
            print(str)
            onCompletion(self.parseDenuncias(data, userId: id))
                
        })

        
    }
    
    func makeHTTPGetRequest(path: String, body : NSData?, onCompletion: (NSData, NSError?) -> Void)
    {
        let request = NSMutableURLRequest(URL: NSURL(string: path)!)
        
        if body != nil
        {
            request.HTTPMethod = "POST"
            request.setValue(String(body!.length), forHTTPHeaderField: "Content-Length")
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        }
        request.HTTPBody = body
        
        let session = NSURLSession.sharedSession()
        
        let task = session.dataTaskWithRequest(request, completionHandler:
            {data, response, error -> Void in
                dispatch_async(dispatch_get_main_queue(), {
                    onCompletion(data!, error)
                })
        })
        task.resume()
    }
    
}
