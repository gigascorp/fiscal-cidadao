//
//  ConvenioViewController.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 19/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class ConvenioViewController: UIViewController, UIScrollViewDelegate
{
    
    var convenio : ConvenioModelo?

    @IBOutlet weak var descLabel: UILabel!
    @IBOutlet weak var proponentLabel: UILabel!
    @IBOutlet weak var responsibleLabel: UILabel!
    @IBOutlet weak var phoneLabel: UILabel!
    @IBOutlet weak var statusLabel: UILabel!
    @IBOutlet weak var valueLabel: UILabel!
    
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        if (convenio != nil)
        {
            descLabel.text = convenio?.desc
            proponentLabel.text = convenio?.proponent
            responsibleLabel.text = convenio?.responsible
            phoneLabel.text = convenio?.responsiblePhone
            statusLabel.text = convenio?.status
            
            let formater = NSNumberFormatter()
            formater.locale = NSLocale(localeIdentifier: "pt_BR")
            formater.positiveFormat = "###,##0.00"
            
            let valor : Float = (convenio?.value)!
//            let strValue = NSString(format: "%.2f", valor)
            let strValue = formater.stringFromNumber(NSNumber(float: valor))!
            
            valueLabel.text = "R$ " + (strValue as String)
            
            let size = convenio?.desc.characters.count
            
            if size > 150
            {
                descLabel.font = UIFont(name: (descLabel.font?.fontName)!, size: 12)
            }
        }
        
    }


    override func didReceiveMemoryWarning()
    {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
