//
//  CrescendoViewController.h
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "ChatUpdateDelegate.h"
#import "XMLClient.h"

#import "ComposeViewController.h"
#import "GamemodeViewController.h"
#import "HelpViewController.h"
#import "TrainingViewController.h"

@interface CrescendoViewController : UIViewController <ChatUpdateDelegate> {
	XMLClient *client;
	IBOutlet ComposeViewController *composeViewController;
	IBOutlet GamemodeViewController *gamemodeViewController;
	IBOutlet HelpViewController *helpViewController;
	IBOutlet TrainingViewController *trainingViewController;
}

@property (nonatomic, retain) XMLClient *client;
@property (nonatomic, retain) ComposeViewController *composeViewController;
@property (nonatomic, retain) GamemodeViewController *gamemodeViewController;
@property (nonatomic, retain) HelpViewController *helpViewController;
@property (nonatomic, retain) TrainingViewController *trainingViewController;

- (IBAction) goToComposeView;
- (IBAction) goToGamemodeView;
- (IBAction) goToHelpView;
- (IBAction) goToTrainingView;

@end

