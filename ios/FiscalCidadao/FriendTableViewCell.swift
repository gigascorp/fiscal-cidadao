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
