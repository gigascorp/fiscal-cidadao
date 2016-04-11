//
//  Perfil.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 11/04/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

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
