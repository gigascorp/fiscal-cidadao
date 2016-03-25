//
//  BaseController.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 19/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class BaseController : NSObject
{
    var observers : [Observer] = []
    
    func addObserver(observer: Observer)
    {
        observers.append(observer)
    }
    func removeObserver(observer : Observer)
    {
        var i = 0
        for  ; i < observers.count ; i++
        {
            if observers[i].equals(observer)
            {
                break;
            }
        }
        if i < observers.count
        {
            observers.removeAtIndex(i)
        }
    }

}
