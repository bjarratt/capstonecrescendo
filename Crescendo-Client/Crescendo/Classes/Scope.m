//
//  NSScope.m
//  ecologylabXML
//
//  Created by Nabeel Shahzad on 1/5/10.
//  Copyright 2010 Interface Ecology Lab. All rights reserved.NSUInteger
//

#import "Scope.h"


@implementation Scope

@synthesize m_parent;

#pragma mark NSScope - class initializers

+ (id) scope {
	return [[[Scope alloc] init] autorelease];
}

+ (id) scopeWithParent: (NSDictionary *) parent {
	return [[[Scope alloc] initWithParent: parent] autorelease];
}

+ (id) scopeWithCapacity: (NSUInteger) capacity {
	return [[[Scope alloc] initWithCapacity: capacity] autorelease];
}

+ (id) scopeWithParentAndCapacity: (NSDictionary *) parent withCapacity: (NSUInteger) capacity {
	return [[[Scope alloc] initWithParentAndCapacity: parent withCapacity: capacity] autorelease];
}

#pragma mark NSScope - instance initializers

- (id) init {
	if ( (self ==[super init]) ) {
    mutableDictionary = [[NSMutableDictionary alloc] init];
	}

	return self;
}

- (id) initWithParent: (NSDictionary *) parent {
	if ( (self ==[super init]) ) {
		self.m_parent = parent;
	}

	return self;
}

- (id) initWithCapacity: (NSUInteger) capacity {
	if ( (self ==[super init]) ) {
		mutableDictionary = [[NSMutableDictionary alloc] initWithCapacity: capacity];
	}

	return self;
}

- (id) initWithParentAndCapacity: (NSDictionary *) parent withCapacity: (NSUInteger) capacity {
	if ( (self ==[super init]) ) {
		self.m_parent = parent;
		mutableDictionary = [[NSMutableDictionary alloc] initWithCapacity: capacity];
	}

	return self;
}

#pragma mark NSScope - instance functions

- (id) objectForKey: (id) aKey {
	id result = [mutableDictionary objectForKey: aKey];
	NSDictionary *operativeParent = m_parent;
	return (result != nil) ? result : ( (operativeParent != nil) ? [operativeParent objectForKey: aKey] : nil );
}

- (void) setObject: (id) anObject forKey: (id) aKey {
	[mutableDictionary setObject: anObject forKey: aKey];
}

- (void) setParent: (NSDictionary *) newParent {
	NSDictionary *thisParent = m_parent;
	if (thisParent == nil) {
		NSLog(@"Scope : Setting parent to %@ but it was already %@", [newParent description], [thisParent description]);
	}
  [thisParent release];
	m_parent = newParent;
  [thisParent retain];
}

- (NSDictionary *) operativeParent {
	return m_parent;
}

- (NSString *) toString {
	//TODO: complete this later add parent message as in java version
	return [NSString stringWithFormat: @"[NSScope w %i elements]", mutableDictionary.count];
}

- (NSString *) sizeMsg {
	//TODO: complete this later
	return nil;
}

- (NSString *) dump {
	//TODO: complete this later
	return nil;
}

- (void) dump: (NSString *) result withPrefix: (NSString *) prefix {
	//TODO: complete this later
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


@end