//
//  ModalmageView.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 06/04/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class ModalmageView: NSObject
{
    var overlay : UIView?
    var imageView : UIImageView?
    
    private let MARGIN : CGFloat = 20
    
    func showWithImage(view: UIView, image : UIImage)
    {
        
        if let scrollView = view as? UIScrollView
        {
            var frame = scrollView.frame
            
            frame.origin = scrollView.contentOffset
            
            overlay = UIView(frame: frame)
            overlay?.backgroundColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0.75)
            
            let tapGesture = UITapGestureRecognizer(target: self, action: #selector(ModalmageView.tapGesture))
            overlay?.addGestureRecognizer(tapGesture)
            
            scrollView.scrollEnabled = false
            
            view.addSubview(overlay!)
            
            let viewSize = view.frame.size
            imageView = UIImageView(frame: CGRect(origin: CGPoint(x: MARGIN, y: MARGIN), size: CGSize(width: viewSize.width - 2 * MARGIN, height: viewSize.height - 2 * MARGIN)))
            imageView!.contentMode = UIViewContentMode.ScaleAspectFit
            
            overlay?.addSubview(imageView!)

        }
        else
        {
            if overlay == nil
            {
                overlay = UIView(frame: view.frame)
                overlay?.backgroundColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0.75)
                
                let tapGesture = UITapGestureRecognizer(target: self, action: #selector(ModalmageView.tapGesture))
                overlay?.addGestureRecognizer(tapGesture)
            }
            
            view.addSubview(overlay!)
            
            if imageView == nil
            {
                let viewSize = view.frame.size
                imageView = UIImageView(frame: CGRect(origin: CGPoint(x: MARGIN, y: MARGIN), size: CGSize(width: viewSize.width - 2 * MARGIN, height: viewSize.height - 2 * MARGIN)))
                imageView!.contentMode = UIViewContentMode.ScaleAspectFit
                
                overlay?.addSubview(imageView!)
            }

        }
        
        
        imageView!.image = image
        
    }
    
    func tapGesture()
    {
        self.dismissView()
    }
    
    func dismissView()
    {
        if let scrollView = self.overlay?.superview as? UIScrollView
        {
            scrollView.scrollEnabled = true
        }
        
        if overlay != nil
        {
            overlay?.removeFromSuperview()
        }
        
    }
    

}
