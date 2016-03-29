//
//  ConvenioViewController.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 19/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class ConvenioViewController: UIViewController, UITableViewDelegate, UITableViewDataSource
{
    
    var convenio : ConvenioModelo?

    @IBOutlet weak var descLabel: UILabel!
    @IBOutlet weak var proponentLabel: UILabel!
    @IBOutlet weak var responsibleLabel: UILabel!
    @IBOutlet weak var phoneLabel: UILabel!
    @IBOutlet weak var statusLabel: UILabel!
    @IBOutlet weak var valueLabel: UILabel!
    
    @IBOutlet weak var tableView: UITableView!
    
    let formarter = NSNumberFormatter()
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        formarter.locale = NSLocale(localeIdentifier: "pt_BR")
        formarter.positiveFormat = "###,##0.00"
        
        self.tableView.delegate = self
        self.tableView.dataSource = self
        if (convenio != nil)
        {
//            descLabel.text = convenio?.desc
//            proponentLabel.text = convenio?.proponent
//            responsibleLabel.text = convenio?.responsible
//            phoneLabel.text = convenio?.responsiblePhone
//            statusLabel.text = convenio?.status
//            
//            let formater = NSNumberFormatter()
//            formater.locale = NSLocale(localeIdentifier: "pt_BR")
//            formater.positiveFormat = "###,##0.00"
//            
//            let valor : Float = (convenio?.value)!
////          let strValue = NSString(format: "%.2f", valor)
//            let strValue = formater.stringFromNumber(NSNumber(float: valor))!
//            
//            valueLabel.text = "R$ " + (strValue as String)
//            
//            let size = convenio?.desc.characters.count
//            
//            if size > 150
//            {
//                descLabel.font = UIFont(name: (descLabel.font?.fontName)!, size: 12)
//            }
        }
        
    }


    override func didReceiveMemoryWarning()
    {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Table View
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int
    {
        return 1
    }

    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        return 12
    }

    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell
    {
        var cell : UITableViewCell!
        
        if indexPath.row % 2 == 0
        {
            cell = tableView.dequeueReusableCellWithIdentifier("SectionCell", forIndexPath: indexPath)
        }
        else
        {
            cell = tableView.dequeueReusableCellWithIdentifier("DetailCell", forIndexPath: indexPath)
        }
        
        switch indexPath.row
        {
        case 0:
            cell?.textLabel?.text = "Objeto"
        case 1:
            cell?.textLabel?.text = convenio?.desc
        case 2:
            cell?.textLabel?.text = "Proponente"
        case 3:
            cell?.textLabel?.text = convenio?.proponent
        case 4:
            cell?.textLabel?.text = "Responsável"
        case 5:
            cell?.textLabel?.text = convenio?.responsible
        case 6:
            cell?.textLabel?.text = "Telefone"
        case 7:
            cell?.textLabel?.text = convenio?.responsiblePhone
        case 8:
            cell?.textLabel?.text = "Período"
        case 9:
            cell?.textLabel?.text = (convenio?.startDate)! + " à " + (convenio?.endDate)!
        case 10:
            cell?.textLabel?.text = "Valor"
        case 11:
            cell?.textLabel?.text = "R$" + formarter.stringFromNumber(NSNumber(float: (convenio?.value)!))!
        default:
            cell?.textLabel?.text = " "
        }
//        let cell = tableView.dequeueReusableCellWithIdentifier("section", forIndexPath: indexPath)
        
        // Configure the cell...
        
        return cell
    }

    

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?)
    {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        
        if segue.identifier == "ComplaintSegue"
        {
            if let cvc = segue.destinationViewController as? ComplaintsViewController
            {
                cvc.convenio = convenio
            }
        }
    }
    

}
