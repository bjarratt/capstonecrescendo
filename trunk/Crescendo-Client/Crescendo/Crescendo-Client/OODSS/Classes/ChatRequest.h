//  ecologylabFundamentalObjC
//
//  Created by William Hamilton on 2/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//
#import <Foundation/Foundation.h>
#import "RequestMessage.h"

@interface ChatRequest : RequestMessage
{
	NSString *message;
}

@property (nonatomic,readwrite, retain) NSString *message;

@end

