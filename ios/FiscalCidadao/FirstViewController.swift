//
//  FirstViewController.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 08/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController
{

    @IBOutlet weak var textField: UITextField!
    @IBOutlet weak var label: UILabel!
    
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        self.navigationController?.setNavigationBarHidden(true, animated: false)
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func viewDidAppear(animated: Bool)
    {
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}

