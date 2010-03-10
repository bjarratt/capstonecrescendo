//
//  ecologylabXMLAppDelegate.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/6/10.
//  Copyright Interface Ecology Lab 2010. All rights reserved.
//

#import "ecologylabXMLAppDelegate.h"
#import "ecologylabXMLViewController.h"

@implementation ecologylabXMLAppDelegate

@synthesize window;
@synthesize viewController;


- (void)applicationDidFinishLaunching:(UIApplication *)application {    
    
    // Override point for customization after app launch    
    [window addSubview:viewController.view];
    [window makeKeyAndVisible];
}


- (void)dealloc {
    [viewController release];
    [window release];
    [super dealloc];
}


@end
