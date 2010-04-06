//
//  TrainingViewController.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "GameTypeUpdateDelegate.h"
#import "XMLClient.h"

@interface TrainingViewController : UIViewController {
	XMLClient *client;
}

@property (nonatomic, retain) XMLClient *client;

- (IBAction) goBack;
- (IBAction) keepTheBeat;
- (IBAction) noteLengths;
- (IBAction) notePitches;

@end
