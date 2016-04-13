/*
 Copyright (c) 2009-2014, Apple Inc. All rights reserved.
 
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

import MapKit
import AddressBook

class MapaAnnotation: NSObject, MKAnnotation
{
    let convenioName : String?
    let responsibleName: String
    let coordinate: CLLocationCoordinate2D
    let ids : [Int]
    
    
    init(id: Int, title: String, responsible: String, coordinate: CLLocationCoordinate2D)
    {
        self.convenioName = title
        self.responsibleName = responsible
        self.coordinate = coordinate
        self.ids = [id]
        super.init()
    }
    
    init (conveniosId : [Int], coordinate: CLLocationCoordinate2D)
    {
        self.convenioName = "Múltiplos convênios"
        self.responsibleName = "Número de convênios: " + String(conveniosId.count)
        self.coordinate = coordinate
        self.ids = conveniosId
        super.init()
    }
    
    var title : String?
    {
        return convenioName
    }
    
    var subtitle: String?
    {
        return responsibleName
    }
    
    func mapItem() -> MKMapItem
    {
        let addressDictionary = [String(kABPersonAddressStreetKey): subtitle as! AnyObject]
        let placemark = MKPlacemark(coordinate: coordinate, addressDictionary: addressDictionary)
        
        let mapItem = MKMapItem(placemark: placemark)
        mapItem.name = title
        
        return mapItem
        
    }

}
