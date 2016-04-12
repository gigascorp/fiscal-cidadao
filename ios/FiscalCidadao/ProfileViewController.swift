//
//  ProfileViewController.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 11/04/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

class ProfileViewController: UIViewController
{
    
    var username : String?
    var registerDate : String?
    var score : Int?

    @IBOutlet weak var profileImageView: UIImageView!
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var countLabel: UILabel!
    @IBOutlet weak var scoresLabel: UILabel!
    
    @IBOutlet weak var loadingView: UIActivityIndicatorView!
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        self.usernameLabel.text = "Carregando..."
        self.dateLabel.text = ""
        self.countLabel.text = ""
        self.scoresLabel.text = ""
    
        reloadProfile()
    }

    func reloadProfile()
    {
        let data = DataController.sharedInstance
        data.loadProfile(Perfil.getUserIdByDevice(), onCompletion:
            {
                perfil in
                if perfil != nil
                {
                    self.usernameLabel.text = perfil!.name
                    self.dateLabel.text = "Fiscal Cidadão desde \(perfil!.registerDate!)"
                    self.countLabel.text = "Realizou \(perfil!.countDenuncias) denúncias"
                    self.scoresLabel.text = "e somou \(perfil!.score) pontos até agora!"
                    
                    if let url = perfil?.urlPhoto
                    {
                        data.makeHTTPGetRequest(url, body: nil, onCompletion:
                            {
                                (data, err) in
                                let img  = UIImage(data: data)
                                self.profileImageView.image = img
                                self.loadingView.hidden = true
                        })
                        
                    }
                }
                else
                {
                    self.usernameLabel.text = "Usuário não autenticado"
                    self.dateLabel.text = ""
                    self.countLabel.text = ""
                    self.scoresLabel.text = ""
                    self.loadingView.hidden = true
                }
        })
    }
    
    override func viewDidAppear(animated: Bool)
    {
        super.viewDidAppear(animated)
        reloadProfile()
    }
    override func didReceiveMemoryWarning()
    {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
