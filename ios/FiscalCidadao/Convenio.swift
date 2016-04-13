/*
 Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guilherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique
 
 This file is part of Fiscal Cidadão.
 
 Fiscal Cidadão is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 Fiscal Cidadão is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with Fiscal Cidadão.  If not, see <http://www.gnu.org/licenses/>.
 */

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
