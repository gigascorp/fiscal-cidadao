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

protocol PhotoViewDelegate
{
    func clickedPhoto(photo : PhotoView)
    func clickedRemovePhoto(photo : PhotoView)
}

class PhotoView: UIView
{
    var photo : UIImage?
    
    let IMAGE_MARGIN : CGFloat = 5
    
    var photoDelegate : PhotoViewDelegate?
    
    init(image : UIImage, frame : CGRect)
    {
        super.init(frame: frame)
        photo = image
        
        let imageViewSize = CGSize(width: frame.size.width - 2*IMAGE_MARGIN, height: frame.size.height - 2*IMAGE_MARGIN)
        let imageOrigin = CGPoint(x: IMAGE_MARGIN, y: IMAGE_MARGIN)
        
        let imageView = UIImageView(frame: CGRect(origin: imageOrigin, size: imageViewSize))
        imageView.contentMode = UIViewContentMode.ScaleAspectFit
        imageView.image = photo
        imageView.userInteractionEnabled = true
        
        let gesture = UITapGestureRecognizer(target: self, action: #selector(PhotoView.tapGesture))
        imageView.addGestureRecognizer(gesture)
        self.addSubview(imageView)
    }
    
    func tapGesture()
    {
        if photoDelegate != nil
        {
            photoDelegate?.clickedPhoto(self)
        }
    }
    
    func tapRemoveGesture()
    {
        if photoDelegate != nil
        {
            photoDelegate?.clickedRemovePhoto(self)
        }
    }
    
    func setRemovable()
    {
        let removeIcon = UIImage(named: "ic_remove_circle_white_18pt")
        let imageView = UIImageView(image: removeIcon?.imageWithRenderingMode(UIImageRenderingMode.AlwaysTemplate))
        imageView.frame.origin.x = self.frame.width - (removeIcon?.size.width)!
        imageView.frame.origin.y = IMAGE_MARGIN / 2
        imageView.userInteractionEnabled = true
        imageView.tintColor = UIColor.redColor()
        self.addSubview(imageView)
        
        let gesture = UITapGestureRecognizer(target: self, action: #selector(PhotoView.tapRemoveGesture))
        imageView.addGestureRecognizer(gesture)
    }
    
    required init(coder: NSCoder)
    {
        super.init(coder: coder)!
    }

}
