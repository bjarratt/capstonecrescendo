//
//  MenuViewController.h
//  Crescendo
//
//  Created by Senior Capstone on 4/8/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface MenuView : UIViewController <UIScrollViewDelegate> {

	IBOutlet UIButton *backButton;
	
	UISlider *keySlider;
	UISlider *timeSlider;
	UISlider *tempoSlider;
	UISlider *barsSlider;
	
	UITextField *keyText;
	UITextField *timeText;
	UITextField *tempoText;
	UITextField *barsText;
	
	UILabel *keyLabel;
	UILabel *timeLabel;
	UILabel *tempoLabel;
	UILabel *barsLabel;

}


@property (nonatomic, retain) IBOutlet UIButton *backButton;

@property (readwrite, assign) UISlider *keySlider;
@property (readwrite, assign) UISlider *timeSlider;
@property (readwrite, assign) UISlider *tempoSlider;
@property (readwrite, assign) UISlider *barsSlider;
@property (readwrite, assign) UITextField *keyText;
@property (readwrite, assign) UITextField *timeText;
@property (readwrite, assign) UITextField *tempoText;
@property (readwrite, assign) UITextField *barsText;
@property (readwrite, assign) UILabel *keyLabel;
@property (readwrite, assign) UILabel *timeLabel;
@property (readwrite, assign) UILabel *tempoLabel;
@property (readwrite, assign) UILabel *barsLabel;

- (IBAction) goBack;
- (void) drawPortraitView;


@end
