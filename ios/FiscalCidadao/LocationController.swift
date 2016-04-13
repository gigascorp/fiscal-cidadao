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

import Foundation

import MapKit

enum
LocationStatus
{
    case NO_STATUS, LOCATION_RESTRICTED, LOCATION_DENIED, LOCATION_ALLOWED
}


class LocationController : BaseController, CLLocationManagerDelegate
{
    static let InitializedLocationMessage = 1
    static let sharedInstance = LocationController()
    var locationManager: CLLocationManager!
    
    var currentLocation : CLLocation?
    var locationStatus = LocationStatus.NO_STATUS
    
    override init()
    {
        super.init()
        
        locationManager = CLLocationManager()
        switch CLLocationManager.authorizationStatus()
        {
        case CLAuthorizationStatus.Restricted:
            locationStatus = LocationStatus.LOCATION_RESTRICTED
        case CLAuthorizationStatus.Denied:
            locationStatus = LocationStatus.LOCATION_DENIED
        case CLAuthorizationStatus.NotDetermined:
            locationStatus = LocationStatus.NO_STATUS
        case CLAuthorizationStatus.Authorized:
            locationStatus = LocationStatus.LOCATION_ALLOWED
        case CLAuthorizationStatus.AuthorizedWhenInUse:
            locationStatus = LocationStatus.LOCATION_ALLOWED

        }
        
        if locationStatus == LocationStatus.LOCATION_ALLOWED
        {
            locationManager.startUpdatingLocation()
            if locationManager.location != nil
            {
                currentLocation = locationManager.location
            }
        }
        locationManager.delegate = self
        
    }
    
    func hasLocation() -> Bool
    {
        
        if locationStatus == LocationStatus.LOCATION_ALLOWED
        {
            if currentLocation != nil
            {
                return true
            }
        }
        
        return false
    }
    
    func getLocation() -> (Double, Double)
    {
        let loc = currentLocation!
        let coord = loc.coordinate
        return (coord.latitude, coord.longitude)
    }
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError)
    {
        locationManager.stopUpdatingLocation()
        print(error)
    }
    
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation])
    {
        let isNewLocation = currentLocation == nil
        currentLocation =  locations.last
        
        if isNewLocation
        {
            notify(LocationController.InitializedLocationMessage)
        }
        
//        print("Last location : \(currentLocation)")
    }
    
    func requestAuth()
    {
        let authorizationStatus = CLLocationManager.authorizationStatus()
        
        if(authorizationStatus == CLAuthorizationStatus.Authorized)
        {
            locationManager.startUpdatingLocation()
        }
        else if #available(iOS 8.0, *)
        {
            if (authorizationStatus == .AuthorizedWhenInUse || authorizationStatus == .AuthorizedAlways)
            {
                locationManager.startUpdatingLocation()
            }
            else if self.locationManager.respondsToSelector(#selector(CLLocationManager.requestAlwaysAuthorization))
            {
                self.locationManager.requestWhenInUseAuthorization()
            }
            else
            {
                locationManager.startUpdatingLocation()
            }
        }
        else
        {
            // TODO:
        }
    }
    
    func locationManager(manager: CLLocationManager, didChangeAuthorizationStatus status: CLAuthorizationStatus)
    {
            var shouldIAllow = false
            
            switch status
            {
            case CLAuthorizationStatus.Restricted:
                locationStatus = LocationStatus.LOCATION_RESTRICTED
            case CLAuthorizationStatus.Denied:
                locationStatus = LocationStatus.LOCATION_DENIED
            case CLAuthorizationStatus.NotDetermined:
                locationStatus = LocationStatus.NO_STATUS
            case CLAuthorizationStatus.Authorized:
                locationStatus = LocationStatus.LOCATION_ALLOWED
                shouldIAllow = true
            case CLAuthorizationStatus.AuthorizedWhenInUse:
                locationStatus = LocationStatus.LOCATION_ALLOWED
                shouldIAllow = true
                
                
            }
            if (shouldIAllow)
            {
                print("Location Allowed")
                locationManager.startUpdatingLocation()
                
                print(locationManager.location)
            }
            else
            {
                print("Denied access: \(locationStatus)")
            }
    }
    

    
}