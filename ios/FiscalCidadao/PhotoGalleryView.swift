//
//  PhotoGalleryView.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 05/04/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

protocol PhotoGalleryDelegate
{
    func onPhotoRemoved(index : Int)
}

class PhotoGalleryView: UIView, PhotoViewDelegate, UIActionSheetDelegate
{
    var photos = [PhotoView]()
    var galleryDelegate : PhotoGalleryDelegate? = nil
    
    var removeActionSheet : UIActionSheet? = nil
    
    private let MARGIN : CGFloat = 5
    private let PHOTO_VIEW_SIZE : CGFloat = 80.0
    
    private var indexToRemove : Int = -1
    
    let popupPreview = ModalmageView()
    
    func addToScrollView(scrollView : UIScrollView)
    {
        self.frame = CGRect(x: 0, y: 0, width: scrollView.frame.size.width, height: scrollView.frame.size.height)
        
        scrollView.contentSize = self.frame.size
        
        scrollView.addSubview(self)
    }
     
    func addPhoto(photo : UIImage, removable : Bool)
    {
        let offset = MARGIN + CGFloat(photos.count) * ( PHOTO_VIEW_SIZE + MARGIN )
        let origin = CGPoint(x: offset, y: MARGIN)
        let size = CGSize(width: PHOTO_VIEW_SIZE, height: PHOTO_VIEW_SIZE);
        let frame = CGRect(origin: origin, size: size)
        
        let photoView = PhotoView(image: photo, frame: frame)
            
        photos.append(photoView)
        
        self.updateViewBounds()
        
        if(removable)
        {
            photoView.setRemovable()
        }
        
        photoView.photoDelegate = self
        
        self.addSubview(photoView)
    }
    
    func updateViewBounds()
    {
        if let parentScrollView = self.superview as? UIScrollView
        {
            let newContentWidth = 2.0 * MARGIN + CGFloat(photos.count) * ( PHOTO_VIEW_SIZE + MARGIN )
            
            if newContentWidth < parentScrollView.frame.width
            {
                var frame = self.frame;
                frame.size.width = parentScrollView.frame.width
                self.frame = frame
                
                var contentSize = parentScrollView.contentSize
                contentSize.width = parentScrollView.frame.width
                parentScrollView.contentSize.width = newContentWidth
            }
            else
            {
                var frame = self.frame;
                frame.size.width = newContentWidth
                self.frame = frame
                
                parentScrollView.contentSize.width = newContentWidth
            }
        }
    }
    
    func removePhoto(index : Int)
    {
        let photo = photos[index]
        
        if index + 1 < photos.count
        {
            for i in index+1...photos.count-1
            {
                let p = photos[i]
                p.frame.origin.x = p.frame.origin.x - PHOTO_VIEW_SIZE - MARGIN
            }
        }
        
        photo.removeFromSuperview()
        photos.removeAtIndex(index)
        updateViewBounds()
        
        if galleryDelegate != nil
        {
            galleryDelegate?.onPhotoRemoved(index)
        }
    }
    
    func getPhotoIndex(photo : PhotoView) -> Int
    {
        var i = 0
        for p in photos
        {
            if p == photo
            {
                return i
            }
            i += 1
        }
        return -1;
    }
    
    func removePhotoView(photo : PhotoView)
    {
        let i = self.getPhotoIndex(photo)
        if i < photos.count
        {
            removePhoto(i)
        }
    }
    
    func clickedPhoto(photo: PhotoView)
    {    
        if self.superview != nil // scrollview
        {
            if ((self.superview?.superview) != nil) // rootView
            {
                popupPreview.showWithImage((self.superview?.superview)!, image: photo.photo!)
            }
        }
        
    }
    
    func clickedRemovePhoto(photo: PhotoView)
    {
        if removeActionSheet == nil
        {
            removeActionSheet = UIActionSheet(title: "Remover foto?", delegate: self, cancelButtonTitle: "Cancelar", destructiveButtonTitle: "Apagar")
        }
        
        indexToRemove = getPhotoIndex(photo)
        if self.superview != nil // scroll view
        {
            if self.superview?.superview != nil // root view
            {
                removeActionSheet?.showInView((self.superview?.superview)!)
            }
            else
            {
                removeActionSheet?.showInView((self.superview)!)
            }
        }
        else
        {
            removePhotoView(photo) // do something...
            indexToRemove = -1
        }
    }
    
    func actionSheet(actionSheet: UIActionSheet, clickedButtonAtIndex buttonIndex: Int) {
        switch buttonIndex
        {
        case actionSheet.destructiveButtonIndex:
            if indexToRemove >= 0
            {
                removePhoto(indexToRemove)
                indexToRemove = -1
            }
            break
        default: ()
        }
    }
    

}
