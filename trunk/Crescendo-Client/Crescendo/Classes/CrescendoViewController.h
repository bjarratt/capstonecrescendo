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
#import "ServerTranslations.h"

#import "ComposeViewController.h"
#import "GamemodeViewController.h"
#import "HelpViewController.h"

@interface CrescendoViewController : UIViewController <UIApplicationDelegate, ConnectionUpdateDelegate, GameTypeUpdateDelegate, PlayNoteUpdateDelegate, XMLClientDelegate> {
	XMLClient *client;
	NSString *playerId;
	IBOutlet GamemodeViewController *gamemodeViewController;
	IBOutlet HelpViewController *helpViewController;
	IBOutlet UIButton *gameModes;
	IBOutlet UIButton *connect;
	IBOutlet UITextField *ipText;
	IBOutlet UILabel *ipLabel;
}

@property (nonatomic, retain) XMLClient *client;
@property (nonatomic, retain) NSString *playerId;
@property (nonatomic, retain) IBOutlet GamemodeViewController *gamemodeViewController;
@property (nonatomic, retain) IBOutlet HelpViewController *helpViewController;
@property (nonatomic, retain) IBOutlet UIButton *gameModes;
@property (nonatomic, retain) IBOutlet UIButton *connect;
@property (nonatomic, retain) IBOutlet UITextField *ipText;
@property (nonatomic, retain) IBOutlet UILabel *ipLabel;

- (IBAction) goToGamemodeView;
- (IBAction) goConnect;
- (IBAction) goToHelpView;

- (void) drawIP;
- (void) drawMain;

@end

