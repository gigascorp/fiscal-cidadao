//
//  DenunciasTableViewController.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 04/04/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class DenunciasTableViewController: UITableViewController
{
    
    var denuncias = [Denuncia]()

    override func viewDidLoad()
    {
        super.viewDidLoad()

        self.loadData()
        
        self.refreshControl = UIRefreshControl()
        
        self.refreshControl?.addTarget(self, action: #selector(DenunciasTableViewController.loadData), forControlEvents: UIControlEvents.ValueChanged)
        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
    }
    
    func loadData()
    {
        let data = DataController.sharedInstance
        
        let id = Perfil.getUserIdByDevice()
        data.getDenuncias(id!, onCompletion: {denuncias in
            
            self.denuncias = denuncias
            self.tableView.reloadData()
            if self.refreshControl != nil
            {
                self.refreshControl?.endRefreshing()
            }
        })
    }
    
    
    override func viewDidAppear(animated: Bool)
    {
        super.viewDidAppear(animated)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return denuncias.count
    }

    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("CellId", forIndexPath: indexPath)

        let denuncia = denuncias[indexPath.row]
        
        cell.textLabel?.text = denuncia.convenio?.desc
        cell.detailTextLabel?.text = "Data da Denúncia: " + denuncia.denunciaDate! + "\n" + denuncia.status!

        return cell
    }
    

    /*
    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            // Delete the row from the data source
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else if editingStyle == .Insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(tableView: UITableView, moveRowAtIndexPath fromIndexPath: NSIndexPath, toIndexPath: NSIndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?)
    {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        
        if(segue.identifier == "DetailSegue")
        {
            if let cell = sender as? UITableViewCell
            {
                let idx = tableView.indexPathForCell(cell)
                if let row = idx?.row
                {
                    if let destination = segue.destinationViewController as? DenunciaReadViewController
                    {
                        destination.denuncia = denuncias[row]
                    }
                    
                }
            }
        }

    }
 

}
