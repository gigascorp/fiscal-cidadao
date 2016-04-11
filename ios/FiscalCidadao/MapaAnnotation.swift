//
//  MapaAnnotation.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 17/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

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
