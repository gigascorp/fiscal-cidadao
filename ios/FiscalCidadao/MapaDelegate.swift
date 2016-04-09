//
//  MapaDelegate.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 17/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import Foundation


import MapKit
import UIKit

extension MapaViewController : MKMapViewDelegate
{
    func mapView(mapView: MKMapView, viewForAnnotation annotation: MKAnnotation) -> MKAnnotationView?
    {
        if let annotation = annotation as? MapaAnnotation
        {
            let identifier = "pin"
            var view: MKPinAnnotationView
            if let dequeuedView = mapView.dequeueReusableAnnotationViewWithIdentifier(identifier)
                as? MKPinAnnotationView { // 2
                    dequeuedView.annotation = annotation
                    view = dequeuedView
            }
            else
            {
                view = MKPinAnnotationView(annotation: annotation, reuseIdentifier: identifier)
                view.canShowCallout = true
                view.calloutOffset = CGPoint(x: -5, y: 5)
                view.rightCalloutAccessoryView = UIButton(type: .DetailDisclosure) as UIView
            }
            return view
        }
        return nil
    }
    
    func mapView(mapView: MKMapView, annotationView view: MKAnnotationView, calloutAccessoryControlTapped control: UIControl)
    {
            let location = view.annotation as! MapaAnnotation
//            let launchOptions = [MKLaunchOptionsDirectionsModeKey: MKLaunchOptionsDirectionsModeDriving]
//            location.mapItem().openInMapsWithLaunchOptions(launchOptions)
        performSegueWithIdentifier("DetailSegue", sender: location)
        
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?)
    {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        
        if(segue.identifier == "DetailSegue")
        {
            if let location = sender as? MapaAnnotation
            {
                let data = DataController.sharedInstance
                let convenio = data.getConvenio(location.id)
                
                if let nvc = segue.destinationViewController as? UINavigationController
                {
                    if let cvc = nvc.viewControllers.first as? ConvenioViewController
                    {
                        cvc.convenio = convenio
                    }
                }
//                    if let cvc = segue.destinationViewController as? ConvenioViewController
//                    {
//                        cvc.convenio = convenio
//                    }
            }
        }
    }
    
    
    
}