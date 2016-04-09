//
//  PhotoView.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 05/04/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

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
