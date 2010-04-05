//
//  CrescendoViewController.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "ServerUpdateDelegate.h"
#import "ConnectionUpdateDelegate.h"
#import "XMLClient.h"

#import "ComposeViewController.h"
#import "GamemodeViewController.h"
#import "HelpViewController.h"
#import "TrainingViewController.h"

@interface CrescendoViewController : UIViewController <ServerUpdateDelegate, ConnectionUpdateDelegate> {
	XMLClient *client;
	NSString *playerId;
	IBOutlet ComposeViewController *composeViewController;
	IBOutlet GamemodeViewController *gamemodeViewController;
	IBOutlet HelpViewController *helpViewController;
	IBOutlet TrainingViewController *trainingViewController;
}

@property (nonatomic, retain) XMLClient *client;
@property (nonatomic, retain) NSString *playerId;
@property (nonatomic, retain) IBOutlet ComposeViewController *composeViewController;
@property (nonatomic, retain) IBOutlet GamemodeViewController *gamemodeViewController;
@property (nonatomic, retain) IBOutlet HelpViewController *helpViewController;
@property (nonatomic, retain) IBOutlet TrainingViewController *trainingViewController;

- (IBAction) goToComposeView;
- (IBAction) goToGamemodeView;
- (IBAction) goToHelpView;
- (IBAction) goToTrainingView;

@end

