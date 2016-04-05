//
//  ComplaintsViewController.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 20/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class DenunciaViewController: UIViewController, UIActionSheetDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate
{
    @IBOutlet weak var descLabel: UILabel!
    
    @IBOutlet weak var textView: UITextView!
    
    @IBOutlet weak var imageView: UIImageView!
    
    var convenio : Convenio?
    
    var images = [UIImage]()
    
    let loadingView = LoadingView()
    
    override func viewDidLoad()
    {
        super.viewDidLoad()

        if convenio != nil
        {
            descLabel.text = convenio?.desc
        }
        
        if navigationController != nil
        {
            navigationController?.title = "Fazer Denúncia"
        }
        
        textView.layer.cornerRadius = 8
        textView.layer.borderWidth = 2
        textView.layer.borderColor = UIColor.grayColor().CGColor
        
        
//        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: "dismissKeyboard")
//        view.addGestureRecognizer(tap)
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
//                if let data = UIImagePNGRepresentation(img)
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
                    alert.show()
                    
                    self.enableView();
                    self.navigationController?.popViewControllerAnimated(true)
                }
                else
                {
                    print("not ok")
                    let alert = UIAlertView(title: "Erro!", message: ("Erro ao enviar denúncia, por favor tente mais tarde!"), delegate: self, cancelButtonTitle: "OK")
                    alert.show()
                    self.enableView()
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
        }
    }
    
    func disableView()
    {
        self.navigationItem.hidesBackButton = true
        self.navigationItem.rightBarButtonItem?.enabled = false
        for tab in (self.tabBarController?.tabBar.items)!
        {
            tab.enabled = false
        }
        loadingView.showLoadView(self);
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
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
