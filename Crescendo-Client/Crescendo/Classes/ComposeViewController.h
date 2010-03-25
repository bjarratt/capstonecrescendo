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
	IBOutlet UIScrollView *myPitchScrollView;
	
	UIImage lengthImages[1];
	
	int lengthPage;
	int pitchPage;
}

@property (nonatomic, retain) UILabel *buildLabel;
@property (nonatomic, retain) UIView *myHolderView;
@property (nonatomic, retain) UIScrollView *myPitchScrollView;
@property (nonatomic, retain) UIScrollView *myLengthScrollView;

- (void) scrollViewDidEndDragging: (UIScrollView *) scrollView willDecelerate: (BOOL) decelerate;
- (void) scrollViewDidEndDecelerating: (UIScrollView *) scrollView;
- (void) determineScrollViewPage: (UIScrollView *) scrollView;

- (IBAction) build;
- (IBAction) goBack;

@end
