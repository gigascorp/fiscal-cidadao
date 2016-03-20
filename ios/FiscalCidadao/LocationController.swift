//
//  LocationController.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 18/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import Foundation

import MapKit

enum
LocationStatus
{
    case NO_STATUS, LOCATION_RESTRICTED, LOCATION_DENIED, LOCATION_ALLOWED
}

class LocationController : NSObject, CLLocationManagerDelegate
{
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
        currentLocation =  locations.last
        print("Last location : \(currentLocation)")
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
            else if self.locationManager.respondsToSelector(Selector("requestAlwaysAuthorization"))
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