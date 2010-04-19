//
//  GamemodeViewController.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "XMLClient.h"

#import "ComposeViewController.h"

@interface GamemodeViewController : UIViewController {
	XMLClient *client;
	NSString *playerId;
	IBOutlet ComposeViewController *composeViewController;
}

@property (nonatomic, retain) XMLClient *client;
@property (nonatomic, retain) NSString *playerId;
@property (nonatomic, retain) IBOutlet ComposeViewController *composeViewController;

- (IBAction) goBack;
- (IBAction) freeCompose;
- (IBAction) noteLengths;
- (IBAction) notePitches;

@end
