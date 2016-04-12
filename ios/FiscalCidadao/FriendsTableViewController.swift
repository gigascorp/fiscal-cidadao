//
//  FriendsTableViewController.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 11/04/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class FriendsTableViewController: UITableViewController
{

    
    var friends = [FriendRank]()
    var images = [UIImage?]()
    override func viewDidLoad()
    {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
        
        loadData()
    }
    
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func loadData()
    {
        let data = DataController.sharedInstance
        
        data.getFriendsRanking(Perfil.getUserIdByDevice(), onCompletion:
        {
            friends in
            
            self.friends = friends
            self.images = [UIImage?](count: friends.count, repeatedValue: nil)
            self.tableView.reloadData()
            
            var i = 0
            for f in friends
            {
                self.loadFriendPhoto(f.urlPhoto!, index: i)
                i+=1
            }
        })
        
    }
    
    func loadFriendPhoto(url : String, index : Int)
    {
        let data = DataController.sharedInstance
        
        data.makeHTTPGetRequest(url, body: nil, onCompletion:
        {
            data, err in
            let img = UIImage(data: data)
            self.images[index] = img
            
            self.tableView.beginUpdates()
            self.tableView.reloadRowsAtIndexPaths([NSIndexPath(forRow: index, inSection: 0)], withRowAnimation: UITableViewRowAnimation.Fade)
            self.tableView.endUpdates()
            
        })
    }

    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return friends.count
    }

    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
         let cell = tableView.dequeueReusableCellWithIdentifier("CellId", forIndexPath: indexPath) as! FriendTableViewCell
        
        cell.nameLabel.text = friends[indexPath.row].name
        cell.scoreLabel.text = "Pontuação : \(friends[indexPath.row].score!)"
        
        if let img = images[indexPath.row]
        {
            cell.friendImageView.image = img
        }
        
        // Configure the cell...

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

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
