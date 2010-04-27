//
//  DataController.m
//  Crescendo
//
//  Created by Brandon Kaster on 4/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "DataController.h"

@implementation DataController

@synthesize list;

- (unsigned) countOfList {
	return [list count];
}

- (id) objectInListAtIndex: (unsigned) theIndex {
	return [list objectAtIndex:theIndex];
}

- (void) removeDataAtIndex: (unsigned) theIndex {
	[list removeObjectAtIndex:theIndex];
}

//Only add new IP to list if it is not already in the list
- (void) addData: (NSString *) data {
	BOOL found;
	found = NO;
	int size = [list count];
	for(int i = 0; i < size; i++){
		NSString *ip = (NSString *) [list objectAtIndex:i];
		if([ip isEqualToString: data])
			found = YES;
	}
	if(!found){
		[list addObject:data];
	}
}

- (void) setList: (NSMutableArray *) newList {
	if (list != newList) {
		[list release];
		list = [newList mutableCopy];
	}
}

- (id) init {
	if (self = [super init]) {
		NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
		NSArray *prefsList = [prefs arrayForKey:@"ipList"];
		if (prefsList != nil) {
			self.list = [prefsList copy];
			[prefsList release];
		}
		else {
			NSMutableArray *localList = [[NSMutableArray alloc] init];
			self.list = localList;
			[localList release];
			
			[self addData:@"128.194.143.165"];
		}
	}
	return self;
}

- (void) dealloc {
	[list release];
	[super dealloc];
}

@end
