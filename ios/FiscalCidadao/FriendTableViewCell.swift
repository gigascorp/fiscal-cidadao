//
//  FriendTableViewCell.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 11/04/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class FriendTableViewCell: UITableViewCell {

    @IBOutlet var friendImageView: UIImageView!
    
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var scoreLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        friendImageView.layer.masksToBounds = true
        friendImageView.layer.cornerRadius = 40
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
