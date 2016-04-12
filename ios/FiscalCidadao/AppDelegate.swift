//
//  AppDelegate.swift
//  FiscalCidadao
//
//  Created by Wallas Henrique Santos on 08/03/16.
//  Copyright © 2016 Wallas Henrique Santos. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool
    {
        let locationController = LocationController.sharedInstance
        
        if locationController.hasLocation()
        {
            let dataController = DataController.sharedInstance
            
            dataController.loadConvenios(locationController.getLocation())
        }
        else
        {
            locationController.requestAuth()
        }
        
        UINavigationBar.appearance().tintColor = UIColor.whiteColor()
        UINavigationBar.appearance().setBackgroundImage(UIImage(named: "navBackground"), forBarMetrics: .Default)
        
        UINavigationBar.appearance().titleTextAttributes = [NSForegroundColorAttributeName : UIColor.whiteColor()]
        UITabBar.appearance().tintColor = UIColor.whiteColor()
//        UITabBar.appearance().thumbTintColor = UIColor(red: 1.0, green: 1.0, blue: 1.0, alpha: 0.5)
        //UIColor(red: 1.0, green: 0.0, blue: 0.0, alpha: 1.0);
        UITabBar.appearance().backgroundImage = UIImage(named: "tabBarBackground")
        
        UINavigationBar.appearance().barStyle = UIBarStyle.BlackOpaque
        UIApplication.sharedApplication().statusBarStyle = UIStatusBarStyle.LightContent

//        [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent];
        
        return true
    }

    func applicationWillResignActive(application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(application: UIApplication) {
        // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }


}

