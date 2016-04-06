//
//  LoadingView.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 04/04/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class LoadingView: NSObject
{
    var overlay : UIView?
    var loadingIndicator : UIActivityIndicatorView?
    
    
    func showLoadView(viewController: UIViewController)
    {
        if overlay == nil
        {
            overlay = UIView(frame: viewController.view.frame)
            overlay?.backgroundColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0.5)
        }
        
        viewController.view.addSubview(overlay!)
        
        if loadingIndicator == nil
        {
            loadingIndicator = UIActivityIndicatorView()
            loadingIndicator?.frame.origin = CGPoint(x: overlay!.frame.size.width/2, y: overlay!.frame.size.height/2)
            loadingIndicator?.frame.size = CGSize(width: 0, height: 0) // TODO: Check this
            loadingIndicator?.startAnimating()
            overlay?.addSubview(loadingIndicator!)
        }
        
    }
    
    func dismissView()
    {
        if overlay != nil
        {
            overlay?.removeFromSuperview()
        }
        
    }
}
