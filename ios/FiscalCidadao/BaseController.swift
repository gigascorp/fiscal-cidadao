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

class BaseController: NSObject
{
    var observers : [Observer] = []
    
    func addObserver(observer: Observer)
    {
        observers.append(observer)
    }
    
    func removeObserver(observer : Observer)
    {
        var i = 0;
        for o in observers
        {
            if o.equals(observer)
            {
                i += 1
                break;
            }
            i += 1
            
        }
        if i < observers.count
        {
            observers.removeAtIndex(i)
        }
    }
    
    func notify(message: Int)
    {
        for o in observers
        {
            if let selector = o.getSelectorForMessage(message, sender: self)
            {
                if o.theObserver!.respondsToSelector(selector)
                {
                    o.theObserver?.performSelector(selector)
                }
            }
        }
    }

}
