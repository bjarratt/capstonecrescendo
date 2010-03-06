//
//  CrescendoAppDelegate.m
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "CrescendoAppDelegate.h"
#import "CrescendoViewController.h"

@implementation CrescendoAppDelegate

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
