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
#import "HelpViewController.h"

@class DataController;

@interface CrescendoViewController : UIViewController <ConnectionUpdateDelegate, GameTypeUpdateDelegate, GameStateUpdateDelegate, PlayNoteUpdateDelegate, XMLClientDelegate, UITextFieldDelegate, UIAlertViewDelegate, UITableViewDelegate, UITableViewDataSource> {
	XMLClient *client;
	BOOL clientConnected;
	NSString *playerId;
	IBOutlet ComposeViewController *composeViewController;
	IBOutlet HelpViewController *helpViewController;
	IBOutlet UIButton *start;
	IBOutlet UIButton *connect;
	IBOutlet UIButton *disconnect;
	IBOutlet UITextField *ipText;
	IBOutlet UILabel *ipLabel;
	NSString *validatedIp;
	DataController *dataController;
	UITableView *ipTableView;
}

@property (nonatomic, retain) XMLClient *client;
@property (nonatomic, readwrite, assign) BOOL clientConnected;
@property (nonatomic, retain) NSString *playerId;
@property (nonatomic, retain) IBOutlet ComposeViewController *composeViewController;
@property (nonatomic, retain) IBOutlet HelpViewController *helpViewController;
@property (nonatomic, retain) IBOutlet UIButton *start;
@property (nonatomic, retain) IBOutlet UIButton *connect;
@property (nonatomic, retain) IBOutlet UIButton *disconnect;
@property (nonatomic, retain) IBOutlet UITextField *ipText;
@property (nonatomic, retain) IBOutlet UILabel *ipLabel;
@property (nonatomic, retain) NSString *validatedIp;
@property (nonatomic, retain) DataController *dataController;
@property (nonatomic, retain) UITableView *ipTableView;

- (IBAction) goStart;
- (IBAction) goConnect;
- (IBAction) goDisconnect;
- (IBAction) goToHelpView;

- (void) drawIP;
- (void) drawMain;

@end

