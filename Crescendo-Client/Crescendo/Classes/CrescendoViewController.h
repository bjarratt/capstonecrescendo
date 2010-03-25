//
//  CrescendoViewController.h
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TrainingViewController.h"
#import "GamemodeViewController.h"
#import "ComposeViewController.h"
#import "HelpViewController.h"
#import "ChatUpdateDelegate.h"
#import "XMLClient.h"

@interface CrescendoViewController : UIViewController <ChatUpdateDelegate> {
	IBOutlet TrainingViewController *trainingViewController;
	IBOutlet GamemodeViewController *gamemodeViewController;
	IBOutlet ComposeViewController *composeViewController;
	IBOutlet HelpViewController *helpViewController;
	XMLClient *client;
}

@property (nonatomic, retain) XMLClient* client;
@property (nonatomic, retain) TrainingViewController *trainingViewController;
@property (nonatomic, retain) GamemodeViewController *gamemodeViewController;
@property (nonatomic, retain) ComposeViewController *composeViewController;
@property (nonatomic, retain) HelpViewController *helpViewController;

- (IBAction) goToTrainingView;
- (IBAction) goToGamemodeView;
- (IBAction) goToComposeView;
- (IBAction) goToHelpView;

@end

