//
//  XmlParser.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 04/04/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class XmlParser: NSObject, NSXMLParserDelegate
{
    
    var statusCode = 0
    
    var isOnString = false
    
    func parser(parser: NSXMLParser, didStartElement elementName: String, namespaceURI: String?, qualifiedName qName: String?, attributes attributeDict: [String : String])
    {
        
        if elementName == "string"
        {
            isOnString = true
        }
        
    }
    
    func parser(parser: NSXMLParser, didEndElement elementName: String, namespaceURI: String?, qualifiedName qName: String?)
    {
        if elementName == "string"
        {
            isOnString = false
        }
    }
    
    func parser(parser: NSXMLParser, foundCharacters string: String)
    {
        if let value = Int(string)
        {
            statusCode = value
        }
        
    }

}
