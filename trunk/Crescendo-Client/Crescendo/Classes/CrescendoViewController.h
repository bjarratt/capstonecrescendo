//
//  CrescendoViewController.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "ConnectionUpdateDelegate.h"
#import	"GameTypeUpdateDelegate.h"
#import "PlayNoteUpdateDelegate.h"
#import "XMLClient.h"

#import "ComposeViewController.h"
#import "GamemodeViewController.h"
#import "HelpViewController.h"

@interface CrescendoViewController : UIViewController <ConnectionUpdateDelegate> {
	XMLClient *client;
	NSString *playerId;
	IBOutlet GamemodeViewController *gamemodeViewController;
	IBOutlet HelpViewController *helpViewController;
}

@property (nonatomic, retain) XMLClient *client;
@property (nonatomic, retain) NSString *playerId;
@property (nonatomic, retain) IBOutlet GamemodeViewController *gamemodeViewController;
@property (nonatomic, retain) IBOutlet HelpViewController *helpViewController;


- (IBAction) goToGamemodeView;
- (IBAction) goToHelpView;

@end

