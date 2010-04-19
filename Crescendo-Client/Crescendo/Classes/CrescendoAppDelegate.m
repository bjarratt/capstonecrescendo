//
//  CrescendoAppDelegate.m
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
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


- (void) dealloc {
    [viewController release];
    [window release];
    [super dealloc];
}

#pragma mark UIApplicationDelegate Methods

// Close the connection when the user hits the home button.
- (void) applicationWillTerminate:(UIApplication*) application
{
	[viewController.client disconnect];
}
@end
