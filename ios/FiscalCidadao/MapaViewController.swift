//
//  MapaViewController.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 17/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import Foundation
import UIKit
import MapKit

class MapaViewController: UIViewController
{
    @IBOutlet weak var mapView: MKMapView!
    
    let regionRadius: CLLocationDistance = 3000
    
    // São Luís UFMA: -2.554014, -44.307548
    override func viewDidLoad()
    {
        navigationController?.setNavigationBarHidden(false, animated: true)
        
        let dataController = DataController.sharedInstance
        
        for convenio in dataController.allConvenios
        {
            let (lat, lng) = convenio.location
            let loc =  CLLocationCoordinate2DMake(lat, lng)
            
            mapView.addAnnotation(MapaAnnotation(id: convenio.id, title: convenio.desc, locationName: convenio.responsible, coordinate: loc))
        }
        mapView.delegate = self
    }
    
    override func viewDidAppear(animated: Bool)
    {
        super.viewDidAppear(animated)
        
        let locationController = LocationController.sharedInstance
        if(locationController.hasLocation())
        {
            initializeLocation()
        }
    }
    
    
    func centerMapOnLocation(location: CLLocation)
    {
        let coordinateRegion = MKCoordinateRegionMakeWithDistance(location.coordinate,
            regionRadius * 2.0, regionRadius * 2.0)
        mapView.setRegion(coordinateRegion, animated: true)
    }
    
    func initializeLocation()
    {
        let locationController = LocationController.sharedInstance
        if(locationController.hasLocation())
        {
            mapView.showsUserLocation = true
            centerMapOnLocation(locationController.locationManager.location!)
        }
    }
}