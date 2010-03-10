//
//  InitiConnectionResponse.h
//  ecologylabXML
//
//  Created by ecologylab on 1/22/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ecologylabxml.h"
#import "ResponseMessage.h"

@interface InitConnectionResponse : ResponseMessage
{
	NSString *sessionId;
}

@property (nonatomic,readwrite, retain) NSString *sessionId;

@end

