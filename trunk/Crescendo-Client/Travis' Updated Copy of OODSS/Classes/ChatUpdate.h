//  ecologylabFundamentalObjC
//
//  Created by William Hamilton on 2/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//
#import <Foundation/Foundation.h>
#import "UpdateMessage.h"

@interface ChatUpdate : UpdateMessage
{
	NSString *message;
	NSString *host;
	int port;
}

@property (nonatomic,readwrite, retain) NSString *message;
@property (nonatomic,readwrite, retain) NSString *host;
@property (nonatomic,readwrite) int port;

- (void) setPortWithReference: (int *) p_port;

@end

