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

class DenunciaReadViewController: UITableViewController
{
    var denuncia : Denuncia?
    var convenio : Convenio?
    
    @IBOutlet weak var descLabel: UILabel!
    @IBOutlet weak var proponentLabel: UILabel!
    @IBOutlet weak var responsibleLabel: UILabel!
    @IBOutlet weak var phoneLabel: UILabel!
    @IBOutlet weak var statusLabel: UILabel!
    @IBOutlet weak var valueLabel: UILabel!
    
    let photoGalleryView = PhotoGalleryView()
    
    
    let formarter = NSNumberFormatter()
    
    var descHeight : CGFloat = 0
    var commentsHeight : CGFloat = 0
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        formarter.locale = NSLocale(localeIdentifier: "pt_BR")
        formarter.positiveFormat = "###,##0.00"
        
        self.tableView.delegate = self
        self.tableView.dataSource = self
        
        self.convenio = self.denuncia?.convenio
        
        var attributes = [String : AnyObject]()
        
        let font  = UIFont.systemFontOfSize(15)
        
        attributes[NSFontAttributeName] = font
        
        let margin : CGFloat = 15 // Cell margin checked in storyboard
        let width = (tableView.frame.size.width - 2 * margin)
        
        // This is very tricky.
        //
        let baseSize = CGSize(width: width ,height: CGFloat.max)
        
        if let desc = convenio?.desc
        {
            let boundingRect = desc.boundingRectWithSize(baseSize, options: NSStringDrawingOptions.UsesLineFragmentOrigin, attributes: attributes, context: nil)
            
            // Add a padding
            descHeight = boundingRect.height + 40
        }
        else
        {
            descHeight = tableView.rowHeight
        }
        
        if let comments = denuncia?.comments
        {
            let boundingRect = comments.boundingRectWithSize(baseSize, options: NSStringDrawingOptions.UsesLineFragmentOrigin, attributes: attributes, context: nil)
            
            // Add a padding
            commentsHeight = boundingRect.height + 40
        }
        else
        {
            commentsHeight = tableView.rowHeight
        }
        
        let data = DataController.sharedInstance
        
        for photo in denuncia!.photos
        {
            data.makeHTTPGetRequest(photo, body: nil, onCompletion:
            {
                (data, err) in
                let img  = UIImage(data: data)
                self.photoGalleryView.addPhoto(img!, removable: false)
            })

        }

    }
    
    
    override func didReceiveMemoryWarning()
    {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Table View
    
    override func numberOfSectionsInTableView(tableView: UITableView) -> Int
    {
        return 1
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        if denuncia!.photos.isEmpty
        {
            return 18
        }
        else
        {
            return 20
        }
        
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell
    {
        var cell : UITableViewCell!
        
        if indexPath.row % 2 == 0
        {
            cell = tableView.dequeueReusableCellWithIdentifier("SectionCell", forIndexPath: indexPath)
        }
        else if indexPath.row == 19
        {
            cell = tableView.dequeueReusableCellWithIdentifier("GalleryCell", forIndexPath: indexPath)
        }
        else
        {
            cell = tableView.dequeueReusableCellWithIdentifier("DetailCell", forIndexPath: indexPath)
        }
        
        switch indexPath.row
        {
        case 0:
            cell?.textLabel?.text = "Denúncia"
        case 1:
            cell?.textLabel?.text = denuncia?.comments
        case 2:
            cell?.textLabel?.text = "Data da denúncia"
        case 3:
            cell?.textLabel?.text = denuncia?.denunciaDate!
        case 4:
            cell?.textLabel?.text = "Parecer do governo"
        case 5:
            cell?.textLabel?.text = denuncia?.status
        case 6:
            cell?.textLabel?.text = "Descrição"
        case 7:
            cell?.textLabel?.text = convenio?.desc
        case 8:
            cell?.textLabel?.text = "Proponente"
        case 9:
            cell?.textLabel?.text = convenio?.proponent
        case 10:
            cell?.textLabel?.text = "Responsável"
        case 11:
            cell?.textLabel?.text = convenio?.responsible
        case 12:
            cell?.textLabel?.text = "Telefone"
        case 13:
            cell?.textLabel?.text = convenio?.responsiblePhone
        case 14:
            cell?.textLabel?.text = "Período"
        case 15:
            cell?.textLabel?.text = (convenio?.startDate)! + " à " + (convenio?.endDate)!
        case 16:
            cell?.textLabel?.text = "Valor"
        case 17:
            cell?.textLabel?.text = "R$ " + formarter.stringFromNumber(NSNumber(float: (convenio?.value)!))!
        case 18:
            cell?.textLabel?.text = "Fotos Enviadas"
        case 19:
            if let galleryCell = cell as? GalleryTableViewCell
            {
                photoGalleryView.topView = self.view
                photoGalleryView.addToScrollView(galleryCell.scrollView)
            }
        default:
            cell?.textLabel?.text = " "
        }
        //        let cell = tableView.dequeueReusableCellWithIdentifier("section", forIndexPath: indexPath)
        
        // Configure the cell...
        
        return cell
    }
    
    
    override func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat
    {
        if indexPath.row == 1
        {
            return commentsHeight
        }
        else if indexPath.row == 19
        {
            return 120 // Hard coded, got by the storyboard
        }
        else if indexPath.row == 7
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

