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

class DenunciaViewController: UIViewController, UIActionSheetDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate, UIAlertViewDelegate, UITextViewDelegate, PhotoGalleryDelegate
{
    
    @IBOutlet weak var textView: UITextView!
    
    @IBOutlet weak var imageView: UIImageView!
    
    @IBOutlet weak var scrollView: UIScrollView!
    
    @IBOutlet weak var textPlaceholder: UILabel!
    
    
    var convenio : Convenio?
    
    var images = [UIImage]()
    
    var denunciaSent = false
    
    let loadingView = LoadingView()
    
    let photoGalleryView = PhotoGalleryView()
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        if navigationController != nil
        {
            navigationController?.title = "Fazer Denúncia"
        }
        
        textView.delegate = self
        
//        textView.layer.cornerRadius = 8
//        textView.layer.borderWidth = 2
//        textView.layer.borderColor = UIColor.grayColor().CGColor
        
        photoGalleryView.galleryDelegate = self
        
        photoGalleryView.addToScrollView(scrollView)
        photoGalleryView.topView = self.view
        
        textView.returnKeyType = UIReturnKeyType.Done
    }
    
    override func viewDidAppear(animated: Bool)
    {
        super.viewDidAppear(animated)
    }
    
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?)
    {
        if !textView.layer.bounds.contains((touches.first?.locationInView(view))!)
        {
            self.view.endEditing(true)
        }
    }

    func dismissKeyboard()
    {
        //Causes the view (or one of its embedded text fields) to resign the first responder status.
        textView.endEditing(true)
    }
    
    override func didReceiveMemoryWarning()
    {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func sendDenunciaAction(sender: AnyObject)
    {
        if(convenio != nil)
        {
            let denuncia = Denuncia(convenioId: convenio!.id);
            denuncia.comments = textView.text
            
            for img in images
            {
                if let data = UIImageJPEGRepresentation(img, 0.5)
                {
                    let data64 = data.base64EncodedDataWithOptions(NSDataBase64EncodingOptions.Encoding64CharacterLineLength)
                    if let str64 = String(data: data64, encoding: NSUTF8StringEncoding)
                    {
                        denuncia.photos.append(str64)
                    }
                }
            }
            
            let data = DataController.sharedInstance
            data.sendDenuncia(denuncia, onCompletion:
            {
                isOk in
                if(isOk)
                {
                    print("ok")
                    let alert = UIAlertView(title: "Denúncia", message: "Denúncia enviada com sucesso!", delegate: self, cancelButtonTitle: "OK")
                    self.denunciaSent = true
                    alert.delegate = self
                    alert.show()
                }
                else
                {
                    print("not ok")
                    let alert = UIAlertView(title: "Erro!", message: ("Erro ao enviar denúncia, por favor tente mais tarde!"), delegate: self, cancelButtonTitle: "OK")
                    alert.delegate = self
                    alert.show()
                }
                    
            });
            
            self.disableView();
        }
    }

    @IBAction func photoAction(sender: AnyObject)
    {
        let action = UIActionSheet(title: "Anexar foto", delegate: self, cancelButtonTitle: "Cancelar", destructiveButtonTitle: nil, otherButtonTitles: "Tirar uma foto", "Procurar na galeria")
        action.showInView(self.view)
        
    }
    
    func actionSheet(actionSheet: UIActionSheet, clickedButtonAtIndex buttonIndex: Int)
    {
        if buttonIndex == 1 || buttonIndex == 2
        {
            let imagePicker : UIImagePickerController = UIImagePickerController()
            
            imagePicker.delegate = self
            imagePicker.allowsEditing = false;
            
            if buttonIndex == 1 //
            {
                imagePicker.sourceType = UIImagePickerControllerSourceType.Camera
            }
            else if buttonIndex == 2
            {
                imagePicker.sourceType = UIImagePickerControllerSourceType.PhotoLibrary
            }
            
            self.presentViewController(imagePicker, animated: true, completion: {})
        }
    }
    
    func imagePickerController( picker: UIImagePickerController,
                                didFinishPickingMediaWithInfo info: [String : AnyObject])
    {
        self.dismissViewControllerAnimated(true, completion: {})
        
        if let image = info[UIImagePickerControllerOriginalImage] as? UIImage
        {
            images.append(image)
            photoGalleryView.addPhoto(image, removable: true)
        }
        UIApplication.sharedApplication().statusBarStyle = UIStatusBarStyle.LightContent

    }
    
    func disableView()
    {
        self.navigationItem.hidesBackButton = true
        self.navigationItem.rightBarButtonItem?.enabled = false
        for tab in (self.tabBarController?.tabBar.items)!
        {
            tab.enabled = false
        }
        loadingView.showLoadView(self.view);
    }
    
    func enableView()
    {
        self.navigationItem.hidesBackButton = false
        self.navigationItem.rightBarButtonItem?.enabled = true
        for tab in (self.tabBarController?.tabBar.items)!
        {
            tab.enabled = true
        }
        loadingView.dismissView()
    }
    
    
    func alertViewCancel(alertView: UIAlertView)
    {
        self.enableView();
        if(denunciaSent)
        {
            self.navigationController?.popViewControllerAnimated(true)
        }
        denunciaSent = false
    }
    
    func alertView(alertView: UIAlertView, clickedButtonAtIndex buttonIndex: Int)
    {
        self.enableView();
        if(denunciaSent)
        {
            self.navigationController?.popViewControllerAnimated(true)
        }
        denunciaSent = false
        
    }
    
    func onPhotoRemoved(index : Int)
    {
        images.removeAtIndex(index)
    }
    
    func textViewDidEndEditing(textView: UITextView)
    {
        textPlaceholder.hidden = textView.hasText()
        
    }
    
    func textViewDidBeginEditing(textView: UITextView)
    {
        textPlaceholder.hidden = true
    }
    
    func textViewShouldEndEditing(textView: UITextView) -> Bool {
        textView.resignFirstResponder()
        return true
    }
    
    func textView(textView: UITextView, shouldChangeTextInRange range: NSRange, replacementText text: String) -> Bool
    {
        if text == "\n"
        {
            textView.endEditing(true)
            return false
        }
        return true;

    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
