//
//  InitConnectionRequest.h
//  ecologylabXML
//
//  Created by ecologylab on 1/22/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ecologylabxml.h"
#import "RequestMessage.h"

@interface InitConnectionRequest : RequestMessage
{	
	NSString *sessionId;
}


@property (nonatomic,readwrite, retain) NSString *sessionId;

+ (id) testObject;
- (id) initTestObject;

@end

