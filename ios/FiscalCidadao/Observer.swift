//
//  Observer.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 19/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import Foundation


protocol Observer
{
    var theObserver : NSObject? {get}
}

extension Observer
{
    func equals(o : Observer) -> Bool
    {
        if o.theObserver != nil && theObserver != nil
        {
            return theObserver! == o.theObserver!
        }
        return false;
    }
}
