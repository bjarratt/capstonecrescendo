//
//  NSDictionaryList.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/5/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.
//

#import "DictionaryList.h"

@implementation DictionaryList

@synthesize mutableArray;

#pragma mark NSScope - class initializers

+ (id) dictionaryList {
	return [[[DictionaryList alloc] init] autorelease];
}

+ (id) dictionaryListFromCapacity: (NSUInteger) capacity {
	return [[[DictionaryList alloc] initFromCapacity: capacity] autorelease];
}

+ (id) dictionaryListFromDictionary: (NSDictionary *) dictionary {
	return [[[DictionaryList alloc] initFromDictionary: dictionary] autorelease];
}

#pragma mark NSScope - instance initializers

- (id) init {
	mutableArray = [NSMutableArray array];
	mutableDictionary = [NSMutableDictionary dictionary];
	return self;
}

- (id) initFromCapacity: (NSUInteger) capacity {
	mutableArray = [NSMutableArray array];
	mutableDictionary = [NSMutableDictionary dictionaryWithCapacity: capacity];
	return self;
}

- (id) initFromDictionary: (NSDictionary *) dictionary {
	mutableArray = [NSMutableArray array];
	mutableDictionary = [NSMutableDictionary dictionaryWithDictionary: dictionary];
	return self;
}

#pragma mark NSScope - instance functions

- (id) objectAtIndex: (NSUInteger) aKey {
	return [mutableArray objectAtIndex: aKey];
}

- (void) setObject: (id) anObject forKey: (id) aKey {
	id prevObj = [mutableDictionary objectForKey: aKey];
	[mutableDictionary setObject: anObject forKey: aKey];
	if (prevObj != nil) {
		[mutableArray removeObjectIdenticalTo: aKey];
	}
	[mutableArray addObject: anObject];
}

- (NSUInteger) countByEnumeratingWithState: (NSFastEnumerationState *) state objects: (id *) stackbuf count: (NSUInteger) len {
	NSUInteger count = 0;

	if (state->state == 0) {
		state->mutationsPtr = &state->extra[0];
	}

	if (state->state < mutableArray.count) {
		state->itemsPtr = stackbuf;
		while ( (state->state < mutableArray.count) && (count < len) ) {
			stackbuf[count] = [mutableArray objectAtIndex: count];
			state->state++;
			count++;
		}
	}
	else {
		count = 0;
	}

	return count;
}

- (void) removeAllObjects {
	[mutableDictionary removeAllObjects];
	[mutableArray removeAllObjects];
}

- (NSArray *) allValues {
	return mutableArray;
}

//TODO : remove this forward invocation and wrap NSMutableDictionary methods or inherit by implementing those required functions.
//This code gives the class functionality to call all the methods from NSMutableDictionary
- (NSMethodSignature *) methodSignatureForSelector: (SEL) selector {
	if ([mutableDictionary respondsToSelector: selector]) {
		return [mutableDictionary methodSignatureForSelector: selector];
	}
	else {
		return [super methodSignatureForSelector: selector];
	}
}

- (void) forwardInvocation: (NSInvocation *) invocation {
	[invocation invokeWithTarget: mutableDictionary];
}

- (void) recycle {
	//TODO : add recycle funationality later..
}

@end