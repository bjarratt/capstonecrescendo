//
//  MyScrollView.m
//  Crescendo
//
//  Created by Brandon on 3/18/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "MyScrollView.h"


@implementation MyScrollView

- (void) touchesEnded: (NSSet *) touches withEvent: (UIEvent *) event {
	
	if (!self.dragging) {
		[self.nextResponder touchesEnded: touches withEvent:event]; 
	}		
	[super touchesEnded: touches withEvent: event];
}

@end
