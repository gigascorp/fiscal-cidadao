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
    
    let baseUrl = "http://www.emilioweba.com/FiscalCidadaoWCF.svc/"
    let getConveniosPath = "GetConveniosByCoordinate"
    
    var allConvenios : [ConvenioModelo] = []
    
    func getConvenio(id : Int) -> ConvenioModelo?
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
    
    func parseConvenios(data : NSData) -> [ConvenioModelo]
    {
        do{
            var jsonData : AnyObject!
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
                        var convenios = [ConvenioModelo]()
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
                                let newConvenio = ConvenioModelo(id: id!, startDate: startDate, endDate: endDate, location: (latitude!, longitude!), desc: desc!, proponent: proponent!, responsible: responsible!, responsiblePhone: responsiblePhone, status: status, value: value!)
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
    
    func getConvenios( location : (Double, Double), onCompletion: ([ConvenioModelo]) -> Void)
    {
        let (latitude, longitude) = location;
        let route = baseUrl + getConveniosPath + "/" + String(latitude) + "/" + String(longitude)
        print("Route : \(route)")
        
        makeHTTPGetRequest(route, onCompletion:
        {
            data, err in
            let convenios  = self.parseConvenios(data)
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
    
    func makeHTTPGetRequest(path: String, onCompletion: (NSData, NSError?) -> Void)
    {
        let request = NSMutableURLRequest(URL: NSURL(string: path)!)
        
        let session = NSURLSession.sharedSession()
        
        let task = session.dataTaskWithRequest(request, completionHandler:
            {data, response, error -> Void in
//                let strData = String(data: data!, encoding: NSUTF8StringEncoding)!
                onCompletion(data!, error)
        })
        task.resume()
    }
    
}
