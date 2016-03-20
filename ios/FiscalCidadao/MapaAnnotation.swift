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
    let _title : String?
    let locationName: String
    let coordinate: CLLocationCoordinate2D
    let id : Int
    
    init(id: Int, title: String, locationName: String, coordinate: CLLocationCoordinate2D)
    {
        self._title = title
        self.locationName = locationName
        self.coordinate = coordinate
        self.id = id
        super.init()
    }
    
    var title : String?
    {
        return _title
    }
    
    var subtitle: String?
    {
        return locationName
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
