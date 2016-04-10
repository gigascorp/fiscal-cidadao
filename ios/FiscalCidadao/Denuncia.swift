//
//  Denuncia.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 03/04/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class Denuncia: NSObject
{
    var id  = 0
    var photos = [String]()
    var convenioId : Int
    let userId : String?
    var comments : String
    var denunciaDate : String?
    var status : String?
    var convenio : Convenio?
    
    init(convenioId : Int)
    {
        userId =  UIDevice.currentDevice().identifierForVendor?.UUIDString
        self.convenioId = convenioId;
        comments = ""
    }
}
