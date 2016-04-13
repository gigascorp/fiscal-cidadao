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

import UIKit

class LoadingView: NSObject
{
    var overlay : UIView?
    var loadingIndicator : UIActivityIndicatorView?
    
    
    func showLoadView(rootView: UIView)
    {
        if overlay == nil
        {
            overlay = UIView(frame: rootView.frame)
            overlay?.backgroundColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0.5)
        }
        
        rootView.addSubview(overlay!)
        
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
