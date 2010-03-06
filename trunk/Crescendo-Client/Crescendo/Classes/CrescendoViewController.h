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
#import "HelpViewController.h"

@interface CrescendoViewController : UIViewController {
	IBOutlet TrainingViewController *trainingViewController;
	IBOutlet GamemodeViewController *gamemodeViewController;
	IBOutlet HelpViewController *helpViewController;
}

- (IBAction) goToTrainingView;
- (IBAction) goToGamemodeView;
- (IBAction) goToHelpView;

@end

