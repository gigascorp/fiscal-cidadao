//
//  ConvenioModelo.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 19/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class Convenio: NSObject
{
    var id : Int
    var startDate : String?
    var endDate : String?
    var location : (Double, Double)
    var desc : String
    var responsible : String
    var proponent : String
    var responsiblePhone : String?
    var status : String?
    var value : Float
    
    init(id : Int, startDate : String?, endDate : String?, location : (Double, Double), desc : String, proponent : String, responsible : String, responsiblePhone : String?, status : String?, value : Float)
    {
        self.id = id
        self.startDate = startDate
        self.endDate = endDate
        self.location = location
        self.desc = desc
        self.proponent = proponent
        self.responsible = responsible
        self.responsiblePhone = responsiblePhone
        self.status = status
        self.value = value
    }
    
    override var description : String
    {
        return "Convenio {id: \(id)" + ", description \(desc), proponente : \(responsible) location(\(location))}"
    }
    
    

}
