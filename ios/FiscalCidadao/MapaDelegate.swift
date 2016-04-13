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

import Foundation


import MapKit
import UIKit

extension MapaViewController : MKMapViewDelegate, Observer
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
        
        if location.ids.count == 1
        {
            performSegueWithIdentifier("DetailSegue", sender: location)
        }
        else if location.ids.count > 1
        {
            performSegueWithIdentifier("MultipleConveniosSegue", sender: location)
        }
        
        
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
                
                if location.ids.count == 1
                {
                    let convenio = data.getConvenio(location.ids.first!)
                    
                    if let cvc = segue.destinationViewController as? ConvenioViewController
                    {
                        cvc.convenio = convenio
                    }
                }
            }
        }
        else if segue.identifier == "MultipleConveniosSegue"
        {
            if let location = sender as? MapaAnnotation
            {
                if let cvc = segue.destinationViewController as? ConveniosListViewController
                {
                    var convenios  = [Convenio]()
                        
                    let data = DataController.sharedInstance
                    for id  in location.ids
                    {
                        convenios.append(data.getConvenio(id)!)
                    }
                        
                    cvc.convenios = convenios
                }
            }
        }
    }
    
    func getSelectorForMessage(message: Int, sender: AnyObject?) -> Selector?
    {
        if message == LocationController.InitializedLocationMessage
        {
            return #selector(MapaViewController.onLocationInitialized)
        }
        return nil
    }
    
    var theObserver: NSObject?
    {
        return self
    }
    
    func onLocationInitialized()
    {
        requestLocation()
        initializeLocation()
    }
}