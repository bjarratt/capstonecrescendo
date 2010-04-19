//
//  ecologylabXMLViewController.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/6/10.
//  Copyright Interface Ecology Lab 2010. All rights reserved.
//

#import "ecologylabXMLViewController.h"
#import "TranslationScope.h"
#import <stdio.h>
#import <objc/runtime.h>

#import "RssState.h"
#import "Schmannel.h"
#import "InitConnectionRequest.h"
#import "DictionaryList.h"
#import "DefaultServicesTranslations.h"
#import "GameData.h"

@implementation ecologylabXMLViewController



/*
   // The designated initializer. Override to perform setup that is required before the view is loaded.
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



// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void) viewDidLoad {
	[super viewDidLoad];
	
	
	//RssState Monomorphic test case
//	//test.xml contains dummy Rss data which will deserialize and then serialize.
	NSString *rssInput = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent: @"test.xml"];
	
	//Creating translation scope from RssTranslationScope.xml and deserializing test.xml
	RssState *test = (RssState *)[ElementState translateFromXML: rssInput translationScope: [RssState getTranslationScope]];

	NSMutableString *rssoutput = [NSMutableString string];
	
	//Serializing the data back to XML into an output MutableString buffer.
	[test translateToXML:rssoutput];
	
	//Throwing output to console
	NSLog(rssoutput);	
//	
//	NSLog(@"\n\n\n\n");	
	
	//GameData Polymorphic test case
	//GameData.xml contains dummy xml data 
	NSString *gameDataInput = [[[NSBundle mainBundle] resourcePath] 
							   stringByAppendingPathComponent: @"GameData.xml"];
	GameData *testGameData = (GameData *) [ElementState translateFromXML: gameDataInput translationScope: [GameData getTranslationScope]];
	
	
	NSMutableString *gamedataOutput = [NSMutableString string];
	
	//Serializing the data back to XML into an ouptut MutableString buffer.
	[testGameData translateToXML:gamedataOutput];
	
	//Throwing output to console
	NSLog(gamedataOutput);
	
	NSLog(@"\n\n\n\n");	
	
	
	//Shmannel Polymorphic Test Case
	//SchmannelTest.xml contains dummy xml data which will be deserialized and then serialized
	NSString *schmannelInput = [[[NSBundle mainBundle] resourcePath] 
								stringByAppendingPathComponent: @"SchmannelTest.xml"];
	Schmannel *testSchmannel = (Schmannel *) [ElementState translateFromXML: schmannelInput translationScope: [Schmannel getTranslationScope]];
	
	
	NSMutableString *schmannelOutput = [NSMutableString string];
	
	//Serializing the data back to XML into an ouptut MutableString buffer.
	[testSchmannel translateToXML:schmannelOutput];
	
	//Throwing output to console
	NSLog(schmannelOutput);
}

/*
   // Override to allow orientations other than the default portrait orientation.
   - (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
   }
 */

- (void) didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
	[super didReceiveMemoryWarning];

	// Release any cached data, images, etc that aren't in use.
}

- (void) viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}

- (void) dealloc {
	[super dealloc];
}

@end