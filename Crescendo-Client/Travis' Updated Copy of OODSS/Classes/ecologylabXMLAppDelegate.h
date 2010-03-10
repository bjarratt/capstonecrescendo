//
//  ecologylabXMLAppDelegate.h
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/6/10.
//  Copyright Interface Ecology Lab 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class ecologylabXMLViewController;

@interface ecologylabXMLAppDelegate : NSObject <UIApplicationDelegate> {
	UIWindow *window;
	ecologylabXMLViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet ecologylabXMLViewController *viewController;

@end