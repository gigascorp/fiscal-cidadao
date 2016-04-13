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

class Perfil: NSObject
{
    var id : String?
    var name : String?
    var urlPhoto : String?
    var score : Int = 0
    var countDenuncias : Int = 0
    var registerDate : String?
    var email : String?
    
    static func getUserIdByDevice() -> String!
    {
        return UIDevice.currentDevice().identifierForVendor?.UUIDString
    }
    
    func isValid() -> Bool
    {
        return name != nil && urlPhoto != nil && registerDate != nil
    }

}
