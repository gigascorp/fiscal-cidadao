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
