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
    
    let regionRadius: CLLocationDistance = 6000

    
    // São Luís UFMA: -2,554014, -44,307548
    override func viewDidLoad()
    {
        
        navigationController?.setNavigationBarHidden(false, animated: true)
        
        LocationController.sharedInstance.addObserver(self)
        let dataController = DataController.sharedInstance
        
        if(dataController.allConvenios.isEmpty && LocationController.sharedInstance.hasLocation())
        {
            requestLocation()
        }
        
        for convenio in dataController.allConvenios
        {
            loadAnnotation(convenio)
        }
        mapView.delegate = self
    }
    
    override func preferredStatusBarStyle() -> UIStatusBarStyle
    {
        return UIStatusBarStyle.LightContent
    }
    
    func loadAnnotation(convenio : Convenio)
    {
        let (lat, lng) = convenio.location
        let loc =  CLLocationCoordinate2DMake(lat, lng)
        
        mapView.addAnnotation(MapaAnnotation(id: convenio.id, title: convenio.desc, responsible: convenio.responsible, coordinate: loc))
    }
    
    func loadMultipleAnnotation(convenios : [Convenio])
    {
        if !convenios.isEmpty
        {
            let (lat, lng) = (convenios.first?.location)!
            let loc =  CLLocationCoordinate2DMake(lat, lng)
            
            var ids = [Int]()
            for c in convenios
            {
                ids.append(c.id)
            }
            
            mapView.addAnnotation(MapaAnnotation(conveniosId: ids, coordinate: loc))
        }
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
            centerMapOnLocation(locationController.currentLocation!)
        }
    }
    
    func requestLocation()
    {
        let locationController = LocationController.sharedInstance;
        let dataController = DataController.sharedInstance
        dataController.getConvenios(locationController.getLocation(), onCompletion:
            {
                convenios in
                
                var annotationsDic = [String : [Convenio]]()
                
                for c in convenios
                {
                    let str = String(c.location.0) + " " + String(c.location.1)
                    
                    if var currentConvenios = annotationsDic[str]
                    {
                        currentConvenios.append(c)
                        annotationsDic[str] = currentConvenios
                    }
                    else
                    {
                        let currentConvenios = [c]
                        annotationsDic[str] = currentConvenios
                    }
                }
                
                for (_, array) in annotationsDic
                {
                    if array.count == 1
                    {
                        self.loadAnnotation(array.first!)
                    }
                    else
                    {
                        self.loadMultipleAnnotation(array)
                    }
                }
                
        })
        
    }
}