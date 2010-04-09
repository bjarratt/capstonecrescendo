//
//  MenuViewController.m
//  Crescendo
//
//  Created by Senior Capstone on 4/8/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "MenuView.h"


@implementation MenuView

@synthesize backButton;

@synthesize keySlider;
@synthesize timeSlider;
@synthesize tempoSlider;
@synthesize barsSlider;
@synthesize keyText;
@synthesize timeText;
@synthesize tempoText;
@synthesize barsText;
@synthesize keyLabel;
@synthesize timeLabel;
@synthesize tempoLabel;
@synthesize barsLabel;


- (IBAction) goBack {
	[self dismissModalViewControllerAnimated:YES];
}

- (void) drawPortraitView {
	/*
	 * Properties
	 */
	backButton.hidden = NO;

	
	
	/*
	 * Sliders
	 */
	keySlider = [[UISlider alloc] initWithFrame: CGRectMake(-12, 350, 118, 23)];
	keySlider.minimumValue = 1;
	keySlider.maximumValue = 12;
	keySlider.value = 12;
	keySlider.continuous = YES;
	keySlider.transform = CGAffineTransformRotate(keySlider.transform, 270.0/180*M_PI);
	[self.view addSubview:keySlider];
	
	timeSlider = [[UISlider alloc] initWithFrame: CGRectMake(63, 350, 118, 23)];
	timeSlider.minimumValue = 1;
	timeSlider.maximumValue = 3;
	timeSlider.value = 3;
	timeSlider.continuous = YES;
	timeSlider.transform = CGAffineTransformRotate(timeSlider.transform, 270.0/180*M_PI);
	[self.view addSubview:timeSlider];
	
	tempoSlider = [[UISlider alloc] initWithFrame: CGRectMake(136, 350, 118, 23)];
	tempoSlider.minimumValue = 1;
	tempoSlider.maximumValue = 12;
	tempoSlider.value = 12;
	tempoSlider.continuous = YES;
	tempoSlider.transform = CGAffineTransformRotate(tempoSlider.transform, 270.0/180*M_PI);
	[self.view addSubview:tempoSlider];
	
	barsSlider = [[UISlider alloc] initWithFrame: CGRectMake(209, 350, 118, 23)];
	barsSlider.minimumValue = 1;
	barsSlider.maximumValue = 8;
	barsSlider.value = 8;
	barsSlider.continuous = YES;
	barsSlider.transform = CGAffineTransformRotate(barsSlider.transform, 270.0/180*M_PI);
	[self.view addSubview:barsSlider];
	
	/*
	 * Slider Text Fields
	 */
	keyText = [[UITextField alloc] initWithFrame:CGRectMake(15, 220, 60, 60)];
	keyText.text = [NSString stringWithFormat: @"Key"];
	keyText.borderStyle = UITextBorderStyleRoundedRect;
	[self.view addSubview:keyText];
	
	timeText = [[UITextField alloc] initWithFrame:CGRectMake(90, 220, 60, 60)];
	timeText.text = [NSString stringWithFormat: @"Time"];
	timeText.borderStyle = UITextBorderStyleRoundedRect;
	[self.view addSubview:timeText];
	
	tempoText = [[UITextField alloc] initWithFrame:CGRectMake(165, 220, 60, 60)];
	tempoText.text = [NSString stringWithFormat: @"Temp"];
	tempoText.borderStyle = UITextBorderStyleRoundedRect;
	[self.view addSubview:tempoText];
	
	barsText = [[UITextField alloc] initWithFrame:CGRectMake(240, 220, 60, 60)];
	barsText.text = [NSString stringWithFormat: @"Bars"];
	barsText.borderStyle = UITextBorderStyleRoundedRect;
	[self.view addSubview:barsText];
	
	/*
	 * Slider Labels
	 */
	keyLabel = [[UILabel alloc] initWithFrame: CGRectMake(30, 180, 50, 30)];
	keyLabel.text = [NSString stringWithFormat: @"Key"];
	keyLabel.backgroundColor = [UIColor blackColor];
	keyLabel.textColor = [UIColor whiteColor];
	[self.view addSubview:keyLabel];
	
	timeLabel = [[UILabel alloc] initWithFrame: CGRectMake(100, 180, 50, 30)];
	timeLabel.text = [NSString stringWithFormat: @"Time"];
	timeLabel.backgroundColor = [UIColor blackColor];
	timeLabel.textColor = [UIColor whiteColor];
	[self.view addSubview:timeLabel];
	
	tempoLabel = [[UILabel alloc] initWithFrame: CGRectMake(170, 180, 75, 30)];
	tempoLabel.text = [NSString stringWithFormat: @"Tempo"];
	tempoLabel.backgroundColor = [UIColor blackColor];
	tempoLabel.textColor = [UIColor whiteColor];
	[self.view addSubview:tempoLabel];
	
	barsLabel = [[UILabel alloc] initWithFrame: CGRectMake(250, 180, 50, 30)];
	barsLabel.text = [NSString stringWithFormat: @"Bars"];
	barsLabel.backgroundColor = [UIColor blackColor];
	barsLabel.textColor = [UIColor whiteColor];
	[self.view addSubview:barsLabel];
}


/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}
*/

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
    [super dealloc];
}


@end
