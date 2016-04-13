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

class ConvenioViewController: UIViewController, UITableViewDelegate, UITableViewDataSource
{
    
    var convenio : Convenio?

    @IBOutlet weak var descLabel: UILabel!
    @IBOutlet weak var proponentLabel: UILabel!
    @IBOutlet weak var responsibleLabel: UILabel!
    @IBOutlet weak var phoneLabel: UILabel!
    @IBOutlet weak var statusLabel: UILabel!
    @IBOutlet weak var valueLabel: UILabel!
    
    @IBOutlet weak var tableView: UITableView!
    
    let formarter = NSNumberFormatter()
    
    var descHeight : CGFloat = 0
    
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
        
//        let font = UIFont(name: "Helvetica", size: 10)
        
        var attributes = [String : AnyObject]()
        
        let font  = UIFont.systemFontOfSize(15)
        
        attributes[NSFontAttributeName] = font
        
        let margin : CGFloat = 15 // Cell margin checked in storyboard
        let width = (tableView.frame.size.width - 2 * margin)
        
        // This is very tricky.
        // 
        let baseSize = CGSize(width: width * 0.5,height: CGFloat.max)
//        let baseSize = CGSize(width: width ,height: CGFloat.max)
        
        if let desc = convenio?.desc
        {
            let boundingRect = desc.boundingRectWithSize(baseSize, options: NSStringDrawingOptions.UsesLineFragmentOrigin, attributes: attributes, context: nil)
            
            
            // Add a padding
            descHeight = boundingRect.height + 40
            
            // DEBUG CODE
//            print(desc)
//            print("Size: ")
//            print(boundingRect)
//            let origin = CGPoint(x : 15.0/2.0, y: 400)
//            boundingRect.origin = origin
//            
//            let width : CGFloat = 570/2.0
//            boundingRect.size = CGSize(width: width, height:boundingRect.size.height)
//            let label = UILabel(frame: CGRect(origin: CGPoint(x : 15.0/2.0, y: 400), size: CGSize(width: width, height: 100)))
////            label.backgroundColor = UIColor.redColor()
//            label.text = desc
//            label.layer.borderColor = UIColor.redColor().CGColor
//            label.layer.borderWidth = 2.0
//            label.numberOfLines = 0
//            label.font = font
//            label.textAlignment = NSTextAlignment.Left
//            label.lineBreakMode = NSLineBreakMode.ByWordWrapping
//            
//            
//            let dummyView = UIView(frame: boundingRect)
//            dummyView.backgroundColor = UIColor.blueColor()
//            dummyView.layer.borderColor = UIColor.magentaColor().CGColor
//            dummyView.layer.borderWidth = 2.0
//            view.addSubview(dummyView)
//            
//            self.view.addSubview(label)
            
        }
        else
        {
            descHeight = tableView.rowHeight
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
            cell?.textLabel?.text = "Descrição"
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
            cell?.textLabel?.text = "R$ " + formarter.stringFromNumber(NSNumber(float: (convenio?.value)!))!
        default:
            cell?.textLabel?.text = " "
        }
//        let cell = tableView.dequeueReusableCellWithIdentifier("section", forIndexPath: indexPath)
        
        // Configure the cell...
        
        return cell
    }

    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat
    {
        if( indexPath.row == 1)
        {
            return descHeight
        }
        else
        {
            return self.tableView.rowHeight
        }
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?)
    {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        
        if segue.identifier == "ComplaintSegue"
        {
            if let cvc = segue.destinationViewController as? DenunciaViewController
            {
                cvc.convenio = convenio
            }
        }
    }
    

}
