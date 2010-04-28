//
//  HelpViewController.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface HelpViewController : UIViewController {

	UITextView *helpText;
}

@property (nonatomic, retain) UITextView *helpText;

- (IBAction) goBack;
-(void) showMainHelp;

@end
