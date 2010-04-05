//
//  CrescendoViewController.h
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "ServerUpdateDelegate.h"
#import "XMLClient.h"

#import "ComposeViewController.h"
#import "GamemodeViewController.h"
#import "HelpViewController.h"
#import "TrainingViewController.h"

@interface CrescendoViewController : UIViewController <ServerUpdateDelegate> {
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

