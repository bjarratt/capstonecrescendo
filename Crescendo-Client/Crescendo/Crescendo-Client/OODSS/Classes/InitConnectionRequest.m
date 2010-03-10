//
//  InitConnectionRequest.m
//  ecologylabXML
//
//  Created by ecologylab on 1/22/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "InitConnectionRequest.h"


@implementation InitConnectionRequest

@synthesize sessionId;

+ (id) testObject {
	return [[[InitConnectionRequest alloc] initTestObject] autorelease];
}

- (id) initTestObject {
	if((self = [super initTestObject])){
		sessionId = @"test id";
	}
	return self;
}

@end
