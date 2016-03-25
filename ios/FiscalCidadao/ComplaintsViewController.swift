//
//  ComplaintsViewController.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 20/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class ComplaintsViewController: UIViewController
{

    @IBOutlet weak var descLabel: UILabel!
    
    @IBOutlet weak var textView: UITextView!
    
    var convenio : ConvenioModelo?
    
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
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
