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
        
        if hasLocation()
        {
            locationManager.startUpdatingLocation()
        }
        locationManager.delegate = self
        
    }
    
    func hasLocation() -> Bool
    {
        return locationStatus == LocationStatus.LOCATION_ALLOWED
    }
    
    func getLocation() -> (Double, Double)
    {
        let loc = locationManager.location!
        let coord = loc.coordinate
        return (coord.latitude, coord.longitude)
    }
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError)
    {
        locationManager.stopUpdatingLocation()
        
//        if (error as! NSError)
//        {
//            if (seenError == false) {
//                seenError = true
//                print(error)
//            }
//        }
    }
    
//    func locationManager(manager: CLLocationManager!, didUpdateLocations locations: AnyObject[]!)
//    {
//        if (locationFixAchieved == false)
//        {
//            locationFixAchieved = true
//            var locationArray = locations as NSArray
//            var locationObj = locationArray.lastObject as CLLocation
//            var coord = locationObj.coordinate
//            
//            println(coord.latitude)
//            println(coord.longitude)
//        }
//    }
    
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
//                locationStatus = "Restricted Access to location"
                locationStatus = LocationStatus.LOCATION_RESTRICTED
            case CLAuthorizationStatus.Denied:
//                locationStatus = "User denied access to location"
                locationStatus = LocationStatus.LOCATION_DENIED
            case CLAuthorizationStatus.NotDetermined:
//                locationStatus = "Status not determined"
                locationStatus = LocationStatus.NO_STATUS
            case CLAuthorizationStatus.Authorized:
                locationStatus = LocationStatus.LOCATION_ALLOWED
                shouldIAllow = true
            case CLAuthorizationStatus.AuthorizedWhenInUse:
                locationStatus = LocationStatus.LOCATION_ALLOWED
                shouldIAllow = true
                
                
            }
//            NSNotificationCenter.defaultCenter().postNotificationName("LabelHasbeenUpdated", object: nil)
            if (shouldIAllow)
            {
                print("Location to Allowed")
                // Start location services
                locationManager.startUpdatingLocation()
            } else {
                print("Denied access: \(locationStatus)")
            }
    }
    

    
}