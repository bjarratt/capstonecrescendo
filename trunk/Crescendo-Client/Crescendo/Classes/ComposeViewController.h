//
//  ComposeViewController.h
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ComposeViewController : UIViewController <UIScrollViewDelegate> {
	IBOutlet UIView *myHolderView;
	IBOutlet UILabel *buildLabel;
	UIScrollView *myLengthScrollView;
	UIScrollView *myPitchScrollView;
	
	UIImage lengthImages[1];
	
	NSString *noteLength;
	NSString *notePitch;
}

@property (nonatomic, retain) IBOutlet UILabel *buildLabel;
@property (nonatomic, retain) IBOutlet UIView *myHolderView;
@property (nonatomic, retain) UIScrollView *myPitchScrollView;
@property (nonatomic, retain) UIScrollView *myLengthScrollView;
@property (nonatomic, retain) NSString *noteLength;
@property (nonatomic, retain) NSString *notePitch;

- (void) scrollViewDidEndDragging: (UIScrollView *) scrollView willDecelerate: (BOOL) decelerate;
- (void) scrollViewDidEndDecelerating: (UIScrollView *) scrollView;
- (void) determineScrollViewPage: (UIScrollView *) scrollView;

- (void) build;

- (IBAction) goBack;

@end
